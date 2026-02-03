The **style attribute** in HTML is used to apply inline CSS (Cascading Style Sheets) directly to an individual HTML element. This allows you to control the appearance of that specific element without affecting others on the page.

## How the `style` Attribute Works

- The `style` attribute can be added to any HTML element.
- It contains one or more CSS property-value pairs, separated by semicolons.
- Inline styles set with the `style` attribute override styles from external or internal stylesheets, unless those styles use the `!important` rule.

**Syntax:**
```xml
<tagname style="property: value; property2: value2;">Content</tagname>
```

**Example:**
```xml
<p style="color: red; font-size: 20px;">This is a styled paragraph.</p>
```
This will display the paragraph text in red and with a font size of 20 pixels.

## Common CSS Properties Used with `style`
- `color`: Sets the text color.
- `background-color`: Sets the background color.
- `font-size`: Changes the size of the text.
- `font-family`: Changes the font type.
- `border`: Adds a border around the element.
- `margin`: Sets the space outside the element.
- `padding`: Sets the space inside the element, between the border and content.
- `text-align`: Aligns the text (e.g., left, right, center).

**Example with Multiple Properties:**
```xml
<div style="background-color: yellow; border: 1px solid black; padding: 10px;">   Highlighted content </div>
```

## Key Points
- The `style` attribute is part of the global attributes and can be used on any HTML element.
- It is best for quick, one-off styles or testing. For large projects or consistent styling, use internal (`<style>`) or external CSS files.
- Overuse of inline styles can make code harder to maintain.

## Summary Table

| Attribute | Purpose                               | Example Usage                               |
| --------- | ------------------------------------- | ------------------------------------------- |
| `style`   | Inline styling of individual elements | `<h1 style="color:blue;">Blue Heading</h1>` |

The `style` attribute is a powerful tool for directly styling HTML elements, but for maintainability and scalability, external CSS is generally preferred for larger projects.
