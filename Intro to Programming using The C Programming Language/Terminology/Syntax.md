**Expected to know:** [[Introduction to Programming]]
**Topics Covered:** What is a syntax, Syntax common to all, syntax of The C programming Language
**Tags:** [[The C Programming Language]] [[Introduction to Programming]]

(Inprove this as you learn about how to make your own compiler and interpreter)
# What is Syntax?

**Syntax** means the set of rules that a programming language follows when a code is written in that language. 
It is like the grammar of that particular programming language.

Every programming language has various elements, like literals, variables, identifiers(keywords, etc,.) comments, libraries, etc,. 
Concept is same everywhere, but the way it is written or interpreted might differ. 

Using The C Programming Language as a starting point, we begin understanding a syntax style that you would find surprisingly common!

---
# Syntax in The C Programming Language 

Let's dive into the basic syntax of the C programming language. Even if you've programmed before, C has its own way of doing things, so paying attention to these details is crucial.

## **1. "Hello, World!" - Your First C Program:(aka, the structure of a programme in this language)**

The classic starting point is the "Hello, World!" program. Here it is:

```c
#include <stdio.h>

int main() {
    printf("Hello, World!\n");
    return 0;
}
```

Here, stuff like `int`, `return`, `printf()`, `main()` are all keywords predefined and are provided by the programming language.

Let's break it down line by line:

- `#include <stdio.h>`: 
	- This line is a _preprocessor directive_. (refer [[The C Implementation]])
	- It tells the C compiler to include the contents of the `stdio.h` file (standard input/output header). This file contains declarations for standard input/output functions like `printf`. Think of it as importing or including a library of useful functions.
    
- `int main() { ... }`: 
	- This defines the `main` function. 
	- Every C program _must_ have a `main` function. 
	- This is where the program's execution begins. `int` indicates that the `main` function returns an integer value (usually 0 to indicate success).
    
- `printf("Hello, World!\n");`: 
	- This line calls the `printf` function to print the text "Hello, World!" to the console. 
	- `\n` is an _escape sequence_ that represents a newline character (moves the cursor to the next line).
    
- `return 0;`: 
	- This line returns the integer value 0 from the `main` function. 
	- This indicates that the program executed successfully.
    

## **2. Comments:**

Comments are essential for explaining your code. C has two types of comments:

- **Single-line comments:** 
	- Start with `//`. Anything after `//` on that line is ignored by the compiler.
	
```c
// This is a single-line comment.
int age = 25; // This is also a comment at the end of a line.
```
	
- **Multi-line comments:** 
	- Start with `/*` and end with `*/`. Everything between these markers is ignored, even across multiple lines.

```c
/* This is a
   multi-line comment. */
int x = 10;
```


## **3. Statements and Semicolons:**

A _statement_ in C is a complete instruction. Almost all C statements end with a semicolon (`;`). **Forgetting a semicolon is a very common beginner mistake!**

```c
int age = 30;  // This is a statement.
printf("Hello!\n"); // This is another statement.
```

## **4. Code Blocks (Braces):**

Code blocks are groups of statements enclosed in curly braces (`{` and `}`). They are used to group related statements together, for example, within a function or a control flow statement (like `if` or `for`).

```c
int main() {  // Start of a code block
    int x = 10;
    int y = 20;
    int sum = x + y;
    printf("Sum: %d\n", sum);
    return 0;
}  // End of the code block
```

## **5. Whitespace:**

C is relatively flexible with whitespace (spaces, tabs, and newlines). 

You can use whitespace to make your code more readable, but it's generally not required for the code to compile correctly (except in some specific situations with the preprocessor). Consistent indentation is highly recommended to improve readability.

```C
int main() { // Valid, but not recommended
    int x=10;int y=20; // Valid, but very hard to read
    int sum = x + y; // Much better!
    return 0;
}
```

## 6. Case Sensitivity:

C is case-sensitive. This means that `myVariable` and `MyVariable` are treated as two different variables.

## 7. Header Files:

As mentioned earlier, header files (like `stdio.h`) contain declarations for functions and other code that you can use in your program. You include them using the `#include` directive.

**Example (combining concepts):**

```C
#include <stdio.h> // Include standard input/output library

int main() { // Main function - execution starts here
    int age = 25; // Declare and initialize an integer variable
    // printf("Age: %d\n", age); // This line is commented out (single-line comment)
    printf("Age: %d\n", age); /* Print the value of age to the console.
                                 This is a multi-line comment. */
    return 0; // Return 0 to indicate successful execution
}
```

## 8. Expression:
An expression in a programming language is a syntactic construct that combines literals, variables, operators, and function or method calls to produce a value when evaluated.
The key characteristic of an expression is that it always results in a value, which could be a number, string, boolean, or other data type, depending on the context and the programming language.

### Every expression in The C Programming Language is a statement:
>Any expression in The C Programming Language is a statement

#### Understanding Statements and Expressions in C
In C, the terms _statement_ and _expression_ have specific meanings, but their relationship is nuanced:
- An **expression** is a combination of variables, constants, operators, and function calls that the compiler can evaluate to produce a value. Examples include `a + b`, `x = y + 2`, or `++count`.
- A **statement** is a complete instruction for the computer to execute, usually ending with a semicolon. Statements include expression statements, compound statements (blocks), control statements (like `if`, `for`), and more

#### The Expression Statement
The most common type of statement in C is the _expression statement_, which is simply an expression followed by a semicolon. For example:
```c
x = y + 2; 
++count;
```
The execution of such a statement causes the associated expression to be evaluated[2](https://farside.ph.utexas.edu/teaching/329/lectures/node11.html)[8](https://learn.microsoft.com/en-us/cpp/c-language/overview-of-c-statements?view=msvc-170). In fact, _any valid expression can be made into a statement by appending a semicolon_—this is called an expression statement.

**Blurring the Line: Why "Some Statement is Also an Expression"**
The phrase "any statement in C is also an expression" is a simplification. What it really means is:
- Most statements in C are _expression statements_, which are just expressions with a semicolon.
- Many expressions in C can cause side effects (e.g., assignments, increments), making them behave like commands or statements.
- This design blurs the distinction between expressions (which compute values) and statements (which perform actions), because in C, _expressions can have side effects and be used as statements_.

#### Example: Assignment as Both Expression and Statement
```c
int rc; 
if (rc = call_some_fn()) 
{     
	fprintf(stderr, "Failed with return code %d\n", rc);    
	exit(1); 
}
```
Here, `rc = call_some_fn()` is an expression (it assigns and returns a value), but when used as part of a statement (with a semicolon or in a control structure), it acts as a statement.

#### Important Clarification
- _Not every statement in C is an expression_: Some statements, like compound statements (`{ ... }`), control statements (`if`, `for`, `while`), and jump statements (`break`, `continue`, `goto`), are not expressions themselves.    
- _But_: Any expression can be made into a statement by adding a semicolon, and many statements in C are in fact expression statements.

> "Any expression in C (with or without any side effect) can be converted to a statement by putting a semicolon at the end. These are called expression statements. This blurs the distinction between an expression and a command in this language."
