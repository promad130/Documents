# 09 - Advanced Topics: Beyond the Basics

## Table of Contents

1. [Multitasking and Scheduling](#multitasking-and-scheduling)
2. [Virtual Memory Advanced](#virtual-memory-advanced)
3. [System Calls](#system-calls)
4. [Symmetric Multiprocessing (SMP)](#symmetric-multiprocessing-smp)
5. [Advanced Filesystems](#advanced-filesystems)
6. [Networking](#networking)
7. [Security and Isolation](#security-and-isolation)
8. [Performance Optimization](#performance-optimization)

---

## Multitasking and Scheduling

### Process Management

A **process** is an executing program with its own:

- Address space
- CPU state (registers)
- Open files
- Permissions

### Process Control Block (PCB)

```c
typedef struct process {
    uint64_t pid;                   // Process ID
    uint64_t parent_pid;            // Parent process ID

    // CPU state
    uint64_t rip;                   // Instruction pointer
    uint64_t rsp;                   // Stack pointer
    uint64_t rflags;                // Flags register
    uint64_t cr3;                   // Page table base

    // General purpose registers
    uint64_t rax, rbx, rcx, rdx;
    uint64_t rsi, rdi, rbp;
    uint64_t r8, r9, r10, r11, r12, r13, r14, r15;

    // Process state
    enum {
        PROCESS_READY,
        PROCESS_RUNNING,
        PROCESS_BLOCKED,
        PROCESS_ZOMBIE
    } state;

    // Scheduling
    uint32_t priority;
    uint64_t time_slice;
    uint64_t total_time;

    // Memory
    uint64_t* page_table;
    uint64_t heap_start;
    uint64_t heap_end;

    // Linked list
    struct process* next;
} process_t;
```

### Context Switch

Saving and restoring CPU state:

```c
void context_switch(process_t* old, process_t* new) {
    // Save old process state
    save_context(old);

    // Switch page tables
    __asm__ volatile("mov %0, %%cr3" :: "r"(new->cr3));

    // Load new process state
    restore_context(new);
}

// In assembly
save_context:
    mov [rdi + OFFSET_RAX], rax
    mov [rdi + OFFSET_RBX], rbx
    mov [rdi + OFFSET_RCX], rcx
    ; ... save all registers
    mov rax, cr3
    mov [rdi + OFFSET_CR3], rax
    ret

restore_context:
    mov rax, [rdi + OFFSET_CR3]
    mov cr3, rax
    mov rax, [rdi + OFFSET_RAX]
    mov rbx, [rdi + OFFSET_RBX]
    ; ... restore all registers
    ret
```

### Scheduling Algorithms

#### 1. Round Robin

Each process gets equal time slice:

```c
process_t* round_robin_schedule(process_t* current) {
    // Move to next process
    if (current && current->next) {
        return current->next;
    }

    // Wrap to beginning of list
    return process_list_head;
}
```

**Pros**: Fair, simple  
**Cons**: No priority, poor for I/O-bound tasks

#### 2. Priority Scheduling

Higher priority processes run first:

```c
process_t* priority_schedule() {
    process_t* highest = NULL;
    uint32_t max_priority = 0;

    process_t* p = process_list_head;
    while (p) {
        if (p->state == PROCESS_READY && p->priority > max_priority) {
            highest = p;
            max_priority = p->priority;
        }
        p = p->next;
    }

    return highest;
}
```

**Pros**: Important tasks run first  
**Cons**: Starvation (low priority never runs)

#### 3. Multi-Level Feedback Queue (MLFQ)

Dynamic priority based on behavior:

```c
#define NUM_QUEUES 4

typedef struct {
    process_t* queue[NUM_QUEUES];
    uint64_t time_quantum[NUM_QUEUES];
} mlfq_t;

process_t* mlfq_schedule(mlfq_t* mlfq) {
    // Check highest priority queue first
    for (int i = NUM_QUEUES - 1; i >= 0; i--) {
        if (mlfq->queue[i]) {
            return mlfq->queue[i];
        }
    }
    return NULL;
}

void mlfq_demote(process_t* p, mlfq_t* mlfq) {
    // If process used full time slice, demote to lower queue
    if (p->priority > 0) {
        p->priority--;
    }
}

void mlfq_boost(mlfq_t* mlfq) {
    // Periodically boost all processes to prevent starvation
    process_t* p = process_list_head;
    while (p) {
        p->priority = NUM_QUEUES - 1;
        p = p->next;
    }
}
```

**Pros**: Adapts to process behavior, prevents starvation  
**Cons**: Complex

### Timer Interrupt

For preemptive multitasking:

```c
#define PIT_FREQUENCY 1193182
#define TIMER_HZ 100  // 100 Hz = 10ms per tick

void timer_init(uint32_t frequency) {
    uint32_t divisor = PIT_FREQUENCY / frequency;

    outb(0x43, 0x36);  // Command: square wave mode
    outb(0x40, divisor & 0xFF);
    outb(0x40, (divisor >> 8) & 0xFF);
}

void timer_irq_handler() {
    // Increment tick count
    timer_ticks++;

    // Decrement time slice
    if (current_process) {
        current_process->time_slice--;

        if (current_process->time_slice == 0) {
            // Time slice expired, schedule new process
            process_t* next = scheduler_pick_next();
            context_switch(current_process, next);
        }
    }

    // Send EOI
    outb(0x20, 0x20);
}
```

---

## Virtual Memory Advanced

### Demand Paging

Allocate physical memory only when accessed:

```c
void* mmap_lazy(size_t size) {
    size_t pages = (size + 4095) / 4096;
    void* virt_addr = find_free_virtual_range(pages);

    // Mark pages as "allocatable" but don't allocate yet
    for (size_t i = 0; i < pages; i++) {
        uint64_t addr = (uint64_t)virt_addr + (i * 4096);
        mark_page_allocatable(addr);
    }

    return virt_addr;
}

void page_fault_handler(uint64_t fault_addr, uint64_t error_code) {
    if (is_page_allocatable(fault_addr)) {
        // Allocate page on demand
        void* phys_page = pmm_alloc_frame();
        map_page(fault_addr & ~0xFFF, phys_page,
                 PAGE_PRESENT | PAGE_WRITABLE | PAGE_USER);
        return;
    }

    // Real page fault
    kernel_panic("Invalid memory access");
}
```

### Copy-on-Write (COW)

Share memory until write:

```c
int fork() {
    process_t* child = create_process_copy(current_process);

    // Mark all pages as read-only and COW
    for (uint64_t addr = USER_SPACE_START; addr < USER_SPACE_END;
         addr += 4096) {
        uint64_t* pte = get_page_table_entry(addr);
        if (*pte & PAGE_PRESENT) {
            // Mark read-only
            *pte &= ~PAGE_WRITABLE;
            // Set COW flag (use available bit)
            *pte |= PAGE_COW;

            // Increment reference count
            frame_incref(*pte & ~0xFFF);
        }
    }

    return child->pid;
}

void cow_page_fault_handler(uint64_t fault_addr) {
    uint64_t* pte = get_page_table_entry(fault_addr);

    if (*pte & PAGE_COW) {
        uint64_t old_frame = *pte & ~0xFFF;

        // Allocate new frame
        void* new_frame = pmm_alloc_frame();

        // Copy data
        memcpy(new_frame, (void*)(fault_addr & ~0xFFF), 4096);

        // Update page table
        *pte = ((uint64_t)new_frame & ~0xFFF) |
               PAGE_PRESENT | PAGE_WRITABLE | PAGE_USER;

        // Decrement reference count on old frame
        frame_decref(old_frame);

        // Invalidate TLB
        invlpg(fault_addr);
    }
}
```

### Memory-Mapped Files

Map file contents to virtual memory:

```c
void* mmap_file(const char* path, size_t offset, size_t length) {
    // Open file
    file_t* file = open_file(path);
    if (!file) return NULL;

    // Find free virtual address range
    void* virt_addr = find_free_virtual_range(
        (length + 4095) / 4096
    );

    // Create VMA (Virtual Memory Area)
    vma_t* vma = create_vma(virt_addr, length);
    vma->type = VMA_FILE;
    vma->file = file;
    vma->file_offset = offset;

    // Pages loaded on demand via page fault
    return virt_addr;
}

void mmap_page_fault(uint64_t fault_addr) {
    vma_t* vma = find_vma(fault_addr);
    if (vma && vma->type == VMA_FILE) {
        // Allocate physical page
        void* page = pmm_alloc_frame();

        // Calculate file offset
        size_t page_offset = (fault_addr - vma->start) + vma->file_offset;

        // Read from file
file_read(vma->file, page, page_offset, 4096);

        // Map page
        map_page(fault_addr & ~0xFFF, page,
                 PAGE_PRESENT | PAGE_WRITABLE | PAGE_USER);
    }
}
```

---

## System Calls

### User Space to Kernel Space Transition

#### Method 1: Software Interrupt (`int 0x80`)

```asm
; User space code
mov rax, 1          ; System call number (write)
mov rdi, 1          ; File descriptor (stdout)
mov rsi, message    ; Buffer
mov rdx, 13         ; Length
int 0x80            ; Trigger system call
```

```c
// Kernel handler
void syscall_handler(registers_t* regs) {
    uint64_t syscall_num = regs->rax;

    switch (syscall_num) {
        case 0:  // read
            regs->rax = sys_read(regs->rdi, regs->rsi, regs->rdx);
            break;
        case 1:  // write
            regs->rax = sys_write(regs->rdi, regs->rsi, regs->rdx);
            break;
        case 2:  // open
            regs->rax = sys_open(regs->rdi, regs->rsi);
            break;
        // ... more syscalls
    }
}
```

#### Method 2: SYSCALL/SYSRET (Fast, x86_64)

```c
void syscall_init() {
    // Enable SYSCALL/SYSRET in EFER
    uint64_t efer = rdmsr(0xC0000080);
    efer |= 1;  // SCE bit
    wrmsr(0xC0000080, efer);

    // Set STAR MSR (segment selectors)
    wrmsr(0xC0000081,
          0x0013000800000000ULL);  // User CS/SS, Kernel CS/SS

    // Set LSTAR MSR (syscall entry point)
    wrmsr(0xC0000082, (uint64_t)syscall_entry);

    // Set SFMASK MSR (RFLAGS mask)
    wrmsr(0xC0000084, 0x200);  // Clear IF
}

// In assembly
syscall_entry:
    swapgs              ; Swap GS base (kernel vs user)

    ; Save user stack
    mov [gs:0], rsp

    ; Load kernel stack
    mov rsp, [gs:8]

    ; Save registers
    push rcx            ; RIP
    push r11            ; RFLAGS

    ; Call C handler
    call syscall_handler

    ; Restore registers
    pop r11
    pop rcx

    ; Restore user stack
    mov rsp, [gs:0]

    swapgs
    sysretq
```

### System Call Interface

```c
// In user space library
long syscall(long num, long arg1, long arg2, long arg3) {
    long ret;
    __asm__ volatile(
        "syscall"
        : "=a"(ret)
        : "a"(num), "D"(arg1), "S"(arg2), "d"(arg3)
        : "rcx", "r11", "memory"
    );
    return ret;
}

// Wrapper functions
ssize_t write(int fd, const void* buf, size_t count) {
    return syscall(SYS_WRITE, fd, (long)buf, count);
}

int open(const char* path, int flags) {
    return syscall(SYS_OPEN, (long)path, flags, 0);
}
```

---

## Symmetric Multiprocessing (SMP)

### Detecting CPUs

```c
#define CPUID_FEAT_EDX_APIC (1 << 9)

int detect_cpus() {
    uint32_t eax, ebx, ecx, edx;

    // Check for APIC
    __cpuid(1, eax, ebx, ecx, edx);
    if (!(edx & CPUID_FEAT_EDX_APIC)) {
        return 1;  // No APIC, single CPU
    }

    // Read APIC ID and count
    uint32_t* lapic = (uint32_t*)0xFEE00000;  // LAPIC base
    uint32_t apic_id = (lapic[0x20 / 4] >> 24) & 0xFF;

    // Parse MADT (Multiple APIC Description Table) from ACPI
    // to find all CPUs

    return cpu_count;
}
```

### Starting Application Processors (APs)

```c
void start_ap(uint8_t apic_id) {
    // Prepare startup code at 0x8000
    memcpy((void*)0x8000, ap_startup_code, ap_startup_code_size);

    // Send INIT IPI
    lapic_send_ipi(apic_id, 0x4500);
    sleep_ms(10);

    // Send STARTUP IPI
    lapic_send_ipi(apic_id, 0x4608);  // 0x08 = page at 0x8000
    sleep_ms(1);

    // Send second STARTUP IPI
    lapic_send_ipi(apic_id, 0x4608);
}

// AP startup code (16-bit real mode)
ap_startup:
    cli
    cld

    ; Setup protected mode
    lgdt [gdt_descriptor]
    mov eax, cr0
    or eax, 1
    mov cr0, eax

    jmp 0x08:ap_protected_mode

ap_protected_mode:
    ; Setup long mode (same as BSP)
    ; ...

    ; Jump to AP main
    jmp ap_main
```

### Per-CPU Data

```c
typedef struct {
    uint8_t cpu_id;
    process_t* current_process;
    uint64_t kernel_stack;
    uint64_t tss_rsp0;
    // ... more per-CPU data
} cpu_local_t;

// Use GS register for per-CPU data
#define CPU_LOCAL ((cpu_local_t*)0xFFFF800000000000)

void setup_cpu_local(uint8_t cpu_id) {
    cpu_local_t* local = kmalloc(sizeof(cpu_local_t));
    local->cpu_id = cpu_id;

    // Set GS base
    wrmsr(0xC0000101, (uint64_t)local);
}

// Access per-CPU data
void scheduler_tick() {
    process_t* current = CPU_LOCAL->current_process;
    // ...
}
```

### Spinlocks

```c
typedef struct {
    volatile uint32_t locked;
} spinlock_t;

void spinlock_acquire(spinlock_t* lock) {
    // Atomic test-and-set
    while (__sync_lock_test_and_set(&lock->locked, 1)) {
        __asm__ volatile("pause");  // Hint to CPU
    }
}

void spinlock_release(spinlock_t* lock) {
    __sync_lock_release(&lock->locked);
}

// Usage
spinlock_t process_list_lock = {0};

void add_process(process_t* p) {
    spinlock_acquire(&process_list_lock);

    // Critical section
    p->next = process_list_head;
    process_list_head = p;

    spinlock_release(&process_list_lock);
}
```

---

## Advanced Filesystems

### ext2 Filesystem

Modern, journaling-ready filesystem:

```c
typedef struct {
    uint32_t s_inodes_count;        // Total inodes
    uint32_t s_blocks_count;        // Total blocks
    uint32_t s_r_blocks_count;      // Reserved blocks
    uint32_t s_free_blocks_count;   // Free blocks
    uint32_t s_free_inodes_count;   // Free inodes
    uint32_t s_first_data_block;    // First data block
    uint32_t s_log_block_size;      // Block size (1024 << size)
    // ... more fields
} ext2_superblock_t;

typedef struct {
    uint16_t i_mode;                // File mode
    uint16_t i_uid;                 // Owner UID
    uint32_t i_size;                // Size in bytes
    uint32_t i_atime;               // Access time
    uint32_t i_ctime;               // Creation time
    uint32_t i_mtime;               // Modification time
    uint32_t i_dtime;               // Deletion time
    uint16_t i_gid;                 // Group ID
    uint16_t i_links_count;         // Hard links
    uint32_t i_blocks;              // Blocks count
    uint32_t i_block[15];           // Pointers to blocks
} ext2_inode_t;
```

### Virtual Filesystem (VFS)

Abstract filesystem interface:

```c
typedef struct filesystem {
    const char* name;

    int (*mount)(struct filesystem* fs, const char* device);
    int (*unmount)(struct filesystem* fs);

    file_t* (*open)(const char* path, int flags);
    void (*close)(file_t* file);

    ssize_t (*read)(file_t* file, void* buf, size_t count);
    ssize_t (*write)(file_t* file, const void* buf, size_t count);

    int (*mkdir)(const char* path, mode_t mode);
    int (*rmdir)(const char* path);
} filesystem_t;

// Register filesystems
filesystem_t* filesystems[] = {
    &fat32_fs,
    &ext2_fs,
    &iso9660_fs,
    NULL
};

// Mount filesystem
int vfs_mount(const char* device, const char* mountpoint,
              const char* fstype) {
    for (int i = 0; filesystems[i]; i++) {
        if (strcmp(filesystems[i]->name, fstype) == 0) {
            return filesystems[i]->mount(filesystems[i], device);
        }
    }
    return -1;
}
```

---

## Networking

### Network Stack Layers

```
Application Layer      │ HTTP, FTP, SSH
Transport Layer        │ TCP, UDP
Network Layer          │ IP, ICMP
Data Link Layer        │ Ethernet, ARP
Physical Layer         │ Network Card Driver
```

### Ethernet Frame

```c
typedef struct {
    uint8_t dest_mac[6];
    uint8_t src_mac[6];
    uint16_t ethertype;         // 0x0800 = IPv4, 0x0806 = ARP
    uint8_t payload[];
} __attribute__((packed)) ethernet_frame_t;
```

### IP Packet

```c
typedef struct {
    uint8_t version_ihl;        // Version (4) | Header length
    uint8_t tos;                // Type of service
    uint16_t total_length;
    uint16_t identification;
    uint16_t flags_fragment;
    uint8_t ttl;                // Time to live
    uint8_t protocol;           // 6 = TCP, 17 = UDP
    uint16_t checksum;
    uint32_t src_ip;
    uint32_t dest_ip;
    uint8_t payload[];
} __attribute__((packed)) ip_header_t;
```

### Socket API

```c
typedef struct {
    int domain;                 // AF_INET, AF_INET6
    int type;                   // SOCK_STREAM, SOCK_DGRAM
    int protocol;               // IPPROTO_TCP, IPPROTO_UDP

    uint32_t local_ip;
    uint16_t local_port;
    uint32_t remote_ip;
    uint16_t remote_port;

    // Buffers
    ring_buffer_t* rx_buffer;
    ring_buffer_t* tx_buffer;
} socket_t;

int socket(int domain, int type, int protocol);
int bind(int sockfd, const struct sockaddr* addr, socklen_t addrlen);
int listen(int sockfd, int backlog);
int accept(int sockfd, struct sockaddr* addr, socklen_t* addrlen);
int connect(int sockfd, const struct sockaddr* addr, socklen_t addrlen);
ssize_t send(int sockfd, const void* buf, size_t len, int flags);
ssize_t recv(int sockfd, void* buf, size_t len, int flags);
```

---

## Security and Isolation

### Address Space Layout Randomization (ASLR)

Randomize memory addresses:

```c
void* aslr_allocate(size_t size) {
    // Get random offset
    uint64_t random = get_random_number();
    uint64_t offset = random & 0x0FFFFFF000;  // Random page offset

    // Allocate at randomized address
    return allocate_at(USER_HEAP_BASE + offset, size);
}
```

### No-Execute (NX) Protection

Mark stack/heap as non-executable:

```c
void protect_stack(process_t* p) {
    uint64_t stack_start = p->stack_base;
    uint64_t stack_end = stack_start + STACK_SIZE;

    for (uint64_t addr = stack_start; addr < stack_end; addr += 4096) {
        uint64_t* pte = get_page_table_entry(addr);
        *pte |= (1ULL << 63);  // Set NX bit
    }
}
```

### Stack Canaries

Detect buffer overflows:

```c
#define CANARY_VALUE 0xDEADBEEFCAFEBABEULL

void function_with_canary() {
    uint64_t canary = CANARY_VALUE;
    char buffer[100];

    // ... use buffer ...

    if (canary != CANARY_VALUE) {
        kernel_panic("Stack smashing detected!");
    }
}
```

---

## Performance Optimization

### Caching

#### Page Table Caching (TLB)

- TLB caches virtual→physical translations
- Flush selectively with `invlpg` instead of full CR3 reload
- Use Page Global bit for kernel pages

#### Disk Cache

```c
#define CACHE_SIZE 256

typedef struct {
    uint32_t lba;
    uint8_t data[512];
    uint8_t valid;
    uint64_t last_access;
} cache_entry_t;

cache_entry_t disk_cache[CACHE_SIZE];

int disk_read_cached(uint32_t lba, void* buffer) {
    // Check cache
    for (int i = 0; i < CACHE_SIZE; i++) {
        if (disk_cache[i].valid && disk_cache[i].lba == lba) {
            memcpy(buffer, disk_cache[i].data, 512);
            disk_cache[i].last_access = get_ticks();
            return 0;
        }
    }

    // Cache miss - read from disk
    int result = disk_read(lba, 1, buffer);
    if (result == 0) {
        // Add to cache (LRU eviction)
        int victim = find_lru_entry();
        disk_cache[victim].lba = lba;
        memcpy(disk_cache[victim].data, buffer, 512);
        disk_cache[victim].valid = 1;
        disk_cache[victim].last_access = get_ticks();
    }

    return result;
}
```

### DMA (Direct Memory Access)

Let device transfer data without CPU:

```c
void dma_transfer(uint32_t channel, void* buffer, size_t size,
                  int direction) {
    // Setup DMA channel
    outb(DMA_CHANNEL_BASE(channel) + DMA_ADDRESS,
         (uint32_t)buffer & 0xFF);
    outb(DMA_CHANNEL_BASE(channel) + DMA_ADDRESS,
         ((uint32_t)buffer >> 8) & 0xFF);

    outb(DMA_CHANNEL_BASE(channel) + DMA_COUNT, size & 0xFF);
    outb(DMA_CHANNEL_BASE(channel) + DMA_COUNT, (size >> 8) & 0xFF);

    // Set direction and start
    outb(DMA_CHANNEL_BASE(channel) + DMA_MODE,
         direction | DMA_MODE_SINGLE);
    outb(DMA_CHANNEL_BASE(channel) + DMA_CONTROL, DMA_START);
}
```

### Profiling

Find performance bottlenecks:

```c
#define PROFILE_FUNCTION(name) \
    uint64_t __profile_start_##name = rdtsc()

#define PROFILE_END(name) \
    do { \
        uint64_t cycles = rdtsc() - __profile_start_##name; \
        print_str(#name ": "); \
        print_dec(cycles); \
        print_str(" cycles\n"); \
    } while(0)

// Usage
void some_function() {
    PROFILE_FUNCTION(some_function);

    // ... code ...

    PROFILE_END(some_function);
}
```

---

## Summary

### Advanced OS Features

1. **Multitasking**: Multiple programs running concurrently
2. **Virtual Memory**: Isolation, demand paging, COW
3. **System Calls**: User→kernel transition
4. **SMP**: Multiple CPUs working together
5. **Advanced FS**: ext2, VFS abstraction
6. **Networking**: TCP/IP stack
7. **Security**: ASLR, NX, stack canaries
8. **Performance**: Caching, DMA, profiling

### Next Steps for Your OS

- [ ] Implement basic multitasking
- [ ] Add system call interface
- [ ] Support SMP (multi-core)
- [ ] Implement networking stack
- [ ] Add security features
- [ ] Optimize hot paths
- [ ] Write comprehensive tests

### Resources for Further Learning

- **Books**:
  - "Operating Systems: Three Easy Pieces"
  - "Linux Kernel Development" by Robert Love
  - "Understanding the Linux Kernel" by Bovet & Cesati

- **Online**:
  - [OSDev Wiki](https://wiki.osdev.org/)
  - [Intel Software Developer Manuals](https://software.intel.com/sdm)
  - [Linux Kernel Source](https://github.com/torvalds/linux)

- **Communities**:
  - [OSDev Forum](https://forum.osdev.org/)
  - [Reddit r/osdev](https://reddit.com/r/osdev)
  - IRC: #osdev on libera.chat

---

**Congratulations!** You've completed the comprehensive OS development notes. You now have the knowledge to build, debug, and extend your own operating system.

---

_"The journey of OS development is endless. Every feature you add opens doors to ten more. Embrace the complexity, enjoy the challenge, and never stop learning."_
