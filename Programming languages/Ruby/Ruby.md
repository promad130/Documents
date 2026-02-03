Ruby is a dynamic, open-source, general-purpose programming language designed for simplicity and productivity. It was created in the mid-1990s by Yukihiro "Matz" Matsumoto in Japan, with its first public release in 1995. Ruby is renowned for its elegant and easy-to-read syntax, which aims to make programming both enjoyable and efficient for developers.

## Key Features

- **Object-Oriented**: In Ruby, everything is an object, including primitive data types. This pure object-oriented approach allows for consistent and flexible code design.
- **Interpreted and Dynamically Typed**: Ruby code is executed by an interpreter, not compiled, and variable types are determined at runtime.
- **Multi-Paradigm**: Ruby supports procedural, object-oriented, and functional programming styles, offering developers flexibility in how they structure their programs.
- **Readable Syntax**: The syntax is designed to be natural and intuitive, often resembling plain English, which lowers the barrier to entry for new programmers and enhances code maintainability.
- **Garbage Collection**: Ruby manages memory automatically, freeing developers from manual memory management.
- **Extensive Standard Library**: Ruby comes with a rich set of built-in functions and libraries for tasks ranging from web development to data processing.

## Common Uses

Ruby is a versatile language used for a variety of applications, including:

- **Web Development**: Ruby is best known for powering the Ruby on Rails framework, which revolutionized web application development by enabling rapid prototyping and clean, maintainable code. Major platforms like GitHub, Shopify, Airbnb, and Hulu use Ruby on Rails
- **Automation and Scripting**: Ruby excels at writing scripts for automation, system administration, and data processing
- **Command-Line Tools**: Many developers use Ruby to create robust command-line utilities
- **Prototyping and MVPs**: Its productivity and flexibility make Ruby a popular choice for startups and rapid prototyping

# That is all about Ruby, but How is it executed?

Ruby's execution process combines interpretation and just-in-time (JIT) compilation, differing significantly from compiled languages like C. Here's how it works:

## Execution Steps

1. **Tokenization & Lexical Analysis**  
    The Ruby interpreter breaks source code into tokens (keywords, identifiers, operators) and validates basic syntax.

2. **Parsing & AST Generation**  
    Tokens are analyzed to build an Abstract Syntax Tree (AST), ensuring code follows Ruby's syntax rules.

3. **Bytecode Compilation**  
    The AST is converted to YARV (Yet Another Ruby VM) bytecode—a low-level intermediate representation. Example bytecode:
    
```ruby
# For `x = 5 + 3` 
putobject 5 
putobject 3 
opt_plus 
setlocal x
```

4. **Virtual Machine Execution**  
    YARV executes the bytecode, handling:
    
    - Memory management (garbage collection)
    - Dynamic method dispatch
    - Metaprogramming features

5. **JIT Compilation (Optional)**  
    Modern Ruby implementations (e.g., MJIT) identify hot code paths and compile them to native machine code during execution.
    

## Key Differences from C

| Feature               | C                      | Ruby                                  |
| --------------------- | ---------------------- | ------------------------------------- |
| **Compilation**       | Direct to machine code | Bytecode for VM                       |
| **Type Checking**     | Compile-time           | Runtime                               |
| **Memory Management** | Manual (`malloc/free`) | Automatic garbage collection          |
| **Execution Model**   | Static binary          | Interpreter + VM required             |
| **Dynamism**          | Fixed at compile time  | Methods/classes modifiable at runtime |
(Detailing Later)

---
# Syntax for Ruby

Now that we know what Ruby and and how it works and executed, let's have a look at the syntax of Ruby:
[[Syntax for Ruby]] 

---
