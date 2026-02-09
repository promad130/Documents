# 08 - Kernel Architecture: Overall System Design

## Table of Contents

1. [Overview](#overview)
2. [Kernel Design Patterns](#kernel-design-patterns)
3. [Our Kernel Structure](#our-kernel-structure)
4. [Initialization Sequence](#initialization-sequence)
5. [Terminal and Shell](#terminal-and-shell)
6. [Error Handling and Panics](#error-handling-and-panics)

---

## Overview

**Kernel architecture** defines how the OS is structured and how components interact.

### What is a Kernel?

The **kernel** is the core of the OS that:

1. Manages hardware resources
2. Provides abstractions (processes, files, memory)
3. Enforces security and isolation
4. Schedules tasks
5. Handles system calls

### Kernel Modes

```
User Space (Ring 3)
    ↓ System Call
─────────────────────────  Privilege Boundary
Kernel Space (Ring 0)
    ├─ Process Management
    ├─ Memory Management
    ├─ Device Drivers
    ├─ Filesystem
    └─ Network Stack
```

---

## Kernel Design Patterns

### 1. Monolithic Kernel

**All services run in kernel space** (our approach):

```
┌──────────────────────────────────────┐
│         Kernel Space (Ring 0)        │
│  ┌────────┬──────────┬────────────┐  │
│  │Drivers │Filesystem│Memory Mgmt │  │
│  └────────┴──────────┴────────────┘  │
└──────────────────────────────────────┘
┌──────────────────────────────────────┐
│         User Space (Ring 3)          │
│         Applications                 │
└──────────────────────────────────────┘
```

**Advantages**:

- ✅ Fast (no context switches between services)
- ✅ Simple communication (direct function calls)
- ✅ Easier to develop initially

**Disadvantages**:

- ❌ Less stable (driver crash = kernel crash)
- ❌ Less secure (all code has full privileges)
- ❌ Harder to maintain long-term

**Examples**: Linux, BSD, traditional UNIX

### 2. Microkernel

**Minimal kernel, services in user space**:

```
┌──────────────────────────────────────┐
│         User Space (Ring 3)          │
│  ┌────────┬──────────┬────────────┐  │
│  │Drivers │Filesystem│Applications│  │
│  └────────┴──────────┴────────────┘  │
└──────────────────────────────────────┘
┌──────────────────────────────────────┐
│         Kernel Space (Ring 0)        │
│  IPC, Scheduling, Memory Management  │
└──────────────────────────────────────┘
```

**Advantages**:

- ✅ More stable (driver crash doesn't crash kernel)
- ✅ More secure (services isolated)
- ✅ Modular and maintainable

**Disadvantages**:

- ❌ Slower (IPC overhead)
- ❌ More complex

**Examples**: Minix, QNX, L4

### 3. Hybrid Kernel

**Mix of monolithic and microkernel**:

```
┌──────────────────────────────────────┐
│         User Space (Ring 3)          │
│  ┌────────────┬──────────────────┐   │
│  │Some Drivers│   Applications   │   │
│  └────────────┴──────────────────┘   │
└──────────────────────────────────────┘
┌──────────────────────────────────────┐
│         Kernel Space (Ring 0)        │
│  Core Services + Critical Drivers    │
└──────────────────────────────────────┘
```

**Examples**: Windows NT, macOS (XNU)

---

## Our Kernel Structure

### Component Diagram

```
Kernel
├── Boot (boot/)
│   ├── header.asm         - Multiboot2 header
│   ├── main.asm           - 32-bit entry, paging setup
│   └── main64.asm         - 64-bit entry
│
├── Architecture (arch/)
│   └── idt/
│       ├── idt.h          - IDT definitions
│       ├── init.c         - IDT setup
│       ├── idt.c          - Exception handlers
│       └── isrs.asm       - ISR wrappers
│
├── Kernel (kernel/)
│   ├── kmain.c            - Main kernel entry
│   ├── kpanic.c           - Kernel panic handler
│   └── terminal.c         - Command parsing
│
├── Drivers (drivers/)
│   ├── display.c          - VGA text mode
│   ├── keyboard.c         - PS/2 keyboard
│   └── memory.c           - PMM + heap allocator
│
├── Filesystem (fs/)
│   └── fat32/
│       └── disk.c         - FAT32 + ATA driver
│
└── Library (lib/)
    ├── printf.c           - Formatted output
    ├── string.c           - String operations
    └── util.c             - Utility functions
```

### Module Responsibilities

| Module           | Responsibility                      |
| ---------------- | ----------------------------------- |
| **Boot**         | CPU initialization, enter long mode |
| **Architecture** | CPU-specific code (IDT, exceptions) |
| **Kernel**       | Core logic, initialization, panic   |
| **Drivers**      | Hardware abstraction                |
| **Filesystem**   | File storage and retrieval          |
| **Library**      | Common utility functions            |

---

## Initialization Sequence

### Boot Flow

```
Power On
    ↓
BIOS/UEFI
    ↓
GRUB (Multiboot2)
    ↓
boot/header.asm         - Multiboot header recognized
    ↓
boot/main.asm           - 32-bit entry point
    ├─ Check Multiboot
    ├─ Check CPUID
    ├─ Check Long Mode
    ├─ Setup Page Tables
    ├─ Enable Paging
    └─ Load GDT
    ↓
boot/main64.asm         - 64-bit entry point
    └─ Clear segment registers
    ↓
kernel/kmain.c          - C kernel entry
    ├─ IDT Setup
    ├─ Memory Management Init
    ├─ FAT32 Init
    ├─ Display Welcome
    └─ Main Loop
```

### kmain.c Implementation

**File: `kernel/kmain.c`**

```c
#include "kernel/kernel.h"

// Global variable definition
char* currentDirectory = "/";

void init_memory_management() {
    uint64_t total_memory = 1 * 1024 * 1024 * 1024;  // 1 GB

    static uint64_t pmm_bitmap_storage[16384];
    pmm_init(total_memory, pmm_bitmap_storage);

    void* heap_start = (void*)0x400000;  // 4MB mark
    kheap_init(heap_start);
}

int init_fat32() {
    if (!fat32_initialized) {
        if (fat32_init(&global_fat32) != 0) {
            print_str("FAT32 initialization failed.\n");
            return -1;
        }
    }
    return 0;
}

void kernel_main() {
    // 1. Setup interrupt handling
    idt_init();
    idt_load();

    // 2. Setup memory management
    init_memory_management();

    // 3. Initialize filesystem
    int fat32_status = init_fat32();
    if (fat32_status != 0) {
        __asm__ volatile("hlt");
    }

    // 4. Display welcome message
    print_clear();
    print_set_color(PRINT_COLOR_WHITE, PRINT_COLOR_BLACK);
    print_str("Welcome to our 64-bit kernel!\n");

    // 5. Run tests (if any)
    test_debug_exception();

    // 6. Display prompt
    print_str(strconcat(currentDirectory, ">: "));

    // 7. Main loop - read and process commands
    while (1) {
        char c = bios_get_char();
        char buffer[2] = {c, '\0'};
        update_input(c);
        c != '\n' ? print_str(buffer) : 0;
    }

    // Should never reach here
    kernel_panic("Kernel main loop exited unexpectedly.");
    for (;;) __asm__ volatile("hlt");
}
```

### Initialization Order is Critical!

⚠️ **Wrong Order Example**:

```c
void kernel_main() {
    init_fat32();           // ❌ Uses kmalloc before heap init!
    init_memory_management();
    idt_init();             // ❌ IDT after interrupts might fire!
}
```

✅ **Correct Order**:

1. **IDT** - Must be before anything that can trigger interrupts
2. **Memory** - Required by filesystem and other subsystems
3. **Filesystem** - Can now allocate memory safely
4. **User Interface** - After everything is ready

---

## Terminal and Shell

### Command Parsing

**File: `kernel/terminal.c`**

```c
#include "kernel/terminal.h"

char* inp;              // Input buffer
int len = 0;            // Current input length

void update_input(char c) {
    if (c == '\n') {
        print_newline();
        parse_command(inp);

        // Reset for next command
        len = 0;
        *(inp + len) = '\0';
        print_str(strconcat(currentDirectory, ">: "));
        return;
    }
    else if (c == '\b') {
        // Backspace
        if (len > 0) {
            len--;
            *(inp + len) = '\0';
        }
        return;
    }

    // Add character to buffer
    if (len < 255) {
        *(inp + len) = c;
        len++;
        *(inp + len) = '\0';
    }
}
```

### Command Dispatcher

```c
void parse_command(char* command) {
    if (strcmp(command, "clear") == 0) {
        print_clear();
    }
    else if (strcmp(command, "help") == 0) {
        print_str("Available commands:\n");
        print_str("clear - Clear the screen\n");
        print_str("help - Show this help message\n");
        print_str("reboot - Reboot the system\n");
        print_str("shutdown - Shutdown the system\n");
        print_str("meminfo - Show memory information\n");
        print_str("ls - List files in directory\n");
        print_str("cd <dir> - Change directory\n");
        print_str("cat <file> - Display file contents\n");
    }
    else if (strcmp(command, "reboot") == 0) {
        print_str("Rebooting...\n");
        sleep(1000);
        __asm__ volatile("int $0x19");  // BIOS reboot
    }
    else if (strcmp(command, "shutdown") == 0) {
        print_str("Shutting down...\n");
        sleep(2000);

        // QEMU/Bochs shutdown
        outw(0x2000, 0x604);

        // Fallback
        print_str("System halted.\n");
        __asm__ volatile("hlt");
    }
    else if (strncmp(command, "echo", 4) == 0) {
        char* message = command + 5;
        print_str(message);
        print_str("\n");
    }
    else if (strcmp(command, "meminfo") == 0) {
        meminfo();
    }
    else if (strcmp(command, "ls") == 0) {
        list_dir(&global_fat32, current_dir_cluster);
    }
    else if (strncmp(command, "cd ", 3) == 0) {
        change_directory(command + 3);
    }
    else if (strncmp(command, "cat ", 4) == 0) {
        char* filename = command + 4;
        fat32_cat(&global_fat32, filename);
    }
    else if (strncmp(command, "touch ", 6) == 0) {
        char* filename = command + 6;
        if (fat32_create_file(&global_fat32, current_dir_cluster,
                              filename) == 0) {
            print_str("File created\n");
        } else {
            print_str("Failed to create file\n");
        }
    }
    else if (command[0] != '\0') {
        print_str("Unknown command: ");
        print_str(command);
        print_str("\n");
    }
}
```

### Implementing Additional Commands

```c
// Directory change
void change_directory(const char* path) {
    if (strcmp(path, "/") == 0) {
        current_dir_cluster = global_fat32.root_cluster;
        currentDirectory = "/";
        return;
    }

    FAT32_DirEntry* entry = fat32_find_file(&global_fat32,
                                            current_dir_cluster, path);
    if (entry && (entry->attr & ATTR_DIRECTORY)) {
        current_dir_cluster = get_cluster(entry);
        currentDirectory = path;
    } else {
        print_str("Directory not found\n");
    }
}

// Display file contents
void fat32_cat(FAT32_Info* info, const char* filename) {
    FAT32_DirEntry* entry = fat32_find_file(info, current_dir_cluster,
                                            filename);
    if (!entry) {
        print_str("File not found\n");
        return;
    }

    if (entry->attr & ATTR_DIRECTORY) {
        print_str("Is a directory\n");
        return;
    }

    // Allocate buffer
    uint8_t* buffer = kmalloc(entry->file_size + 1);
    fat32_read_file(info, entry, buffer, entry->file_size);
    buffer[entry->file_size] = '\0';

    print_str((char*)buffer);
    print_str("\n");
}
```

---

## Error Handling and Panics

### Kernel Panic

When an unrecoverable error occurs, the kernel **panics**:

**File: `kernel/kpanic.c`**

```c
#include "kernel/kpanic.h"

void kernel_panic(const char* message) {
    // Disable interrupts
    __asm__ volatile("cli");

    // Change to panic colors (white on red)
    print_set_color(PRINT_COLOR_WHITE, PRINT_COLOR_RED);
    print_clear();

    // Display panic message
    print_str("\n\n");
    print_str("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
    print_str("!!        KERNEL PANIC                !!\n");
    print_str("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
    print_str("\n");
    print_str("Message: ");
    print_str(message);
    print_str("\n\n");
    print_str("The system has been halted.\n");
    print_str("Please restart your computer.\n");

    // Halt forever
    for (;;) {
        __asm__ volatile("hlt");
    }
}
```

### Assertions

```c
#define ASSERT(condition, message) \
    do { \
        if (!(condition)) { \
            kernel_panic(message); \
        } \
    } while(0)

// Usage:
ASSERT(pmm_bitmap != NULL, "PMM bitmap not initialized");
ASSERT(cluster < total_clusters, "Cluster number out of range");
```

### Error Codes

```c
typedef enum {
    ERR_SUCCESS         = 0,
    ERR_INVALID_ARG     = -1,
    ERR_OUT_OF_MEMORY   = -2,
    ERR_NOT_FOUND       = -3,
    ERR_PERMISSION      = -4,
    ERR_IO_ERROR        = -5,
    ERR_TIMEOUT         = -6,
} error_code_t;

// Function returns error code
error_code_t do_operation() {
    if (!valid_input) {
        return ERR_INVALID_ARG;
    }

    void* mem = kmalloc(1024);
    if (!mem) {
        return ERR_OUT_OF_MEMORY;
    }

    return ERR_SUCCESS;
}

// Caller checks error
error_code_t err = do_operation();
if (err != ERR_SUCCESS) {
    print_str("Operation failed with error: ");
    print_dec(err);
    print_str("\n");
}
```

---

## Summary

### Kernel Architecture Principles

1. **Modularity** - Separate concerns into components
2. **Initialization Order** - Dependencies must be initialized first
3. **Error Handling** - Graceful failure, clear panic messages
4. **Abstraction** - Hide hardware details behind clean interfaces
5. **Testability** - Design for testing (unit tests, debug commands)

### Our Kernel Type

**Monolithic Simple Kernel**:

- All services in kernel space
- Direct function calls (fast)
- Good for learning and simple systems
- Easy to debug initially

### Component Interaction

```
User Input → Keyboard Driver → Terminal → Command Parser
     ↓
File Command → FAT32 → Disk Driver → ATA Hardware
     ↓
Memory Allocation → Heap Allocator → PMM → Physical RAM
     ↓
Display Output → Display Driver → VGA Hardware
```

### Initialization Checklist

- ✅ IDT setup and loaded
- ✅ Physical memory manager initialized
- ✅ Kernel heap initialized
- ✅ Device drivers initialized
- ✅ Filesystem mounted
- ✅ Welcome message displayed
- ✅ Shell ready for input

---

**Next**: [09-Advanced-Topics.md](09-Advanced-Topics.md) - Advanced OS concepts

---

_"Good architecture is the difference between a toy OS and a real system. Plan carefully, code deliberately."_
