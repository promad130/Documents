Typography in CSS encompasses the styling and arrangement of text on web pages, affecting both aesthetics and readability. CSS provides a rich set of properties to control font selection, size, weight, spacing, color, alignment, and responsiveness, enabling designers to create visually appealing and accessible web content.

Now lets do some stuff!

---
# The code:
We will be using this:
![[CSS#CSS Typography]]

---
# text-decoration:

Directly affects the text itself. 
Adds underlines, overlines, or line-throughs.

## Underline
```css
/*
This is how we add comment in CSS

Typography is the way that text is arranged 
and presented

*/

body{
    font-size: 2rem;
    padding: 25%;
}

input,button{
    font: inherit
    
}

main p{
    text-decoration: underline;
}
```
![[Pasted image 20250520114928.png]]

## Overline
```css
/*
This is how we add comment in CSS

Typography is the way that text is arranged 
and presented

*/

body{
    font-size: 2rem;
    padding: 25%;
}

input,button{
    font: inherit
    
}

main p{
    text-decoration: overline;
}
```
![[Pasted image 20250520115039.png]]

## line-through
```css
/*
This is how we add comment in CSS

Typography is the way that text is arranged 
and presented

*/

body{
    font-size: 2rem;
    padding: 25%;
}

input,button{
    font: inherit
    
}

main p{
    text-decoration: line-through;
}
```
![[Pasted image 20250520115124.png]]

---
# text-transform:
Changes case (`uppercase`, `lowercase`, `capitalize`).

```css
/*
This is how we add comment in CSS

Typography is the way that text is arranged 
and presented

*/

body{
    font-size: 2rem;
    padding: 25%;
}

input,button{
    font: inherit
    
}

main p{
    text-decoration: line-through;
    text-transform: uppercase;
}
```
![[Pasted image 20250520115345.png]]

---
# text-align:
Aligns text horizontally (`left`, `right`, `center`, `justify`)

```CSS
/*
This is how we add comment in CSS

Typography is the way that text is arranged 
and presented

*/

body{
    font-size: 2rem;
    padding: 25%;
}

input,button{
    font: inherit
    
}

main p{
    text-decoration: line-through;
    text-transform: uppercase;
    text-align: center; /*Works for the content box*/
}
```
![[Pasted image 20250520115559.png]]

```CSS
/*
This is how we add comment in CSS

Typography is the way that text is arranged 
and presented

*/

body{
    font-size: 2rem;
    padding: 25%;
}

input,button{
    font: inherit
    
}

main p{
    text-decoration: line-through;
    text-transform: uppercase;
    text-align: justify; /*Works for the content box*/
}
```
![[Pasted image 20250520115655.png]]

---
# text-indent:
Indents the first line of text.

---
# **Other Text Properties**

- `color`: Sets the text color using names, HEX, RGB, or RGBA values
- `text-align`: Aligns text horizontally (`left`, `right`, `center`, `justify`)
- `text-transform`: Changes case (`uppercase`, `lowercase`, `capitalize`)[7](https://www.w3schools.com/css/css_text.asp).
- `text-indent`: Indents the first line of text[4](https://www.webfx.com/blog/web-design/css-typography-01/).
- `letter-spacing`: Controls space between letters[4](https://www.webfx.com/blog/web-design/css-typography-01/).
- `line-height`: Sets the vertical spacing between lines.
- `word-spacing`: Adjusts space between words.
- `text-decoration`: Adds underlines, overlines, or line-throughs.

---
# Font Properties:

- `font-family`: Specifies the typeface(s) to use. Always list fallback fonts for better compatibility (e.g., `font-family: "Times New Roman", Times, serif;`)
- `font-size`: Sets the size of the text. Can use units like `px`, `em`, `rem`, `%`, or responsive units
- `font-weight`: Controls the thickness of the text (e.g., `normal`, `bold`, `100`–`900`)
- `font-style`: Sets the style (e.g., `normal`, `italic`, `oblique`)
- `font-variant`: Enables small caps or other variants.
- `font-stretch`: Adjusts the width of characters.
- `@font-face`: Allows custom fonts to be loaded and used on the web

## Resource
Use Google fonts to get font types and names and all, usually this would be stolen from some other site.

We can import stuff from google fonts or any other font by first, copying the link provided by the fonts, and pasting it right before the `<link>` element of CSS sheet in `<head>`, otherwise it will be overridden.

![[Pasted image 20250520145257.png]]
And then to use these fonts, we need to set the font style in our CSS as given in the Google fonts or any other source you are using.
Like:
![[Pasted image 20250520145543.png]]

We could also import a particular style from Google Font, we can do that by copying the import URL and pasting it in our CSS file.
![[Pasted image 20250520145645.png]]

