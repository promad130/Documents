## What is an Instance?
An **instance** is a specific, individual object created from a class (or blueprint). While a **class** defines the structure and behavior (attributes and methods), an **instance** is the actual object in memory that you can use and manipulate.

## Analogy
- **Class**: Like a blueprint for a house.
- **Instance**: A real house built from that blueprint.
You can have many houses (instances) built from the same blueprint (class), each with its own address and inhabitants (unique data).

---
In programming terms:
- A class defines the _type_ of object and what it can do (its attributes and methods).
- An instance is a _specific object_ of that type, with its own specific values for its attributes.

**Example (C++):**
```C++
class Dog { // Dog is the class (the cookie cutter)
public:
    string breed;
    int age;

    void bark() {
        cout << "Woof!" << endl;
    }
};

int main() {
    Dog myDog; // myDog is an instance (a specific cookie) of the Dog class
    myDog.breed = "Golden Retriever"; // myDog's breed is "Golden Retriever"
    myDog.age = 3;                  // myDog's age is 3

    Dog yourDog; // yourDog is *another* instance (another cookie) of the Dog class
    yourDog.breed = "German Shepherd"; // yourDog's breed is "German Shepherd"
    yourDog.age = 5;                  // yourDog's age is 5

    myDog.bark();   // myDog barks
    yourDog.bark(); // yourDog barks

    return 0;
}
```

In this example:
- `Dog` is the class.
- `myDog` and `yourDog` are _instances_ of the `Dog` class. They are two separate dog objects, each with their own attributes (`breed` and `age`). They are both dogs, but they are different individual dogs.
So, an instance is just another word for an _object_ – a specific, concrete thing created from a class. You can have many instances of the same class, each with its own unique data.

---
## Creating Instances in C#:
We create object instance using the `new` keyword:
(Suppose we have a `Score` class)
```Csharp
Score best = new Score();

class Score{
	public string name;
	public int points;
	public int level;
	
	public bool EarnedStar() => (points / level)>1000;
}
```

Instances of a class are created with the new keyword. That Score() thing refers to a special method called a **constructor**, used to get new instances ready for use.
We didn’t define such a constructor in our Score class, but the compiler was nice enough to generate a default one for us.

We can create as many instances as possible!!!
#### Do the instances of classes share the methods or do each instance is given their own copy of method in C#? 
All instances of a class share the same compiled method code. When you create an object (an instance) of a class, only the data (fields, properties) are unique to that instance; the methods are shared and exist only once in memory for that class type.
```csharp
Score a = new Score();
a.name = "R2-D2";
a.points = 12420;
a.level = 15;

Score b = new Score();
b.name = "C-3PO";
b.points = 8543;
b.level = 8;

if (a.EarnedStar())
	Console.WriteLine($"{a.name} earned a star!");
if (b.EarnedStar())
	Console.WriteLine($"{b.name} earned a star!");
```
![[Pasted image 20250606201121.png]]

---
# Constructors:
Creating a new object reserves space for the object’s data on the heap. But it is also vital that new objects come into existence in legitimate starting states. 
What happens to the values when we create a new object? What are they assigned? How is it all managed?

A **constructor** is a special method in a class that is automatically called when an object (instance) of that class is created.
Its main purpose is to initialize the new object, setting up its initial state and ensuring its attributes have valid values.

