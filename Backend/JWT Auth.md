## 1. The Problem JWT Solves

### The Old Way — Sessions

In a traditional session-based system:

1. User logs in → server creates a session in its database
2. Server gives user a `session_id` (stored in a cookie)
3. On every request, server looks up `session_id` in the DB to verify who the user is

```
Client                    Server                    Database
  |                          |                          |
  |-- POST /login ---------->|                          |
  |                          |-- INSERT session ------->|
  |<-- Set-Cookie: sid=abc --|<-- session_id: abc ------|
  |                          |                          |
  |-- GET /profile (sid=abc)>|                          |
  |                          |-- SELECT * WHERE sid=abc>|
  |                          |<-- { userId: 42 } -------|
  |<-- 200 { profile } ------|                          |
```

**The problems:**
- Every request hits the database — slow at scale
- Hard to scale horizontally (Server B doesn't know about sessions on Server A)
- Doesn't work well for APIs consumed by mobile apps, third parties, or microservices

### The JWT Way — Stateless

With JWT, the server doesn't store anything. The token itself contains the user's identity, and the server verifies its authenticity using a secret key — no database lookup needed.

```
Client                    Server A (or B or C)
  |                          |
  |-- POST /login ---------->|
  |<-- { token: "eyJ..." } --|  (server signs a token, stores NOTHING)
  |                          |
  |-- GET /profile           |
|   Authorization: Bearer eyJ...>|
  |                          |  (server verifies signature — no DB call)
  |<-- 200 { profile } ------|
```

**The benefits:**
- Stateless — any server can verify any token
- Scales horizontally with zero friction
- Works perfectly for APIs, microservices, and mobile apps

---

## 2. What is a JWT?

JWT stands for **JSON Web Token**. It is an open standard ([RFC 7519](https://tools.ietf.org/html/rfc7519)) for securely transmitting information between two parties as a compact, URL-safe string.

The key word is **verifiable** — anyone with the secret key can confirm that a JWT was genuinely issued by the server and hasn't been tampered with.

A JWT looks like this:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQyLCJlbWFpbCI6ImFsaWNlQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE3MjM0NTY3LCJleHAiOjE3MTcyMzgxNjd9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

Three parts separated by dots (`.`):

```
HEADER.PAYLOAD.SIGNATURE
```

---

## 3. JWT Structure Deep Dive

### Part 1: Header

The header declares the token type and the signing algorithm used.

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

This is **Base64Url encoded** (not encrypted) to produce:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

> ⚠️ **Base64 ≠ Encryption.** Anyone can decode the header and payload. The security comes from the *signature*, not from hiding the data.

---

### Part 2: Payload

The payload holds the **claims** — the actual data you want to transmit.

```json
{
  "userId": 42,
  "email": "alice@example.com",
  "role": "admin",
  "iat": 1717234567,
  "exp": 1717238167
}
```

Base64Url encoded:
```
eyJ1c2VySWQiOjQyLCJlbWFpbCI6ImFsaWNlQGV4YW1wbGUuY29tIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNzE3MjM0NTY3LCJleHAiOjE3MTcyMzgxNjd9
```

---

### Part 3: Signature

The signature is what makes the token secure and tamper-proof.

It is computed as:

```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  YOUR_SECRET_KEY
)
```

If even a single character in the header or payload changes, the signature becomes completely invalid. This is how the server detects tampering.

**Full token assembled:**

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9        ← Header
.
eyJ1c2VySWQiOjQyLCJlbWFpbCI6ImFsaWNlQGV4YW1wbGUuY29tIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNzE3MjM0NTY3LCJleHAiOjE3MTcyMzgxNjd9   ← Payload
.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c ← Signature
```

**What happens if an attacker tries to change `"role": "admin"` to `"role": "user"` in the payload?**

The payload's Base64 changes → the signature no longer matches → the server rejects the token. The attacker can't forge a valid signature without the secret key.

---

## 4. Under the Hood — Encoding, Hashing & Cryptography

These are the building blocks that make JWT work. Every time you see `eyJ...` you are looking at Base64URL. Every time a token is verified, HMAC-SHA256 runs under the hood. Understanding these removes all the magic.

---

### 4.1 Encoding vs Encryption vs Hashing

Three words that are constantly conflated. They are fundamentally different operations:

| | Encoding | Encryption | Hashing |
|---|---|---|---|
| **Purpose** | Convert data to a different format | Conceal data from unauthorized parties | Produce a fixed-size fingerprint |
| **Reversible?** | ✅ Yes — trivially, no key needed | ✅ Yes — but only with the correct key | ❌ No — strictly one-way |
| **Requires a key?** | ❌ No | ✅ Yes | ❌ No (HMAC adds a key on top) |
| **Examples** | Base64, UTF-8, URL encoding | AES, RSA encryption, ChaCha20 | SHA-256, SHA-512, MD5 |
| **Used in JWT for** | Header & Payload representation | Not used (see JWE below) | Signature computation |

**The critical rule:** A standard JWT is *encoded* and *signed*. It is **not encrypted**. Anyone who has the token can Base64URL-decode the header and payload and read them. The signature only proves the token was not tampered with — it does not hide anything.

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9   ← base64url encoded (readable)
.
eyJ1c2VySWQiOjQyLCJyb2xlIjoiYWRtaW4ifQ ← base64url encoded (readable)
.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c  ← HMAC signature (not reversible)
```

---

### 4.2 What is Base64?

**Definition:** Base64 is an encoding scheme that converts arbitrary binary data (bytes) into a string composed of only 64 printable ASCII characters. It allows binary data to be safely transmitted over systems that only handle text.

**Why 64?** Because 6 bits can represent exactly 64 distinct values (2⁶ = 64). By mapping every 6 bits of input to one of 64 printable characters, you can safely encode any binary data as plain ASCII text.

#### The Base64 Alphabet

```
Value  0–25:   A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
Value 26–51:   a b c d e f g h i j k l m n o p q r s t u v w x y z
Value 52–61:   0 1 2 3 4 5 6 7 8 9
Value 62:      +
Value 63:      /
Padding char:  =  (used to pad the output to a multiple of 4 chars)
```

#### How Base64 Works — Step by Step

Base64 takes **3 bytes (24 bits)** of input at a time and produces **4 Base64 characters** (4 × 6 bits = 24 bits). This is why Base64 output is always 33% larger than the input.

**Example: encoding the word `"Man"`**

Step 1 — Convert each character to its ASCII byte value:

```
Character:    M          a          n
ASCII value:  77         97         110
Binary:       01001101   01100001   01101110
```

Step 2 — Concatenate all 24 bits into one stream:

```
010011010110000101101110
```

Step 3 — Split into groups of 6 bits:

```
010011  |  010110  |  000101  |  101110
```

Step 4 — Look up each 6-bit value in the Base64 alphabet:

```
010011 = 19  →  T
010110 = 22  →  W
000101 =  5  →  F
101110 = 46  →  u
```

Step 5 — Result: **`TWFu`** ✅

**Verify it yourself:**
```javascript
Buffer.from('Man').toString('base64'); // "TWFu"
Buffer.from('TWFu', 'base64').toString('utf-8'); // "Man"
```

#### Padding with `=`

Base64 must process input in 3-byte chunks. If the total input length is not divisible by 3, padding is added:

| Leftover bytes | Padding added | Example |
|---|---|---|
| 0 bytes | none | `"Man"` → `"TWFu"` |
| 1 byte | `==` | `"M"` → `"TQ=="` |
| 2 bytes | `=` | `"Ma"` → `"TWE="` |

```javascript
Buffer.from('M').toString('base64');    // "TQ=="
Buffer.from('Ma').toString('base64');   // "TWE="
Buffer.from('Man').toString('base64');  // "TWFu"
Buffer.from('Many').toString('base64'); // "TWFueQ=="  ← "Man" is one chunk, "y" needs padding
```

The `=` characters are not actual data — they are padding to make the output length a multiple of 4.

---

### 4.3 What is Base64URL?

**Definition:** Base64URL is a modified variant of Base64 designed to be safe for use in URLs, HTTP headers, and filenames. It makes three small but critical changes to the standard Base64 alphabet.

#### Why Standard Base64 Breaks in URLs

Three characters in the standard Base64 alphabet have **special reserved meanings in URLs**:

| Char | What it means in a URL | What goes wrong |
|------|------------------------|-----------------|
| `+` | Represents a space character in query strings | `token=abc+def` is decoded as `abc def` |
| `/` | Path separator | `token=abc/def` is parsed as two path segments |
| `=` | Key=value delimiter in query strings | `token=abc==` can confuse parsers |

If a standard Base64 JWT appeared in a URL like:
```
https://example.com/verify?token=eyJ+IjoiYWRtaW4iLCJpYXQiOjE3M=
```
The URL parser would corrupt the `+` into a space and could misparse the `=` — the token would arrive at the server as something completely different.

#### The Three Changes Base64URL Makes

| Standard Base64 | Base64URL | Reason |
|-----------------|-----------|--------|
| `+` (value 62) | `-` (hyphen) | Hyphen is URL-safe |
| `/` (value 63) | `_` (underscore) | Underscore is URL-safe |
| `=` padding | *stripped entirely* | Not needed for fixed-format parsing |

```
Standard Base64:  eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9==
Base64URL:        eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9    ← no padding, safe everywhere
```

**Padding can be stripped in Base64URL** because a JWT consumer always knows where each part ends (the `.` delimiter), so there is no ambiguity about the length.

```javascript
// Standard Base64
Buffer.from('hello world').toString('base64');
// "aGVsbG8gd29ybGQ="   ← has = padding

// Base64URL (Node.js built-in since v14)
Buffer.from('hello world').toString('base64url');
// "aGVsbG8gd29ybGQ"    ← no padding, and - / _ variants if needed

// Manual conversion from base64 to base64url
const toBase64Url = (b64) => b64.replace(/\+/g, '-').replace(/\//g, '_').replace(/=/g, '');
const toBase64    = (b64url) => b64url.replace(/-/g, '+').replace(/_/g, '/') + '='.repeat((4 - b64url.length % 4) % 4);
```

#### Decoding a JWT Part Manually

```javascript
function decodeJwtPart(base64UrlStr) {
  // Convert Base64URL → standard Base64 → bytes → UTF-8 string → parse JSON
  const base64 = base64UrlStr
    .replace(/-/g, '+')
    .replace(/_/g, '/')
    .padEnd(base64UrlStr.length + (4 - base64UrlStr.length % 4) % 4, '=');

  return JSON.parse(Buffer.from(base64, 'base64').toString('utf-8'));
}

const headerPart = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9';
console.log(decodeJwtPart(headerPart));
// → { alg: 'HS256', typ: 'JWT' }

const payloadPart = 'eyJ1c2VySWQiOjQyLCJyb2xlIjoiYWRtaW4ifQ';
console.log(decodeJwtPart(payloadPart));
// → { userId: 42, role: 'admin' }
```

---

### 4.4 The Full Encoding Pipeline

Here is the exact, step-by-step journey from a JavaScript object to the first part of a JWT:

```
① JavaScript object
   { alg: "HS256", typ: "JWT" }

② JSON.stringify() → a UTF-8 string
   '{"alg":"HS256","typ":"JWT"}'

③ TextEncoder / Buffer → UTF-8 bytes
   7b 22 61 6c 67 22 3a 22 48 53 32 35 36 22 2c
   22 74 79 70 22 3a 22 4a 57 54 22 7d

④ Base64URL encode those bytes
   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

**In Node.js, you can watch every step:**

```javascript
// ① Start object
const header = { alg: 'HS256', typ: 'JWT' };

// ② JSON string
const jsonStr = JSON.stringify(header);
console.log(jsonStr);       // '{"alg":"HS256","typ":"JWT"}'

// ③ UTF-8 bytes (as hex for readability)
const bytes = Buffer.from(jsonStr, 'utf-8');
console.log(bytes.toString('hex'));
// 7b22616c67223a22485332353622 2c22747970223a224a5754227d

// ④ Base64URL encoded
const encoded = bytes.toString('base64url');
console.log(encoded);       // 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9'

// Full reverse:
const back = JSON.parse(Buffer.from(encoded, 'base64url').toString('utf-8'));
console.log(back);          // { alg: 'HS256', typ: 'JWT' }
```

**This pipeline is fully reversible with zero secret.** There is no key, no magic — just encoding. This is exactly why a JWT's header and payload are completely readable by anyone who has the token string.

---

### 4.5 What is a Hash Function?

**Definition:** A hash function (also called a cryptographic digest function) takes an input of any length and deterministically produces a fixed-length output called a **hash**, **digest**, or **checksum**. The operation is strictly one-way.

SHA-256 (Secure Hash Algorithm 256-bit) always produces a 256-bit (32-byte) output — regardless of whether the input is 1 byte or 1 terabyte.

#### The Four Properties That Make Hashing Useful

**1. Deterministic** — identical input always produces identical output:
```
SHA256("hello") → 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
SHA256("hello") → 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
                   ↑ bit-for-bit identical, every single time
```

**2. One-way (Pre-image resistance)** — given a hash, you cannot recover the input:
```
Given:  2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
Find:   ??? → mathematically infeasible to work backwards to "hello"
```

**3. Avalanche effect** — changing even one bit of input produces a completely different hash:
```
SHA256("hello")  → 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
SHA256("hellO")  → 9b71d224bd62f3785d96d46ad3ea3d73319bfbc2890caadae2dff72519673ca7
                   ↑ completely different — not just slightly shifted
```

**4. Collision resistance** — it is computationally infeasible to find two different inputs that produce the same hash output.

**In Node.js:**
```javascript
const crypto = require('crypto');

// Hash a string
const hash = crypto.createHash('sha256').update('hello').digest('hex');
console.log(hash);
// 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824

// Hashing the same value always gives the same result
crypto.createHash('sha256').update('hello').digest('hex') ===
crypto.createHash('sha256').update('hello').digest('hex'); // true

// Completely different hash for even a tiny change
crypto.createHash('sha256').update('hellO').digest('hex');
// 9b71d224bd62f3785d96d46ad3ea3d73319bfbc2890caadae2dff72519673ca7
```

**Why hashing alone is not enough for JWT:** A hash function has no secret key. If you just hashed the JWT payload, an attacker could change the payload, recompute the hash themselves, and attach the new hash — no one could tell. HMAC solves this.

---

### 4.6 What is HMAC?

**Definition:** HMAC (Hash-based Message Authentication Code) is a construction that combines a **secret key** with a hash function to produce a **message authentication code (MAC)**. It provides two guarantees simultaneously:

- **Integrity** — the message was not altered in transit
- **Authenticity** — the message was created by someone who holds the secret key

A plain hash only proves integrity. HMAC proves both integrity AND authenticity because only someone with the correct key can compute a matching MAC.

#### How HMAC Works Conceptually

The full HMAC formula (RFC 2104):

```
HMAC(K, m) = Hash((K' ⊕ opad) ∥ Hash((K' ⊕ ipad) ∥ m))

Where:
  K  = the secret key
  K' = key padded/truncated to the hash's block size
  m  = the message to authenticate
  ⊕  = XOR (exclusive-or)
  ∥  = concatenation
  opad = 0x5c byte repeated (outer padding)
  ipad = 0x36 byte repeated (inner padding)
```

You do not need to memorize this formula. What matters are the behavioral guarantees:

```
Same key    + same message  → identical HMAC    (deterministic)
Wrong key   + same message  → totally different HMAC
Right key   + altered message → totally different HMAC
```

**In Node.js:**
```javascript
const crypto = require('crypto');

const key = 'my-secret-key';
const message = 'the data to protect';

const mac = crypto.createHmac('sha256', key).update(message).digest('hex');
console.log(mac); // a fixed, unique hex string

// Verify: compute again with the same key and compare
const mac2 = crypto.createHmac('sha256', key).update(message).digest('hex');
console.log(mac === mac2); // true ✅

// Any change → different MAC
const mac3 = crypto.createHmac('sha256', key).update('the data to Protect').digest('hex');
console.log(mac === mac3); // false ✅ — tampered message detected

// Wrong key → different MAC
const mac4 = crypto.createHmac('sha256', 'wrong-key').update(message).digest('hex');
console.log(mac === mac4); // false ✅ — key mismatch detected
```

---

### 4.7 HMACSHA256 — The JWT Signature

Now every piece is in place to fully understand how the JWT signature is computed and why it is trustworthy.

**The exact formula:**

```
signature = Base64URL(
  HMAC-SHA256(
    base64url(header) + "." + base64url(payload),
    secret_key
  )
)
```

**Manual, from-scratch JWT construction in Node.js:**

```javascript
const crypto = require('crypto');

const SECRET = 'my-super-secret-key';

// Step 1: Encode header
const headerObj = { alg: 'HS256', typ: 'JWT' };
const header = Buffer.from(JSON.stringify(headerObj)).toString('base64url');
console.log('Header:', header);
// eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9

// Step 2: Encode payload
const payloadObj = { userId: 42, role: 'admin', iat: Math.floor(Date.now() / 1000) };
const payload = Buffer.from(JSON.stringify(payloadObj)).toString('base64url');
console.log('Payload:', payload);
// eyJ1c2VySWQiOjQyLCJyb2xlIjoiYWRtaW4iLCJpYXQiOjE3MTcyMzQ1Njd9

// Step 3: Create the signing input (what gets signed)
const signingInput = `${header}.${payload}`;

// Step 4: Compute HMAC-SHA256 over the signing input using the secret
const signature = crypto
  .createHmac('sha256', SECRET)
  .update(signingInput)
  .digest('base64url'); // base64url-encode the raw HMAC bytes
console.log('Signature:', signature);

// Step 5: Assemble the JWT
const token = `${signingInput}.${signature}`;
console.log('\nFull JWT:');
console.log(token);

// Verification: recompute signature from header.payload and compare
function verify(token, secret) {
  const parts = token.split('.');
  if (parts.length !== 3) throw new Error('Malformed token');

  const [header, payload, signature] = parts;
  const signingInput = `${header}.${payload}`;

  const expectedSig = crypto
    .createHmac('sha256', secret)
    .update(signingInput)
    .digest('base64url');

  // Timing-safe comparison to prevent timing attacks
  const sigBuffer      = Buffer.from(signature,    'base64url');
  const expectedBuffer = Buffer.from(expectedSig,  'base64url');

  if (sigBuffer.length !== expectedBuffer.length) throw new Error('Invalid signature');
  if (!crypto.timingSafeEqual(sigBuffer, expectedBuffer)) throw new Error('Invalid signature');

  return JSON.parse(Buffer.from(payload, 'base64url').toString('utf-8'));
}

const decoded = verify(token, SECRET);
console.log('\nDecoded:', decoded);
// { userId: 42, role: 'admin', iat: 1717234567 }
```

**What makes tampering impossible:**

```
Attacker intercepts:  eyJhbG....eyJ1c2VySWQiOjQyLCJyb2xlIjoiYWRtaW4ifQ.SIGNATURE
                                                             ↑
Attacker changes:     "role":"admin"  →  "role":"superadmin"
                                                             ↓
New payload:          eyJ1c2VySWQiOjQyLCJyb2xlIjoic3VwZXJhZG1pbiJ9  ← different Base64URL

The signing input header.payload is now different
  ↓
HMAC-SHA256(new_signing_input, secret) produces a completely different digest
  ↓
The existing signature no longer matches the new signing input
  ↓
Server calls verify(): signatures don't match → token rejected with JsonWebTokenError

The attacker cannot compute the correct new signature because they don't have the secret key.
```

---

### 4.8 The Dot Separator — Why `.`?

The three parts of a JWT are separated by a period (`.`). This choice is deliberate.

The Base64URL alphabet contains exactly these characters:

```
A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
a b c d e f g h i j k l m n o p q r s t u v w x y z
0 1 2 3 4 5 6 7 8 9 - _
```

The `.` (period) does **not appear anywhere in the Base64URL alphabet**. This guarantees:

```javascript
const parts = token.split('.');
// Always produces exactly 3 elements
// parts[0] → header  (Base64URL, no dots inside)
// parts[1] → payload (Base64URL, no dots inside)
// parts[2] → signature (Base64URL, no dots inside)
```

There is zero ambiguity. A dot inside the token always and only means "separator between parts." You will never accidentally split at the wrong position.

This is also why the signing input uses `.` as a separator:

```javascript
const signingInput = `${header}.${payload}`;
// The dot here is meaningful — "sign exactly this header with exactly this payload"
// An attacker cannot construct a different header+payload combination
// that produces the same signingInput string
```

---

### 4.9 JWS vs JWE — Signed vs Encrypted JWT

JWT is an umbrella term. The JOSE (JSON Object Signing and Encryption) family of standards defines two concrete token formats:

#### JWS — JSON Web Signature (What everyone means by "JWT")

Structure: **3 parts** separated by two dots

```
BASE64URL(Header) . BASE64URL(Payload) . BASE64URL(Signature)
```

- Payload is **visible** (Base64URL encoded, not encrypted)
- Anyone can read the header and payload
- The signature proves the token is authentic and unmodified
- **This is what `jsonwebtoken` library produces**

#### JWE — JSON Web Encryption (Encrypted JWT)

Structure: **5 parts** separated by four dots

```
BASE64URL(Header) . BASE64URL(EncryptedKey) . BASE64URL(IV) . BASE64URL(Ciphertext) . BASE64URL(Tag)
```

- Payload is **encrypted** — unreadable without the private key
- Even if intercepted, an attacker sees only gibberish
- Used when the payload contains sensitive data that must not be readable
- Use the `jose` library for JWE (not `jsonwebtoken`)

```javascript
// JWE example with the 'jose' library
import { EncryptJWT, jwtDecrypt } from 'jose';

// Key must be exactly 32 bytes for A256GCM
const secretKey = new TextEncoder().encode('your-32-byte-secret-key-here!!');

// Encrypt — payload is completely hidden
const encrypted = await new EncryptJWT({ userId: 42, ssn: '123-45-6789' })
  .setProtectedHeader({ alg: 'dir', enc: 'A256GCM' })
  .setExpirationTime('1h')
  .encrypt(secretKey);

console.log(encrypted);
// eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIn0..IV_HERE.CIPHERTEXT_HERE.TAG_HERE
// ← 5 parts, and the payload is completely unreadable

// Decrypt
const { payload } = await jwtDecrypt(encrypted, secretKey);
console.log(payload); // { userId: 42, ssn: '123-45-6789', iat: ..., exp: ... }
```

**When to use JWE vs JWS:**

| Use JWS (standard JWT) when | Use JWE when |
|-----------------------------|--------------|
| Payload has non-sensitive data: user ID, role | Payload contains PII: SSN, medical records |
| You trust all parties who receive the token | The token passes through untrusted intermediaries |
| Performance is a priority | Confidentiality is a hard requirement |
| 99% of typical web auth scenarios | Highly regulated industries (healthcare, finance) |

---

### 4.10 URL-Safe Characters — The Full Picture

RFC 3986 (the URI standard) defines two categories of characters:

**Unreserved characters** — safe everywhere in URLs, no encoding needed:
```
A-Z  a-z  0-9  -  _  .  ~
```

**Reserved characters** — have special URL meaning, must be percent-encoded when used as data:
```
:  /  ?  #  [  ]  @  !  $  &  '  (  )  *  +  ,  ;  =
```

Base64URL deliberately uses **only unreserved characters** (`A-Z a-z 0-9 - _`), which is why a JWT can appear in any of these contexts without modification:

```
# URL query string
GET /verify?token=eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyfQ.sig

# HTTP Authorization header
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyfQ.sig

# Cookie value
Set-Cookie: accessToken=eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyfQ.sig; HttpOnly

# HTML data attribute
<div data-token="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyfQ.sig">

# JSON field
{ "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyfQ.sig" }
```

None of these contexts require any escaping or encoding because Base64URL contains no reserved characters.

---

### 4.11 Secret Key Strength & Entropy

**Entropy** is a measure of unpredictability, expressed in bits. A secret with N bits of entropy would take on average 2^(N-1) guesses to crack.

For HMAC-SHA256, the secret key should have **at least 256 bits of entropy** — matching the output size of the underlying SHA-256 hash function. Using a shorter key is mathematically valid but weakens the security to the key size.

```
Secret string         Bits of entropy    Time to brute-force (rough estimate)
─────────────────── ─────────────────  ──────────────────────────────────────
"secret"              ~35 bits           Milliseconds
"MyP@ssw0rd!"         ~50 bits           Minutes to hours
randomBytes(8)          64 bits           Years (current hardware)
randomBytes(16)        128 bits           Far beyond human timescales
randomBytes(32)        256 bits           ✅ Astronomically secure
randomBytes(64)        512 bits           ✅ Overkill but fine
```

**Always generate secrets with a CSPRNG** (Cryptographically Secure Pseudo-Random Number Generator):

```javascript
const crypto = require('crypto');

// 256-bit secret as hex string (64 hex chars)
const secret = crypto.randomBytes(32).toString('hex');
console.log(secret);
// "a3f8b2c1d4e5f6789012345678901234abcdef1234567890abcdef1234567890ab"

// Or base64url (shorter representation, same entropy)
const secretB64 = crypto.randomBytes(32).toString('base64url');
console.log(secretB64);
// "o_iy...A" (43 chars of base64url)

// ❌ NEVER derive a secret from a human-memorable string — low entropy
const badSecret = 'SuperSecretPassword123!'; // ~70 bits — not cryptographic

// ✅ Store in environment variable, never hardcode
// .env
// ACCESS_TOKEN_SECRET=a3f8b2c1d4e5f6789012345678901234abcdef1234567890abcdef1234567890ab
// REFRESH_TOKEN_SECRET=b4c9d3e2f5a6b7c8d9e0f1234567890123456789abcdef01234567890abcdef12
```

**Generate your secrets from the command line:**
```bash
# Node.js
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"

# OpenSSL
openssl rand -hex 32

# Python
python3 -c "import secrets; print(secrets.token_hex(32))"
```

---

## 5. Claims — What Goes Inside a Token

Claims are statements about the user (or entity) the token represents.

### Registered Claims (Standard — use these)

These are predefined by the JWT spec. Use them consistently.

| Claim | Full Name     | Type   | Example                          | Meaning                        |
|-------|---------------|--------|----------------------------------|--------------------------------|
| `iss` | Issuer        | string | `"https://api.myapp.com"`        | Who created the token          |
| `sub` | Subject       | string | `"user_42"`                      | Who the token is about         |
| `aud` | Audience      | string | `"https://myapp.com"`            | Who should consume the token   |
| `exp` | Expiration    | number | `1717238167` (Unix timestamp)    | When the token expires         |
| `nbf` | Not Before    | number | `1717234567`                     | Token is invalid before this   |
| `iat` | Issued At     | number | `1717234567`                     | When the token was created     |
| `jti` | JWT ID        | string | `"a8f3b1c2-..."`                 | Unique ID for this token       |

### Public Claims

Application-specific claims that don't conflict with registered ones:

```json
{
  "userId": 42,
  "email": "alice@example.com",
  "role": "admin",
  "permissions": ["read:users", "write:posts"]
}
```

### Private Claims

Agreed-upon claims between your specific services (not registered with IANA):

```json
{
  "tenantId": "acme-corp",
  "plan": "enterprise",
  "featureFlags": ["dark-mode", "beta-analytics"]
}
```

### What NOT to put in a payload

Since the payload is only Base64 encoded (not encrypted), **never put sensitive data** in it:

```json
// ❌ NEVER DO THIS
{
  "userId": 42,
  "password": "hunter2",          // Never!
  "creditCardNumber": "4111...",  // Never!
  "ssn": "123-45-6789"            // Never!
}
```

Put only what you need for authorization decisions (IDs, roles, permissions).

---

## 6. How JWT Auth Flow Works

### Login & Token Issuance

```
1. User submits credentials
   POST /auth/login
   Body: { email: "alice@example.com", password: "secret" }

2. Server validates credentials against DB

3. Server creates a JWT:
   payload = { userId: 42, role: "admin", email: "alice@..." }
   token = sign(payload, SECRET_KEY, { expiresIn: "1h" })

4. Server sends token to client
   Response: { accessToken: "eyJ...", refreshToken: "eyJ..." }
```

### Authenticated Request

```
1. Client stores the token

2. On every protected request, client sends token in the Authorization header:
   GET /api/profile
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

3. Server extracts token from header

4. Server verifies signature using its SECRET_KEY
   - Valid? → Extract userId from payload, handle request
   - Invalid/expired? → Return 401 Unauthorized

5. No database lookup needed!
```

### Token Expiry & Refresh

```
Access token expires (e.g., after 15 minutes)
  ↓
Client sends refresh token to POST /auth/refresh
  ↓
Server verifies refresh token (checks DB or signature)
  ↓
Server issues a new access token
  ↓
Client uses the new access token going forward
```

---

## 7. The `jsonwebtoken` Library

The `jsonwebtoken` npm package is the standard library for working with JWTs in Node.js.

### Installation

```bash
npm install jsonwebtoken
```

```javascript
const jwt = require('jsonwebtoken');
// OR with ES modules:
import jwt from 'jsonwebtoken';
```

---

### 6.1 Signing Tokens — `jwt.sign()`

```javascript
jwt.sign(payload, secretOrPrivateKey, [options], [callback])
```

**Basic example:**

```javascript
const jwt = require('jsonwebtoken');

const SECRET = 'my-super-secret-key'; // In real apps: use process.env.JWT_SECRET

const token = jwt.sign(
  { userId: 42, email: 'alice@example.com', role: 'admin' },
  SECRET
);

console.log(token);
// eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2...
```

**With expiration (strongly recommended):**

```javascript
const token = jwt.sign(
  { userId: 42, role: 'admin' },
  SECRET,
  { expiresIn: '1h' }    // Token expires in 1 hour
);
```

**`expiresIn` accepts many formats:**

```javascript
{ expiresIn: '15m' }      // 15 minutes
{ expiresIn: '2h' }       // 2 hours
{ expiresIn: '7d' }       // 7 days
{ expiresIn: 3600 }       // 3600 seconds (= 1 hour), when a number it's always seconds
{ expiresIn: '1y' }       // 1 year (don't do this for access tokens!)
```

**Full options reference:**

```javascript
const token = jwt.sign(
  {
    userId: 42,
    email: 'alice@example.com',
    role: 'admin'
  },
  SECRET,
  {
    expiresIn: '15m',       // Token lifetime
    issuer: 'my-app',       // Sets 'iss' claim
    audience: 'my-app-users', // Sets 'aud' claim
    subject: 'user_42',     // Sets 'sub' claim
    algorithm: 'HS256',     // Default. Other options below
    jwtid: 'unique-token-id' // Sets 'jti' claim
  }
);
```

**Async version (callback):**

```javascript
jwt.sign(
  { userId: 42 },
  SECRET,
  { expiresIn: '1h' },
  (err, token) => {
    if (err) {
      console.error('Error signing token:', err);
      return;
    }
    console.log('Token:', token);
  }
);
```

**Async version (Promise-style with util.promisify):**

```javascript
const { promisify } = require('util');
const signAsync = promisify(jwt.sign);

const token = await signAsync({ userId: 42 }, SECRET, { expiresIn: '1h' });
```

---

### 6.2 Verifying Tokens — `jwt.verify()`

This is the most critical function. It validates the signature AND checks expiration.

```javascript
jwt.verify(token, secretOrPublicKey, [options], [callback])
```

**Basic synchronous verify:**

```javascript
try {
  const decoded = jwt.verify(token, SECRET);
  console.log(decoded);
  // {
  //   userId: 42,
  //   email: 'alice@example.com',
  //   role: 'admin',
  //   iat: 1717234567,   ← "issued at" — added automatically by jwt.sign()
  //   exp: 1717238167    ← "expires at" — added because of expiresIn
  // }
} catch (err) {
  // Token is invalid or expired
  console.error(err.name, err.message);
}
```

**Understanding what verify checks:**

```javascript
// ✅ Valid token — correct signature, not expired
const decoded = jwt.verify(validToken, SECRET);
// → Returns the decoded payload

// ❌ Expired token
jwt.verify(expiredToken, SECRET);
// → Throws TokenExpiredError: jwt expired

// ❌ Wrong secret
jwt.verify(token, 'wrong-secret');
// → Throws JsonWebTokenError: invalid signature

// ❌ Tampered token
jwt.verify(tamperedToken, SECRET);
// → Throws JsonWebTokenError: invalid signature
```

**Verify with options:**

```javascript
const decoded = jwt.verify(token, SECRET, {
  algorithms: ['HS256'],         // Only accept this algorithm (prevents alg:none attacks)
  issuer: 'my-app',              // Reject tokens where iss !== 'my-app'
  audience: 'my-app-users',      // Reject tokens where aud !== 'my-app-users'
  clockTolerance: 10,            // Allow 10 seconds of clock skew between servers
  ignoreExpiration: false        // Default. Set to true only in very specific cases
});
```

**Async version:**

```javascript
jwt.verify(token, SECRET, { algorithms: ['HS256'] }, (err, decoded) => {
  if (err) {
    if (err.name === 'TokenExpiredError') {
      return res.status(401).json({ error: 'Token expired, please refresh' });
    }
    return res.status(401).json({ error: 'Invalid token' });
  }
  // decoded.userId is now available
  req.user = decoded;
});
```

**Promise-based verify:**

```javascript
const verifyAsync = promisify(jwt.verify);

try {
  const decoded = await verifyAsync(token, SECRET, { algorithms: ['HS256'] });
  req.user = decoded;
} catch (err) {
  res.status(401).json({ error: err.message });
}
```

---

### 6.3 Decoding Without Verification — `jwt.decode()`

`decode()` reads the token payload **without verifying the signature**. Use this sparingly.

```javascript
const decoded = jwt.decode(token);
// Same output as verify — BUT NO SIGNATURE CHECK. Anyone could have issued this token.

// With complete header info:
const full = jwt.decode(token, { complete: true });
console.log(full);
// {
//   header: { alg: 'HS256', typ: 'JWT' },
//   payload: { userId: 42, role: 'admin', iat: ..., exp: ... },
//   signature: 'SflKxwRJSMeKKF2QT4...'
// }
```

**When to use `decode()` vs `verify()`:**

| Use case                                                     | Use               |
|--------------------------------------------------------------|-------------------|
| Validating a user's request on your server                   | `verify()` always |
| Reading claims on the client side (you already trust the token) | `decode()` is OK  |
| Checking expiry client-side before sending a request         | `decode()` is OK  |
| Debugging — peeking inside a token                           | `decode()` is OK  |

> ⚠️ **Never use `decode()` on the server to make authorization decisions.** Use `verify()`.

---

### 6.4 Algorithms

The algorithm controls how the signature is created.

| Algorithm | Type        | Key         | Best For                            |
|-----------|-------------|-------------|-------------------------------------|
| `HS256`   | Symmetric   | One shared secret | Simple apps, single service    |
| `HS384`   | Symmetric   | One shared secret | Like HS256 but stronger hash   |
| `HS512`   | Symmetric   | One shared secret | Like HS256 but strongest hash  |
| `RS256`   | Asymmetric  | Public/Private key pair | Microservices, OAuth2/OIDC |
| `ES256`   | Asymmetric  | EC key pair | Modern apps, smaller tokens    |

**HS256 — Symmetric (simplest):**

```javascript
// One secret shared between sign & verify
const token = jwt.sign({ userId: 42 }, 'shared-secret', { algorithm: 'HS256' });
const decoded = jwt.verify(token, 'shared-secret', { algorithms: ['HS256'] });
```

**RS256 — Asymmetric (for microservices/distributed systems):**

```javascript
const fs = require('fs');

// Generate with: openssl genrsa -out private.pem 2048
//                openssl rsa -in private.pem -pubout -out public.pem

const privateKey = fs.readFileSync('private.pem');
const publicKey = fs.readFileSync('public.pem');

// Auth service signs with PRIVATE key (kept secret)
const token = jwt.sign({ userId: 42 }, privateKey, { algorithm: 'RS256' });

// Any service verifies with PUBLIC key (can be distributed freely)
const decoded = jwt.verify(token, publicKey, { algorithms: ['RS256'] });
```

**Why RS256 for microservices?**

With HS256, every service needs the secret — a leak anywhere compromises everything. With RS256, only the auth service needs the private key. All other services just need the public key to verify, but they can't forge new tokens.

---

## 8. Access Tokens vs Refresh Tokens

This is one of the most important patterns in JWT authentication.

### The Problem with Long-Lived Tokens

If you give a user a token valid for 30 days:
- If it's stolen, the attacker has access for up to 30 days
- You can't revoke it (JWTs are stateless — there's nothing to invalidate)

### The Solution: Two-Token Pattern

```
┌─────────────────────────────────────────────────────────┐
│  Access Token                                           │
│  - Short-lived: 15 minutes to 1 hour                   │
│  - Sent with every API request                          │
│  - Contains user identity + permissions                 │
│  - Stateless — no DB lookup needed                      │
│  - If stolen: limited blast radius (expires soon)       │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│  Refresh Token                                          │
│  - Long-lived: 7 days, 30 days, or more                │
│  - Sent ONLY to /auth/refresh endpoint                  │
│  - Stored in DB (can be revoked!)                       │
│  - Used to get a new access token when it expires       │
│  - If stolen: can be revoked by deleting from DB        │
└─────────────────────────────────────────────────────────┘
```

### Implementation

**Issuing both tokens on login:**

```javascript
function generateTokens(userId, role) {
  const accessToken = jwt.sign(
    { userId, role },
    process.env.ACCESS_TOKEN_SECRET,
    { expiresIn: '15m' }
  );

  const refreshToken = jwt.sign(
    { userId },
    process.env.REFRESH_TOKEN_SECRET,
    { expiresIn: '7d' }
  );

  return { accessToken, refreshToken };
}

// Login endpoint
app.post('/auth/login', async (req, res) => {
  const { email, password } = req.body;

  const user = await User.findOne({ email });
  if (!user || !await user.comparePassword(password)) {
    return res.status(401).json({ error: 'Invalid credentials' });
  }

  const { accessToken, refreshToken } = generateTokens(user.id, user.role);

  // Store refresh token in DB (allows revocation)
  await RefreshToken.create({
    token: refreshToken,
    userId: user.id,
    expiresAt: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)
  });

  res.json({ accessToken, refreshToken });
});
```

**Refresh endpoint:**

```javascript
app.post('/auth/refresh', async (req, res) => {
  const { refreshToken } = req.body;

  if (!refreshToken) {
    return res.status(401).json({ error: 'Refresh token required' });
  }

  try {
    // 1. Verify the refresh token's signature and expiry
    const decoded = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET);

    // 2. Check it exists in DB (hasn't been revoked)
    const storedToken = await RefreshToken.findOne({ token: refreshToken });
    if (!storedToken) {
      return res.status(401).json({ error: 'Refresh token revoked' });
    }

    // 3. Get fresh user data from DB
    const user = await User.findById(decoded.userId);

    // 4. Issue a new access token
    const newAccessToken = jwt.sign(
      { userId: user.id, role: user.role },
      process.env.ACCESS_TOKEN_SECRET,
      { expiresIn: '15m' }
    );

    res.json({ accessToken: newAccessToken });

  } catch (err) {
    res.status(401).json({ error: 'Invalid or expired refresh token' });
  }
});
```

**Logout (revoke refresh token):**

```javascript
app.post('/auth/logout', async (req, res) => {
  const { refreshToken } = req.body;

  // Delete from DB — this "revokes" the refresh token
  await RefreshToken.deleteOne({ token: refreshToken });

  res.json({ message: 'Logged out successfully' });
});
```

---

## 9. Real-World Express.js Implementation

### Project Structure

```
src/
├── middleware/
│   └── auth.js          ← JWT verification middleware
├── routes/
│   ├── auth.js          ← Login, refresh, logout
│   └── api.js           ← Protected routes
├── models/
│   ├── user.js
│   └── refreshToken.js
└── app.js
```

### The Auth Middleware

```javascript
// middleware/auth.js
const jwt = require('jsonwebtoken');

