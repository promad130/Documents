A **middleware function** is a function that operates within the request-response cycle of a web application, particularly in frameworks like Express.js and NestJS. It has access to the **request object** (`req`), the **response object** (`res`), and a **next function** (`next`) that passes control to the next middleware function in the stack[1](https://expressjs.com/en/guide/writing-middleware.html)[2](https://expressjs.com/en/guide/using-middleware.html)[3](https://docs.nestjs.com/middleware)[6](https://dev.to/semirteskeredzic/middleware-functions-1hpo).

**Key characteristics of middleware functions:**

- They can **execute any code** during the processing of a request.
    
- They can **modify the request and response objects**, such as adding properties or changing data.
    
- They can **end the request-response cycle** by sending a response to the client.
    
- They can **call the next middleware function** in the stack by invoking `next()`. If `next()` is not called and the response is not sent, the request will hang and eventually time out[1](https://expressjs.com/en/guide/writing-middleware.html)[2](https://expressjs.com/en/guide/using-middleware.html)[3](https://docs.nestjs.com/middleware)[6](https://dev.to/semirteskeredzic/middleware-functions-1hpo)[7](https://www.scaler.com/topics/nodejs/middleware-in-nodejs/).
    

**Common uses of middleware include:**

- Logging requests
    
- Authenticating users
    
- Parsing request bodies (e.g., JSON or URL-encoded data)
    
- Handling errors
    
- Enabling cross-origin resource sharing (CORS)
    

**Example (Express.js):**

javascript

``function loggerMiddleware(req, res, next) {   console.log(`Request received: ${req.method} ${req.url}`);  next(); // Pass control to the next middleware }``

This function logs details about each incoming request and then calls `next()` to continue processing[1](https://expressjs.com/en/guide/writing-middleware.html)[3](https://docs.nestjs.com/middleware).
