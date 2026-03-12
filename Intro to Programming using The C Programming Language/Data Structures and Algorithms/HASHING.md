![[Introduction to Data Structures And Algorithm#HASHING]]

## What is Hashing?

**Hashing** is a technique used to store and retrieve data almost instantly—in **O(1)** (constant) time. Instead of searching through a list one by one to find what you need, you use the data itself to calculate its exact location in memory.

## The Motivation: Why Do We Need Hashing?

### Problem: Finding an Element After Random Modifications

Imagine you're given an array, but the moment you receive it, it gets sorted by someone and duplicates are removed. You don't know the index of any element anymore, and you need to answer queries about element indices **instantaneously**.

**Naive Approach:**

```cpp name=naive_search.cpp
int findIndex(vector<int>& arr, int target) {
    for (int i = 0; i < arr.size(); i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1;  // O(n) time - TOO SLOW!
}
```

**Problem:** This takes **O(n)** time for each query. If you have **q** queries, total time is **O(n × q)**.

### Better Approach: Hash Array (Direct Addressing)

```cpp name=direct_address_array.cpp
int findIndex(int n, int target) {
    vector<int> A(n);
    vector<int> B(MAX_VALUE);  // Hash array
    
    for (int i = 0; i < n; i++) {
        cin >> A[i];
        B[A[i]] = i;  // Store index at position A[i]
    }
    
    // After modifications...
    
    if (target < MAX_VALUE) {
        return B[target];  // O(1) lookup!
    }
    return -1;
}
```

This **significantly reduces** lookup time to **O(1)** because we use the value itself as an index.

---

## 1. Direct Address Tables (DAT)

A **Direct Address Table** is the simplest form of hashing where we use the **key itself as the index** in an array.

### Characteristics

- **Key = Index**: The value of the key directly maps to an array index
- **Time Complexity**: O(1) for insert, delete, and search
- **Space**: O(U) where U is the universe of possible keys

### Example: Number Counting

```cpp name=number_counting.cpp
#include <iostream>
#include <vector>
using namespace std;

int main() {
    int n;
    cin >> n;
    
    vector<int> arr(n);
    vector<int> hash(100, 0);  // Direct address table for values 0-99
    
    // Insert and count
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
        hash[arr[i]]++;  // Use value as index directly
    }
    
    // Query
    int q;
    cin >> q;
    
    while (q--) {
        int target;
        cin >> target;
        cout << "Count of " << target << ": " << hash[target] << endl;
    }
    
    return 0;
}
```

**Input:**
```
7
1 4 1 2 4 1 5
3
1
4
5
```

**Output:**
```
Count of 1: 3
Count of 2: 1
Count of 4: 2
Count of 5: 1
```

### Character Hashing (Direct Addressing)

```cpp name=character_hashing.cpp
#include <iostream>
#include <string>
using namespace std;

int main() {
    string s;
    cin >> s;
    
    // Hash table for lowercase letters
    int hash[26] = {0};
    
    for (int i = 0; i < s.size(); i++) {
        hash[s[i] - 'a']++;  // Map 'a'->0, 'b'->1, ..., 'z'->25
    }
    
    int q;
    cin >> q;
    
    while (q--) {
        char c;
        cin >> c;
        cout << "Count of '" << c << "': " << hash[c - 'a'] << endl;
    }
    
    return 0;
}
```

**Input:**
```
programming
3
g
m
a
```

**Output:**
```
Count of 'g': 2
Count of 'm': 2
Count of 'a': 1
```

### Limitations of Direct Address Tables

**Huge memory waste** if keys are sparse (e.g., keys: 1, 1000000)
**Can't handle negative numbers** directly
**Can't handle non-integer keys** (strings, floats)
**Universe size must be small** (can't create array of size 10^9)

**Example of Problem:**

```cpp
// If you need to store keys: {1, 1000000000}
int hash[1000000000];  // ❌ 4GB of memory for 2 values!
```

---
## 2. Hash Tables

A **Hash Table** solves the limitations of direct addressing by using a **hash function** to map keys to a smaller range of indices.

### Hash Function

A **hash function** `h(k)` maps a key `k` from a large universe to a smaller range `[0, m-1]` where `m` is the table size.

```
h(k) = k mod m
```

### Example: Basic Hash Table

```cpp name=basic_hash_table.cpp
#include <iostream>
#include <vector>
using namespace std;

class HashTable {
private:
    vector<int> table;
    int size;
    
    int hashFunction(int key) {
        return key % size;  // Simple modulo hash
    }
    
public:
    HashTable(int m) : size(m), table(m, -1) {}
    
    void insert(int key) {
        int index = hashFunction(key);
        table[index] = key;
        cout << "Inserted " << key << " at index " << index << endl;
    }
    
    bool search(int key) {
        int index = hashFunction(key);
        return table[index] == key;
    }
    
    void display() {
        cout << "\nHash Table:" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Index " << i << ": ";
            if (table[i] != -1) {
                cout << table[i];
            } else {
                cout << "empty";
            }
            cout << endl;
        }
    }
};

int main() {
    HashTable ht(10);  // Table size = 10
    
    ht.insert(15);  // 15 % 10 = 5
    ht.insert(25);  // 25 % 10 = 5  ← COLLISION!
    ht.insert(35);  // 35 % 10 = 5  ← COLLISION!
    ht.insert(12);  // 12 % 10 = 2
    
    ht.display();
    
    return 0;
}
```

**Output:**
```
Inserted 15 at index 5
Inserted 25 at index 5
Inserted 35 at index 5
Inserted 12 at index 2

Hash Table:
Index 0: empty
Index 1: empty
Index 2: 12
Index 3: empty
Index 4: empty
Index 5: 35      ← Only last value remains!
Index 6: empty
Index 7: empty
Index 8: empty
Index 9: empty
```

### The Collision Problem

**Collision**: When two different keys hash to the same index.

```
h(15) = 15 % 10 = 5
h(25) = 25 % 10 = 5  ← Same index!
h(35) = 35 % 10 = 5  ← Same index!
```

**This is the fundamental problem in hashing!**

---

## 3. Collision Resolution Techniques

There are two main approaches:

1. **Chaining** (Closed Addressing)
2. **Open Addressing** (Linear Probing, Quadratic Probing, Double Hashing)

---

## 4. Chaining (Separate Chaining)

**Idea**: Each table index stores a **linked list** (or vector) of all keys that hash to that index.

### Visual Representation

```
Table of size 10:

Index 0: []
Index 1: []
Index 2: [12]
Index 3: []
Index 4: []
Index 5: [15] -> [25] -> [35]  ← Chain of collided elements
Index 6: []
Index 7: [7] -> [17]
Index 8: []
Index 9: []
```

### Implementation

```cpp name=chaining_hash_table.cpp
#include <iostream>
#include <vector>
#include <list>
using namespace std;

class HashTableChaining {
private:
    vector<list<int>> table;  // Array of linked lists
    int size;
    
    int hashFunction(int key) {
        return key % size;
    }
    
public:
    HashTableChaining(int m) : size(m), table(m) {}
    
    void insert(int key) {
        int index = hashFunction(key);
        table[index].push_back(key);
        cout << "Inserted " << key << " at index " << index << endl;
    }
    
    bool search(int key) {
        int index = hashFunction(key);
        
        // Search in the chain at this index
        for (int val : table[index]) {
            if (val == key) {
                return true;
            }
        }
        return false;
    }
    
    void remove(int key) {
        int index = hashFunction(key);
        table[index].remove(key);
        cout << "Removed " << key << " from index " << index << endl;
    }
    
    void display() {
        cout << "\nHash Table (with Chaining):" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Index " << i << ": ";
            if (table[i].empty()) {
                cout << "empty";
            } else {
                for (int val : table[i]) {
                    cout << val << " -> ";
                }
                cout << "NULL";
            }
            cout << endl;
        }
    }
};

int main() {
    HashTableChaining ht(10);
    
    ht.insert(15);
    ht.insert(25);
    ht.insert(35);
    ht.insert(12);
    ht.insert(7);
    ht.insert(17);
    
    ht.display();
    
    cout << "\nSearch 25: " << (ht.search(25) ? "Found" : "Not Found") << endl;
    cout << "Search 100: " << (ht.search(100) ? "Found" : "Not Found") << endl;
    
    ht.remove(25);
    ht.display();
    
    return 0;
}
```

**Output:**
```
Inserted 15 at index 5
Inserted 25 at index 5
Inserted 35 at index 5
Inserted 12 at index 2
Inserted 7 at index 7
Inserted 17 at index 7

Hash Table (with Chaining):
Index 0: empty
Index 1: empty
Index 2: 12 -> NULL
Index 3: empty
Index 4: empty
Index 5: 15 -> 25 -> 35 -> NULL  ← Chain stores all collisions
Index 6: empty
Index 7: 7 -> 17 -> NULL
Index 8: empty
Index 9: empty

Search 25: Found
Search 100: Not Found

Removed 25 from index 5

Hash Table (with Chaining):
Index 0: empty
Index 1: empty
Index 2: 12 -> NULL
Index 3: empty
Index 4: empty
Index 5: 15 -> 35 -> NULL  ← 25 removed
Index 6: empty
Index 7: 7 -> 17 -> NULL
Index 8: empty
Index 9: empty
```

### Time Complexity with Chaining

Let **α (alpha) = n/m** be the **load factor**, where:
- **n** = number of keys
- **m** = table size
For example, if you have 50 elements inserted into a hash table that only has 10 slots, your load factor is `50 / 10 = 5`. This means, on average, every chain (or linked list) at each slot is 5 elements long!

| Operation | Average Case | Worst Case |
|-----------|--------------|------------|
| **Insert** | O(1) | O(1) |
| **Search** | O(1 + α) | O(n) |
| **Delete** | O(1 + α) | O(n) |

**Explanation:**
- **Average case**: If hash function distributes uniformly, each chain has length ≈ α
- **Worst case**: All keys hash to same index, chain length = n

#### KEEP IN MIND!

**$\alpha$ only tells you the _average_ chain length, not the _actual_ chain length.** In your example:

- Total elements = 50
    
- Total slots = 100
    
- $\alpha = 50 / 100 = 0.5$
    

Statistically, $\alpha$ looks amazing. But reality is exactly as you described:

- Slot 42: **50 elements**
    
- The other 99 slots: **0 elements**
    

If you try to find an element in Slot 42, you have to walk a linked list of 50 items. The time complexity degrades entirely to **$O(N)$** (where $N$ is the number of elements in that specific chain), completely destroying the $O(1)$ speed we wanted.

---

## 5. Load Factor (α)

The **load factor** measures how "full" the hash table is:

```
α = n / m

where:
n = number of elements
m = table size
```

### Impact on Performance

| Load Factor | Performance | Comment |
|-------------|-------------|---------|
| **α < 0.5** | Excellent (almost O(1)) | Low collisions, wasted space |
| **α ≈ 0.75** | Good (balanced) | Recommended for chaining |
| **α ≈ 1.0** | Acceptable | Average chain length = 1 |
| **α > 1.0** | Degrading | Multiple elements per slot |
| **α >> 1** | Poor (approaching O(n)) | Long chains |

### Example: Load Factor Demonstration

```cpp name=load_factor_demo.cpp
#include <iostream>
#include <vector>
#include <list>
using namespace std;

class LoadFactorDemo {
private:
    vector<list<int>> table;
    int size;
    int count;
    
    int hashFunction(int key) {
        return key % size;
    }
    
public:
    LoadFactorDemo(int m) : size(m), table(m), count(0) {}
    
    void insert(int key) {
        table[hashFunction(key)].push_back(key);
        count++;
    }
    
    double loadFactor() {
        return (double)count / size;
    }
    
    void analyzeDistribution() {
        cout << "\n=== Distribution Analysis ===" << endl;
        cout << "Table size (m): " << size << endl;
        cout << "Elements (n): " << count << endl;
        cout << "Load factor (α): " << loadFactor() << endl;
        
        int maxChainLength = 0;
        int emptySlots = 0;
        
        for (int i = 0; i < size; i++) {
            int chainLen = table[i].size();
            maxChainLength = max(maxChainLength, chainLen);
            if (chainLen == 0) emptySlots++;
        }
        
        cout << "Max chain length: " << maxChainLength << endl;
        cout << "Empty slots: " << emptySlots << " (" 
             << (100.0 * emptySlots / size) << "%)" << endl;
        cout << "Average chain length: " << (double)count / (size - emptySlots) << endl;
    }
};

int main() {
    // Test with different load factors
    
    cout << "=== Low Load Factor (α ≈ 0.5) ===" << endl;
    LoadFactorDemo ht1(20);
    for (int i = 0; i < 10; i++) {
        ht1.insert(i * 7);  // Some pattern
    }
    ht1.analyzeDistribution();
    
    cout << "\n=== Medium Load Factor (α ≈ 1.0) ===" << endl;
    LoadFactorDemo ht2(10);
    for (int i = 0; i < 10; i++) {
        ht2.insert(i * 7);
    }
    ht2.analyzeDistribution();
    
    cout << "\n=== High Load Factor (α ≈ 2.0) ===" << endl;
    LoadFactorDemo ht3(10);
    for (int i = 0; i < 20; i++) {
        ht3.insert(i * 7);
    }
    ht3.analyzeDistribution();
    
    return 0;
}
```

**Output:**
```
=== Low Load Factor (α ≈ 0.5) ===

=== Distribution Analysis ===
Table size (m): 20
Elements (n): 10
Load factor (α): 0.5
Max chain length: 2
Empty slots: 12 (60%)
Average chain length: 1.25

=== Medium Load Factor (α ≈ 1.0) ===

=== Distribution Analysis ===
Table size (m): 10
Elements (n): 10
Load factor (α): 1
Max chain length: 3
Empty slots: 3 (30%)
Average chain length: 1.42857

=== High Load Factor (α ≈ 2.0) ===

=== Distribution Analysis ===
Table size (m): 10
Elements (n): 20
Load factor (α): 2
Max chain length: 5
Empty slots: 3 (30%)
Average chain length: 2.85714
```

---

### 6. Expected Search Time

The **expected search time** in a hash table with chaining is:

```
T(n) = O(1 + α)

where α = n/m
```

#### Breakdown

1. **O(1)**: Time to compute hash function and go to index
2. **O(α)**: Time to search through the chain (average length α)

#### Proof by Example

Suppose:
- Table size m = 10
- Number of keys n = 20
- Load factor α = 20/10 = 2

If keys are uniformly distributed:
- Each of 10 slots has **average** 2 keys
- To search, you:
  1. Hash the key → O(1)
  2. Search chain of length ≈ 2 → O(α) = O(2)
  
Total: O(1 + 2) = O(3) = **O(1)** when α is constant!

#### When is α Constant?

If we maintain **α ≤ constant** (like 0.75 in C++ `unordered_map`), then:
- Search time = O(1 + constant) = **O(1)**

This is why hash tables achieve **amortized O(1)** operations!

---

### 7. Hash Function Design

A good hash function should:
1. **Compute quickly** (O(1))
2. **Distribute uniformly** (minimize collisions)
3. **Use all table slots**
4. **Minimize clustering**

#### Division Method

```cpp
int h(int key, int m) {
    return key % m;
}
```

**Pros:**
- Fast (one modulo operation)
- Simple

**Cons:**
- Bad if m is power of 2 (only uses low-order bits)
- Bad if keys have patterns (e.g., all even)

**Best practice:** Choose m as a **prime number** not close to a power of 2.

```cpp
// Good choices for m:
// 101, 211, 503, 1009, 2003, 5009, 10007
```

---

#### 8. Multiplication Method

The **multiplication method** is more sophisticated and works better for non-integer keys:

```
h(k) = ⌊m × (k × A mod 1)⌋

where:
- A is a constant, 0 < A < 1
- m is the table size, the number of slots alloted
- k × A mod 1 extracts fractional part
- Knuth suggests A ≈ (√5 - 1)/2 ≈ 0.618...
```

##### How It Works

1. Multiply key by constant A
2. Extract fractional part
3. Multiply by table size m
4. Take floor

##### Implementation

```cpp name=multiplication_method.cpp
#include <iostream>
#include <cmath>
using namespace std;

class MultiplicationHash {
private:
    int size;
    const double A = 0.6180339887;  // (√5 - 1) / 2
    
public:
    MultiplicationHash(int m) : size(m) {}
    
    int hash(int key) {
        double product = key * A;
        double fractional = product - floor(product);  // Extract fractional part
        return floor(size * fractional);
    }
    
    void demonstrateHashing() {
        cout << "=== Multiplication Method Demo ===" << endl;
        cout << "Table size: " << size << endl;
        cout << "A = " << A << "\n" << endl;
        
        int keys[] = {123, 456, 789, 1024, 2048, 4096};
        
        for (int key : keys) {
            int index = hash(key);
            cout << "h(" << key << ") = " << index << endl;
        }
    }
};

int main() {
    MultiplicationHash mh(100);
    mh.demonstrateHashing();
    
    return 0;
}
```

**Output:**
```
=== Multiplication Method Demo ===
Table size: 100
A = 0.618034

h(123) = 18
h(456) = 81
h(789) = 44
h(1024) = 68
h(2048) = 37
h(4096) = 75
```

#### Advantages of Multiplication Method

**Value of m not critical** (can be power of 2)
**Works well for various key distributions**
**No requirement for m to be prime**

#### Division vs Multiplication

| Method             | Speed  | m Requirement       | Distribution          |
| ------------------ | ------ | ------------------- | --------------------- |
| **Division**       | Faster | Prime, not near 2^k | Good if m chosen well |
| **Multiplication** | Slower | Any value           | Consistently good     |

---

### 9. Open Addressing

Unlike chaining, **Open Addressing** stores all elements **directly in the hash table** (no chains/lists). When collision occurs, we **probe** for the next available slot.

#### Probe Sequence

```
h(k, i) = (h'(k) + f(i)) mod m

where:
- h'(k) is the primary hash function
- f(i) is the probing function (i = 0, 1, 2, ...)
- i is the probe number
```

#### Visual: Collision Resolution

```
Initial hash: h(k) = 5

Table before collision:
[0] [1] [2] [3] [4] [5] [6] [7] [8] [9]
 -   -   -   -   -  15   -   -   -   -

Insert 25 (also hashes to 5):
- Probe 0: h(25, 0) = 5 → Occupied!
- Probe 1: h(25, 1) = 6 → Available ✓

[0] [1] [2] [3] [4] [5] [6] [7] [8] [9]
 -   -   -   -   -  15  25   -   -   -
```

---

### 10. Linear Probing

**Linear Probing** checks slots sequentially until an empty one is found:

```
h(k, i) = (h'(k) + i) mod m

where i = 0, 1, 2, 3, ...
```

#### Example Sequence

For key k with h'(k) = 5 in table of size 10:

```
Probe 0: h(k, 0) = (5 + 0) % 10 = 5
Probe 1: h(k, 1) = (5 + 1) % 10 = 6
Probe 2: h(k, 2) = (5 + 2) % 10 = 7
Probe 3: h(k, 3) = (5 + 3) % 10 = 8
...
```

#### Implementation

```cpp name=linear_probing.cpp
#include <iostream>
#include <vector>
using namespace std;

class LinearProbingHashTable {
private:
    vector<int> table;
    vector<bool> occupied;
    int size;
    int count;
    
    int hashFunction(int key) {
        return key % size;
    }
    
public:
    LinearProbingHashTable(int m) : size(m), table(m, -1), occupied(m, false), count(0) {}
    
    bool insert(int key) {
        if (count == size) {
            cout << "Table is full!" << endl;
            return false;
        }
        
        int index = hashFunction(key);
        int i = 0;
        int originalIndex = index;
        
        cout << "Inserting " << key << " (initial hash: " << index << ")" << endl;
        
        while (occupied[index]) {
            cout << "  Probe " << i << ": index " << index << " occupied" << endl;
            i++;
            index = (originalIndex + i) % size;  // Linear probing
            
            if (index == originalIndex) { // i.e., i = originalIndex, i.e., it circled one whole round of all the values in the table
                cout << "  Table full after probing!" << endl;
                return false;
            }
        }
        
        table[index] = key;
        occupied[index] = true;
        count++;
        cout << "  Inserted at index " << index << " after " << i << " probes" << endl;
        return true;
    }
    
    bool search(int key) {
        int index = hashFunction(key);
        int i = 0;
        int originalIndex = index;
        
        while (occupied[index]) {
            if (table[index] == key) {
                cout << "Found " << key << " at index " << index 
                     << " after " << i << " probes" << endl;
                return true;
            }
            
            i++;
            index = (originalIndex + i) % size;
            
            if (index == originalIndex) break;
        }
        
        cout << "Key " << key << " not found" << endl;
        return false;
    }
    
    void display() {
        cout << "\nHash Table:" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Index " << i << ": ";
            if (occupied[i]) {
                cout << table[i];
            } else {
                cout << "empty";
            }
            cout << endl;
        }
        cout << "Load factor: " << (double)count / size << endl;
    }
};

int main() {
    cout << "=== Linear Probing Demo ===" << endl;
    
    LinearProbingHashTable ht(10);
    
    ht.insert(15);  // h(15) = 5
    ht.insert(25);  // h(25) = 5 → collision!
    ht.insert(35);  // h(35) = 5 → collision!
    ht.insert(45);  // h(45) = 5 → collision!
    ht.insert(12);  // h(12) = 2
    ht.insert(22);  // h(22) = 2 → collision!
    
    ht.display();
    
    cout << "\n--- Searching ---" << endl;
    ht.search(35);
    ht.search(100);
    
    return 0;
}
```

**Output:**
```
=== Linear Probing Demo ===
Inserting 15 (initial hash: 5)
  Inserted at index 5 after 0 probes
Inserting 25 (initial hash: 5)
  Probe 0: index 5 occupied
  Inserted at index 6 after 1 probes
Inserting 35 (initial hash: 5)
  Probe 0: index 5 occupied
  Probe 1: index 6 occupied
  Inserted at index 7 after 2 probes
Inserting 45 (initial hash: 5)
  Probe 0: index 5 occupied
  Probe 1: index 6 occupied
  Probe 2: index 7 occupied
  Inserted at index 8 after 3 probes
Inserting 12 (initial hash: 2)
  Inserted at index 2 after 0 probes
Inserting 22 (initial hash: 2)
  Probe 0: index 2 occupied
  Inserted at index 3 after 1 probes

Hash Table:
Index 0: empty
Index 1: empty
Index 2: 12
Index 3: 22
Index 4: empty
Index 5: 15
Index 6: 25
Index 7: 35
Index 8: 45
Index 9: empty
Load factor: 0.6

--- Searching ---
Found 35 at index 7 after 2 probes
Key 100 not found
```

### Primary Clustering Problem

**Primary Clustering**: Consecutive occupied slots form "clusters". New keys that hash anywhere in the cluster must probe through the entire cluster.

```
Before:
[0] [1] [2] [3] [4] [5] [6] [7] [8] [9]
 -   -  12   -   -  15  25  35  45   -
             ↑         └─────────┘
         Cluster        Main cluster

Problem: Any key hashing to 5, 6, 7, or 8 must probe through all!
```

---

## 11. Quadratic Probing

**Quadratic Probing** uses a quadratic function to calculate the next probe position:

```
h(k, i) = (h'(k) + c₁ × i + c₂ × i²) mod m

Common: c₁ = 1, c₂ = 1
Simplified: h(k, i) = (h'(k) + i²) mod m
```

### Probe Sequence

For key k with h'(k) = 5:

```
Probe 0: (5 + 0²) % 10 = 5
Probe 1: (5 + 1²) % 10 = 6
Probe 2: (5 + 2²) % 10 = 9
Probe 3: (5 + 3²) % 10 = 4
Probe 4: (5 + 4²) % 10 = 1
Probe 5: (5 + 5²) % 10 = 0
...
```

### Implementation

```cpp name=quadratic_probing.cpp
#include <iostream>
#include <vector>
using namespace std;

class QuadraticProbingHashTable {
private:
    vector<int> table;
    vector<bool> occupied;
    int size;
    int count;
    
    int hashFunction(int key) {
        return key % size;
    }
    
public:
    QuadraticProbingHashTable(int m) : size(m), table(m, -1), occupied(m, false), count(0) {}
    
    bool insert(int key) {
        if (count == size) {
            cout << "Table is full!" << endl;
            return false;
        }
        
        int h = hashFunction(key);
        cout << "Inserting " << key << " (initial hash: " << h << ")" << endl;
        
        for (int i = 0; i < size; i++) {
            int index = (h + i * i) % size;  // Quadratic probing
            
            if (!occupied[index]) {
                table[index] = key;
                occupied[index] = true;
                count++;
                cout << "  Inserted at index " << index << " after " << i << " probes" << endl;
                return true;
            }
            
            cout << "  Probe " << i << ": index " << index << " occupied" << endl;
        }
        
        cout << "  Could not insert (table full or no available slot)" << endl;
        return false;
    }
    
    bool search(int key) {
        int h = hashFunction(key);
        
        for (int i = 0; i < size; i++) {
            int index = (h + i * i) % size;
            
            if (!occupied[index]) {
                cout << "Key " << key << " not found (empty slot at probe " << i << ")" << endl;
                return false;
            }
            
            if (table[index] == key) {
                cout << "Found " << key << " at index " << index 
                     << " after " << i << " probes" << endl;
                return true;
            }
        }
        
        cout << "Key " << key << " not found" << endl;
        return false;
    }
    
    void display() {
        cout << "\nHash Table:" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Index " << i << ": ";
            if (occupied[i]) {
                cout << table[i];
            } else {
                cout << "empty";
            }
            cout << endl;
        }
        cout << "Load factor: " << (double)count / size << endl;
    }
};

int main() {
    cout << "=== Quadratic Probing Demo ===" << endl;
    
    QuadraticProbingHashTable ht(11);  // Use prime size
    
    ht.insert(15);  // h(15) = 4
    ht.insert(26);  // h(26) = 4 → collision!
    ht.insert(37);  // h(37) = 4 → collision!
    ht.insert(48);  // h(48) = 4 → collision!
    ht.insert(59);  // h(59) = 4 → collision!
    
    ht.display();
    
    cout << "\n--- Searching ---" << endl;
    ht.search(37);
    ht.search(100);
    
    return 0;
}
```

**Output:**
```
=== Quadratic Probing Demo ===
Inserting 15 (initial hash: 4)
  Inserted at index 4 after 0 probes
Inserting 26 (initial hash: 4)
  Probe 0: index 4 occupied
  Inserted at index 5 after 1 probes
Inserting 37 (initial hash: 4)
  Probe 0: index 4 occupied
  Probe 1: index 5 occupied
  Inserted at index 8 after 2 probes
Inserting 48 (initial hash: 4)
  Probe 0: index 4 occupied
  Probe 1: index 5 occupied
  Probe 2: index 8 occupied
  Inserted at index 2 after 3 probes
Inserting 59 (initial hash: 4)
  Probe 0: index 4 occupied
  Probe 1: index 5 occupied
  Probe 2: index 8 occupied
  Probe 3: index 2 occupied
  Inserted at index 9 after 4 probes

Hash Table:
Index 0: empty
Index 1: empty
Index 2: 48
Index 3: empty
Index 4: 15
Index 5: 26
Index 6: empty
Index 7: empty
Index 8: 37
Index 9: 59
Index 10: empty
Load factor: 0.454545

--- Searching ---
Found 37 at index 8 after 2 probes
Key 100 not found (empty slot at probe 0)
```

### Advantages Over Linear Probing

✅ **Reduces primary clustering**: Probes jump around instead of sequential
✅ **Better cache performance** than random probing
✅ **Faster average search time**

### Secondary Clustering Problem

**Secondary Clustering**: Keys with the same initial hash follow the same probe sequence.

```
If h(k₁) = h(k₂) = 5, then:
- Both try: 5, 6, 9, 4, 1, 0, ...
- Same sequence causes clustering
```


## 12. Double Hashing

**Double Hashing** uses a **second hash function** to calculate the probe step size:

```
h(k, i) = (h₁(k) + i × h₂(k)) mod m

where:
- h₁(k) is the primary hash
- h₂(k) is the secondary hash (probe step)
- h₂(k) must never return 0!
```

### Common Choice for h₂(k)

```
h₁(k) = k mod m
h₂(k) = 1 + (k mod (m - 1))

Note: h₂(k) ∈ [1, m-1], never 0
```

### Implementation

```cpp name=double_hashing.cpp
#include <iostream>
#include <vector>
using namespace std;

class DoubleHashingTable {
private:
    vector<int> table;
    vector<bool> occupied;
    int size;
    int count;
    
    int h1(int key) {
        return key % size;
    }
    
    int h2(int key) {
        // Ensure h2 never returns 0 and is coprime with size
        return 1 + (key % (size - 1));
    }
    
public:
    DoubleHashingTable(int m) : size(m), table(m, -1), occupied(m, false), count(0) {}
    
    bool insert(int key) {
        if (count == size) {
            cout << "Table is full!" << endl;
            return false;
        }
        
        int hash1 = h1(key);
        int hash2 = h2(key);
        
        cout << "Inserting " << key << endl;
        cout << "  h1(" << key << ") = " << hash1 << endl;
        cout << "  h2(" << key << ") = " << hash2 << endl;
        
        for (int i = 0; i < size; i++) {
            int index = (hash1 + i * hash2) % size;  // Double hashing
            
            if (!occupied[index]) {
                table[index] = key;
                occupied[index] = true;
                count++;
                cout << "  Inserted at index " << index << " after " << i << " probes" << endl;
                return true;
            }
            
            cout << "  Probe " << i << ": index " << index << " occupied" << endl;
        }
        
        return false;
    }
    
    bool search(int key) {
        int hash1 = h1(key);
        int hash2 = h2(key);
        
        for (int i = 0; i < size; i++) {
            int index = (hash1 + i * hash2) % size;
            
            if (!occupied[index]) {
                cout << "Key " << key << " not found" << endl;
                return false;
            }
            
            if (table[index] == key) {
                cout << "Found " << key << " at index " << index 
                     << " after " << i << " probes" << endl;
                return true;
            }
        }
        
        cout << "Key " << key << " not found" << endl;
        return false;
    }
    
    void display() {
        cout << "\nHash Table:" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Index " << i << ": ";
            if (occupied[i]) {
                cout << table[i];
            } else {
                cout << "empty";
            }
            cout << endl;
        }
        cout << "Load factor: " << (double)count / size << endl;
    }
};

int main() {
    cout << "=== Double Hashing Demo ===" << endl;
    
    DoubleHashingTable ht(11);  // Prime size
    
    ht.insert(15);
    ht.insert(26);
    ht.insert(37);
    ht.insert(48);
    ht.insert(59);
    
    ht.display();
    
    cout << "\n--- Searching ---" << endl;
    ht.search(37);
    ht.search(100);
    
    return 0;
}
```

**Output:**
```
=== Double Hashing Demo ===
Inserting 15
  h1(15) = 4
  h2(15) = 6
  Inserted at index 4 after 0 probes
Inserting 26
  h1(26) = 4
  h2(26) = 7
  Probe 0: index 4 occupied
  Inserted at index 0 after 1 probes
Inserting 37
  h1(37) = 4
  h2(37) = 8
  Probe 0: index 4 occupied
  Inserted at index 1 after 1 probes
Inserting 48
  h1(48) = 4
  h2(48) = 9
  Probe 0: index 4 occupied
  Inserted at index 2 after 1 probes
Inserting 59
  h1(59) = 4
  h2(59) = 10
  Probe 0: index 4 occupied
  Inserted at index 3 after 1 probes

Hash Table:
Index 0: 26
Index 1: 37
Index 2: 48
Index 3: 59
Index 4: 15
Index 5: empty
Index 6: empty
Index 7: empty
Index 8: empty
Index 9: empty
Index 10: empty
Load factor: 0.454545

--- Searching ---
Found 37 at index 1 after 1 probes
Key 100 not found
```

### Why Double Hashing is Better

✅ **Different probe sequences** for different keys (even with same h₁)
✅ **Eliminates secondary clustering**
✅ **Best distribution** among open addressing methods
✅ **Closest to ideal uniform hashing**

### Requirements for Double Hashing

1. **h₂(k) must never be 0**
2. **h₂(k) should be coprime with m** (use prime m)
3. **Good distribution** for both h₁ and h₂

---

## 13. Comparison of Collision Resolution Techniques

### Summary Table

| Method | Primary Clustering | Secondary Clustering | Space | Deletion | Performance |
|--------|-------------------|---------------------|-------|----------|-------------|
| **Chaining** | No | No | Extra (pointers) | Easy | Best overall |
| **Linear Probing** | Yes ✗ | No | None | Hard | Good cache |
| **Quadratic Probing** | No ✓ | Yes ✗ | None | Hard | Better than linear |
| **Double Hashing** | No ✓ | No ✓ | None | Hard | Best open addressing |

### Performance Comparison

```cpp name=collision_resolution_comparison.cpp
#include <iostream>
#include <vector>
#include <chrono>
using namespace std;
using namespace std::chrono;

// Measure insertion time
template<typename HashTable>
long long measureInsertionTime(HashTable& ht, const vector<int>& keys) {
    auto start = high_resolution_clock::now();
    
    for (int key : keys) {
        ht.insert(key);
    }
    
    auto end = high_resolution_clock::now();
    return duration_cast<microseconds>(end - start).count();
}

int main() {
    const int TABLE_SIZE = 1009;  // Prime number
    const int NUM_KEYS = 700;     // Load factor ≈ 0.7
    
    // Generate keys
    vector<int> keys;
    for (int i = 0; i < NUM_KEYS; i++) {
        keys.push_back(i * 17);  // Create some pattern
    }
    
    cout << "=== Collision Resolution Performance ===" << endl;
    cout << "Table size: " << TABLE_SIZE << endl;
    cout << "Number of keys: " << NUM_KEYS << endl;
    cout << "Load factor: " << (double)NUM_KEYS / TABLE_SIZE << "\n" << endl;
    
    // Here you would test different implementations
    // This is a conceptual demonstration
    
    cout << "Expected characteristics:" << endl;
    cout << "1. Chaining: Most flexible, consistent performance" << endl;
    cout << "2. Linear Probing: Fast when α < 0.5, suffers from clustering" << endl;
    cout << "3. Quadratic Probing: Better than linear, moderate clustering" << endl;
    cout << "4. Double Hashing: Best distribution, no clustering" << endl;
    
    return 0;
}
```

### When to Use Each Method

| Use Case | Best Choice | Reason |
|----------|-------------|--------|
| **General purpose** | Chaining | Flexible, handles high load factors |
| **Cache-sensitive** | Linear Probing | Sequential memory access |
| **Fixed-size table** | Double Hashing | Best space utilization |
| **Unknown data size** | Chaining | Easy to grow |
| **Real-time systems** | Chaining | Predictable performance |

---

## 14. Deletion in Hash Tables

### Deletion with Chaining

**Easy!** Just remove from the linked list.

```cpp
void remove(int key) {
    int index = hashFunction(key);
    table[index].remove(key);  // Remove from list
}
```

### Deletion with Open Addressing

**Problem**: Can't just mark slot as empty!

```
Initial: [10] [20] [30] [empty] ...
         h=0  h=0  h=0
              (probed to 1, 2)

If we delete 20:
[10] [empty] [30] ...

Now searching for 30 will FAIL!
Search sees empty slot at 1 and stops.
```

**Solution**: Use a **deleted marker** (tombstone)

```cpp name=deletion_open_addressing.cpp
#include <iostream>
#include <vector>
using namespace std;

class HashTableWithDeletion {
private:
    vector<int> table;
    vector<int> status;  // 0 = empty, 1 = occupied, 2 = deleted
    int size;
    
    enum Status { EMPTY = 0, OCCUPIED = 1, DELETED = 2 };
    
    int hashFunction(int key) {
        return key % size;
    }
    
public:
    HashTableWithDeletion(int m) : size(m), table(m, -1), status(m, EMPTY) {}
    
    bool insert(int key) {
        int index = hashFunction(key);
        int i = 0;
        int firstDeleted = -1;
        
        while (status[index] == OCCUPIED || status[index] == DELETED) {
            // Remember first deleted slot
            if (status[index] == DELETED && firstDeleted == -1) {
                firstDeleted = index;
            }
            
            // Key already exists
            if (status[index] == OCCUPIED && table[index] == key) {
                return false;
            }
            
            i++;
            index = (hashFunction(key) + i) % size;
        }
        
        // Use first deleted slot if found, otherwise use current empty slot
        if (firstDeleted != -1) {
            index = firstDeleted;
        }
        
        table[index] = key;
        status[index] = OCCUPIED;
        return true;
    }
    
    bool search(int key) {
        int index = hashFunction(key);
        int i = 0;
        
        while (status[index] != EMPTY) {
            if (status[index] == OCCUPIED && table[index] == key) {
                return true;
            }
            
            i++;
            index = (hashFunction(key) + i) % size;
        }
        
        return false;
    }
    
    bool remove(int key) {
        int index = hashFunction(key);
        int i = 0;
        
        while (status[index] != EMPTY) {
            if (status[index] == OCCUPIED && table[index] == key) {
                status[index] = DELETED;  // Mark as deleted, don't empty!
                cout << "Deleted " << key << " from index " << index << endl;
                return true;
            }
            
            i++;
            index = (hashFunction(key) + i) % size;
        }
        
        cout << "Key " << key << " not found" << endl;
        return false;
    }
    
    void display() {
        cout << "\nHash Table:" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Index " << i << ": ";
            if (status[i] == OCCUPIED) {
                cout << table[i];
            } else if (status[i] == DELETED) {
                cout << "DELETED";
            } else {
                cout << "empty";
            }
            cout << endl;
        }
    }
};

int main() {
    cout << "=== Deletion in Open Addressing ===" << endl;
    
    HashTableWithDeletion ht(10);
    
    ht.insert(10);  // h(10) = 0
    ht.insert(20);  // h(20) = 0, goes to 1
    ht.insert(30);  // h(30) = 0, goes to 2
    
    ht.display();
    
    cout << "\n--- Delete 20 ---" << endl;
    ht.remove(20);
    ht.display();
    
    cout << "\n--- Search 30 (should still work!) ---" << endl;
    cout << "Found 30: " << (ht.search(30) ? "Yes" : "No") << endl;
    
    cout << "\n--- Insert 40 (reuses deleted slot) ---" << endl;
    ht.insert(40);
    ht.display();
    
    return 0;
}
```

**Output:**
```
=== Deletion in Open Addressing ===

Hash Table:
Index 0: 10
Index 1: 20
Index 2: 30
Index 3: empty
...

--- Delete 20 ---
Deleted 20 from index 1

Hash Table:
Index 0: 10
Index 1: DELETED  ← Marked, not empty!
Index 2: 30
Index 3: empty
...

--- Search 30 (should still work!) ---
Found 30: Yes  ← Search continues past DELETED marker

--- Insert 40 (reuses deleted slot) ---

Hash Table:
Index 0: 10
Index 1: 40  ← Reused the deleted slot
Index 2: 30
Index 3: empty
...
```

---

## 15. C++ STL: `unordered_map` Internals

C++'s `std::unordered_map` uses:
- **Hash function**: Division method or custom
- **Collision resolution**: Separate chaining (buckets)
- **Load factor**: Default max α = 1.0
- **Automatic resizing**: Rehashes when α > max

### Complete Example

```cpp name=unordered_map_usage.cpp
#include <iostream>
#include <unordered_map>
#include <string>
using namespace std;

int main() {
    cout << "=== std::unordered_map Example ===" << endl;
    
    // Create hash table
    unordered_map<int, string> hashTable;
    
    // Insert
    hashTable[1] = "One";
    hashTable[2] = "Two";
    hashTable[100] = "Hundred";
    hashTable.insert({50, "Fifty"});
    
    // Access
    cout << "hashTable[2] = " << hashTable[2] << endl;
    
    // Check existence
    if (hashTable.find(50) != hashTable.end()) {
        cout << "Key 50 exists with value: " << hashTable[50] << endl;
    }
    
    // Size and load factor
    cout << "\nSize: " << hashTable.size() << endl;
    cout << "Bucket count: " << hashTable.bucket_count() << endl;
    cout << "Load factor: " << hashTable.load_factor() << endl;
    cout << "Max load factor: " << hashTable.max_load_factor() << endl;
    
    // Iterate
    cout << "\nAll elements:" << endl;
    for (const auto& pair : hashTable) {
        cout << "  " << pair.first << " -> " << pair.second << endl;
    }
    
    // Analyze bucket distribution
    cout << "\nBucket distribution:" << endl;
    for (size_t i = 0; i < hashTable.bucket_count(); i++) {
        cout << "Bucket " << i << ": " << hashTable.bucket_size(i) << " elements" << endl;
    }
    
    return 0;
}
```

**Output:**
```
=== std::unordered_map Example ===
hashTable[2] = Two
Key 50 exists with value: Fifty

Size: 4
Bucket count: 7
Load factor: 0.571429
Max load factor: 1

All elements:
  50 -> Fifty
  100 -> Hundred
  1 -> One
  2 -> Two

Bucket distribution:
Bucket 0: 1 elements
Bucket 1: 1 elements
Bucket 2: 1 elements
Bucket 3: 0 elements
Bucket 4: 0 elements
Bucket 5: 0 elements
Bucket 6: 1 elements
```

---

## 16. Complete Time Complexity Summary

| Operation | Direct Address | Chaining (avg) | Chaining (worst) | Open Addr (avg) | Open Addr (worst) |
|-----------|---------------|----------------|------------------|-----------------|-------------------|
| **Insert** | O(1) | O(1) | O(1) | O(1/(1-α)) | O(n) |
| **Search** | O(1) | O(1+α) | O(n) | O(1/(1-α)) | O(n) |
| **Delete** | O(1) | O(1+α) | O(n) | O(1/(1-α)) | O(n) |

### Open Addressing Expected Probes

For load factor α < 1:

**Successful search:**
```
E[probes] ≈ (1/α) × ln(1/(1-α))
```

**Unsuccessful search:**
```
E[probes] ≈ 1/(1-α)
```

**Examples:**

| Load Factor α | Successful | Unsuccessful |
|--------------|------------|--------------|
| 0.5 | 1.4 | 2.0 |
| 0.7 | 2.2 | 3.3 |
| 0.9 | 4.6 | 10.0 |
| 0.99 | 9.1 | 100.0 |

**Conclusion:** Keep α ≤ 0.75 for good performance!

---

## 17. Key Takeaways

### Direct Address Tables
- ✅ O(1) operations, simple
- ❌ Huge space if universe is large
- **Use when:** Small, dense key range

### Chaining
- ✅ Simple, handles any load factor
- ✅ Easy deletion
- ❌ Extra space for pointers
- **Use when:** General purpose, unknown size

### Linear Probing
- ✅ Cache-friendly
- ❌ Primary clustering
- **Use when:** Low load factor, speed critical

### Quadratic Probing
- ✅ Reduces primary clustering
- ❌ Secondary clustering
- **Use when:** Better than linear, moderate loads

### Double Hashing
- ✅ Best distribution, no clustering
- ❌ More complex, two hash functions
- **Use when:** Best performance in open addressing

### Practical Guidelines

1. **Default choice:** Use `std::unordered_map` (chaining)
2. **Load factor:** Keep α ≤ 0.75
3. **Table size:** Use prime numbers
4. **Hash function:** Division for integers, multiplication for others
5. **Resize:** When α exceeds threshold
