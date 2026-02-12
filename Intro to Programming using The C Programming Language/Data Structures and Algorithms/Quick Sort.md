
### 1. The Concept & Logic

Quick Sort relies on a procedure called **Partitioning**.

1. **Divide:** Choose an element from the array to serve as the **pivot**. Rearrange the array so that all elements smaller than the pivot are on its left, and all elements larger are on its right. The pivot is now in its final, sorted position.
    
2. **Conquer:** Recursively sort the sub-arrays to the left and right of the pivot.
    
3. **Combine:** Because the sub-arrays are sorted in place, no extra work is needed to combine them.
    

The elegance of Quick Sort lies in its **in-place** nature (requires only small auxiliary stack space) and its tight inner loop, which makes it faster in practice than Merge Sort for many inputs, despite having a worse worst-case time complexity.

---

### 2. Visualization: Trace of a Partition

Let's visualize the **Lomuto Partition scheme** (the standard method used in CLRS Chapter 7 ).

- **Goal:** Partition array `A` around pivot `P` (last element).
    
- **Indices:**
    
    - `j`: The current element being scanned.
    - `i`: The boundary of the "smaller-than-pivot" region.

**Initial State:** Array `[ 2, 8, 7, 1, 3, 5, 6, 4 ]`. Pivot is **4**. `i` starts before the array (-1).


```Plaintext
Step 1: j scans '2'. (2 <= 4). Increment i, swap A[i], A[j].
Region <= 4: [2]
Array: [ 2, 8, 7, 1, 3, 5, 6, 4 ]
         ^
         i

Step 2: j scans '8'. (8 > 4). Do nothing.
Region <= 4: [2]
Array: [ 2, 8, 7, 1, 3, 5, 6, 4 ]
         ^

Step 3: j scans '7'. (7 > 4). Do nothing.
Region <= 4: [2]
Array: [ 2, 8, 7, 1, 3, 5, 6, 4 ]
         ^

Step 4: j scans '1'. (1 <= 4). Increment i, swap A[i], A[j] (swap 8 and 1).
Region <= 4: [2, 1]
Array: [ 2, 1, 7, 8, 3, 5, 6, 4 ]
            ^
            i

Step 5: j scans '3'. (3 <= 4). Increment i, swap A[i], A[j] (swap 7 and 3).
Region <= 4: [2, 1, 3]
Array: [ 2, 1, 3, 8, 7, 5, 6, 4 ]
               ^
               i

Final Step: Swap Pivot (4) with A[i+1] (8).
Result: [ 2, 1, 3, 4, 7, 5, 6, 8 ]
         <--Small--> Pivot <--Large-->
```

---
### 3. Pseudocode (CLRS Style)

This follows the text strictly. Note that CLRS uses 1-based indexing, but for C++ preparation, logic remains the same.

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

---

### 4. Modern C++ Implementation

Following the "Knuth" philosophy, this code emphasizes readability and standard C++ idioms (using `std::vector` and `std::swap`).

```C++
/**
 * Quick Sort Implementation in Modern C++
 * Reference: CLRS Chapter 7
 */

#include <iostream>
#include <vector>
#include <algorithm> // For std::swap

// Partition Function: Lomuto Partition Scheme
// Time Complexity: Theta(n) for the partition step
int partition(std::vector<int>& arr, int low, int high) {
    int pivot = arr[high]; // Choosing the last element as pivot
    int i = low - 1;       // Index of smaller element

    for (int j = low; j < high; j++) {
        // If current element is smaller than or equal to pivot
        if (arr[j] <= pivot) {
            i++;
            // Swap arr[i] and arr[j]
            std::swap(arr[i], arr[j]);
        }
    }
    // Place pivot in the correct position
    std::swap(arr[i + 1], arr[high]);
    return i + 1;
}

// QuickSort Function
// Time Complexity: O(n lg n) average, O(n^2) worst case
void quickSort(std::vector<int>& arr, int low, int high) {
    if (low < high) {
        // q is the partitioning index, arr[q] is now at right place
        int q = partition(arr, low, high);

        // Separately sort elements before partition and after partition
        quickSort(arr, low, q - 1);
        quickSort(arr, q + 1, high);
    }
}

// Utility function to print the vector
void printVector(const std::vector<int>& arr) {
    std::cout << "[ ";
    for (int val : arr) {
        std::cout << val << " ";
    }
    std::cout << "]" << std::endl;
}

int main() {
    // Example usage
    std::vector<int> data = {10, 7, 8, 9, 1, 5};
    
    std::cout << "Original array: ";
    printVector(data);

    // Initial call: low is 0, high is size - 1
    quickSort(data, 0, data.size() - 1);

    std::cout << "Sorted array:   ";
    printVector(data);

    return 0;
}
```

---

### 5. Complexity Analysis

For your exams, you must be able to derive these based on the **Recurrence Relations** (Master Theorem).

#### **Time Complexity**
- **Worst Case ($O(n^2)$):**
    Occurs when the partition is maximally unbalanced (e.g., sorted or reverse-sorted input where the pivot is always the extreme value).
    - _Recurrence:_ $T(n) = T(n-1) + T(0) + \Theta(n)$
    
- **Best/Average Case ($O(n \lg n)$):**
    Occurs when the partition splits the array roughly in half.
    - _Recurrence:_ $T(n) = 2T(n/2) + \Theta(n)$

#### **Space Complexity**
- **$O(\lg n)$** due to the recursive stack frames in the average case.
- **$O(n)$** in the worst case (though this can be mitigated using tail-call optimization).

#### **Stability**

- **Not Stable.** The swapping in the partitioning step can change the relative order of equal elements.

---
