
## 1. Quick Concept Recap

**JWT (JSON Web Token)** is a compact, self-contained token used to securely transmit information between a client and server.

```
User logs in with email + password
  -> Server verifies credentials
  -> Server creates a signed JWT
  -> Client stores JWT (localStorage / cookie)
  -> Client sends JWT in every future request
  -> Server verifies the JWT on each request
  -> If valid -> grant access | If invalid -> 401
```

**JWT is stateless** — the server does not store sessions. All the information needed to verify the user is inside the token itself.

---

## 2. How JWT Works

### Token Structure

A JWT has 3 parts separated by dots:

```
eyJhbGciOiJIUzI1NiJ9  .  eyJpZCI6IjEyMyIsInJvbGUiOiJhZG1pbiJ9  .  SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
      HEADER                          PAYLOAD                                    SIGNATURE
```

|Part|Contains|Example|
|---|---|---|
|**Header**|Algorithm & token type|`{ "alg": "HS256", "typ": "JWT" }`|
|**Payload**|Your data (claims)|`{ "id": "123", "role": "admin", "exp": 17... }`|
|**Signature**|Tamper-proof seal|Signed with your `JWT_SECRET`|

> The payload is **base64 encoded, not encrypted**. Anyone can decode it. Never store passwords or sensitive data inside a JWT.

### Signing vs Verifying

```js
const jwt = require('jsonwebtoken');
const SECRET = process.env.JWT_SECRET;

// SIGN — creating a token at login
const token = jwt.sign(
  { id: user._id, email: user.email, role: user.role }, // payload
  SECRET,                                                 // secret key
  { expiresIn: '7d' }                                    // options
);

// VERIFY — checking a token on each protected request
try {
  const decoded = jwt.verify(token, SECRET);
  console.log(decoded);
  // { id: '123', email: 'user@test.com', role: 'member', iat: ..., exp: ... }
} catch (err) {
  // err.name === 'TokenExpiredError'  -> token expired
  // err.name === 'JsonWebTokenError' -> token invalid or tampered
}
```

---

## 3. JWT in Node.js — The Essentials

### Setup

```bash
npm install express mongoose jsonwebtoken bcryptjs dotenv
```

```
# .env
MONGO_URI=mongodb://localhost:27017/jwt_practice
JWT_SECRET=your_super_secret_key_here
JWT_EXPIRES_IN=7d
PORT=3000
```

### Password Hashing with bcryptjs

Never store plain-text passwords. Always hash them before saving.

```js
const bcrypt = require('bcryptjs');

// Hashing at registration
const salt           = await bcrypt.genSalt(10);
const hashedPassword = await bcrypt.hash(plainPassword, salt);
// Store hashedPassword in DB

// Comparing at login
const isMatch = await bcrypt.compare(plainPassword, hashedPassword);
// Returns true or false
```

### User Model

```js
// models/User.js
const mongoose = require('mongoose');
const bcrypt   = require('bcryptjs');

const userSchema = new mongoose.Schema({
  name:     { type: String, required: true, trim: true },
  email:    { type: String, required: true, unique: true, lowercase: true },
  password: { type: String, required: true, minlength: 6 },
  role:     { type: String, enum: ['admin', 'member', 'viewer'], default: 'viewer' }
}, { timestamps: true });

// Hash password before saving
userSchema.pre('save', async function (next) {
  if (!this.isModified('password')) return next();
  const salt    = await bcrypt.genSalt(10);
  this.password = await bcrypt.hash(this.password, salt);
  next();
});

// Helper method for password comparison
userSchema.methods.comparePassword = async function (candidate) {
  return bcrypt.compare(candidate, this.password);
};

// Never send password in JSON responses
userSchema.methods.toJSON = function () {
  const obj = this.toObject();
  delete obj.password;
  return obj;
};

module.exports = mongoose.model('User', userSchema);
```

### Auth Controller

