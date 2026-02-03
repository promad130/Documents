**Dart** is a modern, object-oriented programming language developed by Google. It's designed to be easy to learn, productive, and efficient, especially for building user interfaces and cross-platform applications. If you're already familiar with C and the basics of OOP (classes, members), you'll find Dart's syntax approachable and its concepts familiar, but with some unique features and a focus on developer productivity.

## Key Features of Dart

- **Object-Oriented**: Everything in Dart is an object, including numbers, functions, and even `null`. Dart uses classes and single inheritance.
    
- **C-like Syntax**: Dart’s syntax is similar to C, Java, and JavaScript, making it easy for C programmers to pick up.
    
- **Garbage Collected**: Memory management is handled automatically.
    
- **Strongly Typed (with Type Inference)**: Dart supports both static and dynamic typing. You can specify types or let the compiler infer them.
    
- **Null Safety**: Dart has built-in null safety, helping prevent null reference errors at compile time.
    
- **Cross-Platform**: Dart is the language behind Flutter, which allows you to build apps for mobile, web, desktop, and server from a single codebase.
    
- **Fast Compilation**: Dart can be compiled both Just-In-Time (JIT) for development (hot reload) and Ahead-Of-Time (AOT) for optimized production builds.
    

## Dart Program Structure

A basic Dart program looks like this:
```dart
void main() 
{   
	print('Hello, Dart!'); 
}
```
- `main()` is the entry point, similar to C.
- `print()` outputs to the console.

## Variables and Types
Dart supports both explicit and inferred typing:
```dart
int a = 10;       // Explicit type 
var b = 20;       // Type inferred as int 
final c = 30;     // Immutable after assignment 
const d = 40;     // Compile-time constant
```
**Doubt:** _What is the difference between `final` and `const`?_
- `final` variables can only be set once and are initialized at runtime.
- `const` variables are compile-time constants and must be assigned constant values.

## Functions
Functions in Dart are first-class objects:

dart

`int add(int x, int y) {   return x + y; } void main() {   print(add(2, 3)); // Output: 5 }`

You can also use arrow syntax for simple functions:

dart

`int multiply(int x, int y) => x * y;`

## Classes and Objects

Dart uses classes for OOP:
```dart
class Point 
{   
	int x, y;   
	Point(this.x, this.y);   
	
	void display() 
	{    
		print('Point($x, $y)');  
	} 
} 

void main() 
{   
	var p = Point(2, 3);  
	p.display(); // Output: Point(2, 3) 
}
```
**Doubt:** _Is inheritance supported?_
Yes, Dart supports single inheritance and interfaces via the `implements` keyword.

## Null Safety
By default, variables cannot be null unless you specify:
```dart
int? maybeNull; // The ? allows null assignment
```
## Tooling and Ecosystem
- **Dart SDK**: Includes the Dart VM, compiler, and package manager (`pub`).
- **Flutter**: Google's UI toolkit for building natively compiled applications for mobile, web, and desktop from a single codebase—uses Dart as its language.
- **Package Ecosystem**: `pub.dev` is Dart's package repository.

## Example: Simple Dart Program
```dart
void main() 
{   
	var name = 'World';  
	print('Hello, $name!'); 
}
```
This prints "Hello, World!" to the console.

## Common Questions
**Q: Is Dart compiled or interpreted?**  
A: Dart is compiled. For development, it uses JIT for fast hot reloads. For production, it compiles AOT to native code or JavaScript (for web).

**Q: Where is Dart used?**  
A: Dart is primarily used with Flutter for cross-platform app development, but can also be used for web (via Dart-to-JS), server, and command-line tools.

**Q: How is Dart different from JavaScript?**  
A: Dart is statically typed (with optional dynamic typing), supports sound null safety, and is optimized for UI development. Dart code can be compiled to JavaScript for web apps.

---
# Dart Basics
Below is a detailed introduction to **Dart**—covering all essentials: **variables**, **loops**, **control flow**, and **functions**. Each topic includes explanations, syntax, and hands-on examples.

## Data Types in Dart

Dart has a set of built-in data types that help categorize and manage different kinds of data in your programs. These data types can be grouped into a few broad categories:

**1. Primitive Data Types:**
- **int**: Represents whole numbers without decimals (e.g., 5, -42). 
- **double**: Represents floating-point numbers with decimals (e.g., 3.14, -0.001).
- **num**: A common supertype for both `int` and `double` that can hold either type.
- **String**: A sequence of UTF-16 characters used to represent text.
- **bool**: Boolean type with only two values: `true` or `false`.
- **Symbol**: Represents an identifier symbol, mostly used in reflection and meta programming.
- **Null**: Represents the absence of a value (only one instance: `null`).

