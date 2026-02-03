## What is `flex` in `display`?

The `display: flex` property in CSS enables the Flexbox layout model for a container element. When you set an element’s `display` property to `flex`, it becomes a _flex container_, and its direct children become _flex items_ that can be easily aligned, distributed, and ordered along a single axis (row or column).

**Key features of `display: flex`:**

- **One-dimensional layouts:** Flexbox arranges items in a row or column, unlike CSS Grid, which handles two-dimensional layouts.
- **Flexible sizing:** Flex items can grow, shrink, or remain fixed, depending on available space and the `flex` property settings.
- **Responsive design:** Flexbox makes it easier to build layouts that adapt to different screen sizes without complex floats or positioning.

**Example:**

```css
.flex-container {   
	display: flex; 
}
```

```xml
<div class="flex-container">   
	<div>Item 1</div>  
	<div>Item 2</div>  
	<div>Item 3</div> 
</div>`
```
With `display: flex`, the child `divs` are arranged in a row by default, and you can further control their alignment and spacing using other flex properties.