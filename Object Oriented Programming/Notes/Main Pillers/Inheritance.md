<blockquote> "Object-Oriented Principle #4: Inheritance—Basing one class on another, retaining the original class’s functionality while extending the new class with additional capabilities." </blockquote>

Lets take the example of Astroid game. 
There are many types or classes of objects that drift in space. **Asteroids**, **bullets**, and the **player’s ship** use the same mechanics to drift through space. These are distinct classes, with their own behavior, but given only the tools we have learned before now, we would have to copy and paste that drifting logic to the Asteroid, Bullet, and Ship classes as we created them.

# What happens when we define an inhertence?
Whenever inheritance is defined, three things happen:
- The new class gets everything the old class had, i.e., methods and attributes
- The new class can add in extra stuff
- The new class can always be treated as though it were the original since it has all of those capabilities

The original class is called ***base class***, but it is also called ***parent class*** or the ***superclass***,
The class that inherits the original class is called ***derived class***, sometimes also called ***child class*** or ***subclass***.
People will say that the derived class derives from the base class or that the derived class extends the base class.

# inheritance is Everywhere!
This might be surprising, but we have been using, although unknowingly, inheritance all this time.
Everything that we defined had been inheriting an class called `object` by default.
`object`  is like the superclass, the ultimate class.

The `object` class is special. It is the base class of everything, and everything is a specialization of the `object` class. That means anything the object class defines exists on every single object ever created.

## Methods of the `object` Class
Lets dicuss some methods of the `object` class:

### `ToString()`
The ToString method in the C# Object class is a virtual method that returns a string representation of the current object instance. 
By default, if not overridden, ToString returns the fully qualified name `(namespace + class name)` of the object's type.

```csharp
class MyClass { }

class Program
{
    static void Main()
    {
        MyClass obj = new MyClass();
        Console.WriteLine(obj.ToString());
    }
}
```
OUTPUT:
```text
MyNamespace.MyClass
```

hence, its usually better to override(to be discussed in polymorphism) the `ToString` function:
```csharp
class Person {
    public string Name { get; set; }
    public int Age { get; set; }
    public override string ToString() {
        return $"Person: {Name}, Age: {Age}";
    }
}

Person p = new Person { Name = "John", Age = 30 };
Console.WriteLine(p);  // Output: Person: John, Age: 30
```

## `Equals`
Equals is a method defined in the Object class in C#. It is used to compare the current object with another object to determine if they are equal.

How does it check the equality by default?
	By default, for reference types, Equals checks for reference equality — that is, it returns true if both object references point to the exact same instance in memory. 
	For value types, it performs a bitwise or field-by-field comparison to check if the values are the same.
Hence one can say that the functions performed by this method are the same as that of a `++` operator.

Hence, it is advisable to override this function as that was the one of the main purpose of this function, as it is a virtual function.

# But wait a minute, what is a virtual functions?
Before jumping into the virtual functions, lets have a look at actually how to use inheritance in C#?

```csharp
class GameObject
{
	public float Pos_X;
	public float Pos_Y;
	public float Vel_X;
	public float Vel_Y;
	
	public void Update()
	{
		Pox_X += Vel_X;
		Pox_Y += Vel_Y;
	}
}
```

Now =, suppose I create an astroid class that inheritis thios `GameObject` class, how to dro it?
In java, this is done using the `extends` keyword, but in C# we use `:`;
```csharp
public void Astroid : GameObject
{
	public float Size {get; init;}
	public float RotationAngle {get; set;}
}
```

Hence, now we have uysed inheritance to create an Astroid class, which inherits `GameObject`.

## Key Concept: Constructors are NOT Inherited

Unlike other members (methods, properties), **constructors are not inherited** from base classes to derived classes. Each class must define its own constructors.

## Why Constructors Aren't Inherited

- Constructors must put objects into a **valid starting state**
    
- A base class constructor cannot guarantee validity for a derived class object
    
- Derived classes may have additional fields that need initialization
    

