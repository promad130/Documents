**Topics Covered:**
**Tags:** [[TypeCasting]]

Type casting means converting a value from one data type to another. Java, as a strongly typed language, enforces rules on how this conversion can happen. Let’s break down the key ideas, especially where they differ from C/C#.

---
## 1. Automatic Type Conversion (Widening)
Java automatically converts ("widening") a smaller/narrower type to a bigger/wider type when safe and compatible. For example:
```java
int i = 100; 
long l = i;      // int to long, OK, widening 
float f = l;     // long to float, OK 
double d = f;    // float to double, OK
```
- **No data loss possible** in widening conversions.

## _Quick reference:_

| From  | To                              | Automatic? |
| ----- | ------------------------------- | ---------- |
| byte  | short, int, long, float, double | Yes        |
| short | int, long, float, double        | Yes        |
| char  | int, long, float, double        | Yes        |
| int   | long, float, double             | Yes        |
| long  | float, double                   | Yes        |
| float | double                          | Yes        |

---
## 2. Explicit Casting (Narrowing)
When converting a wider type to a narrower one, or between types that may lose information, _explicit casting_ is required:
```java
double d = 123.456; 
int i = (int) d;        // Casts double to int; fractional part lost! 
long l = 1000L; 
short s = (short) l;    // Possible data loss if value is too big for short
```
- Syntax: `targetType variable = (targetType) value;`
- **Data may be lost**, and Java will not stop you at compile time! Be careful.

Example of potential overflow:
```java
int i = 128; 
byte b = (byte) i; // b becomes -128 (overflow/wraps around)
```
---

## 3. Type Conversion Between Primitives & Objects
- **No conversion:** between types like boolean $↔$ int, or char $↔$ boolean. (char can be converted into int in java)
- **[[Wrapper classes]]** (like `Integer`, `Double`) can be cast explicitly, but it's best to use methods like `.intValue()` or `.doubleValue()` when converting boxed types.

---
## 4. Type Casting with Inheritance (Reference Types)
Java allows for _upcasting_ (casting a subclass object to a superclass reference) **automatically**, and _downcasting_ (casting a superclass reference to a subclass reference) **manually**, but only if the actual object is of that type.
```java
class Animal {} 
class Dog extends Animal {} 

Animal a = new Dog();   // Upcasting - always safe, automatic 
Dog d = (Dog) a;        // Downcasting - must check the type
```
**Use `instanceof` to check before downcasting:**
```java
if (a instanceof Dog) 
{     
	Dog d = (Dog) a; 
}
```
An incorrect downcast throws a `ClassCastException` at runtime.

---
## 5. Type Promotion in Expressions
In _mixed-type_ expressions, smaller types are "promoted"—for example:
- All `byte`/`short`/`char` are promoted to `int` in expressions.
- If one operand is a `long`, result is `long`.
- If one operand is `float`, result is `float`.
- If one operand is `double`, result is `double`.
```java
byte b = 10; 
byte c = 20; 
// The result is int, so explicit cast is needed for assignment to byte: 
byte sum = (byte)(b + c); // Otherwise, compile error!
```
This "default to int" for small types is a common source of confusion for C/C# programmers.