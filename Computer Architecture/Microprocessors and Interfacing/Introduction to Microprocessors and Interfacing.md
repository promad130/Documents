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

When MOD=01, the displacement is 8 bits. But the effective address calculations are 16‑bit, so the 8‑bit displacement is **sign‑extended** to 16 bits. [^1][^10]

- Range of 8‑bit signed: $-128$ to $+127$ (80H to 7FH in hex). [^10]
- If top bit (bit 7) is 0 (0x00–0x7F), extend with 0s:
    - Example: 7FH → 007FH. [^1][^11]
- If top bit is 1 (0x80–0xFF), extend with 1s (two’s complement):
    - Example: 80H → FF80H; FFH → FFFFH. [^1][^11]

This allows short displacements (relative + or −) to be encoded compactly. [^12]

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

