# Introduction

**Lists** in C# are dynamic, resizable collections that can store elements of any specified type. 
They are implemented by the generic `List<T>` class, which is part of the `System.Collections.Generic` namespace.

Syntax for initialization and declaration:
```csharp
using System.Collections.Generic;

List<int> numbers = new List<int>(); // Empty list of integers
List<string> names = new List<string> { "Alice", "Bob", "Charlie" }; // Initialized with values
```

We can also just give some default space to it, or copy from another array:
```csharp
List<string> authors = new List<string>(5); // Capacity of 5
string[] animals = { "Cow", "Camel", "Elephant" };
List<string> animalsList = new List<string>(animals); // Copy from array
```

---
# Functions

Lets have a look at some basic functions first:
- `listName.Add(item);`
	- Adds something to the list at the end, i.e., appends the given `item`
- `listName.Count;`
	- Counts the number of items in the list.
- `listName.Remove(item);`
	- Removes the given `item` from the list.
	- `listName.RemoveAt(index);`
		- Removes item at the given `index`.
```csharp
using System;
using System.Collections.Generic;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            List<int> Amount = new List<int>();
            Amount.Add(45);
            Amount.Add(56);
            for (int i = 0; i < Amount.Count; i++)
            {
                Console.WriteLine(Amount[i]);
            }

            Amount.Remove(45);
            for (int i = 0; i < Amount.Count; i++)
            {
                Console.WriteLine(Amount[i]);
            }
        }
    }
}
```

Output:
```text
45
56
56
```

