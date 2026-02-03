***We will use C# and java in order to understand this topic of Object Oriented Programming***
***Pre-requisite: Should know C# from [[C-sharp Programming Language]] till [[C-sharp Programming Language#A BOSS BATTLE]].***
*(For other languages, they have their own separate Object Oriented Programming file.)* 
# Introduction to Object Oriented Programming
Suppose we want to make a game, a asteroid game that looks like this:
![[Pasted image 20250531121610.png]]
As we can see, there are many asteroids here, so would we create code for each asteroid separately again and again? How are we going to represent each bullet? Each enemy ships that come and go?
We could just create a function for each and everything but no, that would make code messy real quickly and make things tough to maintain and work with. Suppose you want to create a game like:
![[Masenshi.jpeg]]

So lets not make stuff complicated by doing that, we use a legendary concept created by the sages of development, called **Object Oriented Programming**, something created to handle complex programme by imitating what we have in real world into the world of programming(you'll know what we mean soon!)

The basic concept of object-oriented programming is that instead of putting all of our code into a single ever-growing blob of code, we split our program into multiple components called **[[Objects]]**. Each object has a single responsibility (or perhaps a set of closely related responsibilities), and the objects work together to solve the overall problem.

Each object contains a set of [[Methods in c-sharp]] and [[Variables in C-Sharp]]. Its variables store its data while its methods allow other objects to make requests of it. 
- Most objects have a few of each. 
- Some objects only need to represent the state something is in and contain only variables. Some do not need to remember any state or data and have only methods.
An object can coordinate with another object by calling one of its methods.
This paradigm is not unique to programming. For example, businesses and team projects work in the same way. Large challenges are too big for a single person, so the overall task is split among many people. They each fulfill their part of the larger system and can make requests and get information from others.

In C#, every object belongs to a specific [class or type](Class%20or%20Type). An object’s class determines the object’s “shape.” 
All objects of the same class have the same data elements and methods. You can think of classes as categories; everything in a class is similar in nature and structure. Many different objects can belong to the same class. You can interact with objects of the same class in the same way, but each object is independent of the others.
Objects of a particular class are often called [[Instances, Constructor and Object Initializer]] of the class.

Many classes of objects already exist as a part of .NET. The string type is a class, for example. 
But C# allows us to define new classes as well.

---
## How does it works?
OOP works by organizing code around the concept of "[[Objects]]," which are [Instances](Instances,%20Constructor%20and%20Object%20Initializer.md) of "[[Class or Type]]."1 Let's break down how this works step by step:

1. **Defining Classes (Blueprints):**
    - A class is like a blueprint or template for creating objects. 
    - It defines the structure and behavior that objects of that class will have.
    - Think of a class `Dog`. It defines what all dogs have in common: a breed, age, color ([[Attributes(or Data)]]), and the ability to bark, eat, and sleep ([[Methods(or Behavior)]]).
	
2. **Creating Objects (Instances):**
    - An object is a specific instance of a class. It's a real thing based on the blueprint.
    - From the `Dog` class, you can create specific dog objects: `myDog` (a Golden Retriever), `yourDog` (a German Shepherd). Each dog object has its own specific values for breed, age, and color.
	
3. **Attributes (Data Members):**
    - Attributes are the data associated with an object. 
    - They represent the object's properties or characteristics.
    - In the `Dog` class, `breed`, `age`, and `color` are attributes. `myDog` might have `breed = "Golden Retriever"`, `age = 3`, and `color = "Golden"`.
    
4. **Methods (Member Functions):**
    - Methods are functions that define the behavior of an object.8 They are actions that an object can perform.
    - In the `Dog` class, `bark()`, `eat()`, and `sleep()` are methods. `myDog.bark()` would make my dog bark.

### **How it all works together:**

1. You define classes to represent the types of objects you need in your program.
2. You create objects (instances) of those classes.
3. Objects have attributes (data) and methods (behavior).
4. Objects interact with each other by calling each other's methods.
5. You access the members and attributes of a class using ***The Dot Operator***.
6. Encapsulation, abstraction, inheritance, and polymorphism help you organize and manage the complexity of your code.

#### **Example (Simplified Analogy):**
Imagine a car factory.
- **Class (Blueprint):** The blueprint for a car (defines what all cars have: engine, wheels, doors, etc.).
- **Object (Instance):** A specific car that comes off the assembly line (a red Mustang, a blue SUV).
- **Attributes (Data):** The car's color, model, year, mileage.
- **Methods (Behavior):** `startEngine()`, `accelerate()`, `brake()`.

The factory (your program) creates car objects based on the blueprint (class), and these car objects have their own specific attributes and can perform actions (methods).

OOP is all about organizing your code in this way, making it more modular, reusable, and easier to manage.

Also it would be beneficial to know and revise this:
![[C-sharp Programming Language#Getting started]]

---
# Principles of Object Oriented Programming;
Now that we have learnt about [[Class or Type]], [[Objects]] and [[Access Modifier]], time to look at the pillers of Object Oriented Programming:

---
## [[Encapsulation]]
Encapsulation in object-oriented programming (OOP) is a foundational concept that bundles data (attributes) and methods (functions) into a single unit, typically a **class**, while controlling access to internal components. 
<blockquote>Encapsulation—Combining data (fields) and the operations on that data (methods) into a well-defined unit (like a class).</blockquote>
This mechanism ensures data integrity, security, and modular code design by hiding implementation details and exposing only necessary interfaces.
### Core Principles of Encapsulation:
1. **Data Bundling**: Combines related data and methods into a class. For example, a `BankAccount` class might encapsulate `balance` (data) and `deposit()`/`withdraw()` (methods).
2. **Access Control**: Uses modifiers like `private`, `protected`, and `public` to restrict direct access to sensitive data. Private members can only be modified via controlled methods (e.g., getters/setters) .
3. **Information Hiding**: Shields internal state from external interference. For instance, a `Car` class might hide engine mechanics but expose `accelerate()` and `brake()` methods
So basically making a class and giving attributes and methods is what encapsulation is.

---
### Information Hiding 
This is an extension of encapsulation.
Suppose we have the following class:
```Csharp
class Rectangle
{
	public float width;
	public float height;
	public float area;
	public Rectangle(float width, float height, float area)
	{
		this.width = width;
		this.height = height;
		this.area = width*height;
	}
}
```
So here, the object can access the area attribute.i.e., even though the area is calculated, it still can accessed and changed.
So there should be some kind of restriction to avoid this type of mistakes. One obvious solution would be to hide the area attribute and make it inaccessible.

This is something that we handle using [[Access Modifier]].

So we make them private:
```Csharp
class Rectangle
{
	private float width;
	private float height;
	private float area;
	public Rectangle(float width, float height, float area)
	{
		this.width = width;
		this.height = height;
		this.area = width*height;
	}
}
```

But now we cannot see or read them outside the class, for that we add these in the class:
```csharp
public float GetWidth() => _width;
public float GetHeight() => _height;
public float GetArea() => _area;
```

And what if I want to change the width and length?
Well then we create these functions in the class:
```csharp
public float SetWidth(float value){
	width = value;
	calculateArea();
}

public float SetHeight(float value){
	height = value;
	calculateArea();
}

private void calculateArea(){
	area = height * width;
}
```

Methods that retrieve a field’s current value are called **getters**. Methods that assign a new value to a field are called **setters**. The above code shows that these methods allow us to perform more than just setting a new value for the field. Both `SetWidth` and `SetHeight` update the rectangle’s area to ensure it stays consistent with its width and height.

While we have intentionally put public or private on all our class members, this is not strictly necessary. We could leave it off entirely. If you don’t specify an accessibility level, members of a class will be private.

## [[Abstraction]]
A magical thing happens when the principles of encapsulation and information hiding are followed. The inner workings of a class are not visible to the outside world. It is like a cell phone’s insides: as long as the phone’s buttons and screen work, we don’t care how the circuitry on the inside works. The human body is like this, as well. We don’t need to know how the nerves and tendons connect, as long as things are working correctly.
With the clear boundary provided by encapsulation and the inner workings kept secret through information hiding, those inner workings can change entirely without any visible effect on the outside world. This ability is called **abstraction**.

We can and should provide accessibility on the types we define in C# (like classes).
```Csharp
public class MyClass{
	private string firstName;
	private string lastName;

	public string GetName() => $"{firstName} {lastName}";
	public void SetName(string firstName, string lastName) => {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
```

There are multiple abstraction techniques:
### [[Properties, an Abstraction]] 
Suppose we have something like this:
```csharp
private float _width;
public float GetWidth() => _width;
public void SetWidth(float value) => _width = value;
```
Here, we can use a property to do what Setter and Getter functions are doing like this:
```csharp
private float _width;
public float Width
{
	get => _width;
	set => _width = value;
}
```
Here we defined a property called `Width`. Property usually follow Pascal Case. The body of a property is defined with a set of curly braces. Inside that, you can define a `getter` (with the `get` keyword) and a `setter` (with the `set` keyword), each with its own body.

Properties do not require both getters and setters. You can have a get-only property or a set-only property. A get-only property makes sense for something that can’t be changed from the outside. The rectangle’s area is like this. We could make a get-only property for it:
```csharp
public float Area
{
	get => _width * _height;
}
```
If a property is get-only and the getter has an expression body, we can simplify it further:
```csharp
public float Area => _width * _height;
```

A property’s `getter` and `setter` do not need to have the same accessibility level. Either `getter` or `setter` can reduce accessibility from what the property has. If we want the property to have a `public getter` and a `private setter`, we could do this:
```csharp
public float Width
{
	get => _width;
	private set => _width = value;
}
```

Now, it would be a good practice to look at [[Instances, Constructor and Object Initializer#Object Initializer Syntax and Init Properties]]

## Note: Anonymous Types
![[Pasted image 20250624111734.png]]

## [[The Static Members and Methods]]
Lets say I want to create a field that would be shared across all the instances of my class, i.e., that member of my class should posses same value across all the instances, hence is to be `shared` among them, then we use the keyword `static`.
>By applying the *static* keyword to a field, you create a *static field* or *static variable*.

For example we have a class that stores player's stats, and we want to create a threshold values to determine if the player is worthy of being kept on the leaderboard:
```csharp
public class Score
{
	private static readonly int PointThreshold = 4000;
	private static readonly int LevelThreshold = 4;
	
	public bool IsWorthyOfTheHighScoreTable()
	{
		if (Points < PointThreshold) return false;
		if (Level < LevelThreshold) return false;
		return true;
	}
	//...
}
```
If a static field is public, it can be used outside the class through the class name(`Score.PointThreshold`, for example).
The static members should be given `PascalCase` only.


## [[Inheritance]]
Sometimes a class is a specialization of another class, i..e, there is a broader category which has multiple specializations.A few exmaples:
- A truck, scooter, bike, car, etc, are a type of vahicle
- An Emplyee, admin, CEO are company members
- A bullet, player, sound, etc,., in a game are a game object
These subtype or specialization relationship in C# code using *inheritance*.

## [[Polymorphism]] 
Inheritence in itself is a powerful tool but it is incomplete without Polymorphism.
Imagine programming a Chess Program, so you would have different types of pieces, and all of them as a common thing to do, check for the next movable blocks, check if those blocks are blocked and check if you are still within the chess board's bounds.

So lets say we define a `ChessPiece` class like this:
```csharp
public class ChessPiece
{
	public int Row { get; set; }
	public int Column { get; set; }
	
	public bool IsLegalMove(int row, int column) =>
		IsOnBoard(row, column) && !IsCurrentLocation(row, column);
	
	protected bool IsOnBoard(int row, int column) =>
		row >= 0 && row < 8 && column >= 0 && column < 8;
	
	protected bool IsCurrentLocation(int row, int column) =>
		row == Row && column == Column;
}
``` 
And just like this, a derived class for each chess piece could be made, for example for King, it would be like:
```csharp
class King :  ChessPiece
{
	public bool isKingLegalMove(int row, int col)
	{
		if(!isLegalMove(row, col))
			return false;
			
		if (Math.Abs(row - Row) > 1) return false;
		if (Math.Abs(column - Column) > 1) return false;
		
		return true;
	}
}
```
But the issue here is that now each piece's class will have their custom version of isLegalMove class, which creates a lot of syntax, and can become a hassle to manage.
This is where Polymorphism comes in play.


teach me the relationships ion OOPS, like for exmaple, has-a. is-a. etc
---
# Quick Summary Of Pillars in Object Oriented Programming

1. **Encapsulation (Data Hiding):**
    
    - [[Encapsulation]] bundles the data (attributes) and the code that operates on that data (methods) within the object.
    - It also often involves _data hiding_, where the attributes are made private (not directly accessible from outside the object), and access is controlled through methods (*getters and setters*).
    - This protects the data from being accidentally modified and keeps the object's internal state consistent.
    - You wouldn't want someone randomly changing a dog's age without going through the proper channels (perhaps a `setAge()` method).
    
2. **Abstraction (Simplifying Complexity):**
    
    - [[Abstraction]] hides the complex implementation details of an object and shows only the essential features.
    - When you interact with a `Dog` object, you don't need to know how the `bark()` method is implemented internally; you just call the method, and it does it's thing.
    - Abstraction simplifies how you interact with objects by hiding the background implementation of the process.
    
3. **Inheritance (Extending Functionality):**
    
    - [[Inheritance]] allows you to create new classes (derived classes) based on existing classes (base classes). 
    - The derived class inherits the attributes and methods of the base class and can add its own or override the existing ones.16
    - You could have a `Pet` class with common pet attributes and methods, and then create `Dog`, `Cat`, and `Bird` classes that inherit from `Pet`. 
    - Each animal class could then add its specific attributes and methods.
    
4. **Polymorphism (Many Forms/ Ability to take more than one form):**
    
    - [[Polymorphism]] allows objects of different classes to respond to the same method call (or a particular method call which is common to all) in their own specific ways.
    - If you have a collection of `Animal` objects (which could be `Dog`, `Cat`, or `Bird` objects), and when we call the `makeSound()` method on each object, then each object will make its own appropriate sound. 
    - This simplifies code that works with collections of objects.

---

# Abstract Classes
![[Pasted image 20250919113830.png]]
Abstract members can only live in abstract classes, but an abstract class can contain any
member it wants—abstract, virtual, or normal. It is not unheard of to have an abstract class
with no abstract members—just a foundation for closely related types to build on.
When a distinction is needed, non-abstract classes are often referred to as *concrete classes*.

Check: 
- [[C-sharp Programming Language#Abstract Classes in C]]
- [[Object Oriented Java#Abstract Class in Java]]

---
# Interface
![[Interfaces]]



---
# Object Oriented Design:
As we tackle larger problems, our solutions grow in size as well. Objects allow us to take the
entire problem and break it into small pieces, where each piece—each object—has its job in
the overall system. Many objects—each doing their part and coordinating with the other
objects—allow us to solve the overall problem in small pieces.
Object-oriented design is the part of crafting software where we decide:
- which objects should exist in our program,
- the classes each of those objects belong to,
- what responsibilities each class or object should handle,
- when objects should come into existence,
- when objects should go out of existence,
- which objects must collaborate with or rely upon which other objects,
- and how an object knows about the other objects it works with.

We have a certain naming conventions for things out there in software development. [Make sure that you follow those conventions](Naming%20Conventions%20in%20Object%20Oriented%20Programming).




















## Memory Allocation 

[[Memory Allocation in OOPS in C++]]

# Types of Data Members and Methods

We can change the property of the Data Members and Member functions(i.e., methods) in a class. 
Just like Access Modifiers, we have a few more things.


# Working with objects and classes:

Remember, classes can be treated as a unique data type in it's own just like structs, so we can do everything with them that we did with other data types and structs.

## Static

[[Static]] 

## Array of Objects 

[[Array of Objects]]

## Giving Object as the parameter in a member function

[[Objects as Parameters]]

## Friend 

[[Friend Functions]] 

[[Friend Classes And Member]] 
