***(Follow Along [[Introduction to Verilog]])***

Digital Design is an engineering discipline that works with designing digital systems that take in some data, do some operations and produce an output.
These digital systems usually work with binary system, i.e., only two values(i.e., 0 and 1). They are the foundation to modern computing and smartphones, and digital communication equipment.

But before we jump into digital system, it is important to understand the types of signals. There are basically two types of Signals:
- **Analog signals:**
	- Analog signals are continuous signals, that can take values in any range, for example, temperature or sound signals.
	- These basically gives a continuous curve when drawn on an Amplitude VS time graph
- **Discrete Signals:**
	- These are discrete signals, i.e., takes in two values which a digital signal can manipulate.
Diagrammatically, an analog signal is continuous in time and amplitude, while a digital signal is sampled and quantized into discrete steps.
Now digital systems use discrete signals due to it's simplicity and easy implementation. Most importantly, they are predictable, i.e., in an analog signal, the value can be anything as there isn't a defined range, while in an discrete system, values are deterministic as they work on a fixed set of them, hence they use a specific, well-defined levels.

Examples of discrete sets:
    - Decimal digits (0–9)
    - Alphabet letters (A–Z)
    - Chessboard squares (64 squares)
Digital systems use **binary values**, commonly represented as 0 and 1, which are easy to implement with electronic devices like transistors.

---
## But what is a Digital System?
It is a system that takes in an input and gives out an output depending upon the internal structure or rules of the system
These usually work in binary systems, i.e., they work with 0 and 1.
This binary representation makes it easier to design reliable hardware and handle the noise and distortions present in physical signals.

A digital system consists of digital devices, that help it execute rules on the given input.
### Digital Devices
It is a system that runs tasks or operates on data which is usually of two values/ states (i.e., 0 and 1), it is a component that is used in digital system that does a simple operation, and a collection of these makes up a Digital System.
Hence:
- A **digital system** refers to the overall setup or network of components working together to process digital information.
- A **digital device** can be any individual part or subsystem within that digital system—such as a logic gate, flip-flop, register, or even a complete microprocessor.

A digital system has the following characteristics:
- **Discrete State operations:**
	- it works with the signals that have discrete states, most commonly two states/values
- **Input and Output**
	- It takes in and input, and is sure to give out and output after processing the input, applying rules on it and operating on it.
- **Deterministic behaviors**
	- This one would be obvious that the output would be of deterministic behavior as it works on discrete signals, specially of two states and following boolean logic, making the output deterministic and predictable.
The most obvious example of digital devices are logic gates:
![[Pasted image 20250830025032.png]]

### But why binary?
- Binary numbers have only two states, which can be physically implemented using two voltage levels, e.g., 0 V for logic 0 and 5 V for logic 1.
- This binary system ensures simplicity and robustness in electronic circuits.
Plus it has became the basics for all modern digital circuits.
Also, it helps in dealing with noise in the signals, i.e., for if the signal is `4.8V`, then also it it interpreted as `1`, i.e., a signal of `5V` helping in dealing with noise and distortion, improving reliability in the system.

---
## Historical context as to why a binary system:
Imagine you are in a room, and you can see a window, now you want to send out a message to an entity observing you through that window, but it cannot hear you. How would you send out a signal?
An answer would be to use a visual signal, i.e., shine something so a hand signal of some sort that is simple to perform and understand, but how would the entity know what the signs or light patterns you send mean?

For solving these issues, various form of binary communication systems were introduced. The most-known ones are:
- **Morse Code (Invented 1837)**
	- Uses only **two symbols**: a short signal (dot) and a long signal (dash).
	- These signals can be sent via sound, light, or electrical pulses.    
	- Each pattern of dots and dashes corresponds to letters, numbers, or punctuation.
	- It is a binary system in the sense that it uses two discrete signal types to represent information.

- **Braille (Invented 1824 by Louis Braille)**
	- Another binary system designed for tactile reading by visually impaired people.    
	- Uses patterns of **raised dots** (present or absent) to represent letters.
	- Both Morse and Braille encode information in units with two states — reinforcing the binary principle.

Notice that the most known ones have something in common, both uses a binary system as a form of communication.
This lays the conceptual foundation for binary digital systems and computers.

### But how to capture an analog signal in the form of an discrete signals?
The answer lies in **Sampling and Quantization**.
- Sampling captures discrete points from a continuous analog signal at set intervals.
- Quantization assigns discrete numeric levels to the sampled values, i.e., the values that we got through the analog signal.
These steps convert an analog signal into a digital representation that digital system can process.

(Tut: Complements and Radix)
(Extra read: Binary Codes from Digital Design Book)

---
# Number Systems
![[Number System]]

---
By referring to the above document, it must be now quite obvious what positional formula is, how number systems work, and how conversions are done along with the logic behind it.

Now let's have a look at a number system conversion which is quite important when it comes to Digital Systems, i.e., to binary numbers.

## Understanding Binary Numbers
Following the positional formula, it is obvious that a decimal number can be expressed as a sum of digits multiplied by powers of 10 (base 10).
Similarly, a binary number is a sum of bits multiplied by powers of 2 (base 2).

For example, the binary number $(11010.11)_{2}$ can be expressed as:
$$(1\times (10)^{(100)}+1\times (10)^{(11)}+0+1\times (10)^{(1)}+0+1\times (10)^{(-1)}+1\times (10)^{(-10)})_{2}$$
OR
$$(1\times (2)^4+1\times (2)^3+0+1\times (2)^1+0+1\times (2)^{-1}+1\times (2)^{-2})_{10}$$
So in binary, we have either 0 or 1, so each placeholder value either contributes to the whole number or does nothing.

Also, in binary, we have a format of specifying larger values for ease of understanding and simplicity:

| Value                   | Defination      | Representation |
| ----------------------- | --------------- | -------------- |
| $2^{10}(1024)$          | Kilo (kilobyte) | 1K             |
| $2^{20}(1,048,576)$     | Mega (megabyte) | 1M             |
| $2^{30}(1,073,741,824)$ | Giga (gigabyte) | 1G             |
| and so on...            |                 |                |
## Number base conversions
There are two types of conversions we can do, from a lower radix to an larger one, or other way round. It would be helpful to keep the positional formula in mind for a better clarity behind these conversion's logics.
It might seem that the method used for conversion from a lower to upper radix can also be used in upper to lower radix, which is true, you can, but it can complicate things as the addition and multiplication rules change. This will become more clear with an example.
Positional formula:
$$(\;.\;.\;.\;.a_na_{n-1}.\;.\;.a_2a_1a_0.a_{-1}a_{-2}.\;.\;.\;.\;)_{k} = \sum_{i}^{}a_ik^{i}$$
OR:
$$(a_{n-1}a_{n-2}a_{n-3}.\;.\;.\;.a_3a_2a_1.a_{-1}a_{-2}a_{-3}.\;.\;.\;.a_{(-(m-1))} = a_{n-1}k^{n-1}+a_{n-2}k^{n-2}+a_{n-3}k^{n-3}.\;.\;.\;.a_2k^2+a_1k^1+a_0k^0.a_{-1}k^{-1}+a_{-2}k^{-2}+a_{-3}k^{-3}+.\;.\;.\;.a_{-(m-3)}k^{-(m-3)}+a_{-(m-2)}k^{-(m-2)}a_{-(m-1)}k^{-(m-1)}$$
![[Pasted image 20250820025616.png]]
### Conversion from Decimal to Base r (Integer Part)
- Divide the decimal number by the base $r$.
- Record the remainder as the least significant digit(Why? Look at the positional formula(in base 10, as the value is in base 10), and do whats told here, you'll get it).
- Use the quotient for the next division.
- Repeat until the quotient is zero.
- Remainders collected in reverse give the number in base $r$.

**Example:** Convert 41 to binary (base 2).
- $41 / 2 = 20$ remainder $1$ (*LSB*(**least significant digit**, the rightmost))
- $20 / 2 = 10$ remainder $0$
- $10 / 2 = 5$ remainder $0$
- $5 / 2 = 2$ remainder $1$
- $2 / 2 = 1$ remainder $0$
- $1 / 2 = 0$ remainder $1$ (*MSB*(**Most significant digit**, the leftmost))
**Result: $(101001)_2$

### Conversion from Decimal Fraction to Base r (Fractional Part)
- Multiply decimal fraction by base $r$.(Again, look at the positional formula in radix of the value we are converting, you'll get it, perform the next steps)
- Integral part of the result is the digit.
- Fractional part is used for next multiplication.
- Repeat until fraction becomes zero or desired precision reached.

**Example:** Convert $0.6875$ to binary.
- $0.6875 * 2 = 1.375$ → digit $1$
- $0.375 * 2 = 0.75$ → digit $0$
- $0.75 * 2 = 1.5$ → digit $1$
- $0.5 * 2 = 1.0$ → digit $1$
Result: $(0.1011)_2$

Now, if we were to convert from radix 2 to 10, then for integer part, we would just find out the value by placing the placeholders/place values in the positional formula, and do mathematical operations in radix 10.
We could however, follow the same method we followed for Decimal to base 2, but then, we would have to do all the operations we did then into base 2.

Same applies for the fractional part. Hence this is what was meant when said:

<blockquote><q>It might seem that the method used for conversion from a lower to upper radix can also be used in upper to lower radix, which is true, you can, but it can complicate things as the addition and multiplication rules change. This will become more clear with an example.</q></blockquote>

(Optional extra read: Connection with chaos theory(check out Digital design space's last second thread's last query))

---
# Binary Logic System
Binary logic deals with variables called "*Binary Variables*" that deals with two values, 0 and 1. The meaning of them can be different, i.e., *true* or *false*, *yes* or *no*, etc,.

Now, binary logic consists of two main things, binary variables and operators. Binary variables are represents by letters like K, Q, A, B, x, y, z, etc,. And the operators are mainly AND, OR and NOT.(refer logics in CS to know what they mean)

- AND: This operation is represented by a dot or by the absence of an operator. For example, $x . y = z$ or $xy = z$ is read “x AND y is equal to z.”
- OR: This operation is represented by a plus sign. For example, $x + y = z$ is read “x OR y is equal to z,”
- NOT: This operation is represented by a prime (sometimes by an overbar). For example, $x' = z$ (or $\bar{x} = z$) is read “not x is equal to z,” meaning that z is what x is not.

Definitions of logical operations may be listed in a compact form called **truth tables**. A *truth table* is a table of all possible combinations of the variables, showing the relation between the values that the variables may take and the result of the operation.

## Logic Gates
Every signal or data is actually a voltage that lies in a certain range. The electric signals are analog signals with the voltage value that lies between 0 V to 3 V, but they are interpreted as either 0 or 1 by the digital system / device that works with them.

In practice, there is an acceptable range for these values to account for noise and distortion, i.e., for 0, the acceptable range is 0 V to 1 V, and for 1 the acceptable range is 2 V to 3 V. The gap between these ranges is called transition region, where the bit flip happens, i.e., the state / value of the binary variable changes:

![[Pasted image 20250820034740.png]]

## Boolean Algebra
The binary logic introduced earlier is part of an algebra called Boolean Algebra. This is used in Digital Systems to design algorithms and digital devices. This helps us understand the formulas and logics more clearly.
<blockquote><q>Boolean algebra, like any other deductive mathematical system, may be defined with a set of elements, a set of operators, and a number of unproved axioms or postulates.</q></blockquote>

### Defining a few things:
- **Set**
	- A set of elements is any collection of objects, usually having a common property. 
	- If S is a set, and $x$ and $y$ are certain objects, then the notation $x \in S$ means that $x$ is a member of the set $S$ and $y \notin S$ means that $y$ is not an element of $S$.
- **Binary Operator:**
	- A binary operator defined on a set $S$ of elements is a rule that assigns, to each pair of elements from $S$, a unique element from $S$. 
	- As an example, consider the relation $a * b = c$. We say that $*$ is a binary operator if it specifies a rule for finding $c$ from the pair $(a, b)$ and also if $a, b, c \in S$. 
	- However, $*$ is not a binary operator if $a, b \in S$, and if $c \notin S$.

### Binary Operator Postulates
The postulates of a mathematical system form the basic assumptions from which it is possible to deduce the rules, theorems, and properties of the system. The most common postulates used to formulate various algebraic structures are as follows:
1. **Closure**
    - The set is closed under the binary operators.
    - For every pair of elements in the set, the operation results in another element of the set.
    - Example: The AND or OR of any two Boolean elements (0 or 1) results in 0 or 1, both in the set {0,1}.
    
2. **Associative Law**
    - The way in which operands are grouped does not affect the result.
    - For all elements $x, y, z: (x⋅y)⋅z=x⋅(y⋅z)$ (for AND), and similarly for OR.
    
3. **Commutative Law**
    - The order of operands does not change the result.
    - For all elements $x, y: x⋅y=y⋅x$ and $x+y=y+x$.
    
4. **Identity Element**
    - There exists an identity element for each operator that leaves any element unchanged.
    - For AND, the identity element is $1: x⋅1=x$.
    - For OR, the identity element is $0: x+0=x$.
    
5. **Inverse (Complement)**
    - Every element has an inverse (complement) such that the element AND its complement is the identity for OR, and vice versa.
    - For each element x, there exists a complement $x′$ such that:
        - $x⋅x′=0$
        - $x+x′=1$
    
6. **Distributive Law**
    - One operation distributes over the other.
    - For all elements $x$, $y$, $z$:
        - $x⋅(y+z)=(x⋅y)+(x⋅z)$
        - $x+(y⋅z)=(x+y)⋅(x+z)$

These axioms form the foundation of Boolean algebra as an algebraic structure and are essential for manipulating and simplifying logic expressions in digital design.
Hence, for every boolean operator to exist, it has to follow these.

A *field* is an example of an algebraic structure. A field is a set of elements, together with two binary operators, each having properties 1 through 5 and both operators combining to give property 6.
For example, a set with the operators $+$ and $.$

### Two-valued boolean algebra
Boolean algebra with only two elements is called **Two-valued boolean algebra**.
#### What is Two-Valued Boolean Algebra?
Two-valued Boolean algebra is an algebraic system where the set of elements contains only two members: **0** and **1**. These elements represent the two logical states in digital circuits:
- 0 = **False** or **Low**
- 1 = **True** or **High**

This algebra uses two binary operators and one unary operator:
- Binary operators: **AND (·)** and **OR (+)**
- Unary operator: **NOT (Complement or ′)**

Two-valued Boolean algebra is the mathematical foundation for designing and analyzing digital logic circuits, where signals represent two distinct states.

---
#### Boolean Algebra Axioms (Huntington Postulates for Two-Valued Boolean Algebra)
These axioms define the behavior of Boolean algebra on the set B={0,1}B={0,1} with the operators AND (·) and OR (+), and complement (′). They must satisfy the following:

1. **Closure**
	- If $a, b \in S$, then $a * b \in S$.
	- _Example (Addition over $\mathbb{N}$):_ $a+b$ is a natural number, but $a-b$ need not be.
2. **Associative Law**
	- $(x * y) * z = x * (y * z)$ for all $x, y, z \in S$.
3. **Commutative Law**
	- $x * y = y * x$ for all $x, y \in S$.
4. **Identity Element**
	- There exists an $e \in S$ such that $e * x = x * e = x$ for all $x \in S$.
	- _Example (For addition over $\mathbb{Z}$):_ $0$ is the identity, since $x+0=0+x=x$.
5. **Inverse**
	- For every $x \in S$ with identity $e$, there exists $y \in S$ such that $x * y = e$.
	- _Example:_ $a + (-a) = 0$ over integers.
6. **Distributive Law**
	- For two operations $*$ and $\cdot$ over $S$:
	- $x * (y \cdot z) = (x * y) \cdot (x * z)$.

However, these are the specialized Boolean algebra postulates:
1. **Closure**
    - The algebra is closed under AND and OR:
        $x⋅y∈B$ , $x+y∈B$ , $∀x,y∈B$
2. **Identity Elements**
    - There exists identity elements for AND and OR:
        $x⋅1=x$ , $x+0=x$ , $∀x∈B$
3. **Commutative Laws**
    - Operators are commutative, meaning order does not matter:
        $x⋅y=y⋅x$ , $x+y=y+x$
4. **Distributive Laws**  
    - AND distributes over OR and OR distributes over AND:
        $x⋅(y+z)=(x⋅y)+(x⋅z)$, $x+(y⋅z)=(x+y)⋅(x+z)$
5. **Complement Laws**  
    - Every element has a complement $x′$ such that:
        $x⋅x′=0$ , $x+x′=1$
6. **Existence of at least two distinct elements**
    - The set B contains at least two distinct elements 0 and 1, satisfying:
        $0 \neq 1$

---
#### Truth Tables
- **AND Operation Truth Table ($x⋅y$)**

| x   | y   | x⋅y (AND) |
| --- | --- | --------- |
| 0   | 0   | 0         |
| 0   | 1   | 0         |
| 1   | 0   | 0         |
| 1   | 1   | 1         |

- $x⋅y$ is $1$ only if both $x$ and $y$ are $1$.    

-  **OR Operation Truth Table ($x+y$)**

| x   | y   | x+y (OR) |
| --- | --- | -------- |
| 0   | 0   | 0        |
| 0   | 1   | 1        |
| 1   | 0   | 1        |
| 1   | 1   | 1        |

- $x+y$ is $1$ if either $x$ or $y$ (or both) are $1$.    

- **NOT Operation Truth Table ($x′$)**

| x   | x′ (NOT) |
| --- | -------- |
| 0   | 1        |
| 1   | 0        |

- $x′$ is the complement (logical negation) of $x$.

---
## Duality
### What is Duality?
The **duality principle** states that every algebraic expression or theorem in Boolean algebra remains valid if:
- All **AND (·)** operators are replaced by **OR (+)** operators,
- All **OR (+)** operators are replaced by **AND (·)** operators,
- All **0**s are replaced by **1**s,
- All **1**s are replaced by **0**s,
and vice versa.

### How to Obtain the Dual of a Boolean Expression?
To find the _dual_ of a Boolean expression:
- Replace every OR $(+)$ with AND $(·)$.
- Replace every AND $(·)$ with OR $(+)$.
- Replace all 0's with 1's and all 1's with 0's.
- Variables and complements remain unchanged.

Some axioms and theorems come in pairs, each a dual of the other:

| Original Axiom/Theorem                           | Dual Axiom/Theorem                               |
| ------------------------------------------------ | ------------------------------------------------ |
| $x+0=x$ (Identity for OR)                        | $x⋅1=x$ (Identity for AND)                       |
| $x+x′=1$ (Complement for OR)                     | $x⋅x′=0$ (Complement for AND)                    |
| $x+y=y+x$ (Commutative OR)                       | $x⋅y=y⋅x$ (Commutative AND)                      |
| $x+(y⋅z)=(x+y)⋅(x+z)$ (Distributive OR over AND) | $x⋅(y+z)=(x⋅y)+(x⋅z)$ (Distributive AND over OR) |

---
## Operator Precedence
The operator precedence for evaluating Boolean expressions is:
1) parentheses, 
2) NOT, 
3) AND, and 
4) OR.

---
## Basic Theorems:
![[Pasted image 20250820051055.png]]
![[Pasted image 20250820051109.png]]
![[Pasted image 20250820051122.png]]
![[Pasted image 20250820051141.png]]
![[Pasted image 20250820051157.png]]
![[Pasted image 20250820051226.png]]
![[Pasted image 20250820051248.png]]

# Complements of Numbers
### **What are Complements?**
Complements are mathematical techniques used to represent negative numbers and simplify arithmetic operations (especially subtraction) in computer systems and digital logic.  They allow us to perform subtraction using addition circuits.

---
## 1. DIMINISHED RADIX COMPLEMENT (DRC)
Also known as **One's Complement** (for binary) or **Nine's Complement** (for decimal)

### **Definition:**
For an n-digit number in base r, the Diminished Radix Complement is: 
```
DRC = (r^n - 1) - N
```
Where:
- **r** = base/radix (2 for binary, 10 for decimal, 16 for hexadecimal)
- **n** = number of digits
- **N** = original number

### Binary Example (One's Complement):
Find the one's complement of **1011** (4-bit number, base 2):
```
DRC = (2^4 - 1) - 1011
    = 15 - 11
    = 4
    = 0100 (in binary)

Quick Method:  Flip all bits
1011 → 0100
```

### Decimal Example (Nine's Complement):
Find the nine's complement of **325** (3-digit number, base 10):
```
DRC = (10^3 - 1) - 325
    = 999 - 325
    = 674

Quick Method: Subtract each digit from 9
9-3=6, 9-2=7, 9-5=4 → 674
```

---
## 2. RADIX COMPLEMENT (RC)
Also known as **Two's Complement** (for binary) or **Ten's Complement** (for decimal)

### **Definition:**
For an n-digit number in base r, the Radix Complement is: 
```
RC = r^n - N
```
Where the values are the same as above.

### Relationship with DRC:
```
RC = DRC + 1
```

### Binary Example (Two's Complement):
Find the two's complement of **1011** (4-bit number, base 2):
```
Method 1 - Using formula:
RC = (2^4) - 1011
   = 16 - 11
   = 5
   = 0101 (in binary)

Method 2 - Using DRC + 1:
One's complement:   1011 → 0100
Add 1:            0100 + 1 = 0101

Quick Method: Flip all bits and add 1
1011 → 0100 (flip) → 0101 (add 1)
```

