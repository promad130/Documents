## What is HTTP?

**HTTP** (Hypertext Transfer Protocol) is the foundational protocol used for transferring data on the World Wide Web. It operates at the application layer of the Internet protocol suite and defines how messages are formatted and transmitted between web clients (such as browsers) and servers.

## How HTTP Works
- **Client-Server Model:** HTTP follows a client-server architecture. The client (usually a web browser) initiates a request for a resource (like a web page or image), and the server responds with the requested data.
- **Request-Response Cycle:** Communication occurs through discrete HTTP requests and responses. The client sends an HTTP request, and the server returns an HTTP response containing the requested resource or an error message.
- **Stateless Protocol:** HTTP is stateless, meaning each request is independent; the server does not retain information about previous requests. Any necessary state is managed using mechanisms like cookies.
- **Resource Types:** HTTP can transfer a variety of resource types, including HTML documents, images, videos, scripts, and data (such as JSON or XML).

## Key Features
- **Extensible and Evolving:** HTTP was first developed by Tim Berners-Lee at CERN in 1989 and has evolved through several versions (HTTP/1.0, HTTP/1.1, HTTP/2, and HTTP/3), each improving performance, reliability, and security.
- **Underlying Protocols:** Traditionally, HTTP runs over TCP/IP, but newer versions like HTTP/3 use QUIC, a protocol designed for faster and more reliable connections.
- **Secure Variant:** HTTPS (Hypertext Transfer Protocol Secure) is the encrypted version of HTTP, providing authentication and confidentiality through TLS (Transport Layer Security).

## Typical Use Cases
- Loading web pages and resources in browsers.
- Transmitting data between web applications and servers (e.g., via APIs).
- Enabling machine-to-machine communication and programmatic access to web services.

---
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
    - Headers (metadata)
    - Optional body (for methods like POST, containing user data)
    
- **Responses** include:
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
- Describe the body of the resource, such as its MIME type or encoding (e.g., `Content-Encoding`, `Content-Type`).

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

# Response-Request Cycle
The **request-response cycle** is a core concept in web development that describes how data is exchanged between a **client** (such as your web browser) and a **server** (the computer hosting the website or application).

**How the request-response cycle works:**

1. **Client Sends a Request:**  
    When you enter a URL in your browser and press Enter, your browser (the client) sends a **request** to the server. This request asks for specific information or resources, such as a web page, image, or data.
2. **Server Processes the Request:**  
    The server receives the request, processes it (which may involve looking up data, running code, or accessing a database), and prepares a **response**.
3. **Server Sends a Response:**  
    The server sends the response back to the client. This response includes the requested content (like an HTML page or image) and a **status code** indicating whether the request was successful (e.g., 200 OK) or if there was an error (e.g., 404 Not Found).
4. **Client Renders the Response:**  
    Your browser receives the response and renders it for you to see and interact with. If the page includes additional resources (like images or scripts), the browser may send more requests to fetch those.
5. **User Interaction Continues:**  
    Any further interaction (like clicking a link or submitting a form) starts the cycle again with a new request[4](https://pravinmd.hashnode.dev/request-response-cycle-explained).    


| Element      | Width | Height | X     | Y     |
| ------------ | ----- | ------ | ----- | ----- |
| Wreck        | 567.9 | 177.8  | 857.8 | 102.7 |
| Havoc        | 527.1 | 165    | 880.1 | 238.2 |
| Fuel the ... | 269.5 | 22.8   | 1009  | 391.8 |
| insert Coin  | 237.5 | 43.6   | 1025  | 546.9 |


for screen dimensions: 1366x768 px