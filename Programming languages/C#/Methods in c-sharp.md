**Expected to know:** [[Functions]] [[Syntax of C-sharp]] [[C-sharp Programming Language]](till now strictly)
**Topics Covered:**
**Tags:** [[Functions]], [[Methods(or Behavior)]]

![[Syntax of C-sharp#**Methods (Functions)**]]

Considering the C Programming Language is already done, the logic behind declaring function here is also same, but functions here are not exactly what we saw previously.
The Functions here are always a part of [[Class or Type]], and are called `methods`, even though the terms functions and methods are used exchangeably by many, but formally speaking there is a subtle difference between the two.

# Making a method
- A _method_ in C# is a block of code that performs a specific task and only runs when called.
- Methods help you organize code, promote reuse, and improve readability—just like functions in C.
- In C#, methods must be declared inside a class or struct.

## Basic Syntax
Basic syntax for making a method is:
```csharp
<AccessModifier> <ReturnType> <MethodName>(<ParameterList>) 
{ 
	// Method body 
}
```
There is only one thing that is new to us when it comes to making a method, i.e., [[Access Modifier]]. 
Also you might have seen the `static` keyword in every function when it is written inside our program class, that is because static means something of the class itself, it does not require one to create instance of the class to call the method.

## Method Signature 
A **method signature** is the unique identifier that a compiler uses to distinguish one method from another within a class.

It consists of exactly two things:

1. **The method's name.**
2. **The parameter list** (the number of parameters, their data types, and the order they appear).

**Crucially, a method signature does NOT include the return type or access modifiers** (`public`, `private`, `static`, etc.).

The primary reason method signatures matter is for **Method Overloading**—allowing you to create multiple methods with the exact same name, as long as their signatures (the parameter lists) are different.

### Examples of Method Signatures

Here is a base method. Its signature is **`Calculate(int, int)`**:

```C#
public int Calculate(int a, int b) { ... }
```

Here are valid overloads. The compiler allows these because their signatures are different from the base method:

**1. Different parameter types:**

Signature: **`Calculate(double, double)`**

```C#
public double Calculate(double a, double b) { ... }
```

**2. Different number of parameters:**

Signature: **`Calculate(int, int, int)`**

```C#
public int Calculate(int a, int b, int c) { ... }
```

**3. Different order of parameter types:**

Signature: **`Display(string, int)`** vs **`Display(int, string)`**

```C#
public void Display(string name, int age) { ... }
public void Display(int age, string name) { ... } 
```

### Example of what fails (The Return Type Trap)

The following will cause a **compiler error** if you try to put it in the same class as the base method:

```C#
public double Calculate(int a, int b) { ... } // ERROR
```

Even though the return type is `double` instead of `int`, the compiler ignores the return type when checking signatures. It only sees **`Calculate(int, int)`**, realizes you already have a method with that exact signature, and throws an error.

## local method
When we declare a method inside another method inside a class, then that method is what we call a `local method`.
for example:
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
            void printHello()
            {
                Console.WriteLine("Hello There");
            }
        }
    }
}
```

## Method Overloading and method group
Each method you create should get a unique name that describes what it does. However, sometimes you have two methods that do essentially the same job, just with slightly different parameters. 
Two methods can share a name as long as their parameter lists are different. Sharing a name is called **method overloading, or simply overloading**, and people call the various methods by the same name overloads.

The most common example is **WriteLine()** method.
```csharp
Console.WriteLine("Welcome to my evil lair!");
Console.WriteLine(42);
```

There is a version of WriteLine with a string parameter and one with an int parameter. When the compiler encounters a method call to an overloaded method, it must figure out which overload to use. 
This process is called overload resolution. It is a complex topic, full of nuance for tricky situations, but the simple version is that it can usually tell which one you want from the types and number of arguments. 
	- When we write `Console.WriteLine(42)`, the compiler picks the version of `WriteLine` with a single int parameter.

`Console.WriteLine` *has a total of 18 different overloads.* Most have a single parameter, each with a different type (string, int, float, bool, etc.), but there is also an overload with no parameters (`Console.WriteLine()`) that just moves to the following line. The set of all overloads of a method name is called a **method group**.

#### Methods should have different signatures as well:
A method's **signature** = **name + parameter types** (return type is NOT part of it).

This is why you **can't overload** a method just by changing the return type:

```csharp
int GetValue() { ... }
string GetValue() { ... }  // ❌ Compile error — same signature
```

The compiler considers these identical signatures and rejects it.

## Passing by Reference 
In C#, you can pass parameters by reference using the `ref`, `out`, or `in` keywords. This allows a method to modify the value of the argument in the calling scope, rather than working on a copy.

---
## How to Pass Parameters by Reference
### 1. Using the `ref` Keyword
- **Purpose:** Allows the method to read and modify the caller's variable.
- **Requirement:** The variable must be initialized before being passed.

**Syntax:**
```csharp
void MyMethod(ref int x) 
{     
	x = x + 10; 
} 

