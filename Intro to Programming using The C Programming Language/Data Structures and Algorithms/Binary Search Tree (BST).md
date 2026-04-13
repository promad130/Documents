
A **Binary Search Tree** is a hierarchical data structure where each node has at most two children (left and right), and it maintains a specific ordering property: for every node, all values in its left subtree are smaller, and all values in its right subtree are larger.

### Key Characteristics

- **Ordered Structure**: Left < Parent < Right
- **Time Complexity**: 
  - **Average**: O(log n) for search, insert, delete
  - **Worst**: O(n) when tree becomes skewed
- **No Duplicates**: Typically doesn't allow duplicate values
- **Use Cases**: Searching, sorting, implementing sets/maps

### BST Property

```
For every node N:
- All nodes in left subtree < N
- All nodes in right subtree > N
- Both left and right subtrees are also BSTs
```

### Visual Representation

```
Valid BST:                  Invalid BST:

        50                      50
       /  \                    /  \
      30   70                 30   70
     / \   / \               / \   / \
    20 40 60 80             20 55 60 80  ← 55 > 50, shouldn't be in left subtree!
    
    
Inserting 25:               Searching for 40:

        50                      50
       /  \                    /  \  ← Go left (40 < 50)
      30   70                 30   70
     / \   / \               / \  ← Go right (40 > 30)
    20 40 60 80             20 40  ← Found!
      \
      25  ← New node
```

### Basic Node Structure

```cpp name=bst_node.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;

    // Constructor
    Node(int value) {
        data = value;
        left = nullptr;
        right = nullptr;
    }
};
```

## Complete BST Implementation
---
### Part 1: Node Structure (The Building Block)

```cpp
struct Node {
    int data;
    Node* left;
    Node* right;

    Node(int value) : data(value), left(nullptr), right(nullptr) {}
};
```

#### Explanation

**`struct Node`**
- This is the **fundamental building block** of the BST
- Each node represents **one element** in the tree

**`int data;`**
- Stores the **actual value** of the node
- Could be any data type (int, string, custom object)

**`Node* left;`**
- Pointer to the **left child** node
- Points to nodes with **smaller values** (BST property)
- `nullptr` if no left child exists

**`Node* right;`**
- Pointer to the **right child** node
- Points to nodes with **larger values** (BST property)
- `nullptr` if no right child exists

**Constructor: `Node(int value) : data(value), left(nullptr), right(nullptr) {}`**
- **Initializer list** syntax for efficiency
- Sets `data` to the provided value
- Initializes both pointers to `nullptr` (no children initially)

#### Visual Representation

```
Single Node:
    ┌─────────┐
    │  data   │ ← Stores value (e.g., 50)
    ├─────────┤
    │  left   │ ─→ nullptr or pointer to left child
    ├─────────┤
    │  right  │ ─→ nullptr or pointer to right child
    └─────────┘

Example node with value 50:
Node(50):
    data = 50
    left = nullptr
    right = nullptr
```

---

### Part 2: Class Structure and Root

```cpp
class BST {
private:
    Node* root;
```

#### Explanation

**`private:`**
- Members here are **hidden** from outside access
- Only class methods can access them

**`Node* root;`**
- **The entry point** to the entire tree
- Points to the topmost node
- `nullptr` when tree is empty

#### Why Private?

```cpp
// ❌ BAD - If root was public:
BST tree;
tree.root = nullptr;  // User could break the tree!

// ✓ GOOD - Root is private:
BST tree;
tree.insert(50);  // Only controlled access through methods
```

---

### Part 3: Insert Helper (Recursive Insertion)

```cpp
Node* insertHelper(Node* node, int value) {
    if (node == nullptr) {
        return new Node(value);
    }
    
    if (value < node->data) {
        node->left = insertHelper(node->left, value);
    } else if (value > node->data) {
        node->right = insertHelper(node->right, value);
    }
    // If equal, don't insert (no duplicates)

    return node;
}
```

#### Line-by-Line Breakdown

**Line 1: `Node* insertHelper(Node* node, int value)`**

**Parameters:**
- `Node* node`: Current node we're examining
- `int value`: Value to insert

**Returns:** Pointer to the (possibly new) node at this position

**Line 2-4: Base Case**
```cpp
if (node == nullptr) {
    return new Node(value);
}
```

- **When?** We've reached a `nullptr` (empty spot)
- **Action:** Create a **new node** with the value
- **Return:** Pointer to the newly created node
- **This new node becomes the child** of the parent who called this function

**Line 6-8: Go Left**
```cpp
if (value < node->data) {
    node->left = insertHelper(node->left, value);
}
```

- **Condition:** Value to insert is **smaller** than current node
- **BST Property:** Smaller values go to the **left**
- **Action:** Recursively insert in the **left subtree**
- **Update:** Set `node->left` to the result (might be new node or unchanged)

**Line 9-11: Go Right**
```cpp
else if (value > node->data) {
    node->right = insertHelper(node->right, value);
}
```

- **Condition:** Value to insert is **larger** than current node
- **BST Property:** Larger values go to the **right**
- **Action:** Recursively insert in the **right subtree**

**Line 12: Duplicate Handling**
```cpp
// If equal, don't insert (no duplicates)
```

- If `value == node->data`, we do **nothing**
- This BST doesn't allow duplicates

**Line 14: Return Current Node**
```cpp
return node;
```

- Return the current node (unchanged)
- This **rebuilds the tree** as recursion unwinds

#### Complete Example: Insert 25 into Tree

```
Initial tree:
        50
       /  \
      30   70
     /
    20

Insert 25:

Step 1: insertHelper(root=50, 25)
  25 < 50? YES → Go left
  node->left = insertHelper(node->left=30, 25)

Step 2: insertHelper(node=30, 25)
  25 < 30? YES → Go left
  node->left = insertHelper(node->left=20, 25)

Step 3: insertHelper(node=20, 25)
  25 < 20? NO
  25 > 20? YES → Go right
  node->right = insertHelper(node->right=nullptr, 25)

Step 4: insertHelper(node=nullptr, 25)
  node == nullptr? YES → BASE CASE
  return new Node(25)  ← New node created!

Unwinding recursion:
Step 3 returns: 20->right = new Node(25)
Step 2 returns: 30 (unchanged, but 30->left now has 25)
Step 1 returns: 50 (unchanged)

Final tree:
        50
       /  \
      30   70
     /
    20
      \
      25  ← New node!
```

#### Why Return Node?

```cpp
node->left = insertHelper(node->left, value);
           └─────────┬─────────┘
                     │
          Returns pointer to child
```

The return value allows us to **update the child pointer**, whether it's:
- A newly created node (at base case)
- The same node (unchanged)

---

### Part 4: Search Helper (Finding a Value)

```cpp
bool searchHelper(Node* node, int value) const {
    if (node == nullptr) {
        return false;
    }

    if (value == node->data) {
        return true;
    } else if (value < node->data) {
        return searchHelper(node->left, value);
    } else {
        return searchHelper(node->right, value);
    }
}
```

#### Line-by-Line Breakdown

**Line 1: Function Signature**
```cpp
bool searchHelper(Node* node, int value) const
                                          └──┬──┘
                                             │
                              Doesn't modify the tree
```

- **`const`**: Promises not to modify the tree
- **Returns `bool`**: `true` if found, `false` otherwise

**Lines 2-4: Base Case - Not Found**
```cpp
if (node == nullptr) {
    return false;
}
```

- Reached the end without finding the value
- Value doesn't exist in the tree

**Lines 6-8: Found It!**
```cpp
if (value == node->data) {
    return true;
}
```

- Current node contains the target value
- Search successful!

**Lines 9-11: Search Left**
```cpp
else if (value < node->data) {
    return searchHelper(node->left, value);
}
```

- Target is smaller → Must be in left subtree
- Recursively search left

**Lines 12-14: Search Right**
```cpp
else {
    return searchHelper(node->right, value);
}
```

- Target is larger → Must be in right subtree
- Recursively search right

#### Example: Search for 40

```
Tree:
        50
       /  \
      30   70
     / \
    20 40

Search(40):

Step 1: searchHelper(50, 40)
  40 == 50? NO
  40 < 50? YES → Search left
  return searchHelper(30, 40)

Step 2: searchHelper(30, 40)
  40 == 30? NO
  40 < 30? NO
  40 > 30? YES → Search right
  return searchHelper(40, 40)

Step 3: searchHelper(40, 40)
  40 == 40? YES! → FOUND
  return true

Result: true ✓
```

#### Time Complexity

```
Best case:    O(1)   - Root is target
Average case: O(log n) - Balanced tree
Worst case:   O(n)   - Skewed tree (like linked list)
```

---

### Part 5: Find Minimum (Leftmost Node)

```cpp
Node* findMin(Node* node) const {
    while (node->left != nullptr) {
        node = node->left;
    }
    return node;
}
```

#### Explanation

**Why leftmost = minimum?**

Because of BST property: **left child < parent**

So keep going left until you can't go anymore!

**Line 2: While Loop**
```cpp
while (node->left != nullptr) {
    node = node->left;
}
```

- Keep moving to the left child
- Stop when left child is `nullptr`

**Line 5: Return**
```cpp
return node;
```

- Current node is the leftmost (minimum)

#### Example

```
Tree:
        50
       /  \
      30   70
     / \   / \
    20 40 60 80
   /
  10  ← This is the minimum!

findMin(root=50):
  50->left != nullptr? YES
    node = 30
  30->left != nullptr? YES
    node = 20
  20->left != nullptr? YES
    node = 10
  10->left != nullptr? NO
    Stop!
  return 10 ✓
```

