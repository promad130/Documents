## Part 1: What is I/O?

**I/O** = **Input/Output**

In simple terms: **any operation that communicates with something outside your program**.

### Types of I/O:

```javascript
// 1. FILE SYSTEM I/O - Reading/Writing files on disk
fs.readFile('data.txt', callback);
fs.writeFile('data.txt', 'content', callback);

// 2. NETWORK I/O - Communicating over the network
http.get('https://api.example.com', callback);
socket.send(data);
socket.on('data', callback);

// 3. DATABASE I/O - Querying a database
db.query('SELECT * FROM users', callback);

// 4. STREAM I/O - Reading/Writing large amounts of data in chunks
fs.createReadStream('large-file.txt');
```

### Why I/O Matters

All these operations are **slow**. A typical timeline:

```
CPU operation: 1 nanosecond (1 billionth of a second)
Memory access: 100 nanoseconds
Disk I/O: 10,000,000 nanoseconds (10 milliseconds)
Network I/O: 100,000,000+ nanoseconds (100+ milliseconds)
```

**The problem with synchronous I/O:**

```javascript
// BAD: Synchronous approach (if Node had this)
const data = fs.readFileSync('file.txt'); // BLOCKS for 10ms
console.log('After read');                 // Waits 10ms before running

// During those 10ms, the entire program is frozen
// No other code can run
// No other users can be served
```

**Why Node.js uses asynchronous I/O:**

```javascript
// GOOD: Asynchronous approach (what Node actually does)
fs.readFile('file.txt', (err, data) => {
  console.log('File read complete');       // Runs LATER
});

console.log('After scheduling read');      // Runs IMMEDIATELY

// The program continues executing
// Other code can run while the file is being read
// Other users can be served
```

This is the core philosophy of Node.js: **Use asynchronous I/O to avoid blocking.**

---

## Part 2: How Node.js Executes I/O Operations

Node.js doesn't actually read files or make network requests itself. It can't — it's single-threaded JavaScript. Instead, it **delegates** to the operating system.

### The Architecture:

```
┌─────────────────────────────────────┐
│     Your JavaScript Code            │
│  (Single-threaded, runs callbacks)  │
└────────────────────┬────────────────┘
                     │
                     ↓ (delegates I/O)
┌─────────────────────────────────────┐
│     libuv Library (C/C++)           │
│   (Event loop + Thread pool)        │
└────────────────────┬────────────────┘
                     │
            ┌────────┴────────┐
            ↓                 ↓
       ┌─────────┐      ┌───────────────┐
       │ Timers  │      │ Thread Pool   │
       └─────────┘      │ (I/O tasks)   │
                        └───────────────┘
                             │
                ┌────────────┬┴──────────┐
                ↓            ↓           ↓
            ┌──────┐    ┌──────┐    ┌──────┐
            │File  │    │Net   │    │OS    │
            │Sys   │    │Sys   │    │Kernel│
            └──────┘    └──────┘    └──────┘
```

### Step-by-Step: What Happens When You Call fs.readFile()

```javascript
const fs = require('fs');

console.log('1. Before readFile');

fs.readFile('data.txt', (err, data) => {
  console.log('3. File read complete:', data);
});

console.log('2. After readFile');
```

**Output:**
```
1. Before readFile
2. After readFile
3. File read complete: [file contents]
```

**Behind the scenes:**

```
TIME 0ms: "Before readFile" prints
┌──────────────────────────────────┐
│ Call Stack (JavaScript)          │
├──────────────────────────────────┤
│ console.log('1. Before...')       │ ✅ executes immediately
└──────────────────────────────────┘

TIME 1ms: fs.readFile is called
┌──────────────────────────────────┐
│ Call Stack (JavaScript)          │
├──────────────────────────────────┤
│ fs.readFile('data.txt', ...)     │
│ ├─ Callback added to libuv       │
│ └─ libuv delegates to OS kernel  │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│ libuv Thread Pool                │
├──────────────────────────────────┤
│ Read 'data.txt' from disk        │ ← Background work
│ (Main thread continues)          │
└──────────────────────────────────┘

TIME 2ms: "After readFile" prints
┌──────────────────────────��───────┐
│ Call Stack (JavaScript)          │
├──────────────────────────────────┤
│ console.log('2. After...')        │ ✅ executes immediately
└──────────────────────────────────┘

(Main JavaScript thread is FREE now)

TIME 10ms: File read completes (in background)
┌──────────────────────────────────┐
│ libuv                            │
├──────────────────────────────────┤
│ File read finished!              │
│ Callback ready to execute        │
│ Add to Poll Queue                │
└──────────────────────────────────┘

TIME 10-15ms: Event loop executes the callback
┌──────────────────────────────────┐
│ Call Stack (JavaScript)          │
├──────────────────────────────────┤
│ console.log('3. File read...')    │ ✅ callback executes
└──────────────────────────────────┘
```

