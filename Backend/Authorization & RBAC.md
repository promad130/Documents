
## PART 1 — Concepts

### What is Authorization vs Authentication?

These two are constantly confused:

|Concept|Question it answers|Example|
|---|---|---|
|**Authentication**|_Who are you?_|Login with email/password → get a JWT|
|**Authorization**|_What are you allowed to do?_|Can you delete this post?|

You must be authenticated _before_ authorization can happen.

---

### What is RBAC?

**Role-Based Access Control** means: instead of giving permissions directly to each user, you assign a **role** to the user, and the role carries the permissions.

```
User  →  has a Role  →  Role has Permissions  →  Permissions guard Resources
```

**Real example:**

```
Alice  →  role: "admin"   →  can: create, read, update, delete
Bob    →  role: "member"  →  can: create, read, update (own posts only)
Carol  →  role: "viewer"  →  can: read only
```

---

### The 3 Core Roles You'll Build

```
ADMIN   ──▶  Full access. Can do anything, to anyone's resources.
MEMBER  ──▶  Can manage their own resources. Cannot touch others'.
VIEWER  ──▶  Read-only. Cannot create, edit, or delete anything.
```

---

### Permissions Matrix

Think of it as a table:

```
Permission    │ ADMIN   │ MEMBER  │ VIEWER
──────────────┼─────────┼─────────┼────────
create        │  yes    │  yes    │  no
read          │  yes    │  yes    │  yes
update (own)  │  yes    │  yes    │  no
update (any)  │  yes    │  no     │  no
delete (own)  │  yes    │  yes    │  no
delete (any)  │  yes    │  no     │  no
```

In Node.js, you'd model this as:

```js
const PERMISSIONS = {
  admin:  ["create", "read", "update:any",  "delete:any"],
  member: ["create", "read", "update:own",  "delete:own"],
  viewer: ["read"],
};
```

---

### Authorization Patterns (from OWASP)

**1. Centralized Authorization** — All access checks go through one place (middleware). Best practice. 

**2. Inline Checks** — Checks scattered across route handlers. Hard to maintain. 

**3. Deny by Default** — If no rule explicitly allows something, deny it. Always do this.

**4. Principle of Least Privilege** — Give users only the permissions they need, nothing more. 

---

## PART 2 — Express Authorization Middleware

### Project Setup

```bash
mkdir rbac-demo && cd rbac-demo
npm init -y
npm install express jsonwebtoken
```

**File structure:**

```
rbac-demo/
├── middleware/
│   └── auth.js          ← JWT verification + RBAC checks
├── routes/
│   ├── users.js
│   └── posts.js
├── data/
│   └── store.js         ← In-memory "database"
└── index.js
```

---

### Step 1 — In-Memory Store (`data/store.js`)

```js
// data/store.js
const users = [
  { id: 1, name: "Alice",  email: "alice@example.com",  role: "admin"  },
  { id: 2, name: "Bob",    email: "bob@example.com",    role: "member" },
  { id: 3, name: "Carol",  email: "carol@example.com",  role: "viewer" },
];

const posts = [
  { id: 1, title: "Admin Post",  content: "Hello from admin",  ownerId: 1 },
  { id: 2, title: "Bob's Post",  content: "Hello from Bob",    ownerId: 2 },
  { id: 3, title: "Another Post",content: "Another by Bob",    ownerId: 2 },
];

module.exports = { users, posts };
```

---

### Step 2 — Auth Middleware (`middleware/auth.js`)

This file has **3 middlewares** — understand each one:

