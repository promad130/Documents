**Expected to know:** [[Introduction to Programming]] 
**Topics Covered:** Basic Data Types(These include both built-in and User Defined Data Types), Non-primitive data type, primitive data type, Data Type Precedence, sizeof() function

Imagine you're organizing a box of toys. You wouldn't put all the toys in one big pile, right? 
You'd probably sort them into categories: 
	- cars, 
	- dolls, 
	- building blocks, etc. 

Data types in programming are like those categories for the information your program uses. They tell the computer what kind of information it's dealing with.

---
# What actually are data types?

You can think of **data types** as a **general classification** that tells the computer what kind of data it is dealing with, while **constants**(will be covered next) are specific values that belong to a particular data type.
These are used everywhere, i.e., in functions, variable, mathematical equations, classes, etc. This is like the part of very heart of programming.

---
# Why do we need data types?
Think of it this way: the computer is very literal. It needs to know exactly what kind of information it's working with so it can handle it correctly. If you try to add a word to a number, the computer will get confused unless it knows how to handle that situation. Data types prevent these kinds of mix-ups.

Data types are fundamental to programming. They classify the kind of data a variable can hold. 
Think of them as labels that tell the computer how to interpret and store a piece of information.
There's one more important thing that a data type tells us – what operations are valid for that data type.
For example, it makes sense to be able to add integers in the normal way, but adding an integer to a true or false value (a Boolean) doesn't make sense at all.

---
# Data Type Modifiers:
These modifiers alter the basic data types to provide more flexibility:
- **signed:**
    - Allows a variable to store both positive and negative values. 

- **unsigned:**
    - Allows a variable to store only non-negative values.

- **short:**
    - Reduces the memory allocated to an integer.
    - Reduces it to the size of short, i.e., 2 bytes

- **long:**
    - Increases the memory allocated to an integer or double.
    - The size to which it increases is mention in [[Numeric Types]]
    - This can either be used with the data type like `long data_type`, or you can use this with a literal like `256L`.

It's important to note that the exact size of these data types can vary depending on the compiler and the system architecture.

---
# **Data Types**

Here's a breakdown of data types in general, along with explanations and examples:

**1. Primitive Data Types (or Fundamental Data Types):**

These are the most basic building blocks. They represent single values. Most programming languages have a set of built-in primitive data types.

- **[[Numeric Types]]:** These represent numbers.
    
	- **Integers:** Whole numbers (without a fractional or decimal part). Examples: -10, 0, 5, 100, 12345. Many languages have different sizes of integers (e.g., `int`, `long`, `short`) to allow you to choose the appropriate range of values and memory usage.
    
	- **Floating-point Numbers:** Numbers with a decimal point (or that can have a fractional part). Examples: 3.14, -2.5, 0.0, 1.618. Common types include `float` (single-precision) and `double` (double-precision), which offer different levels of precision.

- **[[Character Type]]:** 
	
	- Represents a single character (a letter, symbol, or punctuation mark). Examples: 'A', 'z', '$', '9'. Often represented using single quotes.
	- Now we know that the computer only understands binary, so how does these work?
	- Well the answer to that question is ASCII. These symbols represent a specific number for example 65 means 'A', and 97 means 'a'. These numbers are assigned by [[ASCII]] and are used universally in order to maintain uniformity.

- **[[Boolean Type]]:** 
	- Represents a truth value: either `true` or `false`. Used in logical operations and conditional statements.

**2. Non-Primitive Data Types (or Complex Data Types, or Reference Types):**

These are built from primitive data types or other non-primitive types. They can hold collections of values or more complex data structures. 
These derived data types can be said to be a combination of [[Data Types and Constants]] and [[Data Structures]].

- **[[Arrays]]:** 
	- Ordered collections of elements of the _same_ data type. Think of them as a list of boxes, each holding a value of the same type. You access elements in an array using an index (usually starting from 0).

- **[[Strings]]:** 
	- Sequences of characters. Examples: "Hello", "World", "Python programming". Often represented using double quotes. While some languages might treat strings as an array of characters (which would technically make them a data structure), they're so commonly used that it's useful to consider them as a separate category.
	

- **[[Lists (or Dynamic Arrays)]]:** 
	- Similar to arrays, but often more flexible. They can often hold elements of _different_ data types (depending on the language) and can grow or shrink in size dynamically.

- **[[Objects]]:** 
	- Instances of classes. Objects can contain both data (attributes or fields) and functions (methods) that operate on that data. Objects are a core concept in object-oriented programming.

