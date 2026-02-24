# Counting Sort (Stable): The Prefix Sum Trick

## The Problem with Basic Counting Sort

The basic version **loses the original order** of elements. We need to preserve it for Radix Sort and for sorting objects with satellite data.

## The Solution: Cumulative Counts (Prefix Sums)

Instead of just counting frequencies, compute **cumulative counts** to determine **exact positions** for each element.

## The Trick Explained

### Example Array

```
Input: [2, 5, 3, 0, 2, 3, 0, 3]
       (A, B, C, D, E, F, G, H)  ← Labels to track original positions
```

### Step 1: Count Frequencies

```
Value:  0  1  2  3  4  5
Count:  2  0  2  3  0  1
```

### Step 2: Compute Cumulative Counts (Prefix Sums)

**The key insight**: `cumulative[i]` tells us **how many elements are ≤ i**

```
Value:        0  1  2  3  4  5
Count:        2  0  2  3  0  1
Cumulative:   2  2  4  7  7  8
              ↑     ↑  ↑     ↑
          2 ≤ 0  4 ≤ 2  7 ≤ 3  8 ≤ 5
```

**Calculation**:
```
cumulative[0] = count[0] = 2
cumulative[1] = cumulative[0] + count[1] = 2 + 0 = 2
cumulative[2] = cumulative[1] + count[2] = 2 + 2 = 4
cumulative[3] = cumulative[2] + count[3] = 4 + 3 = 7
cumulative[4] = cumulative[3] + count[4] = 7 + 0 = 7
cumulative[5] = cumulative[4] + count[5] = 7 + 1 = 8
```

### Step 3: Place Elements (Right to Left)

**Why right to left?** To maintain stability (original order).

```
Original (right to left): [2ᴬ, 5ᴮ, 3ᶜ, 0ᴰ, 2ᴱ, 3ᶠ, 0ᴳ, 3ᴴ]
                           ↑                               ↑
                          last                           first
```

**Process from right to left**:

#### Processing `3ᴴ` (last element, index 7):
- Value = 3
- cumulative[3] = 7 → place at position 7-1 = **6**
- Decrement: cumulative[3] = 6

```
Output: [_, _, _, _, _, _, 3ᴴ, _]
```

#### Processing `0ᴳ` (index 6):
- Value = 0
- cumulative[0] = 2 → place at position 2-1 = **1**
- Decrement: cumulative[0] = 1

```
Output: [_, 0ᴳ, _, _, _, _, 3ᴴ, _]
```

#### Processing `3ᶠ` (index 5):
- Value = 3
- cumulative[3] = 6 → place at position 6-1 = **5**
- Decrement: cumulative[3] = 5

```
Output: [_, 0ᴳ, _, _, _, 3ᶠ, 3ᴴ, _]
```

#### Processing `2ᴱ` (index 4):
- Value = 2
- cumulative[2] = 4 → place at position 4-1 = **3**
- Decrement: cumulative[2] = 3

```
Output: [_, 0ᴳ, _, 2ᴱ, _, 3ᶠ, 3ᴴ, _]
```

#### Processing `0ᴰ` (index 3):
- Value = 0
- cumulative[0] = 1 → place at position 1-1 = **0**
- Decrement: cumulative[0] = 0

```
Output: [0ᴰ, 0ᴳ, _, 2ᴱ, _, 3ᶠ, 3ᴴ, _]
```

#### Processing `3ᶜ` (index 2):
- Value = 3
- cumulative[3] = 5 → place at position 5-1 = **4**
- Decrement: cumulative[3] = 4

```
Output: [0ᴰ, 0ᴳ, _, 2ᴱ, 3ᶜ, 3ᶠ, 3ᴴ, _]
```

#### Processing `5ᴮ` (index 1):
- Value = 5
- cumulative[5] = 8 → place at position 8-1 = **7**
- Decrement: cumulative[5] = 7

```
Output: [0ᴰ, 0ᴳ, _, 2ᴱ, 3ᶜ, 3ᶠ, 3ᴴ, 5ᴮ]
```

#### Processing `2ᴬ` (index 0):
- Value = 2
- cumulative[2] = 3 → place at position 3-1 = **2**
- Decrement: cumulative[2] = 2

