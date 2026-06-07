***Prerequisite, it is suggest that you cover [[Introduction to Programming]] first, and then come here.***
# An Introduction
C# (pronounced "C-Sharp") is a modern, high-level, object-oriented programming language developed by Microsoft. If you already know C, you'll find C# familiar in many ways, but it introduces powerful new concepts—especially for object-oriented programming (OOP) and application development across a wide range of domains, including game development.

C# works on the basis of .NET Framework which enables it to be available for a wide variety of uses like making games, apps, websites, etc.

C# provides an excellent balance between ease of use and power. There are other languages that provide less power and are easier to use (like Java) and others that provide more power, giving up some of its simplicity (like C++). 

Because of the balance it strikes, it is the perfect language for nearly everything that you will want to do, so it’s a great language to learn, whether it’s your first or your tenth.
# Key Features of 'C#'

1. **Object-Oriented**: C# is fully object-oriented, supporting concepts like encapsulation, inheritance, and polymorphism.
2. **Type-Safe**: It is a strongly-typed language, which helps prevent errors and improves code reliability.
3. **Component-Oriented**: C# supports component-based software development.
4. **Garbage Collection**: Automatic memory management reduces the risk of memory leaks.
5. **Cross-Platform**: With .NET Core and .NET 5+, C# can be used to develop applications for Windows, macOS, Linux, iOS, and Android.

---
# How C# Differs from C
While C is a procedural language focused on functions and manual memory management, C# is designed around objects and classes, with automatic memory management (garbage collection) and a rich standard library. Here are some key differences:

| Feature           | C                           | C#                                       |
| ----------------- | --------------------------- | ---------------------------------------- |
| Paradigm          | Procedural                  | Object-oriented (OOP), multi-paradigm    |
| Memory Management | Manual (malloc/free)        | Automatic (garbage collector)            |
| Syntax Similarity | C family                    | C family (similar to C, C++, Java)       |
| Platform          | Cross-platform (native)     | .NET runtime (cross-platform via .NET)   |
| Pointers          | Extensive use               | Restricted, safer by default             |
| Application Types | System, embedded, CLI tools | Desktop, web, mobile, games, cloud, etc. |
| OOP Features      | None                        | Classes, inheritance, interfaces, etc.   |

---
# How is C# executed?
C# relies heavily on something called the .NET Platform. It is also commonly also called the .NET Framework.
## .Net Platform
The .NET Platform is vast, with many components, but two stand out as the most central.
### 1) The First Part
The first part is the **Common Language Runtime**, often abbreviated as the ***CLR***.

When you launch your EXE file, the CLR will start up and begin taking your code and translating it into the optimal binary instructions for the physical computer that it is running on, and your code comes to life.

So whenever we compile the C# programming language, it is first converted into a CIL also called as Common Intermediate Language, and after that it is then converted to machine code via .NET CLR .

### 2) The Second Part
The second major component of the .NET Platform is the **.NET Standard Library**.

The Standard Library is frequently called the **Base Class Library**. The Standard Library is a massive collection of code that you can reuse within your own programs to accelerate the development of whatever you are working on.

## The **App Models**
Built on top of the .NET Standard Library is a collection of app models. An app model is another large library designed for a specific type of application. This includes things like WPF and Windows Forms for GUI applications, ASP.NET for web development, and Xamarin for iOS and Android development. 

Game frameworks or engines like MonoGame and Unity could also be considered app models, though these are not maintained directly by Microsoft.

![[Pasted image 20250525193021.png]]