int num = 5; 
MyMethod(ref num); // num is now 15
```
Both the method definition and the call must use `ref`.

---
### 2. Using the `out` Keyword
- **Purpose:** Used when the method is expected to assign a value to the parameter.
- **Requirement:** The variable does not need to be initialized before being passed, but the method must assign it a value.

**Syntax:**
```csharp
void GetValues(out int x, out int y) 
{     
	x = 1;    
	y = 2; 
} 

int a, b; 

GetValues(out a, out b); 
// a is 1, b is 2
```
Both the method and call must use `out`.

---
### 3. Using the `in` Keyword
- **Purpose:** Passes the parameter by reference, but as read-only. The method cannot modify the value.
- **Requirement:** The variable must be initialized before being passed.

**Syntax:**
```csharp
void PrintValue(in int x) 
{     
	Console.WriteLine(x); 
} 

int num = 10; 

PrintValue(in num); // Prints 10, cannot modify num inside method
```
Both the method and call must use `in`.

---
### Example: Swapping Values with `ref`
```csharp
void Swap(ref int a, ref int b) 
{     
	int temp = a;    
	a = b;    
	b = temp; 
} 

int x = 100, y = 200; 
Swap(ref x, ref y); // x is now 200, y is now 100
```
The changes to `x` and `y` inside `Swap` are reflected outside because they are passed by reference

---
## Lambda Expressions (The Arrow Functions)
In C#, the "arrow function" refers to a _lambda expression_, which is a concise way to define anonymous functions using the `=>` operator (called the lambda operator).

Lambda expressions are widely used for in-line, short-lived functions, especially in LINQ queries, delegates, and event handling.

### Syntax of Lambda Expressions
There are two main forms of lambda expressions in C#:
- **Expression Lambda**: Contains a single expression, **implicitly returns its result.** Can also be used for methods, while being used for delegates and anonymous functions.
    ```csharp
    (parameters) => expression
	```
    **Example**:
    ```csharp
    x => x * x      
    // Squares the input 
    (x, y) => x + y 
    // Adds two numbers
	```
- **Statement Lambda**: Contains a block of statements, requires braces `{}` and **explicit `return` if a value is returned**. Is only used for delegates or anonymous functions.
    ```csharp
    (parameters) => { statements }
	```
    **Example**:
    ```csharp
    x => { return x * x; } 
    (x, y) => { int sum = x + y; return sum; }
	```

---
### How Lambda Expressions Work
- The left side of the `=>` operator defines the input parameters.
- The right side defines the body, which can be a single expression or a statement block.
Example:
```csharp
public int add(int a, int b)=> a+b;
public int multiply(int a) => {a = a*a; return a;} //not allowed, as cannot use code blocks in expression-bodied members.
```

---
# Delegates

### What is a Delegate?

A **delegate** is a type that holds a reference to a method. Think of it as a **variable that stores a method** instead of a value.

- Delegates are type-safe (the method signature must match the delegate signature)
- They enable **callbacks**, **higher-order functions**, and are the backbone of **events**

>When people say _"the method must match the delegate's signature"_, they're using **signature loosely** to mean **"the method must match in both parameters AND return type"**.
>**Analogy:** A delegate is like a function pointer in C/C++, but safer and object-oriented.

---

### Declaring a Delegate

```csharp
// Syntax
delegate <return-type> <DelegateName>(<parameters>);

// Example
delegate int MathOperation(int a, int b);
```

This declares a delegate that can point to **any method** that takes two `int` parameters and returns an `int`.

---

### Using a Delegate

```csharp
delegate int MathOperation(int a, int b);

class Program
{
    static int Add(int a, int b) => a + b;
    static int Multiply(int a, int b) => a * b;

    static void Main()
    {
        MathOperation op = Add;        // point to Add
        Console.WriteLine(op(3, 4));   // Output: 7

        op = Multiply;                 // reassign to Multiply
        Console.WriteLine(op(3, 4));   // Output: 12
    }
}
```

---

### Multicast Delegates

A delegate can hold **multiple methods** at once using `+=`. All methods are called in order.

```csharp
delegate void Notify(string message);

class Program
{
    static void Logger(string msg) => Console.WriteLine($"[LOG]: {msg}");
    static void Alerter(string msg) => Console.WriteLine($"[ALERT]: {msg}");

    static void Main()
    {
        Notify notify = Logger;
        notify += Alerter;      // now holds two methods

        notify("System started");
        // Output:
        // [LOG]: System started
        // [ALERT]: System started

        notify -= Logger;       // remove Logger
        notify("Only alert now");
        // Output:
        // [ALERT]: Only alert now
    }
}
```

> If a multicast delegate has a **return type**, only the **last method's** return value is used. Avoid multicast delegates with return values.

---

### Built-in Generic Delegates

You rarely need to declare custom delegates. C# provides three built-in ones:

|Delegate|Signature|Use When|
|---|---|---|
|`Action<T>`|Takes params, returns `void`|You want to _do_ something|
|`Func<T, TResult>`|Takes params, returns a value|You want to _compute_ something|
|`Predicate<T>`|Takes one param, returns `bool`|You want to _test_ something|

```csharp
// Action — no return value
Action<string> greet = name => Console.WriteLine($"Hello, {name}!");
greet("Alice");  // Hello, Alice!

