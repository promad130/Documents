
**Insertion Sort** is an efficient algorithm for sorting a small number of elements. It works similarly to the way you might sort a hand of playing cards.

**Pseudocode (CLRS 4th Edition, Page 19):**

```Plaintext
INSERTION-SORT(A, n)
1  for i = 2 to n
2      key = A[i]
3      // Insert A[i] into the sorted sequence A[1 .. i - 1].
4      j = j - 1
5      while j > 0 and A[j] > key
6          A[j + 1] = A[j]
7          j = j - 1
8      A[j + 1] = key
```


```C++
#include <iostream>
#include <vector>
#include <chrono>
#include <fstream>
#include <random>

/**
* INSERTION-SORT(A, n)
* Adheres to CLRS logic with 0-indexed adjustment for C++
*/

void insertionSort(std::vector<int>& A) {
	int n = A.size();
	
	// Start from the second element (j=1)
	for (int j = 1; j < n; j++) {
	int key = A[j]; // Extract key
	
	// Insert A[j] into the sorted sequence A[0..j-1]
	int i = j - 1;
	
	// Shift elements of A[0..j-1] that are greater than key
	while (i >= 0 && A[i] > key) {

	A[i + 1] = A[i];
	i = i - 1;
	}
// Place key in its correct position
		A[i + 1] = key;
	}
}
```
