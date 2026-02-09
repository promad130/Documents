# OS Development - Complete Overview

## Introduction

This document serves as comprehensive study notes for understanding operating system development from the ground up. The notes are based on a real x86_64 OS implementation and cover fundamental to advanced concepts.

## Project Structure

```
os/
├── boot/           # Boot sequence and initialization
├── arch/           # Architecture-specific code (IDT, interrupts)
├── kernel/         # Core kernel functionality
├── drivers/        # Hardware drivers (display, keyboard, memory)
├── fs/             # Filesystem implementations (FAT32)
├── lib/            # Standard library functions
├── includes/       # Header files
├── targets/        # Target-specific configurations (linker scripts, bootloader config)
└── tests/          # Testing infrastructure
```

## What is an Operating System?

An **Operating System (OS)** is system software that:

1. **Manages hardware resources** (CPU, memory, devices)
2. **Provides abstractions** for applications (processes, files, I/O)
3. **Enforces security and isolation** between programs
4. **Schedules tasks** and manages concurrent execution

### OS Layers (Bottom to Top)

1. **Hardware** - Physical CPU, RAM, disk, peripherals
2. **Bootloader** - GRUB2 loads the kernel into memory
3. **Kernel** - Core OS (our code!)
4. **System Libraries** - Standard functions (printf, string ops)
5. **User Applications** - Programs running on the OS

## Key Concepts Covered in These Notes

### 1. Boot Process (Section 01)

- BIOS/UEFI initialization
- Multiboot2 specification
- Real mode → Protected mode → Long mode
- GRUB bootloader configuration

### 2. x86_64 Architecture (Section 02)

- CPU modes and privilege levels
- Registers (general purpose, control, segment)
- Memory segmentation vs paging
- Assembly language basics (NASM syntax)

### 3. Memory Management (Section 03)

- Paging and page tables (4-level)
- Virtual vs physical memory
- Physical Memory Manager (PMM)
- Kernel heap allocator
- Memory layout and linker scripts

### 4. Interrupts and Exceptions (Section 04)

- Interrupt Descriptor Table (IDT)
- CPU exceptions (divide by zero, page faults)
- Hardware interrupts (keyboard, timer)
- Interrupt Service Routines (ISRs)

### 5. Drivers (Section 05)

- VGA text mode display driver
- PS/2 keyboard driver (scancode handling)
- ATA disk driver (PIO mode)
- Port I/O (`inb`, `outb` instructions)

### 6. Filesystem Support (Section 06)

- FAT32 filesystem structure
- MBR partition table
- Reading/writing files
- Directory operations

### 7. Build System (Section 07)

- Cross-compiler setup (x86_64-elf-gcc)
- Makefile organization
- Linking and binary generation
- ISO creation with GRUB

## Learning Path

### Beginner Level

0. Understand C programming and basic assembly
1. Read Section 01 (Boot Process)
2. Read Section 02 (x86_64 Basics)
3. Read Section 03 (Memory Management - basics only)

### Intermediate Level

4. Read Section 04 (Interrupts)
5. Read Section 05 (Drivers)
6. Read Section 03 (Memory Management - advanced)

### Advanced Level

7. Read Section 06 (Filesystem)
8. Read Section 08 (Kernel Architecture)
9. Read Section 09 (Advanced Topics)

## Prerequisites

### Knowledge Prerequisites

- **C Programming**: Pointers, structs, bitwise operations, inline assembly
- **Assembly Language**: Basic x86/x86_64 assembly (NASM or Intel syntax)
- **Computer Architecture**: CPU, memory hierarchy, I/O
- **Data Structures**: Linked lists, trees (for filesystem)
- **Binary/Hexadecimal**: Number systems and bitwise logic

### Tools Required

- **x86_64-elf-gcc**: Cross-compiler for bare-metal x86_64
- **NASM**: Netwide Assembler for x86 assembly
- **GNU Make**: Build automation
- **GRUB2**: Bootloader
- **QEMU**: x86_64 emulator for testing
- **genisoimage/grub-mkrescue**: ISO creation

## Documentation Sections

