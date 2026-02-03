**Expected to know:** [[Syntax]], [[The C Programming Language]]
**Topics Covered:** What is different in Python syntax, what makes it stand out, 
**Tags:** [[Syntax]]

# Python Syntax: A C Programmer’s Perspective

If you’re coming from a deep background in C, Python’s syntax and structure will feel both familiar in logic and radically different in style. Here are the key highlights and differences, along with some new features unique to Python.

---

## **1. Program Structure and Execution**

- **No main() required:** Python scripts execute from top to bottom—there’s no mandatory main() function, though you can define one for clarity.
- **No header files:** Just write code in a `.py` file; import modules as needed—no need for `.h` files or explicit declarations.

---
## **2. Indentation vs. Braces**

- **Blocks by indentation:** Python uses indentation (whitespace) to define code blocks, not curly braces `{}`. Consistent indentation is mandatory; inconsistent indentation results in errors.
```python
if x > 0:     
	print("Positive")
```
- **No semicolons:** End statements simply by starting a new line. Semicolons are optional and rarely used.

---
## **3. Variable Declaration and Typing**

- **Dynamic typing:** No need to declare variable types. Assignment creates the variable, and its type is inferred at runtime
```python
x = 10      # int 
x = "abc"   # now a string
```
- **No pointers:** Python does not have pointers or pointer arithmetic. All variables are references, but you can't manipulate memory addresses directly.

---
## **4. Comments**

- **Single-line:** Use `#` for single-line comments.
```python
# This is a comment
```
- **No block comment syntax like `/* ... */` in C.**
	- You can enclose a block of text (or code) in triple quotes. If this string is not assigned to a variable or used in an expression, Python will ignore it at runtime.
```python
""" 
This is a block of text that Python will ignore. 
Useful for temporarily disabling code or adding long explanations. 
""" 
print("Hello, World!")`
```
And the "But" here is:
    - Triple-quoted strings are technically string literals, not true comments.
    - If placed at the start of a module, class, or function, they become docstrings and are accessible at runtime (e.g., via `.__doc__`), which is not the case for comments.
    - Using triple-quoted strings for commenting out code is a workaround, not a best practice; it can have side effects, such as showing up in documentation or increasing memory usage if used in certain contexts

---
## **5. Control Flow**

- **No switch/case:** Python lacks a direct equivalent to C’s `switch` statement. Use `if-elif-else` chains instead.
- **Boolean operators:** Use `and`, `or`, `not` instead of `&&`, `||`, `!`.
- **No do-while:** Only `while` and `for` loops are available.

---
## **6. Functions**

- **Definition:** Use `def` keyword. No need to declare return type or parameter types.
```python
def add(a, b):
	return a + b
```
- **Default arguments:** Functions can have default parameter values.
- **No function prototypes needed.**

---
## **7. Data Structures**

- **Lists:** Flexible, mutable arrays (like `std::vector` but built-in). Defined with square brackets: `[1][2][3]`.
- **Tuples:** Immutable sequences, defined with parentheses: `(1, 2, 3)`.
- **Dictionaries:** Key-value mappings, similar to hash tables: `{"key": "value"}`.

---
## **8. Operators and Expressions**
- **Division:** `/` always returns float; `//` is integer (floor) division.
- **Exponentiation:** Use `**` for powers (`x ** 2`).
- **Assignment in expressions:** The "walrus operator" `:=` allows assignment inside expressions (since Python 3.8).
- **String operations:** Use `+` for concatenation, `*` for repetition.

---
## **9. Object Orientation**

- **Classes:** Python supports classes and OOP natively, unlike C.
```python
class MyClass:     
	def __init__(self, value):        
		self.value = value
```
- **No need for struct/function pointer tricks to mimic OOP.**

---
## **10. Memory Management**

- **Automatic:** Python uses garbage collection. No need for `malloc`, `free`, or manual memory management. 
- **No direct memory access or pointer arithmetic.**

---
## **11. Modules and Imports**

- **Imports:** Use `import` to bring in modules. No `#include` or preprocessor.
    
```python
import math
```

---
## **12. Slicing and Sequence Features (New in Python)**

- **Slicing:** Powerful syntax to access sublists, substrings, etc. (will be looked into later)
```python
a = [1, 2, 3, 4, 5] 
print(a[1:4])   
# [2, 3, 4] 
print(a[::-1])  
# [5, 4, 3, 2, 1]
```
- **Negative strides:** Can reverse or skip elements in slices.
- **Multiple assignment:** Assign multiple variables in one line:
```python
x, y = 1, 2
```    
- **List comprehensions:** Compact way to generate lists:
```python
squares = [x*x for x in range(10)]
```

***No direct equivalent in C for these features.***