#### Iterative vs Recursive

**Iterative (used here):**
```cpp
Node* findMin(Node* node) const {
    while (node->left != nullptr) {
        node = node->left;
    }
    return node;
}
```

**Recursive alternative:**
```cpp
Node* findMin(Node* node) const {
    if (node->left == nullptr) {
        return node;
    }
    return findMin(node->left);
}
```

Both work! Iterative is slightly more efficient (no recursion overhead).

---

### Part 6: Delete Helper (The Most Complex!)

```cpp
Node* deleteHelper(Node* node, int value) {
    if (node == nullptr) {
        return nullptr;
    }

    if (value < node->data) {
        node->left = deleteHelper(node->left, value);
    } else if (value > node->data) {
        node->right = deleteHelper(node->right, value);
    } else {
        // Node found - three cases

        // Case 1: Leaf node (no children)
        if (node->left == nullptr && node->right == nullptr) {
            delete node;
            return nullptr;
        }

        // Case 2: One child
        if (node->left == nullptr) {
            Node* temp = node->right;
            delete node;
            return temp;
        }
        if (node->right == nullptr) {
            Node* temp = node->left;
            delete node;
            return temp;
        }

        // Case 3: Two children
        // Find inorder successor (smallest in right subtree)
        Node* successor = findMin(node->right);
        node->data = successor->data;
        node->right = deleteHelper(node->right, successor->data);
    }

    return node;
}
```

#### The Three Cases of Deletion
##### Case 1: Leaf Node (No Children)

```cpp
if (node->left == nullptr && node->right == nullptr) {
    delete node;
    return nullptr;
}
```

**Visual:**
```
Before:
    30
   /
  20  ← Delete this leaf

After:
    30
   /
 (null)

Action: Simply delete the node and return nullptr
```

##### Case 2a: Only Right Child

```cpp
if (node->left == nullptr) {
    Node* temp = node->right;
    delete node;
    return temp;
}
```

**Visual:**
```
Before:
    30
      \
      40  ← Delete 30 (has only right child)
        \
        50

After:
    40  ← Right child takes 30's place
      \
      50

Action: Delete node, return its right child to replace it
```

##### Case 2b: Only Left Child

```cpp
if (node->right == nullptr) {
    Node* temp = node->left;
    delete node;
    return temp;
}
```

**Visual:**
```
Before:
      30
     /    ← Delete 30 (has only left child)
    20
   /
  10

After:
    20  ← Left child takes 30's place
   /
  10

Action: Delete node, return its left child to replace it
```

##### Case 3: Two Children (Most Complex!)

```cpp
// Find inorder successor (smallest in right subtree)
Node* successor = findMin(node->right);
node->data = successor->data;
node->right = deleteHelper(node->right, successor->data);
```

**Strategy:** Replace node's value with its **inorder successor**, then delete the successor.

**Inorder successor:** The **smallest value in the right subtree** (leftmost node in right subtree).

**Visual:**
```
Before:
        50  ← Delete this (has two children)
       /  \
      30   70
     / \   / \
    20 40 60 80

Step 1: Find inorder successor
  - Go to right subtree (70)
  - Find minimum in that subtree (60)
  - Successor = 60

Step 2: Replace 50's data with 60's data
        60  ← Data copied from successor
       /  \
      30   70
     / \   / \
    20 40 60 80
          ↑
      Original 60 (now duplicate)

Step 3: Delete the successor (60) from right subtree
  - Call deleteHelper(70, 60)
  - 60 is a leaf, so it's deleted easily

Final:
        60  ← Takes 50's place
       /  \
      30   70
     / \     \
    20 40    80
```

**Why inorder successor?**

The inorder successor is guaranteed to:
1. Be **larger** than everything in the left subtree
2. Be **smaller** than everything else in the right subtree
3. Have **at most one child** (right child only), making it easy to delete

---

### Part 7: Inorder Traversal (Sorted Order)

```cpp
void inorderHelper(Node* node) const {
    if (node != nullptr) {
        inorderHelper(node->left);   // Visit left
        cout << node->data << " ";    // Visit root
        inorderHelper(node->right);   // Visit right
    }
}
```

#### Order: Left → Root → Right

**Why this gives sorted order?**

BST property: Left < Root < Right
So visiting left first, then root, then right gives **ascending order**!

**EXAMPLE:**
```
Tree:
        50
       /  \
      30   70
     / \   / \
    20 40 60 80

Inorder traversal:

inorderHelper(50):
  Visit left (30):
    inorderHelper(30):
      Visit left (20):
        inorderHelper(20):
          Visit left (null) ← Base case
          Print 20
          Visit right (null) ← Base case
      Print 30
      Visit right (40):
        inorderHelper(40):
          Visit left (null)
          Print 40
          Visit right (null)
  Print 50
  Visit right (70):
    inorderHelper(70):
      Visit left (60):
        inorderHelper(60):
          Visit left (null)
          Print 60
          Visit right (null)
      Print 70
      Visit right (80):
        inorderHelper(80):
          Visit left (null)
          Print 80
          Visit right (null)

Output: 20 30 40 50 60 70 80 ← Sorted! ✓
```