// Basic auth middleware — requires a valid access token
function authenticate(req, res, next) {
  // Extract token from Authorization header
  const authHeader = req.headers.authorization;

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ error: 'No token provided' });
  }

  const token = authHeader.split(' ')[1]; // "Bearer TOKEN" → "TOKEN"

  try {
    const decoded = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, {
      algorithms: ['HS256']
    });

    req.user = decoded; // { userId, role, iat, exp }
    next();

  } catch (err) {
    if (err.name === 'TokenExpiredError') {
      return res.status(401).json({ error: 'Token expired', code: 'TOKEN_EXPIRED' });
    }
    return res.status(401).json({ error: 'Invalid token', code: 'INVALID_TOKEN' });
  }
}

// Role-based authorization middleware (builds on authenticate)
function authorize(...requiredRoles) {
  return (req, res, next) => {
    // req.user is set by authenticate() above
    if (!requiredRoles.includes(req.user.role)) {
      return res.status(403).json({
        error: `Access denied. Required role: ${requiredRoles.join(' or ')}`
      });
    }
    next();
  };
}

module.exports = { authenticate, authorize };
```

### Protected Routes

```javascript
// routes/api.js
const express = require('express');
const router = express.Router();
const { authenticate, authorize } = require('../middleware/auth');

// Any logged-in user
router.get('/profile', authenticate, async (req, res) => {
  // req.user.userId is available because authenticate() ran
  const user = await User.findById(req.user.userId);
  res.json(user);
});

