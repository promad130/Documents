## Definition
A **callback function** is a function that is passed as an argument to another function and is then invoked (called) inside that outer function to complete some kind of routine or action.

## How Callbacks Work
When you pass a function as an argument to another function, that function can "call back" to your function at the appropriate time and with the given arguments when we callback that function. 
For example, with asynchronous tasks like timers, network requests, or event handling, callbacks let you specify what should happen after the task finishes.
1. **Passing the Function:**  
    You pass a **function reference**(the callback) as an argument to another function.
2. **Invoking the Callback:**  
    The receiving function is responsible for calling (invoking) the callback at the appropriate time. If the callback is never called, nothing happens.
3. **Naming:**  
    The parameter name in the receiving function (often `callback`, but can be any valid name) is just a reference to the function you passed in.

**Example: setTimeout**
```javascript
setTimeout(function() {   console.log("This runs after 2 seconds"); }, 2000);
```
Here, [[setTimeout()]] is a function, and the anonymous function is a callback that executes after 2 seconds.

**Example: Passing Named Functions**
```javascript
function greet(name) 
{   
	console.log("Hello, " + name); 
} 

function processUserInput(callbackFunction) 
{   
	const name = "Alice";  
	callbackFunction(name); 
} 

processUserInput(greet); // Output: Hello, Alice
```
In this example, `greet` is passed as a callback to `processUserInput`, which calls it after getting the user's name.

**Asynchronous Callbacks**  
Callbacks are especially useful for asynchronous operations, such as:
- Fetching data from a server
- Reading files
- Responding to user events (like clicks)

For instance:
```javascript
function fetchData(callback) 
{   
	setTimeout(() => 
	{    
		console.log("Data fetched");    
		callback();  
	}, 2000); 
} 

function processData() 
{   
	console.log("Processing data..."); } fetchData(processData); // Output after 2 seconds: Data fetched // Then: Processing data...`
```
Here, `processData` is called only after the simulated data fetch completes[3](https://dev.to/jps27cse/exploring-asynchronous-javascript-callbacks-promises-and-asyncawait-16k6).

**Drawbacks: Callback Hell**  
When multiple asynchronous operations depend on each other, callbacks can become deeply nested and hard to read—a situation known as "callback hell"[2](https://developer.mozilla.org/en-US/docs/Learn_web_development/Extensions/Async_JS/Introducing)[5](https://www.stoman.me/articles/async-await-promises-callbacks-in-javascript). For example:

```javascript
doStep1(function(result1) 
	{   
		doStep2(result1, function(result2) 
			{    doStep3(result2, function(result3) 
				{      
					// and so on...    
				});  
			}); 
	}
);
```
This complexity is why modern JavaScript often uses Promises and async/await for asynchronous code.

**Summary Table**

| Term         | Description                                                  |
| ------------ | ------------------------------------------------------------ |
| Callback     | Function passed to another function to be executed later     |
| Usage        | Asynchronous tasks (timers, network, events), modular code   |
| Drawbacks    | Nested callbacks can become hard to manage ("callback hell") |
| Alternatives | Promises, async/await (modern JavaScript)                    |
