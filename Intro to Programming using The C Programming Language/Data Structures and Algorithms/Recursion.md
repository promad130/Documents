
### 1. The Anatomy of a Recursive Procedure

Every well-defined recursive procedure must satisfy two primary conditions:

- **The Base Case:** A condition where the problem is small enough to be solved directly without further recursion.
    
- **The Recursive Step:** The part where the procedure calls itself with a "smaller" or "simpler" version of the original input, moving closer to the base case.

#### C++ Implementation: Factorial Example

Let's look at a classic example: calculating $n!$ (n factorial). Mathematically, $n! = n \times (n-1)!$ for $n > 0$, and $0! [cite_start]= 1$.

```C++
#include <iostream>

/**
 * Calculates factorial of n using recursion.
 * Follows the "Knuth" aesthetic: clear and mathematically sound.
 */
unsigned long long factorial(int n) {
    // Base Case: 0! is defined as 1
    if (n <= 0) { 
        return 1; 
    }
    
    // Recursive Step: n * (n-1)!
    // Each call reduces n, moving toward the base case.
    return n * factorial(n - 1);
}

int main() {
    int value = 5;
    std::cout << "Factorial of " << value << " is " << factorial(value) << std::endl;
    return 0;
}
```

### 2. Visualizing Recursion: The Recursion Tree

To understand how recursion unfolds, we use a **Recursion Tree**. Each node represents the cost of a single subproblem.

For `factorial(4)`, the call stack behaves as follows:

1. `factorial(4)` calls `factorial(3)`
    
2. `factorial(3)` calls `factorial(2)`
    
3. `factorial(2)` calls `factorial(1)`
    
4. `factorial(1)` calls `factorial(0)`
    
5. `factorial(0)` returns `1` (Base Case reached)
    
6. The results propagate back up the tree.
    

### 3. Analyzing Recursive Complexity

In this course, we characterize running times ($T(n)$) using **Recurrences**. A recurrence is an equation that describes a function in terms of its value on smaller inputs.

+1

For a **Divide-and-Conquer** algorithm like **Merge Sort**, the recurrence is typically:

$$T(n) = 2T(n/2) + \Theta(n)$$

- **$2T(n/2)$**: The cost of solving two subproblems of half the size.
    
- **$\Theta(n)$**: The "Combine" step—the time taken to merge the sorted halves.
    

#### Visualization of Merge Sort Recursion

In the Merge Sort tree, the height is $\log_2 n$ because the problem size is halved at each level. The total work at each level remains $cn$, leading to a tight bound of $\Theta(n \log n)$.

+4

### 4. Memory Management & The Stack

In C++, every recursive call consumes memory on the **call stack**. Each call creates a new **stack frame** containing local variables and the return address.

- **Risk:** If the recursion is too deep (or the base case is missing), you will encounter a `std::stack_overflow`.
    
- **Mentor Tip:** For high-stakes assessments like the **Midsem (35%)**, always ensure your recursion has a clear path to termination.
    

### 5. Academic Reference
For a deeper dive into "The substitution method" and "The master method" for solving these references, given in [[Time Complexity]].

# Recursion calls as a stack 
To visualize recursion accurately, you must look at the **Call Stack**. Every time a function calls itself, a new **Stack Frame** is pushed onto the runtime stack. It remains there, partially completed, until the base case is reached.

### 1. The Stack Frame Anatomy

Each frame contains:

- **Local Variables:** Unique to that specific call.
    
- **Return Address:** Where to resume in the caller function.
    
- **Parameters:** The specific input for that subproblem.
    

### 2. Trace: `factorial(3)`

Consider the recurrence $T(n) = T(n-1) + \Theta(1)$.

**The Pushing Phase (Winding):**

1. `main()` calls `fact(3)` $\rightarrow$ Frame 1 pushed.
2. `fact(3)` calls `fact(2)` $\rightarrow$ Frame 2 pushed.
3. `fact(2)` calls `fact(1)` $\rightarrow$ Frame 3 pushed (Base Case).

**The Popping Phase (Unwinding):**

1. `fact(1)` returns `1`. Frame 3 is **deleted** (memory freed).
    
2. `fact(2)` receives `1`, computes $2 \times 1$, returns `2`. Frame 2 popped.
    
3. `fact(3)` receives `2`, computes $3 \times 2$, returns `6`. Frame 1 popped.
    

### 3. C++ Memory Perspective

In C++, deep recursion without a proper base case leads to `std::stack_overflow` because the stack segment of memory is finite (typically 1-8 MB).

```C++

// Aesthetic & Rigorous implementation
int fact(int n) {
    if (n <= 1) return 1; // Base Case
    return n * fact(n - 1); // Recursive Step
}
```

### 4. Comparison: Stack vs. Tree

- **Recursion Tree:** Best for analyzing **Time Complexity** (total work done across all branches).
    
- **Call Stack:** Best for analyzing **Space Complexity** (maximum depth of the recursion, $O(h)$).


# Tail Call Optimization

Tail Call Optimization (TCO) is a compiler-level optimization where the compiler replaces a recursive call with a `jump` instruction, effectively reusing the current stack frame instead of pushing a new one. This happens only if the recursive call is the **final action** in the function.

### 1. Standard Recursion vs. Tail Recursion

In standard recursion, the computer must "remember" to multiply the result of the recursive call by `n`. In tail recursion, the result of the recursive call is returned immediately, so no state needs to be saved.

**Standard (Stack-Heavy):**

C++

```
return n * fact(n - 1); // Multiplication happens AFTER the call returns.
```

**Tail Recursive (Stack-Efficient):**

