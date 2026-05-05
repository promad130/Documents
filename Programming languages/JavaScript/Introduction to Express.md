## **What is Express?**

**Express.js** is a lightweight, minimal web framework for Node.js that makes it easy to build web servers and APIs. It provides a simple way to handle HTTP requests, route URLs, manage middleware, and send responses back to clients.

**Why use Express?**
- Simplifies server creation (compared to raw Node.js HTTP module)
- Built-in routing system
- Middleware support (functions that process requests)
- Easy request/response handling

---

## **1. Installation & Setup**

```bash
# Create a new Node project
npm init -y

# Install Express
npm install express
```

**Basic Express Server:**

```javascript
// server.js
const express = require('express');
const app = express();

// Define a route
app.get('/', (req, res) => {
    res.send('Hello, World!');
});

// Start the server
app.listen(3000, () => {
    console.log('Server running on http://localhost:3000');
});
```

Run it:
```bash
node server.js
```

---

## **2. Understanding the HTTP Cycle**

When a client (browser/app) sends a request to your server:

```
Client → HTTP Request → Express Server → Process → HTTP Response → Client
```

if we consider the http flow along with this, then this is how it would look:
```text
Client → TCP / UDP connection → HTTP Request → Express Server → Process → HTTP Response → Client
```

Each request goes through:
1. **Receive** the request
2. **Process** it (using middlewares and routes)
3. **Send** a response

Before heading ahead, I would say look into [[URLs and Parameters in Express]].

---

## **3. The `req` (Request) Object**

The `req` object contains all information about the HTTP request from the client. It's inherited from Node.js's `http.IncomingMessage`.

### **Key Properties:**

```javascript
app.get('/example/:userId', (req, res) => {
    // URL PARAMETERS (from :userId in the route)
    console.log(req.params.userId); // e.g., "123"
    
    // QUERY STRING (from ?key=value in URL)
    // If URL is: /example/123?age=25&city=NYC
    console.log(req.query.age);    // "25"
    console.log(req.query.city);   // "NYC"
    
    // REQUEST METHOD
    console.log(req.method);       // "GET"
    
    // REQUEST URL/PATH
    console.log(req.url);          // "/example/123?age=25&city=NYC"
    console.log(req.path);         // "/example/123"
    
    // REQUEST HEADERS (metadata sent by client)
    console.log(req.headers);      // { 'content-type': 'application/json', ... }
    console.log(req.get('content-type')); // "application/json"
    
    // CLIENT IP ADDRESS
    console.log(req.ip);           // "192.168.1.1"
    
    // REQUEST BODY (for POST/PUT requests)
    console.log(req.body);         // { username: 'john', ... } (requires middleware)
    
    // COOKIES (requires cookie-parser middleware)
    console.log(req.cookies);      // { sessionId: 'abc123', ... }
});
```

### **`req.body` - Understanding POST Data**

To access `req.body`, you need middleware to parse incoming data:

```javascript
const express = require('express');
const app = express();

// Middleware to parse JSON data
app.use(express.json());

// Middleware to parse URL-encoded data (form data)
app.use(express.urlencoded({ extended: true }));

app.post('/user', (req, res) => {
    console.log(req.body); // { name: 'John', email: 'john@example.com' }
    res.send('User received');
});
```

**How it works:**
- **`express.json()`** - Parses incoming JSON data (Content-Type: application/json)
- **`express.urlencoded()`** - Parses form data (Content-Type: application/x-www-form-urlencoded)

### **Useful `req` Methods:**

```javascript
app.get('/test', (req, res) => {
    // Get a single header
    const contentType = req.get('content-type');
    
    // Check if content-type is JSON
    const isJson = req.is('application/json');
    
    // Check if request is HTTPS
    const isSecure = req.secure;
    
    // Get accepted content types
    const accepts = req.accepts('json'); // true if client accepts JSON
});
```

---

## **4. The `res` (Response) Object**

The `res` object is how you send data back to the client. It's inherited from Node.js's `http.ServerResponse`.

### **Key Methods:**

