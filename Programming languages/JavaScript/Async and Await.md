`async` and `await` are modern JavaScript keywords that make working with asynchronous code (like promises) easier and more readable. They allow you to write asynchronous code that looks and behaves like synchronous code, making it simpler to follow and maintain.

---
## 1. The `async` Keyword
- **Purpose:**  
    The `async` keyword is placed before a function declaration or expression to declare it as an asynchronous function.
- **Effect:**  
    An async function always returns a Promise, even if you return a non-promise value. The returned value is automatically wrapped in a resolved promise.

**Example:**
```javascript
async function sayHello() 
{   
	return "Hello"; 
} 

sayHello().then(result => console.log(result)); // "Hello"
```
This is equivalent to:
```javascript
function sayHello() 
{   
	return Promise.resolve("Hello"); 
}
```
---

## 2. The `await` Keyword
- **Purpose:**  
    The `await` keyword can only be used inside an async function (or at the top level of a module). It pauses the execution of the function until the awaited Promise settles (either fulfilled or rejected).
- **Effect:**
    - If the awaited promise resolves, `await` returns the resolved value.
    - If the promise rejects, `await` throws the rejection reason as an exception, which you can catch with `try...catch`.

**Syntax:**
```javascript
let result = await promise;
```
**Example:**
```javascript
async function fetchData() 
{   
	let response = await fetch('https://api.example.com/data');  
	let data = await response.json();  
	console.log(data); 
} 

fetchData();
```
Here, the code "waits" at each `await` until the promise resolves, then continues.

---

## 3. Control Flow and Error Handling

- **Pausing:**  
    When `await` is encountered, the async function is paused at that point until the promise settles. Other code outside the function keeps running[3](https://javascript.info/async-await)[4](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/await)[5](https://blog.postman.com/understanding-async-await-in-node-js/)[6](https://www.programiz.com/javascript/async-await)[8](https://www.theodinproject.com/lessons/node-path-javascript-async-and-await).
    
- **Error Handling:**  
    Use `try...catch` to handle errors just like synchronous code:
```javascript
async function getData() 
{   
	try 
	{    
		let response = await fetch('bad-url');    
		let data = await response.json();    
		
		console.log(data);  
	} catch (error) 
	{    
		console.error(error); // Handles network or parsing errors  
	} 
}
```

---

## 4. What Can You Await?

- **Promises:**  
    Most commonly, you await a promise—like from `fetch()`, timers, or your own promise-returning functions.
    
- **Thenables:**  
    Any object with a `.then()` method can be awaited.
    
- **Non-promises:**  
    If you await a non-promise value, it is converted to a resolved promise and returned immediately[4](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/await).
    

**Example:**
```javascript
async function demo() 
{   
	let a = await 42; // 42  
	let b = await Promise.resolve("hi"); // "hi"  
	let c = await { then: resolve => resolve("thenable!") }; // "thenable!"  
	console.log(a, b, c); 
} 
demo();
```
---

## 5. Key Rules and Limitations

- `await` can **only** be used inside async functions or at the top level of ES modules.
- An async function **always** returns a promise, even if you return a plain value.
- You can use multiple `await` statements in a row; each one will pause until the promise resolves.

---

## 6. Practical Example
```javascript
function wait(ms) 
{   
	return new Promise(resolve => setTimeout(resolve, ms)); 
} 

async function doTasks() 
{   
	console.log("Start");  
	await wait(1000); // Waits 1 second  
	console.log("1 second passed");  
	await wait(2000); // Waits 2 more seconds  
	console.log("2 more seconds passed"); 
} 

doTasks(); 
// Output: 
// Start 
// (after 1 second) 1 second passed 
// (after 2 more seconds) 2 more seconds passed
```
---

## 7. Benefits of async/await

- **Readability:** Code looks synchronous, easier to follow[3](https://javascript.info/async-await)[5](https://blog.postman.com/understanding-async-await-in-node-js/)[6](https://www.programiz.com/javascript/async-await)[8](https://www.theodinproject.com/lessons/node-path-javascript-async-and-await).
    
- **Error Handling:** Use normal `try...catch` for async errors[2](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function)[3](https://javascript.info/async-await)[5](https://blog.postman.com/understanding-async-await-in-node-js/)[6](https://www.programiz.com/javascript/async-await).
    
- **No Callback Hell:** Avoids deeply nested callbacks and complex `.then()` chains[3](https://javascript.info/async-await)[5](https://blog.postman.com/understanding-async-await-in-node-js/)[8](https://www.theodinproject.com/lessons/node-path-javascript-async-and-await).
    

---

## 8. Summary Table

|Keyword|Where Used|What It Does|
|---|---|---|
|async|Before function|Makes function return a promise; allows `await`|
|await|Inside async func|Pauses until promise settles, returns its value|

---

**In summary:**

- `async` marks a function as asynchronous, making it always return a promise.
    
- `await` pauses inside an async function until a promise is resolved or rejected, letting you write asynchronous code in a synchronous style.
    
- Use `try...catch` for error handling, and remember that `await` only works inside async functions or modules[1](https://www.w3schools.com/js/js_async.asp)[2](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function)[3](https://javascript.info/async-await)[4](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/await)[5](https://blog.postman.com/understanding-async-await-in-node-js/)[6](https://www.programiz.com/javascript/async-await)[8](https://www.theodinproject.com/lessons/node-path-javascript-async-and-await).