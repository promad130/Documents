### 1. What is an Algorithm?

Informally, an **algorithm** is any well-defined computational procedure that takes some value (input) and produces some value (output) in a finite amount of time. It is a tool for solving a well-specified computational problem, providing a precise sequence of steps to achieve a desired result.

An algorithm is considered **correct** if, for every instance of the problem, it eventually halts and produces the correct output.

### 2. The Significance of Algorithms as a Technology

In modern computing, algorithms are as much a technology as fast hardware or high-speed networking. While hardware provides raw power, the algorithm determines how efficiently that power is used.

For instance, consider two sorting algorithms: **Insertion Sort** and **Merge Sort**.

- **Insertion Sort** has a running time roughly proportional to $n^2$.
- **Merge Sort** has a running time roughly proportional to $n \log n$.

If you were to run Insertion Sort on a supercomputer and Merge Sort on a much slower machine, the slower machine would eventually outperform the supercomputer as the input size ($n$) becomes large, simply because the Merge Sort algorithm grows much more slowly.

### 3. Understanding Time Complexity

The study of [[Time Complexity]] (or order of growth) allows us to characterize an algorithm's efficiency in a way that is independent of specific hardware or compilers. We focus on the **asymptotic** behavior—how the running time increases as the input size $n$ approaches infinity.

We use three primary notations to bound these functions:

- **Big-O ($O$):** Provides an asymptotic **upper bound** (it grows "no faster than").
    
- **Big-Omega ($\Omega$):** Provides an asymptotic **lower bound** (it grows "at least as fast as").
    
- **Theta ($\Theta$):** Provides an asymptotically **tight bound** (it grows "precisely at the same rate").

### 4. Randomization in Algorithms

In this course, we will also explore **Randomized Algorithms**. Unlike deterministic algorithms—which always follow the same steps for a given input—randomized algorithms make random choices during execution (e.g., using a random-number generator).

**Significance of Randomization:**

1. **Enforcing Performance:** We can use randomization to ensure that no specific "bad" input always causes poor performance.
    
2. **Average-Case vs. Expected Time:** In a randomized version of an algorithm (like **Randomized Quicksort**), we analyze the _expected_ running time, which often results in superior performance across all possible inputs compared to its deterministic counterpart.

### 5. Implementation Philosophy (C++)

We will prioritize the **Standard Template Library (STL)** for practical efficiency but will frequently peel back the layers to implement structures manually using pointers and classes to ensure you master memory management.

**Manual vs. STL Example (Concept):**

While `std::list` handles pointers for you, manually building a linked list requires you to understand the `new` and `delete` operators in C++ to prevent memory leaks—a critical skill for your Labs (25%) and Exams.

