**Expected to know:** [[Syntax]], [[Bash]]
**Topics Covered:**
**Tags:** [[Syntax]]
## Bash Syntax Overview
Bash syntax is the set of rules that define how Bash scripts and commands are written and interpreted.
The most important command to start off with is the `echo` command. Make sure you know about it from [[Bash]].

Here are the key elements and examples to help you get started:

---
## **1. Shebang Line**
Every Bash script usually starts with a shebang line.
This starts with `#!` followed by the path to the interpreter.

Purpose of this line is to tell which program/programming Language/interpreter to use to compile the file.

```bash
#!/bin/bash
```
The usual location for bash interpreter is `/bin/bash`.

---
## **2. Comments**
Use `#` to add comments. Bash ignores everything after `#` on a line.
```bash
# This is a comment
```
Comments are helpful for explaining your code.

---
## **3. Command Execution**
Commands are executed in order, one after another:
```bash
echo "First command" 
echo "Second command"
```
You can use semicolons `;` to separate multiple commands on the same line:
```bash
echo "Hello"; echo "World"
```
---
## **4. Variables**
Assign variables without spaces around the `=`:

```bash
name="Alice"
```
Access the value with `$`:
```bash
echo $name
```
---

## **5. Reading User Input**

Use `read` to get input from the user:

```bash
echo "Enter your name:" 
read username 
echo "Hello, $username"`
```

---

## **6. Conditional Statements**

Bash uses `if`, `then`, `else`, and `fi` for conditionals:

```bash
if [ $a -eq $b ]; 
then   
	echo "Equal" 
else   
	echo "Not equal" 
fi
```
---

## **7. Loops**

**For loop:**

```bash
for i in {1..5} do   
	echo $i 
done
```

**While loop:**

```bash
count=1 
while [ $count -le 5 ] do   
	echo $count  
	((count++)) 
done
```

---

## **8. Functions**
Define reusable code blocks with functions:

```bash
greet() 
{   
	echo "Hello, $1" 
} 

greet "World"
```

---

## **9. Arrays**

Arrays store multiple values:

```bash
arr=(apple banana cherry) 
echo ${arr[1]}   # Output: banana
```

---

## **10. Case Statements**
Handle multiple conditions:

bash

`case $fruit in   apple)    echo "Apple";;  banana)    echo "Banana";;  *)    echo "Unknown fruit";; esac`

---

## **11. Script Execution**

- Save your script (e.g., `myscript.sh`).
    
- Make it executable:
    
    bash
    
    `chmod +x myscript.sh`
    
- Run it:
    
    bash
    
    `./myscript.sh`
    

---

These are the basic building blocks of Bash syntax.