## **1. Is a URL an Object? Understanding URL Structure**

Not exactly, but **a URL can be broken down and parsed into an object**. When a request comes to Express, the URL string is parsed and its different parts are extracted.

### **URL Components:**

```
http://localhost:3000/users/123?age=25&city=NYC#profile
 │      │               │       │              │
 │      │               │       │              └─ Fragment (anchor)
 │      │               │       └────────────────── Query String
 │      │               └──────────────────────── Path
 │      └──────────────────────────────────────── Host
 └───────────────────────────────────────────── Protocol
```

In Express, this gets parsed into:

```javascript
app.get('/users/:id', (req, res) => {
    console.log(req.protocol);  // "http"
    console.log(req.hostname);  // "localhost"
    console.log(req.port);      // 3000
    console.log(req.path);      // "/users/123"
    console.log(req.url);       // "/users/123?age=25&city=NYC"
    console.log(req.params);    // { id: "123" } (parsed from :id)
    console.log(req.query);     // { age: "25", city: "NYC" } (parsed from ?)
});
```

**So the full URL is broken into:**
- **URL Path** → matched against route patterns
- **Query String** → available in `req.query`
- **Route Parameters** → extracted from `:paramName` → available in `req.params`

---

## **2. URL Parameters Explained**

URL parameters are **placeholders in your route path** that capture variable parts of the URL.

### **Basic Syntax:**

```javascript
// Route definition with parameter
app.get('/users/:id', (req, res) => {
    console.log(req.params); // { id: "123" }
});

// Request: GET /users/123
// :id captures "123"
```

### **How Parameters Work:**

When you define a route with `:paramName`:
1. `:paramName` is a **placeholder** that matches **any value** at that position
2. The matched value is **captured** and stored in `req.params` object
3. Multiple requests to different URLs use the same route handler

```javascript
app.get('/users/:id', (req, res) => {
    res.json({ 
        message: `Getting user ${req.params.id}`,
        id: req.params.id 
    });
});

// Request: GET /users/123
// Response: { message: "Getting user 123", id: "123" }

// Request: GET /users/john
// Response: { message: "Getting user john", id: "john" }

// Request: GET /users/abc-123-xyz
// Response: { message: "Getting user abc-123-xyz", id: "abc-123-xyz" }
```

**Key Point:** `:id` matches **anything** at that position (numbers, letters, special chars) - it's a wildcard.

---

## **3. Multiple URL Parameters**

You can have multiple parameters in a single route:

```javascript
app.get('/users/:userId/posts/:postId', (req, res) => {
    console.log(req.params);
    // For /users/5/posts/42:
    // { userId: "5", postId: "42" }
});

app.get('/year/:year/month/:month/day/:day', (req, res) => {
    console.log(req.params);
    // For /year/2024/month/12/day/25:
    // { year: "2024", month: "12", day: "25" }
});
```

---

## **4. The Route Matching Problem You Asked About**

**Here's your question:** If you have both `/users/:id` and `/user/:userName`, how does Express know which one to use?

### **The Answer: Route Matching Order**

Express uses **"First Come, First Served"** matching. **Routes are matched in the order you define them in your code**, not by any clever logic.

```javascript
const express = require('express');
const app = express();

// Route 1 (defined first)
app.get('/users/:id', (req, res) => {
    res.send(`Getting user by ID: ${req.params.id}`);
});

// Route 2 (defined second)
app.get('/users/:userName', (req, res) => {
    res.send(`Getting user by name: ${req.params.userName}`);
});

app.listen(3000);
```

**What happens?**

```
Request: GET /users/123
→ Matches Route 1 first
→ Response: "Getting user by ID: 123"

Request: GET /users/alice
→ Matches Route 1 first (not Route 2!)
→ Response: "Getting user by ID: alice"
```

**Both requests match Route 1 because it's defined first!** Route 2 is never reached. This is a problem.

### **Solution: Be More Specific - Define Static Routes First**

```javascript
// Route 1: SPECIFIC static route (defined first)
app.get('/users/me', (req, res) => {
    res.send('This is the current logged-in user');
});

// Route 2: More general parameterized route (defined second)
app.get('/users/:id', (req, res) => {
    res.send(`Getting user by ID: ${req.params.id}`);
});

app.listen(3000);
```

**Now:**