---

### Part 8: Preorder Traversal

```cpp
void preorderHelper(Node* node) const {
    if (node != nullptr) {
        cout << node->data << " ";    // Visit root FIRST
        preorderHelper(node->left);   // Then left
        preorderHelper(node->right);  // Then right
    }
}
```

#### Order: Root → Left → Right

**Use case:** Creating a copy of the tree (process root before children)

#### Example

```
Tree:
        50
       /  \
      30   70
     / \   / \
    20 40 60 80

Preorder: 50 30 20 40 70 60 80

Process root first, then its children!
```

---

### Part 9: Postorder Traversal

```cpp
void postorderHelper(Node* node) const {
    if (node != nullptr) {
        postorderHelper(node->left);   // Visit left
        postorderHelper(node->right);  // Visit right
        cout << node->data << " ";      // Visit root LAST
    }
}
```

#### Order: Left → Right → Root

**Use case:** Deleting a tree (process children before parent)

#### Example

```
Tree:
        50
       /  \
      30   70
     / \   / \
    20 40 60 80

Postorder: 20 40 30 60 80 70 50

Process children before parent!
```

---

### Part 10: Level Order Traversal (BFS)

```cpp
void levelOrder() const {
    if (root == nullptr) {
        cout << "Tree is empty" << endl;
        return;
    }

    cout << "Level Order: ";
    queue<Node*> q;
    q.push(root);

    while (!q.empty()) {
        Node* current = q.front();
        q.pop();

        cout << current->data << " ";

        if (current->left != nullptr) {
            q.push(current->left);
        }
        if (current->right != nullptr) {
            q.push(current->right);
        }
    }
    cout << endl;
}
```

#### Uses a Queue (FIFO)

**Algorithm:**
1. Start with root in queue
2. While queue not empty:
   - Dequeue a node
   - Print it
   - Enqueue its children (left, then right)

#### Example

```
Tree:
        50
       /  \
      30   70
     / \   / \
    20 40 60 80

Level order:

Initial: queue = [50]

Step 1: Dequeue 50, print 50
  Enqueue children: queue = [30, 70]

Step 2: Dequeue 30, print 30
  Enqueue children: queue = [70, 20, 40]

Step 3: Dequeue 70, print 70
  Enqueue children: queue = [20, 40, 60, 80]

Step 4: Dequeue 20, print 20
  No children: queue = [40, 60, 80]

Step 5: Dequeue 40, print 40
  No children: queue = [60, 80]

Step 6: Dequeue 60, print 60
  No children: queue = [80]

Step 7: Dequeue 80, print 80
  No children: queue = []

Output: 50 30 70 20 40 60 80
```

---

### Part 11: Height Calculation

```cpp
int heightHelper(Node* node) const {
    if (node == nullptr) {
        return -1;  // Height of empty tree is -1
    }
    return 1 + max(heightHelper(node->left), heightHelper(node->right));
}
```

#### Definition

**Height:** Number of edges on the longest path from node to a leaf.

**Why -1 for empty tree?**
- Leaf node has height 0 (no edges below it)
- Parent of leaf: `1 + max(-1, -1) = 0` (correct!)

#### Example

```
Tree:
        50  ← height 2
       /  \
      30   70  ← height 1
     / \   / \
    20 40 60 80  ← height 0 (leaves)

height(50):
  return 1 + max(height(30), height(70))
  
  height(30):
    return 1 + max(height(20), height(40))
    
    height(20):
      return 1 + max(height(null), height(null))
      return 1 + max(-1, -1) = 0
    
    height(40):
      return 0
    
    return 1 + max(0, 0) = 1
  
  height(70):
    return 1
  
  return 1 + max(1, 1) = 2 ✓
```

---

### Part 12: Count Nodes

```cpp
int countNodesHelper(Node* node) const {
    if (node == nullptr) {
        return 0;
    }
    return 1 + countNodesHelper(node->left) + countNodesHelper(node->right);
}
```

#### Simple Recursive Count

```
count(node) = 1 (self) + count(left) + count(right)
```

#### Example

```
Tree:
        50
       /  \
      30   70
     / \
    20 40

count(50):
  = 1 + count(30) + count(70)
  
  count(30):
    = 1 + count(20) + count(40)
    = 1 + (1 + 0 + 0) + (1 + 0 + 0)
    = 1 + 1 + 1 = 3
  
  count(70):
    = 1 + 0 + 0 = 1
  
  = 1 + 3 + 1 = 5 ✓
```

---

### Part 13: Clear Tree (Destructor)

