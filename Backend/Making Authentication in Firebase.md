Here’s a comprehensive, step-by-step guide to creating a Firebase Authentication login page in a Vue SPA, including CSS styling, with detailed explanations of every function and object used.

---
## 1. **Firebase Setup**
**a. Add Firebase to Your Project**
- Install Firebase via npm:
    ```bash
    npm install firebase
	```
- In your `firebase.js` (or similar) file, initialize Firebase:
    ```    js
    import { initializeApp } from "firebase/app"; 
    import { getAuth } from "firebase/auth"; 
    
    // Your Firebase config from the Firebase Console 
    const firebaseConfig = {   apiKey: "...",  authDomain: "...",  projectId: "...",  // ...other config 
    }; 
    
    // Initialize Firebase 
    const app = initializeApp(firebaseConfig); 
    // Get the Auth service 
    export const auth = getAuth(app);
    ```
    - **initializeApp(config)**: Initializes your app with your Firebase project credentials.
    - **[[getAuth(app)]]**: Returns an Auth instance tied to your app, used for all authentication operations.

---

## 2. **Enable Email/Password Authentication in Firebase Console**

- Go to **Firebase Console > Authentication > Sign-in method**.
    
- Enable **Email/Password** and click Save[2](https://firebase.google.com/docs/auth/web/password-auth)[1](https://firebase.google.com/docs/auth/web/start).
    

---

## 3. **Create a Login Component**

## **Login.vue**
```vue
<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input
        type="email"
        v-model="email"
        placeholder="Email"
        required
      />
      <input
        type="password"
        v-model="password"
        placeholder="Password"
        required
      />
      <button type="submit">Login</button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { signInWithEmailAndPassword } from "firebase/auth"
import { auth } from '../firebase.js'

const email = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()

const login = async () => {
  error.value = ''
  try {
    // signInWithEmailAndPassword(auth, email, password): Authenticates user with Firebase
    await signInWithEmailAndPassword(auth, email.value, password.value)
    router.push('/dashboard') // Redirect on success
  } catch (err) {
    // err.code and err.message: Firebase Auth error details
    error.value = err.message
  }
}
</script>

<style scoped>
.login-container {
  max-width: 350px;
  margin: 80px auto;
  padding: 2rem;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}
.login-container h2 {
  margin-bottom: 1.5rem;
  color: #2c3e50;
}
.login-container input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}
.login-container button {
  width: 100%;
  padding: 0.75rem;
  background: #2c3e50;
  color: #fff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-transform: uppercase;
}
.login-container .error {
  color: #e74c3c;
  margin-top: 1rem;
  text-align: center;
}
</style>

```
---

## **Explanation of Key Functions and Objects**

- **ref()**: Vue’s Composition API function to create reactive variables (e.g., `email`, `password`, `error`).
    
- **useRouter()**: Vue Router function to programmatically navigate between routes.
    
- **signInWithEmailAndPassword(auth, email, password)**: Firebase Auth function that signs in a user with email and password. Returns a Promise that resolves with a `userCredential` object if successful, or throws an error if not.
- **auth**: The Firebase Auth instance you exported from your Firebase setup file.
- **`@submit.prevent="login"`**: Vue directive to call the `login` function when the form is submitted, preventing the default page reload.
- **v-model**: Two-way data binding for form input values and Vue state.
    
- **error.value**: Holds error messages to display to the user.
    

---

## 4. **Track Authentication State**

To keep your app in sync with the user's authentication status, use Firebase’s `onAuthStateChanged`:
```js
import { onAuthStateChanged } from "firebase/auth"; 
import { auth } from '../firebase.js'; 

onAuthStateChanged(auth, (user) => {   
	if (user) 
	{    
		// User is signed in    
		// user.uid, user.email, etc. are available  
	} 
	else 
	{    
		// User is signed out  
	} 
});
```
- **onAuthStateChanged(auth, callback)**: Attaches a listener that triggers whenever the user’s sign-in state changes (login, logout, etc.).

---

## 5. **CSS Styling**

- The provided CSS creates a centered, card-like login form with modern styling: rounded corners, subtle shadow, and clear input/button design.
    
- Adjust colors and spacing as needed for your brand or UI.
    

---

## 6. **Summary Table of Main Functions/Objects**

|Function/Object|Purpose|
|---|---|
|`initializeApp(firebaseConfig)`|Initializes Firebase app with your project config|
|`getAuth(app)`|Returns Auth instance for authentication|
|`signInWithEmailAndPassword(auth, ...)`|Authenticates user with email and password|
|`onAuthStateChanged(auth, callback)`|Listens for changes in authentication state|
|`ref()`|Creates reactive state in Vue|
|`useRouter()`|Allows navigation between Vue routes|
|`v-model`|Two-way data binding for form inputs|

---

## 7. **What Happens After Login?**

- On successful login, Firebase automatically persists the user session in local storage.
    
- The user’s information is available via the `user` object in the `onAuthStateChanged` callback or via `auth.currentUser`.
    
- You can use this state to conditionally render UI, protect routes, or fetch user-specific data[5](https://firebase.google.com/docs/auth)[4](https://softauthor.com/firebase-auth-onauthstatechange/)[1](https://firebase.google.com/docs/auth/web/start)[3](https://blog.logrocket.com/authentication-vue-3-firebase/).
    


# Making Authentication available for multi-page vue apps or multi-firebase instance project
When you have **multiple separate Vue apps** (not just multiple pages in a single Vue SPA), Firebase Authentication **does not automatically share authentication state between them**, even if they are on the same domain or subdomain. The default `local` persistence only works within the same app context (typically, the same JavaScript bundle and origin)

## Why Doesn't Auth State Persist Across Multiple Vue Apps?

- **Scope of Persistence:**  
    Firebase Auth's `local` persistence stores the session in browser storage (like `localStorage`), but the session is scoped to the app's Firebase configuration and bundle. If you have multiple, independently built Vue apps (each with their own Firebase initialization), they do not share the in-memory Firebase Auth instance—even if they use the same Firebase project and config[3](https://www.reddit.com/r/Firebase/comments/138n03u/sharing_firebase_authentication_across_multiple/)[1](https://firebase.google.com/docs/auth/web/auth-state-persistence).
    
- **Cross-App Limitations:**  
    When you load a new Vue app, it initializes a new Firebase App instance, which does not automatically recognize the user session from another app's instance, even if the browser's localStorage contains the session data. This is due to how the Firebase Auth SDK manages its internal state and listeners[3](https://www.reddit.com/r/Firebase/comments/138n03u/sharing_firebase_authentication_across_multiple/).
    

## How Can You Share Auth State Across Multiple Apps?

To **share authentication between multiple Vue apps** (for example, on different subdomains or as completely separate deployments), you need to implement a custom solution. The recommended approach is:

1. **Generate a Firebase Custom Token in the Original App:**
    
    - When a user is authenticated in App A, use a backend service (with Firebase Admin SDK) to generate a custom token for that user's UID[3](https://www.reddit.com/r/Firebase/comments/138n03u/sharing_firebase_authentication_across_multiple/).
        
2. **Pass the Custom Token to the Other App:**
    
    - Send this token securely to App B (e.g., via redirect, postMessage, or API call).
        
3. **Sign In with the Custom Token in the Other App:**
    
    - In App B, use `signInWithCustomToken()` from the Firebase Auth SDK to authenticate the user with the received token. This will establish the user's session in App B[3](https://www.reddit.com/r/Firebase/comments/138n03u/sharing_firebase_authentication_across_multiple/).
        

**Example:**

js

`// In App B import { getAuth, signInWithCustomToken } from "firebase/auth"; const auth = getAuth(); signInWithCustomToken(auth, tokenFromAppA)   .then((userCredential) => {    // User is now signed in to App B  })  .catch((error) => {    // Handle errors  });`

This process ensures that the user's authentication state is established in each app as needed, rather than relying on automatic session sharing[3](https://www.reddit.com/r/Firebase/comments/138n03u/sharing_firebase_authentication_across_multiple/).

## Summary Table

|Scenario|Default Firebase Auth Behavior|Solution for Shared Auth State|
|---|---|---|
|Multiple pages in one Vue SPA|Session is shared automatically|No extra work needed|
|Multiple, independently built Vue apps|Session is **not** shared automatically|Use custom token flow as described|
|Apps on different domains|Session is **not** shared automatically|Use backend-generated custom tokens|

---

**In summary:**  
Firebase Auth's built-in persistence does not automatically keep users logged in across multiple, separately-initialized Vue apps. To share authentication between them, you must implement a custom token exchange flow using the Firebase Admin SDK on your backend and `signInWithCustomToken()` on the client