*(This doc is only for Introduction to Redis, and a full coverage of caching, for a detailed in-depth for anything / quick referance for everything, check out [this](https://redis.io/docs/latest/))*

#  What is Redis?

## The Problem Redis Solves

Imagine you have a Node.js application.

A user requests:
```http
GET /users/123
```

Your server does:
```text
Node.js
    ↓
Database Query
    ↓
PostgreSQL
    ↓
Return Data
```

This is fine.
But now:
```text
1000 users request same data
```

Your database gets hammered.
Every request:

```sql
SELECT * FROM users WHERE id = 123;
```

Even though the data hasn't changed.
This creates:

- Increased latency
- More database load
- More server costs
- Slower user experience

---

## Redis Solution

Redis stores frequently accessed data in memory.
Instead of:
```text
Node.js
    ↓
Database
```

You get:
```text
Node.js
    ↓
Redis
    ↓
Database (only if needed)
```

Flow:
```text
Request
   ↓
Check Redis

Data Exists?
   ↓
Yes → Return instantly

No
   ↓
Query Database
   ↓
Store in Redis
   ↓
Return Response
```

This is called:

> Cache-Aside Pattern

The most common Redis caching pattern.

---

# Why Redis is Fast

Normal databases:

```text
Disk
```

Redis:

```text
RAM (Memory)
```

RAM is thousands of times faster than disk access.

Typical speeds:

```text
PostgreSQL query:
10-100 ms

Redis lookup:
<1 ms
```

---

# What Redis Actually Stores

Redis stores:

```text
Key → Value
```

Example:

```text
user:123 → {
  name: "Nikunj",
  age: 18
}
```

Everything revolves around:

```text
KEY
VALUE
```

Think of it as a giant in-memory dictionary.

---


Now check out [[BullMQ, Background Jobs & Message Queues]]

# Redis Caching with Node.js – A Complete Guide
This guide will walk you through **Redis caching** from the ground up: what it is, why you need it, how to install and connect, all essential data types and commands, caching patterns with **Express**, and best practices.  
You'll find real-world Node.js examples, detailed explanations, and practice exercises to solidify your understanding.

---
## Table of Contents
1. [Why Redis for Caching?](#why-redis-for-caching)
2. [Redis Installation & Setup](#redis-installation--setup)
3. [Connecting Node.js to Redis](#connecting-nodejs-to-redis)
4. [Redis Data Types (with caching focus)](#redis-data-types-with-caching-focus)
5. [Key Commands for Caching](#key-commands-for-caching)
   - [SET, GET, DEL, EXISTS](#set-get-del-exists)
   - [TTL & Expiration (EXPIRE, SETEX, TTL, PTTL)](#ttl--expiration-expire-setex-ttl-pttl)
   - [Pattern Deletion (SCAN + DEL)](#pattern-deletion-scan--del)
6. [Caching Strategies](#caching-strategies)
   - [Cache-Aside (Lazy Loading)](#cache-aside-lazy-loading)
   - [Write-Through](#write-through)
   - [Write-Behind](#write-behind)
7. [Caching in Express – Step by Step](#caching-in-express--step-by-step)
   - [Basic GET endpoint caching](#basic-get-endpoint-caching)
   - [Cache Invalidation on Updates](#cache-invalidation-on-updates)
   - [Full Example: Users API with Redis](#full-example-users-api-with-redis)
8. [Error Handling & Resilience](#error-handling--resilience)
9. [Advanced Tips](#advanced-tips)
   - [Using Hashes for partial caching](#using-hashes-for-partial-caching)
   - [Pipelining & Connection Pooling](#pipelining--connection-pooling)
   - [Cache Stampede Protection](#cache-stampede-protection)
10. [Practice Questions & Exercises](#practice-questions--exercises)
11. [Full .md File Ready](#full-md-file-ready)
---
## Why Redis for Caching?
**Redis** (Remote Dictionary Server) is an in‑memory data store. Data lives in RAM, making reads and writes incredibly fast (sub‑millisecond). It supports rich data structures beyond simple key‑value pairs.
### Benefits for caching:
- **Speed**: In‑memory storage reduces latency dramatically compared to database queries.
- **Data Structures**: Strings, hashes, lists, sets – you can cache complex objects efficiently.
- **Built‑in TTL**: Automatic expiration of keys prevents stale data.
- **Persistence options**: RDB/AOF snapshots if you need durability.
- **Atomic operations**: Safe for concurrent access.
- **Scaling**: Clustering, replication, Sentinel for high‑availability.
Common use‑case: cache database results, API responses, session data, rate‑limiting counters.
---
## Redis Installation & Setup
### Option 1: Local Installation (Linux/macOS)
- **Ubuntu/Debian**  
```bash
sudo apt update
sudo apt install redis-server
sudo systemctl enable redis-server
sudo systemctl start redis-server
```
- **Verify**: `redis-cli ping` → should return `PONG`.

### Option 2: Docker
```bash
docker run --name redis-cache -p 6379:6379 -d redis:7-alpine
```
Check: `docker ps` – the container must be running.

### Node.js Client Library

We’ll use the official `redis` package (v4+ with async/await support).
```bash
npm install redis
```

---

## Connecting Node.js to Redis

Create a module `redisClient.js`:
```javascript
const redis = require('redis');
const client = redis.createClient({
  url: 'redis://localhost:6379'   // default, adjust for Docker or remote
});
client.on('error', (err) => console.error('Redis Client Error', err));
// Connect (async)
(async () => {
  await client.connect();
  console.log('Connected to Redis');
})();
module.exports = client;
```
> **Important**: The `redis` package v4+ requires explicit `client.connect()`. All commands return Promises.  
> Use `await` or `.then()`.

---

## Redis Data Types (with caching focus)

|Type|Description|Caching Use‑Case|
|---|---|---|
|**String**|Binary safe, up to 512 MB.|Serialize JSON objects/strings/numbers.|
|**Hash**|Map of field‑value pairs (like an object).|Cache specific fields of a record.|
|**List**|Linked list of strings.|Caching ordered logs, queues.|
|**Set**|Unordered unique strings.|Cache tags, unique items.|
|**Sorted Set**|Each member has a score; ordered by score.|Leaderboards, time‑based caches.|
|**Stream**|Append‑only log.|Event sourcing, not typical for caching.|

For most API caching, **String** is enough. You serialize a JavaScript object with `JSON.stringify()` and parse it back with `JSON.parse()`.

---

## Key Commands for Caching

### SET, GET, DEL, EXISTS

javascript

// SET a key (string)
await client.set('user:1001', JSON.stringify({ id: 1001, name: 'Alice' }));
// GET
const data = await client.get('user:1001');
const user = JSON.parse(data);   // if exists, else null
// DELETE
await client.del('user:1001');
// EXISTS
const exists = await client.exists('user:1001');   // returns 1 or 0

### TTL & Expiration (EXPIRE, SETEX, TTL, PTTL)

TTL = Time‑To‑Live. After expiration, the key is automatically deleted.

- **SETEX**: Set with expiration in seconds
    
    javascript
    
    await client.setEx('session:abc', 3600, 'active'); // 1 hour
    
- **SET with EX/PX options**:
    
    javascript
    
    await client.set('temp', 'value', { EX: 60 });   // 60 seconds
    await client.set('temp', 'value', { PX: 60000 }); // 60,000 milliseconds
    
- **EXPIRE / PEXPIRE** on existing key:
    
    javascript
    
    await client.expire('user:1001', 120);   // seconds
    await client.pExpire('user:1001', 120000); // milliseconds
    
- **TTL / PTTL** – check remaining time:
    
    javascript
    
    const remainingSec = await client.ttl('user:1001');
    // -1: no expiry, -2: key doesn't exist
    

### Pattern Deletion (SCAN + DEL)

Sometimes you need to delete multiple keys matching a pattern (e.g., `user:*`).  
**Never use `KEYS` in production** – it blocks. Use `SCAN`:

javascript

async function deleteKeysByPattern(pattern) {
  let cursor = 0;
  do {
    const reply = await client.scan(cursor, { MATCH: pattern, COUNT: 100 });
    cursor = reply.cursor;
    const keys = reply.keys;
    if (keys.length > 0) {
      await client.del(keys);
    }
  } while (cursor !== 0);
}
// Usage: await deleteKeysByPattern('user:*');

---

## Caching Strategies

### Cache-Aside (Lazy Loading)

_Most common in web apps._

- **Read**:
    
    1. Check cache.
        
    2. If **hit** → return cached data.
        
    3. If **miss** → query DB, store result in cache (with TTL), return data.
        
- **Write**:  
    Update DB first, then **invalidate** (delete) the corresponding cache key.  
    (Or update cache, but invalidation is simpler and avoids inconsistent states.)
    

**Pros**: Resilient (cache can be empty), only caches what’s needed.  
**Cons**: First request after invalidation is slow; cache stampede possible.

### Write-Through

- Write data to cache and DB simultaneously.
    
- Cache is always up‑to‑date.
    
- **Drawback**: Slower writes (both operations), still needs DB as source of truth.
    

### Write-Behind (Write-Back)

- Write to cache first, then asynchronously to DB.
    
- Faster writes, but risk of data loss if cache crashes before DB sync.
    

_We’ll focus on **Cache-Aside**, which works perfectly for most API endpoints._

---

## Caching in Express – Step by Step

### Basic GET endpoint caching

Create a reusable caching middleware:

javascript

// cacheMiddleware.js
const client = require('./redisClient');
/**
 * Middleware that caches GET responses.
 * @param {number} ttlSeconds - Expiration time in seconds.
 */
function cache(ttlSeconds = 60) {
  return async (req, res, next) => {
    // Only cache GET requests
    if (req.method !== 'GET') {
      return next();
    }
    // Build a unique key from the URL and query params
    const key = `cache:${req.originalUrl || req.url}`;
    try {
      const cached = await client.get(key);
      if (cached) {
        console.log('Cache HIT:', key);
        return res.json(JSON.parse(cached));
      }
      // Intercept res.json() to cache the response
      const originalJson = res.json.bind(res);
      res.json = (body) => {
        // Store in Redis (fire & forget, but catch errors)
        client.setEx(key, ttlSeconds, JSON.stringify(body)).catch(err => {
          console.error('Redis setEx error:', err);
        });
        return originalJson(body);
      };
      next();
    } catch (err) {
      console.error('Redis middleware error:', err);
      next(); // Fallback to database if Redis fails
    }
  };
}
module.exports = cache;

Use it in routes:

javascript

const express = require('express');
const router = express.Router();
const cache = require('./cacheMiddleware');
const { getUserFromDB } = require('./db');
router.get('/users/:id', cache(120), async (req, res) => {
  const user = await getUserFromDB(req.params.id);
  if (!user) return res.status(404).json({ error: 'User not found' });
  res.json(user);
});

- On first `GET /users/1`, cache miss → DB query → cache `cache:/users/1` for 120s.
    
- Subsequent requests within 120s return cached JSON immediately.
    

### Cache Invalidation on Updates

When data changes, delete the cached key so the next read fetches fresh data.

javascript

router.patch('/users/:id', async (req, res) => {
  const updated = await updateUserInDB(req.params.id, req.body);
  // Invalidate cache
  await client.del(`cache:/users/${req.params.id}`);
  // Optionally invalidate list endpoints if affected
  // e.g., await deleteKeysByPattern('cache:/users?*');
  res.json(updated);
});
router.delete('/users/:id', async (req, res) => {
  await deleteUserFromDB(req.params.id);
  await client.del(`cache:/users/${req.params.id}`);
  res.status(204).end();
});

> **Key naming convention**: Be consistent, e.g., `cache:/resource/:id` or `api:users:1`. This makes invalidation easy.

### Full Example: Users API with Redis

(Assuming in‑memory array or DB; the focus is caching.)

javascript

// server.js
const express = require('express');
const client = require('./redisClient');
const cache = require('./cacheMiddleware');
const app = express();
app.use(express.json());
// Mock DB
let users = [
  { id: 1, name: 'Alice', email: 'alice@example.com' },
  { id: 2, name: 'Bob', email: 'bob@example.com' }
];
// Helper to simulate async DB
const findUser = (id) => users.find(u => u.id === id);
const updateUser = (id, data) => {
  const idx = users.findIndex(u => u.id === id);
  if (idx === -1) return null;
  users[idx] = { ...users[idx], ...data };
  return users[idx];
};
const deleteUser = (id) => {
  users = users.filter(u => u.id !== id);
  return true;
};
// GET /users/:id - cached
app.get('/users/:id', cache(60), async (req, res) => {
  const id = parseInt(req.params.id);
  const user = findUser(id);
  if (!user) return res.status(404).json({ error: 'Not found' });
  res.json(user);
});
// PATCH /users/:id - invalidate cache
app.patch('/users/:id', async (req, res) => {
  const id = parseInt(req.params.id);
  const updated = updateUser(id, req.body);
  if (!updated) return res.status(404).json({ error: 'Not found' });
  await client.del(`cache:/users/${id}`);
  res.json(updated);
});
// DELETE /users/:id - invalidate cache
app.delete('/users/:id', async (req, res) => {
  const id = parseInt(req.params.id);
  deleteUser(id);
  await client.del(`cache:/users/${id}`);
  res.status(204).end();
});
app.listen(3000, () => console.log('Server running on port 3000'));

Test with `curl`:

bash

curl http://localhost:3000/users/1    # Miss → DB, cached
curl http://localhost:3000/users/1    # Hit → Redis
curl -X PATCH -H "Content-Type: application/json" -d '{"name":"Ally"}' http://localhost:3000/users/1
curl http://localhost:3000/users/1    # Miss → DB, fresh data

---

## Error Handling & Resilience

Redis is fast, but network issues or crashes happen. Your app should **not** fail if Redis is down – fall back to the database.

In the caching middleware we wrapped the `client.get` in a try/catch and called `next()`.  
For invalidation, you can ignore errors (or log them) because stale cache will expire eventually:

javascript

try {
  await client.del(`cache:/users/${id}`);
} catch (err) {
  console.error('Redis del failed, cache may be stale:', err.message);
}

You can also implement **circuit breaker** patterns, but a simple try/catch is often enough.

---

## Advanced Tips

### Using Hashes for partial caching

Instead of caching a whole object, cache individual fields:

javascript

await client.hSet('user:1', 'name', 'Alice');
await client.hSet('user:1', 'email', 'alice@example.com');
await client.expire('user:1', 3600);
const name = await client.hGet('user:1', 'name');
const all = await client.hGetAll('user:1');   // { name, email }

### Pipelining & Connection Pooling

Pipelining batches commands to reduce network round‑trips:

javascript

const pipeline = client.multi();
pipeline.set('a', '1');
pipeline.set('b', '2');
pipeline.get('a');
const results = await pipeline.exec(); // [null, null, '1']

The `redis` package automatically manages a connection pool, but you can tune it via options (e.g., `socket.reconnectStrategy`).

### Cache Stampede Protection

When a popular cache key expires, multiple concurrent requests can hit the DB simultaneously. Mitigations:

- **Early recompute**: Refresh the key slightly before expiry using a background job.
    
- **Locking**: Use `SETNX` to acquire a lock; only one process recomputes, others serve stale data briefly.  
    Example:
    

javascript

const lockKey = `lock:${key}`;
const acquired = await client.set(lockKey, '1', { NX: true, PX: 5000 });
if (acquired) {
  // recompute and set cache
  await client.del(lockKey);
}

---

## Practice Questions & Exercises

### Setup & Basics

1. Install Redis locally (or via Docker) and connect to it from a Node.js script. Run `client.ping()` and print the result.
    
2. Store a JSON string under key `"test:1"` with a TTL of 10 seconds. Retrieve it immediately and after 11 seconds. What happens?
    
3. Create a function that increments a counter key `"page:visits"` every time it's called, and sets it to expire after 1 hour if the key is new.
    
4. Write a script that scans all keys matching `user:*` and deletes them. Test with at least 5 keys.
    

### Caching in Express

5. Implement the cache-aside pattern for a `GET /products/:id` endpoint. The “database” can be a local array. Cache for 30 seconds.
    
6. Add a `POST /products` endpoint that creates a new product and clears the cache for the new product’s ID (even if it doesn’t exist yet). Why might you want to clear it? _(Hint: to prevent stale list caches if you have them.)_
    
7. Build a `GET /products` list endpoint that caches the entire product array. What’s a good cache key? How do you invalidate it when a product is added/updated/deleted?
    
8. Create a middleware that skips caching when the request header `x-no-cache` is present. Test with `curl -H "x-no-cache: true" ...`.
    
9. Implement a “stale-while-revalidate” strategy using two keys: a “stale” key with longer TTL and a “fresh” key with short TTL. When fresh expires, return stale data and asynchronously refresh. _(Challenging!)_
    

### Error Handling & Resilience

10. Simulate a Redis connection failure (e.g., stop Redis). Ensure your Express app still returns data from the database without crashing.
    
11. Extend the middleware to log cache hit/miss ratios. How would you implement that with counters in Redis?
    

### Advanced

12. Use Redis Hashes to cache a user’s profile fields separately. Update just the `email` field in cache and DB on PATCH.
    
13. Implement a simple locking mechanism to prevent cache stampede when recomputing a heavy query.
    
14. Experiment with `redis` client `duplicate()` to create a subscriber for pub/sub and invalidate caches across multiple app instances