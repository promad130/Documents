# 06 - Filesystem: FAT32 Implementation

## Table of Contents

1. [Overview](#overview)
2. [Disk Structure](#disk-structure)
3. [FAT32 Architecture](#fat32-architecture)
4. [Directory Entries](#directory-entries)
5. [File Operations](#file-operations)
6. [Implementation](#implementation)

---

## Overview

A **filesystem** organizes data on storage devices, providing:

1. **File abstraction** - Named collections of bytes
2. **Directory hierarchy** - Organizing files in folders
3. **Metadata** - Size, timestamps, permissions
4. **Space management** - Tracking free/used blocks
5. **Access control** - Who can read/write files

### Why FAT32?

**FAT32 (File Allocation Table, 32-bit)** is simple and widely supported:

✅ No complex features → easier to implement  
✅ Supported by Windows, Linux, macOS  
✅ Works on USB drives, SD cards  
✅ Well-documented specification  
❌ No permissions/ownership  
❌ No journaling (data loss risk)  
❌ 4 GB max file size

---

## Disk Structure

### Partition Table (MBR)

The **Master Boot Record (MBR)** is sector 0 of the disk:

```
Sector 0 (MBR - 512 bytes):
┌─────────────────────────┬─────────────────────┬──────┐
│ Bootloader (440 bytes)  │ Partition Table (64)│ 0x55 │
│                         │                     │ 0xAA │
└─────────────────────────┴─────────────────────┴──────┘
                           4 entries × 16 bytes   Signature
```

### MBR Structure

```c
typedef struct {
    uint8_t bootloader[440];
    uint32_t disk_signature;
    uint16_t reserved;

    // 4 partition entries
    struct {
        uint8_t  status;            // 0x80 = bootable
        uint8_t  first_chs[3];      // CHS address (obsolete)
        uint8_t  partition_type;    // 0x0B or 0x0C = FAT32
        uint8_t  last_chs[3];
        uint32_t lba_first;         // First sector (LBA)
        uint32_t sector_count;      // Number of sectors
    } __attribute__((packed)) partitions[4];

    uint16_t signature;             // 0xAA55
} __attribute__((packed)) MBR;
```

### Finding FAT32 Partition

```c
#define FAT32_PARTITION_TYPE_LBA 0x0C
#define FAT32_PARTITION_TYPE_CHS 0x0B

int fat32_find_partition(MBR* mbr, uint32_t* partition_lba) {
    // Check MBR signature
    if (mbr->signature != 0xAA55) {
        return -1;
    }

    // Search for FAT32 partition
    for (int i = 0; i < 4; i++) {
        uint8_t type = mbr->partitions[i].partition_type;
        if (type == FAT32_PARTITION_TYPE_LBA ||
            type == FAT32_PARTITION_TYPE_CHS) {
            *partition_lba = mbr->partitions[i].lba_first;
            return 0;
        }
    }

    return -1;  // No FAT32 partition
}
```

---

## FAT32 Architecture

### Boot Sector (VBR - Volume Boot Record)

The first sector of the FAT32 partition:

```c
typedef struct {
    uint8_t  jmp_boot[3];           // Jump instruction
    uint8_t  oem_name[8];           // OEM identifier

    // BIOS Parameter Block (BPB)
    uint16_t bytes_per_sector;      // Usually 512
    uint8_t  sectors_per_cluster;   // Power of 2 (1, 2, 4, 8, ...)
    uint16_t reserved_sectors;      // Usually 32
    uint8_t  num_fats;              // Usually 2
    uint16_t root_entry_count;      // 0 for FAT32
    uint16_t total_sectors_16;      // 0 for FAT32
    uint8_t  media_type;            // 0xF8 = hard disk
    uint16_t fat_size_16;           // 0 for FAT32
    uint16_t sectors_per_track;
    uint16_t num_heads;
    uint32_t hidden_sectors;
    uint32_t total_sectors_32;      // Total sectors

    // FAT32 Extended BPB
    uint32_t fat_size_32;           // Sectors per FAT
    uint16_t ext_flags;
    uint16_t fs_version;
    uint32_t root_cluster;          // Usually 2
    uint16_t fs_info;               // FS Info sector
    uint16_t backup_boot_sector;    // Usually 6
    uint8_t  reserved[12];
    uint8_t  drive_number;
    uint8_t  reserved1;
    uint8_t  boot_signature;        // 0x29
    uint32_t volume_id;
    uint8_t  volume_label[11];
    uint8_t  fs_type[8];            // "FAT32   "

    uint8_t  boot_code[420];
    uint16_t signature;             // 0xAA55
} __attribute__((packed)) FAT32_BootSector;
```

### Key BPB Values

```c
void parse_boot_sector(FAT32_BootSector* bs, FAT32_Info* info) {
    info->bytes_per_sector = bs->bytes_per_sector;
    info->sectors_per_cluster = bs->sectors_per_cluster;
    info->reserved_sectors = bs->reserved_sectors;
    info->num_fats = bs->num_fats;
    info->fat_size = bs->fat_size_32;
    info->root_cluster = bs->root_cluster;
    info->total_sectors = bs->total_sectors_32;

    // Calculate important values
    info->first_fat_sector = info->reserved_sectors;
    info->first_data_sector = info->reserved_sectors +
                              (info->num_fats * info->fat_size);
    info->data_sectors = info->total_sectors - info->first_data_sector;
    info->total_clusters = info->data_sectors / info->sectors_per_cluster;
}
```

### Disk Layout

```
┌─────────────────────────────────────────┐
│ Reserved Region (32 sectors)            │ ← Boot Sector
├─────────────────────────────────────────┤
│ FAT #1 (fat_size_32 sectors)           │ ← File Allocation Table
├─────────────────────────────────────────┤
│ FAT #2 (fat_size_32 sectors)           │ ← Backup FAT
├─────────────────────────────────────────┤
│ Data Region                             │
│   Cluster 2 (Root Directory)            │
│   Cluster 3                             │
│   Cluster 4                             │
│   ...                                   │
│   Cluster N                             │
└─────────────────────────────────────────┘
```

💡 **Tip**: Cluster numbering starts at 2, not 0!

---

## FAT (File Allocation Table)

The FAT is an array where each entry corresponds to a cluster:

```
FAT Entry:
┌────────────┬────────────┬────────────┬────────────┐
│ Cluster 0  │ Cluster 1  │ Cluster 2  │ Cluster 3  │
│ 0xFFFFFFF8 │ 0xFFFFFFFF │ 0x00000004 │ 0x00000005 │
└────────────┴────────────┴────────────┴────────────┘
  (reserved)   (reserved)   (next=4)     (next=5)

┌────────────┬────────────┐
│ Cluster 4  │ Cluster 5  │
│ 0xFFFFFFFF │ 0x00000000 │
└────────────┴────────────┘
  (EOF)        (free)
```

### FAT Entry Values

| Value                   | Meaning               |
| ----------------------- | --------------------- |
| 0x00000000              | Free cluster          |
| 0x00000002 - 0x0FFFFFEF | Next cluster in chain |
| 0x0FFFFFF7              | Bad cluster           |
| 0x0FFFFFF8 - 0x0FFFFFFF | End of file (EOF)     |

### Reading FAT Entries

```c
uint32_t fat_read_entry(FAT32_Info* info, uint32_t cluster) {
    uint32_t fat_offset = cluster * 4;  // 4 bytes per entry
    uint32_t fat_sector = info->partition_lba +
                          info->first_fat_sector +
                          (fat_offset / info->bytes_per_sector);
    uint32_t entry_offset = fat_offset % info->bytes_per_sector;

    // Read FAT sector
    uint8_t buffer[512];
    disk_read(fat_sector, 1, buffer);

    // Extract entry
    uint32_t* entry = (uint32_t*)(buffer + entry_offset);
    return *entry & 0x0FFFFFFF;  // Mask upper 4 bits
}
```

### Writing FAT Entries

```c
void fat_write_entry(FAT32_Info* info, uint32_t cluster, uint32_t value) {
    uint32_t fat_offset = cluster * 4;
    uint32_t fat_sector = info->partition_lba +
                          info->first_fat_sector +
                          (fat_offset / info->bytes_per_sector);
    uint32_t entry_offset = fat_offset % info->bytes_per_sector;

    uint8_t buffer[512];
    disk_read(fat_sector, 1, buffer);

    uint32_t* entry = (uint32_t*)(buffer + entry_offset);
    *entry = (*entry & 0xF0000000) | (value & 0x0FFFFFFF);

    disk_write(fat_sector, 1, buffer);

    // Write to backup FAT
    uint32_t backup_sector = fat_sector + info->fat_size;
    disk_write(backup_sector, 1, buffer);
}
```

### Cluster to Sector Conversion

```c
uint32_t cluster_to_sector(FAT32_Info* info, uint32_t cluster) {
    return info->partition_lba +
           info->first_data_sector +
           ((cluster - 2) * info->sectors_per_cluster);
}
```

📝 **Note**: Subtract 2 because cluster numbering starts at 2.

---

## Directory Entries

Each directory entry is **32 bytes**:

```c
typedef struct {
    uint8_t  name[11];              // 8.3 filename (no dot)
    uint8_t  attr;                  // Attributes
    uint8_t  nt_reserved;           // Windows NT flags
    uint8_t  created_time_tenth;    // Creation time (0.1s)
    uint16_t created_time;          // HH:MM:SS
    uint16_t created_date;          // YYYY-MM-DD
    uint16_t accessed_date;         // Last access date
    uint16_t cluster_high;          // High 16 bits of cluster
    uint16_t modified_time;         // HH:MM:SS
    uint16_t modified_date;         // YYYY-MM-DD
    uint16_t cluster_low;           // Low 16 bits of cluster
    uint32_t file_size;             // Size in bytes
} __attribute__((packed)) FAT32_DirEntry;
```

### Attributes

```c
#define ATTR_READ_ONLY  0x01
#define ATTR_HIDDEN     0x02
#define ATTR_SYSTEM     0x04
#define ATTR_VOLUME_ID  0x08
#define ATTR_DIRECTORY  0x10
#define ATTR_ARCHIVE    0x20
#define ATTR_LONG_NAME  (ATTR_READ_ONLY | ATTR_HIDDEN | \
                         ATTR_SYSTEM | ATTR_VOLUME_ID)
```

### Getting Cluster Number

```c
uint32_t get_cluster(FAT32_DirEntry* entry) {
    return ((uint32_t)entry->cluster_high << 16) | entry->cluster_low;
}

void set_cluster(FAT32_DirEntry* entry, uint32_t cluster) {
    entry->cluster_high = (uint16_t)(cluster >> 16);
    entry->cluster_low = (uint16_t)(cluster & 0xFFFF);
}
```

### Short Filename (8.3 Format)

```
"HELLO   TXT" → "HELLO.TXT"
"README  MD " → "README.MD"
"KERNEL  BIN" → "KERNEL.BIN"
```

Converting:

```c
void convert_to_83(const char* filename, char* name83) {
    // Clear with spaces
    memset(name83, ' ', 11);

    const char* dot = strchr(filename, '.');
    int name_len = dot ? (dot - filename) : strlen(filename);

    // Copy name (max 8 chars)
    memcpy(name83, filename, name_len > 8 ? 8 : name_len);

    // Copy extension (max 3 chars)
    if (dot) {
        const char* ext = dot + 1;
        int ext_len = strlen(ext);
        memcpy(name83 + 8, ext, ext_len > 3 ? 3 : ext_len);
    }

    // Convert to uppercase
    for (int i = 0; i < 11; i++) {
        if (name83[i] >= 'a' && name83[i] <= 'z') {
            name83[i] -= 32;
        }
    }
}
```

### Long Filename (LFN)

For names longer than 8.3, FAT32 uses **Long Filename Entries**:

```c
typedef struct {
    uint8_t  order;                 // Order (0x40 = last)
    uint16_t name1[5];              // Characters 1-5 (Unicode)
    uint8_t  attr;                  // 0x0F (ATTR_LONG_NAME)
    uint8_t  type;                  // 0
    uint8_t  checksum;              // Checksum of short name
    uint16_t name2[6];              // Characters 6-11
    uint16_t cluster_low;           // 0
    uint16_t name3[2];              // Characters 12-13
} __attribute__((packed)) FAT32_LFN_Entry;
```

---

## File Operations

### Reading a File

```c
int fat32_read_file(FAT32_Info* info, FAT32_DirEntry* entry,
                    void* buffer, uint32_t size) {
    uint32_t cluster = get_cluster(entry);
    uint32_t bytes_read = 0;
    uint32_t cluster_size = info->bytes_per_sector *
                            info->sectors_per_cluster;

    while (cluster < 0x0FFFFFF7 && bytes_read < size) {
        // Calculate sector
        uint32_t sector = cluster_to_sector(info, cluster);

        // Read cluster
        uint8_t temp[cluster_size];
        for (int i = 0; i < info->sectors_per_cluster; i++) {
            disk_read(sector + i, 1, temp + (i * info->bytes_per_sector));
        }

        // Copy to buffer
        uint32_t to_copy = cluster_size;
        if (bytes_read + to_copy > size) {
            to_copy = size - bytes_read;
        }
        memcpy((uint8_t*)buffer + bytes_read, temp, to_copy);
        bytes_read += to_copy;

        // Get next cluster
        cluster = fat_read_entry(info, cluster);
    }

    return bytes_read;
}
```

### Writing a File

```c
int fat32_write_file(FAT32_Info* info, uint32_t start_cluster,
                     const void* data, uint32_t size) {
    uint32_t cluster = start_cluster;
    uint32_t bytes_written = 0;
    uint32_t cluster_size = info->bytes_per_sector *
                            info->sectors_per_cluster;

    while (bytes_written < size) {
        uint32_t sector = cluster_to_sector(info, cluster);

        // Prepare data to write
        uint8_t buffer[cluster_size];
        uint32_t to_write = cluster_size;
        if (bytes_written + to_write > size) {
            to_write = size - bytes_written;
        }
        memcpy(buffer, (const uint8_t*)data + bytes_written, to_write);

        // Write cluster
        for (int i = 0; i < info->sectors_per_cluster; i++) {
            disk_write(sector + i, 1,
                      buffer + (i * info->bytes_per_sector));
        }

        bytes_written += to_write;

        if (bytes_written < size) {
            // Need another cluster
            uint32_t next = fat_read_entry(info, cluster);
            if (next >= 0x0FFFFFF7) {
                // Allocate new cluster
                next = fat_allocate_cluster(info);
                fat_write_entry(info, cluster, next);
                fat_write_entry(info, next, 0x0FFFFFFF);  // Mark EOF
            }
            cluster = next;
        }
    }

    return bytes_written;
}
```

### Finding a File

```c
FAT32_DirEntry* fat32_find_file(FAT32_Info* info, uint32_t dir_cluster,
                                const char* filename) {
    char name83[11];
    convert_to_83(filename, name83);

    uint32_t cluster = dir_cluster;

    while (cluster < 0x0FFFFFF7) {
        uint32_t sector = cluster_to_sector(info, cluster);

        for (int s = 0; s < info->sectors_per_cluster; s++) {
            uint8_t buffer[512];
            disk_read(sector + s, 1, buffer);

            FAT32_DirEntry* entries = (FAT32_DirEntry*)buffer;

            for (int i = 0; i < 16; i++) {  // 16 entries per sector
                // Check for end of directory
                if (entries[i].name[0] == 0x00) {
                    return NULL;
                }

                // Skip deleted entries
                if (entries[i].name[0] == 0xE5) {
                    continue;
                }

                // Skip long filename entries
                if (entries[i].attr == ATTR_LONG_NAME) {
                    continue;
                }

                // Compare names
                if (memcmp(entries[i].name, name83, 11) == 0) {
                    // Found! Allocate and return copy
                    FAT32_DirEntry* result = kmalloc(sizeof(FAT32_DirEntry));
                    memcpy(result, &entries[i], sizeof(FAT32_DirEntry));
                    return result;
                }
            }
        }

        cluster = fat_read_entry(info, cluster);
    }

    return NULL;  // Not found
}
```

### Creating a File

```c
int fat32_create_file(FAT32_Info* info, uint32_t dir_cluster,
                      const char* filename) {
    // Find free cluster
    uint32_t new_cluster = fat_allocate_cluster(info);
    if (new_cluster == 0) {
        return -1;  // Disk full
    }

    // Mark as EOF
    fat_write_entry(info, new_cluster, 0x0FFFFFFF);

    // Create directory entry
    FAT32_DirEntry entry;
    memset(&entry, 0, sizeof(entry));
    convert_to_83(filename, (char*)entry.name);
    entry.attr = ATTR_ARCHIVE;
    set_cluster(&entry, new_cluster);
    entry.file_size = 0;

    // Add to directory
    return fat32_add_dir_entry(info, dir_cluster, &entry);
}
```

### Deleting a File

```c
int fat32_delete_file(FAT32_Info* info, FAT32_DirEntry* entry) {
    uint32_t cluster = get_cluster(entry);

    // Free cluster chain
    while (cluster < 0x0FFFFFF7) {
        uint32_t next = fat_read_entry(info, cluster);
        fat_write_entry(info, cluster, 0x00000000);  // Mark free
        cluster = next;
    }

    // Mark directory entry as deleted
    entry->name[0] = 0xE5;

    // Write updated entry back to disk
    // (Implementation depends on how you track entry location)

    return 0;
}
```

---

## Implementation

### Initialization

```c
int fat32_init(FAT32_Info* info) {
    // Read MBR
    MBR mbr;
    if (disk_read(0, 1, &mbr) != 0) {
        return -1;
    }

    // Find FAT32 partition
    uint32_t partition_lba;
    if (fat32_find_partition(&mbr, &partition_lba) != 0) {
        return -1;
    }

    // Read boot sector
    FAT32_BootSector boot;
    if (disk_read(partition_lba, 1, &boot) != 0) {
        return -1;
    }

    // Parse boot sector
    info->partition_lba = partition_lba;
    parse_boot_sector(&boot, info);

    return 0;
}
```

### Complete Operations

```c
// List directory
void list_dir(FAT32_Info* info, uint32_t cluster) {
    uint32_t current = cluster ? cluster : info->root_cluster;

    while (current < 0x0FFFFFF7) {
        uint32_t sector = cluster_to_sector(info, current);

        for (int s = 0; s < info->sectors_per_cluster; s++) {
            uint8_t buffer[512];
            disk_read(sector + s, 1, buffer);

            FAT32_DirEntry* entries = (FAT32_DirEntry*)buffer;

            for (int i = 0; i < 16; i++) {
                if (entries[i].name[0] == 0x00) return;  // End
                if (entries[i].name[0] == 0xE5) continue;  // Deleted
                if (entries[i].attr == ATTR_LONG_NAME) continue;  // LFN

                // Print filename
                for (int j = 0; j < 8; j++) {
                    if (entries[i].name[j] != ' ') {
                        print_char(entries[i].name[j]);
                    }
                }
                if (entries[i].name[8] != ' ') {
                    print_char('.');
                    for (int j = 8; j < 11; j++) {
                        if (entries[i].name[j] != ' ') {
                            print_char(entries[i].name[j]);
                        }
                    }
                }

                // Print type
                if (entries[i].attr & ATTR_DIRECTORY) {
                    print_str(" <DIR>");
                } else {
                    print_str("      ");
                    print_dec(entries[i].file_size);
                    print_str(" bytes");
                }
                print_str("\n");
            }
        }

        current = fat_read_entry(info, current);
    }
}
```

---

## Summary

### FAT32 Structure

```
MBR → Partition → Boot Sector → FAT(s) → Data Clusters
```

### Key Concepts

1. **MBR**: Partition table at sector 0
2. **Boot Sector**: Filesystem parameters (BPB)
3. **FAT**: Cluster allocation table (linked list)
4. **Clusters**: Allocation units (multiple sectors)
5. **Directory Entries**: 32-byte file/folder metadata
6. **8.3 Format**: Short filenames (FILENAME.EXT)

### Memory Layout

```
Cluster 0-1: Reserved
Cluster 2:   Root directory
Cluster 3+:  Files and directories
```

### Operations

- `fat32_read_file()` - Read file contents
- `fat32_write_file()` - Write file contents
- `fat32_find_file()` - Locate file in directory
- `fat32_create_file()` - Create new file
- `fat32_delete_file()` - Remove file
- `list_dir()` - List directory contents

---

**Next**: [07-Build-System.md](07-Build-System.md) - Compilation and toolchain

---

_"Filesystems turn raw disk bytes into organized, accessible data. Master FAT32, and you control file storage."_