// Func — returns a value
Func<int, int, int> add = (a, b) => a + b;
Console.WriteLine(add(5, 3));  // 8

// Predicate — returns bool
Predicate<int> isEven = n => n % 2 == 0;
Console.WriteLine(isEven(4));  // True
```

---

### Anonymous Methods & Lambda Expressions

Instead of defining a named method, you can write the logic inline.

```csharp
// Anonymous method (older syntax)
Func<int, int> square = delegate(int x) { return x * x; };

// Lambda expression (modern, preferred)
Func<int, int> square = x => x * x;

Console.WriteLine(square(5));  // 25
```

---

### Practice — Delegates

**Q1.** Create a delegate `StringTransform` that takes a `string` and returns a `string`. Assign it a method that converts the string to uppercase. Call it with `"hello"`.

**Q2.** Use `Func<double, double>` to store a method that computes the square root of a number. Call it with `144`.

**Q3.** Create a multicast `Action<string>` delegate with two methods — one that prints the string in uppercase, and one that prints its length. Call the delegate with `"delegate"`.

Answers

```csharp
// Q1
delegate string StringTransform(string s);
StringTransform toUpper = s => s.ToUpper();
Console.WriteLine(toUpper("hello")); // HELLO

// Q2
Func<double, double> sqrt = Math.Sqrt;
Console.WriteLine(sqrt(144)); // 12

// Q3
Action<string> actions = s => Console.WriteLine(s.ToUpper());
actions += s => Console.WriteLine(s.Length);
actions("delegate");
// DELEGATE
// 8
```

---

# EVENTS
## Chapter 1: The Problem Events Solve

Before learning events, understand **why they exist**. If you skip this, events feel like pointless complexity.

### The Scenario

You're making a game. A ship explodes. When that happens:

- The sound system plays an explosion sound
- The UI updates the score
- The camera shakes
- The particle system spawns debris
- The achievement system checks if a trophy should unlock

How does the `Ship` class notify all of these?

---

### Option 1: Ship Calls Everything Directly

```csharp
class Ship
{
    SoundManager sound;
    UIManager ui;
    Camera cam;
    ParticleSystem particles;
    AchievementSystem achievements;

    void Explode()
    {
        sound.PlayExplosion();
        ui.UpdateScore();
        cam.Shake();
        particles.SpawnDebris();
        achievements.Check("firstKill");
        // add more here every time something new cares about explosions
    }
}
```

**Problems:**

- `Ship` must know about EVERY class that cares about explosions
- Every time a new system needs to react, you modify `Ship`
- `Ship` becomes tightly tangled with everything
- Nothing can react to a ship explosion without modifying `Ship`

---

### Option 2: Events — The Clean Way

```csharp
class Ship
{
    public event Action ShipExploded; // Ship just announces it happened

    void Explode()
    {
        ShipExploded?.Invoke(); // fire and forget — Ship doesn't know who's listening
    }
}
```

```csharp
// Each system subscribes on its own — Ship doesn't know or care
soundManager.Subscribe(ship);
uiManager.Subscribe(ship);
camera.Subscribe(ship);
// add 10 more systems — Ship.cs never changes
```

**Ship announces. Everyone who cares listens. Ship knows nothing about them.**

That's the entire philosophy of events.

---

## Chapter 2: The Foundation — Why Delegates Are Necessary

You might ask: why not just use a `List<Method>` to store subscribers? Why do we need delegates at all?

Because **a method is not a type**.

### Methods Have No Type On Their Own

```csharp
void PlaySound(string name) { ... }
void UpdateScore(string name) { ... }
void DoSomething(int number) { ... }
```

These three methods exist. But what TYPE are they?

In C#, you cannot write:

```csharp
List<???> subscribers = new List<???>();
subscribers.Add(PlaySound);  // ??? what type is PlaySound?
```

There is no built-in type for "a method that takes a string and returns void." C# has `int`, `string`, `bool`, `float` — but no built-in type for method shapes.

### A Delegate Creates That Missing Type

```csharp
delegate void NotifyHandler(string message);
```

You just created a new type. `NotifyHandler` is now a type in your program, just like `int` or `string`. It represents: **any method that takes a string and returns void**.

Now you can store methods:

```csharp
NotifyHandler handler = PlaySound;   // valid — PlaySound matches the shape
NotifyHandler handler2 = UpdateScore; // valid — same shape
NotifyHandler handler3 = DoSomething; // ❌ invalid — different shape (takes int)
```

A delegate is literally just the answer to: **"what type should I use to store this method?"**

### A Multicast Delegate IS The List

When you do `handler += handler2`, the delegate doesn't store just one method anymore — it stores both. That's called a multicast delegate, and it IS the subscriber list you wanted.

```csharp
NotifyHandler allHandlers = PlaySound;
allHandlers += UpdateScore;
allHandlers += ShakeCamera;