- **[[Dictionaries (or Hash Maps, Associative Arrays)]]:** 
	- Collections of key-value pairs. Each key is unique and is used to access its corresponding value. 
	- Think of them like a real-world dictionary where you look up a word (key) to find its definition (value).

- **[[Sets]]:** 
	- Unordered collections of unique elements. No duplicates are allowed.


**Why are Data Types Important?**

- **Memory Management:** Data types tell the computer how much memory to allocate for a variable. Integers typically require less memory than floating-point numbers, and strings require memory proportional to their length.
- **Data Integrity:** Data types help prevent errors by ensuring that operations are performed on compatible data. You wouldn't try to multiply a string by a number (unless the language has specific rules for that).
- **Code Readability:** Data types make code easier to understand by clearly indicating the kind of data being stored and manipulated.
- **Performance:** Choosing the appropriate data type can improve the performance of your program. For example, using integers for whole numbers is generally faster than using floating-point numbers.

Understanding data types is crucial for writing correct and efficient programs. Choosing the right data type for each variable is an important design decision that impacts how your program works.

---
# **Data Type Precedence**

Data type precedence (or **type promotion hierarchy**) refers to **the priority of data types** when different types are involved in an expression. It determines **how implicit type conversions (type promotions) happen** in mixed-type operations.

When an operation involves **multiple data types**, C **automatically converts** smaller data types to larger ones to prevent data loss.

**Example:**
```c

#include <stdio.h>  

int main() 
{     
	int a = 5;     
	float b = 2.5;     
	float result = a + b;  // 'a' (int) is promoted to float      
	printf("%f\n", result);  // Output: 7.500000     
	return 0; 
}
```

**Here, `int a` is converted to `float` before the addition happens.**

#### The data types follow this order of precedence **from lowest to highest**:

| Precedence  | Data Type                              |
| ----------- | -------------------------------------- |
| 1 (Lowest)  | `char`, `signed char`, `unsigned char` |
| 2           | `short`, `unsigned short`              |
| 3           | `int`, `unsigned int`                  |
| 4           | `long`, `unsigned long`                |
| 5           | `long long`, `unsigned long long`      |
| 6           | `float`                                |
| 7           | `double`                               |
| 8 (Highest) | `long double`                          |

#### So to sum it up:

| Rule                       | Explanation                                                              |
| -------------------------- | ------------------------------------------------------------------------ |
| **Small to Big Promotion** | Lower precedence types automatically convert to higher precedence types. |
| **Integer Promotion**      | `char` and `short` are always promoted to `int` before calculations.     |
| **Float & Double**         | `float` converts to `double` in mixed operations.                        |
| **Explicit Casting**       | Used when implicit conversion is not suitable.                           |

---
# **User Defined Data Types**

In many programming languages users can also define their own custom data tyeps.
There are many types of user-defined data types:
- [[Structs]]
- [[Enums]] 
- [[Unions]]
---
# Finding size of stuff in The C Programming Language

### **What Does `sizeof()` Do?**

The `sizeof()` operator in C **returns the size (in bytes) of a variable, data type, or expression**. It tells you how much memory a particular data type or object occupies.

***For the data type that it returns, it is of size_t type, so what we should use as a format specifier is %zu or %lu, not %d, but for now, lets work with %d as well, but search what is %zu and %lu, what is size_t, and when will %d will break.***


### **Basic Syntax**

```c
sizeof(expression)
sizeof(type)
```

- `expression` → Can be a variable, array, or pointer.
- `type` → Can be a data type (e.g., `int`, `char`, `float`, etc.).

---

### **Example 1: Using `sizeof()` with Data Types**

```c
#include <stdio.h>

int main() {
    printf("Size of int: %lu bytes\n", sizeof(int));
    printf("Size of float: %lu bytes\n", sizeof(float));
    printf("Size of double: %lu bytes\n", sizeof(double));
    printf("Size of char: %lu bytes\n", sizeof(char));
    return 0;
}
```

✅ **Output (on a typical system):**

```
Size of int: 4 bytes
Size of float: 4 bytes
Size of double: 8 bytes
Size of char: 1 byte
```

🔹 **Note:** The actual size may vary depending on the system and compiler.

---

### **Example 2: Using `sizeof()` with Variables**

```c
#include <stdio.h>

int main() {
    int x = 10;
    double y = 5.5;
    
    printf("Size of x: %lu bytes\n", sizeof(x));  // Size of an int variable
    printf("Size of y: %lu bytes\n", sizeof(y));  // Size of a double variable
//it just gives the size of data type that variable belongs.
    return 0;
}

//output:
Size of x: 4 bytes
Size of y: 8 bytes
```