// Only admins
router.get('/admin/users', authenticate, authorize('admin'), async (req, res) => {
  const users = await User.find();
  res.json(users);
});

// Multiple roles allowed
router.get('/reports', authenticate, authorize('admin', 'manager'), async (req, res) => {
  res.json({ report: '...' });
});

// Chaining middleware cleanly
router.delete(
  '/admin/user/:id',
  authenticate,           // 1. Is this a valid JWT?
  authorize('admin'),     // 2. Are they an admin?
  async (req, res) => {   // 3. Do the actual work
    await User.deleteById(req.params.id);
    res.json({ message: 'User deleted' });
  }
);
```

### Full Auth Routes

```javascript
// routes/auth.js
const express = require('express');
const jwt = require('jsonwebtoken');
const router = express.Router();

// POST /auth/login
router.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });

    if (!user || !(await user.comparePassword(password))) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    const accessToken = jwt.sign(
      { userId: user.id, email: user.email, role: user.role },
      process.env.ACCESS_TOKEN_SECRET,
      { expiresIn: '15m', issuer: 'my-app' }
    );

    const refreshToken = jwt.sign(
      { userId: user.id },
      process.env.REFRESH_TOKEN_SECRET,
      { expiresIn: '7d', issuer: 'my-app' }
    );

    await RefreshToken.create({ token: refreshToken, userId: user.id });

    res.json({
      accessToken,
      refreshToken,
      expiresIn: 900 // 15 minutes in seconds — helpful for clients
    });

  } catch (err) {
    res.status(500).json({ error: 'Internal server error' });
  }
});

