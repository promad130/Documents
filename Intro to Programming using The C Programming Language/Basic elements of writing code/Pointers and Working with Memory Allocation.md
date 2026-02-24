**Expected to know:** [[Introduction to Programming]] (Until Now)
**Topics Covered:** Pointers, pass by reference/value,
**Tags:** [[Pointers and Working with Memory Allocation]],[[The C Programming Language]],[[Data Types and Constants]] 

Everything that computer does, every task and data is stored inside the memory irrespective of it's type. We can think of memory as a collection of boxes and each box having it's own unique address that helps us identify them individually.
Every variable/ identifier that exists in the code refers to a memory address, as every constant in the code is stored at a particular address in a memory.

We have some functions and techniques that help us work directly with the memory itself, making the programme more efficiently and space effective.

---
# What is a Pointer?
A **pointer** in computer programming is a variable that stores the memory address of another variable or data structure. It acts as a reference to a location in memory where data is stored, allowing programmers to access and manipulate that data directly.
It is a powerful tool which gives you access to the core of the computer! But that comes at a risk which we will discuss using `The C Programming Language.`

---
# Why do we need them?

- **Accessing Values Indirectly :** Using a pointer, we can access and modify the value of a variable indirectly.
- **Passing Data to Functions Efficiently :** Pointers allow us to pass large data (like arrays) to functions without copying them.
- Working with Arrays and Strings : Pointers help in navigating arrays and modifying strings easily.
- **Working with Structures :** Pointers allow us to efficiently access and modify structure members, especially when passing structures to functions.

---
# Using Pointers:
In C Programming Language, pointer itself is a data type declared using a `pointer dereference operator(*)` :
```c
int * pointer_ofAn_Int;
```

And just like that, we created an integer pointer.
However, the actual meaning of the `pointer dereference operator(*)` is `"Get the value at"`, i.e, it gets the value at the given address when not used in variable declaration.
Something like this:

```c
float * pointer_ofFloat;
```
Creates a pointer for a floating point number data type.

We use `&` before the variable as this gives the address of that variable, i.e., it returns the address of the variable on which this operator is applied. For example:
```c
int main()
{
	int a = 56;
	int * pointer_a = &a;
	
	printf("%d\n",a);
	printf("%p\n", pointer_a);
	printf("%p\n", &pointer_a);
	
	return 0;
}
```

`Output:
`56`
`0x7ffd7023a03c
``0x7ffd7023a040
``
- `56` is the value of `a`
- `0x7ffd7023a03c0` is the address of the variable `a`
- `0x7ffd7023a040` is the address of the pointer that stores the address of `a`

