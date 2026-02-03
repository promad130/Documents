**Expected to know:** [[CSS#Changing stuff]]
**Topics Covered:**
**Tags:**

Selectors are quite easy to understand, and are mostly intuitive. 
What would a selector affect? Everything that is in the element of which we are using the selector of.

So for example:
```CSS
body{
	font-size: 22px;
}
```

affects everything inside the body, same can be said for the paragraph selector `p`, etc,.

There are a few different type of selectors. The ones we were dealing with are called **Element Selector**.

We can work with multiple of them like this:
```css
h1, h2{

}
```
But if we do this:
```css
h1 h2{

}
```
Then it considers the rightmost element to be the active element, i.e., the element on which style has to be applied and `h1` is unaffected.
This syntax is used for working with sub-elements of an element.
```HTML
<p>
	<span>
		
	<\span>
</p>
```
Then we can work with span element like:
```css
p span{
	
}
```
---
# The second type is **Class Selector:**

Can be named anything, and started with a period(.).

These are used for selective styling of elements by creating a class of your choice and setting the class attribute of the particular element you want to personalize to the name of that class.

Something like this:
```HTML
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
    
</head>
<body>
    <p >I'm learning CSS</p>
    <p class="customStuff">hi</p>
</body>
</html>
```

```CSS
p{
    font-size: 32px;
    color: coral; 
}

body{
    font-size: 22px; 
}

.customStuff{
    color: brown;
    font-weight: bold;
    font-size: 64 px;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    display: block;
}
```

and the result:
![[Pasted image 20250519203137.png]]

---
# Classes are actually used more commonly due to the offered flexibility, but a more specific one is **ID Selector**:

They start with `#` followed by the `ID` on which the CSS has to be applied.
Usually we don't include Id in our CSS as it is not a good practice.
```HTML
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
    
</head>
<body>
    <p >I'm learning CSS</p>
    <p class="customStuff">hi</p>
    <p id="tryOut">Hi, this is the result of an ID selector</p>
</body>
</html>
```
```CSS
p{
    font-size: 32px;
    color: coral; 
}

body{
    font-size: 22px; 
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
Result:
![[Pasted image 20250519203931.png]]

---

# Universal Selector

Selects everything in the page:
```css
* {
	
}
```

---
