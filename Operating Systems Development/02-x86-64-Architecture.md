# 02 - x86_64 Architecture: Understanding the CPU

## Table of Contents

1. [Overview](#overview)
2. [Registers](#registers)
3. [Memory Segmentation](#memory-segmentation)
4. [Paging Deep Dive](#paging-deep-dive)
5. [Privilege Levels](#privilege-levels)
6. [Assembly Language Basics](#assembly-language-basics)
7. [Calling Conventions](#calling-conventions)

---

## Overview

The **x86_64** (also called **AMD64** or **x64**) is a 64-bit extension of the x86 architecture. Understanding the CPU is fundamental to OS development because:

- Your OS runs directly on the CPU (no abstraction layer)
- You control memory management (paging, segmentation)
- You handle CPU exceptions and interrupts
- You manage execution context (registers, stack)

### Key Features of x86_64

| Feature                   | Value              | Description                                    |
| ------------------------- | ------------------ | ---------------------------------------------- |
| Word Size                 | 64-bit             | Default operand size                           |
| Address Bus               | 48-bit (practical) | Can address 256 TB                             |
| Virtual Memory            | 48-bit             | 256 TB virtual address space                   |
| General Purpose Registers | 16                 | RAX, RBX, RCX, RDX, RSI, RDI, RBP, RSP, R8-R15 |
| Privilege Levels          | 4 (Ring 0-3)       | Ring 0 = kernel, Ring 3 = user                 |
| Cache Levels              | L1, L2, L3         | CPU cache hierarchy                            |

---

## Registers

Registers are ultra-fast storage locations **inside** the CPU. They're the fastest memory available!

### Register Hierarchy

x86_64 maintains backward compatibility with 32-bit, 16-bit, and 8-bit modes:

```
RAX (64-bit)
├─ EAX (32-bit lower half)
│  ├─ AX (16-bit lower half)
│  │  ├─ AL (8-bit lower)
│  │  └─ AH (8-bit upper)
```

### General Purpose Registers

| 64-bit | 32-bit   | 16-bit   | 8-bit (L/H) | Historical Use    | Modern Use                    |
| ------ | -------- | -------- | ----------- | ----------------- | ----------------------------- |
| RAX    | EAX      | AX       | AL/AH       | Accumulator       | General purpose, return value |
| RBX    | EBX      | BX       | BL/BH       | Base              | General purpose               |
| RCX    | ECX      | CX       | CL/CH       | Counter           | Loop counter, argument 4      |
| RDX    | EDX      | DX       | DL/DH       | Data              | General purpose, argument 3   |
| RSI    | ESI      | SI       | SIL         | Source Index      | String source, argument 2     |
| RDI    | EDI      | DI       | DIL         | Destination Index | String dest, argument 1       |
| RBP    | EBP      | BP       | BPL         | Base Pointer      | Stack frame base              |
| RSP    | ESP      | SP       | SPL         | Stack Pointer     | Top of stack (CRITICAL!)      |
| R8-R15 | R8D-R15D | R8W-R15W | R8B-R15B    | n/a (64-bit only) | General purpose               |

#### Register Usage Examples

```asm
; 64-bit operation
mov rax, 0x123456789ABCDEF0

; 32-bit operation (zero-extends to 64-bit)
mov eax, 0x12345678          ; RAX = 0x0000000012345678

; 16-bit operation (doesn't affect upper bits)
mov ax, 0x1234               ; RAX = 0x0000000012341234

; 8-bit operations
mov al, 0x12                 ; RAX = 0x0000000012341212
mov ah, 0xFF                 ; RAX = 0x000000001234FF12
```

💡 **Tip**: Writing to 32-bit register (EAX) **zero-extends** to 64-bit, but 8/16-bit writes don't!

### Special Purpose Registers

#### Instruction Pointer

- **RIP** (64-bit): Points to current instruction being executed
- Cannot be directly modified (use `jmp`, `call`, `ret`)

#### Flags Register

- **RFLAGS** (64-bit): Contains status and control flags

| Bit   | Name          | Symbol | Description                         |
| ----- | ------------- | ------ | ----------------------------------- |
| 0     | Carry         | CF     | Previous operation carried/borrowed |
| 2     | Parity        | PF     | Even number of set bits in result   |
| 4     | Adjust        | AF     | BCD arithmetic carry                |
| 6     | Zero          | ZF     | Result was zero                     |
| 7     | Sign          | SF     | Result was negative                 |
| 8     | Trap          | TF     | Single-step debugging               |
| 9     | Interrupt     | IF     | Interrupts enabled                  |
| 10    | Direction     | DF     | String operation direction          |
| 11    | Overflow      | OF     | Signed overflow occurred            |
| 12-13 | I/O Privilege | IOPL   | I/O privilege level (0-3)           |
| 14    | Nested Task   | NT     | Task switching flag                 |
| 16    | Resume        | RF     | Debug exception control             |
| 17    | Virtual 8086  | VM     | Virtual 8086 mode                   |
| 18    | Alignment     | AC     | Alignment checking                  |
| 21    | CPUID         | ID     | CPUID instruction support           |

#### Conditional Jumps Based on Flags

```asm
cmp rax, rbx          ; Compare RAX and RBX (sets flags)
je  equal_label       ; Jump if Equal (ZF=1)
jne not_equal_label   ; Jump if Not Equal (ZF=0)
jg  greater_label     ; Jump if Greater (signed)
ja  above_label       ; Jump if Above (unsigned)
jl  less_label        ; Jump if Less (signed)
jb  below_label       ; Jump if Below (unsigned)
```

### Control Registers

Control registers govern CPU operation:

| Register | Purpose            | Key Bits                            |
| -------- | ------------------ | ----------------------------------- |
| **CR0**  | System control     | PE (Protection Enable), PG (Paging) |
| **CR2**  | Page fault address | Contains faulting virtual address   |
| **CR3**  | Page table base    | Physical address of page table      |
| **CR4**  | Extended features  | PAE, PSE, PGE, OSFXSR, OSXSAVE      |
| **CR8**  | Task priority      | TPR for interrupt priority          |

#### CR0 Control Register

```
Bit 0  (PE): Protection Enable - switches to protected mode
Bit 31 (PG): Paging - enables virtual memory
```

```asm
; Enable paging
mov eax, cr0
or eax, 1 << 31       ; Set PG bit
mov cr0, eax          ; Paging is now enabled!
```

#### CR3 Page Table Base

```asm
mov rax, page_table_address
mov cr3, rax          ; Tell CPU where page tables are
```

🔍 **Deep Dive**: Changing CR3 flushes the TLB (Translation Lookaside Buffer), which caches page table entries. This is expensive!

#### CR4 Extended Features

```
Bit 5 (PAE): Physical Address Extension - 36-bit physical addresses
Bit 7 (PGE): Page Global Enable - prevents TLB flush for global pages
```

### Segment Registers

In 64-bit mode, segmentation is mostly disabled, but these still exist:

| Register   | Use in Long Mode                                            |
| ---------- | ----------------------------------------------------------- |
| CS         | Code segment (still used for privilege level)               |
| DS, ES, SS | Ignored (base = 0, limit = max)                             |
| FS, GS     | Can be used with FS.base / GS.base for thread-local storage |

#### Using FS/GS for Thread-Local Storage

```asm
; Set GS base
mov ecx, 0xC0000101   ; GS base MSR
mov eax, base_low
mov edx, base_high
wrmsr

; Access thread-local variable
mov rax, gs:[0x10]    ; Read from GS:0x10
```

### Model-Specific Registers (MSRs)

MSRs control CPU features. Access with `rdmsr` / `wrmsr`:

```asm
mov ecx, 0xC0000080   ; EFER MSR (Extended Feature Enable Register)
rdmsr                 ; Read into EDX:EAX
or eax, 1 << 8        ; Set LME (Long Mode Enable)
wrmsr                 ; Write back
```

Common MSRs:

- `0xC0000080` - EFER (long mode enable)
- `0xC0000100` - FS.base
- `0xC0000101` - GS.base
- `0x174-0x176` - SYSENTER/SYSEXIT configuration
- `0xC0000081-0x084` - SYSCALL/SYSRET configuration

---

## Memory Segmentation

### Historical Background

In real mode (16-bit), segmentation was essential:

- 16-bit registers = 64KB addressable
- Segments extended this: `Physical = Segment * 16 + Offset`
- Example: `0x1000:0x2000` = `0x1000 * 16 + 0x2000` = `0x12000`

### Protected Mode Segmentation

In 32-bit protected mode:

- Segments defined in **Global Descriptor Table (GDT)**
- Each segment has **base**, **limit**, and **access rights**
- Segment registers hold **selectors** (indices into GDT)

#### GDT Entry Format (64-bit)

```c
struct gdt_entry {
    uint16_t limit_low;       // Limit bits 0-15
    uint16_t base_low;        // Base bits 0-15
    uint8_t  base_mid;        // Base bits 16-23
    uint8_t  access;          // Access flags
    uint8_t  granularity;     // Limit bits 16-19 + flags
    uint8_t  base_high;       // Base bits 24-31
} __attribute__((packed));
```

#### Our Minimal GDT

Since segmentation is mostly disabled in long mode, we use a minimal GDT:

```asm
section .rodata
gdt64:
    dq 0                      ; Null descriptor (required)
.code_segment: equ $ - gdt64
    dq (1<<43) | (1<<44) | (1<<47) | (1<<53)  ; Code segment
.data_segment: equ $ - gdt64
    dq (1<<44) | (1<<47)      ; Data segment
.pointer:
    dw $ - gdt64 - 1          ; Size
    dq gdt64                  ; Address
```

**Access byte bits**:

- Bit 43: Executable
- Bit 44: Descriptor type (1 = code/data)
- Bit 47: Present
- Bit 53: 64-bit code segment

### Loading the GDT

```asm
lgdt [gdt64.pointer]          ; Load GDT
; Must reload CS with far jump
jmp gdt64.code_segment:reload_segments
reload_segments:
    mov ax, gdt64.data_segment
    mov ds, ax                 ; Load DS
    mov es, ax                 ; Load ES
    mov ss, ax                 ; Load SS
    ret
```

---

## Paging Deep Dive

**Paging** is THE memory management mechanism in x86_64. It translates virtual addresses to physical addresses.

### Why Paging?

1. **Isolation**: Each process has its own virtual address space
2. **Protection**: Kernel memory protected from user processes
3. **Flexibility**: Physical memory doesn't need to be contiguous
4. **Swapping**: Pages can be moved to disk (swap)
5. **Shared Memory**: Multiple virtual addresses → same physical page

### Virtual Address Format (48-bit)

```
Virtual Address (48 bits used, 16 bits sign-extended):
┌────────┬────────┬────────┬────────┬────────┐
│ Sign   │ PML4   │  PDP   │   PD   │ Offset │
│ Ext    │ Index  │ Index  │ Index  │        │
│ 63-48  │ 47-39  │ 38-30  │ 29-21  │ 20-0   │
└────────┴────────┴────────┴────────┴────────┘
   16b      9b       9b       9b       21b

For 4KB pages (4-level):
│ 47-39  │ 38-30  │ 29-21  │ 20-12  │ 11-0   │
  PML4     PDP      PD       PT      Offset

For 2MB pages (3-level, our implementation):
│ 47-39  │ 38-30  │ 29-21  │ 20-0               │
  PML4     PDP      PD       Offset (2MB offset)
```

### Page Table Structure

```
CR3 → PML4 (Page Map Level 4)
       ├─ Entry 0 → PDP
       ├─ Entry 1 → PDP
       │    ...
       └─ Entry 511 → PDP

      PDP (Page Directory Pointer Table)
       ├─ Entry 0 → PD
       ├─ Entry 1 → PD
       │    ...
       └─ Entry 511 → PD

      PD (Page Directory)
       ├─ Entry 0 → PT or 2MB Page
       ├─ Entry 1 → PT or 2MB Page
       │    ...
       └─ Entry 511 → PT or 2MB Page

      PT (Page Table) - if using 4KB pages
       ├─ Entry 0 → 4KB Physical Page
       ├─ Entry 1 → 4KB Physical Page
       │    ...
       └─ Entry 511 → 4KB Physical Page
```

### Page Table Entry Format

```
63    52 51  12 11      0
┌───────┬──────┬─────────┐
│ Rsvd  │ Addr │  Flags  │
└───────┴──────┴─────────┘
```

**Flags** (bits 0-11):

| Bit  | Name | Description                                               |
| ---- | ---- | --------------------------------------------------------- |
| 0    | P    | **Present**: Page is in memory                            |
| 1    | R/W  | **Read/Write**: 0 = read-only, 1 = writable               |
| 2    | U/S  | **User/Supervisor**: 0 = kernel only, 1 = user accessible |
| 3    | PWT  | Page Write-Through                                        |
| 4    | PCD  | Page Cache Disable                                        |
| 5    | A    | **Accessed**: Set by CPU when page is read                |
| 6    | D    | **Dirty**: Set by CPU when page is written                |
| 7    | PS   | **Page Size**: 1 = huge page (2MB/1GB)                    |
| 8    | G    | **Global**: Not flushed from TLB on CR3 reload            |
| 9-11 | AVL  | Available for OS use                                      |

### Address Translation Example

Let's translate virtual address `0x0000000000401234`:

```
Binary: 0000 0000 0000 0000 | 000 000000 | 000 000001 | 000 000000 | 001 001001 00110100

Breakdown:
PML4 Index: 000000000 = 0
PDP Index:  000000000 = 0
PD Index:   000000001 = 1
PT Index:   000000001 = 1  (if using 4KB pages)
Offset:     000100110100 = 0x234

Translation steps:
1. Read PML4[0] from CR3
2. PML4[0] points to PDP
3. Read PDP[0]
4. PDP[0] points to PD
5. Read PD[1]
6. If PS=1 (2MB page): Physical = PD[1].base + offset (0x1234)
   If PS=0 (4KB page): PD[1] points to PT, read PT[1], Physical = PT[1].base + offset (0x234)
```

### Our Page Table Setup

**Identity Mapping** the first 1GB (virtual = physical):

```asm
setup_page_tables:
    ; Map L4[0] → L3
    mov eax, page_table_l3
    or eax, 0b11              ; Present + Writable
    mov [page_table_l4], eax

    ; Map L3[0] → L2
    mov eax, page_table_l2
    or eax, 0b11              ; Present + Writable
    mov [page_table_l3], eax

    ; Map 512 entries in L2, each covering 2MB
    mov ecx, 0
.loop:
    mov eax, 0x200000         ; 2MB
    mul ecx                   ; EAX = 2MB * counter
    or eax, 0b10000011        ; Present + Writable + Huge Page
    mov [page_table_l2 + ecx * 8], eax

    inc ecx
    cmp ecx, 512              ; 512 * 2MB = 1GB
    jne .loop
    ret

section .bss
align 4096
page_table_l4:
    resb 4096                 ; 512 entries * 8 bytes
page_table_l3:
    resb 4096
page_table_l2:
    resb 4096
```

This maps:

- Virtual `0x00000000 - 0x3FFFFFFF` (0-1GB)
- To Physical `0x00000000 - 0x3FFFFFFF` (0-1GB)

### TLB (Translation Lookaside Buffer)

The **TLB** is a CPU cache for page table entries:

- Speeds up address translation (page table walks are slow!)
- Automatically managed by CPU
- Flushed on CR3 write (context switch)
- Global pages (G flag) not flushed

```asm
; Invalidate single page in TLB
invlpg [virtual_address]

; Flush entire TLB (except global pages)
mov rax, cr3
mov cr3, rax
```

---

## Privilege Levels (Ring 0-3)

x86 has **4 privilege levels** (rings):

```
Ring 0 (Kernel)     [Most Privileged]
   ↓
Ring 1 (Drivers)    [Rarely used]
   ↓
Ring 2 (Drivers)    [Rarely used]
   ↓
Ring 3 (User Apps)  [Least Privileged]
```

Modern OSes typically use only:

- **Ring 0**: Kernel mode (full hardware access)
- **Ring 3**: User mode (restricted access)

### Current Privilege Level (CPL)

The CPL is stored in the **CS register** (bits 0-1):

```asm
mov ax, cs
and ax, 3             ; Mask bits 0-1
; AX now contains CPL (0 = kernel, 3 = user)
```

### Privilege Checks

The CPU enforces several privilege checks:

1. **Instruction Privileges**: Some instructions only work in Ring 0
   - `cli` / `sti` (disable/enable interrupts)
   - `lgdt`, `lidt` (load descriptor tables)
   - `mov cr0, rax` (control register access)
   - `in` / `out` (port I/O, unless IOPL permits)

2. **Memory Access**: Page table U/S bit controls access
   - U/S = 0: Only Ring 0 can access
   - U/S = 1: Ring 3 can access (if readable/writable)

3. **I/O Ports**: IOPL field in RFLAGS controls port access
   - IOPL = 3: User mode can use `in`/`out`
   - IOPL = 0: Only kernel can use `in`/`out`

### Switching Privilege Levels

From Ring 3 to Ring 0 (entering kernel):

- **Syscall** instruction (fast)
- **Interrupt** (int 0x80 in older Linux)
- **Exception** (e.g., page fault)

From Ring 0 to Ring 3 (returning to user):

- **Sysret** instruction (fast, pairs with syscall)
- **IRET** (interrupt return)

---

## Assembly Language Basics

### NASM Syntax (Intel Syntax)

We use **Intel syntax** (destination first):

```asm
mov rax, rbx          ; RAX = RBX (Intel)
movq %rbx, %rax       ; RAX = RBX (AT&T, not used here)
```

### Common Instructions

#### Data Movement

```asm
mov  rax, 123         ; RAX = 123
mov  rax, rbx         ; RAX = RBX
mov  rax, [rbx]       ; RAX = *RBX (load from memory)
mov  [rax], rbx       ; *RAX = RBX (store to memory)
lea  rax, [rbx + 8]   ; RAX = RBX + 8 (load effective address, no memory access!)

push rax              ; ESP -= 8; [ESP] = RAX
pop  rax              ; RAX = [ESP]; ESP += 8
```

#### Arithmetic

```asm
add  rax, rbx         ; RAX += RBX
sub  rax, rbx         ; RAX -= RBX
inc  rax              ; RAX++
dec  rax              ; RAX--
mul  rbx              ; RDX:RAX = RAX * RBX (unsigned)
imul rbx              ; RDX:RAX = RAX * RBX (signed)
div  rbx              ; RAX = RDX:RAX / RBX; RDX = remainder
```

#### Bitwise

```asm
and  rax, rbx         ; RAX &= RBX
or   rax, rbx         ; RAX |= RBX
xor  rax, rbx         ; RAX ^= RBX (also: xor rax, rax = zero RAX)
not  rax              ; RAX = ~RAX
shl  rax, 3           ; RAX <<= 3 (multiply by 8)
shr  rax, 2           ; RAX >>= 2 (divide by 4, unsigned)
sar  rax, 2           ; RAX >>= 2 (divide by 4, signed)
```

#### Control Flow

```asm
jmp  label            ; Unconditional jump
call function         ; Call function (push return address, jump)
ret                   ; Return from function (pop address, jump to it)

cmp  rax, rbx         ; Compare (sets flags: rax - rbx)
je   equal            ; Jump if Equal (ZF = 1)
jne  not_equal        ; Jump if Not Equal (ZF = 0)
jl   less             ; Jump if Less (signed)
jle  less_equal       ; Jump if Less or Equal (signed)
jg   greater          ; Jump if Greater (signed)
jge  greater_equal    ; Jump if Greater or Equal (signed)
```

#### Special

```asm
nop                   ; No operation (0x90, often used for alignment)
hlt                   ; Halt CPU until interrupt
int  0x80             ; Software interrupt (system call in Linux)
cli                   ; Clear Interrupt Flag (disable interrupts)
sti                   ; Set Interrupt Flag (enable interrupts)
cpuid                 ; CPU identification
rdmsr                 ; Read Model-Specific Register
wrmsr                 ; Write Model-Specific Register
```

### Addressing Modes

```asm
mov rax, [address]           ; Direct: [address]
mov rax, [rbx]               ; Indirect: [rbx]
mov rax, [rbx + 8]           ; Indirect + offset
mov rax, [rbx + rcx]         ; Indexed: [base + index]
mov rax, [rbx + rcx * 4]     ; Scaled indexed: [base + index * scale]
mov rax, [rbx + rcx * 8 + 16]; Full form: [base + index * scale + displacement]
```

**Scale** can be 1, 2, 4, or 8 (for sizeof char, short, int, long).

---

## Calling Conventions

### x86_64 System V ABI (Linux, macOS)

Function arguments:

1. **RDI** - First argument
2. **RSI** - Second argument
3. **RDX** - Third argument
4. **RCX** - Fourth argument
5. **R8** - Fifth argument
6. **R9** - Sixth argument
7. **Stack** - Seventh argument onward

Return value:

- **RAX** - Integer return value
- **RDX:RAX** - 128-bit return value

Caller-saved (volatile):

- RAX, RCX, RDX, RSI, RDI, R8-R11
- (Caller must save these before calling function)

Callee-saved (non-volatile):

- RBX, RBP, R12-R15
- (Called function must preserve these)

Stack:

- **16-byte aligned** before `call` instruction
- Grows downward

#### Example: Calling C from Assembly

```asm
extern printf

section .data
format: db "Hello, %s!", 10, 0
name:   db "World", 0

section .text
    lea rdi, [format]     ; First arg: format string
    lea rsi, [name]       ; Second arg: "World"
    xor rax, rax          ; RAX = 0 (no vector registers used)
    call printf           ; Call printf
```

#### Example: C Function in Assembly

```asm
global add_numbers
; int add_numbers(int a, int b, int c)
add_numbers:
    mov eax, edi          ; a (first arg in EDI)
    add eax, esi          ; + b (second arg in ESI)
    add eax, edx          ; + c (third arg in EDX)
    ret                   ; Return in EAX
```

### Microsoft x64 Calling Convention (Windows)

**Different from System V!**

Arguments:

1. **RCX** - First argument
2. **RDX** - Second argument
3. **R8** - Third argument
4. **R9** - Fourth argument
5. **Stack** - Fifth argument onward

Caller must allocate **32 bytes of shadow space** on stack for first 4 args.

---

## Summary

### Key Concepts

1. ✅ **Registers**: 16 general-purpose 64-bit registers + special registers (RIP, RFLAGS, CR0-4)
2. ✅ **Segmentation**: Mostly disabled in long mode, minimal GDT required
3. ✅ **Paging**: 4-level page tables translate virtual to physical addresses
4. ✅ **Privilege Levels**: Ring 0 (kernel) vs Ring 3 (user), enforced by CPU
5. ✅ **Assembly**: Direct hardware control, NASM Intel syntax
6. ✅ **Calling Convention**: How functions receive arguments and return values

### Registers Quick Reference

```
General Purpose: RAX RBX RCX RDX RSI RDI RBP RSP R8-R15
Special:         RIP RFLAGS
Control:         CR0 CR2 CR3 CR4
Segment:         CS DS ES FS GS SS
Debug:           DR0-DR7
```

### Important Bit Flags

```
CR0.PE (bit 0):  Protection Enable
CR0.PG (bit 31): Paging Enable
CR4.PAE (bit 5): Physical Address Extension
RFLAGS.IF (bit 9): Interrupt Enable
RFLAGS.DF (bit 10): Direction Flag
```

---

**Next**: [03-Memory-Management.md](03-Memory-Management.md) - Physical and virtual memory management

---

_"In OS development, the CPU is your canvas and assembly is your brush. Know them intimately."_
