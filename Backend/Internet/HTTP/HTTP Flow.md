Now that we know the basics of how HTTP works, and some basic terminology, what happens when a client wants to communicate with the server?

When a client wants to communicate with a server, either the final server or an intermediate proxy, it performs the following steps:

1. **Open a TCP connection**: The TCP connection is used to send a request, or several, and receive an answer. The client may open a new connection, reuse an existing connection, or open several TCP connections to the servers.
    
2. **Send an HTTP message:** HTTP messages (before HTTP/2) are human-readable. With HTTP/2, these messages are encapsulated in frames, making them impossible to read directly, but the principle remains the same. For example:
    
```http
GET / HTTP/1.1
Host: developer.mozilla.org
Accept-Language: fr
```
	
3. **Read the response sent by the server**, such as:
    
``` http
HTTP/1.1 200 OK
Date: Sat, 09 Oct 2010 14:28:02 GMT
Server: Apache
Last-Modified: Tue, 01 Dec 2009 20:18:22 GMT
ETag: "51142bc1-7449-479b075b2891b"
Accept-Ranges: bytes
Content-Length: 29769
Content-Type: text/html

<!doctype html>… (here come the 29769 bytes of the requested web page)
```
    
4. **Close or reuse the connection for further requests.**

If HTTP pipelining is activated, several requests can be sent without waiting for the first response to be fully received. HTTP pipelining has proven difficult to implement in existing networks, where old pieces of software coexist with modern versions. HTTP pipelining has been superseded in HTTP/2 with more robust multiplexing requests within a frame.

# HTTP MESSAGE

**HTTP messages** are the mechanism used to exchange data between a server and a client in the HTTP protocol. There are two types of messages: **requests** sent by the client to trigger an action on the server, and **responses**, the answer that the server sends in response to a request.

Developers rarely, if ever, build HTTP messages from scratch. Applications such as a browser, proxy, or web server use software designed to create HTTP messages in a reliable and efficient way. How messages are created or transformed is controlled via APIs in browsers, configuration files for proxies or servers, or other interfaces.

In HTTP protocol versions up to HTTP/2, messages are text-based, and are relatively straightforward to read and understand after you've familiarized yourself with the format. In HTTP/2, messages are wrapped in binary framing, which makes them slightly harder to read. However the underlying semantics of the protocol are the same, so you can learn the structure and meaning of HTTP messages based on the text-based format of HTTP/1.x messages, and apply this understanding to HTTP/2 and beyond.

![[http-message-anatomy.svg]]

There are two types of HTTP messages, requests and responses, each with its own format.

### [Requests](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview#requests)

An example HTTP request:

![[http-request.svg|717]]

Requests consist of the following elements:

- An HTTP [method](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods), usually a verb like [`GET`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/GET), [`POST`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/POST), or a noun like [`OPTIONS`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/OPTIONS) or [`HEAD`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/HEAD) that defines the operation the client wants to perform. Typically, a client wants to fetch a resource (using `GET`) or post the value of an [HTML form](https://developer.mozilla.org/en-US/docs/Learn_web_development/Extensions/Forms) (using `POST`), though more operations may be needed in other cases.
- The path of the resource to fetch; the URL of the resource stripped from elements that are obvious from the context, for example without the [protocol](https://developer.mozilla.org/en-US/docs/Glossary/Protocol) (`http://`), the [domain](https://developer.mozilla.org/en-US/docs/Glossary/Domain) (here, `developer.mozilla.org`), or the TCP [port](https://developer.mozilla.org/en-US/docs/Glossary/Port) (here, `80`).
- The version of the HTTP protocol.
- Optional [headers](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers) that convey additional information for the servers.
- A body, for some methods like `POST`, similar to those in responses, which contain the resource sent.

### [Responses](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview#responses)

An example response:

![[http-response.svg|717]]

Responses consist of the following elements:

- **The version** of the HTTP protocol they follow.
- A [status code](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status), indicating if the request was successful or not, and why.
- A **status message**, a non-authoritative short description of the status code.
- HTTP [headers](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers), like those for requests.
- Optionally, a body containing the fetched resource.

![[http-message-anatomy.svg]]