// POST /auth/refresh
router.post('/refresh', async (req, res) => {
  const { refreshToken } = req.body;
  if (!refreshToken) return res.status(400).json({ error: 'Refresh token required' });

  try {
    const decoded = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET, {
      algorithms: ['HS256'],
      issuer: 'my-app'
    });

    const stored = await RefreshToken.findOne({ token: refreshToken });
    if (!stored) return res.status(401).json({ error: 'Token revoked' });

    const user = await User.findById(decoded.userId);
    if (!user) return res.status(401).json({ error: 'User not found' });

    const newAccessToken = jwt.sign(
      { userId: user.id, email: user.email, role: user.role },
      process.env.ACCESS_TOKEN_SECRET,
      { expiresIn: '15m', issuer: 'my-app' }
    );

    res.json({ accessToken: newAccessToken, expiresIn: 900 });

  } catch (err) {
    res.status(401).json({ error: 'Invalid or expired refresh token' });
  }
});

// POST /auth/logout
router.post('/logout', async (req, res) => {
  const { refreshToken } = req.body;
  if (refreshToken) {
    await RefreshToken.deleteOne({ token: refreshToken });
  }
  res.json({ message: 'Logged out' });
});

module.exports = router;
```

---

## 10. Where to Store Tokens

This is one of the most debated topics in frontend security.

### Option 1: localStorage / sessionStorage

```javascript
// Storing
localStorage.setItem('accessToken', token);

