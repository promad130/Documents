**Expected to know:** [[Introduction to Programming]] 
**Topics Covered:** scanf, printf, placeholder, format specifier
**Tags:** 

***(INCLUDE THE CONCEPT OF WHAT HAPPENS TO THE CHARACTERS IN THE STREAM WHEN WE USE INPUT AND OUTPUT OPERATIONS IN THE C PROGRAMMING LANGUAGE)***
# What and Why is Input/Output
One of the basic functions in every programming language is to take input from the user and present it to the user(output). This is the basic foundation of each and every programming language, and this is how a programme is made intractable and understandable.

Every language has their own syntax for input and output, which means each of them have their own identifier for input and output.

So lets go with these lines to satisfy the definition of Input and Output:
	- **Input** in programming languages refers to any data or signals received by a program from external sources, such as users, files, databases, sensors, or other systems. This data is necessary for a program to operate, perform computations, or make decisions
	- **Output** is the data or signals produced by a program and sent to external destinations, such as displaying information on a screen, writing to a file, sending data over a network, or providing results to other systems or users

---
# But how does it work?

Input/Output is commonly worked with by considering it a stream.
## What is stream?
Stream is an ordered sequence of data which flows and each data of that stream is loaded one by one in the memory continuously (i.e., one at a time) rather than loading it all at once. 

So, in more general terminology:
	A **stream** in programming is an abstraction representing a sequence of data elements made available over time, typically processed one at a time in a continuous flow rather than as a single, static batch. 
	Streams are fundamental for handling input and output (I/O) operations efficiently, especially when dealing with large or potentially infinite data sources.

You can think of a stream as a "pipeline" or "conveyor belt" through which data moves from a source to a destination.
Here, source is something from which the data is being supplied, and destination is where that data ends up eventually, may it be a variable in your programme or another file itself. Stream is just the flow of that ordered sequence of data.
### **Streams are lazy and pipelined processing!** 
Many stream APIs let you _pipe_ or _chain_ operations (e.g. filter, map, reduce) so each element is transformed on the fly without building an intermediate collection.

This “lazy” evaluation means you can express complex data workflows without ever holding the entire dataset in memory.
### **So can we say an array or string is an stream?**
A firm no! An array or string is not a stream as first, it is loaded all at once in a contiguous manner, so can access any element at any time.

Streams are _sequential_ and often _one‑way_. You consume each element in order, and once read, you typically can’t “go back.” They can represent potentially infinite or unbounded sources (e.g. live telemetry, user keystrokes, generator functions), whereas arrays/strings are always finite.

---
# ***Input/output in The C Programming Language:***

The *C Programming Language* has a standard library called `stdio.h` which provides the functions to perform Input/Output operations;
- # **Standard Input**(stdin): (Formatting needed)
	- In C, `stdin` stands for *"standard input"* and refers to the *default input stream* for a program, an abstract source of input.
	- It is typically connected to the keyboard of the device, allowing programs to read data entered by the user during execution.
	- Technically, `stdin` is a *constant pointer* (of type `FILE *`) that represents the standard input stream, and it is defined in the C standard library header `<stdio.h>`.
	- ## How it works:
		(details later, explain how it works from scratch, i.e., the keyboard input, what is that input interpreted as and then where it is stored, what happens upon giving multiple input, where is it stored, how does the stream access the buffer, and all)
		When a C program runs, it can read input from `stdin` using functions like `scanf()`, `gets()`, or `fgets()`. For example, `scanf()` reads formatted input from `stdin`, while `fgets()` can read a line of text from `stdin`.
		By default, `stdin` is connected to the keyboard, but it can be redirected to read from a file or another input device using operating system features (such as the `<` operator in Unix-like systems)
	- `stdin` can be redirected so that the program reads input from a file instead of the keyboard. For instance, running `./program < input.txt` in a Unix shell will make the program read from `input.txt` as if it were typed by the user.
	
	
- # **Standard output** (stdout):
	- **stdout** in C refers to the _standard output stream_, which is a default channel provided by the operating system for a program to send its output data. 
	- In most cases, this means displaying text or results to the terminal or console screen where the program is running
	- ## Properties:
		- **The type** is a global `FILE*`
		- **Default Behavior:** By default, anything written to `stdout` appears on the user's screen (console).
		- **Redirection:** The destination of `stdout` can be changed (redirected) by the operating system or shell. 
			For example, running a program with `> output.txt` will send all `stdout` output to the file `output.txt` instead of the screen.
	
- # **Standard error** (`stderr`): For error messages(cover later) 

All these (explain why FILE* as type, and also explain buffering)


However, there are other functions that are used more commonly than these, as they provide a room for easy specification I/O operations:
1) scanf();
	This takes in input according to the given format using placeholders/format specifiers.
	The syntax for this is:
```
scanf("format string", variable1, variable2, ...);
```


and output is given using the keyword:
```
`printf("format string", variable1, variable2, ...);`
```

- The **format string** contains **placeholders** (also called **format specifiers**) that determine how values are printed.
- The values to print are passed as additional arguments.

---
# **Placeholder and Format Specifier**