**2. Collection Types:**

- **`List<T>`**: An ordered collection of items (similar to arrays), where `<T>` denotes the type of elements.
- **`Set<T>`**: An unordered collection of unique items.
- **`Map<K, V>`**: A collection of key-value pairs, where keys and values can be of any type.

**3. Special and Other Types:**
- **Runes**: Represents Unicode code points in a string (less commonly used now due to the Characters API).
- **Function**: Represents functions, both named and anonymous closures.
- **dynamic**: A special type that disables static type checking, allowing any type of value.
- **Object / Object?**: The root superclass of all Dart classes except `Null`.
- **void**: Represents the absence of a return value (commonly used for functions).
- **Never**: Indicates that an expression never completes normally (usually a function that always throws).

**4. Custom Types:**

- Dart supports user-defined classes, enums, mixins, and typedefs allowing you to create complex and modular data structures.
    

## Example: Basic data types in Dart
```dart
void main() 
{   
	int age = 30;  
	double price = 19.99;  
	String name = "Alice";  
	bool isActive = true;  
	
	List<int> scores = [85, 90, 78];  
	Set<String> uniqueNames = {'Alice', 'Bob'};  
	Map<String, int> phoneBook = {'Alice': 12345, 'Bob': 67890};  
	
	Function greet = () => print('Hello!');     
	
	print('Age: $age, Price: $price, Name: $name, Active? $isActive');  
	print('Scores: $scores');  
	print('Unique names: $uniqueNames');  
	print('Phone Book: $phoneBook');  
	
	greet(); 
}
```

## Summary of key built-in Dart types with their common usage:

|Category|Type|Description|
|---|---|---|
|Numbers|int, double, num|Whole and decimal numbers|
|Text|String|Textual data|
|Boolean|bool|True/false|
|Collections|List, Set, Map|Ordered lists, unique sets, key-value pairs|
|Special Types|dynamic, Object, Null, void, Never, Symbol|Special system and control types|
|Function Types|Function|Represents functions and closures|

Dart is a statically typed language but supports type inference and optional type annotations.

This overview covers the common data types you'll encounter and use when programming in Dart, offering type safety and flexibility within the language's object-oriented framework.

## 1. Variables in Dart
Variables store data. In Dart, every variable has a type, but Dart can also infer types.

### Declaring Variables
- **Explicit Typing:**
    ```    dart
    int age = 25; 
    double height = 5.9; 
    String name = 'Alice'; 
    bool isStudent = true;
```
    
- **Type Inference with `var`:**
    ```    dart
    var city = 'Delhi';       // Inferred as String 
    var score = 99;           // Inferred as int 
    var isActive = false;     // Inferred as bool
    
```
- **Final & Const:**
    - `final` means value can’t be reassigned (runtime constant).
    - `const` means value must be known at compile time.
    ```    dart
    final piValue = 3.1415; 
    const country = 'India';
```

## 2. Control Flow Statements

### `if` / `else` Statements
Used for decision making.
```dart
int x = 10; 
if (x > 5) 
{   
	print('x is greater than 5'); 
} else {   
	print('x is less than or equal to 5'); 
}
```
**No parentheses** are needed around conditions in Dart (unlike C).

### `switch` Statement

Efficient for multiple possible values.
```dart
var grade = 'A';
switch (grade) {
  case 'A':
    print('Excellent!');
    break;
  case 'B':
    print('Good');
    break;
  default:
    print('Needs Improvement');
}
```
- `break` is mandatory after each case.
- `continue`, `return`, and other control statements also supported.

### Ternary Operator
Concise `if-else` for expressions.
```dart
String result = (x > 5) ? 'Big' : 'Small';
```

## 3. Loops
### `for` Loop
Basic structure:
```dart
for (int i = 0; i < 5; i++) 
{   
	print(i); // prints 0 to 4 
}
```
### For-each (for...in)
Iterate over a collection:
```dart
var numbers = [10, 20, 30]; 
for (var num in numbers) 
{   
	print(num); 
}
```
### `while` Loop
```dart
int count = 0; 
while (count < 3) 
{   
	print(count);  
	count++; 
}
```
### `do-while` Loop
Executes at least once.
```dart
int count = 0; 
do 
{   
	print(count);  
	count++; 
} while (count < 3);
```
## Breaking and Continuing Loops
```dart
for (int i = 0; i < 10; i++) {
  if (i == 5) break;
  if (i % 2 == 0) continue;
  print(i);
}

```
## 4. Functions
Functions help break code into reusable blocks.

