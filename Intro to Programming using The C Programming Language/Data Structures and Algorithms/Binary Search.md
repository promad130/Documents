
### 1. Concept & Logic

**Binary Search** is the quintessential "Divide and Conquer" algorithm. Unlike Linear Search, which scans elements sequentially ($O(n)$), Binary Search requires the input array to be **sorted** (monotonically increasing or decreasing).

**The Logic:**

The algorithm reduces the search space by half in every step. It compares the target value $v$ with the element at the **midpoint** of the current search interval.

1. If $v$ equals the midpoint, the search is successful.
    
2. If $v$ is smaller than the midpoint, the target _must_ lie in the left subarray; discard the right half.
    
3. If $v$ is larger than the midpoint, the target _must_ lie in the right subarray; discard the left half.
    

This intuitive process is formally described as a recursive or iterative reduction of the subarray $A[low \dots high]$.

**Text-Based Visualization:**

Suppose we are searching for **41** in the sorted array $A$:

`[3, 9, 10, 27, 38, 41, 57, 69, 72]`

- **Iteration 1:**
    
    Range: `[3 ... 72]` (Indices $0$ to $8$).
    
    Midpoint Index: $\lfloor (0+8)/2 \rfloor = 4$. Value: $A[4] = 38$.
    
    Comparison: $41 > 38$.
    
    _Action:_ Target is greater. Discard left half `[3...38]`. New Range: indices $5$ to $8$.
    
- **Iteration 2:**
    
    Range: `[41, 57, 69, 72]` (Indices $5$ to $8$).
    
    Midpoint Index: $\lfloor (5+8)/2 \rfloor = 6$. Value: $A[6] = 57$.
    
    Comparison: $41 < 57$.
    
    _Action:_ Target is smaller. Discard right half `[57...72]`. New Range: indices $5$ to $5$.
    
- **Iteration 3:**
    
    Range: `[41]` (Index $5$).
    
    Midpoint Index: $\lfloor (5+5)/2 \rfloor = 5$. Value: $A[5] = 41$.
    
    Comparison: $41 == 41$.
    
    _Action:_ **Found.** Return index 5.
    

---

### 2. Pseudocode (CLRS Style)

Following the conventions in _Introduction to Algorithms_ (CLRS), we use 1-based indexing for pseudocode, though we will switch to 0-based for C++.

**Iterative Binary Search**


```Plaintext

BINARY-SEARCH(A, n, v)
1.  low = 1
2.  high = n
3.  while low <= high
4.      mid = floor((low + high) / 2)
5.      if A[mid] == v
6.          return mid
7.      elseif A[mid] < v
8.          low = mid + 1
9.      else
10.         high = mid - 1
11. return NIL
```

**Note on Correctness (Loop Invariant):**

To prove correctness formally (required for Endsems), we define a **Loop Invariant**:

> _At the start of each iteration of the while loop, if the target value $v$ exists in the array, it must be located in the subarray $A[low \dots high]$._
> 

**Recursive binary search**:

```
function binarySearch(array, target, left, right):
    // Base case: element not found
    if left > right:
        return -1
    
    // Calculate middle index
    mid = left + (right - left) / 2
    
    // Base case: element found
    if array[mid] == target:
        return mid
    
    // Recursive case: search left half
    if array[mid] > target:
        return binarySearch(array, target, left, mid - 1)
    
    // Recursive case: search right half
    else:
        return binarySearch(array, target, mid + 1, right)
```

**Key points:**

- **Base cases**:
    - If `left > right`, the search space is empty → return -1
    - If `array[mid] == target`, we found it → return the index
- **Recursive cases**:
    - If target is smaller than middle element, search left half
    - If target is larger, search right half
- **Initial call**: `binarySearch(array, target, 0, array.length - 1)`
- **Time complexity**: O(log n)
- **Space complexity**: O(log n) due to recursive call stack

**Precondition**: The array must be sorted.


---

### 3. Complexity Derivation & Analysis

We analyze the time complexity using the **Recurrence Relation** method, a standard mathematical tool from the "Foundations" section of your syllabus.

#### Time Complexity

Let $T(n)$ be the time required to search an array of size $n$.

In each step, we perform a constant amount of work (comparisons and index calculations, $\Theta(1)$) and then solve a problem of half the size ($T(n/2)$).

**The Recurrence:**

$$T(n) = T(n/2) + \Theta(1)$$

**Derivation (Substitution Method):**

We can unroll the recurrence:

1. $T(n) = T(n/2) + c$
    
2. $T(n/2) = T(n/4) + c \rightarrow T(n) = (T(n/4) + c) + c = T(n/4) + 2c$
    
3. $T(n) = T(n/2^k) + k \cdot c$
    

The recursion stops when the problem size becomes 1 (base case), i.e., $\frac{n}{2^k} = 1 \implies 2^k = n$.

Taking the logarithm base 2 on both sides:

$$k = \log_2 n$$

Substituting $k$ back into the equation:

$$T(n) = T(1) + c \cdot \log_2 n$$

$$T(n) = \Theta(1) + c \cdot \log n$$

$$T(n) = \Theta(\log n)$$

**Complexity Summary:**

- **Worst Case:** $O(\log n)$ (Target is not in the array or at the ends).
    
- **Best Case:** $\Omega(1)$ (Target is exactly at the first midpoint).
    
- **Average Case:** $\Theta(\log n)$.
    

#### Space Complexity

- **Iterative Implementation:** $O(1)$. We only store `low`, `high`, and `mid` variables.
    
- **Recursive Implementation:** $O(\log n)$. Each recursive call consumes stack memory.
    

---

### 4. C++ Implementation

Here is a robust, "aesthetic" implementation. Note the calculation of `mid`: we use `low + (high - low) / 2` instead of `(low + high) / 2` to prevent **integer overflow** for large arrays, a detail that demonstrates engineering rigor.


```C++
/**
 * Binary Search Implementation
 * Course: CS F211 Data Structures and Algorithms
 * * Strategy: Divide and Conquer
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */

#include <iostream>
#include <vector>
#include <algorithm> // For std::sort

// Iterative Binary Search
// Returns the index of target if found, otherwise returns -1
int binarySearch(const std::vector<int>& arr, int target) {
    int low = 0;
    int high = static_cast<int>(arr.size()) - 1;

    // Loop Invariant: If target exists, it is in arr[low...high]
    while (low <= high) {
        // Prevent integer overflow: equivalent to (low + high) / 2
        int mid = low + (high - low) / 2;

        if (arr[mid] == target) {
            return mid; // Target found
        } else if (arr[mid] < target) {
            low = mid + 1; // Discard left half
        } else {
            high = mid - 1; // Discard right half
        }
    }

    return -1; // Target not found
}

int main() {
    // 1. Setup Data
    std::vector<int> data = {12, 7, 14, 9, 10, 11, 3, 2, 6, 8};
    int target = 9;

    // 2. Pre-requisite: Sorting (Binary Search requires sorted data)
    // Using STL sort (IntroSort: O(n log n))
    std::sort(data.begin(), data.end());

    std::cout << "Sorted Data: ";
    for (int val : data) std::cout << val << " ";
    std::cout << "\n";

    // 3. Execution
    int result = binarySearch(data, target);

    // 4. Output
    if (result != -1) {
        std::cout << "Element " << target << " found at index " << result << ".\n";
    } else {
        std::cout << "Element " << target << " not found.\n";
    }

    return 0;
}
```

