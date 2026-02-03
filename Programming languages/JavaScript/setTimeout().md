## **Definition**  
`setTimeout` is a built-in JavaScript function that schedules another function (or code) to execute after a specified delay (in milliseconds). It is commonly used to introduce delays, schedule tasks, or defer execution without blocking the main thread.

**Syntax**
```javascript
setTimeout(function, delay, arg1, arg2, ...)
```
- `function`: The function to execute after the delay.
- `delay`: Time to wait before execution, in milliseconds (1 second = 1000 milliseconds).
- `arg1, arg2, ...` (optional): Arguments to pass to the function when it runs.

**Example**
```javascript
setTimeout(function() {   console.log("This message appears after 2 seconds"); }, 2000);
```
- This code prints the message after a 2-second delay.

**Named Function Example**
```javascript
function greet(name) 
{   
	console.log("Hello, " + name); 
} 

setTimeout(greet, 1000, "Alice"); // Prints "Hello, Alice" after 1 second
```
- Here, `greet` is called with `"Alice"` after 1 second.

**Key Points**
- `setTimeout` is asynchronous: The rest of your code continues running while the timer counts down.
- The function provided to `setTimeout` is called a callback.
- The minimum delay is not guaranteed to be exact, especially for very small values, due to browser and JavaScript engine scheduling.

**Use Cases**

- Creating delays or pauses in code execution
    
- Deferring tasks to run after the current call stack is clear
    
- Scheduling repeated tasks (with `setInterval`, a related function)
    

**Example: Execution Order**

javascript

`console.log("Start"); setTimeout(() => console.log("Timeout"), 0); console.log("End"); // Output: Start → End → Timeout`

- Even with a `0` millisecond delay, the callback runs after the current code finishes, demonstrating JavaScript's non-blocking execution order1.
    