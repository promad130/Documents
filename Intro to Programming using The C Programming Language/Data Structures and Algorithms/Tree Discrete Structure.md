A **Tree** is a non-linear, hierarchical data structure consisting of **nodes** connected by **edges**. Unlike arrays or linked lists where data is arranged sequentially, trees organize data hierarchically, similar to a family tree.

## Visualization

Here is a visual representation of a basic tree:

```
          [A]  <--- Root Node (Level 0)
         /   \
       /       \
     [B]       [C] <--- Parent Nodes (Level 1)
    /   \        \
  [D]   [E]      [F] <--- Leaf Nodes (Level 2)
```

## Key Terminology

- **Node:** An entity containing data and pointers to its child nodes (e.g., A, B, C).
    
- **Root:** The topmost node of the tree. It has no parent (e.g., A).
    
- **Edge:** The connecting link between any two nodes.
    
- **Parent:** A node that has child nodes (e.g., B is the parent of D and E).
    
- **Child:** A node extending from another node (e.g., D and E are children of B).
    
- **Leaf:** A node with no children. Also called an external node (e.g., D, E, F).
    
- **Subtree:** A tree consisting of a node and all of its descendants (e.g., Node B, D, and E form a subtree).
    

## Real-World Examples

1. **File Systems:** Your computer's directory structure is a tree.
    
    - _Root:_ `C:\` (Windows) or `/` (Mac/Linux)
        
    - _Parents:_ Folders (`Documents`, `Downloads`)
        
    - _Leaves:_ Individual files (`resume.pdf`, `photo.jpg`)
        
2. **HTML DOM:** Web pages are structured as trees.
    
    - `<html>` is the root, containing `<head>` and `<body>` as children. `<body>` might contain `<div>` and `<p>` leaves.
        
3. **Organization Charts:** A company's hierarchy.
    
    - CEO (Root) -> VPs (Parents) -> Managers -> Employees (Leaves).
        

## Code Example: A Basic Tree in C++

Here is how you can define and build the tree visualized above using C++ and standard vectors:

```
#include <iostream>
#include <vector>
#include <string>

class TreeNode {
public:
    std::string data;
    std::vector<TreeNode*> children;

    // Constructor
    TreeNode(std::string val) {
        data = val;
    }

    // Method to add a child node
    void addChild(TreeNode* childNode) {
        children.push_back(childNode);
    }
};

int main() {
    // 1. Create the nodes dynamically
    TreeNode* root = new TreeNode("A");
    TreeNode* nodeB = new TreeNode("B");
    TreeNode* nodeC = new TreeNode("C");
    TreeNode* nodeD = new TreeNode("D");
    TreeNode* nodeE = new TreeNode("E");
    TreeNode* nodeF = new TreeNode("F");

    // 2. Build the tree structure (Connect edges)
    root->addChild(nodeB);
    root->addChild(nodeC);

    nodeB->addChild(nodeD);
    nodeB->addChild(nodeE);

    nodeC->addChild(nodeF);

    // The tree is now built! 
    // 'root' contains pointers to nodeB and nodeC in its children vector.

    // Note: In a complete C++ program, you should also write a function 
    // to recursively delete the nodes to prevent memory leaks!

    return 0;
}
```

## 🌲 Common Types of Trees

- **Binary Tree:** Every node has at most two children (Left and Right).
    
- **Binary Search Tree (BST):** A Binary Tree where the left child's value is less than the parent, and the right child's value is greater. Highly efficient for searching.
    
- **Trie (Prefix Tree):** Used heavily in auto-complete features and spell checkers.