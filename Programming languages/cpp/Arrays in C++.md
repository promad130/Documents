**Expected to know:** [[Arrays]]
**Topics Covered:**
**Tags:**

C++ allows you to declare arrays either locally (inside a function, e.g., `main`) or globally (outside all functions). However, there are important differences in how much memory you can allocate for these arrays, depending on where they are declared.

# Basic Syntax 
Same as that in The C Programming Language.
```cpp
dataType arrayName[arraySize];
```

# Types of arrays in C++
## Local Arrays (Stack Allocation)
- **Where:** Declared inside functions (e.g., `int arr[100000];` inside `main`).
- **Memory:** Allocated on the stack.
- **Size Limit:** The stack has a limited size, often 1MB on Windows and 8MB on Linux by default.
- **Typical Safe Limit:** Arrays of size up to about 10,000 to 100,000 elements (for `int`) are usually safe, but this depends on your system and compiler settings.
- **What happens if too large?** Declaring a very large local array (e.g., `int arr[1000000];`) can cause a stack overflow, leading to a segmentation fault or crash.

---
## Global Arrays (Data/BSS Segment Allocation)
- **Where:** Declared outside all functions (e.g., `int arr[1000000];` at file scope).
- **Memory:** Allocated in the data or BSS segment, not on the stack.
- **Size Limit:** Much larger than stack, but still limited by:
    - Your system’s available RAM and address space
    - The OS and compiler/linker limits for a single object or the total data segment size
- **What happens if too large?** If the array is too large (e.g., `int arr[10000000000];`), your program may fail to load, crash at runtime, or the OS may refuse to allocate memory.
- **Note:** Even if the program compiles, it may not run if the system cannot provide the required memory.

---
## Why the Difference?
- **Local arrays** use the stack, which is small and intended for short-lived, small data.
- **Global arrays** use the data segment, which can be much larger but is still finite and subject to OS and linker restrictions.

---
# Multidimensional Arrays
Multidimensional arrays in C++ are arrays of arrays, allowing you to store data in a grid-like structure (2D/3D) or higher dimensions. They work similarly to C but with C++-specific features like `std::string` support and range-based loops.

---
## 1. Declaration and Initialization

## **2D Arrays**

```cpp
// Declaration 
int matrix[3][4]; // 3 rows, 4 columns 
// Initialization 
int grid[2][3] = {{1, 2, 3}, {4, 5, 6}}; 

std::string letters[2][4] = {{"A", "B", "C", "D"}, {"E", "F", "G", "H"}};`
```

## **3D Arrays**

```cpp
// Declaration 
int cube[2][3][4]; // 2 layers, 3 rows, 4 columns 
// Initialization 
int cube[2][3][4] = {     
	{
		{0,1,2,3}, 
		{4,5,6,7}, 
		{8,9,10,11}
	},    // Layer 0    
	{
		{12,13,14,15}, 
		{16,17,18,19}, 
		{20,21,22,23}
	} // Layer 1 };`
```
---
## 2. Accessing Elements
Use indices for each dimension:

```cpp
std::cout << grid[0][1];   // Output: 2 
std::cout << cube[1][2][3]; // Output: 23
```

---
## 3. Looping Through Arrays

### Nested For Loops (Traditional)
```cpp
// 2D array 
for (int i = 0; i < 2; i++) 
{     
	for (int j = 0; j < 3; j++) 
	{        
		std::cout << grid[i][j] << " ";    
	}    
	std::cout << "\n"; 
}
```
### Range-Based For Loops (C++11+)
```cpp
for (auto& row : grid) 
{       
	// Note: Use 'auto&' to avoid pointer decay     
	for (int element : row) 
	{        
		std::cout << element << " ";    
	}    
	std::cout << "\n"; 
}
```
### 3D Array Example
```cpp
for (int i = 0; i < 2; i++) 
{     
	for (int j = 0; j < 3; j++) 
	{        
		for (int k = 0; k < 4; k++) 
		{            std::cout << cube[i][j][k] << " ";        }    } }`
```
---
## 4. Key Differences from C
- **String Support**: Use `std::string` instead of `char[]` for text:
    
```cpp
std::string names[2][2] = {{"Alice", "Bob"}, {"Charlie", "Diana"}};
```
    
- **Range-Based Loops**: Simplify iteration with `auto` (C++11+).
- **Type Safety**: Compiler enforces type consistency across dimensions.

---
## 5. Dynamic Allocation
For arrays with sizes determined at runtime, use heap allocation:

```cpp
int** dynamic2D = new int*[rows]; // 2D dynamic array 
for (int i = 0; i < rows; i++) 
{     
	dynamic2D[i] = new int[cols]; 
} // Deallocation required later with delete[]
```

---
## 6. Common Pitfalls

- **Bounds Checking**: No built-in bounds checking; accessing invalid indices causes undefined behavior.
    
- **Memory Layout**: Elements are stored contiguously in row-major order.
    

---

## **7. When to Use Multidimensional Arrays**

- **Fixed-Size Data**: Matrices, grids, or 3D coordinates.
    
- **Performance-Critical Code**: Faster access than nested STL containers (e.g., `std::vector<std::vector<T>>`).
    

---