allHandlers("explosion");
// Calls: PlaySound("explosion")
// Calls: UpdateScore("explosion")
// Calls: ShakeCamera("explosion")
// All three, in order, with the same argument
```

**The delegate is the list. There is no simpler foundation.**

If you tried to build `List<SomeMethodType>`, you'd need to define `SomeMethodType` first. The moment you define it, you've defined a delegate. You went in a circle. Delegates are the primitive.

---

## Chapter 3: Why Subscribers Must Match the Delegate — The Deep Why

This is the most important rule of events and you deserve the full explanation.

### Invoke Passes Fixed Arguments to Everyone

When an event fires, it calls every subscriber with **the exact same arguments**:

```csharp
delegate void ClickHandler(string message);
event ClickHandler OnClick;

OnClick?.Invoke("Button was clicked");
// C# does this internally:
// subscriber1("Button was clicked")
// subscriber2("Button was clicked")
// subscriber3("Button was clicked")
```

C# has **one set of arguments**. It passes that same set to every method in the list.

### Why Different Shapes Are Impossible

```csharp
void HandleString(string msg) { ... }    // expects a string
void HandleNumber(int number) { ... }    // expects an int

// If both could subscribe to OnClick (which passes a string):

OnClick?.Invoke("Button was clicked");
// subscriber1("Button was clicked") → ✅ HandleString can accept this
// subscriber2("Button was clicked") → ❌ HandleNumber cannot accept a string
```

You cannot pass `"Button was clicked"` to a method that expects an `int`. This isn't a design decision that could have been made differently. It's a fundamental constraint: **you cannot call a method with arguments it doesn't accept.**

The type system enforces this at compile time. The error appears before you ever run the program.

### What "Matching" Means Exactly

For a method to subscribe to an event based on `delegate void ClickHandler(string message)`:

|Requirement|Reason|
|---|---|
|Parameters must match in type and order|Invoke passes these exact arguments|
|Return type must match|The delegate's return type is part of its contract|
|Parameter count must match|Can't pass 1 arg to a 2-param method|

```csharp
delegate void ClickHandler(string message);

// ✅ Perfect match
void Handle(string msg) { }

// ❌ Wrong parameter type
void Handle(int msg) { }

// ❌ Extra parameter
void Handle(string msg, int id) { }

// ❌ Missing parameter
void Handle() { }

// ❌ Wrong return type (delegate returns void, this returns string)
string Handle(string msg) { return msg; }
```

---

## Chapter 4: Events — The Protected Delegate

Now you understand delegates. An event is just a delegate with **access control** on top.

### What a Plain Public Delegate Allows (Too Much)

```csharp
class Button
{
    public Action Clicked; // plain delegate, no 'event' keyword
}

class SomeOtherClass
{
    void DoStuff(Button btn)
    {
        btn.Clicked += MyMethod;       // ✅ subscribe — fine
        btn.Clicked -= MyMethod;       // ✅ unsubscribe — fine

        btn.Clicked();                 // 🚨 fire the event from outside — dangerous
        btn.Clicked = null;            // 🚨 wipe ALL subscribers — dangerous
        btn.Clicked = SomeOtherMethod; // 🚨 replace ALL subscribers — dangerous
    }
}
```

Any random class can fire the event whenever it wants. Any class can nuke the entire subscriber list. This is a problem.

### What `event` Keyword Locks Down

```csharp
class Button
{
    public event Action Clicked; // 'event' keyword added
}

class SomeOtherClass
{
    void DoStuff(Button btn)
    {
        btn.Clicked += MyMethod;       // ✅ still allowed
        btn.Clicked -= MyMethod;       // ✅ still allowed

        btn.Clicked();                 // ❌ compile error
        btn.Clicked = null;            // ❌ compile error
        btn.Clicked = SomeOtherMethod; // ❌ compile error
    }
}

class Button // only the owner can fire or reset
{
    public event Action Clicked;

    void Press()
    {
        Clicked?.Invoke(); // ✅ Owner firing its own event — always legal
        Clicked = null;    // ✅ Owner resetting — allowed (though rare)
    }
}
```

The `event` keyword says: **only the class that declared this event can invoke or reassign it. Everyone else can only subscribe and unsubscribe.**

### The Critical Clarification — Calling a Method is Not Firing an Event

This was the source of confusion earlier. Read carefully:

```csharp
class Button
{
    public event Action Clicked;

    public void Press() // regular public method
    {
        Clicked?.Invoke(); // Button fires its OWN event internally — legal
    }
}

class Program
{
    static void Main()
    {
        Button btn = new Button();

        btn.Press();           // ✅ You're calling a PUBLIC METHOD on Button
                               // That method happens to fire the event internally
                               // You never touched the event directly — you touched the method

        btn.Clicked?.Invoke(); // ❌ COMPILE ERROR — you grabbed the event itself and tried to fire it
        btn.Clicked = null;    // ❌ COMPILE ERROR — you tried to reset it from outside
    }
}
```

`btn.Press()` → you called a method. Legal. `btn.Clicked?.Invoke()` → you reached inside and grabbed the event itself. Blocked.

These are completely different things. The event protection is about **direct access to the event member itself**, not about what the owner's methods do internally.

---

## Chapter 5: How Events Work — Complete Mechanics

### What Invoke Does, Step by Step

```csharp
class Alarm
{
    public event EventHandler AlarmRaised;

