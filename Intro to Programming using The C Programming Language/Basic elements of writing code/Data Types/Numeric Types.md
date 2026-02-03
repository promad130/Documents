**Expected to know:** [[Data Types and Constants]]
**Topics Covered:** int, float, double along with their ranges
**Tags:** [[Data Types and Constants]]

This data type represents the numerical data/ numbers. On the surface, there are only two types of numeric data type:

---
## **1) Integers

There are a total of 3 common Integer Data types:
- short
- int
- long
And as the name suggests, used to store integers. 
So what is the difference?
SIZE!!!!
Each can store upto/at least different amount of bytes depending upon the system and OS!

### short
Size is 2 bytes(for sure).
- Minimum Value: -32,768
- Maximum Value: 32,767
Again note that short and short int are equivalent.
### int
Size is 4 bytes.
Nah wrong, it's a misconception, but still we don't touch old systems, size is indeed 4 bytes.

It's a common misconception that "int" always has a size of 4 bytes. While that's frequently the case, it's not a guarantee across all C and C++ implementations. Here's a more accurate breakdown:

- **C Standard Flexibility:**
    - The C standard provides minimum size requirements for integer types, but it allows compilers to choose sizes that are appropriate for the target architecture.
    - The standard only guarantees that:
        - `sizeof(short) <= sizeof(int) <= sizeof(long)`
- **Common Scenarios:**
    - On many modern 32-bit and 64-bit systems, "int" is indeed 4 bytes.
    - However, in older systems, or in some embedded systems, "int" might be 2 bytes.
- **Factors Influencing Size:**
    - **Compiler:** The specific C or C++ compiler being used.
    - **Operating System:** The operating system and its architecture.
    - **Hardware:** The underlying hardware architecture.

Therefore, it's safer to say that "int" is _often_ 4 bytes, but not always.

**SIZE of int:**
- `signed int`: -2,147,483,648 to 2,147,483,647 (for 32-bit)
- `unsigned int`: 0 to 4,294,967,295 (for 32-bit)

### long

Now, it is common when people get confused between `long`, and `long int`, but long and long int are equivalent.

When it comes to size, the size of long can be either 8 bytes or 4 bytes. Usually, in a 64-bit system, the size is 8 bytes, but depending on the OS, it can change.

As for a 32-bit system, well who am I kidding, it is a 32-bit system, size would be 4 bytes.

However, there is another category called `long long` or `long long int` (again, both being equivalent), however, size of this thing is at least 8 bytes. The C Implementation mentions that long long has to have a size of 8 bytes, but it can have a larger size.

**SIZE:**
- **long int:**
    - Size: Typically 4 bytes (32 bits) or 8 bytes (64 bits)
    - `signed long int`: -2,147,483,648 to 2,147,483,647 (for 32-bit), or larger for 64 bit.
    - `unsigned long int`: 0 to 4,294,967,295 (for 32-bit), or larger for 64 bit.
- **long long int:**
    - Size: Typically 8 bytes (64 bits)
    - `signed long long int`: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
    - `unsigned long long int`: 0 to 18,446,744,073,709,551,6151

---
## **2) Floating Point Number**

Real numbers are called floating point numbers. There are usually two types of these:
- `float`
- `double`
Now, the number of digits after the decimal is called precision.
Float and double both have default precision of 6, i.e., they will print the values after decimal till 6 digits.
### float
Used for storing single-precision floating-point numbers (numbers with decimal points, e.g., 3.14, -0.5).
- Size: 4 bytes (32 bits)
- Range: Approximately 3.4E-38 to 3.4E+38
Now there is an issue with float, as when storing highly precise data, it does some precision error, due to which we use Double!!!

### Doubles
Used for storing double-precision floating-point numbers (greater precision than float).
- **double:**
    - Size: 8 bytes (64 bits)
    - Range: Approximately 1.7E-308 to 1.7E+308
- **long double:**
    - Size: Varies, often 10 or 12 bytes
    - Range: Greater precision and range than `double`

But how are they stored in memory? Integers can be stored normally in terms on binary numbers, but how are floating point numbers stored?

---
***(After covering [[Pointers and Working with Memory Allocation]])***
# How are these numbers stored?

