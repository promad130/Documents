# Bucket Sort: Distribution-Based Sorting

## The Core Idea

**Divide and conquer** using the **distribution** of input:
1. Divide input into **n buckets**
2. Sort each bucket **individually**
3. Concatenate buckets to get sorted output

## Assumptions

**Critical**: Input is **uniformly distributed** over a range (e.g., [0, 1))

### What is Uniform Distribution?

**Uniform on [0, 1)**: Every value has **equal probability** of appearing anywhere in the range.

**Example**: 
- Random numbers: 0.23, 0.87, 0.45, 0.12, 0.99
- Each could be anywhere from 0 to 1 with equal likelihood

**Non-uniform example** (doesn't work well):
- 0.01, 0.02, 0.03, 0.04, 0.99 ← Most values clustered near 0

---

## Algorithm Steps

### Input
Array `A` of `n` elements uniformly distributed in `[0, 1)`

### Step 1: Create n Buckets

```
Bucket 0: [0.0, 0.1)
Bucket 1: [0.1, 0.2)
Bucket 2: [0.2, 0.3)
...
Bucket 9: [0.9, 1.0)
```

For `n` elements, create `n` buckets each covering range `1/n`.

### Step 2: Distribute Elements

For each element `A[i]`:
- Compute bucket index: `index = ⌊n × A[i]⌋`
- Insert `A[i]` into `Bucket[index]`

**Example with n=10**:
```
Element 0.23 → bucket ⌊10 × 0.23⌋ = ⌊2.3⌋ = 2
Element 0.87 → bucket ⌊10 × 0.87⌋ = ⌊8.7⌋ = 8
Element 0.12 → bucket ⌊10 × 0.12⌋ = ⌊1.2⌋ = 1
```

### Step 3: Sort Each Bucket

Use any sorting algorithm (typically **Insertion Sort** for small buckets).

### Step 4: Concatenate

Combine buckets in order: `Bucket[0] + Bucket[1] + ... + Bucket[n-1]`

---

## Example Trace

**Input**: `[0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68]`
**n = 10**

### Step 1: Create 10 buckets

```
Bucket 0: [0.0, 0.1)
Bucket 1: [0.1, 0.2)
Bucket 2: [0.2, 0.3)
...
Bucket 9: [0.9, 1.0)
```

### Step 2: Distribute

```
0.78 → bucket 7
0.17 → bucket 1
0.39 → bucket 3
0.26 → bucket 2
0.72 → bucket 7
0.94 → bucket 9
0.21 → bucket 2
0.12 → bucket 1
0.23 → bucket 2
0.68 → bucket 6
```

**Buckets after distribution**:
```
Bucket 0: []
Bucket 1: [0.17, 0.12]
Bucket 2: [0.26, 0.21, 0.23]
Bucket 3: [0.39]
Bucket 4: []
Bucket 5: []
Bucket 6: [0.68]
Bucket 7: [0.78, 0.72]
Bucket 8: []
Bucket 9: [0.94]
```

### Step 3: Sort each bucket

```
Bucket 1: [0.12, 0.17] ✅
Bucket 2: [0.21, 0.23, 0.26] ✅
Bucket 3: [0.39] (already sorted)
Bucket 6: [0.68] (already sorted)
Bucket 7: [0.72, 0.78] ✅
Bucket 9: [0.94] (already sorted)
```

### Step 4: Concatenate

```
Result: [0.12, 0.17, 0.21, 0.23, 0.26, 0.39, 0.68, 0.72, 0.78, 0.94] ✅
```

---

## Pseudocode

```
BUCKET-SORT(A):
    n = length(A)
    Create array B[0...n-1] of empty lists
    
    // Distribute elements into buckets
    for i = 0 to n - 1:
        index = ⌊n × A[i]⌋
        INSERT A[i] into list B[index]
    
    // Sort each bucket
    for i = 0 to n - 1:
        SORT(B[i]) using Insertion Sort
    
    // Concatenate buckets
    result = []
    for i = 0 to n - 1:
        APPEND B[i] to result
    
    return result
```

---

## C++ Implementation

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void bucketSort(vector<double>& arr) {
    int n = arr.size();
    vector<vector<double>> buckets(n);
    
    // Step 1: Distribute into buckets
    for (int i = 0; i < n; i++) {
        int bucketIndex = (int)(n * arr[i]);
        
        // Edge case: arr[i] == 1.0 would give index n
        if (bucketIndex == n) bucketIndex = n - 1;
        
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    cout << "After distribution:\n";
    for (int i = 0; i < n; i++) {
        if (!buckets[i].empty()) {
            cout << "Bucket " << i << ": ";
            for (double x : buckets[i]) cout << x << " ";
            cout << "\n";
        }
    }
    cout << "\n";
    
    // Step 2: Sort each bucket (using insertion sort)
    for (int i = 0; i < n; i++) {
        sort(buckets[i].begin(), buckets[i].end());  // Can use insertion sort
    }
    
    // Step 3: Concatenate
    int index = 0;
    for (int i = 0; i < n; i++) {
        for (double x : buckets[i]) {
            arr[index++] = x;
        }
    }
}

int main() {
    vector<double> arr = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
    
    cout << "Original: ";
    for (double x : arr) cout << x << " ";
    cout << "\n\n";
    
    bucketSort(arr);
    
    cout << "Sorted: ";
    for (double x : arr) cout << x << " ";
    cout << "\n";
    
    return 0;
}
```

**Output**:
```
Original: 0.78 0.17 0.39 0.26 0.72 0.94 0.21 0.12 0.23 0.68 

After distribution:
Bucket 1: 0.17 0.12 
Bucket 2: 0.26 0.21 0.23 
Bucket 3: 0.39 
Bucket 6: 0.68 
Bucket 7: 0.78 0.72 
Bucket 9: 0.94 

Sorted: 0.12 0.17 0.21 0.23 0.26 0.39 0.68 0.72 0.78 0.94
```

---

## Why Does This Work?

**Key insight**: If input is uniformly distributed:
- Each bucket gets approximately **n/n = 1** element on average
- Sorting 1 element takes **O(1)** time
- n buckets × O(1) per bucket = **O(n)** total

**But this is probabilistic!** We need rigorous proof.

---

## Time Complexity (Intuition)

- **Creating buckets**: O(n)
- **Distribution**: O(n)
- **Sorting buckets**: O(Σ nᵢ²) where nᵢ = size of bucket i
  - Insertion sort on bucket of size nᵢ takes O(nᵢ²)
- **Concatenation**: O(n)

**Total**: O(n + Σ nᵢ²)

**Question**: What is the **expected value** of Σ nᵢ²?

---

**Next**: Rigorous probabilistic analysis to prove E[T(n)] = Θ(n).