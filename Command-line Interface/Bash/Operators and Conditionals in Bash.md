**Expected to know:** [[Conditional Statements]] [[Operators]]
**Topics Covered:**
**Tags:** [[Conditional Statements]] [[Operators]]

![[Syntax of Bash#**6. Conditional Statements**]]

## Bash Operators and Conditionals: The Basics

If you know only basic variable assignment in Bash, here’s a clear guide to the essential operators and how to use conditionals (if, else, etc.) in Bash scripting.

---
## **1. Assignment Operators**

- **Standard Assignment:**  
    Assign a value to a variable (no spaces around `=`):
```bash
NAME="Alice" 
COUNT=5
```
    
- **Compound Assignment:**  
    Modify a variable in place (works for integers):
    
```bash
COUNT=10 
COUNT+=5    # COUNT is now 15 
COUNT-=3    # COUNT is now 12 
COUNT*=2    # COUNT is now 24 
COUNT/=4    # COUNT is now 6 
COUNT%=4    # COUNT is now 2
```

---
## **2. Arithmetic Operators**
Use arithmetic operators with the `expr` command or within double parentheses:
- `+` (addition)
- `-` (subtraction)
- `*` (multiplication, escape as `\*` if using `expr`)
- `/` (division)
- `%` (modulo)

**Examples:**

```bash
a=10 
b=20 

sum=$((a + b))      # sum is 30 
diff=$((a - b))     # diff is -10 
prod=$((a * b))     # prod is 200 
quot=$((b / a))     # quot is 2 
mod=$((b % a))      # mod is 0
```
Or using `expr`:
```bash
sum=$(expr $a + $b)
```
**Note:** Always use spaces around operators with `expr`.

`What is expr, why is it needed? And why do I need to do $(())?  why not $()?`

---

## **3. Comparison Operators**

## **Numeric Comparison**

- `-eq` : equal to
- `-ne` : not equal to
- `-lt` : less than
- `-le` : less than or equal to
- `-gt` : greater than
- `-ge` : greater than or equal to

**Example:**
```bash
if [ $a -eq $b ]; then   
	echo "a equals b" 
fi
```
## **String Comparison**

- `=` or `==` : equal to
- `!=` : not equal to
- `<` : less than (ASCII order)
- `>` : greater than (ASCII order)
- `-z` : string is empty
- `-n` : string is not empty

**Example:**
```bash
if [ "$str1" = "$str2" ]; then   
	echo "Strings are equal" 
fi 

if [ -z "$str1" ]; then   
	echo "String is empty" 
fi
```
## **File Test Operators**

- `-e file` : file exists
    
- `-f file` : file is a regular file
    
- `-d file` : file is a directory
    
- `-s file` : file is not empty
    
- `-r file` : file is readable
    
- `-w file` : file is writable
    
- `-x file` : file is executable
    

**Example:**

bash

`if [ -f "myfile.txt" ]; then   echo "File exists and is a regular file" fi`

---

## **4. Logical Operators**

- `&&` or `-a` : AND (both conditions must be true)
    
- `||` or `-o` : OR (at least one condition true)
    
- `!` : NOT (negates a condition)
    

**Example:**

bash

`if [ $a -gt 0 ] && [ $a -lt 10 ]; then   echo "a is between 1 and 9" fi if [ ! -f "file.txt" ]; then   echo "File does not exist" fi`

---

## **5. Conditional Statements**

## **If Statement**
```bash
if [ condition ]; then   
	# commands if true 
fi
```

## **If-Else Statement**
```bash
if [ condition ]; then   
	# commands if true 
else   
	# commands if false 
fi
```

## **If-Elif-Else Statement**
```bash
if [ condition1 ]; then   
	# if condition1 is true 
elif [ condition2 ]; then   
	# if condition2 is true 
else   
	# if none are true 
fi
```
## **Example:**
```bash
num=7 

if [ $num -gt 10 ]; then   
	echo "Greater than 10" 
elif [ $num -eq 10 ]; then   
	echo "Equal to 10" 
else   
	echo "Less than 10" 
fi
```
---

## **Key Rules and Tips**

- Always put spaces around brackets and operators: `[ $a -eq $b ]` not `[$a-eq$b]`.
- Quote variables in conditions to avoid errors with empty or multi-word strings: `[ "$str" = "hello" ]`.
- Use `fi` to end an `if` block.
- For arithmetic inside `(( ... ))`, you can use C-like syntax: `if (( a > b )); then ... fi`

---
