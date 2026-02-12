
### 1. The Concept: Divide and Conquer

Merge Sort operates on three fundamental steps:

1. **Divide:** Split the array $A[p..r]$ into two smaller subarrays $A[p..q]$ and $A[q+1..r]$. We calculate the midpoint as $q = \lfloor(p+r)/2\rfloor$.
2. **Conquer:** Recursively sort the two subarrays. If the subarray size is small enough (base case, size 1), it is considered sorted.
3. **Combine:** **Merge** the two sorted subarrays back into a single sorted subarray $A[p..r]$.

#### Visualizing the Logic

Imagine sorting the array `{ 38, 27, 43, 3, 9, 82, 10 }`.

**Step 1: Recursive Division (Top-Down)**

```Plaintext
             [38, 27, 43, 3, 9, 82, 10]
                    /           \
        [38, 27, 43, 3]       [9, 82, 10]
          /       \             /      \
   [38, 27]     [43, 3]      [9, 82]   [10]
    /    \       /   \       /    \      |
 [38]   [27]  [43]   [3]   [9]   [82]   [10]  <-- Base Cases (Sorted)
```

**Step 2: Merging (Bottom-Up)**

The real work happens here. We compare elements from the two "child" lists and place the smaller one into a sorted buffer.

```Plaintext
 [38] vs [27] -> Merge -> [27, 38]
 [43] vs [3]  -> Merge -> [3, 43]
      ...
 [27, 38] vs [3, 43] -> Merge -> [3, 27, 38, 43]
      ...
 Final Merge -> [3, 9, 10, 27, 38, 43, 82]
```

---

### 2. Pseudocode (CLRS Standard)

We use the standard notation from _Introduction to Algorithms_ (CLRS). The indices $p$ and $r$ represent the start and end of the subarray, respectively.

#### The `Merge-Sort` Procedure

This simply handles the recursion.

Plaintext

```
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

+1

Plaintext

```
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

---

### 3. C++ Implementation

In C++, we prioritize the "Knuth" philosophy: code should be readable and aesthetically pleasing.

- We use `std::vector` for memory safety but implement the copy logic manually to ensure you understand how the auxiliary space ($O(n)$) is utilized.
    
- We pass the vector by reference (`std::vector<int>&`) to avoid unnecessary copying of the main array during recursive calls.
    

```C++
/**
 * Merge Sort Implementation
 * Course: CS F211 / MAC F242
 * Reference: CLRS Chapter 2.3
 */

#include <iostream>
#include <vector>
#include <cmath> // For std::floor if needed, though integer division suffices

// The MERGE function: Combines two sorted subarrays into one.
// Time Complexity: Theta(n) where n is the total number of elements being merged.
// Space Complexity: Theta(n) for the auxiliary arrays L and R.
void merge(std::vector<int>& A, int p, int q, int r) {
    int nL = q - p + 1; // Size of Left subarray
    int nR = r - q;     // Size of Right subarray

    // Create auxiliary arrays (temp storage)
    // We use std::vector for modern memory management (RAII).
    // In raw C++, this would correspond to: int* L = new int[nL];
    std::vector<int> L(nL);
    std::vector<int> R(nR);

    // Copy data to temp arrays L[] and R[]
    for (int i = 0; i < nL; i++)
        L[i] = A[p + i];
    for (int j = 0; j < nR; j++)
        R[j] = A[q + 1 + j];

    // Merge the temp arrays back into A[p..r]
    int i = 0; // Initial index of first subarray
    int j = 0; // Initial index of second subarray
    int k = p; // Initial index of merged subarray

    while (i < nL && j < nR) {
        if (L[i] <= R[j]) {
            A[k] = L[i];
            i++;
        } else {
            A[k] = R[j];
            j++;
        }
        k++;
    }

    // Copy the remaining elements of L[], if there are any
    while (i < nL) {
        A[k] = L[i];
        i++;
        k++;
    }

    // Copy the remaining elements of R[], if there are any
    while (j < nR) {
        A[k] = R[j];
        j++;
        k++;
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

// Utility to print vector
void printVector(const std::vector<int>& A) {
    for (int val : A) {
        std::cout << val << " ";
    }
    std::cout << "\n";
}

int main() {
    std::vector<int> data = {12, 11, 13, 5, 6, 7};

    std::cout << "Original array: ";
    printVector(data);

    // Indexes are 0-based in C++, so we pass 0 and size-1
    mergeSort(data, 0, data.size() - 1);

    std::cout << "Sorted array:   ";
    printVector(data);

    return 0;
}
```

---

### 4. Complexity Analysis

For your Endsem (40%) and Midsem (35%), you must be able to derive these.

#### Time Complexity

The running time is described by the recurrence:

$$T(n) = 2T(n/2) + \Theta(n)$$

- **Divide:** The divide step computes the middle takes $\Theta(1)$.
    
- **Conquer:** We recursively solve two subproblems, each of size $n/2$, taking $2T(n/2)$.
    
- **Combine:** The `MERGE` procedure iterates through $n$ elements exactly once, taking $\Theta(n)$.
    

Using the **Master Theorem** (Case 2), or a recursion tree analysis, this solves to:

**$T(n) = \Theta(n \lg n)$**

#### Space Complexity

Merge Sort is **not** an in-place sort.

- It requires auxiliary space for the `L` and `R` arrays in the `merge` function.
    
- In the worst case, the space required is proportional to the number of elements $n$.
    
- **Total Space:** $O(n)$.
    

**Technical Note:** While $O(\lg n)$ stack space is used for recursion, the dominant factor is the $O(n)$ required for the temporary arrays.
