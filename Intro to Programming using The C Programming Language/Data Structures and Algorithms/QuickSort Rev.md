#### 1. The Main Procedure

This procedure handles the recursive calls. It checks if the subarray has more than one element ($p < r$), partitions it, and then sorts the left and right sides.

```plaintext
QUICKSORT(A, p, r)
1  if p < r
2      q = PARTITION(A, p, r)
3      QUICKSORT(A, p, q - 1)
4      QUICKSORT(A, q + 1, r)
```

**Parameters:**
- $A$: The array to be sorted.
- $p$: The starting index of the sub-array (typically $1$ for the first call).    
- $r$: The ending index of the sub-array (typically $A.length$ for the first call).

#### 2. The Partition Procedure

This is the heart of the algorithm (Lomuto Partition scheme). It selects the last element $A[r]$ as the **pivot** and rearranges the array so that all elements smaller than the pivot come before it, and all elements larger come after it.

```plaintext
PARTITION(A, p, r)
1  x = A[r]                // The Pivot
2  i = p - 1               // Highest index of the "low" side
3  for j = p to r - 1      // Scan through the array
4      if A[j] <= x        // If current element is smaller than pivot
5          i = i + 1
6          exchange A[i] with A[j]
7  exchange A[i + 1] with A[r]  // Place pivot in its correct sorted position
8  return i + 1                 // Return the pivot's new index
```

**Variables:**

- $x$: The value of the pivot element.
- $i$: Tracks the boundary of the "less than pivot" region.
- $j$: The current element being examined in the loop.

```C++
#include <bits/stdc++.h>
using namespace std;

int partition(vector<int>& arr, int low, int high)
{
	int pivot = arr[high];
	int i = low - 1; // region with values less than the pivot	
	for(int j = low; j < high; j++)
	{
		if(arr[j] <= pivot)
		{
			i++;
			swap(arr[i], arr[j]);
		}
	}
	
	swap(arr[i+1], arr[high]);
	return i+1;
}

void quickSort(vector<int>& arr, int low, int high)
{
	if(low >= high){
		return;
	}
	int q = partition(arr, low, high);
	quickSort(arr, low, q);
	quickSort(arr, q + 1, high);
}
```