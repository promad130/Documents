The **id attribute** in HTML is used to assign a unique identifier to an element within a web page. This identifier must be unique across the entire document—no two elements can share the same id value.

## Key Features and Usage
- **Uniqueness:** Each id value must be unique within an HTML document. This ensures that when you reference an id, it points to only one specific element.
- **Syntax:**
```xml
<element id="uniqueId">Content</element>
```
Example:
```xml
<h1 id="main-title">Welcome</h1>
```
	
- **Global Attribute:** The id attribute can be used on any HTML element.

## Common Uses
- **Styling with CSS:**  
    You can use the id as a selector in CSS to apply styles to a specific element:
    
```css
#main-title {   
	color: blue; 
}
```
 What it does?
	- This targets only the element with id="main-title".
    
- **JavaScript Manipulation:**  
    JavaScript can easily select and manipulate elements by their id using methods like `getElementById()`:
    
    javascript
    
    `document.getElementById("main-title").innerText = "Hello!";`
    
    This changes the text of the element with id="main-title"[1](https://www.w3schools.com/tags/att_id.asp)[2](https://www.w3schools.com/tags/att_global_id.asp)[4](https://www.codecademy.com/resources/docs/html/attributes/id).
    
- **Anchor Links (Bookmarks):**  
    The id attribute can be used to create anchor links, allowing users to jump to specific sections of a page:
```xml
<h2 id="about">About Us</h2> 
<a href="#about">Go to About Us section</a>
```
Clicking the link scrolls the page to the element with the matching id.

## Rules and Best Practices
- **Valid id Values:**
    - Must start with a letter (A–Z or a–z).
    - Can contain letters, digits (0–9), hyphens (-), underscores (_), and periods (.).
    - Cannot contain spaces or start with a number.	
- **Case Sensitivity:**  
    id values are case-sensitive ("Header" and "header" are different).
- **One id per Element:**  
    An element can have only one id, and each id must be unique within the page.

## Summary Table

|Feature|Description|
|---|---|
|Purpose|Uniquely identify an element|
|Syntax|`<element id="uniqueId">...</element>`|
|Uniqueness|Only one element per id per page|
|Used for|CSS styling, JavaScript, anchor links/bookmarks|
|Valid Characters|Letters, digits, hyphens, underscores, periods|