    public void TriggerAlarm()
    {
        AlarmRaised?.Invoke(this, EventArgs.Empty);
    }
}
```

When `AlarmRaised?.Invoke(this, EventArgs.Empty)` runs:

1. **`?.`** — checks if anyone has subscribed. If the event is null (zero subscribers), stop. Do nothing. No crash.
2. **`Invoke`** — tells the delegate: call every method in your list
3. **`this`** — passes the `Alarm` object as the first argument (who fired it)
4. **`EventArgs.Empty`** — passes empty event data as the second argument
5. Every subscribed method gets called, in the order they subscribed, with those two arguments

### What Subscribers Receive

Whatever you pass to `Invoke` — subscribers receive exactly that.

```csharp
AlarmRaised?.Invoke(this, EventArgs.Empty);
//                  ^^^^  ^^^^^^^^^^^^^^
//                   |         |
//                   |         └─ subscriber's 'e' parameter = EventArgs.Empty
//                   └─ subscriber's 'sender' parameter = this (the Alarm object)

// So this subscriber:
void OnAlarm(object sender, EventArgs e)
{
    // sender = the Alarm object
    // e = EventArgs.Empty

    Alarm alarm = (Alarm)sender; // cast if you need to access Alarm-specific stuff
}
```

---

## Chapter 6: The EventHandler Pattern — The Standard Way

Declaring a custom delegate every time is repetitive. C# has a built-in delegate that covers almost all cases:

```csharp
// Built into C# — you don't write this, it already exists:
public delegate void EventHandler(object sender, EventArgs e);

// Generic version for when you need to pass data:
public delegate void EventHandler<TEventArgs>(object sender, TEventArgs e);
```

**Always prefer these over declaring your own delegate for events.**

### Two Params — Always The Same Two

Every EventHandler-based subscriber always gets:

- `object sender` — who fired the event. Cast it to the actual type if needed.
- `EventArgs e` — data about what happened. `EventArgs.Empty` if nothing to send.

### Basic EventHandler — No Data

```csharp
class Door
{
    public event EventHandler Opened;

    public void Open()
    {
        Console.WriteLine("Door opens.");
        Opened?.Invoke(this, EventArgs.Empty); // no data to send
    }
}

class SecuritySystem
{
    public void OnDoorOpened(object sender, EventArgs e)
    {
        // sender is the Door object
        Console.WriteLine("Security: Door open detected.");
    }
}

class Program
{
    static void Main()
    {
        Door door = new Door();
        SecuritySystem security = new SecuritySystem();

        door.Opened += security.OnDoorOpened;
        door.Open();
        // Door opens.
        // Security: Door open detected.
    }
}
```

### EventHandler With Data — Custom EventArgs

When you need to send information with the event, create a class that inherits `EventArgs`:

```csharp
// Step 1: Create your data carrier
class DamageEventArgs : EventArgs
{
    public int DamageAmount { get; set; }
    public string DamageType { get; set; }
    public int RemainingHealth { get; set; }
}

// Step 2: Publisher uses EventHandler<T>
class Player
{
    private int _health = 100;

    public event EventHandler<DamageEventArgs> DamageTaken;

    public void TakeDamage(int amount, string type)
    {
        _health -= amount;

        DamageTaken?.Invoke(this, new DamageEventArgs
        {
            DamageAmount = amount,
            DamageType = type,
            RemainingHealth = _health
        });

        if (_health <= 0)
            Console.WriteLine("Player died.");
    }
}

// Step 3: Subscriber reads from the EventArgs
class HUDManager
{
    public void OnDamageTaken(object sender, DamageEventArgs e)
    {
        Console.WriteLine($"HUD: -{e.DamageAmount} {e.DamageType} | HP: {e.RemainingHealth}");
    }
}

class SoundManager
{
    public void OnDamageTaken(object sender, DamageEventArgs e)
    {
        if (e.DamageType == "Fire")
            Console.WriteLine("Sound: *fire damage sound*");
        else
            Console.WriteLine("Sound: *generic hit sound*");
    }
}

class Program
{
    static void Main()
    {
        Player player = new Player();
        HUDManager hud = new HUDManager();
        SoundManager sound = new SoundManager();

        player.DamageTaken += hud.OnDamageTaken;
        player.DamageTaken += sound.OnDamageTaken;

        player.TakeDamage(25, "Fire");
        // HUD: -25 Fire | HP: 75
        // Sound: *fire damage sound*

        player.TakeDamage(10, "Bullet");
        // HUD: -10 Bullet | HP: 65
        // Sound: *generic hit sound*
    }
}
```

---

## Chapter 7: What Methods Can Be Subscribers

Any method whose signature matches the delegate. The method can be:

```csharp
public event EventHandler MyEvent;

// 1. Instance method of another object
door.MyEvent += someObject.MethodName;

// 2. Static method
door.MyEvent += MyClass.StaticMethod;

// 3. Method of the same class
door.MyEvent += this.HandleIt;

// 4. Lambda expression — most common in modern C#
door.MyEvent += (sender, e) => Console.WriteLine("happened");