```js
// middleware/auth.js
const jwt = require("jsonwebtoken");
const { users } = require("../data/store");

const JWT_SECRET = "supersecretkey123"; // use env variable in real apps

// ─────────────────────────────────────────────
// 1. AUTHENTICATE — Verify JWT, attach user to req
// ─────────────────────────────────────────────
const authenticate = (req, res, next) => {
  const authHeader = req.headers["authorization"];
  // Expected format:  "Bearer <token>"
  const token = authHeader && authHeader.split(" ")[1];

  if (!token) {
    return res.status(401).json({ error: "No token provided. Please log in." });
  }

  try {
    const decoded = jwt.verify(token, JWT_SECRET);
    // decoded = { userId: 1, iat: ..., exp: ... }

    const user = users.find((u) => u.id === decoded.userId);
    if (!user) {
      return res.status(401).json({ error: "User not found." });
    }

    req.user = user; // attach full user (including role) to request
    next();
  } catch (err) {
    return res.status(401).json({ error: "Invalid or expired token." });
  }
};

// ─────────────────────────────────────────────
// 2. REQUIRE ROLE — Check if user has one of the allowed roles
// ─────────────────────────────────────────────
//
// Usage:  requireRole("admin")
//         requireRole("admin", "member")   ← either role is fine
//
const requireRole = (...allowedRoles) => {
  return (req, res, next) => {
    // authenticate must run BEFORE this middleware
    if (!req.user) {
      return res.status(401).json({ error: "Not authenticated." });
    }

    if (!allowedRoles.includes(req.user.role)) {
      return res.status(403).json({
        error: `Forbidden. Required role: ${allowedRoles.join(" or ")}. Your role: ${req.user.role}`,
      });
    }

    next();
  };
};

// ─────────────────────────────────────────────
// 3. REQUIRE OWNER OR ADMIN — Resource ownership check
// ─────────────────────────────────────────────
//
// getOwnerId: a function that extracts the resource owner's ID
//             from (req) — so it works for any resource type.
//
// Usage:  requireOwnerOrAdmin((req) => findPost(req.params.id).ownerId)
//
const requireOwnerOrAdmin = (getOwnerId) => {
  return async (req, res, next) => {
    if (!req.user) {
      return res.status(401).json({ error: "Not authenticated." });
    }

    // Admins can always proceed
    if (req.user.role === "admin") {
      return next();
    }

    // For non-admins, check ownership
    try {
      const ownerId = await getOwnerId(req); // could be async (DB lookup)

      if (ownerId === undefined || ownerId === null) {
        return res.status(404).json({ error: "Resource not found." });
      }

      if (req.user.id !== ownerId) {
        return res.status(403).json({
          error: "Forbidden. You can only modify your own resources.",
        });
      }

      next();
    } catch (err) {
      next(err);
    }
  };
};

// Helper to generate tokens (used by /login)
const generateToken = (userId) => {
  return jwt.sign({ userId }, JWT_SECRET, { expiresIn: "1h" });
};

module.exports = { authenticate, requireRole, requireOwnerOrAdmin, generateToken };
```

---

### Step 3 — Main App + Login Route (`index.js`)

```js
// index.js
const express = require("express");
const { users } = require("./data/store");
const { generateToken } = require("./middleware/auth");

const app = express();
app.use(express.json());

app.use("/users", require("./routes/users"));
app.use("/posts", require("./routes/posts"));

// ── LOGIN (no auth required here) ──────────────
// In real apps you'd verify a password hash (bcrypt).
// Here we just check the email and hand out a token.
app.post("/login", (req, res) => {
  const { email } = req.body;
  const user = users.find((u) => u.email === email);

  if (!user) {
    return res.status(401).json({ error: "Invalid credentials." });
  }

  const token = generateToken(user.id);
  res.json({
    message: `Logged in as ${user.name} (${user.role})`,
    token,
  });
});

app.listen(3000, () => console.log("Server on http://localhost:3000"));
```

---

### Step 4 — User Routes (`routes/users.js`)

