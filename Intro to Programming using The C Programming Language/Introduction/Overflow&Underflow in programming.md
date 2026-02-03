**Expected to know:**
**Topics Covered:**
**Tags:**
# **Overflow**

**Definition:**  
Overflow occurs when the result of an arithmetic operation exceeds the maximum value that can be represented by a given data type or storage space. This is common with integers and floating-point numbers, where the number of bits used to store the value is fixed.

**How It Happens:**
- When you add, multiply, or otherwise compute a value larger than the maximum representable value, the system cannot store the correct result.
- Instead of an error, many systems "wrap around" the value, storing only the least significant bits.

**Examples:**
- **Unsigned Integer Overflow (8-bit):**  
    An 8-bit unsigned integer can store values from 0 to 255.  
    If you add 1 to 255:
    $255+1=256$
    
    Since 256 cannot be represented in 8 bits, the result wraps around to 0.
    
- **Signed Integer Overflow (8-bit):**  
    An 8-bit signed integer (using two's complement) can store values from -128 to 127.  
    If you add 1 to 127:$127+1=−128$
    
    The value wraps around to the minimum representable value.
    
- **Floating-Point Overflow:**  
    If a floating-point calculation exceeds the largest value that can be represented (e.g., multiplying very large numbers), the result may be stored as "infinity" or cause an error, depending on the system.

**Consequences:**
- Unexpected program behavior, incorrect calculations, and potential security vulnerabilities (e.g., buffer overflows).

---
# **Underflow**

**Definition:**  
Underflow occurs when the result of an arithmetic operation is smaller (in magnitude) than the smallest value that can be represented by the data type. This is most common in floating-point arithmetic, but can also occur with integers.

**How It Happens:**
- For floating-point numbers, underflow happens when the result is closer to zero than the smallest positive (or negative) value that can be represented.
- For integers, underflow typically means the result is less than the minimum representable value, causing a "wrap around" to the maximum value (for unsigned types) or to the opposite end of the range (for signed types).

**Examples:**
- **Unsigned Integer Underflow (8-bit):**  
    An 8-bit unsigned integer can store values from 0 to 255.  
    If you subtract 1 from 0:
    $0−1=255$
    
    The value wraps around to the maximum representable value.
- **Signed Integer Underflow (8-bit):**  
    An 8-bit signed integer can store values from -128 to 127.  
    If you subtract 1 from -128:
    $−128−1=127$
    
    The value wraps around to the maximum positive value.
    
- **Floating-Point Underflow:**  
    If a calculation produces a number smaller than the smallest representable positive value (e.g., multiplying very small numbers), the result may be stored as zero or as a subnormal number (a very small value with less precision).
    
    Example:  
    If the smallest representable positive value is $1 \times 10^{-38}$, but your calculation yields $1 \times 10^{-40}$, the result may be rounded to zero or a subnormal value.

**Consequences:**
- Loss of precision, inaccurate results, and potential logic errors in programs, especially in iterative algorithms or when comparing very small values.

---
