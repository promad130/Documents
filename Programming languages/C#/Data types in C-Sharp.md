**Expected to know:** [[Introduction to Programming]],[[Data Types and Constants]]
**Topics Covered:**
**Tags:** [[Data Types and Constants]]

Well numbers are numbers no matter which language you choose to write them in. 
**The concept of how data should be classified and stored is broadly similar across programming languages**, even though the specific sizes, representations, and details may differ.

Hence, lets have a look at the size of data types here and lets look at what is new!

---
# Data type sizes and types
Just like in [[Data Types and Constants]], this also have numeric and all, but due to it's object Oriented approach, as few things are defined different.

In C#, data types are primarily classified into two main categories:
- **Value Types:**
    - These data types hold the actual data within their own memory allocation.
    - When you assign a value type to another variable, a copy of the value is created.
    - Examples include:
        - Integral types (e.g., `int`, `long`, `short`, `byte`)
        - Floating-point types (e.g., `float`, `double`, `decimal`)
        - Boolean type (`bool`)
        - Character type (`char`)
        - Structs
        - Enums
- **Reference Types:**
    - These data types store a reference (a memory address) to the data, rather than the data itself.
    - When you assign a reference type to another variable, you're copying the reference, so both variables point to the same data in memory.
    - Examples include:
        - Classes
        - Strings (`string`)
        - Arrays
        - Interfaces
        - Delegates

It is also worth noting that there are also pointer types, but these are used in *unsafe code blocks.*
## Value Type

We can say these include all the primitive data types mentioned in [[Data Types and Constants]], and also it includes [[Structs]] and [[Enums]].

However, there are a few more data types here that were not covered in [[Data Types and Constants]], and the syntax is a bit different:

### **1) Integral type**

#### Different Syntax for:

##### u(name of data type) $\implies$ unsigned data type
##### s(name of data type) $\implies$ signed data type
**(include example)**
#### byte

It is an unsigned 8-bit long integer, i.e., 1 byte data type modifier.
Min. Value: 0
Max. Value: 255

No architecture exception as of till now.

### **2) Floating Point Numbers**

The floating point number here are same as that in [[Data Types and Constants]].
- Doubles
- floats
By default, any literal is set to double if given a decimal point, for float, one has to write `23.5523f`.
#### Decimals
Now, we know that double and float can cause precision errors, so C# has another data type called `decimal`.
This data type is way more precise!

Size; 16 bytes
precision:  upto 28 decimal places
Declaration;
```c
decimal a = 56.3326565265m;
```

- `m` is a notation used in order to tell the compiler to store the value in decimal format. 
- But why?
- Why do we need notation to tell which literal is there, but none were needed in  The C Programming language.

---
# What is new?

