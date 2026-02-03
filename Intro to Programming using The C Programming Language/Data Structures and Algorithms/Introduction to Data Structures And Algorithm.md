
In programming, **techniques** refer to the methods or approaches used to solve problems, organize code, and implement algorithms. These techniques are not tied to a specific programming language but are general strategies that can be applied across different languages and paradigms. Below is a list of common programming techniques:

---
### 1. **Iteration**
   - **Definition:** Repeating a set of instructions until a specific condition is met.
   - **Tools:** Loops like `for`, `while`, and `do-while`.
   - **Use Case:** When you need to perform repetitive tasks, such as processing elements in an array or generating a sequence of numbers.
   - **Example:**
     ```c
     for (int i = 0; i < 10; i++) {
         printf("%d\n", i);
     }
     ```

---
### 2. **Recursion**
   - **Definition:** A function calling itself to solve a problem by breaking it into smaller subproblems.
   - **Use Case:** When a problem can be divided into smaller, similar subproblems (e.g., tree traversal, factorial calculation).
   - **Example:**
     ```c
     int factorial(int n) {
         if (n == 0) return 1;
         return n * factorial(n - 1);
     }
     ```

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
