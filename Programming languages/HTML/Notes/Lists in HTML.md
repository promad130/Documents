# What is the use?
HTML provides several ways to group related items using lists, making content more organized and readable. There are three main types of lists in HTML: unordered lists, ordered lists, and description lists.
## When to use?
- **Unordered List:** When order does not matter (e.g., features, ingredients).
- **Ordered List:** When order is important (e.g., steps, rankings).
- **Description List:** When pairing terms with definitions or explanations.
## Basic Syntax
### 1. **Unordered List (`<ul>`)**
- **Purpose:** Displays items with no particular order.
- **Default Marker:** Bullets (- )
- **Syntax:**
```xml
<ul>   
	<li>Item 1</li>  
	<li>Item 2</li>  
	<li>Item 3</li> 
</ul>
```
- **Use Case:** Grocery lists, features, or any set of items where order is not important.
- **Customizing Bullets:** The `type` attribute can change the bullet style (`disc`, `circle`, `square`, or `none`).

---
### 2. **Ordered List (`<ol>`)**
- **Purpose:** Displays items in a specific sequence.
- **Default Marker:** Numbers (1, 2, 3, ...)
- **Syntax:**
```xml
<ol>   
	<li>Step 1</li>  
	<li>Step 2</li>  
	<li>Step 3</li> 
</ol>
```
- **Use Case:** Instructions, rankings, or any list where the order matters.
- **Customizing Numbers:** The `type` attribute can change numbering style (e.g., `1`, `A`, `a`, `I`, `i`), and the `start` and `reversed` attributes allow further control).

---
### 3. **Description List (`<dl>`)**
- **Purpose:** Pairs terms with descriptions.
- **Syntax:**
```xml
<dl>   
	<dt>HTML</dt>  
		<dd>HyperText Markup Language</dd>  
	<dt>CSS</dt>  
		<dd>Cascading Style Sheets</dd> 
</dl>
```
- **Use Case:** Glossaries, FAQs, or any situation where you need to describe terms

---
# Attributes used?