```javascript
app.get('/demo', (req, res) => {
    
    // 1. SEND TEXT OR HTML
    res.send('Hello, World!');
    // Automatically sets Content-Type
    
    // 2. SEND JSON
    res.json({ name: 'John', age: 30 });
    
    // 3. SET HTTP STATUS CODE
    res.status(201).json({ message: 'Created' }); // 201 = Created
    
    // 4. SEND FILE
    res.sendFile('/path/to/file.pdf');
    
    // 5. REDIRECT TO ANOTHER URL
    res.redirect('/home');
    
    // 6. SET RESPONSE HEADERS
    res.set('Content-Type', 'application/json');
    res.set('X-Custom-Header', 'value');
    // Or set multiple at once:
    res.set({
        'Content-Type': 'application/json',
        'X-Custom': 'value'
    });
    
    // 7. SET COOKIES
    res.cookie('sessionId', 'abc123', {
        maxAge: 1000 * 60 * 60 * 24, // 1 day in milliseconds
        httpOnly: true,               // Only accessible via HTTP, not JS
        secure: true                  // Only sent over HTTPS
    });
    
    // 8. END RESPONSE (with optional data)
    res.end('Goodbye');
    
    // 9. SEND CUSTOM STATUS TEXT
    res.status(404).send('Not Found');
});
```

### **Common HTTP Status Codes:**

| Code | Meaning | Usage |
|------|---------|-------|
| 200  | OK | Successful request |
| 201  | Created | Resource successfully created |
| 204  | No Content | Success, but no data to return |
| 301  | Moved Permanently | Redirect (permanent) |
| 302  | Found | Redirect (temporary) |
| 400  | Bad Request | Client error (invalid data) |
| 401  | Unauthorized | Authentication required |
| 403  | Forbidden | Access denied |
| 404  | Not Found | Resource doesn't exist |
| 500  | Internal Server Error | Server error |

---

## **5. The `next` Function (Middleware)**

The `next` function is a callback that passes control to the **next middleware** in the chain.

### **What is Middleware?**
Middleware are functions that have access to `req`, `res`, and `next`. They execute in sequence and can:
- Modify request/response
- End the request-response cycle
- Pass control to the next middleware

### **How `next` Works:**

```javascript
// Middleware 1: Log every request
app.use((req, res, next) => {
    console.log(`${req.method} ${req.path}`);
    next(); // Pass control to next middleware/route
});

// Middleware 2: Check if user is authenticated
app.use((req, res, next) => {
    if (req.headers.authorization) {
        next(); // User is authorized, continue
    } else {
        res.status(401).send('Unauthorized');
        // Note: We DON'T call next(), request ends here
    }
});

// Route handler (executes after middlewares)
app.get('/protected', (req, res) => {
    res.send('This is protected data');
});
```

**Execution Order:**
```
Request → Middleware 1 → Middleware 2 → Route Handler → Response
```

### **More Middleware Examples:**

```javascript
// Middleware to measure request time
app.use((req, res, next) => {
    const start = Date.now();
    
    // Hook into the 'finish' event
    res.on('finish', () => {
        const duration = Date.now() - start;
        console.log(`Request took ${duration}ms`);
    });
    
    next();
});

// Middleware to add custom data to req
app.use((req, res, next) => {
    req.user = { id: 1, name: 'John' }; // Add user data
    next();
});

// Now you can use it in routes
app.get('/profile', (req, res) => {
    res.json(req.user); // { id: 1, name: 'John' }
});
```

### **Route-Specific Middleware:**

```javascript
// Run middleware only for specific routes
const authenticate = (req, res, next) => {
    if (req.headers.token) {
        next();
    } else {
        res.status(401).send('No token');
    }
};

// Apply middleware to this route only
app.get('/admin', authenticate, (req, res) => {
    res.send('Admin panel');
});

// This route does NOT use the middleware
app.get('/public', (req, res) => {
    res.send('Public page');
});
```

Getting into the details of [[Middleware in Express]]

---

## **6. Routing Basics**

