## Overview of Vue Directives

Vue directives are special HTML attributes prefixed with `v-` that provide additional functionality to HTML elements. They allow you to declaratively bind dynamic behavior to the DOM, making user interfaces more interactive and reactive[3](https://www.w3schools.com/vue/vue_directives.php)[4](https://vueschool.io/articles/vuejs-tutorials/vue-js-directives-a-beginners-guide/)[5](https://www.w3schools.com/vue/vue_ref_directives.php). Directives are a core feature of Vue.js, simplifying tasks like conditional rendering, event handling, and data binding.

## Common Built-in Vue Directives

Here are some of the most frequently used built-in directives in Vue:

| Directive                 | Description                                                                                |
| ------------------------- | ------------------------------------------------------------------------------------------ |
| v-bind                    | Binds an attribute to a data property                                                      |
| v-model                   | Creates two-way binding between form input and data property                               |
| v-if / v-else-if / v-else | Conditionally renders elements based on an expression                                      |
| v-show                    | Toggles an element’s visibility using CSS (`display: none`)                                |
| v-for                     | Renders a list of elements by iterating over an array                                      |
| v-on                      | Attaches event listeners to elements (e.g., `v-on:click` or the shorthand `@click`)        |
| v-html                    | Renders raw HTML inside an element                                                         |
| v-text                    | Updates an element’s text content                                                          |
| v-cloak                   | Hides uncompiled template until Vue instance is ready                                      |
| v-once                    | Renders an element or component only once, skipping future updates                         |
| v-pre                     | Skips compilation for the element and its children (renders template syntax as plain text) |
| v-slot                    | Used for named slots in components                                                         |
| v-memo                    | Memoizes part of the template to optimize performance                                      |

## Example: Using v-bind

xml

`<div v-bind:class="vueClass"></div>`

This binds the `class` attribute of the `<div>` to the `vueClass` property in your Vue instance[3](https://www.w3schools.com/vue/vue_directives.php).

## Example: Using v-model

xml

`<input v-model="fullName" />`

This creates a two-way binding between the input field and the `fullName` data property, updating both as the user types[7](https://www.scholarhat.com/tutorial/vue/directives-in-vuejs).

## Custom Directives

In addition to built-in directives, Vue allows you to create your own custom directives, which are especially useful for encapsulating reusable DOM manipulation logic that doesn't fit well into components or composables[1](https://vuejs.org/guide/reusability/custom-directives)[6](https://www.tutorialspoint.com/vuejs/vuejs_directives.htm)[8](https://dev.to/jacobandrewsky/christmas-guide-to-custom-directives-in-vue-47hj).

## Creating a Custom Directive

Custom directives can be registered globally or locally:

**Global Registration Example:**

javascript

`app.directive('focus', {   mounted(el) {    el.focus();  } });`

This directive (`v-focus`) automatically focuses the element when it is mounted[8](https://dev.to/jacobandrewsky/christmas-guide-to-custom-directives-in-vue-47hj).

**Local Registration Example:**

javascript

`export default {   directives: {    focus: {      mounted(el) {        el.focus();      }    }  } }`

Then use `<input v-focus />` in your template[8](https://dev.to/jacobandrewsky/christmas-guide-to-custom-directives-in-vue-47hj).

## Custom Directive Lifecycle Hooks

A custom directive is defined as an object with lifecycle hooks similar to component hooks. The most common hooks include:

- `created`
    
- `beforeMount`
    
- `mounted`
    
- `beforeUpdate`
    
- `updated`
    
- `beforeUnmount`
    
- `unmounted`[1](https://vuejs.org/guide/reusability/custom-directives)[8](https://dev.to/jacobandrewsky/christmas-guide-to-custom-directives-in-vue-47hj)
    

These hooks receive the element the directive is bound to, allowing for direct DOM manipulation.

## Example: Custom Directive for Styling

javascript

`Vue.directive("changestyle", {   bind(el) {    el.style.color = "red";    el.style.fontSize = "30px";  } });`

Applied as `<div v-changestyle>VueJS Directive</div>`, this will render the text in red and increase the font size[6](https://www.tutorialspoint.com/vuejs/vuejs_directives.htm).

## When to Use Directives

- **Built-in directives** are ideal for common tasks like binding, event handling, and conditional rendering.
    
- **Custom directives** are best for low-level DOM manipulations or behaviors that are reused across components but don't require full component logic (e.g., auto-focusing, tooltips, custom effects)[1](https://vuejs.org/guide/reusability/custom-directives)[8](https://dev.to/jacobandrewsky/christmas-guide-to-custom-directives-in-vue-47hj).
    

For more complex or state-driven logic, consider using Vue components or composables for better integration with Vue's reactivity system[8](https://dev.to/jacobandrewsky/christmas-guide-to-custom-directives-in-vue-47hj).

# Event Modifiers
Event modifiers in Vue are special postfixes (added with a dot, like `.prevent`) that you append to events when using the `v-on` directive (or its shorthand `@`). They let you control how events are handled, making your code cleaner and more expressive by shifting common event logic (like stopping propagation or preventing default behavior) directly into your template.

## Why Use Event Modifiers?

- **Cleaner code:** Keeps event logic out of your methods, letting them focus on business logic instead of DOM details[1](https://vuejs.org/guide/essentials/event-handling)[2](https://www.c-sharpcorner.com/article/what-are-event-modifiers-in-vue-js/).
    
- **Declarative control:** You can see at a glance how events are being handled, right in your template.
    

---

## List of Basic Event Modifiers

Here are the most common event modifiers in Vue, with details and examples for each:

|Modifier|Description|Example|
|---|---|---|
|`.stop`|Calls `event.stopPropagation()`. Stops the event from bubbling up the DOM tree.|`<button @click.stop="doThis">Click</button>`|
|`.prevent`|Calls `event.preventDefault()`. Prevents the default browser action for the event.|`<form @submit.prevent="onSubmit"></form>`|
|`.capture`|Adds the event listener in capture mode (opposite of default bubbling).|`<div @click.capture="doThis"></div>`|
|`.self`|Only triggers the handler if the event target is the element itself, not a child.|`<div @click.self="doThat"></div>`|
|`.once`|The event handler will only be triggered once.|`<button @click.once="doThis">Click</button>`|
|`.passive`|Tells the browser that the handler will never call `preventDefault()`. Good for performance.|`<div @scroll.passive="onScroll"></div>`|

---

## Details and Usage

**.stop**  
Stops event propagation. The event will not bubble up to parent elements.

xml

`<button @click.stop="handleClick">Stop Propagation</button>`

**.prevent**  
Prevents the default action (like submitting a form or following a link).

xml

`<form @submit.prevent="handleSubmit"></form>`

**.capture**  
Makes the event fire during the capture phase instead of the bubbling phase.

xml

`<div @click.capture="handleCapture"></div>`

**.self**  
Only fires if the event originated from the element itself, not from a child.

xml

`<div @click.self="handleSelfClick">Click me</div>`

**.once**  
The handler will only run once for this element.

xml

`<button @click.once="handleOnce">Click Once</button>`

**.passive**  
Improves performance for events like scroll and touch by telling the browser you won’t call `preventDefault()` inside the handler.  
**Important:** Don’t use `.passive` and `.prevent` together—they are mutually exclusive[1](https://vuejs.org/guide/essentials/event-handling).

xml

`<div @scroll.passive="handleScroll"></div>`

---

## Chaining Modifiers

You can combine multiple modifiers together.  
**Order matters**—they are applied left to right.

xml

`<a @click.stop.prevent="handleLink">Stop & Prevent</a>`

- `@click.stop.prevent` will stop propagation first, then prevent default.
    
- `@click.prevent.stop` will prevent default first, then stop propagation.
    

---

## System Modifiers

For keyboard and mouse events, Vue also supports system modifier keys:

- `.ctrl`
    
- `.alt`
    
- `.shift`
    
- `.meta` (Command on Mac, Windows key on Windows)
    
- `.exact` (only triggers if exactly the specified system keys are pressed and no others)
    

**Example:**

xml

`<button @click.ctrl="onCtrlClick">Ctrl + Click</button> <input @keyup.enter="onEnter" /> <input @keyup.ctrl.enter.exact="onCtrlEnterOnly" />`

`.exact` ensures that only the specified keys (and no others) are pressed[1](https://vuejs.org/guide/essentials/event-handling)[3](https://learnvue.co/articles/vue-event-handling-guide).

---

## Best Practices

- Use event modifiers to keep your methods focused on application logic, not DOM event details[1](https://vuejs.org/guide/essentials/event-handling)[2](https://www.c-sharpcorner.com/article/what-are-event-modifiers-in-vue-js/).
    
- Don’t use `.passive` with `.prevent`—they are incompatible[1](https://vuejs.org/guide/essentials/event-handling).
    
- Remember: order of modifiers can affect behavior[1](https://vuejs.org/guide/essentials/event-handling).
    

---

## Summary Table

|Modifier|Purpose|Typical Use Case|
|---|---|---|
|.stop|Stop event propagation|Prevent parent handlers from firing|
|.prevent|Prevent default browser action|Stop form submission or link navigation|
|.capture|Listen in capture phase|Advanced event flow control|
|.self|Only fire if event target is the element|Ignore child element events|
|.once|Fire handler only once|Single-use buttons or actions|
|.passive|Improve performance for scroll/touch events|Mobile scroll, touch events|
|.ctrl, .alt, .shift, .meta|Require modifier keys|Keyboard shortcuts, custom commands|
|.exact|Only specified keys must be pressed|Strict keyboard shortcut handling|
