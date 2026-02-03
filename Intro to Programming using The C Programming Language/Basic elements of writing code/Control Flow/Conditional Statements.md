**Expected to know:** [[Control Flow Statements]]
**Topics Covered:** if-else, nested if, dangling if-else, ternary operator(conditional operator),switch cases
**Tags:**

Conditional statements are the control flow statements that determines the flow of programme, i.e., it can help us add a certain condition which says if the condition is met, then do something, and if not, then do something else. Basically, the program flow is not sequential anymore, and hence the flow of the programme is controlled.

There are various types of conditional statements:
- if 
- if-else
- else if
- switch
- ternary operator
---
#  `if` conditional

The if statement is the most basic form of conditional statement. It checks if a condition is true. If it is, the program executes a block of code, otherwise it will skip that block of code.

```c
if (condition) {  
    // code to execute if condition is true  
}
```

```
### Example:

```c
int num = 10;
if (num > 0) {
    printf("The number is positive\n");
}
```

---

## `if-else` Statement

The `if-else` statement provides an alternative block of code to execute if the condition is `false`.

```c
if (condition) {  
    // code to execute if condition is true  
} else {  
    // code to execute if condition is false  
}
```

### Example:

```c
int num = -5;
if (num > 0) {
    printf("The number is positive\n");
} else {
    printf("The number is not positive\n");
}
```

---

## `else if` Ladder

When multiple conditions need to be checked sequentially, `else if` statements are used.

```c
if (condition1) {
    // code executes if condition1 is true
} else if (condition2) {
    // code executes if condition2 is true
} else {
    // code executes if none of the conditions are true
}
```

### Example:

```c
int num = 0;
if (num > 0) {
    printf("Positive number\n");
} else if (num < 0) {
    printf("Negative number\n");
} else {
    printf("Zero\n");
}
```

---

## Nested `if` Statement

An `if` statement inside another `if` statement is called a nested `if`. This allows checking conditions inside another conditional block.

```c
if (condition1) {
    if (condition2) {
        // code executes if both condition1 and condition2 are true
    }
}
```

### Example:

```c
int age = 20;
if (age > 18) {
    if (age < 25) {
        printf("Eligible for a youth discount\n");
    }
}
```

---

## Dangling `else` Problem

The "dangling else" problem occurs when there is ambiguity in matching `else` statements with `if` statements.

### Example of Ambiguity:

For example, I want to write a programme which give out XYZ if number is negative, and ABC if the number is between 0 to 100;
```c
#include <stdio.h>

int main()
{
	int a;
	scanf("%d", a);
	
	if(a>=0)
		if(a<=100)
			printf("ABC");
	else
		printf("XYZ")
	
	return 0;
}
```
So as in dangling if-else, else refers to the closest if, the programme would actually work like:
```c
#include <stdio.h>

int main()
{
	int a;
	scanf("%d", a);
	
	if(a>=0)
	{
		if(a<=100)
		{
			printf("ABC");
		}
		else
		{
			printf("XYZ")
		}
	}
	return 0;
}
```

which is not according to what we wanted, so in order to avoid ambiguity, we use {}:
```c
#include <stdio.h>

int main()
{
	int a;
	scanf("%d", a);
	
	if(a>=0)
	{
		if(a<=100)
		{
			printf("ABC");
		}
		
	}else
		{
			printf("XYZ")
		}
	return 0;
}
```

---

## Ternary Operator (`?:`)

The ternary operator is a shorthand for `if-else` statements.

```c
condition ? expression_if_true : expression_if_false;
```

It returns a value of the true statement.
### Example:

```c
int num = 5;
printf("%s", (num > 0) ? "Positive" : "Non-positive");
```

---

## `switch` Statement

The `switch` statement is used when multiple possible values for a variable need to be checked.

```c
switch (expression) {
    case value1:
        // code to execute
        break;
    case value2:
        // code to execute
        break;
    default:
        // code to execute if no cases match
}
```

So what is happening?

- Expression is first evaluated
- It is then compared with const-expr-1, const-expr-2,…for equality in order
- If it matches any one, all statements from that point till the end of the switch are executed (including statements for default, if present)
- Use break statements if you do not want this (see example)
- Statements corresponding to default, if present, are executed if no other expression matches

In switch cases, break statements are used in order to stop the execution of the cases, as if a case is found to be the matching case, i.e., true case (i.e., expression == value), then all the cases including that case are executed until a break statement comes.
### Example:

```c
int day = 3;
switch (day) {
    case 1:
        printf("Monday\n");
        break;
    case 2:
        printf("Tuesday\n");
        break;
    case 3:
        printf("Wednesday\n");
        break;
    default:
        printf("Invalid day\n");
}
```

**Important Notes:**

- `break;` stops the execution of the switch cases.
- If `break;` is omitted, execution continues to the next case until a `break;` or the end of the switch statement. I.e, for example in this code:
```c
int day = 2;
switch (day) {
    case 1:
        printf("Monday\n");

    case 2:
        printf("Tuesday\n");

    case 3:
        printf("Wednesday\n");

    default:
        printf("Invalid day\n");
}

```
 It will print all the cases from the given case 2 as no break statement is not there.
so:
```c
//output:
Tuesday
Wednesday
Invalid day
```

---

## Summary

- The `if` statement checks a single condition.
- The `if-else` statement provides an alternative block if the condition is false.
- The `else if` ladder checks multiple conditions.
- Nested `if` statements allow deeper conditional logic.
- The ternary operator (`?:`) is a compact way of writing `if-else`.
- The `switch` statement is used for multi-way branching based on discrete values.

These control flow statements help in writing structured and logical programs that can handle different scenarios dynamically.