// Reading
const token = localStorage.getItem('accessToken');

// Sending with fetch
fetch('/api/profile', {
  headers: { 'Authorization': `Bearer ${token}` }
});
```

| ✅ Pros | ❌ Cons |
|---------|---------|
| Simple to implement | Vulnerable to **XSS attacks** |
| Accessible from any JS | If an attacker runs JS on your page, they steal the token |
| Works with any request type | |

### Option 2: HttpOnly Cookies (Recommended for web apps)

```javascript
// Server sets the cookie (client-side JS can't access HttpOnly cookies)
res.cookie('accessToken', token, {
  httpOnly: true,     // JS can't read this cookie — prevents XSS theft
  secure: true,       // Only sent over HTTPS
  sameSite: 'strict', // Prevents CSRF attacks
  maxAge: 15 * 60 * 1000 // 15 minutes
});
```

```javascript
// Client just makes requests — browser automatically sends the cookie
fetch('/api/profile', { credentials: 'include' });
// No need to manually attach the token
```

| ✅ Pros | ❌ Cons |
|---------|---------|
| XSS-safe (JS can't read HttpOnly cookies) | Needs CORS configured correctly |
| CSRF-safe with SameSite | Doesn't work as easily with mobile apps/APIs |
| Clean client-side code | |

### Option 3: In-Memory (Most Secure for SPAs)

```javascript
// Store in a module variable — never touches storage
let accessToken = null;

