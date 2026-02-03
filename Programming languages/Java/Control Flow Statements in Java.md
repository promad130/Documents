**Expected to know:** [[Introduction to Programming]],[[Control Flow Statements]]
**Topics Covered:**
**Tags:** [[Control Flow Statements]]

The Logic of all control flow statements in all programming languages are same, with different syntax and some new features.i,e., parameters.

This programming language also follow what generally all programming languages follow, hence all the concepts regarding control flow statements and syntax are same as what has been given in [[Control Flow Statements]].

Hence things will seem familiar with a few syntax changes.

---
# Control Flow in Java
## 1. Conditional Statements
Java supports:
- `if`
- `if-else`
- `else if` ladder
- Ternary operator (`? :`)
- `switch` statement

These work almost identically to C and C#, both in logic and syntax—with the following notes:
## `if`, `if-else`, `else if`
```java
int num = 10;
if (num > 0) {
    System.out.println("The number is positive");
} else if (num < 0) {
    System.out.println("Negative number");
} else {
    System.out.println("Zero");
}
```

## Nested `if` and Ternary operator
Works the same way:
```java
int age = 20;
if (age > 18) {
    if (age < 25) {
        System.out.println("Eligible for a youth discount");
    }
}

int num = 5;
System.out.println(num > 0 ? "Positive" : "Non-positive");
```
Works the same way as that in C and C#.

## `switch` Statement
The syntax is same as that in C and C# but here, in the expression, it can be `int`, `short`, `byte`, `char`,...all the primitive data types, also a `String` which is not permitted in C and C#.
Also, the switch cases follow the rules of C#, i.e., they have to have a break statement to prevent dropping through the cases.
But they can share the same case block, hence it is a lot more like C# with a few extra features.
```java
int day = 3;
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    default:
        System.out.println("Invalid day");
}
```
OR
```java
String animal = "cat";
switch (animal) {
    case "cat":
        System.out.println("Meow!");
        break;
    case "dog":
        System.out.println("Woof!");
        break;
    default:
        System.out.println("Unknown sound");
}
```
OR:
```JAVA
int day = 3;
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
    case 3:
        System.out.println("Wednesday");
        break;
    default:
        System.out.println("Invalid day");
}
```
## Looping Statements
Java supports:
- `for` loop
- `while` loop
- `do-while` loop

All behave as in C and C#, with minor syntax differences:
### `for` loop
```java
for (int i = 1; i <= 5; i++) 
{     
	System.out.print(i + " "); 
}
```
#### for-each loop in Java
The **for-each loop in** [Java](https://www.geeksforgeeks.org/java/java-tutorial/) (also called the enhanced for loop) was introduced in Java 5 to simplify iteration over arrays and [collections](https://www.geeksforgeeks.org/java/collections-in-java-2/). It is cleaner and more readable than the traditional for loop and is commonly used when the exact index of an element is not required.

**Example:** Using a for-each loop to print each element of an [array](https://www.geeksforgeeks.org/java/arrays-in-java/) in Java.
```java
// Java Program to Iterate through an array
// Using for-each loop
import java.io.*;

class Geeks {
  
    public static void main(String[] args) {
      
        // Array declaration
        int arr[] = { 1, 2, 3, 4, 5 };
        
        // Using for-each loop to 
        // print each element
        for (int e : arr) {
            System.out.print(e + " ");
        }
    }
}
```

### `while` loop
```java
int i = 1; 
while (i <= 5) 
{     
	System.out.print(i + " ");    
	i++; 
}
```
### `do-while` loop
```java
int i = 1; 
do 
{     
	System.out.print(i + " ");    
	i++; 
} while (i <= 5);
```

## Jump Statements
The jump statements in Java is similar to what we had in C and C#,i.e., `break`, `continue`, and `return`, but instead of `goto`, we have labels in java.
The way labels work is that we give a label to a block of code, i.e., a label is simply an **identifier** followed by a colon (`:`), placed immediately before a statement (usually a loop or block).
```java
labelName: 
for (...) 
{     
	// loop body 
}
```
- The label applies to the **immediately following statement**.
- The label is "attached" to the first statement/block that comes after it—this can be a loop, `if` statement, or `{}` block.
We can also wrap whatever we want to include in a label using `{}`.

When it comes to using labels, we cannot call them outside the label's block, we need to call them inside the block, i.e., we can do `continue label`, `break label`, etc,.
Hence we cannot do a `goto` statement.

