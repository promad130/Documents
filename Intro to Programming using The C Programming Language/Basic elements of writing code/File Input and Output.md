**Expected to know:**
**Topics Covered:**
**Tags:**

It wont be a stretch to say that this is one of the most important part of programming. 
Taking file input, reading, writing and changing it and giving it out as a output or making files is one of the most important aspects of programming, just like pointers, as these help us store stuff, change stuff or check for necessary conditions.

In the C Programming language, the standard library `stdio.h` has a struct called `FILE`.

We can read and write data in multiple file types like:
- .txt (text documents),
- .json (JSON Files),
- XML,
- Binary,
- Custom Formats,
- etc,.

In programming, file I/O happens as a stream.
![[Input&Output and placeholders#What is stream?]]

---
# 1) Text Documents:
They are just a stream of characters, ending with a special `<eof>`(End Of File) character.
![[Pasted image 20250419134407.png]]

---
## How to access a file?

Before accessing the file itself, lets have a look at the basic functions offered by the standard library in The C Programming language that helps in File I/O.

### Basic File Reading functions for File I/O in The C Programming Language:

The standard library offers all the basic function needed to work with a file:

- ### **fopen(FileName,mode)**;
	- Opens a file for code to access by returning the pointer to the starting point of a given file to be used for the given purpose.
	- Takes in the File Name string which can be the file name itself or more precise description of the file location by providing the complete path to the file
	- Takes in the mode in which file has to be opened. The basic modes are:
		- 'r'= read only, but the file must exist
		- 'w'= write the file. It creates the file if it doesn't exist, or truncates(clears everything) in the file if exists 
		- 'a' = append the file, i.e., write data at the end of file/ Creates it if doesn't exist.
	- If the mode cannot create a file and file does not exist, then it returns NULL pointer, which then can be used to check the existence of file like this:
```C
FILE* file_ptr = fopen("textDocument.txt",'r');
if(!file_ptr)
{
	return -1;
}
```
	
- ### **fgetc(file_ptr)**;
	- The `fgetc()` function in C reads a single character from a file at the current position indicated by the file pointer and then advances the file pointer to the next character. 
	- It returns the ASCII value of the character read as an `int`. 
	- If the end of the file is reached or an error occurs, `fgetc()` returns `EOF`
	**Example usage:**
```c
FILE *fptr = fopen("filename.txt", "r"); 
int c = fgetc(fptr); 
if (c != EOF) 
{     
	printf("%c\n", c); 
} 
fclose(fptr);
```
	- This code reads and prints one character from the file.
	
- ### **fclose(file_Ptr)**;
	- The `fclose()` function in C is used to close a file that was previously opened using functions like `fopen()`. 
	- When you call `fclose()` on a file pointer, it performs several important actions:
		- **Flushes Buffers**: Any unwritten data that is buffered is written to the file, and any unread buffered data is discarded.
		- **Closes the File**: The connection between your program and the file is terminated, and the file is properly closed at the operating system level.
	    - **Frees Resources**: Memory and system resources allocated for the file stream are released.
	    - **Invalidates the Pointer**: After calling `fclose()`, the file pointer should not be used again; doing so results in undefined behavior.
		
	- **Return Value:**
		- Returns `0` if the file was closed successfully.
		- Returns `EOF` (typically `-1`) if an error occurred while closing the file.
    
	**Example:**
```c
FILE *fptr = fopen("data.txt", "r"); 

// ... file operations ... 

if (fclose(fptr) != 0) 
{     
	perror("Error closing file"); 
}
```
In summary, `fclose()` ensures that all data is saved, resources are freed, and the file is safely closed, making it a crucial step in file handling in C.
	
### Using the file name:
In order to access a file,first we create a pointer/variable for the name of the file (Better to create a pointer, but why?at [[]]);
```C
char * fileName = "FileName.txt";
```
and then we use the name of the file to access it using `fopen(FileName,Mode);` function in C;
```C
FILE* file_Ptr = fopen(fileName,"mode");
```

The code would check for the `FileName.txt` in the directory where the code is being executed as no file path is given, or if a direct file path is given, then directly in that file path:
```c
char * fileName = "subdir(inside the current dir)/FileName.txt";
```
OR
```c
char * fileName = "/full/path/FileName.txt";
```

---
## How to Write to the file?
The functions provided by the standard library to write in the file by the C Programming language are:

- ### fputc() in C
	- The `fputc()` function in C is used to write a single character to a file at the current position indicated by the file pointer. 
	- After writing the character, the file pointer advances to the next position, so subsequent writes will occur after the character just written.
	- **Syntax**:
		- `int fputc(int character, FILE *stream);`
			- **character**: The character (as an `int`) to be written. Although the argument is of type `int`, only the lower 8 bits (the actual character) are used.
			- **stream**: A pointer to the `FILE` object that identifies the file to which the character will be written.
	
	 #### **How It Works:**
	- `fputc()` converts the supplied character to an unsigned char and writes it to the file at the current file pointer position.
	- If the file is opened in append mode, the character is always written at the end of the file, regardless of the current position.
	- After writing, the file pointer is advanced by one position, ready for the next write operation.
    
	 #### **Return Value:**
	- On success, `fputc()` returns the character written (as an unsigned char cast to int).
	- On failure (such as a write error or if the stream is not open for writing), it returns `EOF`.
    
    #### **Example**
```c
#include <stdio.h> 

int main() 
{     
	FILE *fptr = fopen("example.txt", "w");    
	if (fptr == NULL) 
	{        
		perror("Failed to open file");        
		return 1;    
	}     
	fputc('A', fptr); // Writes 'A' to the file    
	fputc('B', fptr); // Writes 'B' to the file     
	fclose(fptr);    
	return 0; 
}
```
	
---
# Some More basic functions:
Now lets have a look at some more basic function via an example.
Suppose we want to maintain Arya Stark's killing list, which is a `.txt` document like this:
```.txt
Joffrey, dead 
Cersei, alive 
Walder Frey, alive 
Meryn Trant, alive 
Tywin Lannister, dead 
The Red Woman, alive 
Beric Dondarrion, dead 
Thoros of Myr, alive 
Ilyn Payne, alive 
The Mountain, alive 
The Hound, alive
```

So we decide the following structure to write out a programme to maintain it:
- First get the file input
- Now maintain a struct for each person which contains:
	- Their Name
	- Their status(alive or dead)
- Have an array of these structs
- Create a function to read from file and store the data in this struct 
- Create a function to update the death status of a person and update it in the file
- Close the file

The code would go something like this:
```c
typedef struct 
{ 
	char *name; 
	int alive; 
} member_t;

int main() 
{ 
	// creating an array of people in the list
	member_t list[20]; 
	//accessing the file
	char fname[] = "arya_list.txt"; 
	FILE *fp = fopen(fname, "r"); 
	// if the file exists, then we read the data in the list array 
	//using read_list() function
	if (fp) 
		read_list(fp, list); 
	//closing the file which was opened with read-only access
	fclose(fp); 
	// updating Arya's list using kill_by_name() function
	kill_by_name(list, "Cersei"); 
	//opening the file with write permission 
	fp = fopen(fname, "w"); 
	
	//If the file exists, we edit it
	//using fprint_list() function
	if (fp) 
		fprint_list(fp, list); 
	//closing the file
	fclose(fp); 
	//giving the final list as an output in the terminal
	print_list(list); 
	return 0; 
}
```

Now lets discuss the functions used to edit Arya's list:
- ### read_list(file_ptr,list);
	- This function takes in the file's pointer and the list where the data of each person mentioned in the list is stored as the parameter. 
	- As the list is an array, it's pointer is passed, so it is passed by value.
	```c
void read_list(FILE *fp, member_t list[]) 
{ 
	const int MAX_LENGTH = 80; 
	char line[MAX_LENGTH]; 
	int i = 0; 
	while (fgets(line, MAX_LENGTH, fp) != NULL) 
	{ 
		int comma_index = find_index(line, ',');
		/*
		Here, find_index(char * string, char a); is a function created to find 
		the index of the given character 'a' in the given 'string'
		*/ 
		list[i].name = malloc((comma_index + 1) * sizeof(char)); 
		strncpy(list[i].name, line, comma_index); 
		list[i].alive = line[comma_index + 2] == 'a'; 
		++i; 
	} 
}
```
	
	- #### fgets(char * str, int n, FILE * stream)
		This `fgets()` function in C is used to read a string or a line of text from a file or input stream (such as the keyboard) and store it in a character array (buffer). 
		
		It reads up to a specified maximum number of characters, stopping when it encounters:
			- a newline character (`\n`), 
			- reaches the end-of-file (EOF), 
			- or has read the maximum number of characters minus one (to leave space for the null terminator)
		
		Returns NULL if it encounters an error
		
		SYNTAX:
		```c 
	char *fgets(char *str, int n, FILE *stream);
	```
		- `str`: Pointer to the buffer where the read string will be stored.
		- `n`: Maximum number of characters to read (including the null terminator).
	    - `stream`: The input stream to read from (e.g., a file pointer).
		
		**Behavior:**
			- Reads up to `n-1` characters and stores them in the buffer.
			- Stops reading when a newline character is found, the limit is reached, or EOF occurs.
			- The newline character, if read, is included in the buffer.
			- Appends a null terminator (`\0`) at the end of the string.
		    - Returns the pointer to the buffer on success, or `NULL` on error or if no characters are read due to EOF
	
- ### kill_by_name(member_t list[],char * name);
	- This function finds the name given as an argument in all the `member_t` variables defined in the given list, and then sets that name's status from alive to dead.
	```c
	void kill_by_name(member_t list[],char * name)
	{
		int i = 0;
		while(list[i].name ! = NULL && strcmp(list[i].name, name)! = 0)
		{
			i++;
		}
		list[i].alive = 0;
	}
```
	
- ### fprint_list(FILE * fd, member_t list[]);
	- This would copy our list into the file which we are to maintain.
	```c
	void fprint_list(FILE * fd, member_t list[])
	{
		int i = 0;
		while(list[i].name ! = NULL)
		{
			fprintf(fd, "%s, %s\n", list[i].name, list[i].alive ? "alive" : "dead");
			++i;
		}
	}
```
	
	- #### fprintf(FILE * fd, *what_to_print*);
		- This works just like printf(), but instead of printing to a console, it prints to a `FILE`.
	
- ### print_list(member_t list[]);
	- This would give the output of our list into the default location or the specified location!
	```c
	void print_list(member_t list[])
	{
		fprintlist(stdout,list);
	}
```
	- `stdout` is the *default output stream* of The C Programming language.

## Detailed Explanation of File I/O Functions in C

Below are detailed explanations, examples, and use-cases for the following C standard input/output functions: `fgets`, `fputs`, `gets`, `puts`, and `fscanf`.

**fgets()**

- **Purpose:** Reads a string from a file or standard input, up to a specified number of characters.
    
- **Syntax:**  
    `char *fgets(char *str, int n, FILE *stream);`
    
- **How it works:**  
    Reads up to `n-1` characters from the given stream (like a file or `stdin`) and stores them in `str`. Reading stops on a newline (`\n`), EOF, or after reading `n-1` characters. The resulting string is always null-terminated and includes the newline if read[6](https://www.geeksforgeeks.org/fgets-function-in-c/)[7](https://www.ibm.com/docs/en/i/7.4?topic=functions-fgets-read-string).
    
- **Return value:**  
    Returns `str` on success, or `NULL` on error or EOF.
    
- **Example:**
    
    c
    
    `char buff[100]; printf("Enter a string: "); fgets(buff, 100, stdin); printf("You entered: %s", buff);`
    
- **Use-case:**  
    Safely reading strings from user input or files, especially when you need to limit the number of characters to prevent buffer overflow[6](https://www.geeksforgeeks.org/fgets-function-in-c/)[7](https://www.ibm.com/docs/en/i/7.4?topic=functions-fgets-read-string)[1](https://www.digitalocean.com/community/tutorials/fgets-and-gets-in-c-programming).
    

**fputs()**

- **Purpose:** Writes a string to a file or output stream.
    
- **Syntax:**  
    `int fputs(const char *str, FILE *stream);`
    
- **How it works:**  
    Writes the string pointed to by `str` to the specified stream. Unlike `puts()`, it does not automatically append a newline character.
    
- **Return value:**  
    Returns a non-negative value on success, or `EOF` on error.
    
- **Example:**
    
    c
    
    `FILE *fp = fopen("output.txt", "w"); fputs("Hello, World!", fp); fclose(fp);`
    
- **Use-case:**  
    Writing strings to files without adding unwanted newlines, such as saving configuration data or logs[2](https://www.geeksforgeeks.org/php-fputs-function/).
    

**gets()**

- **Purpose:** Reads a line from standard input into a buffer.
    
- **Syntax:**  
    `char *gets(char *str);`
    
- **How it works:**  
    Reads characters from `stdin` until a newline or EOF is encountered, then replaces the newline with a null terminator. **Does not check buffer size**, which can lead to buffer overflows[1](https://www.digitalocean.com/community/tutorials/fgets-and-gets-in-c-programming)[3](https://www.shiksha.com/online-courses/articles/gets-and-puts-in-c/).
    
- **Return value:**  
    Returns `str` on success, or `NULL` on error or EOF.
    
- **Example:**
    
    c
    
    `char str[100]; printf("Enter a string: "); gets(str); printf("You entered: %s\n", str);`
    
- **Use-case:**  
    Historically used for simple input, but **should be avoided** due to security risks. Modern code should use `fgets()` instead[1](https://www.digitalocean.com/community/tutorials/fgets-and-gets-in-c-programming)[3](https://www.shiksha.com/online-courses/articles/gets-and-puts-in-c/).
    

**puts()**

- **Purpose:** Outputs a string to standard output, followed by a newline.
    
- **Syntax:**  
    `int puts(const char *str);`
    
- **How it works:**  
    Writes the string followed by a newline to `stdout` (usually the console)[4](https://www.w3schools.com/c/ref_stdio_puts.php).
    
- **Return value:**  
    Returns a non-negative value on success, or `EOF` on error.
    
- **Example:**
    
    c
    
    `puts("Hello, World!");`
    
- **Use-case:**  
    Simple way to print strings with automatic newline, useful for console output[4](https://www.w3schools.com/c/ref_stdio_puts.php).
    

**fscanf()**

- **Purpose:** Reads formatted data from a file.
    
- **Syntax:**  
    `int fscanf(FILE *stream, const char *format, ...);`
    
- **How it works:**  
    Reads data from the specified file stream according to the format string, similar to `scanf()` but for files. Useful for reading structured data like numbers and words[5](https://www.tutorialspoint.com/c_standard_library/c_function_fscanf.htm).
    
- **Return value:**  
    Returns the number of input items successfully matched and assigned, or `EOF` on error or end of file.
    
- **Example:**
    
    c
    
    `FILE *file = fopen("data.txt", "r"); int number; char word[50]; if (fscanf(file, "%d %49s", &number, word) == 2) {     printf("Number: %d, Word: %s\n", number, word); } fclose(file);`
    
- **Use-case:**  
    Reading structured records from files, such as CSV or log files[5](https://www.tutorialspoint.com/c_standard_library/c_function_fscanf.htm).
    

## Comparison Table

|Function|Input/Output|Stream/File|Adds Newline|Buffer Size Safe|Use-case Example|
|---|---|---|---|---|---|
|fgets|Input|Both|No|Yes|Reading lines from file/user|
|fputs|Output|Both|No|N/A|Writing strings to file|
|gets|Input|stdin|No|No (unsafe)|Simple input (deprecated)|
|puts|Output|stdout|Yes|N/A|Printing strings to console|
|fscanf|Input|Both|N/A|N/A|Reading formatted data|

## Summary

- **Use `fgets()` for safe string input.**
    
- **Use `fputs()` for writing strings to files.**
    
- **Avoid `gets()` due to security risks.**
    
- **Use `puts()` for console output with newline.**
    
- **Use `fscanf()` for reading formatted data from files.**
    
