**Expected to know:** [[Introduction to topic]],[[Syntax]]
**Topics Covered:**
**Tags:**[[Syntax]]

Many programming languages(maybe almost every) is either built upon The C Programming Language or is heavily influenced by it.
Hence, the basic elements of the programming language that we will see are similar or same as that seen in The C Programming language.

## 1. Everything in a Class
Unlike C, every piece of Java code must be inside a class. Even the entry point (the `main` method) is inside a class.

```java
public class HelloWorld{
	public static void main(String[] args){
		System.out.println("Hello World!");
	}
}
```
- In C, you can write code outside functions. In C# and Java, everything belongs inside a class.

## 2. Entry Point
- In C:
	```C
	int main(){
		printf("Hello World");
	}
	```
	
- In Java:
	```java
	public static void main(String[] args){}
	```
- C# uses a similar structure for its `Main` method.

## 3. Variable Declaration
- Very similar in all three languages:
    ```    c
    int x = 10; 
    float y = 3.14;
```
    ```    java
    int x = 10; 
    float y = 3.14f; // Note the 'f' suffix for float literals
```
    - In C#, it's almost the same.

## 4. Printing Output
- C: `printf("Result: %d", x);`
- C#: `Console.WriteLine("Result: " + x);`
- Java: `System.out.println("Result: " + x);`

## 5. Methods / Functions
- C allows functions outside of classes; Java does not. Methods must be inside a class.
- C# is similar: methods are inside classes.

## 6. No Pointers
- Java does **not** support pointers, unlike C and C# (`unsafe` code).

## 7. Object Creation
- Java (and C#) use `new` for objects:
    ```    java
    MyClass obj = new MyClass();
```
- C creates structs, but objects are not as central.

## 8. Access Modifiers
- Java uses: `public`, `private`, `protected`.
- C# adds `internal`; C does not have these (uses `static` for file-level restriction).

## 9. No Structures, No Unions
- Java has classes only; no `struct` or `union` as in C/C#.

## 10. No Operator Overloading
- Java does **not** support operator overloading (C++/C# do).

## 11. Constants
- Use `final` in Java, not `const`:
    ```    java
    final int MAX = 100;
```

## 12. Comments
Same as C/C#:
```java
// Single-line comment 

/* 
	Multi-line comment 
*/`
```