# Table of content:
- [[#Algorithms]]
- [[#Data Structures]]
- [[#Time Complexity And Optimization]]

# Data Structures:

## Linked Lists

### What is a Linked List?

A linked list is a linear data structure where elements are not stored in contiguous memory locations (unlike arrays). Instead, each element is a separate object called a **Node**.

Each Node contains two things:

1. **Data:** The actual value.
    
2. **Pointer (Next):** The memory address of the next node in the sequence.
    

**Why use them?**

- **Dynamic Size:** They can grow and shrink at runtime. You don't need to define a size upfront.
    
- **Fast Insertions/Deletions:** Adding or removing a node at a known position takes $O(1)$ time, as it only requires changing a couple of pointers, not shifting the entire array.
    

**The Trade-off:**

- **No Random Access:** You cannot instantly access the 5th element like `arr[4]`. You must traverse from the beginning (Head) to the 5th node. Access is $O(n)$.
    
- **Extra Memory:** Every node requires extra memory to store the pointer.
    

---

### The Node in C++

In C++, we typically define a node using a `struct` or `class`. Using a `struct` is common for simple nodes because its members are public by default.

C++

```
struct Node {
    int data;       // The value
    Node* next;     // Pointer to the next node

    // Constructor to easily create new nodes
    Node(int val) {
        data = val;
        next = nullptr; // Always initialize next to nullptr
    }
};
```

---

### Core Mechanics & C++ Implementation

Every linked list needs a starting point, called the **Head**. If the list is empty, `Head = nullptr`.

#### 1. Traversing the List

To read or print the list, you create a temporary pointer, point it to the head, and move it forward until it hits `nullptr`.

C++

```
void printList(Node* head) {
    Node* current = head;
    while (current != nullptr) {
        cout << current->data << " -> ";
        current = current->next; // Move to the next node
    }
    cout << "NULL" << endl;
}
```

#### 2. Insertion

To insert a node, you manipulate pointers.

- **At the Head:** Create a new node, point its `next` to the current Head, and update the Head pointer.
    
- **In the Middle/Tail:** Traverse to the desired spot. Point the new node's `next` to the subsequent node, and update the previous node's `next` to point to the new node.
    

C++

```
// Insert at the beginning
void insertAtHead(Node*& head, int val) {
    Node* newNode = new Node(val);
    newNode->next = head;
    head = newNode;
}
```

#### 3. Deletion

To delete a node, you must connect the node _before_ the target to the node _after_ the target, then free the memory of the target node to prevent leaks.

C++

```
void deleteNode(Node*& head, int key) {
    if (head == nullptr) return;

    // If head needs to be removed
    if (head->data == key) {
        Node* temp = head;
        head = head->next;
        delete temp; // Free memory!
        return;
    }

    // Traverse to find the node BEFORE the one we want to delete
    Node* current = head;
    while (current->next != nullptr && current->next->data != key) {
        current = current->next;
    }

    // If key was not found
    if (current->next == nullptr) return;

    // Unlink the node and delete it
    Node* temp = current->next;
    current->next = current->next->next;
    delete temp; 
}
```

---

### Variations of Linked Lists

1. **Singly Linked List:** Nodes point only forward. (Covered above).
    
2. **Doubly Linked List:** Each node has a `prev` pointer and a `next` pointer. Allows traversing backward, but takes more memory.
    
    C++
    
    ```
    struct DoublyNode {
        int data;
        DoublyNode* next;
        DoublyNode* prev; // Points backward
    };
    ```
    
3. **Circular Linked List:** The `next` pointer of the last node points back to the `head`, forming a loop.
    

---

### Patterns for Solving Complex Problems

To solve advanced algorithm problems (like those on LeetCode), raw implementation isn't enough. You need these core patterns:

#### 1. Fast and Slow Pointers (Floyd's Tortoise and Hare)

Use two pointers traversing the list at different speeds (usually one moves 1 step, the other moves 2 steps).

- **Use cases:** Finding the middle of a linked list in one pass, detecting if the linked list has a cycle (loop).
    
- **Logic:** If there is a cycle, the fast pointer will eventually overlap the slow pointer.
    

#### 2. The Dummy Node

When insertions or deletions might affect the `head` of the list, write your code using a "dummy" node that points to the head.

- **Use cases:** Merging two sorted lists, removing specific elements.
    
- **Logic:** It removes edge cases where you have to write `if (head == target)`. You just return `dummy->next` at the end.
    

#### 3. In-Place Reversal

You will need to reverse a list, or parts of a list, without using extra memory (like an array).

- **Logic:** You need three pointers: `prev`, `current`, and `next`. You iterate through, saving the `next` node, flipping the `current->next` pointer backwards to `prev`, and sliding all three pointers forward.
    

---

### Time and Space Complexity Cheatsheet

|**Operation**|**Time Complexity**|**Space Complexity**|
|---|---|---|
|Access|$O(n)$|$O(1)$|
|Search|$O(n)$|$O(1)$|
|Insert at Head|$O(1)$|$O(1)$|
|Insert at Tail|$O(n)$ (or $O(1)$ if tracking Tail pointer)|$O(1)$|
|Delete at Head|$O(1)$|$O(1)$|
|Delete in Middle|$O(n)$|$O(1)$|


# Algorithms
---
# 1. **Iteration**
   - **Definition:** Repeating a set of instructions until a specific condition is met.
   - **Tools:** Loops like `for`, `while`, and `do-while`.
   - **Use Case:** When you need to perform repetitive tasks, such as processing elements in an array or generating a sequence of numbers.
   - **Example:**
     ```c
     for (int i = 0; i < 10; i++) {
         printf("%d\n", i);
     }
     ```

# Algorithms under iteration

## BubbleSort

### 1. Conceptual Overview & Pseudocode

Bubblesort is a popular, albeit inefficient, sorting algorithm that operates by repeatedly swapping adjacent elements that are out of order. The name comes from the way smaller elements "bubble" up to the beginning of the list (or larger elements sink to the end).

**Pseudocode (based on CLRS Problem 2-2):**

Plaintext

```
BUBBLESORT(A, n)
1  for i = 1 to n - 1
2      for j = n downto i + 1
3          if A[j] < A[j - 1]
4              exchange A[j] with A[j - 1]
```

### 2. Flowchart (Directed Graph)

A directed graph representation of the logic:

- **Start** $\rightarrow$ **Initialize $i = 1$**.
    
- **Outer Loop Condition ($i < n$):** If false, **End**. If true, proceed.
    
- **Initialize $j = n$**.
    
- **Inner Loop Condition ($j > i$):** If false, **Increment $i$** and return to Outer Loop.
    
- **Swap Check ($A[j] < A[j-1]$):** If true, **Swap elements**.
    
- **Decrement $j$** $\rightarrow$ Return to Inner Loop Condition.
    

### 3. Proof of Correctness

To prove that `BUBBLESORT` is correct, we use the method of **Loop Invariants**, similar to the proof provided for Insertion Sort.

#### Part A: Inner Loop Invariant (Lines 2-4)

**Invariant:** At the start of each iteration of the inner `for` loop, $A[j]$ is the smallest element in the subarray $A[j:n]$.

- **Initialization:** When $j = n$, the subarray $A[n:n]$ has only one element, so $A[n]$ is trivially the smallest.
    
- **Maintenance:** If $A[j] < A[j-1]$, they are swapped; otherwise, they stay. After the swap (or lack thereof), the new $A[j-1]$ is the minimum of the original $A[j]$ and $A[j-1]$. Since the invariant held for $A[j]$, the new $A[j-1]$ is now the smallest in $A[j-1:n]$.
    
- **Termination:** The loop terminates when $j = i$. By the invariant, $A[i]$ now contains the smallest element of the subarray $A[i:n]$.
    

#### Part B: Outer Loop Invariant (Lines 1-4)

**Invariant:** At the start of each iteration of the outer `for` loop, the subarray $A[1:i-1]$ contains the $i-1$ smallest elements of the original array in sorted order.

- **Termination:** The loop terminates when $i = n$. Substituting $n$ into the invariant, $A[1:n-1]$ contains the $n-1$ smallest elements in sorted order. This implies the $n$-th element is the largest and is in its correct place, meaning the entire array $A[1:n]$ is sorted.
    

### 4. Worst-Case Time Complexity Analysis

In the RAM model, we assume each comparison and swap takes a constant amount of time.

- **Number of Comparisons:** The outer loop runs $n-1$ times. For a given $i$, the inner loop runs $n-i$ times.
    
- **Total Comparisons:** $\sum_{i=1}^{n-1} (n - i) = (n-1) + (n-2) + \dots + 1 = \frac{n(n-1)}{2}$.
    
- **Asymptotic Bound:** This is a quadratic function: $\frac{1}{2}n^2 - \frac{1}{2}n$. Dropping lower-order terms and constant coefficients, the worst-case running time is $\Theta(n^2)$.
    

### 5. C++ Implementation

In modern C++, we utilize the STL for utility functions like `std::swap`, but implement the logic manually to demonstrate the pointer-like behavior of array access.

```C++
#include <iostream>
#include <vector>
#include <algorithm> // for std::swap

/**
 * Clean Implementation of Bubblesort
 * Mirrors the CLRS logic with 0-indexed adjustment
 */
void bubbleSort(std::vector<int>& A) {
    size_t n = A.size();
    // i tracks the start of the unsorted portion
    for (size_t i = 0; i < n - 1; ++i) {
        // j moves from the end to bubble the smallest element to index i
        for (size_t j = n - 1; j > i; --j) {
            if (A[j] < A[j - 1]) {
                std::swap(A[j], A[j - 1]);
            }
        }
    }
}
```


---
# 2. **[[Recursion]]**
   - **Definition:** A function calling itself to solve a problem by breaking it into smaller subproblems.
   - **Use Case:** When a problem can be divided into smaller, similar subproblems (e.g., tree traversal, factorial calculation).
   - **Example:**
     ```c
     int factorial(int n) {
         if (n == 0) return 1;
         return n * factorial(n - 1);
     }
     ```

# Algorithms in Recursion

## Insertion Sort

### 1. Conceptual Breakdown

**Insertion Sort** is an efficient algorithm for sorting a small number of elements. It works similarly to the way you might sort a hand of playing cards.

**Pseudocode (CLRS 4th Edition, Page 19):**

Plaintext

```
INSERTION-SORT(A, n)
1  for j = 2 to n
2      key = A[j]
3      // Insert A[j] into the sorted sequence A[1 .. j - 1].
4      i = j - 1
5      while i > 0 and A[i] > key
6          A[i + 1] = A[i]
7          i = i - 1
8      A[i + 1] = key
```

### 2. Flowchart (Directed Graph Logic)

- **Start** $\rightarrow$ **Initialize $j = 2$**.
    
- **Outer Loop Condition ($j \le n$):** If false, **End**.
    
- **Extract Key:** $key = A[j]$, set $i = j - 1$.
    
- **Inner Loop Condition ($i > 0$ and $A[i] > key$):**
    
    - If **True**: $A[i+1] = A[i]$ (Shift Right), $i = i - 1$. Loop back.
        
    - If **False**: $A[i+1] = key$ (Insert).
        
- **Increment $j$** $\rightarrow$ Return to Outer Loop Condition.
    

### 3. Proof of Correctness (Loop Invariant)

We use the **Loop Invariant** for the outer `for` loop:

- **Invariant:** At the start of each iteration of the `for` loop of lines 1–8, the subarray $A[1 .. j-1]$ consists of the elements originally in $A[1 .. j-1]$, but in sorted order.
    
- **Initialization:** Before the first iteration ($j=2$), $A[1..1]$ is just one element, which is trivially sorted.
    
- **Maintenance:** The `while` loop moves $A[j-1], A[j-2], \dots$ one position to the right until it finds the proper position for $A[j]$. Then it inserts the value of `key`. The subarray $A[1..j]$ now consists of the original elements in sorted order.
    
- **Termination:** The loop terminates when $j = n+1$. By the invariant, $A[1..n]$ is sorted.
    

### 4. Worst-Case Time Complexity

The worst case occurs when the array is in **reverse sorted order**.

- Outer loop runs $n-1$ times.
    
- For each $j$, the inner `while` loop runs $j-1$ times (shifting all elements).
    
- Total steps: $\sum_{j=2}^{n} (j-1) = 1 + 2 + \dots + (n-1) = \frac{n(n-1)}{2}$.
    
- Asymptotically, this is **$\Theta(n^2)$**.
    

```C++
#include <iostream>
#include <vector>
#include <chrono>
#include <fstream>
#include <random>

/**
* INSERTION-SORT(A, n)
* Adheres to CLRS logic with 0-indexed adjustment for C++
*/

void insertionSort(std::vector<int>& A) {
	int n = A.size();
	
	// Start from the second element (j=1)
	for (int j = 1; j < n; j++) {
	int key = A[j]; // Extract key
	
	// Insert A[j] into the sorted sequence A[0..j-1]
	int i = j - 1;
	
	// Shift elements of A[0..j-1] that are greater than key
	while (i >= 0 && A[i] > key) {

	A[i + 1] = A[i];
	i = i - 1;
	}
// Place key in its correct position
		A[i + 1] = key;
	}
}
```

# HASHING
## What is Hashing?
Hashing is a technique used to store and retrieve data almost instantly—in $O(1)$ (constant) time. Instead of searching through a list one by one to find what you need, you use the data itself to calculate its exact location in memory.

For example, I give you the array, but the moment u get it, it's get sorted by a mad man, and he removes all the duplicated elements, now I don't know about index of any element, and I have an interview that is going to ask me about index of given target, and I better give answer instantaneously.

We can do this:
```Text
FindIndex(n, target):
	array A[n];
	for i = 0 to n - 1:
		A[i] = userInput
	
	// MadMan at work
	
	for i = 0 to A.size -1:
		if A[i] == target:
			return i;
	return -1;
```

But this takes a lot of time, and I get kicked. So I do this:
```Text
FindIndex(n, target):
	array A[n];
	array B[largestIntPossiblyAllowed];
	for i = 0 to n - 1:
		A[i] = userInput
		B[userInput] = i;
	
	// MadMan at work
	
	if(target < largestIntPossiblyAllowed):
		return B[target];
	return -1;
```

This significantly reduced the time taken to lookup anything as now I had create B, a hash array that stores that info required in an array where the index is the target given.

## The Problem: Number Counting

Imagine you have an array of numbers: `{1, 4, 1, 2, 4, 1, 5}`. You want to know how many times a specific number appears.

Without hashing, every time you want to know how many `1`s there are, you have to loop through the entire array. If you have a million numbers and ask a million questions, your program will freeze.

```C++
#include <bits/stdc++.h>
using namespace std;

int main(){
	
	int n;
	cin >> n;
	
	int arr[n] = {0};
	int hash[n] = {0};
	for (int i = 0; i < n; i++)
	{
		cin >> arr[i];
		hash[arr[i]]++;
	}
	
	int q;
	cin >> q;
	
	for(int i = 0; i < q; I++)
	{
		int target;
		cin >> target;
		cout << hash[target] << endl;
	}
	
	return 0;
}
```

So this takes in the initial array, and after that number of elements to look up, and the value of them one by one, outputting the count one by one.

## Character Hashing
Another example where do the same, but with lower case ASCII characters:

```C++
#include <bits/stdc++.h>
using namespace std;

int main(){
	string s;
	cin >> s;
	
	int hash[26] = {0};
	for(int i = 0; i < s.size(); i++)
	{
		hash[s[i]-'a']++;
	}
	
	int q;
	cin >> q;
	while(q--){
		char c;
		cin >> c;
		cout << hash[c-'a'] << endl;	
	}
	
	return 0;
}
```

However, if your numbers are massive, negative, or unknown in advance, a standard array will waste too much memory or crash. This is where C++'s built-in hash table comes in: `std::unordered_map`.

## C++ Example: Number Count with `std::unordered_map`

An `unordered_map` stores key-value pairs.
- **Key:** The number we are looking at.
- **Value:** The frequency (how many times we've seen it).

Here is the straightforward, no-bullshit implementation:

```C++
#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

int main() {
    // Our raw data
    vector<int> numbers = {1, 4, 1, 2, 4, 1, 5};

    // The Hash Table: <Key (the number), Value (the count)>
    unordered_map<int, int> hash_table;

    // STEP 1: Pre-compute the counts (Populate the hash table)
    for (int num : numbers) {
        hash_table[num]++; // If it doesn't exist, it starts at 0 and adds 1.
    }

    // STEP 2: Retrieve the data instantly in O(1) time
    cout << "Count of 1: " << hash_table[1] << "\n";
    cout << "Count of 4: " << hash_table[4] << "\n";
    cout << "Count of 5: " << hash_table[5] << "\n";
    
    // Querying a number that isn't in the original list
    cout << "Count of 99: " << hash_table[99] << "\n";

    return 0;
}
```

### Why this is powerful

1. **Speed:** Looking up `hash_table[4]` takes exactly 1 step under the hood. It doesn't matter if your original array had 10 items or 10 billion items. The lookup speed remains $O(1)$.
    
2. **Simplicity:** The logic is handled by the data structure. You just feed it data and pull it out using the key.

## But how does the key lookup for so fast irrespective the values and order?

### How a Hash Function Makes Lookups Instant

The secret to a hash table's blinding speed is that it completely skips the "searching" phase. It doesn't look at the other values in the table. It uses a **Hash Function**, which acts like a mathematical blender.

When you hand a key to a hash table, the hash function performs a calculation on that key and spits out the exact index in memory where the data lives.

### Step-by-Step: How the Hash Function Works

Let’s say you have an underlying array of size 10 to store your data.

**1. The Scramble (Hash Code):** First, the hash function turns your key into an integer.

- If your key is already an integer (like `42`), the hash code might just be `42`.
    
- If your key is a word (like `"apple"`), the function might convert the letters into numbers and multiply them together to get a giant integer (e.g., `874591`).
    

**2. The Squeeze (Modulo):**

You can't put index `874591` into an array of size 10. So, the hash function uses the modulo operator (which gives the remainder of division) to squeeze the number into the array's boundaries:

$index = hash\_code \pmod{array\_size}$

- For the key `42`: $42 \pmod{10} = 2$. The data goes into **Index 2**.
    
- For `"apple"` (874591): $874591 \pmod{10} = 1$. The data goes into **Index 1**.
    

When you look up `"apple"` later, the exact same math happens, pointing you instantly to Index 1.

---

### The Problem: Collisions

Because we are squeezing an infinite number of possible keys (any number, any word) into a finite array size (like 10), eventually, two different keys will produce the exact same index.

Imagine you want to insert the keys `14` and `24` into our array of size 10:

- $14 \pmod{10} = 4$
    
- $24 \pmod{10} = 4$
    

Both keys mathematically map to **Index 4**. This is called a **Collision**. They can't both sit in the exact same chair.

### How C++ Handles Collisions (Chaining)

C++'s `std::unordered_map` handles this using a technique called **Separate Chaining**.

Instead of each array index holding just one value, each index holds a **Linked List** (a chain of values).

1. When `14` maps to Index 4, the map puts it there.
    
2. When `24` also maps to Index 4, the map doesn't overwrite `14`. Instead, it attaches `24` to `14` like a train car.
    
3. When you lookup `24` later, the math instantly takes you to Index 4. Then, the program quickly walks through that specific, tiny chain at Index 4 until it finds `24`.
    

**Why it stays fast:** As long as your hash table is big enough and your hash function scrambles things well, collisions are rare. Your chains stay very short (usually just 1 or 2 items). So, taking a split second to check a tiny chain still averages out to $O(1)$ time.

---
# 3. **Divide and Conquer**
   - **Definition:** Breaking a problem into smaller subproblems, solving them independently, and combining the results.
   - **Use Case:** Efficiently solving problems like sorting (e.g., Merge Sort, Quick Sort) or searching (e.g., Binary Search).
   - **Example:** Merge Sort divides an array into two halves, sorts them, and then merges the sorted halves.

# Algorithms that use Divide and Conquer

- ## [[Quick Sort]]
- ## [[Merge Sort]]
- ## [[Binary Search]]


---
### 4. **Dynamic Programming (DP)**
   - **Definition:** Solving complex problems by breaking them into simpler subproblems and storing the results of these subproblems to avoid redundant computations.
   - **Use Case:** Optimization problems like the Fibonacci sequence, Knapsack problem, or shortest path algorithms.
   - **Example:**
     ```c
     int fib(int n) {
         int dp[n + 1];
         dp[0] = 0; dp[1] = 1;
         for (int i = 2; i <= n; i++) {
             dp[i] = dp[i - 1] + dp[i - 2];
         }
         return dp[n];
     }
     ```

---
### 5. **Greedy Algorithms**
   - **Definition:** Making locally optimal choices at each step with the hope of finding a global optimum.
   - **Use Case:** Problems where a series of locally optimal choices lead to a globally optimal solution (e.g., Huffman coding, Dijkstra's algorithm).
   - **Example:** Selecting the smallest coin denomination first in a coin change problem.

---
### 6. **Backtracking**
   - **Definition:** Systematically searching for a solution by trying out possible options and abandoning them if they don't lead to a solution.
   - **Use Case:** Solving problems like the N-Queens problem, Sudoku, or generating permutations.
   - **Example:**
     ```c
     void solveSudoku(int grid[9][9]) {
         // Try placing numbers 1-9 in empty cells
         // Backtrack if a number doesn't fit
     }
     ```

---

### 7. **Memoization**
   - **Definition:** Storing the results of expensive function calls and reusing them when the same inputs occur again.
   - **Use Case:** Optimizing recursive algorithms (e.g., Fibonacci sequence).
   - **Example:**
     ```c
     int memo[100] = {0};
     int fib(int n) {
         if (memo[n] != 0) return memo[n];
         if (n <= 1) return n;
         memo[n] = fib(n - 1) + fib(n - 2);
         return memo[n];
     }
     ```

---

### 8. **Brute Force**
   - **Definition:** Trying all possible solutions to find the correct one.
   - **Use Case:** When the problem size is small, and efficiency is not a concern.
   - **Example:** Checking all possible combinations to solve a password cracker.

---

### 9. **Two-Pointer Technique**
   - **Definition:** Using two pointers to traverse data structures like arrays or linked lists.
   - **Use Case:** Solving problems like finding pairs in a sorted array, reversing a string, or detecting cycles in a linked list.
   - **Example:**
     ```c
     void reverseArray(int arr[], int n) {
         int left = 0, right = n - 1;
         while (left < right) {
             swap(arr[left], arr[right]);
             left++;
             right--;
         }
     }
     ```

---

### 10. **Sliding Window**
   - **Definition:** Maintaining a "window" of elements in a data structure (e.g., array) and sliding it to solve subproblems.
   - **Use Case:** Problems involving subarrays or substrings, such as finding the maximum sum of a subarray of fixed size.
   - **Example:**
     ```c
     int maxSumSubarray(int arr[], int n, int k) {
         int windowSum = 0, maxSum = 0;
         for (int i = 0; i < k; i++) {
             windowSum += arr[i];
         }
         maxSum = windowSum;
         for (int i = k; i < n; i++) {
             windowSum += arr[i] - arr[i - k];
             maxSum = max(maxSum, windowSum);
         }
         return maxSum;
     }
     ```

---

### 11. **Bit Manipulation**
   - **Definition:** Using bitwise operations to solve problems efficiently.
   - **Use Case:** Problems involving binary representations, such as counting set bits or finding unique numbers.
   - **Example:**
     ```c
     int countSetBits(int n) {
         int count = 0;
         while (n) {
             count += n & 1;
             n >>= 1;
         }
         return count;
     }
     ```

---

### 12. **Object-Oriented Programming (OOP) Techniques**
   - **Definition:** Organizing code using objects, classes, and principles like encapsulation, inheritance, and polymorphism.
   - **Use Case:** Building modular, reusable, and maintainable code.
   - **Example:**
     ```python
     class Dog:
         def __init__(self, name):
             self.name = name
         def bark(self):
             print(f"{self.name} says woof!")
     ```

---

### 13. **Functional Programming Techniques**
   - **Definition:** Treating computation as the evaluation of mathematical functions and avoiding changing state or mutable data.
   - **Use Case:** Writing clean, predictable, and parallelizable code.
   - **Example:**
     ```python
     numbers = [1, 2, 3, 4]
     squared = list(map(lambda x: x ** 2, numbers))
     ```

---

### 14. **Modular Programming**
   - **Definition:** Breaking a program into separate, independent modules or functions.
   - **Use Case:** Improving code readability, reusability, and maintainability.
   - **Example:**
     ```c
     int add(int a, int b) { return a + b; }
     int subtract(int a, int b) { return a - b; }
     ```

---

### 15. **Error Handling**
   - **Definition:** Anticipating and managing errors or exceptions in a program.
   - **Use Case:** Ensuring robustness and reliability in software.
   - **Example:**
     ```python
     try:
         result = 10 / 0
     except ZeroDivisionError:
         print("Cannot divide by zero!")
     ```

# Time Complexity And Optimization

![[Time Complexity]]

# Randomization in Algorithms:

### 1. Conceptual Framework

In standard analysis, we assume an adversary provides the worst possible input (e.g., a reverse-sorted array for Insertion Sort). In this chapter, we introduce two distinct but related approaches:

- **Probabilistic Analysis:** We analyze the **average-case** running time by making assumptions about the distribution of inputs (e.g., assuming all $n!$ permutations of an input are equally likely).
    
- **Randomized Algorithms:** We do not assume a distribution; instead, we **impose** one. The algorithm uses a random-number generator to make internal choices, giving us an **expected** running time that holds regardless of the input provided by an adversary.
    

### 2. The Case Study: The Hiring Problem

Imagine you are hiring an office assistant. You interview $n$ candidates one by one. Your policy is: "Always hire the best person seen so far".

**The Costs:**

- **Interviewing:** $c_i$ (low cost).
    
- **Hiring:** $c_h$ (high cost, includes firing the old assistant).
    
- **Total Cost:** $O(n \cdot c_i + m \cdot c_h)$, where $m$ is the number of times you hire.
    

**Complexity Breakdown:**

1. **Worst Case:** Candidates arrive in strictly increasing order of quality. You hire every single person. Total hiring cost: $O(n \cdot c_h)$.
    
2. **Average Case:** If candidates arrive in a random order, how many do we actually hire? We use **Indicator Random Variables** to solve this.
    

### 3. Indicator Random Variables (IRVs)

IRVs are the surgical tools of probabilistic analysis. Given an event $A$, the indicator $X_A = 1$ if $A$ occurs, and $0$ otherwise. A fundamental lemma states that $E[X_A] = Pr\{A\}$ (the expected value equals the probability).

For the Hiring Problem:

- Let $X_i$ be the IRV that candidate $i$ is hired.
    
- Candidate $i$ is hired only if they are better than the $i-1$ candidates before them.
    
- In a random permutation, the probability that the $i$-th candidate is the best so far is exactly $1/i$.
    
- Total hires $X = \sum_{i=1}^n X_i$.
    
- By **Linearity of Expectation**, $E[X] = \sum_{i=1}^n E[X_i] = \sum_{i=1}^n \frac{1}{i}$.
    

This sum is the **Harmonic Series**, which evaluates to $\ln n + O(1)$. Thus, on average, you only hire $\ln n$ people, even if $n$ is very large.

### 4. C++ Implementation: Randomized Shuffling

To turn the deterministic hiring problem into a **Randomized Algorithm**, we must ensure a random order ourselves. In C++, we can use the `std::shuffle` from the `<algorithm>` header or manually implement the **Fisher-Yates (Randomly-Permute)** algorithm.


```C++
#include <iostream>
#include <vector>
#include <algorithm> // for std::swap
#include <random>    // for std::mt19937

// Manual implementation of RANDOMLY-PERMUTE (Fisher-Yates)
// This ensures every permutation is equally likely (Uniform Random Permutation)
void randomlyPermute(std::vector<int>& A) {
    int n = A.size();
    // Using modern C++ random number generation
    std::random_device rd;
    std::mt19937 g(rd());

    for (int i = 0; i < n; ++i) {
        // RANDOM(i, n-1) logic
        std::uniform_int_distribution<int> dist(i, n - 1);
        int randomIndex = dist(g);
        std::swap(A[i], A[randomIndex]);
    }
}

/**
 * Memory Management Note:
 * In C++, passing a std::vector by reference (&A) is efficient (O(1) time for the pointer swap 
 * internally) compared to passing by value, which would trigger a deep copy (O(n)).
 * Always prefer references or smart pointers (std::unique_ptr) for large data structures 
 * to maintain algorithmic rigor in space complexity[cite: 120].
 */
```

### 5. Summary of Complexity

|**Analysis Type**|**Case**|**Time/Cost Complexity**|
|---|---|---|
|**Deterministic**|Worst-Case Hiring|$O(n)$ hires|
|**Probabilistic**|Average-Case Hiring|$O(\ln n)$ hires|
|**Randomized**|Expected Hiring|$O(\ln n)$ hires (Guaranteed for any input)|
Ensure you understand the distinction between "Average-Case" and "Expected" for your Midsem. Average-case relies on the _input_ being random; Expected relies on the _algorithm_ being random.

# Algorithms with Randomization Modification:
## 1. [[Randomized QuickSort]]