For example, in our `Score` class;
```csharp
class Score
{
	public string name;
	public int points;
	public int level;
	
	public Score()
	{
		name = "Unknown";
		points = 0;
		level = 1;
	}
	
	public bool EarnedStar() => (points / level) > 1000;
}
```
```java
class Fraction{
	private int num = 0;
	private int denom = 0;
	public Fraction(int num, int denom){
		this.num = num;
		this.denom = denom
	}
}
```
```c++
#include <iostream>
using namespace std;

class Complex
{
	int real, imag;
	//creating a constructor
	Complex(void); //Constructor Declaration

	public:
		void printData(void)
		{
			cout<<real<<" i "<<imag<<endl;
		}
};

Complex:: Complex(void)
{
	real = 0;
	imag = 0
}

int main()
{
	
}
```
- **Key Characteristics:**
	- **Same Name as Class:** The constructor has the same name as the class it belongs to.
	- **No Return Type:** Constructors do not have a return type—not even `void`.
	- **Automatic Invocation:** The constructor is executed automatically when an object is created (e.g., using `new` in Java, C#, or C++).
	- **Initialization:** It sets the initial values for the object's attributes, allocates resources, or performs any setup tasks required for the object to function properly.

### But we didn't do anything like this before?
Well, as we saw earlier, the class has a default constructor already available to it, which looks like:
```csharp
public className(){}
```

## Constructors with parameters:
Constructors are allowed to have parameters, just like other methods. It is quite common for constructors to use parameters to let the outside world provide the initial values for some fields.
For example:
```csharp
class Score
{
	public string name;
	public int points;
	public int level;
	
	public Score(string n, int p, int l) // That's a lowercase 'L', not a 1.
	{
		name = n;
		points = p;
		level = l;
	}
	
	public bool EarnedStar() => (points / level) > 1000;
}

```
```c++

//Creating a parameterized constructor

#include <iostream>
using namespace std;

class Complex
{
	int real, imag;
	//creating a constructor
	Complex(int a, int b); //Constructor Declaration

	public:
		void printData(void)
		{
			cout<<real<<" + i"<<imag<<endl;
		}
};

Complex:: Complex(int a, int b)
{
	real = a;
	imag = b;
}
```

And instantiate it:
```csharp
Score player1 = new Score("R2-D2", 12420, 15);
```
```c++
int main()
{
	Complex a(4,5);
	a.printData();
}
```

## Multiple constructors:
A class can define as many constructors as you need, basically function overloading!!!
```csharp
class Score
{
	public string name;
	public int points;
	public int level;
	public Score()
	{
		name = "Unknown";
		points = 0;
		level = 1;
	}
	public Score(string n, int p, int l)
	{
		name = n;
		points = p;
		level = l;
	}
	
	public bool EarnedStar() => (points / level) > 1000;
}
```
```c++
#include <iostream>
using namespace std;

class Complex
{
	int real, imag;
	//creating a constructor
	public:
	Complex(int a, int b); //Constructor Declaration
	Complex(void);
	public:
		void printData(void)
		{
			cout<<real<<" + i"<<imag<<endl;
		}
};

Complex:: Complex(int a, int b)
{
	real = a;
	imag = b;
}
Complex:: Complex(void)
{
	real = 0;
	imag = 0;
}
int main()
{
	Complex a;
	a.printData();
}
```

With two constructors, the outside world pick which constructor it wants to use:
```csharp
Score a = new Score();
Score b = new Score("R2-D2", 12420, 15);
```

## Initializing Fields Inline:
Another way to initialize fields is by doing so inline, where they are declared, as shown below:
```csharp
class Score
{
	public string name = "Unknown";
	public int points = 0;
	public int level = 1;
	
	public bool EarnedStar() => (points / level) > 1000;
}
```

These assignments happen after the memory is zeroed out but before any constructor code runs. These then become the default values for these fields. If these defaults are sufficient and no other initialization needs to happen, you can skip defining your own constructors. But any constructor can also override these defaults as needed

## Name hiding and the `this` keyword:
Lets say we create a constructor which can take parameters:
```csharp
class Score
{
	public string name = "Unknown";
	public int points = 0;
	public int level = 1;
	
	public Score(string n, int p, int l){
		name = n;
		points = p;
		level = l;
	}
	
	public bool EarnedStar() => (points / level) > 1000;
}
```

Instead of creating the parameters like the given ones, which would be confusing when we have more attributes to work with.
It would be easy if we could use the same names that we used in the attributes, but then it would look like this:
```csharp
class Score
{
	private string name = "Unknown";
	private int points = 0;
	private int level = 1;
	
	public Score(string name, int points, int level){
		name = name;
		points = points;
		level = level;
	}
	
	public bool EarnedStar() => (points / level) > 1000;
}
```
And cause expected behaviors.

So, we use `this` keyword. It is a is like a special variable that always refers to the object you are currently in. Using it, we can access fields directly, regardless of what names we have used for local variables and parameters:
```csharp
class Score
{
	public string name;
	public int points;
	public int level;
	
	public Score(string name, int points, int level)
	{
		this.name = name;
		this.points = points;
		this.level = level;
	}
}
```
All three parameters hide fields of the same name, but we can still reach them using this.
The `this` keyword allows us to use straightforward names without decoration while still allowing everything to work out.

## Leaving off the Class Name
When you are creating new instances of a class, if the compiler has enough information to know which class you are using, you can leave the class name out:
```csharp
Score first = new();
Score second = new("R2-D2", 12420, 15);
```

This is like var, only on the opposite side of the equals sign. 
The compiler can infer that you are creating an instance of the Score class because it is assigned to a Score-typed variable.
This feature is most valuable when our type name is long and complex.

# Copy Constructor
## Copy Constructors in Object-Oriented Programming

A copy constructor is a special constructor that creates a new object by copying an existing object of the same class. 
It takes a single parameter—an object of the same class type—and initializes the new instance with the values from the existing object.

## Purpose and Use Cases
Copy constructors are essential when you need to create independent duplicates of objects, particularly when dealing with complex objects that contain reference types or dynamically allocated memory. They ensure proper object duplication while maintaining encapsulation and are invoked when passing objects by value, returning objects from functions, or explicitly creating copies.​

## C# Implementation

In C#, copy constructors must be implemented manually since the language doesn't provide them by default. Here's a practical example:
```Csharp
public class Employee
{
    public string Name { get; set; }
    public int Age { get; set; }
    public string Position { get; set; }

    // Parameterized constructor
    public Employee(string name, int age, string position)
    {
        Name = name;
        Age = age;
        Position = position;
    }

    // Copy constructor
    public Employee(Employee employee)
    {
        Name = employee.Name;
        Age = employee.Age;
        Position = employee.Position;
    }

    public void DisplayInfo()
    {
        Console.WriteLine($"Name: {Name}, Age: {Age}, Position: {Position}");
    }
}

// Usage
Employee emp1 = new Employee("Jake Paul", 30, "Software Engineer");
Employee emp2 = new Employee(emp1);  // Copy constructor invoked
emp2.DisplayInfo();  // Displays same values as emp1

```

## Java Implementation
Java similarly requires explicit copy constructor implementation. Here's an equivalent example:​
```java
public class Employee
{
    private String name;
    private int age;
    private String position;

    // Parameterized constructor
    public Employee(String name, int age, String position)
    {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    // Copy constructor
    public Employee(Employee employee)
    {
        this.name = employee.name;
        this.age = employee.age;
        this.position = employee.position;
    }

    public void displayInfo()
    {
        System.out.println("Name: " + name + ", Age: " + age + ", Position: " + position);
    }
}

// Usage
Employee emp1 = new Employee("Jake Paul", 30, "Software Engineer");
Employee emp2 = new Employee(emp1);  // Copy constructor invoked
emp2.displayInfo();  // Displays same values as emp1

```

## Shallow vs. Deep Copy

Understanding the difference between shallow and deep copies is critical when implementing copy constructors:
- **Shallow copy**: Copies all field values directly, meaning reference types point to the same memory location as the original object    
- **Deep copy**: Creates new instances of referenced objects, ensuring complete independence between the original and copied objects

When your class contains reference types (arrays, lists, or other objects), implement deep copying to avoid unintended side effects where modifications to one object affect another.

# Constructor Chaining
## Constructor Chaining
Constructor chaining is calling one constructor from another constructor within the same class or from a parent class. It reduces code duplication and centralizes initialization logic.​

## Two Types
**Within same class**: Use `this()` to call another constructor in the same class
**Across parent-child classes**: Use `super()` to call parent class constructor from child class​

## Java Example - Same Class
```java
public class Employee {
    private String name;
    private int id;
    private String department;

    // Default constructor
    public Employee() {
        this("Unknown", 0);  // Calls two-parameter constructor
    }

    // Two-parameter constructor
    public Employee(String name, int id) {
        this(name, id, "General");  // Calls three-parameter constructor
    }

    // Three-parameter constructor (main initialization)
    public Employee(String name, int id, String department) {
        this.name = name;
        this.id = id;
        this.department = department;
    }
}

```

## C# Example - Same Class
```csharp
public class Employee 
{
    public string Name { get; set; }
    public int Id { get; set; }
    public string Department { get; set; }

    // Default constructor
    public Employee() : this("Unknown", 0)  // Calls two-parameter constructor
    {
    }

    // Two-parameter constructor
    public Employee(string name, int id) : this(name, id, "General")  // Calls three-parameter
    {
    }

    // Three-parameter constructor (main initialization)
    public Employee(string name, int id, string department)
    {
        Name = name;
        Id = id;
        Department = department;
    }
}

```

## Java Example - Inheritance
```java
class Animal {
    String type;
    
    Animal(String type) {
        this.type = type;
    }
}

class Dog extends Animal {
    String name;
    
    Dog(String name) {
        super("Dog");  // Calls parent constructor first
        this.name = name;
    }
}

```

## C# Example - Inheritance
```csharp
class Animal 
{
    public string Type { get; set; }
    
    public Animal(string type) 
    {
        Type = type;
    }
}

class Dog : Animal 
{
    public string Name { get; set; }
    
    public Dog(string name) : base("Dog")  // Calls parent constructor first
    {
        Name = name;
    }
}

```

## Key Rules
- `this()` or `super()` (Java) / `base()` (C#) must be the first statement
- A constructor can call either `this()` or `super()`/`base()`, never both
- Prevents duplicate initialization code across multiple constructors

---
*(First cover [[Properties, an Abstraction]])*
# Object Initializer Syntax and Init Properties
This is used right after we have constructed the object which allows to make some final adjustment before object initializer statement is completed and it is ready to be used in other parts.

Lets say we have:
```csharp
public class Circle
{
	public float X { get; set; } = 0; // The x-coordinate of the circle's center.
	public float Y { get; set; } = 0; // The y-coordinate of the circle's center.
	public float Radius { get; set; } = 0;
}
```

With this, we could make a circle normally like:
```csharp
Circle circle = new Circle();
circle.X = 4f;
circle.Y = 5.6;
circle.Radius = 4f;
```

Or we could do this:
```csharp
Circle circle = new Circle() {Radius = 3f, Y = -4f};
```
This is **Object Initializer Syntax**, cannot be used with `get-only` stuff, as object initializer comes after the constructor.
This helps in a lot of stuff, for example when we want to create an object but with different default, or want object to have a different values as right after that the object is being used, etc,.

Now but what if we want to edit the `get-only` outside the constructor? Then we use `init` in the properties, works like a setter, but only allows Object Initializer to edit the value:
```csharp
public class Circle
{
	public float X { get; init; } = 0;
	public float Y { get; init; } = 0;
	public float Radius { get; init; } = 0;
}
```

and then we can use it like:
```csharp
Circle circle = new Circle() {X = 1f, Y= -1f, Radius = 6};
```

- The property can be set _via the `init` accessor_ **at the same time as the constructor runs**, or **immediately after**, but only as part of the initialization phase.
    
- There's **no special "init" method** that runs after the constructor.
    
- The actual sequence when using an object initializer is:
    
    1. The constructor runs (whichever is matched -- typically the parameterless one).
        
    2. Then, for each property in the initializer, the corresponding `init` accessor is called. This happens immediately after the constructor finishes and before the variable escapes the initialization expression
The **initialization phase** in C# refers to the period during which an object is being constructed and its properties can be set—either by a constructor or using an _object initializer_ (including via `init`-only properties). This phase is crucial for understanding how and when you can assign values to properties, especially those marked with the `init` accessor.

Now If you want to make it compulsory to define properties at initialization, then


# Copy Constructors

Now what if I want to create an object that is exact copy of another object of the same class?