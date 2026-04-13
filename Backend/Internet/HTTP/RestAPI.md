
## REST Principles

REST is an acronym for **RE**presentational **S**tate **T**ransfer and an architectural style for **distributed hypermedia systems**. Roy Fielding first presented it in 2000 in his famous [dissertation](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm). REST is not a protocol or a standard, it is an architectural style. During the development phase, API developers can implement REST in a variety of ways.

Like the other architectural styles, REST also has its guiding principles and constraints. These principles must be satisfied if a service interface is to be referred to as **RESTful**.
The following four constraints can achieve a uniform REST interface:

- **Identification of resources** – The interface must uniquely identify each resource involved in the interaction between the client and the server.
- **Manipulation of resources through representations** – The resources should have uniform representations in the server response. API consumers should use these representations to modify the resource state in the server.
- **Self-descriptive messages** – Each resource representation should carry enough information to describe how to process the message. It should also provide information on the additional actions that the client can perform on the resource.
- **Hypermedia as the engine of application state** – The client should have only the initial URI of the application. The client application should dynamically drive all other resources and interactions with the use of hyperlinks.

In simpler words, REST defines a consistent and uniform interface for interactions between clients and servers. For example, the HTTP-based REST APIs make use of the standard HTTP methods (GET, POST, PUT, DELETE, etc.) and the URIs (Uniform Resource Identifiers) to identify resources.

REST (Representational State Transfer) is an architectural style for building APIs. Here are the 6 core principles:

### 1. **Client-Server Architecture**
Clear separation between client (who makes requests) and server (who processes them).

```javascript
// Client side - React/Node.js
const fetchUser = async (userId) => {
  const response = await fetch(`https://api.example.com/users/${userId}`);
  return response.json();
};

// Server side - Express.js
app.get('/users/:id', (req, res) => {
  const user = database.findUser(req.params.id);
  res.json(user);
});
```

### 2. **Statelessness**
Each request contains all info needed to process it. Server doesn't store client context between requests.

```javascript
// ✅ GOOD - Stateless (token/auth included in each request)
fetch('https://api.example.com/users/123', {
  headers: {
    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'
  }
});

// ❌ BAD - Stateful (relying on server remembering you)
// Server: "Oh, this is Bob, I remember him from last request"
// Next day: "Who are you?" - Server lost context
```

### 3. **Uniform Interface**
Consistent way to interact with resources. Use standard HTTP methods and status codes.

```javascript
// Resource-based URLs (not action-based)
// ✅ GOOD
GET    /api/books
POST   /api/books
GET    /api/books/123
PUT    /api/books/123
DELETE /api/books/123

// ❌ BAD
GET    /api/getBooks
POST   /api/createBook
GET    /api/getBook?id=123
POST   /api/updateBook
POST   /api/deleteBook
```

### 4. **Cacheability**
Responses should define themselves as cacheable or not.

```javascript
// Server-side - Mark responses as cacheable
app.get('/api/posts', (req, res) => {
  res.set('Cache-Control', 'public, max-age=3600'); // Cache for 1 hour
  res.json(posts);
});

// Client-side - Browsers/proxies respect this
// First request: Fetches from server
// Within 1 hour: Serves from cache
const posts = await fetch('https://api.example.com/posts');
```

### 5. **Layered System**
Client can't tell if connected directly to end server. There can be proxies, load balancers, caches in between.

```
Client → API Gateway → Load Balancer → Server 1
                                     → Server 2
                                     → Server 3

Client doesn't care about this complexity. It just knows the API endpoint.
```

### 6. **Code on Demand** (Optional)
Server can extend client by sending executable code (rarely used in modern APIs).

```javascript
// Less common, but example: Server sends JavaScript to client
// Mostly obsolete - use APIs + frameworks instead
```

---

## HTTP Verbs (Methods)

These define what action you're performing on a resource:

### **GET** - Retrieve data (Safe & Idempotent)

```javascript
// Read-only. Can be called 100 times, same result.
fetch('https://api.example.com/users/5')
  .then(res => res.json())
  .then(user => console.log(user));

