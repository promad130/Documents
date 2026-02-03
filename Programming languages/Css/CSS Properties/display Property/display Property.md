The **display** property in CSS defines how an HTML element is visually rendered and how it behaves in the document flow. It determines whether elements appear as block-level, inline, flex, grid, or other specialized types, and directly affects layout, positioning, and interaction with other elements.

## Common Values of the Display Property

| Value          | Description                                                                                                                                                                                                                                                      |
| -------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| block          | The element starts on a new line and takes up the full width available (e.g., `<div>`, `<p>`)[1](https://www.w3schools.com/cssref/pr_class_display.php)[6](https://www.scaler.com/topics/css/display-property-in-css/).                                          |
| inline         | The element does not start on a new line and only takes up as much width as needed (e.g., `<span>`, `<a>`)[1](https://www.w3schools.com/cssref/pr_class_display.php)[6](https://www.scaler.com/topics/css/display-property-in-css/).                             |
| inline-block   | The element flows inline with other elements but allows setting width and height[1](https://www.w3schools.com/cssref/pr_class_display.php)[6](https://www.scaler.com/topics/css/display-property-in-css/).                                                       |
| none           | The element is completely removed from the layout and not visible[1](https://www.w3schools.com/cssref/pr_class_display.php)[6](https://www.scaler.com/topics/css/display-property-in-css/).                                                                      |
| [[flex value]] | The element becomes a flex container, enabling flexible layouts for its children[2](https://developer.mozilla.org/en-US/docs/Web/CSS/display)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/). |
| grid           | The element becomes a grid container for precise placement of its children[2](https://developer.mozilla.org/en-US/docs/Web/CSS/display)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).       |
| inline-flex    | Like `flex`, but the container itself behaves as an inline element[1](https://www.w3schools.com/cssref/pr_class_display.php)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).                  |
| inline-grid    | Like `grid`, but the container itself behaves as an inline element[1](https://www.w3schools.com/cssref/pr_class_display.php)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).                  |
| table          | The element behaves like a `<table>`[1](https://www.w3schools.com/cssref/pr_class_display.php)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).                                                |
| list-item      | The element behaves like a list item (`<li>`) with bullet points or numbering[1](https://www.w3schools.com/cssref/pr_class_display.php)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).       |
| contents       | The element disappears, making its children act as if they are direct children of its parent[1](https://www.w3schools.com/cssref/pr_class_display.php)[6](https://www.scaler.com/topics/css/display-property-in-css/).                                           |

## Syntax
```css
selector {   
	display: value; 
}
```
## Key Points

- The default value for most elements is either `block` or `inline`, depending on the tag[6](https://www.scaler.com/topics/css/display-property-in-css/).
    
- The `display` property is not inherited by default[1](https://www.w3schools.com/cssref/pr_class_display.php).
    
- Changing the display value can dramatically alter the layout and behavior of elements on a web page[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).
    
- Advanced values like `flex` and `grid` are used for responsive and complex layouts[4](https://www.lambdatest.com/blog/css-display-property/)[5](https://mimo.org/glossary/css/display-property)[6](https://www.scaler.com/topics/css/display-property-in-css/).
    

## Example
```css
div {   
	display: flex; 
} 

span {   
	display: inline-block;  
	width: 100px;  
	height: 30px; 
}
```
In summary, the CSS `display` property is fundamental for controlling how elements are rendered and structured in a web page, offering a wide range of layout possibilities.