### Basic Function
```dart
int add(int a, int b) {   return a + b; }
```
### Arrow Syntax
Useful for single expressions.
```dart
int square(int x) => x * x;
```
### Named and Optional Parameters
#### Optional Positional Parameters
```dart
void greet(String name, [String msg = 'Hello']) {
  print('$msg, $name');
}
greet('Bob');           // Hello, Bob
greet('Bob', 'Hi');     // Hi, Bob

```

### Named Parameters
```dart
void showProfile({String name = 'Anonymous', int age = 0}) {
  print('Name: $name, Age: $age');
}
showProfile(age: 28, name: 'Sara');
```
- Named parameters are optional by default (can be required in newer Dart).

### Anonymous Functions (Lambdas)
Useful for inline functions.
```dart
var numbers = [1, 2, 3]; 
numbers.forEach((num) {   print(num * 2); });
```
## 5. Input and Output
### Print Output
```dart
print('Hello, Dart!');
```
### Reading Input (in Dart CLI)
```dart
import 'dart:io';

void main() {
  print('Enter your name:');
  String? name = stdin.readLineSync();
  print('Hello, $name!');
}
```
- Note: Use `?` to allow `null` (nullable String), needed for input.

## 6. Table: C vs. Dart (Key Syntax Differences)

| Concept        | C Example           | Dart Example                     |
| -------------- | ------------------- | -------------------------------- |
| Variable       | `int x = 5;`        | `int x = 5;`                     |
| Type Inference | N/A                 | `var x = 5;`                     |
| Constant       | `const int x = 5;`  | `const x = 5;` or `final x = 5;` |
| If condition   | `if (x > 5) {}`     | `if (x > 5) {}`                  |
| Function       | `int sum(int, int)` | `int sum(int a, int b)`          |
| Array/List     | `int arr;`          | `var arr = ;`                    |
| String         | `char* s = "Hi";`   | `String s = 'Hi';`               |

## 7. Common Doubts
- **How is Dart different from C/OOP Languages?**
    - Dart is object-oriented by default (everything is an object), supports functional constructs (lambdas), type inference, and has garbage collection.
    
- **Is main() always required?**
    - Yes, Dart execution starts from `main()`.
    
- **Is semicolon needed after every statement?**
    - Yes, like C, every statement ends with `;`.
    

## 8. Full Example: Basic Dart Program
```dart
void main() {
  int count = 3;
  for (int i = 1; i <= count; i++) {
    print('Hello $i');
  }
  print('Sum: ${add(2, 3)}');
}

int add(int a, int b) {
  return a + b;
}

```

## Core Data Structures in Dart

Dart provides several powerful **built-in data structures** that are essential for any programmer, especially if you’re coming from a C background. Here you’ll learn about the most commonly used collections in Dart: **List**, **Set**, and **Map**—with detailed concepts, practical examples, common doubts, and comparisons to C.

## 1. List (Array)
- **A List in Dart is an _ordered_ collection of items.**
- Dart’s `List` is similar to C arrays in concept but is more flexible: you can change its length (growable).
- Lists can contain any object type and support various methods for manipulation.

## Creating and Using Lists
```dart
void main() {
  // Growable list of strings
  var fruits = ['apple', 'banana', 'orange'];
  print(fruits); // [apple, banana, orange]

  // Fixed-length list of integers (initialized with zeros)
  var numbers = List<int>.filled(3, 0);
  numbers[0] = 10;
  numbers[1] = 20;
  numbers[2] = 30;
  print(numbers); // [10, 20, 30]
}

```
## Accessing & Modifying
```dart
void main() 
{   
	var colors = ['red', 'green', 'blue'];  
	print(colors[0]);          // red  
	colors[1] = 'yellow';  
	print(colors);             // [red, yellow, blue]  
	print(colors.length);      // 3 
}
```

## Common Operations
- Add: `fruits.add('kiwi');`
- Remove: `fruits.remove('apple');`
- Iterate (for-each):
    ```    dart
    for (var fruit in fruits) 
	    print(fruit);
```
- Check: `colors.contains('yellow'); // true`

## Hands-on Doubts
- **Can Dart lists have mixed types?**
    - Yes, if you don’t specify a type.
    ```dart
    var mixed = [1, 'hello', true];
    
```
- **What’s the difference with C arrays?**
    - Dart `List` is dynamic and can change in size; C arrays have fixed size.

