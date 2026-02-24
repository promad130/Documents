# The Stability Requirement: Order Preservation

## Definition

A sorting algorithm is **stable** if it preserves the **relative order** of elements with equal keys.

### Example

Input: `[5ᵃ, 3, 5ᵇ, 1]` (where 5ᵃ comes before 5ᵇ originally)

**Stable sort**: `[1, 3, 5ᵃ, 5ᵇ]` ✅ (order of 5s preserved)

**Unstable sort**: `[1, 3, 5ᵇ, 5ᵃ]` ❌ (order of 5s reversed)

## Why Stability Matters for Compositional Sorting

When sorting by **Month**, we want dates with the **same month** to remain in **day-order** from the previous pass.

### Visual Trace

**Original**:
```
Position: 0        1        2        3
Data:     15-03-95 22-01-95 15-01-96 10-01-95
```

**After sorting by Day (stable)**:
```
Position: 0        1        2        3
Data:     10-01-95 15-03-95 15-01-96 22-01-95
          ↑        ↑        ↑        ↑
Original: 3        0        2        1  (original indices)
```

**After sorting by Month (STABLE)**:
```
Months are:    1        1        1        3
Data:     10-01-95 22-01-95 15-01-96 15-03-95
          ↑        ↑        ↑        ↑
From prev: 0        3        2        1
```

Notice: Among month=1, the order is `10, 22, 15` (days in ascending order from previous step).

**After sorting by Year (STABLE)**:
```
Years:    95       95       95       96
Data:     10-01-95 22-01-95 15-03-95 15-01-96 ✅ CORRECT!
```

## What Happens with Unstable Sort?

**After sorting by Month (UNSTABLE)**:
```
Months:    1        1        1        3
Data:     15-01-96 10-01-95 22-01-95 15-03-95  ← Order scrambled!
```

Now day order is lost among month=1.

**After sorting by Year (UNSTABLE)**:
```
Data:     22-01-95 10-01-95 15-03-95 15-01-96  ❌ WRONG!
```

Days are not in order anymore!

## Stable vs Unstable Algorithms

| Algorithm | Stable? |
|-----------|---------|
| Counting Sort | ✅ (with proper implementation) |
| Radix Sort | ✅ (if uses stable sub-sort) |
| Merge Sort | ✅ |
| Insertion Sort | ✅ |
| Bubble Sort | ✅ |
| Quick Sort | ❌ |
| Heap Sort | ❌ |
| Selection Sort | ❌ |

## C++ Demo: Stability Test

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Element {
    int key;
    char id;  // To track original order
};

int main() {
    vector<Element> arr = {
        {5, 'A'}, {3, 'B'}, {5, 'C'}, {1, 'D'}, {5, 'E'}
    };
    
    // Stable sort
    stable_sort(arr.begin(), arr.end(), [](const Element& a, const Element& b) {
        return a.key < b.key;
    });
    
    cout << "Stable sort:\n";
    for (auto& e : arr) {
        cout << e.key << e.id << " ";
    }
    cout << "\n";  // Output: 1D 3B 5A 5C 5E  ✅
    
    // Reset
    arr = {{5, 'A'}, {3, 'B'}, {5, 'C'}, {1, 'D'}, {5, 'E'}};
    
    // Unstable sort (regular sort may be unstable)
    sort(arr.begin(), arr.end(), [](const Element& a, const Element& b) {
        return a.key < b.key;
    });
    
    cout << "Unstable sort (may vary):\n";
    for (auto& e : arr) {
        cout << e.key << e.id << " ";
    }
    cout << "\n";  // Output might be: 1D 3B 5E 5A 5C  ❌
    
    return 0;
}
```

## The Golden Rule

**For compositional sorting to work**: Every intermediate sorting step **MUST** be stable.

---

**Next**: Radix Sort - the master of compositional sorting.