```js
// routes/users.js
const express = require("express");
const router = express.Router();
const { users } = require("../data/store");
const { authenticate, requireRole } = require("../middleware/auth");

// GET /users  — Admins and Members can list users, Viewers cannot
router.get(
  "/",
  authenticate,
  requireRole("admin", "member"),   // ← role guard
  (req, res) => {
    res.json(users.map(({ id, name, role }) => ({ id, name, role })));
  }
);

// GET /users/:id  — Any logged-in user can view a profile
router.get("/:id", authenticate, (req, res) => {
  const user = users.find((u) => u.id === parseInt(req.params.id));
  if (!user) return res.status(404).json({ error: "User not found." });
  const { id, name, role } = user;
  res.json({ id, name, role });
});

// DELETE /users/:id  — Only admins can delete users
router.delete(
  "/:id",
  authenticate,
  requireRole("admin"),             // ← admin-only
  (req, res) => {
    const index = users.findIndex((u) => u.id === parseInt(req.params.id));
    if (index === -1) return res.status(404).json({ error: "User not found." });
    const deleted = users.splice(index, 1)[0];
    res.json({ message: `User ${deleted.name} deleted.` });
  }
);

module.exports = router;
```

---

### Step 5 — Post Routes with Owner Checks (`routes/posts.js`)

```js
// routes/posts.js
const express = require("express");
const router = express.Router();
const { posts } = require("../data/store");
const { authenticate, requireRole, requireOwnerOrAdmin } = require("../middleware/auth");

// GET /posts  — Anyone logged in can read posts
router.get("/", authenticate, (req, res) => {
  res.json(posts);
});

// GET /posts/:id  — Anyone logged in can read a single post
router.get("/:id", authenticate, (req, res) => {
  const post = posts.find((p) => p.id === parseInt(req.params.id));
  if (!post) return res.status(404).json({ error: "Post not found." });
  res.json(post);
});

// POST /posts  — Admins and Members can create posts (not viewers)
router.post(
  "/",
  authenticate,
  requireRole("admin", "member"),
  (req, res) => {
    const { title, content } = req.body;
    const newPost = {
      id: posts.length + 1,
      title,
      content,
      ownerId: req.user.id,           // ← always set owner to current user
    };
    posts.push(newPost);
    res.status(201).json(newPost);
  }
);

// PUT /posts/:id  — Owner or Admin can update
router.put(
  "/:id",
  authenticate,
  requireRole("admin", "member"),     // first: check role
  requireOwnerOrAdmin((req) => {      // then: check ownership
    const post = posts.find((p) => p.id === parseInt(req.params.id));
    return post ? post.ownerId : null;
  }),
  (req, res) => {
    const post = posts.find((p) => p.id === parseInt(req.params.id));
    if (!post) return res.status(404).json({ error: "Post not found." });

    const { title, content } = req.body;
    if (title)   post.title   = title;
    if (content) post.content = content;

    res.json({ message: "Post updated.", post });
  }
);

// DELETE /posts/:id  — Owner or Admin can delete
router.delete(
  "/:id",
  authenticate,
  requireOwnerOrAdmin((req) => {
    const post = posts.find((p) => p.id === parseInt(req.params.id));
    return post ? post.ownerId : null;
  }),
  (req, res) => {
    const index = posts.findIndex((p) => p.id === parseInt(req.params.id));
    if (index === -1) return res.status(404).json({ error: "Post not found." });
    const deleted = posts.splice(index, 1)[0];
    res.json({ message: `Post "${deleted.title}" deleted.` });
  }
);

module.exports = router;
```

---

### Step 6 — Test with curl

**Get tokens for all 3 users:**

```bash
# Login as Admin (Alice)
curl -X POST http://localhost:3000/login \
  -H "Content-Type: application/json" \
  -d '{"email": "alice@example.com"}'

# Login as Member (Bob)
curl -X POST http://localhost:3000/login \
  -H "Content-Type: application/json" \
  -d '{"email": "bob@example.com"}'

# Login as Viewer (Carol)
curl -X POST http://localhost:3000/login \
  -H "Content-Type: application/json" \
  -d '{"email": "carol@example.com"}'
```

