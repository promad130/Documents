**Expected to know:** [[Introduction to Programming]](till now), [[Pointers and Working with Memory Allocation]](till now thoroughly)
**Topics Covered:** [[#How Memory Works in Programming]],[[#Types of Memory in Programming]],[[#Types of Memory Addresses]],[[#Memory Allocation/Deallocation]], [[#Heap and stack memory]], 
**Tags:** [[Memory Allocation]], [[Introduction to Programming]], [[Introduction to Computer Architecture]], [[The C Programming Language]]

Memory in programming refers to the part of a computer where data and instructions are stored so they can be quickly accessed and manipulated by the processor while a program is running. 

In most programming contexts, "memory" typically means the computer's primary memory, or RAM (Random Access Memory), which is a fast, volatile storage area used for temporary data needed by active processes and applications.

## How Memory Works in Programming

- **Declaration:**
	Memory is organized as a sequential set of storage locations, often called "buckets," each with a unique address. These locations can hold numbers, characters, or other data types.
	
- **Initialization:**
	When you declare variables in a program, the programming language and compiler assign them to specific memory addresses. 
	For example, in C, you can directly access these addresses using pointers.
	
	Arrays and structures are collections of contiguous memory locations, allowing efficient storage and access to related data.
	
	The size of each data type (e.g., int, float, char) determines how many bytes of memory it occupies.


---
## Memory vs. Storage

- **Memory** (RAM) is for short-term, fast access and is erased when the computer is turned off.
- **Storage** (like HDDs or SSDs) is for long-term data retention and is much slower than memory.

---
## Types of Memory in Programming

| Type              | Description                                                                       | Volatile | Example Use                 |
| ----------------- | --------------------------------------------------------------------------------- | -------- | --------------------------- |
| RAM               | Fast, temporary storage for active data and instructions                          | Yes      | Running programs, variables |
| ROM               | Permanent storage for firmware and system instructions                            | No       | BIOS, embedded systems      |
| Cache Memory      | Very fast, small memory close to the CPU for frequently accessed data             | Yes      | CPU cache                   |
| Virtual Memory    | Uses disk space(secondary storage) to extend RAM, allowing larger programs to run | Yes      | Paging, swapping            |
| Secondary Storage | Long-term, non-volatile storage (not usually called "memory" in programming)      | No       | HDD, SSD                    |

---
## Memory Management

Memory management is the process of allocating, using, and freeing memory as needed by a program. 
In languages like C, programmers must manually manage memory, which includes:
- **Allocation:** Reserving memory for variables or data structures.
- **Reallocation:** Changing the size of allocated memory.
- **Deallocation:** Releasing memory when it is no longer needed to avoid memory leaks

---
# Types of Memory Addresses

Before we dive into the allocation itself, lets have a look at memory addresses, something that is used to access stuff, something without which everything would become a mess.

Every byte in the memory has it's own memory address to recognize it.

There are two types of memory addresses:
- **Physical Memory Address**
	- Physical memory (e.g., RAM) is divided into small addressable units, typically bytes, each having a **unique physical address**.
    - These addresses are determined by the architecture of the RAM chips and the motherboard design.
	- These physical addresses are fixed for the hardware but are not directly used by most programs.
    
- **Virtual Memory Addresses**
	- Programs do not directly use physical addresses. Instead, they work with **virtual addresses**, which are dynamically assigned by the operating system.
	- The **Memory Management Unit (MMU)** translates virtual addresses into physical addresses using mechanisms like paging or segmentation.
    - This translation allows multiple programs to run simultaneously without interfering with each other's memory, as each program operates in its own virtual address space.

### But where are memory addresses stored?

Memory addresses are **not stored in memory** like data. Instead, they are **inherent to the hardware design** of the computer system. Here’s how it works:

#### **1. Physical Memory Addresses**
- **Hardwired by Design**: Each byte in physical memory (e.g., RAM) has a fixed address determined by its **physical location** on the memory chip. These addresses are hardwired during manufacturing and do not require additional storage.
    - Example: A RAM chip with 1GB capacity has addresses ranging from `0x00000000` to `0x3FFFFFFF` (in hexadecimal), assigned by the hardware layout.
    
- **Access Mechanism**: The CPU uses electrical signals to directly access memory cells via their physical addresses. No storage is needed for these addresses because they are **implicit in the hardware structure**.

#### **2. Virtual Memory Addresses**
Virtual addresses are managed by the operating system (OS) and CPU hardware (Memory Management Unit, or MMU). Here’s how they work:
- **Page Tables**: The OS maintains **page tables** (stored in RAM) that map virtual addresses (used by programs) to physical addresses. These tables consume memory but are a tiny fraction of total system memory.
    - Example: A 4KB page table entry might map virtual address `0x12345678` to physical address `0xABCDEF00`.
    
- **Translation Lookaside Buffer (TLB)**: The MMU caches frequently used virtual-to-physical mappings in the TLB (a small, ultra-fast cache) to speed up address translation.

### How Are Hardwired Memory Addresses Accessed?
When we say memory addresses are "hardwired," it means they are **physically determined by the electrical design of the memory chips and CPU**, not stored as data. Here’s a breakdown of how this works:

#### **1. What Does "Hardwired" Mean?**
- **Physical Layout**: Each memory cell (e.g., a byte in RAM) is assigned a unique address based on its **physical location** on the memory chip. This is analogous to houses having fixed street addresses based on their geographic positions.
- **Manufacturing Process**: During RAM production, the circuitry is designed so that each cell responds to a specific combination of electrical signals (voltages) on the **address bus** (a set of wires connecting the CPU and memory).
- **Storage for memory**:Hence no storage for this is required.

#### **2. How Does the CPU Access Hardwired Addresses?**
The CPU accesses memory by sending electrical signals over the **address bus** and **data bus**:
1. **Address Bus**:
    - The CPU sends a binary address (e.g., `1010...`) as a series of high/low voltages on the address bus wires.
    - This binary signal directly corresponds to the physical location of the memory cell.
    - Example: A 32-bit address bus can represent $2^{32}$ unique addresses (4 GB of memory).
    
2. **Memory Controller**:
    - The memory controller (a hardware component) decodes the address bus signals and activates the specific memory cell at the requested location.
    
3. **Data Transfer**:
    - Once the cell is selected, data is read from or written to it via the **data bus**.
#### **Analogy: Postal System**
- **Hardwired Addresses** = Geographic locations of houses.
- **Address Bus** = The mail carrier following directions to a specific street number.
- **No Storage Needed** = The house’s location isn’t stored inside the house; it’s inherent to where the house was built.

---
### **Virtual vs. Physical Addresses**
- **Virtual Addresses**:
    - Used by programs and mapped to physical addresses via the **Memory Management Unit (MMU)** and **page tables** (stored in RAM).
    - Example: A program might use virtual address `0x12345678`, which the MMU translates to physical address `0xABCDEF00`.

- **Physical Addresses**:    
    - Hardwired and accessed directly via the address bus.

---
# Memory Allocation/Deallocation
When memory is allocated, the system reserves a portion of its memory for a specific purpose, such as storing data or executing a program. Here's how this process works and how addresses are assigned:
## Memory Allocation Process
1. **Static Memory Allocation (Compile-Time)**:
    - Memory is allocated during the program's compilation.
    - The size and location of the memory are fixed and determined by the compiler.
    - Examples include global and static variables, which are stored in fixed memory locations throughout the program's execution.
    
2. **Dynamic Memory Allocation (Run-Time)**:
    - Memory is allocated during program execution.
    - Functions like `malloc` (C), `new` (C++), or equivalent methods in other languages request memory from the heap.
    - The size of the memory block can vary based on runtime requirements, providing flexibility.
    
3. **Automatic Memory Allocation**:
    - Local variables are allocated memory automatically on the stack when functions are called.
    - These variables are deallocated once the function exits.

## Address Assignment
1. **Physical vs. Logical Addresses**:
    - A **logical address** is generated by the CPU during program execution and is used by processes to reference memory.
    - A **physical address** refers to an actual location in the hardware memory. Logical addresses are mapped to physical addresses by the Memory Management Unit (MMU) during execution.
    
2. **Address Binding**:
    - During **compile-time**, if the memory location is predetermined, absolute physical addresses are embedded in the program.
    - During **load-time**, relocatable addresses are used, and the loader maps them to physical addresses.
    - During **execution-time**, dynamic address translation occurs using a base register or page table for mapping logical to physical addresses.
    (look into this later)
3. **Heap Allocation**:
    - When using dynamic allocation (e.g., `malloc`), a block of memory from the heap is assigned, and its starting address is returned as a pointer. This address can then be used to access or manipulate the allocated memory.
    
4. **Contiguous vs. Non-Contiguous Allocation**:
    - In contiguous allocation, a single continuous block of memory is assigned to a process.
    - In non-contiguous allocation, memory is divided into smaller blocks or pages that may be scattered across physical memory but managed via virtual addressing.

## Virtual address assignment
### 1. What Is a Memory Address?
A **memory address** is a unique number assigned to every byte in a computer’s memory. It’s like a street address for a house: it tells the CPU exactly where to find or store a particular piece of data in RAM.
- **Physical Address:** The actual location in the hardware (RAM).
- **Logical/Virtual Address:** The address used by programs, which is mapped to a physical address by the operating system and hardware (MMU).

---
### 2. How Are Memory Addresses Represented?
- **Binary:** Internally, addresses are sequences of bits (e.g., 32 bits on a 32-bit system, 64 bits on a 64-bit system).
- **Hexadecimal:** For human readability, addresses are usually shown in hexadecimal (base 16), using digits 0-9 and letters A-F. For example, `0x7FFF1234`.

---
### 3. How Is Memory Organized?
- **Memory is an array of bytes:** Each byte has its own unique address, starting from 0 up to the maximum addressable memory for the system.
- **Example:** In a 32-bit system, addresses range from `0x00000000` to `0xFFFFFFFF` (4 GB of addressable memory).

---
### 4. What Is Alignment?
**Alignment** refers to the requirement that certain data types or memory blocks must start at addresses that are multiples of a specific value (such as 4, 8, or 16 bytes).
#### Why?
First lets understand how OS access the memory and work with it using address bus. Once this is clear, it will become intuitive why we need to use alignment for efficiency.
##### The Compiler Does the Heavy Lifting
- **Compile time**: The compiler knows the size of each data type (`int` = 4 bytes, `double` = 8 bytes, etc.)
- **Code generation**: The compiler generates machine instructions that specify exactly how many bytes to read/write
- **Runtime**: The CPU executes these pre-compiled instructions that already "know" the correct sizes

**Example:**
```c
int x = 42;        // Compiler knows: int = 4 bytes 

double y = 3.14;   // Compiler knows: double = 8 bytes
```
The compiler generates assembly like:
```assembly
mov eax, [address]     ; Read 4 bytes for int 
movq xmm0, [address]   ; Read 8 bytes for double
```
---
##### Why Must Addresses Be Multiples? How Does It Help with Chunks?
This is where **memory bus architecture** comes into play:
**1. CPUs Read Memory in Fixed-Size Chunks**
- Modern CPUs don't read memory one byte at a time
- They read in **words** (typically 4, 8, or 16 bytes at once) for efficiency
- The memory bus has a fixed width (e.g., 64 bits = 8 bytes)

**2. Memory Bus Alignment**
	- The memory controller can only fetch data starting at addresses that are multiples of the bus width:
	- **Example with 8-byte memory bus:**
		- The memory controller can fetch 8 bytes starting at addresses: 0, 8, 16, 24, 32...
		- It **cannot** start reading from addresses like 1, 2, 3, 5, 6, 7, 9, 10...
    
**3. What Happens with Aligned vs. Unaligned Data**
- **Aligned Data (Good):**
```text
Memory addresses:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 
4-byte int at 4:               [  int data  ]
```
- CPU requests address 4
- Memory controller reads 8 bytes (4-11) in **one operation**
- CPU extracts the 4 bytes it needs

- **Unaligned Data (Bad):**
```text
Memory addresses:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 
4-byte int at 6:                     [  int data  ]
```
- CPU requests address 6
- Memory controller must read **two chunks**:
    - First: bytes 0-7 (to get bytes 6-7)
    - Second: bytes 8-15 (to get bytes 8-9)
- CPU combines pieces from both reads
- **Result: 2× slower, more complex**

---
##### Visual Example: Why Multiples Matter
- **8-Byte Memory Bus Example**
```text
Address:    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15             
			├─────── Chunk 1 ──────┤├─────── Chunk 2 ──────┤ 
			
Aligned 4-byte int at address 4:             [     ][  INT DATA ]            
                                            └── Single chunk read ──┘
Unaligned 4-byte int at address 6:             [  ][INT][DATA][  ]                
                                           └─ Chunk 1 ─┘└─ Chunk 2 ─┘                
                                           Two separate reads needed!
``` 
- **Example:** A 4-byte integer (int) may need to be stored at addresses divisible by 4 (e.g., 0, 4, 8, 12, ...).

(add a summary, add So it is a computer architecture term? that means the word for a particular computter is the max memory it can handle at a time? Like for a 64 bit, it will be 8 byte. But for a computer with 8byte word, won't it cause problem when we try to access 4byte, and then follow up on [[#Implicit free lists]])

---
### 5. Why Are Low-Order Bits Zero?

When a block or data type is aligned to a power-of-two boundary (like 8 bytes), its address must be a multiple of that number.

- **Binary Representation:**
    
    - 8 bytes = 2³, so addresses must be multiples of 8.
        
    - Multiples of 8 in binary always end with three zeros:
        
        - 0: `0000`
            
        - 8: `1000`
            
        - 16: `10000`
            
        - 24: `11000`
            
    - The **lowest 3 bits are always zero** for 8-byte alignment[6](https://stackoverflow.com/questions/13176341/storing-an-integer-and-bit-in-a-single-word)[7](https://stackoverflow.com/questions/381244/purpose-of-memory-alignment).
        
- **General Rule:**
    
    - For alignment to 2n2n bytes, the lowest nn bits of the address are always zero

---
# Heap and stack memory

The memory in the computer is divided into two parts:
- Heap(grows upwards)
- Stack(grows downwards)

## 1) Heap
The **heap** is a region of a computer's memory used for **dynamic memory allocation** during a program's execution. Unlike the stack, which is used for automatic memory allocation and operates in a last-in, first-out (LIFO) manner, the heap provides more flexibility for managing memory. Here's a detailed explanation:

### **Characteristics of the Heap**

1. **Dynamic Allocation**:
    
    - Memory can be allocated and deallocated at runtime based on the program's needs.
    - Functions like `malloc` (C), `new` (C++), or similar mechanisms in other languages are used to allocate memory on the heap[1](https://www.lenovo.com/us/en/glossary/heap/)[2](https://eng.libretexts.org/Bookshelves/Computer_Science/Programming_Languages/Introduction_To_MIPS_Assembly_Language_Programming_\(Kann\)/09:_Arrays/9.01:_Heap_Dynamic_Memory)[6](https://heap-exploitation.dhavalkapil.com/heap_memory).
    
2. **Global Accessibility**:
    
    - Memory allocated on the heap is accessible from anywhere in the program using pointers, making it suitable for objects or data structures that need to persist beyond the scope of a single function[6](https://heap-exploitation.dhavalkapil.com/heap_memory).
    
3. **Manual Management**:
    
    - Unlike stack memory, heap memory must be explicitly managed by the programmer.
    - Deallocation is done using functions like `free` (C) or `delete` (C++). Failure to deallocate can lead to **memory leaks**, where unused memory remains occupied.
    
4. **Flexible Size**:
    
    - The heap can grow or shrink as needed, constrained only by the system's available memory.
    - This makes it ideal for managing large or variable-sized data structures like linked lists, trees, or dynamically sized arrays.
    
5. **Performance Considerations**:
    
    - Accessing heap memory is slower compared to stack memory due to additional overhead in allocation and deallocation.
    - Improper management can lead to issues like fragmentation, where free blocks of memory are scattered, reducing efficiency.
    
### **How Heap Memory Works**

1. **Allocation**:
    
    - When a program requests memory via functions like `malloc`, the heap manager finds a suitable block of free memory, marks it as "in use," and returns its address to the program.
        
2. **Deallocation**:
    
    - The programmer must explicitly release this memory using functions like `free` or `delete`. If not done properly, it results in memory leaks.
    
3. **Shared Pool with Stack**:
    
    - The heap and stack share the same general pool of memory but grow in opposite directions. If they collide, it can cause a program to run out of memory.

## 2) Stack
The **stack** is a region of memory in a computer system used for **automatic memory management**. It operates in a **Last-In, First-Out (LIFO)** manner, meaning the most recently added item is the first one to be removed. Here's an overview:

### **Characteristics of the Stack**
1. **Automatic Allocation and Deallocation**:
    - Memory on the stack is automatically allocated when a function is called and deallocated when the function exits.
    - This makes stack memory management fast and efficient.
    
2. **Local Scope**:
    - The stack stores local variables, function parameters, and return addresses. These variables exist only within the scope of the function that created them.
    
3. **Contiguous Memory**:
    - Stack memory is allocated in contiguous blocks, making access faster compared to heap memory.
    
4. **Fixed Size**:
    - The size of the stack is determined at program start and is limited. Excessive use (e.g., deep recursion or large local variables) can lead to **stack overflow**, causing program crashes.
    
5. **Fast Access**:
    - Accessing stack memory is faster because it involves simple pointer adjustments rather than searching for free blocks as in heap memory.

### **How Stack Memory Works**
1. **Function Calls**:
    - When a function is called, its local variables and parameters are pushed onto the stack.
    - The return address (location to resume execution after the function finishes) is also stored on the stack.
    
2. **Deallocation**:
    - When the function exits, its stack frame (allocated memory block) is popped off, and the memory becomes available for reuse.
    
3. **Memory Growth**:
    - The stack typically grows downward in memory addresses as new data is added.

### **Advantages of Stack Memory**
- Efficient allocation and deallocation due to automatic management.
- Faster access compared to heap memory.
- Reduced risk of memory leaks since memory is reclaimed automatically when functions exit.

### **Limitations of Stack Memory**
- Limited size can lead to stack overflow.
- Only suitable for temporary data that does not need to persist beyond a function's scope.

---
# Endianness
We know that the data is just collection of 1's and 0's as the computer uses base-2 format. So for example a number like 16 is represented as `10000`. 

Now in order to store it in the memory, we have two ways, either like: `00001` or `10000`. This what Endianness is!

**Endianness** is the term used in computing to describe the order in which bytes are arranged and stored in memory for multi-byte data types (such as integers and floating-point numbers).
It determines how a sequence of bytes is interpreted as a larger numerical value.

The way something is stored is important as it allows us to modify data at will depending upon the use-case, like networking, programming, etc,.
Each use-case has a part in data which works like the major determining factor/changes the stuff.

Now in binary or any form of number system, we write the number from left to right, i.e., whenever a unit value in the number system is added, it is added to the rightmost digit.
Hence the leftmost digits tell us the value of the whole number, hence the leftmost part is called the **Most Significant Digit(MSD) or Most Significant byte(MSB).**
## Types of Endianness
The two ways of representing has a name assigned to them:
- **Big-endian (BE):**  
    The most significant byte (the "big end") is stored at the *lowest memory address.*
    This is often called "network byte order" and is used in many network protocols.
    For example, the 32-bit hexadecimal number `0x12345678` would be stored in memory as:
```text
Address:   ... 0    1    2    3 
Value:         12   34   56   78
```    
    
- **Little-endian (LE):**  
    The least significant byte (the "little end") is stored at the *lowest memory address*.
    Little-endian is common on x86 and x86-64 architectures.
	The same number `0x12345678` would be stored as:
```text
Address:   ... 0    1    2    3 
Value:         78   56   34   12
```

---
# Memory allocation functions in The C Programming Language
The C programming language offers the following functions for memory allocation:

## malloc(int size*);
This takes in the size of the memory needed in bytes, and allocates the block of that heap memory and returns the pointer to the first address of the block.
Return type is `void*` so has to be typecasted into required pointer type.
If memory is not allocated, then it returns `NULL`.
By default you should assume that **it stores a garbage value**.

## calloc(int num, int size);
This allocates the memory for an array of `num` objects each with size of `size` bytes.
Returns the first address of the array, pointer type is `void*` hence needs to be typecasted.
**But zeroes the memory**. It is therefore slightly slower, but also less error-prone and more readable.

## realloc(void* ptr,int new_size);
Re-allocates the memory starting from the `ptr` address to the `new_size` as described by the parameters on the heap.


Now the memory on heap no longer in use must be deallocated so the heap is clean and free to use. This makes the programme more efficient and fast.
This deallocation is done by:
- void free(void * ptr);
	- This deallocates the memory block that `ptr` was assigned
Also after deallocation of the `ptr` memory on heap, the pointer should be discarded as well as now it is a dangling pointer, which can lead to anything and can also cause leak.

***(Add what is leak and how is it harmful and add the doubts that you had and ans for them)***

ALSO COVER DOUBLE POINTERS

---
# Implementing Dynamic Memory Allocation
First lets define a few things:
- Application: User of memory allocators
- Allocators: the system/library code that implements `malloc`/`free`

There are a total of two memory allocation techniques:
- Implicit 
- Explicit
## Implicit memory allocation
Before we look at implicit memory allocation, lets answer what is implicit memory? 
Information that people don't purposely try to remember is stored in implicit memory, which is also sometimes referred to as [unconscious memory](https://www.verywellmind.com/what-is-the-unconscious-2796004) or automatic memory. This kind of memory is both unconscious and unintentional.

>Implicit memory is also sometimes referred to as nondeclarative memory since you are not able to consciously bring it into awareness.

So generally, in programming, the implicit memory allocation works like:
- The application allocates
- and the garbage collector free up the memory

## Explicit memory allocation
So, looking at what explicit memory means:
>When you're trying to intentionally remember something (like a formula for [your statistics class](https://www.verywellmind.com/why-are-statistics-necessary-in-psychology-2795146) or a friend's mailing address), this information is stored in your explicit memory. People use these memories every day, from remembering information for a test to recalling the date and time of a doctor's appointment.

Same happens in computer, it is when we handle the memory.
So explicit memory allocation works like:
- application allocates 
- and it is the application that frees the memory.

---
## Things that are common to both:
## Task to achieve:
### Applications
- **Can issue arbitrary sequence of `malloc()` and `free()`**
    - Applications are free to request and release memory in any order and at any time. The allocator must be able to handle any pattern of allocation and deallocation requests.
- **`free()` requests must be to a `malloc()`'d block**
    - The application is only allowed to free memory that was previously allocated by `malloc()` (or similar functions). Freeing memory not obtained from `malloc()` leads to undefined behavior.
 
---
### Allocators
- **Can't control the number or size of allocated blocks**
    - The allocator has no influence over how many blocks are requested or what their sizes are; it must handle whatever the application requests.
- **Must respond immediately to `malloc()` requests (can't reorder or buffer requests)**
    - When an application requests memory, the allocator must either provide a block or report failure right away—it cannot delay, reorder, or batch requests.
- **Must allocate blocks from free memory**
    - Only memory that is currently unallocated (free) can be used to satisfy new allocation requests.
- **Must align blocks so they satisfy all alignment requirements (e.g., 8-byte alignment for GNU `malloc`)**
    - Allocated memory must meet alignment requirements for the target architecture, ensuring that data types are properly aligned in memory.
- **Can manipulate and modify only free memory**
    - The allocator can only change the contents of memory that is not currently allocated to the application. Allocated blocks must not be altered by the allocator until they are freed.
- **Can't move the allocated blocks once they are `malloc()`'d**
    - Once a block has been handed to the application, its address must remain constant. The allocator cannot move or relocate allocated blocks (i.e., no compaction), which can lead to fragmentation over time.

---
### Defining a few stuff:

Suppose application gives a sequence of malloc and free requests: $$R_0, R_1,R_2,....,R_k,....,R_{n-1}$$
#### Aggregate payload $P_k$
So when application uses `malloc(p)` then it is requesting $p$ bytes, so allocator allocates that memory. 
After $R_k$ requests have been completed, the *aggregated payload $P_k$* is sum of all the memory that has been allocated, i.e., *malloc* minus *free*.

#### Current Heap Size $H_k$
The current heap size allocated to an application is denoted by $H_k$.

#### Peak memory utilization after $k$ requests
The maximum amount of memory allocated to a program after $k$ requests have been completed.

---
## Implementation issues faced
### 1. How to know how much memory is being `free()`'d when it's given only a pointer and no length?
When you call `free(ptr)`, you only pass the pointer, not the size of the block you want to free.  
**Challenge:** The allocator must determine the size of the block to properly manage the heap and avoid corrupting memory.

### 2. How to keep track of the free blocks?
The allocator needs to know which parts of the heap are available for future allocations.  
**Challenge:** Efficiently tracking which blocks are free and which are in use.      

### 3. What to do with extra space when allocating a block that is smaller than the free block it is placed in?
If a free block is larger than requested, there’s leftover space after allocation.  
**Challenge:** Avoid wasting this space (internal fragmentation).  

### 4. How to pick a block to use for allocation – many might fit?
When multiple free blocks are large enough to satisfy a request, which one should be used?  
**Challenge:** Choosing the best-fit block to minimize fragmentation and maximize efficiency.  

### 5. How to reinsert a `free()`'d block into the heap?
When a block is freed, it must be made available for future allocations.  
**Challenge:** Efficiently reintegrating the block and possibly merging it with adjacent free blocks to reduce fragmentation (coalescing).  

---
## Fragmentation challenges 
### Internal Fragmentation
Internal fragmentation happens **inside an allocated memory block** when the block is larger than the actual data (payload) it stores. This means some memory within the allocated block is unused and wasted.

**Internal fragmentation is caused by:**
- **Overhead of maintaining heap data structure:** Extra bytes are needed for headers, footers, or bookkeeping information within each block.
- **Padding for alignment purposes:** Memory allocators often round up block sizes to meet alignment requirements (e.g., 8 or 16 bytes), which can leave unused bytes in each block.
- **Explicit policy decisions:** Allocators may use fixed-size blocks or minimum block sizes for simplicity or speed, which can lead to unused space within blocks.

**Example:**  
If you request 13 bytes, but the allocator gives you a 16-byte block (due to alignment or minimum block size), the 3 extra bytes are internal fragmentation.

---
### External Fragmentation
External fragmentation occurs **outside allocated blocks**—in the free memory space of the heap. It happens when the total free memory is enough to satisfy a request, but it is **divided into small, non-contiguous pieces**, so no single free block is large enough for the allocation.

**External Fragmentation is caused by:**
- Even if the sum of all free blocks is large, you might not be able to allocate a large object because memory is scattered in small chunks.

**Example:**  
Suppose you have three free blocks of 10 KB each, but you need to allocate a 25 KB block. Although you have 30 KB free in total, you can't satisfy the allocation because no single block is big enough.

---
## Implicit free lists
The basic idea is that a single list contains _all memory blocks_ (both allocated and free) in the heap as:
- The heap is a sequence of blocks, each with a header (size + allocation flag).
- All blocks (allocated or free) are in a single list.
- To find a free block, the allocator scans the list from the start.

### Block structure
Block structure is the structure of block that holds some memory. But what is block? 
Think of the heap (the memory area used for `malloc()`) as a **street with many houses**. Each **"block"** is like one **individual house** on that street. The "Block Structure" describes how each house is built, not the entire street layout.
Block structure goes like:
- **Header:** Stores block size and a flag (allocated/free).
- **Alignment:** Blocks are aligned to a multiple of, say, 16 bytes.
- **Flag:** The lowest bit of the size can be used as the allocated/free flag.
```text
┌─────────────┬──────────────────────┐
│   HEADER    │       PAYLOAD        │
│ (size+flag) │    (actual data)     │
└─────────────┴──────────────────────┘
```
**Header (8 bytes typically)**
- **Size field**: How big this block is (in bytes)    
- **Allocation flag**: 1 bit indicating if this block is allocated (1) or free (0)

**Payload**
- The actual memory your program uses when you call `malloc()`

**Flag**
Since blocks are **aligned** (e.g., to 16-byte boundaries), the block sizes are always multiples of 16. In binary, multiples of 16 always end in `0000`.

**Example:**
- 16 bytes = `10000` (binary) - lowest bit is 0
- 32 bytes = `100000` (binary) - lowest bit is 0    
- 48 bytes = `110000` (binary) - lowest bit is 0

Since the lowest bit of the size is always 0, we can **steal that bit** to store the allocation flag

Visual Example:
```text
Header Value: 32 | 1 = 33 (binary: 100001)
              ↑     ↑
           size=32  allocated=1

To get size back: 33 & ~1 = 32
To get flag: 33 & 1 = 1

Heap Memory Layout:
┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
│ 16/0    │  FREE   │ 32/1    │ALLOCATED│ 64/0    │  FREE   │
│(header) │(payload)│(header) │(payload)│(header) │(payload)│
└─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
 Block 1              Block 2              Block 3
```

### Finding a free block?
There are three approach for this:
- **First Fit**: Scan from the beginning, pick the first block that fits.
- **Next fit:** Continue search from where the last search ended.
- **Best fit:** Scan all blocks, pick the smallest block that fits.
When we allocate a free block larger than needed, then we split it into two parts, one is allocated and other is free, and when it comes to freeing the block, then we just remove the allocated flag.

## Coalescing
When a block is freed, it may be adjacent to other free blocks. **Coalescing** merges adjacent free blocks into one larger block to reduce or avoid external fragmentation.
- **Forward coalescing:** Merge with next block if it’s free.
- **Bidirectional coalescing:** Merge with previous and next blocks if they’re free. This requires a **footer** at the end of each block to store its size, so you can find the previous block.

## External free lists
As seen in [[#Explicit memory allocation]], explicit memory is what we allocate. 
Hence, so for the explicit memory allocation, we have **Explicit Free Lists** are a memory management technique used in dynamic memory allocators to efficiently track and manage free memory blocks.

An explicit free list is a linked list, that keeps track of all free memory in the heap. Hence it is faster and more efficient than implicit free lists that scans the whole list whenever it comes to allocating stuff.

### **Block Structure:**  
Each free block contains:
- A header (and often a footer) that stores the block size and allocation status.
- Pointers (usually "next" and "prev") to link free blocks together. These pointers are stored in the payload area of the free block, since this space is unused until the block is allocated

### **How it works:**
1) **Free block Format:**
	Each block contains:
	- Header (That mentions the size)
	- Next Block's Pointer
	- Previous Block's Pointer(if using double linked list, i.e., list that has pointer to both next and previous memory)
	- Footer (if we need to coalesce)
```text
+--------+----------+----------+--------+--------+
| Header |  Prev*   |  Next*   |  ...   | Footer |
+--------+----------+----------+--------+--------+
*Only in free blocks
```
	
2) **Allocation**
	To allocate the memory:
	- Traverse the list
	- Find the block that satisfies the required size (by first fit best fit, etc)
	- Splice out the block
	- if block size is larger than needed, then divide into two parts and take whatever is needed
	- Now return the unused block back to list
	- Update the list, headers, footers and pointers accordingly.
3) **Freeing**
	- First mark the block as free in the header
	- Now coalesce it with adjacent free block (if possible)
	- Insert the block into the free list using the chosen policy:
		**Insertion Policies**:
		- LIFO(Last-In-First-Out)
			- This policy is simple, fast and easy to use, working is just what the name suggests, the block added recently to the list(i.e., at the top, right below the head as heap grows upwards) gets removed first.
			
			- But the issue is that this can lead to external fragmentation.
				For Example:
				- We have the given memory allocated:
				```text
				We have three blocks allocated in order:
				- A (size: 16 bytes)
				- B (size: 16 bytes)
				- C (size: 24 bytes)
				
				**Heap layout:**  
				[A][B][C] (all allocated, no free space)
				```
				Now we free block A and C:
				```text
				[Free][B][Free]
				```
				- The first block (`A`) is now free.
				- The second block (`B`) is still used.
				- The third block (`C`) is now free.
				When you free a block, you add it to the _head_ of the free list.
				So, after freeing `A` and then `C`, the free list is:
			    ```text
				Head → C (24 bytes) → A (16 bytes) → null
				```
				Hence now if we want to allocate 32 bytes, we can't, as first, there is non block of that size even though the sum is, and second, we cannot coalesce to create that size as well.
		
		- Segregated Free Lists:
			- **Segregated free lists** are designed to make allocation and freeing operations faster by keeping multiple linked lists of free blocks, each dedicated to a range or class of block sizes. Here’s how their time complexity compares and why they’re efficient:



then we get: $\fbox{O}\;\fbox{1}\;$, which according to what we said translates to going all over the unique boxes once and then we count till we reach $\fbox{1}$, hence it should represent:
$$\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;$$$$\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;$$
But it represents:
$$\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;$$$$\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;$$