Placeholder and format specifier is a concept used synonymously by many, but are actually quite different and are important when it comes to getting a input or giving out output in a specific format of a specific data type that should not be violated.

## **Placeholders**

A **placeholder** is a temporary marker in a string or code that gets replaced with actual data at runtime. It serves as a **position holder** for values that will be inserted later.

### **General Definition**

- A **placeholder** represents where a value will be inserted.
- It does not define the type or format of the value (unless combined with a format specifier).
- It is commonly used in **formatted strings**, **templates**, and **variable substitution**.

### **Examples of Placeholders in Different Languages**

#### **1. C (printf and scanf)**
```c
int age = 18;
printf("I am %d years old.", age);
```
- Here, `%d` is a **format specifier** (specific type of placeholder) for an integer.
- It gets replaced with `age` (18) when the program runs.

#### **2. Python (.format() and f-strings)**
```python
name = "Alice"
print("Hello, {}!".format(name))  # "{}" is a placeholder
print(f"Hello, {name}!")  # "{}" in f-strings is also a placeholder
```
- `{}` is a **placeholder** that gets replaced with `name` (`Alice`).

#### **3. Java (String.format)**
```java
String name = "Bob";
System.out.println(String.format("Hello, %s!", name));
```
- `%s` is a placeholder for a string.

#### **4. HTML (Template Placeholders)**
```html
<input type="text" placeholder="Enter your name">
```
- The `placeholder` attribute in HTML gives a hint inside a text box but does not insert data dynamically.

| Placeholder                  | Meaning                                                       |
| ---------------------------- | ------------------------------------------------------------- |
| `{}` in Python               | General placeholder, can hold any data type(can be formatted) |
| `%d`, `%s`, `%f` in C, Java  | Format specifiers (act as placeholders with type formatting)  |
| `placeholder="text"` in HTML | UI hint, not dynamic replacement                              |

---
## **Understanding Placeholders and Format Specifiers**
Placeholders and format specifiers are related concepts but serve different roles in programming.
But let's first revisit placeholders:
A **placeholder** is a symbol or marker used in a string, template, or function to indicate where actual values will be inserted later.

### **Types of Placeholders**+
Placeholders come in different forms across programming languages.
### **A. Curly Braces `{}` (General Placeholders)**
- Found in Python, JavaScript, and templating languages.
- They do not specify a data type or format by themselves.

**Example in Python:**
```python
name = "Alice"
print("Hello, {}!".format(name))  # "{}" is the placeholder
```

**Example in JavaScript (Template Literals):*
```javascript
let name = "Bob";
console.log(`Hello, ${name}!`); // "${name}" is the placeholder
```

### **B. Percentage-Based Placeholders (`%` Format Specifiers)**
- Used in languages like C, Java, and older Python versions.
- These placeholders specify both position and format.

**Example in C:**
```c
int age = 20;
printf("I am %d years old.", age);  // %d is the placeholder for an integer
```

**Example in Python (old-style formatting):**
```python
print("Hello, %s!" % "Alice")  # %s is a placeholder for a string
```

### **C. Named Placeholders (`{name}`)**
- Used in Python `.format()`, Jinja templating, and some other languages.
- Allow for more readable and structured formatting.

**Example in Python:**
```python
print("Hello, {name}!".format(name="Charlie"))  # {name} is a named placeholder
```

### **D. Dollar Sign `$` Placeholders**

- Common in shell scripting and JavaScript.
- Used for variable substitution.

**Example in Bash:**

```bash
name="David"
echo "Hello, $name!"  # $name is a placeholder
```

**Example in JavaScript:**

```javascript
let age = 25;
console.log(`I am ${age} years old.`);  // ${age} is a placeholder
```

## **What is a Format Specifier?**

A **format specifier** is a special kind of placeholder that not only holds a value but also defines how it should be formatted (such as decimal places, alignment, etc.).
### **Common Format Specifiers in Different Languages**

| Format Specifier | Meaning                   |
| ---------------- | ------------------------- |
| `%d`             | Integer (C, Java, Python) |
| `%f`             | Floating-point number     |
| `%s`             | String                    |
| `%x`             | Hexadecimal integer       |
| `%o`             | Octal integer             |

### **Example of Format Specifiers in Different Languages**

#### **C:**

```c
float pi = 3.14159;
printf("Pi is approximately %.2f", pi);  // Output: Pi is approximately 3.14
```

#### **Python (`format()` and f-strings):**

```python
pi = 3.14159
print("Pi is {:.2f}".format(pi))  # Output: Pi is 3.14
print(f"Pi is {pi:.2f}")  # Using f-strings (modern Python)
```

---

## **3. Key Differences Between Placeholders and Format Specifiers**

|Feature|Placeholder|Format Specifier|
|---|---|---|
|**Definition**|A marker where a value will be inserted|Defines both the placeholder and how the value is formatted|
|**Does it format data?**|No, it just holds the position|Yes, it specifies how the data should be displayed|
|**Examples**|`{}` in Python, `$var` in Bash, `%s` in C|`%d` (integer), `%.2f` (float with 2 decimals), `{:.2f}` (Python format specifier)|
|**Scope**|Used in various languages for variable substitution|Primarily used in formatted output functions like `printf`, `.format()`, and f-strings|