// 5. Anonymous method — older syntax
door.MyEvent += delegate(object s, EventArgs e) { Console.WriteLine("happened"); };

// 6. Stored lambda — use this when you need to unsubscribe later
EventHandler handler = (s, e) => Console.WriteLine("happened");
door.MyEvent += handler;
door.MyEvent -= handler; // works because same reference
```

---

## Chapter 8: Subscribing, Unsubscribing, and Memory Leaks

### Subscribing and Unsubscribing

```csharp
door.Opened += OnDoorOpened;  // subscribe
door.Opened -= OnDoorOpened;  // unsubscribe — exact same reference
```

### The Lambda Unsubscribe Gotcha

```csharp
// ❌ This unsubscribe silently FAILS
door.Opened += (s, e) => Console.WriteLine("hi");
door.Opened -= (s, e) => Console.WriteLine("hi"); // different object — does nothing

// ✅ Store the lambda first
EventHandler handler = (s, e) => Console.WriteLine("hi");
door.Opened += handler;
door.Opened -= handler; // same reference — works
```

Every lambda expression creates a new object. Two lambdas with identical code are still two different objects. `+=` adds one, `-=` looks for that exact object and finds nothing.

### Memory Leaks

When object B subscribes to object A's event, **A holds a reference to B** through the delegate. The garbage collector sees that A references B, so B cannot be collected — even if nothing else in your program uses B anymore.

```csharp
// ❌ Memory leak scenario
void Setup()
{
    var button = globalButton; // long-lived object
    var popup = new Popup();   // should be short-lived

    button.Clicked += popup.HandleClick; // button now holds popup alive forever

    // popup goes out of scope here, but it's NOT collected
    // button's event still references it
}

// ✅ Fix: unsubscribe when done
class Popup
{
    Button _button;

    public Popup(Button btn)
    {
        _button = btn;
        _button.Clicked += HandleClick;
    }

    public void Close()
    {
        _button.Clicked -= HandleClick; // release the reference
    }

    void HandleClick(object s, EventArgs e) { ... }
}
```

---

## Chapter 9: Multiple Subscribers

An event can have many subscribers. They all fire in subscription order.

```csharp
class Engine
{
    public event EventHandler Started;

    public void Start()
    {
        Console.WriteLine("Engine starting...");
        Started?.Invoke(this, EventArgs.Empty);
    }
}

class Program
{
    static void Main()
    {
        Engine engine = new Engine();

        engine.Started += (s, e) => Console.WriteLine("Dashboard: lights on");
        engine.Started += (s, e) => Console.WriteLine("AC: starting");
        engine.Started += (s, e) => Console.WriteLine("Radio: turning on");

        engine.Start();
        // Engine starting...
        // Dashboard: lights on
        // AC: starting
        // Radio: turning on
    }
}
```

The engine knows nothing about dashboards, AC, or radios. It just fires. They all react.

---

## Chapter 10: Events in Unity

Unity uses C# events, but also provides its own `UnityEvent` system. You need to know both.

---

### Option A: Pure C# Events in Unity

This is just standard C# events inside MonoBehaviour classes.

```csharp
// PlayerHealth.cs
using UnityEngine;
using System;

public class PlayerHealth : MonoBehaviour
{
    public event EventHandler<DamageEventArgs> OnDamageTaken;
    public event EventHandler OnPlayerDied;

    private int _health = 100;

    public void TakeDamage(int amount)
    {
        _health -= amount;

        OnDamageTaken?.Invoke(this, new DamageEventArgs { Amount = amount, Remaining = _health });

        if (_health <= 0)
        {
            OnPlayerDied?.Invoke(this, EventArgs.Empty);
        }
    }
}

public class DamageEventArgs : EventArgs
{
    public int Amount { get; set; }
    public int Remaining { get; set; }
}
```

```csharp
// UIManager.cs
using UnityEngine;

public class UIManager : MonoBehaviour
{
    [SerializeField] private PlayerHealth playerHealth; // drag in inspector

    private void OnEnable()
    {
        // subscribe when this object becomes active
        playerHealth.OnDamageTaken += UpdateHealthBar;
        playerHealth.OnPlayerDied += ShowDeathScreen;
    }

    private void OnDisable()
    {
        // ALWAYS unsubscribe when disabled — Unity destroys objects but
        // the event reference can linger and cause null ref errors or leaks
        playerHealth.OnDamageTaken -= UpdateHealthBar;
        playerHealth.OnPlayerDied -= ShowDeathScreen;
    }

    private void UpdateHealthBar(object sender, DamageEventArgs e)
    {
        Debug.Log($"Health bar: {e.Remaining}/100");
        // update your UI slider here
    }

    private void ShowDeathScreen(object sender, EventArgs e)
    {
        Debug.Log("Showing death screen");
    }
}
```

```csharp
// SoundManager.cs — another subscriber to the same events
public class SoundManager : MonoBehaviour
{
    [SerializeField] private PlayerHealth playerHealth;

    private void OnEnable()
    {
        playerHealth.OnDamageTaken += PlayHitSound;
        playerHealth.OnPlayerDied += PlayDeathSound;
    }