```cpp
void clearHelper(Node* node) {
    if (node != nullptr) {
        clearHelper(node->left);
        clearHelper(node->right);
        delete node;
    }
}
```

#### Postorder Deletion

**Why postorder?** Must delete children before parent!

```
Tree:
        50
       /  \
      30   70

Delete order (postorder):
1. Delete 30's children (if any)
2. Delete 30
3. Delete 70's children (if any)
4. Delete 70
5. Delete 50

Never delete a parent before its children!
```

---

### Part 14: Public Interface

```cpp
public:
    BST() : root(nullptr) {}
    
    ~BST() {
        clear();
    }
    
    void insert(int value) {
        root = insertHelper(root, value);
        cout << "Inserted: " << value << endl;
    }
    
    // ... other public methods
```

#### Constructor

```cpp
BST() : root(nullptr) {}
```

- Initialize `root` to `nullptr` (empty tree)

#### Destructor

```cpp
~BST() {
    clear();
}
```

- Automatically called when BST object is destroyed
- Frees all memory to prevent leaks

#### Public Methods

All public methods are **wrappers** that call private helper functions:

```cpp
void insert(int value) {
    root = insertHelper(root, value);
}
```

- User doesn't need to know about `root` pointer
- Clean, simple interface

---

### Time Complexity

| Operation | Best | Average | Worst |
|-----------|------|---------|-------|
| **Search** | O(1) | O(log n) | O(n) |
| **Insert** | O(1) | O(log n) | O(n) |
| **Delete** | O(1) | O(log n) | O(n) |
| **Traversal** | O(n) | O(n) | O(n) |
| **Height** | O(n) | O(n) | O(n) |

**Worst case (O(n))** happens when tree becomes a **linked list** (all nodes in one direction).

**Best/Average case (O(log n))** happens when tree is **balanced**.



### Iterative Insert and Search

```cpp name=bst_iterative.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;

    Node(int value) : data(value), left(nullptr), right(nullptr) {}
};

class BST {
private:
    Node* root;

public:
    BST() : root(nullptr) {}

    // Iterative insert
    void insertIterative(int value) {
        Node* newNode = new Node(value);

        if (root == nullptr) {
            root = newNode;
            cout << "Inserted " << value << " as root" << endl;
            return;
        }

        Node* current = root;
        Node* parent = nullptr;

        while (current != nullptr) {
            parent = current;

            if (value < current->data) {
                current = current->left;
            } else if (value > current->data) {
                current = current->right;
            } else {
                // Duplicate value
                delete newNode;
                cout << "Duplicate value " << value << " not inserted" << endl;
                return;
            }
        }

        // Insert as child of parent
        if (value < parent->data) {
            parent->left = newNode;
            cout << "Inserted " << value << " as left child of " << parent->data << endl;
        } else {
            parent->right = newNode;
            cout << "Inserted " << value << " as right child of " << parent->data << endl;
        }
    }

    // Iterative search
    bool searchIterative(int value) const {
        Node* current = root;

        while (current != nullptr) {
            if (value == current->data) {
                return true;
            } else if (value < current->data) {
                current = current->left;
            } else {
                current = current->right;
            }
        }

        return false;
    }

    void inorder() const {
        inorderHelper(root);
        cout << endl;
    }

private:
    void inorderHelper(Node* node) const {
        if (node != nullptr) {
            inorderHelper(node->left);
            cout << node->data << " ";
            inorderHelper(node->right);
        }
    }
};

int main() {
    cout << "=== Iterative BST Operations ===" << endl;

    BST bst;

    cout << "\n--- Inserting elements ---" << endl;
    bst.insertIterative(50);
    bst.insertIterative(30);
    bst.insertIterative(70);
    bst.insertIterative(20);
    bst.insertIterative(40);

    cout << "\n--- Searching ---" << endl;
    cout << "Search 40: " << (bst.searchIterative(40) ? "Found" : "Not Found") << endl;
    cout << "Search 100: " << (bst.searchIterative(100) ? "Found" : "Not Found") << endl;

    cout << "\n--- Inorder Traversal ---" << endl;
    bst.inorder();

    return 0;
}
```

### BST Deletion - Detailed Explanation