```js
// controllers/authController.js
const jwt  = require('jsonwebtoken');
const User = require('../models/User');

const signToken = (user) =>
  jwt.sign(
    { id: user._id, email: user.email, role: user.role },
    process.env.JWT_SECRET,
    { expiresIn: process.env.JWT_EXPIRES_IN || '7d' }
  );

// POST /api/auth/register
const register = async (req, res) => {
  try {
    const { name, email, password } = req.body;

    const existing = await User.findOne({ email });
    if (existing) {
      return res.status(400).json({ error: 'Email already in use' });
    }

    const user  = await User.create({ name, email, password });
    const token = signToken(user);

    res.status(201).json({ token, user });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// POST /api/auth/login
const login = async (req, res) => {
  try {
    const { email, password } = req.body;

    const user = await User.findOne({ email });
    if (!user) {
      return res.status(401).json({ error: 'Invalid email or password' });
    }

    const isMatch = await user.comparePassword(password);
    if (!isMatch) {
      return res.status(401).json({ error: 'Invalid email or password' });
    }

    const token = signToken(user);
    res.json({ token, user });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// GET /api/auth/me
const getMe = async (req, res) => {
  try {
    const user = await User.findById(req.user.id);
    if (!user) return res.status(404).json({ error: 'User not found' });
    res.json(user);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

module.exports = { register, login, getMe };
```

### Authenticate Middleware

```js
// middleware/authenticate.js
const jwt = require('jsonwebtoken');

const authenticate = (req, res, next) => {
  const authHeader = req.headers['authorization'];

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ error: 'Access denied. No token provided.' });
  }

  const token = authHeader.split(' ')[1];

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    req.user = decoded; // { id, email, role, iat, exp }
    next();
  } catch (err) {
    if (err.name === 'TokenExpiredError') {
      return res.status(401).json({ error: 'Token expired. Please login again.' });
    }
    return res.status(401).json({ error: 'Invalid token.' });
  }
};

module.exports = authenticate;
```

---

## 4. Practice Exercise

### What You Will Build

A mini Express API with:

- User registration and login
- JWT-protected profile route
- Token refresh endpoint
- Logout with token blacklist
- Input validation

---

### Project Structure

```
jwt-practice/
├── .env
├── app.js
├── models/
│   ├── User.js
│   └── BlacklistedToken.js
├── middleware/
│   ├── authenticate.js
│   └── validate.js
├── controllers/
│   └── authController.js
└── routes/
    └── authRoutes.js
```

---

### Task 1 — Register & Login

Build these two routes:

```
POST /api/auth/register
POST /api/auth/login
```

**Register requirements:**

- Accept `name`, `email`, `password` in request body
- Return `400` if email already exists
- Return `400` if any field is missing
- Hash the password before saving
- Return the JWT token + user object (without password) on success

**Login requirements:**

- Accept `email`, `password`
- Return `401` with a generic message if credentials are wrong (do not reveal whether email or password was wrong — security best practice)
- Return JWT token + user object on success

**Test it:**

```bash
# Register
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@test.com","password":"secret123"}'

# Expected: { "token": "eyJ...", "user": { "name": "Alice", ... } }

# Login
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@test.com","password":"secret123"}'

# Wrong password
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@test.com","password":"wrongpassword"}'
# Expected: 401 { "error": "Invalid email or password" }
```

---

### Task 2 — Protected Profile Route

Build this route:

```
GET /api/auth/me   (requires valid JWT)
```

- Return `401` if no token is provided
- Return `401` if token is invalid or expired
- Return the logged-in user's data if token is valid

**Test it:**

```bash
TOKEN="paste_your_token_here"

# Without token -> 401
curl http://localhost:3000/api/auth/me

# With valid token -> user object
curl -H "Authorization: Bearer $TOKEN" http://localhost:3000/api/auth/me

# With fake token -> 401
curl -H "Authorization: Bearer faketoken123" http://localhost:3000/api/auth/me
```

---

### Task 3 — Input Validation

Add validation before hitting the database. Do it manually without a library.

```js
// middleware/validate.js

const validateRegister = (req, res, next) => {
  const { name, email, password } = req.body;
  const errors = [];

  if (!name || name.trim().length < 2)
    errors.push('Name must be at least 2 characters');

  if (!email || !email.includes('@'))
    errors.push('Valid email is required');

  if (!password || password.length < 6)
    errors.push('Password must be at least 6 characters');

  if (errors.length > 0) {
    return res.status(400).json({ errors });
  }

  next();
};

module.exports = { validateRegister };
```

