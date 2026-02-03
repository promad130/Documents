## What Is a Vue App?
A **Vue app** is a JavaScript application built using the Vue.js framework. At its core, a Vue app manages the user interface (UI) of a website or web application by making it reactive and component-based.

---
## 1. Vue as a Framework

- **Vue.js** is a JavaScript framework for building user interfaces. It builds on standard HTML, CSS, and JavaScript, and introduces a declarative, component-based programming model[3](https://vuejs.org/guide/introduction)[4](https://builtin.com/software-engineering-perspectives/vue-js).
    
- Vue apps can be simple (a single button) or complex (entire single-page applications with routing, state management, etc.)[4](https://builtin.com/software-engineering-perspectives/vue-js).
    

---

## **2. The Structure of a Vue App**

- A Vue app starts with a **root component**—this is the main component that contains your entire application[12](https://vuejs.org/guide/essentials/application).
    
- Inside this root component, you can nest other components, creating a tree-like structure. Each component is a reusable, self-contained unit of UI and logic[1](https://vuejs.org/guide/essentials/component-basics)[6](https://www.w3schools.in/vuejs/components)[12](https://vuejs.org/guide/essentials/application).
    
- Components are the building blocks of Vue apps. They help you break down the UI into manageable, reusable pieces[1](https://vuejs.org/guide/essentials/component-basics)[2](https://www.w3schools.com/vue/vue_components.php)[5](https://vuejsdevelopers.com/lessons/vue-essentials-components/1/)[6](https://www.w3schools.in/vuejs/components).
    

---

## **3. How a Vue App Is Created**

- In Vue 3, you create an app by calling the `createApp` function and passing it your root component (an object that defines the app’s data, methods, and template)[3](https://vuejs.org/guide/introduction)[9](https://www.c-sharpcorner.com/article/understanding-application-structure-of-vue-js/).
    
- Example:
    
    js
    
    `import { createApp } from 'vue' createApp({   data() {    return { count: 0 }  } }).mount('#app')`
    
- The `.mount('#app')` part tells Vue to attach your app to the HTML element with the id `app`[3](https://vuejs.org/guide/introduction)[9](https://www.c-sharpcorner.com/article/understanding-application-structure-of-vue-js/).
    

---

## **4. What Does a Vue App Do?**

- **Manages UI:** Vue apps control what the user sees and interacts with, updating the UI automatically when the underlying data changes.
    
- **Handles State:** The app keeps track of its state (data), and when you update the data, Vue updates the UI for you.
    
- **Organizes Code:** By splitting the UI into components, Vue apps are easier to build, maintain, and scale[1](https://vuejs.org/guide/essentials/component-basics)[2](https://www.w3schools.com/vue/vue_components.php)[6](https://www.w3schools.in/vuejs/components).
    

---

## **5. Example: Minimal Vue App**

HTML:

xml

`<div id="app">   <button @click="count++">Count is: {{ count }}</button> </div>`

JavaScript:

js

`import { createApp } from 'vue' createApp({   data() {    return { count: 0 }  } }).mount('#app')`

- Here, the Vue app controls the button and the `count` variable. When you click the button, `count` increases, and the UI updates automatically[3](https://vuejs.org/guide/introduction).
    

---
# Parameters of createApp() function:
Here’s a detailed explanation of the **parameters of the Vue app**—specifically, what you pass to `createApp` and how those parameters work in Vue 3.

---

## 1. **The Parameters of `createApp`**

The `createApp` function is used to create a Vue application instance. It takes up to **two parameters**:

## **a. Root Component (Required, First Parameter)**

- This is the main component that forms the entry point of your Vue app.
    
- It can be:
    
    - An object with component options (like `data`, `methods`, `template`, etc.)
        
    - An imported `.vue` file/component
        

**Example:**

js

`// Inline root component const app = createApp({   data() {    return { message: 'Hello Vue!' }  },  methods: {    greet() { alert(this.message) }  } }) // OR, with a single-file component import App from './App.vue' const app = createApp(App)`

---

## **b. Root Props (Optional, Second Parameter)**

- This is an optional object containing props to pass to the root component.
- Useful if you want to provide initial data or configuration from outside the component.

**Example:**

js

`const app = createApp(App, { someProp: 'value' })`

- Here, `someProp` will be available as a prop inside the `App` component.  
    [3](https://vuejs.org/api/application)[4](https://stackoverflow.com/questions/64353060/passing-data-to-createapp-vue)
    

---

## 2. **What Can You Put in the Root Component?**

The root component (first parameter) can contain many options, just like any Vue component:

- **data()**: Returns an object with the reactive state of your app, which that can be used to access the variables and methods to be used inside the mount element to do stuff!!!
	- `data` is a **property name** that Vue recognizes when you define a component or the root app.
	- Its value **must be a function** that returns an object. This returned object contains the initial state (reactive data) for that component or app
	- Vue calls this function automatically when creating the component instance.
	So, **No, it is not a JavaScript or Vue language keyword**, but we **cannot** just use any other keyword or property name in place of `data` and expect Vue to make it reactive or treat it as the component’s state. 
	Vue specifically looks for the `data` option in your component definition to know where your reactive state comes from
- **methods**: Functions you can call from your template or in response to events.
- **computed**: Properties that are calculated from your data and update reactively.
- **template**: The HTML structure for your component (if not using a `.vue` file).
- **components**: Register child components.
- **props**: Define props if you expect to receive them (especially when using the second parameter).
- **watch**: React to changes in data or computed properties.
- **lifecycle hooks**: Functions like `mounted`, `created`, etc., that run at specific times in the component's life.

**Example:**

js

``const app = createApp({   data() {    return { count: 0 }  },  methods: {    increment() { this.count++ }  },  computed: {    double() { return this.count * 2 }  },  template: `<button @click="increment">Count: {{ count }} (Double: {{ double }})</button>` })``



---

## 3. **How Does This All Work Together?**

- You pass the root component (and optionally, root props) to `createApp`.
    
- Vue creates an application instance based on these options.
    
- You can further configure the app instance (register plugins, global components, etc.).
    
- Finally, you call `.mount('#app')` to attach your app to a DOM element.
    

---

## 4. **Summary Table**

|Parameter|Required|Type|Description|
|---|---|---|---|
|Root Component|Yes|Object or Component|The main component for your app (object or imported `.vue` file)|
|Root Props|No|Object|Props to pass to the root component (optional)|

---
Next up [[Connecting Vue App with the HTML Script]]