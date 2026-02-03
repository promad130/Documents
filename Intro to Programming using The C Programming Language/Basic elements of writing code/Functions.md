**Expected to know:** [[Introduction to Programming]], [[Variables and their scope]], [[Data Types and Constants]]
**Topics Covered:** Functions, parameters, return statements
**Tags:**

Suppose I want to do some task, some computation repeatedly. Lets say that computation requires a 50 lines of code to be written. It is obvious that no one is foolish enough to write 50 lines of code repeatedly, so instead we create something called a **function**.

A **function** in C is a block of code that performs a specific task. Functions help organize code, improve re-usability, and make programs easier to manage.

---
# Types of functions:
There are two types of functions:

## 1) Library functions

Functions that are already defined in the libraries used in the code. The standard library has all the basic functions needed so that any type of computation can be done, just like how in Mathematics, everything can be done just by a simple knowledge of addition and subtraction.

But still, we need a few more predefined functions for the sake of speeding up the process of creating the programme. 

## 2) User-Defined Functions

Functions that are not present in the library and are defined by the user are called User-Defined Functions.

Be aware to not define a function with an identifier which is already used by the standard library. 
As upon defining a function with that identifier, the compiler will use the new function defined whenever that identifier is used, which will lead to the predefined function not working in the desired way, but in the way given by the user-defined function which uses the same identifier.
Hence, the Library function will not work the intended way, and may lead to some serious errors.

---
# How to create a function?

Function is a code of block that can work repeatedly whenever called.
```
void functionEx(int a)
{
	printf("%d",a);
	return;
}
```
It has three main parts:

## Return Types;

- First is the **return type**. This specifies the data type of the value that will be returned by the function. As visible, the data type f return is void, so nothing will be returned, hence whenever the return statement is called, the function will finish it's execution there.

## Parameters

- Second is the **parameter**. This specifies the data that the function needs whenever called. This is the value with which function works in order to do what it is intended to do. In the example, the parameter that the function requires is an **int a**, which the function prints.
- However, whenever we declare a parameter of the function, then in code/main or whenever we use that function, the parameters passed are passed by value, i.e., their values are copied and used rather than the variables itself.
```c
#include <stdio,h>

void add(int a, int b){
a = a+b;}

int main()
{
	int a = 10;
	int b = 20;
	printf("%d %d\n",a,b);
	add(a,b);
	printf("%d %d\n",a,b);
	return 0;
}

//output;
>>>10 20
>>>10 20
```

In above example, we can see that the actual value of `a` did not change, this is exactly the type of behavior expected as explained above. 

## Body of the function

- Third is the **body of the function**.This is the code that matters the most, as this is the task that the function will perform, in our case, the task is to print something and then stop(i.e, return.)

---
# Function Declaration

Now when writing a function, we need to make sure that it is written above the main() function, otherwise upon using the keywords of our function which is declared after the main() function, the compiler will throw an error as it still doesn't know that a function with that keyword exists, as the function is declared after the main() function.

However, if we wish to declare it after the main() function, then we need to add a **declaration** of that function before the main function like this:
```c
#include <stdio.h>

// Function declaration
int add(int, int);

int main() {
    int sum = add(10, 20);  // Function call
    printf("Sum: %d\n", sum);
    return 0;
}

// Function definition
int add(int a, int b) {
    return a + b;
}
``` 

Also, you **Cannot define a function inside a function**, not allowed by any procedural programming language due to the way memory is handled inside the stack.

---
# But what are return statements?

Return statements tells the computer when to exit a particular block of code. It does two things:
- Do exactly what it name suggests, return stuff, stuff means values that that block of code is expected to return.
- Tells the computer to exit that particular block of code.

So in a function:
- The function **stops execution** when `return` is encountered.
- If the function has a **return type (int, float, etc.)**, `return` must provide a value of that type.
- If the function is `void`, `return;` can be used to exit early (optional).

****But can I use format specifier with return statements?****