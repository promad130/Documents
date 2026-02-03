**Expected to know:** [[Introduction to Programming]] [[Syntax]]
**Topics Covered:**
**Tags:** [[Syntax]]

C++ is a programming language that was build upon The C Programming Language to include things that were essential for a programming language and were missing in The C Programming Language.

(add more on this later as you get to know more stuff!)
[[Syntax]] is generally same across all programming language, but some minor changes might be there.
Now with that, lets have a look at what is different in this language when it comes to syntax.

___
# Basic C++ Structure

A minimal C++ program has three parts: header includes, the main function, and return statement.[^9]

```cpp
#include <iostream>

int main() {
    std::cout << "Hello";
    return 0;
}
```


## Main Function

`main()` is the entry point where execution starts. The compiler looks for `main()` and executes statements inside its curly braces `{}`. It must return an integer (typically 0 for success).[^1][^13][^9]

## Namespaces and std

`std` is a **namespace** that contains the entire C++ standard library (cout, cin, string, vector, etc.). A namespace groups related functions and classes to avoid naming conflicts.[^6]

The `::` operator is the **scope resolution operator** - it accesses members inside a namespace. `std::cout` means "cout from the std namespace".[^6]

## Why std:: Everywhere?

Using `std::` explicitly prevents naming conflicts and makes code clear. When you see `std::string`, you know it's from the standard library, not a custom class.[^8]

Alternative: `using namespace std;` lets you skip `std::`, but it's bad practice in large projects because it dumps all standard library names into your scope.[^6]

## Namespaces vs Classes

**Namespace**: A container that groups related code elements. Just organizational - no instantiation, no inheritance.[^6]

**Class**: A blueprint for creating objects with data members and methods. Supports instantiation, inheritance, and encapsulation.

```cpp
namespace MyNamespace {
    void func() {}
}
MyNamespace::func();  // Call directly

class MyClass {
    void func() {}
};
MyClass obj;
obj.func();  // Need object instance
```


---
# Variables and Data Types
- Variables must be declared with a type, just like in C    
- C++ introduces references (`int &ref = x;`) and supports all C data types plus new ones like `string` (from the Standard Library). (CL)
- Use `const` for constants.

---
# Comments
- Single-line: `// comment`    
- Multi-line: `/* comment */`.

---
# Classes and Objects (OOP)
- **Classes:**  
    C++ introduces `class` for object-oriented programming, supporting encapsulation, inheritance, and polymorphism.    	
```cpp
class Greeter 
{ 
public:     
	void sayHello() const 
	{        
		cout << "Hello, world!" << endl;    
	} 
};
```
- **Objects:**  
    Create objects using `ClassName objectName;` and access members with `objectName.member`.

---
# Structures
- Structs can have methods and access specifiers (public/private), unlike in C.
```cpp
struct Student 
{     
	string name;    
	int age;    
	char grade; 
};
```

---
# Function Overloading
- C++ allows multiple functions with the same name but different parameters (function overloading).
    
```cpp
void print(int i) 
{ 
	cout << i; 
} 

void print(double d) 
{ 
	cout << d; 
}
```

---
# References and Pointers (CL)
- References (`int &ref = x;`) are new in C++ and act as aliases for variables.
- Pointers work as in C, but C++ adds smart pointers in modern versions.

---
# Input and Output
- Use `cin >> variable;` for input and `cout << value;` for output.
- `std::endl` or `\n` for newlines.

---
# Control Structures
- `if`, `else`, `for`, `while`, `do-while` loops work as in C.  
- C++ adds range-based for loops in modern versions.
