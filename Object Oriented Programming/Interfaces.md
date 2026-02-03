## Overview
- An **interface** defines a contract or role that objects can fulfill by implementing it
- Specifies methods, properties, and events without providing implementation
- Enables multiple unrelated classes to share common capabilities
- Provides the greatest flexibility for code evolution and substitution
- Real-world analogy: Piano keys interface, vehicle controls interface

## Definition Syntax
```csharp
public interface ILevelBuilder
{
    Level BuildLevel(int levelNumber);
    int Count { get; }
}
```

Cover declaring method in interface, using them, declaring attributes in interfaces and using them as well.

### Key Rules for Interface Definition
- Use `interface` keyword instead of `class`
- All members are implicitly `public` and abstract
- All fields are implicitly `public static final` (constants)
- **Naming Convention**: Start with capital 'I' (e.g., `ILevelBuilder`)
- No definitions of methods are allowed (except default or static methods) 
	Example, before Java8:
```java
// Valid in Java 7 and earlier
interface MyInterface {
	void method1();  // Abstract - no body
	int method2(String s);  // Abstract - no body
	int MAX_VALUE = 100;  // Constant only
}

// Invalid in Java 7 and earlier
interface MyInterface {
	void method1() {  // ERROR: Cannot have method body
		System.out.println("Hello");
	}
}
	```
After Java8:
```java
// Valid from Java 8 onwards
interface MyInterface {
    void abstractMethod();  // Still abstract
    
    default void defaultMethod() {  // Has implementation
        System.out.println("Default implementation");
    }
    
    static void staticMethod() {  // Has implementation
        System.out.println("Static method");
    }
}
```

## Implementation

### Basic Implementation
```csharp
public class FixedLevelBuilder : ILevelBuilder
{
    public Level BuildLevel(int levelNumber) 
    { 
        // Implementation here
        return new Level();
    }
    
    public int Count => 10;
}
```

### Implementation Rules
- Use colon `:` after class name, followed by interface name[2]
- Must implement **every** interface member as `public`[2]
- Do **not** use `override` keyword - this is not overriding[2]
- Interface defines *what* must be done, not *how*[2]

## Interfaces vs. Base Classes

### Inheritance Combinations
```csharp
// Single base class + multiple interfaces
public class MySqlDatabaseLevelBuilder : BasicDatabaseLevelBuilder, ILevelBuilder
{
    // Implementation
}

// Multiple interfaces only
public class SomeClass : ISomeInterface1, ISomeInterface2
{
    // Implementation  
}
```

### Key Differences
- **Classes**: Can extend **one** base class only
- **Interfaces**: Can implement **multiple** interfaces[2]
- **Interfaces can extend other interfaces** (but not classes)[2]

## Using Interfaces

### Polymorphic Usage
```csharp
ILevelBuilder levelBuilder = LocateLevelBuilder();
Level level = levelBuilder.BuildLevel(currentLevel);
```

### Benefits
- **Substitutability**: Swap implementations without changing client code[2]
- **Flexibility**: Most flexible for code evolution over time[2]
- **Boundary Definition**: Clear contract for what object must do[2]

## Explicit Interface Implementation

### When to Use
- Class implements two interfaces with **same method names** but **different meanings**[2]
- Resolves naming conflicts between interfaces[2]

### Syntax
```csharp
public class ExplodingBalloon : IBomb, IBalloon
{
    void IBomb.BlowUp() 
    { 
        Console.WriteLine("Kaboom!"); 
    }
    
    void IBalloon.BlowUp() 
    { 
        Console.WriteLine("Whoosh"); 
    }
}
```

### Usage Constraints
- **Cannot call explicitly implemented methods directly on class instance**[2]
- Must cast to interface type first[2]
```csharp
ExplodingBalloon balloon = new ExplodingBalloon();
// balloon.BlowUp(); // COMPILER ERROR!

IBomb bomb = balloon;
bomb.BlowUp(); // Works - calls bomb version

IBalloon airBalloon = balloon;  
airBalloon.BlowUp(); // Works - calls balloon version
```

## Default Interface Methods

### Purpose
- **Interface Evolution**: Add new members without breaking existing implementations[2]
- Useful when interface is implemented by many classes[2]
- Provide default behavior while allowing overrides[2]

### Syntax
```csharp
public interface ILevelBuilder
{
    Level BuildLevel(int levelNumber);
    int Count { get; }
    
    // Default implementation
    Level[] BuildAllLevels()
    {
        Level[] levels = new Level[Count];
        for (int index = 1; index <= Count; index++)
            levels[index - 1] = BuildLevel(index);
        return levels;
    }
}
```

### Constraints
- **Cannot add instance fields** to interfaces[2]
- Can create protected and static methods[2]
- Static fields are allowed[2]

### Best Practices
- Use **sparingly** - interfaces should define boundaries, not implementation[2]
- Only when many classes implement the interface[2]
- Only when default implementation is nearly always correct[2]
- **Cannot solve all interface changes** (renaming/removing requires updating all implementations)[2]

## Design Patterns & Usage

### Interface as Role Definition
```csharp
// Interface defines the "level building" role
public interface ILevelBuilder
{
    Level BuildLevel(int levelNumber);
}

// Different implementations for different strategies
public class FileBasedLevelBuilder : ILevelBuilder { }
public class DatabaseLevelBuilder : ILevelBuilder { }  
public class RandomLevelBuilder : ILevelBuilder { }
```

### Client Code Benefits
```csharp
public void RunGame()
{
    ILevelBuilder levelBuilder = LocateLevelBuilder();
    int currentLevel = 1;
    
    while (true)
    {
        Level level = levelBuilder.BuildLevel(currentLevel);
        RunLevel(level);
        currentLevel++;
    }
}
```
- **Game logic doesn't care** which implementation is used[2]
- Easy to swap level sources by changing `LocateLevelBuilder()`[2]
- Maximum flexibility for future changes[2]

## Related Topics

### Links to Other OOP Concepts
- [[Inheritance]] - Interfaces work alongside inheritance
- [[Polymorphism]] - Interfaces enable polymorphic behavior  
- [[Encapsulation]] - Interfaces define public contracts
- [[Access Modifier]] - Interface members are implicitly public

### Common Interface Types in .NET
- `IEnumerable<T>` - Foundation for collections and foreach loops
- `IDisposable` - Resource cleanup pattern
- `IComparable<T>` - Object comparison and sorting

## Summary

**Interfaces are contracts that define what a class must do, without specifying how to do it. They provide maximum flexibility for swapping implementations and evolving code over time, making them essential for maintainable, extensible software design.**