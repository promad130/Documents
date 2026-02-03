# Introduction

The way arrays are defined in C-sharp is different than what we learnt in C. 
Although it also requires that all the elements of an array be of same data type, but a predefined size of array is not required.

Syntax to define an array is:
```csharp
DataType[] arrayName;
```

After that we allocate elements to it either by creating memory space via:
```csharp
arrayName = new DataType[n];
```
which allocated `n*(size of DataType)` to the array in the memory, and all the elements are initialized to their default value, or via this:
```csharp
DataType[] arrayName = {Element1,Element2,....};
```

If we wish to initialize array after declaration, we need to use the `new` keyword:
```csharp
arrayName = new DataType[] {element1, element2, ...};
```

---
**Functions Covered:** 
	Class: Array
		- Sort()
		- 

# Functions

## 1) Using `Array.Sort(ArrayName)`;

Sorts elements of the array according to their data type. 
For string, the case matters, the lower case of any letter appears first, and then the upper case.
```csharp
using System;
using System.Collections.Generic;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] Arr = new string[5];
            for (int i = 0; i < Arr.Length; i++)
            {
                Arr[i] = Console.ReadLine();
            }
            Array.Sort(Arr);
            for (int i = 0; i < Arr.Length; i++)
            {
                Console.WriteLine(Arr[i]);
            }
            Console.ReadKey();
        }
    }
}
```
```text
Input:
a
A
b
B
C
Output:
a
A
b
B
C

```

