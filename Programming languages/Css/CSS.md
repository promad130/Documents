When it comes to web development, it is safe to say:
	HTML eazy, css is just like combing that stupid little HTML kid, and java script makes it interactive, connects stuff and makes things happen
CSS is used for styling your site.

Time to look at how it works:

## Changing stuff

we write the keyword of whatever we want to change, and then in `{}` what we want to change.

For example, in a paragraph of this html:
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <p>I'm learning CSS</p>
    <p>hi</p>
</body>
</html>
```
We do something like this:
```css
p{
    font-size: 32px;
    color: coral; 
}
```

![[Pasted image 20250519201045.png]]

And the order in which style sheets are applied matters as:
This gives:
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
    <style>
        p{
            color: aquamarine;
        }
    </style>
</head>
<body>
    <p>I'm learning CSS</p>
    <p>hi</p>
</body>
</html>
```

this:
![[Pasted image 20250519201138.png]]

Hence HTML applies code line by line.

We can also do in-line css, i.e., apply style directly in the element.
```HTML
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
    <style>
        p{
            color: aquamarine;
        }
    </style>
</head>
<body>
    <p style = "color:blue">I'm learning CSS</p>
    <p>hi</p>
</body>
</html>
```
And this would give:
![[Pasted image 20250519201407.png]]

So till now, whatever we worked with has a standard name attached to it.

The `p` in our css is what we call a [selector](Selector%20in%20CSS), something on which we work, i.e,:
 — it selects the element(s) to style. In particular, `p` selects all the paragraphs in the HTML. 
 
 The line inside the curly braces (`{ }`) is called a [**declaration**](Declarations%20in%20CSS) – it sets a value for a specific property. 
	 In this case, the **property** is `color`, which controls the text color of the paragraphs, and the **property value** set is `coral`.

Well CSS doesn't give out error, it ignores whatever is not recognized by it.

---
# Cascade

The CSS work like a waterfall, implements all the line one by one.
So, if we want to override, we can do this:
```css
p{
    font-size: 32px;
    color: coral; 
}

body{
    font-size: 22px; 
}
p{
    font-size: 64px;
    color: aqua;
}
.customStuff{
    color: brown;
    font-weight: bold;
    font-size: 64 px;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    display: block;
}

#tryOut{
    font-size: 100px;
    color: darkgoldenrod;
}
```

And would give out:
![[Pasted image 20250519204810.png]]

Hence, it is specific in what is does, what to override and what to imply.

---
## Inheritance of stuff and whitespaces

In CSS, style is inherited.
So if we have used a `body` selector, and apply font, then all the elements inside body inherit that, as they are part of body, but remember, whatever is used in body works inside body.

On the other hand, universal selector means work on each element individually but simultaneously.
```CSS
body{
    border: 1px solid purple;
}

p{
    font-size: 32px;
    color: coral; 
}

body{
    font-size: 22px; 
}
p{
    font-size: 64px;
    color: aqua;
}
.customStuff{
    color: brown;
    font-weight: bold;
    font-size: 64 px;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    display: block;
}

#tryOut{
    font-size: 100px;
    color: darkgoldenrod;
}
```
![[Pasted image 20250519205621.png]]

but this:
```CSS
* {
    border: 12px solid red;
}

body{
    border: 10px solid purple;
}

p{
    font-size: 32px;
    color: coral; 
}

body{
    font-size: 22px; 
}
p{
    font-size: 64px;
    color: aqua;
}
.customStuff{
    color: brown;
    font-weight: bold;
    font-size: 64 px;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    display: block;
}

#tryOut{
    font-size: 100px;
    color: darkgoldenrod;
}
```
gives:
![[Pasted image 20250519210006.png]]

### ***Note that never do `12 px` or anything like that, it won't work. White spaces matter when it comes to CSS***

---
## Overriding Cascade

We can override any cascade by writing `!important` just after(without any whitespaces) the declaration we want to use and override everything else.

```CSS
* {
    border: 12px solid red;
}

body{
    border: 10px solid purple;
}

p{
    font-size: 32px;
    color: coral!important; 
}

body{
    font-size: 22px; 
}
p{
    font-size: 64px;
    color: aqua;
}
.customStuff{
    color: brown;
    font-weight: bold;
    font-size: 64 px;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    display: block;
}

#tryOut{
    font-size: 100px;
    color: darkgoldenrod;
}
```