**Key insight**: The callback is NOT executed when the file is read. It's added to a queue, and the event loop executes it when the call stack is empty.

---

## Part 3: The libuv Thread Pool

Node.js uses a **thread pool** for I/O operations. By default, it has **4 threads**.

### How the Thread Pool Works:

```javascript
const fs = require('fs');

// All these operations go to the thread pool
fs.readFile('file1.txt', callback1);
fs.readFile('file2.txt', callback2);
fs.readFile('file3.txt', callback3);
fs.readFile('file4.txt', callback4);
fs.readFile('file5.txt', callback5);  // This one waits
```

**Execution:**

```
TIME 0ms: All 5 readFile calls scheduled
┌─────────────────────────────────────┐
│ Thread Pool (4 threads)             │
├─────────────────────────────────────┤
│ Thread 1: Reading file1.txt         │
│ Thread 2: Reading file2.txt         │
│ Thread 3: Reading file3.txt         │
│ Thread 4: Reading file4.txt         │
│ Waiting: file5.txt (queued)         │
└─────────────────────────────────────┘

TIME 5ms: One thread finishes
┌─────────────────────────────────────┐
│ Thread Pool (4 threads)             │
├─────────────────────────────────────┤
│ Thread 1: Reading file5.txt (now!)  │ ← Free thread picks up file5
│ Thread 2: Reading file2.txt         │
│ Thread 3: Reading file3.txt         │
│ Thread 4: Reading file4.txt         │
└─────────────────────────────────────┘

(This happens in parallel with JavaScript execution)
```

### You Can Change the Thread Pool Size:

```javascript
const os = require('os');
process.env.UV_THREADPOOL_SIZE = os.cpus().length;

// Now the thread pool will have as many threads as CPU cores
// More threads = can handle more concurrent I/O operations
```

---

## Part 4: The Event Loop's Poll Phase (I/O Cycle)

Now we connect I/O back to the event loop. Remember the 6 phases?

```
Timers → Pending → Poll ← Check → Close → [back to Timers]
```

The **Poll phase** is where I/O callbacks are executed. This is the "I/O cycle" I mentioned.

### What Happens in the Poll Phase:

```
POLL PHASE:
1. Check the poll queue for completed I/O operations
2. If callbacks are waiting:
   - Execute them synchronously (one at a time)
3. If poll queue is empty:
   - Wait for new I/O events from the kernel
   - Or continue to next phase if setImmediate is scheduled
```

### Visual Example:

```javascript
const fs = require('fs');

console.log('Script start');

fs.readFile('file.txt', () => {
  console.log('Inside I/O callback (Poll phase)');
  // ← We are IN the Poll phase right now
  // ← This is "in an I/O cycle"
});

setTimeout(() => {
  console.log('Timeout (Timers phase)');
}, 0);

console.log('Script end');
```

**Output:**
```
Script start
Script end
Inside I/O callback (Poll phase)
Timeout (Timers phase)
```

**Timeline:**

```
SCRIPT INITIALIZATION:
┌──────────────────────────────────┐
│ Call Stack                       │
├──────────────────────────────────┤
│ console.log('Script start')      │ ✅
│ fs.readFile scheduled            │
│ setTimeout scheduled             │
│ console.log('Script end')        │ ✅
└──────────────────────────────────┘

QUEUES AFTER SCRIPT:
┌──────────────────┐  ┌──────────────────┐
│ Timers Queue     │  │ Poll Queue       │
├──────────────────┤  ├──────────────────┤
│ setTimeout       │  │ fs.readFile      │
│ (waiting)        │  │ (waiting for I/O)│
└──────────────────┘  └──────────────────┘

EVENT LOOP ITERATION 1:

PHASE: Timers
┌──────────────────────────────────┐
│ Execute setTimeout callback      │ ✅
│ console.log('Timeout...')        │
└──────────────────────────────────┘

PHASE: Pending Callbacks
(empty)

PHASE: Poll
┌──────────────────────────────────┐
│ File read completed!             │
│ Execute fs.readFile callback     │ ✅
│ console.log('Inside I/O...')     │
└──────────────────────────────────┘
(We are "in an I/O cycle" here)

PHASE: Check
(empty)

PHASE: Close Callbacks
(empty)

Back to Timers → No more operations
Event loop ends.
```

---

## Part 5: What "Inside an I/O Cycle" Means

This is the critical concept. When I say you're "inside an I/O cycle", it means:

**Your code is executing inside the callback of an I/O operation, while the event loop is in the Poll phase.**

