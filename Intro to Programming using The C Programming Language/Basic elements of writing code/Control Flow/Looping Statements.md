**Expected to know:** [[Control Flow Statements]] (till now), [[Data Types and Constants]], [[Variables and their scope]]
**Topics Covered:** for loops, while loops, do-while loops
**Tags:**

The order in which statements in a program are executed is called the flow of control or control flow.

So far we have only seen sequential and conditional execution.

![[Pasted image 20250224233741.png]]

Now, it is time to look at loops.

Loops does exactly what their name suggests, they repeat a particular task under a certain or no condition at all.

There are three basic types of loops:
- for loops
- while loop
- do-while loop
---
# while Loop

The `while` loop is used when the number of iterations is unknown and depends on a condition being true.
A complete execution of a cycle of a loop is called an iteration.

![[Pasted image 20250224234029.png]]

Try:
```c
#include <stdio.h>
int main()
{
	int m = 6, n = 3; 
	int answer = 1; 
	while (n > 0) 
	{ 
		answer *= m; 
		--n; 
	}
	return 0;
}

```
What will be the output?

$$
//output:
$$
$$
n = 3 \implies answer =  6 
$$
$$
n = 2 \implies answer = 6 \times 6 = 36
$$
$$
n = 1 \implies answer = 6 \times 6 \times 6 = 216
$$
$$
n = 0 \implies answer = \text{exits the loop with} answer  = 216
$$
---
# for loop

The `for` loop is used when the number of iterations is known beforehand. It consists of three parts:

- **Initialization:** Executes once before the loop starts.
- **Condition:** Checks before each iteration; loop runs if true.
- **Increment/Decrement:** Updates the loop variable after each iteration.

**Syntax:**


```c
for(initialization; condition; increment/decrement) 
{     
	// Code to execute in loop 
}
```

**Example:**
```c
for (int i = 1; i <= 5; i++) 
{     
	printf("%d ", i); 
}
```

**Output:**  
`1 2 3 4 5`

---
# do-while loops

The `do-while` loop is similar to the `while` loop, but it ensures that the loop executes at least once, even if the condition is false.
```c
do {
    // Code to execute in loop
} while (condition);

```
 Example;
 ```c
 int i = 1;
do {
    printf("%d ", i);
    i++;
} while (i <= 5);

```
output;
`12345`