    private void OnDisable()
    {
        playerHealth.OnDamageTaken -= PlayHitSound;
        playerHealth.OnPlayerDied -= PlayDeathSound;
    }

    private void PlayHitSound(object sender, DamageEventArgs e)
    {
        Debug.Log("*hit sound*");
    }

    private void PlayDeathSound(object sender, EventArgs e)
    {
        Debug.Log("*death sound*");
    }
}
```

`PlayerHealth` knows nothing about `UIManager` or `SoundManager`. Both of them subscribe and react independently.

---

### Option B: Static Events — For Global Game Events

When objects don't have a reference to each other, static events are useful.

```csharp
// GameEvents.cs — a central hub of events
public static class GameEvents
{
    public static event EventHandler<int> OnScoreChanged;  // int = new score
    public static event EventHandler OnGameOver;
    public static event EventHandler<string> OnLevelLoaded; // string = level name

    // Raise methods — called by publishers
    public static void RaiseScoreChanged(object sender, int newScore)
        => OnScoreChanged?.Invoke(sender, newScore);

    public static void RaiseGameOver(object sender)
        => OnGameOver?.Invoke(sender, EventArgs.Empty);

    public static void RaiseLevelLoaded(object sender, string levelName)
        => OnLevelLoaded?.Invoke(sender, levelName);
}
```

> Note: `EventHandler<int>` works but `int` isn't an `EventArgs`. Some teams use `EventHandler<int>` for simplicity, others always subclass `EventArgs`. Both compile fine.

```csharp
// EnemyKill.cs — publisher
public class Enemy : MonoBehaviour
{
    private int _pointValue = 100;

    public void Die()
    {
        GameEvents.RaiseScoreChanged(this, _pointValue);
    }
}

// ScoreDisplay.cs — subscriber (doesn't know anything about Enemy)
public class ScoreDisplay : MonoBehaviour
{
    private int _totalScore = 0;

    private void OnEnable()
        => GameEvents.OnScoreChanged += HandleScoreChanged;

    private void OnDisable()
        => GameEvents.OnScoreChanged -= HandleScoreChanged;

    private void HandleScoreChanged(object sender, int points)
    {
        _totalScore += points;
        Debug.Log($"Score: {_totalScore}");
    }
}
```

---

### Option C: UnityEvent — Inspector-Wired Events

`UnityEvent` is Unity's own event system. The difference from C# events: **you can wire subscribers in the Inspector without writing code**.

```csharp
// Button.cs
using UnityEngine;
using UnityEngine.Events;

public class Button : MonoBehaviour
{
    public UnityEvent OnClick; // shows up in Inspector — drag methods in

    private void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            OnClick.Invoke(); // fire it — same idea, different system
        }
    }
}
```

In the Unity Inspector, you can drag any GameObject and select any public method to call when `OnClick` fires — without a single line of subscription code.

**UnityEvent with data:**

```csharp
public UnityEvent<int> OnHealthChanged; // generic version
OnHealthChanged.Invoke(75);
```

### C# Events vs UnityEvent — When to Use Which

||C# Events|UnityEvent|
|---|---|---|
|Set up in|Code|Code or Inspector|
|Performance|Faster|Slightly slower|
|Visible in Inspector|❌ No|✅ Yes|
|Good for|Core game logic|UI, designer-facing hooks|
|Type safety|✅ Strong|✅ Good|
|Debugging|Harder|Easier (visible in Inspector)|

In practice: use C# events for gameplay/logic systems, UnityEvent for UI and anything designers configure.

---

## Chapter 11: The Full Mental Model

```
DELEGATE
   └─ A type you define that describes the "shape" of a method
   └─ Can hold multiple methods (multicast)
   └─ IS the subscriber list

EVENT
   └─ A delegate with a lock on it
   └─ Outsiders can only += and -=
   └─ Only the owner can Invoke or reset
   └─ Fires all subscribers with the same arguments

PUBLISHER
   └─ Declares the event
   └─ Calls Invoke when something happens
   └─ Knows nothing about subscribers

SUBSCRIBER
   └─ Has a method matching the delegate's shape
   └─ Subscribes with +=
   └─ Unsubscribes with -= when done
   └─ Receives sender + event data from Invoke
```

---

## Chapter 12: Complete Reference

```csharp
// ─── DECLARING ──────────────────────────────────────────────
// No data:
public event EventHandler MyEvent;

// With data (preferred):
public event EventHandler<MyArgs> MyEvent;

// Custom delegate (avoid unless special reason):
public delegate void MyDelegate(string msg);
public event MyDelegate MyEvent;


// ─── CUSTOM EVENTARGS ────────────────────────────────────────
class MyArgs : EventArgs
{
    public string Info { get; set; }
    public int Value { get; set; }
}


// ─── FIRING (inside the owner class only) ────────────────────
MyEvent?.Invoke(this, EventArgs.Empty);          // no data
MyEvent?.Invoke(this, new MyArgs { Info = "x" }); // with data


// ─── SUBSCRIBING ─────────────────────────────────────────────
obj.MyEvent += MethodName;                       // named method
obj.MyEvent += (s, e) => { ... };                // lambda


