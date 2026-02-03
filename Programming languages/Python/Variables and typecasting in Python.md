**Expected to know:** [[Variables and their scope]], [[Syntax in Python]], [[TypeCasting]]
**Topics Covered:** [[#Using Variables in print()]], 
**Tags:** [[Variables and their scope]] [[TypeCasting]]

![[Syntax in Python#**3. Variable Declaration and Typing**]]

So that is how we declare variables in python.

---
# Using Variables in print()

```python
first_name = "Hola"
print(f"Hi there {first_name}")
```

This is what we call an **f-string**, where we use some place holders in the string itself.

---
# Type Casting in Python

Type casting (or type conversion) in Python refers to converting a variable from one data type to another. This is a common need when processing data from different sources or when combining values of different types in operations.

---
## **Types of Type Casting**

### **1. Explicit Type Casting (Manual Conversion)**
- You, as the programmer, explicitly convert a value to another type using built-in functions.  
- Common functions include:
    - `int()` – Converts to integer
    - `float()` – Converts to floating point
    - `str()` – Converts to string
    - `list()`, `tuple()`, `set()`, `dict()` – Convert between sequence and mapping types
    - `ord()`, `hex()`, `oct()` – Various other conversions
**Examples:**

```python
x = int("5")      # x becomes integer 5 
y = float("3.14") # y becomes float 3.14 
z = str(10)       # z becomes string "10" 
a = list("abc")   # a becomes ['a', 'b', 'c']`
```
- If the conversion is not possible (e.g., `int("abc")`), Python raises a `ValueError`.

### **2. Implicit Type Casting (Automatic Conversion)**

- Python automatically converts one data type to another when needed, usually in expressions.
- Typically, this happens when combining types of different precision (e.g., `int` and `float`).

**Examples:**

```python
a = 5      # int 
b = 2.0    # float 
c = a + b  # c becomes 7.0 (float)
```

- Here, `a` is implicitly converted to `float` before addition, so the result is a float
#### What if we do int/int?

In Python, the expression `c = a / b` will produce a **float** result, even if both `a`and `b` are integers.
Example:
```python
a = 5
b = 2
c = a / b  # This will be 2.5 (float)
print(c)   # Output: 2.5
print(type(c))  # Output: <class 'float'>

```

**Explanation:**
- In Python 3 (and Python 2 with `from __future__ import division`), the `/` operator always performs **floating-point division**.
- So, `5 / 2` results in `2.5`, not `2` as it would in C with integer division.

If you want **integer division** (like C’s `/` for integers), use the `//` operator:
```python
c = a // b  # This will be 2 (int)
print(c)    # Output: 2
```

---

## **Type Casting Functions Overview**

|Function|Description|Example|
|---|---|---|
|`int()`|Converts to integer|`int("42")` → `42`|
|`float()`|Converts to floating point|`float("3.5")` → `3.5`|
|`str()`|Converts to string|`str(100)` → `"100"`|
|`list()`|Converts to list|`list("abc")` → `['a','b','c']`|
|`tuple()`|Converts to tuple|`tuple([1][2][3])` → `(1,2,3)`|
|`set()`|Converts to set|`set([1][2][2][3])` → `{1,2,3}`|
|`dict()`|Converts to dictionary (from key-value pairs)|`dict([(1,"a"), (2,"b")])` → `{1:"a", 2:"b"}`|
|`ord()`|Converts a character to its Unicode integer|`ord('A')` → `65`|
|`hex()`|Converts integer to hexadecimal string|`hex(255)` → `'0xff'`|
|`oct()`|Converts integer to octal string|`oct(8)` → `'0o10'`|