Would now give:
![[Pasted image 20250519210538.png]]

---
# **Colors in CSS (!important)

Time to look at some colors!

We are using:
```HTML
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <header>
        <h1>CSS Colors</h1>
    </header>
    <main>
        <article>
            <h2>Article 1</h2>
            <p class="one">Lorem ipsum, dolor sit amet consectetur adipisicing elit. Numquam cumque voluptatum tempore
                officiis
                deserunt
                officia, molestias tenetur tempora. Numquam assumenda eligendi ipsam ut quibusdam ex autem placeat
                deleniti
                hic optio?</p>
        </article>
        <article>
            <h2>Article 2</h2>
            <p class="two">Iste, possimus, delectus blanditiis, in dolores voluptatem culpa officia quae
                eius
                consequatur
                suscipit optio
                cum hic. Architecto ipsum rem accusamus! Quaerat repellendus <span>nihil</span> ratione tenetur voluptas
                veritatis,
                sunt
                nesciunt rem.</p>
        </article>
        <article>
            <h2>Article 3</h2>
            <p class="three">Iste, possimus, delectus blanditiis, in dolores voluptatem culpa officia quae eius
                consequatur
                suscipit optio
                cum hic. Architecto <span>ipsum</span> rem accusamus! Quaerat repellendus nihil ratione tenetur voluptas
                veritatis,
                sunt
                nesciunt rem.</p>
        </article>
    </main>
</body>

</html>
```
```CSS
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
}
```

![[Pasted image 20250519223322.png]]

This as a start.
WE set the background color using:
```CSS
background-color: blue;
```
OR
```CSS
background: blue;
```
And we set the text color using:
```CSS
color: ;
```
This becomes;
```CSS
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}
```
Which looks like:
![[Pasted image 20250519223747.png]]
	You can see we have also used `rgb(r,g,b)` or `rgba(r,g,b,a)`
	We can use all sort of [color representation formats](Color%20Representation%20Formats) in CSS.