### **Common Format Specifiers in C**

|Data Type|Format Specifier|Example (`printf`)|Example (`scanf`)|
|---|---|---|---|
|**Integer**|`%d`|`printf("%d", 42);`|`scanf("%d", &x);`|
|**Long Integer**|`%ld`|`printf("%ld", 100000L);`|`scanf("%ld", &y);`|
|**Unsigned Integer**|`%u`|`printf("%u", 30000);`|`scanf("%u", &z);`|
|**Float**|`%f`|`printf("%f", 3.14);`|`scanf("%f", &a);`|
|**Double**|`%lf`|`printf("%lf", 3.14159);`|`scanf("%lf", &b);`|
|**Character**|`%c`|`printf("%c", 'A');`|`scanf(" %c", &ch);`|
|**String**|`%s`|`printf("%s", "Hello");`|`scanf("%s", str);`|
|**Hexadecimal**|`%x`|`printf("%x", 255);`|`scanf("%x", &h);`|
|**Octal**|`%o`|`printf("%o", 255);`|`scanf("%o", &o);`|
### **Using those format specifier:**

as seen in:
#### **C:**
```c
float pi = 3.14159;
printf("Pi is approximately %.2f", pi);  // Output: Pi is approximately 3.14
```
We can use float/double format specifier to display as much digits we want after the decimal.
```c
#include <stdio.h>

int main()
{
	double d = 5.0;
	printf("%4.5lf",d);
}

//output:
>>>5.00000
```

## What does %x.ylf does?

**Formatted output in C**, where `x` and `y` are **constants (integers)** in the format specifier `%x.ylf`. Let's break it down.

### **General Format Specifier Syntax:**

```
%[flags][width][.precision][length]specifier
```

Now, let's analyze `%x.ylf`, where `x` and `y` are **integers**.

---

### **Breaking It Down:**

1. **`%x.ylf` structure** (assuming `x` and `y` are numbers):
    - **`%x`** → Field width (`x` defines the minimum number of characters to print).
    - **`.y`** → Precision (`y` defines the number of decimal places for a floating-point value).
    - **`lf`** → Specifies a `double` (or `long double` if `L` is used).

---

### **Example Interpretation:**

```c
#include <stdio.h>

int main() {
    double num = 123.456789;
    printf("%10.3lf\n", num);  // %10.3lf → Width = 10, Precision = 3
    return 0;
}
```

#### **Output:**

```
   123.457
```

- `10` → Reserves **10 character spaces** (including spaces and digits).
- `.3` → Prints **3 decimal places**.
- `lf` → Prints a **double**.

---

### **Key Takeaways:**

- `%x.ylf` → Prints a **floating-point number** (`lf` for double), with:
    - `x` → **Field width** (min number of characters printed).
    - `y` → **Precision** (number of decimal places).
- If `x` is **larger than the actual number's length**, spaces are added for padding.
- If `x` is **smaller**, the number prints normally.

## So %3.2lf for 56.232523 would give what?

Let's analyze **`%3.2lf`** for `56.232523` step by step:

### **Breaking it Down:**

- **`3` (Field Width)** → The minimum number of characters to be printed (not very relevant here unless padding is needed).
- **`.2` (Precision)** → Rounds the number to **2 decimal places**.
- **`lf` (Specifier)** → Specifies a `double`.

### **Applying it to `56.232523`**

- The number **`56.232523`** rounds to **`56.23`** (because of `.2` precision).
- Since **`56.23` already has 5 characters**, but the width is only **3**, it prints normally (no extra spaces needed).

### **Final Output:**

```
56.23
```

If the width (`x`) was **larger** than the number's length (e.g., `%6.2lf`), it would add **leading spaces**:

```
 56.23// (One space before)
```


```c
#include<stdio.h>

int main()
{
   double a = 56.235689;
    printf("The value of a is %8.2f\n", a);
   return 0;
}
//output:
>>>rubber_duck@pop-os:~/Desktop/Acads/Sem2/CP F111 (Prac + Theory)$ ./a.out 
>>>The value of a is    56.24
```

If you use **`%.lf`**, here's how it works:

### **Breaking it Down:**

- **`.` (Precision Indicator)** → Specifies the number of decimal places.
- **`lf` (Specifier)** → Prints a `double`.
- **No Number After `.`** → If the precision is missing, it is treated as **zero decimal places (`.0`)**.

### **Effect on a Number:**

Let's say you have:

```c
#include <stdio.h>

int main() {
    double num = 56.789;
    printf("%.lf\n", num);  // Equivalent to "%.0lf"
    return 0;
}
```

### **Output:**

```
57
```

- The number **`56.789`** is rounded to **`57`** (since `.lf` means **zero decimal places**).
- It **does not** show any fractional part.

---

### **Key Takeaways:**

- `%.lf` is the same as `%.0lf`.
- It rounds the number to the **nearest whole number**.
- No decimal places are printed.