[History of C#]() 

---
# Getting started
As we get started, it is worth defining a few important terms that you’ll be seeing throughout. 

In the world of C#, you’ll commonly see the words **solution**, **project**, and **assembly**, and it is worth taking the time upfront to explain what they are.
These three words describe the code that you’re building in different ways. 
- **Project:**
	- We’ll start with a project. 
	- A project is simply a collection of source code and resource files that will all eventually get built into the same executable program. 
	- A project also has additional information telling the compiler how to build it.
- **Assembly:**
	- When compiled, a project becomes an assembly.
	- In nearly all cases, a single project will become a single assembly. 
	- An assembly shows up in the form of an EXE file or a DLL file.
		- EXE:
			- A *[[process assembly]]* appears as an EXE file.
			- It is a complete program, and has a starting point defined, which the computer knows to run when you start up the EXE file.
		- DLL:
			- A *[[library assembly]]* appears as a DLL file.
			- A DLL file does not have a specific starting point defined. 
			- Instead, it contains code that other programs can access and reuse on the fly, i..e, during the runtime.
- **Solution:**
	- Finally, a solution will combine multiple projects together to accomplish a complete task or form a complete program.
	- Solutions will also contain information about how the different projects should be connected to each other.
	- While solutions can contain many projects, most simple programs (including nearly everything we do in this book) will only need one. Even many large programs can get away with only a single project.

---
# Syntax of C# 
Although it might seem that all programming languages have kind of similar syntax, but C# being an Object Oriented Programming language does have many things that are different.

Let's have a look at "[[Syntax of C-sharp]]".

---
# Elements of the language:
Now that the syntax is covered and we know what and how C# is executed( even though a brief idea), time to look at the elements of this language.

[[Variables in C-Sharp]] 
[[Type System and Constants in 'C-sharp' (Data Types)]]
[[Type inference in C-sharp]]
[[Operators and Operations in C-sharp]] 
[[Input & Output in C-sharp]]

Now lets take some break and have a [math](Mathematical%20Constants%20in%20C-sharp).
With that covered, time to look at the [[Console Class]] that we have been using till now.

[[Overflow and Underflow in C-sharp]]
[[Control Flow Statements in C-sharp]]
[[Data Structures in C-sharp]] 
[[Methods in c-sharp]] 

Lets have a [tea](XML%20Documentation%20for%20Source%20Code).

C# programming Language also offers Memory management. 
As we know from [[Memory Allocation]] and [[Pointers and Working with Memory Allocation]] that memory management is important, otherwise things can go south real quick. At this point, after having a solid idea about the basic elements of this programming language that can help us get started you might be thinking, “I haven’t done anything to clean up my memory yet. Was
I doing something wrong?” But the answer is no. The environment that C# programs run in solves most of this problem for you so that you don’t have to worry about it. But in doing so, important decisions have been made that impact how you work and how the language itself must work, so it is important to understand how it works.

## Memory Management in C#:
On modern computers, the operating system is the great gatekeeper of memory. The operating system gives your programs memory to use when they start up, and your program can negotiate with the operating system for more. The operating system doesn’t care how you use your memory, as long as you don’t attempt to use another program’s memory. Each program and programming language can do as it sees fit. Theoretically, every programming language could handle memory differently. But two models are almost universal: the stack and the heap. C#, like many other languages, uses both.

### The Stack
![[Memory Allocation#2) Stack]]
The stack is a model that allows us to allocate space for each method we call and then free or deallocate the memory when we are done using it as the flow of execution moves from one method to another.

So lets assume that we have a programme in which `static void main(string[] args){}` has two variables, one of line 1, and other on line 2, and on next line we have a method(`method1()`).
When we run the programme, the stack initially looks like this:
![[Pasted image 20250529010556.png]]
Four bytes are reserved for x because it is an int. Eight bytes are reserved for y because it is a double. So 12 bytes total are set aside for Main in a bundle. The image above shows them grouped and labeled on the side with Main. A collection of data needed for a single method is called a **stack frame**.

In the image above, the dashed line marks an important location in the stack. Everything beneath it is currently allocated for specific variables in specific frames on the stack.
Everything above it is not being used and contains garbage. There is memory there, and that memory’s bits and bytes are set to something, but it doesn’t mean anything to the program.

Now we initialize those variables, and invoke **`method1()`**, and the memory for it is allocated:
![[Pasted image 20250529010912.png]]
The main's frame is buried beneath the one for **`method1()`**. The specifics of how exactly that “Main line 3” part is done is a bit too low level for this book, but the concept is correct; we simply record the right information on the stack and use it when we get done in **`method1()`**.

Now the local variables `a` and `b` are allocated and the method is invoked, after it is invoked the memory allocated to it is freed, even if the method is used again in the future, as it doesn't matter to compiler's current state:
![[Pasted image 20250529011209.png]]
All this is managed by the programming language automatically, so nothing to worry about.

### The heap
![[Memory Allocation#1) Heap]]
When we need memory that can be created in arbitrary sizes, we ditch the stack and find another spot. This other spot is called the heap. The heap is not as structured as the stack.

Because a string’s size is dynamic—some require more bytes than others—we must find a spot for this data on the heap instead. We search for an open location with enough space for five characters and allocate it for the string.
That can be anywhere within the heap, which means its location isn’t predictable or computable. To keep track of items placed on the heap, we capture a **reference** to the new object when we create it. 
So for something like this:
```csharp
using System;
using System.Collections.Genaric;

namespace Practice1
{
	class Program
	{
		static void main(string[] args)
		{
			int x = 3;
			string a = "Hello";
		}
	}
}
```
Knowing how the stack works, we might have assumed memory looks something like this:
![[Pasted image 20250529012841.png]]
But that is not right, as string is dynamic, it should be stored on the heap, hence the memory looks like this where `b` acts as a reference to the memory of the string on the heap:
![[Pasted image 20250529012949.png]]
![[Pasted image 20250529013055.png]]

#### Cleaning Up the Heap
Cleaning up the heap is important, and it does not store stuff in any organised manner, so it might seem that cleaning it up is tricky, but it is not!!!
Cleaning it up it pretty simple as we have seen in [[Memory Allocation]]. 
But the hard part about cleaning up the heap is the timing, i.e., when to clean it up. Getting it wrong has dire consequences.
##### Getting it late:
If our program uses memory and fails to clean it up, it cannot be reused by something else. The memory is unused as it stands but cannot be put back into useful service either. This is called a **memory leak**. If a program does not use excessive memory or only runs for a few seconds, this isn’t the end of the world. The program will use more and more memory as it runs, but it will finish before it becomes a problem. On the other hand, memory-intensive or long-running programs will eventually consume all memory on the computer, slowing everything down for a while before bringing things crashing down around it.
##### Getting it early:
Additionally, if we return memory to the heap too early, some part of our program is still using it for what it once was. 
For a time, the rest of the system may just see it as unused memory, and the consequences aren’t high. But eventually, the heap will reuse that section of memory for a second item, and two parts of our program will be using the same memory for two different things. 
This is called a **dangling reference** or a **dangling pointer**. Part of your program unknowingly uses memory that was already given back to the heap.

#### Memory Management in C#:
C# takes the burden of tracking and cleaning up heap objects off of programmers. This task falls on the runtime that your C# programs run within. This approach is called **automatic memory management** or **garbage collection**.
For example:
```csharp
int[] numbers = new int[10];
numbers = new int[5];
```
Two arrays are created on the heap as this code runs, one on each line. After the first line runs, the first array (of length 10) exists and is still usable by the program through the numbers variable. After the second line runs, the second array (of length 5) exists and is usable by the program, but nothing has access to the original 10-element array. It is ready to be cleaned up.

Within the .NET runtime, an element called the garbage collector (sometimes abbreviated to the GC) periodically wakes up and scans the system for anything the program can no longer reach. The search starts from a set of root objects that includes any variable on the stack. For any item still on the heap that is no longer reachable, it recognizes it as garbage and returns that space in memory to the heap for reuse.
In our case, the original 10 elemental array was unreachable.

The garbage collector has a lot of complexity, nuance, and optimization, and we have skipped over many fascinating details with that description, but that is the core of it.

### Value Types and Reference types in C#:
With this understanding of heap and stack, we can now properly define:
- **Value Types**: The types whose value is stored in the stack. They have a known and fixed size.
- **Reference Types**: Variables whose types are reference types hold only a reference to the data, and the data is placed somewhere on the heap. Two pieces of the same type of data are not guaranteed to have the same size in memory, though the references themselves are all the same size.
The way C# handles memory is quite different from that in the other languages.

### Value Semantics and Reference Semantics
There is a consequence of Value types and Reference types. Whenever we use any comparison operator, for example `==`, it equates each and every bit for the equality.
So for example for:
```csharp
int a = 5;
int b = 5;
int[] a1 = new int[3] {1,2,3};
int[] b1 = new int[3] {1,2,3};
```
If we do this:
```csharp
bool check1 = (a == b);
bool check2 = (a1 == b1);
```
Then the output will be `true` for `check1` but `false` for `check2`, this is because `a` and `b` are value type, so stored in the stack itself, but the other two are reference type, so their reference is stored in the memory, which is not same, never same. 

### A BOSS BATTLE
**The Uncoded One’s airship,** the **Manticore**, has begun an all-out attack on the **city of Consolas**. It must be destroyed, or the city will fall. Only by combining Mylara’s prototype, Skorin’s cannon, and your programming skills will you have a chance to win this fight. You must build a program that allows one user—the pilot of the Manticore—to enter the airship’s range from the city and a second user—the city’s defenses—to attempt to find what distance the airship is at and destroy it before it can lay waste to the town.
The first user begins by secretly establishing how far the Manticore is from the city, in the range 0 to 100. The program then allows a second player to repeatedly attempt to destroy the airship by picking the range to target until either the city of Consolas or the Manticore is destroyed. In each attempt, the player is told if they overshot (too far), fell short (not far enough), or hit the Manticore. The damage dealt to the Manticore depends on the turn number. For most turns, 1 point of damage is dealt. But if the turn number is a multiple of 3, a fire blast deals 3 points of damage; a multiple of 5, an electric blast deals 3 points of damage, and if it is a multiple of both 3 and 5, a mighty fire-electric blast deals 10 points of damage. The Manticore is destroyed after taking 10 points of damage.

But if the Manticore does not take any damage, IT DESTROYS THE CITY by 1 point. The city can only take 15 points of damage before being annihilated.

Before a round begins, the user should see the current status: the current round number, the city’s health, and the Manticore’s health.
A Sample would be:

```text
Player 1, how far away from the city do you want to station the manticore?(Between 1 to 100): 

Player 2 now it is your turn
--------------------------------------------------------------------------------
STATUS:		Round: 1	Manticore: 10/10	City: 15/15
The cannon is expected to deal 1 damage this round
Enter the desired cannon range: 56
That round OVERSHOT the target.
--------------------------------------------------------------------------------
STATUS:		Round: 2	Manticore: 10/10	City: 14/15
The cannon is expected to deal 1 damage this round
Enter the desired cannon range: 40
That round FELL SHORT of the target
--------------------------------------------------------------------------------
STATUS:		Round: 3	Manticore: 10/10	City: 13/15
The cannon is expected to deal 3 damage this round
Enter the desired cannon range: 50
That round OVERSHOT the target.
--------------------------------------------------------------------------------
STATUS:		Round: 4	Manticore: 10/10	City: 12/15
The cannon is expected to deal 1 damage this round
Enter the desired cannon range: 45
That round was a DIRECT HIT!
--------------------------------------------------------------------------------
STATUS:		Round: 5	Manticore: 9/10	City: 12/15
The cannon is expected to deal 3 damage this round
Enter the desired cannon range: 45
That round was a DIRECT HIT!
--------------------------------------------------------------------------------
STATUS:		Round: 6	Manticore: 6/10	City: 12/15
The cannon is expected to deal 3 damage this round
Enter the desired cannon range: 45
That round was a DIRECT HIT!
--------------------------------------------------------------------------------
STATUS:		Round: 7	Manticore: 3/10	City: 12/15
The cannon is expected to deal 1 damage this round
Enter the desired cannon range: 45
That round was a DIRECT HIT!
--------------------------------------------------------------------------------
STATUS:		Round: 8	Manticore: 2/10	City: 12/15
The cannon is expected to deal 1 damage this round
Enter the desired cannon range: 45
That round was a DIRECT HIT!
--------------------------------------------------------------------------------
STATUS:		Round: 9	Manticore: 1/10	City: 12/15
The cannon is expected to deal 3 damage this round
Enter the desired cannon range: 45
That round was a DIRECT HIT!
The manticore was DESTROYED!!!!
YOU WIN!!!
```

The Code:
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
            int manticoreHealth = 10;
            int cityHealth = 15;
            int manticorePosition;

            manticorePosition = GetPosition();

            Console.Clear();
            Console.WriteLine("Player 2 now it is your turn");

            int roundNumber = 1;

            while (manticoreHealth > 0)
            {
                Console.WriteLine(new string('-', 80));
                Console.WriteLine($"STATUS:\t\tRound: {roundNumber}\tManticore: {manticoreHealth}/10\tCity: {cityHealth}/15");
                int currentDamage = 1;
                if ((roundNumber) % 3 == 0 && (roundNumber) % 5 == 0)
                {
                    currentDamage = 10;
                }
                else if ((roundNumber) % 3 == 0 || (roundNumber) % 5 == 0)
                {
                    currentDamage = 3;
                }
                Console.WriteLine($"The cannon is expected to deal {currentDamage} damage this round");
                Console.Write("Enter the desired cannon range: ");
                int cannonRange = Convert.ToInt32(Console.ReadLine());
                if (cannonRange == manticorePosition)
                {
                    Console.ForegroundColor = ConsoleColor.DarkGreen;
                    Console.WriteLine("That round was a DIRECT HIT!");
                    Console.ForegroundColor = ConsoleColor.White;
                    manticoreHealth -= currentDamage;
                }
                else if (
                    cannonRange < manticorePosition
                )
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("That round FELL SHORT of the target");
                    Console.ForegroundColor = ConsoleColor.White;
                    cityHealth--;
                }
                else
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("That round OVERSHOT the target.");
                    Console.ForegroundColor = ConsoleColor.White;
                    cityHealth--;
                }
                roundNumber++;
                if (cityHealth == 0)
                {
                    Console.WriteLine("The City has FALLEN\nYOU LOST!");
                    break;
                }
            }

            if (manticoreHealth <= 0)
            {
                Console.WriteLine("The manticore was DESTROYED!!!!\nYOU WIN!!!");
                Console.ReadKey();
            }

            int GetPosition()
            {
                int position = 0;
                Console.Write("Player 1, how far away from the city do you want to station the manticore?(Between 1 to 100): ");
                do
                {
                    position = Convert.ToInt32(Console.ReadLine());
                    if (position/100 > 0 || position <= 0)
                    {
                        Console.ForegroundColor = ConsoleColor.DarkRed;
                        Console.WriteLine("Error: Invalid Position Given, RETRY!!!");
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                } while (position / 100 > 0 || position <= 0);

                return position;
            }

        }
    }
}
```

## The Null Reference
In A few programming languages(including C#), we have a null reference, i.e., the reference variable exists(eg string, arrays, lists, classes, objects), but they don't refer to anything in particular.

In C# it is written as `null`:
```Csharp
string a = null;
```

But null can have its own consequence, for example when we try to do:
```csharp
string a = Console.ReadLine("Enter something");
Console.WriteLine($"{a.Length}");
```
Given input can be null, we can have some issues.
Well we can decide which fields can tolerate null, i.e., null can be expected and where not. 
For that we use `?`:
```csharp
string? a;
```
Like this, we tell the compiler to expect a null form this variable, and hence it wont throw error when we do stuff with it.

The feature to use `?` is relatively new to like all the programming languages.
It is important to point out that, once compiled, there isn’t a difference between string? and string. This symbol simply tells the compiler to ignore all the null related warnings.

For example:
```csharp
private string? GetScoresOfTopPlayer(){
	return _scoreManager.getScore()[0].name;
}
```

Now here, we are using three reference types, anyone of them could be null reference, hence this is where we ought to be careful when we use null reference checks:
```csharp
private string? GetScoresOfTopPlayer(){
	if(_scoreManager == null)
		return null;
	if(_scoreManager.getScore() == null)
		return null;
	if(_scoreManager.getScore()[0]==null)
		return null;
	return _scoreManager.getScore()[0].name;
}
```

with null checks:
```csharp
private string? GetScoresOfTopPlayer(){
	return _scoreManager?.getScore()?[0]?.name;
}
```

### Null Coalescing Operator:
There is a conditional operator that check if the given input is null, if yes then it assigns the given value:
```csharp
string a;
a = Console.ReadLine() ?? "Nothing was given";
```

Or:
```csharp
private string GetScoresOfTopPlayer(){
	return _scoreManager?.getScore()?[0]?.name ?? "Name Not Found";
}
```

![[Pasted image 20250716203100.png]]

---
# Object Oriented Programming
Now that we are aware of the basics of C#, time to head onto the main part that will help us understand this language completely, a concept that is used literally everywhere called [[Object Oriented Programming]].
Some additional things:
- [[The Type Class in C-sharp]]
- [[Inheritance#inheritance is Everywhere!]]

## Interfaces in C#:
![[Interfaces]]

## Abstract Classes in C#:
![[Interfaces]]

## Delegates in C#:
![[Methods in c-sharp#Delegates]]

## Events in C#:
![[Methods in c-sharp#EVENTS]]

---
# Libraries 

## The Standard Library

## The Math Library
There are various libraries that offer math functions in C#.
### 1. **[[System.Math library]]**

The primary library for mathematical functions in C# is the `System.Math` class, which is part of the .NET Base Class Library. 
`System.Math` provides a wide range of static methods for common mathematical operations, including:

- Trigonometric functions: `Sin()`, `Cos()`, `Tan()`, etc.
- Logarithmic and exponential functions: `Log()`, `Exp()`, `Pow()`
- Rounding and absolute value: `Round()`, `Ceiling()`, `Floor()`, `Abs()`
- Min/max and square root: `Min()`, `Max()`, `Sqrt()`
- Other utilities: `Atan2()`, `Sign()`, etc.

All methods are static and can be accessed directly without instantiating the class.

### 2. **System.MathF**

For single-precision (float) math operations, C# provides the `System.MathF` class, which mirrors the functionality of `System.Math` but operates on `float` types instead of `double`.

### **3. Third-Party Libraries**

For advanced mathematical needs beyond what `System.Math` offers (such as linear algebra, statistics, special functions, or numerical analysis), several third-party libraries are widely used:

- **Math.NET Numerics**: An open-source library providing extensive numerical methods, linear algebra, statistics, probability, and special functions[7](https://www.luisllamas.es/en/mathnet-csharp/)[15](https://numerics.mathdotnet.com/)[16](https://ironpdf.com/blog/net-help/mathnet.numerics-slug/).
    
- **Numerics.NET**: Offers additional special functions, vector/matrix operations, and statistical models[6](https://numerics.net/whats-new-40)[8](https://numerics.net/math-features).
    
- **IMSL C# Numerical Library**: A commercial library for advanced mathematical and statistical analysis[14](https://marketplace.visualstudio.com/items?itemName=IMSLNumericalLibraries.IMSLCNumericalLibraryforNETFramework).
    

**Summary Table: Key Libraries for Math in C#**

|Library/Class|Purpose/Scope|Included in .NET?|
|---|---|---|
|System.Math|Basic math functions (double precision)|Yes|
|System.MathF|Basic math functions (single precision/float)|Yes|
|Math.NET Numerics|Advanced math, linear algebra, statistics|No (NuGet)|
|Numerics.NET|Special functions, numerical algorithms|No (NuGet)|
|IMSL Numerical Lib|Advanced/commercial math/statistics|No (Commercial)|
