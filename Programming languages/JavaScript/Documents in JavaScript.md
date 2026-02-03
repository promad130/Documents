**Expected to know:**
**Topics Co vered:** [[#What Are DOMs in JavaScript?]], [[#Using DOM]], [[#Selectors in DOM]], [[#Events in DOM]]
**Tags:**

## What Are DOMs in JavaScript?
**DOM** stands for **Document Object Model**. In the context of JavaScript, the DOM is a programming interface that represents the structure, content, and style of a web document (such as an HTML page) as a tree of objects in memory.

## How the DOM Works
- When a web page loads, the browser converts the HTML (and XML or SVG) into a tree-like structure called the DOM tree.
- Each HTML element (such as `<html>`, `<body>`, `<p>`, etc.) becomes a node (object) in this tree. Text inside elements is also represented as nodes.
- The root of this tree is the `document` object, which represents the entire web page.

## Why the DOM Matters in JavaScript
- JavaScript can use the DOM API to access, modify, add, or remove elements and content on a web page dynamically.
- For example, you can change the text of a paragraph, update styles, add new elements, or respond to user actions (like clicks and keypresses) using the DOM.

### Example
Suppose you have this HTML:
```xml
<p>Hello, world!</p>
```
You can change the text using JavaScript and the DOM:
```javascript
document.querySelector("p").textContent = "Hello, universe!";
```
Here, `document.querySelector("p")` selects the first `<p>` element, and `.textContent` changes its text.

## Key Concepts
- **Nodes and Elements:** Every part of the page (elements, text, attributes) is a node in the DOM tree.
- **APIs and Methods:** JavaScript uses methods like `getElementById`, `querySelector`, `appendChild`, and properties like `.innerHTML` or `.style` to interact with the DOM.
- **Events:** The DOM allows you to attach event handlers (like `onclick`) to nodes so your code can react to user interactions.

## DOM vs. JavaScript
- The DOM is not part of the JavaScript language itself; it is a Web API provided by browsers.
- JavaScript is the most common language used to interact with the DOM, but the DOM can be accessed from other languages as well.

---
# Using DOM
First, we need to `link` a JS script to our HTML.
There are three main ways to link JavaScript (JS) to an HTML document:

## 1. Inline JavaScript
- JavaScript code is written directly within an HTML element’s attribute, typically for event handling.
- Example:
```xml
<button onclick="alert('Hello!')">Click Me</button>
```
- Best for very simple scripts or quick demonstrations, but not recommended for larger projects due to maintainability concerns.

## 2. Internal JavaScript
- JavaScript code is placed inside a `<script>` tag within the HTML file, usually in the `<head>` or at the end of the `<body>`.
- Example:
```xml
<script>   
	function greet() 
	{    
		alert('Hello from internal JS!');  
	} 
</script> 

<button onclick="greet()">Click Me</button>`
```
- Suitable for scripts specific to a single page.

## 3. External JavaScript
- JavaScript code is written in a separate `.js` file, which is then linked to the HTML using the `<script src="..."></script>` tag.
- Example:
```xml
<!-- In your HTML file --> 
<script src="script.js"></script>`
```
```javascript
// In script.js 
function greet() 
{   
	alert('Hello from external JS!'); 
}
```
- This method is best for reusability and organization, especially in larger projects. The external file should not include `<script>` tags inside it—just the JS code itself

---
## Selectors in DOM
So before we do anything to an element, we need to access that element and store it. This is done via selectors in JS.
There are mainly two types of selectors in JS:
- Single element selectors,
- Multiple element selectors
### Single Element Selectors
Single element selectors are used to select only one element from the DOM—the first match found. Even if there are multiple matches in the document, only the first one is selected.

**Common Single Element Selectors:**
- `document.getElementById("id")`:  
    Selects the element with the specified unique ID. IDs should be unique in a document, so this always returns at most one element.
```js
const header = document.getElementById("main-header");
```
- `document.querySelector("selector")`:  
    Selects the first element that matches the given CSS selector (class, id, tag, etc.).
```js
const firstButton = document.querySelector(".btn");`\
```
**Key Point:**  
Even if multiple elements match the selector, only the first one is returned.

---
### Multiple Element Selectors
Multiple element selectors are used to select all elements that match a given criterion. They return a collection (like an array) of elements.

**Common Multiple Element Selectors:**
- `document.getElementsByClassName("className")`:  
    Returns a live HTMLCollection of all elements with the specified class.
```js
const items = document.getElementsByClassName("menu-item");
```
- `document.getElementsByTagName("tagName")`:  
    Returns a live HTMLCollection of all elements with the specified tag name.
```js
const paragraphs = document.getElementsByTagName("p");
```
- `document.querySelectorAll("selector")`:  
    It selects elements from the HTML document (the DOM) using CSS selector syntax. The **argument** you pass to `querySelectorAll()` is a string that follows the same rules as CSS selectors (like `.class`, `#id`, `div > p`, etc.).
    The **method** then searches the HTML document for all elements that match that selector pattern and returns them as a static NodeList (a collection of elements)
```js
const allButtons = document.querySelectorAll("p");
```
So this will select all the paragraph elements in out HTML file.
**Key Point:**  
All matching elements are selected and returned as a collection, allowing you to loop through them and apply changes to each.

---
### In Practice
- Use **single element selectors** when you need to work with one specific element (like a unique header or form).
- Use **multiple element selectors** when you want to manipulate or read from a group of similar elements (like all buttons or all list items).

---
# Events in DOM
## What Are DOM Events?
DOM events are _signals_ that something has happened in a web page—such as a user clicking a button, typing in a field, or the page finishing loading. 
These events can be triggered by user actions or by the browser itself, and they allow JavaScript to respond dynamically, making web pages interactive and engaging.

---
## Common Types of DOM Events
Here are some of the most frequently used DOM events:
- **Mouse Events:** `click`, `dblclick`, `mousedown`, `mouseup`, `mousemove`, `mouseover`, `mouseout`, `contextmenu`
- **Keyboard Events:** `keydown`, `keyup`, `keypress`
- **Form Events:** `submit`, `change`, `focus`, `blur`, `input`
- **Window Events:** `load`, `resize`, `scroll`, `unload`
- **Media Events:** `play`, `pause`, `ended`, `volumechange`
- **Animation/Transition Events:** `animationstart`, `animationend`, `transitionend`

---
## How Events Work in the DOM
When an event occurs (like a click), the browser creates an _event object_ containing details about the event (e.g., which element was clicked, mouse coordinates, which key was pressed). 
JavaScript can then use this information to decide how to react.

---
## Handling Events: Three Main Methods
There are three primary ways to make your JavaScript respond to DOM events:

| Method                     | Example                                         | Pros & Cons                                                                                                                                                                                                                               |
| -------------------------- | ----------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Inline HTML Attributes** | `<button onclick="alert('Hi!')">Click</button>` | Simple, but mixes HTML and JS, and only one handler per event per element[3](https://www.theodinproject.com/lessons/foundations-dom-manipulation-and-events)[8](https://udn.realityripple.com/docs/Web/API/Document_Object_Model/Events). |
| **DOM Element Properties** | `element.onclick = function() { ... }`          | Keeps JS separate, but still only one handler per event per element                                                                                                                                                                       |
| **addEventListener**       | `element.addEventListener('click', handler)`    | Preferred method—can add multiple handlers, more flexible and scalable                                                                                                                                                                    |

**Example using `addEventListener`:**
```javascript
const button = document.getElementById('myButton');
button.addEventListener('click', function(event) 
	{   
		alert('Button clicked!');  
		console.log('Event target:', event.target); 
	});
```
This method allows you to attach as many handlers as you want to the same event and element, and keeps your code modular and maintainable.

### But how are `element.onclick` and `addEventListener` different?
#### How `onclick` Works
When you use:
```javascript
element.onclick = function() {   
	// do something 
};
```
You are assigning a function to the `onclick` _property_ of the element. This property can only hold one function at a time. If you assign another function later:
```javascript
element.onclick = function() {   
	// do something else 
};
```
The previous function is completely replaced. Only the most recent function will run when the element is clicked. You cannot stack or accumulate multiple functions this way—each new assignment overwrites the last one.

---
### How `addEventListener` Works
When you use:
```javascript
element.addEventListener('click', function() {   
	// do something 
}); 
element.addEventListener('click', function() {   
	// do something else 
});
```
You are _adding_ functions to an internal list of handlers for the `'click'` event. Every time the event occurs, **all** the attached handlers run, in the order they were added. This means you can have as many separate pieces of code respond to the same event, without them interfering with each other.

---
### Visual Comparison

| Method             | Can Add Multiple Handlers? | What Happens When You Add Another?              |
| ------------------ | -------------------------- | ----------------------------------------------- |
| `element.onclick`  | No                         | Replaces the previous handler                   |
| `addEventListener` | Yes                        | Adds another handler; both (all) will be called |

---
## The Event Object
When an event handler runs, it is passed an _event object_ as its first argument to the function we attack with the event listener. This object contains useful properties and methods, such as:
- `event.target` — the element that triggered the event
- `event.type` — the type of event (e.g., "click")
- `event.preventDefault()` — prevents the default browser action (useful for forms)
- `event.stopPropagation()` — stops the event from bubbling up to parent elements

---
## Why Are DOM Events Important?
Events are the foundation of interactive web pages. They allow you to:
- Respond to user actions (clicks, typing, form submissions)
- Validate forms before submission
- Create dynamic UIs (show/hide elements, animations)
- Handle asynchronous operations (like loading data)

---
Well that is it, now select elements, and do stuff with them!!! 
Use [This documentation](https://developer.mozilla.org/en-US/docs/Web/API/Document) to know about the stuff you are using while making things.
