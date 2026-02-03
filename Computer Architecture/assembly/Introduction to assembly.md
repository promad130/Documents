***(Expected to follow along [[Introduction to Microprocessors and Interfacing]])***

Assembly language is a **low-level programming language** that lets you write instructions directly for a computer's CPU. Unlike high-level languages (like Python or Java), assembly is closely tied to the hardware and is specific to each processor architecture (such as x86, ARM, or MIPS).
It works directly with the memory, registers, cache, buses, etc,.

![[Introduction to Microprocessors and Interfacing#Machine vs Assembly Language]]

# It is different for different ISA!!!
Assembly is different for different CPU architectures because assembly language is **the human-readable representation of a processor’s machine code**, and machine code itself is tied to how a CPU is designed.

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

To understand **LDS**, **LES** and **LSS**, you first need to understand that the 8086 doesn't just use one number to find a memory location—it uses a **pair** of numbers.

### 1. The "Big Picture" Concepts

Before we touch the commands, we have to define the "Double-Pointer" system of the 8086.

- **Segment (The Neighborhood):** A 64KB block of memory.
    
- **Offset (The House Number):** The specific spot inside that segment.
    
- **Far Pointer:** A 32-bit (4-byte) value that contains both the **Offset** and the **Segment**.
    
    - Think of it like a full mailing address (City + Street) versus just a street name.

---
### 2. What are LDS and LES?

These are **Load Pointer** instructions. They are like "Power-MOV" commands because they move **two** pieces of data at once: a new offset and a new segment.

- **LDS (Load pointer using DS):** Loads a 32-bit pointer from memory. It puts the first two bytes into a **Register** and the next two bytes into the **DS** (Data Segment) register.
    
- **LES (Load pointer using ES):** Does the exact same thing, but puts the second part into the **ES** (Extra Segment) register.
    

---

### 3. Why do we need them? (LEA vs. LDS/LES)

- **LEA:** Only gives you the **Offset**. It assumes you are staying in your current neighborhood (segment).
    
- **LDS/LES:** Give you the **Offset AND the Segment**. You use these when you need to "jump" to a completely different part of the memory warehouse.
    

---

### 4. How the Command Works (Step-by-Step)

Let's look at the syntax: `LDS Destination, Source`

**The Rules:**

1. **Destination:** Must be a **General Purpose Register** (like SI, DI, BX).
    
2. **Source:** Must be a **Memory Location** (using brackets `[]`) that contains a 32-bit pointer.
    

**Example Execution:**

Suppose you have a 4-byte pointer stored in memory at a label called `MY_PTR`.

- Memory `MY_PTR` contains: `00 10 00 20` (Hex).
    

**Command:** `LDS SI, [MY_PTR]`

**What happens inside the CPU:**

1. The CPU goes to `MY_PTR`.
    
2. It takes the first 16 bits (`1000H`) and puts them into **SI** (The Offset).
    
3. It takes the next 16 bits (`2000H`) and puts them into **DS** (The Segment).
    

Now, your "pointer" is fully set up. Any future commands using `SI` will look in the new segment `2000H` at offset `1000H`.

---

### 5. Detailed Comparison Table

|**Instruction**|**Destination 1**|**Destination 2 (Hidden)**|**Source Type**|**Best Use Case**|
|---|---|---|---|---|
|**LEA**|Any Reg|None|Memory|Calculating a local address.|
|**LDS**|Any Reg|**DS** Register|Memory (32-bit)|Switching to a new Data Segment.|
|**LES**|Any Reg|**ES** Register|Memory (32-bit)|Switching to the Extra Segment.|

---

### 6. Common Beginner Mistakes

- **Using a Register as Source:** You cannot do `LDS SI, BX`. The source **must** be memory because a single register isn't big enough to hold a 32-bit far pointer.
    
- **Confusing with MOV:** `MOV DS, [MY_PTR]` only moves 16 bits. It would only load the segment, leaving your offset completely wrong. `LDS` does both at once safely.
    
- **Segment Restrictions:** Remember that you can't load the **CS** (Code Segment) register using these commands; the CPU manages that through JUMP and CALL instructions.
    

**Next Step:** Would you like to see how we use these pointers to move large blocks of data, which is where **LES** is most commonly used in 8086 programming?


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
    