## 2. Set
- **A Set is an unordered collection of unique elements.**
- Unlike lists, sets automatically discard duplicates and do _not_ guarantee element order.
### Creating and Using Sets
```dart
void main() 
{   
	// Set of integers  
	var numbers = <int>{1, 2, 3, 3};  
	//OR
	Set<int> numbers = {1, 2, 3, 3};
	print(numbers); // {1, 2, 3}   
	// Set of strings  
	var names = <String>{'Alice', 'Bob', 'Charlie'};  
	print(names.contains('Bob')); // true 
}
```
### Set Operations
- Add: `numbers.add(4);`
- Add multiple: `numbers.addAll([1][2]);`
- Remove: `names.remove('Bob');`
- Union, Intersection, Difference:
    ```    dart
    var a = {1, 2, 3}; 
    var b = {3, 4, 5}; 
    
    print(a.union(b)); // {1, 2, 3, 4, 5} 
    print(a.intersection(b)); // {3} 
    print(a.difference(b)); // {1, 2}
```

### Key Doubts
- **Are sets indexed?**
    - No. You can’t access elements by index.
    
- **How to create an empty set?**
    - `var s = <int>{};`
    - Using `{}` by itself creates a Map, not a Set.

## 3. Map (Dictionary)
- **A Map is a collection of key-value pairs with unique keys.** 
- Similar to dictionaries or hashmaps in other languages.

### Creating and Using Maps
```dart
void main() 
{   
	var capitals = {    
		'India': 'New Delhi',    
		'USA': 'Washington',    
		'Japan': 'Tokyo'  };  
		
	print(capitals['India']); // New Delhi   
	// Add and modify  
	capitals['France'] = 'Paris';  
	capitals['India'] = 'Delhi'; 
	// Update  
	print(capitals); 
}
```
### Map Operations

- Add: `capitals['Italy'] = 'Rome';`
- Check: `capitals.containsKey('Japan'); // true`
- Remove: `capitals.remove('USA');`
- Iterate:
    ```    dart
    capitals.forEach((country, capital) {   print('$country: $capital'); });
```

### Key Doubts

- **What happens if you access a missing key?**
    
    - You get `null` (not an error, unless you force it).
        
- **Can keys be other types?**
    
    - Yes, keys and values can be of any type (but must be unique and reliable, like ints, strings).
        


---
# OOPS in dart
## Classes and Objects in Dart
Dart is a modern object-oriented language, which means that **classes** and **objects** are core concepts you need to master. If you know C and basic OOP, Dart’s syntax and model will feel familiar but with some unique features.

## What Is a Class?
- **Definition:** A class is a blueprint for creating objects (instances). It defines properties (fields) and behaviors (methods) that describe what its objects will have and can do.
- **Syntax:**
    ```    dart
    class Car {   
	    String brand = 'Toyota';  
	    int year = 2022;   
	    
	    void honk() 
	    {    
		    print('Beep beep!');  
		} 
	}
```
- **Naming:** Use PascalCase for class names (e.g., `Student`, `Person`).

## What Is an Object?
- **Definition:** An object is an instance of a class. It holds real values in the fields defined by the class, and you can call its methods to perform actions.
- **Creating an Object:**
    ```    dart
    void main() 
    {   
	    Car myCar = Car();  
	    print(myCar.brand);    // Toyota  
	    
	    myCar.honk();          // Beep beep! 
	}
```
- Dart allows creating multiple independent objects from the same class.

## Fields (Instance Variables)
- **Purpose:** Hold the data (state) of the object.
- **Example:**
    ```    dart
    class Student 
    {   
	    String? name;  
	    int? rollNumber; 
    }
```
- Dart uses `?` for nullable types for null safety.

## Methods (Member Functions)
- **Purpose:** Define behavior that objects of the class can perform.
- **Example:**
    ```    dart
    class Student 
    {   
	    String? name;  
	    
	    void introduce() 
	    {    
		    print('Hi, my name is $name');  
		} 
	}
```    

## Constructors
- **Purpose:** Special functions to create and initialize objects.
- **Default Constructor:**
    ```    dart
    class Point {   int x = 0;  int y = 0; }
```
    - You get a default constructor automatically.
    