### Example 1: INSIDE an I/O Cycle

```javascript
const fs = require('fs');

fs.readFile('file.txt', () => {
  // ← This entire function is executing IN the Poll phase
  // ← You are "INSIDE AN I/O CYCLE"
  
  console.log('Code here runs in Poll phase');
  
  setTimeout(() => {
    console.log('A');
  }, 0);
  
  setImmediate(() => {
    console.log('B');
  });
});
```

**Output:**
```
Code here runs in Poll phase
B
A
```

**Why B before A?**

When this callback executes in the Poll phase:
- `setTimeout` is added to Timers queue (for NEXT iteration)
- `setImmediate` is added to Check queue (for THIS iteration)

The event loop continues: Poll → Check (B executes) → [next iteration] → Timers (A executes)

### Example 2: OUTSIDE an I/O Cycle

```javascript
// This is main script execution, NOT inside any callback

console.log('Code here runs during script initialization');

setTimeout(() => {
  console.log('A');
}, 0);

setImmediate(() => {
  console.log('B');
});
```

**Output:**
```
Code here runs during script initialization
A
B
```

or sometimes:

```
Code here runs during script initialization
B
A
```

**Why unpredictable?**

Both `setTimeout` and `setImmediate` are queued BEFORE the event loop formally starts iterating. The system determines which queue is processed first based on timing.

---

## Part 6: Why the I/O Cycle Matters

The I/O cycle affects **how timers and immediate callbacks behave**.

### Rule 1: setTimeout vs setImmediate Inside I/O Cycle

```javascript
const fs = require('fs');

fs.readFile('file.txt', () => {
  // Inside I/O cycle (Poll phase)
  
  setTimeout(() => console.log('timeout'), 0);
  setImmediate(() => console.log('immediate'));
});

// Output: ALWAYS
// immediate
// timeout
```

**Why?** Because Check phase comes before Timers in the same iteration.

### Rule 2: setTimeout vs setImmediate Outside I/O Cycle

```javascript
// Not inside any I/O callback

setTimeout(() => console.log('timeout'), 0);
setImmediate(() => console.log('immediate'));

// Output: UNPREDICTABLE
// Could be either order
```

**Why?** Both queued before event loop starts iterating.

### Rule 3: process.nextTick Inside I/O Cycle

```javascript
const fs = require('fs');

fs.readFile('file.txt', () => {
  // Inside I/O cycle (Poll phase)
  
  process.nextTick(() => console.log('nextTick'));
  setTimeout(() => console.log('timeout'), 0);
  setImmediate(() => console.log('immediate'));
  Promise.resolve().then(() => console.log('promise'));
});

// Output: ALWAYS
// nextTick
// promise
// immediate
// timeout
```

**Why?**
1. `process.nextTick` → executed immediately between phases
2. Promise (microtask) → executed between phases
3. `setImmediate` → executed in Check phase of same iteration
4. `setTimeout` → executed in Timers phase of next iteration

---

## Part 7: Real-World I/O Cycle Example

Let's trace through a realistic scenario:

```javascript
const fs = require('fs');
const net = require('net');

console.log('1. Server starting');

// Create HTTP-like server
const server = net.createServer((socket) => {
  console.log('2. Client connected');
  
  // This callback is executed IN an I/O cycle (when socket connects)
  socket.write('Hello\n');
  
  socket.on('data', (data) => {
    console.log('4. Received:', data.toString());
    // This callback is ALSO executed IN an I/O cycle (when data arrives)
  });
});

server.listen(3000, () => {
  console.log('3. Server listening on port 3000');
  // This callback is executed IN an I/O cycle (when server is ready)
});
```

**Timeline when a client connects and sends data:**

```
TIME 0: Server starts
┌──────────────────────────────────┐
│ Call Stack                       │
├──────────────────────────────────┤
│ console.log('1. Server...')      │ ✅ "1. Server starting"
│ server.listen()                  │
└──────────────────────────────────┘

EVENT LOOP RUNNING...

TIME 5ms: Server ready
POLL PHASE:
┌──────────────────────────────────┐
│ Execute listen callback          │ ✅ "3. Server listening..."
└──────────────────────────────────┘

EVENT LOOP CONTINUES...

TIME 100ms: Client connects
POLL PHASE:
┌──────────────────────────────────┐
│ Execute socket connection        │ ✅ "2. Client connected"
│ callback (IN I/O CYCLE)          │
│                                  │
│ socket.write() called            │
│ (Queues write operation)         │
└──────────────────────────────────┘

EVENT LOOP CONTINUES...

TIME 150ms: Data arrives from client
POLL PHASE:
┌──────────────────────────────────┐
│ Execute 'data' event callback    │ ✅ "4. Received: ..."
│ (IN I/O CYCLE)                   │
└──────────────────────────────────┘
```

