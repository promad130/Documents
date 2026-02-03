![CSS Cursor - Scaler Topics](https://d2u1z1lopyfwlx.cloudfront.net/thumbnails/bd610bd4-aa7e-5bb6-94ac-4381a0532664/ab94b7cc-f44d-55a4-b67d-409083717eba.jpg)
The **cursor** property in CSS controls the appearance of the mouse pointer when it hovers over a specific element on a web page. 

This property allows developers to provide visual feedback to users, indicating possible interactions such as clicking, dragging, or that an element is disabled.
## How the CSS Cursor Property Works
- The cursor property can be set to a variety of predefined values (keywords) such as `pointer`, `default`, `text`, `not-allowed`, `wait`, and many more. Each keyword changes the cursor to a different icon, helping users understand what actions are possible.
- You can also specify a custom image for the cursor using the `url()` function, with a fallback keyword in case the image fails to load.

## Syntax
```css
selector {   
	cursor: value; 
}
```
- You can use this property on any HTML element, and it can be combined with pseudo-classes like `:hover` for dynamic effects[7](https://mimo.org/glossary/css/cursor).

## Example Usage
```css
button {   
	cursor: pointer; /* Shows a hand icon to indicate clickability */ 
} 

input:disabled {   
	cursor: not-allowed; /* Shows a "no entry" icon for disabled fields */ 
} 

.custom-cursor {   
	cursor: url('my-cursor.png'), auto; /* Uses a custom image */ 
}
```
## Common Cursor Values

|Value|Description|
|---|---|
|auto|Default; browser decides based on context|
|default|Standard arrow pointer|
|pointer|Hand icon (used for clickable items)|
|text|I-beam (used for text selection)|
|not-allowed|Indicates action is not permitted|
|wait|Indicates a process is ongoing, user must wait|
|grab/grabbing|Indicates draggable elements|
|crosshair|Cross-shaped cursor|
|zoom-in/out|Indicates zoom functionality|

## Why Use the Cursor Property?
- **Improves user experience** by signaling interactivity or restrictions.
- **Clarifies functionality** (e.g., shows when something is clickable or disabled).
- **Enhances design** with custom cursors for branding or creative effects.

## Custom Cursors
- Custom images can be used as cursors via `cursor: url('image.png'), fallback;`.
- Recommended image size is usually `32x32` pixels for compatibility.
- Supported formats include PNG, SVG, and CUR (for desktop browsers).