**Store a token and test:**

```bash
# Set a token variable
TOKEN="paste_your_token_here"

# ✅ Admin: delete any user
curl -X DELETE http://localhost:3000/users/3 \
  -H "Authorization: Bearer $TOKEN"

# ✅ Member: create a post
curl -X POST http://localhost:3000/posts \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title": "My Post", "content": "Hello world"}'

# ❌ Viewer: try to create a post (should get 403)
curl -X POST http://localhost:3000/posts \
  -H "Authorization: Bearer $CAROL_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title": "Can I post?", "content": "Trying..."}'

# ❌ Bob: try to delete Alice's post (post id 1)
curl -X DELETE http://localhost:3000/posts/1 \
  -H "Authorization: Bearer $BOB_TOKEN"
# Expected: 403 Forbidden - you don't own this post
```

---

### Middleware Chain — How It All Connects

```
Incoming Request
      │
      ▼
  authenticate        ← Verifies JWT, sets req.user
      │
      ▼
  requireRole(...)    ← Checks req.user.role against allowed list
      │
      ▼
requireOwnerOrAdmin   ← For non-admins, checks if req.user.id === resource.ownerId
      │
      ▼
  Route Handler       ← Only reaches here if all checks pass
```

**The key insight:** Each middleware calls `next()` to pass control forward, or sends a response to stop the chain. A `403` from `requireRole` never reaches `requireOwnerOrAdmin`. Clean, composable, testable.

---

## PART 3 — Practice Exercise

Build a **Comment system** on top of what you've learned. Here's your task:

### Requirements

**Data:**

```js
const comments = [
  { id: 1, postId: 1, text: "Great post!", ownerId: 2 },
  { id: 2, postId: 1, text: "I agree",     ownerId: 3 },
];
```

**Build these routes in `routes/comments.js`:**

|Method|Route|Who can access?|
|---|---|---|
|`GET`|`/comments`|Any logged-in user|
|`GET`|`/comments/:id`|Any logged-in user|
|`POST`|`/comments`|Members and Admins only|
|`PUT`|`/comments/:id`|Owner or Admin only|
|`DELETE`|`/comments/:id`|Owner, Admin, OR the post's author|

**The last DELETE rule is the challenge** — it requires checking two possible owners (comment owner OR post owner). Hint: write a custom middleware that checks both.

### Bonus challenges:

1. Add a `MODERATOR` role that can delete any comment but cannot delete users
2. Add a `/me` route that returns the currently logged-in user's data using `req.user`
3. Add an `isActive` field to users — inactive users should get a `403` even with a valid token (add this check inside `authenticate`)

### How to verify you're done:

- Bob (member) **can** comment but **cannot** edit Carol's comment
- Carol (viewer) **cannot** post a comment, gets `403`
- Alice (admin) **can** delete any comment
- Alice (author of post 1) **can** delete comment 1 even though Bob owns it

---

## Quick Reference Card

```js
// Protect a route — logged in only
router.get("/secret", authenticate, handler);

// Admin only
router.delete("/nuke", authenticate, requireRole("admin"), handler);

// Admin or Member
router.post("/create", authenticate, requireRole("admin", "member"), handler);

// Owner or Admin (for any resource — just pass the getter)
router.put("/:id", authenticate,
  requireOwnerOrAdmin(req => getResource(req.params.id).ownerId),
  handler
);

// Combine role + ownership
router.put("/:id", authenticate,
  requireRole("admin", "member"),
  requireOwnerOrAdmin(req => getResource(req.params.id).ownerId),
  handler
);
```

---

**Key OWASP takeaways to remember:**

- Always **deny by default** — if no middleware explicitly allows, deny
- **Separate** authentication from authorization (two different jobs)
- **Centralize** your auth checks in middleware, never inline in business logic
- Always check **both role AND ownership** for resource-level operations
- Return `401` when unauthenticated, `403` when authenticated but forbidden — don't mix them up