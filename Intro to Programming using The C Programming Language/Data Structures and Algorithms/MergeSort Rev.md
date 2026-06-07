
#### The `Merge-Sort` Procedure

This simply handles the recursion.

```Plaintext
MERGE-SORT(A, p, r)
1. if p >= r
2.     return                     // Base case: 0 or 1 element
3. q = floor((p + r) / 2)         // Divide
4. MERGE-SORT(A, p, q)            // Conquer Left
5. MERGE-SORT(A, q + 1, r)        // Conquer Right
6. MERGE(A, p, q, r)              // Combine
```

#### The `Merge` Procedure

This is the heart of the algorithm. It assumes the two subarrays are already sorted and "zips" them together.

```Plaintext
MERGE(A, p, q, r)
1.  nL = q - p + 1                // Length of left subarray
2.  nR = r - q                    // Length of right subarray
3.  Let L[0..nL-1] and R[0..nR-1] be new arrays
4.  Copy A[p..q] into L
5.  Copy A[q+1..r] into R
6.  i = 0, j = 0, k = p
7.  while i < nL and j < nR       // Compare and place smallest
8.      if L[i] <= R[j]
9.          A[k] = L[i]
10.         i = i + 1
11.     else
12.         A[k] = R[j]
13.         j = j + 1
14.     k = k + 1
15. Copy remaining elements of L (if any) into A
16. Copy remaining elements of R (if any) into A
```

```C++
#include <bits/stdc++.h>
using namespace std;

void merge(vector<int>& arr, int start, int mid, int end)
{
	int newLower = mid - start + 1;
	int newUpper = end - mid;
	
	vector<int> lower(newLower);
	vector<int> upper(newUpper);
	
	for(int i = 0; i < newLower; i++)
	{
		lower[i] = arr[i];
	}
	for(int i = 0; i < newUpper; i++)
	{
		upper[i] = arr[i];
	}
	
	int i = 0;
	int j = 0;
	int k = low;
	
	while(i < newLower && j < newUpper)
	{
		if(lower[i] <= upper[j])
		{
			arr[k] = lower[i];
			i++;
			k++;
		} else{
			arr[k] = upper[j];
			j++;
			k++;
		}
	}
	
	while(i < newlower)
	{
		arr[k] = lower[i];
		k++;
		i++;
	}
	while(j < newUpper)
	{
		arr[k] = upper[j];
		k++;
		j++;
	}
}

// The MERGE-SORT function: Recursive driver.
// T(n) = 2T(n/2) + Theta(n)
void mergeSort(std::vector<int>& A, int p, int r) {
    if (p >= r) {
        return; // Base case: 0 or 1 element is always sorted
    }
    
    // Calculate midpoint. Using (p+r)/2 avoids overflow for large p,r 
    // but p + (r-p)/2 is safer in strict systems programming.
    int q = p + (r - p) / 2;

    mergeSort(A, p, q);      // Sort the left half
    mergeSort(A, q + 1, r);  // Sort the right half
    merge(A, p, q, r);       // Merge the sorted halves
}
```