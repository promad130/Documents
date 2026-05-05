***(First read [[Tree Discrete Structure]])*** 

A **Binary Tree** is a hierarchical data structure where each node has at most two children, referred to as the **left child** and the **right child**.

## 1. The Basic Structure (C++)

In C++, a binary tree node is typically represented using a `struct` or `class` with pointers to its children.

```
struct Node {
    int data;
    Node* left;
    Node* right;
    
    // Constructor
    Node(int val) {
        data = val;
        left = nullptr;
        right = nullptr;
    }
};
```

## 2. Types of Binary Trees

Here is a breakdown of the different classifications of binary trees based on their structure.

### A. Full Binary Tree (Strict/Proper Binary Tree)

**Definition:** Every node has either **0 or 2 children**. No node has exactly 1 child.

**Visualization:**

```
       1
     /   \
    2     3
  /   \
 4     5
```

_(Node 1 has 2, Node 2 has 2, Nodes 3, 4, 5 have 0. This is a Full Binary Tree)._

### B. Perfect Binary Tree

**Definition:** All internal nodes have **exactly 2 children**, and all leaf nodes are at the **exact same level**.

**Visualization:**

```
       1
     /   \
    2     3
   / \   / \
  4   5 6   7
```

_(Perfectly symmetrical. If height is_ $h$_, it has exactly_ $2^{h+1} - 1$ _nodes)._

### C. Complete Binary Tree

**Definition:** Every level is completely filled, except possibly the last level, which must be filled **from left to right** without any gaps.

**Visualization:**

```
       1
     /   \
    2     3
   / \   / 
  4   5 6   
```

_(Valid Complete Tree: Level 2 is filling left-to-right. If node 6 was the right child of 3 instead of left, it would NOT be complete)._

### D. Degenerate (Pathological) Tree

**Definition:** Every internal node has **exactly one child**. It essentially degrades into a Linked List.

**Visualization:**

```
    1
     \
      2
       \
        3
       /
      4
```

_(Performance degrades to_ $O(N)$ _for operations instead of_ $O(\log N)$_)._

### E. Balanced Binary Tree

**Definition:** The height difference between the left and right subtrees of **any node** is at most 1. Examples include AVL Trees and Red-Black Trees.

**Visualization:**

```
       1
     /   \
    2     3
   /     / 
  4     5   
```

_(Left subtree height of 1 is 2, Right subtree height is 2. Difference is 0. This is balanced)._

## 3. The Binary Search Tree (BST) - The Practical Application

A **Binary Search Tree** is a specific type of binary tree that enforces a strict ordering rule, making data retrieval extremely fast ($O(\log N)$).

**The BST Rule:**

1. The left subtree of a node contains only nodes with keys **less than** the node's key.
2. The right subtree of a node contains only nodes with keys **greater than** the node's key.
3. Left and right subtrees must also be binary search trees.

**Visualization:**

```
       8
     /   \
    3     10
   / \      \
  1   6      14
     / \     /
    4   7   13
```

_(Notice how everything to the left of 8 is < 8, and everything to the right is > 8)._