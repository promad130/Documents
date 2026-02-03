**Expected to know:** [[Indexing]], [[Looping Statements]], [[Data Types and Constants]], [[Data Structures]]
**Topics Covered:** What are arrays, Syntax for Arrays,Indexing, Intro to strings using arrays
**Tags:** [[Data Types and Constants]] [[The C Programming Language]], [[Data Structures]]

So far, we have been storing data in a single variable. But what if I want to store multiple values? Obviously we cannot create that many variables. 
This is where we use something called **Array**.

Array is a collection of homogeneous data type stored in a variable. We access a particular value stored in an array using something called "Indexing". 

---
# **Key Features of Arrays**

✔ **Fixed size** – The size of an array is defined at the time of declaration (in static arrays).  
✔ **Homogeneous elements** – All elements must be of the same type (`int`, `char`, `float`, etc.).  
✔ **Index-based access** – Elements can be accessed using an index (starting from `0`).  
✔ **Efficient retrieval** – Access time is `O(1)` since elements are stored sequentially.

---
# **How to make an array

In The C Programming Language, the syntax to make array is:
```
int array[Size] = {1,2,3,4,5,6,7,8,9,....,Size}
```
where:
- Size is a constant which tells the compiler that how many values that array is going to store.

for example:
```
const int Size = 11;
int scores[Size] = {23, 12, 34, 0, 57, 39, 5, 3, 2, 10, 8};
```

![[Pasted image 20250223044624.png]]

When it comes to accessing the elements of an array, we use indexing which starts from 0.
So:```
```
- scores[0] would be 23
- scores[5] would be 39
```

Also, in C programming language, the arrays are itself a pointer, so accessing them does not require the use of &.
But in order to access the address, we use &.

Also there is something called 'Negative Indexing' in which the allotment starts from back with the index value of -1:
```
- scores[-11] would be 23
- scores[-6] would be 39
```

---
# **Types of Arrays**

### **A. One-Dimensional Array**

A simple list of elements.

```c
int arr[3] = {1, 2, 3};  
```

### **B. Two-Dimensional (2D) Array**

Used to represent matrices (rows & columns).

```c
int matrix[2][3] = { {1, 2, 3}, {4, 5, 6} };
data_type array_name[rows][columns];
```

### **C. Multi-Dimensional Array**

More than 2 dimensions (e.g., 3D arrays for 3D space).

```c
int space[2][3][4];  // 3D array
```

Hence, seeing the pattern, on can say that it goes from right to left, so first creates an array of 4 elements, then 3 of those arrays, and then 2 of those 3x4 matrix array.

---
# Multidimensional Arrays

A **multidimensional array** in C is an array of arrays, allowing you to store data in a grid, table, or higher-dimensional structure. The most common type is the two-dimensional (2D) array, which is often used to represent matrices or tables.

## **Declaration Syntax**

To declare a multidimensional array, specify the size for each dimension:

```c
type arrayName[size1][size2]...[sizeN];
```
- `type` is the data type (e.g., `int`, `float`, `char`)
- `size1`, `size2`, ..., `sizeN` are the sizes of each dimension

Think of it like this, we create an array of size `size1`, after that each element of array is not one element, but rather a collection of elements of size `size2`, and then each element in `size2` array is another array/ collection of elements of size `size3` and so on.

**Examples:**

- 2D array (matrix):
```c
int matrix[3][5]; // 3 rows, 5 columns
```
- 3D array:

```c
float cube[2][4][3]; // 2 layers, 4 rows, 3 columns
```
## **Initialization**

You can initialize a multidimensional array at declaration:

```c
int arr[3][5] = {     
	{1, 2, 3, 4, 5},    
	{10, 20, 30, 40, 50},    
	{5, 10, 15, 20, 25} 
};
```

- This creates a `3x5` integer array, with values filled row by row.
## **Accessing Elements**

To access an element, specify an index for each dimension:
```c
int value = arr[1][2]; // 2nd row, 3rd column (value is 30)
```

- Indices start at 0, so `arr[1][2]` refers to the element in the second row and third column.

## **Memory Layout**

- **Row-major order:** In C, multidimensional arrays are stored in row-major order, meaning all elements of the first row are stored in contiguous memory, followed by all elements of the second row, and so on.
- There is no padding or delimiter between rows; the entire array occupies a single contiguous block of memory.

---
(after covering [[Pointers and Working with Memory Allocation]])
# **Dynamic Multidimensional Arrays**

For arrays whose size is not known at compile time, you can allocate memory dynamically:

```c
int **arr = malloc(rows * sizeof(int *)); 
for (int i = 0; i < rows; i++) 
{     
	arr[i] = malloc(columns * sizeof(int)); 
} // ... use arr[i][j] as usual // Remember to free memory after use 
for (int i = 0; i < rows; i++) 
	free(arr[i]); free(arr);
