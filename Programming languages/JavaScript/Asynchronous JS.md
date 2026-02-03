==Asynchronous operations allow a program to perform tasks independently without blocking the main thread, enabling it to handle multiple requests concurrently==. Conversely, synchronous operations execute tasks sequentially, with the program waiting for one task to complete before proceeding to the next.

Asynchronous JavaScript enables non-blocking execution of tasks like network requests or file operations, crucial for maintaining responsiveness in single-threaded JavaScript. 

When we say "JavaScript enables non-blocking execution of tasks," it means JavaScript can start a time-consuming operation (like reading a file or making a network request) and continue running other code without waiting for that operation to finish. This is achieved through asynchronous programming, using callbacks, promises, or async/await.
This is important as in JS, code is executed line by line, and same applies for the functions. So a function written first has to be executed first, and then the next function and so on.
This is slow and not optimized as we would want multiple thing in a code to happen in parallel at once when we run the script when it comes to things like Network programming.

## Core Concepts
### 1. [[Callbacks]]  
The earliest method for handling async operations. Functions like `setTimeout` or `fetch` accept callback functions to execute after task completion:
```javascript
setTimeout(() => {   console.log("Executed after 2 seconds"); }, 2000);
```
However, complex callback chains lead to "callback hell" with nested functions.

### 2. [[Promises]]  
Introduced to flatten nested callbacks. Represent eventual completion/failure of async operations:
```javascript
fetch('https://api.example.com/data')   
.then(response => response.json())  
.then(data => console.log(data))  
.catch(error => console.error(error));
```
Promises provide `.then()` chaining and centralized error handling via `.catch()`.

### 3. [[Async and Await]]
Syntactic sugar over Promises for synchronous-looking code:
```javascript
async function fetchData() 
{   
	try 
	{    
		const response = await fetch('https://api.example.com/data');    
		const data = await response.json();    
		console.log(data);  
	} catch (error) {    console.error(error);  } 
}
```
The `await` keyword pauses execution until the Promise resolves.

## Execution Mechanism
**Event Loop Architecture**  
JavaScript's runtime uses three key components:
- **Call Stack**: Tracks currently executing functions
- **Web APIs**: Handle async operations (timers, network requests)
- **Task Queue/Callback Queue**: Stores completed async tasks
```javascript
console.log("Start"); 
setTimeout(() => console.log("Timeout"), 0); 
console.log("End"); // Output: Start → End → Timeout
```
The event loop continuously checks:

1. If call stack is empty
    
2. Moves tasks from queue to stack
    
3. Executes them