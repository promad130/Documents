==Asynchronous operations allow a program to perform tasks independently without blocking the main thread, enabling it to handle multiple requests concurrently==. Conversely, synchronous operations execute tasks sequentially, with the program waiting for one task to complete before proceeding to the next.

Asynchronous JavaScript enables non-blocking execution of tasks like network requests or file operations, crucial for maintaining responsiveness in single-threaded JavaScript. 

When we say "JavaScript enables non-blocking execution of tasks," it means JavaScript can start a time-consuming operation (like reading a file or making a network request) and continue running other code without waiting for that operation to finish. This is achieved through asynchronous programming, using callbacks, promises, or async/await.
This is important as in JS, code is executed line by line, and same applies for the functions. So a function written first has to be executed first, and then the next function and so on. This is slow and not optimized as we would want multiple thing in a code to happen in parallel at once when we run the script when it comes to things like Network programming.

An analogy for this is a busy restaurant. Picture a waiter who refuses to wait on other tables until the table they’re presently serving has received their orders and has started eating. While the food is being prepared, the waiter waits around doing nothing and only approaches your table to take your order when they are completely finished with the previous table. Needless to say, the waiter may not receive a great tip for that service.

This is what synchronous code is. It halts the execution of other processes until it’s complete. You can see how it works in the example below:

```javascript
const syncWaiter = (name) => {
    console.log(`${name} attends to tables pretty slowly.`);
};

syncWaiter("Devin");
console.log("At least all the orders are correct!");
```

The code above will be run in sequence, in the order it appears.

Asynchronous code, unlike synchronous code, doesn’t halt all other processes until one task is executed – rather, it proceeds to carry out other tasks while a longer process runs in the background.

Using our waiter analogy, in this case the waiter in the restaurant would go take an order from one table, pass the order to the kitchen, and while it’s being prepared, proceeds to your table to take your order as well. This way, the waiter is able to ensure different processes are started even if one process takes a bit longer than the rest. Check out the example below:

```javascript
const asyncWaiter = (name) => {
    setTimeout(()=> {console.log(`${name} attends to tables pretty quickly.`)}, 3000)
};

asyncWaiter("James");
console.log("Wow! All the tables are attended to in a short time.");
```

Unlike synchronous code, this code does run the function `asyncWaiter()` – but the callback inside the function executes later. When the duration elapses, the result is then shown on the screen. This is why asynchronous programs are referred to as _non-blocking._ They don’t halt the program, but move from one available task to another.

The code above returns the following:

```bash
Wow! All the tables are attended to in a short time.
James attends to tables pretty quickly.
```

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

# Event Loop

Now, looking at the waiter analogy that we discussed before,
```bash
Wow! All the tables are attended to in a short time.
James attends to tables pretty quickly.
```

This print order happens because of how the _event loop_ manages tasks: the synchronous `console.log()` that comes after `asyncWaiter()` runs immediately, while the asynchronous callback inside `asyncWaiter()` (from `setTimeout`) is scheduled to run later. If you don’t understand this just yet, don’t worry as I’ll break it down in detail shortly.

## What Concurrency and Parallelism Mean

Node.js is single-threaded but often gives the appearance of a multi-threaded environment due to how it handles concurrency and parallelism. A thread is a single sequence of instructions executed by the CPU independently. Think of it like a single waiter named James in a restaurant.

If James handles multiple tasks around the same time and quickly, an onlooker outside the restaurant who sees the number of customers moving in and out of the restaurant may assume that there are a ton of waiters serving tables. In reality, James just handles his tasks asynchronously.

Before grasping the concept of the event loop, it’s good to understand what concurrency and parallelism are, as they help explain this.

### Concurrency in Node.js

Concurrency means having multiple processes run around the same time. In the waiter analogy, it is like James carrying out different tasks, though not simultaneously. He could, for instance take an order from a table and, while waiting for the food to arrive, request that extra salt be provided to another table. While the salt is on its way, he uses the waiting time to read the bill to a third table.

The key idea is that James never sits idle — he works on other tasks while waiting for one to finish. If this sounds an awful lot like asynchronous programming, it is because asynchronous code is just one way to achieve concurrency.