```cpp name=bst_deletion_detailed.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

class BSTDeletion {
private:
    Node* root;

    Node* findMin(Node* node) {
        while (node->left != nullptr) {
            node = node->left;
        }
        return node;
    }

    void visualize(Node* node, string prefix = "", bool isLeft = true) {
        if (node != nullptr) {
            cout << prefix;
            cout << (isLeft ? "├──" : "└──");
            cout << node->data << endl;

            visualize(node->left, prefix + (isLeft ? "│   " : "    "), true);
            visualize(node->right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

public:
    BSTDeletion() : root(nullptr) {}

    void insert(int value) {
        root = insertHelper(root, value);
    }

    Node* insertHelper(Node* node, int value) {
        if (node == nullptr) return new Node(value);

        if (value < node->data) {
            node->left = insertHelper(node->left, value);
        } else if (value > node->data) {
            node->right = insertHelper(node->right, value);
        }

        return node;
    }

    void remove(int value) {
        cout << "\n=== Deleting " << value << " ===" << endl;
        cout << "Before deletion:" << endl;
        visualize(root);

        root = deleteNode(root, value);

        cout << "\nAfter deletion:" << endl;
        visualize(root);
    }

    Node* deleteNode(Node* node, int value) {
        if (node == nullptr) {
            cout << "Value not found!" << endl;
            return nullptr;
        }

        if (value < node->data) {
            node->left = deleteNode(node->left, value);
        } else if (value > node->data) {
            node->right = deleteNode(node->right, value);
        } else {
            // Node found!

            // CASE 1: Leaf node (no children)
            if (node->left == nullptr && node->right == nullptr) {
                cout << "Case 1: Leaf node - simply delete" << endl;
                delete node;
                return nullptr;
            }

            // CASE 2: One child (right only)
            if (node->left == nullptr) {
                cout << "Case 2: Node has only right child - replace with right child" << endl;
                Node* temp = node->right;
                delete node;
                return temp;
            }

            // CASE 2: One child (left only)
            if (node->right == nullptr) {
                cout << "Case 2: Node has only left child - replace with left child" << endl;
                Node* temp = node->left;
                delete node;
                return temp;
            }

            // CASE 3: Two children
            cout << "Case 3: Node has two children - find inorder successor" << endl;
            Node* successor = findMin(node->right);
            cout << "Inorder successor is: " << successor->data << endl;
            node->data = successor->data;
            node->right = deleteNode(node->right, successor->data);
        }

        return node;
    }
};

int main() {
    cout << "=== BST Deletion Cases ===" << endl;

    BSTDeletion bst;

    // Build tree
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);
    bst.insert(10);
    bst.insert(25);

    /*
            50
           /  \
          30   70
         / \   / \
        20 40 60 80
       / \
      10 25
    */

    // Case 1: Delete leaf node
    bst.remove(10);

    // Case 2: Delete node with one child
    bst.remove(20);

    // Case 3: Delete node with two children
    bst.remove(30);

    // Delete root
    bst.remove(50);

    return 0;
}
```

### BST Validation

```cpp name=validate_bst.cpp
#include <iostream>
#include <climits>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

class BSTValidator {
public:
    // Check if tree is a valid BST
    bool isValidBST(Node* root) {
        return isValidBSTHelper(root, LONG_MIN, LONG_MAX);
    }

private:
    bool isValidBSTHelper(Node* node, long minValue, long maxValue) {
        if (node == nullptr) {
            return true;
        }

        // Current node must be within valid range
        if (node->data <= minValue || node->data >= maxValue) {
            return false;
        }

        // Check left subtree: all values must be < node->data
        // Check right subtree: all values must be > node->data
        return isValidBSTHelper(node->left, minValue, node->data) &&
               isValidBSTHelper(node->right, node->data, maxValue);
    }
};

int main() {
    cout << "=== BST Validation ===" << endl;

    // Valid BST
    Node* validBST = new Node(50);
    validBST->left = new Node(30);
    validBST->right = new Node(70);
    validBST->left->left = new Node(20);
    validBST->left->right = new Node(40);

    BSTValidator validator;
    cout << "\nValid BST: " << (validator.isValidBST(validBST) ? "YES" : "NO") << endl;

    // Invalid BST
    Node* invalidBST = new Node(50);
    invalidBST->left = new Node(30);
    invalidBST->right = new Node(70);
    invalidBST->left->left = new Node(20);
    invalidBST->left->right = new Node(55);  // 55 > 50, shouldn't be in left subtree!

    cout << "Invalid BST: " << (validator.isValidBST(invalidBST) ? "YES" : "NO") << endl;

    return 0;
}
```

### Common BST Operations

#### 1. Find K-th Smallest Element

```cpp name=kth_smallest.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

class BST {
private:
    Node* root;
    int count;
    int result;

    void kthSmallestHelper(Node* node, int k) {
        if (node == nullptr || count >= k) {
            return;
        }

        // Inorder traversal gives sorted order
        kthSmallestHelper(node->left, k);

        count++;
        if (count == k) {
            result = node->data;
            return;
        }

        kthSmallestHelper(node->right, k);
    }

public:
    BST() : root(nullptr) {}

    void insert(int value) {
        root = insertHelper(root, value);
    }

    Node* insertHelper(Node* node, int value) {
        if (node == nullptr) return new Node(value);
        if (value < node->data) node->left = insertHelper(node->left, value);
        else if (value > node->data) node->right = insertHelper(node->right, value);
        return node;
    }

    int kthSmallest(int k) {
        count = 0;
        result = -1;
        kthSmallestHelper(root, k);
        return result;
    }
};

int main() {
    cout << "=== K-th Smallest Element ===" << endl;

    BST bst;
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);

    // Sorted: 20, 30, 40, 50, 60, 70, 80

    cout << "\n1st smallest: " << bst.kthSmallest(1) << endl;  // 20
    cout << "3rd smallest: " << bst.kthSmallest(3) << endl;     // 40
    cout << "5th smallest: " << bst.kthSmallest(5) << endl;     // 60

    return 0;
}
```

