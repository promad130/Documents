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

#### BITWISE XOR / Any Bitwise operators implemented on integers follow this logic:
Lets say I have this:
![[Pasted image 20260527044323.png]]

Now how does the XOR of integers work and what does it store and all?
To see exactly how it works under the hood—and what happens when you do `1 ^ 2`—we have to look at the numbers the way the computer does: in **binary (bits)**.

##### What happens when you do `1 ^ 2`?

XOR stands for **Exclusive OR**. The rule for individual bits is simple:
- If the bits are the **same**, the result is `0`.
- If the bits are **different**, the result is `1`.

Let's look at `1 ^ 2` in binary:

- `1` in binary is `0 1`
- `2` in binary is `1 0`

```Plaintext
  0 1  (Decimal 1)
^ 1 0  (Decimal 2)
------
  1 1  (Decimal 3)
```

Because the bits in each position are different, they both turn into `1`. `1 1` in binary is **3**. So, `1 ^ 2 = 3`.

##### How is it "storing and canceling"?

Let's take that `3` we just calculated and continue the chain. What happens if we XOR that result with `2` again? (`1 ^ 2 ^ 2`)

We already know `1 ^ 2 = 3` (binary `1 1`). Now let's XOR that with `2` (binary `1 0`):

```Plaintext
  1 1  (Result of 1 ^ 2)
^ 1 0  (Decimal 2)
------
  0 1  (Decimal 1)
```

Look at that: the `2` completely canceled itself out, and we are right back to `1`.

##### Visualizing the whole process

Think of every bit position like a light switch.

- XORing a number into a variable is like flipping the switches corresponding to that number's binary digits.
- If you flip a switch **once** (because the number appeared in `xor1`), the light stays **ON**.
- If you flip that exact same switch a **second time** (because the number also appeared in `xor2`), the light goes back **OFF** (`0`).

Because the array is only missing _one_ number, every single number that is present gets its switches flipped exactly **twice** (once in `xor1` and once in `xor2`), turning those lights off.

The missing number only gets its switches flipped **once** (inside `xor1`), so its lights stay ON at the very end. When you do `xor1 ^ xor2`, you are reading which lights are still standing!

##### How the Code Applies This

The problem asks you to find a missing number in the range $1$ to $N$. The code sets up a game of elimination using two variables:

- **`xor1`**: This calculates the XOR of **all expected numbers** from $1$ to $N$.
    
- **`xor2`**: This calculates the XOR of **all actual numbers** present inside the array `a`.
    

Let’s dry-run the code using the exact example from your image:

**`A = [1, 2, 4, 5]`** and **`N = 5`** (meaning the missing number is **3**).

##### Inside the loop (from `i = 0` to `n - 1`):

The loop runs for the indices of the array (which has a size of $N-1$, stored in variable `n`).

- **`xor2` collects the array elements:** `1 ^ 2 ^ 4 ^ 5`
    
- **`xor1` collects the range values up to $N-1$:** `1 ^ 2 ^ 3 ^ 4`
    

##### Line 8 (After the loop):

`xor1 = xor1 ^ N;`

The loop only went up to $N-1$ (which was 4). This line manually tacks on the final number $N$ (which is 5) to `xor1`.

Now, let's look at what both variables hold right before the final return statement:

- `xor1` = $1 \oplus 2 \oplus 3 \oplus 4 \oplus 5$ _(All numbers that **should** be there)_
- `xor2` = $1 \oplus 2 \oplus 4 \oplus 5$ _(All numbers that **are actually** there)_

##### The Grand Finale: `return xor1 ^ xor2;`

When the code returns `xor1 ^ xor2`, it mashes both groups together into one giant XOR chain. Order doesn't matter in XOR, so we can rearrange them to pair them up:

$$\text{Result} = (1 \oplus 1) \oplus (2 \oplus 2) \oplus 3 \oplus (4 \oplus 4) \oplus (5 \oplus 5)$$

Using our rules from earlier, watch them cancel out:

$$\text{Result} = 0 \oplus 0 \oplus 3 \oplus 0 \oplus 0$$

$$\text{Result} = 3$$

Because $1, 2, 4,$ and $5$ appear in both the expected list and the actual list, they pair up, turn into $0$, and vanish. The missing number ($3$) only appears once in the entire chain, so it has no partner to cancel it out. It passes straight through the zeroes completely untouched.

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