```
Output: [0ᴰ, 0ᴳ, 2ᴬ, 2ᴱ, 3ᶜ, 3ᶠ, 3ᴴ, 5ᴮ] ✅ STABLE!
```

**Notice**: 
- Among 0s: D before G ✅ (original order)
- Among 2s: A before E ✅ (original order)
- Among 3s: C before F before H ✅ (original order)

## Pseudocode (Stable Version)

```
COUNTING-SORT-STABLE(A, k):
    n = length(A)
    Create output array B[0...n-1]
    Create count array C[0...k] initialized to 0
    
    // Step 1: Count frequencies
    for i = 0 to n - 1:
        C[A[i]] = C[A[i]] + 1
    
    // Step 2: Compute cumulative counts (prefix sums)
    for i = 1 to k:
        C[i] = C[i] + C[i-1]
    
    // Step 3: Place elements (RIGHT TO LEFT for stability)
    for i = n - 1 down to 0:
        value = A[i]
        B[C[value] - 1] = A[i]  // Place at correct position
        C[value] = C[value] - 1  // Decrement count
    
    return B
```

## C++ Implementation (Stable)

```cpp
#include <iostream>
#include <vector>
using namespace std;

struct Element {
    int value;
    char id;  // To track stability
};

void countingSortStable(vector<Element>& arr, int k) {
    int n = arr.size();
    vector<Element> output(n);
    vector<int> count(k + 1, 0);
    
    // Step 1: Count frequencies
    for (int i = 0; i < n; i++) {
        count[arr[i].value]++;
    }
    
    cout << "Counts: ";
    for (int i = 0; i <= k; i++) {
        cout << count[i] << " ";
    }
    cout << "\n";
    
    // Step 2: Cumulative counts
    for (int i = 1; i <= k; i++) {
        count[i] += count[i - 1];
    }
    
    cout << "Cumulative: ";
    for (int i = 0; i <= k; i++) {
        cout << count[i] << " ";
    }
    cout << "\n\n";
    
    // Step 3: Place elements (RIGHT TO LEFT)
    for (int i = n - 1; i >= 0; i--) {
        int value = arr[i].value;
        int pos = count[value] - 1;
        
        cout << "Placing " << value << arr[i].id 
             << " at position " << pos << "\n";
        
        output[pos] = arr[i];
        count[value]--;
    }
    
    // Copy back
    arr = output;
}

int main() {
    vector<Element> arr = {
        {2, 'A'}, {5, 'B'}, {3, 'C'}, {0, 'D'}, 
        {2, 'E'}, {3, 'F'}, {0, 'G'}, {3, 'H'}
    };
    int k = 5;
    
    cout << "Original: ";
    for (auto& e : arr) {
        cout << e.value << e.id << " ";
    }
    cout << "\n\n";
    
    countingSortStable(arr, k);
    
    cout << "\nSorted: ";
    for (auto& e : arr) {
        cout << e.value << e.id << " ";
    }
    cout << "\n";
    
    return 0;
}
```

**Output**:
```
Original: 2A 5B 3C 0D 2E 3F 0G 3H 

Counts: 2 0 2 3 0 1 
Cumulative: 2 2 4 7 7 8 

Placing 3H at position 6
Placing 0G at position 1
Placing 3F at position 5
Placing 2E at position 3
Placing 0D at position 0
Placing 3C at position 4
Placing 5B at position 7
Placing 2A at position 2

Sorted: 0D 0G 2A 2E 3C 3F 3H 5B  ✅ STABLE!
```

## Why Right-to-Left Ensures Stability

**Key**: Elements appear in the original array from **left to right**.

When we process **right to left**:
- Later occurrences (rightmost) get placed **first**
- Earlier occurrences (leftmost) get placed **after**, but in positions to the **left**

Since `cumulative[value]` decrements, earlier elements get **earlier positions** among same values.

## Comparison: Basic vs Stable

| Feature | Basic | Stable |
|---------|-------|--------|
| Time | Θ(n + k) | Θ(n + k) |
| Space | O(k) | O(n + k) |
| Stability | ❌ | ✅ |
| Implementation | Simpler | Needs prefix sums |
| Use in Radix Sort | ❌ | ✅ |

---

**Next**: Summary of complexity bounds.