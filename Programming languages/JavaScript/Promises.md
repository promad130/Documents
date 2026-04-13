A **Promise** is a built-in JavaScript object for managing asynchronous operations, such as fetching data from a server or waiting for a timer. It represents a value that may be available now, later, or never, and provides a structured way to handle success and failure without nesting callbacks.

---
## 1. Creating a Promise
You create a promise using the `Promise` constructor:
```javascript
let myPromise = new Promise(function(resolve, reject) 
	{   
		// Asynchronous operation here  
		// Call resolve(value) if successful  
		// Call reject(error) if failed 
	});
```
- **Promise()** is a constructor function. You must use `new Promise(...)` to create a promise object.
- **Executor function:** The constructor takes one argument, called the _executor function_. This function runs immediately and receives two parameters:
    - **resolve**: a function you call to mark the promise as fulfilled (successful).
    - **reject**: a function you call to mark the promise as rejected (failed).
    - Think of this like this, when you are running a promise, the code itself doesn't know when the task is success and when it is a failure. If an error occurs, then that is a different and that too can be handled using try-catch phases, but still there needs to be something to tell the language that the promise is now completed, or the promise has now failed, and that is why we have the resolve and reject.
- You can name these parameters anything (`resolve`, `reject`, `success`, `fail`, etc.), but you must use those names consistently inside your executor function.

### So, what about their parameters?
#### For `resolve(value)`:
- You can pass **any value** as the parameter:
    - A string, number, object, array, boolean, or even `undefined` or `null`.
    - You can also pass another Promise or a "thenable" (an object with a `then` method). If you do, the outer promise will adopt the state of that promise or thenable
#### For `reject(reason)`:
- You can pass **any value** as the parameter, but it's recommended to use an `Error` object for better debugging and stack traces.
    - String, number, object, etc., are all allowed.
    - For best practice, use: `reject(new Error("Something went wrong"))`
### What happens when you call them?
- The value passed to `resolve` becomes the result of the fulfilled promise and is received by the next `.then()` handler, and can be used as the parameter of the function to be executed in `.then()`
- The value passed to `reject` becomes the reason for rejection and is received by the next `.catch()` handler
(if u cannot grasp the concept of using this dot operator or what actually does the .then() and all mean, then I would suggest you check out [[Object Oriented Programming]])

**Example:**
```JS
new Promise((resolve, reject) => {
  resolve("Hello!");
}).then(result => {
  console.log(result); // "Hello!"
});

new Promise((resolve, reject) => {
  reject("Error happened");
}).catch(error => {
  console.log(error); // "Error happened"
});

```

```javascript
let promise = new Promise(function(success, failure) 
	{   
		setTimeout(() => success("Done!"), 1000); 
	});
```
---
## 2. Promise States
A promise can be in one of three states:

| State     | Description                                   |
| --------- | --------------------------------------------- |
| Pending   | Initial state; neither fulfilled nor rejected |
| Fulfilled | Operation completed successfully (resolved)   |
| Rejected  | Operation failed (rejected)                   |

- While pending, the promise is "working" and has no result.    
- When fulfilled, it has a result value.
- When rejected, it has a reason (usually an error object).

---
## 3. Consuming a Promise
You handle the result of a promise using its instance methods:
- **then(onFulfilled, onRejected)**:
    - Takes two optional callback functions, i.e., the function name only:
        - `onFulfilled`: runs if the promise is fulfilled.
        - `onRejected`: runs if the promise is rejected.
    - Returns a new promise, enabling chaining.
- **catch(onRejected)**:
    - Shortcut for `.then(null, onRejected)`. Handles errors.
- **finally(onFinally)**:
    - Runs regardless of the promise outcome (fulfilled or rejected).

**Example:**
```javascript
promise   
.then(result => console.log(result)) // "Done!" after 1 second  
.catch(error => console.error(error))  
.finally(() => console.log("Promise settled"));
```

A better example, where we want to create a list of task, append it first, convert it to list in HTML:
```JS
const posts = [
	{title: "Post 1", boyd: "This is the first post."},
	{title: "Post 2", boyd: "This is the second post."}
];

function getPost(){
	setTimeout(()=>
	{		
		let output = '';
		posts.forEach((post, index)=>
		{
			output += `<li>${post.title}</li>`;
		});
		document.body.innerHTML = output;
	}, 1000); //this would be 1 sec pf delay
}

function createPost(post){
	return new Promise((resolve, reject)=>{
		setTimeout(()=>{
			posts.push(post);
			const error = false;
			if(!error){
				resolve();
			}else{
				reject("Something went wrong!");
			}
		}, 2000);
	})
}

createPost({title:"Post 3", body:"This is post three"})
.then(getPost)
.catch(err => console.log(err));
```
So as the `error` is always `false`, the promise would always be `resolved`, and give out the expected result, i.e., a 3 item list in our main HTML page, 
and if it is set to `true`, then it will output the given statement in the console:
```text
Error: Something went wrong!
```

