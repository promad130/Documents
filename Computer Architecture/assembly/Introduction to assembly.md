***(Expected to follow along [[Introduction to Microprocessors and Interfacing]])***

Assembly language is a **low-level programming language** that lets you write instructions directly for a computer's CPU. Unlike high-level languages (like Python or Java), assembly is closely tied to the hardware and is specific to each processor architecture (such as x86, ARM, or MIPS).
It works directly with the memory, registers, cache, buses, etc,.

![[Introduction to Microprocessors and Interfacing#Machine vs Assembly Language]]

# It is different for different ISA!!!
Assembly is different for different CPU architectures because assembly language is **the human-readable representation of a processor’s machine code**, and machine code itself is tied to how a CPU is designed, i.e, what sequence of BITS does what in that particular CPU.

Here’s why it varies:

---
### 1. **Different Instruction Sets (ISA)**

- Each CPU architecture (x86, ARM, RISC-V, MIPS, etc.) has its own **Instruction Set Architecture (ISA)**.    
- The ISA defines:
    
    - What instructions the CPU understands (`ADD`, `MOV`, `JMP`, etc.).
        
    - How instructions are encoded into binary (machine code).
        
    - How many registers exist and what they’re called (`EAX`, `RAX` on x86 vs `X0`–`X30` on ARM).
	
- Since assembly is just a readable form of these instructions, different ISAs → different assembly syntax.    


### 2. **CPU Design Philosophy**

- **CISC (Complex Instruction Set Computing)** (like x86):
    
    - Many specialized instructions, some very high-level (e.g., `LODS`, `STOS`).    
    - Instructions can be variable length.
- **RISC (Reduced Instruction Set Computing)** (like ARM, RISC-V, MIPS):
    
    - Fewer, simpler instructions, usually fixed length.
    - More reliance on registers and load/store model (must load data into registers before operating).
- Because of these design differences, the assembly looks different and emphasizes different operations.    


### 3. **Registers and Data Width**

- An x86-64 CPU has registers like `RAX`, `RBX`, `RCX` (64-bit general purpose).
- An ARMv8 CPU has registers `X0`–`X30`.
- Different numbers, names, and sizes of registers mean you must write different assembly for each.    


### 4. **Calling Conventions and ABI**

- Even for the same high-level code, assembly differs because **system-level rules** (how function calls, parameters, and stack usage work) vary across architectures.
- Example: On x86-64 Linux, the first integer function arguments go in registers `RDI`, `RSI`, `RDX`, etc.  
    On ARM64 Linux, they go in `X0`, `X1`, `X2`, etc.


### 5. **Endianness & Memory Models**

- Some architectures can be little-endian, big-endian, or configurable.    
- How memory alignment, caches, and atomic operations are handled also varies.


# Assembler

Every ISA and OS has it's own assembler, that defined the syntax for the assembly language.
Sure the mnemonics remain the same, as they are defined by the ISA, but things like Lables, Directives, etc,., are defined by the assemblers. 
A few are MASM, NASM, etc,.

An **Assembler** is a software program that translates **Assembly Language** (human-readable text) into **Machine Code** (binary).

Computers do not understand words like `MOV`, `ADD`, or `JMP`. They only understand zeros and ones. The Assembler bridges that gap.

### The Process

![Image of assembler process flow diagram](https://encrypted-tbn0.gstatic.com/licensed-image?q=tbn:ANd9GcShUz9dBmBdzwXTqVNKLNTpzM6yLu9QmABpbYm7RdmHR97t35wZKS6WC66emsHkrmAsa7m34Jb1Lsjt6Vh6-hAOuPoEr_jSvHEolJs7QRjtwQx7Gxs)

Shutterstock

1. **Input (Source Code):** You write a text file (`.asm`) containing instructions like `MOV AX, 1`.
    
2. **The Assembler:** It reads your file line by line.
    
    - It looks up `MOV` in a table and finds the binary code for it (e.g., `10110`).
    - It calculates memory locations for your variables and labels.
    
3. **Output (Object Code):** It produces a file (usually `.obj`) containing the raw machine code.
    

### Its Two Main Jobs

**1. Mnemonic Translation** It converts symbols humans can remember into numbers computers can execute.

- **You write:** `INC AX` (Increment Register AX)
    
- **Assembler writes:** `01000000` (The opcode `40` in hex)
    

**2. Address Calculation (The Real MVP)** This is the most important feature.

- Without an assembler, if you wanted to `JUMP` to a specific line of code, you would have to count exactly how many bytes away it is (e.g., "Jump forward 14 bytes").
    
- With an assembler, you just place a **Label** (like `StartLoop:`). The assembler does the math and figures out the exact address for you.


# x86 Assembly mnemonics
## What is x86 Assembly?
- It's a low-level language for Intel CPUs (32-bit and 64-bit).
- You write instructions that directly control the processor.
- Syntax is different from C/C++: you use mnemonics like `MOV`, `ADD`, `JMP` instead of keywords.

## Key Concepts
- **Registers:** Small, fast storage locations in the CPU (e.g., `EAX`, `EBX`, `ECX`, `EDX` for 32-bit x86).
- **Instructions:** Commands like `MOV` (move data), `ADD` (add numbers), `JMP` (jump to another part of code).
- **Memory Addressing:** Accessing data in RAM using pointers and offsets.
- **Labels:** Named locations in code for jumps and loops.



## [[The "Copy-Paste" Command, MOV in Assembly]]

The `MOV` instruction is how you move data around.
**Think of it as:** `Destination = Source`

### Syntax

```assembly
MOV Destination, Source
```

* **Source:** Where the data comes *from* (Register, Memory, or a hardcoded number).
* **Destination:** Where the data goes *to* (Register or Memory).


### The Golden Rules (Don't break these)

1. **Sizes Must Match:** You cannot move a 16-bit number into an 8-bit register.
    * *Bad:* `MOV AL, BX` (Trying to fit a bucket into a cup).
    * *Good:* `MOV AX, BX` (Bucket to bucket).
	
2. **No Memory-to-Memory:** You cannot move data directly from one RAM location to another in a single step. You must use a register as a middleman.
    * *Bad:* `MOV [1234H], [5678H]`
    * *Good:*
	
```assembly
MOV AX, [5678H]  ; Bring it into CPU first
MOV [1234H], AX  ; Then write it out
```

3. **Source doesn't change:** It's a "Copy", not a "Cut". The source keeps its value.[^1]

### Examples

```assembly
MOV AX, BX       ; Copy value of BX into AX
MOV CL, 55H      ; Load the hex number 55 into CL (Immediate addressing)
MOV DX, [1234H]  ; Go to memory address 1234H, grab the data, put in DX
```


- ## The Memory Operand `[]`
	
	A **memory operand** is a way of telling the CPU to access data that is stored in the computer's **Random Access Memory (RAM)** rather than inside its internal registers. In 8086 assembly, it is almost always designated by the use of square brackets `[...]`.
	
	- ### 1. The Anatomy of a Memory Operand
	
	To find a "shelf" in memory, the 8086 needs an **address**. A memory operand is the formula used to calculate that address. In 16-bit 8086 architecture, this formula consists of three possible parts:
	
	2. **Base Register:** Either `BX` or `BP`.
	    
	3. **Index Register:** Either `SI` or `DI`.
	    
	4. **Displacement:** A fixed number (constant) like `100` or `0x1234`.
	    
	
	- ### 2. The Strict Rules of 8086 Memory Operands
	
	You cannot just put any register inside brackets. The 8086 hardware is physically wired to only allow specific combinations:
	
	- **Allowed:** You can combine **one** Base and/or **one** Index, plus a displacement.
	    
	    - _Examples:_ `[BX + SI]`, `[BP + DI + 5]`, `[BX + 10]`.
	        
	- **Forbidden:** You cannot use two Bases or two Indexes together.
	    
	    - _Illegal:_ `[BX + BP]` (Two bases) or `[SI + DI]` (Two indexes).
	        
	- **Register Restrictions:** You can **never** use `AX`, `CX`, `DX`, or `SP` as part of a memory operand calculation.

But, our memory address is of 20 bit in length, so how come we are get ting the 

## The "Default Neighborhoods" (Implicit Segments)

Every time you use a memory operand like `[BX]`, the CPU doesn't just wander aimlessly. It has hard-coded **Default Segments** it uses based on which register is doing the work.

| **If you use this Register...** | **The CPU assumes this Neighborhood (Segment)...** |
| ------------------------------- | -------------------------------------------------- |
| **BX, SI, or DI**               | **DS** (Data Segment)                              |
| **BP**                          | **SS** (Stack Segment)                             |
| **SP (Stack Pointer)**          | **SS** (Stack Segment)                             |
| **IP (Instruction Pointer)**    | **CS** (Code Segment)                              |

---

### Segment Overrides (Changing the Neighborhood)

What if your data is in the **Extra Segment (ES)**, but you want to use **BX** to find it? Normally, `[BX]` would look in **DS**. You must use a **Segment Override Prefix** to force the CPU to change neighborhoods .

Example from your material: `MOV CS:[BX], DL`

1. **Normally:** `[BX]` defaults to the **DS** neighborhood.
    
2. **The Override:** Adding `CS:` tells the CPU: "Ignore the default. Go to the **Code Segment** neighborhood instead".
    
3. **Result:** The physical address becomes $(CS \times 10H) + BX$.

***

## 2. The "Math" Command: `ADD`

The `ADD` instruction performs addition and updates the **Flags** (like the Overflow flag we just discussed).

#### Syntax

```assembly
ADD Destination, Source
```

**Logic:** `Destination = Destination + Source`
*(The result overwrites the Destination!)*

#### The Golden Rules

1. **Sizes Must Match:** Just like `MOV`, you can't add a byte to a word.
2. **Flags Get Updated:** Unlike `MOV` (which is silent), `ADD` screams at the processor. If the result is zero, the **Zero Flag** turns on. If it overflows, the **Overflow Flag** turns on.[^2]

#### Examples

```assembly
ADD AL, 05H      ; AL = AL + 5
ADD BX, CX       ; BX = BX + CX
ADD [SI], AL     ; Go to memory at SI, add AL to it, and save it back in memory
```


***

### Putting them Together (A Real Program)

Here is a tiny program that adds two numbers, `5` and `3`.

```assembly
MOV AL, 05H    ; Step 1: Load 5 into AL. (AL = 5)
MOV BL, 03H    ; Step 2: Load 3 into BL. (BL = 3)
ADD AL, BL     ; Step 3: Add BL to AL.
               ; Result: AL becomes 08H. BL stays 03H.
```

**What happened to the Flags?**
Since the result (8) is not zero and didn't overflow, the **Zero Flag (ZF)** and **Overflow Flag (OF)** are both **0**.


## Stack Memory Instruction

**The Golden Rule:** In x86, the stack grows **downwards** (from high memory addresses to low memory addresses).

- **SP (Stack Pointer):** Always points to the top item currently on the stack.

In computer memory maps, **"Downwards"** refers to the **Address Numbers**, not physical direction.

Here is the logic:

1. **Memory Layout:** Imagine memory addresses are like floor numbers in a skyscraper. Address `0xFFFF` is the penthouse (Top), and `0x0000` is the ground floor (Bottom).
    
2. **Starting Point:** The Stack usually starts at a **High** address (the penthouse).
    
3. **Growing Down:** To add data, we move **towards 0**. We must lower the address number.
    

### The Math

Since the 8086 is a **16-bit** system, every register (like AX) takes up **2 bytes** of space.

- To go from Address `1000` to the next available slot _below_ it, you must **subtract 2**.
    
- `1000` - 2 = `0998`
    

### Visualizing the "Subtraction"

Look at how the address numbers get **smaller** (go down) as we PUSH data:

| **Address** | **Content**  | **State**                      |
| ----------- | ------------ | ------------------------------ |
| **...**     | ...          |                                |
| **1002**    | [ Data ]     | (Used)                         |
| **1000**    | [ Data ]     | **<-- Old SP (Start Here)**    |
| **0FFE**    | [ New Item ] | **<-- New SP (After PUSH -2)** |
| **0FFC**    | [ Empty ]    | (Future growth)                |


## 1. PUSH (Put data onto the stack)

**Action:** Decrements the Stack Pointer, then copies the value to memory.

**Logic:**

1. **Step 1:** Subtract **2** from `SP` (make room for a 16-bit word).
    
2. **Step 2:** Copy the **source** value into the memory address `SP` is pointing to.
    

**Syntax:** `PUSH source` (Source can be a register like `AX` or a memory location).

**Example:** Assume `SP` = `1000h` and `AX` = `55AAh`.

1. **Instruction:** `PUSH AX`
    
2. **SP updates:** `1000h` - 2 = `0FFEh`
    
3. **Memory writes:** The value `55AAh` is stored at address `0FFEh`.    


## 2. POP (Take data off the stack)

**Action:** Copies value from memory, then increments the Stack Pointer.

**Logic:**

1. **Step 1:** Copy the value from the memory address `SP` is pointing to into the **destination**.
    
2. **Step 2:** Add **2** to `SP` (shrink the stack).    

**Syntax:** `POP destination` (Destination is usually a register like `BX` or segment register).

**Example:** Assume `SP` is currently `0FFEh` (pointing to the `55AAh` we pushed earlier).

1. **Instruction:** `POP BX`
    
2. **Data Move:** `BX` becomes `55AAh`.
    
3. **SP updates:** `0FFEh` + 2 = `1000h`. _(Note: The data usually remains in memory at 0FFEh, but it is considered "garbage" or "free space" now)._


## LEA 

To understand the **LEA** (Load Effective Address) command, we first need to clear up some "warehouse" terminology that usually confuses beginners who only know `MOV` and `ADD`.

---

### 1. New Terms You Need to Know

Before touching `LEA`, you must understand these three concepts:

- **Effective Address (EA, the calculation):** This is just a fancy name for the **final number** of a memory location. If you have a formula like `[Base + Index + Offset]`, the result of that math is the "Effective Address."
    
- **Offset (the result of that calculation called EA):** This is the distance (in bytes) from the start of a *memory segment* to a specific variable or location.
    
- **Memory Operand:** Any instruction that uses square brackets `[...]` to tell the CPU to look at a memory location.

---

### 2. What is the LEA Command?

**LEA** stands for **Load Effective Address**. It is a 16-bit or 32-bit instruction that calculates an address and stores that **number** in a register.

#### The Golden Rule of LEA:

**LEA never looks at the data inside memory.** It only does the math to find out _where_ that data would be and saves that "where" (the address) into your destination register.

---

### 3. LEA vs. MOV (The Big Confusion)

This is the part that trips everyone up. Let's look at the difference:

|**Instruction**|**What the CPU does**|**Result in Register**|
|---|---|---|
|`MOV AX, [BX]`|Goes to memory address in `BX`, opens the box, and takes the **data**.|The **Value** inside memory.|
|`LEA AX, [BX]`|Calculates the address of `[BX]`. The answer is just the value of `BX`.|The **Address** (same as `BX`).|

**Wait, isn't `LEA AX, [BX]` the same as `MOV AX, BX`?** Yes, in that specific case, they result in the same thing. Both transfer a copy of the offset address into the register. However, `MOV AX, BX` is faster and more efficient. You only use `LEA` when the math gets complicated.

---

### 4. Why Use LEA? (The "Math Trick")

The 8086 has a specialized "Address Generation Unit" that is very fast at adding registers together. `LEA` lets you use that hardware to do math that `ADD` cannot do in one step.

#### Example 1: Calculating at Runtime

Imagine you have a list, and you want to find the address of an item based on an index.

- **Instruction:** `LEA SI, [BX + DI]`
    
- **Scenario:** `BX = 3000H`, `DI = 2000H`.
    
- **Result:** `SI = 5000H`.
    
- **Why it's cool:** You just added two registers and stored the result in a third one in a single instruction. To do this with `ADD`, you would need multiple steps (e.g., `MOV SI, BX` then `ADD SI, DI`).
    

#### Example 2: The "OFFSET" Directive vs. LEA

You might see `MOV BX, OFFSET LIST`.

- **OFFSET:** Only works with simple variable names (like `LIST`). The **Assembler** calculates this before the program runs.
    
- **LEA:** Works with complex math like `[DI]` or `LIST [SI]`. The **Microprocessor** calculates this while the program is actually running.
    

---

### 5. Strict 8086 Restrictions (Very Important!)

You cannot just put any register inside the `LEA` brackets. The 8086 is very picky.

**1. The Allowed Registers:**

Inside the `[]`, you can **only** use these specific registers:

- **Base:** `BX` or `BP`
    
- **Index:** `SI` or `DI`
    

**2. The Forbidden Registers:** You **cannot** use `AX`, `CX`, `DX`, or `SP` inside the brackets for `LEA`.

- `LEA AX, [BX + SI]` is **VALID**.
    
- `LEA AX, [BX + CX]` is **INVALID** (because `CX` isn't a base or index).
    

**3. Modulo Arithmetic:** 8086 math is 16-bit. If your calculation goes above `FFFFH`, it "wraps around" back to zero (Modulo $2^{16}$).

---

### 6. Real-World Example: Swapping Data

Your material shows a great use of `LEA` for setting up pointers to swap two pieces of data:

Code snippet

```
LEA SI, DATA1          ; SI now holds the address of DATA1 [cite: 415]
MOV DI, OFFSET DATA2   ; DI now holds the address of DATA2 [cite: 416]
MOV BX, [SI]           ; Get actual VALUE from DATA1 into BX [cite: 417]
MOV CX, [DI]           ; Get actual VALUE from DATA2 into CX [cite: 418]
MOV [SI], CX           ; Put DATA2 value into DATA1's spot [cite: 419]
MOV [DI], BX           ; Put DATA1 value into DATA2's spot [cite: 428]
```


## LDS, LES, LSS

*(There are two parts, first there is explanation and other is the quick revision overview.)*

### Layer 1: The Big Picture & Motivation (Why do these exist?)

Imagine you are a delivery driver (the CPU). To deliver a package, you need an address. In the simple view (like a C pointer), an address is just a house number: `1234`. But in the x86 segmented memory model, a full address is actually a **City** (Segment) and a **House Number** (Offset).

- **Segment Register (CS, DS, ES, SS):** The City.
    
- **General Register (BX, SI, DI, SP):** The House Number.
    

**The Problem:** If you want to move your delivery truck to a completely new city, you have to update **both** the City and the House Number. If you use `MOV`, you have to do it in two steps:

1. Update the House Number (`MOV BX, ...`) -> _Wait, I'm at the new house number but still in the old city! (Wrong location)_
    
2. Update the City (`MOV DS, ...`) -> _Okay, now I'm in the right place._
    

This two-step process is slow and, in the case of the Stack (SS:SP), **dangerous** (we'll see why in Layer 6).

**The Solution:** `LDS`, `LES`, and `LSS` are **atomic teleportation** commands. They load **both** the "City" (Segment) and the "House Number" (Offset) from memory into the CPU registers in one single shot.

---

### Layer 2: Conceptual Breakdown

These instructions stand for:

- **LDS**: **L**oad **D**ata **S**egment (loads `DS` and a general register).
    
- **LES**: **L**oad **E**xtra **S**egment (loads `ES` and a general register).
    
- **LSS**: **L**oad **S**tack **S**egment (loads `SS` and a general register).
    

They all do the exact same mechanical action; they just target different Segment registers.

**The "Far Pointer" Concept:** In memory, a "full address" (often called a **Far Pointer**) is stored as 4 bytes (in 16-bit mode):

- **First 2 bytes:** The Offset (House Number).
    
- **Next 2 bytes:** The Segment (City).
    

When you run `LDS BX, [memory_address]`, the CPU goes to `memory_address`, grabs the first 2 bytes and puts them in **BX**, then grabs the next 2 bytes and puts them in **DS**.

---

### Layer 3: Visual Reinforcement

Let's look at how memory maps to the registers during an `LDS` instruction.

**Scenario:** We want to load a pointer stored at memory address `0x1000`. The memory at `0x1000` contains: `00 20 00 50` (hex).

**Instruction:** `LDS BX, [0x1000]`

Plaintext

```
       MEMORY (RAM)                            CPU REGISTERS
   +------------------+                   +------------------+
   | Address | Value  |                   |                  |
   +------------------+           +-----> |   BX Register    |
   | 0x1000  |  00    |  (Low byte)       | (Offset)         |
   | 0x1001  |  20    |  (High byte)      | Value: 2000h     |
   +------------------+      ^            +------------------+
                             |
   +------------------+      |            +------------------+
   | 0x1002  |  00    |------+----------> |   DS Register    |
   | 0x1003  |  50    |                   | (Segment)        |
   +------------------+                   | Value: 5000h     |
                                          +------------------+
```

- **Step 1:** CPU reads `2000h` from `0x1000` -> Puts it in **BX**.
    
- **Step 2:** CPU reads `5000h` from `0x1002` -> Puts it in **DS**.
    

**Result:** You are now pointing to offset `2000` inside segment `5000`.

---

### Layer 4: Step-by-Step Walkthrough

Let's compare `MOV`, `LEA`, and `LDS` so you never confuse them.

1. **`MOV BX, [0x1000]`**
    
    - Go to memory `0x1000`.
        
    - Grab the 2 bytes there (`2000h`).
        
    - Put them in `BX`.
        
    - _Result:_ `BX = 2000h`. `DS` is unchanged.
        
2. **`LEA BX, [0x1000]`**
    
    - Look at the number inside the brackets (`0x1000`).
        
    - Put that number in `BX`.
        
    - _Result:_ `BX = 1000h`. (Used for math or calculating pointers).
        
3. **`LDS BX, [0x1000]`**
    
    - Go to memory `0x1000`.
        
    - Grab 2 bytes (`2000h`) -> Put in `BX`.
        
    - Grab next 2 bytes (`5000h`) -> Put in `DS`.
        
    - _Result:_ `BX = 2000h`, `DS = 5000h`.

### Layer 5: Common Pitfalls & Debugging

1. **Endianness Confusion:**
    
    - Remember **Little Endian**: The "little" end (offset) comes first in memory. The "big" end (segment) comes second.
        
    - If you define your data manually as `DW 0x5000, 0x2000`, `LDS` will load `0x5000` into the general register and `0x2000` into the segment register.
        
2. **Confusing LEA and LDS:**
    
    - If you want the _address_ of a variable, use `LEA`.
        
    - If you want the _contents_ of a far pointer stored at that variable, use `LDS`.
        
3. **32-bit Mode (EAX/EBX):**
    
    - If you are in 32-bit mode (like on a modern OS), the "Offset" is 4 bytes (32 bits).
        
    - `LDS EBX, [MEM]` will read **6 bytes** total:
        
        - 4 bytes for EBX (Offset).
            
        - 2 bytes for DS (Segment).

### Quick Overview

These instructions atomically load **two** registers from memory:

1. A **General Register** (for Offset).
    
2. A **Segment Register** (for Segment).
    

They all follow the format: `OPCODE DestinationReg, [SourceMemory]`

|**Instruction**|**Mnemonics Meaning**|**Loads Segment Into...**|**Loads Offset Into...**|**Main Use Case**|
|---|---|---|---|---|
|**`LDS`**|**L**oad **D**ata **S**egment|**`DS`**|Specified Reg (e.g., `SI`)|Setting up source pointer for string operations.|
|**`LES`**|**L**oad **E**xtra **S**egment|**`ES`**|Specified Reg (e.g., `DI`)|Setting up destination pointer for string operations.|
|**`LSS`**|**L**oad **S**tack **S**egment|**`SS`**|**`SP`** (usually)|safely switching stacks without crashing interrupts.|
|**`LCS`**|_(Does not exist)_|N/A|N/A|_Typo/Error._ (Code Segment `CS` can only be changed via Jumps/Calls).|

**Memory Layout (Little Endian Rule):**

When loading from `[Address]`:

- `[Address]` & `[Address+1]` $\rightarrow$ Goes to **Offset Register**.
    
- `[Address+2]` & `[Address+3]` $\rightarrow$ Goes to **Segment Register**.
    

**Example:**

Code snippet

```
LDS SI, [0x1234] 
; 1. Reads word at 0x1234 -> Puts in SI (Offset)
; 2. Reads word at 0x1236 -> Puts in DS (Segment)
```


## String Data Transfer And Manipulation Commands in Assembly

### Layer 1: Big Picture & Motivation

In standard assembly, moving a block of 100 characters would normally require a loop with multiple instructions (load, store, increment pointer, decrement counter, jump). **String instructions** exist to do this in a single step.

- **Real-world use:** Copying text in a word processor, clearing a video buffer (screen), or searching for a specific command in a packet of data.
    

---

### Layer 2: Conceptual Breakdown

String operations rely on three fundamental building blocks:

1. **Pointers (SI and DI):**
    
    - **SI (Source Index):** Points to the source data, usually in the Data Segment (`DS:SI`).
        
    - **DI (Destination Index):** Points to the destination, always in the Extra Segment (`ES:DI`).
        
2. **The Direction Flag (DF):** This bit in the *Flag register* determines the "direction" of processing.
    
    - **CLD (Clear Direction):** Increments SI/DI (moves forward).
        
    - **STD (Set Direction):** Decrements SI/DI (moves backward).
        
3. **The Count (CX):** Holds the number of times the operation should repeat.
    

---

### Layer 3: Visual Reinforcement

- **Direction Flag (D) = 0:** SI/DI → SI+1 / DI+1 (Ascending)
    
- **Direction Flag (D) = 1:** SI/DI → SI-1 / DI-1 (Descending)
    

---

### Layer 4: Step-by-Step Walkthrough

There are five core instructions:

1. **MOVS (Move String):** Copies data from `[SI]` to `[DI]`.
    
2. **LODS (Load String):** Loads data from `[SI]` into the Accumulator (`AL/AX/EAX`).
    
3. **STOS (Store String):** Stores data from the Accumulator into `[DI]`.
    
4. **CMPS (Compare Strings):** Subtracts `[DI]` from `[SI]` to set flags (useful for checking if two strings are identical).
    
5. **SCAS (Scan String):** Subtracts `[DI]` from the Accumulator to find a specific value.
    

**The Secret Sauce: REP Prefix** By adding `REP` before these instructions, the CPU automatically:

1. Executes the instruction.
    
2. Increments/Decrements SI and DI.
    
3. Decrements CX.
    
4. Repeats until `CX = 0`.
    

---

### Layer 5: Code Example (Copying a String)

This minimal example copies a 5-byte string from one location to another.

Code snippet

```
; Setup
MOV SI, OFFSET SourceStr ; SI points to source
MOV DI, OFFSET DestStr   ; DI points to destination
MOV CX, 5                ; Count = 5 bytes
CLD                      ; Auto-increment (D=0)

; The actual transfer
REP MOVSB                ; Repeat Move String Byte 5 times
```

- **Change Impact:** If you change `CLD` to `STD`, the pointers move backward. You would need to point SI and DI to the **end** of the strings for this to work.
    

---

### Layer 6: Common Pitfalls

1. **Forgetting ES:** Unlike SI (which defaults to DS), DI **must** point to the Extra Segment (`ES`). If `ES` is not initialized to your data area, you will write to a random part of memory.
    
2. **Wrong Direction:** If the Direction Flag is accidentally set (`D=1`), your pointers will move backward, likely corrupting previous data. Always use `CLD` to be safe.
    
3. **CX Initialization:** Forgetting to set `CX` results in the operation running for whatever random value happens to be in that register.
    

**Does this make sense so far?** Specifically, is the distinction between why we use `SI` for source and `DI` for destination clear?


### Quick Overview 
#### 1. MOVS (Move String)

**The Big Picture:** Copy-pasting a block of memory from one place to another.

- **Action:** Copies data from `[DS:SI]` to `[ES:DI]`.
    
- **Variants:** `MOVSB` (Byte), `MOVSW` (Word), `MOVSD` (Doubleword).
    
- **Common Use:** Moving arrays or buffers.
    

> **Analogy:** Moving a stack of books from one shelf to another, one by one.

#### 2. STOS (Store String)

**The Big Picture:** Filling a memory range with a specific constant value.

- **Action:** Copies the value in the **Accumulator** (`AL/AX/EAX`) into `[ES:DI]`.
    
- **Variants:** `STOSB`, `STOSW`, `STOSD`.
    
- **Common Use:** Initializing an array to zero or clearing the screen buffer.
    

> **Analogy:** A painter using a roller to paint an entire wall a single color.

#### 3. LODS (Load String)

**The Big Picture:** Bringing data from memory into the CPU to process it.

- **Action:** Copies data from `[DS:SI]` into the **Accumulator** (`AL/AX/EAX`).
    
- **Variants:** `LODSB`, `LODSW`, `LODSD`.
    
- **Note:** We rarely use `REP` with `LODS` because it would just overwrite the Accumulator repeatedly, leaving only the last value. Usually used in a manual loop.
    

> **Analogy:** Picking up a single ingredient from the pantry to inspect/prep it.

#### 4. CMPS (Compare Strings)

**The Big Picture:** Checking if two strings or memory blocks are identical.

- **Action:** Subtracts `[ES:DI]` from `[DS:SI]` to set flags (Zero Flag, Carry Flag, etc.) but **does not store the result**.
    
- **Variants:** `CMPSB`, `CMPSW`, `CMPSD`.
    
- **Common Use:** String comparison (`strcmp` in C). Used with `REPE` (Repeat while Equal).
    

> **Analogy:** Comparing two lists line-by-line to see where they differ.

#### 5. SCAS (Scan String)

**The Big Picture:** Searching for a specific "needle" in a "haystack."

- **Action:** Subtracts `[ES:DI]` from the **Accumulator** and sets flags.
    
- **Variants:** `SCASB`, `SCASW`, `SCASD`.
    
- **Common Use:** Finding a specific character (like a null terminator `\0`) in a string. Used with `REPNE` (Repeat while Not Equal).
    

> **Analogy:** Looking through a bin of apples to find the one that is "Red."

---

### Quick Reference Table

|**Instruction**|**Source**|**Destination**|**Register Used**|**Typical Prefix**|
|---|---|---|---|---|
|**MOVS**|`[SI]`|`[DI]`|None (Direct)|`REP`|
|**STOS**|Accum|`[DI]`|`AL/AX/EAX`|`REP`|
|**LODS**|`[SI]`|Accum|`AL/AX/EAX`|None (Manual loop)|
|**CMPS**|`[SI]`|`[DI]`|None (Compare)|`REPE` / `REPNE`|
|**SCAS**|Accum|`[DI]`|`AL/AX/EAX`|`REPE` / `REPNE`|

---

### Step-by-Step Logic Check

If I want to find the letter 'A' in a paragraph stored in memory:

1. Load 'A' into `AL`.
    
2. Point `DI` to the start of the paragraph.
    
3. Use `CLD` (Forward).
    
4. Use `REPNE SCASB`.
    
5. **Result:** The loop stops when `AL` matches `[DI]`. `DI` will point to the location right after the 'A'.


## REP
### Layer 1: Big Picture & Motivation

In standard programming, if you want to do something 100 times, you write a `for` or `while` loop. In Assembly, writing a manual loop (label, decrement, jump) takes up multiple bytes of code and several CPU cycles for each iteration.

The **REP (Repeat)** prefix is a hardware-level loop. It tells the CPU: _"Don't just do this instruction once; do it over and over until the counter reaches zero."_ 
It is specifically designed to work with the **String Instructions** we just discussed (MOVS, STOS, etc.), making memory operations incredibly fast.

---

### Layer 2: Conceptual Breakdown

The `REP` prefix relies on three "hard-wired" components:

1. **The Instruction:** It must be a string instruction (like `MOVSB`).
    
2. **The Counter (`CX` or `ECX`):** The CPU automatically looks at this register to know how many times to repeat.
    
3. **The Termination Condition:** For basic `REP`, it stops when `CX = 0`. For others (like `REPE`), it can also stop if a certain flag changes.
    

---

### Layer 3: Visual & Diagrammatic Reinforcement

The logic inside the CPU looks like this:

1. Check if `CX == 0`.
    
2. If yes, exit and move to the next line of code.
    
3. If no, execute the string instruction (e.g., move one byte).
    
4. **Automatically decrement `CX`** and **automatically update pointers** (`SI`/`DI`).
    
5. Jump back to step 1.
    

---

### Layer 4: Step-by-Step Walkthrough (The "REP family")

There are actually three versions of the repeat prefix, depending on whether you are copying data or searching for data:

1. **`REP` (Repeat):**
    
    - **Usage:** With `MOVS` or `STOS`.
        
    - **Condition:** Repeat while `CX != 0`.
        
    - **Goal:** Just get the job done (Copying/Filling).
        
2. **`REPE` / `REPZ` (Repeat while Equal / Zero):**
    
    - **Usage:** With `CMPS` or `SCAS`.
        
    - **Condition:** Repeat while `CX != 0` **AND** the Zero Flag (`ZF`) is 1.
        
    - **Goal:** Keep comparing as long as the data matches. Stop as soon as a difference is found.
        
3. **`REPNE` / `REPNZ` (Repeat while Not Equal / Not Zero):**
    
    - **Usage:** With `CMPS` or `SCAS`.
        
    - **Condition:** Repeat while `CX != 0` **AND** the Zero Flag (`ZF`) is 0.
        
    - **Goal:** Search for a specific value. Stop as soon as you find a match.


## CMP and JUMP Statements in Assembly

### Layer 1: Big Picture & Motivation for CMP

In programming, we constantly make decisions: "If the temperature is > 30, turn on the fan" or "If the password is correct, log in."

In Assembly, the CPU cannot "see" an entire condition at once. It has to perform a mathematical operation first to see how two numbers relate to each other. The **CMP (Compare)** instruction is the "decision-maker." It looks at two values and sets the status flags (like a scoreboard) so that the next instruction (a Jump) knows what to do.

---

### Layer 2: Conceptual Breakdown for CMP

The most important thing to remember: **CMP is just a Subtraction that forgets the answer.**

1. **The Operation:** `CMP Destination, Source` performs `Destination - Source`.
    
2. **The Result:** The numerical result of the subtraction is **discarded**. The destination register does NOT change.
    
3. **The Flags:** Only the **Flags Register** (the "Status Scoreboard") is updated.
    
    - **Zero Flag (ZF):** Set to 1 if the numbers are equal (Result = 0).
    - **Sign Flag (SF):** Set to 1 if the result is negative (Dest < Source).
    - **Carry Flag (CF):** Set to 1 if an unsigned "borrow" occurred (Dest < Source).
[This is weird don't you think? the SF and CF?](The%20CF%20And%20SF%20Confusion)

---

### Layer 3: Visual & Diagrammatic Reinforcement for CMP

Imagine CMP as a balance scale:

- **Weights Equal:** The "Zero" light turns on.
- **Left heavier than Right:** The "Sign" and "Carry" lights stay off.    
- **Right heavier than Left:** The "Carry" light (Borrow) turns on.

---

### Layer 4: Step-by-Step Walkthrough for CMP

Let’s see what happens inside the CPU during `CMP AL, 10h`:

1. **Fetch:** CPU reads the instruction.
    
2. **Internal Subtract:** If `AL` contains `15h`, the CPU calculates `15h - 10h = 05h`.
    
3. **Update Flags:**
    
    - `05h` is not zero $\rightarrow$ **ZF = 0**
    - `05h` is positive $\rightarrow$ **SF = 0**
    - No borrow needed $\rightarrow$ **CF = 0**
        
4. **Discard Result:** The `05h` is thrown away. `AL` remains `15h`.

### Layer 5: Combining with JUMP Statements

In a standard program, the CPU is like a train on a straight track, executing instructions one after another (Linear Execution). However, real-world logic requires **forks in the road** (if-else) and **loops** (for/while).

**Jump (JMP) instructions** allow the CPU to break this linear flow by changing the **Instruction Pointer (IP/EIP)**.

- **Real-world use:** Checking if a user entered the correct PIN, repeating a task 10 times, or skipping over a "Secret Menu" section of code unless a specific button is pressed.


Jump instructions are divided into two main categories:

1. **Unconditional Jumps (`JMP`):** * The "Go to" command. No questions asked. The CPU hits this and immediately teleports to a new location.
    
2. **Conditional Jumps (`JZ`, `JNZ`, `JG`, etc.):** * The "If" command. The CPU looks at the **Flags Register** (the scoreboard we discussed in the `CMP` lesson). If the condition is met, it jumps; if not, it simply moves to the very next line.

The CPU calculates the "target address." In most cases, it doesn't store a full 32-bit address. Instead, it stores a **Relative Offset** (e.g., "Jump 20 bytes forward" or "Jump 5 bytes backward").

#### The Basics: Labels and Flags

Before the list, two quick concepts:

1. **Labels:** Since you don't know them yet, just think of a label (e.g., `MY_START:`) as a **bookmark** in your code. A jump command tells the CPU to teleport to that bookmark.
    
2. **Flags:** The CPU has a "Status Register." When you compare two numbers, the CPU flips switches called flags. The most important ones for jumps are:
    
    - **ZF (Zero Flag):** Set to 1 if the result was zero (meaning the numbers were equal).
        
    - **CF (Carry Flag):** Set if an operation required a "carry" or "borrow" (used for unsigned math).
        
    - **SF (Sign Flag):** Set if the result is negative.
        

#### Comparison Jump Table

Most jumps follow a `CMP A, B` instruction. This instruction subtracts B from A (without saving the result) just to set the flags.

|**Command**|**Meaning**|**Condition**|**Flag State**|
|---|---|---|---|
|**JZ / JE**|Jump if Zero / Equal|$A = B$|$ZF = 1$|
|**JNZ / JNE**|Jump if Not Zero / Not Equal|$A \neq B$|$ZF = 0$|
|**JG / JNLE**|Jump if Greater (Signed)|$A > B$|$ZF = 0$ and $SF = OF$|
|**JL / JNGE**|Jump if Less (Signed)|$A < B$|$SF \neq OF$|
|**JA / JNB**|Jump if Above (Unsigned)|$A > B$|$CF = 0$ and $ZF = 0$|
|**JB / JNA**|Jump if Below (Unsigned)|$A < B$|$CF = 1$|
|**JMP**|Unconditional Jump|Always|None (Always Jumps)|

#### Example: Finding a Character in a String

Since you know string operations and `LEA`, here is how you use a comparison and a jump to find a specific character in memory.


```assembly
LEA SI, my_string    ; Load effective address of string into SI
MOV AL, '!'          ; We are looking for the '!' character

CHECK_CHAR:          ; This is a LABEL (our bookmark)
    LODSB            ; Load byte at DS:SI into AL and increment SI
    CMP AL, 0        ; Is it the end of the string (null terminator)?
    JZ FINISHED      ; If ZF=1 (it is 0), jump to FINISHED

    CMP AL, '!'      ; Compare current character with '!'
    JE FOUND_IT      ; If ZF=1 (they match), jump to FOUND_IT
    
    JMP CHECK_CHAR   ; If not found yet, jump back to start of loop

FOUND_IT:
    ; ... code to handle finding the character ...

FINISHED:
    ; ... code to exit ...
```

### Key Takeaways and common pitfalls
1. **Confusing the Order:** `CMP AL, BL` is `AL - BL`. If you flip them, the flags will be the exact opposite. Always remember: **Destination - Source**.
    
2. **Forgetting it's Non-Destructive:** Students often think `CMP` changes the register value like `SUB` does. It doesn't. If you want to keep the result of the subtraction, use `SUB`.
    
3. **Signed vs. Unsigned:**  Use `JG` (Jump Greater) / `JL` (Jump Less) for **signed** numbers.
    
    - Use `JA` (Jump Above) / `JB` (Jump Below) for **unsigned** numbers.
        
    - Using the wrong jump can lead to logic errors when dealing with negative numbers (e.g., is -1 "above" 0? Unsigned, yes; Signed, no).
    
4. **JE/JZ** are the same opcode; they just check if the Zero Flag is active.
    
5. **Unsigned vs Signed:** Use **Above/Below (JA/JB)** for memory addresses or raw data. Use **Greater/Less (JG/JL)** for standard math with negative numbers.
	
6. **JMP** is a "teleport" that doesn't care about flags; it is usually used to close a loop or skip an `ELSE` block.


## INC, DEC, and ADC

#### 1. INC (Increment) & DEC (Decrement)

- **Function:** Adds or subtracts exactly **1** from a register or memory location.
    
- **The "gotcha":** These instructions update most flags (Zero, Sign, Overflow) but **do NOT affect the Carry Flag (CF)**. This is designed so you can use INC/DEC to control a loop counter without messing up the carry bit you might be using for a math calculation inside that loop.
    

#### 2. ADC (Add with Carry)

- **Function:** Adds three things together: **Operand A + Operand B + Carry Flag (CF)**.
    
- **Use Case:** Used for **Multi-word Arithmetic**. If you have a 16-bit processor, you can only add 16 bits at a time. To add 32-bit numbers, you do it in two chunks. The ADC instruction bridges the gap between the lower chunk and the upper chunk.
    

---

### Layer 3: Visual Reinforcement

Let's visualize the **ADC** operation. Imagine we are adding two large numbers on an 8-bit system. We split them into "Low Byte" and "High Byte".

**Step 1:** Add Low Bytes using `ADD`.

$$\begin{array}{r@{\quad}l} \texttt{1111 1111} & (\text{Low Byte A}) \\ + \texttt{0000 0001} & (\text{Low Byte B}) \\ \hline \texttt{0000 0000} & (\text{Result}) \rightarrow \text{Carry Flag (CF) becomes 1} \end{array}$$

**Step 2:** Add High Bytes using `ADC`.

$$\begin{array}{r@{\quad}l} \texttt{0000 0010} & (\text{High Byte A}) \\ \texttt{0010 0000} & (\text{High Byte B}) \\ + \texttt{ 1} & (\textbf{Carry Flag from Step 1}) \\ \hline \texttt{0010 0011} & (\text{Final Result}) \end{array}$$

---

### Layer 4: Step-by-Step Walkthrough

Let's walk through the processor's logic for `ADC AL, BL` (Add BL to AL with Carry).

1. **Fetch:** The CPU fetches the `ADC` opcode.
    
2. **Read Inputs:**
    
    - It reads the value in `AL` (Accumulator Low).
        
    - It reads the value in `BL` (Base Low).
        
    - It reads the current state of the **Carry Flag (CF)** in the Status Register.
        
3. **Execute:** It calculates: $\text{Result} = \text{AL} + \text{BL} + \text{CF}$.
    
4. **Write Back:** It stores the Result in `AL`.
    
5. **Update Flags:** It updates **all** status flags (Carry, Parity, Auxiliary Carry, Zero, Sign, Overflow) based on the result.
    

---

### Layer 5: Code Example (Multi-Byte Addition)

We will demonstrate adding two 32-bit numbers (`0x1234FFFF` + `0x00000002`) using 16-bit registers (AX and DX).

**Scenario:**

- Number A: `0000 0002` (High: DX) `FFFF` (Low: AX)
    
- Number B: `0000 0000` (High: BX) `0001` (Low: CX)
    

**Assembly Code:**

Code snippet

```
; Setup the registers (First Principles: Load data first)
MOV DX, 0000h   ; Top half of Number A
MOV AX, FFFFh   ; Bottom half of Number A (Large number!)
MOV BX, 0000h   ; Top half of Number B
MOV CX, 0001h   ; Bottom half of Number B

; Step 1: Add the lower 16 bits
ADD AX, CX      ; FFFF + 0001 = 0000. 
                ; CAUTION: This overflows 16 bits! 
                ; The Carry Flag (CF) is now SET (1).

; Step 2: Add the upper 16 bits WITH the carry
ADC DX, BX      ; DX = DX + BX + CF
                ; DX = 0000 + 0000 + 1
                ; DX is now 0001.

; Final Result in DX:AX is 0001:0000h (which is 65536 decimal)
```

**What about INC?**

Code snippet

```
INC AX          ; If AX was 0000, it becomes 0001. 
                ; Flags like Zero (Z) and Sign (S) update.
                ; Carry Flag (CF) does NOT change! 
```


## SUB 
We can break the `SUB` command into three building blocks:

1. **The Destination (The Prep Bowl):** This is where the first number lives and where the final answer will be kept. It can be a register (like `AX`) or a memory location.
    
2. **The Source (The Scoop):** This is the value you are taking away. It can be a register, a memory location, or a constant number (immediate data).
    
3. **The Flags (The Chef's Notes):** The CPU doesn't just give you the answer; it leaves "notes" in the **EFLAGS register** about the result (e.g., "Is the answer zero?", "Is it a negative number?").
    

**The Syntax:**

`SUB Destination, Source` $\rightarrow$ (Destination = Destination - Source)

---

### Step-by-Step Walkthrough (The Execution Cycle)

When the CPU sees `SUB AX, BX`:

1. **Fetch:** The CPU pulls the `SUB` instruction from memory.
    
2. **Decode:** It realizes it needs to perform subtraction.
    
3. **Read:** It grabs the values currently inside `AX` and `BX`.
    
4. **Execute:** The Arithmetic Logic Unit (ALU) performs the math.
    
5. **Write-Back:** The new result is saved into `AX`.
    
6. **Flag Update:** The CPU updates the status flags (Z, S, C, O, A, P) to reflect the result.
    

---

### Code & Practical Examples

#### Assembly Example (x86)

Code snippet

```
MOV AX, 10H    ; Put 16 (10 hex) into AX
SUB AX, 05H    ; Subtract 5 from AX. AX now holds 0BH (11 decimal)
```

#### C/C++ Inline Example

In modern programming, we often use C++, but we can peek "under the hood" using an inline assembler block:

C++

```
int health = 100;
int damage = 20;

_asm {
    mov eax, health  ; Load health into EAX
    sub eax, damage  ; Subtract damage from EAX
    mov health, eax  ; Store result back in health variable
}
```

_If we changed `damage` to 110, the "Sign Flag" (S) would trigger because the result is negative!_

---

### Common Pitfalls & Debugging

1. **Memory-to-Memory Subtraction:** You **cannot** subtract a memory location directly from another memory location (e.g., `SUB [var1], [var2]` is illegal). You must move one into a register first.
    
2. **The Order Matters:** Remember, it's `Destination - Source`. If you swap them, you get a completely different answer!
    
3. **Forgetting Flags:** If you're doing "multi-precision" subtraction (like 64-bit math on a 32-bit system), you must use `SBB` (Subtract with Borrow) for the higher bits to account for any "borrows" from the lower bits.


## Multiplication and Division

**What is it?** Multiplication (`MUL`/`IMUL`) and Division (`DIV`/`IDIV`) are the CPU's way of performing rapid scaling.

**Why do we need it?**

- **Graphics:** Calculating where a pixel should appear on a screen (e.g., $Index = Row \times Width + Column$).
    
- **Sensors:** Converting a raw voltage from a temperature sensor into degrees Celsius by multiplying by a scaling factor.
    
- **Averages:** If you have the total sum of 10 heartbeats, you use division to find the average heart rate.
    


### Layer 2: Conceptual Breakdown

There are two major versions of these commands:

1. **Unsigned (`MUL` / `DIV`):** Treats numbers as simple positive values (0 to 255 for a byte).
    
2. **Signed (`IMUL` / `IDIV`):** Treats numbers as "Integers," allowing for negative values using Two's Complement.
    

**The "Double-Width" Rule:**

In the physical world, if you multiply two 2-digit numbers (like $99 \times 99$), the result can be 4 digits ($9,801$). The CPU follows this rule:

- Two **8-bit** numbers produce a **16-bit** product.
    
- Two **16-bit** numbers produce a **32-bit** product.
    
- Two **32-bit** numbers produce a **64-bit** product.
    

---

### Layer 3: Visual & Diagrammatic Reinforcement

When performing these operations, the CPU uses "implicit" registers. You don't always tell it where to put the result; it already knows.

#### The Multiplication Map

When you use `MUL` or `IMUL`, the CPU always starts with a value in the accumulator (`AL`, `AX`, or `EAX`) and places the result in a "double-width" destination.

- **8-bit Map:** `AL` (8-bit) $\times$ `Source` (8-bit) $\rightarrow$ **Result in `AX`** (16-bit).
    
- **16-bit Map:** `AX` (16-bit) $\times$ `Source` (16-bit) $\rightarrow$ **Result in `DX` (High) and `AX` (Low)** (32-bit total).
    
- **32-bit Map:** `EAX` (32-bit) $\times$ `Source` (32-bit) $\rightarrow$ **Result in `EDX` (High) and `EAX` (Low)** (64-bit total).

#### The Division Map

Division is the inverse. You must provide a "double-width" dividend in specific registers before calling `DIV` or `IDIV`.

- **8-bit Division Map:**
    
    - **Dividend:** Must be in `AX` (16-bit).
        
    - **Result:** Quotient goes to **`AL`**, Remainder goes to **`AH`**.
        
- **16-bit Division Map:**
    
    - **Dividend:** Must be in `DX` (High) and `AX` (Low) (32-bit total).
        
    - **Result:** Quotient goes to **`AX`**, Remainder goes to **`DX`**.
        
- **32-bit Division Map:**
    
    - **Dividend:** Must be in `EDX:EAX` (64-bit total).
        
    - **Result:** Quotient goes to **`EAX`**, Remainder goes to **`EDX`**.

---

### Layer 4: Step-by-Step Walkthrough

#### 1. Multiplication (`MUL` and `IMUL`)

For 8-bit multiplication, the **multiplicand** (the first number) is **always** in the `AL` register. You only specify the **multiplier** (the second number), which can be a register or memory.

Note: Immediate multiplication (e.g., `MUL 5`) is NOT allowed in standard 8086/8088 instructions.

**Example Walkthrough: 8-bit Multiplication**

1. `MOV AL, 5` (Put first number in `AL`)
    
2. `MOV BL, 10` (Put second number in `BL`)
    
3. `MUL BL` (The CPU calculates $AL \times BL$. The 16-bit result is stored in `AX`).
    

#### 2. Division (`DIV` and `IDIV`)

Division is the opposite: it starts with a **double-width dividend** and divides it by a **single-width divisor**.

**Example Walkthrough: 8-bit Division**

1. **Prepare the Dividend:** Put the 16-bit number you want to divide into `AX`.
    
2. **Execute:** `DIV CL` (Divide `AX` by `CL`).
    
3. **Check Results:** * The **Quotient** (how many times it fits) goes into `AL`.
    
    - The **Remainder** (what's left over) goes into `AH`.
        


### Layer 5: Code Examples

**16-bit Multiplication (Producing a 32-bit result):**

Code snippet

```
MOV AX, 2000H   ; Multiplicand in AX
MOV CX, 0002H   ; Multiplier in CX
MUL CX          ; DX:AX = AX * CX
; Result: DX = 0000, AX = 4000H [cite: 285]
```

**Signed Division Example:**

If we divide $+16$ (`0010H`) by $-3$ (`0FDH`) using `IDIV`:

Code snippet

```
MOV AX, 0010H   ; Dividend
MOV BL, 0FDH    ; Divisor (-3)
IDIV BL         ; Divide AX by BL
; AL = Quotient, AH = Remainder [cite: 331, 396]
```

---

### Layer 6: Common Pitfalls & Debugging

1. **Divide Overflow:** This happens if the quotient is too large to fit in the result register (e.g., dividing a huge 16-bit number by `1`). The CPU will trigger an interrupt/error.
    
2. **Divide by Zero:** Attempting to divide by `0` will crash your program immediately. **Always** check your divisor before dividing!
    
3. **Forgetting DX:** In 16-bit division, the CPU divides the _entire_ `DX:AX` pair. If you only care about `AX`, you **must** clear `DX` to zero (for unsigned) or use `CWD` (Convert Word to Doubleword) for signed to extend the sign bit into `DX`.
    
4. **Predicting Flags:** Unlike addition, the flags for division are **unpredictable**. Don't rely on the Zero flag after a `DIV`!


## ASCII Related Mnemonics 

### 1. Understanding ASCII Codes

**ASCII** (American Standard Code for Information Interchange) uses a **7-bit binary code** to represent characters such as letters, numbers, and punctuation.

In the context of 8086 arithmetic, we focus on **ASCII-coded numbers** (0-9), which are represented by hex values **30H to 39H**.

- **Decimal 0** is `30H` (Binary: `0011 0000`)
    
- **Decimal 9** is `39H` (Binary: `0011 1001`)
    

#### Why do we need ASCII Arithmetic?

Microprocessors naturally perform binary or hex arithmetic. However, when a user types "5" on a keyboard, the processor receives `35H`. If you add the ASCII for "5" (`35H`) and "5" (`35H`), the result is `6AH`, which is not the ASCII for "10". ASCII instructions "fix" these results so they can be easily converted back into human-readable characters.

---

### 2. The Auxiliary Carry Flag (AF)

The **Auxiliary Carry Flag (AF)** is crucial for ASCII and BCD arithmetic.

- **Definition:** The AF is set if there is a **borrow or carry from bit 3 to bit 4** (the low nibble to the high nibble) during an 8-bit arithmetic operation.
    
- **Role:** The ASCII adjust instructions (like AAA) check the AF to decide if a decimal adjustment is necessary (e.g., if the result of an addition in the low nibble exceeded 9).
    

---

### 3. ASCII Adjust Instructions

These instructions specifically adjust the **AL** register (and sometimes **AH**) to ensure the result represents a valid decimal value.

#### AAA: ASCII Adjust after Addition

**What it does:** Adjusts the result of an `ADD` or `ADC` operation.

1. **Check:** If the low nibble of **AL > 9** OR **AF = 1**:
    
    - Increment **AL** by 6.
        
    - Increment **AH** by 1.
        
    - Set **AF** and **CF** (Carry Flag) to 1.
        
2. **Always:** Clears the high nibble of **AL**.

Now here, setting AF to 1 seems like a scam but we are just doing what AF exists to do, i.e., tell whenever there has been a carry from bit 3 to 4, which there has been in case of our BCD addition 


**Example:** Adding ASCII '1' (`31H`) and '9' (`39H`).

- `MOV AX, 0031H`
    
- `ADD AL, 39H` $\rightarrow$ Result: `AL = 6AH`, `AF = 0` (Binary: `0110 1010`).
    
- `AAA` $\rightarrow$ Since low nibble (`0AH`) > 9:
    
    - `AL = 6A + 6 = 70H`.
        
    - `AH = 0 + 1 = 01H`.
        
    - Clear high nibble of `AL` $\rightarrow$ `AL = 00H`.
        
- **Final Result:** `AX = 0100H`. (Adding `3030H` to this gives `3130H`, which is ASCII '1' and '0') .
    

#### AAS: ASCII Adjust after Subtraction

**What it does:** Adjusts the result of a `SUB` or `SBB` operation.

- It functions similarly to AAA. If the result in the low nibble is greater than 9 or a borrow occurred (AF=1), it subtracts 6 from AL and decrements AH.
    

#### AAM: ASCII Adjust after Multiplication

**What it does:** Converts the product of two **unpacked BCD** numbers (00H-09H) in **AX** into a two-digit unpacked BCD number.

- **Process:** $AH = AL / 10$; $AL = AL \text{ MOD } 10$.
    

**Example:** Multiply 5 and 7 and convert to ASCII.

- `MOV AL, 5`
    
- `MOV BL, 7`
    
- `MUL BL` $\rightarrow$ `AL = 35` (Binary: `23H`).
    
- `AAM` $\rightarrow$ $35 / 10 = 3$ (Quotient in `AH`), $35 \text{ MOD } 10 = 5$ (Remainder in `AL`).
    
    - Result: `AX = 0305H`.
        
- `OR AX, 3030H` $\rightarrow$ Result: `AX = 3335H` (ASCII '3' and '5').
    

#### AAD: ASCII Adjust before Division

**What it does:** Prepared two unpacked BCD digits in **AH** and **AL** for a division.

- **Process:** It multiplies **AH** by 10 and adds it to **AL**, then clears **AH**. This turns the two BCD digits into a single binary/hex value that the `DIV` instruction can process correctly.
    

---

### 4. Is BCD used here?

**Yes.** ASCII arithmetic is essentially a form of **Unpacked BCD** arithmetic.

- **BCD (Binary Coded Decimal):** Represents each decimal digit with 4 bits.
    
- **Unpacked BCD:** Stores one digit per byte (e.g., `05H`).
    
- **ASCII Numbers:** Are basically Unpacked BCD with `3` in the high nibble (e.g., `35H`).
    
- The adjust instructions (AAA, AAS, etc.) strip away or account for the ASCII high nibble (`3`) to perform math on the underlying BCD values.


## DAA and DAS and how are the alike AAA, AAS
DAA and DAS are Decimal Adjustments for BCD calculations. 
So they must be like AAA and AAS right?

### The Core Difference: Unpacked vs. Packed

The distinction lies in **storage density**:

- **AAA/AAS (Unpacked):** These are for ASCII/Unpacked BCD, where you have **one digit per byte** (e.g., `05H`). This is why they only ever look at the **low nibble** and always **clear the high nibble** of AL.
    
- **DAA/DAS (Packed):** These are for **Packed BCD**, where you have **two digits per byte** (e.g., `57H`). Because there are two digits, these instructions have to adjust **both** the low nibble and the high nibble in a single byte.
    

---

### DAA (Decimal Adjust after Addition)

Unlike AAA, which just checks if the low nibble > 9, **DAA** performs a two-stage check to handle two digits at once:

1. **Stage 1 (Low Nibble):** If the low nibble of `AL` is > 9 OR **AF = 1**, it adds `06H` to `AL` and sets **AF**.
    
2. **Stage 2 (High Nibble):** If the original `AL` was > `99H` OR **CF = 1**, it adds `60H` to `AL` and sets **CF**.
    

**Crucial Point:** DAA does **not** clear the high nibble because that high nibble contains your second decimal digit!

#### Example: Adding 39 + 12 in Packed BCD

Code snippet

```
MOV AL, 39H    ; Packed BCD '39'
ADD AL, 12H    ; Packed BCD '12'
; Raw binary result: AL = 4BH, AF = 1 (9+2=11, which is B, carry to bit 4) [cite: 54, 55]
DAA            ; DAA Logic triggers:
               ; 1. Low nibble 'B' > 9 OR AF=1? YES. 
               ;    AL = 4B + 06 = 51H. AF=1.
               ; 2. AL > 99H or CF=1? NO.
; Final Result: AL = 51H. (39 + 12 = 51 in decimal!) 
```

---

### DAS (Decimal Adjust after Subtraction)

DAS works exactly like DAA but uses subtraction to fix the "borrows".

- If the low nibble > 9 or **AF = 1**, it subtracts `06H`.
    
- If the original `AL` > `99H` or **CF = 1**, it subtracts `60H`.
    

---

### Summary:

| **Feature**         | **AAA / AAS**         | **DAA / DAS**                      |
| ------------------- | --------------------- | ---------------------------------- |
| **Data Format**     | Unpacked BCD / ASCII  | Packed BCD                         |
| **Digits per byte** | 1 digit               | 2 digits                           |
| **High Nibble**     | **Cleared** to 0      | **Preserved** (it's the 10s digit) |
| **Registers**       | Affects `AL` and `AH` | Affects **only** `AL`              |
| **Usage**           | After `ADD`/`SUB`     | After `ADD`/`SUB`                  |

## Logic and Bit-Manipulation Instructions

These instructions provide bit-level control, allowing bits to be set, cleared, or inverted.

- **AND:** Performs a logical bit-by-bit AND. Used for **masking** to clear specific bits to 0.
    
    - **Example:** `AND AL, 0FH` clears the high nibble of `AL` while keeping the low nibble unchanged.
        
- **OR:** Performs a logical bit-by-bit inclusive-OR. Used to set specific bits to 1.
    
- **XOR (Exclusive-OR):** Performs a logical XOR. Often used to **clear a register to zero** (e.g., `XOR CH, CH`) because it is more memory-efficient than `MOV CH, 0`. It also inverts bits.
    
- **NOT:** Performs logical inversion (one's complement), changing 0s to 1s and vice-versa.
    
- **NEG:** Performs arithmetic sign inversion (two's complement), effectively multiplying a number by -1.
    
- **TEST:** Performs an AND operation but **does not change the destination operand**. It only updates flags (ZF, PF, SF).
    
    - **Example:** `TEST AL, 1` checks if the rightmost bit is set.
        

---

## Shift Instructions

Shifts move bits left or right, effectively performing fast multiplication or division by powers of 2.

- **SHL / SAL (Shift Left / Shift Arithmetic Left):** Shifts bits left, filling the vacated LSB with 0. The MSB is shifted into the Carry Flag (CF). Both instructions are identical in operation.
    
- **SHR (Shift Right):** Shifts bits right, filling the MSB with 0. The LSB moves into CF.
    
- **SAR (Shift Arithmetic Right):** Shifts bits right but **preserves the sign bit** by filling the MSB with a copy of the old MSB.
    

---

## Rotate Instructions

Rotates move bits from one end of a register to the other, or through the Carry Flag.

- **ROL (Rotate Left):** Moves the MSB into both the LSB position and the Carry Flag.
    
- **ROR (Rotate Right):** Moves the LSB into both the MSB position and the Carry Flag.
    
- **RCL (Rotate Left through Carry):** Rotates bits left, with the Carry Flag acting as an extra bit between the MSB and LSB.
    
- **RCR (Rotate Right through Carry):** Rotates bits right through the Carry Flag.
    

**Note on Counts:** For both shifts and rotates, the shift count can be immediate (e.g., `SHL AX, 1`) or stored in the `CL` register.


## Loops in Assembly

### 1. The `LOOP` Instruction

The `LOOP` instruction is the most direct way to create a fixed-count loop. It relies entirely on the **CX** (Count) register.

**How it works internally:**

1. It decrements **CX** by 1 (`CX = CX - 1`).
    
2. It checks if **CX** is zero.
    
3. If **CX != 0**, it performs a short jump to the specified label.
    
4. If **CX = 0**, it continues to the next instruction in the program.
    

**Example: Printing a character 5 times**

Code snippet

```
MOV AH, 02H    ; Function to print character
MOV DL, 'A'    ; Character to print
MOV CX, 5      ; Initialize loop counter to 5

PrintLoop:
    INT 21H    ; Call DOS interrupt
    LOOP PrintLoop ; Dec CX, jump to PrintLoop if CX != 0
; After 5 iterations, CX becomes 0 and loop terminates
```

---

### 2. Manual Loops (Jumps + CMP)

While `LOOP` is convenient, manual loops provide more flexibility because you can use any register as a counter or base the exit condition on something other than a zero-count (like finding a specific value in an array).

**The typical structure:**

- **Initialization:** Set your counter or pointer.
    
- **Body:** Perform the task.
    
- **Update:** Increment/decrement your counter or pointer.
    
- **Condition:** Use `CMP` to check a value and a conditional jump to repeat or exit.
    

**Example: Summing an array of 10 numbers**

Code snippet

```
MOV SI, OFFSET ARRAY ; Point to start of array
MOV CX, 10           ; Counter
XOR AX, AX           ; Clear AX to store sum

SumNext:
    ADD AX, [SI]     ; Add current element to sum
    INC SI           ; Move to next element (for byte array)
    DEC CX           ; Manual decrement
    JNZ SumNext      ; Jump to SumNext if CX is Not Zero 
```

---

### 3. Conditional Loop Instructions

The 8086 provides specialized loop instructions that cheassemblyck both the **CX** register and the **Zero Flag (ZF)**.

- **LOOPE / LOOPZ (Loop while Equal/Zero):**
    
    - Decrements **CX**.
        
    - Jumps if **CX != 0** AND **ZF = 1**.
        
    - Use case: Scanning an array to find the first _non-zero_ element (loop continues as long as elements are zero).
        
- **LOOPNE / LOOPNZ (Loop while Not Equal/Not Zero):**
    
    - Decrements **CX**.
        
    - Jumps if **CX != 0** AND **ZF = 0**.
        
    - Use case: Searching for a specific value in a buffer (loop continues as long as the value is _not_ found).
        

---

### 4. Critical Rules for Loops

1. **CX Initialization:** If **CX** is 0 when the `LOOP` instruction is first reached, it will decrement to `FFFFH` (65,535) and loop over 65,000 times before stopping. Always ensure **CX** is positive or use `JCXZ` (Jump if CX is Zero) to skip the loop entirely.
    
2. **Short Jumps:** The `LOOP` instruction uses a "short jump," meaning the label must be within **+127 to -128 bytes** of the current instruction. If your loop body is huge, you must use a `JMP` instruction instead.
    
3. **Flag Impact:** The `LOOP` instruction itself **does not affect the flags**, even though it decrements **CX**. However, manual instructions like `DEC CX` **do** affect the Zero Flag, which is why manual loops use `JNZ`.


## CALL and RET in Procedures
(Look into [[#MASM Directives]] first, specifically [[#** Procedural Directives in MASM ** Used to define subroutines (functions).]])

In the 8086 microprocessor, `CALL` and `RET` are the primary instructions used to implement procedures (subroutines). They work together using the system stack to ensure the program can jump to a block of code and then return exactly where it left off.

### 1. The CALL Instruction

The `CALL` instruction transfers program control to a procedure. Before jumping to the new address, it saves the **Return Address** (the address of the instruction immediately following the `CALL`) onto the stack.

There are two types of `CALL` instructions:

- **Near CALL:** Used when the procedure is in the same code segment. It pushes only the 16-bit Instruction Pointer (`IP`) onto the stack.
    
- **Far CALL:** Used when the procedure is in a different code segment. It pushes both the Code Segment (`CS`) register and then the `IP` register onto the stack.


### 2. The RET Instruction

The `RET` instruction is placed at the end of a procedure to return control to the calling program.

- **Operation:** It removes the return address from the top of the stack and places it back into the `IP` (and `CS` for far returns).
    
- **Selection:** The assembler automatically selects the correct near or far `RET` based on how the procedure was defined using the `PROC` directive.
    

### 3. The Stack Mechanism

The stack is a Last-In, First-Out (LIFO) memory area managed by the Stack Pointer (`SP`).

- When a `CALL` occurs, the `SP` decrements as it "pushes" the return address.
    
- When a `RET` occurs, the `SP` increments as it "pops" that address back into the instruction registers.
    
- **Near RET Example:** If `SP` is at `FFFDH` and a `CALL` is made, the 16-bit `IP` is stored in memory, and `SP` moves to `FFFBH`.
    

### 4. Practical Example

This example demonstrates a basic procedure call to add two numbers.

Code snippet

```
; MAIN PROGRAM
MOV AX, 30      ; First number
MOV BX, 40      ; Second number
PUSH AX         ; Pass parameters via stack
PUSH BX
CALL ADDM       ; 1. Pushes current IP to stack. 2. Jumps to ADDM
; ... Execution continues here after RET ...

; PROCEDURE DEFINITION
ADDM PROC NEAR  ; Defined as a NEAR procedure
    PUSH BP     ; Save base pointer
    MOV BP, SP  ; Set BP to access stack parameters
    MOV AX, [BP+4] ; Access pushed BX
    ADD AX, [BP+6] ; Add pushed AX
    POP BP      ; Restore BP
    RET         ; Pops address from stack back into IP
ADDM ENDP
```

**Crucial Note:** If you manually push data onto the stack inside a procedure (like `PUSH BP` above), you **must** pop it back out before hitting the `RET` instruction. If you don't, the `RET` will mistakenly pop your data into the `IP` register instead of the actual return address, causing the program to crash.



# Introduction to MASM Assembler

**MASM** stands for **Microsoft Macro Assembler**.

It is a software tool (a translator) that takes the human-readable code you write (Assembly) and converts it into the raw binary code the processor understands (Machine Code).

### What it actually does

You write a text file (ending in `.asm`). The CPU cannot run this file. You must run it through MASM.

1. **Input:** You give MASM your source code (e.g., `MOV AX, 5`).
    
2. **Processing:** MASM reads your directives (like `.DATA`) to organize memory and translates your instructions (like `MOV`) into hex/binary.
    
3. **Output:** It produces an **Object File** (`.obj`). This contains the machine code but isn't a runnable program yet (it needs a Linker for that).
    

### Why MASM?

- **Target:** It is designed specifically for **Intel x86** processors (the chips inside most PCs).
- **Syntax:** It uses **Intel Syntax**, which is the standard "Destination, Source" format (e.g., `MOV Destination, Source`).
- **"Macro" Feature:** It allows you to define **Macros** (shortcuts). If you have a chunk of 10 lines of code you use often, you can give it a name and type just that name; MASM will paste the 10 lines for you during assembly.

### Why NOT MASM?
It is WINDOWS ASSEMBLER

### The Breakdown

- **It is NOT the language:** The language is "x86 Assembly."
- **It IS the compiler:** Think of MASM as the "compiler" for assembly language on Windows.

### Simple Workflow

1. Write `program.asm` (Text).
2. Run **MASM** $\rightarrow$ gets `program.obj` (Machine parts).
3. Run **Linker** $\rightarrow$ gets `program.exe` (Finished Windows app).


## A few things that needs to be address to avoid future conflict of interest for linux users:

**1. Is MASM only for Windows?** **Yes.** It is a Microsoft product. It is built to create programs specifically for the Windows operating system (and historically MS-DOS). If you were on Linux, you would likely use **NASM** or **GAS** instead.

**2. Is it just an "addition" to normal assembly?** **No.** It is a **Dialect.** There is no such thing as "Normal Assembly" that exists on its own as a text file. You _always_ need a specific tool (an Assembler) to read your code.

- **The Processor Instructions (`MOV`, `ADD`, `SUB`)**: These are standard for all Intel/AMD chips. Every assembler uses these.
- **The Syntax & Directives (`.DATA`, `DWORD`, `OFFSET`)**: This is the "Dialect." MASM has its own style. NASM has a different style.

Think of it like this:

- **The Language:** English.
    
- **The Dialect:** American vs. British.
    
- **The Result:** Both are understood by the human (or CPU), but the spelling and grammar rules (directives) differ slightly.
    

**3. Does it allow for directives?** **Yes, but ALL assemblers use directives.** Every assembler needs to know "where does the code start?" or "make space for a variable."

- **MASM** uses `.DATA` and `DWORD`.
    
- **NASM** (another popular assembler) uses `SECTION .data` and `resd`.
    

**The "BS-Free" Summary:**

- **Instructions (CPU):** Universal (Hardware). `MOV` is always `MOV`.
    
- **Directives (Assembler):** Specific to the tool. MASM directives don't work in NASM.
    
- **MASM:** The Microsoft tool that understands Microsoft-style directives.


# MASM Directives

Assembly Directives are a fundamental part of **MASM** (Microsoft Macro Assembler).

In fact, you cannot write a functional program in MASM without them. While assembly _instructions_ (like `MOV`, `ADD`, or `JMP`) tell the processor what to do at runtime, **directives** tell the MASM assembler how to build the program during the assembly process.

Here is the breakdown of their role and some common examples.

### What Directives Do

Directives (often called pseudo-ops) do not translate into machine code. Instead, they handle tasks such as:

- **Defining Segments:** Telling the assembler which parts of the file are code, data, or stack.
- **Allocating Memory:** Reserving space for variables.
- **Procedure Management:** Marking the beginning and end of functions.
- **Conditional Assembly:** Including or excluding blocks of code based on specific conditions.

### Common MASM Directives

You will likely encounter these categories of directives in almost every MASM program:

#### **Data Definition** Used to define variables and reserve memory. They are ALLOCATORS

- `DB` (Define Byte): Allocates 1 byte.
- `DW` (Define Word): Allocates 2 bytes.
- `DD` (Define Doubleword): Allocates 4 bytes.
- `EQU`: Defines a constant value (similar to `#define` in C).

#### **[[Segment & Section Control]]** Used to organize the program's memory structure.

- `.DATA`: Marks the start of the initialized data segment.
- `.CODE`: Marks the start of the executable code segment.
- `.STACK`: Defines the size of the runtime stack.
- `.MODEL`: Sets the memory model (e.g., `.MODEL SMALL`, `.MODEL FLAT`).

#### **[[Procedural Directives in MASM]]** Used to define subroutines (functions).

- `PROC`: Signals the start of a procedure.
- `ENDP`: Signals the end of a procedure.
- `PROTO`: Declares a function prototype (often used when interfacing with C/C++ or Windows APIs).


#### **[[Operator Directives in MASM]]** Used to define how to interpret any operation

- **Arithmetic:** `+ - * /` (Do math before running).
- **Size/Type:** `BYTE`, `WORD`, `DWORD`, `PTR` (Define width of memory).
- **Meta-Data:** `OFFSET`, `SIZEOF`, `LENGTHOF` (Get info about variables).



### Example: Directives vs. Instructions

In the snippet below, the **directives** are responsible for structure and memory, while the **instructions** perform the logic.


```MASM
.DATA                   ; DIRECTIVE: Start data segment
    val1 DW 10          ; DIRECTIVE: Allocate 2 bytes, initialize to 10

.CODE                   ; DIRECTIVE: Start code segment
main PROC               ; DIRECTIVE: Start procedure
    MOV AX, val1        ; INSTRUCTION: Move data into register
    ADD AX, 5           ; INSTRUCTION: Add 5 to register
    RET                 ; INSTRUCTION: Return from procedure
main ENDP               ; DIRECTIVE: End procedure
END main                ; DIRECTIVE: End of file, entry point is main
```





# Interrupts
(First Read and follow along [[Introduction to Microprocessors and Interfacing#Interrupts]])

## The `INT3` and `INTO` commands
### Part 1: `INT 3` (The Debugger's Secret Weapon)

#### Layer 1: Motivation

Imagine you are writing a program and it crashes, but you don't know where. You open a debugger (like in VS Code or Keil) and click the **red dot** next to a line of code to set a "Breakpoint." When the code runs, it magically freezes exactly at that line so you can inspect variables.

**How does the hardware do that?** It uses the `INT 3` instruction.

#### Layer 2: The "One-Byte" Magic

In x86 assembly, the standard `INT` instruction takes **2 bytes**:

- Byte 1: Opcode `CD`
- Byte 2: The interrupt number (e.g., `21`)

**`INT 3` is unique because it is only 1 byte long.**

- Opcode: `CC`

**Why does size matter?**

To pause your program, the debugger actually **deletes** your instruction in memory and replaces it with `INT 3`.

If `INT 3` were 2 bytes long, it might overwrite part of the _next_ instruction, corrupting your program. Since it is 1 byte, it can safely replace the first byte of _any_ instruction without touching the neighbors.

#### Layer 3: The Mechanism

When the CPU executes this `CC` opcode (INT 3):

1. It behaves like a normal interrupt: Pushes Flags, CS, and IP to the stack.
    
2. It looks up **Vector 3** in the table.
    
3. **Vector 3 Address:** `3 x 4 = 12` (or `0000CH` in Hex).
    
4. It jumps to the address stored there (which is usually the Debugger's pause routine).

#### Layer 4: Example (How a Debugger works)

Let's trace what happens under the hood when you set a breakpoint.

**Original Code in Memory:**


```Assembly
Address   Opcode    Instruction
00100     B8 05 00  MOV AX, 5   <-- You want to stop here
00103     40        INC AX
```

**Step A: You set a Breakpoint**

The debugger software secretly patches memory address `00100`.

Code snippet

```Assembly
Address   Opcode    Instruction
00100     CC        INT 3       <-- Replaces 'B8'
00101     05 00     (Leftover junk data from the MOV)
00103     40        INC AX
```

**Step B: Execution**

The CPU hits `CC`. It immediately triggers **Vector 3**. The Interrupt Handler runs, freezing the screen and showing you the registers.

**Step C: Resume**

When you click "Continue," the debugger restores the original `B8` byte and executes the `MOV AX, 5` normally.

---

### Part 2: `INTO` (The Math Safety Net)

#### Layer 1: Motivation

In signed math (working with positive and negative numbers), it is very easy to accidentally "overflow."

- Example: An 8-bit register can hold numbers from -128 to +127.
    
- If you add `100 + 50`, the result is `150`. This is too big for a signed 8-bit number, so it wraps around to `-106`. This is a disaster for banking software!
    

Normally, you have to check for this manually after every math instruction:

Code snippet

```
ADD AX, BX
JO ERROR_HANDLER  ; Jump if Overflow Flag = 1
```

This clutters your code. Intel gave us a shortcut: **`INTO`**.

#### Layer 2: The Logic

`INTO` stands for **Interrupt on Overflow**. It is a **Conditional Interrupt**.

- **Logic:** It looks at the **Overflow Flag (OF)** in the status register.
    
    - **If OF = 0:** The instruction does nothing (it acts like a NOP - No Operation).
        
    - **If OF = 1:** It triggers an interrupt automatically.
        

#### Layer 3: The Mechanism

If the overflow flag is set:

1. The CPU triggers **Vector 4**.
    
2. **Vector 4 Address:** `4 x 4 = 16` (or `00010H` in Hex).
    
3. It pushes Flags, CS, IP and jumps to the Error Handler.
    

#### Layer 4: Example Code

Here is how you would use it to safely add numbers.

Code snippet

```
MOV AL, 100     ; Load 100 (Positive)
MOV BL, 50      ; Load 50 (Positive)

ADD AL, BL      ; 100 + 50 = 150.
                ; In signed 8-bit, 150 is interpreted as -106.
                ; The CPU sets the Overflow Flag (OF=1).

INTO            ; CHECKPOINT!
                ; Since OF=1, this triggers INT 4.
                ; The CPU jumps to the Overflow Handler.

MOV CL, 10      ; This line is NEVER reached because we jumped away.
```

---

### Summary Checklist

|**Feature**|**INT 3**|**INTO**|
|---|---|---|
|**Full Name**|Breakpoint Interrupt|Interrupt on Overflow|
|**Trigger**|Always (Unconditional)|Only if Overflow Flag (OF) = 1|
|**Vector Number**|**3**|**4**|
|**Vector Address**|`0000CH`|`00010H`|
|**Size**|**1 Byte** (`CC`)|1 Byte (`CE`)|
|**Main Use**|Debugging (Breakpoints)|Error handling in signed math|

**Quick Quiz to Check Understanding:**

1. Why can't we just use the normal `INT 3` opcode (`CD 03`) for breakpoints? Why did Intel create a special 1-byte opcode (`CC`)?
    
2. If I run `SUB AX, BX` and the result is correct (no error), what does the `INTO` instruction do immediately afterward?
    

_(Take a moment to think about the answer, then scroll down)_

.

.

.

**Answers:**

1. Because `CD 03` is 2 bytes long. If we replaced a 1-byte instruction with a 2-byte breakpoint, we would overwrite and corrupt the _next_ instruction in memory.
    
2. It does nothing (acts like a NOP) because the Overflow Flag is 0. Execution simply continues to the next line.

## `INT24H` Command

If you are coming from C or Python, you are used to having a "Standard Library" (`#include <stdio.h>` or `import os`).
In x86 Assembly, you don't have libraries. You have **Interrupt 21h**.

### Layer 1: The "Swiss Army Knife" Analogy

Imagine you are a handy worker (the **User Program**). You want to build a house, but you are not allowed to touch the water pipes or the electric grid directly (the **Hardware**).

Instead, you have a walkie-talkie connected to the **Site Manager** (the **Operating System / MS-DOS**, [[What is MS-DOS?]]*).

- **The Channel:** The walkie-talkie channel is always **`INT 21H`**.
    
- **The Code Words:** You can't just say "Help." You have to use specific codes.
    
    - "Code 02" means "Output a character."
        
    - "Code 09" means "Print a string."
        
    - "Code 4C" means "I quit, I'm going home."
        

This is exactly how `INT 21H` works. It is a single interrupt entry point that provides over 100 different functions.

### Layer 2: The Protocol (The `AH` Register)

Since there is only _one_ interrupt vector for the whole OS (`21H`), how does the computer know if you want to print to the screen or read from the keyboard?

**The Selector Switch:** The **`AH` Register** (Accumulator High). Before you call `INT 21H`, you must load a specific number into `AH`. This number tells MS-DOS which function to run.

**The Workflow:**

1. **Set `AH`** $\rightarrow$ Function ID (e.g., `09H`).
    
2. **Set Data** $\rightarrow$ Put arguments in other registers (like `DX` or `DL`).
    
3. **Call** $\rightarrow$ Execute `INT 21H`.
    
4. **Result** $\rightarrow$ Returns values in `AL` or `AX` (if applicable).
    

### Layer 3: The "Big Three" Functions

#### Interrupt Vectors Defined by Intel:
![[Pasted image 20260216025249.png]]

#### Important AH Values:
![[Pasted image 20260216025421.png]]

#### 1. The "Exit" Function (`AH = 4CH`)

In C, when `main()` finishes, the program ends. In Assembly, if you don't explicitly stop, the CPU just keeps executing whatever junk memory is next (often crashing the system). You **must** tell the OS to take back control.

- **Code:**
    
    Code snippet
    
    ```
    MOV AH, 4CH   ; Function: Terminate with return code
    INT 21H       ; Transfer control back to MS-DOS
    ```
    

#### 2. The "Print Character" Function (`AH = 02H`)

Used to print a single ASCII letter to the screen.

- **Input:** Load the character you want to print into **`DL`**.
    
- **Code:**
    
    Code snippet
    
    ```
    MOV AH, 02H   ; Function: Write Character
    MOV DL, 'A'   ; The character to print goes in DL (not DX!)
    INT 21H       ; Result: 'A' appears on screen
    ```
    

#### 3. The "Print String" Function (`AH = 09H`)

Used to print a whole sentence. This is the `printf()` of the assembly world.

- **Input:** Load the **Effective Address (Offset)** of the string into **`DX`**.
    
- **The Catch:** The string **MUST** end with a **`$`** sign.
    
    - In C, strings end with `\0` (Null).
        
    - In DOS, strings end with `$`. If you forget this, it will keep printing garbage from memory until it finds a random `$` somewhere.
        
- **Code:**
    
    Code snippet
    
    ```
    .DATA
    MSG DB 'Hello Class$'  ; Note the $ terminator!
    
    .CODE
    MOV AH, 09H            ; Function: Write String
    LEA DX, MSG            ; Load Address of MSG into DX
    INT 21H                ; Result: "Hello Class"
    ```
    

---

### Layer 4: A Visual Hierarchy

Slide 8 of your file shows a beautiful diagram of how this fits together.

1. **Application Program:** (You are here). You talk to...
    
2. **MS-DOS Kernel (`INT 21H`):** The logic layer. It talks to...
    
3. **BIOS (`INT 10H`, etc.):** The "Basic Input/Output System." This is firmware burnt into the motherboard. It talks to...
    
4. **Hardware:** The actual electrons, monitor, and keyboard.
    

**Why not talk to BIOS directly?**

You can! But DOS functions (`INT 21H`) are easier. For example, `INT 21H` can read a file from a disk. BIOS only knows how to read "Sector 5, Track 3." DOS handles the complex logic of converting "Sector 5" into "MyEssay.txt."

### Summary & Example

Here is a complete, working program using this topic. This matches the example logic on **Page 19**.

Code snippet

```
.MODEL SMALL
.STACK 100H
.DATA
    GREET DB 'Welcome to MUPI!$'  ; String ending in $

.CODE
MAIN PROC
    ; 1. Initialize Data Segment (Standard Boilerplate)
    MOV AX, @DATA
    MOV DS, AX

    ; 2. Print the String (Function 09H)
    MOV AH, 09H         ; Select "Print String"
    LEA DX, GREET       ; Point DX to the text
    INT 21H             ; Call OS

    ; 3. Print a Newline (Manually printing Char 10 and 13)
    MOV AH, 02H         ; Select "Print Char"
    MOV DL, 0AH         ; Line Feed
    INT 21H
    MOV DL, 0DH         ; Carriage Return
    INT 21H

    ; 4. Exit to DOS (Function 4CH)
    MOV AH, 4CH         ; Select "Terminate"
    INT 21H             ; Bye!
MAIN ENDP
END MAIN
```

**Quick Check:**

If I want to print the letter 'Z', I put 'Z' into the `DL` register and call `INT 21H`. But nothing happens. The screen is blank.

**What one instruction did I forget?**

_(Hint: Did I tell the Site Manager **what** I wanted to do?)_


## File Operations in using `INT24`

### Layer 1: The Concept (The "Ticket" Analogy)

Imagine you go to a coat check at a theater.

1. **Drop Off (Open):** You give them your coat. They don't give you the coat back immediately; they give you a **numbered ticket** (e.g., #42).
    
2. **Retrieve (Read/Write):** Later, when you want your coat, you don't describe the coat ("It's black and wool"). You just give them **Ticket #42**.
    
3. **Leave (Close):** You hand back the ticket, and the transaction is done.
    

In DOS (and modern OSs like Windows/Linux), this "Ticket" is called a **File Handle**.

- It is just a 16-bit number (like `0005`).
    
- You **never** use the filename (`MYFILE.TXT`) after the first step. You only use the Handle.
    

---

### Layer 2: The Protocol (3 Steps)

The file operations follow a strict sequence. All of them use `INT 21H`, but with different `AH` values.

#### Step 1: Open (Get the Ticket)

- **Action:** Tell DOS the filename. DOS checks if it exists and gives you a Handle.
    
- **Function:** `AH = 3DH` (Open existing) or `3CH` (Create new).
    
- **Key Input:** `DX` points to the filename string (must end with 0, not $). `AL` sets the mode (0=Read, 1=Write, 2=Read/Write).
    
    +1
    
- **Key Output:** If successful, `AX` contains the **File Handle**. You **must** save this into a variable.
    

#### Step 2: Work (Read/Write using Ticket)

- **Action:** Move data between the file and a memory buffer.
    
- **Function:** `AH = 3FH` (Read) or `40H` (Write).
    
    +1
    
- **Key Input:** `BX` = **The File Handle** (from Step 1).
    
    +1
    
- **Key Output:** `AX` = Number of bytes actually processed.
    

#### Step 3: Close (Return Ticket)

- **Action:** Tell DOS you are finished so it can save changes to the disk.
    
- **Function:** `AH = 3EH`.
    
- **Key Input:** `BX` = The File Handle.
    

---

### Layer 3: Code Walkthrough (Reading a File)

Let's look at the specific example provided in your file (Slide 25) for reading a file.

**Scenario:** We want to read 512 bytes from a file.

**1. Data Setup**

We need variables to store the filename, the handle, and the data we read.

Code snippet

```
.DATA
    FNAME   DB 'MYDATA.TXT', 0  ; ASCII String ending in 0 (NULL) 
    HANDLE  DW ?                ; Variable to save the Ticket #
    BUFFER  DB 512 DUP(0)       ; Empty space to dump the file contents [cite: 405]
```

**2. The Code (Step-by-Step)**

**A. Open the File**

Code snippet

```
MOV AH, 3DH       ; Function: Open File [cite: 372]
LEA DX, FNAME     ; "Here is the name of the coat" [cite: 373]
MOV AL, 0         ; Mode: Read Only [cite: 376]
INT 21H           ; Call DOS

JC ERROR_HANDLER  ; If Carry Flag=1, it failed (File not found) 
MOV HANDLE, AX    ; SUCCESS! Save the Ticket # (AX) into our variable 
```

**B. Read from File**

Code snippet

```
MOV AH, 3FH       ; Function: Read File [cite: 387]
MOV BX, HANDLE    ; Load the Ticket # we saved earlier [cite: 389]
MOV CX, 512       ; "I want 512 bytes" [cite: 390]
LEA DX, BUFFER    ; "Put them here in memory" [cite: 391]
INT 21H           ; Call DOS

JC READ_ERROR     ; Check for errors [cite: 394]
; Now AX holds the number of bytes actually read (e.g., 50 bytes) [cite: 392]
```

**C. Close the File**

Code snippet

```
MOV AH, 3EH       ; Function: Close File 
MOV BX, HANDLE    ; "Here is the ticket back"
INT 21H
```

---

### Layer 4: Common Pitfalls

1. **The "End of String" Confusion:**
    
    - **Printing (`AH=09h`):** String must end with **`$`**.
        
    - **Filenames (`AH=3Dh`):** String must end with **`0`** (NULL byte).
        
    - _Why?_ Because printing is a DOS feature, but filenames follow C-language standards (ASCIIZ).
        
2. **Forgetting to Save the Handle:**
    
    - When you open a file, the handle is in `AX`. If you then do some math and overwrite `AX` before saving it to a variable (like `MOV HANDLE, AX`), you have lost your access to the file forever.
        
3. **The Carry Flag (CF):**
    
    - In `INT 21H` file operations, DOS uses the **Carry Flag** to indicate success or failure.
        
    - **CF = 0:** Success.
        
    - **CF = 1:** Error. The error code (e.g., "File Not Found") is in `AX`. always check `JC` (Jump if Carry) after a file operation.
        

### Summary Checklist

- **Open** (`3DH`) $\rightarrow$ Get Handle (`AX`).
    
- **Read** (`3FH`) / **Write** (`40H`) $\rightarrow$ Use Handle (`BX`).
    
- **Close** (`3EH`) $\rightarrow$ Release Handle.
    

**Quick Check:**

If I successfully open a file and `AX` returns `0005`, but then I mistakenly execute `MOV BX, 0004` before calling the Read function, what will happen?

_(Hint: Does Ticket #4 belong to you?)_

# Variables and Data Types in x86 Assembly for Beginners


### Variables and [[Data types in Assembly]]
In high level languages, we can store things in something called a variable with a particular data type and a unique identifier used to address to that variable, but in assembly, as it is the cpu itself, i.e., the machine code, we have [Registers](Registers%20in%20x86.md), a small and fast storage loactions in CPU.


This explanation builds on your compiled notes and presents everything clearly for a beginner.

---

## What Is a Variable in Assembly?

- A **variable** in assembly language is a **named label referring to a specific memory location** where data is stored.
    
- Unlike high-level languages, you declare variables mainly in the **data section** of your program.
    
- Each variable reserves a certain number of bytes of memory depending on the **data type** you specify.
    

---

## Data Types and Memory Sizes in Assembly

Assembly does not have data types exactly like high-level languages. Instead, **data types mean the number of bytes reserved and manipulated**:

| Data Directive | Size     | Meaning                      | Equivalent C type        |
| -------------- | -------- | ---------------------------- | ------------------------ |
| `db`           | 1 byte   | Define byte (unsigned 8-bit) | `char`                   |
| `dw`           | 2 bytes  | Define word (16-bit)         | `short`                  |
| `dd`           | 4 bytes  | Define double word (32-bit)  | `int` or `float`         |
| `dq`           | 8 bytes  | Define quad word (64-bit)    | `long long` or `double`  |
| `dt`           | 10 bytes | Define ten bytes             | Extended precision float |

## Example:

text

`section .data myChar db 'A'       ; 1 byte = ASCII 'A' myNum  dd  100      ; 4 bytes = integer 100`

---

## Registers: Your Fast, Small Variables Inside CPU

- Registers (like `eax`, `ebx`, `ecx`) are small storage areas **inside the CPU**.
    
- Usually 32 bits (4 bytes) wide on x86.
    
- You can **load values from variables (memory) into registers**, _operate_ on them, and then **store results back** to memory.
    
- Registers are extremely **fast but very limited in number**.
    

---

## Relationship Between Variables, Data Types, and Registers

|Step|Description|Example|
|---|---|---|
|1. Declare Variable|Reserve memory space for data|`myVar dd 19`|
|2. Load into Register|Copy value from memory into CPU register|`mov eax, [myVar]`|
|3. Process in Register|Perform arithmetic or logic|`add eax, 10`|
|4. Store back to Memory|Write modified value back|`mov [myVar], eax`|

---

## Where Does Your `variableName dd 19` Actually Live?

- `variableName dd 19` creates a **label named variableName** pointing to 4 bytes of memory in the program's **data segment**.
    
- The **number 19 is stored at that memory address** (occupying 4 bytes).
    
- This variable is stored in RAM, not in a register.
    
- The CPU accesses it by the **memory address associated with variableName**.
    
- You use instructions like `mov eax, [variableName]` to **load data into a register to work on it**.
    

---

## A Visual Analogy

|Memory (Variables)|Registers (CPU Variables)|
|---|---|
|Huge space, slow access|Tiny space, very fast access|
|Variables allocated with labels|Hardware registers like `eax`|
|Variables live in RAM|Registers live inside CPU chip|
|Example: `myVar dd 19` allocates memory for 4 bytes at label `myVar`|Example: `eax` is a 32-bit register|
|Data moved between memory and registers|Data manipulated within registers|

---

## Putting It Together: Full Example

text

`section .data number dd 19          ; 4-byte variable "number" with initial value 19  section .text global _start  _start:     mov eax, [number]  ; Load 4 bytes from memory label number into register EAX     add eax, 1         ; Add 1 to EAX (now 20)     mov [number], eax  ; Store updated value back to memory`

- `number` points to memory with 4 bytes allocated.
    
- `eax` is the CPU register holding the working value.
    
- The program loads, modifies, then stores back the variable.
    

