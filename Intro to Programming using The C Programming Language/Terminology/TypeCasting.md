**Expected to know:** [[Data Types and Constants]] [[Variables and their scope]] 
**Topics Covered:** Typecasting into another Data Type

What if while doing some calculation, I want to convert the data type of the variable being used into another data type? Then I use something called TYPECASTING!!!
## **1. What is Type Casting?**

Type casting is the process of converting a variable from one data type to another. It allows a value to be interpreted or stored differently, which is useful when working with different data types in operations.

**Key Points:**

- It ensures compatibility between different data types.
- It can be **implicit** (automatic) or **explicit** (manual).
- It can involve **widening (safe)** or **narrowing (data loss risk)**.

---
## **Types of Type Casting**

There are two types of type casting:
- implicit type casting
- Explicit type casting
### **A) Implicit Type Casting (Automatic Type Conversion)**

- Happens **automatically** when a smaller type is assigned to a larger type.
- No data loss occurs.
- Common in **C, C++, Java, Python, JavaScript, etc.**.

 **Example (in Python, Java, C++)**

```python
x = 10  # Integer
y = x + 2.5  # x automatically converted to float
print(y)  # Output: 12.5
```

```java
int num = 100;
double result = num;  // int → double (automatic)
```

```cpp
int num = 10;
float f = num;  // int → float (implicit)
```

---

### **B) Explicit Type Casting (Manual Conversion)**

- Requires **manual intervention** using casting operators.
- Used when **converting from a larger type to a smaller type** (risk of data loss).
- Different languages have different syntax.

 **Example (Python, Java, C++)**

```python
x = 5.75
y = int(x)  # Explicit casting (float → int)
print(y)  # Output: 5
```

```java
double num = 99.99;
int val = (int) num;  // Explicit conversion
```

```cpp
double d = 10.5;
int i = (int)d;  // Explicit type cast
```

---

## **Type Casting in C**

In **C**, type casting follows the same **implicit and explicit** rules but with additional **pointer-related** casting.

### **Implicit Type Casting**

 **Happens automatically when a smaller type is assigned to a larger type.**

```c
#include <stdio.h>

int main() {
    int a = 10;
    float b = a;  // Implicit conversion (int → float)

    printf("%f\n", b);  // Output: 10.000000
    return 0;
}
```

 **Why?** `float` is larger than `int`, so the conversion is safe.

#### **Implicit Type Promotion in Expressions**

When mixed data types appear in an operation, the **smaller type is promoted** to match the larger type, i.e., smaller type is type casted into larger data type.

```c
#include <stdio.h>

int main() {
    int a = 5;
    float b = 2.5;
    float result = a + b;  // a is promoted to float

    printf("%f\n", result);  // Output: 7.500000
    return 0;
}
```

---

### **Explicit Type Casting in C**

When implicit conversion is **not suitable**, **manual casting** is required.

**Syntax:**

```c
(type) value;
```

#### **When to use???**
#### **Example 1: Preventing Integer Division**

```c
#include <stdio.h>

int main() {
    int a = 5, b = 2;
    float result = (float)a / b;  // Convert a to float

    printf("%f\n", result);  // Output: 2.500000
    return 0;
}
```

**Without casting**, `5 / 2` would be `2` (integer division).

---

#### **Example 2: Casting to Smaller Data Types (Data Loss)**

```c
#include <stdio.h>

int main() {
    float num = 97.9;
    int intNum = (int) num;  // Explicitly cast float to int

    printf("Float: %f, Integer: %d\n", num, intNum);  
    // Output: Float: 97.900000, Integer: 97
    return 0;
}
```

**Warning:** Decimal part is **truncated** (not rounded).

---
## **4. Operator Precedence with Type Casting**

**Type casting has higher precedence than arithmetic operators.**

```c
int x = 10;
int y = 3;
float result = (float)x / y;  // Cast happens first
```

 **Precedence Order:** `()` > `* / %` > `+ -`

---

## **5. When NOT to Use Type Casting**

- **Avoid unnecessary casting**, as it can reduce readability.  
- **Casting larger types to smaller types can cause data loss.**

Example of **data loss**:

```c
int num = 300;
char c = (char) num;  // Loss of data (char range: -128 to 127)
printf("%d\n", c);  // Output: 44 (unexpected value)
```

**Reason:** `char` can store only `-128 to 127`, so `300` overflows.

---

## **6. Summary**

| Type Casting         | When to Use                                               |
| -------------------- | --------------------------------------------------------- |
| **Implicit Casting** | When converting smaller types to larger ones (safe).      |
| **Explicit Casting** | When converting large types to small (risk of data loss). |
| **Avoid Casting**    | When data loss is not acceptable.                         |

---

## **7. Final Thoughts**

- **Implicit casting** is automatic and safe.
- **Explicit casting** is needed when **precision matters** (e.g., division).
- **Pointer casting** is useful in low-level programming.
- **Avoid unnecessary casting** to keep code readable and efficient.
