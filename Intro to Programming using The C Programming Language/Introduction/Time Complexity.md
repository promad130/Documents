Time complexity in programming languages quantifies how an algorithm's execution time scales with input size, independent of hardware or language specifics. It helps developers optimize code efficiency by analyzing the number of operations required. Here's a detailed breakdown:

---
## Key Concepts
1. **Definition**  
    Time complexity measures the number of computational steps or operations an algorithm performs relative to input size($n$)i.e., quantitative measure of how much data is given to an algorithm, expressed using **Big O notation** (e.g., $O(n)$, $O(n^2)$). 
    For example:
    - A loop iterating through $n$ elements has $O(n)$ complexity.    
    - Nested loops over $n$ elements result in $O(n^2)$ complexity.
    
2. **Big O Notation**  
    This mathematical notation describes worst-case scenarios, focusing on growth rates:
    - **Constant time** ($O(1)$): Execution time remains fixed (e.g., accessing an array element) .
    - **Linear time** $O(n)$: Time increases linearly with input size (e.g., iterating through a list). For example, for loop.
    - **Logarithmic time** $O\left(\log{n}\right)$: Time grows slowly (e.g., binary search) .
    - **Quadratic time** $O(n^2)$: Time grows quadratically (e.g., nested loops) 
    
3. **Real-World Impact**
    - Algorithms with lower time complexity (e.g., $O(nlog⁡n)$ handle large datasets faster than those with higher complexity (e.g., $O(n^2)$
    - Example: Binary search $O(\log⁡n)$ outperforms linear search ($O(n)$) for sorted data.
![[Pasted image 20250603225929.png]]

---

## Introduction to Time Complexity

**Time Complexity** is a way to describe how the runtime of an algorithm grows as the input size increases. It helps us:
- Compare different algorithms
- Predict performance on large inputs
- Optimize code effectively

### Why Not Just Measure Time?
```cpp
#include <iostream>
#include <chrono>
#include <vector>
using namespace std;

void slowFunction(int n) {
    int sum = 0;
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < n; j++) {
            sum++;
        }
    }
}

int main() {
    auto start = chrono::high_resolution_clock::now();
    slowFunction(1000);
    auto end = chrono::high_resolution_clock::now();
    
    auto duration = chrono::duration_cast<chrono::microseconds>(end - start);
    cout << "Time taken: " << duration.count() << " microseconds" << endl;
    
    return 0;
}
```

**Problem**: Actual time depends on:
- Hardware speed
- Programming language
- System load
- Compiler optimizations

**Solution**: Use **asymptotic analysis** (Big O notation) to describe growth rate independent of these factors.

---

## Big O Notation

Big O describes the **upper bound** of algorithm runtime as input size (n) approaches infinity.

### Formal Definition
f(n) = O(g(n)) if there exist constants c and n₀ such that:
```
f(n) ≤ c · g(n) for all n ≥ n₀
```

### Other Notations (Quick Reference)
- **Big Ω (Omega)**: Lower bound (best case)
- **Big Θ (Theta)**: Tight bound (average case)
- **Big O**: Upper bound (worst case) ← Most commonly used

---

## Common Time Complexities

| Complexity | Name | Example |
|------------|------|---------|
| O(1) | Constant | Array access |
| O(log n) | Logarithmic | Binary search |
| O(n) | Linear | Single loop |
| O(n log n) | Linearithmic | Merge sort |
| O(n²) | Quadratic | Nested loops |
| O(n³) | Cubic | Triple nested loops |
| O(2ⁿ) | Exponential | Recursive Fibonacci |
| O(n!) | Factorial | Permutations |

### Visualization
```
For n = 100:
O(1)        = 1
O(log n)    = ~7
O(n)        = 100
O(n log n)  = ~700
O(n²)       = 10,000
O(2ⁿ)       = 1,267,650,600,228,229,401,496,703,205,376
```

### Examples with C++ Code

#### O(1) - Constant Time
```cpp
int getFirstElement(vector<int>& arr) {
    return arr[0];  // Always takes same time
}

void swap(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;  // Fixed number of operations
}
```

#### O(log n) - Logarithmic Time
```cpp
// Binary Search
int binarySearch(vector<int>& arr, int target) {
    int left = 0, right = arr.size() - 1;
    
    while(left <= right) {
        int mid = left + (right - left) / 2;
        
        if(arr[mid] == target) return mid;
        else if(arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    
    return -1;
}
// Each iteration cuts search space in half: n → n/2 → n/4 → ...
// Number of iterations = log₂(n)
```

#### O(n) - Linear Time
```cpp
int findMax(vector<int>& arr) {
    int maxVal = arr[0];
    
    for(int i = 1; i < arr.size(); i++) {  // n iterations
        if(arr[i] > maxVal) {
            maxVal = arr[i];
        }
    }
    
    return maxVal;
}
```

#### O(n log n) - Linearithmic Time
```cpp
// Merge Sort
void merge(vector<int>& arr, int left, int mid, int right) {
    vector<int> temp(right - left + 1);
    int i = left, j = mid + 1, k = 0;
    
    while(i <= mid && j <= right) {
        if(arr[i] <= arr[j]) temp[k++] = arr[i++];
        else temp[k++] = arr[j++];
    }
    
    while(i <= mid) temp[k++] = arr[i++];
    while(j <= right) temp[k++] = arr[j++];
    
    for(int i = 0; i < temp.size(); i++) {
        arr[left + i] = temp[i];
    }
}

void mergeSort(vector<int>& arr, int left, int right) {
    if(left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);      // T(n/2)
        mergeSort(arr, mid + 1, right); // T(n/2)
        merge(arr, left, mid, right);   // O(n)
    }
}
// T(n) = 2T(n/2) + O(n) = O(n log n)
```

#### O(n²) - Quadratic Time
```cpp
// Bubble Sort
void bubbleSort(vector<int>& arr) {
    int n = arr.size();
    
    for(int i = 0; i < n; i++) {        // n iterations
        for(int j = 0; j < n - i - 1; j++) {  // n iterations
            if(arr[j] > arr[j + 1]) {
                swap(arr[j], arr[j + 1]);
            }
        }
    }
}
// Total: n × n = O(n²)
```

#### O(2ⁿ) - Exponential Time
```cpp
// Naive Fibonacci (VERY SLOW!)
int fibonacci(int n) {
    if(n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);  // Each call makes 2 more calls
}
// Creates a binary tree of calls: 2^n nodes
```

#### O(n!) - Factorial Time
```cpp
// Generate all permutations
void permute(vector<int>& arr, int start, vector<vector<int>>& result) {
    if(start == arr.size()) {
        result.push_back(arr);
        return;
    }
    
    for(int i = start; i < arr.size(); i++) {
        swap(arr[start], arr[i]);
        permute(arr, start + 1, result);
        swap(arr[start], arr[i]);  // backtrack
    }
}
// For n elements: n! permutations
```

---

## How to Measure Time Complexity Quickly

### Rule 1: Drop Constants
```cpp
// O(2n + 5) → O(n)
void example1(int n) {
    for(int i = 0; i < n; i++) {}     // n
    for(int i = 0; i < n; i++) {}     // n
    for(int i = 0; i < 5; i++) {}     // 5
}
// 2n + 5 ≈ O(n) for large n
```

### Rule 2: Drop Lower Order Terms
```cpp
// O(n² + n + 1) → O(n²)
void example2(int n) {
    for(int i = 0; i < n; i++) {           // n
        for(int j = 0; j < n; j++) {}      // n²
    }
    for(int k = 0; k < n; k++) {}          // n
}
// n² dominates, so O(n²)
```

### Rule 3: Sequential = Add
```cpp
// O(n + m)
void example3(int n, int m) {
    for(int i = 0; i < n; i++) {}  // O(n)
    for(int i = 0; i < m; i++) {}  // O(m)
}
// Total: O(n + m)
```

### Rule 4: Nested = Multiply
```cpp
// O(n × m)
void example4(int n, int m) {
    for(int i = 0; i < n; i++) {      // n times
        for(int j = 0; j < m; j++) {  // m times each
        }
    }
}
// Total: O(n × m)
```

### Rule 5: Halving/Doubling Input = Logarithmic
```cpp
// O(log n)
void example5(int n) {
    for(int i = 1; i < n; i *= 2) {  // i: 1 → 2 → 4 → 8 → ... → n
        // ...
    }
}

void example6(int n) {
    for(int i = n; i > 0; i /= 2) {  // i: n → n/2 → n/4 → ... → 1
        // ...
    }
}
```

---

## Tricks and Tips

### Trick 1: Identify Loop Patterns
```cpp
// Pattern: Single loop → O(n)
for(int i = 0; i < n; i++) { /* ... */ }

// Pattern: Nested same-size loops → O(n²)
for(int i = 0; i < n; i++) {
    for(int j = 0; j < n; j++) { /* ... */ }
}

// Pattern: Loop with halving → O(log n)
for(int i = 1; i < n; i *= 2) { /* ... */ }

// Pattern: Loop with recursion → Check Master Theorem
```

### Trick 2: Amortized Analysis
```cpp
// Vector push_back is O(1) amortized
vector<int> v;
for(int i = 0; i < n; i++) {
    v.push_back(i);  // Occasionally O(n), but O(1) on average
}
// Total: O(n), not O(n²)
```

### Trick 3: Two Pointers Pattern
```cpp
// O(n) - NOT O(n²) because each pointer moves at most n times total
bool hasPairWithSum(vector<int>& arr, int target) {
    int left = 0, right = arr.size() - 1;
    
    while(left < right) {  // At most n iterations combined
        int sum = arr[left] + arr[right];
        if(sum == target) return true;
        else if(sum < target) left++;
        else right--;
    }
    
    return false;
}
```

### Trick 4: Sliding Window Pattern
```cpp
// O(n) - Each element added/removed once
int maxSumSubarray(vector<int>& arr, int k) {
    int maxSum = 0, windowSum = 0;
    
    // Initial window
    for(int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    maxSum = windowSum;
    
    // Slide window
    for(int i = k; i < arr.size(); i++) {
        windowSum += arr[i] - arr[i - k];  // Add new, remove old
        maxSum = max(maxSum, windowSum);
    }
    
    return maxSum;
}
```

### Trick 5: Recognize Recursive Complexity
```cpp
// Count recursive calls and work per call

// Example 1: O(2^n)
void allSubsets(int index, int n) {
    if(index == n) return;
    allSubsets(index + 1, n);  // Include
    allSubsets(index + 1, n);  // Exclude
}
// Tree depth = n, branches = 2 → 2^n nodes

// Example 2: O(n)
void printN(int n) {
    if(n == 0) return;
    cout << n << " ";
    printN(n - 1);  // Single recursive call
}
// Call stack depth = n, O(1) work per call → O(n)
```

### Trick 6: HashMap Operations
```cpp
// Unordered_map: O(1) average, O(n) worst case
#include <unordered_map>

unordered_map<int, int> countFrequency(vector<int>& arr) {
    unordered_map<int, int> freq;
    
    for(int num : arr) {  // O(n)
        freq[num]++;      // O(1) average per operation
    }
    
    return freq;  // Total: O(n) average
}

// Map (ordered): O(log n) per operation
#include <map>

map<int, int> countFrequencySorted(vector<int>& arr) {
    map<int, int> freq;
    
    for(int num : arr) {  // O(n)
        freq[num]++;      // O(log n) per operation
    }
    
    return freq;  // Total: O(n log n)
}
```

---

## Master Theorem

The **Master Theorem** provides a formula to determine the time complexity of **divide-and-conquer** recursive algorithms.

### The Formula

For recurrence relations of the form:
```
T(n) = a·T(n/b) + f(n)
```

Where:
- **a** ≥ 1: Number of subproblems
- **b** > 1: Factor by which input size is divided
- **f(n)**: Cost of work done outside recursive calls (dividing + combining)

### The Three Cases

Let **$c = log_b(a)$** (critical exponent)

#### Case 1: $f(n) = O(n^d)$ where d < c
**Result**: $$T(n) = Θ(n^c) = Θ(n^{(log_ba)})$$

*Recursion dominates*

#### Case 2: $f(n) = O(n^d)$ where d = c
**Result**: $$T(n) = Θ(n^c · log n) = Θ(n^d · log n)$$

*Recursion and combining are balanced*

#### Case 3: $f(n) = O(n^d)$ where d > c
**Result**: $$T(n) = Θ(f(n)) = Θ(n^d)$$

*Combining work dominates*

### Examples with C++ Code

#### Example 1: Binary Search
```cpp
int binarySearch(vector<int>& arr, int left, int right, int target) {
    if(left > right) return -1;
    
    int mid = left + (right - left) / 2;
    
    if(arr[mid] == target) return mid;
    else if(arr[mid] < target) return binarySearch(arr, mid + 1, right, target);
    else return binarySearch(arr, left, mid - 1, target);
}
```

**Recurrence**: T(n) = T(n/2) + O(1)
- a = 1 (one subproblem)
- b = 2 (divide by 2)
- f(n) = O(1) = O(n^0)
- c = log₂(1) = 0

Compare: d = 0, c = 0 → **Case 2**

**Result**: T(n) = Θ(n^0 · log n) = **O(log n)**

#### Example 2: Merge Sort
```cpp
void mergeSort(vector<int>& arr, int left, int right) {
    if(left >= right) return;
    
    int mid = left + (right - left) / 2;
    mergeSort(arr, left, mid);      // T(n/2)
    mergeSort(arr, mid + 1, right); // T(n/2)
    merge(arr, left, mid, right);   // O(n)
}
```

**Recurrence**: T(n) = 2T(n/2) + O(n)
- a = 2 (two subproblems)
- b = 2 (divide by 2)
- f(n) = O(n) = O(n^1)
- c = log₂(2) = 1

Compare: d = 1, c = 1 → **Case 2**

**Result**: T(n) = Θ(n^1 · log n) = **O(n log n)**

#### Example 3: Binary Tree Traversal
```cpp
struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
};

void inorder(TreeNode* root) {
    if(!root) return;
    inorder(root->left);       // T(n/2)
    cout << root->val << " ";  // O(1)
    inorder(root->right);      // T(n/2)
}
```

**Recurrence**: T(n) = 2T(n/2) + O(1)
- a = 2
- b = 2
- f(n) = O(1) = O(n^0)
- c = log₂(2) = 1

Compare: d = 0, c = 1 → d < c → **Case 1**

**Result**: T(n) = Θ(n^1) = **O(n)**

#### Example 4: Matrix Multiplication (Naive)
```cpp
vector<vector<int>> multiply(vector<vector<int>>& A, vector<vector<int>>& B, int n) {
    if(n == 1) {
        return {{A[0][0] * B[0][0]}};
    }
    
    // Divide into 4 submatrices, multiply recursively (8 calls)
    // Then combine (O(n²) work)
    // ... (simplified for illustration)
}
```

**Recurrence**: T(n) = 8T(n/2) + O(n²)
- a = 8
- b = 2
- f(n) = O(n²)
- c = log₂(8) = 3

Compare: d = 2, c = 3 → d < c → **Case 1**

**Result**: T(n) = Θ(n³) = **O(n³)**

#### Example 5: Strassen's Matrix Multiplication
**Recurrence**: T(n) = 7T(n/2) + O(n²)
- a = 7
- b = 2
- f(n) = O(n²)
- c = log₂(7) ≈ 2.807

Compare: d = 2, c ≈ 2.807 → d < c → **Case 1**

**Result**: T(n) = **O(n^2.807)** (Better than naive!)

#### Example 6: Linear Search with Split
```cpp
bool linearSearch(vector<int>& arr, int start, int end, int target) {
    if(start > end) return false;
    
    int mid = (start + end) / 2;
    
    // Check both halves (not efficient, but illustrative)
    return linearSearch(arr, start, mid, target) || 
           linearSearch(arr, mid + 1, end, target);
}
```

**Recurrence**: T(n) = 2T(n/2) + O(1)
- a = 2
- b = 2
- f(n) = O(1) = O(n^0)
- c = log₂(2) = 1

Compare: d = 0, c = 1 → d < c → **Case 1**

**Result**: T(n) = **O(n)** (Same as normal linear search!)

### Master Theorem Summary Table

| Recurrence | a | b | f(n) | c = log_b(a) | Case | Result |
|------------|---|---|------|--------------|------|--------|
| T(n) = T(n/2) + O(1) | 1 | 2 | O(1) | 0 | 2 | O(log n) |
| T(n) = 2T(n/2) + O(n) | 2 | 2 | O(n) | 1 | 2 | O(n log n) |
| T(n) = 2T(n/2) + O(1) | 2 | 2 | O(1) | 1 | 1 | O(n) |
| T(n) = 4T(n/2) + O(n) | 4 | 2 | O(n) | 2 | 1 | O(n²) |
| T(n) = 8T(n/2) + O(n²) | 8 | 2 | O(n²) | 3 | 1 | O(n³) |
| T(n) = 7T(n/2) + O(n²) | 7 | 2 | O(n²) | ~2.807 | 1 | O(n^2.807) |
| T(n) = T(n/2) + O(n) | 1 | 2 | O(n) | 0 | 3 | O(n) |

---

## Quick Reference Cheatsheet

### Loop Analysis
```cpp
for(i = 0; i < n; i++)                  // O(n)
for(i = 0; i < n; i += 2)               // O(n)
for(i = 0; i < n; i++) 
    for(j = 0; j < n; j++)              // O(n²)
for(i = 0; i < n; i++)
    for(j = 0; j < i; j++)              // O(n²)
for(i = 1; i < n; i *= 2)               // O(log n)
for(i = n; i > 0; i /= 2)               // O(log n)
for(i = 0; i < n; i++)
    for(j = 1; j < n; j *= 2)           // O(n log n)
```

### Common Data Structure Operations

| Structure | Access | Search | Insert | Delete |
|-----------|--------|--------|--------|--------|
| Array | O(1) | O(n) | O(n) | O(n) |
| Vector (end) | O(1) | O(n) | O(1)* | O(1) |
| Linked List | O(n) | O(n) | O(1) | O(1) |
| Stack | O(n) | O(n) | O(1) | O(1) |
| Queue | O(n) | O(n) | O(1) | O(1) |
| Hash Table | - | O(1)* | O(1)* | O(1)* |
| Binary Search Tree | O(log n)* | O(log n)* | O(log n)* | O(log n)* |
| Heap | - | O(n) | O(log n) | O(log n) |

*Amortized or average case

### Sorting Algorithms

| Algorithm | Best | Average | Worst | Space |
|-----------|------|---------|-------|-------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |
