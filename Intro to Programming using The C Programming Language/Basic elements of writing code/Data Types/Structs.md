**Expected to know:** [[Variables and their scope]],[[Data Types and Constants]]
**Topics Covered:** What are structs, what is typedef
**Tags:**

This is a custom data type created by the user in order to create a new data type which is collection of multiple data type and has it's own properties.

Let's say I want to create a data structure which can store height, name, age, etc of like 100 players, It is obvious that we will create an array of 100 elements, but how do we store the specific information of a particular player in them? 

One answer would be to create a multidimensional array, but arrays can only store values of only one data type, creating multiple arrays of 100 element for each player property is tedious task and hard to manage.

We create a struct like this:
```c
struct Struct_name
{
	char name[40];
	int age;
	int runs;
	//.....and so on
};
```

And now in order to instantiate it, i.e., create a variable of that struct:
```c
struct Struct_name Vaiable_name={
"Name Lets GO";
56;
100;
};
```

`struct` keyword tells the compiler that it is a custom data type, `Struct_name` refers to which struct, and `Variable_name` is for unique variables.

---
# Struct declaration and Initialization;

The struct initialization the The C Programming language is an Aggregate initialization.

As explained above, a struct is declared like:
```C
struct a {

};
```
and is instantiated in a code like this:
```c
struct a variableA = {};
```

(if you covered [[Pointers and Working with Memory Allocation]])
All the respective things to be included in a struct has to be included at the time of declaration, as the memory is allocated from the stack, not heap, hence it ain't dynamic!

Hence if you try to do this and try to declare it's terms like:
```c
A = {}; 
```

in **C**, it will cause a **compilation error** because this syntax is **not valid** for struct assignment.

### **How to Assign Values to a Struct After Declaration?**

#### **1. Null/0 initialization
```c
#include <stdio.h>

struct a {
    int x;
    int y;
};

int main() {
    struct a A = {0};  // ✅ Zero-initializing struct (all members = 0)
    printf("%d %d\n", A.x, A.y);  // Output: 0 0
    return 0;
}
```

#### **2. Assign Individual Members

```c
#include <stdio.h>

struct a {
    int x, y;
};

int main() {
    struct a A;  // Declaring without initializing
    A.x = 10;    // Assigning values manually
    A.y = 20;

    printf("%d %d\n", A.x, A.y);
    return 0;
}
```

#### **3. Use a Temporary Struct for Assignment**

In **C99 and later**, you can use a **compound literal**:

```c
#include <stdio.h>

struct a {
    int x, y;
};

int main() {
    struct a A;       // Declare struct
    A = (struct a){5, 10};  // ✅ Assign a temporary struct
    
    printf("%d %d\n", A.x, A.y);  // Output: 5 10
    return 0;
}
```

- **Explanation**: `(struct a){5, 10}` creates an **anonymous temporary struct** with `x = 5` and `y = 10`, which gets assigned to `A`.

---
# Better way to declare structs,aka typedef

Now it is tedious and makes code harder to read as it gets complicated and long when we use;
```c
struct Struct_name Varible_name = {};

```
in order to define struct.
So in order to improve code and make it easier to read, we use something called "typedef".

`Typedef` is like identifier changer, it changes the `identifier` from the line in the code where it is used.

The `typedef` declaration provides a way to declare an identifier as a type alias, to be used to replace a possibly complex type name.
for example, changing `double`
```c
typedef double real;
real pi = 3.14;
printf("%lf", pi);
```

So we use typedef for structs:
```c
typedef struct
{
	int a;
	char arr[50];
	
}Name_t;
```
And now we can use our struct as:
```c
Name_t VaribaleName= {

};
```

A doubt may occur that isn't this typedef working on the identifier struct itself? So isn't it changing the identifier of struct?
Answer is no.
Here, we are applying typedef on an anonymous struct created like this:
```c
struct{
	int a;
	char b;
	....
};
```
Hence, what we did is valid and does not affect the definition of struct itself.

However, if we don't declare all the elements which creating an instance of a struct, then all of the other members that are not declared is either set to:
- 0 if the data type is numeric
- "" if data type is string or char 

---
(Upon covering [[Pointers and Working with Memory Allocation]])
# Struct size and padding of structs:
When we create a struct some memory is allocated to that struct. Now it is obvious that when a struct is made it consists of elements of different size, so for ease of access the struct is padded.

**Struct padding** is a mechanism used by C compilers to insert extra bytes between members of a structure to ensure that each member is properly aligned in memory according to the requirements of the target architecture.

So, in struct padding, all the data types are allocated the memory size of the data type that requires the largest size.

For example, in this:
```c
typedef struct{
	int a[50];
	char b[1000];
	char c;
}trial;
```
the size of struct `trial` is 1204 bytes.


---
# Ways to work with struct:
## Array of a Struct
An **array of structs** in C is a collection where each element is a structure, allowing you to store and manage multiple records, each with several fields of potentially different types. This is especially useful for handling lists of complex data, such as records in a database, students in a class, or books in a library.

### **How to Declare an Array of Structs**
First, define your struct type. For example:
```c
struct Book 
{     
	char title[100];    
	double price;    
	int pages; 
};
```
Then, declare an array of that struct type:
```c
struct Book library[3];  // Array of 3 Book structures
```
Or, if you used a `typedef`:
```c
typedef struct {
	char title[100];    
	double price;    
	int pages; 
} Book; Book library[3];
```

### **Initializing an Array of Structs**
You can initialize the array at the time of declaration using nested curly braces:

```c
struct Book library[3] = {
{"Learn C", 650.50, 325},    
{"C Pointers", 175.00, 225},    
{"C Pearls", 250.00, 250} 
};
```
Each element of the array is a struct, so each is initialized with its own set of values.

### **Accessing and Modifying Struct Array Elements**

You access members of a struct in the array using the dot (`.`) operator and array indexing:

```c
strcpy(library[0].title, "Advanced C"); 
library[0].price = 700.00; 
library[0].pages = 400;
```
Looping through the array to print all books:

```c
for (int i = 0; i < 3; i++) 
{     
	printf("Title: %s\tPrice: %.2lf\tPages: %d\n", library[i].title, library[i].price, library[i].pages); 
}
```
## **Reading Data into a Struct Array**

You can use a loop and functions like `scanf` or `fgets` to read values into each struct in the array:

```c
for (int i = 0; i < 3; i++) 
{     
	printf("Enter title: ");    
	fgets(library[i].title, 100, stdin);    
	printf("Enter price: ");    
	scanf("%lf", &library[i].price);    
	printf("Enter pages: ");    
	scanf("%d", &library[i].pages);    
	getchar(); // to consume newline after scanf 
}
```
## **Pointers to Struct Arrays**

You can declare a pointer to the first element of the struct array and use it to access elements:

```c
struct Book *ptr = library; 
for (int i = 0; i < 3; i++) 
{     
	printf("Title: %s\n", (ptr + i)->title); 
}
```

Similarly we can create:
- Array of Array of Struct
- Struct of array of struct