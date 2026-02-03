**Expected to know:** [[Operators]]
**Topics Covered:** [[#1. Arithmetic Operators]],[[#2. Comparison (Relational) Operators]],[[#3. Logical Operators]],[[#4. Bitwise Operators]],[[#5. Assignment Operators]],[[#6. Membership Operators]],[[#7. Identity Operators]], [[The Math Library]]
**Tags:** [[Operators]]

![[Syntax in Python#**8. Operators and Expressions**]]

Python provides a comprehensive set of operators that allow you to perform various operations on variables and values. These include arithmetic, comparison, logical, bitwise, assignment, membership, and identity operators. Here’s an overview of each category with examples:

---
## **1. Arithmetic Operators

Used for basic mathematical calculations:

| Operator | Name           | Example  | Result |
| -------- | -------------- | -------- | ------ |
| +        | Addition       | `a + b`  | 30     |
| -        | Subtraction    | `a - b`  | -10    |
| *        | Multiplication | `a * b`  | 200    |
| /        | Division       | `b / a`  | 2      |
| %        | Modulus        | `b % a`  | 0      |
| **       | Exponent       | `a ** b` | 10^20  |
| //       | Floor Division | `9 // 2` | 4      |

---

## **2. Comparison (Relational) Operators**

Used to compare two values:

|Operator|Name|Example|
|---|---|---|
|<|Less than|`a < b`|
|>|Greater than|`a > b`|
|<=|Less than or equal to|`a <= b`|
|>=|Greater than or equal to|`a >= b`|
|==|Equal to|`a == b`|
|!=|Not equal to|`a != b`|

These return a Boolean value (`True` or `False`).

---

## **3. Logical Operators**

Used to combine conditional statements:

|Operator|Name|Example|
|---|---|---|
|and|AND|`a > 0 and b > 0`|
|or|OR|`a > 0 or b > 0`|
|not|NOT|`not (a > 0)`|

Logical operators return Boolean results.

---

## **4. Bitwise Operators**

Operate at the bit level on integers:

|Operator|Name|Example|Result|
|---|---|---|---|
|&|Bitwise AND|`10 & 7`|2|
|\||Bitwise OR|`10|7`|
|^|Bitwise XOR|`10 ^ 7`|13|
|~|Bitwise NOT|`~10`|-11|
|<<|Left Shift|`10 << 2`|40|
|>>|Right Shift|`10 >> 1`|5|

---

## **5. Assignment Operators**

Used to assign values to variables, often combined with arithmetic operations:

|Operator|Example|Equivalent to|
|---|---|---|
|=|`a = 7`|Assign 7 to a|
|+=|`a += 1`|`a = a + 1`|
|-=|`a -= 3`|`a = a - 3`|
|*=|`a *= 4`|`a = a * 4`|
|/=|`a /= 3`|`a = a / 3`|
|%=|`a %= 10`|`a = a % 10`|
|**=|`a **= 2`|`a = a ** 2`|
|//=|`a //= 2`|`a = a // 2`|

---

## **6. Membership Operators**

Check for membership in sequences (like lists, strings):

|Operator|Example|Description|
|---|---|---|
|in|`'a' in 'cat'`|True if 'a' is in 'cat'|
|not in|`'x' not in 'cat'`|True if 'x' is not in 'cat'|

---

## **7. Identity Operators**

Check if two variables refer to the same object:

|Operator|Example|Description|
|---|---|---|
|is|`a is b`|True if a and b are the same object|
|is not|`a is not b`|True if a and b are not the same|

---
Python’s operators are intuitive and powerful, supporting a wide range of operations for all built-in data types

---
# Mathematical Functions in Python
Python provides a variety of mathematical functions, both as built-in functions and through the `math` library. Here’s a structured overview of both categories:

---
## **Built-in Mathematical Functions**
These functions are always available in Python without needing to import any module:
- `abs(x)`: Returns the absolute value of a number.
- `pow(x, y)`: Returns `x` raised to the power `y`.
- `round(x, n)`: Rounds a number to `n` decimal places.
- `max(iterable, *args)`: Returns the largest item.
- `min(iterable, *args)`: Returns the smallest item.
- `divmod(a, b)`: Returns a tuple `(a//b,a%b)`.
- `sum(iterable)`: Sums items of an iterable.    
**Examples:**
```python
print(abs(-10))        # 10 
print(pow(2, 3))       # 8 
print(round(3.14756, 2)) # 3.15 
print(max(2, 5, 8, 1)) # 8 
print(min(2, 5, 8, 1)) # 1 
print(divmod(10, 3))   # (3, 1) 
print(sum([1, 2, 3]))  # 6
```

---
## **Mathematical Functions in the `math` Library**

To access more advanced mathematical operations, Python provides the `math` module. You must import it before use:

```python
import math
```

### **Key Functions:**

| Function            | Description                                                      |
| ------------------- | ---------------------------------------------------------------- |
| `math.sqrt(x)`      | $\text{Square root of }x$                                        |
| `math.pow(x, y)`    | $x \text{ raised to the power } y$ (returns float)               |
| `math.ceil(x)`      | $\text{Smallest integer} ≥ x$                                    |
| `math.floor(x)`     | $\text{Largest integer} ≤ x$                                     |
| `math.exp(x)`       | $e^x$                                                            |
| `math.log(x, base)` | $\text{Logarithm of }x\text{ to the specified base}$ (default e) |
| `math.factorial(x)` | $\text{Factorial of }x$                                          |
| `math.gcd(a, b)`    | $\text{Greatest common divisor of a and b}$                      |
| `math.fabs(x)`      | $\text{Absolute value as float}$                                 |
| `math.sin(x)`       | $\text{Sine of }x$ (radians)                                     |
| `math.cos(x)`       | $\text{Cosine of }x$ (radians)                                   |
| `math.tan(x)`       | $\text{Tangent of }x$ (radians)                                  |
| `math.degrees(x)`   | $\text{Converts radians to degrees}$                             |
| `math.radians(x)`   | $\text{Converts degrees to radians}$                             |
| `math.isfinite(x)`  | $\text{Checks if }x\text{ is finite}$                            |
| `math.isnan(x)`     | $\text{Checks if }x\text{ is NaN}$                               |
| `math.isinf(x)`     | Checks if xxx is infinite                                        |
| `math.trunc(x)`     | Truncates xxx to integer                                         |
| `math.comb(n, k)`   | Number of ways to choose kkk items from nnn                      |
| `math.perm(n, k)`   | Number of ways to arrange kkk items from nnn                     |
| `math.hypot(x, y)`  | Euclidean norm, \sqrt{x^2 + y^2}x2+y2                            |
| `math.pi`           | Mathematical constant π\piπ                                      |
| `math.e`            | Mathematical constant eee                                        |
| `math.inf`          | Floating-point positive infinity                                 |
| `math.nan`          | Floating-point NaN                                               |

**Examples:**

```python
import math 

print(math.sqrt(25))      # 5.0 
print(math.pow(2, 3))     # 8.0 
print(math.ceil(3.1))     # 4 
print(math.floor(3.9))    # 3 
print(math.exp(2))        # 7.38905609893065 
print(math.log(8, 2))     # 3.0 
print(math.factorial(5))  # 120 
print(math.sin(math.pi/2))# 1.0 
print(math.pi)            # 3.141592653589793`
```
---