Express supports all HTTP methods:

```javascript
// GET - retrieve data
app.get('/users', (req, res) => {
    res.json([ { id: 1, name: 'John' } ]);
});

// POST - create data
app.post('/users', (req, res) => {
    const newUser = req.body;
    res.status(201).json(newUser);
});

// PUT - update entire resource
app.put('/users/:id', (req, res) => {
    res.json({ message: 'User updated' });
});

// PATCH - partial update
app.patch('/users/:id', (req, res) => {
    res.json({ message: 'User partially updated' });
});

// DELETE - remove data
app.delete('/users/:id', (req, res) => {
    res.json({ message: 'User deleted' });
});
```

### **Route Parameters:**

```javascript
// Single parameter
app.get('/users/:id', (req, res) => {
    console.log(req.params.id); // e.g., "123"
    res.json({ userId: req.params.id });
});

// Multiple parameters
app.get('/users/:userId/posts/:postId', (req, res) => {
    console.log(req.params.userId);  // e.g., "1"
    console.log(req.params.postId);  // e.g., "42"
    res.json({ user: req.params.userId, post: req.params.postId });
});

// Optional regex patterns
app.get('/files/:name(.pdf|.txt)', (req, res) => {
    // Only matches .pdf or .txt files
    res.send(`File: ${req.params.name}`);
});
```

---

## **7. Complete Example: User API**

```javascript
const express = require('express');
const app = express();

// Middleware
app.use(express.json());

// Mock database
let users = [
    { id: 1, name: 'Alice', email: 'alice@example.com' },
    { id: 2, name: 'Bob', email: 'bob@example.com' }
];

// Middleware to add timestamp
app.use((req, res, next) => {
    req.timestamp = new Date().toISOString();
    next();
});

// GET all users
app.get('/users', (req, res) => {
    res.json(users);
});

// GET single user by ID
app.get('/users/:id', (req, res) => {
    const user = users.find(u => u.id === parseInt(req.params.id));
    
    if (!user) {
        return res.status(404).json({ error: 'User not found' });
    }
    
    res.json(user);
});

// POST create new user
app.post('/users', (req, res) => {
    const { name, email } = req.body;
    
    // Validation
    if (!name || !email) {
        return res.status(400).json({ error: 'Name and email required' });
    }
    
    const newUser = {
        id: Math.max(...users.map(u => u.id), 0) + 1,
        name,
        email
    };
    
    users.push(newUser);
    res.status(201).json(newUser);
});

// PUT update user
app.put('/users/:id', (req, res) => {
    const user = users.find(u => u.id === parseInt(req.params.id));
    
    if (!user) {
        return res.status(404).json({ error: 'User not found' });
    }
    
    user.name = req.body.name || user.name;
    user.email = req.body.email || user.email;
    
    res.json(user);
});

// DELETE user
app.delete('/users/:id', (req, res) => {
    const index = users.findIndex(u => u.id === parseInt(req.params.id));
    
    if (index === -1) {
        return res.status(404).json({ error: 'User not found' });
    }
    
    const deletedUser = users.splice(index, 1);
    res.json({ message: 'User deleted', user: deletedUser[0] });
});

// Error handling middleware (must be last)
app.use((err, req, res, next) => {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
});

app.listen(3000, () => {
    console.log('Server running on port 3000');
});
```

---

## 8. Quick Reference Table

| Feature                  | Example                       |
| ------------------------ | ----------------------------- |
| **Parse JSON**           | `app.use(express.json())`     |
| **Get URL param**        | `req.params.id`               |
| **Get query string**     | `req.query.search`            |
| **Get POST body**        | `req.body`                    |
| **Set status**           | `res.status(200)`             |
| **Send JSON**            | `res.json(data)`              |
| **Send text**            | `res.send('text')`            |
| **Redirect**             | `res.redirect('/path')`       |
| **Set header**           | `res.set('Key', 'value')`     |
| **Set cookie**           | `res.cookie('name', 'value')` |
| **Call next middleware** | `next()`                      |