## Tools to use:
Use [Coolers](https://coolors.co/) for Color pallets, amazing tool!!!
We also need to check for contrast of the site(coolers offer a contrast checker) and [keep in mind the color theory](The%20Color%20Theory) while choosing the color pallet for the site.

---
# CSS Units and Sizes

Units and Sizes are important as text size can make a lot of difference.
Lets look at some common units and sizes.
We are going to work with what we had previously, and build on that.

There are various numeric value types that you might find yourself using in CSS. The following are all classed as numeric:

| Data type                                                                     | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| ----------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`<integer>`](https://developer.mozilla.org/en-US/docs/Web/CSS/integer)       | An `<integer>` is a whole number such as `1024` or `-55`.                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`<number>`](https://developer.mozilla.org/en-US/docs/Web/CSS/number)         | A `<number>` represents a decimal number — it may or may not have a decimal point with a fractional component. For example, `0.255`, `128`, or `-1.2`.                                                                                                                                                                                                                                                                                                                     |
| [`<dimension>`](https://developer.mozilla.org/en-US/docs/Web/CSS/dimension)   | A `<dimension>` is a `<number>` with a unit attached to it. <br><br>For example, `45deg`, `5s`, or `10px`. `<dimension>` is an umbrella category that includes the [`<length>`](https://developer.mozilla.org/en-US/docs/Web/CSS/length), [`<angle>`](https://developer.mozilla.org/en-US/docs/Web/CSS/angle), [`<time>`](https://developer.mozilla.org/en-US/docs/Web/CSS/time), and [`<resolution>`](https://developer.mozilla.org/en-US/docs/Web/CSS/resolution) types. |
| [`<percentage>`](https://developer.mozilla.org/en-US/docs/Web/CSS/percentage) | A `<percentage>` represents a fraction of some other value. For example, `50%`. Percentage values are always relative to another quantity. For example, an element's length is relative to its parent element's length.                                                                                                                                                                                                                                                    |

The following are all **absolute** length units — they are not relative to anything else, and are generally considered to always be the same size.

|Unit|Name|Equivalent to|
|---|---|---|
|`cm`|Centimeters|1cm = 37.8px = 25.2/64in|
|`mm`|Millimeters|1mm = 1/10th of 1cm|
|`Q`|Quarter-millimeters|1Q = 1/40th of 1cm|
|`in`|Inches|1in = 2.54cm = 96px|
|`pc`|Picas|1pc = 1/6th of 1in|
|`pt`|Points|1pt = 1/72nd of 1in|
|`px`|Pixels|1px = 1/96th of 1in|

Most of these units are more useful when used for print, rather than screen output. For example, we don't typically use `cm` (centimeters) on screen. The only value that you will commonly use is `px` (pixels).

The default size for paragraphs in any browser is `16px`.

Usually we don't want to set the absolute pixel size for fonts as it rips the user off the option of increasing text size willingly.

However, a fixed size should be used for border.

lets have some fun with percentage:
```CSS
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}

h1,h2{
    border: 5px dotted whitesmoke;
    width: 100%;
}
```

As we can see, width is 100%, i.e., it'll take up the space of whole block:
![[Pasted image 20250519225722.png]]

However, we can change it:
```CSS
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}

h1,h2{
    border: 5px dotted burlywood;
    width: 50%;
}
```
![[Pasted image 20250519225828.png]]Now our `h1` is child of `header` element, so if we do:
```css
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}

h1,h2{
    border: 5px dotted burlywood;
    width: 50%;
}
```
Then the header itself is now taking 50% of the block, and as `h1` and `h2` are child of header, their width is relative to that of their parent, so 50% width of `h1` and `h2` would now mean 25% of total page width:
![[Pasted image 20250519230142.png]]

Now with the relativity covered, lets have a look at **Relative Length Units.**
Relative units can be a little more difficult than absolute units in determining which to use, so let’s go through your options in detail.

| Relative Unit | Description                                                                                                                                                                                  |
| ------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| %             | Relative to the parent element’s value for that property                                                                                                                                     |
| em            | Relative to the current font-size of the element                                                                                                                                             |
| rem           | Relative to the font-size of the root (e.g. the `<html>` element). “rem” = “root em”                                                                                                         |
| ch            | Number of characters (1 character is equal to the width of the current font’s 0/zero)                                                                                                        |
| vh            | Relative to the height of the viewport (window or app size). 1vh = 1/100 of the viewport’s height                                                                                            |
| vw            | Relative to the width of viewport. 1vw = 1/100 of the viewport’s width.                                                                                                                      |
| vmin          | Relative to viewport’s smaller dimension (e.g. for portrait orientation, the width is smaller than the height so it’s relative to the width). 1vmin = 1/100 of viewport’s smaller dimension. |
| vmax          | Relative to viewport’s larger dimension (e.g. height for portrait orientation). 1vmax = 1/100 of viewport’s larger dimension.                                                                |
| ex            | Relative to height of the current font’s lowercase “x”.                                                                                                                                      |

It’s not always clear which of these options is best to use for each type of CSS property. For example, `%` is usually more appropriate for layout-related properties like `width` than it would be for `font-size`.

Now when it comes to rem, the root means the default size used either set via html element, or the one given by the browser.

So, for para, 1 rem looks like:
```CSS
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}
header{
    width: 50%;
}
h1,h2{
    border: 5px dotted burlywood;
    width: 50%;
}

p{
    font-size: 1rem;
}
```
![[Pasted image 20250519230757.png]]

Which is usually `16px`(depending upon browser setting, it can change).

`em` is all about the parent element's font size.

Now we can also add padding to header and all, which helps in making it look more clear and clean. For example, without padding:
```CSS
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}
header{
    font-size: 2rem;
    width: 100%;
}
h1,h2{
    border: 5px dotted burlywood;
    width: 50%;
    padding: 1rem;
}

p{
    font-size: 2rem;
}
```
![[Pasted image 20250519232215.png]]

---
# Developer Tools

Shortcut for developer tools are `F12` or `ctrl+shift+I`. 
This can be used to inspect stuff on our web page with great amount of specificity.
We can select individual element and then inspect them in terms of space given to *content*, *padding*, *border*, and whatever is left, i.e., *margins*.

Now every element in our web page is given a box. And that box has stuff like margin, border, etc,. We usually don't want it to set some default margin and border and take control of everything and we usually don't want the box size to include anything else(like padding and border) except the content itself, so we reset everything by adding this at the start:
```CSS
* {
	margin: 0px;
	padding: 0px;
	box-sizing: content-box;
}
```

So what this does is if we have this:
```CSS
* {
    box-sizing: content-box;
}
body {
    font-size: 22px;
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.5;
    background-color: papayawhip;
    color: rgba(7, 41, 7, 0.632);
}
header{
    font-size: 2rem;
    width: 1080px;
    
}
h1,h2{
    border: 5px dotted burlywood;
    padding: 1rem;
}

p{
    font-size: 2rem;
}
```
The width of content box should be 1080px right?
But what we get is:
![[Pasted image 20250520093114.png]]
Why is this?
It is because the header element has `h1` as a child. When we set the global attribute of `box-sizing` then it is applied on both `header` and `h1`, but the width is applied only on `header`. 
As the header itself doesn't have padding and stuff, and content for header is all it's children, i.e, `h1` in our case, all 1080px is given to child, i.e., `h1`, and content of `h1` according to header contains border, padding and content.

If we give width to `h1` itself like this:
```css
h1,h2{
    border: 5px dotted burlywood;
    padding: 1rem;
    width: 1080px;
}
```
And remove the `width = 1080px`, otherwise overflow will occur, i.e., child element being wider than the parent, smt like this:
![[Pasted image 20250520094554.png]]
![[Pasted image 20250520094606.png]]
, so after making necessary changes, we get:
![[Pasted image 20250520094645.png]]


---
# CSS Typography
CSS typography is all about the way text is arranged and presented in a web page.

We will be using this:
```HTML
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    <header>
        <h1>CSS Typography</h1>
    </header>
    <main>
        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Mollitia laudantium exercitationem corporis quia
            dolores esse enim? Necessitatibus quisquam, magni iste beatae earum et fugiat aut delectus porro voluptate,
            praesentium totam?</p>

        <form>
            <label for="name">Name:</label>
            <input id="name" type="text" placeholder="Your Name" />
            <button>Submit</button>
        </form>
    </main>
</body>

</html>
```
```CSS

```
Now we will add some stuff to the CSS.
Also, if the child is something like a button,input, image, etc,., it won't automatically inherit the declaration given in the parent element, so we need to make it inherit all that stuff by:
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
```

And with that, lets have a [look at some text editing in CSS](Typography%20in%20CSS).

---
# Styling Links

Recall in HTML, we had something called **anchor**.
	In web development, an "anchor" refers to a specific location within a webpage or document that can be linked to, allowing users to navigate directly to that section. This is typically created using the HTML `<a>` tag

So now we will style some hyperlinks via `CSS stylesheet`, and we will be using:
`index.html`
```HTML
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CSS Links</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <header>
        <h1>CSS Links</h1>
    </header>
    <main>
        <p>Welcome to my page about <a href="https://www.google.com/search?q=+web+links">cool web links</a>.</p>
        
        <p>Actually.. that isn&apos;t a good search phrase. Let's search for <a href="https://www.google.com/search?q=hypertext+links">hypertext links</a> instead!</p>
        
        <p>I created page one with information about <a href="one.html">guitars</a>.</p>
        
        <p>I created page two with information about <a href="two.html">JavaScript</a>.</p>
    </main>
</body>

</html>
```
`one.html`
```HTML
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page One</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <h1>Page One</h1>
</body>

</html>
```
`two.html`
```HTML
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Two</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <h1>Page Two</h1>
</body>

</html>
```
`style.css`
```CSS
@import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');

body {
    padding: 10%;
    font-size: 2rem;
    font-family: 'Roboto', sans-serif;
}
```

## Notice the small things
We can do variety of stuff with the anchor element. But first lets notice some important details about a site.

When we click on a hyperlink, the color changes to Redish color, when a link is visited, it's color becomes purple.
And when we hover over a link, our cursor also changes shape.

Hence all these effects / styles are default styles offered by all browsers.

So we can change:

```CSS
@import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');

body {
    padding: 10%;
    font-size: 2rem;
    font-family: 'Roboto', sans-serif;
}

a{
    color: burlywood;
}
```
And all those little things about the color of hyperlink upon different activities are gone, as now we have overridden the original style to only one color, i.e., `burlywood`.

Hence, now we need to give color those things specifically.

## Pseudo class of anchor

### Remember in HTML we had:
A **pseudo-class** a keyword added to a CSS selector that allows you to style an element based on its state or position, rather than just its type or class. 
Pseudo-classes are written by appending a colon (`:`) and the pseudo-class name to a selector. 

For example, `a:hover` targets anchor (`<a>`) elements when a user hovers over them with a mouse

Pseudo-classes let you style elements dynamically, such as when a user interacts with them (hovering, clicking, focusing), or based on other states (visited, unvisited links, form validation, etc.)

### Pseudo-classes Offered by the Anchor (`<a>`) Element

The anchor element (`<a>`) has several commonly used pseudo-classes that allow you to style links based on their interaction state. The main pseudo-classes for anchor elements are:

| Pseudo-class | Description                                                    |
| ------------ | -------------------------------------------------------------- |
| `:link`      | Selects unvisited links (links the user has not yet clicked)   |
| `:visited`   | Selects links that the user has already visited                |
| `:hover`     | Selects links when the user hovers the mouse pointer over them |
| `:active`    | Selects links at the moment they are being activated (clicked) |
| `:focus`     | Selects links when they have keyboard focus (e.g., tabbed to)  |

**Example CSS:**

```css
a:link {   
	color: blue; 
} 
a:visited {   
	color: purple; 
} 
a:hover {   
	color: green; 
} 
a:active {   
	color: red; 
} 
a:focus {   
	outline: 2px solid orange; 
}
```
**Order Matters:**  
For correct styling, the recommended order in your CSS is:
- `:link`, 
- `:visited`, 
- `:hover`, 
- `:focus`, 
- `:active`. 

This ensures that more specific states (like `:active`) can override less specific ones due to CSS cascading rules.
Which makes logic as an hyperlink is always active regardless of weather it is being hovered upon or not.

We can also use `&:` to access a pseudo class, like this:
```CSS
ul.features-list{
    margin: 0;
    padding-left: .1em;

    li{
        font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
        font-size: 1.3em;
        margin-bottom: 1em;
        margin-left: 2em;
        position: relative;
        
        /*Giving bullet points that are custom is more of a advanced to intermmediate kind of topic*/
        &:before{
            content: ' ';
            left: -2em;
            position: absolute;
            width: 20px;
            height: 20px;
            background-image: url('../images/bullet.svg');
            background-size: contain;
            margin-right: 0.5em;
        }
    }
}
```

---
# Styling lists
In order to style list, we would be using these as practice:
```HTML
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CSS Lists</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    <header>
        <h1>CSS Lists</h1>
    </header>
    <main>
        <article>
            <h2>Ordered List</h2>
            <ol>
                <li>Step One</li>
                <li>Step Two</li>
                <li>Step Three</li>
            </ol>
        </article>
        <article>
            <h2>Unordered List</h2>
            <ul>
                <li>Step One</li>
                <li>Step Two</li>
                <li>Step Three</li>
            </ul>
        </article>
    </main>
</body>

</html>
```
```CSS
@import url("https://fonts.googleapis.com/css2?family=Roboto&display=swap");
```
![[Pasted image 20250527184847.png]]


## Styling the list-style type:
We can style how the points appear using:
```CSS
ol{
	list-style-type: ;
}
```
(We are working on the ordered list element here)
For example:
```Css
ol{
	list-style-type: disc;
}
```
![[Pasted image 20250527185356.png]]
```CSS
ol{
	list-style-type: lower-greek;
}
```
![[Pasted image 20250527185427.png]]
The default is decimal number for `list-style-type`.

## List style position:
Sometimes, when using text alignment with lists in CSS, the bullet point's position remain a decision of browser:
```css
ul{
    list-style-type: square;
    text-align: center;
}
```
![[Pasted image 20250527185830.png]]
This is when we use `list-style-position`:
```CSS
ul{
    list-style-type: square;
    text-align: center;
    list-style-position: inside;
}
```
![[Pasted image 20250527190036.png]]

And like that there are other list style properties worth looking at as we build more stuff.

## list-style-image

we can also use an image for list-styles instead of the default symbols provided in `list-style-type` by doing:
```CSS
ul{
	list-style-image: url("../path/to/your/image.png");
}
``` 

---
# Variables in CSS
CSS variables, also known as custom properties, are a powerful feature that lets you store values in one place and reuse them throughout your stylesheet. This makes your CSS cleaner, more maintainable, and enables dynamic theming and easier updates.

---
## Defining a CSS Variable
CSS variables are defined by prefixing the property name with two dashes (`--`). You place them inside a CSS selector. For global variables, use the `:root` selector (which targets the root element of the document):

```css
:root {   
	--primary-color: #1e90ff;  
	--font-size: 16px; 
}
````
- `:root` makes the variables available everywhere in your CSS.
- Variable names are case-sensitive (`--mainColor` and `--maincolor` are different).    

You can also define variables locally within a selector, making them available only inside that selector:
```css
.card {   
	--border-color: #ddd;  
	border: 2px solid var(--border-color); 
}
```
Here, `--border-color` is only accessible inside `.card` class of the HTML.

---
## Using a CSS Variable

To use a variable, apply the `var()` function:

```css
body {   
	background-color: var(--primary-color);  
	font-size: var(--font-size); 
}
```
- The `var()` function takes the variable name as its first argument.
- You can provide a fallback value as a second argument, which is used if the variable is not defined:

```css
color: var(--text-color, black);
```
If `--text-color` is not set, `black` will be used.

---
## Benefits of CSS Variables

- **Consistency:** Change a value in one place to update it everywhere it’s used
- **Dynamic Theming:** Easily switch themes (e.g., light/dark mode) by changing variables
- **Less Repetition:** Avoid duplicating values across your CSS.
- **JavaScript Integration:** Variables can be updated dynamically with JavaScript.

---
## Example: Global and Local Variables

```css
:root {   
	--main-bg: #fff;  
	--main-text: #222; 
} 

body {   
	background: var(--main-bg);  
	color: var(--main-text); 
} 

.sidebar {   
	--main-bg: #222;  
	background: var(--main-bg); /* Uses the local variable */  
	color: var(--main-text);    /* Inherits from :root */ 
}
```
- `.sidebar` uses its own `--main-bg`, but still uses the global `--main-text`.

---
## **Key Points**
- Variable names must start with `--` and are case-sensitive.
- Define in `:root` for global scope, or in any selector for local scope.
- Use `var(--variable-name)` to access the value.
- Fallback values ensure robustness if a variable is missing.

---
# Recall Table:
- [[align-items.canvas]] 

---
# Learn By making

Now it is time to learn what we learned in both CSS and HTML and make some stuff.
We would also use [w3school](https://www.w3schools.com/css/default.asp) for searching for new stuff.
## Project 1

Continuing on what we did in [[HTML(Hyper-Text Makeup Language)#Project 1]], we now work on the CSS of it.
It is usually easy to create mobile version first, and then the desktop version.

we used the following new CSS Properties:
- [[display Property]] 
	- [[flex value]] 
	- [[none value]]
- [[place-content Property]] 
	- [[space-between]]
- [[cursor property]] 
- [[justify-content.canvas|justify-content]]
#### HTML
```xml
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My First Project</title>
    <link rel="stylesheet" href="Css/main.css">
</head>
<body>
   <div class="navbar"> <!--This is for the navigation bas-->
        <div class="container"> <!--This one is another container created to keep the navigation bar in center-->
            <!--
            A tip to create a container with a class, use '.' 
            followed by class name you want to give without any spaces 
            in between
            -->
            <a class="logo" href="https://google.com">Remember<span>That</span></a>
            <!--This is for the logo, as usually logo is used for ppl to 
            access the homepage directly-->
            
            <!--
            Now this is a menu button for phone version of this site
            -->
            <img id="mobile.cta" class="mobile-menu" src="images/menu.svg" alt="">
            
            
            <nav>
            <!--Using nav tag for navigation bar elements like homepage and all, recogmmended-->
                <img id="mobile-exit" class="mobile-menu-exit" src="images/exit.svg" alt="close any navigation bar"> <!--Whenever we open a navigation, ppl should have something to close it as well-->
                <ul class="PrimaryNav">
                    <li class="current"><a href="#">Home</a></li>
                    <li><a href="#">Features</a></li>
                    <li><a href="#">Pricing</a></li>
                    <!--Tip, use shift+alt+ctrl+(downArrow) key to replicate stuff-->
                </ul>
                <ul class="SecondaryNav">
                    <li><a href="#">Contact</a></li>
                    <li class="Go-premium-cta"><a href="#">Go Premium</a></li>
                    <!--Tip, Give class to all elements to apply different css to, helps a lot-->
                </ul>
            </nav>

        </div>
   </div> 

   <section class="hero">
        <div class="container"> <!--This is the container for the heading section-->
            <div class="left-col"> <!--For all the text and buttons-->
                <p class="subhead">
                    It's Nitty &amp; Gritty
                </p>
                <h1>A Taks App that doesn't suck</h1>

                <div class="hero-cta"> <!--The buttons below the main heading text-->
                    <a href="#" class="primary-cta">Try for free</a>
                    <a href="#" class="watch-video-cta">
                        <img src="images/watch.svg" alt="Watch a video">
                        Watch a video
                    </a>
                </div>
            </div>
            <img src="images/illustration.svg" class="hero-img" alt="Image for the graphics of this hero section">
        </div>
   </section>

   <section class="features-section">
        <div class="container">
            <ul class="features-list">
                <li>Unlimited Tasks</li>
                <li>SMS Task Reminders</li>
                <li>Confetti Explosions upon Task Completions</li>
                <li>Social Media Announcements</li>
                <li>Real Time Collaboration</li>
                <li>Other awesome features</li>
            </ul>

            <img src="images/holding-phone.jpg" alt="Man holding phont photo from front">
        </div>
   </section>

   <section class="testimonial-section">
        <div class="container">
            <ul class="slides">
                <li class="slide">

                    <img src="images/person.jpg" alt="Person">
                    <blockquote>"This has made my life so organised, simple and fun!"</blockquote>
                    <cite>-Jane Doe</cite>
                </li>
                
                <li class="slide">

                    <img src="images/person.jpg" alt="Person">
                    <blockquote>"This has made my life so organised, simple and fun!"</blockquote>
                    <cite>-Jane Doe</cite>
                </li>
                
                <li class="slide">

                    <img src="images/person.jpg" alt="Person">
                    <blockquote>"This has made my life so organised, simple and fun!"</blockquote>
                    <cite>-Jane Doe</cite>
                </li>
                
            </ul>
        </div>
   </section>


   <section class="contact-section">
        <div class="container">
                <div class="contact-left">
                    <h2>Contact</h2>
                    <form action="">
                        <label for="name">Name</label>
                        <input type="text" name="name" id="name" placeholder="Enter your name">
                            <br> <br>
                        <label for="email">E-mail</label>
                        <input type="text" name="email" id="email" placeholder="Enter your email">
                            <br> <br>
                        <label for="message">Message</label>
                        <textarea name="message" id="message" rows="10" cols="30">
                        </textarea>

                        <input type="submit" class="send-message-cta" value="Send Message">

                    </form>
                </div>
                <div class="contact-right">
                    <!--Time to add a map-->
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d7037738.296334398!2d-29.730330792252243!3d64.49145954571817!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x48d22b52a3eb6043%3A0x6f8a0434e5c1459a!2sIceland!5e0!3m2!1sen!2sin!4v1748410180981!5m2!1sen!2sin" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                </div>
        </div>
   </section>

   <script>
        const mobileBtn = document.getElementById('mobile.cta')
            nav = document.querySelector('nav')
            mobileBtnExit = document.getElementById('mobile-exit');

        mobileBtn.addEventListener('click', ()=>{
            nav.classList.add('menu-btn');
        })
        mobileBtnExit.addEventListener('click',()=>{
            nav.classList.remove('menu-btn');
        })
   </script>

</body>
</html>
```

#### Mobile version CSS

```CSS
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

:root{
    --primary-color: #007af3;
}

body{
    background: #f2f2f2;
    margin: 0;
    font-family: 'Poppins';
}

.navbar{
    background-color: white;
    padding: 1em;

    .logo{
        text-decoration: none;
        font-weight: bold;
        color: black;
        font-size: 1.2em;
        
        span{
            color: var(--primary-color); /*Using the varibale to set up the color*/
        }
    }
    nav{
        display: none; /*Hides stuff */
    }

    .container{
        display: flex;
        place-content: space-between;
    }

    .mobile-menu{
        cursor: pointer;
    }
}

a{
    color: #444444;
}

ul{
    list-style-type: none;
    margin: 0;
    padding: 0;
}

section{
    padding: 5em 2em ;
}

.hero{
    text-align: center;

    .left-col{
        .subhead{
            text-transform: uppercase;
            font-weight: bold;
            letter-spacing: 0.3em;
            color: grey;
        }
        h1{
            font-size: 2.5em;
            line-height: 1.3em;
            margin-top: .2em;
        }
        
        /*Now time to edit the try for free button, it gonna involve some more properties to use*/
        .primary-cta{
            background-color: var(--primary-color);
            color: whitesmoke;
            text-decoration: none;
            padding: 0.6em 1.3em;
            border-radius: 5em;
            font-size: 1.4em;
            font-weight: bold;
            display: inline-block;
        }
        .watch-video-cta{
            display: block;
            margin: 1em 0 0 0;
            
            img{
                margin-right: .5em;
            }
            text-decoration: none;
            
        }
    }

    .hero-img{
        margin-top: 5em;
        width: 70%;
    }


}

.features-section{
    background: #20272e;
    color: whitesmoke
}

ul.features-list{
    margin: 0;
    padding-left: .1em;

    li{
        font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
        font-size: 1.3em;
        margin-bottom: 1em;
        margin-left: 2em;
        position: relative;
        
        /*Giving bullet points that are custom is more of a advanced to intermmediate kind of topic*/
        &:before{
            content: ' ';
            left: -2em;
            position: absolute;
            width: 20px;
            height: 20px;
            background-image: url('../images/bullet.svg');
            background-size: contain;
            margin-right: 0.5em;
        }
    }
}

.features-section img{
    display: none;
}

.testimonial-section{
    background-color: var(--primary-color);
color: whitesmoke;
    ul{
        li{
            background-color: #006bd6;
            text-align: center;
            padding: 2em 1em;
            width: 80%;
            margin: 0 auto 5em auto;

            img{
                width: 5em;
                height: 5em;
                border: 5px solid #006bd6;
                border-radius: 50%;
                margin-top: -4.5em;
            }
        }
    }
}

h2{
    font-size: 2em;
}

label{
    display: block;
    font-size: 1em;
    margin-bottom: 0.5em;
    
}

input, textarea{
    width: 100%;
    padding: 0.8em;
    margin-bottom: 1em;
    border-radius: 0.3em;
    border: 1px solid grey;
    box-sizing: border-box;
}

input[type="submit"]{
    background-color: var(--primary-color);
    color: whitesmoke;
    font-weight: bold;
    font-size: 1.3em;
    border: none;
    margin-bottom: 5em;
    border-radius: 5em;
    display: inline-block;
    padding: 0.8em 2em;
    cursor: pointer;
    width: unset;
}

iframe{
    width: 100%;
    height: 300px;
}

nav.menu-btn{
    display: block;

}

nav{
    position: fixed;
    z-index: 999;
    width: 66%;
    right: 0;
    top: 0;
    background-color: #20272e;
    height: 100vh;

    ul.PrimaryNav{
        margin-top: 5em;

    }
    li{
        a{
            color: whitesmoke;
            display: block;
            text-decoration: none;
            padding: 0.5em;
            font-size: 1.3em;
            text-align: right;

            &:hover{
                font-weight: bold;
                font-size: 1.5em;
            }
        }
    }
}

.mobile-menu-exit{
    float: right;
    margin: 0.5em;
    cursor: pointer;
}
```

#### Desktop-Css
Now time to add some Responsive CSS:
```CSS
/* Adding responsive CSS */
@media only screen and (min-width: 768px){
    .mobile-menu, .mobile-menu-exit{
        display: none;
    }
    .navbar .container{
        display: grid;
        grid-template-columns: 180px auto;
        justify-content: unset;
    }
    .navbar nav{
        display: flex;
        justify-content: unset;
        background: none;

        position: unset;
        height: auto;
        width: 100%;
        padding: 0;

        ul{
            display: flex;

        }
        a{
            color: black;
            font-size: 1em;
            padding: .1em 1em;

        }
        

        ul.PrimaryNav{
            margin: 0;

        }
        li{
            &:hover{
                font-size: realative;
            }
        }
        li.current a{
            font-weight: bold;
        }
        li.Go-premium-cta a{
            color: var(--primary-color);
            border: 3px solid var(--primary-color);
            font-weight: bold;
            border-radius: 5em;
            margin-top: -.2em;
            text-align: right;

            &:hover{
                background: var(--primary-color);
                color: whitesmoke;
            }
        }
    }
}
```

This is appended in the `main.css` script.