**Expected to know:** [[Introduction to Programming]] (till now)
**Topics Covered:** What are Operators, Assignment Operator, Logical Operator
**Tags:** [[Introduction to Programming]]

An operator is a symbol (or combination of symbols) that is used with variables and values to simplify how you write certain program expressions.
Usually, operators are designed to mimic mathematical notation —but do not be fooled into confusing programming and mathematics! 
Operators are special symbols that perform operations on variables and values. They can be classified into different categories.

---
## Different Types of Operators
### (i) Arithmetic Operators
Used for mathematical calculations.

| Operator | Description         | Example (in C)                           |
| -------- | ------------------- | ---------------------------------------- |
| `+`      | Addition            | `int sum = 5 + 3; // sum = 8`            |
| `-`      | Subtraction         | `int diff = 5 - 3; // diff = 2`          |
| `*`      | Multiplication      | `int product = 5 * 3; // product = 15`   |
| `/`      | Division            | `int quotient = 10 / 2; // quotient = 5` |
| `%`      | Modulus (Remainder) | `int rem = 10 % 3; // rem = 1`           |
**Note:** In C, dividing two integers truncates(ignores) the decimal part.
So if we are doing 5/2, where both 5 and 2 are integers, then we get the output as 2, not 2.5.

---
### (ii) Relational (Comparison) Operators
Used for comparing values (returns `1` for true, `0` for false).

| Operator | Description              | Example (in C)          |
| -------- | ------------------------ | ----------------------- |
| `==`     | Equal to                 | `(5 == 3) // false (0)` |
| `!=`     | Not equal to             | `(5 != 3) // true (1)`  |
| `>`      | Greater than             | `(5 > 3) // true (1)`   |
| `<`      | Less than                | `(5 < 3) // false (0)`  |
| `> = `   | Greater than or equal to | `(5 >= 5) // true (1)`  |
| `< =`    | Less than or equal to    | `(5 <= 3) // false (0)` |
**Note:** Returns `1` (true) or `0` (false).

---
### (iii) Logical Operators
Used to combine multiple conditions.

| Operator | Description | Example (in C)                 |
| -------- | ----------- | ------------------------------ |
| `&&`     | Logical AND | `(5 > 3 && 8 > 6) // true (1)` |
| \|\|     | Logical OR  | (5>3\|\|6>8) //true(1)         |
| `!`      | Logical NOT | `!(5 > 3) // false (0)`        |

🔹 **Note:**
- `&&` returns true only if **both** conditions are true.
- `||` returns true if **at least one** condition is true.
- `!` negates a condition.

---
### (iv) Bitwise Operators
Used for bit-level operations.

| Operator | Description | Example (in C)                    |
| -------- | ----------- | --------------------------------- |
| `&`      | Bitwise AND | `5 & 3 // 0101 & 0011 = 0001 (1)` |
| \|       | \|          | Bitwise OR                        |
| `^`      | Bitwise XOR | `5 ^ 3 // 0101 ^ 0011 = 0110 (6)` |
| `~`      | Bitwise NOT | `~5 // Inverts bits`              |
| `<<`     | Left shift  | `5 << 1 // 0101 << 1 = 1010 (10)` |
| `>>`     | Right shift | `5 >> 1 // 0101 >> 1 = 0010 (2)`  |
used in low-level Programming

---
### (v) Assignment Operators
Used to assign values.

| Operator | Description         | Example (in C)         |
| -------- | ------------------- | ---------------------- |
| `=`      | Assign value        | `int a = 5;`           |
| `+=`     | Add and assign      | `a += 3; // a = a + 3` |
| `-=`     | Subtract and assign | `a -= 3; // a = a - 3` |
| `*=`     | Multiply and assign | `a *= 3; // a = a * 3` |
| `/=`     | Divide and assign   | `a /= 3; // a = a / 3` |
| `%=`     | Modulus and assign  | `a %= 3; // a = a % 3` |
Modulus means taking the remainder

THEY RETURN THE ASSIGNED VALUE???? WHY???

---
### (vi) Increment and Decrement Operators
Used to increase or decrease a variable's value by `1`.

| Operator | Description | Example (in C)                                   |
| -------- | ----------- | ------------------------------------------------ |
| `++`     | Increment   | `a++;` (post-increment) / `++a;` (pre-increment) |
| `--`     | Decrement   | `a--;` (post-decrement) / `--a;` (pre-decrement) |

🔹 **Example Difference Between Pre and Post Increment:**

```c
int a = 5;
printf("%d", a++);  // Prints 5, then a becomes 6
printf("%d", ++a);  // a becomes 7, then prints 7
```
Hence, using post would first access/ return the original value, and then increment it, while using pre-increment/decrement would change the value first.

---
# Operator Precedence

Just like how in maths, we have something called BODMAS, which tells us the order in which a particular expression must be calculated, we have something called operator precedence.

This tells us that in which order the operators would be assigned/ applied.
https://en.cppreference.com/w/c/language/operator_precedence  