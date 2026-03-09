# Mathematical Proof of Time Complexity for Max-Heap Operations

A **Max-Heap** is a complete binary tree where the value of each node is greater than or equal to the values of its children. Because it is a complete binary tree, we must first establish the relationship between the number of nodes $n$ and the height of the tree $h$.

## 1. Foundation: Height of a Complete Binary Tree

Let $n$ be the total number of nodes in a complete binary tree.

At level $0$ (root), there is $2^0 = 1$ node.

At level $1$, there are $2^1 = 2$ nodes.

At level $d$, there are $2^d$ nodes.

For a complete binary tree of height $h$ (where the root is at depth $0$ and leaves are at maximum depth $h$), the total number of nodes $n$ is bounded by:

  

$$\sum_{i=0}^{h-1} 2^i < n \leq \sum_{i=0}^{h} 2^i$$$$2^h - 1 < n \leq 2^{h+1} - 1$$

Taking the base-2 logarithm:

  

$$h < \log_2(n+1) \leq h+1$$

Thus, the height of the tree is strictly bounded by:

  

$$h = \lfloor \log_2 n \rfloor$$

**Conclusion:** Any operation that traverses a path from the root to a leaf (or vice versa) will take $O(h) = O(\log n)$ time.

## 2. Insertion (`Insert`)

**Algorithm:**

1. Add the new element to the bottom level of the heap (the next available position to maintain the complete binary tree property).
    
2. Compare the added element with its parent. If it is greater than its parent, swap them (this process is called **sift-up** or **heapify-up**).
    
3. Repeat step 2 until the element is less than or equal to its parent, or it reaches the root.
    

**Mathematical Proof:**

In the worst-case scenario, the newly inserted element is the global maximum and must be swapped all the way up to the root.

The number of swaps equals the length of the path from the newly added leaf to the root.

Since the height of the tree is $h = \lfloor \log_2 n \rfloor$, the maximum number of comparisons and swaps is exactly $h$.

$$T(n) \leq c \cdot h = c \cdot \lfloor \log_2 n \rfloor$$

Where $c$ is the constant time taken for one comparison and one swap.

**Time Complexity:** $O(\log n)$  

## 3. Extract Maximum (`Extract-Max`)

**Algorithm:**

1. The maximum element is always at the root. Remove it.
    
2. Replace the root with the last element in the heap (the rightmost leaf at the bottom level).
    
3. Compare the new root with its children. Swap it with the larger of the two children if the child is strictly greater (this process is called **sift-down** or **heapify-down**).
    
4. Repeat step 3 until the element is greater than both its children, or it becomes a leaf.
    

**Mathematical Proof:**

Removing the root and replacing it takes $O(1)$ time.

The `sift-down` operation moves the element down the tree. In the worst case, the element trickles all the way down to a leaf node.

The path length from the root to a leaf is at most the height of the tree $h$.

At each step, we perform at most 2 comparisons (finding the larger child, comparing with the parent) and 1 swap.

$$T(n) \leq c \cdot h = c \cdot \lfloor \log_2 n \rfloor$$

**Time Complexity:** $O(\log n)$  

## 4. Increase Key (`Increase-Key`)

**Algorithm:**

1. Update the value of the key at a specific index $i$ to a new, larger value.
2. Since the value increased, it might violate the max-heap property with its parent. Perform a **sift-up** operation starting from index $i$.

**Mathematical Proof:**

The element can, at worst, travel from a leaf all the way to the root. The mathematical bound is identical to insertion.

Maximum swaps $\leq h = \lfloor \log_2 n \rfloor$.

**Time Complexity:** $O(\log n)$  

## 5. Build Heap (`Build-Heap`)

This is the most mathematically interesting proof. We want to convert an unsorted array of $n$ elements into a max-heap.

**Algorithm:**

We start from the last non-leaf node (index $\lfloor n/2 \rfloor$) and go up to the root (index $1$), calling `sift-down` on each node.

**Naive Analysis:**

We call `sift-down` ($O(\log n)$) $n/2$ times. Therefore, the upper bound is $O(n \log n)$. While technically correct, this bound is not tight. We can prove the actual complexity is $O(n)$.

