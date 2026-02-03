## What is `v-bind` in Vue.js?

`v-bind` is a built-in Vue directive that lets you **dynamically bind HTML attributes to data or expressions** in your Vue app. This means you can set the value of an attribute (like `src`, `href`, `class`, `style`, etc.) based on your app’s reactive state, and Vue will automatically update the attribute if the data changes.

---

## **Why use `v-bind`?**

- **Dynamic values:** HTML attributes are normally static, but with `v-bind`, you can make them change based on your data.
    
- **Reactivity:** When the bound data changes, Vue updates the attribute in the DOM automatically.
    
- **Cleaner code:** Avoids manual DOM manipulation—Vue handles it for you.
    

---

## **Basic Syntax**

xml

`<!-- Full syntax --> <img v-bind:src="imageUrl"> <!-- Shorthand syntax (recommended) --> <img :src="imageUrl">`

- Both lines do the same thing: they set the `src` attribute of the `<img>` tag to the value of the `imageUrl` variable from your Vue instance[1](https://www.w3schools.com/vue/vue_v-bind.php)[3](https://vuejs.org/guide/essentials/template-syntax)[5](https://www.scholarhat.com/tutorial/vue/directives-in-vuejs)6.
    

---

## **How does it work?**

- Vue watches the data or expression you bind.
    
- When the value changes, Vue updates the attribute in the DOM.
    
- You can bind to any valid HTML attribute, and also to `class` and `style` for dynamic styling[1](https://www.w3schools.com/vue/vue_v-bind.php)[2](https://vuejs.org/api/built-in-directives.html)[4](https://vuejs.org/guide/essentials/class-and-style).
    

---

## **Examples**

## **Binding a Simple Attribute**

xml

`<a v-bind:href="profileLink">My Profile</a> <!-- or shorthand --> <a :href="profileLink">My Profile</a>`

- If `profileLink` changes, the `href` updates automatically.
    

## **Binding Classes and Styles**

xml

`<div v-bind:class="className"></div> <!-- or --> <div :class="className"></div>`

- `className` could be a string, object, or array, allowing for dynamic class assignment[1](https://www.w3schools.com/vue/vue_v-bind.php)[4](https://vuejs.org/guide/essentials/class-and-style).
    

xml

`<div v-bind:style="{ color: textColor, fontSize: size + 'px' }"></div> <!-- or --> <div :style="{ color: textColor, fontSize: size + 'px' }"></div>`

- Styles update as `textColor` or `size` change in your data[1](https://www.w3schools.com/vue/vue_v-bind.php)[4](https://vuejs.org/guide/essentials/class-and-style).
    

## **Conditional Binding**

xml

`<img v-bind:src="isLoggedIn ? userPic : defaultPic">`

- The image source changes based on whether the user is logged in.
    

---

## **Special Notes**

- **Shorthand:** The colon (`:`) is a shorthand for `v-bind:` and is used almost universally in modern Vue code[1](https://www.w3schools.com/vue/vue_v-bind.php)[3](https://vuejs.org/guide/essentials/template-syntax)6.
    
- **One-way binding:** `v-bind` is one-way—changes in data update the attribute, but not the other way around[5](https://www.scholarhat.com/tutorial/vue/directives-in-vuejs).
    
- **Attribute removal:** If the bound value is `null` or `undefined`, Vue removes the attribute from the element[3](https://vuejs.org/guide/essentials/template-syntax).
    

---

## **Summary Table**

|Syntax|What it does|
|---|---|
|`v-bind:attr="val"`|Binds attribute `attr` to JS expression `val`|
|`:attr="val"`|Shorthand for the above|
|`v-bind:class="..."`|Dynamic class assignment|
|`v-bind:style="..."`|Dynamic style assignment|

---

**In short:**  
`v-bind` is how you tell Vue to keep an HTML attribute in sync with your data, making your UI dynamic and reactive[1](https://www.w3schools.com/vue/vue_v-bind.php)[3](https://vuejs.org/guide/essentials/template-syntax)[5](https://www.scholarhat.com/tutorial/vue/directives-in-vuejs). The shorthand `:` makes your code cleaner and easier to read.

Add to follow-up

Check sources

1. [https://www.w3schools.com/vue/vue_v-bind.php](https://www.w3schools.com/vue/vue_v-bind.php)
2. [https://vuejs.org/api/built-in-directives.html](https://vuejs.org/api/built-in-directives.html)
3. [https://vuejs.org/guide/essentials/template-syntax](https://vuejs.org/guide/essentials/template-syntax)
4. [https://vuejs.org/guide/essentials/class-and-style](https://vuejs.org/guide/essentials/class-and-style)
5. [https://www.scholarhat.com/tutorial/vue/directives-in-vuejs](https://www.scholarhat.com/tutorial/vue/directives-in-vuejs)
6. [https://www.youtube.com/watch?v=7ZjNQ92Pl3c](https://www.youtube.com/watch?v=7ZjNQ92Pl3c)
7. [https://vuejs.org/guide/essentials/forms](https://vuejs.org/guide/essentials/forms)