1. **[01-Boot-Process.md](01-Boot-Process.md)** - How the OS boots up
2. **[02-x86-64-Architecture.md](02-x86-64-Architecture.md)** - CPU architecture deep dive
3. **[03-Memory-Management.md](03-Memory-Management.md)** - Paging, allocation, virtual memory
4. **[04-Interrupts-IDT.md](04-Interrupts-IDT.md)** - Interrupt handling system
5. **[05-Drivers.md](05-Drivers.md)** - Hardware device drivers
6. **[06-Filesystem-FAT32.md](06-Filesystem-FAT32.md)** - File storage and retrieval
7. **[07-Build-System.md](07-Build-System.md)** - Compilation and linking
8. **[08-Kernel-Architecture.md](08-Kernel-Architecture.md)** - Overall kernel design
9. **[09-Advanced-Topics.md](09-Advanced-Topics.md)** - Advanced OS concepts

## Important Resources

### Official Documentation

- [Intel Software Developer Manuals](https://software.intel.com/content/www/us/en/develop/articles/intel-sdm.html) - Complete x86_64 reference
- [AMD64 Architecture Programmer's Manual](https://www.amd.com/en/support/tech-docs) - AMD's x86_64 documentation
- [OSDev Wiki](https://wiki.osdev.org/) - Community OS development knowledge base
- [Multiboot2 Specification](https://www.gnu.org/software/grub/manual/multiboot2/) - Bootloader protocol

### Recommended Books

- "Operating Systems: Three Easy Pieces" by Remzi H. Arpaci-Dusseau
- "Operating System Concepts" by Silberschatz, Galvin, Gagne
- "Modern Operating Systems" by Andrew S. Tanenbaum
- "The Design of the UNIX Operating System" by Maurice J. Bach

### Online Tutorials

- [OSDev.org Forum](https://forum.osdev.org/)
- [BrokenThorn OS Development Series](http://www.brokenthorn.com/Resources/)
- [JamesM's Kernel Development Tutorials](http://www.jamesmolloy.co.uk/tutorial_html/)

## Notation Used in These Notes

### Code Block Notation

```c
// C code examples
```

```asm
; Assembly code examples (NASM syntax)
```

```bash
# Shell commands
```

### Important Symbols

- ⚠️ **Warning**: Common pitfall or error
- 💡 **Tip**: Helpful insight or best practice
- 🔍 **Deep Dive**: Advanced technical detail
- 📝 **Note**: Important information
- 🎯 **Example**: Practical code example

### Memory Addresses

- `0xB8000` - Hexadecimal physical address
- `[rax]` - Memory dereference in assembly
- `(void*)0x1000` - C pointer cast

### Register Names

- **64-bit**: `rax, rbx, rcx, rdx, rsi, rdi, rbp, rsp, r8-r15`
- **32-bit**: `eax, ebx, ecx, edx, esi, edi, ebp, esp`
- **16-bit**: `ax, bx, cx, dx, si, di, bp, sp`
- **8-bit**: `al, ah, bl, bh, cl, ch, dl, dh`

## Testing Your OS

### Running in QEMU

```bash
make build-x86_64
qemu-system-x86_64 -cdrom dist/x86_64/kernel.iso -m 1G
```

### Debugging with GDB

```bash
# Terminal 1: Start QEMU with debugging
qemu-system-x86_64 -cdrom dist/x86_64/kernel.iso -s -S

# Terminal 2: Connect GDB
gdb dist/x86_64/kernel.bin
(gdb) target remote localhost:1234
(gdb) break kernel_main
(gdb) continue
```

## Common Debugging Techniques

### 1. Serial Port Logging

Add serial output for debugging without graphics:

```c
void serial_write(char c) {
    while (!(inb(0x3FD) & 0x20));
    outb(0x3F8, c);
}
```

### 2. Triple Fault Detection

Triple faults cause automatic CPU reset:

- Enable QEMU logging: `-d int,cpu_reset`
- Check for stack corruption
- Verify IDT is properly loaded

### 3. Memory Corruption Detection

- Use canary values: `0xDEADBEEF`
- Check stack overflow with guard pages
- Validate pointers before dereferencing

## Development Workflow

1. **Write Code**: Implement new feature in C/ASM
2. **Build**: Run `make build-x86_64`
3. **Test**: Run in QEMU
4. **Debug**: Use serial logs, QEMU monitor, or GDB
5. **Iterate**: Fix bugs and rebuild

## Next Steps

Start with **[01-Boot-Process.md](01-Boot-Process.md)** to understand how your OS comes to life!

---

_Last Updated: February 2026_
_Based on x86_64 OS Implementation_
