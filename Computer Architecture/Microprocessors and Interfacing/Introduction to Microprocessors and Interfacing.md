(Primarily based on 8086 microprocessor)
***(Follow along [[Introduction to assembly]])***
## Microprocessor-Based Computer System

A **microprocessor-based computer system** is a digital system where the microprocessor acts as the central processing unit (CPU) that executes instructions, performs arithmetic/logic operations, and controls data flow between memory and I/O devices. Think of the microprocessor as the brain—it fetches instructions from memory, decodes what they mean, executes them, and manages communication with all other components through buses.[^1]

The system consists of three main components:

- **Microprocessor (CPU)**: Executes instructions and processes data
- **Memory**: Stores programs (instructions) and data
- **I/O devices**: Interfaces for input/output (keyboard, display, sensors, etc.)

These components communicate via buses, which are groups of electrical lines that transfer information.

![[Pasted image 20260108103723.png]]

## The Computer's "Highway System" (Buses)

A "Bus" is just a group of wires that connects the CPU to memory and other devices. Think of it as a highway system where data travels. The 8086 processor has three specific types of buses

* **Address Bus (Unidirectional):** This bus carries the "address" (location) of where the CPU wants to find or store data.
    * **Key Concept:** The 8086 has a **20-bit address bus**. This is crucial because $2^{20}$ allows it to access **1 MB** of memory. If it had fewer wires, it could access less memory.
* **Data Bus (Bidirectional):** This bus carries the actual data back and forth.
    * **Key Concept:** The 8086 has a **16-bit data bus**. This means it can grab 16 bits (2 bytes) of data in a single trip.
* **Control Bus:** These wires carry orders like "Read this" or "Write that" (signals like RD, WR) to tell memory and I/O devices what to do.


![[Pasted image 20260108103756.png]]


## The "Scratchpad" (Registers)

A register is a tiny, super-fast storage spot located *inside* the processor itself.

* **Why do we need them?** Accessing main memory (RAM) is slow. Registers are used to hold numbers the CPU is currently working on so it doesn't have to keep reaching out to slow RAM.
* **Tech Detail:** Physically, a register is just a collection of "Flip-Flops" (electronic switches that hold a 0 or 1) wired together.

![[Pasted image 20260123035124.png]]



## Architectural Philosophies: CISC vs RISC

When designing a processor's instruction set, engineers follow different philosophies that fundamentally affect performance, complexity, and power consumption.

### CISC (Complex Instruction Set Computer)

CISC processors maximize what each instruction can do. The philosophy: "Why use multiple simple instructions when one complex instruction can do the job?"

**Characteristics:**

- Large instruction set (hundreds of instructions)
- Variable-length instruction formats (instructions can be different sizes)
- Multiple addressing modes (5-20 different ways to access data)
- Instructions can directly manipulate memory operands
- Some specialized instructions used infrequently

**Why CISC?** The goal was to bridge the gap between high-level languages and machine code by providing instructions that map closely to high-level statements. For example, a single CISC instruction might perform "multiply and store result in memory."

**Disadvantages:** Complex instruction decoding, larger control unit, increased logic delays, and inconsistent execution times.

**Examples:** Intel x86 processors (including 8086), VAX computers, IBM370, Motorola 68000 series.

### RISC (Reduced Instruction Set Computer)

RISC processors do the opposite—use simple instructions that execute fast. The philosophy emerged in the 1980s: "Simplicity allows single-cycle execution and shorter design time."

**Characteristics:**

- Small instruction set (128 or fewer instructions)
- Fixed-length instruction format (typically 4 bytes, easily decoded)
- Few addressing modes
- Memory access ONLY through LOAD and STORE instructions
- All operations performed within CPU registers
- Consistent instruction execution time
- Use of overlapped register windows for optimization

**Examples:** MIPS R4000 (first commercial RISC), SPARC (Sun), PowerPC (IBM), ARM processors (used in Apple iPods and most mobile devices).

### Performance Trade-off

CISC minimizes **instructions per program** at the cost of **cycles per instruction**. RISC minimizes **cycles per instruction** at the cost of **instructions per program**.

**Example comparison:**

CISC version:

```assembly
mov ax, 20      ; Load 20 into AX
mov bx, 5       ; Load 5 into BX
mul ax, bx      ; Multiply AX by BX (one complex instruction)
```

RISC version:

```assembly
mov ax, 0       ; Clear accumulator
mov bx, 20      ; Load 20
mov cx, 5       ; Load counter (5 times)
again:
add ax, bx      ; Add BX to AX (repeated addition)
loop again      ; Decrement CX and repeat if not zero
```

The CISC code has fewer instructions but each takes multiple cycles. The RISC code has more instructions but each executes in one cycle.

## The 8086 Microprocessor

The **8086** is Intel's first 16-bit microprocessor, released in 1978. It follows the CISC philosophy and introduced several innovations that influenced modern x86 architecture.

### Key Specifications

- **16-bit microprocessor**: Processes 16 bits of data at once
- **20-bit address bus**: Can address 1 MB of memory (2^20 bytes)
- **16-bit data bus**: Transfers 16 bits or 8 bits per operation
- **Segmented memory architecture**: Uses segment registers to access memory beyond 64KB
- **Pipelining**: Fetches next instruction while executing current one

### Pipelining (The Laundry Analogy)

Pipelining is a technique to make the processor faster by doing multiple things at once. The lecture uses a **Laundry Analogy** to explain this:

* **Without Pipelining:** You put load A in the washer. You wait 30 mins. You put it in the dryer. You wait 30 mins. Only *then* do you start washing load B.
![[Pasted image 20260123040018.png]]

* **With Pipelining:** You put load A in the washer. When A moves to the dryer, you *immediately* put load B in the washer. You are now washing and drying at the same time.
![[Pasted image 20260123040027.png]]


**How 8086 does it:**
The 8086 is split into two parts:

1. **Bus Interface Unit (BIU):** Its job is to strictly "fetch" instructions from memory.
2. **Execution Unit (EU):** Its job is to "execute" them.

While the EU is busy executing an instruction, the BIU doesn't sit idle; it runs ahead and fetches the *next* instruction, storing it in a **6-byte Queue**. This overlap is 8086 pipelining.

![[Pasted image 20260123040105.png]]

![[Pasted image 20260123040148.png]]

### 4. Memory Segmentation (The "1 MB" Problem)

This is the most important and confusing concept for beginners.

**The Problem:**
The 8086 has **16-bit registers** (they can only hold numbers up to 65,536).
But, the 8086 has a **20-bit address bus** (it needs to address up to 1,048,576 bytes).
*A 16-bit register cannot hold a 20-bit address.* It's like trying to fit a 5-digit zip code into a 4-digit box.

**The Solution: Segmentation**
The 8086 divides its 1 MB memory into logical chunks called **Segments**. To find a specific location, it uses **two** numbers instead of one:

1. **Segment Address:** The starting block (like a street name).
2. **Offset Address:** The exact location inside that block (like a house number).

The 8086 has four special Segment Registers for different tasks:

* **CS (Code Segment):** Where your program code lives.
* **DS (Data Segment):** Where your variables live.
* **SS (Stack Segment):** Used for temporary storage (the stack).
* **ES (Extra Segment):** Extra space for data.

![[Pasted image 20260123040259.png]]

### 5. Calculating the Physical Address

To get the actual 20-bit address from those two 16-bit numbers, the CPU does a tiny bit of math.

**Formula:**

$$
\text{Physical Address} = (\text{Segment} \times 16) + \text{Offset}
$$

*(Note: Multiplying by 16 is the same as adding a '0' in Hexadecimal)*

**Example from Lecture:**

* **CS (Segment):** `1234H`
* **IP (Offset):** `5678H`

**Calculation:**

1. Take Segment: `1234H`
2. Shift it left (add 0): `12340H`
3. Add Offset: `+ 5678H`
4. **Result:** `179B8H`

This "trick" allows two small 16-bit numbers to combine and point to a large 20-bit address.

Mathematical Proof:
### The Proof

**1. Define the Maximum Values**

The 8086 uses 16-bit registers.

- **Max Segment ($S_{max}$):** $FFFFh$ (which is $65,535$ in decimal).
    
- **Max Offset ($O_{max}$):** $FFFFh$ (which is $65,535$ in decimal).
    

**2. The Formula**

$$Physical Address = (Segment \times 16) + Offset$$

**3. Calculate the Maximum Possible Physical Address**

Let's plug in the maximum values:

$$MaxAddress = (65,535 \times 16) + 65,535$$

$$MaxAddress = 1,048,560 + 65,535$$

$$MaxAddress = \mathbf{1,114,095}$$

(in Decimal)

**4. Compare against 1 Megabyte**

- **1 MB defined:** $1024 \times 1024 = \mathbf{1,048,576}$ bytes.
    
- **The Difference:** $1,114,095 - 1,048,576 = \mathbf{65,519}$ bytes.
    

**5. Conclusion**

The math generates a number that is **65,519 bytes larger** than 1 MB.

In Hexadecimal, the maximum address is **10FFEFh**.

- $100000h$ is the start of the 2nd Megabyte.
    
- So, we have gone $FFEFh$ bytes into the next megabyte.
    

### What happens to this extra memory? (The "A20 Gate" Mystery)

This is a famous historical quirk!

- **On the original 8086:** It only had 20 address lines. It physically could not handle bit #21 (the '1' at the front of `10FFEF`). The CPU simply ignored it, and the address **wrapped around** to zero. (e.g., `10FFEF` became `0FFEF`).
    
- **On newer CPUs (80286+):** They had more address lines. This extra ~64KB is called the **High Memory Area (HMA)**. Operating systems like MS-DOS used special tricks to turn on "Line A20" to access this bonus memory to store drivers!
    

### 8086 Architecture: Two-Unit Design

The 8086 is divided into two independent units that work in parallel:[^1]

**1. Execution Unit (EU):**

- **ALU (Arithmetic Logic Unit)**: 16-bit ALU performs operations like ADD, SUB, AND, OR, XOR
- **General-purpose registers**: Eight 8-bit registers (AH, AL, BH, BL, CH, CL, DH, DL) that can be paired as four 16-bit registers (AX, BX, CX, DX)
- **Pointer and Index registers**: SP, BP, SI, DI for memory addressing
- **Flag register**: Contains conditional flags (status indicators) and control flags
- **Control system**: Decodes instructions and directs operations

**2. Bus Interface Unit (BIU):**

- **Segment registers**: CS, DS, SS, ES hold starting addresses of memory segments
- **Instruction Pointer (IP)**: Points to the next instruction to execute
- **Instruction queue**: 6-byte FIFO buffer for pre-fetched instructions (pipelining)
- **Memory interface**: Handles all data and address transfers on buses

![[Pasted image 20260119122851.png]]

### Registers in Detail

#### **General-Purpose Registers (16-bit when paired):**