### 2. Lowest Common Ancestor (LCA)

```cpp name=lca_bst.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

class BST {
private:
    Node* root;

    Node* findLCA(Node* node, int n1, int n2) {
        if (node == nullptr) {
            return nullptr;
        }

        // If both nodes are smaller, LCA is in left subtree
        if (n1 < node->data && n2 < node->data) {
            return findLCA(node->left, n1, n2);
        }

        // If both nodes are larger, LCA is in right subtree
        if (n1 > node->data && n2 > node->data) {
            return findLCA(node->right, n1, n2);
        }

        // If nodes are on different sides, current node is LCA
        return node;
    }

public:
    BST() : root(nullptr) {}

    void insert(int value) {
        root = insertHelper(root, value);
    }

    Node* insertHelper(Node* node, int value) {
        if (node == nullptr) return new Node(value);
        if (value < node->data) node->left = insertHelper(node->left, value);
        else if (value > node->data) node->right = insertHelper(node->right, value);
        return node;
    }

    int lowestCommonAncestor(int n1, int n2) {
        Node* lca = findLCA(root, n1, n2);
        return lca ? lca->data : -1;
    }
};

int main() {
    cout << "=== Lowest Common Ancestor ===" << endl;

    BST bst;
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);

    /*
            50
           /  \
          30   70
         / \   / \
        20 40 60 80
    */

    cout << "\nLCA of 20 and 40: " << bst.lowestCommonAncestor(20, 40) << endl;  // 30
    cout << "LCA of 20 and 60: " << bst.lowestCommonAncestor(20, 60) << endl;    // 50
    cout << "LCA of 60 and 80: " << bst.lowestCommonAncestor(60, 80) << endl;    // 70

    return 0;
}
```

### 3. Convert Sorted Array to BST

```cpp name=sorted_array_to_bst.cpp
#include <iostream>
#include <vector>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

class BSTBuilder {
public:
    Node* sortedArrayToBST(vector<int>& arr, int start, int end) {
        if (start > end) {
            return nullptr;
        }

        // Middle element becomes root
        int mid = start + (end - start) / 2;
        Node* root = new Node(arr[mid]);

        // Recursively build left and right subtrees
        root->left = sortedArrayToBST(arr, start, mid - 1);
        root->right = sortedArrayToBST(arr, mid + 1, end);

        return root;
    }

    void inorder(Node* node) {
        if (node != nullptr) {
            inorder(node->left);
            cout << node->data << " ";
            inorder(node->right);
        }
    }

    void visualize(Node* node, string prefix = "", bool isLeft = true) {
        if (node != nullptr) {
            cout << prefix;
            cout << (isLeft ? "├──" : "└──");
            cout << node->data << endl;

            visualize(node->left, prefix + (isLeft ? "│   " : "    "), true);
            visualize(node->right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
};

int main() {
    cout << "=== Convert Sorted Array to Balanced BST ===" << endl;

    vector<int> arr = {10, 20, 30, 40, 50, 60, 70};

    cout << "\nSorted Array: ";
    for (int x : arr) cout << x << " ";
    cout << endl;

    BSTBuilder builder;
    Node* root = builder.sortedArrayToBST(arr, 0, arr.size() - 1);

    cout << "\nBalanced BST:" << endl;
    builder.visualize(root);

    cout << "\nInorder Traversal: ";
    builder.inorder(root);
    cout << endl;

    return 0;
}
```

### 4. Range Sum Query

```cpp name=range_sum_bst.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

class BST {
private:
    Node* root;

    int rangeSumHelper(Node* node, int low, int high) {
        if (node == nullptr) {
            return 0;
        }

        int sum = 0;

        // If current node is in range, add it
        if (node->data >= low && node->data <= high) {
            sum += node->data;
        }

        // If left subtree can have values in range
        if (node->data > low) {
            sum += rangeSumHelper(node->left, low, high);
        }

        // If right subtree can have values in range
        if (node->data < high) {
            sum += rangeSumHelper(node->right, low, high);
        }

        return sum;
    }

public:
    BST() : root(nullptr) {}

    void insert(int value) {
        root = insertHelper(root, value);
    }

    Node* insertHelper(Node* node, int value) {
        if (node == nullptr) return new Node(value);
        if (value < node->data) node->left = insertHelper(node->left, value);
        else if (value > node->data) node->right = insertHelper(node->right, value);
        return node;
    }

    int rangeSum(int low, int high) {
        return rangeSumHelper(root, low, high);
    }
};

int main() {
    cout << "=== Range Sum Query in BST ===" << endl;

    BST bst;
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);

    /*
            50
           /  \
          30   70
         / \   / \
        20 40 60 80
    */

    cout << "\nSum of nodes in range [30, 60]: " << bst.rangeSum(30, 60) << endl;  // 30+40+50+60=180
    cout << "Sum of nodes in range [20, 40]: " << bst.rangeSum(20, 40) << endl;    // 20+30+40=90
    cout << "Sum of nodes in range [60, 80]: " << bst.rangeSum(60, 80) << endl;    // 60+70+80=210

    return 0;
}
```

