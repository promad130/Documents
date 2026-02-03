**Topics Covered:** What is a class, syntax of a class,
**Tags:** [[Object Oriented Programming]], [[C-sharp Programming Language]]  

In object-oriented programming (OOP), a **class** is a blueprint or template for creating objects. It defines the structure and behavior that objects of that class will have. 
Think of it as a cookie cutter: the cookie cutter is the *class*, and the cookies you make with it are the [[Objects]].
Here's a breakdown of what classes are and why they're important:

## 1. Blueprint for Objects:
A class is a description of a type of object. 
It specifies:
- **[[Attributes(or Data)]] (Data Members):** The characteristics or properties that objects of this class will have. These are represented by variables. For example, a `Car` class might have attributes like `color`, `model`, `year`, `mileage`.
- **[[Methods(or Behavior)]] (Member Functions):** The actions or behaviors that objects of this class can perform. These are represented by functions. A `Car` class might have methods like `startEngine()`, `accelerate()`, `brake()`.

## 2. Defining the Structure and Behavior:
The class definition specifies:

- **What data an object can hold:** The attributes and their data types.
- **What actions an object can perform:** The methods and their parameters.

## 3. Creating Objects (Instances):
A class itself is not an object. It's the _blueprint_. You use the class to create _objects_, which are specific instances of the class. Each object has its own unique values for the attributes defined in the class.

---
# Syntax:
In C#, a class defines a blueprint for creating objects. The basic syntax for declaring a class is:
```csharp
[access_modifier] class ClassName 
{     
	// Fields    
	// Properties    
	// Constructors    
	// Methods 	
}
```
- **[access_modifier](Access%20Modifier)**: Specifies the visibility of the class (e.g., `public`, `internal`).    
- **`ClassName`**: The name of the class, following PascalCase naming convention.

**Example:**
```csharp
public class Person 
{     
	// Field    
	private string name;     
	
	// Property    
	public string Name    
	{        
		get { return name; }        
		set { name = value; }    
	}     
	
	// Constructor    
	public Person(string name)    
	{        
		this.name = name;    
	}     
	
	// Method    
	public void Greet()    
	{        
		Console.WriteLine($"Hello, my name is {name}.");    
	} 
}
```
This structure includes fields (variables), properties (getters/setters), constructors (special methods to initialize objects), and regular methods.

In Java:

---
## Scope of Variables in Classes
Consider this given example:
```csharp
class Score{
	public string name;
	public int points;
	public int level;
	
	public bool EarnedStar() => (points/level) > 1000;
}
```
As you can notice, we are able to use the variables in the function that are not declared in the function.
This is because we have another type of variables here in Object Oriented Programming, called *fields* or *instance variables*.
These are not like local variables and parameters that come and go with the function, these variables are a part of [[Objects]] and hence live as long as the object lives.
Hence, these variables declared inside a class comes in the scope of the methods that are declared inside the class.

---
### Note; The symbol `=>` in the method definition
```csharp
public bool EarnedStar() => (points/level) > 1000;
```
is called the **expression-bodied member** syntax in C#.
#### What does it do?
- It provides a concise way to define a method (or property, or constructor) that returns the result of a single expression.
- The code above is equivalent to writing:
```csharp
public bool EarnedStar() 
{     
	return (points/level) > 1000; 
}
```
#### How does it work?
- The `=>` operator here is not a lambda (though it looks similar); it's specifically used to simplify methods that just return a value based on an expression.
- It improves readability and reduces boilerplate code for simple logic.
- ---
