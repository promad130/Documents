Vue.js uses an HTML-based template syntax that allows you to declaratively bind the rendered DOM to your component's data. Here are the essential syntax elements you need to get started:

---
**1. Template Structure**
- Vue components are typically structured with three main sections:
    - `<template>`: Defines the HTML structure.
    - `<script>`: Contains JavaScript logic.        
    - `<style>`: Holds CSS styles.

Example:
```vue.js
<template>   
	<h1>{{ message }}</h1> 
</template> 

<script setup> 
	import { ref } from 'vue'; 
	const message = ref('Hello, Vue.js 3!'); 
</script> 

<style scoped> h1 { color: blue; } </style>
```
---
**2. Text Interpolation**
- Use double curly braces (`{{ }}`) to display dynamic data in your HTML.
    
- The expression inside the braces is evaluated as JavaScript.
    

Example:

xml

`<p>{{ message }}</p> <p>{{ 2 + 2 }}</p>`

---

**3. Expressions in Interpolation**

- You can use JavaScript expressions inside `{{ }}`, such as method calls, mathematical operations, or ternary operators.
    

Example:

xml

`<p>{{ message.toUpperCase() }}</p> <p>{{ isActive ? 'Active' : 'Inactive' }}</p>`

---

**4. Directives**

Directives are special attributes with the `v-` prefix. They reactively apply behavior to the DOM:

- **v-bind**: Dynamically bind an attribute to an expression.
    
    - `<a v-bind:href="url">Link</a>`
        
    - Shorthand: `<a :href="url">Link</a>`
        
- **v-on**: Listen to DOM events.
    
    - `<button v-on:click="doSomething">Click</button>`
        
    - Shorthand: `<button @click="doSomething">Click</button>`
        
- **v-model**: Two-way data binding for form inputs.
    
    - `<input v-model="txt">`
        
- **v-if**: Conditional rendering.
    
    - `<div v-if="showValue">Visible if showValue is true</div>`
        
- **v-for**: Render a list by iterating over an array.
    
    - `<li v-for="item in items" :key="item">{{ item }}</li>`  
        [1](https://vuejs.org/guide/essentials/template-syntax)[5](https://www.luisllamas.es/en/vuejs-basic-syntax/)[7](https://www.tatvasoft.com/blog/vuejs-guide/)
        

---

**5. Arguments and Modifiers**

- Some directives accept arguments (e.g., `v-bind:href`).
    
- Modifiers are special postfixes denoted by a dot, used for binding in a special way (e.g., event modifiers like `.prevent` for `v-on:submit.prevent`).  
    [1](https://vuejs.org/guide/essentials/template-syntax)[7](https://www.tatvasoft.com/blog/vuejs-guide/)
    

---

**6. Example: Basic Vue Template**

xml

`<div id="app">   <input v-model="message">  <p>{{ message }}</p>  <button @click="message = message.toUpperCase()">Uppercase</button> </div>`

---

## Summary Table

|Syntax|Purpose|Example|
|---|---|---|
|`{{ msg }}`|Text interpolation|`<span>{{ msg }}</span>`|
|`v-bind` / `:`|Bind attribute|`<img :src="imgUrl">`|
|`v-on` / `@`|Listen to events|`<button @click="doSomething">Click</button>`|
|`v-model`|Two-way data binding|`<input v-model="inputValue">`|
|`v-if`|Conditional rendering|`<div v-if="isVisible"></div>`|
|`v-for`|List rendering|`<li v-for="item in items">{{ item }}</li>`|

---

Vue's syntax is designed to be approachable, leveraging familiar HTML with powerful JavaScript bindings and directives for dynamic, reactive interfaces[1](https://vuejs.org/guide/essentials/template-syntax)[5](https://www.luisllamas.es/en/vuejs-basic-syntax/)[7](https://www.tatvasoft.com/blog/vuejs-guide/).

1. [https://vuejs.org/guide/essentials/template-syntax](https://vuejs.org/guide/essentials/template-syntax)
2. [https://vuejs.org/examples/](https://vuejs.org/examples/)
3. [https://vuejs.org/guide/introduction](https://vuejs.org/guide/introduction)
4. [https://mihai-gheorghe.gitbook.io/tic/vuejs-basic-syntax](https://mihai-gheorghe.gitbook.io/tic/vuejs-basic-syntax)
5. [https://www.luisllamas.es/en/vuejs-basic-syntax/](https://www.luisllamas.es/en/vuejs-basic-syntax/)
6. [https://www.tutorialspoint.com/vuejs/vuejs_examples.htm](https://www.tutorialspoint.com/vuejs/vuejs_examples.htm)
7. [https://www.tatvasoft.com/blog/vuejs-guide/](https://www.tatvasoft.com/blog/vuejs-guide/)
8. [https://www.w3schools.com/vue/vue_intro.php](https://www.w3schools.com/vue/vue_intro.php)
9. [https://www.w3schools.com/vue/](https://www.w3schools.com/vue/)