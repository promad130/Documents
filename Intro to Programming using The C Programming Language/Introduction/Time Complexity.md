Time complexity in programming languages quantifies how an algorithm's execution time scales with input size, independent of hardware or language specifics. It helps developers optimize code efficiency by analyzing the number of operations required. Here's a detailed breakdown:

---
## Key Concepts
1. **Definition**  
    Time complexity measures the number of computational steps or operations an algorithm performs relative to input size($n$)i.e., quantitative measure of how much data is given to an algorithm, expressed using **Big O notation** (e.g., $O(n)$, $O(n^2)$). 
    For example:
    - A loop iterating through $n$ elements has $O(n)$ complexity.    
    - Nested loops over $n$ elements result in $O(n^2)$ complexity.
    
2. **Big O Notation**  
    This mathematical notation describes worst-case scenarios, focusing on growth rates:
    - **Constant time** ($O(1)$): Execution time remains fixed (e.g., accessing an array element) .
    - **Linear time** $O(n)$: Time increases linearly with input size (e.g., iterating through a list). For example, for loop.
    - **Logarithmic time** $O\left(\log{n}\right)$: Time grows slowly (e.g., binary search) .
    - **Quadratic time** $O(n^2)$: Time grows quadratically (e.g., nested loops) 
    
3. **Real-World Impact**
    - Algorithms with lower time complexity (e.g., $O(nlog⁡n)$ handle large datasets faster than those with higher complexity (e.g., $O(n^2)$
    - Example: Binary search $O(\log⁡n)$ outperforms linear search ($O(n)$) for sorted data.
![[Pasted image 20250603225929.png]]

---
## Getting to know O(n) via examples:
Some examples of how time complexity is found:
#### 1. Sorting an Array
- **Input:** An array of numbers, e.g., `[5, 2,[7][1]`
- **Input size:** The number of elements in the array, $n$. Here, $n=4$.
- **Time complexity:** For bubble sort, it's $O(n^2)$, meaning the time increases with the square of the number of elements
#### 2. Searching in a String
- **Input:** A string, e.g., `"hello world"`
- **Input size:** The number of characters in the string, $n$. Here, $n=11$.
- **Time complexity:** For a simple search, it's $O(n)$, since you may have to check each character once.
#### 3. Checking if a Number if Prime
- **Input:** A single integer, e.g., $n=31$
- **Input size:** The number of bits needed to represent n in binary, which is $⌊log⁡2n⌋+1$. For 31, that's 5 bits (`11111` in binary)
- **Time complexity:** If you check all numbers up to n, it's $O(n)$, but in terms of input size (bits), that's exponential ($O(2^b)$, where b is the number of bits)