export const setToken = (token) => { accessToken = token; };
export const getToken = () => accessToken;
export const clearToken = () => { accessToken = null; };

// Usage
fetch('/api/profile', {
  headers: { 'Authorization': `Bearer ${getToken()}` }
});
```

| ✅ Pros | ❌ Cons |
|---------|---------|
| XSS can't steal what's not in storage | Token lost on page refresh |
| Most secure option | Need refresh token in HttpOnly cookie to restore session |

### Recommended Pattern for SPAs

- **Access token**: In-memory (JavaScript variable)
- **Refresh token**: HttpOnly cookie
- On page load: call `/auth/refresh` using the HttpOnly cookie → get a new access token in memory

---

## 11. Security Best Practices

### 1. Use Strong Secrets

```javascript
// ❌ Terrible
const SECRET = 'secret';
const SECRET = 'myapp';
const SECRET = '123456';

// ✅ Good — generate with: node -e "console.log(require('crypto').randomBytes(64).toString('hex'))"
const SECRET = process.env.JWT_SECRET;
// e.g. "a3f8b2c1d4e5f6789012345678901234abcdef1234567890abcdef1234567890ab"
```

### 2. Always Specify Algorithms

The `alg: none` attack exploits libraries that accept tokens with no algorithm.

```javascript
// ❌ Vulnerable — accepts any algorithm including "none"
jwt.verify(token, secret);

