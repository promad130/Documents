# Complete Demo: All Linear Sorting Algorithms

## Comprehensive C++ Implementation

This file contains a **complete, runnable implementation** of all three linear sorting algorithms with detailed output.

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <random>
using namespace std;

// ==================== COUNTING SORT ====================

void countingSortStable(vector<int>& arr, int k) {
    int n = arr.size();
    vector<int> output(n);
    vector<int> count(k + 1, 0);
    
    cout << "=== COUNTING SORT ===\n";
    cout << "Input: ";
    for (int x : arr) cout << x << " ";
    cout << "\n\n";
    
    // Step 1: Count
    for (int i = 0; i < n; i++) {
        count[arr[i]]++;
    }
    
    cout << "Counts:\n";
    for (int i = 0; i <= k; i++) {
        if (count[i] > 0) {
            cout << "  " << i << ": " << count[i] << "\n";
        }
    }
    
    // Step 2: Cumulative
    for (int i = 1; i <= k; i++) {
        count[i] += count[i - 1];
    }
    
    cout << "\nCumulative counts:\n";
    for (int i = 0; i <= k; i++) {
        cout << "  " << i << ": " << count[i] << "\n";
    }
    
    // Step 3: Place (right to left for stability)
    cout << "\nPlacement (right to left):\n";
    for (int i = n - 1; i >= 0; i--) {
        int value = arr[i];
        int pos = count[value] - 1;
        cout << "  arr[" << i << "]=" << value << " → position " << pos << "\n";
        output[pos] = value;
        count[value]--;
    }
    
    arr = output;
    
    cout << "\nSorted: ";
    for (int x : arr) cout << x << " ";
    cout << "\n\n";
}

// ==================== RADIX SORT ====================

int getDigit(int num, int pos) {
    return (num / (int)pow(10, pos)) % 10;
}

void countingSortByDigit(vector<int>& arr, int pos) {
    int n = arr.size();
    vector<int> output(n);
    vector<int> count(10, 0);
    
    // Count
    for (int i = 0; i < n; i++) {
        int digit = getDigit(arr[i], pos);
        count[digit]++;
    }
    
    // Cumulative
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }
    
    // Place (backwards for stability)
    for (int i = n - 1; i >= 0; i--) {
        int digit = getDigit(arr[i], pos);
        output[count[digit] - 1] = arr[i];
        count[digit]--;
    }
    
    arr = output;
}

void radixSort(vector<int>& arr) {
    cout << "=== RADIX SORT ===\n";
    cout << "Input: ";
    for (int x : arr) cout << x << " ";
    cout << "\n\n";
    
    // Find max to determine number of digits
    int maxNum = *max_element(arr.begin(), arr.end());
    int numDigits = 0;
    int temp = maxNum;
    while (temp > 0) {
        numDigits++;
        temp /= 10;
    }
    
    cout << "Max number: " << maxNum << " → " << numDigits << " digits\n\n";
    
    // Sort by each digit
    for (int pos = 0; pos < numDigits; pos++) {
        cout << "Pass " << (pos + 1) << " (digit position " << pos << "):\n";
        
        cout << "  Before: ";
        for (int x : arr) {
            cout << x << "(" << getDigit(x, pos) << ") ";
        }
        cout << "\n";
        
        countingSortByDigit(arr, pos);
        
        cout << "  After:  ";
        for (int x : arr) cout << x << " ";
        cout << "\n\n";
    }
    
    cout << "Final sorted: ";
    for (int x : arr) cout << x << " ";
    cout << "\n\n";
}

// ==================== BUCKET SORT ====================