- **AX (AH:AL) - Accumulator**: Primary register for *arithmetic/logic operations* and *function return values*
- **BX (BH:BL) - Base Register**: Used as base pointer for *memory access and indexed addressing*
- **CX (CH:CL) - Count Register**: *Loop counter* for iterations, shift/rotate instructions
- **DX (DH:DL) - Data Register**: Used for *I/O operations and holds high-order bits in multiplication/division*

#### **Pointer and Index Registers:**

- **SP (Stack Pointer)**: Points to current top of stack, used in PUSH/POP operations
- **BP (Base Pointer)**: Base pointer for stack frame, accesses function parameters and local variables
- **SI (Source Index)**: Source pointer for string/memory operations
- **DI (Destination Index)**: Destination pointer for string/memory operations

#### **Segment Registers:**

The 8086 uses **memory segmentation** because 16-bit registers can only address 64KB ($2^{16}$), but the address bus is 20-bit (1MB). Physical address calculation: **Physical Address = (Segment Register × 16) + Offset**

- **CS (Code Segment)**: Holds starting address of code segment where program instructions reside
- **DS (Data Segment)**: Starting address of data segment for variables and arrays
- **SS (Stack Segment)**: Starting address of stack segment, works with SP to access stack
- **ES (Extra Segment)**: Additional data segment for string operations

#### **Instruction Pointer (IP):**

- 16-bit register pointing to the *next instruction's offset* within the code segment
- Works with CS: Next instruction address = (CS × 16) + IP

![[Pasted image 20260123205552.png]]


### Flag Register

The flag register contains 9 active flags (out of 16 bits):

#### **Conditional Flags (reflect result of operations):**

- **CF (Carry Flag)**: Set to 1 when carry/borrow out of MSB occurs
- **PF (Parity Flag)**: Set to 1 if low-order byte has even number of 1s
- **AF (Auxiliary Carry Flag)**: Carry out of bit 3 (used in BCD arithmetic)
- **ZF (Zero Flag)**: Set to 1 if result is zero
- **SF (Sign Flag)**: Copy of MSB of result (indicates sign in signed arithmetic)
- **OF (Overflow Flag)**: Set when there's mismatch between carry into and carry out of MSB in signed arithmetic

#### Logic for Overflow Flag:
So there are two types of numbers in binary that we use in computers:
- Signed and 
- Unsigned.
The *Overflow flag* only works for the **Signed Numbers**, and completely ignores the unsigned numbers, as for the **unsigned numbers**, we have the *Carry Flag as the watch dog*, which tells whenever an overflow has occurred for unsigned numbers. 

The logic behind their working is this:

The Overflow Flag (OF) uses a specific logic gate called an **XOR (Exclusive OR)** on the "Carries" of the last two bits.

##### Layer 1: The Hardware Logic

Internally, the Arithmetic Logic Unit (ALU) looks at two specific carry signals:

1. **Carry-In ($C_{in}$):** The carry coming into the MSB (Sign Bit).
    
2. **Carry-Out ($C_{out}$):** The carry going out of the MSB into nothingness.
    

**The Formula:** `OF = (Carry-In to MSB) XOR (Carry-Out from MSB)`

##### Layer 2: How it works (The 4 Scenarios)

Let's see how this logic "catches" your $10 - 200$ scenario.

| **Scenario**                          | **Cin​ to MSB** | **Cout​ from MSB** | **OF (XOR)** | **Result**    |
| ------------------------------------- | --------------- | ------------------ | ------------ | ------------- |
| **1. Normal Addition**                | 0               | 0                  | **0**        | No Overflow   |
| **2. Normal Addition**                | 1               | 1                  | **0**        | No Overflow   |
| **3. Positive + Positive = Negative** | 1               | 0                  | **1**        | **OVERFLOW!** |
| **4. Negative + Negative = Positive** | 0               | 1                  | **1**        | **OVERFLOW!** |

---

##### Layer 3: Applying it to $10 - 200$

In 8-bit, $10 - 200$ is treated as $10 + (-200)$.

- Binary 10: `0000 1010`
- Binary -200: `0011 1000` (The "impossible" bit pattern)

**The Math at the MSB (Bit 7):**

1. Add Bit 6: $0 + 1 = 1$. (No carry into Bit 7). **$C_{in} = 0$**.
    
2. Add Bit 7: $0 + 0 + (C_{in}=0) = 0$. (No carry out). **$C_{out} = 0$**.
    
3. **Wait!** That doesn't trigger OF... **unless** the CPU sees the "200" pattern for what it is.
    

**Correct Correction:** For a **subtraction** ($A - B$), the CPU performs $A + (\text{NOT } B) + 1$.

- $10$: `0000 1010`
    
- $200$: `1100 1000`
    
- NOT $200$: `0011 0111`
    
- Add them:
    

```Plaintext
      (Carries: 0111 0000)  <-- Notice $C_{in}$ to MSB is 1, $C_{out}$ is 0
        0000 1010 (10)
      + 0011 0111 (NOT 200)
      +         1 (The +1 for 2's complement)
      -----------
        0100 0010 (42h)
```

**The XOR Result:**

- **$C_{in}$ (into MSB):** 1
    
- **$C_{out}$ (out of MSB):** 0
    
- **Logic:** `1 XOR 0 = 1`. **OF turns ON.**


#### So all these registers are just storage devices at the end right? So can I use them for the purpose that they are not listed or defined for?
So no, you dont have to use them for the reason or purpose they are defined for. They can be used for other purpose as well, but there are some exceptions to that as well.

The General Purpose registers like AX(AH:AL), BX(BH:BL), DX(DH:DL), CX(CH:CL), can store any 16-bit data and be swapped freely with MOV
Pointer/Index Registers (SP, BP, SI, DI) are optimised for pointer operations, but can be used as general storage too.

However, we have some Special Registers:
- Segment Registers (Bus Interface Unit)
- IP (instruction pointer in BIU)
- Flags (present in the Execution Unit(EU)) 
	
	These cannot be used freely and assembly mnemonics have limited control over them.
	

#### **Control Flags (control processor behavior):**

- **TF (Trap Flag)**: Enables single-step debugging mode
- **IF (Interrupt Flag)**: Enables/disables maskable interrupts
- **DF (Direction Flag)**: Controls direction of string operations


### Pipelining in 8086

The BIU prefetches up to 6 instruction bytes and stores them in a queue while the EU executes the current instruction. This **pipelining** increases throughput because instruction fetching and execution happen simultaneously. However, the queue is flushed on JMP and CALL instructions, reducing efficiency.

## Assembly Language Example: Execution Walkthrough

Let's trace how 8086 executes this simple program:

```assembly
MOV AL, 7H      ; Move hexadecimal 7 into AL register
ADD AL, 05H     ; Add hexadecimal 5 to AL
```

### Step-by-step execution:

1. **Fetch MOV instruction**: BIU fetches instruction bytes from memory address `(CS × 16) + IP` and places them in instruction queue
2. **Decode MOV**: EU's control system decodes the instruction as "move immediate value 7H into AL register"[^1]
3. **Execute MOV**: EU transfers 7H to AL register. Now AL = 07H[^1]
4. **Update IP**: IP increments to point to next instruction[^1]
5. **Fetch ADD instruction**: While EU executed MOV, BIU already prefetched ADD instruction into queue (pipelining)
6. **Decode ADD**: Control system decodes as "add immediate value 05H to AL"[^1]
7. **Execute ADD**: ALU receives operands (AL=7H and immediate 05H) via internal buses, performs addition: 7H + 5H = CH
8. **Store result**: Result CH is written back to AL. Final AL = 0CH[^1]
9. **Update flags**: Flags are updated based on result (PF, AF, ZF, SF, CF, OF as appropriate)

The internal buses (A-BUS, B-BUS, C-BUS) transfer operands between registers, ALU, and memory interface during execution. This parallel architecture allows the EU and BIU to work independently, improving performance through overlapped fetch-execute cycles.

Live example given in [[3_MUPI_Architecture_II.pdf]]

## Machine vs Assembly Language

**Machine Language**: Binary codes the microprocessor executes directly. It's difficult because you must memorize thousands of binary instruction codes, and errors easily occur with long sequences of 1s and 0s.

**Assembly Language**: Uses 2-4 letter mnemonics (ADD, SUB, MOV, XOR) instead of binary. Assembly instructions have four fields:

- **LABEL**: Optional identifier for the instruction location
- **OPCODE**: The instruction mnemonic (e.g., ADD, MOV)
- **OPERAND**: The data or addresses the instruction works on
- **COMMENT**: Optional explanation (starts with semicolon)

Example: `NEXT: ADD AL, 07H ; Add correction factor`

**Assembler**: A program that reads assembly source code and converts it to binary machine language that the CPU can execute.

## How Addressing Works with Segments: 

### "Geography" of Memory.

It is important to distinguish between two things:

1. **Segments (The Mechanism):** The `CS`, `DS`, `SS`, `ES` registers can technically point _anywhere_. You can move them around like a flashlight in a dark room.
    
2. **The Memory Map (The Standard):** In the IBM PC standard (which 8086 is famous for), specific areas of the room were reserved for specific things. You couldn't just put your data anywhere!


#### 1) The "Flashlight" Analogy for the Registers (The Sliding Window)

1. **The Dark Room:** The **1MB Physical Memory** is a huge, dark warehouse.
    
2. **The Flashlight Beam:** The CPU can only shine a light on a **64KB chunk** of that warehouse at any one time.
    
3. **The Segment Register (The Hand):** This register determines **where the beam starts**.
    
    - If you change the Segment Register, you physically **move the beam** to a different part of the warehouse.
        
4. **The Offset (The Object):** The offset is just looking for an item _inside_ that lit-up beam.


#### 2) The Standard PC Memory Map (The "640KB" Limit)

The 1MB of address space is split into two main zones:

1. **Lower 640KB (00000h – 9FFFFh):** RAM (Random Access Memory). This is for you, the programmer.
    
2. **Upper 384KB (A0000h – FFFFFh):** Reserved System Area (Video, BIOS, ROMs).
    

Here are the specific "Fixed" Addresses you need to know for exams and interfacing:
Here is the famous **1MB Memory Map** of the 8086/8088 PC.

| **Start Addr (Hex)** | **End Addr (Hex)** | **Size** | **Name**                         | **Purpose**                                                   |
| -------------------- | ------------------ | -------- | -------------------------------- | ------------------------------------------------------------- |
| **00000**            | **003FF**          | 1 KB     | **IVT** (Interrupt Vector Table) | Holds the addresses for interrupt handlers.                   |
| **00400**            | **004FF**          | 256 B    | **BDA** (BIOS Data Area)         | Stores system variables (keyboard buffer, tick count).        |
| **00500**            | **9FFFF**          | ~640 KB  | **Conventional Memory**          | **User RAM.** This is where your OS (DOS) and programs live.  |
| **A0000**            | **BFFFF**          | 128 KB   | **Video RAM (VRAM)**             | Writing here lights up pixels/text on the screen immediately. |
| **C0000**            | **DFFFF**          | 128 KB   | **ROM Extensions**               | BIOS for Add-on cards (like Hard Drive controllers).          |
| **E0000**            | **FFFFF**          | 128 KB   | **System BIOS**                  | The Motherboard ROM. Contains the boot code.                  |

