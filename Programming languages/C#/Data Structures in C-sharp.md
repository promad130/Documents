**Expected to know:** [[Introduction to Programming]],[[Data Structures]], [[C-sharp Programming Language]], [[Pointers and Working with Memory Allocation]]
**Topics Covered:** [[#Syntax for basic Data Structures]] [[#Array]]([[#Indexing in C]], [[#Ranges in Arrays for C]], [[#Functions for arrays in C]]), [[#Tuples]]()
**Tags:** [[Data Structures]]

We know about the most common data structures offered by each programming language.
Many programming languages offer some new type of data structure depending upon the use-case of the language itself.

Even the common data structures have different syntax sometimes.

---
# Syntax for basic Data Structures
The syntax for some basic data structures like arrays, lists, etc,., is quite different here in C#. Lets have a look:

## Array
In an array, the syntax for declaring it is:
```csharp
DataType[] identifierForArray;
```
Using this, we created/initialized an array of the data type `DataType`, but nothing is allocated to it, i.e., it doesn't point to anywhere or stores anything, or is of any worth.
![[Pasted image 20250526135546.png]]
Till now, it has only been created, it does not point anywhere or has any space allocated to it, for that we use `new` keyword, and allocated it some space:
```csharp
identifierForArray = new int[N];
```
Where `N` is the number of elements in the array.

Arrays in C# just like java are objects, whose reference is stored on the heap memory. It is a class under System namespace, and all the arrays in C# inherit from the base class `System.Array`.

We can also pre-specify the elements of array by declaring them at the time of initialization or later on by using the keyword `new`:
```csharp
int[] a = [2, 35, 2, 6, 1, 3, 513, 56, 2];
int[] a2 = new int[8]{2, 32, 321, 5, 3, 7812, 16, 8};
int[] a3;
a3 = new int[] { 2, 3, 2, 3, 12, 1, 2, 1, 2, 2 };
```
We first initialize it via `DataType[] arrayName;`, and then we allocate it memory or elements.
We can access the length of array by using `arrayName.Length`.

Whenever we declare the array, i.e., allocate it some space, memory, all the undefined elements are allocated their default value, which is *0* for *int*, *false* for *bool*, and *null* for *strings and char*.

Now with that, lets have a look at indexing in C#.
### Indexing in C# 
Indexing in C# also starts from 0, i.e., index 0 for the first element, 1 for second element and so on.
However, they also support negative indexing(Just like in Python), i.e., -1 for last element, -2 for second last and so on.
Think of it is a circle, or a loop. However, we directly don't write `arr[-1]`, we use `^` in C# instead..
So, `a2[^1]` would be `8`.

### Ranges in Arrays for C#:
We can access the arrays in C# in ranges, i.e., starting from a index to the given next specified index(excluding the later one) using the range operator(`..`). This is just like slicing in Python.
For example, we do `2..5`, then it means index `2,3,4`. We can also do `1..^1`, and it would mean 2nd element till last second element.

### Functions for arrays in C#:
Arrays in C# has many functions to be explored and are useful, so lets have a [look](Functions%20for%20Arrays%20in%20C-Sharp).

## Jagged and Multi-dimensional arrays in C#:
You might think, there are multiple ways to create multiple dimensional array. we have been taught that multiple dimensional array is:![[Arrays#Multidimensional Arrays]]
But who says that a multiple dimensional array has to be like a matrix? 
It can be array of arrays, or array of matrix, or matrix of arrays also!
For any multidimensional array, the syntax for initialization is:
```csharp
DataType[][] ArrayIdentifier;
```
When we create an array of an array, then it is called a **jagged array**. The syntax of which being really weird and really tedious to write:
```csharp
//2D Array
int[][] matrix = new int[3][];
matrix[0] = new int[] { 1, 2 };
matrix[1] = new int[] { 3, 4 };
matrix[2] = new int[] { 5, 6 };
```
As you can see, we can give array of any size to each element of the parent array, and like that we can increase the size and the way we are using the array!

But we might not want to do this customization always, and have an array with fixed number of columns and rows. These types of arrays are called **Multidimensional Arrays or rectangular arrays**.
The syntax for this is:
```csharp
DataType[,] Indentifier = new DataType[N1,N2];
```
Where `N1` would work like number of rows, and `N2` like number of columns.
For example:
```csharp
int[,] an2DArray = new int[3,2];
int[,] another2dArray = new int[3,2] {{1,2},{3,4},{5,6}};
int[,,] a3dArray = new int[2,3,4]; //has 2 layes, 3 rows, 4 columns
```

We can also have multidimensional array of regular arrays (`int[,][]`), regular array of multidimensional array(`int[][,,,]`), etc,.

## Lists
Arrays are something that are fixed in size. That is an issue when we have something dynamic in nature, hence this is when we use something called a *list*.

Syntax for list declaring a list is:
```csharp
List<DataType> ListName;
```

Functions that are used to work with lists [are](Functions%20for%20Lists%20in%20C-Sharp).

---
**Now head back to [[C-sharp Programming Language]]**

---

## Tuples
A tuple in C# is a lightweight data structure that groups multiple values—possibly of different types—into a single object, without needing to define a custom class or struct.
<blockquote>In C#, the simplest tool for creating composite types is called a tuple (pronounced “TOO-ples”or “TUP-ples”).</blockquote> 
These are also sometimes referred to by the number of items in them: a 2-tuple if it has two things, an 8-tuple if it has eight things, etc.
### How to Create a Tuple
You can create tuples in C# in several ways:
- **Using Parentheses (C# 7.0 and later, ValueTuple):**
	```csharp
	(int, string) person = (25, "Alice"); 
	Console.WriteLine(person.Item1); // 25 
	Console.WriteLine(person.Item2); // Alice
	```
    You can also give the elements names for better readability:
    ```csharp
    (int Age, string Name) person = (25, "Alice"); 
    Console.WriteLine(person.Name); // Alice 
    Console.WriteLine(person.Age);  // 25
	```
	Variable of this type is also made similarly:
	```csharp
	var person = (25,"Alice");
	var score = (Name:"Tetris God", Points: 12420, Level: 15);
	```
	```csharp
	(string, int P, int L) score = (Name: "R2-D2", Points: 12420, Level: 15);
	Console.WriteLine($"Name:{score.Item1} Level:{score.L} Score:{score.P}");
	```
	With the above code, the names are `Item1`, `P`, and `L`, not `Name`, `Points`, and `Level`.
- **Using the Tuple Class (Older Syntax):**
    ```csharp
    var author = new Tuple<string, string, int>("Mahesh", "ADO.NET Programming", 2003); 
    Console.WriteLine(author.Item1); // Mahesh 
    Console.WriteLine(author.Item2); // ADO.NET Programming 
    Console.WriteLine(author.Item3); // 2003
```
    Or using the static `Create()` method:
    ```csharp
    var student = Tuple.Create("Taylor", 27, "Orlando");
```

---
### Accessing Tuple Elements
- **ValueTuple (C# 7.0+):**  
    Use `.Item1`, `.Item2`, etc., or the names you assign (e.g., `.Name`, `.Age`).
- **Tuple Class:**  
    Use `.Item1`, `.Item2`, etc.
Both start from 1.

---
### Tuple Features

- **Supports Multiple Data Types:**  
    Each element can be a different type (e.g., `(string, int, double)`).
- **No Need for Custom Types:**  
    Useful for grouping related data without defining a new class or struct.
- **Return Multiple Values:**  
    Commonly used to return more than one value from a method.
- **Named Elements:**  
    Improves code readability and maintainability.
- **Mutable (ValueTuple):**  
    Elements can be changed after creation

#### Using them with Methods
We can use tuples as the parameters or the return type of a method. 
This becomes useful when we write a clean code.

For example:
```csharp
void displayScore((string Name, int Points, int level) score)
{
	Console.WriteLine($"{score.Name} has {score.Points} points on level {score.level}");
}
```
This uses tuples as parameters.

And we can:
```csharp
(string Name, int Points, int level) GetScore(){
	(string, int, int) score;
	Console.Write("Enter name: ");
	score.Item1 = Console.Readline();
	Console.Write("Enter points scored: ");
	score.Item2 = Convert.ToInt32(Console.Readline());
	Console.Write("Enter player level: ");
	score.Item2 = Convert.ToInt32(Console.Readline());
	
	return score;
	
}
```

#### Deconstructing Tuples
We can also deconstruct tuples into individual variables.
```csharp
(string name,int points, int level) score = ("ExplodeRider", 16541, 5);

string Name;
int point;
int Level;
(Name, point, Level) = score;
```

Now what if we don't want all the value? we want to discard a few value?
Then we use Discards (`_`):
```csharp
(Name,_, Level);
```

As tuples are not reference type, we can Compare them!!!
All the types of the tuples in comparison along with the order should be same, and their value should be exactly the same as well.
If any part of the tuple is a reference type, then the reference is checked, not the value of reference, hence that would make a tuple unequal no matter what!!!

So this gives out `true`:
```csharp
var a = (X: 2, Y:4);
var b = (U:2, V:4);
bool Comparison = (a==b);
Console.Write(Comparison);
```

Hence the `name Of The Variable Does Not Matter`!!!

---
