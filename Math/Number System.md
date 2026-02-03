**Expected to know:** Basic Math
**Topics Covered:** [[#What are Number Systems?]], [[#**Key Features of Number Systems**]],[[#Types of Number Systems]],[[#How does it works?]],[[#Mathematical front]]
**Tags:**

We have used the decimal system in mathematics right from the start. It is assumed that only 10 numbers exist, but that is not the rule. Value to any number can differ and cannot be absolute.
As our habitual number system is the decimal system itself, that is why we will talk about N-base system in terms of decimal system.

---
# What are Number Systems?
A **number system** is a mathematical framework used to represent and express numbers in a consistent manner using digits or symbols. 
It serves as the foundation for counting, measuring, and performing arithmetic operations. Number systems are essential in mathematics, computer science, and everyday applications.

## Key Features of Number Systems
1. **Representation of Numbers**: Each number system uses a specific set of symbols (digits) to represent numbers.
    
2. **Base (Radix)**: The base of a number system determines the number of unique digits it uses. For example:
    - Decimal (Base-10): Uses 10 digits (0–9).
    - Binary (Base-2): Uses 2 digits (0, 1).
    - Octal (Base-8): Uses 8 digits (0–7).    
    - Hexadecimal (Base-16): Uses 16 symbols (0–9 and A–F).
    
3. **Place Value**: The value of a digit depends on its position in the number and the base of the system.
    
4. **Arithmetic Operations**: Number systems allow operations like addition, subtraction, multiplication, and division.

---
# Types of Number Systems

## **Decimal System (Base-10)**:
- Most commonly used worldwide.   
- Digits: 0–9.
- Example: $456 \text{ represents: } 4×100+5×10+6×14 \times 100 + 5 \times 10 + 6 \times 14×100+5×10+6×1$.

## **Binary System (Base-2)**:
- Used in computers and digital systems.
- Digits: 0, 1.
- Example: $101_2 \text{ represents: } 1 \times 4 + 0 \times 2 + 1 \times 1 = 5_{10}.$

## **Octal System (Base-8)**
- Digits: 0–7.
- Example: $234_8 \text{ represents: } 2 \times 64 + 3 \times 8 + 4 \times 1 = 156_{10}.$

## **Hexadecimal System (Base-16)**:
- Commonly used in computing for memory addressing.
- Symbols: 0–9 and A–F (where A=10, B=11, ..., F=15).
- Example: $4B_{16} \text{ represents: } 4 \times 16 + B(11) = 75_{10}.$

---
# How does it works?
Lets revisit how counting works. Let's say I give you 9 wooden blocks, each with a unique symbol on it:
$$\fbox{@}\;\fbox{8}\;\fbox{O}\;\fbox{p}\;\fbox{)}\;\fbox{()}\;\fbox{*}\;\fbox{1}\;\fbox{?}\;$$
For simplicity and ease of use, lets redefine them as:
$$\fbox{2}\;\fbox{1}\;\fbox{5}\;\fbox{3}\;\fbox{7}\;\fbox{6}\;\fbox{4}\;\fbox{O}\;\fbox{@}\;$$
Now I want you to represent the number of chocolates I have in my hand using those block, and to make sure that you don't run out of block, I do provide you with duplicates of them.

So you would think to use each unique symbol to represent a unique value, and then arrange them in increasing order of that value for later reference.
$$\fbox{O}\;\fbox{1}\;\fbox{2}\;\fbox{3}\;\fbox{4}\;\fbox{5}\;\fbox{6}\;\fbox{7}\;\fbox{@}\;$$
Now you start counting, and for each new item, you replace the previous block with the one right next to it in the terms of greater value:
- on first chocolate: $\fbox{O}\;$
- on second chocolate: $\fbox{1}\;$
.
.
. and so on you reach the 9th chocolate: $\fbox{@}$

Now, you need to count the **10th chocolate**. You're out of unique symbols in a _single_ block, so you invent a new position (column, like tens place in decimal), to the left or right (let’s follow the usual convention and add to the left), and fill it with the smallest value block:
$$\fbox{O}\;\fbox{O}\;$$

But here’s the mistake and the confusion:  
What number does $\fbox{O}\;\fbox{O}\;$ mean? How do you differentiate between $\fbox{O}\;$ (which was one chocolate) and $\fbox{O}\;\fbox{O}\;$? What about $\fbox{O}\;\fbox{1}\;$? Is it 1 or 10 or something else?

**If you do not have a "zero value" block**, you'll quickly run into ambiguity:
- What should the first position mean if it contains the lowest block?
- How do you represent "no value" in a given position?
---
Let’s see what would happen if you used only non-zero blocks for each position:
## Suppose:
- $\fbox{O}\;$ represents 1
- $\fbox{1}\;$ represents 2
- ...
- $\fbox{@}\;$ represents 9
- Two blocks: $\fbox{O}\;\fbox{O}\;$ is... ?

You now have a huge problem:  
You have no way to represent numbers like 10, 20, 30 (or 9+1, etc.) without a symbol for "none" in the units place. If you used only non-zero blocks for new positions, your representations will collide or overlap, and you couldn't tell, for example, if $\fbox{O}\;$ meant "one chocolate" or if $\fbox{O}\;\fbox{O}\;$ meant the "tenth" or "first" chocolate in the tens place.

---
## Why the "Zero" Block Is Needed
Let’s look at proper place-value (positional) notation:
- In decimal, after 9 comes 10:  
    1 in the tens place, 0 in the ones (units) place.
    
- In your 9-block system (which is base 9), after $\fbox{@}\;$ (count 9), you want to write 10 as:
    $$\fbox{1}\;\fbox{O}\;$$
    - "1" means one group of 9,
    - "O" means zero ones (no leftover blocks).

But your scheme had no "O" block with value zero—without it, you can't fill the empty spot, so you invented meaningless or ambiguous patterns!

## Without zero, positional notation **doesn't work**.
With zero:
- The "O" block acts as a placeholder, not a value itself,
- Making counts like 1  O1O (for ten), 1  111 (for eleven), 2  O2O (for eighteen), etc., clear and unambiguous.

---
## Final Realization
**If we continue without a proper "zero" placeholder:**
- You'll start creating combinations like:$$\fbox{O}\;\fbox{O}\;$$$$\fbox{1}\;\fbox{O}\;$$$$\fbox{O}\;\fbox{1}\;$$$$\fbox{1}\;\fbox{1}\;$$
- But you will not be able to **consistently** represent numbers, or know which pattern means "just more blocks in that position" or "nothing" in that position unless you assign one special block (the "null block," a zero) for "empty" in each place.

This is because of the absence of the null block, i.e., the block with no value. Whenever we create a new space/ position, it is already there, but just has no value to it, and hence we need a block to represent that so that we can be precise with our counting, and represent how many times to go over the blocks precisely without any errors.

So we decide to give the block $\fbox{O}$ no value, and restart our counting, and when we encounter a need to create a new position, i.e., to mention how many times over we have went through the first position, we don't encounter the previous issue, i.e., when we reach:
$$\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\; = \fbox{@}\;$$, we can carry the next chocolate as how many times over the unique boxes:
$$\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}\; = \fbox{O}\;\fbox{1}\;$$
(Note that in this system we are creating the new position on the right side instead of usual left side).

