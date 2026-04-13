**Expected to know:** [[Data Structures]] [[Arrays]] [[Objects]]
**Topics Covered:** [[#Arrays]], [[#Object Literals]]
**Tags:** [[Data Structures]] [[Arrays]]

![[Syntax of JS#Objects and Arrays]]

# Arrays
## Overview of Arrays in JavaScript
Arrays in JavaScript are special objects designed to store ordered collections of values. They are dynamic, zero-indexed, and can contain elements of any data type—including numbers, strings, objects, functions, and even other arrays (nested arrays).

## Creating Arrays
There are several ways to create arrays in JavaScript:
- **Array Literal:** The most common and straightforward method.
```js
let fruits = ["Apple", "Banana", "Cherry"]; 
let empty = []; 
let mixed = [1, "two", { three: 3 }, [4]];
```
- **Array Constructor:** Less common, but possible.
```js
let numbers = new Array(10, 20, 30); 
let emptyArray = new Array(); // creates an empty array 
let fixedSize = new Array(5); // creates an array with length 5, all elements undefined
```
- **Spread Operator:** Useful for copying or combining arrays.
```js
let copy = [...fruits]; 
let combined = [...fruits, ...numbers];
```
- **Array.from():** Creates an array from an iterable or array-like object.
```js
let arrFromString = Array.from("hello"); // ["h", "e", "l", "l", "o"]
```

We can also create `const` array, but then the once declared elements cannot be modified!

## Array Properties
- **length:** Returns the number of elements in the array.
```js
// same as that in strings
fruits.length; // 3
```
- **isArray():** Checks if a variable is an array.
```js
Array.isArray(fruits); // true
```

## Accessing and Modifying Elements
- Access elements by index (zero-based):
```js
fruits[0]; // "Apple"
```
- Modify elements by assigning a new value:
```js
fruits[1] = "Orange"; // ["Apple", "Orange", "Cherry"]
```
- Access nested (multidimensional) arrays:
```js
let arr = [[1, 2], [3, 4]]; 
arr[1][0]; // 3
```

## Common Array Methods

|Method|Description|Example Usage|
|---|---|---|
|push()|Adds an element to the end|fruits.push("Lemon")|
|pop()|Removes the last element|fruits.pop()|
|shift()|Removes the first element|fruits.shift()|
|unshift()|Adds an element to the beginning|fruits.unshift("Strawberry")|
|concat()|Merges arrays|fruits.concat(["Mango", "Peach"])|
|slice()|Returns a shallow copy of a portion|fruits.slice(1, 3)|
|splice()|Adds/removes elements at a specific index|fruits.splice(1, 1, "Kiwi")|
|indexOf()|Finds the index of an element|fruits.indexOf("Apple")|
|includes()|Checks if an array contains an element|fruits.includes("Banana")|
|forEach()|Executes a function for each element|fruits.forEach(fruit => ...)|
|map()|Creates a new array by transforming elements|fruits.map(fruit => fruit.length)|
|filter()|Creates a new array with elements that pass a test|fruits.filter(fruit => fruit.length > 5)|
|reduce()|Reduces array to a single value|fruits.reduce((a, b) => a + b)|
|sort()|Sorts the array|fruits.sort()|
|reverse()|Reverses the array|fruits.reverse()|

## Key Characteristics
- **Dynamic:** Arrays grow and shrink as needed; no need to declare a fixed size.
	- i.e., for an array like 
	```JS
	const arr = new Array(5)
	```
	- we can do something like `arr[6] = 45;` and it won't complain 
- **Untyped:** Elements can be of any type, and types can be mixed within the same array.
- **Zero-based Indexing:** The first element is at index 0.
- **Can Hold Complex Structures:** Arrays can contain objects, functions, and other arrays (multidimensional arrays).

## Example
```js
const shoppingList = ["bread", "milk", "cheese"]; 

console.log(shoppingList[0]); // "bread" 
shoppingList.push("eggs"); // ["bread", "milk", "cheese", "eggs"] 

shoppingList[1] = "almond milk"; // ["bread", "almond milk", "cheese", "eggs"] 
shoppingList.length; // 4`
```

---
# Object Literals
***(Note: this is a part of [[Object Oriented Programming]], so if you feel confused, first check that out!!!, and then check out [[Object Oriented Programming in JS (Including TypeScript)]])***
**Object literals** (also called _object initializers_) are a concise way to define and create objects in JavaScript using a comma-separated list of key-value pairs wrapped in curly braces `{}`.
([But is the object here the same as that we saw in object oriented programming ???](Objects%20in%20JS%20vs%20that%20in%20OOPs.md))
## Syntax
```js
const person = {   
	firstName: "John",  
	lastName: "Doe",  
	age: 50,  
	eyeColor: "blue" , 
	MethodName(/*Parameters*/){/*What to do*/}
	};
```
Each property consists of a key (also called a property name) and a value, separated by a colon. Properties are separated by commas.
Object literals can be declared with var or let as well.

## Features
- **Direct Creation:** You can define all properties and methods at once, without needing to create an empty object and add properties later.
- **Flexible Keys:** Keys can be identifiers, strings, or numbers. For computed property names, use square brackets.
- **Values:** Values can be any valid JavaScript expression, including primitives, objects, arrays, or functions (methods).
- **Shorthand Notation:** If the variable name and property name are the same, you can use shorthand:
```js
const a = 1; 
const obj = { a }; // same as { a: a }
```

## Accessing Properties

- **Dot Notation:** `person.firstName`
- **Bracket Notation:** `person["age"]`
- **Nested Objects:** You can nest objects as values:
```js
const car = {   model: "Honda",  engine: { cylinders: 4, size: 2.2 } };
```

## Use Cases
- **Encapsulating Data:** Object literals are ideal for grouping related data and behavior (methods) together.
- **Minimizing Globals:** They help reduce the use of global variables by encapsulating data.
- **Quick Object Creation:** Useful when you know all properties and methods at the time of creation.

## Example

```js
const rectangle = {   
	length: 10,  
	width: 5,  
	area() 
	{    
		return this.length * this.width;  
	} 
}; 

console.log(rectangle.area()); // 50
```

## Stuff we can do with object literals

### Object Destructuring in JavaScript
**Object destructuring** is a concise syntax in JavaScript that allows you to extract properties from objects and assign them to variables in a single statement.

#### Basic Syntax
```js
const person = { firstName: "John", lastName: "Doe", age: 50 }; 

const { firstName, lastName } = person; 

console.log(firstName); // "John" 
console.log(lastName);  // "Doe"`
```
- The property names inside the curly braces must match the object’s property names.
- The order of properties does not matter.

#### Assigning to New Variable Names
You can assign an object property to a variable with a different name using a colon:
```js
const user = { id: 42, isVerified: true }; 
const { id: userId, isVerified: status } = user; console.log(userId); // 42 console.log(status); // true`
```
Here, `id` is assigned to `userId`, and `isVerified` to `status`.

#### Default Values
If a property does not exist in the object, you can set a default value:
```js
const { name = "Anonymous", age = 25 } = person; 
console.log(name); // "Anonymous" 
console.log(age);  // 25
```
You can also combine renaming and default values:
```js
const { a: aa = 10, b: bb = 5 } = { a: 3 }; 

console.log(aa); // 3 
console.log(bb); // 5
```
#### Nested Destructuring
You can extract properties from nested objects:
```js
const person = { name: "Alice", address: { city: "Paris", zip: "75000" } }; 

const { address: { city } } = person; 
console.log(city); // "Paris"
```
#### Rest Syntax
You can collect the remaining properties into a new object:
```js
const { a, ...rest } = { a: 1, b: 2, c: 3 }; 
console.log(a);    // 1 
console.log(rest); // { b: 2, c: 3 }
```
#### Function Parameters
Destructuring is often used in function parameters for cleaner code:
```js
function greet({ name, age }) {   
	console.log(`Hello, ${name}. You are ${age} years old.`); 
} 

greet({ name: "Bob", age: 40 });
```

---
### Adding new attributes
We can add new attributes in JS Object Literals via:
```JS
const person = { firstName: "John", lastName: "Doe", age: 50 }; 

person.job = "Software designer";
```

---
### Making JSON 
To create JSON in JavaScript, you typically start by defining a JavaScript object or array, and then convert it to a JSON string using the built-in `JSON.stringify()` method.
We use this to send information to server and all.

---
#### Steps to Make [[JSON]] via JavaScript

##### 1. Create a JavaScript Object or Array
```js
const obj = {   name: "John",  age: 30,  city: "New York" };`
```
OR
```JS
const todos = [
	{
		id: 1,
		task: 'Take out Trash',
		isCompleted: true
	},
	{
		id: 2,
		task: 'Do laundry',
		isCompleted: false
	},
	{
		id: 3,
		task: 'Feed pet',
		isCompleted: true
	}
]
```
##### 2. Convert the Object to a JSON String

Use [[JSON.stringify()]] to convert the JavaScript object into a JSON-formatted string:
```js
const jsonString = JSON.stringify(obj); 

console.log(jsonString); // Output: {"name":"John","age":30,"city":"New York"}`
```
This string can now be sent to a server or saved as a `.json` file.

##### 3. Create JSON from an Array
```js
const arr = ["John", "Peter", "Sally", "Jane"]; 
const jsonArr = JSON.stringify(arr); 
console.log(jsonArr); // Output: ["John","Peter","Sally","Jane"]`
```
Arrays can also be converted to JSON strings in the same way.

---
