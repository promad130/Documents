**Expected to know:** [[Introduction to Programming]],[[TypeCasting]],[[Input&Output and placeholders]], [[Data types in C-Sharp#Typecasting syntax]]
**Topics Covered:**
**Tags:** [[Input&Output and placeholders]], [[TypeCasting]]

Introduction and all, 

---
# Basic Input/Output Functions

C# provides several straightforward commands for handling input and output in console applications. These commands are primarily accessed through the `Console` class in the `System` namespace.

---
## **Output Commands**
### Writing to console
#### **`Console.WriteLine()`

Prints text or variables to the console and moves the cursor to the next line.
- Example:
```csharp
Console.WriteLine("Hello World!"); Console.WriteLine(3 + 3); // Outputs: 6
```
Multiple `WriteLine()` calls print each output on a new line.

#### **`Console.Write()`

- Prints text or variables to the console but does not move the cursor to a new line.
- Example:
```csharp
Console.Write("Hello "); Console.Write("World!"); // Output: Hello World!
```
- Useful for printing multiple items on the same line.

We can mix up the output by `adding` stuff up in the function parameter!
For example:
```csharp
using System;
using System.Collections.Generic;

using namespace Hello
{
    class Prac
    {
        static void Main(string[] args)
        {
	        //Changes the title of the console
            Console.title = "My First Programe"; 
            Console.ForegroundColor = ConsoleColor.Red; //changes text color
            Console.WindowHeight = 50; //changes height of console

            Console.WriteLine("Hello World");
            string a = Console.ReadLine(); //input cmd, covered later
            Console.WriteLine("Hi!, you wrote:" + a);
            Console.Readkey();
        }
    }
}
```

---

## **Input Commands**

### **`Console.ReadLine()`**
Reads an entire line of input from the user as a string, i.e., stops reading at a newline character, and the newline character itself (the Enter key press) is not included in the returned string; it is consumed and discarded by the method, hence removed from the input stream.
- Example:
```csharp
Console.Write("Enter your name: "); 
string name = Console.ReadLine(); 
Console.WriteLine("Hello, " + name);
```

The `ReadLine()` takes input in form of string.
To read numbers, input must be converted, hence typecasted from string:
```csharp
Console.Write("Enter your age: "); 
int age = Convert.ToInt32(Console.ReadLine());
```
If non-numeric input is entered where a number is expected, an exception will occur.

### **`Console.Read()`**
Reads the next character from the input stream and returns its ASCII integer value.

When `Console.Read()` encounters a newline character, it reads and returns its ASCII value (10 for `\n`). 
The newline character is not skipped or discarded automatically; it is treated like any other character and must be handled in your code if you want to ignore it. 

If you want to skip over the newline, you need to explicitly check for it and handle it accordingly-for example, by reading and discarding it if it appears after the intended input.

Example:
```csharp
int asciiValue = Console.Read(); 
Console.WriteLine("ASCII Value = " + asciiValue);
```
Reads only one character, not a full line.

### **`Console.ReadKey()`**
Reads the next key pressed by the user, immediately (without waiting for Enter), and returns a `ConsoleKeyInfo` object.

Example:
```csharp
Console.WriteLine("Press any key to continue..."); 
Console.ReadKey();
```

Useful for pausing the program until a key is pressed, or for reading single key input (like a menu selection).

---
# More functions