// Server
app.get('/users/:id', (req, res) => {
  res.json(database.getUser(req.params.id));
});
```

### **POST** - Create new resource (Not Idempotent)

```javascript
// Creates a NEW resource each time
fetch('https://api.example.com/users', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ name: 'Alice', email: 'alice@example.com' })
})
.then(res => res.json())
.then(newUser => console.log('User created:', newUser.id));

// Server
app.post('/users', (req, res) => {
  const newUser = database.createUser(req.body);
  res.status(201).json(newUser); // 201 = Created
});

// ⚠️ Calling POST 3 times = 3 new users created (NOT idempotent)
```

### **PUT** - Replace entire resource (Idempotent)

```javascript
// Replace the ENTIRE resource
fetch('https://api.example.com/users/5', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    name: 'Alice Updated',
    email: 'newemail@example.com',
    age: 30
    // Must include ALL fields, even unchanged ones
  })
})
.then(res => res.json());

// Server
app.put('/users/:id', (req, res) => {
  const updated = database.replaceUser(req.params.id, req.body);
  res.json(updated);
});

// ✅ Calling PUT 3 times = Same result each time (idempotent)
// User remains in the same state after first request
```

### **PATCH** - Partial update (Sometimes Idempotent)

```javascript
// Update ONLY the fields you provide
fetch('https://api.example.com/users/5', {
  method: 'PATCH',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    email: 'newemail@example.com'
    // Only updating email, other fields untouched
  })
})
.then(res => res.json());

// Server
app.patch('/users/:id', (req, res) => {
  const updated = database.updateUser(req.params.id, req.body);
  res.json(updated);
});

// Difference: PUT requires full object, PATCH only changed fields
```

### **DELETE** - Remove resource (Idempotent)

```javascript
// Delete the resource
fetch('https://api.example.com/users/5', {
  method: 'DELETE'
})
.then(res => res.status === 204 ? console.log('Deleted') : res.json());

// Server
app.delete('/users/:id', (req, res) => {
  database.deleteUser(req.params.id);
  res.status(204).send(); // 204 = No Content
});

// ✅ Calling DELETE 3 times = Resource gone after first request (idempotent)
```

### **HEAD** - Like GET but no response body

```javascript
// Useful for checking if resource exists without downloading data
fetch('https://api.example.com/users/5', { method: 'HEAD' })
  .then(res => console.log('User exists:', res.ok));

// Server - same as GET but no body
app.head('/users/:id', (req, res) => {
  const exists = database.userExists(req.params.id);
  res.status(exists ? 200 : 404).send();
});
```

### **OPTIONS** - Describe communication options

```javascript
// Ask server what methods are allowed
fetch('https://api.example.com/users/5', { method: 'OPTIONS' })
  .then(res => console.log(res.headers.get('Allow')));
  // Returns: "GET, POST, PUT, DELETE, OPTIONS"

// Server
app.options('/users/:id', (req, res) => {
  res.set('Allow', 'GET, POST, PUT, DELETE, OPTIONS');
  res.send();
});
```

---

## Idempotent vs Non-Idempotent

**Idempotent**: Calling multiple times = same result as calling once.

```javascript
// IDEMPOTENT (safe to retry)
PUT    /users/5         // Replace user 5 - 3 calls = same state
DELETE /users/5         // Delete user 5 - already deleted, idempotent
GET    /users/5         // Get user 5 - doesn't change anything

// NON-IDEMPOTENT (risky to retry)
POST   /users           // Create new user - 3 calls = 3 new users
PATCH  /api/counter/inc // Increment - 3 calls = counter += 3
```

---

## API Design Best Practices

### 1. **Use Plural Resource Names**

```javascript
// ✅ GOOD
GET    /api/v1/users
GET    /api/v1/users/5
POST   /api/v1/users
DELETE /api/v1/users/5

// ❌ BAD
GET    /api/v1/user
GET    /api/v1/user/5
```

### 2. **Use Sub-resources for Relationships**

```javascript
// ✅ GOOD - Get all posts by user 5
GET /api/v1/users/5/posts

