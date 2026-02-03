# Java Constructors: Coming from C# 🏗️

Since you know C# constructors, I'll focus on **similarities**, **differences**, and **Java-specific features**!

---

## Quick Comparison: C# vs Java

| Feature                     | C#                                | Java                          |
| --------------------------- | --------------------------------- | ----------------------------- |
| **Syntax**                  | `public ClassName() { }`          | `public ClassName() { }`      |
| **Default constructor**     | Auto-generated if none exists     | Auto-generated if none exists |
| **Constructor chaining**    | `: this()` or `: base()`          | `this()` or `super()`         |
| **Primary constructor**     | ✅ C# 12+                          | ❌ Not available               |
| **Static constructor**      | ✅ `static ClassName() { }`        | ❌ Use static blocks           |
| **Property initialization** | `public int X { get; set; } = 5;` | Field initialization only     |
| **Record types**            | ✅ `record Person(string Name);`   | ❌ Use regular classes         |
| **Init-only properties**    | ✅ `{ get; init; }`                | ❌ Use `final` fields          |

---
## 1. Basic Constructor

### C#:
```csharp
public class SpaceShip
{
    private double x;
    private double y;
    
    // Constructor
    public SpaceShip(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}
```

### Java:
```java
public class SpaceShip {
    private double x;
    private double y;
    
    // Constructor - exactly the same!
    public SpaceShip(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
```

**Same in both languages!**

---
## 2. Default Constructor
### C#:
```csharp
public class SpaceShip
{
    private double x = 0;
    private double y = 0;
    
    // Optional: explicit default constructor
    public SpaceShip()
    {
    }
}

// Usage
var ship = new SpaceShip();
```

### Java:
```java
public class SpaceShip {
    private double x = 0;
    private double y = 0;
    
    // Optional: explicit default constructor
    public SpaceShip() {
    }
}

// Usage
SpaceShip ship = new SpaceShip();
```

**Same in both!**

**Important:** If you define **any** constructor, the default one is **not** auto-generated!

```java
public class SpaceShip {
    private double x, y;
    
    // Custom constructor
    public SpaceShip(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

// ❌ This won't compile!
SpaceShip ship = new SpaceShip(); // ERROR: no default constructor

// ✅ Must use the defined constructor
SpaceShip ship = new SpaceShip(100, 200);
```

---

## 3. Constructor Overloading

### C#:
```csharp
public class SpaceShip
{
    private double x, y;
    private string name;
    
    // Constructor 1
    public SpaceShip()
    {
        this.x = 0;
        this.y = 0;
        this.name = "Unknown";
    }
    
    // Constructor 2
    public SpaceShip(double x, double y)
    {
        this.x = x;
        this.y = y;
        this.name = "Unknown";
    }
    
    // Constructor 3
    public SpaceShip(double x, double y, string name)
    {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
```

### Java:
```java
public class SpaceShip {
    private double x, y;
    private String name;
    
    // Constructor 1
    public SpaceShip() {
        this.x = 0;
        this.y = 0;
        this.name = "Unknown";
    }
    
    // Constructor 2
    public SpaceShip(double x, double y) {
        this.x = x;
        this.y = y;
        this.name = "Unknown";
    }
    
    // Constructor 3
    public SpaceShip(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
```

**✅ Identical!**

---

## 4. Constructor Chaining: `this()`

### C#:
```csharp
public class SpaceShip
{
    private double x, y;
    private string name;
    private int lives;
    
    // Main constructor
    public SpaceShip(double x, double y, string name, int lives)
    {
        this.x = x;
        this.y = y;
        this.name = name;
        this.lives = lives;
    }
    
    // Calls main constructor
    public SpaceShip(double x, double y) : this(x, y, "Unknown", 3)
    {
    }
    
    // Calls other constructor
    public SpaceShip() : this(0, 0)
    {
    }
}
```

### Java:
```java
public class SpaceShip {
    private double x, y;
    private String name;
    private int lives;
    
    // Main constructor
    public SpaceShip(double x, double y, String name, int lives) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.lives = lives;
    }
    
    // Calls main constructor
    public SpaceShip(double x, double y) {
        this(x, y, "Unknown", 3); // MUST be first statement!
    }
    
    // Calls other constructor
    public SpaceShip() {
        this(0, 0); // MUST be first statement!
    }
}
```

**🔑 Key Difference:**

| C# | Java |
|-----|------|
| `: this()` **after** parameter list | `this()` **inside** constructor body |
| Can have code before chaining | `this()` **must be first statement** |

