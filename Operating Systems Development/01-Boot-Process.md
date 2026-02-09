# 01 - Boot Process: From Power-On to Kernel

## Table of Contents
1. [Overview](#overview)
2. [BIOS/UEFI Boot Sequence](#biosuefi-boot-sequence)
3. [Multiboot2 Specification](#multiboot2-specification)
4. [CPU Mode Transitions](#cpu-mode-transitions)
5. [Our Boot Implementation](#our-boot-implementation)
6. [GRUB Configuration](#grub-configuration)

---

## Overview

The boot process is the sequence of events that occur from powering on the computer to running your kernel code. This is one of the most complex parts of OS development because it involves:

1. Hardware initialization (BIOS/UEFI)
2. Bootloader loading (GRUB)
3. CPU mode transitions (Real → Protected → Long)
4. Memory setup (paging, segmentation)
5. Jumping to kernel code

### The Boot Chain

```
Power On → BIOS/UEFI → Bootloader (GRUB) → Our Boot Code → Kernel Main
           (Hardware)   (Finds kernel)     (Setup CPU)      (OS Logic)
```

---

## BIOS/UEFI Boot Sequence

### BIOS (Basic Input/Output System) - Legacy Method

When you power on a computer with BIOS:

1. **POST (Power-On Self Test)**
   - Hardware check (RAM, CPU, devices)
   - Initializes hardware to known states
   - Beep codes indicate problems

2. **Boot Device Selection**
   - Check boot order (HDD, CD-ROM, USB, Network)
   - Read first sector (512 bytes) called **Master Boot Record (MBR)**
   - MBR contains bootloader code

3. **Bootloader Execution**
   - CPU starts in **Real Mode** (16-bit, 1MB memory limit)
   - Transfer control to bootloader (GRUB in our case)

### UEFI (Unified Extensible Firmware Interface) - Modern Method

UEFI is more advanced:
- Boots in 32-bit or 64-bit mode directly
- Uses GPT (GUID Partition Table) instead of MBR
- Has its own filesystem (FAT32 ESP - EFI System Partition)
- Provides more services to bootloader

📝 **Note**: Our OS uses BIOS boot but can work with UEFI through GRUB.

---

## Multiboot2 Specification

### What is Multiboot?

**Multiboot** is a specification that defines how bootloaders (like GRUB) should load kernels. It standardizes:
- Kernel file format
- Information passed from bootloader to kernel
- Initial CPU state

### Why Multiboot2?

Instead of writing our own bootloader (complex!), we use GRUB which:
- Understands Multiboot2
- Handles hardware detection
- Sets up initial environment
- Loads our kernel into memory

### The Multiboot2 Header

Our kernel must contain a special header that GRUB can recognize:

**File: `boot/header.asm`**
```asm
section .multiboot_header
header_start:

    ; Magic number - identifies this as Multiboot2
    dd 0xe85250d6       ; Required magic value

    ; Architecture - tells GRUB what CPU mode to use
    dd 0x00             ; 0 = protected mode i386

    ; Header length - size of this header
    dd header_end - header_start

    ; Checksum - validation (magic + arch + length + checksum = 0)
    dd 0x100000000 - (0xe85250d6 + 0x00 + (header_end - header_start))

    ; End tag - indicates no more data
    dw 0                ; Type: End tag
    dw 0                ; Flags
    dd 8                ; Size

header_end:
```

#### Header Breakdown

| Field | Value | Purpose |
|-------|-------|---------|
| Magic Number | `0xe85250d6` | Multiboot2 identifier |
| Architecture | `0x00` | i386 protected mode (32-bit) |
| Header Length | Calculated | Size of entire header |
| Checksum | Calculated | Validation (sum = 0) |
| End Tag | `0, 0, 8` | Marks end of tags |

💡 **Tip**: GRUB searches for this magic number in the first 32KB of the kernel file.

### Information Passed to Kernel

When GRUB boots our kernel:
- `EAX` register contains **magic value** `0x36d76289` (Multiboot2 loaded)
- `EBX` register contains **physical address** of Multiboot information structure
- CPU is in **32-bit protected mode**
- A20 line is enabled (full memory access)

---

## CPU Mode Transitions

The x86_64 CPU supports multiple modes, each with different capabilities:

### 1. Real Mode (16-bit)
- **Memory**: 1 MB addressable (20-bit addresses)
- **Registers**: 16-bit (AX, BX, CX, DX, SI, DI, BP, SP)
- **Usage**: BIOS, ancient DOS programs
- **Limitations**: No memory protection, no virtual memory

### 2. Protected Mode (32-bit)
- **Memory**: 4 GB addressable (32-bit addresses)
- **Registers**: 32-bit (EAX, EBX, ECX, EDX, ESI, EDI, EBP, ESP)
- **Features**: 
  - Memory protection (ring 0-3 privilege levels)
  - Virtual memory via paging
  - Hardware task switching
- **Usage**: 32-bit operating systems (Windows 95-XP, Linux 32-bit)

### 3. Long Mode (64-bit)
- **Memory**: Theoretical 16 EB, practical 256 TB (48-bit addresses)
- **Registers**: 64-bit (RAX, RBX, etc.) + 8 new registers (R8-R15)
- **Features**:
  - Everything from protected mode
  - Larger address space
  - More registers
  - Mandatory paging
  - No hardware task switching
- **Usage**: Modern 64-bit operating systems

### Mode Transition Sequence

Our boot process follows this path:

```
BIOS → Real Mode (16-bit)
  ↓
GRUB → Protected Mode (32-bit) ← We start here!
  ↓
Our Code → Long Mode (64-bit) ← Final destination
```

---

## Our Boot Implementation

### Entry Point: `boot/main.asm`

**File: `boot/main.asm`**
```asm
global start
extern long_mode_start

section .text
bits 32
start:
    mov esp, stack_top        ; Setup stack

    call check_multiboot      ; Verify GRUB loaded us
    call check_cpuid          ; Check if CPUID instruction exists
    call check_long_mode      ; Verify CPU supports 64-bit mode

    call setup_page_tables    ; Setup paging structures
    call enable_paging        ; Enable paging and long mode

    lgdt [gdt64.pointer]      ; Load 64-bit GDT
    jmp gdt64.code_segment:long_mode_start  ; Jump to 64-bit code

    hlt                       ; Should never reach here
```

### Step 1: Setup Stack

```asm
mov esp, stack_top
```

- Stack grows downward in memory
- `ESP` (stack pointer) needs to point to valid memory
- `stack_top` defined later in `.bss` section

### Step 2: Check Multiboot

```asm
check_multiboot:
    cmp eax, 0x36d76289       ; Multiboot2 magic value
    jne .no_multiboot         ; If not equal, error
    ret
.no_multiboot:
    mov al, "M"               ; Error code 'M'
    jmp error                 ; Display error and hang
```

🔍 **Deep Dive**: GRUB puts `0x36d76289` in EAX to prove it loaded us correctly.

### Step 3: Check CPUID Support

```asm
check_cpuid:
    pushfd                    ; Save FLAGS register
    pop eax                   ; Copy to EAX
    mov ecx, eax              ; Save original
    xor eax, 1 << 21          ; Flip ID bit (bit 21)
    push eax                  
    popfd                     ; Load modified FLAGS
    pushfd
    pop eax                   ; Read FLAGS again
    push ecx
    popfd                     ; Restore original FLAGS
    cmp eax, ecx              ; Did bit 21 change?
    je .no_cpuid              ; If same, CPUID not supported
    ret
.no_cpuid:
    mov al, "C"
    jmp error
```

💡 **Tip**: If bit 21 of FLAGS can be flipped, CPUID instruction exists.

### Step 4: Check Long Mode Support

```asm
check_long_mode:
    mov eax, 0x80000000       ; Extended CPUID function
    cpuid                     ; Execute CPUID
    cmp eax, 0x80000001       ; Check if extended functions available
    jb .no_long_mode          ; Jump if below

    mov eax, 0x80000001       ; Get extended features
    cpuid
    test edx, 1 << 29         ; Test LM bit (bit 29)
    jz .no_long_mode          ; Jump if zero (not supported)
    
    ret
.no_long_mode:
    mov al, "L"
    jmp error
```

🔍 **Deep Dive**: CPUID with EAX=0x80000001 returns features in EDX. Bit 29 = Long Mode support.

### Step 5: Setup Page Tables

Before entering long mode, we MUST enable paging. Long mode requires paging.

#### Paging Overview

**Paging** translates virtual addresses to physical addresses using **page tables**:

```
Virtual Address → [Page Tables] → Physical Address
```

In x86_64, we use **4-level paging**:

```
CR3 → Page Table Level 4 (PML4)
        ↓
      Page Directory Pointer Table (PDP/Level 3)
        ↓
      Page Directory (PD/Level 2)
        ↓
      Page Table (PT/Level 1)
        ↓
      Physical Page (4KB)
```

💡 **Tip**: We use **2MB huge pages** to simplify, so we skip Level 1.

#### Page Table Setup Code

```asm
setup_page_tables:
    ; Link L4 → L3
    mov eax, page_table_l3
    or eax, 0b11              ; Set Present + Writable bits
    mov [page_table_l4], eax  ; Store in first entry of L4
    
    ; Link L3 → L2
    mov eax, page_table_l2
    or eax, 0b11              ; Present + Writable
    mov [page_table_l3], eax  ; Store in first entry of L3

    ; Map 512 * 2MB = 1GB of memory
    mov ecx, 0                ; Counter
.loop:
    mov eax, 0x200000         ; 2MB page size
    mul ecx                   ; EAX = 2MB * counter
    or eax, 0b10000011        ; Present + Writable + Huge Page
    mov [page_table_l2 + ecx * 8], eax  ; Store in L2 entry

    inc ecx                   ; Increment counter
    cmp ecx, 512              ; 512 entries in page table
    jne .loop                 ; Continue if not done

    ret
```

#### Page Table Entry Format

Each entry is 8 bytes (64 bits):

| Bits | Name | Purpose |
|------|------|---------|
| 0 | Present | 1 = page is in memory |
| 1 | Writable | 1 = can write to page |
| 2 | User | 0 = kernel only |
| 3-11 | Flags | Various flags |
| 12-51 | Address | Physical address of next level |
| 52-63 | Reserved | Set to 0 |

⚠️ **Warning**: Addresses in page tables must be **4KB aligned** (bottom 12 bits = 0).

### Step 6: Enable Paging

```asm
enable_paging:
    ; Tell CPU where page tables are
    mov eax, page_table_l4
    mov cr3, eax              ; CR3 holds page table base

    ; Enable PAE (Physical Address Extension)
    mov eax, cr4
    or eax, 1 << 5            ; Set PAE bit
    mov cr4, eax

    ; Enable Long Mode in EFER MSR
    mov ecx, 0xC0000080       ; EFER MSR number
    rdmsr                     ; Read MSR into EAX
    or eax, 1 << 8            ; Set LME bit (Long Mode Enable)
    wrmsr                     ; Write back to MSR

    ; Enable paging (also activates long mode)
    mov eax, cr0
    or eax, 1 << 31           ; Set PG bit
    mov cr0, eax              ; Paging is now ON!

    ret
```

🔍 **Deep Dive**: 
- **CR3**: Page table base register
- **CR4**: Control register with feature flags
- **EFER**: Extended Feature Enable Register (MSR)
- **CR0**: Control register, bit 31 = paging enable

### Step 7: Load GDT and Jump to Long Mode

```asm
lgdt [gdt64.pointer]                    ; Load GDT
jmp gdt64.code_segment:long_mode_start  ; Far jump to 64-bit code
```

The **Global Descriptor Table (GDT)** defines memory segments:

```asm
section .rodata
gdt64:
    dq 0                      ; Null descriptor (required)
.code_segment: equ $ - gdt64
    dq (1<<43) | (1<<44) | (1<<47) | (1<<53)  ; Code segment
.pointer:
    dw $ - gdt64 - 1          ; GDT size
    dq gdt64                  ; GDT address
```

**GDT Entry Bits**:
- Bit 43: Executable
- Bit 44: Code/Data segment
- Bit 47: Present
- Bit 53: 64-bit mode

### Step 8: Long Mode Code

**File: `boot/main64.asm`**
```asm
global long_mode_start
extern kernel_main

section .text
bits 64
long_mode_start:
    ; Clear segment registers (not used in long mode)
    mov ax, 0
    mov ss, ax
    mov ds, ax
    mov es, ax
    mov fs, ax
    mov gs, ax

    call kernel_main          ; Jump to C code!
    hlt                       ; Halt if kernel returns
```

💡 **Tip**: In long mode, segmentation is mostly disabled. We use paging exclusively.

---

## GRUB Configuration

### Linker Script

The **linker script** tells the linker how to arrange our kernel in memory:

**File: `targets/x86_64/linker.ld`**
```ld
ENTRY(start)                  /* Entry point symbol */

SECTIONS
{
  . = 1M;                      /* Load kernel at 1MB mark */

  .boot :
  {
    KEEP(*(.multiboot_header)) /* Multiboot header first */
  }

  .text :
  {
    *(.text)                   /* All code sections */
  }
  
  /* Additional sections: .rodata, .data, .bss */
}
```

📝 **Note**: 1M (1 megabyte) = `0x100000` is a safe loading address.

### GRUB Configuration File

**File: `targets/x86_64/grub.cfg`**
```
set timeout=0
set default=0

menuentry "My OS" {
    multiboot2 /boot/kernel.bin
    boot
}
```

### Creating Bootable ISO

```bash
# Build kernel
make build-x86_64

# Create ISO structure
mkdir -p targets/x86_64/iso/boot/grub
cp dist/x86_64/kernel.bin targets/x86_64/iso/boot/
cp targets/x86_64/grub.cfg targets/x86_64/iso/boot/grub/

# Create ISO
grub-mkrescue -o dist/x86_64/kernel.iso targets/x86_64/iso
```

---

## Memory Layout After Boot

After our boot code runs:

```
Virtual Address     Physical Address    Description
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
0x0000000000000000  0x0000000000000000  First 1GB identity mapped
...                 ...                 (virtual = physical)
0x0000000040000000  0x0000000040000000  (1GB mark)

0x0000000000100000  0x0000000000100000  ← Our kernel loaded here (1MB)
    ↓ .text                              (Code section)
    ↓ .rodata                            (Read-only data)
    ↓ .data                              (Initialized data)
    ↓ .bss                               (Uninitialized data)
    ↓ Stack                              (Growing downward)
    ↓ Heap                               (Growing upward)
```

---

## Common Boot Problems

### Problem 1: Triple Fault (Immediate Reboot)

**Symptoms**: QEMU restarts immediately

**Causes**:
- Invalid GDT
- Stack overflow
- Paging misconfiguration
- IDT not loaded before enabling interrupts

**Debug**:
```bash
qemu-system-x86_64 -d int,cpu_reset kernel.iso
```

### Problem 2: "Operating System Not Found"

**Symptoms**: GRUB shows error or doesn't find kernel

**Causes**:
- Multiboot header missing or incorrect
- Kernel not in `/boot/` directory of ISO
- GRUB config wrong

**Fix**: Verify `grub.cfg` path and check Multiboot magic number.

### Problem 3: Page Fault on Boot

**Symptoms**: Crash with error code

**Causes**:
- Page tables not set up correctly
- Writing to unmapped memory
- Stack pointer invalid

**Debug**: Check CR2 register (contains faulting address).

---

## Summary

The boot process involves:

1. ✅ **BIOS** initializes hardware
2. ✅ **GRUB** loads our kernel using Multiboot2
3. ✅ **Start in 32-bit protected mode**
4. ✅ **Check CPU capabilities** (CPUID, Long Mode)
5. ✅ **Setup page tables** (4-level paging with 2MB pages)
6. ✅ **Enable paging and long mode**
7. ✅ **Load GDT** for 64-bit segments
8. ✅ **Jump to 64-bit kernel code**
9. ✅ **Call `kernel_main()`** in C

### Key Takeaways

- 💡 Bootloader (GRUB) does heavy lifting for us
- 💡 Multiboot2 header is essential for GRUB to find us
- 💡 Long mode requires paging to be enabled
- 💡 We identity map first 1GB (virtual = physical)
- 💡 After boot, we have 64-bit CPU with paging active

---

**Next**: [02-x86-64-Architecture.md](02-x86-64-Architecture.md) - Deep dive into x86_64 CPU

---

*"The boot process is where hardware meets software. Master it, and you've conquered the first mountain of OS development."*
