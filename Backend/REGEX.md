Regular expressions—commonly known as **regex**—are essentially supercharged search queries for text. They allow you to look for complex patterns within strings, match specific formats (like emails or phone numbers), and replace or extract text with incredible precision.

Think of it as a find-and-replace tool on steroids.

## 1. Anatomy of a Regex

A standard regular expression consists of the **pattern** (what you are looking for) wrapped inside **delimiters**, followed by optional **flags** (modifiers that change how the search behaves).

Plaintext

```
  / [a-z]+ / g
  │   └───┘  └── Flag (Global search)
  │     └────── Pattern (One or more lowercase letters)
  └──────────── Delimiters (Marks the start and end)
```

## 2. The Core Building Blocks

To write regex, you combine literal characters (like searching for the exact letters `cat`) with **meta-characters**, which have special rules.

### A. Character Classes (The "What")

Character classes tell the engine what type of character to match.

- **`.` (Dot):** Matches _any single character_ except a newline.
    
- **`\d`:** Matches any **digit** (0-9).
    
- **`\w`:** Matches any **word character** (alphanumeric characters and underscores).
    
- **`\s`:** Matches any **whitespace** (spaces, tabs, newlines).
    
- **`[abc]`:** A custom set. Matches any character inside the brackets (`a`, `b`, or `c`).
    
- **`[a-z]`:** A range. Matches any lowercase letter from `a` to `z`.
    

> 💡 **Pro-Tip:** Capitalizing a shorthand character class inverts it. For example, `\D` matches anything that is _not_ a digit, and `\S` matches anything that is _not_ whitespace.

### B. Quantifiers (The "How Many")

Quantifiers specify how many times the preceding character or group should appear.

- **`*`:** 0 or more times.
    
- **`+`:** 1 or more times.
    
- **`?`:** 0 or 1 time (makes the character optional).
    
- **`{n}`:** Exactly _n_ times.
    
- **`{n,m}`:** Between _n_ and _m_ times.
    

### C. Anchors (The "Where")

Anchors don't match actual characters; instead, they match _positions_ in the text.

- **`^`:** Matches the **start** of a string or line.
    
- **`$`:** Matches the **end** of a string or line.
    

## 3. Putting It Together: Real-World Examples

Let's see how these blocks stack up to solve actual pattern-matching problems.

### Example 1: Matching a Component of a Date (e.g., Year)

If you want to find a 4-digit year like `2026`:

- Pattern: **`\d{4}`**
    
- _Breakdown:_ `\d` looks for a digit, and `{4}` demands exactly four of them in a row.
    

### Example 2: Matching a Simple Indian Phone Number Format

Imagine matching numbers written like `+91-9876543210` or `9876543210`.

- Pattern: **`^(\+91-)?\d{10}$`**
    
- _Breakdown:_ * `^` ensures we start matching from the very beginning of the string.
    
    - `(\+91-)?` looks for the literal string `+91-`. The `?` makes this entire group optional (note that `+` is escaped with a backslash because it's a special character).
        
    - `\d{10}` matches exactly 10 digits.
        
    - `$` ensures the string ends right after those 10 digits, preventing accidental matches on longer sequences.
        

## 4. Common Flags

Flags are appended to the end of the regex string (after the closing delimiter) to alter the engine's behavior globally:

|**Flag**|**Name**|**What it does**|
|---|---|---|
|**`g`**|Global|Finds all matches in the text rather than stopping after the first one.|
|**`i`**|Case-insensitive|Ignores upper/lowercase differences (e.g., `/cat/i` matches "cat", "Cat", or "CAT").|
|**`m`**|Multiline|Makes `^` and `$` apply to the start and end of individual lines, rather than the whole string.|

## 5. The "Escaping" Gotcha

Because characters like `.` `*` `+` `?` `^` `$` `(` `)` `[` `]` `{` `}` `|` `\` have special powers in regex, you can't just type them if you want to search for them literally.

To search for a literal period or dollar sign, you must **escape** it using a backslash (`\`).

- Matching a regular sentence period: **`\.`**
    
- Matching a dollar amount: **`\$`**
    

What kind of text-processing or validation problem are you looking to solve with regex right now?