Note that the format specifier for pointer is `%p`.
In C memory addresses are typically displayed in hexadecimal format.The prefix `0x` indicates that the address is in hexadecimal [Base 16](obsidian://open?vault=Documents&file=Math%2FNumber%20System). 
The exact representation of the addresses would be looked upon when we talk about memory allocation.

---
# Size of a pointer:

The size of pointer is fixed, i.e., independent of the data type it points to. However pointers are unsigned integers and it depends of the system itself:
- 16-bit system: 2 bytes
- 32-bit system: 4 bytes
- 64-bit system: 8 bytes

---
# Types of pointers:

Pointers can be of different type depending upon the usage:
## 1) Simple Pointer
A pointer that stores the address of a variable of a standard data types such as `int`, `double`, `char`, etc,.

## 2) Null Pointer 
A pointer that does not point to any `valid memory location`.
So, what is a valid memory location? We will look into this when we discuss memory allocation!

## 3) Void Pointer
A pointer that holds address of any data type!
```c
void * a = &variableA;
```

## 4) Pointer to pointers
A pointer that points to a pointer:
```c
int a = 56;
int *a_p = &a;
int **A = &a_p;
```
Similarly, we can also create pointer that pointer, which points to another pointer which points to a variable, but anything except a pointer which points to a pointer is quite rare!
```c
int *** A_p = &A;
```

For Example:
```c
#include <stdio.h>

int main()
{
	int a = 56;
	int *a_p = &a;
	int **A = &a_p;
	int ***A_p = &A;
	
	printf("%d, %d, %d, %d\n", a, *a_p, **A, ***A_p);
	printf("%p, %p, %p, %p\n", &a, a_p, *A, **A_p);
	
	return 0;
}
```
OUTPUT:
```c
rubber_duck@pop-os:~/Desktop/Acads/Sem2/CP F111 (Prac + Theory)$ ./a.out 
56, 56, 56, 56
0x7ffd7fcf9e8c, 0x7ffd7fcf9e8c, 0x7ffd7fcf9e8c, 0x7ffd7fcf9e8c
rubber_duck@pop-os:~/Desktop/Acads/Sem2/CP F111 (Prac + Theory)$ 
```

But it's use case is quite amazing!!!!

## Pointers to Arrays 
Would be discussed in the ![[Arrays#Pointers to an array]]
## Pointer to Structs



## Pointers to constants
Pointers that points to the variables, but cannot change the value it points to are called `Pointers to Constants`. 
It cannot change the value it points to,i.e., if we try to do this:
```c
int a = 56;
const int* const_ptr = &a;
*const_ptr = 5;
```
Then it throws an error.
## Constant Pointers
A pointer who's value cannot be changed, i.e., we cannot change the virtual memory(what it means would be covered later) it points to!
```c
void* const a_ptr = &a;
```

---
# Pointer Arithmetic
This allows us to move through the memory by going up and down the memory addresses using Pointer arithmetics.

There are 5 basic operations:
- **Increment (ptr++)**
- **Decrement (ptr--)**
- **Addition (ptr+n)**
- **Subtraction(ptr-n)**
- **Pointer difference (ptr1-ptr2)** 

For example:
```c
#include <stdio.h>

int main()
{
	int a = 56;
	int*a_p = &a;
	int *b = a_p + 5;
	
	printf("%p, %p\n", a_p, b);	
	return 0;
}
```
OUTPUT:
```c
rubber_duck@pop-os:~/Desktop/Acads/Sem2/CP F111 (Prac + Theory)$ ./a.out 
0x7ffdaf0dc7f4, 0x7ffdaf0dc808
```

So here we can see the arithmetic happened as:
$$x_{ptr} + 5 = x_{ptr} + 5\times\text{(size of int)}$$

---
# **Ways to use pointer**
	
## Using it to pass the parameters by value

```c
#include <stdio.h>
#include <string.h>

void add(int a, int b)
{
	a = a+b;
	return;
}

void print(int a)
{
	printf("%d",a);
	return;
}

int main()
{
	int x = 10;
	print(x);
	int y = 15;
	print(y);
	
	add(x,y);

	print(x); print(y);
	
	return 0;
}

```

So in this programme, the values of the variable given in as the parameter doesn't change, instead, they remain same as they are `passed by reference`, i.e, their copies are passed instead and worked with instead of the integers itself.

In order to change the variables, their address's should be knows to the function to actually act on the values of the variables itself. So for that we create a pointer pointing too the address, and pass in the pointer and use `pointer dereferance operator` to get the value at the address passed:

```c
#include <stdio.h>
#include <string.h>

void add(int* a, int* b)
{
	*a = *a+*b;
	return;
}

void print(int a)
{
	printf("%d",a);
	return;
}

int main()
{
	int x = 10;
	print(x);
	int y = 15;
	print(y);
	
	add(&x,&y);

	print(x); print(y);
	
	return 0;
}

```

OUTPUT:
10
15
25
15

So the value of `x` itself has now changed. This is why scanf() function also requires us to pass the address of the variable instead of the variable itself.


## Using it to reduce the memory storage

Everything takes storage. From variables to user-defined data types, all have different storage value.

For example in this:

```c
#include <stdio.h>
#include <string.h>

struct emp{
	char name[50];
	char ID[20];
	char title[60];
	int age;
	int sal;
};

void getInp(struct emp *e)
{
	printf("Enter the name of the employee: ");
	scanf("%s", e->name);
	printf("Enter the ID of the employee: ");
	scanf("%s", e->ID);
	printf("Enter the title of the employee: ");
	scanf("%s", e->title);
	printf("Enter the age of the employee: ");
	scanf("%d", &e->age);
	printf("Enter the salary of the employee: ");
	scanf("%d", &e->sal);
}
// Check the number of employ whose name starts with the given letter(Case matters)
int CheckName(struct emp employ[], int len, int key)
{
	int count = 0;
	for (int i = 0; i < len; i++)
	{
		if(employ[i].name[0] == key)
		{
			count++;
		}
	}
	return count;
}

int main()
{
	struct emp e[100];
	for (int i = 0; i < 100; i++)
	{
		getInp(&e[i]);
	}
	
	CheckName(e, 100, 'A');

	return 0;
}

```

We are passing the whole array of employee by reference, so their value in copied in the memory and then worked upon. Each struct has the size of 138 bytes, and the array has 100 elements, so a total of 13800 bytes is copied, which occupies a lot of memory.

This can be avoided by using pointers, as we refer to the original value via a pointer.

```c
typedef struct 
{ 
	char author[20]; 
	char text[140]; 
	char timestamp[40]; 
	int num_rt; 
	int num_quote_rt; 
	int num_likes; 
} tweet_t;

tweet_t more_liked_tweet_p(tweet_t *t1_p, tweet_t *t2_p) 
{ 
	tweet_t ans = ((*t1_p).num_likes > (*t2_p).num_likes) ? (*t1_p) : (*t2_p);
	return ans; 
} 

int main() 
{ 
	tweet_t t1 = { … }; 
	tweet_t t2 = { … }; 
	tweet_t popular_p = more_liked_tweet_p(&t1, &t2); 
	printf("Tweet '%s' has more likes\n” , popular_p.text); 
	return ;
}
```

So here, the pointer of the struct is passed, so instead of copying stuff, we just make a pointer which takes up much less space as the space of the pointer is same, i.e., 8 bytes, and then we can use dereference operator in the function!!!

---
# Arrow Operator

So, for a pointer variable that is pointing that has members, we can use this variable.
For example, a struct has variables that we can access using the dot (.) operator, but when we have a pointer to a struct, first we  need to access it using dereference and then we can use the members!

```c
(*struct_ptr).member;
```

is same as:

```c
struct_ptr->member;
```

---
# Memory Allocation

In any programming language, the rules of memory allocation are same and is/ can be only influenced by the type of OS being used. Going in too detail would be like touching the computer architecture which is well planned to be discussed later. 
So here we cover the basics of it:
[[Memory Allocation]] 
![[Memory Allocation#Memory allocation functions in The C Programming Language]]

---
# Array is an pointer?

Check out the after section partition in [[Arrays#What are arrays actually?]] under [[Data Types and Constants]].
![[Arrays#What are arrays actually?]]

---
# Function Pointers
In The C Programming Language, we can also create function pointers.
```C
int (*func)(int *, char);
```

## Function Pointers

A **function pointer** is a special type of pointer variable that stores the address of a function rather than the address of a data variable. This allows functions to be called indirectly and enables dynamic execution of different functions at runtime.

## Key Characteristics

- **Storage of Function Addresses:**  
    Like data pointers, function pointers store addresses, but specifically the addresses of functions in memory.
    
- **Declaration Syntax:**  
    The declaration of a function pointer closely resembles a function prototype, with the addition of a `*` to indicate it's a pointer.  
    Example (C/C++):
    
    c
    
    `int (*funcPtr)(int, int);`
    
    This declares `funcPtr` as a pointer to a function that takes two `int` arguments and returns an `int`[3](https://jenniferchukwu.com/posts/functionpointer)[6](https://w3.cs.jmu.edu/kirkpams/OpenCSF/Books/csf/html/FunctionPointers.html).
    
- **Assignment and Usage:**  
    You can assign a function's address to the pointer and then call the function via the pointer:
    
    c
    
    `int add(int a, int b) { return a + b; } int (*funcPtr)(int, int) = add; int result = funcPtr(2, 3);  // Calls add(2, 3)`
    
    The return type and parameter list of the pointer must match the function it points to[3](https://jenniferchukwu.com/posts/functionpointer)[4](https://www.linkedin.com/pulse/function-pointer-uttam-basu)[6](https://w3.cs.jmu.edu/kirkpams/OpenCSF/Books/csf/html/FunctionPointers.html).
    

## Benefits and Use Cases

- **Callbacks:**  
    Function pointers are essential for implementing callback mechanisms, where a function is passed as an argument to another function. This is common in event handling, sorting routines, and modular programming[5](https://www.sanfoundry.com/c-tutorials-advantages-pointers-modular-programming/)[6](https://w3.cs.jmu.edu/kirkpams/OpenCSF/Books/csf/html/FunctionPointers.html).
    
- **Plug-ins and Modularity:**  
    They facilitate the creation of modular systems where different modules or plug-ins can be swapped in and out by simply changing the function pointer assignment, without altering the main program logic[2](https://unstop.com/blog/function-pointers-in-c)[5](https://www.sanfoundry.com/c-tutorials-advantages-pointers-modular-programming/).
    
- **Lookup Tables:**  
    Arrays of function pointers can be used to create jump tables or lookup tables for efficient dispatch of different functions based on runtime conditions[6](https://w3.cs.jmu.edu/kirkpams/OpenCSF/Books/csf/html/FunctionPointers.html).
    

## Example

c

`#include <stdio.h> int add(int a, int b) { return a + b; } int multiply(int a, int b) { return a * b; } int main() {     int (*operation)(int, int);     operation = add;    printf("Sum: %d\n", operation(2, 3)); // Output: 5     operation = multiply;    printf("Product: %d\n", operation(2, 3)); // Output: 6     return 0; }`

This example shows how a function pointer can be reassigned to different functions, allowing dynamic selection of behavior at runtime[3](https://jenniferchukwu.com/posts/functionpointer)[4](https://www.linkedin.com/pulse/function-pointer-uttam-basu).
