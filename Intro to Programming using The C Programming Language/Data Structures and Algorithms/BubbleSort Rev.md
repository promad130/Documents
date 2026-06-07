
### 1. Conceptual Overview & Pseudocode
Bubblesort is a popular, albeit inefficient, sorting algorithm that operates by repeatedly swapping adjacent elements that are out of order. The name comes from the way smaller elements "bubble" up to the beginning of the list (or larger elements sink to the end).

**Pseudocode (based on CLRS Problem 2-2):**

```Plaintext
procedure bubbleSort(array: list of sortable items)      // 1. START OF THE PROCEDURE
    n = length(array)
    
    repeat                                               // 2. START OF OUTER LOOP
        swapped = false
        
        for i = 0 to n - 2 do                            // 3. START OF INNER LOOP
            
            if array[i] > array[i + 1] then              // 4. START OF IF STATEMENT
                swap(array[i], array[i + 1])
                swapped = true
            end if                                       // 5. END OF IF STATEMENT
            
        end for                                          // 6. END OF INNER LOOP
        
        n = n - 1                                        // 7. AFTER INNER LOOP FINISHES
        
    until not swapped                                    // 8. END OF OUTER LOOP
    
end procedure                                            // 9. END OF THE PROCEDURE
```

In modern C++, we utilize the STL for utility functions like `std::swap`, but implement the logic manually to demonstrate the pointer-like behavior of array access.

```C++
#include <iostream>
#include <vector>
#include <algorithm> // for std::swap

/**
 * Clean Implementation of Bubblesort
 * Mirrors the CLRS logic with 0-indexed adjustment
 */
void bubbleSort(std::vector<int>& A) {
    size_t n = A.size();
    // i tracks the start of the unsorted portion
    for (size_t i = 0; i < n - 1; ++i) {
        // j moves from the end to bubble the smallest element to index i
        for (size_t j = n - 1; j > i; --j) {
            if (A[j] < A[j - 1]) {
                std::swap(A[j], A[j - 1]);
            }
        }
    }
}
```

Or we can implement it like this:
```C++
#include <bits/stdc++.h>
using namespace std;

void bubbleSort(vector<int>& A)
{
	if(A.size() <= 1)
		return;
		
	bool isSwapped = false;
	do
	{
		isSwapped = false;
		for(int i = 0; i < A.size(); i++)
		{
			if(A[i] > A[i+1])
			{
				swap(A[i], A[i+1]);
				isSwapped = true;
			}
		}
	}while(isSwapped)
}
```