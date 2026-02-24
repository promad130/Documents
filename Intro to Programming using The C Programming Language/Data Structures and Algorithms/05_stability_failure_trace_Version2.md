# Stability Failure Trace: When Radix Sort Breaks

## The Scenario

Let's trace **exactly** what happens when we use an **unstable** sorting method for digits in Radix Sort.

## Example Array

```
[170ᵃ, 170ᵇ, 45, 2]
```

(We label the two 170s to track their original order)

## Correct Trace (Stable Sorting)

### Pass 1: Sort by ones digit (stable)

```
Digit:  0    0    5    2
Data:  170ᵃ 170ᵇ  45   2

After sorting:
[170ᵃ, 170ᵇ, 2, 45]  ← 170ᵃ still before 170ᵇ ✅
```

### Pass 2: Sort by tens digit (stable)

```
Digit:  7    7    0    4
Data:  170ᵃ 170ᵇ  2   45

After sorting:
[2, 45, 170ᵃ, 170ᵇ]  ← 170ᵃ still before 170ᵇ ✅
```

### Pass 3: Sort by hundreds digit (stable)

```
Digit:  0    0    1    1
Data:   2   45  170ᵃ 170ᵇ

After sorting:
[2, 45, 170ᵃ, 170ᵇ]  ← CORRECT! ✅
```

---

## Incorrect Trace (Unstable Sorting)

### Pass 1: Sort by ones digit (UNSTABLE)

```
Digit:  0    0    5    2
Data:  170ᵃ 170ᵇ  45   2

After UNSTABLE sorting:
[170ᵇ, 170ᵃ, 2, 45]  ← Order FLIPPED! ❌
```

### Pass 2: Sort by tens digit (UNSTABLE)

```
Digit:  7    7    0    4
Data:  170ᵇ 170ᵃ  2   45

After UNSTABLE sorting:
[2, 45, 170ᵃ, 170ᵇ]  ← Looks OK so far...
```

### Pass 3: Sort by hundreds digit (UNSTABLE)

```
Digit:  0    0    1    1
Data:   2   45  170ᵃ 170ᵇ

After UNSTABLE sorting:
[2, 45, 170ᵇ, 170ᵃ]  ← WRONG ORDER! ❌
```

The final result has **170ᵇ before 170ᵃ**, violating the original order!

---

## Concrete Failure: Different Numbers

Let's use a more dramatic example:

**Array**: `[329, 457, 657, 839, 436, 720, 355]`

### Unstable Sort on Digit 0 (ones)

```
Original:  329  457  657  839  436  720  355
Ones:       9    7    7    9    6    0    5

Unstable result: [720, 355, 436, 457, 657, 329, 839]
                                    ↑    ↑    ↑
                           657 before 329 before 839 (7 before 9s)
```

### Unstable Sort on Digit 1 (tens)

```
Input:     720  355  436  457  657  329  839
Tens:       2    5    3    5    5    2    3

Unstable result: [720, 329, 436, 839, 657, 457, 355]
                                     ↑    ↑    ↑
                              All have tens=5, but order scrambled
```

### Unstable Sort on Digit 2 (hundreds)

```
Input:     720  329  436  839  657  457  355
Hundreds:   7    3    4    8    6    4    3

Final: [329, 355, 436, 457, 657, 720, 839]
```

**Compare to correct answer**: `[329, 355, 436, 457, 657, 720, 839]`

In this case, we got lucky! But change one number and it breaks.

---

## Mathematical Proof of Failure

**Claim**: If the digit sort is unstable, Radix Sort can produce incorrect output.

**Proof by counterexample**:

Consider array `A = [20, 10]` with 2 digits.

**Correct sorted output**: `[10, 20]`

### With Stable Sort:
- Pass 1 (ones): `[20, 10]` (both have same ones digit 0, order preserved)
- Pass 2 (tens): `[10, 20]` ✅

### With Unstable Sort:
- Pass 1 (ones): `[10, 20]` (order can flip arbitrarily)
- Pass 2 (tens): `[10, 20]` (happens to be correct)

**Better counterexample**: `A = [11, 21, 12]`

### With Unstable Sort:
- Pass 1 (ones): Sorting by digit 1 or 2:
  - `[11, 21, 12]` → `[11ᵃ, 21, 12]` or `[21, 11ᵃ, 12]` (unstable on 1s)
  
Let's say we get: `[21, 11, 12]`

- Pass 2 (tens): Sorting by digit 1 or 2:
  - Tens: `2, 1, 1`
  - Result: `[11, 12, 21]` ✅ (correct by luck)

**Counterexample that truly fails**: `A = [BA, BB, AA]` (where B > A)

- Pass 1 on 2nd digit: `[BA, BB, AA]` → `[BA, AA, BB]` (unstable, AA and BA flipped)
- Pass 2 on 1st digit: `[BA, AA, BB]` → `[AA, BA, BB]` ✅ (correct)

Hmm, still works...

### The Real Counterexample

`A = [13, 12, 23, 22]`

**Correct**: `[12, 13, 22, 23]`

- Pass 1 (ones): Sort by 3,2,3,2
  - Unstable: `[12, 22, 13, 23]` (swapped within 2s and 3s)
  
- Pass 2 (tens): Sort by 1,2,1,2
  - Unstable: `[13, 12, 23, 22]` ❌ WRONG!

**Conclusion**: Unstable sorting **will** produce incorrect results for Radix Sort.

---

## C++ Demo: Unstable vs Stable

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Num {
    int value;
    char id;  // To track original position
};

int getDigit(int num, int pos) {
    return (num / (int)pow(10, pos)) % 10;
}

// UNSTABLE sort on digit (using regular sort)
void unstableDigitSort(vector<Num>& arr, int pos) {
    sort(arr.begin(), arr.end(), [pos](const Num& a, const Num& b) {
        return getDigit(a.value, pos) < getDigit(b.value, pos);
    });
}

// STABLE sort on digit (using stable_sort)
void stableDigitSort(vector<Num>& arr, int pos) {
    stable_sort(arr.begin(), arr.end(), [pos](const Num& a, const Num& b) {
        return getDigit(a.value, pos) < getDigit(b.value, pos);
    });
}

int main() {
    vector<Num> arr1 = {{13, 'A'}, {12, 'B'}, {23, 'C'}, {22, 'D'}};
    vector<Num> arr2 = arr1;  // Copy for stable version
    
    cout << "=== UNSTABLE RADIX SORT ===\n";
    for (int pos = 0; pos < 2; pos++) {
        unstableDigitSort(arr1, pos);
        cout << "After digit " << pos << ": ";
        for (auto& n : arr1) cout << n.value << n.id << " ";
        cout << "\n";
    }
    
    cout << "\n=== STABLE RADIX SORT ===\n";
    for (int pos = 0; pos < 2; pos++) {
        stableDigitSort(arr2, pos);
        cout << "After digit " << pos << ": ";
        for (auto& n : arr2) cout << n.value << n.id << " ";
        cout << "\n";
    }
    
    return 0;
}
```

**Possible Output**:
```
=== UNSTABLE RADIX SORT ===
After digit 0: 22D 12B 13A 23C 
After digit 1: 13A 12B 23C 22D   ❌ WRONG!

=== STABLE RADIX SORT ===
After digit 0: 12B 22D 13A 23C 
After digit 1: 12B 13A 22D 23C   ✅ CORRECT!
```

---

**Key Takeaway**: **Radix Sort REQUIRES stable sorting for each digit pass, or it will fail.**

---

**Next**: Counting Sort - the stable building block.