**Expected to know:** [[Arrays]] [[Data Types and Constants]]
**Topics Covered:** strings, strings in C, string library in C
**Tags:**

So far we have only looked at a single character. But what if I want to store a collection of them? Most logical answer would be to create an array which will store those characters for us.
This array of characters which store a bunch of char is what we call **"String"**.
The way the string is stores is rather language specific.

---
# How to create a String in C:

Suppose we want to store "bits goa" as a variable, so we declare the string/character array as:
```c
char string[12] = "bits goa";
```

This stores the Hello World as:
![[Pasted image 20250227061758.png]]

where:
- **\0** is called a string terminating NULL character. This tells the compiler that that is the end of string.
So upon modifying it as:
```c
string[2] = '\0';
```

The compiler will think of the string as:
```c
string = "bi"
```

---
# Working with strings in C

### String PlaceHolder
Now the question is how will we print the string? Surely we don't want to use a For Loop everytime, so there is a special placeholder for string data type:
```c
%s
```
so;
```c
printf("%s\n",string);
//output;
bi
```

### Using scanf with strings
Upon using scanf for strings, it will take input until it encounters a whitespace, and ignores any leading whitespaces.
But what specifier to use? 
1. `%[^\n]s`: This is a scanset specifier that reads all characters until it encounters a newline character (`\n`)[1](https://stackoverflow.com/questions/54748441/what-is-the-difference-between-n-and-ns)[2](https://www.sololearn.com/en/Discuss/2810449/what-does-n-mean-when-attach-to-scanf-like-scanf-n-s). The `^` symbol negates the set, meaning it will read any character except for `\n`.
2. `%s` will take input until it encounters a white space ignoring all the leading whitespaces. 

## String Library in C;

The C Programming language provides a library called string.h which helps working with string more easily.

### Using strlen() function
Now how to get the length of string? for that there is a function called "strlen()".
so:
```c
#include <string.h>
int main(){
char institute[20] = "bits goa"; 
printf("%d\n" , strlen(institute));
return 0;}
```

This function's return type is not int, but we can ignore any errors thrown y compiler for now.
So the output would be 8, remember that:
$$
\text{length of array} \neq \text{length of string}
$$
String ends at the NULL character \0.

### Using strcpy(string_1,string_2)
```c
//To copy one string to another - strcpy(destination,source)
	char str2[10];
	strcpy(str2,str);
	printf("str = %s, str2 = %s\n", str, str2);
```

### Using strcat(string1,string2)
```c
//To contatenate one string with another - strcat(string1,string2)
	//Similar to (string1 = string1 + string2)
	char str3[20] = "ABcDE";
	strcat(str,str3);
	printf("str = %s, str3 = %s\n", str, str3);
```

### Using strcmp(string1,string2)
```c
//To compare one string with another - strcmp(string1,string2)
	//Checks from the first character of both strings.
	//If first non-matching character in string1 is greater (in ASCII) than that of string2 - returns > 0
	//If first non-matching character in string1 is lesser (in ASCII) than that of string2 - returns < 0
	//If both strings are equal - returns = 0
	int result = strcmp(str2,str3);
	printf("Result of comparison = %d\n", result);
```