**C# allows:**
```csharp
public SpaceShip(double x, double y) : this(x, y, "Unknown", 3)
{
    Console.WriteLine("Constructor called"); // ✅ OK after chaining
}
```

**Java does NOT allow:**
```java
public SpaceShip(double x, double y) {
    System.out.println("Before"); // ❌ ERROR!
    this(x, y, "Unknown", 3); // Must be first!
}

public SpaceShip(double x, double y) {
    this(x, y, "Unknown", 3); // ✅ Must be first
    System.out.println("After"); // ✅ OK after this()
}
```

---
## 5. Calling Parent Constructor: `super()`
### C#:
```csharp
public class GameObject
{
    protected double x, y;
    
    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}

public class SpaceShip : GameObject
{
    private int lives;
    
    // Call parent constructor
    public SpaceShip(double x, double y, int lives) : base(x, y)
    {
        this.lives = lives;
    }
}
```

### Java:
```java
public class GameObject {
    protected double x, y;
    
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class SpaceShip extends GameObject {
    private int lives;
    
    // Call parent constructor
    public SpaceShip(double x, double y, int lives) {
        super(x, y); // MUST be first statement!
        this.lives = lives;
    }
}
```

**Key Differences:**

| C#                   | Java                        |
| -------------------- | --------------------------- |
| `: base()`           | `super()`                   |
| After parameter list | Inside constructor body     |
| -                    | **Must be first statement** |

**Important:** If you don't call `super()`, Java **automatically** calls `super()` (parent's no-arg constructor).

```java
public class SpaceShip extends GameObject {
    private int lives;
    
    public SpaceShip(int lives) {
        // Java automatically adds: super();
        // This calls GameObject's no-arg constructor
        this.lives = lives;
    }
}
```

**This will fail if parent has no default constructor:**
```java
public class GameObject {
    protected double x, y;
    
    // Only this constructor exists
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class SpaceShip extends GameObject {
    // ❌ ERROR! No GameObject() constructor found
    public SpaceShip() {
        // Java tries: super(); but it doesn't exist!
    }
    
    // ✅ Must explicitly call parent constructor
    public SpaceShip() {
        super(0, 0); // Now it works
    }
}
```

---

## 6️⃣ Constructor Order of Execution

### C#:
```csharp
public class GameObject
{
    public GameObject()
    {
        Console.WriteLine("1. GameObject constructor");
    }
}

public class SpaceShip : GameObject
{
    public SpaceShip()
    {
        Console.WriteLine("2. SpaceShip constructor");
    }
}

var ship = new SpaceShip();
// Output:
// 1. GameObject constructor
// 2. SpaceShip constructor
```

### Java:
```java
public class GameObject {
    public GameObject() {
        System.out.println("1. GameObject constructor");
    }
}

public class SpaceShip extends GameObject {
    public SpaceShip() {
        System.out.println("2. SpaceShip constructor");
    }
}

SpaceShip ship = new SpaceShip();
// Output:
// 1. GameObject constructor
// 2. SpaceShip constructor
```

**✅ Same execution order!**

---

## 7️⃣ Field Initialization Order

### C#:
```csharp
public class SpaceShip
{
    private int lives = 3;              // 1. Field initialization
    private string name = "Ship";       // 2. Field initialization
    
    public SpaceShip()                  // 3. Constructor body
    {
        Console.WriteLine($"Lives: {lives}, Name: {name}");
    }
}
```

### Java:
```java
public class SpaceShip {
    private int lives = 3;              // 1. Field initialization
    private String name = "Ship";       // 2. Field initialization
    
    public SpaceShip() {                // 3. Constructor body
        System.out.println("Lives: " + lives + ", Name: " + name);
    }
}
```

**✅ Same order!**

**Full initialization order:**
```
1. Parent field initialization
2. Parent constructor
3. Child field initialization  
4. Child constructor
```

**Example:**
```java
public class GameObject {
    private String type = "GameObject"; // 1. Parent field
    
    public GameObject() {
        System.out.println("2. GameObject constructor: " + type);
    }
}

public class SpaceShip extends GameObject {
    private int lives = 3; // 3. Child field
    
    public SpaceShip() {
        super(); // Called automatically
        System.out.println("4. SpaceShip constructor: " + lives);
    }
}

SpaceShip ship = new SpaceShip();
// Output:
// 2. GameObject constructor: GameObject
// 4. SpaceShip constructor: 3
```