### Time Complexity Analysis

| Operation | Average Case | Worst Case | Best Case |
|-----------|-------------|------------|-----------|
| **Search** | O(log n) | O(n) | O(1) |
| **Insert** | O(log n) | O(n) | O(1) |
| **Delete** | O(log n) | O(n) | O(1) |
| **Find Min/Max** | O(log n) | O(n) | O(1) |
| **Inorder Traversal** | O(n) | O(n) | O(n) |

**Why Worst Case O(n)?**

When tree becomes skewed (like a linked list):

```
Balanced BST (O(log n)):    Skewed BST (O(n)):

        50                      10
       /  \                       \
      30   70                     20
     / \   / \                      \
    20 40 60 80                     30
                                      \
                                      40
                                        \
                                        50

Height = log n              Height = n
```

### Space Complexity

- **Storage**: O(n) for n nodes
- **Recursion Stack**: 
  - Average: O(log n)
  - Worst: O(n) for skewed tree

### BST vs Other Data Structures

| Structure | Search | Insert | Delete | Sorted Output |
|-----------|--------|--------|--------|---------------|
| **BST** | O(log n)* | O(log n)* | O(log n)* | O(n) |
| **Array (sorted)** | O(log n) | O(n) | O(n) | O(n) |
| **Linked List** | O(n) | O(1) | O(n) | O(n log n) |
| **Hash Table** | O(1) | O(1) | O(1) | O(n log n) |

*Average case; O(n) worst case for unbalanced BST

### Advantages & Disadvantages

#### Advantages
- **Fast search**: O(log n) average case
- **Dynamic**: Easy insertion and deletion
- **Ordered**: Inorder traversal gives sorted data
- **Range queries**: Efficient range searches
- **No extra space**: Unlike hash tables

#### Disadvantages
- **Can become unbalanced**: Worst case O(n)
- **No random access**: Unlike arrays
- **Extra pointers**: Uses more memory than arrays
- **Not cache-friendly**: Random memory access


### BST Traversal Summary

```cpp name=traversal_summary.cpp
#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;
    Node(int val) : data(val), left(nullptr), right(nullptr) {}
};

void inorder(Node* node) {
    // Left, Root, Right → Sorted order
    if (node != nullptr) {
        inorder(node->left);
        cout << node->data << " ";
        inorder(node->right);
    }
}

void preorder(Node* node) {
    // Root, Left, Right → Create copy of tree
    if (node != nullptr) {
        cout << node->data << " ";
        preorder(node->left);
        preorder(node->right);
    }
}

void postorder(Node* node) {
    // Left, Right, Root → Delete tree
    if (node != nullptr) {
        postorder(node->left);
        postorder(node->right);
        cout << node->data << " ";
    }
}

int main() {
    /*
            50
           /  \
          30   70
         / \   / \
        20 40 60 80
    */

    Node* root = new Node(50);
    root->left = new Node(30);
    root->right = new Node(70);
    root->left->left = new Node(20);
    root->left->right = new Node(40);
    root->right->left = new Node(60);
    root->right->right = new Node(80);

    cout << "Inorder (sorted):   ";
    inorder(root);
    cout << endl;
    // Output: 20 30 40 50 60 70 80

    cout << "Preorder:           ";
    preorder(root);
    cout << endl;
    // Output: 50 30 20 40 70 60 80

    cout << "Postorder:          ";
    postorder(root);
    cout << endl;
    // Output: 20 40 30 60 80 70 50

    return 0;
}
```

### Key Takeaways

1. **BST Property**: Left < Root < Right
2. **Average O(log n)**: For balanced trees
3. **Worst O(n)**: For skewed trees (use AVL/Red-Black for guaranteed balance)
4. **Inorder = Sorted**: Inorder traversal gives sorted order
5. **Three Delete Cases**: Leaf, one child, two children
6. **Common Uses**:
   - Implementing sets and maps
   - Database indexing
   - Expression trees
   - Auto-complete features
   - File system organization

7. **Balanced BST Variants**:
   - **AVL Tree**: Strictly balanced (height diff ≤ 1)
   - **Red-Black Tree**: Less strict, used in C++ STL (map, set)
   - **Splay Tree**: Recently accessed items near root
   - **B-Tree**: Used in databases