// ✅ Safe — only accept HS256
jwt.verify(token, secret, { algorithms: ['HS256'] });
```

### 3. Keep Access Tokens Short-Lived

```javascript
// ❌ Too long — too much damage if stolen
{ expiresIn: '7d' }
{ expiresIn: '30d' }

// ✅ Good balance
{ expiresIn: '15m' }  // Access token
{ expiresIn: '7d' }   // Refresh token (stored in DB, can be revoked)
```

### 4. Use Different Secrets for Access and Refresh Tokens

```javascript
// ❌ Same secret for both
jwt.sign(payload, process.env.JWT_SECRET); // access
jwt.sign(payload, process.env.JWT_SECRET); // refresh

// ✅ Different secrets — compromise of one doesn't compromise the other
jwt.sign(payload, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '15m' });
jwt.sign(payload, process.env.REFRESH_TOKEN_SECRET, { expiresIn: '7d' });
```

### 5. Rotate Secrets Without Downtime

When rotating secrets, support both old and new during the transition:

```javascript
function verifyWithRotation(token) {
  const secrets = [
    process.env.JWT_SECRET_CURRENT,
    process.env.JWT_SECRET_OLD  // Keep old secret for active tokens
  ];

  for (const secret of secrets) {
    try {
      return jwt.verify(token, secret, { algorithms: ['HS256'] });
    } catch (err) {
      continue; // Try next secret
    }
  }

  throw new Error('Invalid token');
}
```

### 6. Never Log Token Values

```javascript
// ❌ Token ends up in log files — attackers can harvest from logs
console.log('User token:', req.headers.authorization);

