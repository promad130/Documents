## 1. Why Use the Vue CDN?
- **No installation required:** Just add a `<script>` tag—no need for npm, Node.js, or build tools.
- **Great for learning, demos, or small projects:** Perfect for experimenting or adding Vue to an existing static site.
- **Instant setup:** You can start coding Vue in seconds.

---
## 2. Basic Steps
### **Step 1: Create an HTML File**
Start by creating a new file called `index.html`.
## **Step 2: Add the Vue CDN Script**
Insert the Vue CDN link in your HTML’s `<head>` or just before the closing `</body>` tag. For development and learning, use the development version:
```xml
<script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
```
### **Step 3: Add an App Container**
Add a `<div>` with an `id` (like `app`). This is where Vue will take control, i.e., where we will export all the vue components:
```xml
<div id="app">   {{ message }} </div>
```
### **Step 4: Write Your Vue App Script**
Below the CDN script (or in a `<script>` tag after your app container), write your Vue app code:
```xml
<script>   
	const { createApp } = Vue   
	createApp({    
		data() 
		{      
			return 
			{        
				message: 'Hello Vue!'      
			}    
		}  
	}).mount('#app') 
</script>`
```
OR
```JS
import { createApp } from 'Vue'

const myVueApp = createApp();
```
OR
```JS
const mVueApp = Vue.createApp();
```
#### What is happening here?
##### 1. `const { createApp } = Vue`
- **What is `Vue`?**  
    When you include Vue via the CDN (like `<script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>`), a global object called `Vue` becomes available in your browser.
- **What is `{ createApp } = Vue`?** 
    This is called **destructuring assignment** in JavaScript. It means:  
    “Take the `createApp` property from the `Vue` object and make a variable called `createApp` that refers to it.”
    (Remember) We can also change the identifier like we did in Object Literals in JS when we did destructed the Object:
    `{createApp : identfierName}`
- **Why do this?**  
    It’s a convenient way to use `createApp` directly, instead of always writing `Vue.createApp`,i.e., the other ways to do this.

---

## 2. `createApp({ ... })`

- **What is `createApp`?**  
    It’s a function provided by Vue 3 that creates a new Vue application instance.
    
- **What does it take as an argument?**  
    It takes an **options object**—an object that defines your app’s data, methods, lifecycle hooks, etc.
    
- **What does it return?**  
    It returns an **application instance**—an object that represents your Vue app and lets you configure or mount it.
    

---

## 3. The Options Object

js

`{   data() {    return {      message: 'Hello Vue!'    }  } }`

- **`data()`**  
    This is a function (not just an object!) that Vue will call to get the initial state (data) for your app.
    
- **Why a function?**  
    Each time you create a new app/component, Vue calls this function to make sure each instance gets its own copy of the data (avoiding shared state bugs).
    
- **What does it return?**  
    It returns an object with a property called `message`, set to `'Hello Vue!'`.
    
- **What does this mean?**  
    Your app’s state now has a property called `message` that you can use in your template (HTML).
    

---

## 4. `.mount('#app')`

- **What does `.mount()` do?**  
    This method tells Vue:  
    “Take this app instance and attach it to the DOM element with the given selector.”
    
- **What does `'#app'` mean?**  
    It’s a CSS selector for an element with the id `app`, like `<div id="app"></div>` in your HTML.
    
- **What happens when you mount?**  
    Vue takes over that DOM element, renders your app’s template (by default, the HTML inside the element), and keeps it in sync with your app’s data.
    

---

## Putting It All Together

Let’s imagine you have this HTML:

xml

`<div id="app">   {{ message }} </div>`

When the code runs:

1. Vue finds the `<div id="app">` element.
    
2. It creates a Vue app instance with a `data` function returning `{ message: 'Hello Vue!' }`.
    
3. It mounts the app to the `#app` element.
    
4. Vue replaces `{{ message }}` in the HTML with the value of `message` from your data—so the user sees:
    
    text
    
    `Hello Vue!`
    
5. If you change `message` in your app (for example, in the browser console: `app.message = 'Hi!'`), Vue will automatically update the DOM to show the new message.

---

## 3. Complete Example
Here’s the full code for a basic Vue app using the CDN:
```xml
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Vue CDN Example</title>
</head>
<body>
  <div id="app">
    {{ message }}
  </div>

  <!-- Vue.js from CDN -->
  <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
  <script>
    const { createApp } = Vue

    createApp({
      data() {
        return {
          message: 'Hello Vue!'
        }
      }
    }).mount('#app')
  </script>
</body>
</html>
```
---