**Rigorous Mathematical Proof:**

Observe that `sift-down` takes time proportional to the height of the node _from the bottom of the tree_.

- Leaves (**height 0**) take 0 steps to sift down.
    
- Nodes at height 1 take at most 1 step.
    
- The root (at height $h$) takes at most $h$ steps.
    

In a complete binary tree of $n$ nodes, the number of nodes at height $k$ (where leaves are at height 0) is at most:
$$N(k) = \left\lceil \frac{n}{2^{k+1}} \right\rceil$$
(
	This is basically as the level is such that the height at leaves is 0, and that the nodes at a give level is twice the number of elements in the previous root wrt the the main root node (i.e., increase in height with height system used here)
	Using that logic, the number of nodes at height $k$ would then be $2^{h-k}$ where $h$ is height
	This then equates to become $\frac{2^{h+1}}{2^{k+1}}$ and for large $n$ (total number of elements), the factor of $-1$ is negligible.
)

The cost to run `sift-down` on a node at height $k$ is $O(k)$.

The total work $W$ done by `Build-Heap` is the sum of the costs for all nodes at all heights:

  

$$W \leq \sum_{k=0}^{\lfloor \log_2 n \rfloor} \left\lceil \frac{n}{2^{k+1}} \right\rceil \cdot O(k)$$

Dropping the ceiling and the Big-O for the algebraic summation, we factor out $n/2$:

  

$$W \leq \frac{n}{2} \sum_{k=0}^{\lfloor \log_2 n \rfloor} \frac{k}{2^k}$$

To solve this, we evaluate the infinite arithmetico-geometric series $S$:

  

$$S = \sum_{k=0}^{\infty} \frac{k}{2^k}$$$$S = \frac{0}{1} + \frac{1}{2} + \frac{2}{4} + \frac{3}{8} + \frac{4}{16} + \dots \quad \text{--- (Equation 1)}$$

Multiply the entire equation by $\frac{1}{2}$:

  

$$\frac{1}{2}S = \frac{0}{2} + \frac{1}{4} + \frac{2}{8} + \frac{3}{16} + \dots \quad \text{--- (Equation 2)}$$

Subtract Equation 2 from Equation 1 (aligning terms with the same denominators):

  

$$S - \frac{1}{2}S = \left(\frac{1}{2} - 0\right) + \left(\frac{2}{4} - \frac{1}{4}\right) + \left(\frac{3}{8} - \frac{2}{8}\right) + \left(\frac{4}{16} - \frac{3}{16}\right) + \dots$$$$\frac{1}{2}S = \frac{1}{2} + \frac{1}{4} + \frac{1}{8} + \frac{1}{16} + \dots$$

The right side is now an infinite geometric series with the first term $a = \frac{1}{2}$ and common ratio $r = \frac{1}{2}$. The sum of an infinite geometric series is $\frac{a}{1 - r}$:

  

$$\frac{1}{2}S = \frac{1/2}{1 - 1/2} = \frac{1/2}{1/2} = 1$$$$S = 2$$

Substituting $S=2$ back into our total work equation:

Since the sum up to $\lfloor \log_2 n \rfloor$ is strictly less than the infinite sum, we have:

  

$$\sum_{k=0}^{\lfloor \log_2 n \rfloor} \frac{k}{2^k} < \sum_{k=0}^{\infty} \frac{k}{2^k} = 2$$

Therefore, the total work $W$ is bounded by:

  

$$W < \frac{n}{2} \cdot 2 = n$$

**Time Complexity:** $O(n)$  

## Summary Table

|                  |                     |                                                    |
| ---------------- | ------------------- | -------------------------------------------------- |
| **Operation**    | **Time Complexity** | **Brief Reason**                                   |
| **Get Max**      | $O(1)$              | Root node extraction without modification          |
| **Insert**       | $O(\log n)$         | Sift-up traverses at most the height of the tree   |
| **Extract-Max**  | $O(\log n)$         | Sift-down traverses at most the height of the tree |
| **Increase-Key** | $O(\log n)$         | Sift-up traverses at most the height of the tree   |
| **Build-Heap**   | $O(n)$              | Most nodes are at the bottom and do minimal work   |