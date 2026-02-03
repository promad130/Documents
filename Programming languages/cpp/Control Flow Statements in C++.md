**Expected to know:** [[Introduction to Programming]]
**Topics Covered:**
**Tags:** [[Control Flow Statements]]

This programming language also follow what generally all programming languages follow, hence all the concepts regarding control flow statements and syntax are same as what has been given in [[Control Flow Statements]].

But still, there are a few things that were introduced newly in C++.

---
# What is new?

The standard control flow statements in C++-such as `if`, `if-else`, `switch`, `for`, `while`, `do-while`, `break`, `continue`, `goto`, and `return`-are well-documented and likely already familiar. 

Here, we'll focus on control flow features in C++ that are new or different compared to a basic introduction, and provide detailed explanations for each.

---

## **1. Ranged-based For Loop (`for-each` loop)**

**What’s new:**  
The range-based for loop was introduced in C++11, offering a more concise and readable way to iterate over elements in containers (like arrays, vectors, etc.).

**Syntax:**

```cpp
for (declaration : container) 
{     
	// body 
}
```
**Example:**

```cpp
std::vector<int> 

nums = {1, 2, 3, 4}; 

for (int n : nums) {     
	std::cout << n << " "; 
}
```
**Explanation:**  
This loop automatically iterates over each element in the container, assigning it to `n` in each iteration. It improves readability and reduces errors compared to the traditional index-based `for` loop.

---
## **2. Exception Handling (`try`, `catch`, `throw`)**
**What’s new:**  
C++ introduces structured exception handling, allowing programs to respond to runtime errors or exceptional conditions in a controlled way.

**Syntax:**

```cpp
try {     
	// code that might throw an exception 
} catch (ExceptionType e) {     
	// code to handle the exception 
}
```
**Example:**

```cpp
try {     
	if (x < 0) 
		throw std::runtime_error("Negative value!"); 
} catch (const std::runtime_error& e) {     
	std::cout << e.what(); 
}
```

**Explanation:**

- `try` block contains code that may throw an exception.
- `throw` is used to signal an error.
- `catch` block handles the exception if one is thrown.  
    This mechanism allows for separating error handling from regular code flow, making programs more robust.

---

## **3. `exit()` and Program Halting**

**What’s new:**  
Besides `return`, C++ provides the `exit()` function (from `<cstdlib>`) to terminate a program at any point, returning a status code to the operating system.

**Syntax:**

cpp

`#include <cstdlib> exit(status_code);`

**Example:**

cpp

`if (fatalError) {     std::exit(1); // Immediately terminates the program }`

**Explanation:**  
Unlike `return`, which only exits the current function, `exit()` stops the entire program immediately, regardless of where it is called[4](https://labex.io/questions/what-are-c-control-flow-statements-178534)[5](https://www.wikitechy.com/tutorials/c++/c++-flow-control).

---

## **4. Assignment Expressions in Conditionals**

**What’s new:**  
C and C++ allow assignment expressions within control flow statements, which can be both powerful and error-prone.

**Example:**

cpp

`int x; if (x = 5) { // Assignment, not comparison!     // This block runs because x is assigned 5, which is true (nonzero) }`

**Explanation:**

- The assignment `x = 5` returns the assigned value (5), which is treated as `true`.
    
- This can lead to bugs if `=` is used instead of `==` by mistake.
    
- Similarly, assignments in loops can be used for idiomatic patterns, such as reading input until EOF.
    

**Key Point:**  
Always be careful to distinguish assignment (`=`) from comparison (`==`) in control flow1.

---

## **5. Function Calls and Control Flow**

**What’s new:**  
While function declarations are not control flow statements, function calls do affect control flow by transferring execution to the function and returning back after completion.

**Example:**

cpp

`void greet() {     std::cout << "Hello\n"; } int main() {     greet(); // Control jumps to greet, then returns    std::cout << "Back to main\n"; }`

**Explanation:**  
The function call causes a jump in execution, but this is not classified as a control flow statement per se. Instead, it is a fundamental part of program structure1.

---

## **6. Summary Table: Additional Control Flow Features**

|Feature|Description|Example Usage|
|---|---|---|
|Range-based for loop|Iterates over elements in a container|`for (auto x : v)`|
|Exception handling|Structured error handling with `try`, `catch`, and `throw`|See above|
|`exit()` function|Immediately terminates the program, returning a status code|`exit(1);`|
|Assignment in conditionals|Assignments inside `if`/`while` evaluated as conditions|`if (x = 5)`|
|Function calls|Execution jumps to function and returns, but not a control flow statement|`greet();`|

---

## **Conclusion**

Beyond the standard `if`, `switch`, loops, and jump statements, C++ offers advanced control flow features such as range-based for loops, structured exception handling, program halting with `exit()`, and nuanced behaviors like assignments in conditionals. These features enhance the flexibility, safety, and readability of C++ programs, but also require careful usage to avoid subtle bugs1[4](https://labex.io/questions/what-are-c-control-flow-statements-178534)[5](https://www.wikitechy.com/tutorials/c++/c++-flow-control).

### Citations:

1. [https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/52976615/aed6a99e-89d8-4c96-9a4b-15e213686ad0/Control-Flow-Statements.md](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/52976615/aed6a99e-89d8-4c96-9a4b-15e213686ad0/Control-Flow-Statements.md)
2. [https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/52976615/aed6a99e-89d8-4c96-9a4b-15e213686ad0/Control-Flow-Statements.md](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/52976615/aed6a99e-89d8-4c96-9a4b-15e213686ad0/Control-Flow-Statements.md)
3. [https://www.codecademy.com/learn/c-plus-plus-for-programmers/modules/control-flow-cpp/cheatsheet](https://www.codecademy.com/learn/c-plus-plus-for-programmers/modules/control-flow-cpp/cheatsheet)
4. [https://labex.io/questions/what-are-c-control-flow-statements-178534](https://labex.io/questions/what-are-c-control-flow-statements-178534)
5. [https://www.wikitechy.com/tutorials/c++/c++-flow-control](https://www.wikitechy.com/tutorials/c++/c++-flow-control)
6. [https://www.learncpp.com/cpp-tutorial/control-flow-introduction/](https://www.learncpp.com/cpp-tutorial/control-flow-introduction/)
7. [https://www.ccbp.in/blog/articles/control-structures-in-cpp](https://www.ccbp.in/blog/articles/control-structures-in-cpp)
8. [https://embetronicx.com/tutorials/p_language/c/flow-control-in-c/](https://embetronicx.com/tutorials/p_language/c/flow-control-in-c/)
9. [http://www.it.griet.ac.in/wp-content/uploads/2014/08/UNIT-IIQA.pdf](http://www.it.griet.ac.in/wp-content/uploads/2014/08/UNIT-IIQA.pdf)
10. [https://cplusplus.com/doc/tutorial/control/](https://cplusplus.com/doc/tutorial/control/)
11. [https://www.w3schools.com/CPP/cpp_conditions.asp](https://www.w3schools.com/CPP/cpp_conditions.asp)
12. [https://blog.heycoach.in/flow-control-statements-in-c/](https://blog.heycoach.in/flow-control-statements-in-c/)

---

Answer from Perplexity: [pplx.ai/share](https://www.perplexity.ai/search/pplx.ai/share)