```
Request: GET /users/me
→ Matches Route 1 (exact match is checked before parameters)
→ Response: "This is the current logged-in user"

Request: GET /users/123
→ Doesn't match Route 1
→ Matches Route 2
→ Response: "Getting user by ID: 123"
```

---

## **5. Distinguishing Between Different Parameter Types**

You said you want to expect a string as parameter. But here's the issue: **`:id` and `:userName` both accept any string!**

```javascript
app.get('/users/:id', (req, res) => {
    res.send(`ID: ${req.params.id}`);
});

app.get('/users/:userName', (req, res) => {
    res.send(`Username: ${req.params.userName}`);
});

// GET /users/123 → matches first route only
// GET /users/alice → matches first route only
```

### **Solution 1: Regex Constraints - Restrict Parameter Patterns**

Use regex patterns in your route to match specific formats:

```javascript
// Route 1: Only match numeric IDs
app.get('/users/:id(\\d+)', (req, res) => {
    res.send(`User ID: ${req.params.id}`);
});

// Route 2: Only match alphanumeric usernames (letters and numbers)
app.get('/users/:userName([a-zA-Z0-9_]+)', (req, res) => {
    res.send(`Username: ${req.params.userName}`);
});

app.listen(3000);
```

**Now:**

```
Request: GET /users/123
→ Matches Route 1 (123 is numeric)
→ Response: "User ID: 123"

Request: GET /users/alice
→ Doesn't match Route 1 (alice is not numeric)
→ Matches Route 2 (alice is alphanumeric)
→ Response: "Username: alice"

Request: GET /users/123abc
→ Doesn't match Route 1 (has letters)
→ Doesn't match Route 2 (Route 1 already consumed it... wait, no)
→ Actually matches Route 2 (123abc is alphanumeric)
→ Response: "Username: 123abc"
```

### **Solution 2: Use Different URL Paths (Best Practice)**

Instead of relying on parameter patterns, use different paths:

```javascript
// Route 1: Get user by ID
app.get('/users/id/:id', (req, res) => {
    res.send(`User ID: ${req.params.id}`);
});

// Route 2: Get user by username
app.get('/users/username/:userName', (req, res) => {
    res.send(`Username: ${req.params.userName}`);
});

app.listen(3000);
```

**Clear and unambiguous:**

```
GET /users/id/123 → Route 1
GET /users/username/alice → Route 2
```

---

## **6. Advanced: Regex Patterns in Routes**

Here's how to use regex constraints:

```javascript
// Only match numeric values
app.get('/posts/:id(\\d+)', (req, res) => {
    res.send(`Post ID (numeric): ${req.params.id}`);
});

// Only match UUIDs (36 character format)
app.get('/items/:uuid([a-f0-9-]{36})', (req, res) => {
    res.send(`UUID: ${req.params.uuid}`);
});

// Only match slugs (lowercase letters, numbers, hyphens)
app.get('/articles/:slug([a-z0-9-]+)', (req, res) => {
    res.send(`Article: ${req.params.slug}`);
});

// Match email-like patterns
app.get('/email/:email([\\w\\.-]+@[\\w\\.-]+\\.\\w+)', (req, res) => {
    res.send(`Email: ${req.params.email}`);
});
```

---

## **7. Complete Example: Understanding Route Matching**

```javascript
const express = require('express');
const app = express();

// ===== STATIC ROUTES (highest priority) =====
app.get('/users/me', (req, res) => {
    res.json({ message: 'This is your profile' });
});

app.get('/users/admin', (req, res) => {
    res.json({ message: 'Admin panel' });
});

// ===== SPECIFIC REGEX ROUTES (medium priority) =====
// Only numeric IDs
app.get('/users/:id(\\d+)', (req, res) => {
    res.json({ 
        type: 'numeric-id',
        id: req.params.id 
    });
});

// ===== GENERAL PARAMETERIZED ROUTES (lowest priority) =====
// Matches anything
app.get('/users/:name', (req, res) => {
    res.json({ 
        type: 'username',
        name: req.params.name 
    });
});

// ===== MULTIPLE PARAMETERS =====
app.get('/users/:userId/posts/:postId(\\d+)', (req, res) => {
    res.json({ 
        user: req.params.userId,
        post: req.params.postId 
    });
});

app.listen(3000, () => console.log('Server on port 3000'));

/* 
MATCHING EXAMPLES:

GET /users/me
→ Matches: "/users/me" (static route)
→ Response: { message: 'This is your profile' }

GET /users/admin
→ Matches: "/users/admin" (static route)
→ Response: { message: 'Admin panel' }

GET /users/123
→ Matches: "/users/:id(\\d+)" (regex route, 123 is numeric)
→ Response: { type: 'numeric-id', id: '123' }

GET /users/alice
→ Matches: "/users/:name" (general route)
→ Response: { type: 'username', name: 'alice' }

GET /users/123/posts/456
→ Matches: "/users/:userId/posts/:postId(\\d+)"
→ Response: { user: '123', post: '456' }

GET /users/alice/posts/hello
→ Doesn't match "/users/:userId/posts/:postId(\\d+)" 
   (because 'hello' is not numeric for postId)
→ No route matches (404 Not Found)
*/
```

