**Expected to know:** [[Operators]], [[Variables in C-Sharp]]
**Topics Covered:**
**Tags:** [[Operators]]

C# builds upon C-style operators while introducing enhancements for type safety, object-oriented programming, and modern language features. Here's a focused comparison for developers familiar with C:

## Core Operator Categories (Shared with C)

1. **Arithmetic Operators**  
    `+ - * / % ++ --` (Same behavior, but with overflow checking in checked contexts)
```csharp
int x = 10 / 3;  // Result: 3 (integer division) 
double y = 10.0 / 3;  // Result: 3.333...`
```
    
2. **Comparison Operators**  
    `== != > < >= <=` (Value comparison for primitives, reference comparison for objects by default)
    
3. **Logical Operators**  
    `&& || !` (Short-circuiting behavior preserved)
    
4. **Bitwise Operators**  
    `& | ^ ~ << >>` (Works with integral types)
    
5. **Assignment Operators**  
    `= += -= *= /= %= &= |= ^= <<= >>=`

## Key C# Enhancements

### 1. **Type Testing Operators**

#### `is` (Type check):
```csharp
if (obj is string s) 
{ 
	/* use s */ 
}
```
- But what is `s`?
	- The expression `(obj is string s)` in C# checks **only if `obj` is of type `string`**. If this check succeeds, it then declares a new variable `s` of type `string` and assigns the value of `obj` to `s` within the scope of the `if` block. 
	- It does **not** check whether `obj` is equal to some existing variable `s`—rather, it introduces `s` as a new variable for use within the block
- We can also do:
```csharp
if(obj is string)
{
	
}
```

#### `as` (Safe cast):
```csharp
string s = obj as string;  // Returns null if cast fails
````
- Checks if the obj is string, and if not, then it returns `null`.

## 2. **Null Operators**
- Null-coalescing (`??`):
    ```    csharp
    string name = username ?? "Guest";
    ```
- Null-conditional (`?.` and `?[]`):
    ```    csharp
    int? length = customer?.Orders?[0]?.Total;
    ```
(More on this later)

## 3. **Overloadable Operators**
C# allows custom implementation for operators in classes/structs:
```csharp
public static Vector operator + (Vector a, Vector b)      
=> new Vector(a.X + b.X, a.Y + b.Y); // Usage Vector result = v1 + v2;
```

## 4. **Type Information Operators**
- `typeof()`: Gets System.Type object
- `nameof()`: Returns identifier name (compile-time safe)
    ```csharp
    Console.WriteLine(nameof(Customer.Name));  // Outputs "Name"
    ```

## 5. **LINQ/Lambda Operators**

- Lambda operator (`=>`):
    
    csharp
    
    `Func<int, int> square = x => x * x;`
    
- Query expressions:
    
    csharp
    
    `var results = from item in collection               where item.Price > 100              select item.Name;`
    

## Best Practices

1. Use `nameof()` instead of string literals for refactoring safety
    
2. Prefer `is`/`as` over explicit casts for type safety
    
3. Overload operators judiciously (only when meaning is intuitive)
    
4. Use null-conditional operators to avoid null-reference exceptions
    

---
# Data type precedence:
Operator precedence in C# and C differs for some common operators, despite sharing similarities in basic arithmetic and logical operations. Here's a breakdown:

---
## **Key Similarities**
1. **Arithmetic Operators**  
    `* / %` > `+ -` in both languages.  
    Example: `3 + 4 * 5` evaluates to `23` in both C# and C.
    
2. **Logical Operators**  
    `&&` (logical AND) > `||` (logical OR) in both.
    
3. **Bitwise Shift**  
    `<<` and `>>` have the same precedence relative to each other.
    

---
## **Key Differences**
## 1. **Bitwise vs. Equality/Relational Operators**
- **C**: Bitwise operators (`&`, `|`, `^`) have **lower** precedence than equality (`==`, `!=`) and relational (`<`, `>`, etc.) operators.  
    Example: `a & b == c` → Evaluates as `a & (b == c)`.
    
- **C#**: Bitwise operators have **higher** precedence than equality/relational operators.  
    Example: `a & b == c` → Evaluates as `(a & b) == c`.

## 2. **Assignment Operators**
- **C**: Assignment (`=`) has very low precedence (above only the comma operator).
- **C#**: Assignment operators (`=`, `+=`, etc.) also have low precedence but differ in interaction with other operators like null-coalescing (`??`).

## 3. **Conditional Operator (`?:`)**
- Both languages treat `?:` as right-associative, but precedence relative to other operators varies slightly.

---

## **Example Comparison**

|Expression|C Evaluation|C# Evaluation|
|---|---|---|
|`a & b == c`|`a & (b == c)`|`(a & b) == c`|
|`x = y = z`|`x = (y = z)` (right-associative in both)|Same as C|
|`a && b||c`|

***Check out [this](https://www.programtopia.net/csharp/docs/operator-precedence-and-associativity) for the complete data type precedence for C#.***
