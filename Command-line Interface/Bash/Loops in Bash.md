**Expected to know:** [[Control Flow Statements]]
**Topics Covered:**
**Tags:**

![[Syntax of Bash#**7. Loops**]]

## Loops in Bash: The Basics
Loops in Bash allow you to repeat commands automatically. Here’s a breakdown of the three main loop types, with examples tailored to your existing knowledge of variables and operators.

---
## **1. `for` Loop**

Used to iterate over a list of items (numbers, strings, files, etc.).
**Syntax:**
```bash
for variable in item1 item2 item3 
do   
	# Commands using $variable 
done
```

**Examples:**
- **Basic list iteration:**
    
```bash
for color in red green blue 
do   
	echo "Color: $color" 
done
```
    Output:    
```text
Color: red 
Color: green 
Color: blue
```
- **Number ranges:**
    
```bash
for num in {1..5}  
# Expands to 1 2 3 4 5 
do   
	echo "Number: $num" 
done
```
    
- **C-style syntax (familiar from C):**
```bash
for (( i=0; i<5; i++ )) 
do   
	echo "Count: $i" 
done
```
Output:
```text
Count: 0 
Count: 1 
Count: 2 
Count: 3 
Count: 4
```
---
## **2. `while` Loop**
Runs commands **while** a condition is true.
**Syntax:**
```bash
while [ condition ] 
do   
	# Commands 
done
```
**Example:**

```bash
counter=1 
while [ $counter -le 3 ] 
do   
	echo "Counter: $counter"  
	((counter++))  # Increment counter (like counter += 1) 
done
```
Output:

```text
Counter: 1 
Counter: 2 
Counter: 3
```

---
## **3. `until` Loop**

Runs commands **until** a condition becomes true (opposite of `while`).

**Syntax:**

```bash
until [ condition ] 
do   
	# Commands 
done
```

**Example:**

bash

`count=1 until [ $count -gt 3 ] do   echo "Count: $count"  ((count++)) done`

Output:

text

`Count: 1 Count: 2 Count: 3`

---

## **Loop Control**

- **`break`**: Exit the loop immediately.
    
    bash
    
    `for num in {1..10} do   if [ $num -eq 5 ]; then    break  fi  echo $num done`
    
    Output: `1 2 3 4`
    
- **`continue`**: Skip the current iteration.
    
    bash
    
    `for num in {1..5} do   if [ $num -eq 3 ]; then    continue  fi  echo $num done`
    
    Output: `1 2 4 5`
    

---

## **Common Use Cases**

- **Iterate over files:**
    
    bash
    
    `for file in *.txt do   echo "Processing $file" done`
    
- **Read input line-by-line:**
    
    bash
    
    `while read line do   echo "Line: $line" done < input.txt`
    

---

## **Key Differences**

|Loop Type|When to Use|
|---|---|
|`for`|Known number of iterations or lists|
|`while`|Continue until a condition is false|
|`until`|Continue until a condition is true|

With these basics, you can automate repetitive tasks in Bash scripts. Start with simple loops and gradually combine them with conditionals (`if`) for more complex logic.
