# 03 - Memory Management: Allocating and Protecting Memory

## Table of Contents

1. [Overview](#overview)
2. [Physical Memory Manager](#physical-memory-manager)
3. [Virtual Memory](#virtual-memory)
4. [Kernel Heap Allocator](#kernel-heap-allocator)
5. [Memory Layout](#memory-layout)
6. [Advanced Paging](#advanced-paging)

---

## Overview

Memory management is the heart of any operating system. The OS must:

1. **Track** which memory is free and which is used
2. **Allocate** memory to processes efficiently
3. **Protect** memory from unauthorized access
4. **Reclaim** memory when programs finish
5. **Provide** virtual address spaces for isolation

### Memory Management Layers

```
Application Code
     ↓
kmalloc() / kfree()          ← Kernel Heap Allocator
     ↓
Physical Memory Manager       ← Frame Allocation (4KB chunks)
     ↓
Paging Hardware              ← MMU translates virtual → physical
     ↓
Physical RAM
```

---

## Physical Memory Manager

The **Physical Memory Manager (PMM)** tracks which physical memory frames (4KB blocks) are free or allocated.

### Why Frame-Based Allocation?

Modern CPUs use **paging**, which divides memory into fixed-size blocks:

- **Page**: 4KB virtual memory block
- **Frame**: 4KB physical memory block
- Page tables map pages to frames

**Frame size = Page size = 4KB** (on x86_64)

### Bitmap Allocator Design

Our PMM uses a **bitmap** where each bit represents one frame:

- Bit = 0: Frame is **free**
- Bit = 1: Frame is **allocated**

```
Bit:    0  1  2  3  4  5  6  7  ...
Frame:  ── ▓▓ ── ▓▓ ▓▓ ── ── ▓▓  ...
State:  F  A  F  A  A  F  F  A   ...

F = Free, A = Allocated
```

#### Memory Calculation

For 1 GB of RAM:

- Frames: `1 GB / 4 KB = 1,073,741,824 / 4,096 = 262,144 frames`
- Bitmap size: `262,144 bits / 8 = 32,768 bytes = 32 KB`

💡 **Tip**: Bitmap is very memory-efficient! Only 32 KB to track 1 GB.

### PMM Implementation

**File: `drivers/memory.c`**

#### Data Structures

```c
#define FRAME_SIZE 4096        // 4KB per frame

// Bitmap to track frames (each bit = one frame)
static uint64_t* pmm_bitmap = NULL;

// Total number of frames
static uint64_t pmm_total_frames = 0;

// Number of free frames
static uint64_t pmm_free_frames = 0;
```

#### Initialization

```c
void pmm_init(uint64_t mem_size, uint64_t* bitmap) {
    pmm_bitmap = bitmap;
    pmm_total_frames = mem_size / FRAME_SIZE;
    pmm_free_frames = pmm_total_frames;

    // Clear bitmap (all frames initially free)
    uint64_t bitmap_size = (pmm_total_frames + 63) / 64;
    for (uint64_t i = 0; i < bitmap_size; i++) {
        pmm_bitmap[i] = 0;
    }
}
```

📝 **Note**: `(pmm_total_frames + 63) / 64` rounds up to nearest multiple of 64.

#### Bitmap Manipulation

```c
// Mark frame as allocated
static void pmm_set_frame(uint64_t frame_num) {
    uint64_t index = frame_num / 64;      // Which uint64_t?
    uint64_t bit = frame_num % 64;        // Which bit?
    pmm_bitmap[index] |= (1ULL << bit);   // Set bit
}

// Mark frame as free
static void pmm_clear_frame(uint64_t frame_num) {
    uint64_t index = frame_num / 64;
    uint64_t bit = frame_num % 64;
    pmm_bitmap[index] &= ~(1ULL << bit);  // Clear bit
}

// Check if frame is allocated
static int pmm_test_frame(uint64_t frame_num) {
    uint64_t index = frame_num / 64;
    uint64_t bit = frame_num % 64;
    return (pmm_bitmap[index] & (1ULL << bit)) != 0;
}
```

🔍 **Deep Dive**: Using `uint64_t` means each array element tracks 64 frames.

Example:

```
pmm_bitmap[0]  → Tracks frames 0-63
pmm_bitmap[1]  → Tracks frames 64-127
pmm_bitmap[2]  → Tracks frames 128-191
...
```

#### Frame Allocation

```c
void* pmm_alloc_frame() {
    if (pmm_free_frames == 0) {
        return NULL;  // Out of memory!
    }

    // Find first free frame
    for (uint64_t frame = 0; frame < pmm_total_frames; frame++) {
        if (!pmm_test_frame(frame)) {
            pmm_set_frame(frame);
            pmm_free_frames--;
            return (void*)(frame * FRAME_SIZE);
        }
    }

    return NULL;
}
```

⚠️ **Warning**: This is O(n) in worst case. Production PMMs use faster algorithms (buddy system, slab allocator).

#### Frame Deallocation

```c
void pmm_free_frame(void* frame) {
    uint64_t frame_addr = (uint64_t)frame;

    // Ensure alignment
    if (frame_addr % FRAME_SIZE != 0) {
        return;  // Invalid!
    }

    uint64_t frame_num = frame_addr / FRAME_SIZE;

    // Ensure valid frame number
    if (frame_num >= pmm_total_frames) {
        return;
    }

    // Only free if currently allocated
    if (pmm_test_frame(frame_num)) {
        pmm_clear_frame(frame_num);
        pmm_free_frames++;
    }
}
```

### Memory Information Display

```c
void meminfo() {
    print_str("Memory Information:\n");
    print_str("- Total Frames: ");
    print_dec(pmm_total_frames);
    print_str("\n- Free Frames: ");
    print_dec(pmm_free_frames);
    print_str("\n");

    uint64_t total_allocated = pmm_total_frames - pmm_free_frames;
    print_str("- Total Allocated Frames: ");
    print_dec(total_allocated);
    print_str("\n");

    uint64_t memory_usage = (total_allocated * 100) / pmm_total_frames;
    print_str("- Memory Usage: ");
    print_dec(memory_usage);
    print_str("%\n");
}
```

---

## Virtual Memory

**Virtual memory** provides each process with its own isolated address space.
![[Pasted image 20260210000239.png]]
![[Pasted image 20260210000325.png]]

### Benefits

1. **Isolation**: Process A can't access Process B's memory
2. **Simplicity**: Each process thinks it has full address space
3. **Flexibility**: Virtual addresses don't need contiguous physical memory
4. **Protection**: Kernel memory protected from user processes
5. **Swapping**: Rarely-used pages moved to disk

### Virtual vs Physical

```
Process A                 Process B
Virtual   Physical       Virtual   Physical
0x1000 → 0x5000          0x1000 → 0x8000
0x2000 → 0x6000          0x2000 → 0x9000
0x3000 → 0x7000          0x3000 → 0xA000

Same virtual addresses, different physical addresses!
```

### Page Fault Handling

A **page fault** occurs when:

1. Accessing unmapped virtual address
2. Writing to read-only page
3. User accessing kernel page
4. Page is swapped to disk (advanced OSes)

The CPU raises **exception #14** (page fault):

- CR2 register contains faulting virtual address
- Error code on stack describes fault type

#### Page Fault Error Code

```
Bit 0 (P):  0 = Not present, 1 = Protection violation
Bit 1 (W):  0 = Read, 1 = Write
Bit 2 (U):  0 = Supervisor, 1 = User mode
Bit 3 (R):  1 = Reserved bit set
Bit 4 (I):  1 = Instruction fetch
```

#### Example Page Fault Handler

```c
void page_fault_handler(registers_t* regs) {
    uint64_t faulting_address;
    __asm__ volatile("mov %%cr2, %0" : "=r"(faulting_address));

    int present = regs->error_code & 0x1;
    int write = regs->error_code & 0x2;
    int user = regs->error_code & 0x4;

    print_str("Page Fault! Address: ");
    print_hex(faulting_address);
    print_str("\n");

    if (!present) {
        print_str("Cause: Page not present\n");
    }
    if (write) {
        print_str("Cause: Write operation\n");
    }
    if (user) {
        print_str("Cause: User mode access\n");
    }

    // TODO: Handle page fault (allocate page, load from disk, etc.)
    kernel_panic("Unhandled page fault");
}
```

### Higher-Half Kernel

Many kernels use **higher-half** mapping:

```
Virtual Address Space (per process):
┌─────────────────────┐ 0xFFFFFFFFFFFFFFFF
│ Kernel (High Half)  │ ← Shared across all processes
│ (0xFFFF800000000000 │
│  and above)         │
├─────────────────────┤ 0x00007FFFFFFFFFFF
│ Unused (Sign Ext)   │
├─────────────────────┤ 0x0000800000000000
│ User Space          │ ← Unique per process
│ (0x0 - 0x00007FFF..)│
└─────────────────────┘ 0x0000000000000000
```

Benefits:

- Kernel always mapped at high addresses
- Context switch only changes user portion
- No need to reload kernel pages

💡 **Tip**: Our simple OS uses **identity mapping** (virtual = physical) for simplicity.

---

## Kernel Heap Allocator

The **kernel heap** provides dynamic memory allocation (`kmalloc` / `kfree`).

### Bump Allocator (Simple)

Our implementation uses a **bump allocator**:

- Start at `heap_start`
- Allocate by moving pointer forward
- Never frees memory! (for simplicity)

```c
static void* heap_current = 0;
static void* heap_start = 0;
static size_t total_heap_allocated = 0;

void kheap_init(void* heap_start_addr) {
    heap_start = heap_start_addr;
    heap_current = heap_start_addr;
    total_heap_allocated = 0;
}

void* kmalloc(size_t size) {
    if (size == 0) {
        return NULL;
    }

    // Align to 8-byte boundary
    size = (size + 7) & ~7;

    void* allocated = heap_current;
    heap_current = (void*)((uint64_t)heap_current + size);
    total_heap_allocated += size;

    return allocated;
}
```

📝 **Note**: `(size + 7) & ~7` rounds up to next multiple of 8 for alignment.

Examples:

- `size = 5` → `(5 + 7) & ~7 = 12 & ~7 = 8`
- `size = 9` → `(9 + 7) & ~7 = 16 & ~7 = 16`
- `size = 16` → `(16 + 7) & ~7 = 23 & ~7 = 16`

### Better Allocators

Production kernels use sophisticated allocators:

#### 1. Free List Allocator

- Maintains list of free blocks
- Can reuse freed memory
- Fragmentation issues

```
Heap:
┌────┬────┬────┬────┬────┐
│Used│Free│Used│Free│Used│
└────┴────┴────┴────┴────┘
      ↓         ↓
   Free List: [────]→[────]→NULL
```

#### 2. Slab Allocator

- Pre-allocates objects of common sizes
- Very fast allocation/deallocation
- Low fragmentation
- Used by Linux kernel

```
Slabs:
16-byte objects:  [○][○][●][○][●] ← ● = allocated, ○ = free
32-byte objects:  [●][○][●][●][○]
64-byte objects:  [○][●][○][○][○]
```

#### 3. Buddy System

- Splits memory into power-of-2 sizes
- Easy merging of adjacent free blocks
- Some internal fragmentation

```
1MB Block
├─ 512KB [Free]
└─ 512KB
   ├─ 256KB [Used]
   └─ 256KB
      ├─ 128KB [Used]
      └─ 128KB [Free]
```

### Memory Utility Functions

#### memcpy - Copy Memory

```c
void* memcpy(void* dest, const void* src, size_t n) {
    unsigned char* d = (unsigned char*)dest;
    const unsigned char* s = (const unsigned char*)src;

    while (n--) {
        *d++ = *s++;
    }

    return dest;
}
```

Usage:

```c
char buffer[100];
memcpy(buffer, "Hello", 5);  // Copy "Hello" to buffer
```

#### memset - Fill Memory

```c
void* memset(void* s, int c, size_t n) {
    unsigned char* p = (unsigned char*)s;

    while (n--) {
        *p++ = (unsigned char)c;
    }

    return s;
}
```

Usage:

```c
char buffer[100];
memset(buffer, 0, 100);      // Zero out buffer
memset(buffer, 'A', 10);     // Fill first 10 bytes with 'A'
```

#### memcmp - Compare Memory

```c
int memcmp(const void* s1, const void* s2, size_t n) {
    const unsigned char* p1 = (const unsigned char*)s1;
    const unsigned char* p2 = (const unsigned char*)s2;

    while (n--) {
        if (*p1 != *p2) {
            return *p1 - *p2;
        }
        p1++;
        p2++;
    }

    return 0;
}
```

Returns:

- `< 0` if s1 < s2
- `0` if s1 == s2
- `> 0` if s1 > s2

---

## Memory Layout

Understanding the memory layout is crucial for OS development.

### Physical Memory Map (Example)

```
0x00000000 - 0x000003FF  (1 KB)     Real Mode IVT (Interrupt Vector Table)
0x00000400 - 0x000004FF  (256 B)    BIOS Data Area
0x00000500 - 0x00007BFF  (~30 KB)   Usable
0x00007C00 - 0x00007DFF  (512 B)    Bootloader (MBR)
0x00007E00 - 0x0007FFFF  (~480 KB)  Usable
0x00080000 - 0x0009FFFF  (128 KB)   BIOS Extended Data Area
0x000A0000 - 0x000BFFFF  (128 KB)   Video RAM (VGA)
0x000C0000 - 0x000FFFFF  (256 KB)   ROM Area (BIOS)
0x00100000 - ...         (...)      Extended Memory (our kernel starts here!)
```

💡 **Tip**: We load kernel at `0x100000` (1 MB) to avoid low memory conflicts.

### Virtual Memory Layout (Our OS)

```
Virtual = Physical (Identity Mapped)

0x00000000 ┌─────────────────────────┐
           │ BIOS & Low Memory       │ (0-1MB)
0x00100000 ├─────────────────────────┤
           │ Kernel Code (.text)     │
           ├─────────────────────────┤
           │ Kernel Data (.data)     │
           ├─────────────────────────┤
           │ Kernel BSS (.bss)       │
0x00200000 ├─────────────────────────┤
           │ Kernel Stack            │ (grows down)
           ├─────────────────────────┤
0x00400000 │ Kernel Heap             │ (grows up)
           ├─────────────────────────┤
           │ Free Memory             │
           ├─────────────────────────┤
           │ Memory-Mapped I/O       │
0x40000000 └─────────────────────────┘ (1GB, end of our mapping)
```

### Linker Script Control

The **linker script** defines memory layout:

**File: `targets/x86_64/linker.ld`**

```ld
ENTRY(start)

SECTIONS
{
  . = 1M;                    /* Start at 1MB */

  .boot :
  {
    KEEP(*(.multiboot_header))  /* Multiboot header first */
  }

  .text ALIGN(4K) :
  {
    *(.text)                 /* Code */
  }

  .rodata ALIGN(4K) :
  {
    *(.rodata)               /* Read-only data */
  }

  .data ALIGN(4K) :
  {
    *(.data)                 /* Initialized data */
  }

  .bss ALIGN(4K) :
  {
    *(COMMON)                /* Uninitialized data */
    *(.bss)
  }

  /* End of kernel marker */
  kernel_end = .;
}
```

🔍 **Deep Dive**: `ALIGN(4K)` ensures section starts at 4KB boundary (page-aligned).

### Getting Kernel Size

```c
extern char kernel_end;      // Defined in linker script

void print_kernel_size() {
    uint64_t kernel_start = 0x100000;  // 1MB
    uint64_t kernel_size = (uint64_t)&kernel_end - kernel_start;

    print_str("Kernel size: ");
    print_dec(kernel_size);
    print_str(" bytes\n");
}
```

---

## Advanced Paging

### Write-Protected Pages

Mark kernel code as read-only:

```c
void protect_kernel_code() {
    uint64_t kernel_start = 0x100000;
    uint64_t kernel_code_end = (uint64_t)&_text_end;  // From linker script

    for (uint64_t addr = kernel_start; addr < kernel_code_end; addr += 0x1000) {
        uint64_t* pte = get_page_table_entry(addr);
        *pte &= ~0x2;  // Clear writable bit
    }
}
```

### No-Execute (NX) Bit

Prevent code execution in data pages:

```c
void set_nx_bit(uint64_t virtual_addr) {
    uint64_t* pte = get_page_table_entry(virtual_addr);
    *pte |= (1ULL << 63);  // Set NX bit (bit 63)
}
```

⚠️ **Warning**: Must enable NX in EFER MSR first:

```asm
mov ecx, 0xC0000080       ; EFER MSR
rdmsr
or eax, 1 << 11           ; Set NXE bit
wrmsr
```

### Copy-on-Write (COW)

Optimize `fork()` by sharing pages:

1. Mark page as read-only in both processes
2. On write attempt → page fault
3. Allocate new physical frame
4. Copy data to new frame
5. Update page table to point to new frame
6. Mark as writable

```c
void handle_cow_page_fault(uint64_t virtual_addr) {
    // Allocate new frame
    void* new_frame = pmm_alloc_frame();

    // Copy data from old page
    void* old_page = (void*)(virtual_addr & ~0xFFF);
    memcpy(new_frame, old_page, 4096);

    // Update page table
    uint64_t* pte = get_page_table_entry(virtual_addr);
    uint64_t old_frame = *pte & ~0xFFF;
    *pte = ((uint64_t)new_frame & ~0xFFF) | 0x3;  // Present + Writable

    // Invalidate TLB
    __asm__ volatile("invlpg (%0)" :: "r"(virtual_addr) : "memory");

    // Decrement reference count on old frame
    frame_decref(old_frame);
}
```

### Demand Paging

Allocate pages only when accessed (lazy allocation):

```c
void* lazy_alloc(size_t size) {
    size_t pages = (size + 4095) / 4096;
    void* virtual_addr = find_free_virtual_range(pages);

    // Don't allocate physical frames yet!
    // Just mark as "allocatable" in metadata
    mark_range_allocatable(virtual_addr, pages);

    return virtual_addr;
}

// Page fault handler allocates on-demand
void page_fault_handler(uint64_t addr) {
    if (is_allocatable(addr)) {
        void* frame = pmm_alloc_frame();
        map_page(addr, frame, PAGE_PRESENT | PAGE_WRITABLE);
    } else {
        // Real page fault
        kernel_panic("Invalid memory access");
    }
}
```

---

## Summary

### Memory Management Layers

1. **Physical Memory Manager** (PMM)
   - Tracks free/allocated frames (4KB chunks)
   - Uses bitmap for efficiency
   - `pmm_alloc_frame()` / `pmm_free_frame()`

2. **Virtual Memory** (Paging)
   - 4-level page tables translate virtual → physical
   - Isolation between processes
   - Protection (R/W/X permissions)

3. **Kernel Heap Allocator**
   - Dynamic allocation for kernel
   - `kmalloc()` / `kfree()` (simplified)
   - Production uses slab/buddy allocators

### Key Formulas

```c
// Frame number from address
frame_num = address / FRAME_SIZE

// Bitmap index
index = frame_num / 64
bit = frame_num % 64

// Alignment (round up to multiple of N)
aligned = (value + (N - 1)) & ~(N - 1)

// Page-align address
page_aligned = address & ~0xFFF  // Clear bottom 12 bits
```

### Common Operations

```c
// Allocate 1 page
void* frame = pmm_alloc_frame();

// Allocate from heap
char* buffer = kmalloc(1024);

// Zero memory
memset(buffer, 0, 1024);

// Copy memory
memcpy(dest, src, size);

// Free frame
pmm_free_frame(frame);
```

---

**Next**: [04-Interrupts-IDT.md](04-Interrupts-IDT.md) - Handling CPU exceptions and interrupts

---

_"Memory is the foundation of computing. Manage it well, and your OS will stand strong."_