```

## **Summary Table**

| Feature           | Example Syntax         | Description                            |
| ----------------- | ---------------------- | -------------------------------------- |
| 2D Declaration    | `int arr[3][5];`       | 3 rows, 5 columns                      |
| 2D Initialization | `{ {1,2,3}, {4,5,6} }` | Row-wise initialization                |
| 2D Access         | `arr[1][2]`            | 2nd row, 3rd column                    |
| 3D Declaration    | `float arr[2][4][3];`  | 2 layers, 4 rows, 3 columns            |
| Memory Layout     | Row-major              | All rows stored sequentially in memory |

---
***After covering [[Pointers and Working with Memory Allocation]]***

---
# What are arrays actually?

Arrays itself are pointers which stores the addresses to the values initialized, that is why we don't need to pass the address of the array to `scanf()` function in the code.

---
## What actually happens when I do `arr[i]`?
Now to address the elephant in the room, when we do:
```c
int arr[50];
arr[i] = 4;
```
Then the programming language actually interprets it as:
```c
*(arr+i)
```

Where `arr` is the pointer to the first element of the array, and hence the indexing starts from zero!!!
(attach this in the indexing to refer [[Indexing]])

---
## Array initialization re-discovered

In C programming, array initialization and memory management depend on whether arrays are declared statically (fixed size) or dynamically (variable size). Here's a breakdown of both approaches:
### Static Array Initialization

- **Memory Allocation**:  
    Static arrays have fixed sizes determined at compile time. Memory is allocated on the stack, and the compiler handles deallocation automatically when the array goes out of scope.  
    Example:
```c    
int arr[5] = {1, 2, 3}; // Partially initialized; remaining elements are 0
```
(Uninitialized elements default to zero.)***(Recheck this fact)***

- **Limitations**:  
    Size cannot be modified after declaration. Overflows occur if accessed beyond the allocated size.

### Dynamic Array Initialization
Dynamic arrays use heap memory and require manual management via functions like `malloc()`, `calloc()`, `realloc()`, and `free()`.

#### Allocation
1. **`malloc()`**: Allocates uninitialized memory.
```c
int *arr = malloc(5 * sizeof(int)); // Allocates space for 5 integers
```
2. **`calloc()`**: Allocates zero-initialized memory.
```c
int *arr = calloc(5, sizeof(int)); // Allocates and initializes to 0
```
3. **`realloc()`**: Resizes existing memory blocks.
```c
arr = realloc(arr, 10 * sizeof(int)); // Expands to 10 integers
```

#### Initialization
- Values must be assigned manually:
```c
for (int i = 0; i < 5; i++)      
	arr[i] = i + 1;