// ❌ BAD
GET /api/v1/posts?authorId=5

// ✅ GOOD - Get comments on post 42
GET /api/v1/posts/42/comments

// Nested example:
GET /api/v1/users/5/posts/42/comments/100
```

### 3. **Use Query Parameters for Filtering/Pagination**

```javascript
// Filtering
GET /api/v1/users?status=active&role=admin

// Pagination
GET /api/v1/users?page=2&limit=20

// Sorting
GET /api/v1/users?sort=createdAt&order=desc

// Combination
GET /api/v1/posts?authorId=5&status=published&page=1&limit=10

// Client example
const params = new URLSearchParams({
  status: 'active',
  page: 1,
  limit: 20
});

fetch(`https://api.example.com/users?${params}`)
  .then(res => res.json());
```

### 4. **Use Correct HTTP Status Codes**

```javascript
// Server responses with proper status codes
app.get('/users/:id', (req, res) => {
  const user = database.getUser(req.params.id);
  
  if (!user) {
    return res.status(404).json({ error: 'User not found' }); // 404
  }
  
  res.status(200).json(user); // 200 = OK
});

app.post('/users', (req, res) => {
  if (!req.body.name) {
    return res.status(400).json({ error: 'Name required' }); // 400 = Bad Request
  }
  
  const newUser = database.createUser(req.body);
  res.status(201).json(newUser); // 201 = Created
});

// Common codes:
// 2xx Success
// 200 OK - Request succeeded
// 201 Created - Resource created
// 204 No Content - Success, no body (DELETE)
//
// 4xx Client Error
// 400 Bad Request - Invalid data
// 401 Unauthorized - Auth failed
// 403 Forbidden - Auth ok but no permission
// 404 Not Found - Resource doesn't exist
// 409 Conflict - Request conflicts (duplicate)
//
// 5xx Server Error
// 500 Internal Server Error - Server broke
// 503 Service Unavailable - Temporarily down
```

### 5. **Version Your API**

```javascript
// In URL (most common)
GET /api/v1/users
GET /api/v2/users

// In header
GET /api/users
Headers: { 'API-Version': '1' }

app.get('/users', (req, res) => {
  const version = req.headers['api-version'] || '1';
  // Handle based on version
});
```

### 6. **Handle Errors Consistently**

```javascript
// ✅ GOOD - Consistent error format
{
  "error": "User not found",
  "code": "USER_NOT_FOUND",
  "statusCode": 404,
  "timestamp": "2026-04-12T10:30:00Z"
}

// Implementation
app.get('/users/:id', (req, res) => {
  const user = database.getUser(req.params.id);
  
  if (!user) {
    return res.status(404).json({
      error: 'User not found',
      code: 'USER_NOT_FOUND',
      statusCode: 404,
      timestamp: new Date().toISOString()
    });
  }
  
  res.json(user);
});

// Client-side handling
fetch('https://api.example.com/users/999')
  .then(res => {
    if (!res.ok) {
      return res.json().then(err => { throw err; });
    }
    return res.json();
  })
  .catch(error => {
    console.error(`Error (${error.code}): ${error.error}`);
  });
```

### 7. **Use Content Negotiation**

```javascript
// Client requests specific format
fetch('https://api.example.com/users/5', {
  headers: { 'Accept': 'application/json' }
});

// Server responds appropriately
app.get('/users/:id', (req, res) => {
  const user = database.getUser(req.params.id);
  
  if (req.headers.accept.includes('application/xml')) {
    res.type('application/xml').send(`<user>${user.name}</user>`);
  } else {
    res.json(user);
  }
});
```

---

## Complete REST API Example

```javascript
// Express.js server - Simple blog API
const express = require('express');
const app = express();
app.use(express.json());

let posts = [
  { id: 1, title: 'First Post', content: 'Hello', author: 'Alice' },
  { id: 2, title: 'Second Post', content: 'World', author: 'Bob' }
];

let nextId = 3;