- **Custom Constructor:**
```    dart
    class Point 
    {   
	    int x, y;  
	    Point(this.x, this.y); // Shorthand constructor 
	} 
	
	void main() 
	{   
		Point p = Point(3, 4);  
		print(p.x); // 3 
	}
```
- **Named Constructors:** Dart lets you define multiple constructors with different names for different ways of object creation.
   ```dart
    class Rectangle 
    {   
	    int width, height;  
	    Rectangle(this.width, this.height);  
	    Rectangle.square(int size) : width = size, height = size; 
	}
```    

## Using Class Members
- Access fields/methods with dot (`.`) notation:
    ```    dart
    var student = Student(); 
    student.name = 'Arjun'; 
    
    student.introduce();  // Hi, my name is Arjun
```

## Null Safety in Dart
Dart’s type system enforces that all **non-nullable fields** in a class _must_ be initialized before they are accessed. This rule improves code safety, reliability, and prevents many common programming bugs.
- Dart's fields can be nullable or non-nullable.
- `String name;` (non-nullable, must be assigned)
- `String? name;` (nullable, default value is null)

## Example: Full Class and Object Code
```dart
class Animal 
{   
	String? name;  
	int? numberOfLegs;   
	
	Animal(this.name, this.numberOfLegs);   
	
	void display() 
	{    
		print("Animal: $name, Legs: $numberOfLegs");  
	} 
} 

void main() 
{   
	Animal dog = Animal('Dog', 4);  
	dog.display(); 
}
```
**Output:**
```text
Animal: Dog, Legs: 4
```
## Common Doubts
- **Can fields or methods be private?**
    - Use an underscore (`_`) before the name (e.g., `_secret`) to make a field or method private to its library.
- **Do objects have destructors?**
    - No explicit destructors; Dart uses garbage collection.
- **Is there inheritance?**
    - Yes; use `extends` for inheriting another class.

## Inheritance in Dart
Inheritance is a fundamental concept in Dart’s object-oriented programming that allows one class to acquire the properties and methods of another, promoting code reuse and logical hierarchy.
### Key Concepts
- **Parent (Super) Class**: The class whose features are inherited (sometimes called base or super class).
- **Child (Sub) Class**: The class that inherits from the parent (also called derived class).

Dart supports **single inheritance**—each class can extend only one other class (unlike C++ which supports multiple inheritance).

### Syntax and Example
Use the `extends` keyword:
```dart
class Animal 
{   
	void eat() 
	{    
		print('Animal eats');  
	} 
} 

class Dog extends Animal 
{   
	void bark() 
	{    
		print('Dog barks');  
	} 
} 

void main() 
{   
	Dog dog = Dog();  
	dog.eat();  // Inherited method  
	dog.bark(); // Own method 
}
```
Here:
- `Dog` inherits the `eat()` method from `Animal` and adds its own method `bark()`.

### Types of Inheritance in Dart

| Type                 | Supported in Dart? | Description                                                              |
| -------------------- | ------------------ | ------------------------------------------------------------------------ |
| Single Inheritance   | Yes                | Class extends one parent class                                           |
| Multi-level          | Yes                | Class extends a class, which itself extends another                      |
| Hierarchical         | Yes                | Multiple subclasses inherit from one parent class                        |
| Multiple Inheritance | No                 | Cannot directly extend more than one class (use mixins for code sharing) |

### Constructor Inheritance
Constructors are **not inherited**. If the parent class has a parameterized constructor, the child must call it using `super()`.
```dart
class Person 
{
	String name;
	Person(name){
		this.name = name;
	}
}

class Student extends Person {
	int rollNo;
	Student(String name, rollNo) : super(name){
		this.rollNo = rollNo;
	}
}
```
## The `super` Keyword in Dart

The `super` keyword is used to refer to the immediate parent class object. It’s essential when a subclass needs to:

- Access a parent’s property or method that is overridden in the child.
    
- Call a parent class constructor from the child’s constructor.
    

## Common Uses

**1. Accessing Parent Class Members:**

dart

`class Animal {   void speak() => print('Animal sound'); } class Dog extends Animal {   @override  void speak() {    print('Dog barks');    super.speak(); // Calls parent class method  } }`

Output:

text

`Dog barks Animal sound`

**2. Resolving Name Conflicts:**

If both child and parent have a variable or method of the same name, use `super` to refer to the parent’s version.

dart

`class Animal {   int count = 10; } class Dog extends Animal {   int count = 20;     void printCounts() {    print(count);        // Child's count (20)    print(super.count);  // Parent's count (10)  } }`

**3. Calling Parent Constructors:**

When creating an object, the parent constructor must be called if it’s not the default one:

dart