## Constructor Calling Rules

## 1. Automatic Base Constructor Calling

**If base class has parameterless constructor:**

```csharp
public class GameObject
{
    public GameObject()  // Parameterless constructor
    {
        PositionX = 2; 
        PositionY = 4;
    }
}

public class Asteroid : GameObject
{
    public Asteroid()  // Automatically calls GameObject()
    {
        RotationAngle = -1;
    }
}

```

**Execution Order:**
1. `new Asteroid()` called
2. Jumps to `GameObject()` constructor first
3. Sets `PositionX = 2, PositionY = 4`
4. Returns to `Asteroid()` constructor
5. Sets `RotationAngle = -1`

## 2. Compiler-Generated Default Constructors

**If no constructors are explicitly defined:**

```csharp
public class GameObject  // Compiler generates: public GameObject() { }
{
    // Properties only
}

public class Asteroid : GameObject  // Compiler generates: public Asteroid() : base() { }
{
    // Properties only
}
```

The compiler automatically creates parameterless constructors and handles the base class calling.

## 3. Explicit Base Constructor Calling with `base`

**When base class has no parameterless constructor:**

```csharp
public class GameObject
{
    // Only parameterized constructor exists
    public GameObject(float positionX, float positionY, 
                     float velocityX, float velocityY)
    {
        PositionX = positionX; 
        PositionY = positionY;
        VelocityX = velocityX; 
        VelocityY = velocityY;
    }
}

public class Asteroid : GameObject
{
    // MUST explicitly call base constructor
    public Asteroid() : base(0, 0, 0, 0)  // Uses base keyword
    {
        // Asteroid-specific initialization
    }
    
    // More common: pass parameters through
    public Asteroid(float positionX, float positionY, 
                   float velocityX, float velocityY) 
        : base(positionX, positionY, velocityX, velocityY)
    {
        // Additional derived class initialization
    }
}

```

## Constructor Chaining Rules

## Using `this` vs `base`

```csharp
public class Asteroid : GameObject
{
    // Constructor chaining within same class
    public Asteroid() : this(0, 0, 0, 0)  // Calls other Asteroid constructor
    {
    }
    
    public Asteroid(float x, float y, float vx, float vy) 
        : base(x, y, vx, vy)  // Calls GameObject constructor
    {
        // Asteroid-specific setup
    }
}
```

## Important Rules:

- **Cannot use both `this` and `base` together** on the same constructor
    
- Can use `this` to call another constructor in the same class
    
- Must eventually call a base class constructor (no infinite loops allowed)
    
- Compiler prevents constructor call loops
    

## Summary of Constructor Behavior

|Scenario|Behavior|Syntax Required|
|---|---|---|
|Base has parameterless constructor|Automatically called|None (implicit)|
|No constructors defined anywhere|Compiler generates defaults|None|
|Base has only parameterized constructors|Must explicitly specify which one|`: base(args)`|
|Want to chain constructors in same class|Call another constructor first|`: this(args)`|

## Best Practices

1. **Always consider base class initialization** when designing constructors
    
2. **Use constructor chaining** to avoid code duplication
    
3. **Pass relevant parameters** from derived to base constructors
    
4. **Let the compiler help** - it will catch constructor-related errors
    
5. **Document complex constructor chains** for clarity
    

## Common Pattern Example

```csharp
public class GameObject
{
    public GameObject(float x, float y, float vx, float vy)
    {
        PositionX = x; PositionY = y;
        VelocityX = vx; VelocityY = vy;
    }
}

public class Asteroid : GameObject
{
    // Default asteroid at origin, not moving
    public Asteroid() : base(0, 0, 0, 0)
    {
        Size = 50;
        RotationAngle = 0;
    }
    
    // Custom positioned asteroid
    public Asteroid(float x, float y) : base(x, y, 0, 0)
    {
        Size = 50;
        RotationAngle = 0;
    }
    
    // Fully customizable asteroid
    public Asteroid(float x, float y, float vx, float vy, int size) 
        : base(x, y, vx, vy)
    {
        Size = size;
        RotationAngle = 0;
    }
}
```

