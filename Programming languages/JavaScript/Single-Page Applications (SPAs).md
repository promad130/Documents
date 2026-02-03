A single-page application (SPA) is a type of web application or website that loads a single HTML page and then dynamically updates the content of that page in response to user interactions, without requiring a full page reload from the server. Instead of navigating to entirely new pages, the application rewrites parts of the existing page using JavaScript, often fetching new data as needed via APIs or AJAX calls.

**How SPAs Work:**
- When you first visit an SPA, the browser loads the core HTML, CSS, and JavaScript needed for the app in one go.
- As you interact (click links, submit forms, etc.), the app updates the visible content by modifying the page dynamically, often requesting only the necessary data from the server (usually in JSON format).
- This results in faster, smoother transitions and a more app-like, seamless user experience, since there are no disruptive page reloads.

**Examples of SPAs:**  
Popular SPAs include Gmail, Facebook, Google Maps, Twitter, Netflix, and GitHub.

**Benefits:**
- Faster and smoother navigation, similar to desktop apps
- Reduced server load and network traffic, as only data (not full pages) are transferred after the initial load
- Enhanced user experience with real-time updates and interactions

**Trade-offs:**
- More complex client-side code
- SEO (search engine optimization) can be more challenging compared to traditional multi-page sites
- Initial load time may be longer, as more resources are loaded up front
