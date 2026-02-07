[[time-complexity-guide]]

In programming, **techniques** refer to the methods or approaches used to solve problems, organize code, and implement algorithms. These techniques are not tied to a specific programming language but are general strategies that can be applied across different languages and paradigms. Below is a list of common programming techniques:

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


---
### 3. **Divide and Conquer**
   - **Definition:** Breaking a problem into smaller subproblems, solving them independently, and combining the results.
   - **Use Case:** Efficiently solving problems like sorting (e.g., Merge Sort, Quick Sort) or searching (e.g., Binary Search).
   - **Example:** Merge Sort divides an array into two halves, sorts them, and then merges the sorted halves.

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
