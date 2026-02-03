## So what happens when we compile a code?

However when we compile the code, a series of tasks are performed first by the operating system called "Compilation pipeline", This happens before execution.
The process of compilation pipeline consists of:
1) preprocessor
2) compiling
3) assembling
4) linking
These tasks all together form something called **"Compilation pipeline"**.

---
Let’s break down the compilation pipeline into its main steps, including preprocessing, compiling, assembling, and linking.

---
### Compilation Process Overview
1. **Preprocessing**
- The preprocessor handles directives like `#include`, `#define`, and `#ifdef`.
- It produces a **pure C code file** with macros expanded and includes replaced by their actual content.
- Input: `.c` file with preprocessor directives.
- Output: **Expanded C code** (no directives, typically `.i` file).

---
2. **Compilation**
- The compiler translates the expanded C code into **assembly language**, a low-level language specific to the target architecture (e.g., x86, ARM).
- Input: Expanded C code (`.i` file).
- Output: **Assembly code** (usually `.s` file).

---
3. **Assembling**
- The assembler converts assembly code into **machine code** (binary instructions that the CPU can execute).
- This machine code is stored in an **object file**, which is not yet executable because it may reference external symbols (functions or variables in other files).
- Input: Assembly code (`.s` file).
- Output: **Object file** (e.g., `.o` or `.obj` file, depending on the platform).

---
Explain C99 and all, i.e., the standardized versions and all

---
