# Radix Sort: Digit-by-Digit Sorting

## The Algorithm

**Radix Sort** sorts integers by processing their **digits** from **least significant** to **most significant**.

### Key Properties
- **Time Complexity**: Θ(d(n + k))
  - `d` = number of digits
  - `n` = number of elements
  - `k` = range of each digit (e.g., 10 for decimal)
- **Space**: O(n + k)
- **Requires**: Stable sorting for each digit

## How It Works

### Example: Sorting [170, 45, 75, 90, 802, 24, 2, 66]

**Digit positions** (base 10):
- d₀: ones place (least significant)
- d₁: tens place
- d₂: hundreds place (most significant)

**Pass 1: Sort by ones digit (d₀)**
```
170  →  0
 45  →  5
 75  →  5
 90  →  0
802  →  2
 24  →  4
  2  →  2
 66  →  6

Result: [170, 90, 802, 2, 24, 45, 75, 66]
```

**Pass 2: Sort by tens digit (d₁) - STABLE**
```
170  →  7
 90  →  9
802  →  0
  2  →  0
 24  →  2
 45  →  4
 75  →  7
 66  →  6

Result: [802, 2, 24, 45, 66, 170, 75, 90]
```

**Pass 3: Sort by hundreds digit (d₂) - STABLE**
```
802  →  8
  2  →  0
 24  →  0
 45  →  0
 66  →  0
170  →  1
 75  →  0
 90  →  0

Result: [2, 24, 45, 66, 75, 90, 170, 802] ✅ SORTED!
```

## Pseudocode

```
RADIX-SORT(A, d):
    for i = 1 to d:
        STABLE-SORT A on digit i (from right to left)
```

## C++ Implementation (Using Counting Sort for Each Digit)

```cpp
#include <iostream>
#include <vector>
using namespace std;

// Get the digit at position 'pos' (0 = ones, 1 = tens, etc.)
int getDigit(int num, int pos) {
    return (num / (int)pow(10, pos)) % 10;
}

// Stable counting sort on a specific digit position
void countingSortByDigit(vector<int>& arr, int pos) {
    int n = arr.size();
    vector<int> output(n);
    vector<int> count(10, 0);  // Digits 0-9
    
    // Count occurrences of each digit
    for (int i = 0; i < n; i++) {
        int digit = getDigit(arr[i], pos);
        count[digit]++;
    }
    
    // Convert to cumulative count (for stable sorting)
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }
    
    // Build output array (iterate BACKWARDS for stability)
    for (int i = n - 1; i >= 0; i--) {
        int digit = getDigit(arr[i], pos);
        output[count[digit] - 1] = arr[i];
        count[digit]--;
    }
    
    // Copy back
    arr = output;
}

void radixSort(vector<int>& arr) {
    // Find maximum number to know number of digits
    int maxNum = *max_element(arr.begin(), arr.end());
    
    // Get number of digits
    int numDigits = 0;
    int temp = maxNum;
    while (temp > 0) {
        numDigits++;
        temp /= 10;
    }
    
    // Sort by each digit
    for (int pos = 0; pos < numDigits; pos++) {
        cout << "Sorting by digit at position " << pos << ":\n";
        countingSortByDigit(arr, pos);
        
        for (int x : arr) cout << x << " ";
        cout << "\n\n";
    }
}

int main() {
    vector<int> arr = {170, 45, 75, 90, 802, 24, 2, 66};
    
    cout << "Original: ";
    for (int x : arr) cout << x << " ";
    cout << "\n\n";
    
    radixSort(arr);
    
    cout << "Final sorted: ";
    for (int x : arr) cout << x << " ";
    cout << "\n";
    
    return 0;
}
```

**Output**:
```
Original: 170 45 75 90 802 24 2 66 

Sorting by digit at position 0:
170 90 802 2 24 45 75 66 

Sorting by digit at position 1:
802 2 24 45 66 170 75 90 

Sorting by digit at position 2:
2 24 45 66 75 90 170 802 

Final sorted: 2 24 45 66 75 90 170 802
```

## Time Complexity Analysis

- **Number of passes**: d (number of digits)
- **Each pass**: O(n + k) using Counting Sort
  - n = array size
  - k = base (10 for decimal)
- **Total**: Θ(d(n + k))

### When is this linear?

If **d is constant** (e.g., 32-bit integers have at most 10 digits):
- Θ(d(n + k)) = Θ(n) when d and k are constants

## Space Complexity

- O(n + k) for the counting sort auxiliary arrays

## Variants

### Different Bases

Instead of base 10, use base 2ᵇ:
- **Base 256 (b=8)**: Process bytes instead of digits
- Reduces number of passes but increases k

### Example: Base 256 for 32-bit integers
- d = 4 (4 bytes)
- k = 256
- Time: Θ(4(n + 256)) = Θ(n)

---

**Next**: What happens if our digit sort is unstable?