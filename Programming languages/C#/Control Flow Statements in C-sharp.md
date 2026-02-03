**Expected to know:** [[Introduction to Programming]],[[Control Flow Statements]],[[C-sharp Programming Language]](till now)
**Topics Covered:**
**Tags:** [[Control Flow Statements]]

![[Syntax of C-sharp#**Control Structures**]]

The Logic of all control flow statements in all programming languages are same, with different syntax and some new features.i,e., parameters.

This programming language also follow what generally all programming languages follow, hence all the concepts regarding control flow statements and syntax are same as what has been given in [[Control Flow Statements]].

---
# Control Flow in C#:
Now lets have a look at control flow statements in C# programming languages. It offers sone new stuff and useful changes as well!!!

---
## The loops 
### **For Loop**

```csharp
for (initialization; condition; iteration) 
{     // Code to execute 
}
```

---
### **While Loop**

```csharp
while (condition) 
{     
	// Code to execute 
}
```

---

### **Do While Loop**
```csharp
do {     
	// Code to execute 
} while (condition);
```

---
***After covering [[Data Structures in C-sharp#Array]]***
### **foreach() loop**
Loops and arrays and stuff goes hand-in-hand, that is why we have something called `foreach` in C#, which offers simpler syntax for accessing each element in the array or data structure and working on them in the loop.
This makes;
```csharp
for(int i = 0;i < array.Length; i++)
{
	
}
```
Equivalent to this:
```csharp
foreach(int Number in NumberArray)
{
	
}
```

See, so simple!!
To make a foreach loop, you use the foreach keyword. Inside of parentheses, you declare a variable that will hold each item in the array in turn. The in keyword separates the variable from the array to iterate over. The variable can be used inside the loop.

The only downside is that we won't know which index are we currently working on with this loop.

---
## The conditionals
### **If Statement**

```csharp
if (condition) 
{     
	// Code to execute if condition is true 
}
```

### **Else If Statement**

```csharp
if (condition1) 
{     
	// Code if condition1 is true 
} else if (condition2) {     
	// Code if condition2 is true 
} else {     
	// Code if none of the above conditions are true 
}
```

### **If-Else Statement**

```csharp
if (condition) 
{     
	// Code if condition is true 
} else {     
// Code if condition is false 
}
```

C# also provides with the dangling if-else statement!?!?!
Also, whitespaces doesn't matter, so ***Don't judge a code by it's looks***.

### Ternary Operator

```csharp
return (condition)? something : somethingElse;
```

---
### **Switch Statement**

```csharp
switch (expression) {     
	case value1:
	// Code block        
	break;    
	case value2:        
	// Code block        
	break;    
	default:        
	// Default code block        
	break; 
}
```
![[Pasted image 20250523141018.png]]

But, the switch in C# allows us to have multiple cases for the same arm of switch:
```csharp
case value1:
case value2:
	Console.WriteLine("This is interpreted as either value1 or value2 is the value of our expression");
	break;
```

#### Switch Expression
Now what if instead of doing something upon finding the matching value, I want to get something, something should be returned?
Then we have something called a Switch Expression, where the concept is same but the syntax is changed for easiness:
```csharp
string response;

response = choice switch
{
	Value1 => "The given value of choice was Value1",
	Value2 => "The given value of choice was Value2",
	Value3 => "The given value of choice was Value3",
	_ => "This is the default condition, i.e., the value of choice wasnot among the given values!"
};

Console.WriteLine(response);
```

This returns stuff instead of outputting them, and is useful in a lot of things.
The response stores the returned value.

---
## **Jump Statements**

| Statement  | Syntax Example                    | Description                                                                   |
| ---------- | --------------------------------- | ----------------------------------------------------------------------------- |
| `break`    | `break;`                          | Exits the nearest loop or switch statement.                                   |
| `continue` | `continue;`                       | Skips the rest of the current loop iteration and moves to the next iteration. |
| `goto`     | `goto label;`                     | Jumps to a labeled statement within the same method.                          |
| `return`   | `return;` or `return value;`      | Exits a method and optionally returns a value.                                |
| `throw`    | `throw new Exception("message");` | Throws an exception and transfers control to the nearest catch block.         |



---
## **Recall Table**

| Statement | Basic Syntax Example                               |
| --------- | -------------------------------------------------- |
| for       | `for (init; cond; iter) { /* code */ }`            |
| while     | `while (cond) { /* code */ }`                      |
| do while  | `do { /* code */ } while (cond);`                  |
| if        | `if (cond) { /* code */ }`                         |
| else if   | `else if (cond) { /* code */ }`                    |
| if-else   | `if (cond) { /* code */ } else { /* code */ }`     |
| switch    | `switch(expr) { case val: /* code */ break; ... }` |
| break     | `break;`                                           |
| continue  | `continue;`                                        |
| goto      | `goto label; ... label: /* code */`                |
| return    | `return;` or `return value;`                       |
| throw     | `throw new Exception("msg");`                      |

---