## **4. How It Works**

- The CDN script loads Vue into your page.
    
- `createApp({...}).mount('#app')` tells Vue to control the element with id `app`.
    
- The `data()` function returns an object with your state (here, just `message`).
    
- `{{ message }}` in the HTML is a Vue template expression—it displays the value of `message` and updates automatically if it changes.
    

---

## **5. What Can You Do Next?**

- **Add more data:** Just add more properties to the `data()` object.
    
- **Use methods:** Add a `methods` property for functions.
    
- **Try the Composition API:** Use a `setup()` function and `ref()` for more advanced reactivity[1](https://vuejs.org/guide/quick-start)[4](https://dev.to/lenildoluan/creating-a-vue-application-with-cdn-setup-32d2).
    
- **Create components:** Even with CDN, you can define and use components[6](https://www.compilenrun.com/docs/framework/vue/vuejs-fundamentals/vuejs-cdn-setup/).
    

---

## **6. Limitations of the CDN Approach**

- **No Single File Components (.vue files)**
    
- **No build tools or advanced features**
    
- **Best for learning, prototypes, or enhancing static pages**[1](https://vuejs.org/guide/quick-start)[4](https://dev.to/lenildoluan/creating-a-vue-application-with-cdn-setup-32d2)[6](https://www.compilenrun.com/docs/framework/vue/vuejs-fundamentals/vuejs-cdn-setup/)
    

For bigger projects, you’ll eventually want to use Vue CLI or Vite for a more robust setup[3](https://cli.vuejs.org/guide/creating-a-project).

---

**Summary:**  
Using the Vue CDN is the simplest way to start a Vue project—just add a script tag, create an app container, and write your Vue code right in the HTML file. This is perfect for learning, demos, or adding Vue to existing sites[1](https://vuejs.org/guide/quick-start)[4](https://dev.to/lenildoluan/creating-a-vue-application-with-cdn-setup-32d2)[6](https://www.compilenrun.com/docs/framework/vue/vuejs-fundamentals/vuejs-cdn-setup/).

1. [https://vuejs.org/guide/quick-start](https://vuejs.org/guide/quick-start)
2. [https://www.youtube.com/watch?v=UNfkkXxBbZA](https://www.youtube.com/watch?v=UNfkkXxBbZA)
3. [https://cli.vuejs.org/guide/creating-a-project](https://cli.vuejs.org/guide/creating-a-project)
4. [https://dev.to/lenildoluan/creating-a-vue-application-with-cdn-setup-32d2](https://dev.to/lenildoluan/creating-a-vue-application-with-cdn-setup-32d2)
5. [https://shouts.dev/articles/vue-js-routing-from-scratch-using-cdn-without-cli](https://shouts.dev/articles/vue-js-routing-from-scratch-using-cdn-without-cli)
6. [https://www.compilenrun.com/docs/framework/vue/vuejs-fundamentals/vuejs-cdn-setup/](https://www.compilenrun.com/docs/framework/vue/vuejs-fundamentals/vuejs-cdn-setup/)
7. [https://techformist.com/use-vue-from-url-simple-app/](https://techformist.com/use-vue-from-url-simple-app/)
8. [https://stackoverflow.com/questions/73256427/how-do-i-make-vue-app-load-its-js-files-and-css-files-from-a-cdn](https://stackoverflow.com/questions/73256427/how-do-i-make-vue-app-load-its-js-files-and-css-files-from-a-cdn)
9. [https://www.youtube.com/watch?v=MEgEcmutqxc](https://www.youtube.com/watch?v=MEgEcmutqxc)
10. [https://www.reddit.com/r/vuejs/comments/avdoop/whats_the_bestproper_way_to_write_a_vue_app_using/](https://www.reddit.com/r/vuejs/comments/avdoop/whats_the_bestproper_way_to_write_a_vue_app_using/)
11. [https://www.youtube.com/watch?v=j5bOIMhPqww](https://www.youtube.com/watch?v=j5bOIMhPqww)
12. [https://masteringjs.io/tutorials/vue/cdn](https://masteringjs.io/tutorials/vue/cdn)
13. [https://panjiachen.github.io/vue-element-admin-site/guide/advanced/cdn.html](https://panjiachen.github.io/vue-element-admin-site/guide/advanced/cdn.html)
14. [https://www.youtube.com/watch?v=b7CYM7F8LBE](https://www.youtube.com/watch?v=b7CYM7F8LBE)