**Expected to know:** [[Control Flow Statements]] [[Looping Statements]] [[Conditional Statements]] [[Functions]] [[Jump Statements]]
**Topics Covered:** [[#Control Flow in JavaScript]] 
**Tags:**  [[Control Flow Statements]], [[Looping Statements]], [[Conditional Statements]], [[Functions]], [[Introduction to JavaScript]], [[Jump Statements]]

![[Syntax of JS#Functions]]

## Control Flow in JavaScript
**Control flow** refers to the order in which the JavaScript engine executes statements in a script. By default, code runs from the top of the file to the bottom, one line at a time. 
However, control flow statements allow you to alter this sequence, making your programs capable of making decisions, repeating actions, or responding to events.

---
## Main Control Flow Structures
### 1. Conditional Statements
- Allow your code to make decisions and execute different blocks based on certain conditions.
- Common examples:
    - `if`, `else if`, `else`
    - `switch` statements
```js
if (score > 90) 
{   
	console.log("Excellent!"); 
} else if (score > 75) 
{   
	console.log("Good job!"); 
} else 
{   
	console.log("Keep practicing!"); 
}
```
This structure lets the program choose which code block to execute depending on the value of `score`.

---
### 2. Loops
- Enable repeated execution of code blocks as long as a condition is met.
- Types of loops:
    
    - `for` loop: When you know how many times to repeat.
    - `while` loop: When you repeat as long as a condition is true.
    - `do...while` loop: Like `while`, but always runs at least once.
    - `for...of` loop: Iterates over iterable objects (like arrays).
    - `for...in` loop: Iterates over the properties of an object.
```js
for (let i = 0; i < 3; i++) { console.log(i); }
```
- **`break`**: Exits a loop early.
- **`continue`**: Skips the current iteration and moves to the next.
Then there is `return` which works just like how `return` usually works in Programming Languages 
We also have `labels` which we can use on control flow, which allows us to literally *"label"* a code block, which then can be used as we wish:
```JS
outmostloop:for(let i = 0; i< 100; i++)
{
	console.log("This is the iteration ${i} of first loop");
	for(let j = 0; j < i; j++)
	{
		if(j>5){
			break outmostloop;
		}
	}
}
```
And this will bring the flow of execution out of both loops when the given condition is true. Here, `outmostloop:` is called **Label** in JS.

---
### 3. Functions
Functions group code into reusable blocks and can be called from different places in your program, affecting the flow of execution.

**Syntax:**

Functions in JS are created using the keyword `function` followed by function name and then in parenthesis we enter the parameters, and then in `{}` we write what that function will do.
It is our choice to give a return value or not.
They should always follow `camelCase` as `PascalCase` is reserved for something else!!!
```js
function greet(name) 
{   
	console.log("Hello, " + name + "!"); 
} 

greet("Alice");`
```
```JS
function add(num1 = 2, num2 = 5){
	console.log("The addition is ${num1 + num2}");
}

add(); // 6
add(4,6); // 10

```

#### Arrow Function:
We can create functions using arrow function syntax for a clean and clear understanding of the JS script,

**Syntax:**

This is done by first declaring a variable, and then assigning it Parameters followed by an arrow operator which containes the body of function!!!
```JS
const addNums = (num1 = 2, num2 = 3) => {
	return num1 + num2;
};

let b = addNums(2,65);
```
And once declared, we can then use it like a normal function.
We can also give it `let` or `var`, but `const` is preferred for accidental reassignment.

```JS
let a = (a = 1, b = 3) => {return a+b;};
console.log(a);
let b = a(5,68);
console.log(b);
```
**Output:**
```text
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript$ nodejs prac.js 
[Function: a]
73
```

---
## How Control Flow Works
- **Top-to-bottom**: By default, JavaScript executes statements in order, from the first to the last line.
- **Altered by control structures**: Conditionals, loops, and functions can change this order, allowing for complex logic and interactivity.
- **Event-driven**: In web applications, code can also be triggered by events (like clicks or form submissions), further influencing control flow.

# Now what do we mean when we say "functions are first-class objects in JS"

## Why Functions are First-Class Objects

To qualify as "first-class," an entity must be able to do these four things:

### 1. Be stored in a variable

You can assign a function to a variable just like you would assign the number 10.

JavaScript

```
const greet = function() {
  console.log("Hello!");
};
```

### 2. Be passed as an argument to a function

This is the foundation of **callbacks**. You can send a function into another function to be executed later.

JavaScript

```
function executeTask(task) {
  task(); // Running the function passed in
}

executeTask(greet); 
```

### 3. Be returned from a function

A function can "spit out" another function as its result. This is a core part of **closures** and functional programming.

JavaScript

```
function createMultiplier(multiplier) {
  return function(num) {
    return num * multiplier;
  };
}

const double = createMultiplier(2);
console.log(double(5)); // 10
```

### 4. Have properties and methods

Since functions are actual objects in JavaScript, you can even attach data to them.

JavaScript

```
function sayHi() {
  console.log("Hi");
}

sayHi.description = "A simple greeting function";
console.log(sayHi.description); 
```

---

## The "Why It Matters" Factor

Treating functions as first-class objects is what makes JavaScript so flexible. It allows for:

- **Asynchronous programming:** Passing a function to `setTimeout` or a network request.
    
- **Higher-Order Functions:** Using methods like `.map()`, `.filter()`, and `.reduce()` which take functions as inputs to transform data.
    
- **Modular Code:** Breaking logic into tiny, portable pieces that can be moved around your application easily.
    

> **Fun Fact:** Because functions are objects, they actually have a prototype link to the global `Function.prototype` object, giving them built-in methods like `.call()`, `.apply()`, and `.bind()`.