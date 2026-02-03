**Expected to know:** [[Enums]], [[Data types in C-Sharp]],
**Topics Covered:** [[#Basic Syntax]],[[#What to write inside enums?]],[[#Where Enums Can Be Declared]],[[#Accessing enums]],[[#Enums in different files]]
**Tags:** [[Enums]]

## Enumerations(A custom data type)
Enumeration is a custom user defined data type, needed when we want to choose from a limited amount of things.
An *enumeration* or an *enumerated type* is a type whose choices are one of a small list of possible options. The verb enumerate means “to list off things, one by one,” hence the name. We can define new enumerations in our code to represent concepts of this nature.

For example, the Boolean values true and false would be an excellent enumeration if they were not already part of the bool type. With only four choices, the year’s seasons are also a great candidate for an enumeration.

### Basic Syntax:
```csharp
enum Season {Winter, Spring, Summer, Fall}
```
Starts with the Keyword `enum` followed by enum's name in Camel Case, followed by the options 
#### What to write inside enums?
- **Names Only:**  
    Each member is a name (identifier). By default, the first member has the value 0, the next 1, and so on.
    
- **Optional Explicit Values:**  
    You can assign specific numeric values to members if you want, using any integral numeric type (`int` by default, or you can specify `byte`, `short`, `long`, etc.). For example:
    ```csharp
    enum StatusCode : byte 
    {     
	    OK = 200,    
	    NotFound = 404,    
	    InternalServerError = 500 
	}
	```
    
- **No Strings or Non-Numeric Types:**  
    Enum members cannot be strings, floating-point numbers, or any type other than an integral numeric type.

##### **What NOT to Write in Enums**
- **No Methods or Variables:**  
    You cannot define methods or variables inside an enum.
- **No Non-Constant Expressions:**  
    Enum member values must be constant expressions that fit the underlying numeric type.

##### **Examples**
```csharp
// Valid enum 
enum Season 
{     
	Spring,    // 0    
	Summer,    // 1    
	Autumn,    // 2    
	Winter     // 3 
} 
// Valid enum with explicit values 
enum ErrorCode : ushort {     
	None = 0,    
	Unknown = 1,    
	ConnectionLost = 100,    
	OutlierReading = 200 
} 

// Invalid enum (string values not allowed) 
enum Color 
{     
	Red = "red",    // Error: cannot assign string    
	Blue = "blue"   // Error: cannot assign string 
}
```
---
### Placement of enums
enums unlike The C Programming Language cannot be declared anywhere you want.

#### Where Enums Can Be Declared
- **At the Namespace Level:**  
    You can declare an enum directly inside a namespace, outside of any class or struct. This makes the enum accessible throughout the namespace and, if public, from other namespaces as well.
```csharp
namespace MyApp 
{     
	public enum OrderStatus 
	{ Pending, Processing, Shipped, Delivered } 
}
```
- **Inside a Class or Struct:**  
    Enums can be nested inside a class or struct. In this case, the enum's scope is limited to the containing type, and you access it using the class or struct name as a qualifier.
```csharp
class Program 
{     
	private enum Level { Low, Medium, High } 
}
```
- **Inside a Struct:**  
    Similarly, you can nest an enum within a struct, which restricts its visibility to the struct and its members.

---

#### Where Enums Cannot Be Declared
- **Inside Methods (Including Main):**  
    You cannot declare an enum inside a method body, such as `Main` or any other function. Attempting to do so will result in a compilation error.
```csharp
void SomeMethod() 
{     
	// This is invalid and will not compile:    
	enum MyEnum { Value1, Value2 } 
}
```
---
### Why Enums Cannot Be Declared Inside Methods**
- **Enums Are Types, Not Variables:**  
    In C#, enums are considered named types—just like classes, structs, and interfaces. The C# language specification only allows type declarations (including enums) at the namespace, class, or struct level, not inside method bodies.
    
- **Design and Scope:**  
    Types defined at the method level would only be usable within that method, which is not consistent with the intended use of enums as reusable, named sets of constants. The language is designed to keep type declarations at a broader scope for maintainability and clarity.
    
- **Compilation Model:**  
    Allowing types inside methods would complicate the language's compilation and runtime model. C# enforces a clear separation between type definitions (which are part of the program's structure) and executable code (which resides in methods).    

---
### Enums in different files
Usually it is recogmmended to declare enums in theri own files, and then use those files in the code to access the enums.

### Accessing enums
With our enum defined, we can use it like a type:
```csharp
namespace projectA
{
	class Program
	{
	enum name{Arnab,Krishna,Sharadh}
		static void Main(string[] args)
		{
			name Trial;
			Trial = name.Arnab;
		}
	}
}
```
The value of enums are accessed using the dot operator(`.`)
Enums are so like `int` and have so much in common with them.
So, now this should make sense: 
```csharp
Console.BackgroundColor = ConsoleColor.Yellow;
```
This means that the `ConsoleColor` is an enum!!!

---