// GET all posts with filtering
app.get('/api/v1/posts', (req, res) => {
  let filtered = posts;
  
  if (req.query.author) {
    filtered = filtered.filter(p => p.author === req.query.author);
  }
  
  res.set('Cache-Control', 'public, max-age=300');
  res.json(filtered);
});

// GET single post
app.get('/api/v1/posts/:id', (req, res) => {
  const post = posts.find(p => p.id === parseInt(req.params.id));
  
  if (!post) {
    return res.status(404).json({
      error: 'Post not found',
      code: 'POST_NOT_FOUND'
    });
  }
  
  res.json(post);
});

// CREATE new post
app.post('/api/v1/posts', (req, res) => {
  if (!req.body.title || !req.body.content) {
    return res.status(400).json({
      error: 'Title and content required',
      code: 'MISSING_FIELDS'
    });
  }
  
  const newPost = {
    id: nextId++,
    title: req.body.title,
    content: req.body.content,
    author: req.body.author || 'Anonymous'
  };
  
  posts.push(newPost);
  res.status(201).json(newPost);
});

// UPDATE entire post
app.put('/api/v1/posts/:id', (req, res) => {
  const post = posts.find(p => p.id === parseInt(req.params.id));
  
  if (!post) {
    return res.status(404).json({ error: 'Post not found' });
  }
  
  post.title = req.body.title;
  post.content = req.body.content;
  post.author = req.body.author;
  
  res.json(post);
});

// PARTIAL update
app.patch('/api/v1/posts/:id', (req, res) => {
  const post = posts.find(p => p.id === parseInt(req.params.id));
  
  if (!post) {
    return res.status(404).json({ error: 'Post not found' });
  }
  
  if (req.body.title) post.title = req.body.title;
  if (req.body.content) post.content = req.body.content;
  if (req.body.author) post.author = req.body.author;
  
  res.json(post);
});

// DELETE post
app.delete('/api/v1/posts/:id', (req, res) => {
  const index = posts.findIndex(p => p.id === parseInt(req.params.id));
  
  if (index === -1) {
    return res.status(404).json({ error: 'Post not found' });
  }
  
  posts.splice(index, 1);
  res.status(204).send();
});

app.listen(3000, () => console.log('API running on port 3000'));
```

Client usage:

```javascript
// GET all posts
fetch('http://localhost:3000/api/v1/posts')
  .then(r => r.json())
  .then(posts => console.log(posts));

// GET filtered
fetch('http://localhost:3000/api/v1/posts?author=Alice')
  .then(r => r.json());

// GET single
fetch('http://localhost:3000/api/v1/posts/1')
  .then(r => r.json());

// CREATE
fetch('http://localhost:3000/api/v1/posts', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    title: 'New Post',
    content: 'Great article',
    author: 'Charlie'
  })
})
.then(r => r.json())
.then(post => console.log('Created:', post.id));

// UPDATE
fetch('http://localhost:3000/api/v1/posts/1', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    title: 'Updated Title',
    content: 'Updated content',
    author: 'Alice Updated'
  })
})
.then(r => r.json());

// PARTIAL UPDATE
fetch('http://localhost:3000/api/v1/posts/1', {
  method: 'PATCH',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ title: 'New Title Only' })
})
.then(r => r.json());

