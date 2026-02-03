**Expected to know:** [[Functions]] [[Syntax of C-sharp]] [[C-sharp Programming Language]](till now strictly)
**Topics Covered:**
**Tags:** [[Functions]], [[Methods(or Behavior)]]

![[Syntax of C-sharp#**Methods (Functions)**]]

Considering the C Programming Language is already done, the logic behind declaring function here is also same, but functions here are not exactly what we saw previously.
The Functions here are always a part of [[Class or Type]], and are called `methods`, even though the terms functions and methods are used exchangeably by many, but formally speaking there is a subtle difference between the two.

# Making a method
- A _method_ in C# is a block of code that performs a specific task and only runs when called.
- Methods help you organize code, promote reuse, and improve readability—just like functions in C.
- In C#, methods must be declared inside a class or struct.

## Basic Syntax
Basic syntax for making a method is:
```csharp
<AccessModifier> <ReturnType> <MethodName>(<ParameterList>) 
{ 
	// Method body 
}
```
There is only one thing that is new to us when it comes to making a method, i.e., [[Access Modifier]]. 
Also you might have seen the `static` keyword in every function when it is written inside our program class, that is because static means something of the class itself, it does not require one to create instance of the class to call the method.
## local method
When we declare a method inside another method inside a class, then that method is what we call a `local method`.
for example:
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            void printHello()
            {
                Console.WriteLine("Hello There");
            }
        }
    }
}
```

## Method Overloading and method group
Each method you create should get a unique name that describes what it does. However, sometimes you have two methods that do essentially the same job, just with slightly different parameters. 
Two methods can share a name as long as their parameter lists are different. Sharing a name is called **method overloading, or simply overloading**, and people call the various methods by the same name overloads.

The most common example is **WriteLine()** method.
```csharp
Console.WriteLine("Welcome to my evil lair!");
Console.WriteLine(42);
```

There is a version of WriteLine with a string parameter and one with an int parameter. When the compiler encounters a method call to an overloaded method, it must figure out which overload to use. 
This process is called overload resolution. It is a complex topic, full of nuance for tricky situations, but the simple version is that it can usually tell which one you want from the types and number of arguments. 
	- When we write `Console.WriteLine(42)`, the compiler picks the version of `WriteLine` with a single int parameter.

`Console.WriteLine` *has a total of 18 different overloads.* Most have a single parameter, each with a different type (string, int, float, bool, etc.), but there is also an overload with no parameters (`Console.WriteLine()`) that just moves to the following line. The set of all overloads of a method name is called a **method group**.

## Passing by Reference 
In C#, you can pass parameters by reference using the `ref`, `out`, or `in` keywords. This allows a method to modify the value of the argument in the calling scope, rather than working on a copy.

---
## How to Pass Parameters by Reference
### 1. Using the `ref` Keyword
- **Purpose:** Allows the method to read and modify the caller's variable.
- **Requirement:** The variable must be initialized before being passed.

**Syntax:**
```csharp
void MyMethod(ref int x) 
{     
	x = x + 10; 
} 

int num = 5; 
MyMethod(ref num); // num is now 15
```
Both the method definition and the call must use `ref`.

---
### 2. Using the `out` Keyword
- **Purpose:** Used when the method is expected to assign a value to the parameter.
- **Requirement:** The variable does not need to be initialized before being passed, but the method must assign it a value.

**Syntax:**
```csharp
void GetValues(out int x, out int y) 
{     
	x = 1;    
	y = 2; 
} 

int a, b; 

GetValues(out a, out b); 
// a is 1, b is 2
```
Both the method and call must use `out`.

---
### 3. Using the `in` Keyword
- **Purpose:** Passes the parameter by reference, but as read-only. The method cannot modify the value.
- **Requirement:** The variable must be initialized before being passed.

**Syntax:**
```csharp
void PrintValue(in int x) 
{     
	Console.WriteLine(x); 
} 

int num = 10; 

PrintValue(in num); // Prints 10, cannot modify num inside method
```
Both the method and call must use `in`.

---
### Example: Swapping Values with `ref`
```csharp
void Swap(ref int a, ref int b) 
{     
	int temp = a;    
	a = b;    
	b = temp; 
} 

int x = 100, y = 200; 
Swap(ref x, ref y); // x is now 200, y is now 100
```
The changes to `x` and `y` inside `Swap` are reflected outside because they are passed by reference

---
## Lambda Expressions (The Arrow Functions)
In C#, the "arrow function" refers to a _lambda expression_, which is a concise way to define anonymous functions using the `=>` operator (called the lambda operator).

Lambda expressions are widely used for in-line, short-lived functions, especially in LINQ queries, delegates, and event handling.

### Syntax of Lambda Expressions
There are two main forms of lambda expressions in C#:
- **Expression Lambda**: Contains a single expression, **implicitly returns its result.** Can also be used for methods, while being used for delegates and anonymous functions.
    ```csharp
    (parameters) => expression
	```
    **Example**:
    ```csharp
    x => x * x      
    // Squares the input 
    (x, y) => x + y 
    // Adds two numbers
	```
- **Statement Lambda**: Contains a block of statements, requires braces `{}` and **explicit `return` if a value is returned**. Is only used for delegates or anonymous functions.
    ```csharp
    (parameters) => { statements }
	```
    **Example**:
    ```csharp
    x => { return x * x; } 
    (x, y) => { int sum = x + y; return sum; }
	```

---
### How Lambda Expressions Work
- The left side of the `=>` operator defines the input parameters.
- The right side defines the body, which can be a single expression or a statement block.
Example:
```csharp
public int add(int a, int b)=> a+b;
public int multiply(int a) => {a = a*a; return a;} //not allowed, as cannot use code blocks in expression-bodied members.
```

---
# Delegates
- Lambda expressions can be assigned to delegate types like `Func<>` (returns a value) or `Action<>` (returns void).

**Examples:**
```csharp
Func<int, int> square = x => x * x;
Console.WriteLine(square(5)); // Output: 25 