**Apply it to your register route:**

```js
router.post('/register', validateRegister, authController.register);
```

**Test it:**

```bash
# Missing fields
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"bad-email","password":"123"}'

# Expected: 400 { "errors": ["Name must be at least 2 characters", ...] }
```

---

### Task 4 — Token Refresh

**Real-world scenario:** Access tokens expire. Instead of forcing re-login, issue a new token if the current one is still valid.

```
POST /api/auth/refresh   (requires valid JWT)
```

How it works:

1. Client sends their current token
2. Server verifies it
3. If valid, issue a brand new token with a fresh expiry
4. Client replaces the old token with the new one

```js
// controllers/authController.js — add this function
const refresh = (req, res) => {
  // req.user is set by authenticate middleware
  const newToken = jwt.sign(
    { id: req.user.id, email: req.user.email, role: req.user.role },
    process.env.JWT_SECRET,
    { expiresIn: process.env.JWT_EXPIRES_IN || '7d' }
  );

  res.json({ token: newToken });
};
```

**Add the route:**

```js
router.post('/refresh', authenticate, authController.refresh);
```

**Test it:**

```bash
curl -X POST http://localhost:3000/api/auth/refresh \
  -H "Authorization: Bearer $TOKEN"

# Expected: { "token": "eyJ... (new token with fresh expiry)" }
```

---

### Task 5 — Logout with Token Blacklist

**The problem with JWTs:** Once issued, a JWT is valid until it expires. There is no built-in way to invalidate it on the server.

**The solution:** Maintain a blacklist of logged-out tokens in the database.

**Step 1 — Create the BlacklistedToken model:**

```js
// models/BlacklistedToken.js
const mongoose = require('mongoose');

const blacklistedTokenSchema = new mongoose.Schema({
  token: { type: String, required: true, unique: true },
  // Auto-delete this document after the token's own expiry
  // 604800 seconds = 7 days — match your JWT_EXPIRES_IN
  createdAt: { type: Date, default: Date.now, expires: 604800 }
});

module.exports = mongoose.model('BlacklistedToken', blacklistedTokenSchema);
```

**Step 2 — Logout route:**

```js
// controllers/authController.js — add this function
const BlacklistedToken = require('../models/BlacklistedToken');

const logout = async (req, res) => {
  try {
    const token = req.headers['authorization'].split(' ')[1];
    await BlacklistedToken.create({ token });
    res.json({ message: 'Logged out successfully' });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
```

**Step 3 — Update authenticate middleware to check the blacklist:**

```js
// middleware/authenticate.js (updated)
const jwt              = require('jsonwebtoken');
const BlacklistedToken = require('../models/BlacklistedToken');

const authenticate = async (req, res, next) => {
  const authHeader = req.headers['authorization'];

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ error: 'Access denied. No token provided.' });
  }

  const token = authHeader.split(' ')[1];

  try {
    // Check blacklist BEFORE verifying
    const isBlacklisted = await BlacklistedToken.findOne({ token });
    if (isBlacklisted) {
      return res.status(401).json({ error: 'Token has been invalidated. Please login again.' });
    }

    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    req.user = decoded;
    next();
  } catch (err) {
    if (err.name === 'TokenExpiredError') {
      return res.status(401).json({ error: 'Token expired. Please login again.' });
    }
    return res.status(401).json({ error: 'Invalid token.' });
  }
};

module.exports = authenticate;
```

**Add the route:**

```js
router.post('/logout', authenticate, authController.logout);
```

**Test it:**

```bash
# Logout
curl -X POST http://localhost:3000/api/auth/logout \
  -H "Authorization: Bearer $TOKEN"
# Expected: { "message": "Logged out successfully" }

# Try the same token again -> should now be rejected
curl -H "Authorization: Bearer $TOKEN" http://localhost:3000/api/auth/me
# Expected: 401 { "error": "Token has been invalidated. Please login again." }
```

---

### All Routes at a Glance

