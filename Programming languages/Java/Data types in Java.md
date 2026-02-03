 **Expected to know:** [[Introduction to Programming]],[[Data Types and Constants]]
**Topics Covered:**
**Tags:** [[Data Types and Constants]]

Well numbers are numbers no matter which language you choose to write them in. 
**The concept of how data should be classified and stored is broadly similar across programming languages**, even though the specific sizes, representations, and details may differ.

---
## Java Data Types
Let’s build your understanding by connecting Java’s data type system with familiar points from C and C#.

## 1. Primitive Data Types
Java defines 8 _primitive_ data types. These are similar to those in C and C#, but with some important differences:

| Type    | Size    | Range                                                   | Notes                                                                          |
| ------- | ------- | ------------------------------------------------------- | ------------------------------------------------------------------------------ |
| byte    | 8 bits  | –128 to 127                                             | Always signed; no unsigned types in Java                                       |
| short   | 16 bits | –32,768 to 32,767                                       | Always signed                                                                  |
| int     | 32 bits | –2,147,483,648 to 2,147,483,647                         | Fixed size (unlike C, platform independent)                                    |
| long    | 64 bits | –9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 | Fixed size                                                                     |
| float   | 32 bits | ~1.4e–45 to ~3.4e+38                                    | IEEE 754 standard                                                              |
| double  | 64 bits | ~4.9e–324 to ~1.8e+308                                  | IEEE 754 standard                                                              |
| char    | 16 bits | 0 to 65,535 (Unicode characters)                        | _Important difference!_ Java’s char is 16-bit Unicode, not 8-bit ASCII as in C |
| boolean | 1 bit   | true or false                                           | Only those two values; no numeric conversion                                   |

## _Compare with C/C#:_
- **No unsigned types in Java** (e.g. no `unsigned int`).
- **Size is always the same across platforms**. In C int can be 16, 32, or 64 bits depending on implementation; in Java, int is _always_ 32 bits.
- **char is Unicode** in Java, not ASCII. This matters for internationalization.
- **boolean is not 0/1 or convertible to int:** only `true` or `false`.

## Declaration Examples:
```java
int num = 100; 
long big = 9876543210L; 

float f = 1.5f; 
double d = 3.14159; 
char c = 'A';  // Unicode 
boolean isValid = true;
```
Notice the `L` for long literals and `f` for float literals.

---
## 2. Reference Types (Objects and Arrays)
Unlike in C/C# where you have pointers (`int*`, etc.), Java treats all _non-primitive types_ as object references.
- **[String](Strings%20in%20Java):** Built-in object type, not an array of chars as in C.
- **Arrays:** Arrays in Java are objects (references), always with bounds checking. No pointer arithmetic.
- **Classes:** Any user-defined type is a reference.

## Example:
```java
String name = "Alice";           // String is a Java class (object) 
int[] nums = new int;         // Array of ints, fixed size, index from 0 (nums ... nums) 
MyClass obj = new MyClass();     // Custom class reference
```
If you assign one reference to another, both refer to the same object. Deep copies require explicit coding.

## Null value:
Any reference type can be null, indicating it’s not pointing to an object.

---
## 3. Type Conversion
Java performs _widening conversions_ automatically (e.g., int to long), but _narrowing conversions_ require a cast and may lose data.
```java
int i = 110; 
byte b = (byte) i;  // Explicit cast needed
```
- char, byte, short auto-promote to int in expressions.
- No implicit conversion between boolean and numeric types (avoids common C/C# pitfalls).

---
## 4. [Strings](Strings%20in%20Java)
In C: `char str[] = "abc";`  
In C#: `string str = "abc";`  
In Java: `String str = "abc";`  
But Java’s String is a class with many helpful methods (length, substring, equals, etc.).

### Interpolation
#### Traditional Java Approaches
1. **String Concatenation (`+`)**
The simplest—if least elegant—way:
```Java
String name = "Alice";
int age = 22;
String result = "Hello, " + name + "! You are " + age + " years old.";
// Output: Hello, Alice! You are 22 years old.
```
- All primitive types are automatically converted to strings.
- **Be careful with order and parentheses:** `"Total: " + 5 + 4` yields `"Total: 54"`, not `"Total: 9"`.

2. **String.format (C-style Formatting)**
Preferred for readable, localized, and formatted output:
```java
String name = "Alice"; 
int age = 22; 
String result = String.format("Hello, %s! You are %d years old.", name, age); 
// Output: Hello, Alice! You are 22 years old.`
```
- `%s` for strings, `%d` for integers, `%f` for floats/doubles, etc.
- You can control widths, decimal places, etc.

3. **System.out.printf**
Works like `printf` in C:
```java
String name = "Alice"; 
int age = 22; 

System.out.printf("Hello, %s! You are %d years old.\n", name, age);
```
- Prints directly, rather than producing a string.

---
## Comparison With C# and Newer Java (Preview)
- **C#** offers true interpolation: `$"Hello, {name}!"`
- **Modern Java (21+)** introduces [String Templates](https://openjdk.org/jeps/430) as a _preview feature_:
    ```java
    `// Java 21+ (Preview Feature) 
    String name = "Alice"; 
    int age = 22; 
    String s = STR."Hello, \{name}! You are \{age} years old.";` 
    
```
    But this is **not enabled by default** and not standard yet in most Java usage.
---