```

#### Deallocation
- Use `free()` to release memory:
```c
free(arr); arr = NULL; // Prevents "dangling pointers"
``` 
- **Failure to free memory causes leaks**, which can degrade performance or crash programs.

---
## Pointers to an array:

Now what the initialization of an array along with the meaning of an array has been discussed, let's discuss traversing an array:

```c
int arr[50];
```

For this array, the pointer:

```c
int *arr_ptr = arr[0];
```

and the pointer `arr` are same, as both refer to the first element of the array.

Now that with that covered, both of these:

```c
*(arr_ptr+i);
arr[i];
```
Mean the same thing!!!(already covered)

Lets get to real stuff, Pointer to an entire array 😈. So for the initial array, a pointer declared like this:
```c
type (*ptr)[N] = &array;
```

Basically, `type` is the data types of the element of the array, `N` is the number of elements of the array, everything else is pretty self explanatory.

But what if I do:
```c
type *ptr[N];

for(int i = 0;i<N;i++)
	*ptr[i] = *arr[i];

```

In C, `type (*ptr)[N]` and `type *ptr[N]` are fundamentally different declarations with distinct memory layouts and use cases. `type *ptr[N]` is called an `Array of Pointers` and here is what happens:

- Declaration: `ptr` is an **array of `N` pointers** to `type`.
- Size: `sizeof(ptr)` equals `N * sizeof(type *)` (e.g., 40 bytes for `N=10` on a 64-bit system).
- Operator Precedence:  
    `[]` binds first, making `ptr` an array. The `*` then specifies each element is a pointer.
- **Example**:
```c
int a = 1, b = 2, c = 3; 
int *ptr[3] = {&a, &b, &c};  // Array of 3 integer pointers
```
- `ptr[i]` gives the address of the `i-th` element.
- `*ptr[i]` dereferences to the value at that address.

While in `type (*ptr)[N]`, operator Precedence:
- `()` ensures `*` binds to `ptr` first, making it a pointer.
- `[N]` then modifies the type it points to (an array of size `N`).
- Without parentheses (`type *ptr[N]`), `[]` takes precedence, creating an array of pointers.

#### Key Differences

| Feature               | `type (*ptr)[N]`                     | `type *ptr[N]`                      |
| --------------------- | ------------------------------------ | ----------------------------------- |
| **Type**              | Pointer to an array                  | Array of pointers                   |
| **Size**              | Size of a pointer (4/8 bytes)        | `N × sizeof(pointer)`               |
| **Memory Allocation** | Points to a single contiguous block  | Stores `N` separate pointers        |
| **Arithmetic**        | Advances by `N × sizeof(type)` bytes | Advances by `sizeof(pointer)` bytes |

---
# Passing Array to a function;

As array is a pointer, we pass it in a function like this:
```c
void print_array(int *num_p, int length) 
{ 
	for (int i = 0; i < length; ++i) 
		printf("%d ", num_p[i]); 
	printf("\n"); 
}

int main() 
{ 
	int nums[] = {10, 20, 30, 40, 50, 60}; 
	print_array(nums, 6); 
	return 0; 
}
```

---
Back to [[Pointers and Working with Memory Allocation]]

---
After covering [[Pointers and Working with Memory Allocation]]
## 2D Arrays and Memory Management

- **Single-block allocation**:
```c
int (*arr)[5] = malloc(3 * sizeof(*arr)); // 3 rows, 5 columns free(arr);
``` 
- **Array of pointers**:
```c
int **arr = malloc(3 * sizeof(int*)); 

for (int i = 0; i < 3; i++)      
	arr[i] = malloc(5 * sizeof(int)); 

// Deallocate: 
for (int i = 0; i < 3; i++)      
	free(arr[i]); free(arr);
```

## Best Practices

1. **Check for allocation success**:
    
    c
    
    `if (arr == NULL) {     printf("Memory allocation failed");    exit(1); }`
    
2. **Avoid double-free errors**: Never call `free()` on the same pointer twice[4](https://www.log2base2.com/C/pointer/free-in-c.html).
    
3. **Prevent leaks**: Ensure every `malloc()`/`calloc()` has a corresponding `free()`
    

By understanding these principles, you can efficiently manage memory in C while avoiding common pitfalls like leaks or invalid accesses.