---

### **Example 3: Using `sizeof()` with Arrays**

```c
#include <stdio.h>

int main() {
    int arr[5] = {1, 2, 3, 4, 5};
    
    printf("Size of array: %lu bytes\n", sizeof(arr));
    printf("Size of one element: %lu bytes\n", sizeof(arr[0]));
    printf("Total elements in array: %lu\n", sizeof(arr) / sizeof(arr[0]));

    return 0;
}
```

✅ **Output (on a typical system):**

```
Size of array: 20 bytes
Size of one element: 4 bytes
Total elements in array: 5
```

🔹 **Explanation:**

- `sizeof(arr)` gives the total memory occupied by the array (5 × 4 = 20 bytes).
- `sizeof(arr[0])` gives the size of one element (4 bytes).
- `sizeof(arr) / sizeof(arr[0])` calculates the **number of elements** in the array.

---

### **Example 4: Using `sizeof()` with Pointers**

```c
#include <stdio.h>

int main() {
    int *ptr;
    printf("Size of pointer: %lu bytes\n", sizeof(ptr));
    return 0;
}
```

✅ **Output (on a typical 64-bit system):**

```
Size of pointer: 8 bytes
```

🔹 **Explanation:**

- The size of a pointer **depends on the system architecture**:
    - **4 bytes on a 32-bit system**
    - **8 bytes on a 64-bit system**

---

### **Key Takeaways**

✅ `sizeof()` returns the size (in bytes) of a variable, type, or expression.  
✅ It works **at compile time**, except when used with dynamically allocated memory.  
✅ It is **not a function**, but an **operator** in C.  
✅ The size of a pointer depends on the **system architecture** (32-bit or 64-bit).  
✅ `sizeof(array) / sizeof(array[0])` helps find the **total number of elements** in an array.o

---
# Constants

Now lets talk about constants.
A **constant** is a value that does not change during the execution of a program. It is assigned a value once and cannot be modified.
It is the value itself that is acted upon, is stored, passed ,etc,.
In **C**, constants work based on their **type** and **scope**, but when you use them in function calls, they follow the same rules as normal variables of their type.
They are what we store in variables if we want to use them repeatedly, they are like numbers, but for programming. 

# **Types of Constants in C**

## **1) Literal Constants**: Directly written values (e.g., `5`, `3.14`, `'A'`).

A **literal constant** in C is a fixed value that appears directly in the source code. These values do not change during program execution and are not stored in variables unless assigned.

### **Types of Literal Constants**

#### 1.1 **Integer Constants**
These are whole numbers written without a decimal point.  
They can be:

- **Decimal (Base 10)** → `10`, `255`
- **Octal (Base 8, starts with `0`)** → `075` (equivalent to 61 in decimal)
- **Hexadecimal (Base 16, starts with `0x`)** → `0xFF` (equivalent to 255 in decimal)
- **Binary (Base 2, starts with `0b`, supported in C23)** → `0b1010` (equivalent to 10 in decimal)

**Example:**

```c
int a = 10;    // Decimal
int b = 075;   // Octal (61 in decimal)
int c = 0xFF;  // Hexadecimal (255 in decimal)
```

**Integer Suffixes:**

| Suffix | Meaning                 |
| ------ | ----------------------- |
| `U`    | Unsigned (`100U`)       |
| `L`    | Long (`100L`)           |
| `UL`   | Unsigned Long (`100UL`) |

#### 1.2 **Floating-Point Constants**

These are numbers with a decimal point or in scientific notation.

- **Decimal notation** → `3.14`, `0.5`
- **Exponential notation** → `2.5e3` (equivalent to `2500.0`)

**Example:**

```c
float pi = 3.14;        // Normal decimal
double large = 2.5e3;   // Exponential notation (2500.0)
```

**Floating-Point Suffixes:**

| Suffix | Meaning               |
| ------ | --------------------- |
| `F`    | Float (`3.14F`)       |
| `L`    | Long Double (`3.14L`) |

#### 1.3 **Character Constants**

A **single character enclosed in single quotes (`'` )** represents a character constant.

- **Example:** `'A'`, `'5'`, `'$'`
- Stored as an **integer (ASCII value)** in memory.

**Example:**

```c
char ch = 'A';  // Stored as 65 (ASCII value of 'A')
```

**Escape Sequences:**

