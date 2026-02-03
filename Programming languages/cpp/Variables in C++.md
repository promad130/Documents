**Expected to know:** [[Introduction to Programming]]
**Topics Covered:**
**Tags:** [[Variables and their scope]]

# Introduction: Variables in C++

A **variable** in C++ is a named storage location in memory that can hold a value which may change during program execution. Variables allow you to store, update, and retrieve data as your program runs. Each variable has a **type** (such as `int`, `float`, `char`, etc.) that determines what kind of data it can hold.

**Example:**
```cpp
int age = 14; // 'age' is a variable of type int, initialized to 14 
age = 17;     // The value of 'age' can be changed`
```
---
## Rules for Naming Variables in C++
- Must start with a letter or underscore (`_`), not a digit.
- Can contain letters, digits, and underscores.
- Are case-sensitive (`Age` and `age` are different).
- Cannot contain spaces or special characters.
- Cannot be a reserved keyword (e.g., `int`, `for`).
- Should be unique within their scope.
---
# What's new and different?
Now lets look at what is different here than what is mentioned in [[Variables and their scope]]?
## 1. Constants
- **C++ introduces the `const` keyword** to declare variables whose values cannot be changed after initialization.
    - Example: `const int LIGHT_SPEED = 299792458;`
    - Any attempt to modify a `const` variable will result in a compilation error.
- **C also supports `const`**, but in C++ it is used more extensively and is preferred over macros for defining constants.
- **C++ also supports `constexpr`**, which is used for compile-time constants.
- **Both C and C++ support macros (`#define`)** for constants, but C++ best practice prefers `const` or `constexpr` due to type safety and scoping.

## 2. Variable Declaration and Definition
- In both C and C++, you must declare a variable before using it, specifying its type and name.
- **Initialization at declaration** is common and encouraged in C++:
    - Example: `int score = 100;`
- Both languages allow declaration and initialization in one step.

## 3. Types of Variables
- **C++ and C share the same basic types of variables:** local, global, and static.
- **Scope and lifetime rules are the same** in both languages.

## 4. Variable Naming
- **Rules are almost identical** in C and C++ (letters, digits, underscores, no starting with a digit, no keywords, case-sensitive).
- **C++ conventionally prefers camelCase or snake_case** for variable names, and UPPER_CASE for constants.

## 5. Constants and Literals
- **Literals** (fixed values like `1`, `2.5`, `'c'`, `"hello"`) work the same way in both C and C++.
- **String literals** are enclosed in double quotes, character literals in single quotes.

## 6. Best Practices
- **C++ prefers `const` or `constexpr`** for constants, while C code often uses `#define` macros (but `const` is available in both).
- **C++ encourages type safety and scoping** with constants, which macros do not provide.

---
## Summary Table: Key Differences

| Feature               | C (from your file)                | C++ (from sources)                        |
| --------------------- | --------------------------------- | ----------------------------------------- |
| Constant declaration  | `#define`, `const`                | Prefer `const`, also supports `constexpr` |
| Modifying constants   | Not allowed for `const`/`#define` | Not allowed for `const`/`constexpr`       |
| Variable naming rules | Letters, digits, underscores      | Same as C                                 |
| Initialization        | At declaration or later           | At declaration (preferred), or later      |
| Type safety for const | Weaker for macros                 | Strong for `const`/`constexpr`            |
| Scope for const       | Follows normal variable scoping   | Follows normal variable scoping           |
| Macros for constants  | Common                            | Supported, but discouraged                |
