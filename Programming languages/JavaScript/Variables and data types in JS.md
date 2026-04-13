**Expected to know:** [[Variables and their scope]]
**Topics Covered:** [[#Declaring Variables in JavaScript]]([[#1. `var`]],[[#2. `let`]],[[#3. `const`]]), [[#Syntax and Naming Rules]], [[#Data Types]], [[#`typeof variableName` in JS]]
**Tags:** [[Variables and their scope]]

If you know C, you'll find some similarities and key differences in how variables work in JavaScript.

## Declaring Variables in JavaScript
JavaScript offers three main keywords for declaring variables:

- `var`
- `let`
- `const`

## 1. `var`
- Used in older JavaScript code.
- Function-scoped. 	  ```
	- Due to this, it is no longer used.
- Can be redeclared and updated.
- Example:
```javascript
var x = 10; 
var y; 
y = 20;
```
## Problems with var

**Function scope instead of block scope** - `var` variables are accessible throughout the entire function, even outside the block where they're declared:​

```javascript
if (true) 
{   
	var x = 10; 
} 

console.log(x); // Outputs 10 (leaks out of the block!)`
```

This causes unexpected behavior where variables "leak" outside their intended scope.​

**Allows re-declaration** - You can accidentally declare the same variable twice without errors:​

```javascript
var name = "John"; 
var name = "Jane"; // No error, just overwrites
```

**Hoisting confusion** - `var` declarations get "hoisted" to the top of their scope and initialized as `undefined`, leading to bugs:​

```javascript
console.log(name); // Outputs undefined (should be an error!) 
var name = "Alice";
```

**Adds properties to global object** - Using `var` at the top level creates properties on the `window` object, polluting the global namespace
## 2. `let`
- Introduced in ES6 (2015).
- Block-scoped (like variables inside `{}` in C).
- Can be updated but not redeclared in the same scope.
- Preferred for variables whose values may change.
- Example:
```javascript
let count = 5; 
count = 6; //valid
```

## 3. `const`
- Also introduced in ES6.
- Block-scoped.
- **Must be** initialized at declaration and cannot be reassigned, error will be thrown otherwise!!!
- Good for values that should not change (constants).
- Example:
```javascript
const PI = 3.14159;
```

## Syntax and Naming Rules
- Variable names (identifiers) must start with a letter, underscore `_`, or dollar sign `$` (not a digit).
- After the first character, digits are allowed.
- JavaScript is case-sensitive (`myVar` and `myvar` are different).
- CamelCase is common for multi-word names: `userName`, `totalAmount`.

## Assigning Values
- You can declare and assign in one step:
```javascript
let name = "Alice";
```
- Or declare first, then assign:
```javascript
let age; 
age = 30;`
```

## Data Types
JavaScript variables can hold any data type and can change type at runtime:

- Numbers: `let x = 10;`, means number literally, nothing different for `4.5` and `4`
- Strings: `let msg = "hello";` 
	- Can have both, `' '` or `" "`.
	- String Interpolation: 
		- Either use `+` like ("Hello my name is " + `name`+".")
		- Or use (`Hello my name is ${name}`);
			- **Remember to use backticks for interpolation, not double quotes(")**
	- String Functions:
		- We can access string functions in JS using the Dot Operator(`.`)[So are they classes?](JS%20Data%20Types%20are%20primitive%20types)
		- There are some common function which every Programming Language provides like:
			- `string.length`: returns the length of `string`, and `.length` is a property, not a method
			- `string.toUpperCase()`: Makes the string in all upper case
			- `string.substring(a,b)`: Returns the part of string with index from a to b (where b is excluded)
			We can also do: `string.substring(a,b).toUpperCase();`, won't be an issue. Associativity is from left to right
			- `string.split(character)`: splits the string into an array using `character` as the split indicator, i.e., for `let v = 'a b c'`, `v.split(' ')` would give `['a', 'b', 'c']` 
- Booleans: `let isActive = true;`
- undefined:
	In JavaScript, `undefined` is a primitive value automatically assigned to variables that have been declared but not yet given a value. It acts as a placeholder, indicating the absence of an assigned value. This is the default state for any variable that is declared but not initialized.
- null: mean a variable is there but nothing inside it, basically empty!
- Arrays: `let arr = [1][2][3];`
- Objects: `let user = { name: "John" };`

for example:
```JS
const age = 30;
const name = 'WormHole';
const isCool = true;
const  rating = 4.5;
```

## Key Differences from C

| JavaScript                  | C                                  |
| --------------------------- | ---------------------------------- |
| No type declarations needed | Must specify type (int, char, etc) |
| Can change type at runtime  | Type is fixed                      |
| `let`/`const` block-scoped  | Block-scoped variables             |
| `var` function-scoped       | No direct equivalent               |

## Good Practices
- Always declare variables before use.
- Prefer `let` and `const` over `var` in modern code.
- Use `const` for values that should not change.

## Example
```javascript
const price1 = 5; 
const price2 = 6; 

let total = price1 + price2;`
```
Here, `price1` and `price2` are constants, while `total` is a variable that can be changed later.

# `typeof variableName` in JS

This when used returns the type of the variable whose name is passes in as the parameter:
```js
let a = true;
let b = 4.5;
let b2 = 5;
let c = null;
let d = 'string';
let d2 = "This is also a string";
let e= undefined;

console.log(typeof a)
console.log(typeof b)
console.log(typeof b2)
console.log(typeof c)
console.log(typeof d)
console.log(typeof d2)
console.log(typeof e)
```
Output:
```text
boolean
number
number
object
string
string
undefined
```

Now note that the `null` is given as an `object`. This is because initially in JS, values were represented as a type tag, and an object has a tag of `0` and null was represented as the **NULL** pointer(i.e., `0x000...`), hence it was also given `0` as type tag, hence the bogus `typeof` return value.

---
## Types of Type Casting in JavaScript

### 1. Implicit Type Casting (Type Coercion)

- JavaScript automatically converts data types when necessary, often during operations or comparisons.
- **Examples:**
    
    ```js
    let result = "3" + 2;      // "32" (number 2 is converted to string) 
    let result2 = "4" - "2";   // 2   (strings are converted to numbers) 
    let result3 = 1 == "1";    // true (string "1" is converted to number 1)
    ```
    
- The `+` operator with a string triggers string concatenation; other arithmetic operators (`-`, `*`, `/`) trigger numeric conversion.


### 2. Explicit Type Casting (Manual Conversion)

- You can manually convert data types using built-in functions:
    
    - **To Number:** `Number(value)`, `parseInt(value)`, `parseFloat(value)`
    - **To String:** `String(value)`, `value.toString()`
    - **To Boolean:** `Boolean(value)`
- **Examples:**
    ```    js
    Number("5");         // 5 
    String(true);        // "true" 
    Boolean(0);          // false 
    parseInt("42");      // 42 
    parseFloat("3.14");  // 3.14
	```
- Useful when you want to ensure a value is treated as the correct type.

---
## Conversion Rules and Table

| Original Value                                                         | To Number | To String         | To Boolean |
| ---------------------------------------------------------------------- | --------- | ----------------- | ---------- |
| false                                                                  | 0         | "false"           | false      |
| true                                                                   | 1         | "true"            | true       |
| 0                                                                      | 0         | "0"               | false      |
| 1                                                                      | 1         | "1"               | true       |
| "0"                                                                    | 0         | "0"               | true       |
| "1"                                                                    | 1         | "1"               | true       |
| ""                                                                     | 0         | ""                | false      |
| "20"                                                                   | 20        | "20"              | true       |
| "twenty"                                                               | NaN       | "twenty"          | true       |
| null                                                                   | 0         | "null"            | false      |
| undefined                                                              | NaN       | "undefined"       | false      |
| []                                                                     | 0         | ""                | true       |
| [10](https://thetechgenics.com/javascript/type-casting-in-javascript/) | NaN       | "10,20"           | true       |
| {}                                                                     | NaN       | "[object Object]" | true       |

>_Hence as you can see, JavaScript does not use ASCII values, it works on the basis of the logic that a number is a number, irrespective of it's type!!! Everything else is NaN(not a number), that is why the typecasting gives the given result. 