| Escape Sequence | Meaning                                     |
| --------------- | ------------------------------------------- |
| `'\n'`          | Newline                                     |
| `'\t'`          | Tab                                         |
| `'\0'`          | Null character (end of a string)            |
| `'\''`          | Single quote                                |
| `'\x41'`        | Hexadecimal representation (ASCII 65 → 'A') |

#### 1.4 **String Constants**

A **sequence of characters enclosed in double quotes (`" "` )**.

**Example:**

```c
char str[] = "Hello, World!";
```

- Stored as an array of characters, ending with `'\0'` (null character).
- `"Hello"` is **different** from `'H'`.

### **Key Differences Between Constants & Variables**

| Feature | Literal Constant               | Variable            |
| ------- | ------------------------------ | ------------------- |
| Value   | Fixed                          | Can change          |
| Storage | No memory allocated separately | Stored in memory    |
| Usage   | Directly used in expressions   | Needs a declaration |

##  **2) `#define` Macros**: Preprocessor replacements (`#define PI 3.14`).

A **macro** is a preprocessor directive, primarily used in **C and C++**, that replaces a symbolic name with a defined value before compilation.

#### **Defining Macros**
```C/C++
#define MAX_VALUE 100  // Macro definition
```
This is not a variable; it's a **text replacement** that happens before the compiler processes the code.
**Macros can be large:**
```C
#define SKIP_SPACES(p, limit)
do { char *lim = (limit);
while(p < lim) {
if(*p++ != ' ') { p--; break; }
}
} while(0)
```
However, in most modern languages, macros are discouraged in favor of constants, functions, or templates.

## **3) `const` Variables**: Immutable variables (`const int x = 10;`)

`const variables` are those whose's values cannot be changed in the programme.
In The C Programming Language, they are declared as:
```C
const int a = 56;
```

## **4) `enum` Constants**: Named integer constants (`enum { RED = 1, GREEN = 2 }`)

This is a type of literal constant, but due to it's wide use-case, it is written specially.

These are named constants of integer type.
**Example:**

```c
enum Colors { RED = 1, GREEN = 2, BLUE = 3 };

int main() {
    int color = RED;  // Equivalent to 1
}
```

- **Enumeration Constants** → Named integer values (`enum Colors { RED = 1 }`).
---
---
# Constants passing by value/by reference

1. **Literal Constants and const Variables**
    - When passed to a function, they follow **pass by value**.
    - Example:
        
        ```c
        #include <stdio.h>
        
        void func(int x) {
            x = 20;  // This won't affect the original value
        }
        
        int main() {
            const int num = 10;
            func(num);  // Passed by value
            printf("%d\n", num);  // Still 10
            return 0;
        }
        ```
        
    - The function gets a **copy** of `num`, so modifying `x` inside `func()` does not affect `num`.
2. **Passing Pointers to Constants (Pass by Reference)**
    - If you use a pointer to a `const` value, you can achieve **pass by reference**.
    - Here, `x` is a **pointer to a constant integer**, meaning it cannot be modified.
- Example:
```c
void func(const int *x) {
	// *x = 20;  // Error: Cannot modify a const value
	printf("%d\n", *x);
}

int main() {
	const int num = 10;
	func(&num);  // Passed by reference
	return 0;
}
```
	
3. **Macros (`#define`) and `enum` Constants**
    
    - These are replaced at compile time, so they **do not exist in memory**.
    - Example:
        
        ```c
        #define VALUE 100
        
        void func(int x) {
            printf("%d\n", x);
        }
        
        int main() {
            func(VALUE);  // Compiler replaces VALUE with 100 before compiling
            return 0;
        }
        ```
        
    - This behaves like a literal value being passed by **value**.

### **Initializing Constants**

The method of initializing a constant depends on the programming language:

- **C / C++**
    
    ```c
    const int MAX_VALUE = 100;  // Constant integer
    ```
    
    In C++, you can also use `constexpr` for compile-time constants:
    
    ```cpp
    constexpr int MAX_VALUE = 100;
    ```
    
- **Python**
    
    ```python
    MAX_VALUE = 100  # No real constants, but conventionally written in uppercase
    ```
    
- **Java**
    
    ```java
    final int MAX_VALUE = 100;  // `final` makes it a constant
    ```
    
- **C#**
    
    ```csharp
    const int MAX_VALUE = 100;  // `const` for compile-time constants
    ```

---

### **Difference Between Macros and Constants**