// ✅ Log the decoded identity instead
const decoded = jwt.verify(token, SECRET);
console.log('Request from userId:', decoded.userId);
```

### 7. Validate What's Inside the Token

```javascript
// ❌ Trust the token blindly
req.user = decoded;
const user = await User.findById(req.user.userId); // What if user was deleted?

// ✅ Use token for initial auth, fetch fresh data for critical operations
const decoded = jwt.verify(token, SECRET);
const user = await User.findById(decoded.userId);
if (!user || user.isBanned) {
  return res.status(401).json({ error: 'Access denied' });
}
```

---

## 12. Common Errors & What They Mean

These are the exact error objects thrown by `jsonwebtoken`:

### `TokenExpiredError`

```javascript
{
  name: 'TokenExpiredError',
  message: 'jwt expired',
  expiredAt: 2024-05-01T10:00:00.000Z
}
```

**Cause:** The current time is past the `exp` claim.
**Fix:** Issue a new access token using the refresh token.

```javascript
if (err.name === 'TokenExpiredError') {
  return res.status(401).json({ code: 'TOKEN_EXPIRED', error: 'Please refresh your token' });
}
```

### `JsonWebTokenError`

```javascript
{ name: 'JsonWebTokenError', message: 'invalid signature' }
{ name: 'JsonWebTokenError', message: 'jwt malformed' }
{ name: 'JsonWebTokenError', message: 'jwt must be provided' }
{ name: 'JsonWebTokenError', message: 'invalid algorithm' }
```

**Cause:** Token was tampered with, wrong secret was used, or token is not valid JWT format.
**Fix:** The client must log in again. Do not attempt to recover.

### `NotBeforeError`

```javascript
{
  name: 'NotBeforeError',
  message: 'jwt not active',
  date: 2024-05-01T10:00:00.000Z
}
```

**Cause:** The current time is before the `nbf` claim.
**Fix:** Wait until the token becomes active, or remove `nbf` if it's not needed.

### Handling All Errors Cleanly

```javascript
function authenticate(req, res, next) {
  const token = req.headers.authorization?.split(' ')[1];
  if (!token) return res.status(401).json({ error: 'No token' });

  try {
    req.user = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, {
      algorithms: ['HS256']
    });
    next();
  } catch (err) {
    const errorMap = {
      TokenExpiredError: { status: 401, code: 'TOKEN_EXPIRED',  message: 'Token expired' },
      JsonWebTokenError: { status: 401, code: 'INVALID_TOKEN',  message: 'Invalid token' },
      NotBeforeError:    { status: 401, code: 'TOKEN_NOT_ACTIVE', message: 'Token not active yet' },
    };

    const response = errorMap[err.name] ?? { status: 500, code: 'AUTH_ERROR', message: 'Auth error' };
    res.status(response.status).json({ code: response.code, error: response.message });
  }
}
```

---

## 13. Quick Reference Cheat Sheet

```javascript
const jwt = require('jsonwebtoken');

// ─── SIGN ────────────────────────────────────────────────
const token = jwt.sign(
  { userId: 42, role: 'admin' },     // payload
  process.env.JWT_SECRET,            // secret
  { expiresIn: '15m', algorithm: 'HS256' } // options
);

// ─── VERIFY ──────────────────────────────────────────────
try {
  const decoded = jwt.verify(token, process.env.JWT_SECRET, {
    algorithms: ['HS256']
  });
  // decoded = { userId: 42, role: 'admin', iat: 1234, exp: 5678 }
} catch (err) {
  // err.name: 'TokenExpiredError' | 'JsonWebTokenError' | 'NotBeforeError'
}

// ─── DECODE (no verification) ────────────────────────────
const payload = jwt.decode(token);
const full = jwt.decode(token, { complete: true }); // includes header

// ─── COMMON OPTIONS ──────────────────────────────────────
// expiresIn:  '15m' | '2h' | '7d' | 3600 (seconds as number)
// algorithm:  'HS256' | 'HS512' | 'RS256' | 'ES256'
// issuer:     sets/checks 'iss' claim
// audience:   sets/checks 'aud' claim
// subject:    sets/checks 'sub' claim

// ─── ERROR NAMES ─────────────────────────────────────────
// 'TokenExpiredError' → token past exp date → ask client to refresh
// 'JsonWebTokenError' → bad signature/malformed → force re-login
// 'NotBeforeError'    → token used before nbf date → timing issue
```

---

## Summary

| Concept | Key Takeaway |
|---------|-------------|
| JWT Structure | Header.Payload.Signature — only signature ensures security |
| Payload | Base64 only — visible to anyone. Never store passwords or PII |
| `jwt.sign()` | Creates a token. Always set `expiresIn` |
| `jwt.verify()` | Validates signature + expiry. Always specify `algorithms` |
| `jwt.decode()` | No verification — for client-side reading only |
| Access Token | Short-lived (15m), stateless, sent with every request |
| Refresh Token | Long-lived (7d+), stored in DB, used only to get new access token |
| Storage | HttpOnly cookies for web, in-memory + cookie combo for SPAs |
| Secrets | Long random strings from env vars, different per token type |
| Algorithms | HS256 for simple apps, RS256 for distributed/microservice systems |


[[Some Practice for JWT Auth]]