Action<string> greet = name => { Console.WriteLine($"Hello {name}!"); }; 
greet("Alice"); // Output: Hello Alice!
```


## Func<> and Action<> in C#

**Func<>** and **Action<>** are generic delegates in C# that allow you to encapsulate methods (including lambda expressions) and pass them as parameters, return them from methods, or assign them to variables. They are foundational for functional programming patterns and are heavily used in LINQ and event-driven code[1](https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c)[2](https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top)[3](https://www.tutorialsteacher.com/csharp/csharp-func-delegate)[4](https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/)[5](https://www.bytehide.com/blog/func-keyword-csharp)[6](https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html).

---

## Func<>

- **Definition:** Represents a delegate (function pointer) that takes zero or more input parameters and always returns a value. The last generic type parameter specifies the return type; the preceding parameters specify the input types[7](https://learn.microsoft.com/en-us/dotnet/api/system.func-2?view=net-9.0)[1](https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c)[2](https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top)[3](https://www.tutorialsteacher.com/csharp/csharp-func-delegate)[5](https://www.bytehide.com/blog/func-keyword-csharp)[6](https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html).
    
- **Syntax:**
    
    csharp
    
    `Func<T1, T2, ..., TResult> // TResult is the return type`
    
- **Examples:**
    
    csharp
    
    `Func<int, int, int> add = (a, b) => a + b; // Takes two ints, returns an int int sum = add(3, 4); // sum = 7 Func<string, string> toUpper = s => s.ToUpper(); string result = toUpper("hello"); // result = "HELLO" Func<int> getRandom = () => new Random().Next(1, 100); int number = getRandom(); // returns a random number between 1 and 100`
    
- **Parameter Limit:** Can have up to 16 parameters (including the return type)[5](https://www.bytehide.com/blog/func-keyword-csharp)[6](https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html).
    

---

## Action<>

- **Definition:** Represents a delegate that takes zero or more input parameters and returns no value (void). All generic type parameters specify input types; there is no return type[1](https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c)[2](https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top)[4](https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/).
    
- **Syntax:**
    
    csharp
    
    `Action<T1, T2, ...> // No return type`
    
- **Examples:**
    
    csharp
    
    `Action<string> greet = name => Console.WriteLine($"Hello, {name}!"); greet("Alice"); // Output: Hello, Alice! Action<int, int> printSum = (a, b) => Console.WriteLine(a + b); printSum(2, 3); // Output: 5 Action sayHello = () => Console.WriteLine("Hello!"); sayHello(); // Output: Hello!`
    
- **Parameter Limit:** Can have up to 16 parameters[4](https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/).
    

---

## Key Differences

|Feature|Func<>|Action<>|
|---|---|---|
|Return Value|Yes (last type parameter)|No (void)|
|Parameters|0 to 15 input parameters|0 to 16 input parameters|
|Use Case|When a result is needed|When no result is needed|

---

## Usage Context

- **Func<>** is used when you need to pass a function that returns a value (e.g., selecting, transforming, or computing data).
    
- **Action<>** is used when you need to pass a function that performs an action but does not return a value (e.g., logging, printing, or modifying state)[1](https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c)[2](https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top)[4](https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/)[5](https://www.bytehide.com/blog/func-keyword-csharp)[6](https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html).
    

---

## Practical Example

csharp

`// Using Func<> Func<int, int, int> multiply = (x, y) => x * y; int product = multiply(3, 4); // product = 12 // Using Action<> Action<string> print = message => Console.WriteLine(message); print("Hello, world!"); // Output: Hello, world!`

---

**Summary:**

- **Func<>**: Use when you need a delegate that returns a value.
    
- **Action<>**: Use when you need a delegate that does not return a value[1](https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c)[2](https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top)[3](https://www.tutorialsteacher.com/csharp/csharp-func-delegate)[4](https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/)[5](https://www.bytehide.com/blog/func-keyword-csharp)[6](https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html).
    

1. [https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c](https://medium.com/@interviewer.live/func-and-action-in-c-a-complete-guide-dfe8cf31581c)
2. [https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top](https://dev.to/canro91/what-the-func-action-4c7g?comments_sort=top)
3. [https://www.tutorialsteacher.com/csharp/csharp-func-delegate](https://www.tutorialsteacher.com/csharp/csharp-func-delegate)
4. [https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/](https://www.bomberbot.com/c/action-and-func-delegates-in-c-explained-with-examples/)
5. [https://www.bytehide.com/blog/func-keyword-csharp](https://www.bytehide.com/blog/func-keyword-csharp)
6. [https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html](https://www.growingwiththeweb.com/2012/08/func-and-action-basics-in-c.html)
7. [https://learn.microsoft.com/en-us/dotnet/api/system.func-2?view=net-9.0](https://learn.microsoft.com/en-us/dotnet/api/system.func-2?view=net-9.0)
8. [https://www.c-sharpcorner.com/article/func-delegate-in-c-sharp/](https://www.c-sharpcorner.com/article/func-delegate-in-c-sharp/)
9. [https://learn.microsoft.com/en-us/dotnet/api/system.func-1?view=net-9.0](https://learn.microsoft.com/en-us/dotnet/api/system.func-1?view=net-9.0)
10. [https://canro91.github.io/2019/03/22/WhatTheFuncAction/](https://canro91.github.io/2019/03/22/WhatTheFuncAction/)