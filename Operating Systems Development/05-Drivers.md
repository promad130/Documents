# 05 - Drivers: Interfacing with Hardware

## Table of Contents

1. [Overview](#overview)
2. [Port I/O](#port-io)
3. [Display Driver (VGA Text Mode)](#display-driver-vga-text-mode)
4. [Keyboard Driver](#keyboard-driver)
5. [Disk Driver (ATA PIO)](#disk-driver-ata-pio)
6. [Driver Development Best Practices](#driver-development-best-practices)

---

## Overview

**Device drivers** are kernel code that communicate with hardware devices. They:

1. **Abstract hardware** - Provide simple interface to complex hardware
2. **Handle I/O** - Read/write data to/from devices
3. **Manage interrupts** - Respond to device signals
4. **Buffer data** - Queue requests and responses
5. **Enforce access control** - Prevent invalid operations

### Driver Architecture

```
Application
    ↓
System Call Interface
    ↓
Kernel
    ↓
Device Driver Layer
    ├─ Display Driver  →  VGA Controller
    ├─ Keyboard Driver →  PS/2 Controller
    ├─ Disk Driver     →  ATA Controller
    └─ Network Driver  →  NIC
```

### Communication Methods

1. **Port I/O** - `in`/`out` instructions (legacy devices)
2. **Memory-Mapped I/O** - Device registers mapped to memory addresses
3. **DMA** - Direct Memory Access (device transfers data without CPU)
4. **Interrupts** - Device signals CPU when ready

---

## Port I/O

**Port I/O** uses special x86 instructions to communicate with devices.

### I/O Address Space

Separate from memory address space:

- 65,536 ports (0x0000 - 0xFFFF)
- Most ports are 8-bit or 16-bit
- Some 32-bit ports exist

### Port I/O Instructions

```asm
; 8-bit I/O
in al, dx           ; AL = inb(DX)
out dx, al          ; outb(DX, AL)

; 16-bit I/O
in ax, dx           ; AX = inw(DX)
out dx, ax          ; outw(DX, AX)

; 32-bit I/O
in eax, dx          ; EAX = inl(DX)
out dx, eax         ; outl(DX, EAX)

; Immediate port (0-255 only)
in al, 0x60         ; Read from port 0x60
out 0x64, al        ; Write to port 0x64
```

### C Wrappers for Port I/O

```c
static inline void outb(uint16_t port, uint8_t value) {
    __asm__ volatile("outb %0, %1" : : "a"(value), "Nd"(port));
}

static inline uint8_t inb(uint16_t port) {
    uint8_t ret;
    __asm__ volatile("inb %1, %0" : "=a"(ret) : "Nd"(port));
    return ret;
}

static inline void outw(uint16_t port, uint16_t value) {
    __asm__ volatile("outw %0, %1" : : "a"(value), "Nd"(port));
}

static inline uint16_t inw(uint16_t port) {
    uint16_t ret;
    __asm__ volatile("inw %1, %0" : "=a"(ret) : "Nd"(port));
    return ret;
}
```

#### Inline Assembly Syntax

```c
__asm__ volatile("instruction" : outputs : inputs : clobbers);

"a" - Use RAX/EAX/AX/AL register
"b" - Use RBX/EBX/BX/BL register
"c" - Use RCX/ECX/CX/CL register
"d" - Use RDX/EDX/DX/DL register
"N" - Immediate constant (0-255 for port)
"=a" - Output in RAX
```

### Common I/O Ports

| Port Range  | Device               |
| ----------- | -------------------- |
| 0x20-0x21   | Master PIC           |
| 0xA0-0xA1   | Slave PIC            |
| 0x40-0x43   | PIT (Timer)          |
| 0x60, 0x64  | PS/2 Keyboard/Mouse  |
| 0x3D4-0x3D5 | VGA CRT Controller   |
| 0x3F8-0x3FF | Serial Port 1 (COM1) |
| 0x1F0-0x1F7 | Primary ATA          |
| 0x170-0x177 | Secondary ATA        |

---

## Display Driver (VGA Text Mode)

**VGA Text Mode** is the simplest way to output text to screen.

### VGA Memory Layout

The **VGA buffer** is memory-mapped at physical address `0xB8000`:

- 80 columns × 25 rows = 2000 characters
- Each character = 2 bytes (character + attribute)

```
Memory Address: 0xB8000
┌────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┐
│ H  │ 0F │ e  │ 0F │ l  │ 0F │ l  │ 0F │ o  │ 0F │ ...│
└────┴────┴────┴────┴────┴────┴────┴────┴────┴────┴────┘
 Char Attr Char Attr Char Attr Char Attr Char Attr

Attribute byte:
┌─────────┬──────────┐
│ BG (4b) │ FG (4b)  │
└─────────┴──────────┘
Background   Foreground
```

### VGA Colors

```c
enum vga_color {
    VGA_BLACK         = 0,
    VGA_BLUE          = 1,
    VGA_GREEN         = 2,
    VGA_CYAN          = 3,
    VGA_RED           = 4,
    VGA_MAGENTA       = 5,
    VGA_BROWN         = 6,
    VGA_LIGHT_GREY    = 7,
    VGA_DARK_GREY     = 8,
    VGA_LIGHT_BLUE    = 9,
    VGA_LIGHT_GREEN   = 10,
    VGA_LIGHT_CYAN    = 11,
    VGA_LIGHT_RED     = 12,
    VGA_LIGHT_MAGENTA = 13,
    VGA_YELLOW        = 14,
    VGA_WHITE         = 15,
};
```

### Character Attribute

```c
uint8_t vga_entry_color(uint8_t fg, uint8_t bg) {
    return fg | (bg << 4);
}

uint16_t vga_entry(char c, uint8_t color) {
    return (uint16_t)c | ((uint16_t)color << 8);
}
```

### VGA Buffer Access

```c
#define VGA_WIDTH  80
#define VGA_HEIGHT 25
#define VGA_BUFFER ((uint16_t*)0xB8000)

void put_char_at(char c, size_t x, size_t y, uint8_t color) {
    if (x >= VGA_WIDTH || y >= VGA_HEIGHT) {
        return;
    }

    size_t index = y * VGA_WIDTH + x;
    VGA_BUFFER[index] = vga_entry(c, color);
}
```

### Cursor Control

The VGA cursor is controlled via CRT Controller ports:

```c
void update_cursor(size_t x, size_t y) {
    uint16_t pos = y * VGA_WIDTH + x;

    // Cursor Low byte
    outb(0x3D4, 0x0F);
    outb(0x3D5, (uint8_t)(pos & 0xFF));

    // Cursor High byte
    outb(0x3D4, 0x0E);
    outb(0x3D5, (uint8_t)((pos >> 8) & 0xFF));
}

uint16_t get_cursor_position() {
    uint16_t pos = 0;

    outb(0x3D4, 0x0F);
    pos |= inb(0x3D5);

    outb(0x3D4, 0x0E);
    pos |= ((uint16_t)inb(0x3D5)) << 8;

    return pos;
}
```

### Scrolling

When reaching bottom of screen, shift all lines up:

```c
void scroll_screen() {
    // Copy lines 1-24 to lines 0-23
    for (int y = 1; y < VGA_HEIGHT; y++) {
        for (int x = 0; x < VGA_WIDTH; x++) {
            VGA_BUFFER[(y - 1) * VGA_WIDTH + x] =
                VGA_BUFFER[y * VGA_WIDTH + x];
        }
    }

    // Clear last line
    for (int x = 0; x < VGA_WIDTH; x++) {
        VGA_BUFFER[(VGA_HEIGHT - 1) * VGA_WIDTH + x] =
            vga_entry(' ', 0x07);
    }
}
```

### Complete Display Driver

**File: `drivers/display.c`**

```c
#include "drivers/display.h"

static inline void outb(uint16_t port, uint8_t value) {
    __asm__ volatile("outb %0, %1" : : "a"(value), "Nd"(port));
}

static inline uint8_t inb(uint16_t port) {
    uint8_t ret;
    __asm__ volatile("inb %1, %0" : "=a"(ret) : "Nd"(port));
    return ret;
}

void put_char_at(char c, size_t x, size_t y, uint8_t color) {
    if (x >= VGA_WIDTH || y >= VGA_HEIGHT) {
        return;
    }

    VGA_BUFFER[y * VGA_WIDTH + x] = (uint16_t)(c | (color << 8));

    // Update cursor after character
    size_t cursor_position = y * VGA_WIDTH + x + 1;
    outb(0x3D4, 0x0F);
    outb(0x3D5, (uint8_t)(cursor_position & 0xFF));
    outb(0x3D4, 0x0E);
    outb(0x3D5, (uint8_t)((cursor_position >> 8) & 0xFF));
}

void remove_char_at_cursor() {
    outb(0x3D4, 0x0F);
    size_t cursor_low = inb(0x3D5);
    outb(0x3D4, 0x0E);
    size_t cursor_high = inb(0x3D5);
    size_t cursor_position = (cursor_high << 8) | cursor_low;

    if (cursor_position == 0) {
        return;
    }

    cursor_position--;

    size_t x = cursor_position % VGA_WIDTH;
    size_t y = cursor_position / VGA_WIDTH;

    VGA_BUFFER[y * VGA_WIDTH + x] = 0;

    outb(0x3D4, 0x0F);
    outb(0x3D5, (uint8_t)(cursor_position & 0xFF));
    outb(0x3D4, 0x0E);
    outb(0x3D5, (uint8_t)((cursor_position >> 8) & 0xFF));
}
```

---

## Keyboard Driver

The keyboard controller uses the **PS/2 interface**.

### PS/2 Keyboard Ports

- **0x60** - Data port (read scan codes)
- **0x64** - Status/Command port

### Scan Codes

When a key is pressed/released, keyboard sends a **scan code**:

- **Make code**: Key pressed (e.g., 0x1E for 'A')
- **Break code**: Key released (make code | 0x80, e.g., 0x9E for 'A' released)

### Scan Code Table (Partial)

| Scan Code | Key   | Scan Code | Key       |
| --------- | ----- | --------- | --------- |
| 0x01      | ESC   | 0x10      | Q         |
| 0x02      | 1     | 0x11      | W         |
| 0x03      | 2     | 0x12      | E         |
| 0x1E      | A     | 0x1F      | S         |
| 0x2C      | Z     | 0x2D      | X         |
| 0x39      | Space | 0x0E      | Backspace |
| 0x1C      | Enter | 0x0F      | Tab       |

### Reading Scan Codes

```c
uint8_t read_scancode() {
    // Wait for data
    while (!(inb(0x64) & 0x01)) {
        __asm__ volatile("pause");
    }

    return inb(0x60);
}
```

### Scan Code to ASCII Translation

```c
static const char scancode_to_ascii[] = {
    0, 0x1B, '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', '\b',
    '\t', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\n',
    0,    'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'', '`', 0,
    '\\', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/', 0, '*', 0,
    ' ', // Space (0x39)
    // ... more entries
};

char scancode_to_char(uint8_t scancode) {
    if (scancode < sizeof(scancode_to_ascii)) {
        return scancode_to_ascii[scancode];
    }
    return 0;
}
```

### Handling Key Releases

```c
char read_from_usb_keyboard() {
    static uint8_t last_scancode = 0;
    static uint8_t key_released = 1;
    uint8_t scancode;

    __asm__ volatile("inb $0x60, %%al\n" : "=a"(scancode) : :);

    // Check if release code (bit 7 set)
    if (scancode & 0x80) {
        key_released = 1;
        last_scancode = 0;
        return 0;
    }

    // Only process if key state changed
    if (scancode != last_scancode || key_released) {
        last_scancode = scancode;
        key_released = 0;
        return scancode_to_char(scancode);
    }

    return 0;
}
```

### Blocking Keyboard Input

```c
char bios_get_char() {
    char c = 0;
    while ((c = read_from_usb_keyboard()) == 0) {
        __asm__ volatile("pause");  // Yield CPU
    }
    return c;
}
```

💡 **Tip**: The `pause` instruction improves performance on hyper-threaded CPUs by signaling a spin-wait loop.

### Keyboard IRQ Handler

For interrupt-driven input:

```c
#define KEYBOARD_IRQ 1

void keyboard_irq_handler() {
    uint8_t scancode = inb(0x60);

    // Process scancode
    if (!(scancode & 0x80)) {  // Key press
        char c = scancode_to_char(scancode);
        keyboard_buffer_push(c);
    }

    // Send EOI to PIC
    outb(0x20, 0x20);
}
```

---

## Disk Driver (ATA PIO)

**ATA (Advanced Technology Attachment)** is the interface for hard drives.

### PIO Mode

**PIO (Programmed I/O)** mode:

- CPU reads/writes each byte manually
- Slow but simple
- Contrast with DMA (Direct Memory Access)

### ATA Registers (Primary Bus)

| Port  | Register     | Read                   | Write                  |
| ----- | ------------ | ---------------------- | ---------------------- |
| 0x1F0 | Data         | Read data              | Write data             |
| 0x1F1 | Error        | Error info             | Features               |
| 0x1F2 | Sector Count | Sector count           | Sector count           |
| 0x1F3 | LBA Low      | LBA bits 0-7           | LBA bits 0-7           |
| 0x1F4 | LBA Mid      | LBA bits 8-15          | LBA bits 8-15          |
| 0x1F5 | LBA High     | LBA bits 16-23         | LBA bits 16-23         |
| 0x1F6 | Drive/Head   | Drive + LBA bits 24-27 | Drive + LBA bits 24-27 |
| 0x1F7 | Status       | Status byte            | Command                |

### Status Register Bits

```
Bit 7 (BSY): Busy - drive is busy
Bit 6 (DRDY): Drive ready
Bit 5 (DF): Drive fault
Bit 4 (SRV): Overlapped mode service request
Bit 3 (DRQ): Data request - ready to transfer
Bit 2 (CORR): Corrected data
Bit 1 (IDX): Index (obsolete)
Bit 0 (ERR): Error occurred
```

### Waiting for Drive Ready

```c
#define ATA_PRIMARY_IO      0x1F0
#define ATA_REG_STATUS      7
#define ATA_REG_DATA        0

int ata_wait_bsy() {
    for (int i = 0; i < 1000000; i++) {
        uint8_t status = inb(ATA_PRIMARY_IO + ATA_REG_STATUS);
        if (!(status & 0x80)) {  // BSY cleared
            return 0;
        }
    }
    return -1;  // Timeout
}

int ata_wait_drq() {
    for (int i = 0; i < 1000000; i++) {
        uint8_t status = inb(ATA_PRIMARY_IO + ATA_REG_STATUS);

        if (status & 0x01) {  // ERR bit set
            return -2;  // Error
        }

        if (status & 0x08) {  // DRQ set
            return 0;
        }
    }
    return -1;  // Timeout
}
```

### Reading Sectors

```c
#define ATA_CMD_READ_PIO    0x20

int disk_read(uint32_t lba, uint32_t sectors, void* buffer) {
    if (sectors == 0) return -1;

    uint16_t* buf = (uint16_t*)buffer;

    for (uint32_t i = 0; i < sectors; i++) {
        uint32_t current_lba = lba + i;

        // Wait for drive ready
        if (ata_wait_bsy() != 0) {
            return -1;
        }

        // Select drive (LBA mode, drive 0)
        outb(ATA_PRIMARY_IO + 6, 0xE0 | ((current_lba >> 24) & 0x0F));

        // Small delay
        for (volatile int d = 0; d < 400; d++);

        // Set sector count
        outb(ATA_PRIMARY_IO + 2, 1);

        // Set LBA
        outb(ATA_PRIMARY_IO + 3, (uint8_t)current_lba);
        outb(ATA_PRIMARY_IO + 4, (uint8_t)(current_lba >> 8));
        outb(ATA_PRIMARY_IO + 5, (uint8_t)(current_lba >> 16));

        // Send read command
        outb(ATA_PRIMARY_IO + 7, ATA_CMD_READ_PIO);

        // Wait for data ready
        if (ata_wait_drq() != 0) {
            return -1;
        }

        // Read 256 words (512 bytes)
        for (int j = 0; j < 256; j++) {
            buf[i * 256 + j] = inw(ATA_PRIMARY_IO + ATA_REG_DATA);
        }
    }

    return 0;
}
```

### Writing Sectors

```c
#define ATA_CMD_WRITE_PIO   0x30
#define ATA_CMD_CACHE_FLUSH 0xE7

int disk_write(uint32_t lba, uint32_t sectors, const void* buffer) {
    if (sectors == 0) return -1;

    const uint16_t* buf = (const uint16_t*)buffer;

    for (uint32_t i = 0; i < sectors; i++) {
        uint32_t current_lba = lba + i;

        if (ata_wait_bsy() != 0) return -1;

        outb(ATA_PRIMARY_IO + 6, 0xE0 | ((current_lba >> 24) & 0x0F));
        for (volatile int d = 0; d < 400; d++);

        outb(ATA_PRIMARY_IO + 2, 1);
        outb(ATA_PRIMARY_IO + 3, (uint8_t)current_lba);
        outb(ATA_PRIMARY_IO + 4, (uint8_t)(current_lba >> 8));
        outb(ATA_PRIMARY_IO + 5, (uint8_t)(current_lba >> 16));

        // Send write command
        outb(ATA_PRIMARY_IO + 7, ATA_CMD_WRITE_PIO);

        if (ata_wait_drq() != 0) return -1;

        // Write 256 words
        for (int j = 0; j < 256; j++) {
            outw(ATA_PRIMARY_IO + ATA_REG_DATA, buf[i * 256 + j]);
        }

        if (ata_wait_bsy() != 0) return -1;
    }

    // Flush cache
    outb(ATA_PRIMARY_IO + 7, ATA_CMD_CACHE_FLUSH);
    if (ata_wait_bsy() != 0) return -1;

    return 0;
}
```

### LBA (Logical Block Addressing)

**LBA** treats disk as linear array of sectors:

```
Sector 0, Sector 1, Sector 2, ..., Sector N

LBA = (Cylinder × HPC + Head) × SPT + (Sector - 1)

Where:
  HPC = Heads Per Cylinder
  SPT = Sectors Per Track
```

Modern drives use 28-bit or 48-bit LBA.

---

## Driver Development Best Practices

### 1. Register Documentation

Always document register layouts:

```c
/* ATA Primary Bus Registers */
#define ATA_PRIMARY_IO          0x1F0
#define ATA_REG_DATA            0     // R/W: Data
#define ATA_REG_ERROR           1     // R: Error info
#define ATA_REG_FEATURES        1     // W: Features
#define ATA_REG_SECCOUNT0       2     // R/W: Sector count
#define ATA_REG_LBA0            3     // R/W: LBA bits 0-7
#define ATA_REG_LBA1            4     // R/W: LBA bits 8-15
#define ATA_REG_LBA2            5     // R/W: LBA bits 16-23
#define ATA_REG_HDDEVSEL        6     // R/W: Drive/Head
#define ATA_REG_STATUS          7     // R: Status
#define ATA_REG_COMMAND         7     // W: Command
```

### 2. Timing and Delays

Hardware needs time to respond:

```c
// ATA requires 400ns delay after drive select
static inline void ata_delay_400ns() {
    for (volatile int i = 0; i < 4; i++) {
        inb(ATA_PRIMARY_IO + ATA_REG_STATUS);  // Each read ~100ns
    }
}

// Or use PIT timer for precise delays
void delay_microseconds(uint32_t us);
```

### 3. Error Handling

```c
typedef enum {
    DISK_OK = 0,
    DISK_ERR_TIMEOUT = -1,
    DISK_ERR_HARDWARE = -2,
    DISK_ERR_INVALID = -3,
} disk_error_t;

disk_error_t disk_read_with_retry(uint32_t lba, void* buffer) {
    for (int retries = 0; retries < 3; retries++) {
        disk_error_t err = disk_read(lba, 1, buffer);
        if (err == DISK_OK) {
            return DISK_OK;
        }
        delay_milliseconds(10);  // Brief delay before retry
    }
    return DISK_ERR_HARDWARE;
}
```

### 4. Buffering

```c
#define DISK_CACHE_SIZE 64

typedef struct {
    uint32_t lba;
    uint8_t data[512];
    int valid;
} cache_entry_t;

cache_entry_t disk_cache[DISK_CACHE_SIZE];

int disk_read_cached(uint32_t lba, void* buffer) {
    // Check cache first
    for (int i = 0; i < DISK_CACHE_SIZE; i++) {
        if (disk_cache[i].valid && disk_cache[i].lba == lba) {
            memcpy(buffer, disk_cache[i].data, 512);
            return 0;
        }
    }

    // Cache miss - read from disk
    return disk_read(lba, 1, buffer);
}
```

### 5. Interrupt-Driven I/O

```c
volatile int disk_operation_complete = 0;

void disk_irq_handler() {
    disk_operation_complete = 1;
    // Send EOI
    outb(0x20, 0x20);
}

int disk_read_async(uint32_t lba, void* buffer) {
    disk_operation_complete = 0;

    // Start read operation
    // ... (setup registers) ...

    // Wait for interrupt
    while (!disk_operation_complete) {
        __asm__ volatile("hlt");  // Sleep until interrupt
    }

    // Read data
    uint16_t* buf = (uint16_t*)buffer;
    for (int i = 0; i < 256; i++) {
        buf[i] = inw(ATA_PRIMARY_IO);
    }

    return 0;
}
```

---

## Summary

### Driver Components

1. **Hardware Interface** - Port I/O or MMIO
2. **Initialization** - Configure device
3. **I/O Operations** - Read/write data
4. **Interrupt Handling** - Respond to device events
5. **Error Handling** - Timeouts, retries, recovery

### Key Concepts

- **Port I/O**: `inb`, `outb`, `inw`, `outw` for device communication
- **Memory-Mapped I/O**: VGA buffer at 0xB8000
- **Polling**: Busy-wait for device ready
- **Interrupts**: Asynchronous device notifications
- **Buffering**: Cache data to reduce hardware access

### Common Ports

| Port        | Device             |
| ----------- | ------------------ |
| 0x60, 0x64  | PS/2 Keyboard      |
| 0x1F0-0x1F7 | Primary ATA        |
| 0x3D4-0x3D5 | VGA CRT Controller |
| 0x3F8-0x3FF | Serial COM1        |

---

**Next**: [06-Filesystem-FAT32.md](06-Filesystem-FAT32.md) - File storage and management

---

_"Drivers are the bridge between software and hardware. Write them carefully, for they control the real world."_