---

## Part 8: Practical Implications of I/O Cycles

### Problem: Blocking the Poll Phase

```javascript
const fs = require('fs');

fs.readFile('large-file.txt', () => {
  // If this callback takes a long time...
  let sum = 0;
  for (let i = 0; i < 1000000000; i++) {
    sum += i;  // 2 seconds of heavy work
  }
  console.log('Done');
});

// While this callback runs, NO OTHER CODE can run
// The entire event loop is blocked
// No other I/O operations can be processed
// Other clients get slow response times
```

**Solution: Break up the work**

```javascript
const fs = require('fs');

fs.readFile('large-file.txt', (err, data) => {
  // Process data in chunks
  processDataInChunks(data);
});

function processDataInChunks(data, index = 0) {
  if (index >= data.length) {
    console.log('Done');
    return;
  }
  
  // Process a small chunk
  const chunk = data.slice(index, index + 1000);
  process.nextTick(() => {
    // Schedule the next chunk for later
    processDataInChunks(data, index + 1000);
  });
}

// This allows the event loop to handle other operations between chunks
```

### Problem: Concurrent I/O Bottleneck

```javascript
const fs = require('fs');

const files = ['file1.txt', 'file2.txt', 'file3.txt', ..., 'file100.txt'];

// Bad: All 100 operations queued at once
files.forEach(file => {
  fs.readFile(file, (err, data) => {
    processData(data);
  });
});

// This queues 100 I/O operations to the thread pool
// If the thread pool has only 4 threads, 96 operations wait
// Not efficient!
```

**Solution: Use controlled concurrency**

```javascript
const fs = require('fs').promises;

async function processFiles(files, concurrency = 4) {
  for (let i = 0; i < files.length; i += concurrency) {
    const batch = files.slice(i, i + concurrency);
    await Promise.all(
      batch.map(file => fs.readFile(file))
    );
  }
}

// Only 4 operations at a time, better control
```

### Practical Use: Predictable Scheduling

```javascript
const fs = require('fs');

// Because we're inside an I/O cycle, order is PREDICTABLE
fs.readFile('config.txt', () => {
  // This is GUARANTEED to run before the setTimeout
  console.log('A');
  
  setTimeout(() => {
    console.log('B');  // Always runs after
  }, 0);
  
  setImmediate(() => {
    console.log('C');  // Runs between A and B
  });
});

// Output: ALWAYS
// A
// C
// B
```

This predictability is useful for:
- Initialization code
- Configuration loading
- Setting up event handlers

---

## Part 9: Summary & Quick Reference

### I/O in Node.js:

| Concept | What it is | Examples |
|---------|-----------|----------|
| **I/O** | Communication with external resources | File system, network, database |
| **Blocking** | Code freezes while waiting for I/O | `fs.readFileSync()` |
| **Non-blocking** | Code continues while I/O happens in background | `fs.readFile()` (with callback) |
| **libuv** | C library that handles async I/O | Manages thread pool + event loop |
| **Thread Pool** | Workers that perform I/O tasks | Default 4 threads, handles fs + DNS |
| **I/O Cycle** | Event loop executing I/O callback in Poll phase | Inside `fs.readFile(callback)` |

### I/O Callback Behavior:

```javascript
// INSIDE I/O cycle (Poll phase):
fs.readFile('file.txt', () => {
  setTimeout(() => console.log('A'), 0);     // Next iteration
  setImmediate(() => console.log('B'));      // Same iteration
  process.nextTick(() => console.log('C'));  // Right now
  Promise.resolve().then(() => console.log('D')); // Right now
});

// Output: ALWAYS C, D, B, A
```

```javascript
// OUTSIDE I/O cycle (script initialization):
setTimeout(() => console.log('A'), 0);       // Maybe first
setImmediate(() => console.log('B'));        // Maybe first
process.nextTick(() => console.log('C'));    // Right now
Promise.resolve().then(() => console.log('D')); // Right now

// Output: ALWAYS C, D, then A or B (unpredictable)
```

### Key Rules:

1. **Node.js delegates I/O to the OS/kernel** — JavaScript doesn't do I/O directly
2. **I/O callbacks are queued in the Poll phase** — They wait in a queue until the event loop reaches Poll phase
3. **Only one I/O callback executes at a time** — Even though multiple I/O operations happen in parallel
4. **Inside I/O cycle = in Poll phase** — Your code can rely on Check phase coming next
5. **Thread pool is limited** — Be mindful of concurrent I/O operations

This is how Node.js achieves high performance: **many I/O operations in parallel, callbacks execute sequentially in a predictable order.**