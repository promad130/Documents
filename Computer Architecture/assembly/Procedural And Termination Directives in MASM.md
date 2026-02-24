
Since you are on an **8086 (16-bit)** system, the rules change slightly. You don't have 32-bit registers (like `EAX`), and your "native" size is a **WORD** (2 bytes), not a DWORD.

Here is how Procedural Directives work specifically for the 16-bit world.

### 1. The Core: `PROC` and `ENDP`

These define the boundaries of your function.

- **`PROC`**: Tells the assembler "Function starts here."
    
- **`ENDP`**: Tells the assembler "Function ends here."
    

**Crucial 8086 Difference: `NEAR` vs. `FAR`** In 16-bit architecture, memory is segmented (Code Segment, Data Segment, etc.). You must tell the assembler if the function is in the _same_ segment or a _different_ one.

- **`NEAR`**: The function is in the same Code Segment (`CS`). The CPU only needs to save the **Instruction Pointer (`IP`)**. (This is the default for small programs).
    
- **`FAR`**: The function is in a different segment. The CPU must save **both** `CS` and `IP`.
    

Code snippet

```
; DEFINITION
MyAdd PROC NEAR       ; Directive: Start of function (Same Segment)
    ADD AX, BX        ; Logic
    RET               ; Instruction: Return
MyAdd ENDP            ; Directive: End of function
```

### 2. The Argument Directive (High-Level Syntax)

Writing raw assembly for stack frames is painful. You have to calculate offsets like `[BP+4]` manually. MASM allows you to define arguments right in the `PROC` line.

**The Directive Way:**

Code snippet

```
Sum PROC NEAR, arg1:WORD, arg2:WORD
```

- **What MASM does:** It automatically equates `arg1` to `[BP+4]` and `arg2` to `[BP+6]`.
    
- **What you write:** `MOV AX, arg1`
    
- **What the CPU executes:** `MOV AX, [BP+4]`
    

### 3. `LOCAL` Directive (Local Variables)

If you need variables that only exist _inside_ the function (stored on the stack), use `LOCAL`.

- **Syntax:** `LOCAL varName:Type`
    
- **Action:** MASM reserves space on the stack by subtracting from the Stack Pointer (`SP`) and allows you to use the name instead of `[BP-2]`.
    

Code snippet

```
MyFunc PROC NEAR
    LOCAL temp:WORD   ; Reserve 2 bytes on stack for 'temp'
    MOV temp, 10      ; Move 10 into that stack slot
    MOV AX, temp
    RET
MyFunc ENDP
```

### 4. `USES` Directive (Register Saving)

In 8086, registers are scarce. You often need to save `SI`, `DI`, or `BP` before using them. The `USES` directive tells MASM to generate `PUSH` instructions at the start and `POP` instructions at the end automatically.

Code snippet

```
; Auto-saves SI and DI
ProcessArray PROC NEAR USES SI DI, count:WORD
    MOV SI, count
    ; Do work...
    RET               ; MASM inserts POP DI, POP SI before this RET
ProcessArray ENDP
```

### Visualizing the "Stack Frame"

When you use these directives, MASM is silently building a **Stack Frame** for you.

1. **Arguments**: Pushed by the caller (Positive offset from BP).
    
2. **Return Address**: Pushed by `CALL`.
    
3. **Saved BP**: Pushed by the Prologue.
    
4. **Local Variables**: Space reserved by `SUB SP, n` (Negative offset from BP).

# Module Termination Directive

### 1. `END` ([[END Directive in MASM]])

- **Type:** **Assembly Control / Module Termination**
    
- **Scope:** The entire file (Source Module).
    
- **Action:** Tells the **Assembler** (MASM) to stop reading the file immediately.
    
- **Optional Argument:** The Entry Point label (e.g., `END MAIN`).
    

### 2. `ENDP` (End Procedure)

- **Type:** **Procedure Definition**
    
- **Scope:** A specific block of code (function).
    
- **Action:** Marks the end of a procedure block (like a closing curly brace `}` in C).
    
- **Syntax:** `MAIN ENDP`
    

### 3. `ENDS` (End Segment)

- **Type:** **Segment Definition**
    
- **Scope:** A memory segment.
    
- **Action:** Marks the end of a segment block (e.g., end of data or stack).
    
- **Syntax:** `MY_DATA ENDS` (Used in the "Old/Full" segment definitions, not the `.DATA` simplified models).