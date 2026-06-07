**Expected to know:** [[Object Oriented Programming]], [[Syntax]] 
**Topics Covered:**
**Tags:** [[Syntax]]

# C# Syntax Overview
C# is a modern, object-oriented language with a clear and structured syntax. If you already know C, much of the syntax will feel familiar, but C# introduces new constructs for classes, objects, and other features essential for OOP and application development.

The best way to learn is to build and learn, so lets create our first program.

---
## **Our First Program**
(Add various ways of creating a C# project and all using different IDE's later, not now, later when u have more confi abt how to do that stuff)

Every C# program consists of at least one class, and the execution starts from the `Main` method. Here’s a simple example:

```csharp
using System; 

namespace HelloWorld 
{     
	class Program    
	{        
		static void Main(string[] args)        
		{            
			Console.WriteLine("Hello World!");        
		}    
	} 
}
```
- `using System;` allows access to classes in the `System` namespace, such as `Console`.
- `namespace` is used to organize code. Namespaces are the highest level grouping of code
- `class` defines a blueprint for objects and contains data and methods.  
- `static void Main(string[] args)` is the entry point of the program; code execution starts here. It is like the `main()` function (refer to The C Programming Language) of C#.
- `Console.WriteLine("Hello World!");` prints text to the console.
- Every statement ends with a semicolon `;`.
- Curly braces `{}` define code blocks.
Now here, between the `Console` and `WriteLine` we have used something called a `period(.) character`. This is called a *member access operator* or the *dot operator*.

In object Oriented programming there is a hierarchy of things, unlike what we saw in `The C Programming Language`, here, we have things like classes, objects, methods, namespaces, etc,. Although those things are a topic that we will discuss later when we do Object Oriented Programming(OOPs) itself, but knowing the hierarchy will help us making things more easy to understand.
Now here, the keyword `Console` is a class and the keyword `WriteLine` is a method that exists inside that class.

And similarly, we have a collection of these so-called `classes` inside something that we call `namespace`. The keyword `using` does exactly, that.
By writing `using System;`, we are telling the compiler to use `System` namespace. And we store all these namespaces inside something called a `Base Class Library`.
This looks like:
![[Pasted image 20250525201237.png]]

---
## **Key Syntax Rules**
- **Case Sensitivity:** C# is case-sensitive. For example, `MyClass` and `myclass` are different identifiers
- **Statements:** Each statement ends with a semicolon `;`.
- **Code Blocks:** Curly braces `{}` group statements into blocks for classes, methods, loops, and conditionals.
- **Indentation:** Whitespace and indentation are ignored by the compiler but are important for readability.

---
## **Variables**

```csharp
int age = 30; 
const double Pi = 3.14; 
string name = "John";
```

- Variables store data; constants hold values that cannot change.

---
## Data types
C# data types specify what kind of data a variable can hold. They are mainly categorized into **Value Types** and **Reference Types**.
### **Value Types**

- Store actual data directly.
- Examples:
    - `int`: 32-bit integer (e.g., `100`)
    - `long`: 64-bit integer (e.g., `15000000000L`)
    - `float`: 32-bit floating-point (e.g., `10.2f`)
    - `double`: 64-bit floating-point (e.g., `85.5`)
    - `decimal`: High-precision decimal (e.g., `389.5m`)
    - `char`: Single Unicode character (e.g., `'A'`)
    - `bool`: Boolean value (`true` or `false`)
    - `byte`, `short`, `ushort`, `uint`, `ulong`, `sbyte`: Other numeric types with different sizes and ranges

### **Reference Types**
- Store a reference (address) to the actual data.
- Examples:
    - `string`: Sequence of characters (e.g., "Hello World")
	    - Native `string` type (immutable), easy concatenation, and powerful features like string interpolation
    - `object`: Base type for all other types
    - `Arrays`: e.g., `int[] numbers = {1, 2, 3};`
    - `Classes`, `interfaces`, `delegates`

| Category        | Examples                        | Usage Example          |
| --------------- | ------------------------------- | ---------------------- |
| Value Types     | int, float, double, char, bool  | int age = 20;          |
| Reference Types | string, object, arrays, classes | string name = "Alice"; |

---
## Control Structures

### Conditional Statements:

```csharp
if (age >= 18) 
{     
	Console.WriteLine("You are an adult."); 
} else {     
	Console.WriteLine("You are a minor."); 
}
```
`if`, `else`, and `switch` are used for decision-making.

### Loops:

```csharp
for (int i = 0; i < 5; i++) 
{     
	Console.WriteLine(i); 
} 

while (condition) {     
	// Repeated code 
} 

do {     
	// Code runs at least once 
} while (anotherCondition);
```
- C# supports `for`, `while`, `do-while`, and `foreach` loops.

---
## **Methods (Functions)**

```csharp
int Add(int a, int b) 
{     
	return a + b; 
} 

void SayHello(string name) 
{     
	Console.WriteLine($"Hello, {name}!"); 
}
```
- Methods are defined inside classes and can return values or be `void`.

In other programming languages, methods are sometimes called **functions**, **procedures**, or **subroutines**.

---
## Comments 
The comments in C# has the same syntax as that in The C Programming Language.

---
# Basic Terminology

## Members
When one thing is contained in another, it is said to be a **member** of it.
So the **Program class** is a member of the namespace, and the **Main method** is a member of the **Program class**.