### What do we mean by Segment Registers can technically point anywhere?

#### The "4 Workers" Analogy

Imagine you have 4 workers in a warehouse (Memory). You (the programmer) hold the walkie-talkies (Registers) to tell them where to stand.

1. **CS (Code Segment) - The Reader:**
    
    - **Job:** Reads the instructions (the code).
        
    - **Where does it work?** Wherever you put your program code.
        
    - **Rule:** If you load your program at the bottom of RAM (`00000`), you set `CS=0000`. If you load it at `10000`, you set `CS=1000`.
        
2. **DS (Data Segment) - The Note Taker:**
    
    - **Job:** Reads and writes variables.
        
    - **Where does it work?** Wherever you decided to store your data.
        
    - **Rule:** Often, we put Data right after Code.
        
3. **SS (Stack Segment) - The Scratchpad:**
    
    - **Job:** Temporary storage (Stack).
        
    - **Where does it work?** Usually at the end of your memory block to keep it safe.
        
4. **ES (Extra Segment) - The Helper:**
    
    - **Job:** Helping move data (like copying strings).
        
    - **Where does it work?** Wherever the destination is.
        


#### The "Starting & Ending" Formula

Since you control them, the addresses are calculated dynamically:

- **Start Address** = `Value_in_Register` $\times$ 16 (Hex `10`)
    
- **End Address** = **Start Address** + 65,535 bytes (`FFFF`)
    

#### PROOF: The Multi-Segment Visualizer

**Try this specific experiment to see "Where they work":**

1. **Standard ".COM" Program:** Set ALL registers (`CS`, `DS`, `SS`, `ES`) to `1000`.
    
    - _Result:_ They all sit on top of each other. This is how simple programs work.
        
2. **Standard ".EXE" Program:**
    
    - Set `CS` = `1000` (Code at bottom)
        
    - Set `DS` = `2000` (Data above code)
        
    - Set `SS` = `3000` (Stack above data)
        
    - _Result:_ You will see they are now separate blocks "working" in different areas.

---

Check out [[MemoryAddressing.html]]

## Basics of Addressing in Assembly:

### 1. The Default "Marriages" (Implicit Segments)

When you write an instruction, you usually only provide the **Offset** (the address inside the segment). The CPU automatically adds the **Segment** based on these strict rules:

|**If you use this Offset...**|**The CPU ASSUMES this Segment:**|**Example Instruction**|**What CPU actually does**|
|---|---|---|---|
|**[BX]** (Base)|**DS** (Data Segment)|`MOV AX, [BX]`|`MOV AX, DS:[BX]`|
|**[SI]** (Source Index)|**DS** (Data Segment)|`MOV AX, [SI]`|`MOV AX, DS:[SI]`|
|**[DI]** (Dest Index)|**DS** (Data Segment)|`MOV AX, [DI]`|`MOV AX, DS:[DI]`|
|**[Number]** (Direct)|**DS** (Data Segment)|`MOV AX, [1000h]`|`MOV AX, DS:[1000h]`|
|**[BP]** (Base Pointer)|**SS** (Stack Segment)|`MOV AX, [BP]`|`MOV AX, SS:[BP]`|
|**[SP]** (Stack Pointer)|**SS** (Stack Segment)|`PUSH AX`|`MOV SS:[SP], AX`|
|**IP** (Instr Pointer)|**CS** (Code Segment)|_(Cannot be changed)_|_(Always Code)_|

**Crucial Takeaway:**

- If you touch data (`BX`, `SI`, `DI`, numbers), the CPU looks in **DS**.
    
- If you touch the stack (`BP`, `SP`), the CPU looks in **SS**.
    

---

### 2. The Syntax to Change It (Segment Override)

You can force the CPU to look elsewhere. This is called a **Segment Override Prefix**.

**The Syntax:**

Put the `Segment Register Name` followed by a **Colon (`:`)** before the offset.

**Example:**

Imagine `DS = 1000h` and `ES = B800h` (Video Memory).

Your `BX = 0010h`.

1. **Default:**
    
    - `MOV AX, [BX]`
        
    - CPU calculates: `DS` + `BX` -> `10000h + 0010h` = `10010h`.
        
    - _Result:_ Reads a variable from your data.
        
2. **Override (The Change):**
    
    - `MOV AX, ES:[BX]` <-- **THIS IS THE SYNTAX**
        
    - CPU calculates: `ES` + `BX` -> `B8000h + 0010h` = `B8010h`.
        
    - _Result:_ Reads a pixel from the screen!

## Addressing Modes

Addressing modes define how the CPU locates operands (data) for instructions. They optimize code size, speed, and enable flexible data manipulation.

( 
The **H** suffix stands for **hexadecimal** in 8086 assembly language notation
- `1234H` = hexadecimal value 1234₁₆ = 4660 in decimal
- `0100H` = hexadecimal value 100₁₆ = 256 in decimal    
- `2345H` = hexadecimal value 2345₁₆ = 9029 in decimal
)

### **1. Register Addressing**: Both operands are registers.

- Example: `MOV CX, DX` copies DX content to CX
- Fastest mode since no memory access needed

### **2. Immediate Addressing**: Data is part of the instruction itself.

- Example: `MOV AL, 22H` loads hexadecimal value 22 directly into AL
- Example: `MOV CX, 437BH` loads 437B into CX (43 in CH, 7B in CL)

### **3. Direct Addressing**: Instruction contains the memory address.

- Example: `MOV AL, [1234H]` loads byte from memory address DS:1234H into AL
- Physical address = (Segment × 10H) + Offset
- For words: low byte from specified address, high byte from next address

### **4. Register Indirect Addressing**: Memory address is stored in a register (BX, BP, SI, or DI).

- Example: `MOV AX, [BX]` loads word from address pointed to by BX
- If BX=1000H and DS=0100H, physical address = 01000H + 1000H = 02000H
- **BP uses SS (stack segment)** by default; others use DS

### **5. Base-plus-Index Addressing**: Combines base register (BX or BP) with index register (SI or DI).

- Example: `MOV DX, [BX+DI]` loads from address BX+DI
- Useful for arrays: BX holds array start address, DI holds element offset
- Example code: To move element 10H to element 20H in an array:

```
MOV BX, OFFSET ARRAY  ; BX points to array start
MOV DI, 10H           ; DI = offset to element 10H
MOV AL, [BX+DI]       ; Get element 10H
MOV DI, 20H           ; DI = offset to element 20H
MOV [BX+DI], AL       ; Store in element 20H
```


**Memory Access Formula**: Physical Address = (Segment Register × 10H) + Effective Address. The segment is shifted left 4 bits (multiplied by 16), then added to the offset to create a 20-bit physical address.[^1]

**Note**: Memory-to-memory transfers are not allowed (except with MOVS string instruction). You must use a register as intermediate storage.

### Advanced Addressing Modes
#### A. Register Relative Addressing

- **Concept:** You use a register plus a fixed number (displacement).

- **Syntax:** `MOV AX, [BX+4]` or `MOV AX, ARRAY[BX]`

- **How it works:** CPU adds `BX` + `4`.​

- **Use Case:** Accessing fields in a **Structure** (Struct).
    
    - Imagine a struct `Student { int ID; int Age; }`.
        
    - `BX` points to the start of the `Student`.
        
    - `ID` is at `[BX+0]`.
        
    - `Age` is at `[BX+2]` (since ID is 2 bytes).


#### B. Base Relative-Plus-Index Addressing

- **Concept:** The "Mother of all 16-bit modes." Combines Base + Index + Constant.

- **Syntax:** `MOV AX, [BX+SI+100H]`

- **How it works:** EA = `BX` + `SI` + `100H`.​