| Feature          | Macros (`#define`)                            | Constants (`const`, `final`, etc.)                       |
| ---------------- | --------------------------------------------- | -------------------------------------------------------- |
| **Scope**        | No scope, replaced before compilation         | Has a defined scope                                      |
| **Type Safety**  | No type checking                              | Type-checked by the compiler                             |
| **Memory Usage** | No memory allocation (text substitution)      | Uses memory if stored in variables                       |
| **Debugging**    | Hard to debug, as errors show after expansion | Easier to debug                                          |
| **Performance**  | Faster in some cases (no memory access)       | Optimized by compiler, usually no performance difference |

---

### **What Happens if a Variable Has the Same Name as a Constant or Macro?**

- **Same Name as a Constant:**
    - In **C++**, this is an error.
    - In **C**, it is allowed but can be confusing.
```c
const int VALUE = 10;
int VALUE = 20;  // Compilation error in C++, allowed in C (but bad practice)
```
    
- **Same Name as a Macro:**
    The compiler replaces `VALUE` with `100`, which may cause unexpected behavior.
```c
#define VALUE 100
int VALUE = 10;  // Allowed, but VALUE will always be replaced by 100
```
    
- **Same Name for Macro and Constant:**
	The `#define VALUE 100` replaces all `VALUE` occurrences with `100`, potentially causing conflicts.
```c
#define VALUE 100
const int VALUE = 200;  // Compiler will likely issue an error or warning
```

### **Best Practices**

- Prefer `const`, `constexpr`, or `final` over `#define` for readability and type safety.
- Avoid using macros for defining constants.
- Use clear naming conventions (`UPPER_CASE` for constants/macros).

---
## **But what happens if I use same identifier for them?**

In **C**, if a variable has the same name as a `const` constant, the behavior depends on the scope in which they are defined.
***Scope means the function in which they are defined. For this, first check out function.***

### **Case 1: `const` and a Variable with the Same Name in Different Scopes**

```c
#include <stdio.h>

const int VALUE = 100;  // Global constant

int main() {
    int VALUE = 50;  // Local variable with the same name
    printf("%d\n", VALUE);  // Refers to the local variable (prints 50)
    return 0;
}
```

🔹 **What Happens?**

- The local variable `VALUE` **shadows** the global constant `VALUE`.
- Inside `main()`, `VALUE` refers to the local variable (prints `50`).
- Outside `main()`, `VALUE` refers to the global `const`.

---

### **Case 2: `const` and a Variable with the Same Name in the Same Scope**

```c
#include <stdio.h>

int main() {
    const int VALUE = 100;
    int VALUE = 50;  // ❌ Error: redefinition of 'VALUE' in the same scope
    return 0;
}
```

🔹 **What Happens?**

- In **C**, the compiler throws an **error**: `redeclaration of VALUE with no linkage`.
- A `const` variable still follows normal scoping rules.

---

### **Case 3: `#define` Macro and a Variable with the Same Name**

```c
#include <stdio.h>

#define VALUE 100  // Macro definition

int main() {
    int VALUE = 50;  // Allowed, but unexpected behavior
    printf("%d\n", VALUE);  // ❌ VALUE will be replaced by 100, causing an issue
    return 0;
}
```

🔹 **What Happens?**

- The **macro substitution happens first** before compilation.
- So, the compiler sees:
    
    ```c
    int 100 = 50;  // ❌ Syntax error: invalid variable name
    ```
    
- This causes a **compilation error**.

---

### **Case 4: `#define` and `const` with the Same Name**

```c
#include <stdio.h>

#define VALUE 100  // Macro definition
const int VALUE = 200;  // Constant definition

int main() {
    printf("%d\n", VALUE);  // ❓ What happens?
    return 0;
}
```

🔹 **What Happens?**

- **Preprocessor runs first**, so all occurrences of `VALUE` are replaced by `100`.
- The compiler sees:
    
    ```c
    const int 100 = 200;  // ❌ Syntax error
    ```
    
- This results in **a compilation error**.

---

### **Conclusion**

|Scenario|What Happens?|
|---|---|
|`const` and variable (different scopes)|The local variable **shadows** the `const`|
|`const` and variable (same scope)|**Compilation error** (redefinition)|
|`#define` macro and variable (same name)|**Preprocessor replaces name**, leading to possible syntax errors|
|`#define` and `const` (same name)|**Macro replaces all instances**, causing errors|

So, macros are **replaced before compilation**, while constants are **handled during compilation**. This is why naming conflicts with macros cause more unexpected behavior than with `const`.