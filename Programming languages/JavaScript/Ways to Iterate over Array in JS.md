**Topics Covered:**
**Tags:** [[Arrays]], [[Looping Statements]]

## Iterating Over Arrays in JavaScript

JavaScript offers several ways to loop through arrays, each with its own use cases and advantages. Here are the most common methods:

---
## 1. **Classic for Loop**

The `for` loop is a traditional way to iterate over array elements using an index.
```js
const numbers = [10, 20, 30, 40, 50]; 

for (let i = 0; i < numbers.length; i++) {   console.log(numbers[i]); }
```
- **How it works:**
    - `let i = 0` initializes the counter.
    - `i < numbers.length` is the loop condition.    
    - `i++` increments the counter after each iteration.
    
- **Use case:**
    - When you need access to the index and want full control over the loop execution.

---
## 2. **forEach() Method**

The `forEach()` method executes a provided function once for each array element.
```js
const fruits = ["apple", "orange", "cherry"]; 
fruits.forEach(function(item, index, array) {   console.log(item, index); });
```
- **Arguments:**
    - `item`: current element
    - `index`: index of the current element    
    - `array`: the array being traversed
    
- **Notes:**
    - Does not return a value (always returns `undefined`).    
    - Cannot be broken out of early (no `break` or `continue`).
    
- **Use case:**
    - When you want to perform an action for each item without creating a new array.

---
## 3. **map() Method**

The `map()` method creates a new array by applying a function to each element.
```js
const numbers = [10, 20, 30, 40, 50]; 
const multiplied = numbers.map(function(item) {   return item * 10; }); 
console.log(multiplied); // [100, 200, 300, 400, 500]
```
- **Returns:**
    - A new array with the results of calling the provided function on every element.
    
- **Use case:**
    - When you want to transform each element and collect the results in a new array.

---
## 4. **filter() Method**

The `filter()` method creates a new array with all elements that pass a test implemented by the provided function.
```js
const ages = [32, 33, 16, 40]; 
const adults = ages.filter(function(age) {   return age >= 18; }); 
console.log(adults); // [32, 33, 40]
```
- **Returns:**
    - A new array with only the elements that pass the test.
    - This is different from map() in terms of array size.
- **Use case:**
    - When you want to select a subset of elements based on a condition

---
# Using them together
We can also use them together and make stuff more interesting:
```js
const todos = [
	{
		id: 1,
		text: "Workout",
		isCompleted: true
	},
	{
		id: 2,
		text: "Do laundry",
		isCompleted: false
	},
	{
		id: 3,
		text: "Cook Breakfast",
		isCompleted: true
	}
];

const todoCompleted = todos.filter(function(todo){
	return todo.isCompleted === true;
}).map(function(todo){
	return todo.text;
});
```

So our new array `todoCompleted` consists of `text` of all the completed tasks.