- **Use Case:** Complex data structures, like an **Array of Structs**.
    
    - `100H` = Start of the array.
        
    - `BX` = Which struct (e.g., Student #5).
        
    - `SI` = Which field (e.g., Grade).


# Instruction Set

## 1. What “instruction set” means

- A microprocessor understands only **machine code**: sequences of bits (0/1).
- An **instruction set** is the complete set of operations that a particular CPU can execute (e.g., MOV, ADD, SUB, JMP). 
- Each family of CPUs has its own **ISA (Instruction Set Architecture)** – 8086 has one ISA, ARM has another, etc.
- **Assembly language** is a human-readable form of that machine code, where you write mnemonics like `MOV AX, BX` instead of raw bits. 
- The ISA is the **interface** between software and hardware: compilers and assembly programmers target the ISA, and the CPU implements it. 

Example:

- Assembly: `ADD AX, BX`
- The CPU actually sees: a few bytes of machine code that encode “ADD”, “AX”, “BX”, and operand sizes. 

***

## 2. High‑level 8086 instruction format

8086 instructions are variable length: from 1 to 6 bytes typically. 

For 16‑bit mode (8086/8088):

- **Opcode**: 1–2 bytes – which operation (MOV, ADD, etc.). 
- **MOD‑REG‑R/M byte**: 0–1 byte – how to interpret operands (register or memory, which register, which addressing mode). 
- **Displacement**: 0–2 bytes – constant offset added to an address (8‑bit or 16‑bit). 
- **Immediate**: 0–2 bytes – constant data embedded in the instruction (e.g. `MOV AX, 1234H`). 

So a typical memory/register instruction looks like:

- Byte 1: opcode + control bits (D, W). 
- Byte 2: MOD‑REG‑R/M. 
- Extra bytes: displacement and/or immediate depending on addressing mode and instruction type. 

![[Pasted image 20260128011143.png]]

In x86/8086 architecture, **at most ONE operand can be a memory location** in a single instruction.

***

## Byte 1: opcode, D bit, W bit

In many 8086 data‑movement / arithmetic instructions, the first byte is: 

- Bits 7–2: **opcode** (6 bits) – identifies the operation (e.g. `100010` for MOV reg/mem to/from reg). 
- Bit 1: **D (Direction) bit** – decides which operand is the destination. 
- Bit 0: **W (Word) bit** – decides operand size (byte vs word). 


### D (Direction) bit

There are two operands in many instructions: one from the REG field, one from the R/M field (explained later). 

- `D = 1`: destination is the **REG** field, source is **R/M**. 
- `D = 0`: destination is the **R/M** field, source is **REG**. 

Example mental model (abstract):

- Encoded pattern says “MOV between REG and R/M”. D decides “arrow direction”:
    - D=1 → `MOV REG, R/M`
    - D=0 → `MOV R/M, REG` 


### W (Word) bit

- `W = 0`: byte operation (8‑bit). [^1][^6]
- `W = 1`: word operation (16‑bit in 16‑bit mode; 32‑bit if using 32‑bit regs in later x86). [^1][^8]

Example:

- `MOV AL, BL` → byte registers, so W=0. [^1][^7]
- `MOV AX, BX` → word registers, so W=1. [^1][^7]

***

## 4. Byte 2: MOD‑REG‑R/M

The second byte (if present) is split into fields: [^1][^4]

- Bits 7–6: **MOD** (2 bits) – addressing mode (register vs memory, and displacement size). 
- Bits 5–3: **REG** (3 bits) – which register (or sometimes opcode extension). [^1][^4]
- Bits 2–0: **R/M** (3 bits) – either register (if MOD=11) or detailed memory addressing mode (if MOD=00/01/10). 

So: `MOD REG R/M` (2 + 3 + 3 = 8 bits). 

### REG field: register codes

REG + W decides which register you refer to. Typical mapping: [^1][^6]

- For W=0 (8‑bit):
    - 000: AL
    - 001: CL
    - 010: DL
    - 011: BL
    - 100: AH
    - 101: CH
    - 110: DH
    - 111: BH [^1][^6]
- For W=1 (16‑bit):
    - 000: AX
    - 001: CX
    - 002: DX
    - 003: BX
    - 100: SP
    - 101: BP
    - 110: SI
    - 111: DI [^1][^6]

In 32‑bit mode, W=1 selects EAX, ECX, … EDI similarly. [^1][^8]

### MOD field: register vs memory, and displacement

For 16‑bit addressing in 8086: 

- `MOD = 11` (binary): **register mode** – R/M is also a register code; no memory access. 
- `MOD = 00, 01, 10`: **memory mode**:
    - 00: memory, **no displacement**, except a special case with R/M=110. [^1][^2]
    - 01: memory, **8‑bit displacement** (signed). 
    - 10: memory, **16‑bit displacement**. 


### R/M field: effective address (16‑bit mode with 20-bit addressing)

For MOD=00/01/10, R/M selects which base/index registers form the effective address: [^1][^10]

- 000: [BX + SI]
- 001: [BX + DI]
- 010: [BP + SI]
- 011: [BP + DI]
- 100: [SI]
- 101: [DI]
- 110:
    - If MOD=00: **direct address** (16‑bit displacement alone).
    - If MOD=01 or 10: [BP + disp].
- 111: [BX] 

For MOD=11, R/M has the same codes as REG (i.e., it is a register). [^1][^4]

Example for meaning:

- `MOD=00, R/M=100` → `[SI]` (contents of SI used as address). [^1][^10]
- `MOD=01, R/M=001` and an 8‑bit displacement d8 → `[BX + DI + d8]`. [^1][^10]

***

## 5. Sign extension of 8‑bit displacement

When MOD=01, the displacement is 8 bits. But the effective address calculations are 16‑bit, so the 8‑bit displacement is **sign‑extended** to 16 bits.

- Range of 8‑bit signed: $-128$ to $+127$ (80H to 7FH in hex).
- If top bit (bit 7) is 0 (0x00–0x7F), extend with 0s:
    - Example: 7FH → 007FH. 
- If top bit is 1 (0x80–0xFF), extend with 1s (two’s complement):
    - Example: 80H → FF80H; FFH → FFFFH. 

This allows short displacements (relative + or −) to be encoded compactly. 

***

## 6. Worked example 1 – decoding `8BECh`

We’re told: instruction bytes = `8B EC` (hex). [^1]

Binary:

- 8B → `10001011`
- EC → `11101100` [^1]

Step by step:

1. **Byte 1 – opcode, D, W**
    - Bits 7–2: `100010` → opcode for MOV between REG and R/M.
    - Bit 1: `1` → D=1 (destination is REG). [^1][^3]
    - Bit 0: `1` → W=1 (word/16‑bit). [^1][^6]
2. **Byte 2 – MOD, REG, R/M**
    - Bits 7–6: `11` → MOD=11, register addressing (no memory). [^1][^4]
    - Bits 5–3: `101` → REG=101 → BP (word) because W=1. [^1][^6]
    - Bits 2–0: `100` → R/M=100 → SP when MOD=11. [^1][^6]
3. Interpret with D:

- D=1 → `MOV REG, R/M` → `MOV BP, SP`. [^1][^3]

So `8B EC` encodes `MOV BP, SP`. [^1][^4]

***

## 7. Worked example 2 – `MOV AX, BX`

Goal: find machine code for `MOV AX, BX`. [^1]

We know it’s register‑to‑register, word move. For 8086 encoding of `MOV r16, r/m16` / `MOV r/m16, r16` we use opcode `100010` plus D, W bits. [^1][^4]

1. Decide D and W:

- Destination: AX
- Source: BX
- Both are word regs → W=1. [^1]
- We want `MOV REG, R/M`, with REG=AX, R/M=BX → D=1 (destination is REG). [^1][^3]

So byte 1 bits:

- Opcode: 100010
- D=1
- W=1

→ `10001011` = 8Bh. [^1][^4]

2. Byte 2: MOD‑REG‑R/M with register mode

- MOD=11 (register mode). [^1]
- REG= AX → 000. [^1][^6]
- R/M= BX → 011. [^1][^6]

Bits: `11 000 011` → `11000011` = C3h. [^1][^4]

So the full machine code: `8B C3` (two bytes) for `MOV AX, BX`. [^1][^4]

***

## 8. Special “direct address” mode: `MOV [1000H], DL`

Consider `MOV [1000H], DL`. This moves DL into memory location with offset 1000H (in the default data segment). [^1]

We need: memory destination, register source, byte move. [^1][^2]

1. Opcode/D/W:

- Operation is MOV between REG and memory. [^1]
- Source: REG (DL), destination: memory → D=0 (destination is R/M). [^1][^3]
- DL is 8‑bit → W=0. [^1][^6]
- Opcode bits for MOV: 100010. [^1][^4]

Thus byte 1: `10001000` = 88h (this pattern corresponds to MOV r/m8, r8). [^13]

2. MOD‑REG‑R/M: direct 16‑bit address

- Direct address uses MOD=00, R/M=110, and then a 16‑bit displacement that *is* the address. [^1][^5]
- REG = DL → W=0, code 010. [^1][^6]

So:

- MOD=00
- REG=010
- R/M=110

Bits: `00 010 110` → `00010110` = 16h. [^1][^4]

3. Displacement bytes: low then high (little‑endian)

- 1000H → low byte = 00H, high byte = 10H. [^1][^8]

Final encoding:

- Byte 1: 88h
- Byte 2: 16h
- Byte 3: 00h
- Byte 4: 10h

So machine code: `88 16 00 10` for `MOV [1000H], DL` (16‑bit mode). [^1][^5]

Note: The slides mention a subtlety: assembler may rewrite this using BP‑relative addressing internally, but conceptually for you as a beginner, “MOD=00, R/M=110, displacement=1000H” means **direct address**. [^1][^2]

***

## 9. How to think about encoding as a beginner

When you see instructions like `MOV reg, reg/mem` or `ADD reg/mem, reg`, mentally map them like this: [^1][^2]

1. Decide opcode pattern (which instruction).
2. Decide:
    - Are you moving between reg and reg/mem? Then you will have D and W bits. [^1][^7]
    - What is operand size? → W.
    - Which way is the arrow? REG ←→ R/M → D. [^1][^3]
3. Fill REG field from destination/source according to D. [^1][^4]
4. Decide whether the other operand is register or memory:
    - If register → MOD=11, R/M = that register. [^1][^4]
    - If memory → choose appropriate MOD/R/M combination and add displacement bytes (if needed). [^1][^10]
5. For small signed displacements, remember sign extension from 8‑bit to 16‑bit when interpreting addresses. [^1][^11]


# Revise:

![[Memory Allocation#Endianness]]

***(Check out till and including MASM in [[Introduction to assembly]] now)***

---
# Interrupts (INT)
![[Pasted image 20260223083452.png]]

## Introduction to Interrupts:
### Layer 1: Big Picture & Motivation (The "Busy Chef" Analogy)

Imagine you are a master chef (the **CPU**) working in a busy kitchen. You have a cake baking in the oven that will take 30 minutes.

You have two ways to handle this:

1. **Polling (The Amateur Way):** You stop chopping vegetables every 5 seconds, walk to the oven, stare at the cake to see if it's done, then walk back. You waste huge amounts of time walking back and forth.
    
2. **Interrupts (The Pro Way):** You set a timer and go back to chopping vegetables. When the timer rings (the **Interrupt**), you _immediately_ stop chopping, take the cake out, and then resume chopping exactly where you left off.
    

**Why do we need this?** In early or simple systems, we used **polling**—software that continuously checks: "Is a key pressed? Is a key pressed?". This wastes CPU cycles. **Interrupts** allow the hardware to "poke" the CPU only when attention is needed. This frees the CPU to execute other software (like printing a report) while waiting for slow events (like a human typing).

---

### Layer 2: Conceptual Breakdown

An interrupt system relies on four main components. Think of them as the protocol for our Chef:

1. **The Trigger (Hardware Pins/Software Instructions):**
    
    - **Hardware:** Physical pins on the chip, like **INTR** (Interrupt Request) or **NMI** (Non-Maskable Interrupt), connected to devices like keyboards or sensors.
        
    - **Software:** Instructions like `INT 21h` that your program calls intentionally.
        
2. **The Lookup (Interrupt Vector Table - IVT):**
    
    - When the "bell rings," the CPU needs to know _what to do_. It looks up a specific address in a table located in the first 1024 bytes of memory. This table holds the addresses (vectors) of the code to run.
        
3. **The Handler (Interrupt Service Routine - ISR):**
    
    - This is the specific "recipe" or small program that runs to handle the event (e.g., code that reads the key from the keyboard buffer).
        
4. **The Return (IRET):**
    
    - After the handler finishes, the CPU must go back to the _exact_ instruction it was executing before the interruption. We use a special instruction, **IRET** (Interrupt Return), to do this.
        

---

### Layer 3: Visual & Diagrammatic Reinforcement

Let's visualize the **Interrupt Vector Table (IVT)**. In Real Mode (standard for 8086/8088), this table is strictly defined.

**The Map:**

- **Location:** `00000H` to `003FFH` (First 1K of memory).
    
- **Size:** Contains 256 vectors (labeled 0 to 255).
    
- **Entry Size:** Each vector is **4 bytes**.
    
    - 2 Bytes: **IP** (Instruction Pointer - Offset)
        
    - 2 Bytes: **CS** (Code Segment - Base)
        
- **Total:** 256 vectors $\times$ 4 bytes = 1024 bytes.
    

**Key Vectors:**

- **Vector 0:** Divide Error (if you try to divide by zero).
    
- **Vector 1:** Single Step (used for debuggers).
    
- **Vector 2:** NMI (Non-Maskable Interrupt - for critical errors like power failure).
    

---

### Layer 4: Step-by-Step Walkthrough (The Interrupt Cycle) OR EFFECT OF INTERRUPTS ON THE STACK (IMPORTANT)

When an interrupt occurs (e.g., `INTR` pin goes high), the CPU doesn't just jump immediately. It follows a strict protocol to ensure it can return safely.

1. **Finish Current Instruction:** The CPU completes the instruction currently moving through the pipeline.
2. **Push Flags:** It saves the current Status Register (Flags) onto the Stack so we don't lose the state of our math operations.
3. **Disable Interrupts:** It clears the **IF** (Interrupt Flag) and **TF** (Trap Flag). This prevents _another_ interrupt from interrupting us immediately (unless it's an NMI).
4. **Push Return Address:** It pushes the current **CS** (Code Segment) and **IP** (Instruction Pointer) onto the Stack. This marks our "bookmark" to return to.
5. **Fetch Vector:** It reads the IVT. It takes the interrupt number (say, type 5), multiplies it by 4 (5 $\times$ 4 = 20), and reads the address at memory location 20.
6. **Jump:** It loads that fetched address into CS and IP. The CPU is now executing the **ISR**.
7. **Execute ISR:** The handler code runs.
8. **Return:** The ISR ends with `IRET`. This pops IP, CS, and the Flags back off the stack, restoring the CPU to its exact previous state.

---

### Layer 5: Code Example (x86 Assembly)

Here is a simple example of how you might write a custom ISR and install it. We will replace the "Divide by Zero" handler (Vector 0).

**Scenario:** We want to run a custom procedure if a divide error occurs.

```Code snippet

; ASSUME DS points to segment 0000h (IVT location)

; 1. Installation (Setup Phase)
CLI             ; Clear Interrupts - Disable them while we mess with the table! [cite: 215]
MOV AX, 0       ; Point ES to the bottom of memory
MOV ES, AX

; Save the address of OUR procedure into Vector 0 (Address 0000h)
MOV WORD PTR ES:[0], OFFSET My_Div_Handler  ; Store IP (Low Word)
MOV WORD PTR ES:[2], CS                     ; Store CS (High Word)
STI             ; Set Interrupts - Re-enable them [cite: 215]

; ... Main program code ...
MOV AX, 100
MOV BL, 0
DIV BL          ; CRASH! This triggers INT 0

; ---------------------------------------------------------
; 2. The Interrupt Service Routine (ISR)
My_Div_Handler PROC FAR
    ; Save any registers we plan to use
    PUSH AX
    PUSH DX

    ; <Insert code here to handle error, e.g., print "Error!">

    ; Restore registers
    POP DX
    POP AX

    IRET        ; SPECIAL RETURN! Pops IP, CS, and Flags 
My_Div_Handler ENDP
```

**Key Observation:** Note that we used `IRET` at the end, not `RET`. `RET` only pops the address. `IRET` pops the address **AND** the flags. If you use `RET`, your system will crash because the flags are left stuck on the stack.

---
### Layer 6: Common Pitfalls & Debugging
1. **Using `RET` instead of `IRET`**:
    
	- _Symptom:_ The program crashes or behaves erratically upon returning from an interrupt.
    - _Fix:_ Always end an ISR with `IRET` (or `IRETD` for 32-bit protected mode).
    
2. **Forgetting to Save Registers**:
    
    - _Symptom:_ Your main program's variables mysteriously change value.    
    - _Fix:_ If your ISR uses `AX` or `BX`, you _must_ `PUSH` them at the start and `POP` them at the end. The main program doesn't know the ISR ran, so it expects registers to stay untouched.
    
3. **Blocking Interrupts for Too Long**:
	
	- _Symptom:_ The system feels "sluggish" or misses keystrokes.
    - _Fix:_ Keep your ISRs short and fast. Do the minimum work needed and return. If you stay in an ISR too long with interrupts disabled, you miss other critical events.

---
### `IRET` and `IRETD` Commands
#### Layer 1: The "Time Machine" Analogy

Imagine you are playing a video game (the Main Program). Suddenly, the phone rings (Interrupt). You pause the game to answer the phone.

When you finish the call, you don't just want to unpause the game. You need to make sure:

1. You are at the exact same pixel (Location).    
2. Your health, ammo, and score are exactly the same as before (State).

- **`RET` (Standard Return):** Puts you back at the location, but your "health/ammo" (Flags) might be corrupted by whatever happened during the phone call.
    
- **`IRET` (Interrupt Return):** A time machine that restores **Location + State**.

#### Layer 2: The Definition (`IRET` vs `IRETD`)
Just like we have Real Mode (16-bit) and Protected Mode (32-bit), we have two versions of this instruction.

|**Instruction**|**Full Name**|**Mode**|**Register Width**|
|---|---|---|---|
|**`IRET`**|Interrupt Return|**Real Mode** (16-bit)|Pops `IP`, `CS`, `FLAGS`|
|**`IRETD`**|Interrupt Return **Double**|**Protected Mode** (32-bit)|Pops `EIP`, `CS`, `EFLAGS`|

**Note:** Your slides specifically mention that these instructions are used **only** with software or hardware interrupt service procedures. You cannot use them for normal subroutines (functions).


#### Layer 3: The Stack Mechanics (Visualizing the Pop)
When you execute `IRET`, the CPU performs three distinct "Pop" operations from the stack hardware.

**The Sequence (16-bit `IRET`):**

1. **Pop IP (Instruction Pointer):** Restores the "Offset" of the next instruction.
2. **Pop CS (Code Segment):** Restores the "Segment" of the next instruction.
    - _Together, CS:IP tells the CPU exactly where it was in the code._
3. **Pop FLAGS:** Restores the Status Register (Zero Flag, Carry Flag, Interrupt Flag, etc.).

**The Sequence (32-bit `IRETD`):**

It does the exact same thing, but it pops the extended 32-bit versions: `EIP` and `EFLAGS`.

#### Layer 4: The "Manual" Equivalent
Your slides offer a fascinating insight: You could _theoretically_ simulate an `IRET` using two other instructions.

Since `IRET` restores flags and then returns far, it is logically equivalent to:

```Code snippet
; The Manual Way (Do not actually do this in an ISR!)
POPF        ; Pop the top of stack into the Flags register
RETF        ; Return Far (Pop IP, then Pop CS)
```

**Why don't we use this manual method?**

Because interrupts are asynchronous. If an NMI (Non-Maskable Interrupt) hits _between_ the `POPF` and the `RETF`, your system state could be corrupted. `IRET` does it all atomically (in one unbreakable step).

### Check for Understanding

**Summary:**

Interrupts are hardware or software signals that pause the main program to handle urgent events. They use a **Vector Table** to find the address of the code to run. The CPU automatically saves its state (Flags, CS, IP) onto the stack before jumping, and the **IRET** instruction restores that state so the main program never knows it was interrupted.

1. **`RET`**: Pops Return Address (IP/CS). Used for `CALL`.
2. **`IRET`**: Pops Return Address (IP/CS) **+ Flags**. Used for `INT`.
3. **`IRETD`**: The 32-bit version for Protected Mode.

**Quick Quiz:**

1. What is the difference between `INTR` and `NMI`?    
2. If I trigger **INT 5**, at which memory address does the CPU look for the vector? (Hint: Multiply by 4).
3. Why does the CPU automatically clear the Interrupt Flag (IF) when an interrupt starts?
4. If you accidentally write a normal `RET` at the end of your Interrupt Service Routine instead of `IRET`, what specific register remains "stuck" on the stack, eventually causing a stack overflow crash?

**Answers:**

1. `INTR` can be masked (ignored) by software (using CLI); `NMI` (Non-Maskable Interrupt) cannot be ignored.
2. Address `00014H` (5 $\times$ 4 = 20, which is 14 in Hex).
3. To prevent a new interrupt from interrupting the current one immediately, which could cause a stack overflow if they happen too fast.
4. _(Hint: `RET` pops 4 bytes (CS+IP). `IRET` pops 6 bytes (CS+IP+Flags). What was left behind?)_

---
## What does an Input Interrupt takes in as a value?

To clear up the biggest misconception right away: **the keyboard does not send ASCII or Unicode to the computer.** The conversion to those standard codes happens _after_ the interrupt, entirely in software.

When you press a key, the hardware deals purely in physical locations, not letters. Here is the exact step-by-step journey of a keystroke, from the moment your finger pushes down to the moment a letter appears on your screen.

### 1. The Hardware Trigger: Scancodes

When you press a key, an electrical circuit closes on the keyboard's internal matrix. A tiny microcontroller inside the keyboard detects this specific coordinate and generates a raw, hardware-specific number called a **Scancode** (specifically, a "Make Code"). When you release the key, it sends a "Break Code."

For example, pressing the 'A' key might send a scancode of `0x1E`. The keyboard has no idea what 'A' is; it just knows "the key in the second row, first column was pressed."

### 2. The Hardware Interrupt

The keyboard sends this scancode to the computer's motherboard (via USB or historically, a PS/2 controller).

1. The motherboard's I/O controller receives the scancode and immediately signals the computer's Programmable Interrupt Controller (PIC).
    
2. The PIC sends a hardware **Interrupt Request (IRQ)** directly to the CPU.
    
3. The CPU immediately halts whatever program it is currently running. To ensure it doesn't lose its place, it pushes its current state (the Instruction Pointer, Code Segment, and Status Flags) onto the stack.
    
4. The CPU then consults the **Interrupt Vector Table (IVT)** to find the memory address of the specific piece of code meant to handle keyboard inputs, known as the Interrupt Service Routine (ISR) or keyboard driver.
    

### 3. The Software Translation (Where ASCII comes in)

Now that the CPU has jumped into the OS's keyboard driver, the software takes over.

1. The driver reads the raw scancode from a specific hardware I/O port.
    
2. The driver looks at the current state of modifier keys (is Shift held down? Is Caps Lock on?).
    
3. The driver uses a lookup table based on your configured keyboard layout (US QWERTY, Dvorak, French AZERTY, etc.).
    
4. **The Translation:** The driver maps that physical scancode (`0x1E`) into a standardized digital encoding. If no modifiers are pressed, `0x1E` becomes the ASCII value `97` (lowercase 'a'). If Shift is held, it becomes ASCII `65` (uppercase 'A').
    

The OS then takes that ASCII/Unicode value and puts it into an input buffer for your current active application (like a text editor or a game) to read.

---

### Why ASCII and Not Other Standards?

Early computers couldn't talk to each other because every manufacturer used their own arbitrary binary codes for letters. A standardized character encoding was desperately needed. Here is how the standard codes evolved:

#### 1. Baudot Code (The Predecessor)

Invented in the 1870s for telegraphs, this was a 5-bit code. 5 bits only gives you 32 possible combinations ($2^5 = 32$). Because the English alphabet has 26 letters, there wasn't enough room for numbers and punctuation. They had to use special "shift" characters to toggle the machine between "letter mode" and "number mode." It was highly inefficient for computing.

#### 2. EBCDIC (The Rival)

Created by IBM for its massive mainframes in the 1960s, EBCDIC (Extended Binary Coded Decimal Interchange Code) was an 8-bit standard. However, it was famously frustrating for programmers. The letters weren't entirely sequential! The binary values for A through I were sequential, but then there was a gap before J through R. Sorting data alphabetically in EBCDIC required complex workarounds.

#### 3. ASCII (The Victor)

ASCII (American Standard Code for Information Interchange) was developed in the 1960s alongside EBCDIC but was largely based on teleprinter standards. It was a 7-bit code ($2^7 = 128$ characters), which perfectly fit the English alphabet (upper and lower case), numbers 0-9, punctuation, and control characters (like "Carriage Return" or "Escape").

ASCII won the standard war because:

- **Sequential Logic:** Letters were perfectly sequential. 'A' is 65, 'B' is 66, 'C' is 67. If you wanted to check if a character was a capital letter in code, you just had to check if its value was between 65 and 90.
    
- **Case Toggling:** The difference between an uppercase letter and its lowercase counterpart was exactly one bit. (e.g., 'A' is binary `01000001`, 'a' is `01100001`). This made writing compilers and string manipulation incredibly fast on early hardware.
    

#### 4. Unicode (The Modern Standard)

As computers went global, ASCII's 128 characters weren't enough to handle Russian Cyrillic, Chinese Kanji, Arabic, or emojis. Unicode (specifically UTF-8 encoding) was introduced to solve this. UTF-8 is brilliant because it is perfectly backwards-compatible with ASCII. The first 128 characters of Unicode are identical to the 128 characters of ASCII, but Unicode can dynamically expand up to 4 bytes to represent over a million different characters.

***(Cover [[Introduction to assembly#Interrupts]])***

# A Few Examples

## Taking in the Character Input and storing it into AL:
We use AH = 01H with INT 21H, and it stores the value in AL

### subtract from the ASCII
```code 
MOV AH, 01H
INT 21H
; we now have the ASCII of input in AL
SUB AL, 30H ; convert to numerical value
```

### subtract '0'
```code
MOV AH, 01H
INT 21H

SUB AL, '0'
```

### Mask the Lower BITS
```code
MOV AH, 01H
INT 21H
AND AL, 0FH
```
```TEXT
E.g.
'7' = 37H
37H AND 0FH = 07
Fast method, Input has to be a digit
```

### Multi-Digit Input in ASCII
```code
.model small
.stack 100h

.data
    prompt db "Enter a number: $"
    
    ; --- THE BUFFER STRUCTURE ---
    buffer_max db 6         ; Max 5 digits + 1 for Carriage Return (max 16-bit int is 65535)
    buffer_len db ?         ; DOS will write the actual typed length here
    buffer_str db 6 dup(?)  ; The actual string of ASCII chars goes here

    result dw 0             ; Variable to store our final converted integer

.code
main proc
    ; Initialize Data Segment
    mov ax, @data
    mov ds, ax

    ; --- 1. PROMPT THE USER ---
    lea dx, prompt          ; Load Effective Address of prompt
    mov ah, 09h             ; DOS function: Print String
    int 21h                 ; Call DOS interrupt

    ; --- 2. TAKE STRING INPUT ---
    lea dx, buffer_max      ; DX must point to the FIRST byte of the buffer structure
    mov ah, 0Ah             ; DOS function: Buffered Input
    int 21h                 ; Call DOS interrupt

    ; --- 3. CONVERT ASCII TO INTEGER ---
    lea si, buffer_str      ; Point Source Index (SI) to the actual ASCII characters
    mov cl, buffer_len      ; Load the actual length into CL for our loop counter
    mov ch, 0               ; Clear CH so CX is exactly the string length
    
    mov ax, 0               ; AX will be our Running Total (start at 0)
    mov bx, 10              ; BX is our multiplier (10)

convert_loop:
    ; Multiply current running total (AX) by 10
    mul bx                  ; AX = AX * 10

    ; Get the next ASCII character from the buffer
    mov dl, [si]            ; Read 1 byte from memory at address SI
    sub dl, '0'             ; Subtract ASCII 48 to get the real numeric value
    mov dh, 0               ; Clear DH so DX just contains our single digit (0-9)
    
    ; Add the new digit to the running total
    add ax, dx              ; AX = (AX * 10) + new_digit

    ; Move to the next character in the buffer
    inc si                  ; Move pointer forward by 1 byte
    
    ; Loop handles CX decrement and jump
    loop convert_loop       ; Decrement CX. If CX != 0, jump back to convert_loop

    ; --- 4. STORE THE RESULT ---
    mov result, ax          ; Store the final integer from AX into memory

    ; Exit Program cleanly
    mov ah, 4Ch
    int 21h
main endp
end main
``` 

# Real Mode vs. Protected Mode
They are the modes in which the CPU works in the x86 computer architecture.

### Layer 1: The Analogy (The "Wild West" vs. The "Gated Community")

- **Real Mode (The Wild West):**
    
    Imagine a small village where everyone knows everyone. You can walk into anyone's house, open their fridge, and eat their food. There are no locks.
    
    - **Pros:** Simple, fast, total freedom.
    - **Cons:** If the village idiot (a bad program) burns down a house, the whole village might burn down.
    - **Who uses it?** MS-DOS, early BIOS, and simple embedded microcontrollers.
    
- **Protected Mode (The Gated City):**
    
    Now imagine a modern apartment complex. You have a key card. It only opens _your_ door. You cannot enter your neighbor's apartment. There is a security guard (the hardware) watching the cameras.
    
    - **Pros:** Safe. If your neighbor causes a fire, the sprinklers isolate it to just their room.
        
    - **Cons:** Complex. You need permission for everything.
        
    - **Who uses it?** Windows, Linux, macOS, Android.
        

---

### Layer 2: Conceptual Breakdown (The Specs)

According to the comparison table in your file, here are the technical differences:

1. **Memory Reach (Addressing):**
    
    - **Real Mode:** Can only see **1 MB** of RAM. Even if you install 32 GB, it ignores everything above the first 1 MB.
    - **Protected Mode:** Can address **16 MB** (on a 286) up to **4 GB+** (on 386 and later).
    
2. **Safety (Memory Protection):**
    
    - **Real Mode:** **No Protection.** Your program can accidentally write over the Operating System code, causing a crash.    
    - **Protected Mode:** **Protection Available.** If a program tries to access memory it doesn't own, the CPU throws a "General Protection Fault" (GPF) and terminates just that program.
    
3. **Multitasking Support:**
    
    - **Real Mode:** Single-tasking. One program runs at a time.
        
    - **Protected Mode:** Supports **Virtual Memory** (up to 64 TB!) and hardware context switching, allowing you to run Spotify, Chrome, and Word all at once.
        

---

### Layer 3: Visualizing the Difference

Let's look at how they handle **Interrupts**, which is a key distinction mentioned in your slides.

- **Real Mode (IVT):**
    
    - The "Phone Book" (Vector Table) is **fixed** at memory address `00000H`.    
    - Each entry is simple: just **4 bytes** (CS:IP address).
	
- **Protected Mode (IDT):**
	
    - The "Phone Book" is replaced by an **Interrupt Descriptor Table (IDT)**.
    - Each entry is huge: **8 bytes** (descriptors) that contain not just the address, but also security permissions (e.g., "Can a user program call this? Or only the OS?").

---

### Layer 4: Why do we care? (The "Boot" Process)

You might ask: _"If Real Mode is so old and unsafe, why do we still learn it?"_

**The Secret:** Every x86 Intel processor (even the Core i9 in your laptop) **wakes up in Real Mode**.

1. **Power On:** The chip acts like an old 8086 (1 MB limit, no security). This ensures backward compatibility.
2. **BIOS/Bootloader:** It initializes the hardware in this simple mode.
3. **Switch:** The Operating System (Windows/Linux) executes a special instruction to **switch** the CPU into Protected Mode to unlock the full power and memory.


# File Operations using `INT24H`:
![[Introduction to assembly#File Operations in using `INT24`]]

# Some important interrupts:
[[Introduction to assembly#`INT24H` Command]]

# Interfacing 

![[21 Lecture_1.pdf]]

## What is Dual In-Line package:

A **Dual Inline Package (DIP)** is a type of housing for integrated circuits (ICs) and electronic components. It is one of the most recognizable forms of microchips, characterized by its rectangular body and two parallel rows of electrical connecting pins.

### Key Characteristics

- **Physical Structure:** The package features a rectangular housing, usually made of molded plastic or ceramic.
    
- **The Pins:** Two rows of metal pins extend downward from the long sides of the package. These pins are spaced at a standard interval—most commonly **0.1 inches (2.54 mm)**—which makes them perfectly compatible with breadboards and prototype strips.
    
- **Mounting:** DIPs are designed for **through-hole mounting**. The pins are inserted into holes drilled in a printed circuit board (PCB) and then soldered on the opposite side. Alternatively, they can be inserted into a **DIP socket**, allowing for easy replacement without soldering.
---

### Anatomy and Identification

To ensure the chip is installed correctly, DIPs have specific orientation markers:

1. **The Notch:** Most DIPs have a U-shaped notch at one end.
    
2. **Pin 1:** When looking at the chip from the top with the notch facing up, Pin 1 is the top-left pin. The numbering then continues down the left side and back up the right side in a counter-clockwise direction.

## Multiplexing of Address and Data in AD (1 to 20) pins:


Multiplexing in the 8086 and 8088 processors is a clever engineering "hack" to save on the physical pin count of the chip. Since a 40-pin **Dual Inline Package (DIP)** is relatively small, there aren't enough pins to give every address and data line its own dedicated spot.

Here is the breakdown of how the 8086/8088 executes this multiplexing using **time-division**.

### The Mechanism: Time-Division Multiplexing

The processor shares the same physical pins ($AD_0$ to $AD_{15}$) for two different purposes by using different "time slots" within a single **Bus Cycle**. A standard bus cycle consists of four clock states, labeled **$T_1$, $T_2$, $T_3$, and $T_4$**.

#### 1. The Address Phase ($T_1$)

During the very first clock cycle ($T_1$), the processor places the **memory address** on the bus. At this moment, the pins act strictly as an Address Bus.

- **The ALE Signal:** To tell the rest of the system that "this is an address," the processor pulses a special pin called **ALE (Address Latch Enable)** to HIGH.

#### 2. The Latching Step

Because the address will disappear from the pins in the next clock cycle, the system uses external hardware (typically a **74LS373 Latch**) to "catch" and hold that address.

- When ALE goes HIGH, the latch opens.
- When ALE goes LOW (at the end of $T_1$), the latch "freezes" the address on its output pins, providing a stable address to the memory chips for the rest of the cycle.

#### 3. The Data Phase ($T_2, T_3, T_4$)

After $T_1$, the processor stops sending the address and clears the bus. The pins now switch functions to become the **Data Bus**.

- **Read Operation:** The memory chip (seeing the latched address) puts data onto the $AD$ pins, and the processor reads it during $T_3$.
- **Write Operation:** The processor puts its own data onto the $AD$ pins to be sent to memory.

## Basically this is what happens in Multiplexing of AD Lines

Imagine a single **Bus Cycle** (the time it takes to read or write one piece of data) as a four-act play:

### The 4-State Sequence ($T_1$ to $T_4$)

- **$T_1$ (The Announcement):** The bus acts only as an **Address Bus**. The 8086 puts the memory address on the $AD$ lines and screams "Hey, I'm talking to THIS address!" by pulsing the **ALE** signal.
    
- **$T_2$ (The Changeover):** This is the "breathing room" state. The 8086 stops sending the address so the pins can get ready to receive or send data. If it's a "Read" operation, the bus goes into a high-impedance state (basically disconnecting) so the memory chip can take control of the lines without a "tug-of-war" (bus contention).
    
- **$T_3$ (The Payload):** The bus now acts strictly as a **Data Bus**. During a Read, the processor waits for the data to stabilize on the lines.
    
- **$T_4$ (The Wrap-up):** The processor actually "latches" (captures) the data into its internal registers, and the cycle ends.


## TEST, WAIT and READY pins in 8086/8088

### Layer 1: Big Picture & Motivation

Imagine a Chef (CPU) and a slow assistant (Memory).

- **The Problem:** The Chef yells "Give me the onions!" and immediately reaches out his hand. But the assistant hasn't finished chopping them yet. The *Chef grabs air* and tries to cook it. The recipe is ruined.
    
- **The Solution:** We need a way for the assistant to say "Wait!" until the onions are ready.
    
    - **READY** is used for slow "waiters" like Memory and I/O.
        
    - **$\overline{TEST}$ and WAIT** are used for "specialist" waiters, specifically the 8087 Math Coprocessor.

---

### Layer 2: Conceptual Breakdown

1. **The READY Pin (Hardware Handshake):** This is an **input** pin to the 8086. It allows slow memory or I/O devices to tell the CPU, "I'm not finished with the data yet; please don't move to the next step."
    
2. **The $\overline{TEST}$ Pin (The Sensor):** This is also an **input** pin. It doesn't do anything by itself. It is only "checked" when the CPU executes a specific instruction. It's like a sensor that tells the CPU if an external specialist (like a math chip) is still busy.
    
3. **The WAIT Instruction (The Command):** This is the software command that tells the CPU: "Stop right here. Look at the $\overline{TEST}$ pin. If it is 'High' (Logic 1), keep waiting. Once it goes 'Low' (Logic 0), you may proceed."


![[22 Lecture2.pdf]]

## What is a co-processor?
Think of a **Coprocessor** as a specialized tool in a workshop.

- The **CPU** is like a high-end Swiss Army Knife—it can do almost anything (cut, screw, saw), but it’s not the best at any one specific task.
    
- The **Coprocessor** is like a specialized power saw. It can only do one thing (cut wood), but it does it 100 times faster than the Swiss Army Knife ever could.

### Layer 1: Big Picture & Motivation

Think of a **Coprocessor** as a specialized tool in a workshop.

- The **CPU** is like a high-end Swiss Army Knife—it can do almost anything (cut, screw, saw), but it’s not the best at any one specific task.
- The **Coprocessor** is like a specialized power saw. It can only do one thing (cut wood), but it does it 100 times faster than the Swiss Army Knife ever could.    

In the early days, CPUs were slow at complex math and moving large amounts of data. Intel created "helper" chips to take over these specific, heavy-duty chores so the main CPU could focus on managing the system.

---
### Layer 2: Intel’s 8086 Coprocessor Lineup

For the 8086 and 8088 microprocessors, there were two primary types of specialists:

#### 1. The Arithmetic (Numeric) Coprocessor: The 8087

- **The Role:** This was the most famous coprocessor, often called the **Numeric Processor Extension (NPX)**.
    
- **The Specialty:** While the 8086 could only easily handle whole numbers (integers), the 8087 was a master of **Floating-Point** math (decimals), square roots, and trigonometry.
    
- **The Benefit:** It could perform these complex calculations many times faster than the 8086 could using software alone.
    

#### 2. The I/O Processor: The 8089

- **The Role:** The **Input/Output Processor (IOP)**.
    
- **The Specialty:** Its job was to manage high-speed data transfers between the memory and peripheral devices (like disk drives) without the 8086 having to micromanage every single byte.
    
- **The Benefit:** This freed up the 8086 to keep executing programs while the 8089 handled the "shipping and receiving" of data in the background.
    

---

### Layer 3: Is the Terminology Still Used?

**Yes and No.** The term "coprocessor" is still used in engineering, but you rarely hear a regular computer user say it today. Why? Because the specialists moved **inside** the main chip.

1. **Integration:** Starting with the **80486DX**, Intel built the arithmetic coprocessor directly onto the same piece of silicon as the main CPU.
    
2. **Internal Units:** In modern CPUs, what we used to call "coprocessors" are now referred to as **Execution Units** or **Instruction Extensions**. Examples include:
    
    - **MMX (Multimedia Extensions):** Specialized units for handling video and audio data.
        
    - **SSE/AVX (Streaming SIMD Extensions):** Units that perform "Parallel Processing," doing math on multiple pieces of data at the exact same time.

---
### Layer 4: Modern Day "Coprocessors"

While they are often called by different names now, we actually use more coprocessors today than ever before:

- **GPU (Graphics Processing Unit):** The ultimate modern coprocessor. It handles all the heavy math for 3D games and video editing, leaving the CPU free to handle the operating system.
	
- **NPU (Neural Processing Unit):** A very common term today for "AI Coprocessors." These are specialized to handle the specific types of math needed for Artificial Intelligence and Machine Learning.
    
- **TPM (Trusted Platform Module):** A security coprocessor that handles encryption and prevents your computer from being tampered with.


![[23 Lecture3.pdf]]

## What is this Co-processors Mode of operations that are mentioned here? 

To understand **coprocessor operations**, we have to look at how a single CPU handles tasks that are "above its pay grade."

In the 8086 era, the main microprocessor was great at moving data and basic logic, but it was relatively slow at complex floating-point math (like $sin(x)$, square roots, or large decimal multiplications). Intel created the **8087 Numeric Processor Extension (NPX)**—the "Math Coprocessor"—to help. (The Co-processor)

But there’s a catch: **The 8087 doesn't have its own connection to the memory.** It has to "hitchhike" on the 8086's bus. This is why **Maximum Mode** is required.

### Layer 1: Big Picture & Motivation

Think of the **8086** as a **General Manager** and the **8087** as a **Specialist Accountant**.

- The General Manager *(8086) reads the mail* (instructions from memory).
    
- If the mail says "Pay the taxes" (a math instruction), the *Manager can't do it quickly. He hands the ledger to the Accountant (8087).*
    
- The *Accountant needs to use the desk (the System Bus)* to do the work.
    
- **Maximum Mode** is the set of rules that allows the Manager and Accountant to share the same desk without bumping into each other.

---
### Layer 2: Conceptual Breakdown (The "Max Mode" Secret)

When the 8086 is in **Maximum Mode**, it provides three specific features that allow coprocessors to work:

1. **The Request/Grant ($RQ/GT$) Pins:** Instead of a simple "Hold" signal, these pins allow a coprocessor to politely ask, "Can I use the bus?" and the 8086 to reply, "Yes, I'm stepping aside now."
    
2. **Instruction Queue Status ($QS_0, QS_1$):** The 8087 needs to know exactly what the 8086 is doing. These pins tell the coprocessor, "I just fetched an instruction," or "I'm executing from my internal buffer." This keeps both chips "in sync."
    
3. **The Bus Controller (8288):** Because the 8086 is busy coordinating with the coprocessor, it stops generating memory signals. The 8288 chip takes over that manual labor so the two processors can focus on "talking" to each other.

## The Minimum Mode

### Layer 1: Big Picture & Motivation

In a Minimum Mode system, we are trying to save money and space.

- **The Goal:** Build a computer with the fewest chips possible.
    
- **The Mechanism:** By setting **Pin 33 ($MN/\overline{MX}$)** to **High (+5V)**, the 8086 changes the function of 8 specific pins (Pins 24–31) to become a "Command Center."
    
- **Analogy:** Think of the CPU as a DIY homeowner. Instead of hiring a contractor (the 8288 Bus Controller) to manage the plumbers and electricians, the homeowner does all the wiring and piping themselves.
    

---

### Layer 2: The Pin-Out Breakdown (Pins 24–31)

When you flip that switch to Minimum Mode, these pins take on their "Min Mode" identities. Let's look at what each one controls:

|**Pin #**|**Name**|**Description**|
|---|---|---|
|**24**|**$\overline{INTA}$**|**Interrupt Acknowledge:** The CPU tells an external device, "I heard your interrupt request, go ahead and send your vector number."|
|**25**|**ALE**|**Address Latch Enable:** The most important pin! It pulses high to tell external latches (like the 74LS373) to "freeze" the address currently on the multiplexed bus.|
|**26**|**$\overline{DEN}$**|**Data Enable:** Acts like a gatekeeper for the data buffers. It tells them when it's safe to let data onto the system bus.|
|**27**|**$DT/\overline{R}$**|**Data Transmit/Receive:** Tells the buffers which way the traffic is moving. High = CPU is sending; Low = CPU is receiving.|
|**28**|**$M/\overline{IO}$**|**Memory/IO:** Tells the system if the address on the bus is for a Memory chip (High) or an Input/Output device (Low). _Note: On the 8088, this is reversed ($IO/\overline{M}$)._|
|**29**|**$\overline{WR}$**|**Write:** The "Save" button. When this goes Low, the CPU is writing data to memory or an I/O port.|
|**30**|**HLDA**|**Hold Acknowledge:** The CPU says "Okay, I've stopped using the bus," in response to a HOLD request.|
|**31**|**HOLD**|**Hold Request:** An external device (like a DMA controller) asks the CPU, "Can I borrow the bus for a second?"|

---

### Layer 3: Visual & Diagrammatic Reinforcement

In Minimum Mode, the system architecture looks very "direct."

Observe how the **ALE** signal connects directly to the "Latch" to separate the Address from the Data.

---

### Layer 4: Step-by-Step Bus Cycle

How does the CPU actually "Control" a memory read in Minimum Mode?

1. **Start (T1):** The CPU puts the **Address** on the bus. It pulses **ALE** High. The Latch grabs the address and holds it.
    
2. **Direction (T2):** The CPU sets **$DT/\overline{R}$** to Low (Receive). It switches the bus pins from "Address mode" to "Data mode."
    
3. **The Command (T3):** The CPU pulls **$\overline{RD}$** (Read) and **$\overline{DEN}$** Low. This tells the Memory chip: "Put your data on the bus now!"
    
4. **The Finish (T4):** The CPU "samples" (reads) the data, then sets all signals back to High/Inactive.
    

---

### Layer 5: "Syntax" and Restrictions

**HARDWARE SYNTAX: Minimum Mode**

- **Pin 33 ($MN/\overline{MX}$):** Must be connected to **Vcc (+5V)**.
    
- **Address Latching:** You **MUST** use an external latch (e.g., 8282 or 74LS373) because the address disappears from the pins halfway through the cycle.
    

**RESTRICTIONS:**

- **No 8087/8089:** You cannot use Intel's coprocessors. They require "Status Bits" ($S_0, S_1, S_2$) which are **only** available in Maximum Mode.
    
- **Single Master:** This mode is terrible for multi-processor systems because it lacks the advanced "Bus Lock" and "Status" signals needed for coordination.
    
- **No 8288:** Do **not** connect an 8288 Bus Controller in this mode; the signals will conflict and potentially damage the chips.


## Clock Generators and Crystal Oscillators:
Just as your heart pumps blood at a steady rhythm to keep your body synchronized, a microprocessor needs a precise, repeating electrical pulse to coordinate its billions of tiny switches.

We will explore how this heartbeat is created using **Crystal Oscillators** and the **8284A Clock Generator**, specifically for the 8086/8088 systems, and then see how this has evolved in the modern era.

---
### Layer 1: Big Picture & Motivation

Imagine a massive rowing team with 40 rowers. If everyone rows whenever they feel like it, the boat goes nowhere and the oars hit each other. You need a **coxswain** shouting "Stroke! Stroke!" to make sure every oar enters the water at the exact same moment.

In a computer:

- The **Crystal** is the drummer providing the raw rhythm.
    
- The **Clock Generator (8284A)** is the coxswain who shapes that rhythm into a perfect command for the "rowers" (the CPU, memory, and I/O).

---
### Layer 3: Visual & Diagrammatic Reinforcement

To understand the 8284A, we must look at how it sits between the raw crystal and the 8086.
![[Pasted image 20260302083707.png]]

---
### Layer 2: Conceptual Breakdown

The clocking system consists of three main building blocks:

1. **The Quartz Crystal:** A physical piece of quartz that vibrates at a very specific frequency when electricity is applied. It provides the "raw" high-speed rhythm.
    
2. **The Oscillator Section (Inside 8284A):** This circuit keeps the crystal vibrating and outputs a square wave at the crystal's natural frequency (OSC output).
    
3. **The Shaper & Divider (Inside 8284A):** The 8086 is a "picky" eater. It doesn't just want a fast clock; it wants a clock that is "High" for exactly 1/3 of the time and "Low" for 2/3 of the time (a **33% duty cycle**). The 8284A divides the crystal frequency by **3** to create this specific signal.

---
### Layer 4: Step-by-Step Walkthrough

Here is the sequence of events that creates the 8086's heartbeat:

1. **Oscillation:** You connect a 15 MHz crystal to the **X1** and **X2** pins of the 8284A.
    
2. **Raw Output:** The 8284A creates a 15 MHz square wave available at the **OSC** pin.
    
3. **Frequency Division:** An internal "Divide-by-3" counter takes that 15 MHz and turns it into 5 MHz.
    
4. **Pulse Shaping:** The circuit ensures the 5 MHz signal is high for one-third of each cycle, creating the **CLK** signal for the 8086.
    
    +1
    
5. **Peripheral Clocking:** A second "Divide-by-2" stage takes the CLK and creates **PCLK** (2.5 MHz), which is a slower, 50% duty cycle clock for older, slower peripheral chips.
    
6. **Synchronization:** Simultaneously, the 8284A monitors the **RES** (Reset) and **RDY** (Ready) pins to ensure that when you press "Reset," the signal is perfectly aligned with the clock so the CPU doesn't get confused.
    

---

### Layer 5: Hardware Example & "Syntax"

To build this, you follow these hardware "syntax" rules:

**HARDWARE CONFIGURATION: 8284A to 8086**

- **Crystal Selection:** Frequency must be exactly **3x** the desired CPU speed (e.g., use 24 MHz crystal for an 8 MHz 8086).
    
- **Clock Connection:** `8284A Pin 8 (CLK) -> 8086 Pin 19 (CLK)`.
    
    +1
    
- **Source Select:** `Pin 13 (F/C)` tied to **GND** to use the local crystal.
    

**RESTRICTIONS:**

- **No Direct Crystal:** You **cannot** connect a crystal directly to an 8086; it lacks the internal circuitry to divide the frequency and shape the duty cycle.
    
- **Voltage Limits:** The 8284A requires a stable **+5.0 V** supply.
    

---

### Layer 6: Modern Situation (Current CPUs)

How has this changed in the age of Intel Core i9s and AMD Ryzens?

1. **Integration:** The 8284A is "extinct." Modern CPUs have integrated the clock generator and synchronization logic **inside** the main processor die.
    
2. **PLL (Phase-Locked Loops):** Instead of requiring a crystal that is 3x the speed of the CPU, modern CPUs use a single, low-frequency "Base Clock" (usually **100 MHz**). An internal circuit called a **PLL** acts like an electronic multiplier, turning that 100 MHz into 5,000 MHz (5 GHz).
    
3. **Dynamic Frequency Scaling:** Unlike the 8086, which ran at one fixed speed, modern CPUs change their "heartbeat" constantly. If you are just typing, the clock slows down to save power; if you start a game, the "coxswain" speeds up the rowing to maximum.
    

---

### Closure & Reinforcement

**Does this make sense?** Is it clear why the 8284A was necessary to "translate" the crystal's raw vibration into a language the 8086 could understand?

**Summary of Key Takeaways:**

- **Crystal:** The raw frequency source.
    
- **8284A:** The translator that divides the frequency by 3 and shapes it for the 8086.
    
- **CLK Pin:** The main 33% duty cycle heartbeat.
    
    +1
    
- **Modern Era:** Everything is now internal, using PLLs to multiply speed rather than external dividers to reduce it.
    

**Self-Test Questions:**

1. If I want my 8086 to run at 10 MHz, what frequency crystal should I plug into the 8284A?
    
2. What is the duty cycle percentage required by the 8086 clock input?
    
3. Why did the 8284A provide a separate **PCLK** signal?
    


## But how does CPU Uses this to start and fetch first instruction from the state of complete nothingness?
### Layer 1: Big Picture & Motivation

When you flip the power switch, the CPU is in a state of "amnesia." It doesn't know what its job is yet. To fix this, Intel designed the chip to always wake up and look in the exact same place for its first instruction.

- **The Problem:** How does a "blank" chip know where to start?
    
- **The Solution:** The **RESET** signal. It forces the CPU to a specific "Home" address.
    
- **Analogy:** Imagine a chef who, every morning when the alarm goes off, is magically teleported to the exact same spot in the kitchen where the "To-Do" list is always taped.
    

---

### Layer 2: Conceptual Breakdown

To get that first instruction, the CPU must perform a **Read Bus Cycle**. This requires four distinct heartbeats, which we call **T-States** (T1, T2, T3, and T4).

+1

1. **The Wake-Up Call (RESET):** When the `RESET` pin is held high for at least four clock cycles, the CPU's internal registers are cleared.
    
2. **The Home Address:** The 8086/8088 always wakes up at memory location **FFFF0H**. This is known as the **Cold-Start Location**.
    
    +1
    
3. **The Latch (ALE):** Because the CPU uses the same pins for both addresses and data, it needs a "postal worker" (the ALE signal) to tell the system, "Hey, I'm sending an address right now; catch it!".
    

---

### Layer 3: Visual & Diagrammatic Reinforcement

Imagine a timeline divided into four segments (T1 to T4).

- **T1:** Address is sent out; **ALE** pulses high to "lock" the address into external hardware.
    
    +1
    
- **T2:** The CPU stops sending the address and gets the data bus ready.
    
    +1
    
- **T3:** The **$\overline{RD}$** (Read) signal goes low, telling memory to "Speak now!".
    
    +1
    
- **T4:** The CPU "grabs" the data from the bus and brings it inside.
    
    +1
    

---

### Layer 4: Step-by-Step Walkthrough

Here is exactly what happens during the very first bus cycle:

1. **The Trigger:** Power is applied. The 8284A sends a `RESET` pulse to Pin 21 of the 8086.
    
2. **The Setup:** Internally, the Code Segment (CS) is set to `FFFFH` and the Instruction Pointer (IP) is set to `0000H`. This points to the physical address **FFFF0H**.
    
3. **T1 (The Reach):** The CPU places `FFFF0H` on the address bus. It pulses **ALE** high. An external chip (like the 74LS373) "catches" this address and holds it for the memory.
    
    +1
    
4. **T2 (The Pause):** The CPU switches its multiplexed pins to "input mode" so it can listen for data.
    
5. **T3 (The Command):** The CPU pulls the **$\overline{RD}$** pin to 0 (Low). The EPROM chip at address FFFF0H sees this and puts its first byte of code onto the data bus.
    
    +1
    
6. **T4 (The Capture):** The CPU "swallows" that byte. This byte is usually the start of a `JMP` (Jump) instruction that tells the CPU where the _real_ program is located (since there are only 16 bytes left at the very top of memory!).
    

---

### Layer 5: Hardware Syntax & Rules

Since this is a fixed hardware process, the "syntax" is in the wiring.

**HARDWARE RULE: Cold-Start Configuration**

- **Reset Pin (Pin 21):** Must be held High for $\ge 4$ clock cycles to trigger the wake-up.
    
- **First Address:** `FFFF0H` (This is non-negotiable; you **must** have your startup EPROM mapped to cover this address).
    
- **The "Jump" Requirement:** Because `FFFF0H` is near the very end of the 1MB memory space, the first instruction at that location **must** be a `JMP` to a lower address where your main code lives.
    

---

### Layer 6: Common Pitfalls & Debugging

1. **The "Address 0" Mistake:** Many students think the CPU starts at `00000H`. If you put your startup code there, the computer will never boot because the 8086 is looking at the "ceiling" (`FFFF0H`), not the "floor" (`00000H`).
    
2. **Weak Reset:** if the `RESET` signal is too short (less than 4 clocks), the registers won't clear properly. The CPU will wake up "confused" and try to execute random noise as instructions.
    
3. **Missing ALE Latch:** If you don't use an external latch, the address "disappears" from the pins before the memory chip has a chance to read it. The CPU will end up reading nothing but "FFH" (empty bus).
    

---

**Does this sequence of heartbeats make sense?** Specifically, can you see why we need those 4 distinct T-states just to get one single byte of code?

**Self-Test Questions:**

1. At what exact hexadecimal address does the 8086 look for its very first instruction?
    
2. Which signal tells the rest of the computer that the address on the bus is valid and should be "latched"?
    
3. Why is the first instruction at the cold-start location almost always a `JMP` instruction?


**Answers to Self-Test:**

1. _FFFF0H._
    
2. _ALE (Address Latch Enable)._
    
3. _Because there are only 16 bytes left at the top of the 1MB memory map; there isn't enough room for a full program._