### Decimal Example (Ten's Complement):
Find the ten's complement of **325** (3-digit number, base 10):
```
Method 1 - Using formula: 
RC = (10^3) - 325
   = 1000 - 325
   = 675

Method 2 - Using DRC + 1:
Nine's complement: 325 → 674
Add 1:            674 + 1 = 675

Quick Method: Subtract from 10 (rightmost), then subtract others from 9
10-5=5, 9-2=7, 9-3=6 → 675
```

## The Two's Complement Decoding Formula

For an n-bit signed number in Two's Complement:

```
If MSB (sign bit) = 0:  Number = normal binary value
If MSB (sign bit) = 1:  Number = -(2^(n-1)) + (remaining bits)
```

Or more formally:
```
Decimal Value = -b(n-1) × 2^(n-1) + Σ(b(i) × 2^i)
                where b(n-1) is the MSB (sign bit)
                and i goes from 0 to n-2
```

---
### Decoding 1011 as a 4-bit Signed Number
Let me break down **1011** step by step:

```
Bit pattern:   1 0 1 1
Position:     3 2 1 0  (powers of 2)

Standard binary would be: 
= 1×2³ + 0×2² + 1×2¹ + 1×2⁰
= 8 + 0 + 2 + 1
= 11 (unsigned)

But in Two's Complement (signed), the leftmost bit is NEGATIVE:
= -1×2³ + 0×2² + 1×2¹ + 1×2⁰
= -8 + 0 + 2 + 1
= -8 + 3
= -5 ✓
```

### Why Does the MSB Become Negative?
In Two's Complement for n-bit numbers:

- The MSB represents **-2^(n-1)** instead of **+2^(n-1)**
- For 4-bit: MSB = -2³ = -8 (not +8)
- For 8-bit: MSB = -2⁷ = -128 (not +128)
- For 16-bit: MSB = -2¹⁵ = -32,768 (not +32,768)

## **Visual Representation**
```
4-bit Two's Complement Range:  -8 to +7

Bit Pattern → Decimal Value
  0111    →  +7
  0110    →  +6
  0101    →  +5
  0100    →  +4
  0011    →  +3
  0010    →  +2
  0001    →  +1
  0000    →  0
  1111    →  -1   (all 1s)
  1110    →  -2
  1101    →  -3
  1100    →  -4
  1011    →  -5   ← HERE! 
  1010    →  -6
  1001    →  -7
  1000    →  -8   (most negative)
```

## Comparison Table

| Feature | Diminished Radix (DRC) | Radix Complement (RC) |
|---------|------------------------|----------------------|
| **Formula** | (r^n - 1) - N | r^n - N |
| **Also Called** | One's (binary), Nine's (decimal) | Two's (binary), Ten's (decimal) |
| **Relationship** | RC = DRC + 1 | DRC = RC - 1 |
| **Zero Representation** | Two representations | One representation |
| **Use Case** | Less common | Standard in computers |
| **Range (n-bit)** | -(2^(n-1)-1) to +(2^(n-1)-1) | -(2^(n-1)) to +(2^(n-1)-1) |

---

## **Why Use Complements?**

### **1. Simplify Subtraction**
Instead of:  A - B
We can do: A + RC(B)

**Example (8-bit binary):**
- 12 - 5 = 7
- 00001100 - 00000101 = ? 
- 12 + Two's complement of 5
- 00001100 + 11111011 = 100000111
- Ignore overflow → 00000111 = 7 ✓

### **2. Represent Negative Numbers**
In Two's Complement, the leftmost bit is the sign bit: 
- 0 = positive
- 1 = negative

### **3. Simplify Hardware**
Computers only need adders, not separate subtractor circuits.

---

## **Practice Problems**

### **Problem 1:** Find the one's and two's complement of **1101** (4-bit)
```
One's Complement (DRC):
  Method:  Flip bits
  1101 → 0010

Two's Complement (RC):
  Method:  Flip bits and add 1
  1101 → 0010 → 0011
  OR: 16 - 13 = 3 = 0011
```

### **Problem 2:** Find the nine's and ten's complement of **567** (3-digit)
```
Nine's Complement (DRC):
  Method: Subtract each digit from 9
  9-5=4, 9-6=3, 9-7=2 → 432

Ten's Complement (RC):
  Method: Nine's complement + 1
  432 + 1 = 433
  OR: 1000 - 567 = 433
```

---


# Addition Logic Design
Addition Logic Design in digital electronics refers to the design and implementation of circuits that perform arithmetic addition of binary numbers. 

It encompasses the creation of combinational logic circuits such as half adders and full adders, which add single bits or multi-bit numbers, respectively.

## Half Adder
A half adder is a fundamental combinational logic circuit in digital design that adds two single-bit binary inputs, usually labeled A and B. 

It produces two outputs: the SUM (S) and the CARRY (C). 
The SUM output is the least significant bit and is obtained by an XOR gate, while the CARRY output, the most significant bit, is produced using an AND gate.
![[Pasted image 20250830030127.png]]

### Key Characteristics
- Inputs: Two single-bit binary numbers (A and B).
- Outputs: Sum (S) and Carry (C).
- Sum formula: $S = A \oplus B$ (XOR operation).
- Carry formula: $C = A \cdot B$ (AND operation).
- It does not account for any carry input from a previous addition (i.e., no carry-in).

### Truth Table

| A   | B   | Sum (S) | Carry (C) |
| --- | --- | ------- | --------- |
| 0   | 0   | 0       | 0         |
| 0   | 1   | 1       | 0         |
| 1   | 0   | 1       | 0         |
| 1   | 1   | 0       | 1         |

### Applications
- Building blocks for more complex adders like full adders.
- Used in arithmetic circuits, data processing, address decoding, encoders, decoders, multiplexers, demultiplexers, and counters.
- Useful in places where addition of two single bits is required.

### Advantages and Disadvantages
- Advantages: Simple design and fast operation.
- Disadvantages: Cannot handle carry-in, limiting its use in multi-bit binary addition.

The half adder is thus a basic yet essential digital logic circuit foundational for binary addition operations in digital systems.

## BCD Addition
BCD Addition is a method of adding numbers where the numbers are represented in Binary-Coded Decimal (BCD) form. To teach BCD Addition comprehensively, let's start with the basics and move step-by-step.

### What is BCD (Binary-Coded Decimal)?
BCD is a way to represent each decimal digit (0 to 9) individually using a fixed number of binary bits, usually 4 bits per digit. Unlike pure binary representation where the entire number is represented as a binary number, BCD encodes each digit separately.

- Each decimal digit (0 to 9) is represented by a 4-bit binary code, for example:
    - 0 is 0000
    - 1 is 0001
    - 2 is 0010
    - ...
    - 9 is 1001
    
- BCD codes from 1010 (decimal 10) to 1111 (decimal 15) are not valid for BCD digits.

This encoding makes it easier to display decimal numbers in digital systems like calculators and clocks.

### Basics of BCD Addition
When adding two BCD digits, the process resembles binary addition but requires extra steps to ensure the result remains a valid BCD code:

1. Add the two BCD digits normally as if they were binary numbers, including any carry from the previous addition.
2. Check the sum:
    - If the 4-bit sum is less than or equal to 9 (1001 in binary) and there is no carry out, the result is a valid BCD digit.
    - If the sum is greater than 9 or there is a carry out, the result is _not_ a valid BCD digit.
3. If the result is not valid (sum > 9 or carry out = 1), add 6 (0110 in binary) to the sum to correct it. Adding 6 adjusts the sum back into the BCD valid range and produces a carry to move into the next higher decimal digit.
4. The carry resulting from this correction is forwarded to the next digit's addition in multi-digit BCD addition.

### Why add 6?
The decimal digits go from 0 to 9, which in 4-bit binary is 0000 to 1001. When a sum exceeds 1001 (9 decimal), it means the binary sum is not valid for a single BCD digit. Adding 6 (which is 0110 in binary) adjusts the binary result to the next valid BCD code while generating a carry to the next digit.

## Step-by-Step Example of BCD Addition

Let’s add 37 and 48 in BCD:
- 37 in BCD: 0011 (3) 0111 (7)
- 48 in BCD: 0100 (4) 1000 (8)

## Add the rightmost digits (7 + 8)
- 0111 + 1000 = 1111 (binary), which is 15 decimal.
- 15 > 9, so this is not valid in BCD.
- Add 6 (0110) to correct: 1111 + 0110 = 1 0101 (binary), which is 21 decimal.
- The lower 4 bits 0101 represent 5, and the leftmost 1 is a carry to the next digit.
- So, the result digit here is 5, with a carry of 1.

## Add the next digits plus carry (3 + 4 + 1 carry)
- 0011 + 0100 + 0001 = 1000 (binary), which is 8 decimal.
- 8 <= 9, and no carry out, so it’s valid BCD.

## Final result
- Left digit: 1000 (8)
- Right digit: 0101 (5)

So, 37 + 48 = 85 in decimal, and in BCD, it is 1000 0101.

## Summary Steps for BCD Addition
- Add the digits including carry.
- If sum > 9 or carry out, add 6 to correct.
- Forward carry to next digit.
- Repeat for all digits.

This method allows safe and accurate addition on numbers represented in BCD, commonly used in digital electronics for decimal calculations.

If needed, I can provide more examples or explain how BCD arithmetic units are implemented in digital circuits. Would that be helpful?

## Binary Subtractor 
The subtraction of unsigned binary numbers can be done most conveniently by means of complements, as discussed. Remember that the subtraction A - B can be done by taking the 2’s complement of B and adding it to A. The 2’s complement can be obtained by taking the 1’s complement and adding 1 to the least significant pair of bits. The 1’s complement can be implemented with inverters, and a 1 can be added to the sum through the input carry.

![[Pasted image 20251211182947.png]]

The addition and subtraction operations can be combined into one circuit with one common binary adder by including an exclusive-OR gate with each full adder. A four-bit adder-subtractor circuit is shown in Fig. 4.13. The mode input M controls the operation.

When M = 0, the circuit is an adder, and when M = 1, the circuit becomes a subtractor.

 If the two binary numbers are considered to be unsigned, then the C bit detects a carry after
addition or a borrow after subtraction, which helps in detecting overflow if needed.
If the numbers are considered to be signed, then the V bit detects an overflow. If V = 0 after an addition or subtraction, then no overflow occurred and the n-bit result is correct. If V = 1, then the result of the operation contains n + 1 bits, but only the rightmost n bits of the number fit in the space available, so an overflow has occurred. 
The (n + 1)th bit is the actual sign and has been shifted out of position, as the MSB should not change for signed numbers, and V   checks for that.

---
# Boolean Functions
A **Boolean function** is a mathematical function that takes binary inputs (variables that have values 0 or 1) and produces a binary output (also 0 or 1).
Such functions describe logical relationships between the inputs and the output. The input values correspond to the possible states of a logical variable—true (1) or false (0).

For example, a function $f(x,y,z) = x \cdot y + z'$ means that the output depends on the AND of x and y or the complement (NOT) of $z$. Boolean functions provide a foundation for designing digital circuits.

Components of a boolean functions are:
- Binary Variables
- Logical Operators

Every Boolean function can be completely described by a **truth table**. A truth table lists all possible input combinations and the corresponding output. For $n$ inputs, there are $2^n$ possible combinations.

## Boolean Algebra and Simplifications
In digital design, often the original Boolean functions are simplified to:
- Reduce the number of gates required in a circuit.
- Reduce complexity, cost, and power consumption.
- Increase speed by reducing gate delays.

The simplification is done using Boolean algebra laws such as:
- **Idempotent Law:** $A+A=A$, $A\cdot A=A$
- **Complement Law:** $A\cdot A' = 0$, $A+A'=1$
- **Distributive Law:** $A(B+C)=AB+AC$
- **De Morgan’s Theorems:** $(A⋅B)'=A'+B'$, $(A+B)'=A'\cdot B'$

For example:
Consider the given boolean function:
$$F_2=x'y'z + x'yz + xy'$$
This is how this looks:
![[Pasted image 20250902025237.png]]

Upon simplifying this, we get:
$$F_2=x'y'z+x'yz+xy' = x'z(y'+y)+xy' = x'z + xy'$$
And this is the final design:
![[Pasted image 20250902025414.png]]

## Summing it up:
1. **Boolean Expression and Terms:**    
    - A Boolean function can be expressed as an algebraic sum of products (ANDs and ORs) and complemented variables.
    - A **literal** is a variable or its complement within a term.
    - Example: The term $xyz$ has three literals: $x$,$y$,$z$.
    - The number of literals and terms directly relates to the complexity of the circuit.
    
2. **Why Simplify?**
    - Simplifying reduces the number of logic gates needed.
    - Fewer gates mean fewer components, less wiring, lower power consumption, and often faster operation.
    
3. **Methods of Simplification:**
    - Using Boolean algebra laws (commutative, associative, distributive, identity, null, complement laws).
    - Applying theorems such as De Morgan’s theorems.
    - Reduction involves factoring, combining terms, and eliminating redundancies.

# Solving a truth table and finding its boolean function
Before we see the trick, lets talk about something important:
## Min and Max terms
Minterms and Maxterms are fundamental concepts in Boolean algebra and digital logic design that help systematically express and analyze Boolean functions.

### Minterms:
- A **minterm** is a product (AND) term involving all variables of a Boolean function, *where each variable appears exactly once* either in its true form (uncomplemented) or complemented form.
    
- For example, if you have variables A, B, and C, the minterms are all the possible products of these variables and their complements such as:
    $$ABC,\; AB'C,\; A'BC',\; ....$$
- There are $2^n$ minterms for $n$ variables because each variable can be either complemented or uncomplemented, and these $2^n$ minterms correspond exactly to all possible input combinations (rows) of the truth table.
- Each minterms gives high value only for one valuation, i..e, one row in the truth table, and gives false for all other valuations in the truth table.

### Maxterms:
- A **maxterm** is a sum (OR) term involving all variables, where each variable appears exactly once either in its true (uncomplemented) or complemented form, but complemented oppositely to in minterms.

- For the same variables A,B,C, example maxterms include:
    $A+B+C$,$A+B+C'$,$A+B'+C$,$A'+B+C$,…
	- There are also $2^n$ maxterms for $n$ variables.

- Each maxterm corresponds exactly to one row in the truth table where the function evaluates to 0.

## Why do they exist?
1. **To Represent Each Input Combination Uniquely:**
    - For $n$ variables, there are $2^n$ possible input combinations.
    - Minterms and maxterms provide a **one-to-one correspondence** to each input row in the truth table.
    - Each minterm or maxterm corresponds exactly to one unique input combination of variable values—no more, no less.
2. **To Enable Complete and Canonical Representation of Boolean Functions:**
	- Any Boolean function maps inputs (variable values) to outputs (0 or 1).
	- To represent the function algebraically with **absolute clarity and no ambiguity**, minterms and maxterms are used.
	- They form **canonical forms**: Sum of Minterms (SOP) and Product of Maxterms (POS).

So now if you think about it, we are mapping all the conditions when a particular truth table gives a high (in case of minterms) or a low(in case of maxterms).

If we take the sum of all minterms, we form an OR condition. This ensures that whenever the truth table outputs a high (1), the expression evaluates to 1. Similarly, if we take the product of all maxterms, we form an AND condition, which evaluates to low (0) whenever the truth table specifies a low output.

**Hence their combination represent the boolean function itself:**
## Sum of Minterms (SOP):
- The Boolean function outputs 1 for some input combinations.
- For each such combination, there is a corresponding minterm that is 1 only at that combination.
- The **sum (logical OR) of all such minterms** covering all rows where the function is 1 produces a Boolean expression that equals the function.
- Intuitively, the function is "true" if **any** of those minterms are true (since each minterm matches exactly one input combination).

## Product of Maxterms (POS):
- Similarly, for input combinations where the function outputs 0, there are maxterms that are 0 only on those rows.
- The function can be represented as the **product (logical AND) of all maxterms** corresponding to 0 output rows.
- Here, the function is "true" only if **all** maxterms (conditions ruling out false outputs) are true, which matches the function's behavior.

## K-Maps / Karnaugh Map
**Gate-level minimization** is the process of finding the most optimal implementation of Boolean functions at the gate level. The goal is to design digital hardware circuits with the least complexity, fewer logic gates, and fewer inputs to each gate. 
This is crucial for reducing cost, power consumption, and improving speed.
- Although modern computer-based tools do this efficiently for large inputs, understanding the mathematical and manual methods is essential for foundational knowledge.
- The minimization task aims to simplify Boolean expressions that describe a digital circuit while preserving logical equivalence.
- Complex expressions can vary greatly even if they represent the same logical function. Minimizing them structurally optimizes the hardware.

Although the truth table representation of a function is unique, when it is expressed algebraically it can appear in many different, but equivalent, forms.
The method that we saw earlier is the basic logic behind finding the boolean function but that method does not have a specific rules to how to simplify the boolean expressions.
Hence we use another method, a pictorial representation of truth table called K-Maps / Karnaugh Maps.

Try to keep this line in mind when learning about how functions are derived from k-maps and things should automatically make sense:
<blockquote>They work on the same logic as that in min-max terms, but here, we also check which variable's change in value is/is not affecting the change in output.</blockquote>

## Karnaugh Map
**A Karnaugh map** is *a two-dimensional grid representing all possible values of a Boolean function’s variables*. Each cell in the grid corresponds to a unique combination of input variables, **arranged so that adjacent cells differ by only one variable value (Gray code ordering)**. 
The cells contain the output values (1s or 0s) for these input combinations.

## Constructing a Karnaugh Map:
### Variables and Number of Cells
- The number of cells in a K-map equals $2^n$, where $n$ is the number of variables in the Boolean function.
- For example, 2 variables require 4 cells, 3 variables require 8 cells, and 4 variables require 16 cells.

### Grid Layout and Gray Code Ordering
- The grid is typically arranged in rows and columns based on the number of variables.
- Variables are split evenly between rows and columns (for example, for 4 variables, 2 variables for rows and 2 for columns).
- Important: The order of the cells follows **Gray code**, which means only one variable changes between adjacent cells. This property ensures that adjacent cells differ by only one bit, helping to group terms easily for simplification.

### Filling the Karnaugh Map
- Once the grid is formed, the output values (0 or 1) from the function’s truth table or Boolean expression are filled into the corresponding cells based on the input variable values.
- Each cell represents a minterm (or maxterm) of the function depending on the method used. 

## Deriving a boolean function out of K-Map
Constructing a Boolean function from a Karnaugh Map (K-map) involves identifying groups of 1s (for Sum of Products form) or 0s (for Product of Sums form) in the map and then translating these groups back into simplified Boolean expressions. 
First we would look at **Sum Of Products**, Here's a step-by-step explanation of the process:

### Step 1: Identify Groups of Adjacent 1s
- Scan the K-map for adjacent cells containing 1s. Adjacent cells must be physically next to each other either horizontally or vertically, and the number of cells grouped must be in powers of two (1, 2, 4, 8, etc.).
- Groups can wrap around the edges of the map because of the Gray code arrangement.
- Larger groups mean greater simplification.

### Step 2: Form Groups to Simplify and Write their Boolean Expression
- We need to form as many groups needed to cover all the minterms in the given K-map.
- Each group corresponds to a product term (AND of variables) in the simplified Boolean expression.
- Within a group, a variable that changes its value is eliminated. (As the change in it is actually not contributing to the output)
    - For example, if a group includes cells where variable A is 0 in some cells and 1 in others, A is eliminated in that group’s expression.
