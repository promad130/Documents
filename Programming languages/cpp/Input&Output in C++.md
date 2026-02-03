**Expected to know:**
**Topics Covered:**
**Tags:** [[Input&Output and placeholders]]

# Input/Output in C++
C++ uses a powerful and flexible system called **streams** for input and output (I/O) operations. The most commonly used streams for console I/O are provided by the `<iostream>` standard library header.

---
## **Key Stream Objects**

| Object  | Purpose                                          |
| ------- | ------------------------------------------------ |
| `cin`   | Standard input (keyboard)                        |
| `cout`  | Standard output (screen/console)                 |
| `cerr`  | Standard error output (unbuffered, for errors)   |
| `clog`  | Standard log output (buffered, for logging/info) |
| `wcin`  | Wide-character input                             |
| `wcout` | Wide-character output                            |
| `wcerr` | Wide-character error output                      |
| `wclog` | Wide-character log output                        |
All these objects are defined in the `std` namespace and are available after including `<iostream>`.

---
## **Basic Output: `cout`**

- Used to display output on the screen.
- Uses the **insertion operator** `<<` to send data to the output stream.
	- **`<<` (Left Shift):** Shifts the bits of a number to the left by a specified number of positions. 
	- For example, `5 << 2` shifts the bits of 5 (binary `101`) two places left, resulting in `20` (binary `10100`).

So, it is used with output streams (like `std::cout`) to output data.
**Example:**
```cpp
#include <iostream> 
using namespace std; 

int main() 
{     
	cout << "Hello, world!" << endl;    
	int x = 42;    
	cout << "The value of x is: " << x << endl;    
	return 0; 
}
```
- Here, `<<` inserts the string into the output stream for display
- `endl` is a manipulator that inserts a newline and flushes the output buffer.
- You can also use `\n` for a newline.

---
## **Basic Input: 

### **`cin`

- Used to take input from the user (keyboard).
- Uses the **extraction operator** `>>` to extract data from the input stream.
	- **`>>` (Right Shift):** Shifts the bits of a number to the right by a specified number of positions. 
	- For example, `20 >> 2` results in `5`

The `>>` operator in `cin` **skips all leading whitespace characters** (spaces, tabs, and newlines) in the input stream before it starts extracting data for your variable. 
This is built into the behavior of the extraction operator.

The `cin` in C++ reads input from the standard input buffer, which is populated when the user presses **Enter**(i.e., `\n`).
`cin` stops at the `whitespace` or `\n` and **leaves them in the input stream**.

Hence, If you give `cin` this `abc(whitespace)\n` then `(whitespace)\n` is still in the input stream.
**Example:**
```cpp
#include <iostream> 
using namespace std; 

int main() 
{     
	int age;    
	cout << "Enter your age: ";    
	cin >> age;    
	cout << "Your age is " << age << endl;    
	return 0; 
}
```
- You can cascade input: `cin >> x >> y;` reads two values in sequence, i.e, `x` first, then `y`.

### **`getline(cin, string)`

The `getline()` function in C++ is used to read an entire line of text from an input stream (like `std::cin`) and store it in a string variable. Unlike `cin >>`, which stops reading at whitespace, `getline()` reads input until it encounters a specified delimiter (by default, the newline character `\n`), making it ideal for capturing multi-word or full-line input.

Syntax:
```cpp
#include <iostream>
#include <string>

std::string str;
std::getline(std::cin, str);           // Reads until '\n' by default
std::getline(std::cin, str, ',');      // Reads until ',' (custom delimiter)
```
- **First parameter:** Input stream (e.g., `std::cin`)
- **Second parameter:** String variable to store the input
- **Third parameter (optional):** Delimiter character (default is `\n`)

#### **How `getline()` Works**
- Reads characters from the input stream and appends them to the string variable.
- Stops reading when it encounters the delimiter (default: newline `\n`), which is **removed from the input stream** but **not stored** in the string

***This is why, if you mix `cin >>` and `getline`, you often get an empty string: the leftover `\n` is immediately consumed by `getline` before the user can enter new input.***

---
## **Multiple Inputs and Outputs**

You can chain multiple insertions or extractions:

```cpp
int a, b; 
cout << "Enter two numbers: "; 
cin >> a >> b; 
cout << "Sum: " << (a + b) << endl;
```
- Input and output can be combined in a single statement using chaining.

---
## **Error and Log Streams**

- `cerr` is used for error messages (unbuffered, appears immediately).
- `clog` is used for logging information (buffered, may appear with a delay).(more on this later as you learn, basically [here](Temp%20clog%20information) is what I found on perplexity)

---
## **Wide Character Streams**

- For Unicode or wide-character support, use `wcin`, `wcout`, `wcerr`, and `wclog`(more on this later as we apply and learn).

---