`class Vehicle {   String model;  Vehicle(this.model); } class Car extends Vehicle {   Car(String model): super(model); }`

## Common Doubts

## Why does Dart not support multiple inheritance?

Dart avoids classic multiple inheritance to keep the language simple and to prevent ambiguities from inheriting the same property from multiple parents. Use **mixins** if you want to reuse code from multiple classes.

## What is the difference between `super` and `this`?
- `super` refers to members of the parent class.
- `this` refers to the current object’s own members.    

## Comparison Table: Dart vs C OOP Basics

| Concept           | C (struct)            | Dart (class/object)                 |
| ----------------- | --------------------- | ----------------------------------- |
| Keyword           | `struct`              | `class`                             |
| Constructor       | Via custom function   | Built-in and custom with class name |
| Methods           | Function pointers     | Declared inside class               |
| Fields visibility | No built-in privacy   | Private with `_`, public by default |
| Object creation   | `struct var = {...};` | `ClassName var = ClassName();`      |

# Widgets
In Dart (especially in the context of **Flutter**), a **Widget** is the fundamental building block for building user interfaces. Since you already know C and OOP basics, think of a Widget as a lightweight object (like a struct/class) that describes part of the UI. However, in Flutter (which is Dart’s most popular framework), nearly _everything_ is a Widget – from buttons and text, to entire screens and layouts.

## What is a Widget?
A Widget is an **immutable** description of part of the UI. When you construct your UI in Dart using Flutter, you do it by composing Widgets. They specify _what_ to display and _how_ to arrange children, but not _how to paint_ pixels directly.

> “Widgets are the building blocks of a Flutter app’s user interface.”  

## Types of Widgets
There are two primary types:

- **StatelessWidget**
    - Represents UI that does _not_ depend on any mutable state.
    - Example: A static title, static image, a fixed button label.
    
- **StatefulWidget**
    - Represents UI that _does_ depend on mutable state.
    - Example: A counter that increments when you press a button.

## Difference Table

|Type|Can Change Over Time?|Example|
|---|---|---|
|StatelessWidget|No|Logo, static label|
|StatefulWidget|Yes|Counter, form input, switch|

## Examples

## Example 1: StatelessWidget

```dart
import 'package:flutter/material.dart';

class HelloWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Text('Hello, World!');
  }
}
```

Here, `HelloWidget` always displays the same text. It doesn’t hold or modify any state.

## Example 2: StatefulWidget

```dart
import 'package:flutter/material.dart';

class CounterWidget extends StatefulWidget {
  @override
  _CounterWidgetState createState() => _CounterWidgetState();
}

class _CounterWidgetState extends State<CounterWidget> {
  int counter = 0;

  void _increment() {
    setState(() {
      counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Text('Counter: $counter'),
        ElevatedButton(
          onPressed: _increment,
          child: Text('Increment'),
        ),
      ],
    );
  }
}

```

Here, pressing the button modifies the state (`counter`), and the UI updates.

## Common Widget Categories in Flutter
- **Text widgets**: Display static or styled text.
- **Button widgets**: Trigger actions, e.g., `ElevatedButton`, `TextButton`.
- **Image widgets**: Display images from assets, network, or files.
- **Layout widgets**: Arrange, align, or resize children (e.g., `Row`, `Column`, `Stack`).
- **Input widgets**: Get user input, e.g., `TextField`, `Checkbox`.

## Widgets are Composable
Widgets can contain other widgets.
```dart
Widget build(BuildContext context) {
  return Column(
    children: [
      Text('A label'),
      ElevatedButton(onPressed: () {}, child: Text('Press')),
    ],
  );
}
```

## Rendering & Updating
- **Immutable:** Widgets themselves don’t change; when the state changes, Flutter creates a new widget instance and updates what should be displayed.
- **Build method:** When you change the state (in a StatefulWidget), the `build()` method runs again and replaces only what changed.

## Doubts You May Have
**Q: How are Widgets different from components in other frameworks?**
- Widgets are very lightweight, immutable, and declarative. Flutter completely rebuilds parts of your UI tree quickly and efficiently without you having to manage the diffing yourself.


**Q: Do I always need to use StatefulWidget if my UI can change?**
- Yes. If any part of your widget tree needs to react to runtime changes (user interaction, data updates), you need at least one StatefulWidget at that place in the widget tree.

**Q: Can Widgets access resources or context (like Theme, MediaQuery)?**
- Yes, via the `BuildContext` parameter passed to the `build()` method.

# Types of Widgets
Write about the widgets that are default in Android Studio.