// DELETE
fetch('http://localhost:3000/api/v1/posts/1', {
  method: 'DELETE'
})
.then(r => r.status === 204 && console.log('Deleted'));
```


***[Use as reference while learning](https://restfulapi.net/)***

## 2. What is a Resource?

The key **abstraction of information** in REST is a [resource](https://restfulapi.net/resource-naming/). Any information that we can name can be a resource. For example, a REST resource can be a document or image, a temporal service, a collection of other resources, or a non-virtual object (e.g., a person).

The state of the resource at any particular time is known as the **resource representation**. The resource representations consist of:

- the **data**
- the **metadata** describing the data
- and the **hypermedia links** that can help the clients transition to the next desired state.

> A REST API consists of an assembly of interlinked resources. This set of resources is known as the REST API’s _**resource model**_.

### 2.1. Resource Identifiers

REST uses resource identifiers to identify each resource involved in the interactions between the client and the server components.

### 2.2. Hypermedia

The data format of a representation is known as a [media type](https://www.iana.org/assignments/media-types/media-types.xhtml). The media type identifies a specification that defines how a representation is to be processed.

**A RESTful API looks like [_hypertext_](https://restfulapi.net/hateoas/).** Every addressable unit of information carries an address, either explicitly (e.g., link and id attributes) or implicitly (e.g., derived from the media type definition and representation structure).

> _Hypertext_ (or hypermedia) means the **simultaneous presentation of information and controls** such that the information becomes the affordance through which the user (or automaton) obtains choices and selects actions.
> 
> Remember that hypertext does not need to be HTML (or XML or JSON) on a browser. Machines can follow links when they understand the data format and relationship types.
> 
> — Roy Fielding

### 2.3. Self-Descriptive

Further, **resource representations shall be self-descriptive**: the client does not need to know if a resource is an employee or a device. It should act based on the media type associated with the resource.

So in practice, we will create lots of **custom media types** – usually one media type associated with one resource.

Every media type defines a default processing model. For example, HTML defines a rendering process for hypertext and the browser behavior around each element.

> Media Types have no relation to the resource methods GET/PUT/POST/DELETE/… other than the fact that some media type elements will define a process model that goes like “anchor elements with an `href` attribute create a hypertext link that, when selected, invokes a retrieval request (GET) on the URI corresponding to the `CDATA`-encoded `href` attribute.”

### 2.4. Example

Consider the following REST resource that represents a blog post with links to related resources in an HTTP-based REST API. This has the necessary information about the blog post, as well as the hypermedia links to the related resources such as author and comments. Clients can follow these links to discover additional information or perform actions.

```json
{
  "id": 123,
  "title": "What is REST",
  "content": "REST is an architectural style for building web services...",
  "published_at": "2023-11-04T14:30:00Z",
  "author": {
    "id": 456,
    "name": "John Doe",
    "profile_url": "https://example.com/authors/456"
  },
  "comments": {
    "count": 5,
    "comments_url": "https://example.com/posts/123/comments"
  },
  "self": {
    "link": "https://example.com/posts/123"
  }
}
```

## 3. Resource Methods

Another important thing associated with REST is **resource methods**. These resource methods are used to perform the desired transition between two states of any resource.

A large number of people wrongly relate resource methods to [HTTP methods](https://restfulapi.net/http-methods/) (i.e., GET/PUT/POST/DELETE). Roy Fielding has never mentioned any recommendation around which method to use in which condition. All he emphasizes is that it should be a **uniform interface**.

For example, if we decide that the application APIs will use HTTP POST for updating a resource – rather than the more common HTTP PUT – it’s all right. Still, the application interface will be RESTful.

Ideally, everything needed to transition the resource state shall be part of the resource representation – including all the supported methods and what form they will leave the representation.

> We should enter a REST API with no prior knowledge beyond the initial URI (a bookmark) and a set of standardized media types appropriate for the intended audience (i.e., expected to be understood by any client that might use the API).
> 
> From that point on, all application state transitions must be driven by the client selection of server-provided choices present in the received representations or implied by the user’s manipulation of those representations.
> 
> The transitions may be determined (or limited by) the client’s knowledge of media types and resource communication mechanisms, both of which may be improved on the fly (e.g., _code-on-demand_). [Failure here implies that out-of-band information is driving interaction instead of hypertext.]

## 4. REST and HTTP are Not the Same

Many people prefer to compare HTTP with REST. **REST and HTTP are not the same.**

> **REST != HTTP**

Though REST also intends to make the web (internet) more streamlined and standard, Roy Fielding advocates using REST principles more strictly. And that’s where people try to start comparing REST with the web.

Roy Fielding, in his dissertation, has not mentioned any implementation direction – including any protocol preference or even HTTP. As long as we are honoring the six guiding principles of REST, we can call our interface RESTful.