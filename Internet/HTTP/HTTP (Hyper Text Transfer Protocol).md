For Detailed Explanation, refer to [this](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview) as well

## What is HTTP?

**HTTP** (Hypertext Transfer Protocol) is the foundational [protocol](https://developer.mozilla.org/en-US/docs/Glossary/Protocol) used for transferring data on the World Wide Web. It operates at the application layer of the Internet protocol suite and defines how messages are formatted and transmitted between web clients (such as browsers) and servers.

## How HTTP Works
- **Client-Server Model:** HTTP follows a client-server architecture. The client (usually a web browser) initiates a request for a resource (like a web page or image) called a **request**, and the server responds with the requested data called a **response**.

- **Request-Response Cycle:** Communication occurs through discrete HTTP requests and responses. The client sends an HTTP request, and the server returns an HTTP response containing the requested resource or an error message.

- **Stateless Protocol:** HTTP is stateless, meaning each request is independent; the server does not retain information about previous requests. Any necessary state is managed using mechanisms like cookies.

- **Resource Types:** HTTP can transfer a variety of resource types, including HTML documents, images, videos, scripts, and data (such as JSON or XML).


![[Pasted image 20260327222846.png]]


## Components of HTTP based System
HTTP is a client-server protocol: requests are sent by one entity, the user-agent (or a proxy on behalf of it). Most of the time the user-agent is a Web browser, but it can be anything, for example, a robot that crawls the Web to populate and maintain a search engine index.

Each individual request is sent to a server, which handles it and provides an answer called the _response_. Between the client and the server there are numerous entities, collectively called [proxies](https://developer.mozilla.org/en-US/docs/Glossary/Proxy_server), which perform different operations and act as gateways or [caches](https://developer.mozilla.org/en-US/docs/Glossary/Cache), for example.

![[client-server-chain.svg|717]]

In reality, there are more computers between a browser and the server handling the request: there are routers, modems, and more. Thanks to the layered design of the Web, these are hidden in the network and transport layers. HTTP is on top, at the application layer. Although important for diagnosing network problems, the underlying layers are mostly irrelevant to the description of HTTP.

### Proxies
Between the Web browser and the server, numerous computers and machines relay the HTTP messages. Due to the layered structure of the Web stack, most of these operate at the transport, network or physical levels, becoming transparent at the HTTP layer and potentially having a significant impact on performance. Those operating at the application layers are generally called **proxies**. 

These can be transparent, forwarding on the requests they receive without altering them in any way, or non-transparent, in which case they will change the request in some way before passing it along to the server. 

Proxies may perform numerous functions:

- caching (the cache can be public or private, like the browser cache)
- filtering (like an antivirus scan or parental controls)
- load balancing (to allow multiple servers to serve different requests)
- authentication (to control access to different resources)
- logging (allowing the storage of historical information)

Next up, check out [[HTTP Flow]]

# What is HTTPS?
**HTTPS** stands for _Hypertext Transfer Protocol Secure_. It is the secure version of HTTP, the foundational protocol used for data communication on the web. HTTPS enhances HTTP by adding a layer of security through encryption, ensuring that data transmitted between a user's browser and a website's server is protected from interception and tampering.

## How Does HTTPS Work?
- **Encryption:** HTTPS uses SSL (Secure Sockets Layer) or its successor TLS (Transport Layer Security) to encrypt data. This means any information sent or received is scrambled, making it unreadable to eavesdroppers.
- **Authentication:** Websites using HTTPS must obtain a digital certificate (SSL/TLS certificate) from a trusted Certificate Authority (CA). This certificate verifies the website's identity, helping users ensure they are connecting to the legitimate site.
- **Port:** HTTPS operates over port 443 by default, while HTTP uses port 80.
- **URL Prefix:** Websites using HTTPS have URLs that begin with `https://`, and browsers typically display a padlock icon to indicate a secure connection.

## Key Benefits of HTTPS
- **Data Security:** All data exchanged is encrypted, protecting sensitive information (such as passwords, credit card details, and personal data) from hackers.
- **Data Integrity:** HTTPS helps ensure that the data sent and received cannot be altered or corrupted during transfer without being detected.
- **Authentication:** The SSL/TLS certificate confirms the website's identity, reducing the risk of man-in-the-middle attacks.
- **SEO Advantages:** Search engines like Google give preference to HTTPS websites, potentially improving search rankings.

## Comparison Table: HTTP vs. HTTPS

|Feature|HTTP|HTTPS|
|---|---|---|
|Security|None; data sent as plaintext|Encrypted with SSL/TLS|
|Port|80|443|
|Certificate|Not required|SSL/TLS certificate required|
|URL Prefix|http://|https://|
|Data Integrity|Vulnerable to tampering|Protected|
|Authentication|No identity verification|Verifies server identity|
|SEO Impact|No ranking benefit|Preferred by search engines|

---
# What Kind of Information is Sent Over HTTP/HTTPS?

![[HTTP Flow]]

When using HTTP or HTTPS, a wide range of information can be transmitted between a client (like a web browser) and a server. This information is exchanged through structured messages called **requests** (from client to server) and **responses** (from server to client).

## Types of Information Sent
**1. Resource Data**
- HTML documents (web pages)
- Images (JPEG, PNG, GIF, etc.)
- Videos and audio files
- Scripts (JavaScript)
- Stylesheets (CSS)
- Data files (JSON, XML, CSV, etc.)

**2. User Input and Form Data**
- Login credentials (usernames, passwords)
- Search queries
- Form submissions (contact forms, survey responses)
- File uploads

**3. Metadata (Headers)**
- Information about the browser or client (User-Agent)
- Cookies (session data, preferences)
- Content type and encoding
- Caching instructions
- Authentication tokens

**4. Request Methods**
- GET: Requests data from the server (parameters sent in the URL) 
- POST: Submits data to the server (data sent in the request body)
- PUT: Update data already on the server
- DELETE: Delete data from the server
- etc.:

**5. Status and Control Information**
- HTTP status codes (e.g., 200 OK, 404 Not Found)
- Error messages or redirects
- Security policies (e.g., Content-Security-Policy)

## Examples
- When you visit a web page, your browser sends a GET request, and the server responds with the HTML, images, and scripts needed to display the page.
- When you submit a login form, your browser sends a POST request containing your username and password in the request body.
- When you upload a file, the file data is sent in the body of a POST request.

## Security Note
- With **HTTP**, all information is sent in plain text and can be intercepted.
- With **HTTPS**, all information (including sensitive data like passwords) is encrypted for privacy and security.

---
# How is the Information Structured?
- **Requests** include:
    - HTTP method (GET, POST, etc.)
    - URL/path of the resource
    - Protocol
    - Headers (metadata)
    - Optional body (for methods like POST, containing user data)
    
- **Responses** include:
	- Protocol and its version
    - Status code and message
    - Headers (metadata about the response)
    - Optional body (the requested resource or data))

---
## HTTP Headers
**HTTP headers** are key-value pairs sent as part of HTTP requests and responses, allowing the client and server to pass additional information beyond the main content. They play a crucial role in defining how the communication should be handled, providing metadata, instructions, and context for the transferred data.

### Structure and Syntax
- In HTTP/1.x, headers are case-insensitive names followed by a colon and their value (e.g., `Content-Type: text/html`).
- In HTTP/2 and above, headers are typically displayed in lowercase and may include pseudo-headers (e.g., `:status: 200`).

### Types of HTTP Headers
HTTP headers can be grouped based on their context and function
#### **1. Request Headers**
- Sent by the client (browser or API client) to the server.
- Provide information about the client, its preferences, and the requested resource.
- Common examples:
    - `Accept`: Media types the client can process (e.g., `Accept: application/json`)
    - `User-Agent`: Information about the client software (e.g., browser type)
    - `Authorization`: Credentials for authentication
    - `Host`: Domain name of the server
    - `Cookie`: Previously stored cookies

#### **2. Response Headers**
- Sent by the server back to the client in response to a request.
- Provide metadata about the response and instructions for handling it.
- Common examples:
    - `Content-Type`: Type of data being sent (e.g., `text/html`, `application/json`)
    - `Set-Cookie`: Instructs the client to store cookies
    - `Cache-Control`: Caching directives
    - `Server`: Information about the server software

#### **3. Representation Headers**
- Contain information about the body of the resource, like its [MIME type](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/MIME_types), or encoding/compression applied.

#### **4. Payload Headers**
- Provide information about the payload data, independent of representation (e.g., `Content-Length`, `Transfer-Encoding`).

### End-to-End vs. Hop-by-Hop Headers
- **End-to-End Headers:** Must be transmitted to the final recipient (server or client). Proxies and caches must forward these headers unmodified.
- **Hop-by-Hop Headers:** Only relevant for a single transport-level connection and must not be retransmitted by proxies or cached. Only hop-by-hop headers may be set using the `Connection` header.

### Examples
**Request Header Example:**
```text
GET /index.html HTTP/1.1 
Host: www.example.com 
Accept: text/html 
User-Agent: Mozilla/5.0`
```
**Response Header Example:**
```text
HTTP/1.1 200 OK 
Content-Type: text/html; charset=utf-8 
Cache-Control: max-age=3600 
Set-Cookie: sessionId=abc123; Path=/ 
Server: Apache
```
### Custom Headers
- Historically, custom headers used the `X-` prefix (e.g., `X-Requested-With`), but this practice is now deprecated.

---
# HTTP Request Methods
HTTP defines a set of **request methods** to indicate the purpose of the request and what is expected if the request is successful. Although they can also be nouns, these request methods are sometimes referred to as _HTTP verbs_. Each request method has its own semantics, but some characteristics are shared across multiple methods, specifically request methods can be [safe](https://developer.mozilla.org/en-US/docs/Glossary/Safe/HTTP), [idempotent](https://developer.mozilla.org/en-US/docs/Glossary/Idempotent), or [cacheable](https://developer.mozilla.org/en-US/docs/Glossary/Cacheable).

- [`GET`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/GET)
	
	The `GET` method requests a representation of the specified resource. Requests using `GET` should only retrieve data and should not contain a request [content](https://developer.mozilla.org/en-US/docs/Glossary/HTTP_Content).
	
- [`HEAD`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/HEAD)
	
	The `HEAD` method asks for a response identical to a `GET` request, but without a response body.
	
- [`POST`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/POST)
	
	The `POST` method submits an entity to the specified resource, often causing a change in state or side effects on the server.
	
- [`PUT`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/PUT)
	
	The `PUT` method replaces all current representations of the target resource with the request [content](https://developer.mozilla.org/en-US/docs/Glossary/HTTP_Content).
	
- [`DELETE`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/DELETE)
	
	The `DELETE` method deletes the specified resource.
	
- [`CONNECT`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/CONNECT)
	
	The `CONNECT` method establishes a tunnel to the server identified by the target resource.
	
- [`OPTIONS`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/OPTIONS)
	
	The `OPTIONS` method describes the communication options for the target resource.
	
- [`TRACE`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/TRACE)
	
	The `TRACE` method performs a message loop-back test along the path to the target resource.
	
- [`PATCH`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/PATCH)
	
	The `PATCH` method applies partial modifications to a resource.


## Safe, idempotent, and cacheable request methods

The following table lists HTTP request methods and their categorization in terms of safety, cacheability, and idempotency.

| Method                                                                                   | Safe | Idempotent | Cacheable    |
| ---------------------------------------------------------------------------------------- | ---- | ---------- | ------------ |
| [`GET`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/GET)         | Yes  | Yes        | Yes          |
| [`HEAD`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/HEAD)       | Yes  | Yes        | Yes          |
| [`OPTIONS`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/OPTIONS) | Yes  | Yes        | No           |
| [`TRACE`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/TRACE)     | Yes  | Yes        | No           |
| [`PUT`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/PUT)         | No   | Yes        | No           |
| [`DELETE`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/DELETE)   | No   | Yes        | No           |
| [`POST`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/POST)       | No   | No         | Conditional* |
| [`PATCH`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/PATCH)     | No   | No         | Conditional* |
| [`CONNECT`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/CONNECT) | No   | No         | No           |

* `POST` and `PATCH` are cacheable when responses explicitly include [freshness](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Caching) information and a matching [`Content-Location`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Content-Location) header.

## Specifications

| Specification                                                                  |
| ------------------------------------------------------------------------------ |
| [HTTP Semantics  <br># OPTIONS](https://httpwg.org/specs/rfc9110.html#OPTIONS) |
| [HTTP Semantics  <br># POST](https://httpwg.org/specs/rfc9110.html#POST)       |
| [HTTP Semantics  <br># GET](https://httpwg.org/specs/rfc9110.html#GET)         |
| [HTTP Semantics  <br># DELETE](https://httpwg.org/specs/rfc9110.html#DELETE)   |
| [HTTP Semantics  <br># HEAD](https://httpwg.org/specs/rfc9110.html#HEAD)       |
| [HTTP Semantics  <br># CONNECT](https://httpwg.org/specs/rfc9110.html#CONNECT) |
| [HTTP Semantics  <br># PUT](https://httpwg.org/specs/rfc9110.html#PUT)         |

## Browser compatibility

| desktop                                                                                  |        |      |         |       | mobile |                |                     |               |               |                  |                 |                |
| ---------------------------------------------------------------------------------------- | ------ | ---- | ------- | ----- | ------ | -------------- | ------------------- | ------------- | ------------- | ---------------- | --------------- | -------------- |
|                                                                                          | Chrome | Edge | Firefox | Opera | Safari | Chrome Android | Firefox for Android | Opera Android | Safari on iOS | Samsung Internet | WebView Android | WebView on iOS |
| [`CONNECT`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/CONNECT) | 1      | 12   | 1       | 15    | 1      | 18             | 4                   | 14            | 1             | 1                | 4.4             | 1              |
| [`DELETE`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/DELETE)   | 1      | 12   | 1       | 15    | 1      | 18             | 4                   | 14            | 1             | 1                | 4.4             | 1              |
| [`GET`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/GET)         | 1      | 12   | 1       | 2     | 1      | 18             | 4                   | 10.1          | 1             | 1                | 1               | 1              |
| [`HEAD`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/HEAD)       | 1      | 12   | 1       | 15    | 1      | 18             | 4                   | 14            | 1             | 1                | 4.4             | 1              |
| [`OPTIONS`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/OPTIONS) | 1      | 12   | 1       | 15    | 1      | 18             | 4                   | 14            | 1             | 1                | 4.4             | 1              |
| [`POST`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/POST)       | 1      | 12   | 1       | 15    | 1      | 18             | 4                   | 14            | 1             | 1                | 4.4             | 1              |
| [`PUT`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/PUT)         | 1      | 12   | 1       | 15    | 1      | 18             | 4                   | 14            | 1             | 1                | 4.4             | 1              |
The cells are the versions in which the compatibility was introduced


# HTTP Status Codes:
HTTP status codes are three-digit numbers returned by a server in response to a client's request, indicating the result of the request and guiding the client on what to do next. These codes are grouped into five classes, determined by the first digit of the code:

## Categories of HTTP Status Codes

| Code Range | Category      | Description                                                                |
| ---------- | ------------- | -------------------------------------------------------------------------- |
| 1xx        | Informational | The request was received and is being processed; not a final response.     |
| 2xx        | Successful    | The request was successfully received, understood, and accepted.           |
| 3xx        | Redirection   | Further action is needed to complete the request (e.g., follow a new URL). |
| 4xx        | Client Error  | The request contains errors or cannot be fulfilled due to client issues.   |
| 5xx        | Server Error  | The server failed to fulfill a valid request due to server-side problems.  |

---
## Common HTTP Status Codes
**1xx – Informational**
- 100 Continue: The server has received the request headers and the client should proceed to send the body.
- 101 Switching Protocols: The server is switching protocols as requested by the client.

**2xx – Successful**
- 200 OK: The request was successful, and the server returned the requested resource.
- 201 Created: The request succeeded and a new resource was created.
- 204 No Content: The request succeeded, but there's no content to send in the response.

**3xx – Redirection**
- 301 Moved Permanently: The resource has been permanently moved to a new URL.
- 302 Found: The resource is temporarily located at a different URL.
- 304 Not Modified: The resource has not been modified since the last request.

**4xx – Client Error**
- 400 Bad Request: The request was malformed or invalid.
- 401 Unauthorized: Authentication is required and has failed or not been provided.
- 403 Forbidden: The server understands the request but refuses to authorize it.
- 404 Not Found: The requested resource could not be found on the server.

**5xx – Server Error**
- 500 Internal Server Error: The server encountered an unexpected condition.
- 502 Bad Gateway: The server received an invalid response from an upstream server.
- 503 Service Unavailable: The server is currently unable to handle the request (overload or maintenance).

---
## How to Interpret Status Codes
- The first digit tells you the general class of the response.
- The last two digits provide more specific information about the outcome.    
- Codes outside the standard ranges may be non-standard or custom to specific server software.

---
## Some common Examples:
- `200`: OK
- `201`: OK created
- `301`: Moved to a new URL
- `304`: Not Modified (cached version)
- `400`: Bad request
- `401`: Unauthorized
- `404`: Not found
- `500`: Internal server error 