This pattern provides flexibility while ensuring proper base class initialization in all scenarios.

# upcasting and downcasting

## Inheritance Revision

- A class specifies its **base class** after a colon. Example:  
    `public class Asteroid : GameObject`
    
- The **Asteroid** class inherits:
    - Properties and methods from **GameObject**: `PositionX`, `PositionY`, `VelocityX`, `VelocityY`, `Update()`
    - Adds its own members: `Size`, `RotationAngle`
    
- Other classes (e.g., Bullet, Ship) can also derive from **GameObject**.
    
You can store a **mixed collection** of objects (`Asteroid`, `Bullet`, `Ship`) because they all share the same base type (`GameObject`). You can always call methods defined in the base class (e.g., `Update()`) on derived class objects via polymorphism.

## Inheritance Rules
- Inheritance works _one way only_:
    - You can use an **Asteroid** when a **GameObject** is needed.
    - But you cannot assign a **GameObject** directly to an **Asteroid**.
        
- Example of invalid code:  
    `Asteroid asteroid = new GameObject(); // ERROR!`
    
- Inheritance hierarchy can be multiple levels deep:
    - Example: `Scout : Ship → Ship : GameObject → GameObject : object`
    - A `Bomber` can be treated as a `Ship`, `GameObject`, or `object`.
    
- C# does **not** support multiple inheritance (one class can inherit from only _one base class_).

## Casting and Downcasting

- Storing derived objects in base class variables is common:  
    `GameObject gameObject = new Asteroid();`
    
- **Downcasting**: converting a base class reference back to a derived type.
    - Works if the object is actually that derived type.
    - Unsafe if the type doesn’t match → runtime crash.
    - Example:
```csharp
GameObject gameObject = new Asteroid(); 
Asteroid asteroid = (Asteroid)gameObject; // Valid
```
```csharp
Asteroid badCast = (Asteroid)CreateAGameObject(); // Risky, might crash
```

---
## Safe Type Checking / Conversions
Three main ways to safely work with downcasting:
1. **GetType() with typeof**
```csharp
if (gameObject.GetType() == typeof(Asteroid)) { ... }`
```    
- Compares exact types.
- `asteroid.GetType() == typeof(GameObject)` → false (since they are different types).
        
2. **`as` keyword**
```csharp
Asteroid? asteroid = gameObject as Asteroid; // should be invalid, but if the gameObject object instance is of anything that is not valid, null will be returned.
if (asteroid != null) { ... }`
```    
- Returns reference if cast is valid, otherwise returns `null`.
- Must check for `null` before using.

3. **`is` keyword**
```csharp
if (gameObject is Asteroid asteroid) 
{     
	// Use asteroid here 
}
```    
- Checks type and creates a new typed variable if compatible.
- Cleaner and safer than `as`.

## typeof Operator
- **Usage**: Requires the name of a type at compile time (e.g., `typeof(int)`, `typeof(MyClass)`).
- **Purpose**: Returns a `System.Type` object representing the specified type.
- **Syntax Example**:
```csharp
Type t = typeof(string);  // t represents the type 'string'
```
- **Characteristics**:
    - Cannot be used with variables or instances, only with type names.
    - Evaluated **at compile-time**; used when the type is already known when writing code.

## GetType() Method
- **Usage**: Called on an instance (object) at runtime (e.g., `myObject.GetType()`).
- **Purpose**: Returns a `System.Type` object representing the actual runtime type of the instance.
- **Syntax Example**:
```csharp
string s = "hello"; 
Type t = s.GetType();    // t represents the type 'string'
```
- **Characteristics**:
    - Always works at **runtime**, revealing the true type of an instance.
    - Can be called on any object or variable to inspect what type it is, regardless of how it was declared or what its compile-time type is.

(Note: Have a look at [[Protected]])