Other ways to execute concurrency are [multithreading](https://www.freecodecamp.org/news/multithreading-for-beginners/) on a single CPU core and [coroutines](https://www.freecodecamp.org/news/how-to-handle-concurrency-in-go/) which are just functions that pause their execution to resume at a later time.

### Parallelism in Node.js

Parallelism, on the other hand, also means having several tasks run at the same time – but instead of the tasks just being processed around the same time, they are executed at exactly the same time, simultaneously. In this case, the restaurant manager decides to hire multiple waiters and each table has a waiter who is taking orders at exactly the same time.

Parallelism can be achieved using multithreading on multiple CPU cores. In this setup, the threads share the same memory and run simultaneously while using clusters which run independently – each with its own memory space. Here’s a clear example of parallelism using the `worker_threads` module:

```javascript
const { Worker}  = require('worker_threads');

new Worker('./worker.js');
new Worker('./worker.js');
new Worker('./worker.js');

console.log("Main thread keeps running in the process...");
```

The code above creates three worker threads in parallel on a multi-core machine. This doesn’t stop the main thread which continues to run, allowing each worker thread independently do its task. `worker.js` could be a simple file carrying out any task. In this case, it simply logs a message to the screen:

```javascript
console.log("This worker thread is running here!");
```

Note that the argument for the `Worker` constructor can be any file path, and the order in which they are executed isn’t dependent on the order they appear in code. Each worker runs independently of the others and they run in parallel.

---
## What is the Event Loop?

The event loop is the mechanism that allows JavaScript—a single-threaded language—to handle asynchronous operations without blocking execution. It's the core engine that decides **which code runs, when it runs, and in what order**.

Think of it like a manager at a desk (the call stack) who processes tasks one at a time. When the desk is clear, the manager checks a filing system (callback queues) for more work. That manager is constantly looping, checking for new tasks.

---
## The Core Components

### 1. **Call Stack**

The call stack is where code currently being executed lives. JavaScript processes one thing at a time here—synchronous operations run immediately.

```JavaScript
function greet() {
  console.log("Hello");
}

function sayGoodbye() {
  console.log("Goodbye");
}

greet();      // Added to call stack → executes → removed
sayGoodbye(); // Added to call stack → executes → removed

// Output:
// Hello
// Goodbye
```

In this example, both functions are synchronous. The call stack processes them sequentially: `greet()` runs, completes, gets removed, then `sayGoodbye()` runs.

### 2. **Web APIs / Node.js APIs**

When you call async functions like `setTimeout`, `fetch`, or `fs.readFile`, they don't execute in the call stack. Instead, they're handed off to **Web APIs** (browser) or **Node APIs** (Node.js) which run in parallel. These are asynchronous operations provided by the browser (or Node.js). They handle things like:

- `setTimeout()` / `setInterval()`
- `fetch()`
- `DOM events`
- `fs` operations (Node.js)
- `Promises`

When you call these, they offload work to the Web API environment, freeing up the call stack.

```JavaScript
console.log("Start");

setTimeout(() => {
  console.log("Timeout callback");
}, 1000);

console.log("End");

// Output:
// Start
// End
// Timeout callback (after 1 second)
```

Here, `setTimeout()` doesn't block execution. The callback goes to the Web APIs, the stack continues, and later the callback comes back.


### 3. **Callback Queue (Macrotask Queue)**

Asynchronous callbacks wait here until the call stack is empty. This includes:

- `setTimeout()` callbacks
- `setInterval()` callbacks
- `setImmediate()` (Node.js)
- DOM events (click, input, etc.)

### 4. **Microtask Queue**

Higher priority than the callback queue. Includes:

- `Promise` callbacks (`.then()`, `.catch()`, `.finally()`)
- `async/await` (which is syntactic sugar for Promises)
- `queueMicrotask()` (explicit API)
- `MutationObserver`

---

## The Event Loop Algorithm

Here's the critical sequence:

```Code
1. Execute all synchronous code in the call stack
2. Call stack is empty? 
   → Check microtask queue (process ALL pending microtasks)
   → Check callback queue (process ONE macrotask)
   → Check microtask queue again
   → Repeat from step 2
```

**Key insight**: The microtask queue is checked completely before moving to the next macrotask.

## Example 1: Basic Synchronous Code

```JavaScript
console.log('1. Start');

function add(a, b) {
  return a + b;
}

const result = add(5, 3);
console.log('2. Result:', result);

console.log('3. End');
```

**Output:**

```Code
1. Start
2. Result: 8
3. End
```

**Call Stack Visualization:**

```Code
STEP 1:
┌─────────────────────┐
│  console.log('1')   │
│  add(5, 3)          │
│  console.log('2')   │
│  console.log('3')   │
└─────────────────────┘

All synchronous code executes immediately, in order.
No event loop involved yet.
```

---

## Example 2: Introducing Asynchronous Code (setTimeout)

```JavaScript
console.log('1. Script start');

setTimeout(() => {
  console.log('2. setTimeout callback');
}, 0);

console.log('3. Script end');
```

**Output:**

```Code
1. Script start
2. Script end
3. setTimeout callback
```

**Visual Breakdown - Step by Step:**

```Code
STEP 1 - Script executes:
┌─────────────────────────────┐
│ Call Stack                  │
├─────────────────────────────┤
│ console.log('1')            │ ← executes immediately
└─────────────────────────────┘

STEP 2 - setTimeout encountered:
┌─────────────────────────────┐
│ Call Stack                  │
├─────────────────────────────┤
│ setTimeout()                │ ← PASSED TO WEB/NODE API
└─────────────────────────────┘

┌─────────────────────────────┐
│ Web/Node APIs (background)  │
├─────────────────────────────┤
│ setTimeout(0ms timer)       │ ← Running the timer
└─────────────────────────────┘

STEP 3 - Continue sync code:
┌─────────────────────────────┐
│ Call Stack                  │
├─────────────────────────────┤
│ console.log('3')            │ ← executes immediately
└─────────────────────────────┘

Output so far:
1. Script start
2. Script end

STEP 4 - setTimeout completes:
┌─────────────────────────────┐    ┌──────────────────────┐
│ Call Stack                  │    │ Macrotask Queue      │
├─────────────────────────────┤    ├──────────────────────┤
│ (empty)                     │    │ setTimeout callback  │ ← waiting
└─────────────────────────────┘    └──────────────────────┘

STEP 5 - Event Loop picks up macrotask:
┌─────────────────────────────┐
│ Call Stack                  │
├─────────────────────────────┤
│ console.log('setTimeout')   │ ← now executes
└─────────────────────────────┘

Output:
2. setTimeout callback
```

---

## Example 3: Promises (Microtask Priority)

```JavaScript
console.log('1. Start');

Promise.resolve()
  .then(() => {
    console.log('2. Promise 1');
  })
  .then(() => {
    console.log('3. Promise 2');
  });

setTimeout(() => {
  console.log('4. setTimeout');
}, 0);

console.log('5. End');
```

**Output:**

```Code
1. Start
2. End
3. Promise 1
4. Promise 2
5. setTimeout
```

**Why? Microtasks run BEFORE macrotasks!**

**Visual Breakdown:**

```Code
STEP 1 - Synchronous code executes:
┌────────────────────────┐
│ Call Stack             │
├────────────────────────┤
│ console.log('1')       │ ✅ outputs: 1. Start
└────────────────────────┘

STEP 2 - Promise encountered (async):
┌────────────────────────┐
│ Call Stack             │
├────────────────────────┤
│ Promise.resolve()      │ ← handed to APIs
└────────────────────────┘

┌────────────────────────┐
│ Microtask Queue        │
├────────────────────────┤
│ .then(() => {...})     │ ← queued here (NOT in macrotask!)
└────────────────────────┘

STEP 3 - setTimeout encountered:
┌────────────────────────┐    ┌──────────────────────┐
│ Microtask Queue        │    │ Macrotask Queue      │
├────────────────────────┤    ├──────────────────────┤
│ .then callbacks        │    │ setTimeout callback  │
└────────────────────────┘    └──────────────────────┘

STEP 4 - Synchronous code ends:
┌────────────────────────┐
│ Call Stack             │
├────────────────────────┤
│ console.log('5')       │ ✅ outputs: 5. End
└────────────────────────┘

STEP 5 - Call stack empty! Event loop checks microtask queue:
┌────────────────────────────────┐
│ Call Stack                     │
├────────────────────────────────┤
│ .then(() => {...})             │ ← microtask executes
└────────────────────────────────┘

✅ outputs: 2. Promise 1

STEP 6 - Second promise then:
┌────────────────────────┐
│ Call Stack             │
├────────────────────────┤
│ .then(() => {...})     │
└────────────────────────┘

✅ outputs: 3. Promise 2

STEP 7 - ALL microtasks done! Now check macrotask:
┌────────────────────────┐
│ Call Stack             │
├────────────────────────┤
│ setTimeout callback    │ ← NOW it can run
└────────────────────────┘

✅ outputs: 4. setTimeout
```

---

## Example 4: The Classic Interview Question

```JavaScript
console.log('A');

setTimeout(() => {
  console.log('B');
}, 0);

Promise.resolve()
  .then(() => {
    console.log('C');
  })
  .then(() => {
    console.log('D');
  });

console.log('E');
```

**Output:**

```Code
A
E
C
D
B
```

**Complete Timeline:**

Code

```
TIME 1: Script starts
┌──────────────────┐
│ Call Stack       │
├──────────────────┤
│ console.log('A') │ ✅ A
└──────────────────┘

TIME 2: setTimeout queued
┌──────────────────┐     ┌──────────────────┐
│ Call Stack       │     │ Macrotask Queue  │
├──────────────────┤     ├──────────────────┤
│ setTimeout()     │ ──→ │ log('B')         │
└──────────────────┘     └──────────────────┘

TIME 3: Promise queued
┌──────────────────┐     ┌──────────────────┐
│ Call Stack       │     │ Microtask Queue  │
├──────────────────┤     ├──────────────────┤
│ Promise.resolve()│ ──→ │ .then(log 'C')   │
│                  │     │ .then(log 'D')   │
└──────────────────┘     └──────────────────┘

TIME 4: Last sync code
┌──────────────────┐
│ Call Stack       │
├──────────────────┤
│ console.log('E') │ ✅ E
└──────────────────┘

TIME 5: Stack empty! Process ALL microtasks
┌──────────────────┐     ┌──────────────────┐
│ Call Stack       │     │ Microtask Queue  │
├──────────────────┤     ├──────────────────┤
│ .then(log 'C')   │ ✅ C│                  │
└──────────────────┘     └──────────────────┘

TIME 6: Process next microtask
┌──────────────────┐
│ Call Stack       │
├──────────────────┤
│ .then(log 'D')   │ ✅ D
└──────────────────┘

TIME 7: Microtask queue empty! Now check macrotask
┌──────────────────┐     ┌──────────────────┐
│ Call Stack       │     │ Macrotask Queue  │
├──────────────────┤     ├──────────────────┤
│ log('B')         │ ✅ B│                  │
└──────────────────┘     └──────────────────┘
```

---

## Example 5: Complex Real-World Scenario

JavaScript

```
console.log('Start');

setTimeout(() => {
  console.log('Timeout 1');
  Promise.resolve().then(() => {
    console.log('Promise inside Timeout 1');
  });
}, 0);

Promise.resolve()
  .then(() => {
    console.log('Promise 1');
    setTimeout(() => {
      console.log('Timeout inside Promise 1');
    }, 0);
  })
  .then(() => {
    console.log('Promise 2');
  });

console.log('End');
```

**Output:**

Code

```
Start
End
Promise 1
Promise 2
Timeout 1
Promise inside Timeout 1
Timeout inside Promise 1
```

**Detailed Execution:**

Code

```
PHASE 1: Synchronous execution
┌──────────────────┐
│ Call Stack       │
├──────────────────┤
│ console.log      │ ✅ "Start"
│ console.log      │ ✅ "End"
└──────────────────┘

QUEUES AFTER SYNC:
┌──────────────────┐     ┌──────────────────┐
│ Microtask Queue  │     │ Macrotask Queue  │
├──────────────────┤     ├──────────────────┤
│ Promise.then(1)  │     │ setTimeout(1)    │
│ Promise.then(2)  │     │                  │
└──────────────────┘     └──────────────────┘

PHASE 2: Process ALL microtasks
✅ "Promise 1" (Promise.then executes)
  ├─ setTimeout pushed to macrotask queue
✅ "Promise 2" (second Promise.then executes)

CURRENT QUEUES:
┌──────────────────┐     ┌──────────────────────┐
│ Microtask Queue  │     │ Macrotask Queue      │
├──────────────────┤     ├──────────────────────┤
│ (empty)          │     │ setTimeout(1)        │
│                  │     │ setTimeout(inside P) │
└──────────────────┘     └──────────────────────┘

PHASE 3: Process ONE macrotask
✅ "Timeout 1"
  ├─ Promise pushed to microtask queue

CURRENT QUEUES:
┌──────────────────┐     ┌──────────────────┐
│ Microtask Queue  │     │ Macrotask Queue  │
├──────────────────┤     ├──────────────────┤
│ Promise.then(3)  │     │ setTimeout(I.P)  │
└──────────────────┘     └──────────────────┘

PHASE 4: Process ALL microtasks again
✅ "Promise inside Timeout 1"

PHASE 5: Process next macrotask
✅ "Timeout inside Promise 1"

Done!
```

---

## Example 6: Node.js Specific - Event Loop Phases

In Node.js, the event loop has specific phases:

JavaScript

```
const fs = require('fs');

console.log('1. Script start');

// Phase: Timers
setTimeout(() => {
  console.log('2. setTimeout');
}, 0);

// Phase: I/O operations (Poll phase)
fs.readFile(__filename, () => {
  console.log('3. fs.readFile');
  
  // Inside I/O callback
  Promise.resolve().then(() => {
    console.log('4. Promise inside fs');
  });
  
  setImmediate(() => {
    console.log('5. setImmediate inside fs');
  });
});

// Phase: Check phase (setImmediate)
setImmediate(() => {
  console.log('6. setImmediate');
});

// Microtask
Promise.resolve().then(() => {
  console.log('7. Promise');
});

console.log('8. Script end');
```

**Output:**

Code

```
1. Script start
2. Script end
3. Promise
4. setTimeout
5. fs.readFile
6. Promise inside fs
7. setImmediate inside fs
8. setImmediate
```

**Why this order?**

Code

```
INITIAL PHASE - Synchronous execution:
✅ 1. Script start
✅ 8. Script end

PHASE 1 - Process microtasks:
✅ 7. Promise

PHASE 2 - Timers phase:
✅ 2. setTimeout (0ms elapsed)

PHASE 3 - Poll phase (I/O operations):
✅ 3. fs.readFile completes
   └─ Microtask created: Promise inside fs
   └─ Macrotask queued: setImmediate inside fs

PHASE 4 - Check phase (setImmediate):
   └─ First process microtask from I/O:
      ✅ 4. Promise inside fs
   └─ Then execute setImmediate from check phase:
      ✅ 6. setImmediate
   └─ Then execute setImmediate from I/O callback:
      ✅ 5. setImmediate inside fs
```

---

## Key Takeaways

|Concept|Priority|Queue|When it runs|
|---|---|---|---|
|Synchronous code|1 (highest)|Call Stack|Immediately|
|Microtasks (Promises, process.nextTick)|2|Microtask Queue|After call stack empties, BEFORE macrotasks|
|Macrotasks (setTimeout, I/O)|3 (lowest)|Macrotask Queue|After ALL microtasks complete|

**The Golden Rule:**

> **Process ALL synchronous code → Process ALL microtasks → Process ONE macrotask → Check for microtasks → Repeat**

This is why promises run before setTimeout, even if setTimeout has 0 delay!


Now that we have covered things in basic, it is time to get in detail of event loop. This is specially important if we intent to make back-end for any application or web-service or anything at all:

![[Event Loop in JS in Detail]]