// ─── UNSUBSCRIBING ───────────────────────────────────────────
obj.MyEvent -= MethodName;

EventHandler stored = (s, e) => { };             // store lambda if you need to unsub
obj.MyEvent += stored;
obj.MyEvent -= stored;


// ─── SUBSCRIBER METHOD SHAPE ─────────────────────────────────
// For EventHandler:
void Handler(object sender, EventArgs e) { }

// For EventHandler<MyArgs>:
void Handler(object sender, MyArgs e) { }
```

---

## Chapter 13: Common Mistakes

|Mistake|Result|Fix|
|---|---|---|
|`MyEvent.Invoke()` without `?.`|Crashes if no subscribers|Always use `MyEvent?.Invoke()`|
|Subscribing a lambda and unsubscribing a new identical lambda|Unsubscribe silently fails, memory leak|Store lambda in a variable|
|Never unsubscribing in Unity|Null reference errors after object is destroyed|Unsubscribe in `OnDisable` or `OnDestroy`|
|Using a public delegate field instead of event|Anyone can invoke or reset it|Add the `event` keyword|
|Putting subscription code in `Start` instead of `OnEnable`|Misses events if object is disabled and re-enabled|Subscribe in `OnEnable`, unsubscribe in `OnDisable`|
|Firing the event from outside the owner class|Compile error|Move firing into a method inside the owner|

---

## ✏️ Practice

**Q1 — Basic:** Create a `TrafficLight` class. It has an event `LightChanged` that sends the new colour as a string. Fire it in a `ChangeLight(string colour)` method. Subscribe two methods — one that prints `"Cars: stopping"` on red, and one that always prints the colour.

**Q2 — EventArgs:** Create a `BankAccount` class. It fires a `TransactionOccurred` event with custom EventArgs containing `Amount`, `Type` ("deposit"/"withdrawal"), and `Balance`. Subscribe a method that warns if balance drops below 100.

**Q3 — Unity Scenario:** Design (in plain C#, no Unity needed) a `GameManager` class that fires `OnGameOver` and `OnLevelComplete`. Create a `UISystem` class and a `SoundSystem` class that both subscribe to both events and print what they'd do.

<details> <summary>Answers</summary>

```csharp
// Q1
class TrafficLight
{
    public event EventHandler<string> LightChanged;

    public void ChangeLight(string colour)
    {
        LightChanged?.Invoke(this, colour);
    }
}

var light = new TrafficLight();
light.LightChanged += (s, colour) =>
{
    if (colour == "red") Console.WriteLine("Cars: stopping");
};
light.LightChanged += (s, colour) => Console.WriteLine($"Light is now: {colour}");

light.ChangeLight("red");
// Cars: stopping
// Light is now: red

light.ChangeLight("green");
// Light is now: green


// Q2
class TransactionArgs : EventArgs
{
    public decimal Amount { get; set; }
    public string Type { get; set; }
    public decimal Balance { get; set; }
}

class BankAccount
{
    public event EventHandler<TransactionArgs> TransactionOccurred;
    private decimal _balance = 500;

    public void Deposit(decimal amount)
    {
        _balance += amount;
        TransactionOccurred?.Invoke(this, new TransactionArgs
        { Amount = amount, Type = "deposit", Balance = _balance });
    }

    public void Withdraw(decimal amount)
    {
        _balance -= amount;
        TransactionOccurred?.Invoke(this, new TransactionArgs
        { Amount = amount, Type = "withdrawal", Balance = _balance });
    }
}

var account = new BankAccount();
account.TransactionOccurred += (s, e) =>
{
    Console.WriteLine($"{e.Type}: {e.Amount} | Balance: {e.Balance}");
    if (e.Balance < 100)
        Console.WriteLine("⚠ Warning: Low balance!");
};

account.Withdraw(420); // withdrawal: 420 | Balance: 80 | Warning: Low balance!


// Q3
class GameManager
{
    public event EventHandler OnGameOver;
    public event EventHandler<string> OnLevelComplete; // string = level name

    public void GameOver() => OnGameOver?.Invoke(this, EventArgs.Empty);
    public void CompleteLevel(string name) => OnLevelComplete?.Invoke(this, name);
}

class UISystem
{
    public UISystem(GameManager gm)
    {
        gm.OnGameOver += (s, e) => Console.WriteLine("UI: Showing Game Over screen");
        gm.OnLevelComplete += (s, level) => Console.WriteLine($"UI: Showing '{level}' complete banner");
    }
}

class SoundSystem
{
    public SoundSystem(GameManager gm)
    {
        gm.OnGameOver += (s, e) => Console.WriteLine("Sound: Playing game over music");
        gm.OnLevelComplete += (s, level) => Console.WriteLine("Sound: Playing victory jingle");
    }
}

var gm = new GameManager();
var ui = new UISystem(gm);
var sound = new SoundSystem(gm);

gm.CompleteLevel("World 1-1");
// UI: Showing 'World 1-1' complete banner
// Sound: Playing victory jingle

gm.GameOver();
// UI: Showing Game Over screen
// Sound: Playing game over music
```

</details>