We know that memory is stored in form of binary numbers, i,e. base 2.
The endianness plays an important role while storing numbers in computer memory. 
Integers are stored in either big-endian or little-endian format depending on the system architecture. Most modern computers use little-endian, but big-endian is still used in some systems and in network protocols 

So for example, the number 20 would be stored as:
`00000000 00000000 00000000 00010100` 

But how would we store the negative of this number?

There are two ways of going about it.

- The first method is to reserve the most significant bit for this purpose, i.e., to tell if the number is positive or negative, i.e., if the MSB is 1, then the number is negative, or else the number is positive(i.e., the MSB is 0).

- Second method is to use **Two's Complement**, which stores the inverse of the number, which is replacing all the 0's with 1's and vice versa, and adding 1 to the number.

Now that covers all the integer values, but what about floating point numbers?
Storing floating point number is different from storing the integers, as we have to store a huge number if we remove the decimal point in a limited and small memory.

## Storage of Double-Precision Floating-Point Numbers in Memory

Double-precision floating-point numbers (`double` in C/C++) are stored using the **IEEE 754 binary64** format. 
This format divides the 64-bit (8-byte) memory into three components:
- **sign bit**, 
- **exponent**, and 
- **significand (mantissa)**. 

Below is a detailed breakdown:
### **Memory Layout (64 bits)**

|Component|Bits|Purpose|
|---|---|---|
|**Sign**|1|Determines if the number is positive (`0`) or negative (`1`).|
|**Exponent**|11|Stores the exponent in **biased form** (actual exponent = stored value - bias).|
|**Significand**|52|Stores the fractional part of the normalized binary number (with an implicit leading `1`).|
### **Key Concepts**

1. **Normalization**  
    Floating-point numbers are represented in **scientific notation**:
    $$(-1)^{\text{sign}} \times 2^{\text{exponent - bias}} \times 1.\text{significand}$$
    Example:  
    $10.75$ in binary is $1010.11$(use positional formula for the decimal part in binary), which normalizes to $1.01011 \times 2^3$.
    
2. **Exponent Bias**
    
    - The exponent is stored as an **unsigned 11-bit integer** with a bias of $1023$.
    - Example: An exponent of 3 is stored as 3+1023=1026 (binary: `10000000010`).
    
3. **Implicit Leading 1**  
    The normalized form always starts with 1.(e.g., 1.01011), so the leading `1` is **not stored** in memory. Only the fractional part (`01011`) is stored in the significand field, padded with zeros to 52 bits.

#### **Example: Storing 10.75 as a `double`**

1. **Convert to Binary**  
    $10.75_{10} = 1010.11_2$.
    
2. **Normalize**  
    $1.01011 \times 2^3$(exponent = 3).
    
3. **Bias the Exponent**  
    Stored exponent = $3+1023=1026$ → binary: `10000000010`.
    
4. **Significand**  
    Fractional part after the leading `1`: `01011`, padded to 52 bits:  
    `0101100000000000000000000000000000000000000000000000`.
    
5. **Sign Bit**  
    $10.75$ is positive → sign bit = `0`.
    
6. **Final 64-Bit Representation**
```text
0 10000000010 0101100000000000000000000000000000000000000000000000
```

### **Special Values**

|Exponent|Significand|Meaning|
|---|---|---|
|All `1`s|All `0`s|±Infinity (sign bit determines ±)|
|All `1`s|Non-zero|NaN (Not a Number)|
|All `0`s|Non-zero|Subnormal numbers (denormalized)|
|All `0`s|All `0`s|Zero (±0, depending on sign bit)|

### **Comparison with `float` and `long double`**

|Type|Size (Bytes)|Significand Bits|Exponent Bits|Bias|
|---|---|---|---|---|
|`float`|4|23 (24 with implicit `1`)|8|127|
|`double`|8|52 (53 with implicit `1`)|11|1023|
|`long double`|10-16*|≥64|≥15|Varies|

*`long double` size and precision are compiler-dependent (e.g., 80 bits in x86 systems).

### **Why This Matters**

- **Precision:** A `double` provides ~15-17 significant decimal digits.
- **Range:** Can represent values from ≈ $1.7×10^{−308} to ≈1.7×10^{308}$
- **Efficiency:** Hardware acceleration for IEEE 754 operations ensures fast computations.