- Variables that stay constant within that group are included in the product term.
    - If the variable stays 0, it appears as the complemented variable (e.g., A').
    - If the variable stays 1, it appears as the variable itself (e.g., A).
- Convert each group into its simplified product term using the rules above.

**Rules for Grouping are:**
1. Groups must contain $2^n$ cells:
	- Groups can have sizes that are powers of two: 1,2,4,8,16,…
	- For example, valid groups are 1 cell, 2 adjacent cells, 4 cells forming a rectangle or square, 8 cells, etc.
    
2. Cells in a group must be adjacent:
	- Adjacent cells share a common edge (top, left, right, or bottom).
	- Diagonal adjacency (sharing only a corner) does **not** count.
	- Cells can wrap around the edges of the K-map (top adjacent to bottom, left adjacent to right).
    
3. Groups must be rectangular or square:
	- Groups must be contiguous blocks forming a rectangle or square shape.
	- Irregular shapes such as “L”-shapes, “T”-shapes, or other patterns are invalid.
	
4. Groups should be as large as possible:
	- Always form the largest possible group to maximize simplification.
	- Larger groups eliminate more variables because fewer variables change across the group.
	
5. Groups can overlap and cells can be reused:
	- It is allowed and often necessary for groups to overlap.
	- A single cell can belong to multiple groups if it helps in simplifying the expression further.
	
6. Every cell containing a 1 must be covered:
	- Ensure all cells with 1 (for SOP) are included in at least one group.
	- For POS, cover all 0 cells similarly.
	
7. Wrap-around adjacency:
	- The map is treated as if edges are connected (torus topology).
	- For example, leftmost and rightmost columns are adjacent.
	- Similarly, top and bottom rows are adjacent.

### Step 3: Sum (OR) All Product Terms
- Combine all the product terms from each group by an OR operation.
- This creates the final simplified Sum of Products (SOP) Boolean expression.

## K-maps for POS
**K-maps also simplify to Product-of-Sums form, useful in some designs.**
## Process:
- Instead of grouping 1s, group 0s (places where function output is 0).
- Each group of 0s corresponds to a sum term.
- Combine these sum terms with AND to get the POS form.

## Example Process:
- Identify all 0 cells.
- Group adjacent zeros in powers of two.
- Write sum term for each group:
    - Variable is complemented if constant 1 in the group.
    - Variable is non-complemented if constant 0 in the group.
    
- Multiply sum terms for the minimized POS expression.

## Prime implicants
<blockquote><q>A prime implicant is a product term obtained by combining the maximum possible number of adjacent squares in the map</q></blockquote>
### Short Summary
- Groups that cannot be combined into any larger group.
- Essential prime implicants cover minterms uniquely (no other group covers that minterm).

### In Detail:
A **prime implicant** is a group of one or more adjacent 1s in a Karnaugh Map that satisfies the following conditions:
1. **Maximal grouping**: The group is as large as possible, meaning it cannot be combined with any other adjacent group of 1s to form a larger group.
2. **Power of two size**: The group size must be 1,2,4,8,… cells.
3. **Rectangular or square shape**: The cells in the group form a rectangular or square block.
4. **Represents a simplified term**: Each prime implicant corresponds to a product term (in SOP form) with variables that are constant throughout the group.

**Significance**: Prime implicants represent the most simplified product terms that still cover parts of the function’s output.

## Essential Prime Implicants
An **essential prime implicant** is a special kind of prime implicant with this unique property:
- It contains at least one **minterm (cell with 1)** that **no other prime implicant covers**.

Because it covers a unique portion of the function, an essential prime implicant **must be included** in the final minimized Boolean expression; otherwise, some minterms would remain uncovered.

Hence, now when you solve questions, you will find that there are multiple boolean functions that we can use for a given truth table.


For Example:
(Include a few examples written on notebook)



## Don't Care Condition
Let's break down _don't care conditions_, which are essential for simplifying digital logic functions, especially when designing circuits using **Karnaugh maps (K-Maps)** and Boolean algebra.

### What Are Don't Care Conditions?

In digital design, some input combinations may **never occur** or their output doesn't matter. These are called **don't care conditions** and are usually represented by an 'X' in a truth table or K-Map. The formal term is **incompletely specified functions**.

- _Example:_ If a system only uses decimal digits (0 to 9), but the circuit has 4 inputs (allowing 16 combinations, 0 to 15), the states from 10 to 15 are never used. What happens for those states? Designers "don't care" – the output can be 0 or 1, whichever makes the circuit easier to implement. 

### Why Do We Use Don't Cares?

The main purpose of using don't cares is to **simplify Boolean expressions** and **reduce hardware cost**. By treating don't-care entries as either 0 or 1 in a K-Map, you can make larger groups, which leads to simpler logic equations (fewer gates, cheaper circuits).

### Key Rules for Using Don't Care Conditions
1. **Placement**: In your truth table or K-Map, mark don't care conditions as 'X'.
2. **Group Formation**: When grouping 1s (for SOP) or 0s (for POS), you can include an 'X' if it lets you make a bigger group. You get to _decide_ whether to treat an 'X' as 1 or 0 – but only if this choice helps make the grouping larger or the equation simpler.
3. **Ignore if Not Helpful**: If including the 'X' doesn't help in simplification, you simply leave it out.

### Example: K-Map Simplification with Don't Cares

Suppose you have a function $F(A,B,C,D)$ that is $1$ for minterms $1$, $3$, $7$, $11$, $15$ and has don't cares at minterms $0$, $2$, $5$. Place '1' for $F$ minterms, 'X' for the don't cares, '0' for the rest. When grouping in the K-Map, include X cells if they allow forming larger groups (e.g., to combine adjacent ones for a simpler expression).

# NAND And NOR Implementation
So bascially usage of NAND($\uparrow$) and NOR($\downarrow$) gates in electrical circuits is more common than that of AND and OR gates.
This is due to ease of production and implementation benifits(which will be come obvious when we make adders, subtracters, etc,.) it offers.

## NAND Implementation
The NAND gate is said to be the *universal gate* as any logic gate can be constructed using this, hence any digital circuite can be designed using the NAND only.

To prove that anything can be construced using NAND, we ought to show that it can construct AND, OR and NOT gate:

- The complement operation is obtained from a one-input NAND gate that behaves exactly like an inverter.
![[Pasted image 20250909224047.png]]

- AND Gate:
![[Pasted image 20250909224121.png]]

- OR Gate:
![[Pasted image 20250909224157.png]]

A convenient way to implement a Boolean function with NAND $(\oplus)$ gates is to obtain the simplified Boolean function in terms of Boolean operators and then convert the function to NAND logic.

NAND Boolean Function would be:
$$F = x' + y'$$
OR
$$F=x'y+y'x$$

![[Pasted image 20250909225723.png]]

![[Pasted image 20250909230014.png]]

# Combinational Circuits

## What Are Combinational Circuits?
A **Combinational Circuit** is a type of digital circuit whose output is determined solely by the present combination of its inputs. There is **no memory** or feedback element involved, which means the output depends only on the current inputs, not on any past inputs or previous states.

This is different from sequential circuits, which have memory elements and whose output depends on both current inputs and past inputs (history).

---
## Key Characteristics of Combinational Circuits
- **No memory elements:** Output is based on the current input values only.
- **Logic gates:** Built using basic logic gates like AND, OR, NOT, NAND, NOR, XOR, etc.
- **Deterministic output:** For a given set of inputs, the output is fixed.
- **Function realization:** Implements a Boolean function or logical operation.

---
## Basic Building Blocks of Combinational Circuits
Some commonly used combinational circuit components are:
- **Adders:** Perform addition of binary numbers (half adder, full adder).
- **Subtractors:** Perform subtraction.
- **Comparators:** Compare two binary numbers and output the comparison result.
- **Decoders:** Convert coded inputs to a set of outputs (e.g., 2-to-4 decoder).
- **Encoders:** The reverse of decoders, convert multiple inputs into coded outputs.
- **Multiplexers:** Select one input from many inputs based on select lines.
- **Demultiplexers:** Distribute one input to one of many outputs.

---
## How to Design a Combinational Circuit?
Designing combinational circuits follows a systematic procedure:

## Step 1: Define Inputs and Outputs
- Determine how many inputs and outputs the circuit needs.
- Assign symbols to each input (e.g., A, B, C, D) and output (e.g., F, W, X, Y, Z).

## Step 2: Construct the Truth Table
- List all possible combinations of inputs.
- Define the corresponding outputs for each input combination according to the desired function.

## Step 3: Obtain Boolean Expressions
- Use algebraic expressions or Karnaugh maps (K-maps) to simplify the output functions from the truth table.
- Simplification reduces logic gate count and overall circuit complexity.

## Step 4: Draw the Logic Circuit
- Implement the simplified Boolean functions with logic gates.
- Draw the corresponding logic diagram showing inputs, gates, and outputs.

## Step 5: Verify the Design
- Check manually or by simulation that the constructed circuit behaves as intended.

---

## Example: Designing a BCD to Excess-3 Code Converter
- **Inputs:** 4 bits representing a Binary Coded Decimal (BCD)
- **Outputs:** 4 bits representing the Excess-3 code (BCD + 3)
- Follow the design steps: define truth table showing BCD inputs and Excess-3 outputs; derive simplified Boolean expressions; implement using logic gates.

(Give an example)

# Adders
There are two types of adders:
- Half-Adders
- Full-Adders

## Full Adders: Deep Dive and Step-by-Step Design
**Main Takeaway**  
A **full adder** computes the sum of three one-bit binary inputs—two operand bits and an incoming carry—producing a one-bit sum and an outgoing carry. Its core logic uses _exclusive-OR_ for the sum and _majority_ logic for the carry, enabling creation of multi-bit adders by cascading full adders.

---

## 1. From Half Adder to Full Adder

## 1.1 Half Adder Recap

- **Inputs**: X, Y
    
- **Outputs**:
    
    - Sum S=X⊕Y=X Y‾+X‾ YS = X \oplus Y = X\,\overline Y + \overline X\,YS=X⊕Y=XY+XY
        
    - Carry C=X YC = X\,YC=XY
        
- **Logic Diagram**: XOR gate for sum; AND gate for carry.
    

## 1.2 Need for a Third Input

When adding multi-bit numbers, each bit position receives an incoming carry $C_{in}$. A half adder cannot incorporate $C_{in}$. A **full adder** extends the half adder by taking three inputs:
- X, Y: the two operand bits
- Z (or $C_{in}$): the carry from the previous less-significant bit    

---
## 2. Full Adder Truth Table

| X   | Y   | Z ($C_{in}$) | Sum (S) | $C_{out}$ |
| --- | --- | ------------ | ------- | --------- |
| 0   | 0   | 0            | 0       | 0         |
| 0   | 0   | 1            | 1       | 0         |
| 0   | 1   | 0            | 1       | 0         |
| 0   | 1   | 1            | 0       | 1         |
| 1   | 0   | 0            | 1       | 0         |
| 1   | 0   | 1            | 0       | 1         |
| 1   | 1   | 0            | 0       | 1         |
| 1   | 1   | 1            | 1       | 1         |

- **Sum** is 1 when an odd number of inputs are 1.    
- **Carry out** is 1 whenever at least two inputs are 1.

---
## 3. Deriving the Boolean Expressions
### 3.1 Sum Expression
Observing that $S=1$ for minterms $1, 2, 4, 7$ (in decimal):
$$S = \Sigma m(1,2,4,7)$$

Group those in K-map or note parity property:

S=X⊕Y⊕Z=Z (X⊕Y)′+Z′ (X⊕Y)=Z (X‾ Y‾+X Y)+Z‾ (X Y‾+X‾ Y).S = X \oplus Y \oplus Z = Z\,(X \oplus Y)' + Z'\,(X \oplus Y) = Z\,(\overline X\,\overline Y + X\,Y) + \overline Z\,(X\,\overline Y + \overline X\,Y).S=X⊕Y⊕Z=Z(X⊕Y)′+Z′(X⊕Y)=Z(XY+XY)+Z(XY+XY).

A more compact form:

S=Z⊕(X⊕Y).S = Z \oplus (X \oplus Y).S=Z⊕(X⊕Y).
![[Pasted image 20250910003005.png]]
![[Pasted image 20250910002953.png]]
## 3.2 Carry-Out Expression

Carry is 1 for minterms 3, 5, 6, 7:

Cout=Σm(3,5,6,7).C_{out} = \Sigma m(3,5,6,7).Cout=Σm(3,5,6,7).

Grouping yields the majority-function form:

Cout=X Y+Y Z+X Z.C_{out} = X\,Y + Y\,Z + X\,Z.Cout=XY+YZ+XZ.
![[Pasted image 20250910003023.png]]

---

## 4. Logic Diagram Construction

1. **Compute P=X⊕YP = X \oplus YP=X⊕Y** with an XOR gate.
    
2. **Compute Sum**: feed PPP and ZZZ into a second XOR to get S=P⊕ZS = P \oplus ZS=P⊕Z.
    
3. **Compute Partial Carries**:
    
    - G1=X YG_1 = X\,YG1=XY (AND gate)
        
    - G2=P ZG_2 = P\,ZG2=PZ (AND gate with PPP and ZZZ)
        
4. **Compute Carry-Out**: OR the partial carries:
    
    Cout=G1+G2.C_{out} = G_1 + G_2.Cout=G1+G2.

![[Pasted image 20250910003038.png]]
![[Pasted image 20250910003231.png]]

---

## 5. Cascading Full Adders for Multi-Bit Addition
- Chain several full adder blocks.
- The $C_{out}$ of bit $i$ becomes $C_{in}$ of bit $i+1$.
- For an n-bit adder, connect n full adders in series.

![[Pasted image 20250910003726.png]]

**Propagation Delay Considerations**:
- Each full adder has XOR→AND→OR depths; carry must ripple through, giving worst-case delay nnn × tFAt_{FA}tFA.NOR
- For high-speed designs, consider lookahead or prefix adders.

---
## 6. Common Questions and Clarifications
- _Why two XOR gates?_  
    The first XOR computes $X\oplus Y$. The second XOR adds the incoming carry, implementing the three-input parity.
    
- _Is $C_{out} = X\,Y + Z\,(X \oplus Y)$ equivalent?_  
    Yes. Since $P = X\oplus Y$, $G_2 = P\,Z$. Thus  
      $\;C_{out}=X\,Y + Z\,(X\oplus Y).$
    
- _Can we implement sum and carry with only NAND gates?_  
    Absolutely. By applying De Morgan’s law, all gates can be converted to NAND form for CMOS implementation.

---

## Summary of Key Formulas
$$\boxed{ \begin{aligned} S &= X \oplus Y \oplus Z,\\ C_{out} &= X\,Y + Y\,Z + X\,Z. \end{aligned} }$$

# Multiplexers
A **multiplexer** (often called MUX) is a digital circuit that selects one of several input signals and forwards it to a single output line. Think of it as a "data selector"—it lets you choose which input gets sent to the output, based on the values of special control signals called **selection lines**.

- **Inputs:** There are usually $2^n$ input lines, where $n$ is the number of selection lines.
- **Selection lines:** These control which input is chosen.
- **Output:** Only one input is sent to the output at a time.

For example, a **4-to-1 multiplexer** has 4 inputs, 2 selection lines, and 1 output. The selection lines decide which input ($I_0$, $I_1$, $I_2$, or $I_3$) appears at the output.

Multiplexers are used in:
- Data routing in computers
- Communication systems
- Logic circuit design

## 2:1 MUX
- Inputs: Two data inputs $I_0$,$I_1$
- Select input: $S_0$
- Output: $Y$

**Function:** Output $Y$ equals one of the inputs depending on $S_0$:
$$Y = \left((S_0)^{'}I_0+(S_0)I_1\right)$$
- If $S_0=0$, output $Y=I_0$
- If $S_0=1$, output $Y=I_1$

## 2:1 MUX with Enabler
- Similar to the above but includes an additional **enable input EE**.
- When $E=0$, MUX is active and selects input as per $S_0$.    
- When $E=1$, output is disabled (usually 0 or high impedance).

Boolean expression with enable:
$$Y = \bar{E}\left((S_0)^{'}I_0+(S_0)I_1\right)$$
In digital circuits, **high impedance (Hi-Z)** refers to a state where the output behaves almost like an open circuit, meaning it effectively **disconnects from the circuit**. This means:
- The output neither drives a high (logic 1) nor a low (logic 0) voltage level.
- The output presents a very **high resistance (impedance)**, so it doesn't affect or load the rest of the circuit connected to that line.
- Other devices connected to the same line can drive it without interference.

**Why is High Impedance Useful?**
- It allows **multiple outputs** to share a single physical line or bus without conflicting signals. Only one device actively drives the line while others stay in Hi-Z state.
- It prevents **signal contention**, avoiding damage or incorrect logic levels from conflicting drivers.

## 4:1 MUX
- Inputs: Four data inputs $I_0,I_1,I_2,I_3$
- Select inputs: Two select lines $S_1,S_0$ (because 2 bits can select among 4 options)
- Output: Y

**Function:** Output equals the selected input based on the 2-bit select input:

| $S_1$ | $S_0$ | Output Y |
| ----- | ----- | -------- |
| 0     | 0     | $I_0$    |
| 0     | 1     | $I_1$    |
| 1     | 0     | $I_2$    |
| 1     | 1     | $I_3$    |

Boolean expression:
$$Y={S_1}'{S_0}'I_0+{S_1}'S_0I_1+S_1{S_0}'I_2+S_1S_0I_3$$

This logic combines AND gates for each select combination with the corresponding input, and ORs the results to produce Y.

# Decoders
A **decoder** is a combinational logic circuit that converts binary encoded inputs into a unique set of output signals. 
It takes an n-bit binary input and activates exactly one of its $2^n$ output lines corresponding to the input code, while all other outputs remain inactive. This means for every combination of input bits, only one output line is "high" or active.

## Key points about decoders:
- They perform the reverse function of encoders.
- A common example is a 2-to-4 decoder: it takes 2 input bits and produces 4 output lines, with only one output active at any time.
- Decoders are used in several digital applications like memory address decoding, driving 7-segment displays, data routing, and selecting devices on a bus.
- They often have an enable input; when disabled, all outputs are inactive.
- The output lines of a decoder are usually mutually exclusive.

## Example of operation:
For a 2-to-4 decoder:
- Input 00 activates output line 0
- Input 01 activates output line 1
- Input 10 activates output line 2    
- Input 11 activates output line 3

## Applications of Decoders
- **Memory Address Decoding**: Selecting specific memory locations in RAM or ROM
- **Data Routing**: Directing data to appropriate destinations
- **Display Systems**: Driving seven-segment displays
- **Instruction Decoding**: In microprocessors for executing specific operations

## Truth Table Example: 2-to-4 Decoder

| Input $A_1$ | Input $A_0$ | Output $D_3$ | Output $D_2$ | Output $D_1$ | Output $D_0$ |
| ----------- | ----------- | ------------ | ------------ | ------------ | ------------ |
| 0           | 0           | 0            | 0            | 0            | 1            |
| 0           | 1           | 0            | 0            | 1            | 0            |
| 1           | 0           | 0            | 1            | 0            | 0            |
| 1           | 1           | 1            | 0            | 0            | 0            |

## 3-to-8 Line Decoder: Detailed Analysis
Let's examine the **3-to-8 decoder** from your slides:

**Circuit Specifications**:
- **Inputs**: 3 lines $(X, Y, Z)$
- **Outputs**: 8 lines ($D₀$ to $D₇$)
- **Function**: For each 3-bit input combination, exactly one output becomes HIGH

**Truth Table Analysis**:

| X   | Y   | Z   | Active Output | Binary Value | Decimal Equivalent |
| --- | --- | --- | ------------- | ------------ | ------------------ |
| 0   | 0   | 0   | D₀ = 1        | 000          | 0                  |
| 0   | 0   | 1   | D₁ = 1        | 001          | 1                  |
| 0   | 1   | 0   | D₂ = 1        | 010          | 2                  |
| 0   | 1   | 1   | D₃ = 1        | 011          | 3                  |
| 1   | 0   | 0   | D₄ = 1        | 100          | 4                  |
| 1   | 0   | 1   | D₅ = 1        | 101          | 5                  |
| 1   | 1   | 0   | D₆ = 1        | 110          | 6                  |
| 1   | 1   | 1   | D₇ = 1        | 111          | 7                  |

# Encoders
An **encoder** is a digital combinational circuit that performs the reverse operation of a decoder. It takes multiple input lines—usually $2^n$ inputs—and converts them into a smaller number of output lines $n$, producing a binary code that represents the active input line.

## Key points about encoders:
- Encoders have up to $2^n$ input lines and $n$ output lines.
- Only one input is assumed to be active at a time, and the encoder outputs the binary code corresponding to this active input.
- For example, a 4-to-2 encoder has 4 input lines and 2 output lines. If the third input line is active, the encoder outputs the binary code "10" (which is 2 in decimal).
- Encoders convert parallel inputs into a binary-encoded output, saving on the number of required wires or lines.
- Types of encoders include priority encoders (which assign priority when multiple inputs are active) and binary-weighted encoders.
- Encoders are widely used in digital systems for data compression, signal encoding, and simplifying connections like keyboards or multiplexers.

## Example operation:
- Input with only the first line active: output is 000 (binary code for 0).
- Input with only the fifth line active: output is 100 (binary code for 4).

## Real-World Analogy: Elevator Control System
Your lecture provides another excellent practical example: a 4-floor building elevator system where:
- **4 input lines**: Floor call buttons (one per floor)
- **2 output lines**: Binary representation of requested floor
- **Efficiency**: Instead of 4 separate wires, only 2 wires needed to transmit floor information

**Basic Encoder Limitations**:
- Only one input can be active at a time
- If multiple inputs are active simultaneously, output becomes unpredictable

## Priority Encoder Solution
In an encoder, we have a problem, what if more than one values are high together. A **priority encoder** solves the multiple input problem by assigning **priorities** to inputs. When multiple inputs are active, the **highest priority input determines the output**.

- Handles multiple simultaneous inputs by assigning **priorities**
- Higher priority input takes precedence when multiple inputs are active
- Includes a **Valid (V)** output to indicate if any input is active    

## Priority Encoder Truth Table (4-to-2)

| $D_3$ | $D_2$ | $D_1$ | $D_0$ | $Y_1$ | $Y_0$ | V   | Notes                      |
| ----- | ----- | ----- | ----- | ----- | ----- | --- | -------------------------- |
| 0     | 0     | 0     | 0     | x     | x     | 0   | No valid input             |
| 0     | 0     | 0     | 1     | 0     | 0     | 1   | $D_0$ has priority         |
| 0     | 0     | 1     | x     | 0     | 1     | 1   | $D_1$ has priority         |
| 0     | 1     | x     | x     | 1     | 0     | 1   | $D_2$ has priority         |
| 1     | x     | x     | x     | 1     | 1     | 1   | $D_3$ has highest priority |

*(Note: 'x' represents don't care conditions)*
![[Pasted image 20250917041504.png]]

**Key Features**:
1. **Priority Assignment**: D₃ (highest) → D₂ → D₁ → D₀ (lowest)
2. **Valid Indicator (V)**: Shows when at least one input is active
3. **Don't Care Handling**: 'X' in truth table represents optimization opportunities

**Boolean Expression Derivation Using K-Maps**:
From our analysis above, the **K-map simplification** yields:
- **x = D₂ + D₃** (x=1 whenever D₂ OR D₃ is active)
- **y = D₃ + D₁D₂'** (y=1 when D₃=1 OR when D₁=1 AND D₂=0)
- **V = D₀ + D₁ + D₂ + D₃** (V=1 when any input is active)

**Working Principle Examples**:
- **Input 0001**: Only $D₀$ active → Output: $x=0, y=0, V=1$ (binary 00)
- **Input 0011**: $D₁$ and $D₀$ active → $D₁$ wins → Output: $x=0, y=1, V=1$ (binary 01)
- **Input 1010**: $D₃$ and $D₁$ active → $D₃$ wins → Output: $x=1, y=1, V=1$ (binary 11)

## **Priority Encoder Applications**
**Interrupt Controllers**:
- **Multiple interrupt sources**: Different devices can request CPU attention
- **Priority handling**: Critical interrupts (like hardware failures) override less important ones
- **Efficient processing**: CPU handles highest priority interrupt first

**Data Acquisition Systems**:
- **Multiple sensor inputs**: Various sensors may activate simultaneously
- **Priority-based sampling**: Most critical sensor data processed first
- **Real-time response**: System responds to highest priority events immediately

# BCD Adder
![[#BCD Addition]]

A **BCD adder** adds two 4-bit BCD numbers and produces a valid BCD sum. Since BCD represents decimal digits 0-9 using 4 bits, any sum greater than 9 must be corrected.

## Design Steps
**Step 1:** Use a 4-bit binary adder to add two BCD digits plus carry-in.
**Step 2:** Check if correction is needed:
- Binary sum > 9 (greater than 1001₂), OR
- Carry-out from the adder is 1
**Step 3:** If correction needed, add 6 (0110₂) to the binary sum using another 4-bit adder.
**Step 4:** The corrected result is the final BCD output with proper carry propagation.

So, we define the binary sum of a 4-bit adder as $K$, $Z_8$, $Z_4$, $Z_2$, $Z_1$, and we define the BCD sum as $K$, $S_8$, $S_4$, $S_2$, $S_1$

![[Pasted image 20250917125038.png]]

Now, let define the check that tells us if the number is greater than 9 as Correction Logic.
So, by seeing the truth table, we can tell that the correction logic should apply when:
- K is high
- $Z_8 \cdot Z_4$ is high
- $Z_8 \cdot Z_2$ is high
Hence, the Correction Logic becomes:
$$C = K + Z_8 \cdot Z_4 + Z_8 \cdot Z_2$$
So, if the $C$ is high, then we add 6($0110$) to the number and generate the result:
![[Pasted image 20250917125715.png]]

# Magnitude Comparator
A magnitude comparator is a combinational circuit that compares two numbers A and B and determines their relative magnitudes.
The outcome of the comparison is specified by three binary variables that indicate whether $A \lt B$, $A = B$, or $A \gt B$. Consider two numbers, A and B, with four digits each. Write the coefficients of the numbers in descending order of significance:
$$A = A_3A_2A_1A_0$$
$$B = B_3B_2B_1B_0$$

The **two numbers are equal if** all pairs of significant digits are equal: $A_3 = B_3$, $A_2 = B_2$, $A_1 = B_1$ and $A_0 = B_0$. 
When the numbers are binary, the digits are either 1 or 0, and the equality of each pair of bits can be expressed logically with an exclusive-NOR function as:
$$x_i = A_iB_i+A_i'B_i'$$
Hence for equality to exist:
$$(A=B)=x_3x_2x_1x_0$$

Now, then it comes to a number being greater than other, we have:
$$(A\gt B) = A_3B_3' + x_3A_2B_2' + x_3x_2A_1B_1' + x_3x_2x_1A_0B_0'$$
$$(A\lt B) = B_3A_3' + x_3B_2A_2' + x_3x_2B_1A_1' + x_3x_2x_1B_0A_0'$$
Here, the logic is if 4th term of A is not greater than that of B, then it must be equal and the next term might be, if not that as well, then that also must be same and so on, if A is actually greater than B. (Same for B greater than A).

![[Pasted image 20250923005151.png]]

# Binary Multiplier
Multiplication of binary numbers is performed in the same way as multiplication of decimal numbers.
So, How is it performed?
- The multiplicand is multiplied by each bit of the multiplier, starting from the least significant bit. 
- Each such multiplication forms a partial product. 
- Successive partial products are shifted one position to the left. 
- The final product is obtained from the sum of the partial products.
For example, for the multiplicand $B_1B_0$ and multiplier $A_1A_0$, we get the product $C_3C_2C_1C_0$:
![[Pasted image 20250923010155.png]]

Hence, for $J$ multiplier bits and $K$ multiplicand bits, we need $J \times K$ AND gates and $J - 1$ K-bit adders to produce a product of $(J + K)$ bits.

For example, multiplication of $B_3B_2B_1B_0 \times A_2A_1A_0$, we have J = 3, and K = 4:
![[Pasted image 20250923011006.png]]


# Sequential Circuits
Sequential circuits, however, act as storage elements and have memory. They can store, retain, and then retrieve information when needed at a later time.
This allows the circuit's output not only to depend on the current inputs but also on previous states or past inputs.
## What Are Sequential Circuits?
- Sequential circuits use stored information, called **state**, in addition to present inputs, to determine the output.
- Unlike **combinational circuits**, where outputs depend solely on input values at that moment, sequential circuits have memory components that keep track of past activity.
- This "memory" enables complex behaviors such as counting, timing, storing data, and implementing state machines.

## Basic Components of Sequential Circuits
1. **Combinational Logic:**
    - This part performs logical operations on current inputs and stored state variables.    
    - It decides the next state and output based on inputs and current stored information.
    
2. **Memory Elements:**
    - These store the current state information (history).
    - Commonly implemented using **flip-flops** or **latches**, these elements hold binary data and update at specific times (triggered by a clock signal in synchronous circuits).
    
3. **Inputs and Outputs:**
    - The circuit takes external inputs.
    - It produces outputs based on both inputs and stored state.

![[Pasted image 20250923031408.png]]

How does it work:
- The circuit receives input signals.
- The combinational logic processes inputs and the current state stored in the memory elements.
- It determines the **next state** and output.
- At a clock signal (in synchronous designs), flip-flops update their stored value to this next state.
- This cycle repeats, making the circuit's behavior depend on the sequence of inputs over time.

## What is a clock signal?
- **Clock:** A clock is a digital signal that oscillates between 0 (low) and 1 (high) at a steady frequency.
- **Purpose:**
    - It provides a timing reference to synchronize operations in a digital system.
    - Controls when data is stored, transferred, or acted upon, ensuring coordinated operation.
    
- **Clock Signal Features:**
    - **Frequency:** How often the clock cycles per second (measured in Hertz).
    - **Period:** Time for one full cycle (high+low duration).
    - **Duty Cycle:** Percentage of the time the clock is high during one period.
    
- **Edges:**
    - **Rising edge:** Transition from 0 to 1, often used for triggering events.
    - **Falling edge:** Transition from 1 to 0, sometimes used instead.
    
- **Analogy:** Like a conductor guiding musicians to play notes exactly on beat, the clock guides the circuit to update state at exact times.

## Types of Sequential Circuits
Sequential circuits are broadly classified into two main types:

### 1. Synchronous Sequential Circuits
- **Definition:** These circuits change their state only at specific times, synchronized by a clock signal.
- **Clock Signal:** A regular, repeating digital pulse (usually a square wave) that acts like a heartbeat for the circuit.
- **How It Works:**
    - The circuit waits for the clock’s rising edge (transition from 0 to 1) or falling edge (1 to 0) to update its state.
    - All memory elements (like flip-flops) inside the circuit change their output simultaneously at the clock edge.
    
- **Advantages:**
    - Predictable timing and orderly operation.
    - Easier to design and troubleshoot since all actions sync with the clock.
	
- **Examples:** Counters, registers, flip-flops, state machines in computers.

### 2. Asynchronous Sequential Circuits
- **Definition:** These circuits change state immediately in response to input changes, without waiting for a clock signal.
- **How It Works:**
    - The output can change as soon as input changes.
    - No central timing signal controls state updates.
    
- **Advantages:**
    - Typically faster because they do not wait for a clock pulse.
    - Useful when speed is critical.
    
- **Disadvantages:**
    - More complex to design.
    - Susceptible to glitches or unstable states because of timing uncertainties.
    
- **Examples:** Certain ripple counters, circuit parts needing immediate response.

# Synchronous Sequential Circuits
As Asynchronous circuits are quite complex to design and poses a lot of glitches, we would not focus on them, and stick with refining the basics and cover synchronous sequential circuits.

## Role of the Clock in Sequential Circuits
- Circuits that rely on a clock signal to update their states are called **synchronous sequential circuits**.
- They are "synchronous" because **all activities inside them happen in coordination with clock pulses**.
- For example, if you design a circuit to add two numbers and then store the result:    
    - The addition is computed by **combinational logic** (adders).
    - But the result is _only stored_ in flip-flops when the clock arrives.
    - Between clock pulses, outputs of flip-flops don’t change—even if combinational circuit outputs do. (Magik!)

## Flip-Flops (Memory Elements)
- A **flip-flop** is a 1-bit memory element. It holds its value (0 or 1) until a clock signal tells it to update.
- Many flip-flops are combined in a circuit to store multiple bits (registers, counters, etc.).
- A flip-flop:
    - _Input_: Next value (decided by combinational logic or feedback).
    - _Output_: Stored value, updated only on clock edge.

## Block Diagram Behavior
If you imagine the block diagram:
- Inputs and current flip-flop values feed into **combinational logic** (which computes next state and outputs).
- The next state output of the combinational logic is connected to flip-flop inputs.
- On the next clock pulse, flip-flops _captures_ this next state.
- Outputs may depend directly on the current flip-flop values, current inputs, or both depending upon the combinational logic used.

## Importance of Timing
(Move ahead, cover the flip-flop theory first, then check this out)
For an edge-trigger flip-flop:
- Since updates happen only on clock edges, **timing constraints** between pulses matter.
- The **combinational logic must settle to a stable value before the next pulse comes**.
- The minimum time between clock pulses (i.e., the maximum clock speed) depends on:
    - **Propagation delay** of the combinational logic (time for signal to stabilize).
	    - Delay time, or propagation delay, is the time interval between the triggering clock edge and the time when the flip-flop output (Q) settles to the new stable state. 
		- It represents how quickly the flip-flop responds internally to the clock transition and presents the new output data.
		
    - **Flip-flop setup time** (time before clock when data must be stable).
	    Setup time is the minimum time duration for which the data input (D) to the flip-flop must be held stable before the triggering clock edge arrives.
	    
	- **Flip-flop hold time** (time after clock when data must remain stable).
		- Flip-Flop hold time is the minimum time duration for which the data input (D) to the flip-flop must remain stable and unchanged after the triggering clock edge has occurred.
		- In other words, once the clock edge (e.g., a rising edge if it is a positive-edge triggered flip-flop) arrives to trigger the flip-flop, the input data must be held steady without change during this hold time period. 
		- This ensures that the flip-flop reliably latches the input data associated with that clock edge.
_(The Triggering clock edge can be either the positive edge $(0\rightarrow 1)$, or negative edge$(0\rightarrow 1)$ depending upon the design.)_

For a level-triggered flip-flop:

https://github.com/copilot/c/daf02aef-3ba8-4eac-a10e-1def08355f61

If the pulse comes too fast:
- The combinational logic doesn't finish before the next clock edge.
- Incorrect values are stored in flip-flops → **logic failure**.

<blockquote><q>All this defines the <b>maximum frequency</b> the circuit can run at safely.</q></blockquote>

## Breaking Feedback Loops
- Without a clock, circuits with feedback loops (like flip-flop outputs feeding back into their inputs) could oscillate or behave unpredictably. Hence a breaking feedback loop in the design is necessary.
- But with synchronous design:
    - Between clock pulses, flip-flop outputs stay fixed, "breaking" the feedback loop.
    - The only time the state can change is at the clock transition.
    - This prevents instability and makes analysis easier.

![[Pasted image 20250924005437.png]]

# Latches
Latches are basic digital circuits used for storing one bit of information, acting as simple memory elements. 
Unlike flip-flops that are edge-triggered, latches are **level-sensitive devices**—meaning their output changes instantly based on input signals as long as the enabling signal (or control input, a signal that is needed to tell if the latch is accepting the response or not) is active.

## What is a Latch?
- A latch stores a single bit of data (0 or 1) and holds that value until explicitly changed by new inputs.
- It is controlled by a signal level rather than a clock edge, so it is called **level-triggered**.
- When enabled, the latch's output changes to reflect its inputs. When disabled, it stores the last value.
- Latches are useful in asynchronous sequential circuits and temporary data storage.

## Types of Latches
1. **SR Latch (Set-Reset Latch):**
    - Has two inputs: Set (S) and Reset (R).
    - Setting S to 1 makes the output Q = 1, resetting R to 1 makes Q = 0.
    - Both S and R = 1 is an invalid or forbidden state.
    - Implemented using NOR or NAND gates cross-connected.
    
2. **Gated SR Latch:**
    - An SR latch with an additional Enable input.
    - Outputs respond to S and R only when Enable is active; otherwise, outputs hold their state.
    
3. **D Latch (Data or Transparent Latch):**
    - Has one data input (D) and an Enable (E) input.
    - When Enable is high, output Q follows input D (transparent).
    - When Enable is low, output Q holds the last value.
    - Useful for synchronizing data to control signals.
    
4. **Gated D Latch:**
    - Similar to the D latch with an enable control.
    - Holds data when enable is low, passes through when enable is high.

## Differences from Flip-Flops
- **Latches are level-sensitive** (output follows input as long as enable signal is active).
- **Flip-flops are edge-triggered**, updating output only on clock edge transitions.

# SR Latch
## What is an SR Latch?
- The SR latch is one of the simplest memory circuits in digital electronics.
- It has two inputs: S (Set) and R (Reset), and two outputs: Q and Q̅ (Q-bar; the inverse of Q).
- Its job: Store one bit (0 or 1) and keep it “latched” (remembered) until told to change.

---
## A) SR Latch Using NOR Gates
### Circuit Structure
- **Two NOR gates**: Output of each gate connects as one input to the other—this is called “cross-coupling.”
- Inputs: S (Set), R (Reset).
- Outputs: Q, Q̅.
![[Pasted image 20251010042235.png]]

### How It Works
- If S=1 and R=0: Output Q is set to 1.
- If S=0 and R=1: Output Q is reset to 0.
- If S=0 and R=0: Output Q remains the same (previous value held).
- If S=1 and R=1: Invalid—both outputs forced to 0, not allowed.

### Truth Table (NOR Gate Version)

|S|R|Q (Output)|Q̅ (Output)|Description|
|---|---|---|---|---|
|0|0|Previous Q|Previous Q̅|Memory|
|0|1|0|1|Reset|
|1|0|1|0|Set|
|1|1|0|0|Forbidden|

## B) SR Latch Using NAND Gates

### Circuit Structure
- **Two NAND gates**: Also cross-coupled.
- Inputs are still labeled S and R.
![[Pasted image 20251010042404.png]]

### Key Difference
- **Active LOW inputs**: S and R must be set to 0 (LOW) to trigger setting or resetting.
- Outputs (Q, Q̅) are inverted compared to NOR version.

### Truth Table (NAND Gate Version)

|S|R|Q (Output)|Q̅ (Output)|Description|
|---|---|---|---|---|
|1|1|Previous Q|Previous Q̅|Memory|
|1|0|1|0|Set|
|0|1|0|1|Reset|
|0|0|1|1|Forbidden|

## C) Gated SR Latch (With Enable Input)

### Structure
- Adds an **Enable (E)** input.
- S and R are combined with Enable signal (often using AND gates) before going to NOR or NAND gates.
- When Enable = 1: Latch behaves like basic SR.
- When Enable = 0: Outputs stay unchanged, regardless of S and R.

![[Pasted image 20251010042842.png]]

- An indeterminate condition occurs when all three inputs are equal to 1. 
- This condition places 0’s on both inputs of the basic SR latch, which puts it in the undefined state.
## Graphic Symbols for SR Latches
![[Pasted image 20251010045304.png]]

---
# D Latch
- Now, one of the problems in the SR Latch is the undetermined case, i..e, when both S and R are 1 (in a NOR gated SR Latch).
- One way to eliminate the undesirable condition of the indeterminate state in the SR latch is to ensure that inputs S and R are never equal to 1 at the same time. 
- This is done in the **D latch**.

## What is a D-Latch(Data Latch or Transparent Latch)?
A D Latch is a **level-triggered sequential logic circuit** that can store one bit of binary information (0 or 1). It's called "transparent" because when enabled, the output directly follows the input, making the circuit appear "transparent" - as if there's a direct path from input to output.

## Circuit Components:
1. **Basic SR Latch**: Two cross-coupled NAND gates forming the storage element
2. **Input Control Gates**: Two NAND gates that control when inputs can affect the latch
3. **Inverter**: Ensures S and R inputs are always complementary
4. **Two Inputs**: D (Data) and Enable (E or EN)
5. **Two Outputs**: Q and Q' (complement of Q)

![[Pasted image 20251010045359.png]]
## Transparent Mode (Enable = High):
- **Immediate Response**: Q output changes immediately when D input changes    
- **Continuous Tracking**: Q follows all changes in D during the entire high period of Enable
- **No Delay**: Changes propagate through the latch with only gate propagation delay

## Hold Mode (Enable = Low):
- **State Retention**: Q maintains its last value regardless of D changes
- **Memory Function**: Acts as a 1-bit memory element
- **Isolation**: Input changes are completely ignored

## Detailed Operation Analysis:
**Case 1: Enable = 0 (Disabled/Hold Mode)**
- Regardless of D input value, the latch maintains its current state
- Both input NAND gates output '1', keeping the SR latch in hold mode
- $Q$ retains previous value: $Q(t+1) = Q(t)$

**Case 2: Enable = 1, D = 0 (Reset Mode)**
- Upper NAND gate receives (0,1) → outputs 1
- Lower NAND gate receives (1,1) → outputs 0
- This creates S=1, R=0 condition for the SR latch
- Result: Q = 0, Q' = 1

**Case 3: Enable = 1, D = 1 (Set Mode)**
- Upper NAND gate receives (1,1) → outputs 0
- Lower NAND gate receives (0,1) → outputs 1
- This creates S=0, R=1 condition for the SR latch
- Result: Q = 1, Q' = 0

## Graphic Symbols for SR latch and D latch
![[Pasted image 20251010045737.png]]

# Flip-Flops
([Material to refer](Flip-Flops%20&%20Sequential%20Circuits.pptx.pdf))
- A flip-flop is a synchronous device that changes its output state only at specific times determined by a clock input signal.
- Unlike latches, flip-flops are edge-triggered, meaning they respond to transitions (edges) of the clock (either rising or falling edge). 
- This edge-triggering prevents changes in output except at clock edges, thus avoiding unintended changes from varying inputs.

## Why not Latches?
When latches are used for the storage elements, a serious difficulty arises. The state transitions of the latches start as soon as the clock pulse changes to the logic-1 level.
The new state of a latch appears at the output while the pulse is still active. This output is connected to the inputs of the latches through the combinational circuit.

This is because latches are "Responsive to positive level". The key to the proper operation of a flip-flop is to trigger it only during a signal transition.

![[Pasted image 20251010054514.png]]

## Basic flip-flop structure
- Flip Flops are made using latches placed in a *Master-Slave configuration*.
- This converts the state to change on only when the signal moves from low to high, as opposed to all the time when the enable signal is high
![[Pasted image 20251010052218.png]]

## Types of Flip-Flops
There are several types of flip-flops, differentiated by their inputs and behavior:
- **SR Flip-Flop (Set-Reset):** Has two inputs, S (set) and R (reset). Setting S=1 sets the output to 1, R=1 resets output to 0.
- **D Flip-Flop (Data or Delay):** Has a single data input D. The output Q takes on the value of D at the clock edge. It is commonly used for data storage.
- **JK Flip-Flop:** An improvement over SR flip-flop. It has inputs J and K, functioning as set and reset inputs but without invalid states. When J = K = 1, the output toggles.
- **T Flip-Flop (Toggle):** Has a single input T. When T=1 at the clock edge, the output toggles its state; when T=0, output remains the same.

## SR Flip-Flop
![[Pasted image 20251010052236.png]]
An **SR flip-flop** is a type of bistable multivibrator, meaning it has two stable states and can store one bit of information. The two inputs are:
- **S (Set):** Used to set the output to 1.
- **R (Reset):** Used to reset the output to 0.

The outputs are usually labeled **Q** (the main output) and **Q‾Q** (the complement of Q).

### Construction
SR flip-flops can be built using either **NOR** or **NAND** gates:
- **NOR-based SR Latch:** Inputs are normally at 0. A 1 on S sets Q to 1; a 1 on R resets Q to 0.
- **NAND-based SR Latch:** Inputs are normally at 1. A 0 on S sets Q to 1; a 0 on R resets Q to 0.

> The difference is in the logic level required to activate the set/reset, but the basic operation is the same.

### Truth Table (for NOR-based SR Latch)

| S   | R   | Q (next)  | Q' (next)  | Description           |
| --- | --- | --------- | ---------- | --------------------- |
| 0   | 0   | Q (holds) | Q' (holds) | No change (memory)    |
| 0   | 1   | 0         | 1          | Reset                 |
| 1   | 0   | 1         | 0          | Set                   |
| 1   | 1   | 0         | 0          | **Invalid/Forbidden** |
## D Flip-Flop
![[Pasted image 20251010055336.png]]

## JK Flip-Flop
![[Pasted image 20251010055700.png]]
## T Flip-Flop
![[Pasted image 20251010055746.png]]

# Edge transition Flip-Flops (The true flip-flops)
Now, all these Flip-Flops has one issue, they all are responsive to the positive level, which can give unpredictable behaviors as in the main circuit, we have a feedback loop, i..e, the output of the storage element is connected to the input, which we don't want.
Hence, it would be better to design a edge trigger responsive flip-flops.

One way to do that is to follow the master-slave configuration
![[Pasted image 20251010052218.png]]

Another way to do that is to produce a flip-flop that triggers only during a signal transition (from 0 to 1 or from 1 to 0) of the synchronizing signal (clock) and is disabled during the rest of the clock pulse.

## Master-Slave D Flip-Flop
 A master–slave D flip-flop ensures glitch-free, edge-triggered storage of a single bit by cascading two level-sensitive latches (master and slave). A **flip-flop** is edge-sensitive, updating its output only on a clock transition (edge) rather than continuously while the clock is active.

![[Pasted image 20251010061218.png]]

### Operation Steps
1. **CLK low → high:**
    - Master latch enabled: $Y←D$
    - Slave latch disabled: $Q$ unchanged
    
2. **During CLK = high:**
    - $D$ may change; $Y$ tracks, but $Q$ remains fixed
    
3. **CLK high → low:**
    - Master disables (holds last Y)
    - Slave enables: $Q←Y$
	
4. **CLK low:**
    - Master closed; slave closed; $Q$ retained until next rising edge

That was a **negative edge triggered D flip-flop.** 
If we were to have a positive edge triggered D flip-flop, then we would have to take the negation of clock input.
So, now the NOT Gate is connected to the master flip-flop's clock, and there is no NOT Gate in slave flip-flop's clock.

## D-type positive-edge-triggered flip-flop
![[Pasted image 20251010061701.png]]

This approach is less common than the master-slave D latch method, but it's a great way to see how edge-triggering can be achieved using only basic storage elements.
### Operation Sequence
- **Clock Low (0):**
    - The outputs of the first two latches are both high, so the output latch holds its state (no change).
    - The D input can change, but it doesn't affect the output yet.
    
- **Clock Rising Edge (0→1):**
    - Depending on the value of D at the moment of the rising edge, one of the first two latches will output a low pulse (either S or R for the output latch).
    - This pulse sets or resets the output latch, updating Q to match D.
    
- **Clock High (1):**
    - The outputs of the first two latches remain latched, so the output latch is forced to stay in its new state, regardless of further changes in D.
    
- **Clock Falling Edge (1→0):**
    - The circuit returns to the initial condition, ready for the next clock cycle.
[Detailed Explanation of the working](https://youtu.be/NHPDR5ElYoc?si=5wN-dFV_DgfvC6v-)

## Graphical Symbol of Edge-Triggered D FlipFlops
![[Pasted image 20251027110928.png]]

## Edge Transition JK Flip Flop and T Flip-Flop using D FlipFlops
Similarly, we get JK flip-flop in terms of D Flip-Flop:
$$D = JQ' + K'Q$$
![[Pasted image 20251014173651.png]]

And T Flip-Flop in terms of D Flip-Flop:
$$D=T\oplus Q = TQ' + T'Q$$
![[Pasted image 20251014173821.png]]

And using these, we can construct an edge transition flip-flop for both cases.
These not only allow us to store the info, but also access or inverse it as well quite easily.

# Characteristic Table:
A characteristic table defines the logical properties of a flip-flop by describing its operation in tabular form.
They define the next state of the flip-flop as a function of inputs and present state.
 ![[Pasted image 20251014174217.png]]
# Characteristic Equations
Logical property of a flip-flop described via a characteristic table can be described via characteristic equations as well.
For a D Flip-Flop, the equation is:
$$Q(t+1)=D$$
For a JK Flip-Flop:
$$Q(t+1) = J\cdot Q(t)'+K'\cdot Q(t)$$
Similarly for a T Flip-Flop:
$$Q(t+1)= T\oplus Q(t) = T\cdot Q(t)'+T'\cdot Q(t)$$

# Direct Inputs in Flip-Flops:
Some flip-flops have asynchronous inputs that are used to force the flip-flop to a particular state independently of the clock.

- The input that sets the flip-flop to 1 is called *preset* or *direct set*.
- The input that sets the flip-flop to 0 is called *clear* or *direct reset*.

As when we start a flip-flop, then the state of the flip-flop is unknown, hence this helps us all to start on the same page.

# Analysis of Clocked Sequential Circuits
A Sequential circuit can operates under a certain conditions. The behaviors is determined by the Input, Outputs, and the state of it's flip-flops, with output being the function of both input and the present state of the flip-flops.

## State Equations
These are the equations that represent the next state as a function of present state and inputs.
This might look the same as [[#Characteristic Equations]], but they are only for the flip-flops, but state equations are defined for the whole system.

For example:

Consider a sequential circuit with two D flip-flops A and B, one input $x$, and output $y$. The combinational logic gives:
- $D_A = A\cdot x + B\cdot x$
- $D_B=\overline{A}\cdot x$

Since for a D Flip-Flop, $Q(t+1)=D$, the state equations are:
- $A(t+1)=A(t)\cdot x(t) + B(t)\cdot x(t)$
- $B(t+1)=\overline{A(t)}\cdot x(t)$

This if represented using gates, would look like this:
![[Pasted image 20251015022255.png]]

## State Table
A **state table** is a tabular representation of a sequential circuit's behavior that shows the relationship between the _present state_, _inputs_, _next state_, and _outputs_. It is a key tool for designing and analyzing sequential circuits.

### How to Write a State Table
1. **List all possible present states**: For a circuit with _n_ flip-flops, there are up to 2n2n states.
2. **List all possible input combinations** for each present state.
3. **Determine the next state** for each present state and input combination. This is done based on the circuit’s logic or flip-flop characteristic equations.
4. **Determine the output** for each combination. Output can depend on the present state and input (Mealy machine) or only on present state (Moore machine).

For Example, the state table of the previous Circuit is:
![[Pasted image 20251015024528.png]]

## State Diagram
A **state diagram** is a graphical representation of a sequential circuit's behavior. It shows how the circuit transitions from one state to another based on inputs, and it usually includes state outputs.

### Elements of a State Diagram
- **States:** Represented by circles or ovals; each circle shows a possible state of the circuit.
- **Transitions:** Directed arrows between states, showing how the circuit moves from one state to another when an input occurs.
- **Labels on Transitions:** Usually written as `input/output` or `input` (with output inside states) to indicate what input causes the transition and what output is produced.
- **Initial State:** One state may be marked or assumed as starts when the circuit powers on or resets.

For example, the state diagram for the previous state table is:
![[Pasted image 20251015024838.png]]

## Input / Output Equations
Input and output equations in sequential circuits define the relationships between the circuit inputs, the current state, and the outputs.
### Input Equations
<blockquote>The part of the combinational circuit that generates external outputs is described algebraically by a set of Boolean functions called <b>output equations</b>.</blockquote> 
- These describe the inputs to the flip-flops in terms of the present state (flip-flop outputs) and external inputs.
- For each flip-flop, the input equation expresses the flip-flop input signals (like D, J, K, T) as Boolean functions. This controls how the state evolves.

### Output Equations
<blockquote>The part of the circuit that generates the inputs to flip-flops is described algebraically by a set of Boolean functions called flip-flop <b>input equations</b>.</blockquote>
- These define the outputs of the sequential circuit as functions of the present state and sometimes the inputs.
- Output equations depend on circuit type:
    - For **Moore machines**, outputs depend only on the present state.
    - For **Mealy machines**, outputs depend on the present state and inputs.

### Example
- Input equations for flip-flops:
    $$D_A=A\cdot x + B\cdot \overline{x}$$
    $$D_B=\overline{A}\cdot x$$
    
- Output equation:
    $$Y=A\cdot B + \overline{x}$$
This is an example of a **Mealy Machine**.

# State Machines (The Sequential Circuits)
The most general model of a sequential circuit has inputs, outputs, and internal states. 
It is customary to distinguish between two models of sequential circuits: 
- ***the Mealy model***
	*In the Mealy model, the output is a function of both the present state and the input.*
	![[Pasted image 20251015042536.png]]
	A classic example for Mealy Model is an Sequence detector:
	- A sequence detector is the digital circuit that detects some input signal sequences from a set of the binary data. 
	- One can determine whether incoming bits are equal to a prestored sequence, thus widely used in Communication Systems, Data Processing, and Digital Signal Processing.
	-  Sequence detector is of two types:
		- Overlapping
		- Non-Overlapping
		```text
		For non overlapping case 
		Input :0110101011001 
		Output:0000100010000 
		
		For overlapping case 
		Input :0110101011001 
		Output:0000101010000
		```
		
	
- ***the Moore model***
	In the Moore model, the output is a function of only the present state. A circuit may have both types of outputs.
	![[Pasted image 20251015042549.png]]
	A classic example for Moore Machine is a binary counter:
	![[Pasted image 20251015042928.png]]

## But how to design a machine?
Ok, so lets start by an example, lets design a 101 Mealy sequence detector (non-Overlapping):

### Step 1: Knowing your states
First step in designing is know how are we reaching the desired output, hence making the state diagram of the machine.

![[Pasted image 20251015043944.png]]

So here, as we can see, we have considered cases where what output makes the machine go to what state. A quick exercise, try to understand how is this working, the changing of state.

### Step 2: Assigning values to those states
Now that we know about the state it is time to assign them the values.
For now, it is pretty intuitive that the following should be the value:
- a: 00
- b: 10
- c: 01
However knowing the basis of these assignment is important, i.e., we should know what and what not to consider which assigning these values.

- ***Rule 1*** : States having the same next states for a given input condition should have adjacent assignments.
	- If two or more states transition to the same state(s) under a specific input, assign their binary codes so that they are _adjacent_ in a Karnaugh map (K-map).
    - This adjacency in binary codes helps produce simpler Boolean expressions for flip-flop inputs.
    - Example:
	    - Suppose states $S_1$ and $S_2$ both transition to $S_3$ when input $X=1$. 
	    - If you assign $S_1=00$ and $S_2=01$ (adjacent codes), their K-map cells are next to each other. 
	    - You can group them, leading to a simpler logic equation for the transition to $S_3$
- ***Rule 2***: States that are the next states to a single state must be given adjacent assignments.   
	- If a single state transitions to multiple next states, assign the binary codes of these next states so that they are adjacent.
    - This maintains logical grouping that aids simplification.
    - Example
		Suppose state $S_0$ can go to $S_1$, $S_2$, or $S_3$ depending on the input.  
		Assign $S_1 = 100$, $S_2 = 101$, $S_3 = 110$.  
		These codes are adjacent in a K-map (they differ by only one bit each), so the logic for transitioning from $S_0$ to any of these states can be simplified.
(_Rule 1 given preference over Rule 2._)
Example:
# Systematic State Encoding—Step-by-Step with Your Rules
Let’s now apply the **two classic state encoding rules** to your state diagram, explaining all reasoning explicitly:
- **Rule 1**: States with the _same next state for a given input_ should have adjacent codes
- **Rule 2**: States that are _next states from a single state_ should be adjacent
(Prefer Rule 1 over Rule 2 if there’s a conflict.)

## Your State Diagram Recap

|Current|Input/Out|Next|
|---|---|---|
|A|0/0|A|
|A|1/0|B|
|B|0/0|C|
|B|1/0|B|
|C|0/0|A|
|C|1/0|D|
|D|0/1|C|
|D|1/1|B|

## **Step 1: Tabulate Next States for Same Input (Rule 1)**
**For Input 0:**
- A (0): A
- B (0): C
- C (0): A
- D (0): C

**A and C both go to A when input is 0** → We should make A and C adjacent.  
**B and D both go to C when input is 0** → B and D should be adjacent.

**For Input 1:**

- A (1): B
    
- B (1): B
    
- C (1): D
    
- D (1): B
    

**A, B, D all go to B when input is 1** → These three (A, B, D) should be adjacent to B or possibly each other.

## **Step 2: Tabulate Next States from a Single State (Rule 2)

For each state, what are its possible next states?

- A → A (0), B (1)
    
- B → C (0), B (1)
    
- C → A (0), D (1)
    
- D → C (0), B (1)
    

So:

- From A: next states are A, B → make these adjacent.
    
- From B: next states are C, B → adjacent.
    
- From C: next states are A, D → adjacent.
    
- From D: next states are C, B → adjacent.
    

## **Step 3: Group States on Adjacency Graph

## _Adjacency Graph ("must be adjacent")_

- **A ↔ C** (both go to A with 0)
    
- **B ↔ D** (both go to C with 0)
    
- **A ↔ B** (from A with 1)
    
- **B ↔ C** (from B with 0)
    
- **C ↔ D** (from C with 1)
    
- **D ↔ B** (from D with 1)
    

## **Step 4: Choose Binary Assignments**

**We need two bits for 4 states**:

- Possible codes: 00, 01, 10, 11
    

**Try to satisfy as many adjacents as possible:**

## Arrangement 1:

- **A = 00**
    
- **B = 01**
    
- **C = 11**
    
- **D = 10**
    

Let's check adjacencies:

- **A↔B**: 00–01 ✔ (adjacent, 1 bit diff)
    
- **A↔C**: 00–11 ✘ (not adjacent, 2 bits diff)
    
- **C↔D**: 11–10 ✔ (adjacent, 1 bit diff)
    
- **B↔D**: 01–10 ✘ (not adjacent)
    
- **B↔C**: 01–11 ✔ (adjacent)
    
- **D↔B**: 10–01 ✘ (not adjacent)
    

## Arrangement 2:

Try:

- **A = 00**
    
- **B = 10**
    
- **C = 11**
    
- **D = 01**
    

Adjacents:

- **A↔B**: 00–10 ✔
    
- **A↔C**: 00–11 ✘
    
- **C↔D**: 11–01 ✔
    
- **B↔D**: 10–01 ✔
    
- **B↔C**: 10–11 ✔
    
- **D↔B**: 01–10 ✔
    

Here, most adjacency requests are satisfied:

- **A and B**: adjacent
    
- **B and C**: adjacent
    
- **B and D**: adjacent
    
- **C and D**: adjacent
    
- **D and B**: adjacent
    

The only NOT adjacent is **A↔C** (00–11), which is unavoidable for 4 states, but we hit the rest.

## **Step 5: Assign Codes Based on Your Rules**

**FINAL ASSIGNMENT:**

- A = 00
    
- B = 10
    
- C = 11
    
- D = 01
    

## **Step 6: Build the Transition Table with Chosen Codes**

|State|S₁|S₀|Input (x)|Next State|S₁⁺|S₀⁺|Output (z)|
|---|---|---|---|---|---|---|---|
|A|0|0|0|A|0|0|0|
|A|0|0|1|B|1|0|0|
|B|1|0|0|C|1|1|0|
|B|1|0|1|B|1|0|0|
|C|1|1|0|A|0|0|0|
|C|1|1|1|D|0|1|0|
|D|0|1|0|C|1|1|1|
|D|0|1|1|B|1|0|1|

## **Step 7: K-Maps and Minimization**

Now, with these assignments, build your K-maps for next states S1+S_1^+S1+ and S0+S_0^+S0+ and output zzz, using table above—and simplify your logic!

**If you want, we can do a live minimization round together next. But this assignment follows your adjacency rules and gives you the most logical simplification possible. Want to continue with K-map filling and logic equations now?**

**Key Takeaway:**

> Systematic encoding by following adjacency rules means less complex logic—always base your codes on the _actual state diagram and transition patterns_, not just textbook order.

### How to Assign Values to K-Maps for States

#### 1. State Assignment
- **Assign binary codes to each state.** For $m$ states, use $n$ bits where $2^n \geq m$. For example, with 5 states, use 3 bits: 000,001,010,011,100.​
- **Follow adjacency rules:**
    - States with the same next state for a given input should have adjacent codes in the K-map (differ by one bit).
    - States that are next states of a single state should also be assigned adjacent codes, but this is a lower priority.

#### 2. Create the State Table
- List all present states (using assigned codes), all possible input combinations, and the corresponding next states and outputs.

#### 3. Set Up the K-Map
- For each flip-flop input and output equation, create a K-map with variables for present state bits and inputs.
- For example, with 3 state bits ($Q_2,Q_1,Q_0$) and 2 inputs ($X,Y$), you’ll use a 5-variable K-map.

#### 4. Fill the K-Map
- For each cell, use the present state and input combination to look up the next state or output from the state table.
- Mark a '1' for conditions where the output or flip-flop input should be high, and '0' otherwise.    
- For SOP (Sum of Products), place '1's in the K-map for each minterm where the function is true.

#### 5. Simplify Using the K-Map
- Group adjacent '1's to form the simplest possible Boolean expressions for each flip-flop input and output.
- This step is where good state assignment pays off: adjacent codes allow larger groups and simpler logic.

(Refer to this while reading the rules)
![[Pasted image 20251015043944.png]]
![[Pasted image 20251015045024.png]]
![[Pasted image 20251015045054.png]]
### Step 3: Make the State Table:
What? Make the table!
![[Pasted image 20251015050426.png]]

### Step 4: Draw the K-Maps, solve and voila, the circuit is ready!
![[Pasted image 20251015050527.png]]![[Pasted image 20251015050747.png]]

## State Reduction in FSD(Finite State Machines, aka Mealy and Moore Machines)

# Registers
A **register** is a group of flip‐flops, each one of which shares a common clock and is capable of storing one bit of information. An *n‐bit register* consists of a group of $n$ flip‐flops
A **binary cell** is the smallest storage element in a digital system. It can exist in **two stable states**, representing **1** and **0**. Each flip-flop (FF) in a circuit serves as one such cell.

The internal structure of a register typically contains:
- **Flip-Flops (FFs):** Used for data storage. Commonly D-type FFs are used.
- **Control Signals:** Such as **Load**, **Clear**, **Shift**, and **Clock (CLK)**.
- **Data Lines:** Inputs and outputs for binary information.

## Types of Registers
Registers differ by the way they accept or deliver data:

| Type of Register                   | Data Operation                   | Description                                        | Example Application               |
| ---------------------------------- | -------------------------------- | -------------------------------------------------- | --------------------------------- |
| **Simple Data Register**           | Parallel load and read           | Stores data temporarily                            | Used between ALU operations       |
| **Shift Register**                 | Serial shifting (L-R or R-L)     | Used for serial communication and bit manipulation | Serial data transfer              |
| **Universal Shift Register**       | Left/Right shift + parallel load | Supports multiple modes via control signals        | Data conversion (parallel↔serial) |
| **Ring Counter / Johnson Counter** | Circulating bits                 | Used in timing signal generation                   | Sequential pulse generation       |

# Parallel Load Registers
A **parallel load register** is a group of **flip-flops** (most often D-type) where each flip-flop stores one bit of data, and all flip-flops share a **common clock** and a **common load control signal**.​
The *transfer of new information into a register* is referred to as **loading** or **updating** the register. If all the bits of the register are loaded simultaneously with a common clock pulse, we say that the loading is done in **parallel**.
- _Parallel load_ means that multiple bits (for example, 4 or 8) are **transferred into the register at the same time** through dedicated input lines.    
- The **load control input (Load)** determines whether the register should **accept new data** or **retain its existing data**.
- The **clock pulse** triggers the loading: the inputs are captured only at the **clock edge** (positive or negative edge depending on flip-flop type).

---
## Structure and Operation
Lets take an example, and consider a **4-bit register with parallel load**:
We are designing a simple 4-bit data storage register with parallel load. So what could be the most simplified way in which one can design suck a thing? 
![[Pasted image 20251118040249.png]]

Yep, the most simplified way is to connect the Input ports to the a D flip-flop and then get the output. Have a common clock for all the flip flops, and to reset it all, a reset signal.
But then we encounter an issue, **What if we want to hold the data?** 

Then we would have two options:
- Hold the input constant, which would require a data bus, and hence, it wont be available for other operations
- Design a way to inhibit clock from the circuit.

Now if we were to do the second option, as the first one obviously is not gonna give us optimization and it consumes resource, then the clock can be inhibited from reaching the register by controlling the clock input signal with an enabling gate.

However, it is ill advised to use combinational logic with the clock input as gates take some time to execute, which can then introduce unexpected delays, leading the circuit to go asynchronous wrt to the *master clock* of the system. (**Master clock** is the main clock that provides a common clock beats for the whole system, i.e., it acts as a drum beat for all the circuits(like adders, registers, etc,.) in the system.)

So then, the next best option is to use an MUX:
![[Pasted image 20251118041854.png]]

- The circuit consists of **four D flip-flops**, each having its own **data input (I₀–I₃)** and **output (A₀–A₃)**.
- All flip-flops share:
    - A **common clock input (CLK)**.
    - A **common load control signal (Load)**.
    - A **clear input (Clear̅)** to reset the register asynchronously.

**Operation Principle:**
1. **Load = 1:**  
    When the load control signal is high, all data bits present at I₀–I₃ are loaded into the flip-flops on the next **positive edge of the clock**.  
    This means A₀–A₃ get updated simultaneously.
    
2. **Load = 0:**  
    When Load = 0, the register _holds_ its last value.  
    To achieve that, internal feedback paths recirculate the **current outputs (A₀–A₃)** back into the D inputs, making D = A.
    
3. **Clear̅ = 0:**  
    When the clear input is active low, all flip-flops are reset to **0** asynchronously, regardless of the clock.

The logic can be summarized as:

| Control    | Action                              |
| ---------- | ----------------------------------- |
| Clear̅ = 0 | Reset all bits to 0                 |
| Load = 1   | Load new data (I₀–I₃) on next clock |
| Load = 0   | Hold current data (A₀–A₃)           |

### Example Circuit Implementation
A **4-bit parallel load register** uses multiplexers (MUXs) to control whether new input or stored data is fed into each flip-flop.
- Each flip-flop’s D input is driven by a **2-to-1 MUX**:
    - Input 0 of MUX: The **current flip-flop output** (for holding data).
    - Input 1 of MUX: The **external data input (Iᵢ)** for loading new data.
    
- The **Load** signal selects between these two inputs.

So,
$$D_i = (Load)·I_i + (\neg Load)·A_i$$
- $I_i$ = new input
- $A_i$ = Previously stored value

This keeps the clock path clean — no gating of the clock — which is crucial for **synchronous systems**, maintaining timing integrity.​

### Timing Operation
When the **positive clock edge** occurs:
- If _Load = 1_, each flip-flop stores the **corresponding bit** from its input line.
- If _Load = 0_, it re-stores its own **previous output**, so the data remains unchanged between cycles.

### Verilog Model (Behavioral Example)

A 4-bit parallel load register can be modeled as:
```Verilog
module Register4 (output reg [3:0] A,
                  input [3:0] I,
                  input Load, Clock, Clearb);

  always @(posedge Clock or negedge Clearb)
    if (!Clearb)
       A <= 4'b0000;
    else if (Load)
       A <= I;     // Load input in parallel
    else
       A <= A;     // Hold previous value
endmodule

```

## Result:
This design allows parallel data input without interfering with system timing, ensuring all bits remain synchronized.

# Shift Registers
A register capable of shifting the binary information held in each cell to its neighboring
cell, in a selected direction, is called a **shift register**.

<blockquote><q>A <b>shift register</b> is a type of <b>sequential circuit</b> used to <b>store and move binary data</b> in a controlled way. It consists of <b>a chain of flip-flops</b>, each capable of storing one bit, with connections that allow the contents of one flip-flop to <b>shift</b> into the next on each <b>clock pulse</b>.</q></blockquote>

In simpler terms, a shift register takes bits one at a time (serially), or all at once (in parallel), and moves them left or right, depending on its configuration.

A simplest form of Shift Register would be:
![[Pasted image 20251018083211.png]]

Sometimes it is necessary to control the shift so that it occurs only with certain pulses, but not with others. So again, we face the same problem as that in parallel one, and again we have two options:
- Gate the clock (ill advised)
- design a combinational logic for all the D flip-flops that connects to a input signal called *Shift Control* or *enabler*
And it is obvious to go for the later, so how do we do it then? We design a 2:1 MUX for all the D flip-flops, which let the input of the previous flip-flop in when the MUX Signal(i.e., shift control) is 1, and let the previous value of the flip-flop in if MUX signal is 0.

## Concept of Shifting
Each **clock pulse** in a shift register triggers all flip-flops simultaneously:
- The output of one flip-flop becomes the input to the next.
- This movement from one flip-flop to another is called a **shift**.
- The shifting direction (left or right) depends on how the flip-flops are connected.

Thus, shift registers are used not just for data storage, but also for **data manipulation**, **conversion**, and **transfer**.

## Structure of a Shift Register
A typical **n-bit shift register** contains:
- **n flip-flops**, connected in series, each storing one bit. 
- A **common clock input** — synchronizes bit movement.
- A **serial input** (SI) — for inserting new bits during shifts.
- A **serial output** (SO) — for reading bits as they leave the register.

The simplest configuration is **unidirectional**, meaning bits shift only in one direction (e.g., right). More advanced types allow **bi-directional shifting** (left and right).

## Types of Shift Registers
Shift registers are classified based on **how data enters and leaves** the circuit :​

| Type                            | Input Mode                            | Output Mode       | Description                              |     |
| ------------------------------- | ------------------------------------- | ----------------- | ---------------------------------------- | --- |
| Serial In Serial Out (SISO)     | One bit at a time                     | One bit at a time | Basic shift-right storage register       |     |
| Serial In Parallel Out (SIPO)   | One bit at a time                     | All bits at once  | Used in serial-to-parallel conversion    |     |
| Parallel In Serial Out (PISO)   | All bits at once                      | One bit at a time | Used in parallel-to-serial conversion    |     |
| Parallel In Parallel Out (PIPO) | All bits at once                      | All bits at once  | Works like a parallel load register      |     |
| Universal Shift Register        | Both serial and parallel input/output | Both directions   | Can load, shift left/right, or hold data | ,   |

## Serial Transfer Shift Register
A **serial transfer shift register** is a configuration of **two shift registers** that allows data to be transferred **bit by bit (serially)** from one register to another through a single line.
In **serial transfer**, data is moved **one bit at a time** under the control of clock pulses. Each bit in the source register moves to the next position, and its **rightmost bit (serial output)** goes into the **leftmost bit (serial input)** of the destination register.

This operation is performed simultaneously in both registers under a **common clock**, so that in each clock cycle:
- One bit exits the source register (Register A). 
- One bit enters the destination register (Register B).

After **n clock pulses**, where _n_ is the number of bits in the registers, the **entire word(represents the amount of data the system processes, stores, or transfers at one time in a particular system. In this case, a word would be 4 bits)** is transferred serially from A to B.

### Structure
A typical **serial transfer system** involves:
1. **Two shift registers** — Register A (source) and Register B (destination).
2. A **serial connection** between the output of A and the input of B.
3. A **clock signal** shared by both registers to synchronize shifting.
4. A **shift control** signal to determine when shifting occurs.

The basic connections are:
- **Serial Output (SOA)** of Register A → **Serial Input (SIB)** of Register B
- **Clock** connected in parallel to both registers
- **Shift Control** used to enable shifts for a fixed number of cycles

### Circuit Explanation
Each shift register is made of flip-flops connected in a cascade. For **rightward shifting**, each flip-flop’s output connects to the input of the one on its right.

During serial transfer:
- The **LSB** (least significant bit) of Register A exits first through its serial output.
- This bit enters the **MSB** (most significant position) of Register B.
- Both registers shift one position right on every clock pulse.

To prevent losing data in A during transfer, the system **recirculates data** in A — by connecting its output back to its input. This ensures A retains its original content even as B receives a copy.

![[Pasted image 20251018091821.png]]
**(This practice can be problematic because it may compromise the clock path of the circuit, as discussed earlier.)**
![[Pasted image 20251118033800.png]]

## Serial Addition Shift Register
**Serial addition** is the process of adding two binary numbers by processing them sequentially—handling one pair of bits per clock cycle. Think of it like a **manufacturing assembly line** rather than doing everything at once:
- **Parallel addition (conventional way)**: All bits are added simultaneously, which is faster, but requires more hardware
- **Serial addition (sequential way)**: Bits are added one at a time, which is slower per operation, but uses much less hardware

In **VLSI (Very Large Scale Integration) circuits**, a serial adder requires significantly fewer silicon transistors compared to a parallel adder because it:
1. Uses only **one full adder** instead of n full adders (where n = number of bits)
2. Uses **one carry storage flip-flop** to remember carries between cycles    
3. Uses **shift registers** to process data sequentially

This translates to **reduced chip area, lower power consumption, and lower manufacturing costs**—critical considerations in digital design.

### Working:
So, one way to do it would be to store the two binary numbers to be added serially in two shift registers, lets say register A stores the augend, and register B stores the addend. 

Beginning with the least significant pair of bits, the circuit adds one pair at a time through a single full‐adder (FA) circuit, and then store the result of the addition in the register A, and the carry is stored in a D flip-flop called Carry Flip-Flop (Q). As the addition result is stored in register A, we can store the input serial signal into register B.

In order to keep the bits in sync, (i.e., if we have $A_3A_2A_1A_0$ and $B_3B_2B_1B_0$, then making sure that $B_0$ adds $A_0$, $B_1$ adds $A_1$), the output of the adder (S) is given as the serial input of A, then a serial input is given in register B, this serial input is the input port.

Now, there is another signal called *Shift Control*, that controls when the shift will happen and when it will not irrespective of the clock signals. Its implementation was discussed earlier.

**Architecture:**
1. **Register A (Augend Register)**: Stores the first number, shifts right on each clock pulse
2. **Register B (Addend Register)**: Stores the second number, shifts right on each clock pulse
3. **Full Adder**: Combines one bit from A, one bit from B, and the carry from previous cycle
4. **Carry D Flip-Flop (Q)**: Stores the carry output, provides carry input to next cycle
5. **Control Signal (Shift Control)**: Enables shifting when the addition is active

Example implementation, A has 0000, and B has 0000, then we want to add 1001 and 1101, then try thinking what would happen if we pass 1001 bit by bit followed by 1101 which is followed by 0000 again.

![[Pasted image 20251118052051.png]]
**(Note that only LSB of both are the ones that are being added, and only MSB of both as the ones which are taking the inputs)**

### Signal Flow in Each Clock Cycle
With each clock pulse:
1. **Before Clock Edge**:
    - Serial outputs of Register A and B present x and y inputs to full adder
    - Carry flip-flop output (Q) provides z input to full adder
    - Full adder immediately computes: Sum (S) and Carry-out
    
2. **At Clock Edge**:
    - Sum bit (S) shifts into the leftmost position of Register A
    - All bits in both registers shift one position to the right
    - Carry-out transfers into the D flip-flop (becomes the new Q)
    - Registers shift out their rightmost bits (which are lost or go to next stage)
    
3. **After Clock Edge**:
    - New values are in registers and carry flip-flop
    - Circuit is ready for next bit pair on next clock pulse

# Universal Shift Register
So, If we have access to the flip-flops that store the information of a shift register, then we have option to either take the output out in parallel or in series form.
Similarly, with the data stored, we have freedom of storing the input in parallel form, or we can pass in the information serially by shifting.

So some has necessary input output for parallel shifts, some for serial shifts. If a shift register can shift only in one direction, then it is called *unidirectional*, while if it can shift in both directions, then it is called *bidirectional* shift register.

A general shift register has the following capabilities:
- a *clear* control to reset the input
- A *clock* input to synchronize the operation
- A *shift‐right* control to enable the shift‐right operation and the *serial* input and output lines associated with the shift right.
- A *shift‐left* control to enable the shift‐left operation and the *serial* input and output lines associated with the shift left.
- A *parallel‐load* control to enable a parallel transfer and the *n* input lines associated with the parallel transfer.
- *n* parallel output lines
- A control state that leaves the information in the register unchanged in response to the clock

Now other registers may have some of these functions, with atleast one shift operations.
If a shift register has all of the given features, then it is called **Universal Shift Register**.

![[Pasted image 20251118144759.png]]

Here is a universal shift register with its graphical symbol. 
As we can see and analyse, the MUX inputs handle it like this:
![[Pasted image 20251118145022.png]]

# Counter
A **counter** is essentially a **register** that changes its state in a predefined pattern upon receiving clock pulses.​ Thus, while a **register** stores data, a **counter** automatically **updates its stored data** to represent the number of occurrences of a specific event.A counter that follows the binary number sequence is called a **binary counter**.

For an **n-bit counter**, there are $2^n$ possible states, so it can count from 0 up to $2^n−1$.
Well, it can be said that the counters are a type of FSM.
For example, a circuit that counts from 0 to 16 would be considered as a counter.

## How a Counter Works
A counter consists of:
- **Flip-flops** (each storing 1 bit)
- **Clock pulses** (triggering state changes)
- **Combinational logic** (determining the next state based on the current state)

## Types of counters
Counters are available in two categories: 
- **Ripple Counters**
	- This is an Asynchronous Circuit, in which the output of one is influences the input of another. There is no common clock connected, and the output changes as the input changes instead of the clock signals.
	- In other words, the C input of some or all flip‐flops are triggered, not by the common clock pulses, but rather by the transition that occurs in other flip‐flop outputs.  
- **Synchronous Counters**
	- However, here a common clock is present which determines when the output changes.
	- Inputs of all flip‐flops receive the common clock.

## Ripple Counters
A **ripple counter** is a type of digital counter where the flip-flops are connected in series (cascade) and the output of one flip-flop acts as the clock input for the next flip-flop in line. It is called "asynchronous" because not all flip-flops are clocked simultaneously.

### How it Works
1. **First flip-flop** is clocked directly by an external clock signal.
2. **Subsequent flip-flops** are triggered by the output of the previous flip-flop.

**Example:**  
Consider a 4-bit ripple counter using T flip-flops.

- The external clock drives the first flip-flop.
- The output of the first flip-flop drives the clock of the second, and so on.

### Key Features
- **Simple design**
- **Propagation delay accumulates:** Each flip-flop waits for the previous one, causing delays.
- **Suitable for low-speed applications**
- **Modulus:** An n-bit ripple counter counts from 0 to 2ⁿ–1.

#### **Timing Diagram**
When the clock pulses, the outputs change in a "ripple" manner—one after another.

---
## Synchronous Counter
A **synchronous counter** is a type of counter where all flip-flops receive the clock pulse **at the same time**. This means that all the flip-flops are triggered synchronously (simultaneously).

### How it Works
- **Clock connected to all flip-flops:**  
    Every flip-flop gets the same clock signal.
- **Logic circuitry controls toggling:**  
    Additional logic gates decide when each flip-flop should change state, based on the current count.

**Example:**  
In a 4-bit synchronous counter, all four flip-flops receive the clock together, and logic gates ensure correct counting.

### Key Features

- **Fast operation:** All flip-flops change simultaneously, so propagation delay is minimized.
- **Complex design:** Needs extra logic gates for correct operation.
- **Used in high-speed digital applications**

## Example: 3-bit Binary Ripple Counter
A 3-bit ripple counter counts from 000 to 111 (0 to 7 in decimal).

### **Circuit Diagram**
```
                     ┌─────┐      ┌─────┐      ┌─────┐
         CLK ────────┤     │      │     │      │     │
                     │ FF0 │  Q0──┤ FF1 │  Q1──┤ FF2 │  Q2
                     │ T   │      │ T   │      │ T   │
                     └─────┘      └─────┘      └─────┘
                        │            │            │
                       Q0           Q1           Q2
                     (LSB)                     (MSB)
```

**Key Points:**

- All flip-flops are T (toggle) flip-flops with T=1 (always toggling)
- **FF0** is clocked by the external clock
- **FF1** is clocked by Q0 (output of FF0)
- **FF2** is clocked by Q1 (output of FF1)

### **Truth Table**

|Clock Pulse|Q2|Q1|Q0|Decimal|
|---|---|---|---|---|
|0|0|0|0|0|
|1|0|0|1|1|
|2|0|1|0|2|
|3|0|1|1|3|
|4|1|0|0|4|
|5|1|0|1|5|
|6|1|1|0|6|
|7|1|1|1|7|
|8|0|0|0|0|

### **Timing Diagram**

Code

```
CLK   ┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐
      └─┘└─┘└─┘└─┘└─┘└─┘└─┘└─┘

Q0    ┐   ┌───┐   ┌───┐   ┌───┐   ┌
      └───┘   └───┘   └───┘   └───┘

Q1    ┐       ┌───────┐       ┌───────┐
      └───────┘       └───────┘       └

Q2    ┐               ┌───────────────┐
      └───────────────┘               └

Time:  0   1   2   3   4   5   6   7   8
```

### **How It Works**

1. **Clock Pulse 1:**
    
    - CLK toggles FF0 → Q0 goes from 0→1
    - FF1 and FF2 are not affected yet
2. **Clock Pulse 2:**
    
    - CLK toggles FF0 → Q0 goes from 1→0 (falling edge)
    - Q0's falling edge toggles FF1 → Q1 goes from 0→1
3. **Clock Pulse 3:**
    
    - CLK toggles FF0 → Q0 goes from 0→1
    - Q1 remains 1
4. **Clock Pulse 4:**
    
    - CLK toggles FF0 → Q0 goes from 1→0
    - Q0's falling edge toggles FF1 → Q1 goes from 1→0
    - Q1's falling edge toggles FF2 → Q2 goes from 0→1

**Notice:** The "ripple" effect—changes propagate from FF0 to FF2 sequentially.

## Example: 3-bit Synchronous Binary Counter

### **Circuit Diagram**

Code

```
                  ┌─────┐      ┌─────┐      ┌─────┐
         CLK ─────┤>    │      │>    │      │>    │
             ├────┤ FF0 │  ┌───┤ FF1 │  ┌───┤ FF2 │
             │    │ T   │  │   │ T   │  │   │ T   │
             │    └─────┘  │   └─────┘  │   └─────┘
             │       │     │      │     │      │
             │      Q0     │     Q1     │     Q2
             │       │     │      │     │
             │       └─────┤      │     │
             │             │      │     │
             │           ┌─┴─┐    │     │
             │           │AND│────┤     │
             │           └───┘    │     │
             │                    │     │
             │                  ┌─┴─┐   │
             │                  │AND│───┤
             │                  └───┘   │
             │                    ▲     │
             └────────────────────┴─────┘
                           (3-input AND)
```

**Logic:**

- **FF0:** T0 = 1 (always toggles)
- **FF1:** T1 = Q0 (toggles when Q0=1)
- **FF2:** T2 = Q0·Q1 (toggles when both Q0 and Q1 are 1)

### **Truth Table**

|Clock Pulse|Q2|Q1|Q0|Decimal|
|---|---|---|---|---|
|0|0|0|0|0|
|1|0|0|1|1|
|2|0|1|0|2|
|3|0|1|1|3|
|4|1|0|0|4|
|5|1|0|1|5|
|6|1|1|0|6|
|7|1|1|1|7|
|8|0|0|0|0|

### **Timing Diagram**
```
CLK   ┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐ ┌┐
      └─┘└─┘└─┘└─┘└─┘└─┘└─┘└─┘

Q0    ┐   ┌───┐   ┌───┐   ┌───┐   ┌
      └───┘   └───┘   └───┘   └───┘

Q1    ┐       ┌───────┐       ┌───────┐
      └───────┘       └───────┘       └

Q2    ┐               ┌───────────────┐
      └───────────────┘               └

Time: 0   1   2   3   4   5   6   7   8
```

### **How It Works**

**All flip-flops are clocked simultaneously! **

1. **Clock Pulse 1:**
    
    - T0=1, so Q0 toggles: 0→1
    - T1=0 (Q0 was 0), so Q1 stays 0
    - T2=0, so Q2 stays 0
    - **Result:** 001
2. **Clock Pulse 2:**
    
    - T0=1, so Q0 toggles: 1→0
    - T1=1 (Q0 is 1 before toggle), so Q1 toggles: 0→1
    - T2=0 (Q0·Q1 was 0), so Q2 stays 0
    - **Result:** 010
3. **Clock Pulse 3:**
    
    - T0=1, so Q0 toggles: 0→1
    - T1=0 (Q0 was 0), so Q1 stays 1
    - T2=0, so Q2 stays 0
    - **Result:** 011
4. **Clock Pulse 4:**
    
    - T0=1, so Q0 toggles: 1→0
    - T1=1 (Q0 is 1), so Q1 toggles: 1→0
    - T2=1 (Q0·Q1 was 1), so Q2 toggles: 0→1
    - **Result:** 100

**Key:** All changes happen **simultaneously** at the clock edge—no ripple effect!


# Asynchronous Sequential Ciruits
Asynchronous sequential logic is a fundamental type of sequential circuit that operates **without a clock signal**.
- **Do NOT use a clock signal** 
- Respond immediately to input changes
- Changes occur whenever inputs change, at any time
- Use time-delay devices or gate propagation delays for storage

## Core Characteristics of Asynchronous Sequential Circuits
Key properties that define asynchronous circuits:

### Storage Elements:  
Asynchronous circuits use **time-delay devices** as their storage mechanism. The critical insight here is that the **propagation delay** of logic gates itself provides the needed storage capability. When a signal passes through a gate, it takes a small amount of time (nanoseconds) - this delay is exploited to store information temporarily.

### Feedback Structure:  
An asynchronous sequential circuit can be viewed as a **combinational circuit with feedback**. The outputs are fed back to the inputs, creating a loop. This feedback, combined with gate delays, creates memory.

### State Changes:  
The behavior depends on:
- The **input signals at any instant of time**
- The **order in which inputs change**
This makes them fundamentally different from synchronous circuits where only clock edges matter.

## How Asynchronous Circuits Work
**Basic Operation:**
1. Input changes propagate through the combinational logic
2. The gate delays provide time for signals to stabilize
3. Feedback paths maintain the circuit state
4. New inputs can cause immediate state transitions

**Speed Advantage:**  
Since there's no clock to wait for, asynchronous circuits can potentially operate **faster** than synchronous circuits. As soon as inputs change, the circuit responds - there's no waiting for the next clock edge.​

## Critical Design Challenges
**1. Stability Problems:**  
Because of feedback among logic gates, asynchronous circuits may become **unstable** at times. The circuit might oscillate or enter undefined states, making design much more challenging.Digital-Design-With-an-Introduction-to-the-Verilog-HDL-5th-ED.pdf​

**2. Race Conditions:**  
A **race condition** occurs when multiple signals change simultaneously and the final state depends on which signal arrives first. For example:
- If two inputs change at "the same time" but one arrives nanoseconds before the other
- The circuit might enter different states depending on arrival order
- This makes outputs **uncertain** and **unreliable**

**3. Fundamental Mode vs. Pulse Mode:**  
Asynchronous circuits operate in two modes:
- **Fundamental mode**: Only one input changes at a time, and only when the circuit is stable
- **Pulse mode**: Inputs are pulses with specific timing requirements

| Feature            | Synchronous                                                                 | Asynchronous           |
| ------------------ | --------------------------------------------------------------------------- | ---------------------- |
| Clock Signal       | Required Digital-Design-With-an-Introduction-to-the-Verilog-HDL-5th-ED.pdf​ | Not used ​             |
| Speed              | Limited by clock​                                                           | Potentially faster     |
| Design Complexity  | Easier, predictable ​                                                       | More difficult​        |
| Output Reliability | More reliable                                                               | Uncertain, race-prone​ |
| State Changes      | Only at clock edges                                                         | Any time inputs change |
| Storage Elements   | Flip-flops                                                                  | Time-delay devices     |

# Analysis of Asynchronous Sequential Circuits
## STANDARD NOTATION:
- **Y**: Excitation variable (next state)
- **y**: Secondary variable (present state/feedback)
- **Y = y**: **STABLE STATE** (circuit is stable)
- **Y ≠ y**: **UNSTABLE STATE** (circuit will transition)

---
## 1. TRANSITION TABLE (Excitation Table)

A **transition table** shows:
- **y (present state)**: Current value of feedback variables
- **Y (next state)**: Excitation values (what state wants to become)
- **Inputs**: External signals
- **Outputs**: Circuit outputs

### Example 1: Simple Asynchronous Circuit
Circuit equation: **Y = x₁ + x₂y**

#### **Transition Table:**

|y (Present)|x₁x₂ = 00|x₁x₂ = 01|x₁x₂ = 10|x₁x₂ = 11|
|---|---|---|---|---|
|0|0|0|1|1|
|1|1|1|1|1|

**Reading:**
- When **y=0, x₁x₂=00**: Y=0 (stable, since Y=y)
- When **y=0, x₁x₂=10**: Y=1 (unstable, will transition to y=1)
- When **y=1, x₁x₂=00**: Y=1 (stable, since Y=y)

## 2. FLOW TABLE

A **flow table** marks stable states with **circles** and shows state names.

### Notation in Flow Tables:
- **Ⓐ, Ⓑ, Ⓒ** or **①, ②, ③**: **STABLE STATES** (Y = y)
- **A, B, C** or **1, 2, 3**: **UNSTABLE STATES** (Y ≠ y, transitioning)
- **-**: Impossible or don't care

### **Example 2: Flow Table from Transition Table**
Converting the previous example:
#### **Flow Table:**

| State (y) | x₁x₂ = 00 | x₁x₂ = 01 | x₁x₂ = 10 | x₁x₂ = 11 |
| --------- | --------- | --------- | --------- | --------- |
| a         | **Ⓐ**     | **Ⓐ**     | b         | b         |
| b         | a         | a         | **Ⓑ**     | **Ⓑ**     |

**State Assignment:**
- State **a**: y = 0
- State **b**: y = 1

**Analysis:**
- **Ⓐ (y=0, x₁x₂=00)**: Stable, Y=0=y ✓
- **From Ⓐ, x₁x₂→10**: Y=1, but y=0, so unstable → transition to state b
- **Ⓑ (y=1, x₁x₂=10)**: Stable, Y=1=y ✓

## 3. COMPLETE EXAMPLE: Two-Variable Circuit
### Given Circuit:
- **Y₁ = x₁y₂ + x₂y₁**
- **Y₂ = x₁x₂ + x₁y₁**

#### **Step 1: Create Transition Table**

| y₁y₂ (Present) | x₁x₂ = 00 | x₁x₂ = 01 | x₁x₂ = 10 | x₁x₂ = 11 |
| -------------- | --------- | --------- | --------- | --------- |
| 00             | **00** ✓  | 00        | 00        | 01        |
| 01             | 00        | **01** ✓  | 10        | 11        |
| 10             | 10        | 00        | **10** ✓  | 11        |
| 11             | 10        | 01        | 10        | **11** ✓  |

**✓ = Stable states** (where Y₁Y₂ = y₁y₂)

#### **Step 2: Create Flow Table**

| State | x₁x₂ = 00 | x₁x₂ = 01 | x₁x₂ = 10 | x₁x₂ = 11 | y₁y₂ |
| ----- | --------- | --------- | --------- | --------- | ---- |
| a     | **Ⓐ**     | a         | a         | b         | 00   |
| b     | a         | **Ⓑ**     | c         | d         | 01   |
| c     | c         | a         | **Ⓒ**     | d         | 10   |
| d     | c         | b         | c         | **Ⓓ**     | 11   |

## 4. RACE CONDITIONS (Proper Explanation)

A **race** occurs when **two or more y variables must change simultaneously**.

### Example: Race in State Transition
From the table above, look at: **State a (00) with x₁x₂ = 11**

- Present: y₁y₂ = 00
- Next: Y₁Y₂ = 01
- **Only y₂ changes** (0→1), **y₁ stays 0**
- **NO RACE** ✓

Now consider if we had: y₁y₂ = 00 → Y₁Y₂ = 11

- Both y₁ and y₂ must change!
- **RACE CONDITION! ** ⚠️

### Types of Races:

#### A. Non-Critical Race
**Definition:** Multiple variables change, but **all paths lead to the same stable state**.

**Example:**

| y₁y₂ | x=0 | x=1    |
| ---- | --- | ------ |
| 00   | 00  | **11** |
| 01   | 00  | **11** |
| 10   | 00  | **11** |
| 11   | 00  | **11** |

**Transition: 00 → 11 when x=1**

**Path 1:** 00 → 01 → 11 (y₂ changes first)  
**Path 2:** 00 → 10 → 11 (y₁ changes first)  
**Result:** Both reach **11** ✓ **NON-CRITICAL**

#### B. Critical Race
**Definition:** Different paths lead to **different stable states**.

**Example:**

| y₁y₂ | x=0 | x=1    |
| ---- | --- | ------ |
| 00   | 00  | **11** |
| 01   | 00  | **01** |
| 10   | 00  | **10** |
| 11   | 00  | **11** |

**Transition: 00 → 11 when x=1**

**Path 1:** 00 → 01 → **STUCK at 01** (stable!)  
**Path 2:** 00 → 10 → **STUCK at 10** (stable!)  
**Result:** Different final states! ❌ **CRITICAL RACE**

### **How to Avoid Critical Races:**
1. **State Assignment**: Make sure only **ONE variable changes** at a time
2. **Add intermediate states**
3. **Use proper encoding** (Gray code, one-hot, etc.)

## 5. PROPER ANALYSIS PROCEDURE

### **Given:** Circuit with equations

**Step 1:** Create **Transition Table**
- List all combinations of y (present state)
- Calculate Y (next state) for each input combination

**Step 2:** Identify **Stable States**
- Mark where **Y = y** with circles or checkmarks

**Step 3:** Create **Flow Table**
- Assign state names (a, b, c, ...)
- Show stable states with circles (Ⓐ, Ⓑ, Ⓒ, ...)

**Step 4:** Check for **Races**
- Find transitions where multiple bits change
- Classify as critical or non-critical

# SR Latch in Asynchronous Circuits
An SR latch is a fundamental bistable memory element in asynchronous sequential circuits with two inputs (S for Set and R for Reset) and two outputs (Q and Q'). The latch can be implemented using cross-coupled NOR gates or NAND gates, maintaining stable states without requiring a clock signal.[sathyabama+1](https://www.sathyabama.ac.in/sites/default/files/course-material/2020-10/unit5.PDF)​

## NOR-based SR Latch

The NOR SR latch operates with the excitation function Y=SR′+R′yY = SR' + R'yY=SR′+R′y where SR = 0 must be satisfied. The circuit has two cross-coupled NOR gates with feedback from output to input.[allaboutcircuits+1](https://www.allaboutcircuits.com/textbook/digital/chpt-10/s-r-latch/)​

**Transition Table for NOR SR Latch:**

|**SR**|**y=0**|**y=1**|**Output Q**|
|---|---|---|---|
|00|0|1|Hold|
|01|0|0|Reset (Q=0)|
|10|1|1|Set (Q=1)|
|11|0|0|Invalid|

**Excitation Table:**

|**Present State Q**|**Next State Q***|**S**|**R**|
|---|---|---|---|
|0|0|0|X|
|0|1|1|0|
|1|0|0|1|
|1|1|X|0|

The stable states occur when Y = y: SR = 10 gives Q = 1 (set), and SR = 01 gives Q = 0 (reset).[ranger.uta+1](https://ranger.uta.edu/~carroll/cse2341/summer99/html%20files/chapter_6/tsld017.htm)​

## NAND-based SR Latch

The NAND SR latch operates with inverted logic, using excitation function Y=S′+RyY = S' + RyY=S′+Ry where S'R' = 0 must be satisfied. Both inputs normally remain at logic 1, and applying 0 to either input changes the state.[sathyabama](https://www.sathyabama.ac.in/sites/default/files/course-material/2020-10/unit5.PDF)​

## Race Conditions in SR Latch

A race condition occurs when two mutually-exclusive events are simultaneously initiated through different circuit elements by a single cause. In the SR latch, the problematic race condition happens when both S and R transition from 1 to 0 simultaneously.[reddit+1](https://www.reddit.com/r/FPGA/comments/17p8vo5/what_in_the_world_is_race_condition/)​youtube​

**Why SR = 11 → 00 causes race:**  
When both inputs are 1, both outputs Q and Q' become 0 (violating the complement requirement). When both inputs then change to 0, the circuit behavior becomes unpredictable depending on which gate responds faster—if S reaches 0 first, Q remains 0; if R reaches 0 first, Q becomes 1. This creates oscillation if both gates have identical timing characteristics.youtube​[allaboutcircuits+1](https://www.allaboutcircuits.com/textbook/digital/chpt-10/s-r-latch/)​

**Avoiding Race Conditions:**

- **Design constraint:** Ensure SR = 0 (for NOR) or S'R' = 0 (for NAND) at all times through proper input logic[sathyabama](https://www.sathyabama.ac.in/sites/default/files/course-material/2020-10/unit5.PDF)​
    
- **Time-delay method:** Insert deliberate delays to give one signal a clear advantage, ensuring predictable outcomes[allaboutcircuits](https://www.allaboutcircuits.com/textbook/digital/chpt-10/s-r-latch/)​
    
- **Race-free state assignment:** Use extra intermediate states with adjacent binary encodings to prevent multiple bit changes during transitions[sathyabama](https://www.sathyabama.ac.in/sites/default/files/course-material/2020-10/unit5.PDF)​
    

## Circuit with Built-in SR=0 Constraint

A gated D-latch uses two SR latches with inherent SR = 0 protection through complementary inputs. The circuit has inputs G (gate) and D (data), generating S and D signals where S and R are never simultaneously 1.[sathyabama](https://www.sathyabama.ac.in/sites/default/files/course-material/2020-10/unit5.PDF)​

**Circuit Implementation:**

- Latch 1: S₁ = DG, R₁ = D'G (where D' is complement of D)
    
- Latch 2: Receives outputs from Latch 1
    
- Since D and D' are complements, S₁R₁ = (DG)(D'G) = 0 always[sathyabama](https://www.sathyabama.ac.in/sites/default/files/course-material/2020-10/unit5.PDF)​
    

**Transition Table for Gated D-Latch:**

|**DG**|**y=0**|**y=1**|
|---|---|---|
|00|0|1|
|01|0|0|
|10|0|1|
|11|1|1|

The reduced excitation function is Y=DC+C′yY = DC + C'yY=DC+C′y, where the circuit naturally prevents the invalid SR = 11 condition through input logic design.

# Debounce Circuit
The circuit in your image demonstrates a **switch debouncing technique using an SR latch** to eliminate mechanical contact bounce.image.jpg​

## Circuit Components

The debounce circuit consists of:

- **Single-pole, double-throw (SPDT) switch** with two positions: A and B
    
- **Two cross-coupled NAND gates** forming an SR latch
    
- **Ground connection** providing logic 0 reference
    
- **Pull-up resistors** (implicit) providing logic 1 when contacts are open
    

## How It Works

**Position A (Initial State):**

- Contact A connects to ground → S = 0, R = 1 (floating, pulled high)
    
- SR latch output: Q = 0, Q' = 1
    
- The latch is in RESET stateimage.jpg​
    

**Switching from A to B:**  
When the switch moves to position B, mechanical bounce occurs—the contact vibrates, making and breaking connection multiple times over several milliseconds. However:image.jpg​

1. **Initial transition:** Contact A opens (S becomes 1), contact B closes (R becomes 0)
    
2. **During bounce:** Even though contact B bounces repeatedly, the SR latch **holds its state** because:
    
    - When B bounces open: R goes to 1 (floating), S stays at 1 → SR = 11 (hold condition for NAND latch)
        
    - The latch maintains Q = 1, Q' = 0 through internal feedbackimage.jpg​
        
3. **Output remains stable:** Q' stays at 1 and Q stays at 0 throughout the bounce period, producing a **single clean transition**image.jpg​
    

## Key Principle

The SR latch acts as a **memory element** that latches onto the first valid transition and ignores subsequent bounces. Since one contact opens before the other closes, there's always one input at logic 0, preventing the invalid SR = 00 condition (which would be SR = 11 in the original NOR latch notation).image.jpg​

**Timing Diagram Analysis:**  
The waveforms show that despite the mechanical bouncing at positions A and B, the outputs Q and Q' transition cleanly with single, smooth edges—eliminating the oscillations that would occur with a direct switch connection.image.jpg​

This circuit is particularly effective because **no timing components (RC networks) are required**—the latch's bistable nature inherently filters the bounce

![[Pasted image 20251211015907.png]]


# Asynchronous Sequential Circuit Design Procedure

### **Example Problem:** Design a circuit that detects the sequence "01" on input line X

## STEP 1: Obtain a Primitive Flow Table
A **primitive flow table** shows: 
- **Stable states** (circled) - where the circuit "rests"
- **Unstable states** (not circled) - temporary transitions
- Only ONE stable state per row

**Rules/Assumptions for Fundamental Mode:**
1. Only one input changes at a time
2. System stabilizes before next input can change

### Building the Primitive Flow Table:
Let's trace through what happens: 

**State a:** Initial state, X=0
- If X stays 0 → stay in state **a** (stable)
- If X changes to 1 → move to state **b**

**State b:** Saw "0" then "1" 
- Output = 1 (sequence detected!)
- If X stays 1 → stay in state **b** (stable)
- If X returns to 0 → move to state **c**

**State c:** Back to X=0 after detecting sequence
- If X stays 0 → stay in state **c** (stable)
- If X changes to 1 → move to state **d**

**State d:** Saw "01" again
- Output = 1
- If X stays 1 → stay in state **d** (stable)
- If X returns to 0 → move to state **c**

### Primitive Flow Table: 

```
┌───────┬─────────────┬─────────────┬────────┐
│ State │   X = 0     │   X = 1     │ Output │
├───────┼─────────────┼─────────────┼────────┤
│   a   │    ⓐ        │     b       │   0    │
│   b   │     c       │    ⓑ        │   1    │
│   c   │    ⓒ        │     d       │   0    │
│   d   │     c       │    ⓓ        │   1    │
└───────┴─────────────┴─────────────┴────────┘
```

**Ⓐ = stable state (circled)**  
**a = unstable state (will transition)**

---
## STEP 2: Reduce the Flow Table
We want to **minimize states** to reduce hardware.  We merge **compatible states**. 

### What Makes States Compatible? 
Two states are **compatible** if and only if:

#### Condition 1: Same Output
For every input combination, they produce the **same output** whenever both outputs are specified.

#### Condition 2: Compatible Next States
For every input combination, their **next states** must also be compatible (or the same state, or unspecified).

##### Example 1: States with Same Output BUT NOT Compatible
```
┌───────┬─────────┬─────────┬────────┐
│ State │  X = 0  │  X = 1  │ Output │
├───────┼─────────┼─────────┼────────┤
│   a   │   ⓐ     │    c    │   0    │  ← Output is 0
│   b   │   ⓑ     │    d    │   0    │  ← Output is 0 (SAME!)
└───────┴─────────┴─────────┴────────┘
```

**Question:** Are states **a** and **b** compatible?
**Your first thought:** "Yes! Both have output = 0"
**But wait...** Let's check their **next states**:

- When X=0:
    - State **a** goes to **a** (stays)
    - State **b** goes to **b** (stays)
    - Next states are **a** and **b** (compatible if a and b are compatible ✓)
- When X=1:
    - State **a** goes to **c**
    - State **b** goes to **d**
    - **Are c and d compatible?** We need to check!

```
┌───────┬─────────┬─────────┬────────┐
│ State │  X = 0  │  X = 1  │ Output │
├───────┼─────────┼─────────┼────────┤
│   c   │    a    │   ⓒ     │   1    │  ← Output is 1
│   d   │    b    │   ⓓ     │   0    │  ← Output is 0
└───────┴─────────┴─────────┴────────┘
```

**States c and d have DIFFERENT outputs** (1 vs 0)!

**Therefore:** c and d are **NOT compatible** **Which means:** a and b are **NOT compatible** (even though they have the same output!)

### Using an Implication Table:
```
      a    b    c
   ┌────┬────┬────┐
 b │ ✗  │    │    │  (different outputs:  0 vs 1)
   ├────┼────┼────┤
 c │ ✓  │ ✗  │    │  (a,c compatible:  same output, next states ok)
   ├────┼────┼────┤
 d │ ✗  │ ✓  │ ✗  │  (b,d compatible: both output 1, both go to c)
   └────┴────┴────┘
```

### Compatible Pairs:
- **(a, c)** - both output 0, both can coexist
- **(b, d)** - both output 1, both can coexist

### Reduced Flow Table: 
Merge a+c → **State A**  
Merge b+d → **State B**

```
┌───────┬─────────────┬─────────────┬────────┐
│ State │   X = 0     │   X = 1     │ Output │
├───────┼─────────────┼─────────────┼────────┤
│   A   │    Ⓐ        │     B       │   0    │
│   B   │     A       │    Ⓑ        │   1    │
└───────┴─────────────┴─────────────┴────────┘
```

**Great!** We reduced from 4 states to 2 states! 

---
## STEP 3: State Assignment (Race-Free)
Now we assign **binary codes** to states.  This is **CRITICAL** for asynchronous circuits!

### The Race Problem: 
Imagine state variables are **y₂y₁**: 
- State A = 00
- State B = 11

**Problem:** Transitioning from 00→11 means BOTH bits change! 
- Path 1: 00 → 01 → 11
- Path 2: 00 → 10 → 11

**Which path?** We can't predict!  This is a **CRITICAL RACE**

### Solution: Adjacent Assignments
**Adjacent** = Only ONE bit changes

For 2 states, assign:
- **State A = 0** (using y₁)
- **State B = 1** (using y₁)

### Transition Table: 
```
┌─────┬───────────────┬───────────────┬────────┐
│  y₁ │   X = 0       │   X = 1       │ Output │
│     │  Y₁ (next)    │  Y₁ (next)    │   Z    │
├─────┼───────────────┼───────────────┼────────┤
│  0  │   ⓪ (0)       │    1          │   0    │
│  1  │    0          │   ① (1)       │   1    │
└─────┴───────────────┴───────────────┴────────┘
```

**Circled states** = stable states

---
## **STEP 4: Output Assignment**
Handle **don't-care outputs** in unstable states to avoid **output glitches**.

### Example with 3 states:

```
Original:
┌─────┬─────────┬─────────┬────────┐
│  y₁ │  X = 0  │  X = 1  │   Z    │
├─────┼─────────┼─────────┼────────┤
│  0  │   ⓪     │    1    │   0    │
│  1  │    0    │   ①     │   -    │  ← don't care! 
└─────┴─────────┴─────────┴────────┘
```

**Assign the don't-care (-):**
- If we want to avoid glitches, make it **0** so output doesn't spike during transition

**Better:**
```
┌─────┬─────────┬─────────┬────────┐
│  y₁ │  X = 0  │  X = 1  │   Z    │
├─────┼─────────┼─────────┼────────┤
│  0  │   ⓪     │    1    │   0    │
│  1  │    0    │   ①     │   0    │  ← assigned! 
└─────┴─────────┴─────────┴────────┘
```

---
## **STEP 5: Derive Logic Diagram**
From the transition table, derive Boolean functions: 

### From our example: 
**Next state function:**
```
Y₁ = X·y₁ + X·y₁'
Y₁ = X
```

**Output function:**
```
Z = y₁
```

### Circuit Implementation:

```
     X ────┐
           │
         ┌─▼─┐
         │ Y │ (feedback from latch output)
         └─┬─┘
           │
           ▼
      ┌────────┐
      │ LATCH  │
      │  (SR)  │
      └───┬────┘
          │
          ▼ y₁
          │
          ├──── Z (output)
```

---
## Advanced Topics:  Fixing Race Conditions
### Types of Races:

#### 1. **Non-Critical Race** (OK)
Both paths lead to same final state: 

```
State 00 → 11
Path A: 00 → 01 → 11 ✓
Path B: 00 → 10 → 11 ✓
Final: Always reach 11
```

#### 2. **Critical Race** (BAD)
Different paths lead to different states:

```
State 00 → 11
Path A: 00 → 01 → 11 (intended state)
Path B: 00 → 10 → 10 (wrong stable state!)
```

### Solution Methods:
#### **Method 1: Shared-Row (Adding Cycles)**
Add intermediate states: 
```
Original problem:
┌─────┬─────────┬─────────┐
│ y₂y₁│  X = 0  │  X = 1  │
├─────┼─────────┼─────────┤
│ 00  │   ⓪⓪   │    11   │  ← RACE!
│ 11  │    00   │   ①①   │
└─────┴─────────┴─────────┘
```

**Add intermediate state:**

```
Fixed: 
┌─────┬─────────┬─────────┐
│ y₂y₁│  X = 0  │  X = 1  │
├─────┼─────────┼─────────┤
│ 00  │   ⓪⓪   │    01   │  ← only y₁ changes
│ 01  │    00   │    11   │  ← only y₂ changes
│ 11  │    01   │   ①①   │
└─────┴─────────┴─────────┘

Transition:  00 → 01 → 11 (controlled path!)
```

#### **Method 2: Multiple-Row**
Give each state multiple binary codes:
```
State A: can be 00 or 01
State B:  can be 10 or 11

This ensures any transition has an adjacent option
```

---
## Hazards:  The Hidden Enemy
### What is a Hazard?
A **momentary glitch** in output due to unequal gate delays. 

### Example of Static-1 Hazard:
**Function:** F = AB + A'C  
**Input change:** A goes from 1→0, while B=1, C=1

**Expected:** F should stay 1 (because C=1)

**Reality:**
```
Time:   0    1    2    3
       │    │    │    │
AB  ───┘    └────────────  (AB term goes to 0)
A'C ──────────────┌──────  (A'C term delayed!)
F   ───────┐  ┌───────────  ⚠️ Glitch!
           └──┘
```

### Fix: Add Redundant Term
**Fixed function:** F = AB + A'C + **BC**

The BC term "bridges" the gap: 

```
Time:  0    1    2    3
       │    │    │    │
AB  ───┘    └────────────  
BC  ──────────────────────  (keeps F=1!)
A'C ──────────────┌──────  
F   ──────────────────────  ✓ No glitch!
```

### On a K-map:
```
Original (has hazard):
     C
   ┌───┬───┐
A  │ 1 │ 1 │  AB covers these
   ├───┼───┤
A' │ 1 │ 1 │  A'C covers these
   └───┴───┘
    Gap between terms causes hazard! 

Fixed (redundant term):
     C
   ┌───┬───┐
A  │ 1 │ 1 │  
   ├───┼───┤
A' │ 1 │ 1 │  
   └───┴───┘
     BC overlaps both! 
```

---
## Using SR Latches to Avoid Hazards

**SR latches are naturally immune** to momentary glitches: 

```
NOR Latch: 
- Momentary 0 on S or R → NO EFFECT
- Only sustained signals change state

NAND Latch: 
- Momentary 1 on S or R → NO EFFECT
- Only sustained signals change state
```

### Example SR Latch Implementation:
```
From: Y₁ = S + R'·y₁

Derive: 
S = (terms that should SET y₁)
R = (terms that should RESET y₁)

     S ────┐
           ├──┐
           │  │
     R ────┤  ├──── Y₁
           │  │
           └──┘
          SR NOR
          LATCH
```

---

## Complete Example: Arbiter Circuit

**Problem:** Design a circuit that grants access to two requestors (R1, R2) one at a time.

### Step 1: Primitive Flow Table

```
┌───────┬─────────┬─────────┬─────────┬─────────┬─────────┐
│ State │ R1R2=00 │ R1R2=01 │ R1R2=11 │ R1R2=10 │Out(G1G2)│
├───────┼─────────┼─────────┼─────────┼─────────┼─────────┤
│   a   │   ⓐ     │    b    │    d    │    c    │   00    │
│   b   │    a    │   ⓑ     │    d    │    -    │   01    │
│   c   │    a    │    -    │    d    │   ⓒ     │   10    │
│   d   │    a    │    b    │   ⓓ     │    c    │   11    │
└───────┴─────────┴─────────┴─────────┴─────────┴─────────┘
```

### Step 2: Merge Compatible States

After analysis: 
- States a (no compatible merges)
- States b, d can merge (both grant access)

(In practice you'd do full implication table)

### Step 3: Race-Free Assignment

With 3-4 states, use 2 bits: 
```
State a: 00
State b: 01 (adjacent to a)
State c: 10 (adjacent to a)
State d: 11 (adjacent to b and c)
```

### Step 4: Derive Excitation Functions

```
Y₂ = R1·R2 + R2·y₁ + R1·y₂
Y₁ = R2·R1' + R2·y₁ + ... 

G1 = y₂
G2 = y₁
```

### Step 5: Implement with SR Latches

```
          ┌──────┐
R1,R2 ───→│  S1  │
     ───→│Latch1│───→ Y₂ → G1
y₁,y₂ ───→│  R1  │
          └──────┘
          ┌──────┐
R1,R2 ───→│  S2  │
     ───→│Latch2│───→ Y₁ → G2
y₁,y₂ ───→│  R2  │
          └──────┘
```

## Methods to avoid critical race condition
You're right, I apologize for the oversight!  I did cover the **Methods for Race-Free Assignment** in my original explanation, but let me make sure they're **crystal clear** and properly highlighted.

---
## Methods for Race-Free Assignment

When you can't directly assign adjacent binary codes to all states that need transitions between them, you have **two main methods** to fix this:

---

### **Method 1: Shared-Row Method (Adding Cycles)**

**Concept:** Add **extra intermediate rows** (unstable states) to create a controlled path between non-adjacent states. 

#### The Problem: 

```
Need transition from state 00 → 11

But 00 and 11 differ in TWO bits! 
  00
   ↓  (which bit changes first?  UNPREDICTABLE!)
  11

This is a RACE CONDITION! 
```

#### The Solution - Create a Cycle:

Add an **intermediate unstable state** to force a specific path:

```
Original (RACE):
┌───────┬─────────┬─────────┐
│ y₂y₁  │  X = 0  │  X = 1  │
├───────┼─────────┼─────────┤
│  00   │   ⓪⓪   │   11    │  ← 2 bits change = RACE! 
│  11   │   00    │   ①①   │
└───────┴─────────┴─────────┘

Fixed with Shared-Row (adding row 01):
┌───────┬─────────┬─────────┐
│ y₂y₁  │  X = 0  │  X = 1  │
├───────┼─────────┼─────────┤
│  00   │   ⓪⓪   │   01    │  ← only y₁ changes
│  01   │   00    │   11    │  ← only y₂ changes (intermediate)
│  11   │   01    │   ①①   │  ← only y₁ changes
└───────┴─────────┴─────────┘

Controlled Path:   00 → 01 → 11
                      ↑
              (cycle through unstable state)
```

#### Visual Representation:

```
        BEFORE (Race)              AFTER (Cycle)
        
           00                          00
            ↓ ?                          ↓
           11                          01 (unstable)
      (unpredictable)                   ↓
                                       11
                                  (predictable!)
```

**Key Point:** The extra row (01) is **never stable** for this transition - it's just a "stepping stone"!

---

### **Method 2: Multiple-Row Method**

**Concept:** Assign **multiple binary codes** to each original state, so every state has an adjacent code to transition to.

#### The Problem:

```
3 states need transitions between ALL of them: 

    A ←→ B
    ↑     ↑
    └──→ C ←┘

With 2 bits, we have codes: 00, 01, 10, 11

If:   A = 00, B = 01, C = 11

Then: A→C requires 00→11 (2 bits change = RACE!)
```

#### The Solution - Multiple Codes Per State:

```
Assign TWO codes to some states:

State A: 00
State B: 01 OR 11  (two equivalent codes!)
State C: 10

Now check transitions:
  A→B: 00→01 ✓ (1 bit)
  A→C: 00→10 ✓ (1 bit)  
  B→A: 01→00 ✓ (1 bit)
  B→C: 11→10 ✓ (1 bit)  ← use the 11 code for B! 
  C→A: 10→00 ✓ (1 bit)
  C→B: 10→11 ✓ (1 bit)

ALL transitions are adjacent!
```

#### Flow Table with Multiple-Row: 

```
┌───────┬─────────────┬─────────────┬─────────────┐
│ y₂y₁  │   Input 00  │   Input 01  │   Input 10  │
├───────┼─────────────┼─────────────┼─────────────┤
│  00   │     Ⓐ       │     01      │     10      │  A
├───────┼─────────────┼─────────────┼─────────────┤
│  01   │     00      │     Ⓑ       │     11      │  B (code 1)
├───────┼─────────────┼─────────────┼─────────────┤
│  11   │     01      │     Ⓑ       │     10      │  B (code 2)
├───────┼─────────────┼─────────────┼─────────────┤
│  10   │     00      │     11      │     Ⓒ       │  C
└───────┴─────────────┴─────────────┴─────────────┘

States 01 and 11 are EQUIVALENT (both represent state B)
```

---

## 📊 Comparison of Methods

| Aspect | Shared-Row Method | Multiple-Row Method |
|--------|-------------------|---------------------|
| **Approach** | Add intermediate unstable states | Give states multiple binary codes |
| **Extra Rows** | Yes, for transitions | Yes, for equivalent states |
| **Complexity** | Simpler to understand | More flexible |
| **When to Use** | Few problematic transitions | Many interconnected states |
| **Transition Speed** | Slower (goes through extra states) | Faster (direct but redundant) |

---
## Complete Example:  Both Methods

### Problem:  4 states with these required transitions: 

```
State diagram:
    A ←→ B
    ↑     ↓
    D ←── C
```

### Attempt Direct Assignment: 

```
A = 00
B = 01  (adjacent to A ✓)
C = 11  (adjacent to B ✓)
D = 10  (adjacent to A ✓)

Check all transitions:
  A↔B: 00↔01 ✓
  B→C: 01→11 ✓
  C→D: 11→10 ✓
  D→A: 10→00 ✓

All adjacent! No race-free fix needed! 
```

### But what if we needed A↔C directly?

```
A = 00, C = 11  → 00↔11 = 2 bits = RACE! 

Fix with Shared-Row: 
Add intermediate:  00 → 01 → 11

Fix with Multiple-Row: 
A = 00 or 10
C = 11 or 01
Use:  A(00)→C(01) = 1 bit ✓
```


# Memory in Digital Systems 
There are two types of memories that are used in digital systems: 
- **Random‐access memory (RAM)** 
	- RAM stores new information for later use.
	- A memory unit is a collection of storage cells, together with associated circuits needed to transfer information into and out of a device. The architecture of memory is such that information can be selectively retrieved from any of its internal locations.
	- The time it takes to transfer information to or from any desired random location is always the same—hence the name random‐access memory, abbreviated RAM.
	
- **Read‐only memory (ROM)**.
	ROM is a programmable logic device (PLD)

The process of storing new information into memory is referred to as a memory *write* operation The process of transferring the stored information out of memory is referred to as a memory *read* operation. 
	- RAM can perform both write and read operations. 
	- ROM can perform only the read operation.

## word
A memory unit stores binary information in groups of bits called **words**.
A **word** in memory is an entity of bits that move in and out of storage as a unit. A memory word is a group of 1’s and 0’s and may represent a number, an instruction, one or more alphanumeric characters, or any other binary‐coded information.

![[Pasted image 20251211155750.png]]

Communication between memory and its environment is achieved through *data input* and *output lines*, *address selection lines*, and *control lines* that specify the direction of transfer.
- The **n data input lines** provide the information to be stored in memory
- The **n data output lines** supply the information coming out of memory
- The **k address lines** specify the particular word chosen among the many available.
- The **two control inputs** specify the direction of transfer desired:
	- The *Write* input causes binary data to be transferred into the memory
	- The *Read* input causes binary data to be transferred out of memory

### Address lines and Size of a words 

Each word in memory is assigned an identification number, called an address, starting from 0 up to $2^k - 1$, where k is the number of address lines.

Memories vary greatly in size and may range from 1,024 words, requiring an address of 10 bits, to 232 words, requiring 32 address bits. 
It is customary to refer to the number of words (or bytes) in memory with one of the letters:
- K (kilo), 
	- K is equal to $2^{10}$, 
- M (mega),
	- M is equal to $2^{20}$,
- G (giga). 
	- G is equal to $2^{30}$

Thus, $64K = 2^{16}$, $2M = 2^{21}$, and $4G = 2^{32}$.

![[Pasted image 20251211161240.png]]

- The **access time** of memory is the time required to select a word and read it 
- The **cycle time** of memory is the time required to complete a write operation. 
The CPU must provide the memory control signals in such a way as to synchronize its internal clocked operations with the read and write operations of memory. This means that the access time and cycle time of the memory must be within a time equal to a fixed number of CPU clock cycles.

For Example:
Suppose as an example that a CPU operates with a clock frequency of 50 MHz, giving a period of 20 ns for one clock cycle. 
Suppose also that the CPU communicates with a memory whose access time and cycle time do not exceed 50 ns. 

This means that the write cycle terminates the storage of the selected word within a 50‐ns interval and that the read cycle provides the output data of the selected word within 50 ns or less. (The two numbers are not always the same.) 
Since the period of the CPU cycle is 20 ns, it will be necessary to devote at least two‐and‐a-half, and possibly three, clock cycles for each memory request.

![[Pasted image 20251211165733.png]]

![[Pasted image 20251211165739.png]]

## Types of memories
The mode of access of a memory system is determined by the type of components used.
In RAM, memory can be thought of like a collection of space, where each space is accessible at any time without any delay or variability, i.e., has a fixed amount of access time. 

While in something like a magnetic disk, it is a sequential‐access memory, the information stored in some medium is not immediately accessible, but is available only at certain intervals of time.  Each memory location passes the read and write heads in turn, but information is read out only when the requested word has been reached.

Integrated circuit RAM units are available in two operating modes:
- **Static random access memory (SRAM)**
	- Static RAM (SRAM) consists essentially of internal latches that store the binary information.
	- The stored information remains valid as long as power is applied to the unit.
- **Dynamic random access memory (DRAM)**
	- Dynamic RAM (DRAM) stores the binary information in the form of electric charges on capacitors provided inside the chip by MOS transistors. 
	- The stored charge on the capacitors tends to discharge with time, and the capacitors must be periodically recharged by refreshing the dynamic memory.
	- Refreshing is done by cycling through the words every few milliseconds to restore the decaying charge.

*DRAM offers reduced power consumption and larger storage capacity in a single memory chip. SRAM is easier to use and has shorter read and write cycles.*

### Volatile and non-volatile memory units

Memory units that lose stored information when power is turned off are said to be **volatile**.
	CMOS integrated circuit RAMs, both static and dynamic, are of this category, since the binary cells need external power to maintain the stored information.

In contrast, a **nonvolatile memory**, such as magnetic disk, retains its stored information after the removal of power. This type of memory is able to retain information because the data stored on magnetic components are represented by the direction of magnetization, which is retained after power is turned off.
	ROM is another nonvolatile memory. 
	A nonvolatile memory enables digital computers to store programs that will be needed again after the computer is turned on.

## Memory Decoding
In addition to requiring a storage component, there is a need for a unit that is able to retrieve the memory at the specified memory address called memory decoders.
To demonstrate its working, we will construct a 16 bit RAM which works on 4 words of 4 bits each.

### Internal construction of RAM
An $m$ word ram with $n$ bits each word require $m \times n$ binary storage cells and an internal decoding circuit to for selecting individual words. 
We are using a SR Latch modeled as a D Latch with *Read/Write* and *Select* along with the *Input* for a particular storage cell of our RAM:
![[Pasted image 20251211172315.png]]
- The binary cell stores one bit in its internal latch.
- The select input enables the cell for reading or writing,
- and the read/write input determines the operation of the cell when it is selected.
	- A 1 in the read/write input provides the read operation by forming a path from the latch to the output terminal. 
	- A 0 in the read/write input provides the write operation by forming a path from the input terminal to the latch.

Following the conventions and logics we saw earlier about a RAM that 4 word RAM should require 2 bits address line length, here is the logic diagram of our 16 bit RAM:

![[Pasted image 20251211172816.png]]

The two address inputs go through a 2 * 4 decoder to select one of the four words. The decoder is enabled with the memory‐enable input.
	- When the memory enable is 0, all outputs of the decoder are 0 and none of the memory words are selected. 
	- With the memory select at 1, one of the four words is selected, dictated by the value in the two address lines.

### Coincident Decoding 
A decoder with $k$ inputs and $2^k$ outputs requires $2^k$ AND gates with $k$ inputs per gate.
The total number of gates and the number of inputs per gate can be reduced by employing two decoders in a two‐dimensional selection scheme.

The baisic idea is to represent a word as a location on a 2D Matrix. This can be done by splitting a memory address of length n in two halfs, i.e., n/2 MSB representing X coordinate, and later n/2 bits representing Y coordinate.
This would require us to use two $\frac{k}{2}$ decoders instead of one k decoder, reducing number of gates and space required exponentially.

For Example:
Instead of using a single 10 * 1,024 decoder, we use two 5 * 32 decoders.
With the single decoder, we would need 1,024 AND gates with 10 inputs in each. In the two-decoder case, we need 64 AND gates with 5 inputs in each.

consider the word whose address is 404. The 10‐bit binary equivalent of 404 is 01100 10100. This makes X = 01100 (binary 12) and Y = 10100 (binary 20).

![[Pasted image 20251211174730.png]]

### Address Multiplexing
The SRAM memory cell typically contains six transistor. In order to build memories with higher density, it is necessary to reduce the number of transistors in a cell.

The DRAM cell contains a single MOS transistor and a capacitor. The charge stored on a capacitor discharges with time and the memory cells must be periodically recharged by refreshing the memory. Because of their simple cell structure., DRAMs typically have 4 times the density of SRAMs.

Because of their large capacity, the address decoding of DRAMs is arranged in a two‐dimensional array, and larger memories often have multiple arrays.
We will use a 64K‐word memory to illustrate the address‐multiplexing idea.

![[Pasted image 20251211180243.png]]

The memory consists of a two‐dimensional array of cells arranged into 256 rows by 256 columns, for a total of $2^8 * 2^8 = 2^{16} = 64K$ words.
There is: 
- a single data input line, 
- a single data output line, and 
- a read/write control, 
- as well as an eight‐bit address input and two address strobes, the latter included for enabling the row and column address into their respective regiters. 
	- The row address strobe (RAS) enables the eight‐bit row register, and 
	- the column address strobe (CAS) enables the eight‐bit column register. 
The bar on top of the name of the strobe symbol indicates that the registers are enabled on the zero level of the signal.

Initially, both strobes are in the 1 state. The 8‐bit row address is applied to the address inputs an RAS is changed to 0. 
This loads the row address into the row address register. RAS also enables the row decoder so that it can decode the row address and select one row of the array. After a time equivalent to the settling time of the row selection, RAS goes back to the 1 level.
The 8‐bit column address is then applied to the address inputs, and CAS is driven to the 0 state.This transfers the column address into the column register and enables the column decoder. 

Now the two parts of the address are in their respectiv registers, the decoders have decoded them to select the one cell corresponding to the row and column address, and a read or write operation can be performed on that cell. CAS must go back to the 1 level before initiating another memory operation.


## ROM
A Read-Only Memory (ROM) is a **memory device in which permanent binary information is stored**. This information is specified by the designer and then embedded in the unit to form a fixed interconnection pattern. Because the pattern is established and remains fixed even when power is removed (making it nonvolatile memory),, a ROM can perform only the **read operation**; the stored data cannot be altered by writing. The ROM is classified as a **programmable logic device (PLD)**.

### ROM Architecture and Implementation

A ROM is essentially a unit that implements a combinational circuit. It is defined by its number of words and the number of bits per word.

- **Inputs and Outputs:** A ROM requires $k$ address input lines to specify up to $2^k$ words. The outputs supply the data bits of the selected word. For example, a $32 \times 8$ ROM requires five input lines ($2^5 = 32$) and contains 32 words of 8 bits each. ROMs do not have data inputs because they lack a write operation.
- **Internal Logic:** The internal construction of a ROM consists of a **decoder** and a set of **OR gates**. A $2^k \times n$ ROM utilizes a $k \times 2^k$ decoder, where $k$ is the number of address lines and $n$ is the number of output bits per word.
- **Programming (Crosspoints):** The outputs of the decoder are connected to the inputs of the $n$ OR gates. This results in $2^k$ inputs to each OR gate, totaling $2^k \times n$ internal connections. These intersections are **programmable crosspoints**. Programming involves altering these connections (often through fuse links) to be either **closed (1)** or **open (0)** according to the required function.

### ROM Design and Programming

When a combinational circuit is implemented using a ROM, the internal operation can be interpreted such that each output terminal represents a **Boolean function expressed as a sum of minterms**.

- **Design Procedure:** The primary step in designing a combinational circuit using a ROM is to specify the necessary **truth table**. This table must contain all the information needed for programming the device.
- **Example:** If you wanted to design a circuit that accepts a three-bit input number and outputs the binary equivalent of the square of that number, the necessary ROM must be large enough to accommodate the inputs and outputs.

### Types of ROMs

ROM devices are classified primarily by the manufacturing or programming method employed:

1. **Mask Programming:** This is an expensive and irreversible process performed by the **manufacturer** during fabrication, based on a custom mask corresponding to the customer's truth table. This method is typically economical only for large quantities.
2. **Programmable Read-Only Memory (PROM):** These devices are programmed by the **user** in the laboratory. They arrive with all fuses intact (representing all 1s), and programming involves blowing specific fuse links (irreversible).
3. **Erasable PROM (EPROM):** EPROMs can be erased and reprogrammed. Exposure to **ultraviolet light** discharges the internal floating gates, restructuring the unit to its initial state.
4. **Electrically Erasable PROM (EEPROM or E2PROM):** Similar to EPROM, but erasure is achieved using an **electrical signal**, often allowing in-circuit reprogramming.
5. **Flash Memory:** These are nonvolatile devices similar to EEPROMs, allowing electrical erasure, typically in large blocks.
