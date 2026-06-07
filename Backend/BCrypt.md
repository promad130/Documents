**Bcrypt** is the industry standard for securely hashing and storing user passwords. Unlike standard cryptographic hash functions (like MD5 or SHA-256) which were designed to be as *fast* as possible, bcrypt was designed from the ground up to be **deliberately slow**.

This intentional slowness is exactly what makes it highly resistant to modern brute-force and hardware-accelerated attacks.

Here is a complete breakdown of how bcrypt works under the hood, focusing on its core pillars: **Hashing, Salting, and Cost Factors**.

---

## 1. The Core Problem: Why Regular Hashing Fails

To understand bcrypt, we have to understand why standard hashing isn’t enough for passwords.

A traditional hash function takes an input (a password) and turns it into a fixed-length string of characters. It is a **one-way function**—you can easily go from `Password123` $\rightarrow$ `Hash`, but you can't reverse the `Hash` back into `Password123`.

```text
Traditional Approach:
Password ──> [ SHA-256 Hash Function ] ──> Fast, Deterministic Hash

```

However, algorithms like SHA-256 or MD5 are incredibly fast. A modern computer can compute billions of SHA-256 hashes per second. If an attacker steals a database of SHA-256 hashes, they can use massive precomputed tables (**Rainbow Tables**) or brute-force software to guess millions of common passwords a second until they find a match.

Furthermore, if two users have the exact same password, their SHA-256 hashes will be identical, letting an attacker compromise multiple accounts at once.

---

## 2. The Solution: Salting

Bcrypt completely neutralizes Rainbow Tables and identical-password matching by enforcing **Salting**.

A **salt** is a sequence of random characters generated uniquely for every single password. Before the password is hashed, the salt is appended to it.

```text
User 1: "password123" + "random_salt_A9x" ──> Unique Hash
User 2: "password123" + "random_salt_z2P" ──> Completely Different Hash

```

### Why Salting Matters:

* **Unique Hashes:** Even if two users choose `Password123`, their salts will be different, resulting in two completely unique hashes in the database.
* **Destroys Rainbow Tables:** Attackers cannot use precomputed tables of common passwords because the table would have to guess every password combined with every possible random salt, making the storage requirements impossibly massive.

With bcrypt, **you do not need to store the salt in a separate database column**. Bcrypt automatically bakes the salt directly into the final output hash string.

---

## 3. Future-Proofing: The Cost Factor (Work Factor)

Salting stops precomputed tables, but it doesn't stop a powerful computer from trying to brute-force a single account by guessing passwords one by one very quickly. This is where bcrypt’s secret weapon comes in: the **Cost Factor**.

The cost factor (an integer, typically between `10` and `14`) determines how many times the hashing algorithm will loop internally. Specifically, the number of rounds is calculated as:

$$\text{Rounds} = 2^{\text{Cost Factor}}$$

* If the cost factor is `10`, the algorithm loops $2^{10} = 1,024$ times.
* If you increase it to `11`, it loops $2^{11} = 2,048$ times (doubling the work).
* If you set it to `12`, it loops $2^{12} = 4,096$ times.

### The Magic of Adaptive Hashing

Because it uses an exponential scale, bcrypt is **adaptive**. As computers get faster and cheaper over the next decade, you don't need to change your entire database setup or switch algorithms. You simply bump the cost factor from `12` to `13` or `14`.

This keeps the calculation time at an optimal sweet spot: fast enough for a single user logging in (e.g., 100 to 300 milliseconds), but cripplingly slow for an hacker trying to guess millions of combinations.

---

## 4. Anatomy of a Bcrypt Hash String

When bcrypt outputs a hash to be saved in your database, it outputs a single, structured string that looks something like this:

```text
$2b$12$R9hTxC9xcgJc8BVQvVHXNeUuL0vK1YlFwO3K02D8R892kLmMwE8iS
├──┘├──┘└────────────────────────┘└────────────────────────┘
Version Cost          Salt                    Hashed Password
(2b)   (12)       (22 characters)             (31 characters)

```

By looking at the output string, the bcrypt library can extract everything it needs to verify a password later:

1. **`$2b$` (Version):** Identifies the specific variant of the bcrypt algorithm being used.
2. **`$12$` (Cost Factor):** Tells the system that this password was run through $2^{12}$ (4,096) iterations.
3. **`R9hTxC9xcgJc8BVQvVHXNe` (Salt):** The 22-character random salt generated during registration.
4. **`uuL0vK1YlFwO3K02D8R892kLmMwE8iS` (Hash):** The actual resulting payload after passing the salted password through the loops.

---

## 5. How Authentication Works (Verification)

Because bcrypt hashes are one-way and include random salts, you might wonder: *How do we check if a logging-in user typed the right password?*

You never try to "decrypt" the hash. Instead, you let bcrypt handle the comparison:

1. The user types their password into the login form.
2. Your application pulls the stored bcrypt string from the database for that user.
3. The bcrypt library parses that string to extract the **Version**, **Cost Factor**, and the exact **Salt** used originally.
4. Bcrypt takes the newly entered password, mixes it with that extracted salt, and runs it through the exact same number of cost loops.
5. If the newly computed string matches the stored string exactly, the password is correct!

---

## Summary Checklist for Implementations

* **Don't hardcode salts:** Let your language's bcrypt library generate the salt automatically for every single password change or registration.
* **Pick the right cost factor:** Aim for a cost factor that takes around `200ms-300ms` on your production server. As of 2026, a cost factor of `11` or `12` is generally the baseline standard for standard web applications.
* **Max limit gotcha:** Bcrypt has a built-in character limit of **72 bytes**. Any characters in a password past the 72nd byte are ignored by the native algorithm. If you expect users to submit massive passphrases, it is common practice to pre-hash the password with SHA-256 before feeding it into bcrypt.

[[Some practice for BCrypt]]