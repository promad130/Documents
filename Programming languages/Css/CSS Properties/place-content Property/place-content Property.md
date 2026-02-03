The `place-content` property is a CSS shorthand used in [[flex value]] (for multi-line flex containers) and [[CSS Grid layouts]]. It combines the effects of `align-content` (vertical/block axis alignment) and `justify-content` (horizontal/inline axis alignment) into a single declaration.

## **How it works:**

- **Syntax:**
    
```css
place-content: <align-content> <justify-content>;
```
- The first value sets `align-content`
- The second value sets `justify-content`
- If only one value is provided, it applies to both

**Example:**

```css
.flex-container {   
	display: flex;  
	flex-wrap: wrap;  
	place-content: end space-between; }`
```
- This aligns the flex lines to the end (bottom) of the container and distributes the items with space between them horizontally.

## **Common values:**

- `start`, `end`, `center`, `stretch`
- [[space-between]], `space-around`, `space-evenly`
- `flex-start`, `flex-end`
- `baseline`

## **When to use:**

- Use `place-content` when you want to control the alignment and distribution of content in multi-line flex or grid containers with a single property.
- For single-line flex containers, `align-items` and `justify-content` are more commonly used.