void bucketSort(vector<double>& arr) {
    int n = arr.size();
    vector<vector<double>> buckets(n);
    
    cout << "=== BUCKET SORT ===\n";
    cout << "Input: ";
    for (double x : arr) cout << x << " ";
    cout << "\n\n";
    
    // Distribute
    cout << "Distribution:\n";
    for (int i = 0; i < n; i++) {
        int bucketIndex = (int)(n * arr[i]);
        if (bucketIndex == n) bucketIndex = n - 1;
        
        cout << "  " << arr[i] << " → bucket " << bucketIndex << "\n";
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    cout << "\nBuckets after distribution:\n";
    for (int i = 0; i < n; i++) {
        if (!buckets[i].empty()) {
            cout << "  Bucket " << i << " [" << (double)i/n << ", " 
                 << (double)(i+1)/n << "): ";
            for (double x : buckets[i]) cout << x << " ";
            cout << "(" << buckets[i].size() << " elements)\n";
        }
    }
    
    // Sort each bucket
    cout << "\nSorting buckets:\n";
    for (int i = 0; i < n; i++) {
        if (!buckets[i].empty()) {
            cout << "  Bucket " << i << " before: ";
            for (double x : buckets[i]) cout << x << " ";
            
            sort(buckets[i].begin(), buckets[i].end());
            
            cout << " → after: ";
            for (double x : buckets[i]) cout << x << " ";
            cout << "\n";
        }
    }
    
    // Concatenate
    int index = 0;
    for (int i = 0; i < n; i++) {
        for (double x : buckets[i]) {
            arr[index++] = x;
        }
    }
    
    cout << "\nFinal sorted: ";
    for (double x : arr) cout << x << " ";
    cout << "\n\n";
}

// ==================== MAIN ====================

int main() {
    // Test Counting Sort
    vector<int> arr1 = {2, 5, 3, 0, 2, 3, 0, 3};
    countingSortStable(arr1, 5);
    
    cout << "\n" << string(60, '=') << "\n\n";
    
    // Test Radix Sort
    vector<int> arr2 = {170, 45, 75, 90, 802, 24, 2, 66};
    radixSort(arr2);
    
    cout << "\n" << string(60, '=') << "\n\n";
    
    // Test Bucket Sort
    vector<double> arr3 = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
    bucketSort(arr3);
    
    cout << "\n" << string(60, '=') << "\n\n";
    
    // Bonus: Sorting dates with Radix Sort
    cout << "=== BONUS: SORTING DATES WITH RADIX SORT ===\n\n";
    
    struct Date {
        int day, month, year;
        int toInt() const { return year * 10000 + month * 100 + day; }
        void print() const { 
            cout << (day < 10 ? "0" : "") << day << "-" 
                 << (month < 10 ? "0" : "") << month << "-" << year; 
        }
    };
    
    vector<Date> dates = {
        {15, 3, 1995}, {22, 1, 1995}, {15, 1, 1996}, {10, 1, 1995}
    };
    
    cout << "Original dates:\n";
    for (auto& d : dates) {
        cout << "  ";
        d.print();
        cout << " (as int: " << d.toInt() << ")\n";
    }
    
    // Convert to integers and sort
    vector<int> dateInts;
    for (auto& d : dates) {
        dateInts.push_back(d.toInt());
    }
    
    radixSort(dateInts);
    
    cout << "Sorted dates:\n";
    for (int di : dateInts) {
        int year = di / 10000;
        int month = (di / 100) % 100;
        int day = di % 100;
        cout << "  " << (day < 10 ? "0" : "") << day << "-" 
             << (month < 10 ? "0" : "") << month << "-" << year << "\n";
    }
    
    return 0;
}
```

---

## Sample Run

Compile and run:

```bash
g++ -std=c++17 -o linear_sort linear_sort.cpp
./linear_sort
```

**Expected output** (abbreviated):

```
=== COUNTING SORT ===
Input: 2 5 3 0 2 3 0 3 

Counts:
  0: 2
  2: 2
  3: 3
  5: 1

Cumulative counts:
  0: 2
  1: 2
  2: 4
  3: 7
  4: 7
  5: 8

Placement (right to left):
  arr[7]=3 → position 6
  arr[6]=0 → position 1
  arr[5]=3 → position 5
  arr[4]=2 → position 3
  arr[3]=0 → position 0
  arr[2]=3 → position 4
  arr[1]=5 → position 7
  arr[0]=2 → position 2

Sorted: 0 0 2 2 3 3 3 5 

============================================================

=== RADIX SORT ===
Input: 170 45 75 90 802 24 2 66 

Max number: 802 → 3 digits

Pass 1 (digit position 0):
  Before: 170(0) 45(5) 75(5) 90(0) 802(2) 24(4) 2(2) 66(6) 
  After:  170 90 802 2 24 45 75 66 

Pass 2 (digit position 1):
  Before: 170(7) 90(9) 802(0) 2(0) 24(2) 45(4) 75(7) 66(6) 
  After:  802 2 24 45 66 170 75 90 

Pass 3 (digit position 2):
  Before: 802(8) 2(0) 24(0) 45(0) 66(0) 170(1) 75(0) 90(0) 
  After:  2 24 45 66 75 90 170 802 

Final sorted: 2 24 45 66 75 90 170 802 

============================================================

=== BUCKET SORT ===
Input: 0.78 0.17 0.39 0.26 0.72 0.94 0.21 0.12 0.23 0.68 

Distribution:
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

Buckets after distribution:
  Bucket 1 [0.1, 0.2): 0.17 0.12 (2 elements)
  Bucket 2 [0.2, 0.3): 0.26 0.21 0.23 (3 elements)
  Bucket 3 [0.3, 0.4): 0.39 (1 elements)
  Bucket 6 [0.6, 0.7): 0.68 (1 elements)
  Bucket 7 [0.7, 0.8): 0.78 0.72 (2 elements)
  Bucket 9 [0.9, 1): 0.94 (1 elements)

Sorting buckets:
  Bucket 1 before: 0.17 0.12  → after: 0.12 0.17 
  Bucket 2 before: 0.26 0.21 0.23  → after: 0.21 0.23 0.26 
  Bucket 3 before: 0.39  → after: 0.39 
  Bucket 6 before: 0.68  → after: 0.68 
  Bucket 7 before: 0.78 0.72  → after: 0.72 0.78 
  Bucket 9 before: 0.94  → after: 0.94 

Final sorted: 0.12 0.17 0.21 0.23 0.26 0.39 0.68 0.72 0.78 0.94 
```

---

## Test Your Understanding

Try modifying the code to:

1. **Test stability**: Add IDs to elements and verify stable sorting
2. **Worst case Bucket Sort**: Create all elements in one bucket
3. **Different bases for Radix Sort**: Use base 2 or base 256
4. **Sort strings**: Adapt Radix Sort to sort fixed-length strings

---

**Congratulations!** You've mastered linear time sorting algorithms! 🎉