---

## 8️⃣ Static Constructor vs Static Block

### C#:
```csharp
public class GameConfig
{
    public static int MaxPlayers;
    public static string ServerUrl;
    
    // Static constructor
    static GameConfig()
    {
        MaxPlayers = 10;
        ServerUrl = "game.server.com";
        Console.WriteLine("Static constructor called");
    }
}
```

### Java (No Static Constructor!):
```java
public class GameConfig {
    public static int maxPlayers;
    public static String serverUrl;
    
    // Static initialization block (Java's alternative)
    static {
        maxPlayers = 10;
        serverUrl = "game.server.com";
        System.out.println("Static block called");
    }
}
```

**🔑 Key Difference:** Java doesn't have static constructors, use **static blocks** instead!

**Multiple static blocks allowed:**
```java
public class GameConfig {
    static {
        System.out.println("First static block");
    }
    
    public static int value = 5;
    
    static {
        System.out.println("Second static block");
        value = 10;
    }
}
// Output:
// First static block
// Second static block
```

---
## Private Constructors (Singleton Pattern)

### C#:
```csharp
public class GameManager
{
    private static GameManager instance;
    
    // Private constructor
    private GameManager()
    {
        Console.WriteLine("GameManager created");
    }
    
    public static GameManager Instance
    {
        get
        {
            if (instance == null)
                instance = new GameManager();
            return instance;
        }
    }
}

// Usage
var manager = GameManager.Instance;
```

### Java:
```java
public class GameManager {
    private static GameManager instance;
    
    // Private constructor
    private GameManager() {
        System.out.println("GameManager created");
    }
    
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
}

// Usage
GameManager manager = GameManager.getInstance();
```

**Nearly identical!** Just method syntax differs.

**Better Java Singleton (thread-safe):**
```java
public class GameManager {
    // Initialized on class loading (thread-safe)
    private static final GameManager INSTANCE = new GameManager();
    
    private GameManager() {
        System.out.println("GameManager created");
    }
    
    public static GameManager getInstance() {
        return INSTANCE;
    }
}
```

---
## Copy Constructor

### C#:
```csharp
public class SpaceShip
{
    private double x, y;
    private int lives;
    
    public SpaceShip(double x, double y, int lives)
    {
        this.x = x;
        this.y = y;
        this.lives = lives;
    }
    
    // Copy constructor
    public SpaceShip(SpaceShip other)
    {
        this.x = other.x;
        this.y = other.y;
        this.lives = other.lives;
    }
}

// Usage
var original = new SpaceShip(100, 200, 3);
var copy = new SpaceShip(original);
```

### Java:
```java
public class SpaceShip {
    private double x, y;
    private int lives;
    
    public SpaceShip(double x, double y, int lives) {
        this.x = x;
        this.y = y;
        this.lives = lives;
    }
    
    // Copy constructor
    public SpaceShip(SpaceShip other) {
        this.x = other.x;
        this.y = other.y;
        this.lives = other.lives;
    }
}

// Usage
SpaceShip original = new SpaceShip(100, 200, 3);
SpaceShip copy = new SpaceShip(original);
```

**✅ Identical!**

---

## 1️⃣1️⃣ What C# Has That Java Doesn't

### ❌ Primary Constructors (C# 12+)

**C#:**
```csharp
// Primary constructor
public class SpaceShip(double x, double y, int lives)
{
    public void Display()
    {
        Console.WriteLine($"Ship at ({x}, {y}) with {lives} lives");
    }
}

var ship = new SpaceShip(100, 200, 3);
```

**Java:** Not available! Must use traditional constructors.

---

### ❌ Object Initializer Syntax

**C#:**
```csharp
var ship = new SpaceShip
{
    X = 100,
    Y = 200,
    Lives = 3
};
```

**Java:** Not available! Must set via constructor or setters.

```java
// Java way
SpaceShip ship = new SpaceShip();
ship.setX(100);
ship.setY(200);
ship.setLives(3);

// Or use constructor
SpaceShip ship = new SpaceShip(100, 200, 3);
```

---

### ❌ Required Members (C# 11+)

**C#:**
```csharp
public class SpaceShip
{
    public required string Name { get; init; }
    public required int Id { get; init; }
}

// Must initialize required members
var ship = new SpaceShip { Name = "Fighter", Id = 1 };
```

**Java:** Not available! Use constructor validation instead.

