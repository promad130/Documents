
# 1. General-Purpose Registers (Your Main Variables)
These are like your regular variables in C/C++. You can store any data and perform arithmetic operations.

## 32-bit General-Purpose Registers (8 total)

| Register | Historical Name   | Typical Use                                | Like Variables In...   |
| -------- | ----------------- | ------------------------------------------ | ---------------------- |
| **EAX**  | Accumulator       | Arithmetic, return values                  | `int result`           |
| **EBX**  | Base              | Base pointer for arrays                    | `int *base_ptr`        |
| **ECX**  | Counter           | Loop counters                              | `int i` (in for loops) |
| **EDX**  | Data              | I/O operations, arithmetic                 | `int data`             |
| **ESI**  | Source Index      | Source for string operations               | `char *src`            |
| **EDI**  | Destination Index | Destination for string operations          | `char *dest`           |
| **ESP**  | Stack Pointer     | **Special**: Points to top of stack        | (automatic in C)       |
| **EBP**  | Base Pointer      | **Special**: Points to current stack frame | (automatic in C)       |

## Register Size Variations (Multi-size Variables)

Unlike C where `int` is always 4 bytes, you can access the **same register** in different sizes:
```
; 32-bit (full register)
mov eax, 12345678h   ; EAX = 0x12345678

; 16-bit (lower half)
mov ax, 1234h        ; AX = 0x1234, EAX = 0x12341234

; 8-bit (even smaller pieces)
mov al, 56h          ; AL = 0x56, AX = 0x1256, EAX = 0x12341256
mov ah, 78h          ; AH = 0x78, AX = 0x7856, EAX = 0x12347856

```
**Visual breakdown for EAX:**
```
EAX: [31....24][23....16][15....8][7.....0]
                                  [AH] [AL] <- 8-bit pieces
                          [      AX      ] <- 16-bit piece
     [         EAX                     ] <- 32-bit full

```
## 2. Segment Registers (Memory Organization)

Think of these as **base addresses** for different sections of your program:

| Register | Purpose          | Like in C                        |
| -------- | ---------------- | -------------------------------- |
| **CS**   | Code Segment     | Where your functions live        |
| **DS**   | Data Segment     | Where your global variables live |
| **SS**   | Stack Segment    | Where your local variables live  |
| **ES**   | Extra Segment    | Extra space for data             |
| **FS**   | Extra Segment #2 | Thread-local storage             |
| **GS**   | Extra Segment #3 | Operating system data            |

**Modern Note:** In 32-bit/64-bit mode, most of these point to the same memory space (flat model), so you usually don't worry about them.[eecg.utoronto+1](https://www.eecg.utoronto.ca/~amza/www.mindsec.com/files/x86regs.html)

## 3. Flags Register (Status Information)

**EFLAGS** is like a collection of **boolean variables** that tell you the result of operations:

|Flag|Bit|Purpose|Like in C|
|---|---|---|---|
|**CF**|0|Carry Flag|Did addition overflow?|
|**ZF**|6|Zero Flag|`if (result == 0)`|
|**SF**|7|Sign Flag|`if (result < 0)`|
|**OF**|11|Overflow Flag|Did signed math overflow?|
|**PF**|2|Parity Flag|Even number of 1-bits?|

Example:

text

`mov eax, 5 sub eax, 5        ; EAX = 0 ; ZF (Zero Flag) is now SET because result is 0 jz zero_label     ; Jump if Zero flag is set`

## 4. Instruction Pointer (Program Counter)

|Register|Purpose|Like in C|
|---|---|---|
|**EIP**|Points to next instruction|Hidden - compiler manages this|

You can't directly modify EIP, but **jump instructions** change it:[wikipedia+1](https://en.wikipedia.org/wiki/Program_counter)

text

`jmp my_label      ; EIP now points to my_label call my_function  ; EIP points to my_function, old EIP saved on stack`

## 5. Data Types in Assembly

Unlike C's `int`, `char`, `float`, assembly sees everything as **raw bytes**:

## Declaring Variables (in .data section)

text

`section .data myByte    db  42        ; 1 byte (like unsigned char) myWord    dw  1234      ; 2 bytes (like short) myDword   dd  12345678  ; 4 bytes (like int) myString  db  'Hello',0 ; Array of bytes (like char array) myArray   dd  1,2,3,4   ; Array of 4-byte integers`

## Size Prefixes

- **db** = define byte (1 byte)
    
- **dw** = define word (2 bytes)
    
- **dd** = define double word (4 bytes)
    
- **dq** = define quad word (8 bytes)
    

## 6. Practice: Variable Assignment

Let's compare C code to Assembly:

**C Code:**

c

`int a = 10; int b = 20; int sum = a + b;`

**Assembly Code:**

text

`section .data a     dd  10          ; int a = 10 b     dd  20          ; int b = 20 sum   dd  0           ; int sum = 0  section .text mov eax, [a]          ; Load a into EAX register add eax, [b]          ; Add b to EAX mov [sum], eax        ; Store result in sum`

**Your Turn:**

1. What will be stored in the `sum` variable after this code runs?
    
2. Which register acts like a temporary variable here?
    
3. If we changed `add eax, [b]` to `sub eax, [b]`, what would happen?
    