Now when we reach $\fbox{@}\;\fbox{@}\;$, then it means we have covered/ used all the possible unique combinations (as cannot have duplicate numbers (like using $\fbox{1}\;\fbox{1}\;$ twice/thrice) to represent different number of chocolates) when we use only two positions, so we create a third position which would mean that:
$$\text{value at }3^{th}\text{ position}\times (9)^{2}$$
Where 9 is the number of unique blocks, i.e., the base of the number system.
Hence, in a more generalized form:
$$\text{value at }n^{th}\text{ position}\times (base)^{n-1}$$
And hence we can create as many positions as needed! 

So a collection of unique boxes like:
$$\fbox{5}\;\fbox{1}\;\fbox{3}\;\fbox{O}\;$$
Would mean:
- $\fbox{5}\;$times we go over all number of unique combination for $\fbox{placeHolder}\;\fbox{placeHolder}\;\fbox{placeHolder}\;$, i.e., $9^{3}+$ (Using permutations and combinations)
- $\fbox{1}\;$times we go over all number of unique combination for $\fbox{placeHolder}\;\fbox{placeHolder}\;$, i.e., $9^{2}+$
- $\fbox{3}\;$times we go over all number of unique combination for $\fbox{placeHolder}\;$, i.e., $9^{1} +$
- $\fbox{0}$ itself at the first place
I.e., $\fbox{5}\times 9^3+\fbox{1}\times 9^2+\fbox{3}\times 9^1+\fbox{0}\times 9^0$

Hence, each position in any number of any base has a value in terms of the base itself.
So for example we have these places:
$$.\;.\;.\;.\;\fbox{}\;\fbox{}\;\fbox{}\;\fbox{}$$
Each box represents a position in a number. Lets call the rightmost position as the first position, the box right next to it in the left as second position, and so on.(as the standard done by all base systems)

And hence, in any base system for a number, we have a formula for position called positional formula:
$$(a_{n-1}a_{n-2}....a_1a_0)_b = \sum_{i=0}^{n-1}a_ib^{i}$$
Where the addition and subtraction has to be performed in terms of that base.
If we add or subtract in form of base 10, then we get the decimal value of it, and it is intuitive that what would happen when we do the same in terms of other bases in-place of base 10, we get the value in that base!

---
# Mathematical front

When it comes to calculating and converting from one base to other, it can be tedious to perform addition and subtraction in form of other bases, so we use another method as it is quite simple:
- First divide the number with the base number, and write the remainder in the one's place, i.e., the first place(from right to left)
- Then we take the quotient, and then divide it again with the base and write the number in the second place
- and so on,.., we keep going until all we have is a remainder and no quotient.
![[Pasted image 20250415012533.png]]

## But what is the logic behind this?

It works something like this:
- The main purpose in finding the number is finding the constants for all the powers of the base
- The value of the number goes something like this:
$$(a_{n-1}a_{n-2}a_{n-3}.....a_2a_1a_0) = a_{n-1}b^{n-1}+a_{n-2}b^{n-2}+...+a_1b^1+a_0b^0$$
- Upon dividing by the base $b$:
	- Let $X = (a_{n-1}a_{n-2}a_{n-3}.....a_2a_1a_0)$
	- $$\frac{X}{b} = Quotient + remainder$$
	- where, remainder = $a_0$ and the quotient = $a_{n-1}b^{n-2}+a_{n-2}b^{n-3}+...+a_1b^0$
Hence the method works for converting bases of the numbers!!!!

---
