(Primarily based on 8086 microprocessor)
***(Follow along [[Introduction to assembly]])***
## Microprocessor-Based Computer System

A **microprocessor-based computer system** is a digital system where the microprocessor acts as the central processing unit (CPU) that executes instructions, performs arithmetic/logic operations, and controls data flow between memory and I/O devices. Think of the microprocessor as the brain—it fetches instructions from memory, decodes what they mean, executes them, and manages communication with all other components through buses.

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

The 8086 is divided into two independent units that work in parallel:

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
- The 8086 instruction set is a **CISC** (Complex Instruction Set Computer) architecture where instructions vary in length from **1 to 6 bytes**. A 6-byte queue ensures that the Bus Interface Unit (BIU) can hold at least one complete "longest" instruction or several shorter ones.

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
- Binary -200: `0011 1000` (The "impossible" bit pattern, it is the two's complement of binary number `1100 1000`)

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

| If you use this Offset... | **The CPU ASSUMES this Segment:** | **Example Instruction** | **What CPU actually does** |
| ------------------------- | --------------------------------- | ----------------------- | -------------------------- |
| **[BX]** (Base)           | **DS** (Data Segment)             | `MOV AX, [BX]`          | `MOV AX, DS:[BX]`          |
| **[SI]** (Source Index)   | **DS** (Data Segment)             | `MOV AX, [SI]`          | `MOV AX, DS:[SI]`          |
| **[DI]** (Dest Index)     | **DS** (Data Segment)             | `MOV AX, [DI]`          | `MOV AX, DS:[DI]`          |
| **[Number]** (Direct)     | **DS** (Data Segment)             | `MOV AX, [1000h]`       | `MOV AX, DS:[1000h]`       |
| **[BP]** (Base Pointer)   | **SS** (Stack Segment)            | `MOV AX, [BP]`          | `MOV AX, SS:[BP]`          |
| **[SP]** (Stack Pointer)  | **SS** (Stack Segment)            | `PUSH AX`               | `MOV SS:[SP], AX`          |
| **IP** (Instr Pointer)    | **CS** (Code Segment)             | _(Cannot be changed)_   | _(Always Code)_            |

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

Now, keep this in mind:
- The 8086 instruction set is a **CISC** (Complex Instruction Set Computer) architecture where instructions vary in length from **1 to 6 bytes**. A 6-byte queue ensures that the Bus Interface Unit (BIU) can hold at least one complete "longest" instruction or several shorter ones.

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
**Interfacing in a microprocessor** refers to the process of connecting external devices—such as memory (RAM, ROM), input/output (I/O) peripherals (keyboard, display, sensors), and other components—to the microprocessor to enable effective communication and data exchange.

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

 When the 8086 microprocessor is in **Maximum Mode** (switched by connecting the $MN/\overline{MX}$ pin to ground), pins 24 through 31 change their functions entirely to support multiprocessor configurations and co-processors (like the 8087).

Here is the breakdown for the same pins in **Maximum Mode**:

| Pin # | Name | Description |
| :--- | :--- | :--- |
| **24** | $QS_1$ | **Queue Status 1**: Works with $QS_0$ (Pin 25) to tell external devices (like a coprocessor) what the internal Instruction Queue is doing (e.g., "I just took an opcode byte," or "The queue is empty"). |
| **25** | $QS_0$ | **Queue Status 0**: The partner to $QS_1$. Together, they provide the status code (00=No Op, 01=First Byte of Opcode, 10=Queue Empty, 11=Subsequent Byte). |
| **26** | $\overline{S_0}$ | **Status 0**: Part of the 3-bit status bus ($\overline{S_0}, \overline{S_1}, \overline{S_2}$). These replace the single control signals (like $\overline{RD}$, $\overline{WR}$) and tell the **8288 Bus Controller** exactly what bus cycle is happening (e.g., Interrupt Acknowledge, I/O Read, Code Access). |
| **27** | $\overline{S_1}$ | **Status 1**: The second bit of the bus status code. |
| **28** | $\overline{S_2}$ | **Status 2**: The third bit of the bus status code. Together with $\overline{S_0}$ and $\overline{S_1}$, they define 8 distinct bus cycle types. |
| **29** | $\overline{LOCK}$ | **Lock**: The "Do Not Disturb" sign. When this signal is active (Low), the CPU tells other bus masters that they cannot take control of the system bus. This is crucial for atomic instructions like `LOCK XCHG`. |
| **30** | $\overline{RQ}/\overline{GT_1}$ | **Request/Grant 1**: A bidirectional line used for bus arbitration. Other processors use this to ask for the bus ("Request"), and the CPU uses the *same pin* to say "Go ahead" ("Grant"). This pin has **lower priority** than Pin 31. |
| **31** | $\overline{RQ}/\overline{GT_0}$ | **Request/Grant 0**: Same function as Pin 30 (Request/Grant), but this pin has **higher priority**. If both pins receive a request simultaneously, the device on this pin wins the bus. |

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
5. **Peripheral Clocking:** A second "Divide-by-2" stage takes the CLK and creates **PCLK** (2.5 MHz), which is a slower, 50% duty cycle clock for older, slower peripheral chips
6. **Synchronization:** Simultaneously, the 8284A monitors the **RES** (Reset) and **RDY** (Ready) pins to ensure that when you press "Reset," the signal is perfectly aligned with the clock so the CPU doesn't get confused.

---
### Layer 5: Hardware Example & "Syntax"

To build this, you follow these hardware "syntax" rules:

**HARDWARE CONFIGURATION: 8284A to 8086**

- **Crystal Selection:** Frequency must be exactly **3x** the desired CPU speed (e.g., use 24 MHz crystal for an 8 MHz 8086).
- **Clock Connection:** `8284A Pin 8 (CLK) -> 8086 Pin 19 (CLK)`.
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

1. **The Wake-Up Call (RESET):** When the `RESET` pin is held high for at least four clock cycles, the CPU's internal registers are cleared.
2. **The Home Address:** The 8086/8088 always wakes up at memory location **FFFF0H**. This is known as the **Cold-Start Location**.
3. **The Latch (ALE):** Because the CPU uses the same pins for both addresses and data, it needs a "postal worker" (the ALE signal) to tell the system, "Hey, I'm sending an address right now; catch it!".

---

### Layer 3: Visual & Diagrammatic Reinforcement

Imagine a timeline divided into four segments (T1 to T4).

- **T1:** Address is sent out; **ALE** pulses high to "lock" the address into external hardware.
- **T2:** The CPU stops sending the address and gets the data bus ready.
- **T3:** The **$\overline{RD}$** (Read) signal goes low, telling memory to "Speak now!".
- **T4:** The CPU "grabs" the data from the bus and brings it inside.

---
### Layer 4: Step-by-Step Walkthrough

Here is exactly what happens during the very first bus cycle:

1. **The Trigger:** Power is applied. The 8284A sends a `RESET` pulse to Pin 21 of the 8086.
    
2. **The Setup:** Internally, the Code Segment (CS) is set to `FFFFH` and the Instruction Pointer (IP) is set to `0000H`. This points to the physical address **FFFF0H**.
    
3. **T1 (The Reach):** The CPU places `FFFF0H` on the address bus. It pulses **ALE** high. An external chip (like the 74LS373) "catches" this address and holds it for the memory.
    
4. **T2 (The Pause):** The CPU switches its multiplexed pins to "input mode" so it can listen for data.
    
5. **T3 (The Command):** The CPU pulls the **$\overline{RD}$** pin to 0 (Low). The EPROM chip at address FFFF0H sees this and puts its first byte of code onto the data bus.
    
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

---

![[24 Lecture_4_new.pdf]]

### Layer 1: Big Picture & Motivation (Why do we need this?)

Imagine you are a world-class chef (the CPU) working in a tiny, incredibly fast food truck. You have thousands of ingredients (Memory) and several cooking stations (I/O devices). But here is the catch: your food truck only has **one small window** to pass orders out and bring ingredients in.

Because you only have one window, you can't shout an order number (an **Address**) and receive the ingredients (the **Data**) at the exact same time through that same window. They would crash into each other! You have to do it in phases: first, you shout the address, then you wait a split second, and then the ingredients are passed through.

In the microprocessor world, the 8086 is a 16-bit processor with a 20-bit address bus. To have separate pins for 20 Address lines and 16 Data lines, plus control signals, we'd need a massive, expensive chip. So, Intel engineers used **Multiplexing**—they made the pins share jobs! The pins $AD_0$ to $AD_{15}$ act as an Address bus for a fraction of a millionth of a second, and then magically transform into a Data bus.

But memory chips aren't that smart; they get confused. We need external helper chips—**Latches, Buffers, and Decoders**—to organize this traffic.

_Pause: Does this shared highway/window analogy make sense so far? if not then I suggest you check out [[#Multiplexing of Address and Data in AD (1 to 20) pins]]_

---

### Layer 2: Conceptual Breakdown

Let’s divide our external helper hardware into three fundamental building blocks:

#### 1. The Latch (e.g., 74LS373 or 8282)

- **What it is:** A temporary memory storage unit. Inside, it’s made of "D-type flip-flops."
- **Analogy:** A photographer's camera.
- **How it works:** When the CPU puts an address on the shared $AD_0-AD_{15}$ pins, it simultaneously sends out a flash of light—a signal called **ALE (Address Latch Enable)**. The Latch sees this "flash," takes a snapshot of the address, and holds it steady on its output pins. Now, the CPU can safely change the $AD$ pins to handle Data, while the memory chip still reads the frozen Address from the Latch.
	- Check out the diagram of the latch, it is made so that it can hold a byte of information

#### 2. The Transceiver / Buffer (e.g., 74LS245 or 8286)

- **What it is:** An electrical amplifier and traffic director.
- **Analogy:** A megaphone and a two-way traffic cop.
- **How it works:** 
	- A CPU’s electrical signals are mathematically perfect but electrically weak. If you connect a CPU directly to 10 memory chips, the signal drops (we call this a _fan-out_ limitation, check out [[21 Lecture_1.pdf#page=9]], it covers that topic). 
	- A buffer amplifies the current. Furthermore, a _transceiver_ (transmitter-receiver) has a direction pin ($DT/\overline{R}$ in the 8086) that tells the traffic whether it is flowing _Out_ of the CPU (Write) or _In_ to the CPU (Read).
    

#### 3. The Decoder (e.g., 74LS138)

- **What it is:** A chip that translates a binary code into a single, specific selection line.
    
- **Analogy:** A mail sorting machine.
    
- **How it works:** If you have 8 different memory chips, the CPU needs a way to say "I want to talk to Chip #3." The CPU sends a 3-bit binary code (e.g., `011` for 3) to the 74LS138 decoder. The decoder reads `011` and exclusively turns ON the wire connected to Chip #3, waking it up.
    

_Quick check in your own words: If I want to freeze a signal so it doesn't disappear, which of the three chips do I use? (Answer: The Latch!)_

---

### Layer 3: Visual & Diagrammatic Reinforcement

Let's look at the **Bus Demultiplexing Block Diagram** (as seen in your Lecture 4 PDF):

![[24 Lecture_4_new.pdf#page=12|fix]]
_Notice the color coding in your mind: Red for control lines (`ALE`, `DT/R'`, `DEN'`), Blue for the mixed `AD` lines, and Green for the pure output lines._
BB is Bi-Directional Buffer

**ISSUES WITH THE DIAGRAM:**

Looking at this 8086 CPU interfacing diagram, here are the inaccuracies compared to real-world implementations:

#### Inaccuracies in the Diagram:

1. **Incomplete Address Bus Multiplexing**
   - The diagram shows A19-A0 directly from the CPU, but in real 8086 systems, A19-A8 are multiplexed with AD7-AD0 on the lower address/data bus during the first clock cycle. The diagram doesn't show this multiplexing clearly.

2. **Missing Demultiplexer for Address/Data**
   - Real 8086 systems require a demultiplexer (typically using latches with ALE signal) to separate the multiplexed address (AD0-AD7) from data during T1 state. This diagram oversimplifies this critical timing.

3. **Oversimplified Latch Operation**
   - The diagram shows latches but doesn't clearly indicate the ALE (Address Latch Enable) signal timing that controls when latches capture the address. In reality, ALE pulses during T1 to latch the lower address bits.

4. **Buffer Control Logic Not Shown**
   - The buffers should have directional control signals (typically from control unit) to determine read/write direction. The diagram shows generic "Buffer" boxes without clear control signal connections for direction and enable.

5. **Missing Critical Control Signals**
   - The diagram doesn't show how RD/WR signals control the bidirectional buffers, or how the status signals (S3-S7) are used for bus control and memory/IO selection.

6. **Incomplete Bus Architecture**
   - Real systems need separate latches for upper address (A19-A8) which are stable throughout the bus cycle. The diagram doesn't distinguish between multiplexed lower address and stable upper address.

7. **Data Bus Separation Not Clear**
   - In reality, data (D15-D0) should flow through bidirectional buffers with proper tri-state control. The diagram doesn't clearly show the separation and control of data path.

8. **No Tri-State Control Detail**
   - The actual enable/disable logic for buffers and latches (typically derived from control signals) isn't shown, making it unclear how bus contention is prevented.

These are pedagogical simplifications common in textbook diagrams, but they omit critical timing and control details essential for actual system design.

Roughly, it should be like this:
![[Pasted image 20260314052945.png]]

### **Latch 1 (Top - Status/Upper Address)**

- **Inputs:** BHE/S₇, A₁₆/S₃, A₁₇/S₄, A₁₈/S₅, A₁₉/S₆
- **Outputs:** BHE, A₁₆–A₁₉ (part of address bus)
- **Purpose:** Latches the upper 4 address bits + BHE signal
- ✅ **Uses 5 pins** (only needs 5 of the 8 available D inputs)

**The remaining 3 D latch inputs are LEFT UNUSED/FLOATING** - they don't connect to anything.

---

### **Latch 2 (Middle)**

- **Inputs:** AD₈–AD₁₅ (lower 8 bits of multiplexed bus)
- **Outputs:** A₈–A₁₅ (address bus)
- **Purpose:** Latches the middle address bits
- ✅ **Uses all 8 D inputs**

---

### **Latch 3 (Bottom)**

- **Inputs:** AD₀–AD₇ (lower 8 bits of multiplexed bus)
- **Outputs:** D₀–D₇ (data bus)
- **Purpose:** Latches lower address during T1, then acts as data bus buffer
- ✅ **Uses all 8 D inputs**

---

### Layer 4: Step-by-Step Walkthrough (The Bus Cycle)

In your lecture slides, there are timing diagrams for Read and Write operations. A standard 8086 bus cycle takes 4 clock periods, called $T_1, T_2, T_3, T_4$. Let's walk through a **Memory Write Operation**:

1. **Step 1: $T_1$ (The Setup).** The CPU places the 20-bit destination address on the multiplexed pins. It simultaneously forces the **ALE** pin HIGH. The 74LS373 Latch captures the address. The CPU also sets $M/\overline{IO}$ to 1 (telling the world "I want Memory, not an I/O port").
    
2. **Step 2: $T_2$ (The Switch).** The CPU drops ALE to LOW. The address disappears from the CPU pins, but the _Latch_ is now holding it safely for the memory chip. The CPU switches the $AD$ pins to become a Data bus. It asserts the $\overline{DEN}$ (Data Enable) to turn on the buffers.
    
3. **Step 3: $T_3$ (The Transfer).** The CPU places the actual data onto the data pins. The $\overline{WR}$ (Write) control signal goes LOW, commanding the memory chip: "Grab the data on your pins right now!"
    
4. **Step 4: $T_4$ (The Cleanup).** The $\overline{WR}$ signal goes back HIGH (deactivated). The bus cycle ends, and the CPU prepares for the next instruction.
    

---

### Layer 5: Code / Hardware Example & Syntax Restrictions

How do we actually trigger this beautiful hardware dance in software? By writing an instruction! Let's look at the exact syntax for moving data and multiplying data, complete with the strict rules you must follow.

#### The `MOV` Instruction

Triggers a bus cycle to move data to/from memory or registers.

Code snippet

```
MOV <Destination>, <Source>

RESTRICTIONS:
1. Cannot move data directly from one memory location to another memory location (e.g., MOV [1000H], [2000H] is ILLEGAL). You must use a register as a middleman.
2. Cannot move an immediate value directly into a Segment Register (CS, DS, ES, SS).
3. Both operands must be the same size (cannot move an 8-bit source into a 16-bit destination without extending).
4. The CS (Code Segment) register cannot be used as a destination.
```

#### The `OUT` Instruction

Triggers an I/O Write cycle (using decoders to select a port).

Code snippet

```
OUT <Port>, <Accumulator>

RESTRICTIONS:
1. The source MUST be the Accumulator register (AL for 8-bit, AX for 16-bit). You cannot output from BL or CX.
2. The Port address can be an 8-bit immediate value (00H to FFH).
3. If the Port address is larger than 8 bits (up to 16 bits), it MUST be pre-loaded into the DX register. You cannot write `OUT 03F8H, AL`. You must do `MOV DX, 03F8H` then `OUT DX, AL`.
```

#### The `MUL` Instruction (As requested for syntax examples)

Performs unsigned multiplication.

Code snippet

```
MUL <Source>

RESTRICTIONS:
1. Only takes ONE operand (the multiplier). The multiplicand is implicitly always in AL (for 8-bit) or AX (for 16-bit).
2. The operand CANNOT be an immediate number. `MUL 5` is ILLEGAL. You must put 5 in a register or memory first.
3. If <Source> is 8-bit, it multiplies AL * Source. Result always goes into AX.
4. If <Source> is 16-bit, it multiplies AX * Source. Result always goes into DX:AX (DX holds high 16 bits, AX holds low 16 bits).
```

---

### Layer 6: Common Pitfalls & Debugging

When my students wire these up in the lab or simulate them in Proteus, they almost always make these 3 mistakes:

1. **Floating Control Pins:** Forgetting to connect the $\overline{OE}$ (Output Enable) pin of the 74LS373 latch to Ground. If it floats, it randomly turns on and off, and your memory receives garbage addresses. _Fix: Always tie active-low enables to Ground if they should always be on._
    
2. **Bus Contention (The "Magic Smoke" Error):** Having two chips try to write data to the data bus at the exact same time. This causes a short circuit! _Fix: Ensure your Address Decoder (74LS138) logic guarantees that only ONE memory chip gets a Chip Select ($\overline{CS}$) signal at a time._
    
3. **Ignoring the T-States:** Assuming memory is instantly fast. If you connect an old, slow ROM chip to a fast CPU, the CPU reaches $T_3$ and expects data, but the ROM isn't ready. _Fix: Use the `READY` pin on the 8086 to insert "Wait States" ($T_w$) between $T_2$ and $T_3$, giving the memory time to catch up._


**Self-Test Questions:**

1. Why does the 8086 multiplex the Address and Data lines instead of keeping them separate?
2. During which T-state does the ALE signal go high?
3. What is the fundamental restriction on the operands of a `MOV` instruction regarding memory?

## Memory interfacing and Clock Timing 

![[25 Lecture_5.pdf]]


### 1. Microprocessor Timing & Bus Cycles

A **Bus Cycle** is the time required for the microprocessor to perform a single basic operation, such as reading from or writing to memory. In the 8086, a standard bus cycle consists of four clock periods called **T-states** ($T_1, T_2, T_3, T_4$).

#### The 4 States of a Cycle

- **$T_1$ (Address State):** The microprocessor places the memory or I/O address on the address bus.
    
- **$T_2$ (Status/Setup State):** The address is removed from the multiplexed bus to prepare for data. Control signals like $\overline{RD}$ or $\overline{WR}$ are issued.
    
- **$T_3$ (Data Transfer):** Data is either sampled from the bus (Read) or placed onto the bus (Write).
    
- **$T_4$ (Cycle End):** The control signals are deactivated, and the cycle concludes.

#### Wait States ($T_w$)

If a memory device is slower than the processor, it cannot provide or accept data within the standard $T_2$ to $T_3$ window.

- **The Term:** A **Wait State** is an extra clock period inserted between $T_2$ and $T_3$ to lengthen the bus cycle.
    
- **Example:** On a **5 MHz** clock, one $T$-state is **200 ns**. Adding one wait state increases the total available time for memory to respond by exactly 200 ns.


### 2. Signal Definitions (Hardware "Language")

To interface with memory, the 8086 uses specific signals to communicate its intent:

- **Multiplexed Busses ($AD_{15}-AD_0$):** To save pins, the same wires carry the **Address** during $T_1$ and **Data** during $T_2-T_4$.
    
- **ALE (Address Latch Enable):** A pulse that tells external hardware (latches) to "capture" the address before the pins switch over to carrying data.
    
- **$M/\overline{IO}$:** Distinguishes between a **Memory** operation (logic 1) and an **I/O** operation (logic 0).
    
- **$DT/\overline{R}$ (Data Transmit/Receive):** Tells the system buffers which direction data is flowing—**out** of the CPU (Transmit) or **into** the CPU (Receive).
    
- **$\overline{DEN}$ (Data Enable):** Activates external data buffers to connect the CPU to the memory system.

---
### 3. Read and Write Cycle Examples

#### Memory Write Cycle Example

1. **$T_1$:** CPU sets $M/\overline{IO} = 1$ (Memory) and $DT/\overline{R} = 1$ (Transmit). It places the address on the bus and pulses **ALE** high.
    
2. **$T_2$:** CPU places the **Data** to be written on the $AD$ bus. It drops $\overline{WR}$ (Write) to 0 to signal the memory to prepare for input.
    
3. **$T_3$:** The memory chip "grabs" the data from the bus while $\overline{WR}$ is low.
    
4. **$T_4$:** $\overline{WR}$ goes back to 1, and the bus is cleared.

#### Memory Read Cycle Example

1. **$T_1$:** CPU pulses **ALE** to latch the address. It sets $DT/\overline{R} = 0$ (Receive).
    
2. **$T_2$:** The CPU stops driving the $AD$ bus (it "floats" the pins) so the memory can take control. CPU drops $\overline{RD}$ (Read) to 0.
    
3. **$T_3$:** The memory chip places its data on the bus. The CPU "samples" (reads) this data.
    
4. **$T_4$:** CPU raises $\overline{RD}$ to 1 and ends the cycle.

### 4. Memory Access Time & Performance

**Memory Access Time** is the time the memory has to provide valid data after the address is stable.

#### **The Calculation**

The available time for memory to respond is roughly 3 clock cycles ($T_1, T_2, T_3$), but we must subtract the overhead required for the CPU to set up the signals.

- **Formula:** $\text{Access Time} = (3 \times \text{Clock Period}) - \text{TCLAV}$.
    
- **TCLAV:** Time from **C**lock to **L**ow **A**ddress **V**alid (the delay for the address to appear).
    

**Example Calculation (5 MHz Clock):**

- One clock period ($T$) = **200 ns**.
    
- Three clocking states = **600 ns**.
    
- Subtract $TCLAV$ (e.g., **110 ns**) and other setup delays (e.g., **30 ns**).
    
- **Net Access Time:** $600 - 110 - 30 = \mathbf{460\text{ ns}}$.
    

---
### 5. Memory Hardware Fundamentals

### **Memory Types**

- **RAM (Volatile):** Loses data when power is off. Includes **SRAM** (fast, used for cache) and **DRAM** (cheaper, used for main memory).
    
- **ROM (Non-Volatile):** Keeps data without power.
    
    - **PROM:** Programmable once.
        
    - **EPROM:** Erasable via UV light.
        
    - **Flash (EEPROM):** Electrically erasable; writing is slower than RAM.

#### Architecture: Pins vs. Locations

- **Address Pins:** The number of pins ($n$) determines the number of locations ($2^n$).
    
    - _Example:_ 10 pins = $2^{10} = 1,024$ (1K) locations.
        
- **Data Pins:** Related to the width of each location. A "1K x 8" chip means 1,024 locations, each 8 bits (1 byte) wide.
    
- **Control Pins:**
    
    - **$\overline{CS}$ (Chip Select):** Must be logic 0 to enable the chip.
        
    - **$\overline{OE}$ (Output Enable):** Enables the chip's buffers to send data out.
        
    - **$\overline{WE}$ (Write Enable):** Signals the chip to store incoming data.


## Address Decoding

![[26 Lecture_6.pdf]]

This guide covers the technical operations of the 8086 microprocessor, focusing on timing precision and memory mapping.

---

### **1. Microprocessor Timing & Bus Operations**

#### **The Bus Cycle**

A **Bus Cycle** is the fundamental time unit the 8086 takes to perform one task (like reading or writing 1 byte). It consists of four **T-states** ($T_1, T_2, T_3, T_4$), each equal to one clock period .

- **$T_1$ (Address State):** The CPU places the memory address on the bus. **ALE** (Address Latch Enable) pulses to tell external hardware to save this address.
    
- **$T_2$ (Setup State):** The address is removed. For a **Read**, the bus "floats" (goes idle) to let memory take over. For a **Write**, the CPU puts data on the bus.
    
- **$T_3$ (Transfer State):** Data is actually sampled (Read) or accepted by memory (Write).
    
- **$T_4$ (Ending State):** Control signals like $\overline{RD}$ or $\overline{WR}$ go high, ending the cycle.
    

#### **Wait States ($T_w$)**

- **The Point:** Some memory chips are too slow to respond within the standard $T_2$-$T_3$ window.
- **How it works:** An extra clock period ($T_w$) is inserted between $T_2$ and $T_3$. This "freezes" the CPU for 1 clock cycle, giving the memory more time to stabilize data.

#### **Access Time Constants**

- **$TCLAV$ (Clock to Address Valid):** The delay from the start of $T_1$ until the address is actually stable on the pins (e.g., 110 ns at 5 MHz).
- **$TDVCL$ (Data Valid to Clock):** The "setup time" the CPU needs—the data must be stable for this long _before_ the end of $T_3$ so the CPU can read it correctly.

---

### **2. Practical Performance Problems**

#### **Clock Frequency Adjustments**

If you speed up the clock, the time for each T-state shrinks.

- **Example (8 MHz):** One clock period ($T$) = $1 / 8\text{ MHz} = \mathbf{125\text{ ns}}$.
    
- **Problem:** If 1 wait state is added to an 8 MHz cycle, what is the total memory access time window?
    
- **Solution:** Memory access happens over $T_1, T_2,$ and $T_3$. With one $T_w$, you have 4 states. Total time = $4 \times 125\text{ ns} = \mathbf{500\text{ ns}}$.
    

#### **16-Bit Data Read Problem**

Reading a 16-bit value from an **odd address** (e.g., $2010\text{H}$) requires **two** bus cycles because the 8086 can only access 16 bits in one go if they are at an even address.

- **Calculation:** If one cycle takes 4 T-states (at 8 MHz) and you need two cycles:
    
    $\text{Total Time} = 2 \text{ cycles} \times (4 \times 125\text{ ns}) = \mathbf{1000\text{ ns}}$.
    

---

### **3. Specific Memory Hardware Interfacing**

#### **EPROM 2716 ($2\text{K} \times 8$)**

A non-volatile storage chip holding 2048 bytes.

- **Address Pins ($A_0$-$A_{10}$):** 11 pins are needed because $2^{11} = 2048$.
    
- **$PD/PGM$:** Power Down/Program pin. Used to program the chip or put it in standby.
    
- **$\overline{CS}$ (Chip Select):** The "wake up" pin. The chip ignores everything unless this is 0.
    

#### **RAM Signals**

RAM needs to both read and write, so it adds **$\overline{WE}$ (Write Enable)**.

- **$\overline{OE}$ (Output Enable):** Turns on the chip's data output buffers.
    
- **Tristate logic:** When $\overline{CS}$ or $\overline{OE}$ are high (1), the pins are in **High-Z** (disconnected), so they don't interfere with other chips on the same bus.
    

---

### **4. Deep Dive: Memory Address Decoding**

#### **The Splicing Concept**

The 8086 has 20 address lines ($A_0$-$A_{19}$), allowing it to see **1,048,576 addresses (1MB)**. However, a $2\text{K}$ EPROM only uses 11 lines ($A_0$-$A_{10}$). **Decoding** is the logic that uses the "leftover" lines ($A_{11}$-$A_{19}$) to "splice" that small chip into a specific part of the 1MB map.

#### **Decoding Logic ($n = \log_2 N$)**

To interface $P$ locations out of a total $N$ possible locations:

1. Connect the **lowest address lines** (e.g., $A_0$-$A_{10}$) directly to the chip.
    
2. Use the **highest address lines** (e.g., $A_{11}$-$A_{19}$) as inputs to a **NAND gate**.
    
3. The NAND gate output goes to the chip's **$\overline{CS}$** pin. Because a NAND gate only outputs **0** when all inputs are **1**, the chip only "wakes up" when the CPU calls that specific range.
    

#### **Example: Mapping $FF800\text{H}$ to $FFFFF\text{H}$**

- **The Goal:** Place a 2KB chip at the very top of the memory map.
    
- **Binary of $FF800\text{H}$:** `1111 1111 1000 0000 0000`.
    
- **Binary of $FFFFF\text{H}$:** `1111 1111 1111 1111 1111`.
    
- **Observation:** In this range, lines $A_{19}, A_{18}, \dots A_{11}$ are **all 1s** .
    
- **Implementation:** Connect $A_{11}$ through $A_{19}$ to a 9-input NAND gate. When the CPU hits an address in that range, the gate sees all 1s, outputs a 0, and selects the chip.
    

#### **Example: Using Inverters for $DF800\text{H}$ to $DFFFF\text{H}$**

- **Address in Binary:** $D = 1101$. So $A_{19}=1, A_{18}=1, A_{17}=0, A_{16}=1$ .
    
- **The Problem:** A NAND gate needs all 1s to output a 0. But $A_{17}$ is a **0** here.
    
- **The Fix:** Pass $A_{17}$ through an **Inverter (NOT gate)** before it reaches the NAND gate. This flips the 0 to a 1.
    
- **Logic:** The chip is selected only when ($A_{19}=1, A_{18}=1, \mathbf{NOT}\ A_{17}=1, A_{16}=1, \dots$). If any bit is wrong, the NAND gate output stays at 1, and the chip stays "asleep".



![[27 Lecture_7.pdf]]


## **1. Advanced Decoding: The 74LS138 (3-to-8 Line Decoder)**

### **The "Why" and the "Point"**

As your memory system grows from one chip to eight or sixteen, using raw NAND gates becomes a nightmare of wiring and physical space. The **74LS138** is an Integrated Circuit (IC) designed specifically to handle **Chip Select ($\overline{CS}$)** logic for up to eight memory devices simultaneously.

Instead of a separate gate for every chip, you use one IC that "decodes" three binary address lines into eight distinct signals.

### **How it Works (Pins & Logic)**

- **Enable Pins ($G_1, \overline{G_{2A}}, \overline{G_{2B}}$):** To "turn on" the decoder, you must satisfy three conditions: $G_1$ must be **High (1)**, and both $\overline{G_{2A}}$ and $\overline{G_{2B}}$ must be **Low (0)**. These are often connected to higher-order address bits or the $M/\overline{IO}$ signal.
    
- **Select Inputs ($A, B, C$):** These are connected to the next set of address lines. They act as a 3-bit binary input ($000$ to $111$).
    
- **Outputs ($O_0$ to $O_7$):** Based on the $A, B, C$ input, exactly **one** output will drop to **Logic 0** (Active Low), while the other seven stay at Logic 1. This "0" is fed directly into the $\overline{CS}$ pin of a specific memory chip.
    

---

## **2. Design Example: Mapping a 1K RAM at FFC00H**

Let's design a system that places a **1KB RAM** at the address range **FFC00H to FFFFFH** using the 74LS138.

### **Step 1: Address Line Allocation**

- **Inside the chip ($A_0-A_9$):** Since $2^{10} = 1024$, bits $A_0$ through $A_9$ are wired directly to the RAM's address pins to select bytes _within_ that 1KB block .
    
- **The Select Pins ($A_{10}, A_{11}, A_{12}$):** We connect these to the $A, B, C$ inputs of the decoder. For the range starting at **FFC00H**, these bits must be $111$ (binary for 7) .
    
- **The Enable Pins ($A_{13}-A_{19}$):** For the hex range **FF** ($1111\ 1111$), all these high bits are 1s. We connect $A_{19}$ to $G_1$ and use NAND gates to group the others into the active-low $\overline{G_{2}}$ enables .
    

### **The Result**

When the CPU issues any address between **FFC00H and FFFFFH**, the decoder is enabled, sees the binary `111` on its select pins, and pulls its **$Y_7$** output to **0**. This "wakes up" the RAM chip mapped to that output.

---

## **3. Multi-Chip Memory Bank Interfacing**

### **The Point**

In the real world, you might need 8KB of memory but only have 2KB chips available. You must create a **Memory Bank**.

### **Calculation & Mapping (8KB Example)**

- **Chips Required:** $8\text{KB total} / 2\text{KB per chip} = \mathbf{4\text{ chips}}$ ($RAM_1$ to $RAM_4$).
    
- **Address Pins:** A 2KB chip needs 11 lines ($A_0-A_{10}$).
    
- **The "Banking" Logic:** We use the 74LS138 to select which of the four chips is active .
    
    - $RAM_1$ ($00000\text{H}–007\text{FFH}$): Decoder input $000$, Output $O_0$ active .
        
    - $RAM_2$ ($00800\text{H}–00\text{FFH}$): Decoder input $001$, Output $O_1$ active .
        
    - $RAM_3$ ($01000\text{H}–017\text{FFH}$): Decoder input $010$, Output $O_2$ active .
        
    - $RAM_4$ ($01800\text{H}–01\text{FFH}$): Decoder input $011$, Output $O_3$ active .

---

## **4. Mixed RAM/ROM System Design**

### **The Scenario**

Most microprocessors need a mix of **ROM** (to hold the boot/BIOS code) and **RAM** (for temporary data).

### **Mixed Design Example**

- **ROM:** 4KB total, starting at $00000\text{H}$. Uses two 2KB (2716) chips ($ROM_1, ROM_2$).
    
- **RAM:** 8KB total, starting at $08000\text{H}$. Uses four 2KB (6116) chips ($RAM_1-RAM_4$) .
    

**The Logic:**

1. Connect $A_0-A_{10}$ to all chips.
    
2. Connect $A_{11}, A_{12}$ to decoder inputs $A, B$. Connect $A_{15}$ to decoder input $C$ .
    
3. **ROM Selection:** When $A_{15}=0$ and $A_{12}, A_{11}$ are $00$ or $01$, decoder outputs $O_0$ and $O_1$ trigger the ROM chips.
    
4. **RAM Selection:** When $A_{15}=1$, binary input jumps to $100$ (decimal 4). Outputs $O_4, O_5, O_6, O_7$ trigger the RAM chips.
    

---

## **5. High-Address Space Interfacing (64K EPROM)**

### **The Goal**

Placing a large block of non-volatile memory at the very top of the 1MB map ($F0000\text{H}$ to $FFFFF\text{H}$).

### **Hardware: 2764 EPROMS ($8\text{K} \times 8$)**

- **Chip Count:** To get 64KB using 8KB chips, you need **8 EPROMS**.
    
- **Address Pins:** Each 8KB chip uses 13 address lines ($A_0-A_{12}$).
    
- **Decoding:**
    
    - Lines **$A_{13}, A_{14}, A_{15}$** connect to the Decoder's **$A, B, C$** select pins .
        
    - Lines **$A_{16}-A_{19}$** are wired to the Enable pins ($G_1, \overline{G_{2A}}, \overline{G_{2B}}$) to ensure the system only responds when the CPU is in the **"F" ($1111$)** segment of memory .
        

|**Output**|**Address Range**|**Chip Selected**|
|---|---|---|
|**$O_0$**|$F0000\text{H}–F1\text{FFFH}$|EPROM 1|
|**$O_7$**|$FE000\text{H}–F\text{FFFFH}$|EPROM 8|

---

## **Summary of New Terms**

- **74LS138:** A 3-to-8 decoder chip used to generate active-low chip select signals.
    
- **Memory Mapping:** The systematic process of assigning specific binary address ranges to physical hardware chips.
    
- **Programmable Logic (PLD/FPGA):** Modern alternatives to the 74LS138 that can be programmed via software for even more complex decoding.

[[A Comprehensive example of Desiging 8K RAM Chip]]

![[28 Lecture_8.pdf]]

### **1. 8086 Memory Bank Organization**

#### **The Problem & The Point**

The 8088 has an 8-bit data bus, so it reads 1 byte at a time. The 8086 has a **16-bit data bus** ($D_0–D_{15}$), meaning it can read **two bytes** in a single bus cycle. However, sometimes the CPU only needs 1 byte. To allow the CPU to pick either the "low" byte, the "high" byte, or both at once, the 1MB memory space is physically split into two parallel **banks**.

- **Even Bank (Low Bank):**
    
    - **Addresses:** All even addresses ($00000\text{H}, 00002\text{H}, \dots$).
    - **Wiring:** Connects to the **lower** half of the data bus ($D_0–D_7$).
    
- **Odd Bank (High Bank):**
    
    - **Addresses:** All odd addresses ($00001\text{H}, 00003\text{H}, \dots$).    
    - **Wiring:** Connects to the **upper** half of the data bus ($D_8–D_{15}$).

---

### **2. Bank Control Signals ($\overline{BHE}$ and $A_0$)**

To control which bank is "active," the 8086 uses two hardware pins as selectors.

- **$A_0$:** The lowest address bit. In 8086 memory interfacing, $A_0$ is **not** used to select a byte inside a chip; it is used as the **Even Bank Enable**. When $A_0 = 0$, the even bank is on.
    
- **$\overline{BHE}$ (Bus High Enable):** A dedicated pin used as the **Odd Bank Enable**. When $\overline{BHE} = 0$, the odd bank is on.
    

#### **Selection Truth Table**

|**BHE**|**A0​**|**Operation Type**|**What happens?**|
|---|---|---|---|
|**0**|**0**|**Whole Word**|Both banks active. CPU reads 16 bits ($D_0–D_{15}$) in 1 cycle.|
|**0**|**1**|**Upper Byte**|Only Odd Bank active. Data travels on $D_8–D_{15}$.|
|**1**|**0**|**Lower Byte**|Only Even Bank active. Data travels on $D_0–D_7$.|
|**1**|**1**|**None**|Neither bank is enabled.|

---

### **3. Transfer Efficiency: Aligned vs. Misaligned**

#### **The Concept**

Because of banking, **where** you store data in memory drastically changes performance.

- **Aligned Word Access:** A 16-bit word starting at an **even address** (e.g., $00004\text{H}$).
    
    - **Mechanism:** The CPU sets $\overline{BHE}=0$ and $A_0=0$. It pulls the low byte from the even bank and the high byte from the odd bank **simultaneously**.
    - **Speed:** Takes **1 Bus Cycle**.
    
- **Misaligned Word Access:** A 16-bit word starting at an **odd address** (e.g., $00005\text{H}$).
    
    - **Mechanism:** The CPU cannot read this in one go. It must perform **two cycles**:    
        1. Cycle 1: Read the byte at $00005\text{H}$ (Odd Bank). (carried on D8 to D 15, which is to be treated as D0 to D7)
        2. Cycle 2: Read the byte at $00006\text{H}$ (Even Bank). (carried on D0 to D 7, which is to be treated as D8 to D15)
    - **Speed:** Takes **2 Bus Cycles** (50% slower performance).

---

### **4. Hardware Interfacing with 16-bit Banks**

When building the circuit, you must combine the **Address Decoder** (which picks the address range) with the **Bank Select Signals** (which pick the bank).

#### **The Selection Logic (OR Gates)**

Memory chips have an active-low Chip Select ($\overline{CS}$). To trigger it, you use an **OR gate**.

- **Even Chip Select:** Combine the Decoder Output with **$A_0$**.
- **Odd Chip Select:** Combine the Decoder Output with **$\overline{BHE}$**.

**How it works:** An OR gate outputs **0** only if **both** inputs are 0. So, the chip only wakes up if the Decoder says "This is the right address" (0) **AND** the CPU says "I want this specific bank" (0).

#### **Memory Mapping Change**

In a 16-bit 8086 system, the chips ignore $A_0$.

- Instead, **CPU $A_1$** connects to **Chip $A_0$**, **CPU $A_2$** to **Chip $A_1$**, and so on.
- This effectively "skips" every other address for that specific bank, which is exactly what we want since each bank only handles half the addresses.

---

### **5. Scaled Memory Decoding**

#### **1 MB Decoding**

To manage the full 1MB of 8086 memory, engineers use multiple **74LS138 decoders** cascaded together. One decoder might handle a specific "page" or "segment" of memory, while others select individual chips within that segment.

#### **Advanced Architectures (80386SX)**

The 80386SX also has a 16-bit data bus and uses the exact same banking system ($\overline{BHE}$ and $A_0$) as the 8086. Higher-end chips like the full **80386DX** have a **32-bit bus**, meaning they use **four banks** and four selection signals ($\overline{BE_0}$ to $\overline{BE_3}$).

---

### **Term Summary**

- **$D_0–D_{15}$**: The 16-bit wide "highway" for data.
    
- **$\overline{BHE}$**: The "Odd Bank Switch".
    
- **Aligned**: Efficiently placed at an even address (fast).
    
- **Misaligned**: Poorly placed at an odd address (slow).
    
- **Bank Select Logic**: Using gates to ensure the right bank speaks at the right time.

# I/O Interfacing

![[29 Lecture_9.pdf#page=13]]

### **1. I/O Mapping Methods: Where does the device "live"?**

The CPU needs a way to distinguish between a request for a memory byte and a request for data from a keyboard or sensor.

#### **Isolated I/O (Mapped I/O)**

- **The Point:** Keeps the entire 1MB memory space free for software/data by putting I/O devices in a separate "shadow" map.
    
- **How it Works:** The 8086 uses the **$M/\overline{IO}$** pin. When this pin is **Logic 0**, the address on the bus is treated as an I/O port, not a memory location.
    
- **Capacity:** It provides a 64K ($65,536$) port address space.
    
- **Instructions:** Only specific `IN` and `OUT` instructions work here.
    

#### **Memory-Mapped I/O**

- **The Point:** Allows the CPU to use its full suite of powerful memory instructions (like `MOV`, `ADD`, `OR`) on external hardware.

- **How it Works:** A range of the 1MB address space is "stolen" and assigned to a device.

- **Trade-off:** It reduces the total RAM available to the system, but makes I/O programming more flexible.

---

### **2. I/O Port Addressing & Instructions**

A **Port** is like a mailbox for a specific device.

#### **Addressing Types**

- **Fixed (Direct) Port Addressing:**
	
    - **Term:** The port address is a constant 8-bit number ($00\text{H}$ to $FF\text{H}$) hardcoded into the instruction.    
    - **Example:** `IN AL, 10H` — Immediately gets data from Port 10.
    
- **Variable (Indirect) Port Addressing:**
    
    - **Term:** The port address is stored in the **DX register**, allowing for a 16-bit range.
        
    - **Why do it?** This allows you to reach all **65,536 ports** and change the target port dynamically while the program is running.
        
    - **Example:** `MOV DX, 03F8H`
        
        `IN AL, DX` — Gets data from a high-address COM port.

#### **Instructions: The Mechanics**

- **`IN`**: Transfers data **from** a peripheral **to** the Accumulator (AL or AX).
    
- **`OUT`**: Transfers data **from** the Accumulator **to** a peripheral.
    
- **The Rule:** All I/O data **must** pass through the Accumulator register. You cannot `OUT` data directly from memory to a port.
    

---

### 3. Data Transfer Modes: Coordination

The CPU is millions of times faster than a keyboard or sensor. Coordination is required to prevent data loss.

- **Simple I/O (Unconditional):** The CPU just blasts data out. This is used for devices that are "always ready," like an LED display.
    
- **Programmed I/O (Handshaking):** * The Point: Synchronizing a fast CPU with a slow device.
    
    - **Mechanism:** The CPU "polls" (repeatedly checks) a status bit from the device. It only transfers data when the device indicates it is ready.
        
- **Interrupt-Driven I/O:** The most efficient method. The CPU goes about its business, and the device sends an electrical "ping" (Interrupt) only when it actually has data ready.

![[30 Lecture_10.pdf]]

### **4. 8255 Programmable Peripheral Interface (PPI)**

The **8255** is a versatile "middle-man" chip. It provides the physical pins for the CPU to talk to the real world.

#### **Architecture**

- **Port A & Port B:** Two standard 8-bit ports for high-volume data.
    
- **Port C:** An 8-bit port that can be split into two **4-bit nibbles** (Upper and Lower).
    
- **The "Point" of splitting Port C:** These 4-bit sections often act as the "Handshaking" lines (Status/Control) for Ports A and B.
    

#### **Operating Modes**

1. **Mode 0 (Basic I/O):** Simple input or output. No status checking. Used for switches or LEDs.
    
2. **Mode 1 (Strobed I/O):** Uses Port C pins to handle the Handshaking signals automatically.
    
3. **Mode 2 (Bi-directional):** Port A becomes a 2-way street (sending and receiving on the same wires), using 5 bits of Port C for control.
    

---

### **5. Programming the 8255**

You configure the 8255 by sending a single byte, the **Control Word**, to its internal Control Register.

#### **Control Word Format (I/O Mode)**

To set the chip to I/O mode, **Bit 7 must be 1**.

- **Bit 6 & 5:** Select Mode for Port A ($00$ = Mode 0).
    
- **Bit 4:** Port A direction ($1$ = Input, $0$ = Output).
    
- **Bit 3:** Port C Upper direction.
    
- **Bit 2:** Select Mode for Port B.
    
- **Bit 1:** Port B direction.
    
- **Bit 0:** Port C Lower direction.
    

#### **BSR (Bit Set/Reset) Mode**

- **The Point:** You want to flip one single switch (like an LED on Port C bit 3) without changing any other pins.
    
- **How it Works:** Set **Bit 7 to 0**. The remaining bits specify which bit of Port C to change and whether to make it a $1$ or a $0$.
    

---

### **Practical Example: Configuring the 8255**

**Task:** Configure the 8255 where Port A = Input (Switches), Port B = Output (LEDs), and all of Port C = Output. Use Mode 0.

1. **Construct the Control Word:**
    
    - Bit 7 = `1` (I/O Mode)
        
    - Bits 6,5 = `00` (Port A Mode 0)
        
    - Bit 4 = `1` (Port A Input)
        
    - Bit 3 = `0` (Port C Upper Output)
        
    - Bit 2 = `0` (Port B Mode 0)
        
    - Bit 1 = `0` (Port B Output)
        
    - Bit 0 = `0` (Port C Lower Output)
        
    - **Result:** `10010000` Binary = **$90\text{H}$**.
        
2. **Code:**
    
    `MOV AL, 90H`
    
    `OUT Control_Register_Address, AL`


# I/O Addressing Modes

The 8086 has two ways to identify a port. The address bus uses $A_0$ to $A_{15}$ for I/O, allowing for 65,536 ports.

1. **Fixed Port Addressing**: Used for ports `00H` to `FFH` (the first 256). The port number is written directly into the instruction.
2. **Variable Port Addressing**: Used for any port from `0000H` to `FFFFH`. The address **must** be loaded into the `DX` register first.

#### MASM Examples

```MASM
; --- Fixed Addressing (Port 80H) ---
IN  AL, 80H        ; Read 1 byte from port 80H into AL
OUT 20H, AX        ; Write 2 bytes (AX) to ports 20H and 21H

; --- Variable Addressing (Port 03F8H) ---
MOV DX, 03F8H      ; Load the 16-bit port address into DX
IN  AL, DX         ; Read from the address stored in DX
OUT DX, AL         ; Write to the address stored in DX
```

### Handshaking: Synchronization

Microprocessors are much faster than mechanical devices (like printers). To prevent data loss, they use **Handshaking signals**:

- **STB (Strobe)**: The sender pulses this line to signal, "The data on the bus is valid and ready for you to take!" 📢
- **ACK (Acknowledge)**: The receiver pulses this to say, "I've successfully captured the data, you can send the next piece now." ✅
- **BUSY/READY**: Indicates if the device is currently occupied or available to communicate.

| **Signal**  | **Direction** | **Purpose**                            |
| ----------- | ------------- | -------------------------------------- |
| **STB**     | Source → Sink | Data is valid on the bus               |
| **ACK**     | Sink → Source | Data has been received                 |
| **I/O R/W** | CPU → Device  | Specifies if we are reading or writing |

**Quick Check**
>If we wanted to send data to a hardware device located at port address `0520H`, which of the two addressing methods (Fixed or Variable) would we have to use, and why?
>Since `0520H` is greater than `00FFH` (the limit for 8-bit fixed addressing), we **must** use **Variable Port Addressing** 🛠️.

In MASM, it would look like this:

```Code
MOV DX, 0520H  ; Load the 16-bit port address into DX
OUT DX, AL     ; Send data in AL to that port
```

Now, let's look at how the CPU physically "sees" these I/O devices. There are two main methods used in hardware design: **Isolated I/O** and **Memory-Mapped I/O**

### 1. Isolated I/O (Standard I/O)

In this setup, the CPU treats memory and I/O as two completely different worlds.

- **Address Space**: I/O has its own 64K "room" separate from the 1MB memory room.
- **Instructions**: Only uses `IN` and `OUT`.
- **Signals**: The CPU uses a special pin (M/IO) to tell the system "I am talking to a port right now, not memory!"

### 2. Memory-Mapped I/O

Here, the I/O device is "disguised" as a memory location.

- **Address Space**: A portion of the 1MB memory space is reserved for I/O.
- **Instructions**: You can use _any_ instruction that works with memory (`MOV`, `ADD`, `AND`, `OR`).
- **Signals**: The CPU thinks it's talking to memory, so M/IO stays high (Memory mode).

### Comparison Table

| Feature           | Isolated I/O             | Memory-Mapped I/O                    |
| ----------------- | ------------------------ | ------------------------------------ |
| **Instructions**  | `IN`, `OUT` only         | `MOV`, `LD`, `ST`, etc.              |
| **Address Space** | Separate (64K)           | Shared (part of 1MB)                 |
| **Complexity**    | Simple hardware decoding | Requires more complex decoding       |
| **Speed**         | Fast (dedicated lines)   | Can be faster for complex operations |

## Revision Point: Interrupts and IVTs

### What is an Interrupt?

An interrupt is a signal that tells the CPU to pause its current program and execute a special function called an **Interrupt Service Routine (ISR)**.

- **Software Interrupts**: Triggered by instructions like `INT 21H`.
- **Hardware Interrupts**: Triggered by external pins like `INTR` or `NMI`.

### The Interrupt Vector Table (IVT)

The 8086 can handle **256** different interrupt types (0 to 255). To find the address of the ISR for a specific interrupt, the CPU looks at the IVT.

- **Location**: The IVT always starts at the very beginning of memory: physical address `00000H`.
- **Size**: Each interrupt "vector" (the address of the ISR) is **4 bytes** long.
    - **2 bytes** for the **Offset (IP)** (stored first).
    - **2 bytes** for the **Segment (CS)** (stored second).

### Calculating the Address 🧮

To find the starting physical address in the IVT for any **Type N** interrupt, use this simple formula:

$$\text{Starting Address} = N \times 4$$

**Example: Type 10H (Video Services)**

1. Convert $10\text{H}$ to decimal: $16$.    
2. Multiply by 4: $16 \times 4 = 64$.
3. Convert back to hex: $64 = 40\text{H}$.
4. The vector for `INT 10H` is stored at memory locations `00040H` through `00043H`.

# The 80286

Now we transition from the 8086 to the **80286**. The 80286 can operate in "Real Mode" (acting just like an 8086) or **"Protected Mode"**, which is where the hardware complexity increases.

In Protected Mode, the CPU can address **16 MB** of physical RAM and up to **1 GB** of virtual memory. It achieves this by changing how segment registers work.

## Selectors and Descriptors

In the 8086, `DS` held a memory address. In the 80286, `DS` holds a **Selector**. This selector is like an index or a "pointer" to a row in a **Descriptor Table**.

- **GDT (Global Descriptor Table)**: Contains segments accessible by all programs (like the Operating System).
- **LDT (Local Descriptor Table)**: Contains segments private to a specific task or program.

Each **Descriptor** (a row in the table) is 8 bytes long and contains:

1. **Base Address (24-bit)**: The actual starting point in the 16MB memory.
2. **Limit (16-bit)**: The size of the segment. If a program tries to access memory beyond this limit, the hardware triggers an "exception" (protection).
3. **Access Rights**: Tells the CPU if the segment is Read-Only, Read/Write, or Executable code.

## Address Calculation in Protected Mode

Unlike the 8086 ($Segment \times 16 + Offset$), the 80286 finds the **Base** from the Descriptor Table and simply adds the **Offset** to it.

$$\text{Physical Address} = \text{Base Address (from Descriptor)} + \text{Offset}$$

### 1. The Virtual Memory Machine 

The 80286 can address **1 GB** of virtual memory, even though it only has **16 MB** of physical RAM.

- **Mechanism**: It maps a large "logical" space (on the hard disk) into a smaller "physical" space (RAM).
- **Segmentation**: In the 80286, this is done entirely through **Segments**. If a program tries to access a segment that isn't currently in RAM, the hardware triggers an exception, and the OS swaps the data in from the disk.
- **Limitation**: Unlike later processors (like the 80386), the 80286 does **not** support "Paging" — it only uses segments to manage virtual memory.

---

### 2. Hardware Interrupts: NMI vs. INTR

The 8086/80286 has two main hardware pins used by external devices for interrupts:

| **Feature**    | **NMI (Non-Maskable)**           | **INTR (Interrupt Request)**              |
| -------------- | -------------------------------- | ----------------------------------------- |
| **Priority**   | Higher                           | Lower                                     |
| **Maskable?**  | **No** (Always recognized)       | **Yes** (Can be turned off)               |
| **Control**    | None                             | Controlled by the **IF** (Interrupt Flag) |
| **Type**       | Fixed as **Type 2**              | Provided by the external hardware         |
| **Common Use** | Power failure, RAM parity errors | Keyboard, Mouse, Disk drives              |

#### MASM Control

You can control whether the CPU listens to the **INTR** line using these instructions:

```Code
CLI    ; Clear Interrupt Flag (IF = 0). CPU ignores INTR signals.
STI    ; Set Interrupt Flag (IF = 1). CPU now listens to INTR signals.
```

# Port Decoding

Now, let's move to **I/O Port Decoding**. This is how the hardware "recognizes" its own address on the bus.

### How Port Decoding Works

When the CPU executes `OUT 20H, AL`, it puts the value `20H` on the address bus. For a hardware device (like a sensor) to respond, it needs a **decoder circuit** that only activates when that specific pattern of 1s and 0s appears.

- **8-bit Decoding**: Uses $A_0$ to $A_7$ (Fixed addressing).
- **16-bit Decoding**: Uses $A_0$ to $A_{15}$ (Variable addressing).

#### The Decoding Logic

Think of a decoder as a "lock" and the address as the "key." 

- If we want a device to respond to Port `FFH` ($1111 1111_2$), we could use an **8-input NAND gate**.
- The gate only outputs a "Low" signal (which enables the device) when all 8 address lines are High ($1$).

### Example: The 74LS138 Decoder

This is a common chip used in interfacing. It takes 3 address lines (like $A_3, A_4, A_5$) and can select one of 8 different I/O devices.

In your exam, you might see a question asking how to decode a specific address. Let's look at the binary for Port `F0H`:

- **Hex**: `F0H`
- **Binary**: $1111\ 0000_2$

To decode this, the hardware would check if:

- $A_7, A_6, A_5, A_4$ are all **1**
- $A_3, A_2, A_1, A_0$ are all **0**

## Buffers and Latches

### Why do we need them? 

The CPU is a "fast talker," and external devices are "slow listeners."

- **Buffers (Input)**: Used when the CPU **reads** from a device. They act like a gate that only opens when the CPU is ready to "peek" at the data. Without a buffer, the device might constantly "scream" data onto the bus, causing a collision with other parts of the system. 

- **Latches (Output)**: Used when the CPU **writes** to a device. The CPU only puts data on the bus for a few nanoseconds. A latch "captures" that data and holds it steady so the device (like a printer or LED) has time to process it. 

---
# The 8255 Programmable Peripheral Interface (PPI)
## What Problem Does the 8255 Solve?

The 8086 can only talk to the outside world through `IN` and `OUT` instructions on its data bus. But external devices — printers, keyboards, sensors, LEDs — don't speak "data bus." They need dedicated I/O lines that can be held high or low, toggled, and controlled independently.

The 8255 gives you **24 such lines**, organized and controllable via software. Without it, you'd need custom hardware for every device. With it, you write a control word and you're done.

---

## Internal Architecture — What's Actually Inside

```
         ┌─────────────────────────────────────┐
         │              8255                   │
D0–D7 ───┤  Data Bus Buffer                    │
         │       ↕                             │
RD̄  ─────┤  Read/Write                         ├──── Port A (PA0–PA7)   8 lines
WR̄  ─────┤  Control Logic  ←── Group A Control │
CS̄  ─────┤                 ←── Group B Control ├──── Port B (PB0–PB7)   8 lines
A0  ─────┤                                     │
A1  ─────┤                                     ├──── Port C (PC0–PC7)   8 lines
RESET ───┤                                     │
         └─────────────────────────────────────┘
```

### The Three Ports

|Port|Lines|Group|What It Can Do|
|---|---|---|---|
|Port A|PA0–PA7|Group A|Input, Output, or Bidirectional (Mode 0/1/2)|
|Port B|PB0–PB7|Group B|Input or Output (Mode 0/1 only)|
|Port C|PC0–PC7|Split|Upper (PC4–PC7) → Group A, Lower (PC0–PC3) → Group B|

Port C is not just a regular I/O port. In Modes 1 and 2, **it gets hijacked** to carry handshaking signals. This is crucial — remember it.

---

## Address Lines A0 and A1 — How the CPU Talks to the Right Register

The 8255 has 4 internal registers. You select which one you're talking to using **A0 and A1** on the address bus:

|A1|A0|Register Selected|
|---|---|---|
|0|0|Port A|
|0|1|Port B|
|1|0|Port C|
|1|1|**Control Register**|

In practice, if your 8255 is mapped to base address `60H`, then:

- `60H` → Port A
- `61H` → Port B
- `62H` → Port C
- `63H` → Control Register

**You always write the control word to the control register (A1=1, A0=1) first, before doing anything else.**

---

## The Control Word — Decoded Bit by Bit

This is the most important thing in the entire 8255. Every programming question starts here.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌────┬──────┬────┬──────┬─────┬────┬──────┬─────┐
│ 1  │ Mode │Mode│  A   │ C   │Mode│  B   │ C   │
│    │  A   │  A │Dir   │upper│ B  │ Dir  │lower│
└────┴──────┴────┴──────┴─────┴────┴──────┴─────┘
  ↑     ↑      ↑    ↑      ↑    ↑    ↑     ↑      
  │    D6      D5   D4     D3   D2   D1    D0───┘
  │
  └── Must be 1 (this is an I/O mode control word)
```

Let's go through each bit:

|Bit|Name|Values|
|---|---|---|
|D7|Mode set flag|**Always 1** for setting I/O modes|
|D6–D5|Group A Mode|`00`=Mode 0, `01`=Mode 1, `1x`=Mode 2|
|D4|Port A Direction|`1`=Input, `0`=Output|
|D3|Port C Upper Direction (PC4–7)|`1`=Input, `0`=Output|
|D2|Group B Mode|`0`=Mode 0, `1`=Mode 1|
|D1|Port B Direction|`1`=Input, `0`=Output|
|D0|Port C Lower Direction (PC0–3)|`1`=Input, `0`=Output|

---

## How to Calculate a Control Word — The Process

**Step 1:** Start with D7 = 1 (mandatory). **Step 2:** Decide modes for Group A and Group B. **Step 3:** Decide direction (Input=1, Output=0) for each port. **Step 4:** Assemble the byte and convert to hex.

### Example 1: Port A = Output, Port B = Input, Port C = Output, All Mode 0

```
D7=1  (mode set)
D6=0, D5=0  (Group A = Mode 0)
D4=0  (Port A = Output)
D3=0  (Port C upper = Output)
D2=0  (Group B = Mode 0)
D1=1  (Port B = Input)
D0=0  (Port C lower = Output)

Binary: 1 00 0 0 0 1 0 = 1000 0010 = 82H
```

### Example 2: Port A = Input, Port B = Input, Port C = Input, All Mode 0

```
D7=1
D6=0, D5=0  (Mode 0)
D4=1  (Port A = Input)
D3=1  (Port C upper = Input)
D2=0  (Mode 0)
D1=1  (Port B = Input)
D0=1  (Port C lower = Input)

Binary: 1 00 1 1 0 1 1 = 1001 1011 = 9BH
```

---

## Mode 0 — Basic I/O (No Handshaking)

This is the simplest mode. Each port just acts as a plain input or output. No automatic signaling. The CPU reads or writes whenever it wants.

**When to use it:** When you control timing yourself in software — like lighting LEDs, reading DIP switches, simple sensors that are always ready.

### MASM Example: Read a Switch, Light an LED

Assume 8255 is at base `60H`. Port A is switches (input), Port B is LEDs (output).

```masm
; Control Word: Port A = Input, Port B = Output, Mode 0
; D7=1, D6D5=00, D4=1, D3=0, D2=0, D1=0, D0=0
; Binary: 1000 1000 = 90H  ← wait, let me redo:
; D7=1, D6=0, D5=0, D4=1(A=in), D3=0(Cup=out), D2=0(Bmode0), D1=0(B=out), D0=0(Clo=out)
; = 1000 0000 = 90H? No: 10010000 = 90H

; D7  D6  D5  D4  D3  D2  D1  D0
;  1   0   0   1   0   0   0   0   = 90H ✓

MOV AL, 90H         ; Control word: Port A = In, Port B = Out, Mode 0
OUT 63H, AL         ; Send to Control Register

LOOP_READ:
    IN  AL, 60H     ; Read 8 switches from Port A
    OUT 61H, AL     ; Mirror the switch state directly to LEDs on Port B
    JMP LOOP_READ   ; Repeat forever
```

That's Mode 0 in its purest form. The CPU controls everything, there's no hardware handshaking at all.

---

## Mode 1 — Strobed I/O (Hardware Handshaking)

This is where the 8255 becomes genuinely powerful. In Mode 1, the chip manages the STB/ACK/IBF/OBF handshaking signals **automatically in hardware**, so your software just reads or writes data without timing concerns.

**Port C is consumed for handshaking signals.** This is non-negotiable in Mode 1.

---

### Mode 1: Strobed INPUT (Group A)

When Port A is configured as a **strobed input**, an external device (say, a keyboard scanner) controls when it puts data onto Port A's lines. The signals are:

|Signal|Pin|Direction|Meaning|
|---|---|---|---|
|STB̄ (Strobe)|PC4|External → 8255|Device says: "Data is valid on Port A right now, latch it!"|
|IBF (Input Buffer Full)|PC5|8255 → External|8255 says: "I've latched the data, don't send more yet"|
|INTR (Interrupt Request)|PC3|8255 → CPU|8255 tells the CPU: "New data is waiting for you"|

**The hardware sequence:**

```
1. External device puts data on PA0–PA7
2. External device pulses STB̄ LOW
3. 8255 automatically latches the data into its internal buffer
4. 8255 raises IBF HIGH → tells device "I got it, stop sending"
5. 8255 raises INTR → signals the CPU to come read the data
6. CPU executes IN AL, Port_A
7. IBF drops LOW → device can now send the next byte
```

Notice: the CPU doesn't need to babysit the timing. The 8255 handles steps 3–5 entirely in hardware.

### Mode 1: Strobed OUTPUT (Group A)

When Port A is **strobed output** (e.g., connected to a printer):

|Signal|Pin|Direction|Meaning|
|---|---|---|---|
|OBF̄ (Output Buffer Full)|PC7|8255 → Device|8255 says: "New data is on Port A, come read it!"|
|ACK̄ (Acknowledge)|PC6|Device → 8255|Device says: "I took the data, you can send the next byte"|
|INTR|PC3|8255 → CPU|8255 tells CPU: "The device has taken the data, load the next byte"|

**The hardware sequence:**

```
1. CPU executes OUT Port_A, AL  → 8255 puts data on PA0–PA7
2. 8255 automatically pulls OBF̄ LOW → tells printer "data is ready"
3. Printer reads data and pulses ACK̄ LOW
4. 8255 raises OBF̄ back HIGH → output buffer is now empty
5. 8255 raises INTR → tells CPU "send the next byte"
6. CPU writes the next byte, cycle repeats
```

### Mode 1 Control Word

For Mode 1, Group A: D6=0, D5=1 (binary `01`). For Mode 1, Group B: D2=1.

**Example: Port A = Mode 1 Output, Port B = Mode 1 Input**

```
D7=1 (mode set)
D6=0, D5=1  → Group A = Mode 1
D4=0        → Port A = Output
D3=X        → Port C upper is taken by handshaking (don't care)
D2=1        → Group B = Mode 1
D1=1        → Port B = Input
D0=X        → Port C lower is taken by handshaking (don't care)

= 1 01 0 X 1 1 X

Set X = 0:
= 1010 0110 = A6H
```

### MASM Example: Interrupt-Driven Output to a Printer (Mode 1)

Assume 8255 base = `60H`. Port A is the data output to the printer. INTR is wired to IR5 of the 8259, which fires INT type 0DH.

```masm
DATA SEGMENT
    MSG DB 'HELLO', '$'       ; 5 bytes to print
    PTR_IDX DW 0              ; index into message
DATA ENDS

CODE SEGMENT
    ASSUME CS:CODE, DS:DATA

START:
    MOV AX, DATA
    MOV DS, AX

    ; --- Step 1: Program 8255 ---
    ; Port A = Mode 1 Output, Port B = Mode 0 Input (unused here)
    ; D7=1, D6D5=01(Mode1), D4=0(A=out), D3=0, D2=0(B=Mode0), D1=0, D0=0
    ; = 1010 0000 = A0H
    MOV AL, 0A0H
    OUT 63H, AL               ; Write control word

    ; --- Step 2: Install ISR for INT 0DH ---
    MOV AX, 0
    MOV ES, AX
    MOV WORD PTR ES:[0DH*4],   OFFSET PRINTER_ISR
    MOV WORD PTR ES:[0DH*4+2], SEG    PRINTER_ISR

    ; --- Step 3: Enable interrupts ---
    STI

    ; --- Step 4: Send the first byte to kick off the chain ---
    MOV BX, OFFSET MSG
    MOV AL, [BX]
    OUT 60H, AL               ; Write first byte to Port A
    INC PTR_IDX

    ; --- Step 5: Wait for all bytes to be sent ---
WAIT_DONE:
    CMP PTR_IDX, 5
    JL  WAIT_DONE
    ; (In real code, HLT or do other work here)

    MOV AH, 4CH
    INT 21H

; --- The ISR: called by 8255 INTR after each byte is ACKed ---
PRINTER_ISR PROC FAR
    PUSH AX
    PUSH BX

    MOV BX, PTR_IDX
    CMP BX, 5
    JGE DONE_PRINTING         ; No more bytes

    MOV AL, MSG[BX]           ; Get next byte
    OUT 60H, AL               ; Send to Port A → 8255 drives OBF̄ low
    INC PTR_IDX

DONE_PRINTING:
    MOV AL, 20H               ; Non-specific EOI to 8259
    OUT 20H, AL

    POP BX
    POP AX
    IRET
PRINTER_ISR ENDP

CODE ENDS
END START
```

**What's happening here:**

- The 8255 sends each byte, the printer ACKs it, the 8255 fires INTR, the ISR runs and sends the next byte. The CPU is free during printing. This is hardware-managed handshaking at work.

---

## Mode 2 — Bidirectional I/O (Port A Only)

Mode 2 is only available for **Port A**. It makes Port A a **full bidirectional bus** — data can flow in either direction on the same 8 lines. This is useful for computer-to-computer communication, GPIB bus interfacing, etc.

In Mode 2, **Port C gives up 5 pins** for handshaking (both input and output signals combined):

|Signal|Pin|Direction|Role|
|---|---|---|---|
|OBF̄|PC7|Out|Output buffer full (CPU wrote data)|
|ACK̄|PC6|In|External device acknowledged the output|
|STB̄|PC4|In|External device is strobing data in|
|IBF|PC5|Out|Input buffer full (data latched from bus)|
|INTR|PC3|Out|Interrupt to CPU (for both directions)|

### Mode 2 Control Word

Mode 2 for Group A: D6=1, D5=X → set D6D5=1X, so use `10` or `11`.

**Example: Port A = Mode 2, Port B = Mode 0 Output**

```
D7=1
D6=1, D5=0  → Group A = Mode 2
D4=X        → don't care (direction is both)
D3=X        → PC upper used by Mode 2
D2=0        → Group B = Mode 0
D1=0        → Port B = Output
D0=X        → set to 0

= 1 10 X X 0 0 0
= 1100 0000 = C0H  (with X=0)
```

### Bit Set/Reset (BSR) — The Other Control Word

There's a second type of control word for the 8255 that **D7 = 0** selects. This is **not** an I/O mode word — it sets or resets individual bits of Port C.

```
D7=0 (BSR mode — bit set/reset)
D6–D4: don't care (set to 0)
D3–D1: bit number (which PC pin to affect)
D0: 1 = SET, 0 = RESET
```

**Example: Set PC3 high (to manually trigger INTR for testing)**

```
D7=0, D6D5D4=000, D3D2D1=011 (bit 3), D0=1 (set)
= 0000 0111 = 07H
```

```masm
MOV AL, 07H
OUT 63H, AL    ; Sets PC3 HIGH
```

**Example: Clear PC3**

```
= 0000 0110 = 06H
```

This is extremely useful for generating the Data Strobe (DS) signal manually from software when Mode 1 handshaking doesn't fit your timing needs.

---

## Summary: Choosing the Right Mode

|Situation|Mode|
|---|---|
|Simple LEDs, switches, sensors with no timing concern|Mode 0|
|Printer, keyboard — device signals when ready|Mode 1|
|Two-way data bus between two computers or GPIB|Mode 2|
|Toggle a single Port C pin from software|BSR (D7=0)|

---

## Full Programming Checklist for 8255

Every time you program the 8255, do this in order — no exceptions:

**1.** Decode the base address. Know which port is at which address (A1A0 selects it).

**2.** Calculate the control word from scratch using the bit table. Don't guess.

**3.** Send the control word to the control register (base + 3) first, before touching any port.

**4.** In Mode 1/2, check which Port C pins are still free (not consumed by handshaking) if you need extra I/O.

**5.** If using interrupts, enable the INTE bit via BSR on PC4 (Group A input) or PC6 (Group A output) — the INTR output of 8255 is only active if INTE is set.

---

## Quick Practice Problem

**Q:** The 8255 is mapped to ports `C0H` to `C3H`. Configure it so that: Port A is Mode 1 strobed input, Port B is Mode 0 output, Port C lower is output.

**Solution:**

```
Base = C0H → Port A=C0H, Port B=C1H, Port C=C2H, Control=C3H

D7 = 1        (mode set)
D6D5 = 01     (Group A = Mode 1)
D4 = 1        (Port A = Input)
D3 = X → 0   (PC upper consumed by Mode 1 handshaking)
D2 = 0        (Group B = Mode 0)
D1 = 0        (Port B = Output)
D0 = 0        (Port C lower = Output)

= 1 01 1 0 0 0 0 = 1011 0000 = B0H
```

```masm
MOV AL, 0B0H
OUT 0C3H, AL    ; Initialize 8255
```

---
# The 8254 Programmable Interval Timer

## What Problem Does the 8254 Solve?

Your 8086 runs at a fixed clock speed. But real systems constantly need to answer questions like:

- _"Send an interrupt to the CPU exactly every 1 millisecond"_ → real-time clock
- _"Generate a 1 kHz square wave to drive a speaker"_ → tone generation
- _"Refresh DRAM every 2 µs or it loses data"_ → memory refresh
- _"Count how many external events happened in the last second"_ → event counter

You could do all of this in software with loops. But then the CPU is doing nothing else. The 8254 does it **entirely in hardware** — you program it once, and it runs independently, forever, without touching the CPU.

It's a **self-contained counting engine** that you aim and fire.

---

## Internal Architecture

The 8254 has **three completely independent 16-bit counters** inside one chip. Each counter is its own unit — different mode, different count, different output. They don't know about each other.

```
                    ┌──────────────────────────────────────┐
                    │               8254                   │
                    │                                      │
D0–D7 ──────────────┤  Data Bus Buffer                     │
                    │       ↕                              │
RD̄  ────────────────┤  Read/Write                          │
WR̄  ────────────────┤  Control Logic                       │
CS̄  ────────────────┤                                      │
A0  ────────────────┤  Address                             │
A1  ────────────────┤  Decoder                             │
                    │      │                               │
                    │      ├──→ Counter 0 ←─ CLK0  OUT0 ───┤
                    │      │               ←─ GATE0        │
                    │      │                               │
                    │      ├──→ Counter 1 ←─ CLK1  OUT1 ───┤
                    │      │              ←─ GATE1         │
                    │      │                               │
                    │      └──→ Counter 2 ←─ CLK2  OUT2 ───┤
                    │                     ←─ GATE2         │
                    └──────────────────────────────────────┘
```

Each counter has exactly **three external connections:**

|Pin|Direction|Purpose|
|---|---|---|
|**CLK**|External → Counter|The clock. Every rising edge, the counter decrements by 1|
|**GATE**|External → Counter|Controls whether the counter runs. Behavior depends on mode|
|**OUT**|Counter → External|The output waveform or pulse. This is what you connect to your circuit|

---

## Address Selection — A0 and A1

Just like the 8255, you select which internal register to access using A0 and A1:

|A1|A0|Register|
|---|---|---|
|0|0|Counter 0|
|0|1|Counter 1|
|1|0|Counter 2|
|1|1|**Control Register**|

If the 8254 is mapped to base `40H` (standard PC mapping):

- `40H` → Counter 0
- `41H` → Counter 1
- `42H` → Counter 2
- `43H` → Control Register

---

## The Internal Structure of One Counter

This is what's actually inside each of the three counters. Understanding this prevents mistakes when programming.

```
      ┌────────────────────────────────────────────────┐
      │                  Counter N                     │
      │                                                │
      │  CR (Count Register)  ← CPU writes count here  │
      │         │                                      │
      │         ↓ (transferred to CE on trigger)       │
      │  CE (Counting Element) ← actual 16-bit counter │
      │    ↑ CLK                   ↓ counts down       │
      │    │                       │                   │
      │  GATE                 OL (Output Latch)        │
      │  (controls CE)        ← CPU reads from here    │
      └────────────────────────────────────────────────┘
```

**CR (Count Register):** When you write a count value via `OUT`, it goes here first. It does **not** immediately load into CE.

**CE (Counting Element):** The actual 16-bit synchronous down counter. It decrements on every CLK edge. When it reaches 0, it triggers whatever the mode dictates.

**OL (Output Latch):** Normally follows CE in real-time. When you issue a latch command, it freezes — the CPU can then read a stable value even while CE keeps counting.

**Why does this matter?** Because writing the count and the counter actually starting are two different events. In some modes, the count loads immediately. In others, you need a GATE trigger first. Get this wrong and your counter never starts.

---

## The Control Word — Every Bit Explained

Before you write any count, you must send a control word to the control register (`43H`). This tells the 8254 which counter you're programming, how to read/write it, what mode it's in, and whether to count in binary or BCD.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│ SC1 │ SC0 │ RL1 │ RL0 │ M2  │ M1  │ M0  │ BCD │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```

### SC1, SC0 — Select Counter

|SC1|SC0|Counter Selected|
|---|---|---|
|0|0|Counter 0|
|0|1|Counter 1|
|1|0|Counter 2|
|1|1|Read-Back Command (special — covered later)|

### RL1, RL0 — Read/Load (How the count is transferred)

This is subtle and catches people out constantly.

|RL1|RL0|Meaning|
|---|---|---|
|0|0|Counter Latch Command (freeze OL for reading)|
|0|1|Read/Write **LSB only**|
|1|0|Read/Write **MSB only**|
|1|1|Read/Write **LSB first, then MSB** (most common)|

For a 16-bit count, you almost always use `11` — two separate byte writes. The 8254 has an internal flip-flop that tracks whether the next byte is the LSB or MSB. First `OUT` → LSB. Second `OUT` → MSB. This is why you must always write the count in two steps for 16-bit values.

### M2, M1, M0 — Mode Select

|M2|M1|M0|Mode|
|---|---|---|---|
|0|0|0|Mode 0 — Interrupt on Terminal Count|
|0|0|1|Mode 1 — Hardware Retriggerable One-Shot|
|0|1|0|Mode 2 — Rate Generator|
|0|1|1|Mode 3 — Square Wave Generator|
|1|0|0|Mode 4 — Software Triggered Strobe|
|1|0|1|Mode 5 — Hardware Triggered Strobe|

### BCD — Binary or BCD Count

|BCD|Counting Mode|
|---|---|
|0|Binary (count 0000H to FFFFH, max 65535)|
|1|BCD (count 0000 to 9999 in BCD, max 9999)|

Use binary (BCD=0) unless you specifically need a decimal count. Binary gives you a larger range.

---

## The Programming Sequence — Always Follow This Order

```
Step 1: Write control word to port 43H
         ↓
Step 2: Write LSB of count to counter port (40H/41H/42H)
         ↓
Step 3: Write MSB of count to the same counter port
         ↓
Counter starts running (either immediately or on next GATE trigger)
```

Never write the count before the control word. Never write only one byte for a 16-bit count when RL=11. Both mistakes produce undefined behavior.

---

## Calculating the Count Value N

This is the most common calculation in any 8254 problem.

The counter decrements once per CLK period. When it reaches 0, the output does something (depends on mode). So:

$$N = \frac{f_{CLK}}{f_{OUT}}$$

or equivalently:

$$N = \frac{T_{OUT}}{T_{CLK}}$$

**Example:** CLK = 2 MHz, you want an output frequency of 1 kHz.

$$N = \frac{2{,}000{,}000}{1{,}000} = 2000 = 07D0H$$

**Example:** CLK = 1.19318 MHz (the actual PC system clock to the 8254), you want the real-time clock interrupt at ~18.2 Hz:

$$N = \frac{1{,}193{,}180}{18.2} \approx 65536 = 0000H$$

In the 8254, writing `0000H` means a count of **65536** (the counter wraps from 0 to FFFFH and counts from there). This is exactly how the IBM PC generates its 18.2 Hz timer tick.

---

## Mode 0 — Interrupt on Terminal Count

### Behavior

- OUT starts **LOW** when you write the control word
- Count loads and starts decrementing on the next CLK after the count is written
- When CE hits 0, OUT goes **HIGH** and **stays HIGH**
- It does not repeat. It's a one-shot event
- GATE must be HIGH for counting to occur. Pull GATE LOW to pause

```
CLK:   ─┐─┐─┐─┐─┐─┐─┐─┐─┐─┐─
GATE:  ────────────────────────  (HIGH = counting enabled)
OUT:   ──────────────┐──────────
                     ↑
                 N CLK cycles after count written
                 (OUT goes HIGH, stays HIGH)
```

### When to Use

Generating a single interrupt after a precise delay. Write count, connect OUT to INTR, CPU gets interrupted exactly N clocks later. Then reprogram for the next event.

### MASM Example: Generate interrupt after exactly 5 ms

Assume CLK = 2 MHz. N = 2,000,000 × 0.005 = **10,000 = 2710H**. Counter 0 at `40H`. GATE0 wired HIGH permanently.

```masm
; Program Counter 0, Mode 0, Binary, LSB then MSB
; SC=00, RL=11, M=000, BCD=0
; D7D6=00, D5D4=11, D3D2D1=000, D0=0
; = 0011 0000 = 30H

MOV AL, 30H
OUT 43H, AL         ; Control word → Counter 0, Mode 0, binary, LSB/MSB

MOV AL, 10H         ; LSB of 2710H = 10H
OUT 40H, AL         ; Write LSB first

MOV AL, 27H         ; MSB of 2710H = 27H
OUT 40H, AL         ; Write MSB — counter starts NOW

; CPU continues doing other work
; OUT0 goes HIGH after exactly 5 ms → triggers interrupt
```

**Key point:** The moment you write the MSB, the count transfers from CR to CE and counting begins. If GATE is LOW at this point, it waits — GATE going HIGH kicks it off.

---

## Mode 1 — Hardware Retriggerable One-Shot

### Behavior

- OUT starts **HIGH**
- Writing the count does NOT start anything — the counter waits
- When GATE has a **rising edge** (LOW→HIGH), CE loads the count and OUT goes **LOW**
- OUT stays LOW for exactly N CLK cycles, then goes HIGH again
- While OUT is LOW, if GATE gets another rising edge, the counter **reloads** N and starts over (retriggerable)
- The OUT pulse width = N × T_CLK regardless of how long GATE was high

```
GATE:  ───┐└──────────────┐└────────────────
         ↑ rising           ↑ retrigger
OUT:   ───┘─────────────┐└──┘─────────────┐└──
         N cycles pulse      N cycles again
```

### When to Use

Watchdog timers. Debounce circuits. Any time you want a precise fixed-length pulse triggered by an external event. The retrigger behavior means a repeated trigger before the count expires simply restarts the countdown.

### MASM Example: Set up Counter 1 as a 2 ms one-shot

CLK = 1 MHz. N = 1,000,000 × 0.002 = **2000 = 07D0H**

```masm
; SC=01 (Counter 1), RL=11, M=001 (Mode 1), BCD=0
; D7D6=01, D5D4=11, D3D2D1=001, D0=0
; = 0111 0010 = 72H? Let me verify:
; D7=0,D6=1,D5=1,D4=1,D3=0,D2=0,D1=1,D0=0 = 0111 0010 = 72H ✓

MOV AL, 72H
OUT 43H, AL         ; Counter 1, Mode 1, binary, LSB/MSB

MOV AL, 0D0H        ; LSB of 07D0H = D0H
OUT 41H, AL

MOV AL, 07H         ; MSB of 07D0H = 07H
OUT 41H, AL

; Counter is armed. Nothing happens until GATE1 gets a rising edge.
; When GATE1 pulses HIGH, OUT1 goes LOW for exactly 2 ms, then HIGH.
```

---

## Mode 2 — Rate Generator

### Behavior

- This mode **repeats automatically forever** — no reprogramming needed
- OUT is HIGH most of the time
- Every N CLK cycles, OUT goes LOW for exactly **1 CLK period**, then HIGH again
- This produces a regular pulse train at frequency = f_CLK / N
- GATE HIGH = running, GATE LOW = pauses, next cycle starts from N when GATE returns HIGH

#### The Timing Diagram — Proper Version

Here N = 5 as an example. The counter counts 5 → 4 → 3 → 2 → 1 → pulse → reload → repeat.

```
         1   2   3   4   5   1   2   3   4   5   1   2   3   4   5
         ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓   ↓
CLK  ____┌───┐   ┌───┐   ┌───┐   ┌───┐   ┌───┐   ┌───┐   ┌───┐   ┌───┐
         │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │
         ┘   └───┘   └───┘   └───┘   └───┘   └───┘   └───┘   └───┘   └

         ___________________________________________
OUT  ___/ HIGH        HIGH  \__/ HIGH        HIGH  \__/ HIGH
    |                        |  |                   |  |
    |←── 4 CLKs HIGH ───────→|LO|←── 4 CLKs HIGH ──→|LO|←── repeats

         ←────────── N = 5 CLKs per cycle ─────────→
```

The output is **not** a square wave (1 clock low, N-1 clocks high). Mode 3 makes square waves — Mode 2 makes a narrow pulse train.

#### GATE Behaviour Visualised

```
GATE  ───────────────┐           ┌────────────────────────────
                     │   LOW     │
                     └───────────┘

CE    ────5─4─3──────╔═══════════╗════5─4─3─2─1─5─4─3─2─1───
                     ║  frozen   ║ ↑
                     ║  (paused) ║ resumes from N when GATE returns HIGH

OUT   ─────────────┐ ╔═══════════╗─────────────────────┐
                   │L║  frozen   ║HIGH  HIGH  HIGH  HIGH│L
                   └─╚═══════════╝                      └────
```

- GATE goes LOW mid-count → counter **freezes**, OUT goes **HIGH immediately** (even if it was in its LOW phase)
- GATE returns HIGH → counter **reloads N from scratch** and starts a fresh cycle
- It does **not** resume from where it paused — it resets to N

### Real PC Application

Counter 1 in the IBM PC runs in Mode 2 with N=18 to generate the DRAM refresh request every ~15 µs. The CPU never deals with this — 8254 handles it autonomously.

### MASM Example: 10 kHz pulse train on Counter 2, CLK = 2 MHz

N = 2,000,000 / 10,000 = **200 = 00C8H**

```masm
; SC=10 (Counter 2), RL=11, M=010 (Mode 2), BCD=0
; D7=1,D6=0,D5=1,D4=1,D3=0,D2=1,D1=0,D0=0
; = 1011 0100 = B4H

MOV AL, 0B4H
OUT 43H, AL         ; Counter 2, Mode 2, binary, LSB/MSB

MOV AL, 0C8H        ; LSB of 00C8H = C8H
OUT 42H, AL

MOV AL, 00H         ; MSB = 00H
OUT 42H, AL

; OUT2 now produces a 10 kHz pulse train — forever, without CPU involvement
```

---

## Mode 3 — Square Wave Generator

### Behavior

This is Mode 2's cleaner cousin. Same repeat-forever behavior, but OUT produces an **actual square wave:**

- For **even N:** OUT is HIGH for N/2 clocks, LOW for N/2 clocks → perfect 50% duty cycle
- For **odd N:** OUT is HIGH for (N+1)/2 clocks, LOW for (N-1)/2 clocks → close to 50%
- GATE behavior same as Mode 2

```
N=4 (even):
CLK:  ─┐─┐─┐─┐─┐─┐─┐─┐─┐─┐─
OUT:  ───┐└──┘┌──┘┌──┘┌──
       2 hi  2 lo  2 hi  2 lo

N=5 (odd):
CLK:  ─┐─┐─┐─┐─┐─┐─┐─┐─┐─┐─
OUT:  ────┐└──┘┌───┘┌──┘┌───
       3 hi  2 lo  3 hi 2 lo
```

### Real PC Application

Counter 2 of the PC's 8254 drives the PC speaker. When you write a frequency count and enable the speaker gate via port `61H`, Mode 3 generates the tone waveform. The speaker directly follows OUT2.

### MASM Example: Play a 440 Hz tone (musical note A4)

PC system clock = 1.19318 MHz. N = 1,193,180 / 440 = **2712 = 0A98H**

```masm
; SC=10 (Counter 2), RL=11, M=011 (Mode 3), BCD=0
; D7=1,D6=0,D5=1,D4=1,D3=0,D2=1,D1=1,D0=0
; = 1011 0110 = B6H

MOV AL, 0B6H
OUT 43H, AL         ; Counter 2, Mode 3, binary, LSB/MSB

MOV AL, 098H        ; LSB of 0A98H = 98H
OUT 42H, AL

MOV AL, 00AH        ; MSB of 0A98H = 0AH
OUT 42H, AL

; Now enable the speaker via port 61H
IN  AL, 61H         ; Read current state of port 61H
OR  AL, 03H         ; Set bits 0 and 1 (GATE2 enable + speaker enable)
OUT 61H, AL         ; Speaker now produces 440 Hz tone

; Wait ~1 second (simplified — in reality use INT 15H or 8254 counter 0)
MOV CX, 0FFFFh
DELAY: LOOP DELAY

; Disable speaker
IN  AL, 61H
AND AL, 0FCH        ; Clear bits 0 and 1
OUT 61H, AL
```

---

## Mode 4 — Software Triggered Strobe

### Behavior

- OUT starts **HIGH**
- Writing the count arms the counter and it starts counting immediately (software triggered — no GATE needed)
- When CE reaches 0, OUT goes **LOW for exactly 1 CLK period**, then HIGH again
- It does **not** repeat — one count, one pulse, done
- To fire again, you must reprogram the count

```
CLK:  ─┐─┐─┐─┐─┐─┐─┐─┐─┐─┐─
OUT:  ───────────────────┐└───
      ← N clocks after writing count → 1-clock LOW pulse
```

### Compared to Mode 0

|Feature|Mode 0|Mode 4|
|---|---|---|
|OUT starts at|LOW|HIGH|
|After terminal count|Goes HIGH, stays|Goes LOW for 1 CLK, returns HIGH|
|Repeats?|No|No|
|Trigger|Software (writing count)|Software (writing count)|

Mode 0 is for interrupts (OUT stays HIGH → connected to INTR). Mode 4 is for generating a precise strobe pulse after a delay.

### MASM Example: Generate a strobe pulse 100 µs after command, CLK = 1 MHz

N = 1,000,000 × 0.0001 = **100 = 0064H**

```masm
; SC=00 (Counter 0), RL=11, M=100 (Mode 4), BCD=0
; D7=0,D6=0,D5=1,D4=1,D3=1,D2=0,D1=0,D0=0
; = 0011 1000 = 38H

MOV AL, 38H
OUT 43H, AL

MOV AL, 64H         ; LSB of 0064H
OUT 40H, AL

MOV AL, 00H         ; MSB
OUT 40H, AL

; Counting starts NOW. Exactly 100 µs later, OUT0 pulses LOW for 1 CLK.
; Connect OUT0 to a device's strobe input.
```

---

## Mode 5 — Hardware Triggered Strobe

### Behavior

Identical output to Mode 4 (single 1-CLK LOW pulse after N counts), but the trigger is **hardware** — a rising edge on GATE, not writing the count.

- Writing the count arms the counter but does NOT start it
- GATE rising edge starts the countdown
- If GATE gets another rising edge before count expires, the counter **reloads N and starts over** (retriggerable, same as Mode 1)
- OUT goes LOW for 1 CLK at terminal count, then returns HIGH

```
GATE: ──┐└───────────────────────────
         ↑ rising edge starts count

OUT:  ─────────────────────┐└────────
      ←── N clocks ──────→ strobe
```

### Mode 4 vs Mode 5 — The Critical Difference

|Feature|Mode 4|Mode 5|
|---|---|---|
|What starts counting|Writing the count|Rising edge on GATE|
|Retriggerable?|No|Yes|
|Use case|Fixed delay from software command|Delay from external hardware event|

---

## GATE Pin Summary — All Modes in One Table

This is exam gold. The GATE pin does something different in every mode.

|Mode|GATE LOW|GATE Rising Edge|GATE HIGH|
|---|---|---|---|
|0|Pauses count|Restarts count|Enables count|
|1|No effect|Starts/Reloads count|No effect|
|2|Pauses, resets OUT HIGH|Restarts from N|Enables count|
|3|Pauses, resets OUT HIGH|Restarts from N|Enables count|
|4|Pauses count|Restarts count|Enables count|
|5|No effect|Starts/Reloads count|No effect|

Notice the pattern: **Modes 1 and 5** (hardware triggered) only respond to GATE edges, not levels. **Modes 0, 2, 3, 4** (software triggered) respond to GATE levels — HIGH to run, LOW to pause.

---

## Reading the Counter — Two Methods

You need to read a counter's current value while it's still counting. But CE is changing every CLK cycle. If you read the LSB and then the MSB, the counter might have ticked between the two reads, giving you a garbage value. The 8254 solves this with two methods.

---

### Method 1: Counter Latch Command

This freezes the OL (output latch) at the current CE value. The counter keeps running, but OL holds the frozen value. You then read it at leisure. After reading, OL automatically resumes following CE.

The latch command is just a control word with **RL1=0, RL0=0:**

```
D7D6 = Counter select (SC)
D5D4 = 00   ← this is what makes it a latch command, not a mode command
D3–D0 = ignored (set to 0)
```

**Example: Latch and read Counter 0**

```masm
; Latch Counter 0
; SC=00, RL=00 → D7D6=00, D5D4=00, rest=0000
; = 0000 0000 = 00H

MOV AL, 00H
OUT 43H, AL         ; Latch Counter 0

; Now read — 2 bytes because count is 16-bit
IN  AL, 40H         ; Read LSB (arrives first)
MOV BL, AL          ; Save LSB

IN  AL, 40H         ; Read MSB (arrives second)
MOV BH, AL          ; Save MSB

; BX now contains the latched 16-bit count value
```

The 8254 knows LSB comes first because of the RL setting from when you originally programmed the counter. If you used RL=11, it gives LSB then MSB.

---

### Method 2: Read-Back Command (8254 only, not 8253)

More powerful. One command can latch **multiple counters simultaneously**, and can also latch the **status byte** of any counter.

The Read-Back command is triggered when SC1=SC0=1 in the control word (D7D6=11):

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  1  │  1  │CNT̄  │ STĀT│ CNT2│ CNT1│ CNT0│  0  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```

|Bit|Meaning|
|---|---|
|D5 (CNT̄)|**0** = Latch count of selected counters|
|D4 (STAT̄)|**0** = Latch status of selected counters|
|D3|1 = include Counter 2|
|D2|1 = include Counter 1|
|D1|1 = include Counter 0|
|D0|Always 0|

Note: bits are **active LOW** for CNT̄ and STAT̄ — set to 0 to activate.

**Example: Latch count AND status of Counter 0 and Counter 2 simultaneously**

```
D7D6=11 (read-back)
D5=0 (latch count — active LOW)
D4=0 (latch status — active LOW)
D3=1 (Counter 2)
D2=0 (Counter 1 — skip)
D1=1 (Counter 0)
D0=0

= 1100 1010 = CAH
```

```masm
MOV AL, 0CAH
OUT 43H, AL         ; Read-back: latch count+status for Counter 0 and Counter 2

; Read Counter 0 status byte first (it comes before the count)
IN  AL, 40H         ; Status byte of Counter 0
MOV DL, AL

; Read Counter 0 count
IN  AL, 40H         ; LSB
MOV BL, AL
IN  AL, 40H         ; MSB
MOV BH, AL

; Skip Counter 1 (not latched), read Counter 2 status
IN  AL, 42H         ; Status byte of Counter 2
MOV DH, AL

; Read Counter 2 count
IN  AL, 42H         ; LSB
IN  AL, 42H         ; MSB
```

---

### The Status Byte Format

When you latch the status, the byte you read back tells you the exact state of the counter at the moment of the read-back command:

```
  D7      D6    D5    D4    D3    D2    D1    D0
┌───────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│ OUT   │ NULL│ RL1 │ RL0 │ M2  │ M1  │ M0  │ BCD │
│ pin   │COUNT│     │     │     │     │     │     │
└───────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```

|Bit|Meaning|
|---|---|
|D7|Current state of the OUT pin (1=HIGH, 0=LOW)|
|D6|NULL COUNT: 1 = count has been written but not yet loaded into CE (counter hasn't started the new count yet)|
|D5–D0|Exact copy of the control word that programmed this counter|

**The NULL COUNT bit is critical:** If you write a new count and immediately try to read the counter, D6=1 means the new count isn't active yet — don't trust the reading. Wait until D6=0.

---

## Complete Worked Example

**Problem:** Using the 8254 at base `40H` with CLK = 2 MHz:

1. Configure Counter 0 as a real-time interrupt at 100 Hz (Mode 2)
2. Configure Counter 2 as a 1 kHz square wave for a speaker (Mode 3)
3. After setup, read Counter 0's current value

**Calculations:**

- Counter 0: N = 2,000,000 / 100 = **20,000 = 4E20H**
- Counter 2: N = 2,000,000 / 1,000 = **2,000 = 07D0H**

```masm
; ════════════════════════════════
; Counter 0: Mode 2 (Rate Generator), 100 Hz
; SC=00, RL=11, M=010, BCD=0
; D7=0,D6=0,D5=1,D4=1,D3=0,D2=1,D1=0,D0=0 = 0011 0100 = 34H
; ════════════════════════════════

MOV AL, 34H
OUT 43H, AL         ; Counter 0, Mode 2, binary

MOV AL, 020H        ; LSB of 4E20H = 20H
OUT 40H, AL

MOV AL, 04EH        ; MSB of 4E20H = 4EH
OUT 40H, AL

; Counter 0 now fires OUT0 LOW for 1 CLK every 10 ms (100 Hz)
; Wire OUT0 to INTR for a 100 Hz timer interrupt


; ════════════════════════════════
; Counter 2: Mode 3 (Square Wave), 1 kHz
; SC=10, RL=11, M=011, BCD=0
; D7=1,D6=0,D5=1,D4=1,D3=0,D2=1,D1=1,D0=0 = 1011 0110 = B6H
; ════════════════════════════════

MOV AL, 0B6H
OUT 43H, AL         ; Counter 2, Mode 3, binary

MOV AL, 0D0H        ; LSB of 07D0H = D0H
OUT 42H, AL

MOV AL, 007H        ; MSB of 07D0H = 07H
OUT 42H, AL

; OUT2 now produces a 1 kHz square wave


; ════════════════════════════════
; Read Counter 0's current value (Counter Latch method)
; SC=00, RL=00 → 0000 0000 = 00H
; ════════════════════════════════

MOV AL, 00H
OUT 43H, AL         ; Latch Counter 0

IN  AL, 40H         ; Read LSB
MOV BL, AL

IN  AL, 40H         ; Read MSB
MOV BH, AL

; BX = current count of Counter 0 (between 0 and 4E20H = 0 and 20000)
```

---

## Practice Problem

**Q:** The 8254 is at base `C0H`. CLK = 1.19318 MHz. Configure Counter 1 in Mode 1 (one-shot) to produce a 500 µs LOW pulse on OUT1 every time GATE1 is pulsed. Count in BCD.

**Work it out:**

```
N = 1,193,180 × 0.0005 = 596.59 ≈ 597 decimal

In BCD: 597 → 0597H (BCD)

Control word:
SC = 01  (Counter 1)
RL = 11  (LSB then MSB)
M  = 001 (Mode 1)
BCD = 1

D7=0,D6=1,D5=1,D4=1,D3=0,D2=0,D1=1,D0=1
= 0111 0011 = 73H
```

```masm
MOV AL, 73H
OUT 0C3H, AL        ; Counter 1, Mode 1, BCD, LSB/MSB

MOV AL, 97H         ; LSB of BCD 0597H = 97H
OUT 0C1H, AL

MOV AL, 05H         ; MSB of BCD 0597H = 05H
OUT 0C1H, AL

; Each rising edge on GATE1 now produces a 500 µs LOW pulse on OUT1.
; Retriggerable — a new GATE1 edge before 500 µs restarts the countdown.
```

---

## Module 3 Summary

|Mode|OUT Default|Trigger|Repeats?|Output Shape|
|---|---|---|---|---|
|0|LOW|Software|No|Goes HIGH at TC, stays|
|1|HIGH|GATE edge|No (retriggerable)|LOW pulse, N wide|
|2|HIGH|Software|**Yes**|1-CLK LOW pulse, period=N|
|3|HIGH|Software|**Yes**|Square wave, period=N|
|4|HIGH|Software|No|1-CLK LOW pulse after N|
|5|HIGH|GATE edge|No (retriggerable)|1-CLK LOW pulse after N|

**The three golden rules:**

1. Always send the control word before writing the count
2. For 16-bit counts (RL=11), always write LSB first, then MSB
3. Writing count = 0 means N = 65536 in binary mode

---

# DAC & ADC Interfacing with 8086

## What's the Point of This Module?

Everything so far has been digital — 1s and 0s, ports, counters. But the real world is analog. Temperature, pressure, sound, motor speed — none of these are discrete binary values. They're continuous.

To talk to the real world, you need two bridges:

- **DAC (Digital-to-Analog Converter):** Your 8086 writes a number, the DAC produces a proportional voltage. CPU → real world.
- **ADC (Analog-to-Digital Converter):** A sensor produces a voltage, the ADC converts it to a number the CPU can read. Real world → CPU.

This module covers both. It's shorter than Module 3 but the concepts are extremely concrete — you'll see exactly how voltages and binary numbers relate.


## Part 1: The DAC 0830

### What It Is

The DAC 0830 (also written DAC0830 or DAC-0830) is an **8-bit digital-to-analog converter**. You write an 8-bit value to it, it produces a proportional **current output**, which you then convert to a voltage using an op-amp.

8-bit means 256 possible output levels (0 to 255). That's your resolution.

---

### Internal Architecture

The 0830 has two internal stages chained together:

```
                 ┌─────────────────────────────────────────┐
                 │               DAC 0830                  │
                 │                                         │
  DI0–DI7 ───────┤  8-bit          8-bit                   │
                 │  Input    →   DAC        →  IOUT1  ─────┤──→ to op-amp
  ILE    ────────┤  Latch         Register                 │
  CS̄     ────────┤  (Stage 1)  (Stage 2)    →  IOUT2  ─────┤──→ (complement)
  WR̄1    ────────┤       ↑           ↑                     │
  WR̄2    ────────┤       │           │                     │
  XFER̄   ────────┤    WR̄1+CS̄+ILE  WR̄2+XFER̄                 │
                 │                                         │
  VREF   ────────┤  Reference voltage input                │
  AGND   ────────┤  Analog ground                          │
  DGND   ────────┤  Digital ground                         │
                 └─────────────────────────────────────────┘
```

**Two-stage latching is the key design choice here.** Here's why it exists:

- **Stage 1 (Input Latch):** Controlled by ILE, CS̄, WR̄1. Captures the byte from the data bus. Transparent when all three are active.
- **Stage 2 (DAC Register):** Controlled by WR̄2 and XFER̄. Transfers data from Stage 1 into the actual DAC. The analog output only changes when Stage 2 updates.

This two-stage design lets you **load multiple DACs simultaneously** — pre-load each one into Stage 1, then pulse XFER̄ to update all of them at exactly the same moment. Critical in multi-channel systems (stereo audio, XY plotters) where you can't have one channel update before the other.

---

### Pin Descriptions — Every Pin Matters

|Pin|Type|Description|
|---|---|---|
|DI0–DI7|Input|8-bit digital data. DI7 is MSB|
|ILE|Input|Input Latch Enable. Active HIGH. Must be HIGH for Stage 1 to accept data|
|CS̄|Input|Chip Select. Active LOW. Selects this DAC|
|WR̄1|Input|Write strobe for Stage 1. Active LOW|
|XFER̄|Input|Transfer signal. Active LOW. Triggers Stage 2 load|
|WR̄2|Input|Write strobe for Stage 2. Active LOW|
|VREF|Input|Reference voltage. Sets the full-scale output range|
|IOUT1|Output|Current output. Increases as digital input increases|
|IOUT2|Output|Complement of IOUT1. IOUT1 + IOUT2 = constant|
|RFB|—|Internal feedback resistor for op-amp. Connect to op-amp output|
|AGND|—|Analog ground|
|DGND|—|Digital ground|

**Simplest connection (single 8086 write):** Tie ILE HIGH permanently, tie XFER̄ and WR̄2 LOW permanently. Now Stage 2 is always transparent — data flows straight through when CS̄ and WR̄1 are asserted. A single `OUT` instruction updates the analog output directly.

---

### The R-2R Ladder — How It Actually Converts

Inside the DAC, each digital bit controls a switch connected to a resistor ladder. You don't need to memorize the circuit, but you need to understand the result:

Each bit contributes a weighted current to IOUT1:

$$I_{OUT1} = V_{REF} \times \frac{1}{R} \times \left(\frac{D7}{2^1} + \frac{D6}{2^2} + \frac{D5}{2^3} + ... + \frac{D0}{2^8}\right)$$

Simplified to what you actually use:

$$I_{OUT1} = V_{REF} \times \frac{D_{in}}{256 \times R}$$

Where $D_{in}$ is the 8-bit integer you wrote (0–255).

MSB (D7) contributes half the full-scale current. D6 contributes a quarter. D0 contributes 1/256. This is a **weighted binary** current summing network.

---

### Voltage Conversion — The Op-Amp Output Stage

IOUT1 is a current, not a voltage. To get a voltage, connect IOUT1 to the inverting input of an op-amp, with RFB as the feedback resistor:

```
         RFB (internal to DAC 0830)
         ┌───┤├───────────────────┐
         │                       │
IOUT1 ───┤(−)    Op-Amp       ───┤──→ V_OUT
         │(+)                    │
IOUT2 ───┘   (or AGND)           │
```

The output voltage is:

$$\boxed{V_{OUT} = -V_{REF} \times \frac{D_{in}}{256}}$$

The negative sign comes from the inverting op-amp configuration. To get a positive output, either use a negative VREF, or add a second inverting stage.

### Concrete Examples

Assume VREF = +5V:

|Digital Input|Calculation|VOUT|
|---|---|---|
|00H = 0|−5 × 0/256|0.000 V|
|40H = 64|−5 × 64/256|−1.250 V|
|80H = 128|−5 × 128/256|−2.500 V|
|C0H = 192|−5 × 192/256|−3.750 V|
|FFH = 255|−5 × 255/256|−4.980 V|

Notice: FFH gives you −4.980V, not −5.000V. You can **never reach** exactly −VREF because 256/256 would require a 9th bit. Full scale is always one LSB short. Each LSB step = VREF/256 = 5/256 ≈ **19.5 mV**.

### Reverse Calculation — What code gives me a target voltage?

$$D_{in} = \frac{|V_{OUT}|}{V_{REF}} \times 256$$

**Example:** You want −3.3V output. VREF = 5V.

$$D_{in} = \frac{3.3}{5} \times 256 = 169.0 \approx 169 = A9H$$

---

### Interfacing DAC 0830 with 8086 — Circuit

```
8086 Data Bus (D0–D7) ─────────────────→ DI0–DI7 (DAC 0830)
                                          ILE  ←── +5V (always enabled)
Address Decoder Output ─────────────────→ CS̄
8086 IOW̄ ──────────────────────────────→ WR̄1
                                          WR̄2 ←── GND (always low)
                                          XFER̄ ←── GND (always low)
VREF ←── +5V (or precision reference)
IOUT1 ──→ Op-Amp (−) input ──→ VOUT
RFB ──→ Op-Amp feedback
AGND, DGND ──→ Ground
```

With WR̄2 and XFER̄ tied LOW, every `OUT` instruction to the DAC's port address immediately updates the analog output. Simple, clean, direct.

---

### MASM Example 1: Output a Fixed Voltage

Output −2.5V. VREF = 5V. Required code = (2.5/5) × 256 = **128 = 80H**. DAC at port `D0H`.

```masm
MOV AL, 80H         ; Code for -2.5V output
OUT 0D0H, AL        ; Write to DAC → VOUT immediately = -2.5V
```

That's it. One instruction. The hardware does the rest.

---

### MASM Example 2: Sawtooth Waveform

A sawtooth wave ramps linearly from 0 to full scale, then instantly resets to 0 and repeats. In digital terms: count 00H → FFH, wrap back to 00H, repeat.

```masm
CODE SEGMENT
ASSUME CS:CODE

START:
    MOV AL, 00H             ; Start at 0

SAWTOOTH_LOOP:
    OUT 0D0H, AL            ; Output current value to DAC
    INC AL                  ; Increment (wraps 0FFH → 00H automatically)
    JMP SAWTOOTH_LOOP       ; Repeat forever

CODE ENDS
END START
```

The output voltage ramps from 0V to −4.98V, snaps back to 0V, and repeats. The frequency of the sawtooth depends on how fast the loop executes. At 5 MHz clock (200 ns per cycle), each `OUT + INC + JMP` takes roughly 4–6 cycles ≈ ~1 µs per step × 256 steps ≈ **256 µs period ≈ 3.9 kHz sawtooth**.

---

### MASM Example 3: Triangle Wave

Ramp up 00H→FFH, then ramp back down FFH→00H, repeat. Two nested directions.

```masm
CODE SEGMENT
ASSUME CS:CODE

START:
    MOV AL, 00H

RAMP_UP:
    OUT 0D0H, AL
    INC AL
    JNZ RAMP_UP             ; Keep going until AL wraps to 0 (after FFH)

    ; AL is now 00H after overflow. Ramp down from FFH.
    DEC AL                  ; AL = FFH

RAMP_DOWN:
    OUT 0D0H, AL
    DEC AL
    JNZ RAMP_DOWN           ; Keep going until AL reaches 0

    ; AL is now 0, output it once then go back up
    OUT 0D0H, AL
    JMP RAMP_UP

CODE ENDS
END START
```

---

### MASM Example 4: Sine Wave (Table Lookup)

You can't calculate sin() in real-time efficiently in assembly. Pre-compute 256 samples of one sine cycle and store them in a lookup table. Then read and output them in sequence.

```masm
DATA SEGMENT
    ; 256 samples of sin(x) mapped to 0–255 range
    ; Formula: sample[i] = 128 + 127*sin(2*pi*i/256)
    ; First 16 values shown — full table would have 256 entries
    SINE_TABLE DB 128,131,134,137,140,143,146,149
               DB 152,155,158,162,165,168,171,174
               ; ... (240 more entries)
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

START:
    MOV AX, DATA
    MOV DS, AX
    MOV BX, OFFSET SINE_TABLE

SINE_LOOP:
    MOV AL, [BX]            ; Read next sample from table
    OUT 0D0H, AL            ; Output to DAC
    INC BX                  ; Next sample
    MOV CX, OFFSET SINE_TABLE
    ADD CX, 256
    CMP BX, CX              ; Past end of table?
    JL  SINE_LOOP
    MOV BX, OFFSET SINE_TABLE  ; Reset to start
    JMP SINE_LOOP

CODE ENDS
END START
```

The output is a smooth sine wave. The frequency is determined by how fast you cycle through the table — add delay loops to lower the frequency, use an 8254 timer interrupt to control it precisely.

---

## Part 2: The ADC — Analog to Digital Conversion

### The Core Problem

An ADC takes a continuous voltage (say, 0–5V from a temperature sensor) and produces a binary number. The 8086 can then process that number.

The key parameter is **resolution** — an 8-bit ADC splits the input range into 256 steps. A 12-bit ADC gives 4096 steps. Higher resolution = smaller voltage difference you can detect.

$$\text{LSB voltage} = \frac{V_{REF}}{2^n}$$

For an 8-bit ADC with VREF = 5V: each step = 5/256 ≈ **19.5 mV**. You can't distinguish two voltages less than 19.5 mV apart.

---

### The Successive Approximation ADC — How It Works

The most common ADC architecture in embedded systems. The 8086 slides use this type. Here's the mechanism:

It works exactly like the children's game of guessing a number between 1 and 256 using "higher/lower" — but it always starts from the middle:

```
Step 1: Try 10000000 (128) → Is Vin > 1.25V? Yes → keep bit 7 = 1
Step 2: Try 11000000 (192) → Is Vin > 1.875V? No  → set bit 6 = 0
Step 3: Try 10100000 (160) → Is Vin > 1.5625V? Yes → keep bit 5 = 1
Step 4: Try 10110000 (144) → Is Vin > 1.40625V? No → set bit 4 = 0
...and so on for all 8 bits
```

**It always takes exactly N clock cycles for an N-bit ADC.** 8-bit SAR ADC = 8 clock cycles to complete one conversion. This is predictable — unlike some other ADC types.

### Internal Block Diagram

```
                 ┌──────────────────────────────────┐
                 │        SAR ADC                   │
                 │                                  │
Vin ─────────────┤→ Comparator ←── Internal DAC ←───┤
                 │      │               ↑           │
                 │      ↓               │           │
                 │  SAR Logic ──────────┘           │
                 │  (Successive                     │
                 │   Approximation                  │
                 │   Register)                      │
                 │      │                           │
                 │      ↓                           │
                 │  Output Register ──→ DO0–DO7 ────┤──→ CPU data bus
                 │                                  │
CLK ─────────────┤                                  │
SC (Start) ──────┤  Status: EOC (End of Convert) ───┤──→ CPU status port
                 └──────────────────────────────────┘
```

**The SAR Logic:**

1. Loads 10000000 into the internal DAC
2. Compares Vin to the DAC output
3. Keeps or clears the bit based on comparator result
4. Shifts to the next bit, repeats
5. After 8 steps, the output register holds the final digital value
6. Pulses EOC (End of Conversion) to tell the CPU the result is ready

---

### Interfacing the SAR ADC with the 8086

The ADC needs two control signals from the CPU:

|Signal|Direction|Purpose|
|---|---|---|
|SC / START|CPU → ADC|Pulse to start a new conversion|
|EOC|ADC → CPU|Goes HIGH when conversion is complete and data is valid|

And two data paths:

|Path|Direction|Purpose|
|---|---|---|
|DO0–DO7|ADC → CPU|8-bit conversion result|
|Status port|ADC → CPU|CPU reads EOC bit via `IN` instruction|

### Two Ways to Know When Conversion is Done

**Method 1: Polling (what the slides cover)**

The CPU starts conversion, then sits in a loop checking EOC. Simple, no hardware complications, but wastes CPU time.

```
CPU:  Pulse SC → Wait → Poll EOC → If EOC=0, poll again → EOC=1 → Read result
```

**Method 2: Interrupt-Driven**

Wire EOC to INTR (via 8259). CPU starts conversion and goes off to do other work. When conversion is done, ADC interrupts the CPU. CPU reads result in ISR. More efficient but more hardware.

We focus on polling here since interrupts are Module 5.

---

### MASM Example: Read ADC via Polling

Assume:

- ADC data output at port `50H`
- ADC control port at `51H`
    - Bit 0 of `51H` controls SC (write 1 to pulse start)
    - Bit 7 of `51H` is EOC (read 1 = conversion done)

```masm
CODE SEGMENT
ASSUME CS:CODE

READ_ADC PROC NEAR
    ; Step 1: Pulse the START CONVERSION signal
    MOV AL, 01H             ; SC = 1
    OUT 51H, AL             ; Send to control port — conversion begins
    MOV AL, 00H             ; SC = 0 (pulse, not a level)
    OUT 51H, AL             ; Pull SC back low

    ; Step 2: Poll EOC until conversion is complete
WAIT_EOC:
    IN  AL, 51H             ; Read status port
    TEST AL, 80H            ; Check bit 7 (EOC)
    JZ  WAIT_EOC            ; If EOC=0, conversion still running, keep polling

    ; Step 3: EOC=1 — Read the result
    IN  AL, 50H             ; Read 8-bit result from ADC data port

    ; AL now contains the digital value of Vin
    ; Convert to voltage: Vin = (AL / 256) * VREF
    RET

READ_ADC ENDP

CODE ENDS
END
```

**What's happening step by step:**

- `OUT 51H` with bit 0 high pulses SC — the ADC resets its internal SAR and begins the 8-step comparison
- The `TEST AL, 80H` checks only bit 7 — if it's 0 (zero flag set → JZ jumps), conversion isn't done
- Once EOC goes high, the loop exits and you read the stable result from port `50H`

---

### MASM Example: Continuous Sampling — Data Logger

Read the ADC repeatedly, store 100 samples in memory, then process them.

```masm
DATA SEGMENT
    SAMPLES DB 100 DUP(0)   ; 100 bytes of storage
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

START:
    MOV AX, DATA
    MOV DS, AX

    MOV CX, 100             ; Sample count
    MOV DI, OFFSET SAMPLES  ; Pointer to storage buffer

SAMPLE_LOOP:
    ; Start conversion
    MOV AL, 01H
    OUT 51H, AL
    MOV AL, 00H
    OUT 51H, AL

    ; Wait for EOC
POLL:
    IN  AL, 51H
    TEST AL, 80H
    JZ  POLL

    ; Read and store result
    IN  AL, 50H
    MOV [DI], AL            ; Store sample in buffer
    INC DI                  ; Next buffer slot

    LOOP SAMPLE_LOOP        ; CX--, repeat until CX=0

    ; All 100 samples collected in SAMPLES array
    ; Now process them (find average, detect threshold, etc.)

    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
```

---

### MASM Example: Voltage Threshold Alert

Read ADC continuously. If the voltage exceeds 3.5V, output FFH to Port B (connected to an alarm). VREF = 5V.

Threshold calculation:

$$D_{threshold} = \frac{3.5}{5} \times 256 = 179.2 \approx 179 = B3H$$

```masm
CODE SEGMENT
ASSUME CS:CODE

THRESHOLD EQU 0B3H          ; 179 decimal = 3.5V threshold
ADC_DATA  EQU 50H
ADC_CTRL  EQU 51H
ALARM_PORT EQU 61H

START:

MONITOR_LOOP:
    ; Start conversion
    MOV AL, 01H
    OUT ADC_CTRL, AL
    MOV AL, 00H
    OUT ADC_CTRL, AL

    ; Poll EOC
WAIT:
    IN  AL, ADC_CTRL
    TEST AL, 80H
    JZ  WAIT

    ; Read result
    IN  AL, ADC_DATA

    ; Compare to threshold
    CMP AL, THRESHOLD
    JB  NO_ALARM            ; Jump if Below threshold — no alarm

    ; Voltage exceeded threshold — trigger alarm
    MOV AL, 0FFH
    OUT ALARM_PORT, AL      ; All 8 alarm lines HIGH
    JMP MONITOR_LOOP

NO_ALARM:
    MOV AL, 00H
    OUT ALARM_PORT, AL      ; All alarm lines LOW
    JMP MONITOR_LOOP

CODE ENDS
END START
```

---

### DAC + ADC Together: Closed-Loop Control

This is where both chips earn their keep simultaneously. A classic use case: **motor speed control**.

```
Desired Speed → CPU → DAC → Motor Driver → Motor
                               ↑                ↓
                            (adjust)       Speed Sensor
                               │                ↓
                            CPU ←────────── ADC ←──
                          (compare and correct)
```

```masm
DATA SEGMENT
    SETPOINT DB 80H         ; Desired speed = half speed (128/256)
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

START:
    MOV AX, DATA
    MOV DS, AX

    MOV BL, [SETPOINT]      ; Target value

CONTROL_LOOP:
    ; Step 1: Read actual speed from ADC
    MOV AL, 01H
    OUT 51H, AL             ; Start conversion
    MOV AL, 00H
    OUT 51H, AL

WAIT_EOC:
    IN  AL, 51H
    TEST AL, 80H
    JZ  WAIT_EOC

    IN  AL, 50H             ; AL = actual speed (digital)

    ; Step 2: Compare actual vs setpoint
    CMP AL, BL
    JE  NO_CHANGE           ; Exactly right, do nothing
    JA  TOO_FAST            ; Actual > setpoint

TOO_SLOW:
    ; Actual < setpoint: increase DAC output to speed up motor
    IN  AL, 0D0H            ; Read current DAC value... 
    ; (In practice, track DAC value in a register, not readable from DAC)
    MOV AL, BL              ; Simplified: just write setpoint directly
    OUT 0D0H, AL
    JMP CONTROL_LOOP

TOO_FAST:
    ; Actual > setpoint: decrease motor drive
    DEC BL                  ; Reduce target
    MOV AL, BL
    OUT 0D0H, AL
    JMP CONTROL_LOOP

NO_CHANGE:
    JMP CONTROL_LOOP

CODE ENDS
END START
```

This is a primitive proportional controller. Real PID controllers add integral and derivative terms, but the ADC→CPU→DAC loop is identical.

---

## Important Numbers to Remember

|Parameter|DAC 0830 (8-bit, VREF=5V)|
|---|---|
|Resolution|8 bits = 256 levels|
|LSB size|5V / 256 ≈ 19.5 mV|
|Max output|−4.980V (255/256 × VREF)|
|Full scale|Never exactly VREF|
|Conversion time|Immediate (combinational)|

|Parameter|8-bit SAR ADC (VREF=5V)|
|---|---|
|Resolution|8 bits = 256 levels|
|LSB size|5V / 256 ≈ 19.5 mV|
|Conversion time|Exactly 8 clock cycles|
|Max detectable|VREF − 1 LSB|

---

## Module 4 Summary

**DAC (Digital → Analog):**

- 8-bit input code → proportional current → op-amp converts to voltage
- $V_{OUT} = -V_{REF} \times D_{in} / 256$
- Two-stage latching: Stage 1 captures from bus, Stage 2 drives the output
- Simple connection: tie ILE HIGH, WR̄2 and XFER̄ LOW → single `OUT` instruction updates analog output
- Waveform generation by writing a sequence of codes in a loop

**ADC (Analog → Digital):**

- SAR architecture: 8 clock cycles, always deterministic
- Start with SC pulse, poll EOC, read result when EOC=1
- $D_{in} = (V_{in} / V_{REF}) \times 256$ — this gives you the expected code for a known voltage
- Polling wastes CPU; interrupts solve this (Module 5 and 6)

**The connection between modules:** The EOC-based polling loop you just wrote is exactly what interrupts eliminate. Instead of `JZ POLL` burning CPU cycles, in Module 6 you'll connect EOC to the 8259 and let the hardware call your ISR automatically. Keep that in mind as we move forward.

---

# Module 5: 8086 Interrupts

---

## Why Interrupts Exist — The Real Motivation

You've already seen polling in Module 4. The ADC example had this loop:

```masm
WAIT_EOC:
    IN  AL, 51H
    TEST AL, 80H
    JZ  WAIT_EOC
```

While this loop runs, the CPU does **nothing else**. It can't. It's stuck checking one bit. Now imagine you have a keyboard, a disk controller, a serial port, and an ADC all needing attention. You can't poll all four simultaneously. By the time you get back to the keyboard in your polling loop, the user's keypress is gone.

Interrupts invert the relationship. Instead of the CPU asking _"are you ready yet?"_ in a loop, the device taps the CPU on the shoulder and says _"I need you now."_ The CPU handles it immediately, then returns to whatever it was doing — without ever having wasted cycles waiting.

This is the architectural shift that makes real multitasking systems possible.

---

## The Interrupt Mechanism — Big Picture First

When an interrupt occurs, the 8086 does exactly four things in hardware, automatically, before your code runs:

```
1. Finish the current instruction (never mid-instruction)
2. Push FLAGS register onto the stack
3. Push CS onto the stack
4. Push IP onto the stack
   → Then: clear IF and TF flags
   → Look up the ISR address in the IVT
   → Jump to ISR
```

When your ISR finishes, `IRET` reverses this:

```
IRET:
1. Pop IP from stack
2. Pop CS from stack
3. Pop FLAGS from stack  ← this restores IF, TF, everything
   → Continue from exactly where you were interrupted
```

The stack is the mechanism that makes this transparent. The interrupted program never knows it was paused — every register it cares about must be exactly as it left it when the ISR returns.

---

## The Interrupt Vector Table (IVT) — Complete Picture

### Location and Structure

The IVT lives at the very bottom of memory, **always**, from `00000H` to `003FFH`. That's 1024 bytes total.

```
Physical Memory:
┌─────────────────────────────────────┐ ← 00000H
│  Vector 0  (4 bytes): IP_low IP_high CS_low CS_high  │
├─────────────────────────────────────┤ ← 00004H
│  Vector 1  (4 bytes)                                 │
├─────────────────────────────────────┤ ← 00008H
│  Vector 2  (4 bytes)                                 │
├─────────────────────────────────────┤ ← 0000CH
│  ...                                                 │
├─────────────────────────────────────┤ ← 003FCH
│  Vector 255 (4 bytes)                                │
└─────────────────────────────────────┘ ← 003FFH
```

Each 4-byte entry is stored in memory as:

```
Byte 0 (lowest address): IP low byte   ← Offset of ISR (low)
Byte 1:                  IP high byte  ← Offset of ISR (high)
Byte 2:                  CS low byte   ← Segment of ISR (low)
Byte 3:                  CS high byte  ← Segment of ISR (high)
```

**Little-endian, offset first, segment second.** Always.

### Address Formula

$$\text{IVT Entry Address} = \text{Type Number} \times 4$$

|Type|IVT Address|Name|
|---|---|---|
|0|00000H|Divide Error|
|1|00004H|Single Step (Debug)|
|2|00008H|NMI|
|3|0000CH|Breakpoint|
|4|00010H|Overflow (INTO)|
|5|00014H|Bound Range Exceeded|
|6|00018H|Invalid Opcode|
|7|0001CH|Coprocessor Not Available|
|8–1FH|00020H–0007CH|Reserved (Intel)|
|20H–FFH|00080H–003FCH|User/DOS/BIOS defined|

**Types 0–7 are reserved by Intel.** The 8086 generates them itself in response to specific conditions. Types 8–1FH were reserved for future Intel use. Types 20H–FFH (32–255) are yours to use — this is where DOS INT 21H, BIOS INT 10H, and your hardware IRQs live.

### Reading and Writing the IVT in Assembly

To install your own ISR, you write its address (IP and CS) into the correct IVT slot. You use segment `0000H` since the IVT is at physical address 00000H.

```masm
; Install ISR for type 50H (user-defined)
; IVT address = 50H × 4 = 140H

    CLI                         ; Disable interrupts while modifying IVT
                                ; (critical — must not be interrupted mid-update)

    MOV AX, 0000H
    MOV ES, AX                  ; ES points to segment 0 (where IVT lives)

    MOV WORD PTR ES:[0140H], OFFSET MY_ISR   ; Write IP (offset)
    MOV WORD PTR ES:[0142H], SEG    MY_ISR   ; Write CS (segment)

    STI                         ; Re-enable interrupts
```

The safer way is to use DOS INT 21H function 25H (Set Interrupt Vector), which handles the CLI/STI for you and is compatible with protected mode:

```masm
; Install ISR using DOS INT 21H function 25H
    MOV AH, 25H                 ; Function: Set Interrupt Vector
    MOV AL, 50H                 ; Interrupt type number
    MOV DS, SEG MY_ISR          ; DS:DX = address of ISR
    MOV DX, OFFSET MY_ISR
    INT 21H                     ; DOS installs it safely
```

---

## The Four Categories of 8086 Interrupts

```
                    ┌─────────────────────┐
                    │   8086 Interrupts   │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              ↓                ↓                ↓
        Hardware           Software          Internal
        Interrupts         Interrupts        Exceptions
              │                │                │
        ┌─────┴─────┐      INT n           ┌────┴────┐
        ↓           ↓      INT 3           ↓         ↓
       NMI         INTR    INTO         Divide    Single
    (Type 2)   (Type from  (Type 4)     Error     Step
               hardware)               (Type 0)  (Type 1)
```

Let's go through each one properly.

---

## NMI — Non-Maskable Interrupt

### What It Is

NMI is wired to a dedicated pin on the 8086. It is **edge-triggered** (responds to a LOW→HIGH transition, not a level). Once that edge is detected, nothing stops it — not CLI, not any software instruction, nothing. It will always be serviced.

It always generates **Type 2** — fixed, hardwired. The IVT entry at `00008H` is the NMI handler.

### Hardware Requirement — Edge Trigger

Because NMI is edge-triggered, holding the NMI pin HIGH does not cause repeated interrupts. Only the transition matters. This is by design — a power-failure signal that stays asserted shouldn't hammer the CPU with repeated interrupts.

```
NMI pin:  ───────┐
                 └─────────────────────────
                 ↑
           This rising edge → one NMI fires
           (staying HIGH does nothing more)
```

### When It's Used

- **RAM parity error** — a memory bit flipped spontaneously. Data integrity is compromised. You must halt or report this immediately.
- **Power failure detection** — UPS systems detect incoming power loss and assert NMI. The ISR then saves critical state to battery-backed RAM before power dies.
- **Coprocessor error** — the 8087 FPU asserts NMI on certain errors.
- **Watchdog timer** — a hardware timer that asserts NMI if the CPU doesn't periodically reset it (detects software hang).

### NMI ISR Structure

Since you can't disable NMI, your NMI handler must be written defensively. It might arrive at any point — even inside another ISR.

```masm
NMI_HANDLER PROC FAR
    PUSH AX                     ; Save everything — don't trust state
    PUSH BX
    PUSH CX
    PUSH DX
    PUSH DS
    PUSH ES
    PUSH SI
    PUSH DI
    PUSHF

    ; Determine cause (read system status register — hardware dependent)
    IN  AL, 061H                ; PC port 61H: bit 7 = parity error flag
    TEST AL, 80H
    JNZ PARITY_ERROR

    ; Handle other NMI sources here
    JMP NMI_DONE

PARITY_ERROR:
    ; Log error, halt system, alert operator
    ; In a real system: write to error log in battery-backed RAM
    MOV AL, 0FFH
    OUT 80H, AL                 ; POST code to port 80H (debug LED in PCs)
    HLT                         ; Stop. Memory is compromised.

NMI_DONE:
    POPF
    POP DI
    POP SI
    POP ES
    POP DS
    POP DX
    POP CX
    POP BX
    POP AX
    IRET
NMI_HANDLER ENDP
```

**Why HLT after a parity error?** Because you can no longer trust the contents of RAM. Any data you read might be corrupted. Continuing execution on corrupted memory is worse than stopping.

---

## INTR — Maskable Hardware Interrupt

### What It Is

INTR is the general-purpose hardware interrupt pin. Unlike NMI:

- It is **level-triggered** (responds to a sustained HIGH level)
- It **can be masked** using the IF (Interrupt Flag) in the FLAGS register
- The interrupt **type number** is not fixed — it's provided by the interrupting device during the INTA̅ bus cycle
- In real systems, INTR is driven by the **8259 PIC** (Module 6)

### The IF Flag — Controlling INTR

```masm
CLI         ; CLear Interrupt flag → IF=0 → CPU ignores INTR
STI         ; SeT Interrupt flag   → IF=1 → CPU responds to INTR
PUSHF       ; Push FLAGS to stack (can inspect IF this way)
POPF        ; Pop FLAGS from stack (can restore IF this way)
```

**Critical rule:** IF only controls INTR. It has absolutely no effect on NMI. NMI ignores IF completely.

### The INTR Bus Cycle — How the Type Number is Transferred

This is the hardware sequence that happens when INTR fires and IF=1:

```
Clock Cycle 1–2:  CPU finishes current instruction
Clock Cycle 3–4:  CPU drives INTĀ LOW (Interrupt Acknowledge)
                  → The 8259 PIC (or device) sees this
Clock Cycle 5–6:  CPU drives INTĀ LOW again (second pulse)
                  → Device puts the type number on D0–D7
Clock Cycle 7:    CPU reads type number from data bus
                  → CPU uses this to calculate IVT address
                  → CPU pushes FLAGS, CS, IP onto stack
                  → CPU fetches ISR address from IVT
                  → Jumps to ISR
```

```
INTR:  ──────────────────────────────────
INTĀ:  ────┐└──────┐└────────────────────
           ↑        ↑
        1st INTĀ   2nd INTĀ:
        (no data)  device puts type on D0-D7

D0-D7: ────────────────[TYPE NUM]────────
                              ↑
                      CPU reads type here
```

**Why two INTA̅ pulses?** The first is a "prepare" signal — tells the device to get ready. The second is "transmit" — device puts the type number on the bus and CPU reads it.

### INTR Response Rules

|IF State|INTR asserted|Response|
|---|---|---|
|IF = 1 (STI)|Yes|CPU completes current instruction, then services INTR|
|IF = 0 (CLI)|Yes|CPU completely ignores INTR — it's invisible|
|IF = 1 (STI)|No|Nothing happens|

**"Completes current instruction first"** is non-negotiable. The 8086 will never abandon an instruction mid-execution for an interrupt. This guarantees instruction atomicity.

---

## INTO — Overflow Interrupt (Type 4)

### What It Is

INTO is a **software-conditional** instruction, not an external signal. It checks the Overflow Flag (OF) in FLAGS. If OF=1, it triggers Type 4 interrupt. If OF=0, INTO is a no-op — it does nothing and execution continues normally.

```masm
ADD AX, BX      ; Signed addition — might overflow
INTO            ; If OF=1 (signed overflow occurred), fire INT 4
                ; If OF=0, continue normally
```

### When to Use It

Signed arithmetic overflow detection. The 8086 doesn't automatically generate an exception on overflow (unlike division by zero, which automatically generates Type 0). You have to explicitly add `INTO` after signed arithmetic if you want overflow protection.

```masm
; Signed byte addition with overflow protection
MOV AL, 7FH         ; AL = +127 (max positive signed byte)
MOV BL, 01H         ; BL = +1
ADD AL, BL          ; Result = 80H = -128 in signed — OVERFLOW!
INTO                ; OF=1 here → INT 4 fires → overflow handler called

; vs unsigned — CMP after ADD and check carry flag instead
```

### The INTO ISR

```masm
; Install INTO handler
    MOV AX, 0
    MOV ES, AX
    MOV WORD PTR ES:[00010H], OFFSET OVERFLOW_HANDLER
    MOV WORD PTR ES:[00012H], SEG    OVERFLOW_HANDLER

OVERFLOW_HANDLER PROC FAR
    PUSH AX
    PUSH DX

    ; Report overflow error
    MOV AH, 09H             ; DOS: print string
    MOV DX, OFFSET ERR_MSG
    INT 21H

    POP DX
    POP AX
    IRET                    ; Return to instruction AFTER the INTO
OVERFLOW_HANDLER ENDP
```

Note: `IRET` after INTO returns to the instruction **after** the INTO instruction, not after the ADD. Your handler should correct the value in the register or abort — just continuing might produce wrong results.

---

## Type 0 — Divide Error (Automatic Exception)

You don't trigger this manually. The 8086 generates Type 0 automatically when:

- You execute `DIV` or `IDIV` and the **divisor is zero**
- You execute `DIV` and the **quotient doesn't fit** in the destination register (e.g., dividing a large AX by a small BL gives a result too big for AL)

```masm
MOV AX, 1000H       ; AX = 4096
MOV BL, 01H         ; BL = 1
DIV BL              ; Quotient = 4096 in AL? AL can only hold 0-255!
                    ; → Quotient overflow → Type 0 fires automatically
```

Unlike INTO, you don't get to suppress this. It fires regardless of IF flag state. It's an internal trap.

---

## Type 1 — Single Step (Debug Trap)

When **TF (Trap Flag)** = 1 in the FLAGS register, the 8086 generates a Type 1 interrupt **after every single instruction**. This is what debuggers use — set TF=1 and you get an interrupt after each instruction, letting you inspect registers and memory between every step.

The 8086 automatically clears TF when entering any ISR (including the Type 1 ISR itself), so the single-step handler doesn't single-step itself.

```masm
; Enable single-step mode (used by debuggers, rarely by user code)
PUSHF               ; Get current FLAGS
POP AX
OR  AX, 0100H       ; Set bit 8 = TF
PUSH AX
POPF                ; Restore FLAGS with TF=1
                    ; Next instruction will trigger INT 1 after completion
```

---

## INT n — Software Interrupts

### What They Are

Software interrupts are deliberate calls from your program. `INT n` is essentially a "super call" — it looks up the ISR address in the IVT at entry `n×4`, saves FLAGS/CS/IP, and jumps to it.

From the CPU's perspective, `INT n` and a hardware interrupt are nearly identical once triggered. The difference is:

- `INT n` is always immediate — no waiting, no IF check, no INTĀ cycle
- Hardware INTR waits for IF=1 and does the INTĀ bus cycle to get the type number

### The BIOS and DOS Interrupt Architecture

This is how the PC software stack works:

```
Your Program
     │
     │  INT 21H (DOS services: files, console, memory)
     ↓
  DOS (in memory, loaded at boot)
     │
     │  INT 10H (BIOS video), INT 13H (BIOS disk), INT 16H (BIOS keyboard)
     ↓
  BIOS (in ROM)
     │
     │  Direct hardware I/O (IN/OUT to ports)
     ↓
  Hardware
```

You call INT 21H to print a string. DOS calls INT 10H to output characters. BIOS uses `OUT` to drive the video hardware. Each layer is just an ISR.

### Most Important Software Interrupts

|INT|Function (AH)|Description|
|---|---|---|
|INT 21H|AH=01H|Read single character from keyboard (with echo)|
|INT 21H|AH=02H|Write single character in DL to console|
|INT 21H|AH=09H|Print string (DS:DX points to string ending with `$`)|
|INT 21H|AH=0AH|Buffered keyboard input|
|INT 21H|AH=4CH|Terminate program, AL=exit code|
|INT 10H|AH=00H|Set video mode|
|INT 10H|AH=0EH|Teletype output (print char in AL)|
|INT 16H|AH=00H|Wait for keypress, return scan code/ASCII|
|INT 16H|AH=01H|Check if key pressed (no wait)|

### MASM Examples — Software Interrupts in Action

**Example 1: Print a character and a string**

```masm
DATA SEGMENT
    MESSAGE DB 'Hello, 8086!', 0DH, 0AH, '$'
    ; 0DH = Carriage Return, 0AH = Line Feed, $ = string terminator for DOS
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

START:
    MOV AX, DATA
    MOV DS, AX

    ; Print a single character (letter 'A')
    MOV AH, 02H             ; DOS function: output character
    MOV DL, 'A'             ; Character to print
    INT 21H                 ; Call DOS → prints 'A'

    ; Print a complete string
    MOV AH, 09H             ; DOS function: print string
    MOV DX, OFFSET MESSAGE  ; DS:DX → the string
    INT 21H                 ; Prints "Hello, 8086!" + newline

    ; Exit cleanly
    MOV AH, 4CH
    MOV AL, 00H             ; Exit code 0 = success
    INT 21H

CODE ENDS
END START
```

**Example 2: Read a character and echo it back**

```masm
CODE SEGMENT
ASSUME CS:CODE

START:
    ; Wait for keypress
    MOV AH, 01H             ; DOS function: read char with echo
    INT 21H                 ; Waits until key pressed
                            ; AL = ASCII code of key pressed
                            ; Character is automatically echoed to screen

    ; AX now has the ASCII code — use it
    MOV BL, AL              ; Save keypress in BL

    ; Print a newline
    MOV AH, 02H
    MOV DL, 0DH             ; CR
    INT 21H
    MOV DL, 0AH             ; LF
    INT 21H

    ; Print the character again manually
    MOV AH, 02H
    MOV DL, BL              ; The key they pressed
    INT 21H

    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
```

**Example 3: Check BIOS keyboard buffer without blocking (INT 16H AH=01H)**

This is exactly like polling but at the software level. You check if a key is waiting — if not, do other work.

```masm
CODE SEGMENT
ASSUME CS:CODE

START:

MAIN_LOOP:
    ; Check if any key pressed WITHOUT waiting
    MOV AH, 01H             ; BIOS function: check keystroke
    INT 16H                 ; Returns: ZF=1 if no key, ZF=0 if key waiting
    JZ  NO_KEY              ; Zero flag set = no key available

    ; Key is available — read it
    MOV AH, 00H             ; BIOS function: get keystroke (removes from buffer)
    INT 16H                 ; AL = ASCII, AH = scan code

    CMP AL, 'Q'
    JE  QUIT                ; Quit if 'Q' pressed

    ; Process other keys here
    JMP MAIN_LOOP

NO_KEY:
    ; No key — do other work here
    ; (compute, poll hardware, update display, etc.)
    JMP MAIN_LOOP

QUIT:
    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
```

---

## Writing a Complete ISR — The Rules

Every ISR you write must follow these rules without exception. Break any one of them and you corrupt the interrupted program.

### Rule 1: Save Every Register You Touch

The interrupted program expects every register to be unchanged when it resumes. If your ISR touches AX, BX, DX, DS — you must save and restore all of them.

```masm
MY_ISR PROC FAR
    PUSH AX             ; Save everything your ISR uses
    PUSH BX
    PUSH CX
    PUSH DX
    PUSH SI
    PUSH DI
    PUSH DS
    PUSH ES

    ; Your actual ISR code here
    ; Use AX, BX, etc. freely — they're saved

    POP ES              ; Restore in REVERSE order
    POP DS
    POP DI
    POP SI
    POP DX
    POP CX
    POP BX
    POP AX
    IRET                ; NOT RET — must be IRET to restore FLAGS too
MY_ISR ENDP
```

**Why IRET and not RET?**

- `RET` pops IP (and CS for FAR). Stack would be misaligned — FLAGS would be left on the stack and IP/CS would read the wrong values.
- `IRET` pops IP, CS, and FLAGS — exactly what was pushed when the interrupt occurred.

### Rule 2: Set Up DS if You Need Data

Interrupts can occur at any time, so DS might point to anything when your ISR starts. If you need to access your own data segment:

```masm
MY_ISR PROC FAR
    PUSH AX
    PUSH DS

    MOV AX, SEG MY_DATA     ; Point DS to your ISR's data
    MOV DS, AX

    ; Now safely access your variables
    MOV BYTE PTR [MY_FLAG], 1

    POP DS
    POP AX
    IRET
MY_ISR ENDP
```

### Rule 3: Hardware ISRs Must Send EOI to the 8259

For interrupts coming through the 8259 PIC (covered next module), you must signal End Of Interrupt before returning. The 8259 won't allow lower or equal priority interrupts until it receives EOI.

```masm
MY_HW_ISR PROC FAR
    PUSH AX

    ; ... handle the interrupt ...

    MOV AL, 20H             ; Non-Specific EOI command
    OUT 20H, AL             ; Send to 8259 master PIC port
                            ; (If slave 8259 involved: also OUT A0H, AL)
    POP AX
    IRET
MY_HW_ISR ENDP
```

### Rule 4: Re-enable Interrupts If Your ISR Takes Long

When the CPU enters an ISR, IF is automatically cleared (CLI happens automatically). If your ISR takes a long time and you need other hardware interrupts to still work during it (nested interrupts), call STI explicitly early in your ISR:

```masm
MY_ISR PROC FAR
    PUSH AX
    PUSH BX
    STI                 ; Re-enable interrupts — allow higher priority IRQs
                        ; Only do this if your ISR is reentrant and safe!

    ; Long processing here...

    CLI                 ; Optional: disable before EOI to avoid race condition
    MOV AL, 20H
    OUT 20H, AL

    STI                 ; Re-enable before IRET
    POP BX
    POP AX
    IRET
MY_ISR ENDP
```

**Don't do this carelessly.** If your ISR modifies shared data, re-enabling interrupts means another interrupt could fire mid-modification, causing corruption. Only enable nested interrupts if your ISR is designed for it.

---

## Complete ISR Example — Timer Tick Counter

A real-world example. The IBM PC has Counter 0 of the 8254 firing INT 08H (IRQ0) at 18.2 Hz. The default BIOS ISR counts ticks for `time of day`. Let's write our own INT 08H handler that counts ticks and chains to the original BIOS handler.

```masm
DATA SEGMENT
    TICK_COUNT  DW 0            ; Our tick counter
    OLD_ISR_IP  DW 0            ; Saved original INT 08H offset
    OLD_ISR_CS  DW 0            ; Saved original INT 08H segment
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

; ─────────────────────────────────────────
; Our replacement timer ISR
; ─────────────────────────────────────────
NEW_TIMER_ISR PROC FAR
    PUSH AX
    PUSH DS

    MOV AX, SEG TICK_COUNT      ; Set DS to our data segment
    MOV DS, AX

    INC TICK_COUNT               ; Count this tick

    ; Chain to the original BIOS timer ISR
    ; (It handles EOI to 8259 and maintains BIOS time-of-day)
    PUSHF                        ; Simulate an interrupt call to old ISR
    CALL DWORD PTR [OLD_ISR_IP]  ; Far call through saved address

    POP DS
    POP AX
    IRET                         ; BIOS handler sent EOI, so we don't need to
NEW_TIMER_ISR ENDP

; ─────────────────────────────────────────
; Main program: install ISR, wait 5 seconds, restore, exit
; ─────────────────────────────────────────
START:
    MOV AX, DATA
    MOV DS, AX

    CLI                          ; Disable interrupts while modifying IVT

    ; Step 1: Save the original INT 08H handler address
    MOV AX, 0
    MOV ES, AX
    MOV AX, ES:[0008H * 4]       ; Original IP
    MOV OLD_ISR_IP, AX
    MOV AX, ES:[0008H * 4 + 2]   ; Original CS
    MOV OLD_ISR_CS, AX

    ; Step 2: Install our new handler
    MOV WORD PTR ES:[0008H * 4],   OFFSET NEW_TIMER_ISR
    MOV WORD PTR ES:[0008H * 4+2], SEG    NEW_TIMER_ISR

    STI                          ; Re-enable interrupts

    ; Step 3: Wait until TICK_COUNT reaches ~91 (18.2 Hz × 5 seconds = 91)
WAIT_5SEC:
    CMP TICK_COUNT, 91
    JL  WAIT_5SEC

    CLI

    ; Step 4: Restore original ISR
    MOV AX, 0
    MOV ES, AX
    MOV AX, OLD_ISR_IP
    MOV WORD PTR ES:[0008H * 4],   AX
    MOV AX, OLD_ISR_CS
    MOV WORD PTR ES:[0008H * 4+2], AX

    STI

    ; Done
    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
```

**What this demonstrates:**

- Saving the old ISR before replacing it (always do this — don't orphan the BIOS handler)
- Chaining via `PUSHF + CALL` (simulates an interrupt to the original handler)
- Using `TICK_COUNT` as a reliable time source without any hardware programming

---

## The Full Interrupt Priority Hierarchy

When multiple interrupts arrive simultaneously, the 8086 services them in this priority order:

```
HIGHEST PRIORITY
│
├── 1. Internal Exceptions (Type 0: Divide, Type 1: Single Step)
│      Generated during instruction execution — handled before next instruction
│
├── 2. NMI (Type 2)
│      Always serviced, IF is irrelevant
│
├── 3. INTR (via 8259)
│      Only if IF=1; 8259 internally prioritizes between multiple IRQs
│
└── 4. Single Step (Type 1 via TF)
       After each instruction when TF=1
LOWEST PRIORITY (in terms of what gets preempted)
```

**Practical note:** Type 1 (single step) is actually generated after instruction completion, so it's lower than NMI and INTR in the sense that those preempt it. But divide error (Type 0) fires during the DIV instruction itself — before the instruction "completes" — so it sits above NMI effectively.

---

## Interrupt Processing — Complete Step-by-Step

Let's trace exactly what happens in the processor from the moment INTR goes HIGH to when the ISR starts executing. Every step is hardware, automatic, no software involved until the ISR itself:

```
Hardware sequence (approximately 61 clock cycles on 8086):

1.  INTR pin goes HIGH (asserted by device or 8259)
2.  CPU checks IF after completing current instruction
3.  IF=0 → ignore INTR completely, go back to step 1
    IF=1 → proceed

4.  CPU drives INTĀ LOW (first acknowledge pulse)
    → Device knows: CPU is acknowledging, prepare type number

5.  CPU drives INTĀ LOW again (second pulse)
    → Device places TYPE NUMBER on D0–D7
    → CPU reads D0–D7 = let's say it's 0BH (IRQ3)

6.  CPU clears IF (prevents further INTR during ISR entry)
7.  CPU clears TF (prevents single-step during ISR entry)

8.  CPU pushes FLAGS onto stack
    SP = SP - 2
    SS:[SP] = FLAGS

9.  CPU pushes CS onto stack
    SP = SP - 2
    SS:[SP] = CS

10. CPU pushes IP onto stack
    SP = SP - 2
    SS:[SP] = IP  ← points to NEXT instruction that was about to execute

11. CPU reads IVT entry for type 0BH:
    Physical address = 0000:0000 + (0BH × 4) = 0000:002CH
    New IP = word at 0000:002CH
    New CS = word at 0000:002EH

12. CPU loads CS:IP with ISR address
    → Execution jumps to ISR

═══ ISR code runs ═══

13. IRET at end of ISR:
    IP = SS:[SP]; SP = SP + 2    ← pop IP
    CS = SS:[SP]; SP = SP + 2    ← pop CS
    FLAGS = SS:[SP]; SP = SP + 2 ← pop FLAGS (restores IF, TF, everything)

14. CPU resumes interrupted program from the instruction at the IP/CS that was saved
```

---

## Flags After An Interrupt — What Changes

When an interrupt is acknowledged (hardware or software INT n), the CPU automatically:

|Flag|Action|Why|
|---|---|---|
|IF|**Cleared to 0**|Prevent INTR from interrupting the ISR entry sequence itself|
|TF|**Cleared to 0**|Prevent single-step trap inside ISR (debugger would go insane)|
|All others|**Unchanged**|FLAGS was pushed before clearing — restored by IRET|

This means **at the start of every ISR, IF=0 (interrupts disabled).** If you want nested interrupts, you must explicitly call `STI` inside your ISR.

---

## Common Mistakes — What Kills ISRs

```masm
; ❌ MISTAKE 1: Using RET instead of IRET
MY_ISR PROC FAR
    ...
    RET         ; WRONG — leaves FLAGS on stack, CS/IP get garbage values
MY_ISR ENDP

; ❌ MISTAKE 2: Not saving registers
MY_ISR PROC FAR
    MOV AX, 0  ; You've just destroyed AX for the interrupted program
    ...
    IRET

; ❌ MISTAKE 3: Forgetting to send EOI to 8259 for hardware interrupts
MY_ISR PROC FAR
    PUSH AX
    ; handle interrupt...
    POP AX
    IRET        ; 8259 never got EOI — no more IRQs will be serviced!

; ❌ MISTAKE 4: Modifying IVT without CLI
    MOV WORD PTR ES:[VECTOR_OFFSET], OFFSET MY_ISR
    ; An interrupt could fire between this write and the next!
    ; CPU would read half-old, half-new address → crash
    MOV WORD PTR ES:[VECTOR_OFFSET+2], SEG MY_ISR

; ✓ CORRECT — Always CLI before modifying IVT, STI after
    CLI
    MOV WORD PTR ES:[VECTOR_OFFSET],   OFFSET MY_ISR
    MOV WORD PTR ES:[VECTOR_OFFSET+2], SEG    MY_ISR
    STI
```

---

## Module 5 Summary

**The three hardware interrupt sources:**

|Source|Type|Maskable?|Trigger|Who sends type?|
|---|---|---|---|---|
|NMI|2 (fixed)|No|Rising edge on NMI pin|Hardwired to type 2|
|INTR|Varies|Yes (IF flag)|High level on INTR pin|Device during INTĀ cycle|
|INTO|4 (fixed)|No (it's an instruction)|OF=1 when INTO executes|Hardwired to type 4|

**IVT:** Always at `00000H`–`003FFH`. Entry for Type N is at `N×4`. 4 bytes: IP low, IP high, CS low, CS high.

**ISR rules — non-negotiable:**

1. Save every register you use, restore in reverse order
2. End with `IRET`, never `RET`
3. Set up DS before accessing your own data
4. Send EOI to 8259 before IRET for hardware interrupts
5. CLI before modifying IVT, STI after

---

# The 8259 Programmable Interrupt Controller

## The Problem It Solves

At the end of Module 5 you understood exactly how INTR works. The 8086 has **one INTR pin**. One. But a real system has a keyboard, a timer, a serial port, a disk controller, a mouse, a parallel port — all of which need to interrupt the CPU independently and at any time.

How do you connect 8 devices to 1 pin, while also:

- Knowing **which device** interrupted (so you call the right ISR)
- **Prioritising** them (timer more important than mouse)
- **Masking** individual devices without disabling all interrupts
- Handling the case where a **higher priority device interrupts while you're serving a lower one**

You can't do this with wire-ORing the INTR lines together — you'd never know which device fired. You need a dedicated controller chip. That's the 8259.

---

## What the 8259 Does — One Paragraph

The 8259 sits between up to 8 external devices and the 8086's single INTR pin. Devices connect to its IR0–IR7 input lines. When a device asserts its IR line, the 8259 checks priority and masking, then asserts INTR to the CPU. When the CPU responds with INTA̅, the 8259 puts the **correct type number** on the data bus — the one corresponding to whichever IR line fired. The CPU then goes to the IVT, finds the right ISR, and calls it. The 8259 handles everything in between.

---

## Internal Architecture

```
                    ┌─────────────────────────────────────────┐
                    │                 8259A                   │
                    │                                         │
 IR0 ───────────────┤→┐                                       │
 IR1 ───────────────┤→│                                       │
 IR2 ───────────────┤→│  IRR          IMR                     │
 IR3 ───────────────┤→│  (Interrupt   (Interrupt         INT ─┤──→ 8086 INTR
 IR4 ───────────────┤→│  Request      Mask            INTĀ ───┤──← 8086 INTĀ
 IR5 ───────────────┤→│  Register)    Register)            │  │
 IR6 ───────────────┤→│      │             │               │  │
 IR7 ───────────────┤→┘      ↓             ↓               │  │
                    │     Priority      Masking          ISR  │
                    │     Resolver  ────────────→    (In-     │
                    │        │       (blocks        Service   │
                    │        ↓       masked IRs)   Register)  │
                    │   Selects                        │      │
                    │   highest                        ↓      │
                    │   unmasked IR               Control     │
                    │        │                    Logic       │
                    │        ↓                       │        │
                    │   Control Word                 ↓        │
                    │   Logic ─────────────→  D0–D7 ──────────┤──↔ Data Bus
                    │                                         │
 A0 ────────────────┤                                         │
 CS̄ ────────────────┤                                         │
 RD̄ ────────────────┤                                         │
 WR̄ ────────────────┤                                         │
                    │  CAS0 ──────────────────────────────────┤
                    │  CAS1 ──────────────────────────────────┤ (Cascade bus
                    │  CAS2 ──────────────────────────────────┤  for slaves)
                    │  SP/ĒN ─────────────────────────────────┤
                    └─────────────────────────────────────────┘
```

### The Three Internal Registers You Must Know

These three registers are the heart of the 8259. Everything the chip does flows through them.

**IRR — Interrupt Request Register (8 bits)** One bit per IR line. When IR3 goes HIGH, bit 3 of IRR goes HIGH. The IRR is a simple latch — it records which devices are currently requesting service. You can read this register to see who's knocking.

**IMR — Interrupt Mask Register (8 bits)** Your software-controlled gatekeeper. If bit N of IMR is 1, the corresponding IR line is **completely ignored** — it won't set IRR, won't get prioritised, won't reach the CPU. Set bit, block device. Clear bit, allow device. You write OCW1 to control this register directly.

**ISR — In-Service Register (8 bits)** Records which interrupt is **currently being serviced**. When the CPU acknowledges IR3, bit 3 of ISR goes HIGH. It stays HIGH until you send EOI. While bit 3 is set, the 8259 knows IR3's ISR is running — this is how it prevents lower-priority interrupts from jumping in.

```
Example state during INT from IR3:

IRR:  0 0 0 0 1 0 0 0   ← IR3 requested service
      7 6 5 4 3 2 1 0

IMR:  0 0 0 1 0 0 0 0   ← IR4 is masked (ignored)
      7 6 5 4 3 2 1 0

ISR:  0 0 0 0 1 0 0 0   ← IR3 is currently being serviced
      7 6 5 4 3 2 1 0
```

---

## Pin Descriptions

|Pin|Direction|Description|
|---|---|---|
|IR0–IR7|In|Interrupt Request lines from devices. IR0 = highest priority by default|
|INT|Out|Asserted HIGH to tell 8086 an interrupt is pending. Connect to 8086 INTR|
|INTĀ|In|Interrupt Acknowledge. 8086 pulses this LOW twice during interrupt cycle|
|D0–D7|Bidirectional|Data bus. Type number goes out here during INTĀ; command words come in here|
|CS̄|In|Chip Select. Active LOW. Must be LOW for CPU to read/write 8259 registers|
|WR̄|In|Write strobe. CPU writes ICW/OCW command words when LOW|
|RD̄|In|Read strobe. CPU reads IRR/ISR/IMR when LOW|
|A0|In|Address bit. A0=0 selects one register, A0=1 selects another. Works with CS̄ to distinguish ICW1 from ICW2, OCW1 from OCW2, etc.|
|CAS0–CAS2|Bidirectional|Cascade bus. Master drives these to tell slaves which one should put its type number on the data bus|
|SP̄/ĒN|In/Out|Dual function: In cascade mode — input, HIGH=master, LOW=slave. In buffered mode — output, enables data bus buffer|

---

## The Interrupt Sequence — Full Hardware Flow

Let's trace exactly what happens from IR4 going HIGH to the ISR executing. This builds on Module 5's INTĀ cycle.

```
Step 1: Device asserts IR4 HIGH
        ↓
Step 2: 8259 sets bit 4 of IRR
        ↓
Step 3: 8259 checks IMR — is bit 4 of IMR = 0? (not masked?)
        YES → continue
        NO  → ignore, stop here
        ↓
Step 4: 8259 checks ISR — is anything of higher or equal priority already running?
        YES (bit 0,1,2,3 of ISR set) → wait, don't assert INT yet
        NO  → continue
        ↓
Step 5: 8259 asserts INT HIGH → 8086 INTR goes HIGH
        ↓
Step 6: 8086 finishes current instruction, checks IF
        IF=0 → ignores it (8259 keeps INT HIGH until acknowledged)
        IF=1 → proceeds
        ↓
Step 7: 8086 sends first INTĀ pulse LOW
        → 8259 receives it, prepares type number for IR4
        ↓
Step 8: 8086 sends second INTĀ pulse LOW
        → 8259 places TYPE NUMBER on D0–D7
        → 8259 sets bit 4 of ISR (IR4 is now "in service")
        → 8259 clears bit 4 of IRR (request is acknowledged)
        ↓
Step 9: 8086 reads type number from D0–D7
        → Pushes FLAGS, CS, IP
        → Looks up IVT at (type × 4)
        → Jumps to ISR
        ↓
Step 10: ISR runs
        ↓
Step 11: ISR sends EOI command to 8259 before IRET
         → 8259 clears bit 4 of ISR
         → 8259 can now service equal or lower priority interrupts again
        ↓
Step 12: IRET → returns to interrupted program
```

The ISR bit stays set the **entire time** your ISR runs. That's the lock that prevents IR5, IR6, IR7 from interrupting IR4's handler (in fully nested mode). IR0, IR1, IR2, IR3 can still interrupt it because they have higher priority — their bits in ISR would be set above bit 4.

---

## Priority — How It Works by Default

By default, IR0 has the highest priority, IR7 the lowest:

```
IR0  ← Highest priority (always serviced first if multiple pending)
IR1
IR2
IR3
IR4
IR5
IR6
IR7  ← Lowest priority
```

**Example:** IR2 and IR5 both assert simultaneously. IRR = 00100100. The Priority Resolver picks IR2 (higher priority). IR5 waits. After IR2's ISR sends EOI, IR5 gets serviced.

**While IR5's ISR is running:** IR6 and IR7 cannot interrupt it (lower priority, ISR bit 5 blocks them). But IR0–IR4 **can** interrupt it — they would preempt IR5's ISR, run their own, send EOI, and return control to IR5's ISR which then continues and sends its own EOI.

This is **automatic nested interrupt handling** — no software required. The ISR bits in the 8259 enforce it purely in hardware.

---

## Operating Modes

### 1. Fully Nested Mode (Default)

This is what you just read above. IR0 = highest, IR7 = lowest. ISR bits prevent lower/equal priority preemption. This is the mode you'll use 90% of the time and the mode the IBM PC runs in.

### 2. Special Fully Nested Mode

Used **only on the master** when cascading multiple 8259s. In normal fully nested mode, while the master's IR2 is being serviced (which is a slave), another interrupt from the same slave (same IR2 line of master) would be blocked because ISR bit 2 is set. Special fully nested mode allows the master to recognise a higher-priority interrupt **from the same slave** while its ISR bit is set. You enable this via ICW4.

When we have more than 8 hardware devices needing interrupts, a single 8259 chip isn't enough. To solve this, we use a setup called **Cascading** ⛓️, which uses the **Master/Slave** architecture.

This allows us to expand from 8 interrupt lines to up to 64!

#### The Master and the Slave roles

Think of this like a corporate hierarchy:

- **The Master**: This is the "Lead" 8259. Its `INT` pin is connected directly to the CPU’s `INTR` pin. It is the only chip that communicates directly with the processor.
    
- **The Slave**: This is a "Subordinate" 8259. Instead of talking to the CPU, its `INT` pin is connected to one of the **IRQ** pins on the Master chip.
    

#### How they communicate

When a Slave receives an interrupt from a device, it signals the Master. The Master then signals the CPU. To coordinate this, they use three special pins called **CAS0, CAS1, and CAS2** (Cascade lines).

1. The Master uses the **CAS lines** to "call out" which Slave is allowed to send its interrupt vector to the CPU.
    
2. If the Master sees an interrupt on its own IRQ2, and it knows a Slave is attached there, it puts the code for "Slave 2" on the CAS bus.
    
3. The Slave sees its ID on the CAS bus and knows, "Okay, it's my turn to talk to the CPU!"

### 3. Rotating Priority Mode

The priority list rotates after each EOI. Whoever just got serviced drops to lowest priority. This gives all 8 devices equal long-term access — no device can starve because it's always IR7.

```
Before: IR0(highest)→IR1→IR2→IR3→IR4→IR5→IR6→IR7(lowest)
IR3 gets serviced → after EOI:
After:  IR4(highest)→IR5→IR6→IR7→IR0→IR1→IR2→IR3(lowest)
```

You command this via OCW2. Useful in systems where all devices have equal importance — disk drives, network buffers, multi-channel data acquisition.

### 4. Special Mask Mode (SMM)

Normally, while IR3's ISR is running, IR4–IR7 are blocked. SMM lets you selectively allow lower-priority interrupts **while a higher-priority ISR is running**, without disabling the higher-priority ISR's lock entirely. You enable via OCW3, then use OCW1 to set which levels you want to allow through. Niche use — mainly in complex real-time systems.

### 5. Poll Mode

You don't use INTR at all. Instead, your software periodically writes a Poll Command (via OCW3) and reads the 8259's response — the response tells you which IR line has the highest priority pending request. You then call the appropriate ISR manually. Useful when IF must stay cleared for long periods but you still need to service devices. Essentially software-driven interrupt dispatch.

---

## Programming the 8259 — The Two Phases

Programming the 8259 works in two completely separate phases:

```
PHASE 1: INITIALIZATION (done once at startup)
         Send ICW1 → ICW2 → (ICW3 if cascade) → (ICW4 if needed)
         Must be in this exact order. No exceptions.

PHASE 2: OPERATION (done during normal running)
         Send OCW1, OCW2, OCW3 in any order, any time
```

The 8259 has an internal state machine that advances through ICW1→2→3→4 automatically. You don't tell it "this is ICW2" — it knows because it received ICW1 first and is expecting ICW2 next. **This is why sequence matters absolutely.** Send them out of order and the 8259 misinterprets every byte.

---

## Initialization Command Words — ICW1 through ICW4 

### ICW1 — Sent to address A0=0 (e.g., port 20H for master)

ICW1 is always the first word. The 8259 recognises it as ICW1 because bit 4 is **always 1** in ICW1 — this is the flag that tells the chip "start of initialization sequence."

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  0  │  0  │  1  │LTIM │ ADI │SNGL │ IC4 │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
                    ↑
              Always 1 — identifies this as ICW1
```

|Bit|Name|Meaning|
|---|---|---|
|D4|—|**Always 1.** This is what identifies ICW1|
|D3|LTIM|**0** = Edge triggered IRs (standard for 8086 systems) **1** = Level triggered|
|D2|ADI|Call address interval. **0**=8 (use this), 1=4. Only relevant for 8080/8085 mode|
|D1|SNGL|**0** = Cascade mode (multiple 8259s), **1** = Single 8259, no cascade. If 1, ICW3 is skipped|
|D0|IC4|**1** = ICW4 will be sent, **0** = ICW4 not needed. **Always set 1 for 8086**|

**Typical ICW1 for a single 8259 in an 8086 system:**

- Edge triggered (LTIM=0)
- Single chip (SNGL=1) → ICW3 will be skipped
- ICW4 needed (IC4=1)

```
D7=0, D6=0, D5=0, D4=1, D3=0, D2=0, D1=1, D0=1
= 0001 0011 = 13H
```

**Typical ICW1 for master in a cascaded 8086 system:**

- Edge triggered, cascade mode (SNGL=0), ICW4 needed (IC4=1)

```
D7=0, D6=0, D5=0, D4=1, D3=0, D2=0, D1=0, D0=1
= 0001 0001 = 11H
```

---

### ICW2 — Sent to address A0=1 (e.g., port 21H for master)

ICW2 sets the **base type number** for IR0. The 8259 automatically assigns:

- IR0 → Type (ICW2)
- IR1 → Type (ICW2 + 1)
- IR2 → Type (ICW2 + 2)
- ...
- IR7 → Type (ICW2 + 7)

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│ T7  │ T6  │ T5  │ T4  │ T3  │  0  │  0  │  0  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
│←──────── Base type (upper 5 bits) ────────────→│ │← don't care →│
```

D2–D0 are don't-care for the 8086 (they matter for 8085 mode). The 8259 fills them in automatically based on which IR line fired.

**Key constraint:** The base type must be a **multiple of 8** (so D2–D0 are all 0 in the value you write, and the 8259 can fill them). In practice, for 8086 PCs:

- Master 8259: ICW2 = **08H** → IR0=Type 08H, IR1=Type 09H, ..., IR7=Type 0FH
- Slave 8259: ICW2 = **70H** → IR0=Type 70H, ..., IR7=Type 77H

**Why start at 08H and not 00H?** Types 00H–07H are reserved by Intel for internal exceptions (divide error, NMI, breakpoint, etc.). You can't use them for hardware IRQs.

```
ICW2 = 08H:

IR0 → Type 08H → IVT at 0020H → IRQ0 (Timer)
IR1 → Type 09H → IVT at 0024H → IRQ1 (Keyboard)
IR2 → Type 0AH → IVT at 0028H → IRQ2 (Cascade/EGA)
IR3 → Type 0BH → IVT at 002CH → IRQ3 (COM2)
IR4 → Type 0CH → IVT at 0030H → IRQ4 (COM1)
IR5 → Type 0DH → IVT at 0034H → IRQ5 (LPT2/Sound)
IR6 → Type 0EH → IVT at 0038H → IRQ6 (Floppy)
IR7 → Type 0FH → IVT at 003CH → IRQ7 (LPT1)
```

---

### ICW3 — Sent to A0=1, only if SNGL=0 in ICW1

ICW3 has **different meanings** for master and slave. This confuses people. Keep them separate in your head.

**ICW3 for MASTER — which IR lines have slaves attached:**

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│ S7  │ S6  │ S5  │ S4  │ S3  │ S2  │ S1  │ S0  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

Each bit: 1 = a slave 8259 is connected to this IR line
          0 = a regular device is connected to this IR line
```

Example: Slave connected to IR2 of master → ICW3 master = **00000100 = 04H**

**ICW3 for SLAVE — which IR line of the master it's connected to:**

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  0  │  0  │  0  │  0  │ ID2 │ ID1 │ ID0 │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

ID2–ID0: Binary number of the master IR line this slave is on
```

Example: Slave is on IR2 of master → ICW3 slave = **00000010 = 02H**

**Why does the slave need to know its own IR line number?** During the INTĀ cycle, the master drives CAS0–CAS2 with the binary-encoded IR number of whichever line is being acknowledged. Every slave listens on CAS0–CAS2 — only the slave whose ID matches CAS0–CAS2 is allowed to put its type number on the data bus. ICW3 gives the slave its ID so it can compare.

---

### ICW4 — Sent to A0=1, only if IC4=1 in ICW1

ICW4 is essential for 8086 mode. Without it, the 8259 defaults to 8080/8085 behaviour which is wrong for the 8086.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  0  │  0  │SFNM │ BUF │M/S̄  │AEOI │ µPM │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```

|Bit|Name|Meaning|
|---|---|---|
|D4|SFNM|Special Fully Nested Mode. **1**=enable, **0**=normal. Use 1 on master when cascading|
|D3|BUF|Buffered mode. **1**=SP̄/ĒN pin acts as buffer enable output. **0**=non-buffered|
|D2|M/S̄|Master/Slave in buffered mode. **1**=master, **0**=slave. Ignored if BUF=0|
|D1|AEOI|Automatic EOI. **1**=8259 automatically clears ISR bit after second INTĀ pulse. **0**=you must send EOI manually|
|D0|µPM|**1**=8086/8088 mode. **0**=8080/8085 mode. **Always set 1 for 8086**|

**AEOI — should you use it?**

With AEOI=1, the 8259 clears the ISR bit automatically when the CPU sends the second INTĀ pulse — before your ISR even runs. This means a lower priority interrupt can fire again immediately, even while your ISR is running. In a simple system this is fine. In any system with nested interrupt concerns, leave AEOI=0 and send EOI manually. The IBM PC uses AEOI=0.

**Typical ICW4 for single 8086 system:**

- No SFNM (single chip), no buffering, manual EOI, 8086 mode

```
D7=0,D6=0,D5=0,D4=0,D3=0,D2=0,D1=0,D0=1
= 0000 0001 = 01H
```

**ICW4 for master in cascaded 8086 system with buffering:**

- SFNM=1, BUF=1, M/S̄=1 (master), AEOI=0, µPM=1

```
D4=1,D3=1,D2=1,D1=0,D0=1
= 0001 1101 = 1DH
```

---

## The ICW Initialization Flowchart

```
                    ┌─────────────────┐
                    │ Send ICW1       │  → to A0=0 (port 20H/A0H)
                    │ (always first)  │
                    └────────┬────────┘
                             ↓
                    ┌─────────────────┐
                    │ Send ICW2       │  → to A0=1 (port 21H/A1H)
                    │ (always)        │
                    └────────┬────────┘
                             ↓
                    ┌─────────────────────────┐
                    │ SNGL bit in ICW1 = 1?   │
                    │ (single chip, no slaves) │
                    └───────┬─────────┬───────┘
                          YES         NO
                            │         │
                            │         ↓
                            │  ┌─────────────────┐
                            │  │ Send ICW3       │  → to A0=1
                            │  │ (cascade only)  │
                            │  └────────┬────────┘
                            │           │
                            └─────┬─────┘
                                  ↓
                    ┌─────────────────────────┐
                    │ IC4 bit in ICW1 = 1?    │
                    └───────┬─────────┬───────┘
                          YES         NO
                            │         │
                            ↓         │
                    ┌─────────────────┐│
                    │ Send ICW4       ││  → to A0=1
                    │ (8086 mode)     ││
                    └────────┬────────┘│
                             │         │
                             └────┬────┘
                                  ↓
                    ┌─────────────────────────┐
                    │  8259 is initialized.   │
                    │  Ready for operation.   │
                    │  Send OCWs any time.    │
                    └─────────────────────────┘
```

---

## Operational Command Words — OCW1, OCW2, OCW3

These are sent **after** initialization, during normal operation, any time, in any order.

---

### OCW1 — Interrupt Mask Register Control

Sent to **A0=1** (same address as ICW2/3/4, but 8259 knows initialization is over).

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│ M7  │ M6  │ M5  │ M4  │ M3  │ M2  │ M1  │ M0  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

M0–M7: 1 = mask (ignore) the corresponding IR line
        0 = allow the corresponding IR line through
```

This directly writes the IMR. Simple.

**Example:** Allow only IR0 (timer) and IR1 (keyboard), mask everything else:

```
M7=1,M6=1,M5=1,M4=1,M3=1,M2=1,M1=0,M0=0
= 1111 1100 = FCH
```

```masm
MOV AL, 0FCH
OUT 21H, AL         ; Write OCW1 — only IR0 and IR1 get through
```

**Reading the IMR** — you can also read back the current mask by reading from A0=1:

```masm
IN  AL, 21H         ; Read current IMR state
```

---

### OCW2 — EOI and Priority Rotation Commands

Sent to **A0=0** (same address as ICW1, but 8259 can tell them apart because bit 4 of OCW2 is always 0, while bit 4 of ICW1 is always 1).

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  R  │ SL  │ EOI │  0  │  0  │ L2  │ L1  │ L0  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
  ↑     ↑     ↑              │←── IR level (for specific EOI/rotate)
  │     │     └── End of Interrupt
  │     └── Specific Level (use L2-L0 to identify which IR)
  └── Rotate priority
```

The meaningful combinations of R, SL, EOI:

|R|SL|EOI|Command|Description|
|---|---|---|---|---|
|0|0|1|**Non-Specific EOI**|Clear the highest priority ISR bit. Most common.|
|0|1|1|**Specific EOI**|Clear the ISR bit for the IR level in L2–L0|
|1|0|1|Rotate on Non-Specific EOI|EOI + rotate priority|
|1|1|1|Rotate on Specific EOI|Specific EOI + rotate priority|
|1|0|0|Rotate in AEOI mode (set)|Enable automatic rotate|
|0|0|0|Rotate in AEOI mode (clear)|Disable automatic rotate|
|1|1|0|Set Priority|Set lowest priority to L2–L0|
|0|1|0|No Operation|—|

**The two EOI commands you'll use in every ISR:**

**Non-Specific EOI (most common):**

```
R=0, SL=0, EOI=1, rest=0
= 0010 0000 = 20H
```

```masm
MOV AL, 20H
OUT 20H, AL         ; Non-Specific EOI to master 8259
```

The 8259 automatically knows which ISR bit to clear — the highest one currently set. Since the priority resolver only services the highest priority request, the highest ISR bit is always the one currently running. This works correctly in virtually all cases.

**Specific EOI (when you know exactly which IR fired):**

Useful in Special Fully Nested Mode where multiple ISR bits might be set from the same slave. Clear exactly the one you handled:

```
R=0, SL=1, EOI=1, L2-L0=IR level
To clear IR3: L2=0,L1=1,L0=1
= 0110 0011 = 63H
```

```masm
MOV AL, 63H
OUT 20H, AL         ; Specific EOI for IR3
```

---

### OCW3 — Read Status and Special Modes

Sent to **A0=0**. Distinguished from OCW2 because bit 3 is always 1 in OCW3, and OCW2 always has bit 3 = 0.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │ESMM │ SMM │  0  │  1  │  P  │ RR  │ RIS │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
                              ↑
                     Always 1 — identifies OCW3
```

|Bit|Name|Meaning|
|---|---|---|
|D6|ESMM|Enable Special Mask Mode. 1=use SMM bit, 0=no change|
|D5|SMM|Special Mask Mode. 1=enable SMM, 0=disable|
|D2|P|Poll command. 1=next read is a poll response|
|D1|RR|Register Read. 1=set up to read IRR or ISR on next IN|
|D0|RIS|Register Interrupt Select. 0=read IRR, 1=read ISR (only used if RR=1)|

**Reading the IRR (who's requesting):**

```
ESMM=0, SMM=0, P=0, RR=1, RIS=0
= 0000 1010 = 0AH
```

```masm
MOV AL, 0AH
OUT 20H, AL         ; Set up to read IRR
IN  AL, 20H         ; Read IRR — bits show which IR lines are pending
```

**Reading the ISR (who's being serviced):**

```
RR=1, RIS=1 → 0000 1011 = 0BH
```

```masm
MOV AL, 0BH
OUT 20H, AL         ; Set up to read ISR
IN  AL, 20H         ; Read ISR — bits show which IRs are in-service
```

---

## Cascade Mode — Expanding to 64 IRQs

A single 8259 gives you 8 IRQ lines. By cascading, you can have 1 master + up to 8 slaves = **64 IRQ lines total**. The IBM PC AT used 2 (1 master + 1 slave) for 15 usable IRQs.

### Hardware Connection

```
        8086
      INTR ←──────────────────────────── INT (Master 8259)
      INTĀ ────────────────────────────→ INTĀ (Master)
                                         │
                  ┌──────────────────────┤ IR0 ← Timer (IRQ0)
                  │                      │ IR1 ← Keyboard (IRQ1)
                  │ ┌──INT (Slave 8259)──┤ IR2 ← Slave cascade input
                  │ │  INTĀ ←── CAS0─2 ──┤ IR3 ← COM2 (IRQ3)
                  │ │           │         │ IR4 ← COM1 (IRQ4)
                  │ │  IR0 ← IRQ8        │ IR5 ← LPT2 (IRQ5)
                  │ │  IR1 ← IRQ9        │ IR6 ← Floppy (IRQ6)
                  │ │  IR2 ← IRQ10       │ IR7 ← LPT1 (IRQ7)
                  │ │  IR3 ← IRQ11       │
                  │ │  IR4 ← IRQ12       │
                  │ │  IR5 ← IRQ13       │
                  │ │  IR6 ← IRQ14       │
                  │ │  IR7 ← IRQ15       │
                  │ │                    │
                  │ └── CAS0-2 ──────────┘ (master drives cascade bus)
```

The slave's INT output connects to the master's IR2. When a slave device fires, the slave asserts its INT → master IR2 goes HIGH → master tells 8086 → 8086 sends INTĀ → master sends first INTĀ to slave via CAS bus → slave puts **its** type number on D0–D7 (not master's).

### What Happens During Cascade INTĀ Cycle

```
1. Slave IR5 (IRQ13) fires
2. Slave asserts INT → Master IR2 goes HIGH
3. Master asserts 8086 INTR
4. 8086 sends 1st INTĀ to master
5. Master drives CAS0-2 = 010 (binary for IR2, where slave is connected)
6. Slave sees CAS0-2 = 010, matches its ICW3 ID = 010 → it's being addressed
7. 8086 sends 2nd INTĀ
8. Slave puts its type number (base + 5 for IR5) on D0-D7
9. Master stays silent on data bus during this
10. 8086 reads slave's type number → jumps to correct ISR
```

---

## Complete ALP Example 1 — Single 8259 Initialization

System: Single 8259, master at ports 20H/21H, edge triggered, base type = 08H, manual EOI, 8086 mode.

```masm
; ═══════════════════════════════════════
; Initialize single 8259
; ═══════════════════════════════════════

INIT_8259_SINGLE PROC NEAR

    ; ── ICW1 ─────────────────────────────
    ; Edge triggered (LTIM=0)
    ; Single chip (SNGL=1) → skip ICW3
    ; ICW4 needed (IC4=1)
    ; D7=0,D6=0,D5=0,D4=1,D3=0,D2=0,D1=1,D0=1 = 13H
    MOV AL, 13H
    OUT 20H, AL             ; ICW1 → A0=0

    ; ── ICW2 ─────────────────────────────
    ; Base type = 08H
    ; IR0→08H, IR1→09H, ... IR7→0FH
    MOV AL, 08H
    OUT 21H, AL             ; ICW2 → A0=1

    ; ICW3 skipped (SNGL=1 in ICW1)

    ; ── ICW4 ─────────────────────────────
    ; No SFNM, no buffering, manual EOI, 8086 mode
    ; D0=1 = 01H
    MOV AL, 01H
    OUT 21H, AL             ; ICW4 → A0=1

    ; ── OCW1 — set initial mask ──────────
    ; Allow all interrupts (mask = 00H)
    MOV AL, 00H
    OUT 21H, AL             ; OCW1 — unmask all IR lines

    RET
INIT_8259_SINGLE ENDP
```

---

## Complete ALP Example 2 — Cascaded Master and Slave

System: Master at 20H/21H (IR0–IR7, base type 08H), Slave at A0H/A1H connected to master IR2 (IR0–IR7, base type 70H).

```masm
; ═══════════════════════════════════════
; Initialize MASTER 8259
; ═══════════════════════════════════════

INIT_MASTER PROC NEAR

    ; ICW1: cascade (SNGL=0), edge, ICW4 needed
    ; D4=1,D1=0,D0=1 = 0001 0001 = 11H
    MOV AL, 11H
    OUT 20H, AL             ; ICW1 to master (A0=0)

    ; ICW2: base type = 08H
    MOV AL, 08H
    OUT 21H, AL             ; ICW2 to master (A0=1)

    ; ICW3 MASTER: slave on IR2 → bit 2 set
    ; = 0000 0100 = 04H
    MOV AL, 04H
    OUT 21H, AL             ; ICW3 master → A0=1

    ; ICW4: SFNM=1 (master in cascade), no buffer, manual EOI, 8086 mode
    ; D4=1,D3=0,D2=0,D1=0,D0=1 = 0001 0001... wait:
    ; SFNM=D4=1, BUF=D3=0, M/S=D2=0, AEOI=D1=0, µPM=D0=1
    ; = 0001 0001 = 11H
    MOV AL, 11H
    OUT 21H, AL             ; ICW4 master → A0=1

    ; OCW1: mask IR2 of master is NOT needed —
    ; cascade input on IR2 should be unmasked (leave bit 2 = 0)
    MOV AL, 00H             ; Allow all IRs on master
    OUT 21H, AL

    RET
INIT_MASTER ENDP


; ═══════════════════════════════════════
; Initialize SLAVE 8259
; ═══════════════════════════════════════

INIT_SLAVE PROC NEAR

    ; ICW1: cascade (SNGL=0), edge, ICW4 needed = 11H
    MOV AL, 11H
    OUT 0A0H, AL            ; ICW1 to slave (A0=0)

    ; ICW2: base type = 70H
    ; IR0→70H, IR1→71H, ..., IR7→77H
    MOV AL, 70H
    OUT 0A1H, AL            ; ICW2 to slave (A0=1)

    ; ICW3 SLAVE: connected to master IR2 → ID = 010 = 02H
    MOV AL, 02H
    OUT 0A1H, AL            ; ICW3 slave → A0=1

    ; ICW4: no SFNM for slave, no buffer, manual EOI, 8086 mode = 01H
    MOV AL, 01H
    OUT 0A1H, AL            ; ICW4 slave → A0=1

    ; OCW1: unmask all slave IRs
    MOV AL, 00H
    OUT 0A1H, AL

    RET
INIT_SLAVE ENDP
```

---

## Complete ALP Example 3 — Full System With ISR

Single 8259. Install ISR for IR1 (keyboard, type 09H). Initialize 8259, install ISR, enable only the keyboard interrupt, wait for a keypress, print it, restore and exit.

```masm
DATA SEGMENT
    KEY_PRESSED DB 0        ; Stores the ASCII code of key
    GOT_KEY     DB 0        ; Flag: 1 when key is available
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

; ─────────────────────────────────────────
; Keyboard ISR — called on IRQ1 (Type 09H)
; ─────────────────────────────────────────
KB_ISR PROC FAR
    PUSH AX
    PUSH DS

    ; Point DS to our data segment
    MOV AX, SEG KEY_PRESSED
    MOV DS, AX

    ; Read scan code from keyboard controller port
    IN  AL, 60H             ; Port 60H = keyboard data port (IBM PC)

    ; Only process key-down events (bit 7 = 0 means key pressed)
    TEST AL, 80H
    JNZ KB_DONE             ; Bit 7 set = key released, ignore it

    ; Store the scan code (simplified — real code converts to ASCII)
    MOV KEY_PRESSED, AL
    MOV GOT_KEY, 1          ; Signal main program

KB_DONE:
    ; Send EOI to 8259 before returning
    MOV AL, 20H             ; Non-Specific EOI
    OUT 20H, AL

    POP DS
    POP AX
    IRET
KB_ISR ENDP


; ─────────────────────────────────────────
; Main Program
; ─────────────────────────────────────────
START:
    MOV AX, DATA
    MOV DS, AX

    ; ── Step 1: Initialize 8259 ──────────
    CLI                     ; Disable interrupts during setup

    MOV AL, 13H             ; ICW1: single, edge, ICW4 needed
    OUT 20H, AL

    MOV AL, 08H             ; ICW2: base type = 08H
    OUT 21H, AL

    MOV AL, 01H             ; ICW4: 8086 mode, manual EOI
    OUT 21H, AL

    ; ── Step 2: Install KB ISR in IVT ────
    ; IR1 → Type 09H → IVT entry at 09H×4 = 24H
    MOV AX, 0
    MOV ES, AX
    MOV WORD PTR ES:[0024H], OFFSET KB_ISR
    MOV WORD PTR ES:[0026H], SEG    KB_ISR

    ; ── Step 3: OCW1 — mask all except IR1 ───
    ; IMR = 1111 1101 = FDH (only IR1 unmasked)
    MOV AL, 0FDH
    OUT 21H, AL

    STI                     ; Re-enable interrupts

    ; ── Step 4: Wait for keypress ────────
WAIT_KEY:
    CMP GOT_KEY, 0
    JE  WAIT_KEY            ; Spin until ISR sets GOT_KEY=1

    ; ── Step 5: Process the key ──────────
    MOV AL, KEY_PRESSED
    ; (In real system: convert scan code to ASCII using lookup table)
    ; For demonstration, print the raw scan code as hex digit
    MOV AH, 02H
    MOV DL, AL
    INT 21H                 ; Print to console

    ; ── Step 6: Clean up and exit ────────
    CLI
    MOV AL, 0FFH            ; Mask all interrupts before exit
    OUT 21H, AL
    STI

    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
```

---

## EOI — When and How in Cascade Systems

In a cascaded system, if the interrupt came from a **slave**, you must send **two EOIs**:

```masm
; ISR for slave IR5 (IRQ13, Type 75H)
SLAVE_ISR PROC FAR
    PUSH AX

    ; ... handle the device ...

    ; EOI to SLAVE first (clears slave's ISR bit)
    MOV AL, 20H
    OUT 0A0H, AL            ; Non-Specific EOI → slave 8259

    ; EOI to MASTER second (clears master's ISR bit for IR2)
    MOV AL, 20H
    OUT 20H, AL             ; Non-Specific EOI → master 8259

    POP AX
    IRET
SLAVE_ISR ENDP
```

**Order matters:** Slave EOI first, then master EOI. If you send master EOI first, the master thinks IR2 is done — it allows another IR2 interrupt (another slave interrupt) before the slave has cleared its own ISR bit. This can cause a spurious interrupt.

---

## Spurious Interrupts — IRQ7

If an INTR signal disappears (glitch, noise) between when the 8259 asserts INT and when the 8086 sends INTĀ, the 8259 has no valid IR to acknowledge. Rather than crashing, the 8259 responds with **IR7** — the lowest priority, effectively a "null" interrupt. This is called a **spurious interrupt**.

Your IR7 ISR should always check whether it's real or spurious by reading the ISR register:

```masm
IRQ7_ISR PROC FAR
    PUSH AX

    ; Read ISR to check if IR7 is genuinely in-service
    MOV AL, 0BH             ; OCW3: read ISR
    OUT 20H, AL
    IN  AL, 20H             ; Read ISR

    TEST AL, 80H            ; Check bit 7 (IR7)
    JZ  SPURIOUS            ; Bit 7 not set → spurious interrupt

    ; Real IR7 (LPT1 or whatever is on IR7) — handle it
    ; ... device handling code ...

    ; Send EOI only for real interrupts
    MOV AL, 20H
    OUT 20H, AL

SPURIOUS:
    ; Spurious — do NOT send EOI (no ISR bit was set, EOI would clear wrong bit)
    POP AX
    IRET
IRQ7_ISR ENDP
```

**Never send EOI for a spurious interrupt.** Since the ISR bit was never set, sending EOI would clear whichever ISR bit happens to be highest — corrupting the 8259's state.

---

## Module 6 Summary

**The four ICWs — sequence and content:**

|Word|Port|When|Key bits|
|---|---|---|---|
|ICW1|A0=0|Always first|Bit 4 always 1, SNGL, IC4, LTIM|
|ICW2|A0=1|Always second|Base type number (multiple of 8)|
|ICW3|A0=1|Only if cascade|Master: which IRs have slaves. Slave: which master IR it's on|
|ICW4|A0=1|Only if IC4=1|µPM=1 for 8086, AEOI, SFNM, BUF|

**The three OCWs — used during operation:**

|Word|Port|Purpose|
|---|---|---|
|OCW1|A0=1|Write/read IMR — mask/unmask individual IRs|
|OCW2|A0=0|Send EOI (20H = non-specific, most common)|
|OCW3|A0=0|Read IRR/ISR, enable poll mode, SMM|

**Non-negotiable rules:**

1. ICWs must be sent in exact sequence — no skipping, no reordering
2. Always send EOI before IRET in every hardware ISR
3. In cascade: slave EOI first, then master EOI
4. Check ISR register in IR7 handler to detect spurious interrupts
5. CLI before modifying IVT, STI after

---

# The 8237 DMA Controller

## The Problem — And Why It's Worse Than You Think

Every data transfer you've done so far follows the same pattern:

```
CPU reads from source  →  CPU writes to destination
```

Whether it's copying memory, sending data to a printer, reading from disk — the CPU is in the middle of every single byte transfer. Let's count the instructions for copying one byte from memory to a port:

```masm
MOV AL, [SI]        ; CPU reads from memory  (4+ clock cycles)
OUT PORT, AL        ; CPU writes to device   (4+ clock cycles)
INC SI              ; Update pointer         (2+ clock cycles)
DEC CX              ; Update counter         (2+ clock cycles)
JNZ LOOP            ; Branch                 (4+ clock cycles)
```

That's **16+ clock cycles per byte**, and the CPU is doing nothing else the entire time. For a 10 KB block transfer at 5 MHz, the CPU is locked out for over **32 milliseconds**. For a floppy disk streaming data continuously, the CPU must keep up with every single byte or lose data permanently — there's no buffer.

DMA solves this by removing the CPU from the transfer completely. The 8237 takes over the address and data buses directly, reads from source, writes to destination, updates its own address counters — all in **2 clock cycles per byte** instead of 16+. The CPU is free to do actual work. This isn't an optimisation — for high-speed devices like disk drives, it's the only way that works at all.

---

## How DMA Works — The Core Concept

Normal bus ownership:

```
┌─────┐  Address Bus  ┌──────────┐
│ CPU │ ────────────→ │  Memory  │
│     │  Data Bus     │  or I/O  │
│     │ ↔──────────── │          │
└─────┘               └──────────┘
CPU owns the bus. Everything goes through it.
```

During DMA:

```
┌─────┐   HRQ    ┌──────┐  Address Bus  ┌──────────┐
│ CPU │ ←─────── │ 8237 │ ────────────→ │  Memory  │
│     │   HLDA   │ DMA  │  Data Bus     │  or I/O  │
│     │ ────────→│      │ ↔──────────── │          │
│ CPU │ suspended└──────┘               └──────────┘
│ floats its                 8237 owns the bus.
│ bus pins                   CPU is out of the picture.
└─────┘
```

The handshake:

1. 8237 asserts **HRQ** (Hold Request) → asks CPU to release the bus
2. CPU finishes its current bus cycle, floats all bus pins (tri-state), asserts **HLDA** (Hold Acknowledge)
3. 8237 sees HLDA → takes control of address and data buses
4. 8237 performs the transfer directly: memory→memory, memory→I/O, I/O→memory
5. When done, 8237 drops HRQ → CPU sees HRQ low → drops HLDA → resumes

The CPU is not halted. Its internal logic keeps running. It just can't use the bus until HLDA drops. In practice, the CPU runs internal operations (multiply, divide, register ops) during the DMA cycles — it's not completely idle.

---

## 8237 Internal Architecture — The Four Channels

The 8237 has **four independent DMA channels**, numbered 0–3. Each channel is completely self-contained with its own:

```
Channel N contains:
├── Current Address Register  (16-bit) — where to read/write next
├── Base Address Register     (16-bit) — the original start address (for auto-init)
├── Current Count Register    (16-bit) — how many bytes remain - 1
├── Base Count Register       (16-bit) — the original count (for auto-init)
└── Mode Register             (8-bit)  — this channel's operating mode
```

Plus chip-wide registers shared by all channels:

```
Chip-wide:
├── Command Register  — global chip settings
├── Status Register   — TC flags and DREQ status
├── Request Register  — software-triggered DMA requests
├── Mask Register     — enable/disable individual channels
└── Temp Register     — used internally during memory-to-memory
```

---

## Pin-Out — Every Pin Explained

This is large but you need to know it. Every pin has a specific role in the DMA cycle.

```
                    ┌──────────────────────────────────┐
        CLK ────────┤                                  │
      RESET ────────┤                                  ├──── HRQ    → 8086 HOLD
       CS̄  ─────────┤                                  ├──── HLDA  ← 8086 HLDA
       RD̄  ─────────┤                                  │
       WR̄  ─────────┤       8237 DMA                   ├──── DREQ0 ← Device 0
      READY ────────┤       Controller                 ├──── DREQ1 ← Device 1
                    │                                  ├──── DREQ2 ← Device 2
  A0–A3  ↔──────────┤  (register select                ├──── DREQ3 ← Device 3
                    │   when CS̄ low)                   │
                    │                                  ├──── DACK0̄ → Device 0
  DB0–DB7 ↔─────────┤  (data bus,                      ├──── DACK1̄ → Device 1
                    │   register R/W)                  ├──── DACK2̄ → Device 2
                    │                                  ├──── DACK3̄ → Device 3
  A0–A3 ──────────→ ┤  (address output                 │
  A4–A7 ──────────→ ┤   during DMA —                   ├──── EOP̄  ↔ End of Process
                    │   low 8 address                  │
                    │   bits)                          ├──── AEN  → Address Enable
                    │                                  ├──── ADSTB → Address Strobe
  DB0–DB7 ────────→ ┤  (high address                   │
                    │   byte output                    │
                    │   during DMA)                    │
                    └──────────────────────────────────┘
```

The most important thing to notice: **A0–A3 and DB0–DB7 are dual-purpose.**

- When CS̄ is LOW (CPU programming the 8237): A0–A3 select internal registers, DB0–DB7 carry command/data
- During DMA cycles: A0–A7 output the **low 8 bits of the DMA address**, DB0–DB7 output the **high 8 bits of the DMA address** (latched externally by ADSTB), and the data being transferred flows through the data bus separately

This dual use of the data bus for high address bits is how the 8237 generates a 16-bit address using only 8 address output pins. It needs an external latch to capture the high byte.

### Signal by Signal

**HRQ / HLDA — Bus Acquisition**

```
8237: HRQ ──────────────────────────────────→ 8086 HOLD pin
8086: HLDA ─────────────────────────────────→ 8237 HLDA pin

8237 raises HRQ when a channel needs to transfer.
8086 raises HLDA when it has released the bus.
8237 starts DMA only after seeing HLDA high.
```

**DREQ0–DREQ3 — DMA Request from Devices**

Each device connected to the 8237 has one DREQ line. When a device needs a DMA transfer (e.g., disk controller has a byte ready), it asserts its DREQ line HIGH (or LOW — polarity is programmable via Command Register). The 8237 sees this, arbitrates priority, and initiates the bus request sequence.

**DACK̄0–DACK̄3 — DMA Acknowledge to Device**

The 8237 asserts DACK̄ LOW to tell the selected device "you have the bus, proceed with your transfer." The device uses this signal to enable its data buffers onto the data bus. Only one DACK̄ is active at a time.

```
Device asserts DREQ2 ──────────────────────→ 8237
8237 gets bus, then: 8237 asserts DACK̄2 ──→ Device
Device: "Great, I'll put my data on the bus now"
```

**AEN — Address Enable**

During DMA, the 8237 asserts AEN HIGH. This signal goes to the system's address decoders and bus buffers to tell them: _ignore the CPU's address — the DMA controller is driving the address bus now._ Without AEN, both the CPU and 8237 would fight over the address bus simultaneously.

**ADSTB — Address Strobe**

The 8237 only has A0–A7 as address output pins. To generate a full 16-bit address, it puts the high byte (A8–A15) on DB0–DB7 and pulses ADSTB HIGH. An external 8-bit latch (e.g., 74LS373) captures this high byte and holds it steady on the address bus for the entire DMA cycle. Then the 8237 drives A0–A7 directly for the low byte.

```
Timing:
DB0–DB7: [  A15–A8 high byte  ][  data flows here  ]
ADSTB:   ──────┐└───────────────────────────────────
               ↑
         latch captures A15–A8 here, holds it all cycle

A0–A7:   ────────────────[  A7–A0 low byte  ]──────
```

**EOP̄ — End of Process**

Bidirectional, active LOW, open-drain:

- **Output:** When a channel's count reaches terminal count (TC), the 8237 asserts EOP̄ LOW automatically. This signals external hardware that the transfer is complete. You can connect EOP̄ to INTR (via 8259) to interrupt the CPU when DMA finishes.
- **Input:** You can force-terminate any DMA transfer by pulling EOP̄ LOW externally. The 8237 will stop the transfer as if TC had been reached.

---

## Address Selection — A0–A3 for Register Access

When the CPU is programming the 8237 (CS̄ low), A0–A3 select which internal register is being accessed:

|A3|A2|A1|A0|Register|
|---|---|---|---|---|
|0|0|0|0|Channel 0 — Base/Current Address|
|0|0|0|1|Channel 0 — Base/Current Count|
|0|0|1|0|Channel 1 — Base/Current Address|
|0|0|1|1|Channel 1 — Base/Current Count|
|0|1|0|0|Channel 2 — Base/Current Address|
|0|1|0|1|Channel 2 — Base/Current Count|
|0|1|1|0|Channel 3 — Base/Current Address|
|0|1|1|1|Channel 3 — Base/Current Count|
|1|0|0|0|Command Register (write) / Status Register (read)|
|1|0|0|1|Request Register|
|1|0|1|0|Single Mask Register|
|1|0|1|1|Mode Register|
|1|1|0|0|Clear Byte Flip-Flop|
|1|1|0|1|Master Clear (write) / Temp Register (read)|
|1|1|1|0|Clear Mask Register|
|1|1|1|1|All-Channel Mask Register|

If the 8237 is at base address `00H` in the IBM PC:

- Channel 0 Address → port `00H`
- Channel 0 Count → port `01H`
- Channel 1 Address → port `02H`
- Channel 1 Count → port `03H`
- Channel 2 Address → port `04H`
- Channel 2 Count → port `05H`
- Channel 3 Address → port `06H`
- Channel 3 Count → port `07H`
- Command/Status → port `08H`
- Request → port `09H`
- Single Mask → port `0AH`
- Mode → port `0BH`
- Clear Flip-Flop → port `0CH`
- Master Clear → port `0DH`
- Clear All Masks → port `0EH`
- All-Channel Mask → port `0FH`

---

## The Byte Flip-Flop — The Most Critical Detail

The address and count registers are **16-bit**, but the data bus is **8-bit**. You need two writes to load a 16-bit value. The 8237 has an internal 1-bit flip-flop that tracks whether the next write goes to the **low byte** or **high byte** of the selected register.

```
Flip-flop = 0 → next write goes to LOW byte
Flip-flop = 1 → next write goes to HIGH byte
After each write, flip-flop toggles automatically.
```

**This flip-flop must be in the correct state before you start writing.** If you write three bytes by mistake, or a previous operation left the flip-flop in state 1, your addresses will be wrong — low byte goes where high byte should and vice versa.

**Always clear the flip-flop before loading any address or count:**

```masm
OUT 0CH, AL         ; Clear byte flip-flop
                    ; (value of AL doesn't matter — the OUT itself does it)
                    ; Flip-flop → 0 (next write = low byte)
```

After this, your sequence is:

```masm
OUT 0CH, AL         ; Clear flip-flop → state = 0

OUT 00H, AL         ; Write LOW byte of address → flip-flop → 1
OUT 00H, AL         ; Write HIGH byte of address → flip-flop → 0

OUT 01H, AL         ; Write LOW byte of count → flip-flop → 1
OUT 01H, AL         ; Write HIGH byte of count → flip-flop → 0
```

Forget the flip-flop clear and you might write the high byte first, producing an address that's 256× wrong. This is the number one DMA programming bug.

---

## The Command Register — Global Chip Settings

Written to port `08H`. Controls chip-wide behaviour affecting all channels.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│DACK │DREQ │ EXT │ PRI │COMP │  CH0│ ADH │MEME │
│ POL │ POL │WRITE│     │TIME │ADDR │HOLD │     │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```

|Bit|Name|0|1|
|---|---|---|---|
|D7|DACK Polarity|DACK̄ active LOW (normal)|DACK active HIGH|
|D6|DREQ Polarity|DREQ active HIGH (normal)|DREQ active LOW|
|D5|Extended Write|Normal write timing|Extended write (for slow devices)|
|D4|Priority|Fixed (CH0 highest)|Rotating priority|
|D3|Compressed Timing|Normal (4 clocks/transfer)|Compressed (3 clocks/transfer)|
|D2|CH0 Address Hold|CH0 address increments normally|CH0 address held fixed (for memory-to-memory source)|
|D1|Controller Disable|8237 enabled|8237 disabled (all channels masked)|
|D0|Memory-to-Memory|Disabled|Enabled (uses CH0 and CH1)|

**Typical command register for normal operation:**

- DACK active LOW, DREQ active HIGH, normal write, fixed priority, normal timing, no M→M
- All bits 0: **00H**

```masm
MOV AL, 00H
OUT 08H, AL         ; Normal operation, fixed priority, no memory-to-memory
```

**For memory-to-memory transfer:**

- D0=1 (enable M→M), D2=1 (hold CH0 source address fixed if you want to fill from one location)
- = 00000101 = **05H** (with CH0 hold) or **01H** (without CH0 hold)

---

## The Mode Register — Per-Channel Settings

Written to port `0BH`. You write one mode byte per channel — the channel is selected by bits D1–D0 within the byte itself.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│MODE1│MODE0│ IDEC│ INIT│ TYPE│ TYPE│ SEL1│ SEL0│
│     │     │     │AUTO │  1  │  0  │     │     │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```

### D1–D0: Channel Select

|D1|D0|Channel|
|---|---|---|
|0|0|Channel 0|
|0|1|Channel 1|
|1|0|Channel 2|
|1|1|Channel 3|

### D3–D2: Transfer Type

|D3|D2|Transfer Type|
|---|---|---|
|0|0|Verify (no actual transfer — address cycles only, used for testing)|
|0|1|**Write** (I/O → Memory: device writes TO memory)|
|1|0|**Read** (Memory → I/O: read FROM memory, send TO device)|
|1|1|Illegal|

**Naming from the memory's perspective:**

- **Write** = data flows INTO memory (from device/I/O)
- **Read** = data flows OUT OF memory (to device/I/O)

This naming trips people up. Think of it from memory's perspective, not the device's.

### D4: Auto-Initialization

|D4|Meaning|
|---|---|
|0|Single transfer — when TC reached, channel stops and must be reprogrammed|
|1|Auto-initialize — when TC reached, Base Address and Base Count are automatically copied back into Current Address and Current Count, channel restarts immediately|

Auto-init is essential for continuous transfers like audio streaming or serial port buffering — the channel restarts the moment it finishes, with no CPU intervention.

### D5: Address Increment/Decrement

|D5|Address Action|
|---|---|
|0|Increment address after each transfer|
|1|Decrement address after each transfer|

Use decrement for rare cases like filling memory backwards or certain peripheral protocols.

### D7–D6: DMA Mode

|D7|D6|Mode|
|---|---|---|
|0|0|**Demand Mode** — transfer continues as long as DREQ stays asserted. If DREQ drops, 8237 releases bus, waits. When DREQ returns, resumes.|
|0|1|**Single Mode** — transfers exactly one byte per DREQ assertion. Bus released after each byte. Device must re-assert DREQ for each byte.|
|1|0|**Block Mode** — transfers entire block without releasing bus until TC or EOP̄. DREQ only needed to start.|
|1|1|**Cascade Mode** — used to cascade another 8237. HRQ/HLDA of slave connect to DREQ/DACK of master channel.|

**Choosing the mode:**

```
Demand Mode:  Device can't always supply data fast enough.
              → Use for: slow disk controllers, modems
              → 8237 pauses when device isn't ready, resumes automatically

Single Mode:  Maximum fairness. Each byte releases the bus.
              → Use for: sharing bus with time-critical CPU operations
              → Slowest mode — one DREQ per byte

Block Mode:   Maximum throughput. Bus locked until done.
              → Use for: fast disk, memory fill, audio
              → CPU gets NO bus access during entire block transfer
              → Don't use if CPU needs regular bus access
```

### Example Mode Register Values

**Channel 2, single mode, write (I/O→memory), auto-init, increment:**

```
D7D6=01(single), D5=0(inc), D4=1(auto), D3D2=01(write), D1D0=10(CH2)
= 0101 0110 = 56H
```

**Channel 0, block mode, read (memory→I/O), no auto-init, increment:**

```
D7D6=10(block), D5=0(inc), D4=0(no auto), D3D2=10(read), D1D0=00(CH0)
= 1000 1000 = 88H
```

---

## The Mask Register — Enabling and Disabling Channels

Two ways to access the mask register:

### Single Channel Mask (port 0AH)

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  0  │  0  │  0  │  0  │MASK │ SEL1│ SEL0│
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

D2:  1 = mask (disable) this channel
     0 = unmask (enable) this channel
D1-D0: channel select
```

```masm
; Mask (disable) channel 2
MOV AL, 00000110B   ; D2=1 (mask), D1D0=10 (CH2)
OUT 0AH, AL

; Unmask (enable) channel 1
MOV AL, 00000001B   ; D2=0 (unmask), D1D0=01 (CH1)
OUT 0AH, AL
```

**Important:** Always mask a channel before programming it. You don't want the channel to start a DMA cycle while you're halfway through writing its address register.

### All-Channel Mask (port 0FH)

Write all 4 channel masks simultaneously — one bit per channel:

```masm
; Mask all channels (stop everything)
MOV AL, 0FH         ; bits 0-3 = 1111, all masked
OUT 0FH, AL

; Unmask all channels
MOV AL, 00H         ; bits 0-3 = 0000, all unmasked
OUT 0FH, AL

; Unmask only channel 0 and channel 2
MOV AL, 0AH         ; bits = 1010, CH3 and CH1 masked, CH2 and CH0 unmasked
OUT 0FH, AL
```

---

## The Status Register — Reading DMA State

Read from port `08H` (same address as Command Register — reading gives Status, writing gives Command).

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│DREQ3│DREQ2│DREQ1│DREQ0│ TC3 │ TC2 │ TC1 │ TC0 │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
│←──── current DREQ status ────→│←── terminal count flags ──→│
```

|Bits|Meaning|
|---|---|
|D7–D4|Current state of DREQ3–DREQ0 lines (1=active)|
|D3–D0|Terminal Count flags. Bit N = 1 means channel N reached TC since last status read. **Cleared on read.**|

```masm
; Poll until channel 2 completes its transfer
WAIT_TC2:
    IN  AL, 08H             ; Read status register
    TEST AL, 04H            ; Check bit 2 (TC2)
    JZ  WAIT_TC2            ; Zero → not done yet, keep waiting

; TC2=1: Channel 2 has completed its transfer
```

**TC flags clear on read** — once you read them, they reset. If you need to check TC for multiple channels, read the status once and check all the bits you need from that single reading.

---

## The Request Register — Software DMA Requests

Port `09H`. Allows software to initiate a DMA transfer without a hardware DREQ signal. Useful for memory-to-memory transfers (no physical device involved) or for testing.

```
  D7    D6    D5    D4    D3    D2    D1    D0
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  0  │  0  │  0  │  0  │ REQ │ SEL1│ SEL0│
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

D2: 1 = set software request for this channel
    0 = clear software request
D1-D0: channel select
```

```masm
; Set software DMA request for channel 0 (memory-to-memory source)
MOV AL, 00000100B   ; D2=1 (request), D1D0=00 (CH0)
OUT 09H, AL
```

Software requests are only valid in **block mode**. The 8237 will process them the same as hardware DREQ, but since no physical device is involved, block mode is the right choice — demand and single modes need real hardware handshaking.

---

## Software Commands — The Four Instant Actions

Four ports trigger immediate actions when written to, regardless of the value written:

|Port|Command|Effect|
|---|---|---|
|`0CH`|**Clear Byte Flip-Flop**|Resets flip-flop to 0 (next write = low byte). Do this before every address/count load.|
|`0DH` (write)|**Master Clear**|Resets entire 8237 to power-on state. All channels masked, registers cleared. Same as hardware RESET.|
|`0EH`|**Clear All Masks**|Unmasks all four channels simultaneously.|
|`0DH` (read)|**Read Temp Register**|Returns the last byte transferred in a memory-to-memory operation.|

```masm
; Full reset of 8237
OUT 0DH, AL         ; Master Clear (value doesn't matter)

; Clear flip-flop before loading registers
OUT 0CH, AL         ; Clear byte flip-flop

; Enable all channels at once after programming
OUT 0EH, AL         ; Clear all masks = enable all channels
```

---

## Programming Sequence — The Exact Order Every Time

This is the template. Follow it for every channel you set up. Deviate and you'll get wrong addresses or spurious transfers.

```
Step 1:  Mask the channel (disable it while programming)
          OUT 0AH, AL  (single channel mask with D2=1)

Step 2:  Master Clear or Clear Flip-Flop
          OUT 0DH, AL  (master clear, full reset) — first time only
          OUT 0CH, AL  (flip-flop clear) — before each address/count load

Step 3:  Write Base/Current Address (16-bit, LSB first then MSB)
          OUT [addr_port], AL  (low byte)
          OUT [addr_port], AL  (high byte)

Step 4:  Clear flip-flop again before count
          OUT 0CH, AL

Step 5:  Write Base/Current Count (16-bit, LSB first then MSB)
         Note: Count = N-1 (for N bytes, write N-1)
          OUT [count_port], AL  (low byte of N-1)
          OUT [count_port], AL  (high byte of N-1)

Step 6:  Write Mode Register for this channel
          OUT 0BH, AL

Step 7:  Write Command Register (if not already set)
          OUT 08H, AL

Step 8:  Unmask the channel (enable it)
          OUT 0AH, AL  (single channel mask with D2=0)

Step 9:  If software-triggered: write Request Register
          OUT 09H, AL
```

### The Count Value — N−1

This catches everyone. If you want to transfer **N bytes**, you write **N−1** to the count register. The 8237 counts down from N−1 to 0, generating N transfers (including the one at count 0). When it decrements below 0 (reaching FFFFH from 0000H), TC fires.

```
Transfer 1 byte:  write count = 0000H (1-1 = 0)
Transfer 256 bytes: write count = 00FFH (256-1 = 255)
Transfer 1000 bytes: write count = 03E7H (1000-1 = 999)
Transfer 65536 bytes: write count = FFFFH (65536-1 = 65535)
```

---

## The 8237 Connected to the 8086 — Full System Circuit

This is what makes DMA work as a complete system. Understanding the external hardware around the 8237 is essential.

```
                    ┌───────────────────────────────────┐
                    │              8086                  │
                    │  A0–A19  D0–D15  HOLD  HLDA  BHĒ  │
                    └───┬────────┬──────┬─────┬─────────┘
                        │        │      │     │
              ┌─────────┘        │    HOLD  HLDA
              │                  │      │     │
              │                  │      │     │
         ┌────┴────┐             │   ┌──┴─────┴──────────────┐
         │ Address │             │   │        8237            │
         │ Latches │←─ ADSTB ───┼───┤ADSTB                  │
         │74LS373  │             │   │                  HRQ──┤→ HOLD
         │(A8-A15) │             │   │                 HLDA←─┤
         └────┬────┘             │   │                       │
              │                  │   │ A0–A7 ────────────────┤→ A0–A7 (bus)
              ↓ A8–A15           │   │                       │
         Address Bus             │   │ DREQ0 ←───────────────┤ Timer/device
         (A0–A15)                │   │ DREQ1 ←───────────────┤ Keyboard
                                 │   │ DREQ2 ←───────────────┤ Floppy
                                 │   │ DREQ3 ←───────────────┤ HDD
              ┌──────────────────┘   │                       │
              │                      │ DACK̄0 ────────────────┤→ Device 0
         ┌────┴────┐                 │ DACK̄1 ────────────────┤→ Device 1
         │  Data   │                 │ DACK̄2 ────────────────┤→ Floppy ctrl
         │  Bus    │                 │ DACK̄3 ────────────────┤→ HDD ctrl
         │Buffers  │←─ AEN ─────────┤AEN                    │
         │74LS245  │                 │                       │
         └─────────┘                 │  DB0–DB7 ─────────────┤↔ Data Bus
                                     │                       │
                                     │  EOP̄ ─────────────────┤→ 8259 IR
                                     └───────────────────────┘
```

### Key External Components

**Address Latch (74LS373):**

- During DMA, 8237 puts A15–A8 on DB0–DB7 and pulses ADSTB
- 74LS373 latches this byte and drives A15–A8 on the address bus
- 8237 then drives A7–A0 directly on A0–A7
- Together they form the full 16-bit address: latch provides high byte, 8237 provides low byte

**Data Bus Buffers (74LS245):**

- Bidirectional buffer, direction controlled by the transfer type
- AEN from 8237 disables the CPU side of the buffer during DMA
- Prevents CPU (in tri-state) from interfering with DMA data

**AEN → Address Decoder Disable:**

- AEN also connects to the chip-select logic for RAM and I/O
- During DMA, AEN tells decoders to respond to 8237's address, not CPU's address
- Without this, the RAM decoder wouldn't enable for DMA reads/writes

**EOP̄ → 8259 IR:**

- When DMA completes (TC), EOP̄ goes LOW
- Connected to an IR line of the 8259
- CPU gets interrupted automatically when the transfer is done
- ISR can then process the transferred data or start the next transfer

---

## DMA Transfer Timing — What Happens in 4 Clock Cycles

A single DMA transfer takes 4 clock cycles (in normal mode):

```
State S1: 8237 asserts HRQ. Waits for HLDA.
           (This can take many cycles if CPU is busy)

State S2: HLDA received. 8237 puts A15–A8 on data bus, pulses ADSTB.
           Latch captures high address byte.

State S3: 8237 drives A7–A0 on address bus.
           8237 asserts AEN, drives DACK̄ to device.
           Memory and I/O devices enabled by AEN'd decoders.

State S4: Data transfer occurs (read from source, write to destination).
           8237 decrements Current Address, decrements Current Count.
           If count = FFFF (underflow from 0000): TC! Assert EOP̄.

After S4: 
  - If more bytes: either release bus (single/demand) or immediately do next S2–S4 (block)
  - If TC: drop HRQ, CPU gets bus back (HLDA drops)
```

In compressed timing mode (D3 of Command Register = 1), S3 and S4 are merged — 3 cycles total per transfer instead of 4. Only use this for fast memory; slow devices need the full 4-cycle timing.

---

## Memory-to-Memory Transfer

The 8237 supports direct memory-to-memory transfer using **channels 0 and 1 together**:

- **Channel 0** = source address
- **Channel 1** = destination address

The transfer happens in two bus cycles per byte: first CH0 reads the byte from memory into the 8237's internal temp register, then CH1 writes that byte to the destination. Both use normal DMA timing.

Enable with D0=1 in the Command Register.

**Optional CH0 address hold (D2=1 in Command):** If you want to fill a block of memory with a single value, hold CH0's address fixed. It reads the same source byte repeatedly and writes it to every destination address as CH1 increments. This is the **memory fill** operation.

### MASM Example: Copy 1000H bytes from segment A to segment B

Assume 8237 at base `00H`. Source = physical address `20000H`, destination = `30000H`, length = `1000H` bytes.

**The 8237 only handles 16-bit addresses.** For 20-bit 8086 addresses, the upper 4 bits (page) must be set separately via a **Page Register** (a set of external latches, one per channel, typically at ports 83H, 82H, 81H, 80H on IBM PC). The 8237 handles A0–A15, the page register handles A16–A19.

```
Source:  20000H → page = 2, offset = 0000H
Dest:    30000H → page = 3, offset = 0000H
```

```masm
; ═══════════════════════════════════════════════
; Memory-to-Memory: 20000H → 30000H, 1000H bytes
; ═══════════════════════════════════════════════

    ; Step 1: Mask both channels during setup
    MOV AL, 00000100B       ; Mask CH0 (D2=1, D1D0=00)
    OUT 0AH, AL
    MOV AL, 00000101B       ; Mask CH1 (D2=1, D1D0=01)
    OUT 0AH, AL

    ; Step 2: Master Clear — full reset
    OUT 0DH, AL             ; Master Clear (value irrelevant)

    ; Step 3: Set page registers for 20-bit addressing
    MOV AL, 02H             ; Page 2 for CH0 source (A16-A19 = 0010)
    OUT 87H, AL             ; CH0 page register (IBM PC port)
    MOV AL, 03H             ; Page 3 for CH1 destination
    OUT 83H, AL             ; CH1 page register

    ; Step 4: Load CH0 source address (0000H within page 2)
    OUT 0CH, AL             ; Clear flip-flop
    MOV AL, 00H
    OUT 00H, AL             ; CH0 address LSB = 00H
    MOV AL, 00H
    OUT 00H, AL             ; CH0 address MSB = 00H → address = 0000H

    ; Step 5: Load CH0 count (1000H - 1 = 0FFFH)
    OUT 0CH, AL             ; Clear flip-flop
    MOV AL, 0FFH
    OUT 01H, AL             ; CH0 count LSB = FFH
    MOV AL, 0FH
    OUT 01H, AL             ; CH0 count MSB = 0FH → count = 0FFFH

    ; Step 6: Load CH1 destination address (0000H within page 3)
    OUT 0CH, AL             ; Clear flip-flop
    MOV AL, 00H
    OUT 02H, AL             ; CH1 address LSB
    MOV AL, 00H
    OUT 02H, AL             ; CH1 address MSB → address = 0000H

    ; Step 7: Load CH1 count (same as CH0: 0FFFH)
    OUT 0CH, AL             ; Clear flip-flop
    MOV AL, 0FFH
    OUT 03H, AL             ; CH1 count LSB
    MOV AL, 0FH
    OUT 03H, AL             ; CH1 count MSB

    ; Step 8: Mode Register for CH0 — read (source), block, no auto-init, increment
    ; D7D6=10(block), D5=0(inc), D4=0, D3D2=10(read), D1D0=00(CH0)
    ; = 1000 1000 = 88H
    MOV AL, 88H
    OUT 0BH, AL

    ; Step 9: Mode Register for CH1 — write (destination), block, no auto-init, increment
    ; D7D6=10(block), D5=0(inc), D4=0, D3D2=01(write), D1D0=01(CH1)
    ; = 1000 0101 = 85H
    MOV AL, 85H
    OUT 0BH, AL

    ; Step 10: Command Register — enable memory-to-memory (D0=1)
    ; D0=1, all else 0 = 01H
    MOV AL, 01H
    OUT 08H, AL

    ; Step 11: Unmask CH0 and CH1
    MOV AL, 00000000B       ; Unmask CH0 (D2=0, D1D0=00)
    OUT 0AH, AL
    MOV AL, 00000001B       ; Unmask CH1 (D2=0, D1D0=01)
    OUT 0AH, AL

    ; Step 12: Software request to start CH0 (triggers the transfer)
    MOV AL, 00000100B       ; Request CH0 (D2=1, D1D0=00)
    OUT 09H, AL

    ; Step 13: Poll TC1 (channel 1 completion) in status register
WAIT_DONE:
    IN  AL, 08H             ; Read status register
    TEST AL, 02H            ; Check bit 1 (TC1 — channel 1 terminal count)
    JZ  WAIT_DONE           ; Not done yet

    ; Transfer complete — 1000H bytes copied from 20000H to 30000H
```

---

## Complete Application: Memory Fill Using 8237

Fill memory block from `14000H` to `17FFFH` with value `00H`. Block size = 4000H bytes.

Strategy: Store `00H` at source address `13FFFH`. CH0 holds at this address (Command D2=1). CH1 writes `00H` to every address from `14000H` to `17FFFH`.

```masm
DATA SEGMENT
    ZERO_BYTE DB 00H        ; The fill value lives here
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

ZERO    EQU 0               ; Fill value
CH0A    EQU 00H             ; CH0 address port
CH0C    EQU 01H             ; CH0 count port
CH1A    EQU 02H             ; CH1 address port
CH1C    EQU 03H             ; CH1 count port
CMD     EQU 08H             ; Command register
MODE    EQU 0BH             ; Mode register
SMASK   EQU 0AH             ; Single channel mask
MCLR    EQU 0DH             ; Master clear
FFLOP   EQU 0CH             ; Flip-flop clear
REQ     EQU 09H             ; Request register
CH0PAGE EQU 87H             ; CH0 page register
CH1PAGE EQU 83H             ; CH1 page register

CLEAR PROC NEAR USES AX

    ; Mask both channels
    MOV AL, 04H             ; Mask CH0
    OUT SMASK, AL
    MOV AL, 05H             ; Mask CH1
    OUT SMASK, AL

    ; Master clear
    OUT MCLR, AL

    ; ── CH0: Source = 13FFFH (page 1, offset 3FFFH) ──
    MOV AL, 01H
    OUT CH0PAGE, AL         ; CH0 page = 1 → A16-A19 = 0001

    OUT FFLOP, AL           ; Clear flip-flop

    MOV AL, 0FFH            ; Low byte of 3FFFH
    OUT CH0A, AL
    MOV AL, 3FH             ; High byte of 3FFFH
    OUT CH0A, AL

    ; CH0 count = 0 (transfers only 1 byte — it's held anyway)
    OUT FFLOP, AL
    MOV AL, 00H
    OUT CH0C, AL
    MOV AL, 00H
    OUT CH0C, AL

    ; ── CH1: Destination = 14000H (page 1, offset 4000H) ──
    ; Wait — 14000H: page = 1, offset = 4000H
    ; (Page covers A16–A19, offset is A0–A15)
    MOV AL, 01H
    OUT CH1PAGE, AL         ; CH1 page = 1

    OUT FFLOP, AL
    MOV AL, 00H             ; Low byte of 4000H
    OUT CH1A, AL
    MOV AL, 40H             ; High byte of 4000H
    OUT CH1A, AL

    ; CH1 count = 4000H - 1 = 3FFFH
    OUT FFLOP, AL
    MOV AL, 0FFH            ; Low byte of 3FFFH
    OUT CH1C, AL
    MOV AL, 3FH             ; High byte of 3FFFH
    OUT CH1C, AL

    ; ── Mode: CH0 = block read (source), CH1 = block write (dest) ──
    MOV AL, 88H             ; CH0: block, read, no auto, increment
    OUT MODE, AL
    MOV AL, 85H             ; CH1: block, write, no auto, increment
    OUT MODE, AL

    ; ── Command: M→M enabled, CH0 address HOLD (D2=1, D0=1) ──
    MOV AL, 05H             ; D2=1 (CH0 hold), D0=1 (M→M enable)
    OUT CMD, AL

    ; ── Place fill value at source address ──
    ; We need to write 00H to physical address 13FFFH
    ; Since we can't do 20-bit addressing directly, use a segment:offset
    ; Segment 13FFH, offset 0000H? No: 13FFH×16 + 0 = 13FF0H ≠ 13FFFH
    ; Segment 1000H, offset 3FFFH: 1000×16 + 3FFF = 10000+3FFF = 13FFFH ✓
    MOV AX, 1000H
    MOV ES, AX
    MOV BYTE PTR ES:[3FFFH], 00H   ; Store fill value at 13FFFH

    ; ── Unmask and trigger ──
    MOV AL, 00H             ; Unmask CH0
    OUT SMASK, AL
    MOV AL, 01H             ; Unmask CH1
    OUT SMASK, AL

    MOV AL, 04H             ; Software request CH0 (D2=1, D1D0=00)
    OUT REQ, AL

    ; ── Poll for completion ──
WAIT:
    IN  AL, CMD             ; Read status register (same port 08H)
    TEST AL, 02H            ; TC1 bit (channel 1 done)
    JZ  WAIT

    ; Memory from 14000H to 17FFFH is now all 00H
    RET

CLEAR ENDP

CODE ENDS
END
```

---

## Complete Application: DMA-Driven Printer Interface

The real power of DMA. Instead of the CPU sending one character at a time to the printer (polling or interrupt-driven), the 8237 feeds the entire print buffer to the printer at hardware speed. CPU hands off the job and goes free.

**Setup:**

- Print buffer in memory at `20000H`, 100 bytes
- Printer data port at I/O address `378H` (LPT1 data)
- Printer uses DREQ3, DACK̄3
- Channel 3 configured: read from memory, write to I/O, single mode (printer needs handshaking per byte)

```masm
DATA SEGMENT
    PRINT_BUF  DB 'HELLO FROM DMA PRINTER INTERFACE'
               DB 0DH, 0AH                    ; CR+LF
               DB 66 DUP('*')                 ; Fill to 100 bytes
    BUF_SIZE   EQU 100
    PRINT_DONE DB 0                            ; Flag: set by EOI ISR
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA

CH3A    EQU 06H             ; CH3 address port
CH3C    EQU 07H             ; CH3 count port
CH3PAGE EQU 82H             ; CH3 page register (IBM PC)

; ─────────────────────────────────────────
; DMA Complete ISR — triggered by EOP̄ → 8259 IR
; ─────────────────────────────────────────
DMA_DONE_ISR PROC FAR
    PUSH AX
    PUSH DS

    MOV AX, SEG PRINT_DONE
    MOV DS, AX

    MOV PRINT_DONE, 1       ; Signal main program

    ; Re-mask CH3 — transfer is done, don't accept new DREQs
    MOV AL, 07H             ; Mask CH3 (D2=1, D1D0=11)
    OUT 0AH, AL

    ; EOI to 8259
    MOV AL, 20H
    OUT 20H, AL

    POP DS
    POP AX
    IRET
DMA_DONE_ISR ENDP


; ─────────────────────────────────────────
; PRINT procedure — sets up DMA and returns
; (non-blocking: CPU is free while printing)
; ─────────────────────────────────────────
PRINT PROC NEAR

    ; Step 1: Mask CH3 during setup
    MOV AL, 07H             ; Mask CH3
    OUT 0AH, AL

    ; Step 2: Clear flip-flop
    OUT 0CH, AL

    ; Step 3: Source address — physical address of PRINT_BUF
    ; Calculate: SEG PRINT_BUF × 16 + OFFSET PRINT_BUF
    ; For simplicity assume it's in page 0 (address < 10000H)
    ; Page = 0
    MOV AL, 00H
    OUT CH3PAGE, AL         ; CH3 page = 0

    OUT 0CH, AL             ; Clear flip-flop
    MOV AX, OFFSET PRINT_BUF
    MOV AL, AL              ; Low byte of offset
    OUT CH3A, AL
    MOV AL, AH              ; High byte of offset
    OUT CH3A, AL

    ; Step 4: Count = BUF_SIZE - 1 = 99 = 63H
    OUT 0CH, AL
    MOV AL, 63H             ; Low byte of 99
    OUT CH3C, AL
    MOV AL, 00H             ; High byte = 0
    OUT CH3C, AL

    ; Step 5: Mode — CH3, single mode, read (memory→I/O), no auto-init, increment
    ; D7D6=01(single), D5=0(inc), D4=0, D3D2=10(read), D1D0=11(CH3)
    ; = 0100 1011 = 4BH
    MOV AL, 4BH
    OUT 0BH, AL

    ; Step 6: Command — normal operation (no M→M)
    MOV AL, 00H
    OUT 08H, AL

    ; Step 7: Unmask CH3 — DMA can now respond to printer's DREQ
    MOV AL, 03H             ; Unmask CH3 (D2=0, D1D0=11)
    OUT 0AH, AL

    ; Transfer now runs autonomously:
    ; Printer asserts DREQ3 when ready for each byte
    ; 8237 responds, sends byte, 8237 releases bus
    ; Repeats 100 times
    ; When done, EOP̄ fires → 8259 → DMA_DONE_ISR

    RET                     ; Return immediately — printing happens in background
PRINT ENDP


; ─────────────────────────────────────────
; Main Program
; ─────────────────────────────────────────
START:
    MOV AX, DATA
    MOV DS, AX

    MOV PRINT_DONE, 0

    ; Install DMA done ISR (assume EOP̄ → IR5 → Type 0DH)
    CLI
    MOV AX, 0
    MOV ES, AX
    MOV WORD PTR ES:[0DH*4],   OFFSET DMA_DONE_ISR
    MOV WORD PTR ES:[0DH*4+2], SEG    DMA_DONE_ISR
    STI

    ; Start printing (non-blocking)
    CALL PRINT

    ; CPU is now free — do other work while printing
    ; For demo: just wait for completion
WAIT_PRINT:
    CMP PRINT_DONE, 1
    JNE WAIT_PRINT

    ; Printing complete
    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
```

---

## Common DMA Bugs — And What They Produce

```
Bug 1: Forgetting to clear the flip-flop
       Effect: High and low address bytes are swapped
               → Transfer happens at wrong address (256× off)
               → Memory corruption in a completely unexpected location

Bug 2: Writing count = N instead of N-1
       Effect: Transfer is one byte too long
               → Reads/writes one extra byte past the intended end
               → Could corrupt adjacent data structure

Bug 3: Unmasking channel before mode/address/count are loaded
       Effect: Channel starts transferring with garbage addresses
               → Random memory corruption
               → System crash

Bug 4: Not setting page registers
       Effect: Transfer always happens in page 0 (first 64KB)
               → Source/destination in upper memory silently ignored

Bug 5: Using block mode with a device that needs bus time
       Effect: CPU bus-starved during entire transfer
               → Timer interrupts missed, display freezes
               → Use single or demand mode for real-time systems

Bug 6: Not sending slave EOI before master EOI (cascade)
       Effect: Spurious interrupt on next DMA completion
               → Cascaded 8259 gets confused
               → Always: slave EOI → master EOI

Bug 7: Polling TC without knowing it clears on read
       Effect: Two reads for same TC → second read always shows 0
               → Looks like transfer never completed
               → Read once, store in register, then check
```

---

## Module 7 Summary

**The 8237 is a 4-channel DMA controller. Each channel has:**
 
|Register|Size|Purpose|
|---|---|---|
|Current Address|16-bit|Where to read/write next (auto-increments/decrements)|
|Base Address|16-bit|Start address (reloaded on auto-init)|
|Current Count|16-bit|Bytes remaining − 1|
|Base Count|16-bit|Original count (reloaded on auto-init)|

**Programming order — every time, no exceptions:**

1. Mask the channel
2. Master Clear (first time) or Clear Flip-Flop
3. Write address (LSB then MSB)
4. Clear Flip-Flop again
5. Write count (N−1, LSB then MSB)
6. Write Mode Register
7. Write Command Register
8. Unmask the channel

**Three things that make DMA work as a system:**

- **HRQ/HLDA:** Bus ownership handshake with CPU
- **ADSTB + external latch:** Generates full 16-bit address from 8-bit data bus
- **AEN:** Tells system decoders "8237 owns the bus now"

**Mode selection guide:**

- Demand Mode → device controls pacing, bus shared
- Single Mode → one byte per DREQ, maximum bus sharing
- Block Mode → fastest, no bus sharing until TC
- Cascade → connect second 8237

---

