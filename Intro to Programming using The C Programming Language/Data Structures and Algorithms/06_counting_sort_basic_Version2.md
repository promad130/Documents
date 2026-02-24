# Counting Sort (Basic): Frequency-Based Sorting

## The Core Idea

Instead of **comparing** elements, **count** how many times each value appears, then reconstruct the sorted array.

## Requirements

- **Input**: Array of integers
- **Range**: Elements are in range `[0, k]` where `k` is known and small
- **Output**: Sorted array

## Algorithm Steps (Basic Version - NOT STABLE)

1. **Count** frequencies of each value
2. **Reconstruct** array by placing each value according to its count

## Example

**Input**: `[2, 5, 3, 0, 2, 3, 0, 3]`
**Range**: 0 to 5 (k = 5)

### Step 1: Count Frequencies

```
Value:  0  1  2  3  4  5
Count:  2  0  2  3  0  1
```

### Step 2: Reconstruct

```
Value 0 appears 2 times → [0, 0]
Value 1 appears 0 times → []
Value 2 appears 2 times → [2, 2]
Value 3 appears 3 times → [3, 3, 3]
Value 4 appears 0 times → []
Value 5 appears 1 time  → [5]

Result: [0, 0, 2, 2, 3, 3, 3, 5] ✅
```

## Pseudocode (Basic - Unstable)

```
COUNTING-SORT-BASIC(A, k):
    Create count array C[0...k] initialized to 0
    
    // Count frequencies
    for i = 0 to length(A) - 1:
        C[A[i]] = C[A[i]] + 1
    
    // Reconstruct
    index = 0
    for value = 0 to k:
        for j = 0 to C[value] - 1:
            A[index] = value
            index = index + 1
```

## C++ Implementation (Basic)

```cpp
#include <iostream>
#include <vector>
using namespace std;

void countingSortBasic(vector<int>& arr, int k) {
    int n = arr.size();
    vector<int> count(k + 1, 0);  // count[0...k]
    
    // Step 1: Count frequencies
    for (int i = 0; i < n; i++) {
        count[arr[i]]++;
    }
    
    cout << "Counts: ";
    for (int i = 0; i <= k; i++) {
        cout << "count[" << i << "]=" << count[i] << " ";
    }
    cout << "\n\n";
    
    // Step 2: Reconstruct
    int index = 0;
    for (int value = 0; value <= k; value++) {
        for (int j = 0; j < count[value]; j++) {
            arr[index] = value;
            index++;
        }
    }
}

int main() {
    vector<int> arr = {2, 5, 3, 0, 2, 3, 0, 3};
    int k = 5;  // Maximum value
    
    cout << "Original: ";
    for (int x : arr) cout << x << " ";
    cout << "\n\n";
    
    countingSortBasic(arr, k);
    
    cout << "Sorted: ";
    for (int x : arr) cout << x << " ";
    cout << "\n";
    
    return 0;
}
```

**Output**:
```
Original: 2 5 3 0 2 3 0 3 

Counts: count[0]=2 count[1]=0 count[2]=2 count[3]=3 count[4]=0 count[5]=1 

Sorted: 0 0 2 2 3 3 3 5
```

## Time Complexity

- **Counting**: O(n) - iterate through array once
- **Reconstructing**: O(n + k) - iterate through count array and place elements
- **Total**: **Θ(n + k)**

When **k = O(n)**, this is **Θ(n)** → **linear time**!

## Space Complexity

- **Count array**: O(k)
- **Total**: **O(n + k)** (if we include output array)

## Limitations of Basic Version

❌ **NOT STABLE**: Original order of equal elements is lost
❌ **Can't handle associated data**: What if each number has associated info (like a name)?

Example:
```
Input: [(3, "Alice"), (2, "Bob"), (3, "Charlie")]

Basic counting sort only sees: [3, 2, 3]
Result: [2, 3, 3]

But which 3 is Alice's and which is Charlie's? We lost that info!
```

## When to Use Basic Counting Sort

✅ Sorting simple integers
✅ Small range k
✅ Stability doesn't matter

❌ Sorting objects with associated data
❌ Need stability (for Radix Sort)

---

**Next**: Making Counting Sort stable - the crucial trick!