```java
public class SpaceShip {
    private final String name;
    private final int id;
    
    public SpaceShip(String name, int id) {
        if (name == null) {
            throw new IllegalArgumentException("Name is required");
        }
        this.name = name;
        this.id = id;
    }
}
```

---

### ❌ Records

**C#:**
```csharp
public record SpaceShip(double X, double Y, int Lives);

var ship = new SpaceShip(100, 200, 3);
```

**Java (Java 14+):** Has records, but different syntax!

```java
public record SpaceShip(double x, double y, int lives) { }

SpaceShip ship = new SpaceShip(100, 200, 3);
```

---

## 1️⃣2️⃣ Complete Game Example

```java
// Base class
public class GameObject {
    protected double x, y;
    protected boolean active;
    
    // Default constructor
    public GameObject() {
        this(0, 0);
    }
    
    // Main constructor
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.active = true;
        System.out.println("GameObject created at (" + x + ", " + y + ")");
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }
}

// Child class
public class SpaceShip extends GameObject {
    private int lives;
    private String name;
    private double velocityX, velocityY;
    
    // Constructor 1: Default
    public SpaceShip() {
        this(400, 300, "Unknown", 3);
    }
    
    // Constructor 2: Position only
    public SpaceShip(double x, double y) {
        this(x, y, "Unknown", 3);
    }
    
    // Constructor 3: Main constructor
    public SpaceShip(double x, double y, String name, int lives) {
        super(x, y); // Call parent constructor
        this.name = name;
        this.lives = lives;
        this.velocityX = 0;
        this.velocityY = 0;
        System.out.println("SpaceShip '" + name + "' created with " + lives + " lives");
    }
    
    // Constructor 4: Copy constructor
    public SpaceShip(SpaceShip other) {
        super(other.x, other.y);
        this.name = other.name + " (Copy)";
        this.lives = other.lives;
        this.velocityX = other.velocityX;
        this.velocityY = other.velocityY;
        System.out.println("SpaceShip copied");
    }
    
    public void display() {
        System.out.printf("Ship '%s' at (%.1f, %.1f) - Lives: %d%n", 
                         name, x, y, lives);
    }
    
    // Getters
    public int getLives() { return lives; }
    public String getName() { return name; }
}

// Usage
public class Game {
    public static void main(String[] args) {
        // Different ways to create SpaceShip
        SpaceShip ship1 = new SpaceShip();
        ship1.display();
        
        SpaceShip ship2 = new SpaceShip(200, 150);
        ship2.display();
        
        SpaceShip ship3 = new SpaceShip(100, 100, "Fighter", 5);
        ship3.display();
        
        SpaceShip ship4 = new SpaceShip(ship3);
        ship4.display();
    }
}
```

**Output:**
```
GameObject created at (400.0, 300.0)
SpaceShip 'Unknown' created with 3 lives
Ship 'Unknown' at (400.0, 300.0) - Lives: 3

GameObject created at (200.0, 150.0)
SpaceShip 'Unknown' created with 3 lives
Ship 'Unknown' at (200.0, 150.0) - Lives: 3

GameObject created at (100.0, 100.0)
SpaceShip 'Fighter' created with 5 lives
Ship 'Fighter' at (100.0, 100.0) - Lives: 5

GameObject created at (100.0, 100.0)
SpaceShip copied
Ship 'Fighter (Copy)' at (100.0, 100.0) - Lives: 5
```

---

## 📊 Quick Reference

| Feature | C# | Java |
|---------|-----|------|
| **Basic syntax** | Same | Same |
| **Default constructor** | Auto-generated | Auto-generated |
| **Overloading** | Supported | Supported |
| **Constructor chaining** | `: this()` | `this()` (first statement) |
| **Parent constructor** | `: base()` | `super()` (first statement) |
| **Static constructor** | ✅ | ❌ Use static blocks |
| **Private constructor** | Supported | Supported |
| **Copy constructor** | Supported | Supported |
| **Primary constructor** | ✅ C# 12+ | ❌ |
| **Object initializer** | ✅ | ❌ |
| **Required members** | ✅ C# 11+ | ❌ |

---

## 🎯 Key Takeaways

1. ✅ **Most constructor concepts are the same** between C# and Java
2. ✅ **Main difference:** `this()` and `super()` placement (must be first in Java)
3. ✅ **Java uses static blocks** instead of static constructors
4. ✅ **No object initializers** in Java - use constructors or setters
5. ✅ **No primary constructors** in Java (yet!)

You already know 90% of Java constructors from C#! The syntax is almost identical! 🚀