---

## **8. Query Parameters vs URL Parameters**

Don't confuse these two!

```javascript
// URL PARAMETER (part of the path)
app.get('/users/:id', (req, res) => {
    console.log(req.params.id); // From :id in path
});

// Request: /users/123
// req.params = { id: '123' }

// =====================================================

// QUERY PARAMETER (after ? in URL)
app.get('/users', (req, res) => {
    console.log(req.query.sort); // From ?sort=name in URL
});

// Request: /users?sort=name&limit=10
// req.query = { sort: 'name', limit: '10' }

// =====================================================

// BOTH TOGETHER
app.get('/users/:id', (req, res) => {
    console.log(req.params.id);  // From path: 123
    console.log(req.query.sort); // From query: name
});

// Request: /users/123?sort=name
// req.params = { id: '123' }
// req.query = { sort: 'name' }
```

---

## **9. Real-World Example: E-Commerce API**

```javascript
const express = require('express');
const app = express();

// Static routes (specific)
app.get('/products/featured', (req, res) => {
    res.json({ message: 'Featured products' });
});

app.get('/products/sale', (req, res) => {
    res.json({ message: 'Sale products' });
});

// Numeric product IDs only
app.get('/products/:productId(\\d+)', (req, res) => {
    const productId = req.params.productId;
    const qty = req.query.qty || 1;  // Query parameter for quantity
    
    res.json({
        productId,
        quantity: qty,
        message: `Fetching product ${productId} with quantity ${qty}`
    });
});

// User profiles by username
app.get('/users/:username', (req, res) => {
    const username = req.params.username;
    const showDetails = req.query.details === 'true'; // Query parameter
    
    res.json({
        username,
        showDetails,
        message: `User: ${username}`
    });
});

// Complex: User's orders by order ID
app.get('/users/:username/orders/:orderId(\\d+)', (req, res) => {
    const { username, orderId } = req.params;
    const includeItems = req.query.items === 'true';
    
    res.json({
        username,
        orderId,
        includeItems,
        message: `Order ${orderId} from ${username}`
    });
});

app.listen(3000);

/*
EXAMPLES:

GET /products/featured
→ { message: 'Featured products' }

GET /products/123
→ { productId: '123', quantity: '1', message: '...' }

GET /products/123?qty=5
→ { productId: '123', quantity: '5', message: '...' }

GET /users/alice
→ { username: 'alice', showDetails: false, message: '...' }

GET /users/alice?details=true
→ { username: 'alice', showDetails: true, message: '...' }

GET /users/alice/orders/456
→ { username: 'alice', orderId: '456', includeItems: false, message: '...' }

GET /users/alice/orders/456?items=true
→ { username: 'alice', orderId: '456', includeItems: true, message: '...' }
*/
```

---

## **10. Quick Reference Table**

| Concept | Example | Accessed Via |
|---------|---------|--------------|
| **URL Parameter** | `/users/:id` with request `/users/123` | `req.params.id` |
| **Multiple Params** | `/users/:userId/posts/:postId` | `req.params.userId`, `req.params.postId` |
| **Regex Constraint** | `/products/:id(\\d+)` | `req.params.id` (only numbers) |
| **Query Parameter** | `/search?q=javascript&page=2` | `req.query.q`, `req.query.page` |
| **Path** | Any URL path | `req.path` or `req.url` |
| **Static Route** | `/users/me` | Matches exact path |
| **Parameterized Route** | `/users/:name` | Matches any value at that position |

