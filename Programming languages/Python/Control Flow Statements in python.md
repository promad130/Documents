**Expected to know:** [[Control Flow Statements]]
**Topics Covered:**
**Tags:** [[Control Flow Statements]]

# Control Statements in Python
![[Syntax in Python#**5. Control Flow**]]
Python, like C, uses control statements to manage the flow of execution in a program. If you know C, you'll find the core concepts familiar, but Python's syntax is more concise and emphasizes readability.

---
## **Types of Control Statements in Python**

- Conditional Statements (`if`, `elif`, `else`)
- Looping Statements (`for`, `while`)
- Jump Statements (`break`, `continue`, `pass`)

---
### **1. Conditional Statements**

Python uses `if`, `elif`, and `else` for decision-making, similar to C's `if`, `else if`, and `else`. However, Python uses indentation instead of curly braces `{}` to define code blocks.

**Example:**
```python
x = 10 
if x > 0:     
	print("Positive") 
elif x == 0:     
	print("Zero") 
else:     
	print("Negative")
```
- No parentheses needed around conditions.
- Indentation (usually 4 spaces) defines the code block, not braces

---
### **2. Looping Statements**
#### **a. for Loop**
Python's `for` loop iterates directly over items in a sequence (like a list or string), not via an index unless you use `range()`.

**Example:**
```python
for i in range(5):  # Loops from 0 to 4     
	print(i)
```
- Equivalent to C's `for (int i = 0; i < 5; i++)`.

#### **b. while Loop**
Works similarly to C. The loop continues as long as the condition is true.

**Example:**
```python
count = 0 
while count < 5:     
	print(count)    
	count += 1`
```
- Indentation replaces braces

#### **c. else with Loops**
Python allows an `else` clause after a loop, which runs if the loop was not terminated by a `break`.

**Example:**
```python
for i in range(3):     
	print(i) 
else:     
	print("Loop finished")`
```
- This feature does not exist in C.

---
## **3. Jump Statements**
- **break**: Exits the nearest enclosing loop, like in C.
- **continue**: Skips to the next iteration of the loop, like in C.
- **pass**: Does nothing; used as a placeholder when a statement is required syntactically but no action is needed (no direct equivalent in C).
	for example, an empty function, or an empty class etc,.

**Example:**
```python
for i in range(5):     
	if i == 3:        
		break    
	print(i)
```

---
## 4. Ternary Operator
Python has a ternary conditional operator, which is a concise way to write simple conditional expressions in a single line. Its syntax is:

```python
x if condition else y
```
### Key Features:

1. **Order of Evaluation**:  
    The `condition` is evaluated first.
    - If **True**, `x` is returned.
    - If **False**, `y` is returned.
    
2. **Readability**:  
    Unlike C's `condition ? x : y`, Python uses keywords (`if`/`else`), making the logic more explicit.

So basically, we use if-else to create a ternary in python!

For example:
```python
num = 5

result = "Positive" if num > 0 else "Negative"
print(f"The {num} is {result}")
```

---