---
## 4. Static Methods
JavaScript provides several static methods for working with multiple promises:

| Method               | Description                                                                     |
| -------------------- | ------------------------------------------------------------------------------- |
| Promise.all()        | Waits for all promises to fulfill, or rejects if any reject                     |
| Promise.allSettled() | Waits for all promises to settle (fulfilled or rejected), returns their results |
| Promise.any()        | Resolves as soon as any promise fulfills, or rejects if all reject              |
| Promise.race()       | Settles as soon as any promise settles (fulfilled or rejected)                  |
| Promise.resolve()    | Returns a promise resolved with the given value                                 |
| Promise.reject()     | Returns a promise rejected with the given reason                                |

---
## 5. Parameters and Keywords
- **Promise() constructor:**
    
    - Takes one parameter: the executor function.
        
    - The executor function takes two parameters: commonly named `resolve` and `reject` (but can be any names)[1](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise/Promise)[5](https://www.codeguage.com/courses/advanced-js/promises-basics)[7](https://www.reddit.com/r/learnjavascript/comments/zaxnin/what_are_the_arguments_of_the_promise_object/).
        
- **resolve(value):**
    
    - Call this to fulfill the promise with `value`.
        
- **reject(reason):**
    
    - Call this to reject the promise with `reason` (often an error object).
        

**Example with custom parameter names:**

javascript

`let p = new Promise(function(succeed, fail) {   setTimeout(() => succeed("Hello!"), 500); }); p.then(msg => console.log(msg)); // "Hello!" after 0.5 seconds`

Here, `succeed` and `fail` are just custom names for the `resolve` and `reject` functions[5](https://www.codeguage.com/courses/advanced-js/promises-basics).

---

## 6. Promise Object Properties
- **state**: Internal state (`pending`, `fulfilled`, `rejected`). Not directly accessible.
- **result**: The value (if fulfilled) or error (if rejected). Not directly accessible.

You must use `.then()`, `.catch()`, or `.finally()` to access the result.


If the response file we are getting is of type text, then we do:
### .text()
The `.text()` method is a function available on the `Response` object returned by the Fetch API in JavaScript. It is used to read the body of a response and return it as a plain text string. Like other response methods (such as `.json()`), `.text()` itself returns a **promise** that resolves with the text content of the response.

---
#### How `.text()` Works in a Promise Chain
1. **Fetch returns a Promise:**  
    When you call `fetch(url)`, it returns a promise that resolves to a `Response` object.
2. **`.text()` returns a Promise:**  
    When you call `.text()` on the response, it returns another promise, which resolves with the body content as a string.

#### Example:
```javascript
fetch('https://example.com/file.txt')   
.then(response => response.text()) // returns a promise for the text  
.then(text => {    
	console.log(text); // text contains the plain text response body  
})  
.catch(error => {    console.error('Error:', error);  });
```
- The first `.then()` receives the `Response` object and calls `.text()`, which returns a promise for the text.
- The second `.then()` receives the resolved text value.

---
#### When to Use `.text()`
- Use `.text()` when you expect the server to return plain text, HTML, or any content you want as a raw string.
- For JSON data, use `.json()` instead, which parses the response as JSON.

---
And if the response file is of type JSON, then we do:
### .json()
The `.json()` method is a function available on the `Response` object returned by the Fetch API in JavaScript. It is used to parse the body of an HTTP response as JSON and returns a **promise** that resolves with the parsed JavaScript object or array.

---
#### How `.json()` Works in a Promise Chain
1. **Fetch returns a Promise:**  
    `fetch(url)` returns a promise that resolves to a `Response` object.
2. **`.json()` returns a Promise:**  
    Calling `.json()` on the response returns another promise, which resolves with the result of parsing the response body as JSON.

## Example
```javascript
fetch('https://api.example.com/data')
  .then(response => response.json()) // returns a promise for the parsed JSON
  .then(data => {
    console.log(data); // data is a JavaScript object or array
  })
  .catch(error => {
    console.error('Error:', error);
  });
```
- The first `.then()` receives the `Response` object and calls `.json()`, which parses the body and returns a promise.
- The second `.then()` receives the parsed JavaScript object or array returned by the previous `.then()`.

---

## When to Use `.json()`

- Use `.json()` when you expect the server to return data in JSON format (the standard for most APIs).
    
- It automatically parses the JSON string into a JavaScript object, so you don't need to use `JSON.parse()` manually.
    

---

## Key Points

- `.json()` is asynchronous and returns a promise.
    
- It is the standard way to handle JSON responses from web APIs in JavaScript.
    
- It fits naturally into promise chains and async/await code.
    

---