C++

```
return fact_tail(n - 1, n * accumulator); // Nothing left to do after the call.
```

### 2. The Visual Shift: Stack to Loop

When a function is tail-recursive, the stack does not grow. It stays at a constant depth of 1.

### 3. C++ Implementation

To implement this, we use an **accumulator** to carry the partial result forward.

C++

```
#include <iostream>

// Tail recursive factorial
// 'acc' stores the result as we go down the 'stack'
unsigned long long fact_helper(int n, unsigned long long acc) {
    if (n <= 1) return acc; 
    
    // Tail Call: The return value is exactly the result of the next call.
    return fact_helper(n - 1, n * acc); 
}

unsigned long long factorial(int n) {
    return fact_helper(n, 1);
}

int main() {
    // This will not cause stack overflow even for large n (within type limits)
    std::cout << factorial(20) << std::endl; 
    return 0;
}
```

### 4. Why this matters for your Evaluation

- **Space Complexity:** Standard recursion uses $O(n)$ space (stack depth). Tail-optimized recursion uses $O(1)$ space.
    
- **Performance:** Avoiding stack allocation and deallocation reduces overhead, mimicking the performance of a `while` loop.
    
- **Compiler Note:** In C++, TCO is usually performed by `g++` or `clang++` at optimization levels `-O2` or `-O3`.
    

### 5. Summary Table

|**Feature**|**Standard Recursion**|**Tail Recursion**|
|---|---|---|
|**Stack Growth**|Linear $O(n)$|Constant $O(1)$|
|**Final Operation**|Arithmetic (e.g., `n * ...`)|The Recursive Call itself|
|**Risk**|Stack Overflow|Safe (like a loop)|

![[Resursion  Examples.pdf]]


# The Recursion Tree Method

While the **Call Stack** visualizes the _execution order_ (memory), the **Recursion Tree** visualizes the **cost** (time complexity). It is the most intuitive way to solve recurrences of the form $T(n) = aT(n/b) + f(n)$.

## 1. The Anatomy of a Node

In a recursion tree:

- **The Node** represents the cost ($f(n)$) incurred at that specific level of recursion (e.g., the partitioning step in Quicksort or the merging step in Merge Sort).
    
- **The Edges** represent the recursive calls ($T(n/b)$) branching out to children nodes.
    

## 2. Methodology: The 4-Step Process

To derive the complexity $T(n)$:

1. **Draw the Tree:** Expand the recurrence for at least 3 levels.
    
2. **Calculate Tree Height:** Determine how many levels ($h$) exist before reaching the base case ($n=1$).
    
3. **Sum Each Level:** Calculate the total work done across all nodes at depth $i$.
    
4. **Sum Total:** Sum the work of all levels to get the final Big-O.
    

## 3. Visualization: Merge Sort Analysis

**Recurrence:** $T(n) = 2T(n/2) + cn$

- $2$ **branches:** We split into 2 subproblems.
    
- $n/2$ **size:** Input halves at every step.
    
- $cn$ **cost:** Linear work to merge results.
    

### The Tree Diagram

```
Level 0:                cn                       (Total: cn)
                       /  \
                      /    \
Level 1:          cn/2     cn/2                  (Total: cn/2 + cn/2 = cn)
                  /  \     /  \
                 /    \   /    \
Level 2:      cn/4  cn/4 cn/4  cn/4              (Total: 4 * cn/4 = cn)
               / \   / \   / \   / \
              :   : :   : :   : :   : 
              
... (Recursion continues until n=1) ...

Level h:    T(1) T(1) ... ... T(1) T(1)          (Total: n * c = cn)
```

## 4. Mathematical Derivation

### Step A: Calculate Height (h)

The problem size starts at $n$ and divides by 2 at each level. We stop when the size is 1.

$$\frac{n}{2^h} = 1 \implies n = 2^h \implies h = \log_2 n$$

### Step B: Summing the Costs

Notice a pattern in the "Total" column of the diagram above?

- Level 0 cost: $cn$
    
- Level 1 cost: $cn$
    
- Level 2 cost: $cn$
    
- ...
    
- Level $h$ cost: $cn$ (approximate, technically $\Theta(n)$ for leaves)
    

### Step C: Final Calculation

$$T(n) = \sum_{i=0}^{\log_2 n} (\text{Cost at Level } i)$$$$T(n) = \sum_{i=0}^{\log_2 n} cn$$

Since there are $\log_2 n + 1$ levels, and each costs $cn$:

$$T(n) = cn \times (\log_2 n + 1)$$$$T(n) = cn \lg n + cn$$$$\mathbf{T(n) = \Theta(n \lg n)}$$

## 5. Uneven Split Example (Advanced)

**Recurrence:** $T(n) = T(n/3) + T(2n/3) + n$

- Used in algorithms where partition is unbalanced.
    
- **Visual difference:** The tree is "lopsided". The branch reducing by $2/3$ goes deeper than the branch reducing by $1/3$.
    

```
              n
            /   \
        n/3      2n/3       --> Total: n
        / \      /  \
      ... ...  ...  ...     --> Total: n
```

- **Longest Path:** $n \rightarrow (2/3)n \rightarrow (2/3)^2 n \dots \rightarrow 1$.
    
- **Height:** $\log_{3/2} n$.
    
- **Result:** Still $\Theta(n \lg n)$ because level sums are bounded by $n$.
    

## 6. Academic Note

The Recursion Tree is often used to generate a "guess" for the complexity. In rigorous exams (Midsem/Endsem), you should verify this guess using the **[[Time Complexity#Substitution Method]]**.