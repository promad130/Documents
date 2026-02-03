An **implementation** refers to the actual software system that enables programs written in that language to be executed or interpreted. This includes compilers, interpreters, and runtime environments. Each programming language requires an implementation, as a programming language is just a way to communicate to the computer.

---
## What is an Implementation in Programming Languages?

An **implementation** of a programming language is a software system or toolchain that translates, interprets, and executes code written in that language according to the language's specifications.
It consists of:
1. **[[Program execution strategies]]:**
	- Converts the source code written by a programmer into machine-readable code (compilers) or executes it directly (interpreters).
2. **[[Standard Library]]:**
	- A collection of pre-written code that provides essential functionality like string manipulation, file handling, and math operations.
 3. **[[Runtime System]]:**
	- Manages the execution of programs, including memory management, error handling, and input/output operations.

---
### Examples of Programming Language Implementations
Here’s how various languages are implemented:
#### 1. Python
- **CPython:** The default and most widely used implementation of Python, written in C.
- **PyPy:** An alternative implementation, optimized for speed using Just-In-Time (JIT) compilation.
- **Jython:** An implementation of Python written in Java, designed to run on the JVM (Java Virtual Machine).

#### 2. Java
- **OpenJDK:** A free and open-source implementation of the Java Standard Edition.
- **Oracle JDK:** A commercial implementation of Java by Oracle.
- Java programs are executed by the **Java Virtual Machine (JVM)**, which is part of these implementations.

#### 3. JavaScript
- Each browser has its own JavaScript implementation:
- **V8 (Chrome, Node.js):** Developed by Google.
- **SpiderMonkey (Firefox):** Developed by Mozilla.
- **JavaScriptCore (Safari):** Developed by Apple.
- These implementations interpret JavaScript code and optimize its execution.

#### 4. C++
- **GCC (GNU Compiler Collection):** A widely used open-source compiler for C++.
- **Clang:** A modern compiler with advanced diagnostics and fast performance.
- **MSVC (Microsoft Visual C++):** A compiler developed by Microsoft.

#### 5. Ruby
- **MRI (Matz's Ruby Interpreter):** The standard Ruby implementation, written in C.
- **JRuby:** Ruby implemented in Java, designed to run on the JVM.
- **TruffleRuby:** A high-performance Ruby implementation built on the GraalVM.

#### 6. Rust
- **Rust Compiler (rustc):** The standard Rust compiler implementation, developed by the Rust community.
- **MRustC:** An experimental implementation of the Rust compiler.

---
### Implementation vs. Specification
- **Specification:**
	- A formal definition of how the programming language should behave (syntax, semantics, etc.).
	- Example: The **C Standard (ISO/IEC 9899)** defines how C should work.
- **Implementation:**
	- The practical software that adheres to the specification and executes the code.
	- Example: GCC or Clang are implementations of the C language specification.
---
## What happens when I run a script in an [[Integrated Development Environment(IDE)]]?
When you write a script in a programming language and press the "run" button (for example, in an IDE or code editor), the following general process occurs:
1. **Script Loading**  
    The script file you wrote is loaded into memory by the programming environment or runtime.
2. **Interpretation or Compilation**
    - For most scripting languages (like Python, JavaScript, or PHP), the code is _interpreted_. This means the interpreter reads your script line by line and executes each instruction directly, translating it into machine code on the fly at runtime.
    - For some languages (like Java or C#), the code may first be compiled into an intermediate form (such as bytecode), which is then executed by a virtual machine (like the JVM for Java). In this process, the compiler translates your code into a lower-level form, and the virtual machine interprets or further compiles it to machine code as needed.
3. **Execution Begins**  
    The script typically starts running from the first line of code and proceeds sequentially, unless directed otherwise by functions, loops, or conditionals.
4. **Runtime Handling**
    - The interpreter or runtime environment manages memory allocation, variable typing (often dynamically in scripting languages), and executes built-in or user-defined functions as they are encountered.
    - Errors or exceptions are handled as they occur, often stopping execution or invoking error-handling routines.
5. **Output and Results**  
    Any output produced by your script (such as print statements or results) is displayed in the console, output window, or user interface provided by your programming environment.

**Example:**  
If you run a Python script in an IDE, the IDE invokes the Python interpreter, which reads your script, processes each line, and displays output in the output pane. If you run a JavaScript script in a browser, the browser's JavaScript engine interprets and executes your code, updating the webpage or logging results to the console.