```js
// routes/authRoutes.js
const express              = require('express');
const router               = express.Router();
const authenticate         = require('../middleware/authenticate');
const { validateRegister } = require('../middleware/validate');
const authController       = require('../controllers/authController');

router.post('/register', validateRegister, authController.register);
router.post('/login',                      authController.login);
router.get( '/me',       authenticate,     authController.getMe);
router.post('/refresh',  authenticate,     authController.refresh);
router.post('/logout',   authenticate,     authController.logout);

module.exports = router;
```

|Route|Method|Auth Required|What it does|
|---|---|---|---|
|`/api/auth/register`|POST|No|Create account, get token|
|`/api/auth/login`|POST|No|Login, get token|
|`/api/auth/me`|GET|Yes|Get own profile|
|`/api/auth/refresh`|POST|Yes|Get a fresh token|
|`/api/auth/logout`|POST|Yes|Invalidate current token|

---

## 5. Bonus Challenges

Try these after finishing the main tasks:

### Bonus 1 — Change Password

```
PATCH /api/auth/change-password   (requires JWT)
Body: { "currentPassword": "...", "newPassword": "..." }
```

- Verify the current password before allowing the change
- After changing, invalidate all old tokens by adding a `passwordChangedAt` timestamp to the User model and rejecting tokens issued before that timestamp

```js
// Hint: check this inside authenticate middleware
if (user.passwordChangedAt) {
  const changedAtSeconds = parseInt(user.passwordChangedAt.getTime() / 1000);
  if (decoded.iat < changedAtSeconds) {
    return res.status(401).json({ error: 'Password recently changed. Please login again.' });
  }
}
```

### Bonus 2 — Role Promotion (Admin Only)

```
PATCH /api/admin/users/:id/role   (admin only)
Body: { "role": "member" }
```

- Combine with the RBAC middleware from the previous exercise
- Only an `admin` can promote or demote users
- Validate that the new role is one of `['admin', 'member', 'viewer']`

### Bonus 3 — Rate Limiting on Login

Prevent brute-force attacks by limiting login attempts per IP:

```bash
npm install express-rate-limit
```

```js
const rateLimit = require('express-rate-limit');

const loginLimiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15-minute window
  max: 5,                    // max 5 attempts per IP
  message: {
    error: 'Too many login attempts. Please try again in 15 minutes.'
  }
});

router.post('/login', loginLimiter, authController.login);
```

---

## 6. Testing Checklist

```
Registration
  [ ] Returns 201 + token on success
  [ ] Returns 400 if email already exists
  [ ] Returns 400 if name / email / password is missing or invalid
  [ ] Password is hashed in the database (never plain text)
  [ ] Password field is NOT present in the response

Login
  [ ] Returns 200 + token on valid credentials
  [ ] Returns 401 on wrong password
  [ ] Returns 401 on non-existent email
  [ ] Error message is generic — does not reveal which field was wrong

Protected Routes
  [ ] /me returns 401 with no token
  [ ] /me returns 401 with a fake or tampered token
  [ ] /me returns 401 with an expired token
  [ ] /me returns 200 with a valid token

Token Refresh
  [ ] Returns a new token with a fresh expiry
  [ ] Old token still works until it expires naturally

Logout and Blacklist
  [ ] Returns 200 on logout
  [ ] Using the same token after logout returns 401
  [ ] A different valid token from another user is unaffected

Validation
  [ ] Short passwords are rejected with 400
  [ ] Invalid email formats are rejected with 400
  [ ] Missing fields return 400 with descriptive error messages
```

---

## Key Takeaways

- JWTs are **stateless** — the server issues them and does not store them (unless blacklisting for logout)
- The payload is **encoded, not encrypted** — never put secrets or passwords inside a JWT
- Always use **bcrypt** to hash passwords — never store or compare plain text
- Use **generic error messages** on login failures — do not reveal which field was wrong
- Implement a **token blacklist** for proper logout support
- Handle both `TokenExpiredError` and `JsonWebTokenError` separately for clear, specific error messages
- `401 Unauthorized` means not authenticated | `403 Forbidden` means authenticated but not allowed

---

_Combine this with the RBAC exercise to build a fully authenticated and authorized API._