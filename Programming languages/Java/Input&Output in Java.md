**Expected to know:**
**Topics Covered:**
**Tags:** [[Input&Output and placeholders]]

In java, basic input and output is handled by the System class. First lets have a look at the **console-based I/O:**

---
# Basic Input Functions
## Output
In java, output is pretty simple, and is controlled by `out` static method of the `System` class:
```java
System.out
```
There are two main basic function for output:
- `System.out.print`:
	This is like `Console.Write()` from C#, i.e., it prints whatever is passed but does not add a new-line character at the end of the output.
- `System.out.println`:
	This is like `Console.WriteLine()` from C#, it prints whatever is passed and adds a newline character at the end.
Both of these methods are overloaded to support all the primitive types of java, i.e., if int, long, bool, or objects are given, then the compiler implicitly called `toString()` typecast for the given input.
For Example:
```java
public static void main(String[] args)
{
	System.out.println(3+4);
}
//Output: 34
```

## Input
For input reading, there is a static method called `in` in `System` class, but it is generally avoided. Input stream is read as it is, i.e., it reads raw bytes entered in by the given input hardware and is not convenient for us to use. It is low level.

And converting it from raw information to a human readable form requires converting it by yourself, which can be tricky and error prone, hence we use libraries and modules to get the input converted into a usable form.

Hence we have the following libraries used to take in the basic input from the user in java:
- **Scanner**
	- Scanner is a high-level java utility class under `java.util` which helps to take in user input.
	- It **wraps** `System.in`, converting raw keyboard input into useful data types, like `int`, `double`, and `String`.
	- **But why can't Scanner be used to read binary files then?**
	How to use:
	```java
	import java.util.Scanner;
	
	Scanner sc = new Scanner(System.in);
	<DataType> <variableIdentifier> = sc.<methodToUse>;
```
	It Offers the following functions:
	- `.nextInt();`
		- Reads the input and converts to int. If the given input is not of suitable type, then it throws the error: **InputMismatchException**
	- `.nextDouble();`
		- Reads the input and converts to int. If the given input is not of suitable type, then it throws the error: **InputMismatchException**
	- `.nextLine();`
		- Reads the entire line of text, i.e., until it finds a newline character.
	- Like this, it supports all the primitive data types:
		- `nextLong()`
		- `nextFloat()`
		- `nextBoolean()`
		- `nextByte()`
		- `nextShort()`
		- `next()`: 
			- Takes in the next word as input
			- **skips leading whitespace**, then reads until the next whitespace (space, tab, newline).
			- It does **not** read beyond a whitespace (so only one word/number at a time).
- **InputStreamReader**
	- **InputStreamReader** is a bridge between byte streams and character streams.
	- It takes an **InputStream** (like `System.in`) and _converts_ raw bytes into characters using a character encoding.
	- It’s frequently used to wrap `System.in` (or file streams) so they can be used with classes like `BufferedReader`.
	```java
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	```
	- InputStreamReader turns `System.in`’s bytes into characters.
	- BufferedReader gives you convenient and efficient line/char reading.
	
- **BufferedReader**
	- **BufferedReader** is an advanced class for **efficient, line-wise** and **character-wise** input.
	- It’s often used when you want to read complete lines or handle more advanced text processing.
	- Internally, it **buffers** input to make reading faster and more efficient—less system calls!
	```java
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String line = br.readLine();
	```
	The functions it offers:
	- `br.readLine()` reads a whole line of text at once.
	- `read()`: Reads a single character, returns int (Unicode codepoint or -1 at EOF)
	When to use:
	- When you want to read multi-line text, process files, or need efficient character/line input.
	- It allows you to buffer input and move back and forth with mark/reset (advanced usage).
	- Common in file processing, but can be used for console input too.
---
# More functions