**Expected to know:** [[Type System and Constants in 'C-sharp' (Data Types)]] [[TypeCasting]]
**Topics Covered:** 
**Tags:** [[TypeCasting]]

The C# compiler is very smart, and has a powerful feature within it called type inference. Just like what we saw in The C Programming language, something called [[TypeCasting]], we have Type Inference in C#.

**Type inference** in C# refers to the compiler's ability to automatically determine the type of a variable or expression based on the context in which it is used, rather than requiring the programmer to explicitly specify the type.

The compiler makes the life easier for us by determining the type of the variable/ literals by using the different hints available in the code.

A variable can be declared using the `var` keyword, instead of one of the other types we’ve talked about.
```csharp
var a = 56;
```
- When you declare a variable with `var`, the compiler infers its type from the expression on the right-hand side of the assignment.

---
# But what would be the use of mentioning data types anymore?

It’s important to point out that var does not mean anything can go in the variable.
It is not a catch-all. Types are important to C#, and var doesn’t erase all of that.

The real drawback to var is that it can reduce code clarity, which is a big deal.
	- The var message example doesn’t illustrate this very well, but there are plenty of scenarios where the compiler can easily infer the right type, but where the human programmer has a much harder time. 
	- Just because the compiler can infer the correct type does not necessarily mean you should make humans infer it as well.

---
# Typecasting in C-sharp
We can also determine the data type of stuff manually, convert them and use them as you wish.
The typecasting syntax is same as that in [[TypeCasting]].
So something like this would be valid:
```csharp
int a = 5;
int b = 2;

float = ((float)a)/b;
```

However, C# also provides with built-in methods for safe type conversion, especially useful when converting between unrelated types or parsing strings.

**Common Methods:**
    - `Convert.ToInt32()`
    - `Convert.ToDouble()`
    - `Convert.ToString()`
    - `Convert.ToBoolean()`
Example
```csharp
string str = "123";
int num = Convert.ToInt32(str); // Converts string to int
```
**When used:** Useful for converting user input or between types that don't have direct casting.


