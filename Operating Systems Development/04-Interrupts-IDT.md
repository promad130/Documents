# 04 - Interrupts and IDT: Handling CPU Events

## Table of Contents

1. [Overview](#overview)
2. [Interrupt Descriptor Table](#interrupt-descriptor-table)
3. [CPU Exceptions](#cpu-exceptions)
4. [Hardware Interrupts](#hardware-interrupts)
5. [Interrupt Service Routines](#interrupt-service-routines)
6. [Implementation](#implementation)

---

## Overview

**Interrupts** are signals that temporarily pause normal execution to handle urgent events.

### Types of Interrupts

1. **Exceptions** (CPU-generated)
   - Synchronous (triggered by executing code)
   - Examples: Divide by zero, page fault, invalid opcode
   - Error code sometimes pushed to stack

2. **Hardware Interrupts** (external devices)
   - Asynchronous (can happen anytime)
   - Examples: Keyboard press, timer tick, disk I/O complete
   - Routed through PIC (Programmable Interrupt Controller) or APIC

3. **Software Interrupts** (intentional)
   - `int N` instruction
   - Used for system calls (e.g., `int 0x80` in Linux)

### Why Interrupts Matter

Without interrupts:

```c
// Inefficient polling
while (1) {
    if (keyboard_has_data()) {
        process_keystroke();
    }
    if (disk_operation_complete()) {
        handle_disk();
    }
    // CPU wastes time checking!
}
```

With interrupts:

```c
// CPU does useful work, interrupted only when needed
while (1) {
    do_important_work();
    // Keyboard interrupt → automatically calls handler
    // Disk interrupt → automatically calls handler
}
```

💡 **Tip**: Interrupts allow efficient multitasking and responsive I/O.

---

## Interrupt Descriptor Table

The **IDT (Interrupt Descriptor Table)** tells the CPU how to handle each interrupt.

### IDT Structure

The IDT contains 256 entries (0-255), each describing an interrupt handler:

```
IDT (in memory)
├─ Entry 0: Divide by Zero Handler
├─ Entry 1: Debug Exception Handler
├─ Entry 2: NMI Handler
│  ...
├─ Entry 14: Page Fault Handler
│  ...
├─ Entry 32: Timer Interrupt Handler (IRQ0)
├─ Entry 33: Keyboard Interrupt Handler (IRQ1)
│  ...
└─ Entry 255: Custom/Unused
```

### IDT Entry Format (64-bit)

Each IDT entry is **16 bytes**:

```c
struct idt_entry {
    uint16_t off_low;       // Handler address bits 0-15
    uint16_t selector;      // Code segment (usually 0x08)
    uint8_t  ist;           // Interrupt Stack Table index (0-7)
    uint8_t  flags;         // Type and attributes
    uint16_t off_mid;       // Handler address bits 16-31
    uint32_t off_high;      // Handler address bits 32-63
    uint32_t zero;          // Reserved (must be 0)
} __attribute__((packed));
```

#### Handler Address Reconstruction

```c
uint64_t handler_address =
    ((uint64_t)entry.off_high << 32) |
    ((uint64_t)entry.off_mid << 16) |
    ((uint64_t)entry.off_low);
```

#### Flags Byte Breakdown

```
Bit 7      Bit 6-5    Bit 4  Bit 3-0
┌─────────┬─────────┬──────┬────────┐
│ Present │ DPL     │ Zero │ Type   │
└─────────┴─────────┴──────┴────────┘

Present: 1 = entry is valid
DPL (Descriptor Privilege Level): 0-3 (ring level)
Zero: Always 0
Type: Gate type (see below)
```

**Gate Types**:

- `0x5` = Task Gate (32-bit, not used in 64-bit)
- `0xE` = **Interrupt Gate (32-bit)**
- `0xF` = Trap Gate (32-bit)
- `0xC` = Call Gate (64-bit)
- `0xE` = **Interrupt Gate (64-bit)** ← We use this!
- `0xF` = Trap Gate (64-bit)

**Interrupt Gate vs Trap Gate**:

- **Interrupt Gate**: Automatically disables interrupts (clears IF flag) when called
- **Trap Gate**: Leaves interrupts enabled

💡 **Tip**: Use interrupt gates for hardware interrupts to prevent nesting. Use trap gates for exceptions that might need interrupts enabled.

#### Complete Flags Examples

```c
// Interrupt gate, Ring 0, Present
uint8_t flags = 0x8E;
// Binary: 1000 1110
//         │└─┬─┘│└─┬─┘
//         │  │  │  └── Type = 0xE (Interrupt Gate)
//         │  │  └───── Always 0
//         │  └──────── DPL = 0 (Ring 0 only)
//         └─────────── Present = 1

// Trap gate, Ring 3, Present (for syscalls)
uint8_t flags = 0xEF;
// Binary: 1110 1111
//         │└─┬─┘│└─┬─┘
//         │  │  │  └── Type = 0xF (Trap Gate)
//         │  │  └───── Always 0
//         │  └──────── DPL = 3 (User mode can trigger)
//         └─────────── Present = 1
```

### IDTR Register

The **IDTR (IDT Register)** points to the IDT:

```c
struct idt_ptr {
    uint16_t limit;         // Size of IDT - 1
    uint64_t base;          // Address of IDT
} __attribute__((packed));
```

Loading the IDTR:

```asm
lidt [idtr]                 ; Load IDT address into CPU
```

---

## CPU Exceptions

The first 32 interrupt vectors (0-31) are **reserved for CPU exceptions**.

### Exception Table

| Vector | Name                 | Error Code | Description                        |
| ------ | -------------------- | ---------- | ---------------------------------- |
| 0      | Divide Error         | No         | Division by zero or overflow       |
| 1      | Debug                | No         | Debug breakpoint or single-step    |
| 2      | NMI                  | No         | Non-Maskable Interrupt (hardware)  |
| 3      | Breakpoint           | No         | `int 3` instruction                |
| 4      | Overflow             | No         | `into` instruction                 |
| 5      | Bound Range          | No         | `bound` instruction                |
| 6      | Invalid Opcode       | No         | Undefined/illegal instruction      |
| 7      | Device Not Available | No         | FPU instruction when FPU disabled  |
| 8      | Double Fault         | Yes (0)    | Exception while handling exception |
| 9      | (Reserved)           | No         | Coprocessor Segment Overrun        |
| 10     | Invalid TSS          | Yes        | Task State Segment invalid         |
| 11     | Segment Not Present  | Yes        | Segment not in memory              |
| 12     | Stack Fault          | Yes        | Stack overflow or underflow        |
| 13     | General Protection   | Yes        | Protection violation               |
| 14     | Page Fault           | Yes        | Memory access violation            |
| 15     | (Reserved)           | No         | -                                  |
| 16     | x87 FPU Error        | No         | Floating-point exception           |
| 17     | Alignment Check      | Yes (0)    | Unaligned memory access            |
| 18     | Machine Check        | No         | Hardware error (fatal)             |
| 19     | SIMD FP Exception    | No         | SSE/AVX floating-point error       |
| 20     | Virtualization       | No         | EPT violation (VT-x)               |
| 21-29  | (Reserved)           | No         | -                                  |
| 30     | Security Exception   | Yes        | Security-sensitive operation       |
| 31     | (Reserved)           | No         | -                                  |

### Common Exceptions

#### Exception #0: Divide by Zero

Occurs when:

```c
int a = 10;
int b = 0;
int c = a / b;           // Triggers #0 exception
```

or integer overflow:

```asm
mov rax, 0x8000000000000000  ; Minimum signed 64-bit
mov rbx, -1
idiv rbx                     ; Triggers divide error
```

#### Exception #1: Debug

Used for debugging:

- Hardware breakpoints (DR0-DR3)
- Single-stepping (TF flag in RFLAGS)
- Instruction/data breakpoints

```asm
; Set trap flag for single-stepping
pushf
or qword [rsp], 1 << 8   ; Set TF bit
popf
; Now CPU will trigger debug exception after each instruction
```

#### Exception #6: Invalid Opcode

```asm
db 0xFF, 0xFF            ; Invalid instruction bytes
```

#### Exception #13: General Protection Fault

Triggered by:

- Accessing kernel memory from user mode
- Loading invalid segment selector
- Executing privileged instruction in Ring 3
- Writing to read-only segment

```c
// Example: User mode accessing kernel memory
uint64_t* kernel_addr = (uint64_t*)0xFFFFFFFF80000000;
*kernel_addr = 0x1234;   // GPF!
```

#### Exception #14: Page Fault

Most common exception! Occurs when:

- Accessing unmapped virtual address
- Writing to read-only page
- User accessing supervisor page
- Instruction fetch from non-executable page

**Error Code Format**:

```
Bit 0 (P):  0 = Not present, 1 = Protection violation
Bit 1 (W):  0 = Read, 1 = Write
Bit 2 (U):  0 = Supervisor, 1 = User mode
Bit 3 (R):  1 = Reserved bit violation
Bit 4 (I):  1 = Instruction fetch
```

**CR2 Register**: Contains faulting virtual address

Example handler:

```c
void page_fault_handler(registers_t* regs) {
    uint64_t faulting_addr;
    __asm__ volatile("mov %%cr2, %0" : "=r"(faulting_addr));

    print_str("Page Fault at 0x");
    print_hex(faulting_addr);

    if (!(regs->error_code & 0x1)) {
        print_str(" - Page not present\n");
    }
    if (regs->error_code & 0x2) {
        print_str(" - Write attempt\n");
    }
    if (regs->error_code & 0x4) {
        print_str(" - User mode\n");
    }

    kernel_panic("Unhandled page fault");
}
```

---

## Hardware Interrupts

Hardware devices signal the CPU through **IRQs (Interrupt Requests)**.

### PIC (Programmable Interrupt Controller)

The 8259 PIC routes hardware interrupts:

```
Master PIC (IRQ 0-7)     Slave PIC (IRQ 8-15)
├─ IRQ 0: Timer          ├─ IRQ 8: Real-Time Clock
├─ IRQ 1: Keyboard       ├─ IRQ 9: ACPI
├─ IRQ 2: Cascade        ├─ IRQ 10: Available
├─ IRQ 3: Serial 2       ├─ IRQ 11: Available
├─ IRQ 4: Serial 1       ├─ IRQ 12: PS/2 Mouse
├─ IRQ 5: Parallel 2/3   ├─ IRQ 13: FPU
├─ IRQ 6: Floppy         ├─ IRQ 14: Primary ATA
└─ IRQ 7: Parallel 1     └─ IRQ 15: Secondary ATA
```

### IRQ to Interrupt Vector Mapping

By default, IRQs 0-15 map to interrupts 0-15, **conflicting with CPU exceptions!**

We **remap** IRQs to interrupts 32-47:

| IRQ | Default Vector | Remapped Vector | Device             |
| --- | -------------- | --------------- | ------------------ |
| 0   | 8              | 32              | PIT Timer          |
| 1   | 9              | 33              | Keyboard           |
| 2   | 10             | 34              | Cascade (internal) |
| 14  | 22             | 46              | Primary ATA Disk   |
| 15  | 23             | 47              | Secondary ATA Disk |

### PIC Initialization (Remapping)

```c
#define PIC1_COMMAND    0x20
#define PIC1_DATA       0x21
#define PIC2_COMMAND    0xA0
#define PIC2_DATA       0xA1

void pic_remap(int offset1, int offset2) {
    // ICW1: Initialize
    outb(PIC1_COMMAND, 0x11);       // Start init sequence
    outb(PIC2_COMMAND, 0x11);

    // ICW2: Vector offsets
    outb(PIC1_DATA, offset1);       // Master offset (32)
    outb(PIC2_DATA, offset2);       // Slave offset (40)

    // ICW3: Wiring
    outb(PIC1_DATA, 0x04);          // Tell master about slave at IRQ2
    outb(PIC2_DATA, 0x02);          // Tell slave its cascade identity

    // ICW4: Mode
    outb(PIC1_DATA, 0x01);          // 8086 mode
    outb(PIC2_DATA, 0x01);

    // Clear masks (enable all IRQs)
    outb(PIC1_DATA, 0x00);
    outb(PIC2_DATA, 0x00);
}
```

### Sending EOI (End of Interrupt)

After handling interrupt, must send **EOI** to PIC:

```c
#define PIC_EOI         0x20

void pic_send_eoi(int irq) {
    if (irq >= 8) {
        outb(PIC2_COMMAND, PIC_EOI);  // Send to slave
    }
    outb(PIC1_COMMAND, PIC_EOI);      // Always send to master
}
```

⚠️ **Warning**: Forgetting EOI will prevent future interrupts from that IRQ!

---

## Interrupt Service Routines

An **ISR (Interrupt Service Routine)** handles an interrupt.

### ISR Requirements

1. **Save context** - Push all registers
2. **Handle interrupt** - Execute handler code
3. **Restore context** - Pop all registers
4. **Return** - Use `iret` instruction

### Stack Layout During Interrupt

When interrupt occurs, CPU automatically pushes:

```
Stack (grows down):
┌──────────────┐ ← RSP before interrupt
│ SS           │
│ RSP          │
│ RFLAGS       │
│ CS           │
│ RIP          │ ← RSP after CPU pushes
│ Error Code   │ (only for some exceptions)
└──────────────┘
```

In 64-bit mode, all pushes are 8 bytes.

### ISR Wrapper (Assembly)

```asm
; Macro for ISR without error code
%macro ISR_NOERR 1
global isr%1
isr%1:
    push 0              ; Dummy error code
    push %1             ; Interrupt number
    jmp isr_common
%endmacro

; Macro for ISR with error code
%macro ISR_ERR 1
global isr%1
isr%1:
    ; CPU already pushed error code
    push %1             ; Interrupt number
    jmp isr_common
%endmacro

; Common ISR handler
isr_common:
    ; Save all registers
    push rax
    push rbx
    push rcx
    push rdx
    push rsi
    push rdi
    push rbp
    push r8
    push r9
    push r10
    push r11
    push r12
    push r13
    push r14
    push r15

    ; Call C handler
    mov rdi, rsp        ; Pass pointer to register struct
    call isr_handler

    ; Restore registers
    pop r15
    pop r14
    pop r13
    pop r12
    pop r11
    pop r10
    pop r9
    pop r8
    pop rbp
    pop rdi
    pop rsi
    pop rdx
    pop rcx
    pop rbx
    pop rax

    ; Remove error code and interrupt number
    add rsp, 16

    ; Return from interrupt
    iret

; Define ISRs
ISR_NOERR 0             ; Divide by zero
ISR_NOERR 1             ; Debug
ISR_NOERR 2             ; NMI
; ... (define all 255)
ISR_ERR 8               ; Double fault
ISR_ERR 13              ; General protection fault
ISR_ERR 14              ; Page fault
```

### Register Structure

```c
typedef struct {
    uint64_t r15, r14, r13, r12, r11, r10, r9, r8;
    uint64_t rbp, rdi, rsi, rdx, rcx, rbx, rax;
    uint64_t int_no, error_code;
    uint64_t rip, cs, rflags, rsp, ss;
} registers_t;
```

### C Handler

```c
void isr_handler(registers_t* regs) {
    if (interrupt_handlers[regs->int_no]) {
        // Call registered handler
        interrupt_handlers[regs->int_no](regs);
    } else {
        print_str("Unhandled interrupt: ");
        print_dec(regs->int_no);
        print_str("\n");
    }
}
```

---

## Implementation

### Our IDT Implementation

**File: `arch/idt/init.c`**

```c
#include "arch/idt.h"

// IDT with 256 entries
struct idt_entry idt[256];

// IDTR structure
struct idt_ptr idtr;

void idt_attach_handler(int n, void* handler) {
    uint64_t handler_addr = (uint64_t)handler;

    idt[n].off_low = handler_addr & 0xFFFF;
    idt[n].selector = 0x08;         // Kernel code segment
    idt[n].ist = 0;                 // No IST
    idt[n].flags = 0x8E;            // Present, Ring 0, Interrupt Gate
    idt[n].off_mid = (handler_addr >> 16) & 0xFFFF;
    idt[n].off_high = (handler_addr >> 32) & 0xFFFFFFFF;
    idt[n].zero = 0;
}

void idt_load() {
    idtr.limit = sizeof(idt) - 1;
    idtr.base = (uint64_t)&idt;

    // Load IDTR
    __asm__ volatile("lidt %0" : : "m"(idtr));
}
```

### Handler Registration

**File: `arch/idt/idt.c`**

```c
void idt_init() {
    // Attach exception handlers
    idt_attach_handler(0, isr_divide_by_zero);
    idt_attach_handler(1, isr_debug_exception);
    // ... attach more handlers
}

// Handler for divide by zero
void divide_by_zero_handler() {
    kernel_panic("Divide by Zero Exception!");
    for (;;) __asm__ volatile("hlt");
}

// Handler for debug exception
void debug_exception_handler() {
    kernel_panic("Debug Exception!");

    // Read debug registers
    uint64_t dr0, dr1, dr2, dr3, dr6, dr7;
    __asm__ volatile(
        "mov %%dr0, %0\n"
        "mov %%dr1, %1\n"
        "mov %%dr2, %2\n"
        "mov %%dr3, %3\n"
        "mov %%dr6, %4\n"
        "mov %%dr7, %5\n"
        : "=r"(dr0), "=r"(dr1), "=r"(dr2),
          "=r"(dr3), "=r"(dr6), "=r"(dr7)
    );

    print_str("DR0-DR3: ");
    print_hex(dr0); print_str(" ");
    print_hex(dr1); print_str(" ");
    print_hex(dr2); print_str(" ");
    print_hex(dr3); print_str("\n");

    for (;;) __asm__ volatile("hlt");
}
```

### Using the IDT

**File: `kernel/kmain.c`**

```c
void kernel_main() {
    // Setup IDT
    idt_init();         // Register handlers
    idt_load();         // Load into CPU

    // Interrupts are still disabled (IF=0)
    // Enable interrupts when ready:
    __asm__ volatile("sti");

    // Now interrupts can occur!
    while (1) {
        // Main loop
    }
}
```

### Testing Exception Handlers

```c
void test_debug_exception() {
    print_str("Testing debug exception...\n");
    __asm__ volatile("int $1");  // Trigger interrupt 1
}

void test_divide_by_zero() {
    print_str("Testing divide by zero...\n");
    int x = 10 / 0;  // Will trigger exception
}
```

---

## Summary

### Interrupt Flow

1. **Event occurs** (exception, hardware IRQ, software int)
2. **CPU checks IDT** using interrupt number as index
3. **CPU saves state** (push RFLAGS, CS, RIP, error code)
4. **CPU disables interrupts** (for interrupt gates)
5. **CPU jumps to ISR** (from IDT entry)
6. **ISR saves registers** (assembly stub)
7. **ISR calls C handler** (our code)
8. **Handler processes interrupt** (e.g., read keyboard, handle fault)
9. **ISR sends EOI** (for hardware interrupts)
10. **ISR restores registers** (assembly stub)
11. **IRET** returns to interrupted code

### Key Structures

```c
// IDT Entry (16 bytes)
struct idt_entry {
    uint16_t off_low, selector;
    uint8_t ist, flags;
    uint16_t off_mid;
    uint32_t off_high, zero;
} __attribute__((packed));

// IDTR (10 bytes)
struct idt_ptr {
    uint16_t limit;
    uint64_t base;
} __attribute__((packed));
```

### Important Instructions

```asm
lidt [idtr]         ; Load IDT
int N               ; Software interrupt
iret                ; Return from interrupt
cli                 ; Disable interrupts (clear IF)
sti                 ; Enable interrupts (set IF)
```

### Common Pitfalls

⚠️ **Forgetting to send EOI** → IRQs stop working  
⚠️ **Enabling interrupts before IDT is loaded** → Triple fault  
⚠️ **Invalid IDT entry** → Triple fault  
⚠️ **Stack overflow in ISR** → Triple fault  
⚠️ **Accessing paged-out memory in ISR** → Double fault → Triple fault

---

**Next**: [05-Drivers.md](05-Drivers.md) - Hardware device drivers

---

_"Interrupts are the nervous system of your OS. Handle them well, and your OS becomes responsive and efficient."_
