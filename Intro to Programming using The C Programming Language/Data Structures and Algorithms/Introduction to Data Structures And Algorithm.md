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
## Stack

# Stack Data Structure

A **Stack** is a linear data structure that follows the **LIFO (Last In, First Out)** principle. Think of it like a stack of plates: you add plates on top and remove them from the top. The last plate you put on is the first one you take off.

## Key Characteristics

- **LIFO**: Last In, First Out
- **Restricted Access**: Can only access the top element
- **Time Complexity**: O(1) for push, pop, and peek operations
- **Use Cases**: Function calls, undo/redo, expression evaluation, backtracking

## Basic Operations

1. **Push**: Add an element to the top
2. **Pop**: Remove and return the top element
3. **Peek/Top**: View the top element without removing it
4. **isEmpty**: Check if stack is empty
5. **Size**: Get number of elements

## Visual Representation

```
Push operations:          Pop operations:

                         ┌─────┐
    Push(5)              │  3  │ ← Pop() returns 3
    ──────→              ├─────┤
                         │  7  │
    ┌─────┐             ├──��──┤
    │  5  │ ← Top       │  5  │ ← New Top
    └─────┘             └─────┘

    Push(7)
    ──────→

    ┌─────┐
    │  7  │ ← Top
    ├─────┤
    │  5  │
    └─────┘

    Push(3)
    ──────→

    ┌─────┐
    │  3  │ ← Top
    ├─────┤
    │  7  │
    ├─────┤
    │  5  │
    └─────┘
```

## Implementation Methods

Stacks can be implemented using:
1. **Arrays** (fixed or dynamic)
2. **Linked Lists**

## Array-Based Implementation

```cpp name=stack_array.cpp
#include <iostream>
#include <stdexcept>
using namespace std;

class Stack {
private:
    int* arr;           // Array to store stack elements
    int capacity;       // Maximum size of stack
    int topIndex;       // Index of top element (-1 when empty)

public:
    // Constructor
    Stack(int size = 100) {
        arr = new int[size];
        capacity = size;
        topIndex = -1;
    }

    // Destructor
    ~Stack() {
        delete[] arr;
    }

    // Push element onto stack
    void push(int value) {
        if (isFull()) {
            throw overflow_error("Stack Overflow! Cannot push " + to_string(value));
        }
        arr[++topIndex] = value;
        cout << "Pushed: " << value << endl;
    }

    // Pop element from stack
    int pop() {
        if (isEmpty()) {
            throw underflow_error("Stack Underflow! Cannot pop from empty stack");
        }
        return arr[topIndex--];
    }

    // Get top element without removing it
    int peek() const {
        if (isEmpty()) {
            throw underflow_error("Stack is empty! No top element");
        }
        return arr[topIndex];
    }

    // Check if stack is empty
    bool isEmpty() const {
        return topIndex == -1;
    }

    // Check if stack is full
    bool isFull() const {
        return topIndex == capacity - 1;
    }

    // Get current size
    int size() const {
        return topIndex + 1;
    }

    // Display stack contents
    void display() const {
        if (isEmpty()) {
            cout << "Stack is empty" << endl;
            return;
        }

        cout << "Stack (top to bottom): ";
        for (int i = topIndex; i >= 0; i--) {
            cout << arr[i];
            if (i > 0) cout << " <- ";
        }
        cout << endl;
    }

    // Clear the stack
    void clear() {
        topIndex = -1;
        cout << "Stack cleared" << endl;
    }
};

int main() {
    cout << "=== Array-Based Stack Implementation ===" << endl;

    Stack stack(5);  // Create stack with capacity 5

    // Test isEmpty
    cout << "\nIs empty? " << (stack.isEmpty() ? "Yes" : "No") << endl;

    // Push elements
    cout << "\n--- Pushing elements ---" << endl;
    stack.push(10);
    stack.push(20);
    stack.push(30);
    stack.push(40);

    // Display stack
    cout << "\n";
    stack.display();
    cout << "Size: " << stack.size() << endl;

    // Peek top element
    cout << "\nTop element: " << stack.peek() << endl;

    // Pop elements
    cout << "\n--- Popping elements ---" << endl;
    cout << "Popped: " << stack.pop() << endl;
    cout << "Popped: " << stack.pop() << endl;

    cout << "\n";
    stack.display();

    // Push more elements
    cout << "\n--- Pushing more elements ---" << endl;
    stack.push(50);
    stack.push(60);
    stack.push(70);

    cout << "\n";
    stack.display();

    // Test overflow
    cout << "\n--- Testing overflow ---" << endl;
    try {
        stack.push(80);  // This should cause overflow
    } catch (const overflow_error& e) {
        cout << "Exception: " << e.what() << endl;
    }

    // Pop all elements
    cout << "\n--- Popping all elements ---" << endl;
    while (!stack.isEmpty()) {
        cout << "Popped: " << stack.pop() << endl;
    }

    // Test underflow
    cout << "\n--- Testing underflow ---" << endl;
    try {
        stack.pop();  // This should cause underflow
    } catch (const underflow_error& e) {
        cout << "Exception: " << e.what() << endl;
    }

    return 0;
}
```

## Linked List-Based Implementation

```cpp name=stack_linked_list.cpp
#include <iostream>
#include <stdexcept>
using namespace std;

// Node structure
struct Node {
    int data;
    Node* next;

    Node(int value) : data(value), next(nullptr) {}
};

class Stack {
private:
    Node* topNode;      // Pointer to top of stack
    int count;          // Number of elements

public:
    // Constructor
    Stack() : topNode(nullptr), count(0) {}

    // Destructor
    ~Stack() {
        clear();
    }

    // Push element onto stack
    void push(int value) {
        Node* newNode = new Node(value);
        newNode->next = topNode;
        topNode = newNode;
        count++;
        cout << "Pushed: " << value << endl;
    }

    // Pop element from stack
    int pop() {
        if (isEmpty()) {
            throw underflow_error("Stack Underflow! Cannot pop from empty stack");
        }

        Node* temp = topNode;
        int value = topNode->data;
        topNode = topNode->next;
        delete temp;
        count--;

        return value;
    }

    // Get top element without removing it
    int peek() const {
        if (isEmpty()) {
            throw underflow_error("Stack is empty! No top element");
        }
        return topNode->data;
    }

    // Check if stack is empty
    bool isEmpty() const {
        return topNode == nullptr;
    }

    // Get current size
    int size() const {
        return count;
    }

    // Display stack contents
    void display() const {
        if (isEmpty()) {
            cout << "Stack is empty" << endl;
            return;
        }

        cout << "Stack (top to bottom): ";
        Node* current = topNode;
        while (current != nullptr) {
            cout << current->data;
            if (current->next != nullptr) cout << " <- ";
            current = current->next;
        }
        cout << endl;
    }

    // Clear the stack
    void clear() {
        while (!isEmpty()) {
            pop();
        }
        cout << "Stack cleared" << endl;
    }
};

int main() {
    cout << "=== Linked List-Based Stack Implementation ===" << endl;

    Stack stack;

    // Push elements
    cout << "\n--- Pushing elements ---" << endl;
    stack.push(10);
    stack.push(20);
    stack.push(30);
    stack.push(40);
    stack.push(50);

    // Display stack
    cout << "\n";
    stack.display();
    cout << "Size: " << stack.size() << endl;
    cout << "Top: " << stack.peek() << endl;

    // Pop elements
    cout << "\n--- Popping elements ---" << endl;
    cout << "Popped: " << stack.pop() << endl;
    cout << "Popped: " << stack.pop() << endl;

    cout << "\n";
    stack.display();

    return 0;
}
```

## Template-Based Generic Stack

```cpp name=stack_template.cpp
#include <iostream>
#include <stdexcept>
#include <string>
using namespace std;

template<typename T>
class Stack {
private:
    struct Node {
        T data;
        Node* next;
        Node(const T& value) : data(value), next(nullptr) {}
    };

    Node* topNode;
    int count;

public:
    Stack() : topNode(nullptr), count(0) {}

    ~Stack() {
        clear();
    }

    void push(const T& value) {
        Node* newNode = new Node(value);
        newNode->next = topNode;
        topNode = newNode;
        count++;
    }

    T pop() {
        if (isEmpty()) {
            throw underflow_error("Stack Underflow!");
        }

        Node* temp = topNode;
        T value = topNode->data;
        topNode = topNode->next;
        delete temp;
        count--;

        return value;
    }

    T peek() const {
        if (isEmpty()) {
            throw underflow_error("Stack is empty!");
        }
        return topNode->data;
    }

    bool isEmpty() const {
        return topNode == nullptr;
    }

    int size() const {
        return count;
    }

    void display() const {
        if (isEmpty()) {
            cout << "Stack is empty" << endl;
            return;
        }

        cout << "Stack: ";
        Node* current = topNode;
        while (current != nullptr) {
            cout << current->data << " ";
            current = current->next;
        }
        cout << endl;
    }

    void clear() {
        while (!isEmpty()) {
            pop();
        }
    }
};

int main() {
    cout << "=== Generic Stack (Template) ===" << endl;

    // Integer stack
    cout << "\n--- Integer Stack ---" << endl;
    Stack<int> intStack;
    intStack.push(10);
    intStack.push(20);
    intStack.push(30);
    intStack.display();

    // String stack
    cout << "\n--- String Stack ---" << endl;
    Stack<string> strStack;
    strStack.push("Hello");
    strStack.push("World");
    strStack.push("C++");
    strStack.display();

    cout << "Popped: " << strStack.pop() << endl;
    strStack.display();

    // Character stack
    cout << "\n--- Character Stack ---" << endl;
    Stack<char> charStack;
    charStack.push('A');
    charStack.push('B');
    charStack.push('C');
    charStack.display();

    return 0;
}
```

## Using STL Stack

C++ provides a built-in stack in the Standard Template Library:

```cpp name=stl_stack.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

int main() {
    cout << "=== STL Stack (std::stack) ===" << endl;

    // Create stack
    stack<int> s;

    // Push elements
    cout << "\n--- Pushing elements ---" << endl;
    s.push(10);
    s.push(20);
    s.push(30);
    s.push(40);

    cout << "Size: " << s.size() << endl;
    cout << "Top: " << s.top() << endl;

    // Pop elements
    cout << "\n--- Popping elements ---" << endl;
    while (!s.empty()) {
        cout << "Popped: " << s.top() << endl;
        s.pop();
    }

    cout << "\nIs empty? " << (s.empty() ? "Yes" : "No") << endl;

    // String stack
    cout << "\n--- String Stack ---" << endl;
    stack<string> strStack;
    strStack.push("First");
    strStack.push("Second");
    strStack.push("Third");

    cout << "Top: " << strStack.top() << endl;
    cout << "Size: " << strStack.size() << endl;

    return 0;
}
```

## Real-World Applications

### 1. Function Call Stack (Recursion)

```cpp name=function_call_stack.cpp
#include <iostream>
using namespace std;

// Recursive function demonstrating call stack
int factorial(int n) {
    cout << "Called factorial(" << n << ")" << endl;

    if (n <= 1) {
        cout << "Base case reached, returning 1" << endl;
        return 1;
    }

    int result = n * factorial(n - 1);
    cout << "Returning " << n << " * factorial(" << (n-1) << ") = " << result << endl;

    return result;
}

int main() {
    cout << "=== Function Call Stack Example ===" << endl;
    cout << "\nCalculating 5!" << endl;
    cout << "\nCall Stack Visualization:" << endl;

    int result = factorial(5);

    cout << "\nFinal result: " << result << endl;

    /*
    Call Stack grows like this:

    factorial(5)
    ├─ factorial(4)
    │  ├─ factorial(3)
    │  │  ├─ factorial(2)
    │  │  │  ├─ factorial(1)  ← Base case
    │  │  │  └─ returns 1
    │  │  └─ returns 2
    │  └─ returns 6
    └─ returns 24
    
    Returns 120
    */

    return 0;
}
```

### 2. Expression Evaluation

```cpp name=expression_evaluation.cpp
#include <iostream>
#include <stack>
#include <string>
#include <cctype>
using namespace std;

// Check if character is operator
bool isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/';
}

// Get precedence of operator
int precedence(char op) {
    if (op == '+' || op == '-') return 1;
    if (op == '*' || op == '/') return 2;
    return 0;
}

// Convert infix to postfix notation
string infixToPostfix(const string& infix) {
    stack<char> s;
    string postfix = "";

    for (char c : infix) {
        // If operand, add to output
        if (isalnum(c)) {
            postfix += c;
        }
        // If '(', push to stack
        else if (c == '(') {
            s.push(c);
        }
        // If ')', pop until '('
        else if (c == ')') {
            while (!s.empty() && s.top() != '(') {
                postfix += s.top();
                s.pop();
            }
            if (!s.empty()) s.pop();  // Remove '('
        }
        // If operator
        else if (isOperator(c)) {
            while (!s.empty() && s.top() != '(' &&
                   precedence(s.top()) >= precedence(c)) {
                postfix += s.top();
                s.pop();
            }
            s.push(c);
        }
    }

    // Pop remaining operators
    while (!s.empty()) {
        postfix += s.top();
        s.pop();
    }

    return postfix;
}

// Evaluate postfix expression
int evaluatePostfix(const string& postfix) {
    stack<int> s;

    for (char c : postfix) {
        // If operand, push to stack
        if (isdigit(c)) {
            s.push(c - '0');
        }
        // If operator, pop two operands and apply operator
        else if (isOperator(c)) {
            int val2 = s.top(); s.pop();
            int val1 = s.top(); s.pop();

            switch (c) {
                case '+': s.push(val1 + val2); break;
                case '-': s.push(val1 - val2); break;
                case '*': s.push(val1 * val2); break;
                case '/': s.push(val1 / val2); break;
            }
        }
    }

    return s.top();
}

int main() {
    cout << "=== Expression Evaluation Using Stack ===" << endl;

    // Test cases
    string expressions[] = {
        "2+3*4",
        "(2+3)*4",
        "2+3+4*5",
        "((2+3)*4)/5"
    };

    for (const string& infix : expressions) {
        cout << "\nInfix:    " << infix << endl;

        string postfix = infixToPostfix(infix);
        cout << "Postfix:  " << postfix << endl;

        // For simple evaluation with single digits
        if (infix.find_first_not_of("0123456789+-*/()") == string::npos) {
            int result = evaluatePostfix(postfix);
            cout << "Result:   " << result << endl;
        }
    }

    return 0;
}
```

### 3. Balanced Parentheses Checker

```cpp name=balanced_parentheses.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

bool isMatchingPair(char open, char close) {
    return (open == '(' && close == ')') ||
           (open == '{' && close == '}') ||
           (open == '[' && close == ']');
}

bool areParenthesesBalanced(const string& expr) {
    stack<char> s;

    for (char c : expr) {
        // If opening bracket, push to stack
        if (c == '(' || c == '{' || c == '[') {
            s.push(c);
        }
        // If closing bracket
        else if (c == ')' || c == '}' || c == ']') {
            // Stack empty or brackets don't match
            if (s.empty() || !isMatchingPair(s.top(), c)) {
                return false;
            }
            s.pop();
        }
    }

    // Stack should be empty if balanced
    return s.empty();
}

int main() {
    cout << "=== Balanced Parentheses Checker ===" << endl;

    string testCases[] = {
        "{[()]}",           // Balanced
        "{[(])}",           // Not balanced
        "((()))",           // Balanced
        "((())",            // Not balanced
        "{[}]",             // Not balanced
        "",                 // Balanced (empty)
        "(){}[]",           // Balanced
        "((()()()))",       // Balanced
        "{[(())]}()",       // Balanced
        "([)]"              // Not balanced
    };

    for (const string& test : testCases) {
        bool balanced = areParenthesesBalanced(test);
        cout << "\nExpression: \"" << test << "\"" << endl;
        cout << "Result: " << (balanced ? "Balanced ✓" : "Not Balanced ✗") << endl;
    }

    return 0;
}
```

### 4. Undo/Redo Functionality

```cpp name=undo_redo.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

class TextEditor {
private:
    string text;
    stack<string> undoStack;
    stack<string> redoStack;

public:
    TextEditor() : text("") {}

    void write(const string& newText) {
        undoStack.push(text);  // Save current state
        text += newText;
        
        // Clear redo stack on new action
        while (!redoStack.empty()) {
            redoStack.pop();
        }

        cout << "Written: \"" << newText << "\"" << endl;
    }

    void undo() {
        if (undoStack.empty()) {
            cout << "Nothing to undo!" << endl;
            return;
        }

        redoStack.push(text);  // Save current state for redo
        text = undoStack.top();
        undoStack.pop();

        cout << "Undo performed" << endl;
    }

    void redo() {
        if (redoStack.empty()) {
            cout << "Nothing to redo!" << endl;
            return;
        }

        undoStack.push(text);  // Save current state
        text = redoStack.top();
        redoStack.pop();

        cout << "Redo performed" << endl;
    }

    void display() const {
        cout << "Current text: \"" << text << "\"" << endl;
    }
};

int main() {
    cout << "=== Text Editor with Undo/Redo ===" << endl;

    TextEditor editor;

    editor.display();

    cout << "\n--- Writing text ---" << endl;
    editor.write("Hello");
    editor.display();

    editor.write(" World");
    editor.display();

    editor.write("!");
    editor.display();

    cout << "\n--- Performing undo ---" << endl;
    editor.undo();
    editor.display();

    editor.undo();
    editor.display();

    cout << "\n--- Performing redo ---" << endl;
    editor.redo();
    editor.display();

    cout << "\n--- Writing new text ---" << endl;
    editor.write(" C++");
    editor.display();

    cout << "\n--- Attempting redo (should fail) ---" << endl;
    editor.redo();

    return 0;
}
```

### 5. Browser History (Back/Forward)

```cpp name=browser_history.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

class Browser {
private:
    stack<string> backStack;
    stack<string> forwardStack;
    string currentPage;

public:
    Browser() : currentPage("Homepage") {
        cout << "Browser started at: " << currentPage << endl;
    }

    void visit(const string& url) {
        if (!currentPage.empty()) {
            backStack.push(currentPage);
        }

        currentPage = url;

        // Clear forward history on new visit
        while (!forwardStack.empty()) {
            forwardStack.pop();
        }

        cout << "Visited: " << url << endl;
    }

    void back() {
        if (backStack.empty()) {
            cout << "No previous page!" << endl;
            return;
        }

        forwardStack.push(currentPage);
        currentPage = backStack.top();
        backStack.pop();

        cout << "Back to: " << currentPage << endl;
    }

    void forward() {
        if (forwardStack.empty()) {
            cout << "No forward page!" << endl;
            return;
        }

        backStack.push(currentPage);
        currentPage = forwardStack.top();
        forwardStack.pop();

        cout << "Forward to: " << currentPage << endl;
    }

    void displayCurrent() const {
        cout << "Current page: " << currentPage << endl;
    }
};

int main() {
    cout << "=== Browser History Simulation ===" << endl;
    
    Browser browser;

    cout << "\n--- Visiting pages ---" << endl;
    browser.visit("google.com");
    browser.visit("github.com");
    browser.visit("stackoverflow.com");

    cout << "\n--- Going back ---" << endl;
    browser.back();
    browser.displayCurrent();

    browser.back();
    browser.displayCurrent();

    cout << "\n--- Going forward ---" << endl;
    browser.forward();
    browser.displayCurrent();

    cout << "\n--- Visiting new page ---" << endl;
    browser.visit("youtube.com");
    browser.displayCurrent();

    cout << "\n--- Trying to go forward (should fail) ---" << endl;
    browser.forward();

    return 0;
}
```

## Time Complexity Analysis

| Operation | Array Implementation | Linked List Implementation |
|-----------|---------------------|---------------------------|
| **push()** | O(1)* | O(1) |
| **pop()** | O(1) | O(1) |
| **peek()** | O(1) | O(1) |
| **isEmpty()** | O(1) | O(1) |
| **size()** | O(1) | O(1) |

*Amortized O(1) for dynamic array (may need resizing)

## Space Complexity

- **Array-based**: O(n) where n is capacity (may waste space)
- **Linked List-based**: O(n) where n is number of elements (no wasted space, but extra space for pointers)

## Array vs Linked List Implementation

### Array-Based Stack

✅ **Advantages:**
- Faster access (cache-friendly)
- Less memory per element (no pointers)
- Simple implementation

❌ **Disadvantages:**
- Fixed size (or resizing overhead)
- Wasted space if not full
- Stack overflow possible

### Linked List-Based Stack

✅ **Advantages:**
- Dynamic size (no overflow unless out of memory)
- No wasted space
- Easy to implement

❌ **Disadvantages:**
- Extra memory for pointers
- Slower (pointer dereferencing)
- Cache-unfriendly

## Common Stack Problems

### 1. Reverse a String

```cpp name=reverse_string.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

string reverseString(const string& str) {
    stack<char> s;

    // Push all characters
    for (char c : str) {
        s.push(c);
    }

    // Pop all characters
    string reversed = "";
    while (!s.empty()) {
        reversed += s.top();
        s.pop();
    }

    return reversed;
}

int main() {
    string text = "Hello World!";
    cout << "Original: " << text << endl;
    cout << "Reversed: " << reverseString(text) << endl;

    return 0;
}
```

### 2. Next Greater Element

```cpp name=next_greater_element.cpp
#include <iostream>
#include <stack>
#include <vector>
using namespace std;

vector<int> nextGreaterElement(const vector<int>& arr) {
    int n = arr.size();
    vector<int> result(n, -1);
    stack<int> s;  // Store indices

    for (int i = 0; i < n; i++) {
        // Pop elements smaller than current
        while (!s.empty() && arr[s.top()] < arr[i]) {
            result[s.top()] = arr[i];
            s.pop();
        }
        s.push(i);
    }

    return result;
}

int main() {
    vector<int> arr = {4, 5, 2, 25, 7, 8};

    cout << "Array: ";
    for (int x : arr) cout << x << " ";
    cout << endl;

    vector<int> result = nextGreaterElement(arr);

    cout << "Next Greater Element: ";
    for (int x : result) {
        if (x == -1) cout << "- ";
        else cout << x << " ";
    }
    cout << endl;

    /*
    Explanation:
    4 → 5 (next greater)
    5 → 25
    2 → 25
    25 → -1 (no greater element)
    7 → 8
    8 → -1
    */

    return 0;
}
```

## Key Takeaways (For Interviews)

1. **Stack = LIFO**: Last In, First Out principle
2. **O(1) Operations**: Push, pop, and peek are constant time
3. **Restricted Access**: Can only access top element
4. **Two Implementations**: Array (fixed/dynamic) or Linked List
5. **Common Uses**: 
   - Function calls (recursion)
   - Expression evaluation
   - Undo/Redo functionality
   - Backtracking algorithms
   - Browser history
   - Balanced parentheses

6. **When to Use Stack:**
   - Need LIFO behavior
   - Reversing data
   - Depth-first traversal
   - Managing nested structures
   - Keeping track of previous states

## Queues

# Queue Data Structure

A **Queue** is a linear data structure that follows the **FIFO (First In, First Out)** principle. Think of it like a line of people waiting: the first person to join the line is the first person to be served. It's the opposite of a stack!

## Key Characteristics

- **FIFO**: First In, First Out
- **Two Ends**: Front (for removal) and Rear/Back (for insertion)
- **Time Complexity**: O(1) for enqueue and dequeue operations
- **Use Cases**: Task scheduling, BFS, buffering, request handling

## Basic Operations

1. **Enqueue**: Add an element to the rear
2. **Dequeue**: Remove and return the front element
3. **Front/Peek**: View the front element without removing it
4. **isEmpty**: Check if queue is empty
5. **Size**: Get number of elements

## Visual Representation

```
Enqueue operations:          Dequeue operations:

Front → [  ] ← Rear         Front → [  ] ← Rear
        
Enqueue(5)
Front → [5] ← Rear          Front → [  ] ← Rear
        
Enqueue(7)                  Dequeue() returns 5
Front → [5, 7] ← Rear       Front → [7] ← Rear
        
Enqueue(3)                  Dequeue() returns 7
Front → [5, 7, 3] ← Rear    Front → [3] ← Rear

        ↑           ↑
    Remove here  Add here
```

## Types of Queues

1. **Simple Queue**: Basic FIFO queue
2. **Circular Queue**: Last position connects to first (efficient use of space)
3. **Priority Queue**: Elements have priorities
4. **Double-Ended Queue (Deque)**: Insert/delete from both ends

## Simple Queue - Array Implementation

```cpp name=queue_array.cpp
#include <iostream>
#include <stdexcept>
using namespace std;

class Queue {
private:
    int* arr;
    int capacity;
    int frontIndex;
    int rearIndex;
    int count;

public:
    // Constructor
    Queue(int size = 100) {
        arr = new int[size];
        capacity = size;
        frontIndex = 0;
        rearIndex = -1;
        count = 0;
    }

    // Destructor
    ~Queue() {
        delete[] arr;
    }

    // Add element to rear
    void enqueue(int value) {
        if (isFull()) {
            throw overflow_error("Queue Overflow! Cannot enqueue " + to_string(value));
        }

        rearIndex = (rearIndex + 1) % capacity;  // Circular increment
        arr[rearIndex] = value;
        count++;

        cout << "Enqueued: " << value << endl;
    }

    // Remove element from front
    int dequeue() {
        if (isEmpty()) {
            throw underflow_error("Queue Underflow! Cannot dequeue from empty queue");
        }

        int value = arr[frontIndex];
        frontIndex = (frontIndex + 1) % capacity;  // Circular increment
        count--;

        return value;
    }

    // Get front element without removing
    int front() const {
        if (isEmpty()) {
            throw underflow_error("Queue is empty! No front element");
        }
        return arr[frontIndex];
    }

    // Get rear element
    int rear() const {
        if (isEmpty()) {
            throw underflow_error("Queue is empty! No rear element");
        }
        return arr[rearIndex];
    }

    // Check if queue is empty
    bool isEmpty() const {
        return count == 0;
    }

    // Check if queue is full
    bool isFull() const {
        return count == capacity;
    }

    // Get current size
    int size() const {
        return count;
    }

    // Display queue contents
    void display() const {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }

        cout << "Queue (front to rear): ";
        int i = frontIndex;
        for (int j = 0; j < count; j++) {
            cout << arr[i];
            if (j < count - 1) cout << " <- ";
            i = (i + 1) % capacity;
        }
        cout << endl;
    }

    // Clear the queue
    void clear() {
        frontIndex = 0;
        rearIndex = -1;
        count = 0;
        cout << "Queue cleared" << endl;
    }
};

int main() {
    cout << "=== Array-Based Queue Implementation ===" << endl;

    Queue queue(5);

    // Test isEmpty
    cout << "\nIs empty? " << (queue.isEmpty() ? "Yes" : "No") << endl;

    // Enqueue elements
    cout << "\n--- Enqueueing elements ---" << endl;
    queue.enqueue(10);
    queue.enqueue(20);
    queue.enqueue(30);
    queue.enqueue(40);

    // Display queue
    cout << "\n";
    queue.display();
    cout << "Size: " << queue.size() << endl;
    cout << "Front: " << queue.front() << endl;
    cout << "Rear: " << queue.rear() << endl;

    // Dequeue elements
    cout << "\n--- Dequeueing elements ---" << endl;
    cout << "Dequeued: " << queue.dequeue() << endl;
    cout << "Dequeued: " << queue.dequeue() << endl;

    cout << "\n";
    queue.display();
    cout << "Front: " << queue.front() << endl;

    // Enqueue more elements (demonstrating circular behavior)
    cout << "\n--- Enqueueing more elements ---" << endl;
    queue.enqueue(50);
    queue.enqueue(60);
    queue.enqueue(70);

    cout << "\n";
    queue.display();

    // Test overflow
    cout << "\n--- Testing overflow ---" << endl;
    try {
        queue.enqueue(80);
    } catch (const overflow_error& e) {
        cout << "Exception: " << e.what() << endl;
    }

    // Dequeue all
    cout << "\n--- Dequeueing all elements ---" << endl;
    while (!queue.isEmpty()) {
        cout << "Dequeued: " << queue.dequeue() << endl;
    }

    // Test underflow
    cout << "\n--- Testing underflow ---" << endl;
    try {
        queue.dequeue();
    } catch (const underflow_error& e) {
        cout << "Exception: " << e.what() << endl;
    }

    return 0;
}
```

## Circular Queue Implementation

```cpp name=circular_queue.cpp
#include <iostream>
#include <stdexcept>
using namespace std;

class CircularQueue {
private:
    int* arr;
    int capacity;
    int front;
    int rear;
    int count;

public:
    CircularQueue(int size) {
        arr = new int[size];
        capacity = size;
        front = 0;
        rear = -1;
        count = 0;
    }

    ~CircularQueue() {
        delete[] arr;
    }

    void enqueue(int value) {
        if (isFull()) {
            throw overflow_error("Queue is full!");
        }

        rear = (rear + 1) % capacity;
        arr[rear] = value;
        count++;

        cout << "Enqueued: " << value << " at position " << rear << endl;
    }

    int dequeue() {
        if (isEmpty()) {
            throw underflow_error("Queue is empty!");
        }

        int value = arr[front];
        cout << "Dequeued: " << value << " from position " << front << endl;
        front = (front + 1) % capacity;
        count--;

        return value;
    }

    int getFront() const {
        if (isEmpty()) throw underflow_error("Queue is empty!");
        return arr[front];
    }

    int getRear() const {
        if (isEmpty()) throw underflow_error("Queue is empty!");
        return arr[rear];
    }

    bool isEmpty() const {
        return count == 0;
    }

    bool isFull() const {
        return count == capacity;
    }

    int size() const {
        return count;
    }

    void display() const {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }

        cout << "Queue: [";
        int i = front;
        for (int j = 0; j < count; j++) {
            cout << arr[i];
            if (j < count - 1) cout << ", ";
            i = (i + 1) % capacity;
        }
        cout << "]" << endl;
        cout << "Front index: " << front << ", Rear index: " << rear << endl;
    }
};

int main() {
    cout << "=== Circular Queue Implementation ===" << endl;

    CircularQueue cq(5);

    cout << "\n--- Enqueueing elements ---" << endl;
    cq.enqueue(10);
    cq.enqueue(20);
    cq.enqueue(30);
    cq.display();

    cout << "\n--- Dequeueing 2 elements ---" << endl;
    cq.dequeue();
    cq.dequeue();
    cq.display();

    cout << "\n--- Enqueueing more (circular wrap) ---" << endl;
    cq.enqueue(40);
    cq.enqueue(50);
    cq.enqueue(60);
    cq.display();

    cout << "\nThis demonstrates circular behavior - elements wrap around!" << endl;

    return 0;
}
```

## Linked List-Based Queue

```cpp name=queue_linked_list.cpp
#include <iostream>
#include <stdexcept>
using namespace std;

struct Node {
    int data;
    Node* next;

    Node(int value) : data(value), next(nullptr) {}
};

class Queue {
private:
    Node* frontNode;
    Node* rearNode;
    int count;

public:
    // Constructor
    Queue() : frontNode(nullptr), rearNode(nullptr), count(0) {}

    // Destructor
    ~Queue() {
        clear();
    }

    // Add element to rear
    void enqueue(int value) {
        Node* newNode = new Node(value);

        // If queue is empty
        if (rearNode == nullptr) {
            frontNode = rearNode = newNode;
        } else {
            rearNode->next = newNode;
            rearNode = newNode;
        }

        count++;
        cout << "Enqueued: " << value << endl;
    }

    // Remove element from front
    int dequeue() {
        if (isEmpty()) {
            throw underflow_error("Queue Underflow! Cannot dequeue from empty queue");
        }

        Node* temp = frontNode;
        int value = temp->data;

        frontNode = frontNode->next;

        // If queue becomes empty
        if (frontNode == nullptr) {
            rearNode = nullptr;
        }

        delete temp;
        count--;

        return value;
    }

    // Get front element
    int front() const {
        if (isEmpty()) {
            throw underflow_error("Queue is empty!");
        }
        return frontNode->data;
    }

    // Get rear element
    int rear() const {
        if (isEmpty()) {
            throw underflow_error("Queue is empty!");
        }
        return rearNode->data;
    }

    // Check if empty
    bool isEmpty() const {
        return frontNode == nullptr;
    }

    // Get size
    int size() const {
        return count;
    }

    // Display queue
    void display() const {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }

        cout << "Queue (front to rear): ";
        Node* current = frontNode;
        while (current != nullptr) {
            cout << current->data;
            if (current->next != nullptr) cout << " <- ";
            current = current->next;
        }
        cout << endl;
    }

    // Clear queue
    void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }
};

int main() {
    cout << "=== Linked List-Based Queue Implementation ===" << endl;

    Queue queue;

    cout << "\n--- Enqueueing elements ---" << endl;
    queue.enqueue(10);
    queue.enqueue(20);
    queue.enqueue(30);
    queue.enqueue(40);
    queue.enqueue(50);

    cout << "\n";
    queue.display();
    cout << "Size: " << queue.size() << endl;
    cout << "Front: " << queue.front() << endl;
    cout << "Rear: " << queue.rear() << endl;

    cout << "\n--- Dequeueing elements ---" << endl;
    cout << "Dequeued: " << queue.dequeue() << endl;
    cout << "Dequeued: " << queue.dequeue() << endl;

    cout << "\n";
    queue.display();

    cout << "\n--- Enqueueing more ---" << endl;
    queue.enqueue(60);
    queue.enqueue(70);

    cout << "\n";
    queue.display();

    return 0;
}
```

## Template-Based Generic Queue

```cpp name=queue_template.cpp
#include <iostream>
#include <stdexcept>
#include <string>
using namespace std;

template<typename T>
class Queue {
private:
    struct Node {
        T data;
        Node* next;
        Node(const T& value) : data(value), next(nullptr) {}
    };

    Node* frontNode;
    Node* rearNode;
    int count;

public:
    Queue() : frontNode(nullptr), rearNode(nullptr), count(0) {}

    ~Queue() {
        clear();
    }

    void enqueue(const T& value) {
        Node* newNode = new Node(value);

        if (isEmpty()) {
            frontNode = rearNode = newNode;
        } else {
            rearNode->next = newNode;
            rearNode = newNode;
        }

        count++;
    }

    T dequeue() {
        if (isEmpty()) {
            throw underflow_error("Queue is empty!");
        }

        Node* temp = frontNode;
        T value = temp->data;

        frontNode = frontNode->next;
        if (frontNode == nullptr) {
            rearNode = nullptr;
        }

        delete temp;
        count--;

        return value;
    }

    T front() const {
        if (isEmpty()) throw underflow_error("Queue is empty!");
        return frontNode->data;
    }

    T rear() const {
        if (isEmpty()) throw underflow_error("Queue is empty!");
        return rearNode->data;
    }

    bool isEmpty() const {
        return frontNode == nullptr;
    }

    int size() const {
        return count;
    }

    void display() const {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }

        cout << "Queue: ";
        Node* current = frontNode;
        while (current != nullptr) {
            cout << current->data << " ";
            current = current->next;
        }
        cout << endl;
    }

    void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }
};

int main() {
    cout << "=== Generic Queue (Template) ===" << endl;

    // Integer queue
    cout << "\n--- Integer Queue ---" << endl;
    Queue<int> intQueue;
    intQueue.enqueue(10);
    intQueue.enqueue(20);
    intQueue.enqueue(30);
    intQueue.display();
    cout << "Front: " << intQueue.front() << endl;

    // String queue
    cout << "\n--- String Queue ---" << endl;
    Queue<string> strQueue;
    strQueue.enqueue("First");
    strQueue.enqueue("Second");
    strQueue.enqueue("Third");
    strQueue.display();
    cout << "Dequeued: " << strQueue.dequeue() << endl;
    strQueue.display();

    // Character queue
    cout << "\n--- Character Queue ---" << endl;
    Queue<char> charQueue;
    charQueue.enqueue('A');
    charQueue.enqueue('B');
    charQueue.enqueue('C');
    charQueue.display();

    return 0;
}
```

## Using STL Queue

```cpp name=stl_queue.cpp
#include <iostream>
#include <queue>
#include <string>
using namespace std;

int main() {
    cout << "=== STL Queue (std::queue) ===" << endl;

    // Integer queue
    queue<int> q;

    cout << "\n--- Enqueueing elements ---" << endl;
    q.push(10);
    q.push(20);
    q.push(30);
    q.push(40);

    cout << "Size: " << q.size() << endl;
    cout << "Front: " << q.front() << endl;
    cout << "Back: " << q.back() << endl;

    cout << "\n--- Dequeueing elements ---" << endl;
    while (!q.empty()) {
        cout << "Front: " << q.front() << endl;
        q.pop();
    }

    cout << "\nIs empty? " << (q.empty() ? "Yes" : "No") << endl;

    // String queue
    cout << "\n--- String Queue ---" << endl;
    queue<string> strQueue;
    strQueue.push("First");
    strQueue.push("Second");
    strQueue.push("Third");

    cout << "Front: " << strQueue.front() << endl;
    cout << "Back: " << strQueue.back() << endl;
    cout << "Size: " << strQueue.size() << endl;

    return 0;
}
```

## Double-Ended Queue (Deque)

```cpp name=deque_implementation.cpp
#include <iostream>
#include <deque>
using namespace std;

int main() {
    cout << "=== Double-Ended Queue (Deque) ===" << endl;

    deque<int> dq;

    // Insert at both ends
    cout << "\n--- Inserting at both ends ---" << endl;
    dq.push_back(10);    // Insert at rear
    dq.push_front(5);    // Insert at front
    dq.push_back(20);    // Insert at rear
    dq.push_front(1);    // Insert at front

    cout << "Deque: ";
    for (int x : dq) {
        cout << x << " ";
    }
    cout << endl;

    cout << "Front: " << dq.front() << endl;
    cout << "Back: " << dq.back() << endl;

    // Remove from both ends
    cout << "\n--- Removing from both ends ---" << endl;
    dq.pop_front();  // Remove from front
    dq.pop_back();   // Remove from rear

    cout << "After removal: ";
    for (int x : dq) {
        cout << x << " ";
    }
    cout << endl;

    // Access by index
    cout << "\n--- Random access ---" << endl;
    cout << "Element at index 0: " << dq[0] << endl;
    cout << "Element at index 1: " << dq[1] << endl;

    return 0;
}
```

## Priority Queue

```cpp name=priority_queue.cpp
#include <iostream>
#include <queue>
#include <vector>
using namespace std;

int main() {
    cout << "=== Priority Queue ===" << endl;

    // Max heap (default - largest element has highest priority)
    cout << "\n--- Max Priority Queue ---" << endl;
    priority_queue<int> maxPQ;

    maxPQ.push(30);
    maxPQ.push(10);
    maxPQ.push(50);
    maxPQ.push(20);

    cout << "Elements (in priority order): ";
    while (!maxPQ.empty()) {
        cout << maxPQ.top() << " ";
        maxPQ.pop();
    }
    cout << endl;

    // Min heap (smallest element has highest priority)
    cout << "\n--- Min Priority Queue ---" << endl;
    priority_queue<int, vector<int>, greater<int>> minPQ;

    minPQ.push(30);
    minPQ.push(10);
    minPQ.push(50);
    minPQ.push(20);

    cout << "Elements (in priority order): ";
    while (!minPQ.empty()) {
        cout << minPQ.top() << " ";
        minPQ.pop();
    }
    cout << endl;

    // Custom priority (for structs/classes)
    cout << "\n--- Custom Priority Queue ---" << endl;

    struct Task {
        string name;
        int priority;

        Task(string n, int p) : name(n), priority(p) {}

        // Define comparison (higher priority value = higher priority)
        bool operator<(const Task& other) const {
            return priority < other.priority;  // Max heap
        }
    };

    priority_queue<Task> taskQueue;
    taskQueue.push(Task("Low priority task", 1));
    taskQueue.push(Task("High priority task", 5));
    taskQueue.push(Task("Medium priority task", 3));

    cout << "Tasks (by priority):" << endl;
    while (!taskQueue.empty()) {
        Task t = taskQueue.top();
        cout << "  " << t.name << " (priority: " << t.priority << ")" << endl;
        taskQueue.pop();
    }

    return 0;
}
```

## Real-World Applications

### 1. CPU Task Scheduling

```cpp name=cpu_scheduling.cpp
#include <iostream>
#include <queue>
#include <string>
using namespace std;

struct Process {
    int id;
    string name;
    int burstTime;

    Process(int i, string n, int bt) : id(i), name(n), burstTime(bt) {}
};

void roundRobinScheduling(queue<Process>& processQueue, int timeQuantum) {
    cout << "=== Round Robin CPU Scheduling ===" << endl;
    cout << "Time Quantum: " << timeQuantum << " units\n" << endl;

    int currentTime = 0;

    while (!processQueue.empty()) {
        Process p = processQueue.front();
        processQueue.pop();

        cout << "Time " << currentTime << ": Running " << p.name 
             << " (PID: " << p.id << ")" << endl;

        if (p.burstTime > timeQuantum) {
            // Process not finished, use time quantum
            currentTime += timeQuantum;
            p.burstTime -= timeQuantum;

            cout << "  -> Not finished. Remaining time: " << p.burstTime 
                 << ". Re-queuing..." << endl;

            // Re-add to queue
            processQueue.push(p);
        } else {
            // Process finished
            currentTime += p.burstTime;
            cout << "  -> Completed!" << endl;
        }

        cout << endl;
    }

    cout << "All processes completed at time " << currentTime << endl;
}

int main() {
    queue<Process> processQueue;

    processQueue.push(Process(1, "Chrome", 10));
    processQueue.push(Process(2, "VSCode", 5));
    processQueue.push(Process(3, "Spotify", 8));
    processQueue.push(Process(4, "Terminal", 3));

    roundRobinScheduling(processQueue, 3);

    return 0;
}
```

### 2. Breadth-First Search (BFS)

```cpp name=bfs_queue.cpp
#include <iostream>
#include <queue>
#include <vector>
#include <unordered_set>
using namespace std;

class Graph {
private:
    int vertices;
    vector<vector<int>> adjList;

public:
    Graph(int v) : vertices(v), adjList(v) {}

    void addEdge(int u, int v) {
        adjList[u].push_back(v);
        adjList[v].push_back(u);  // Undirected graph
    }

    void BFS(int start) {
        cout << "BFS traversal starting from vertex " << start << ":" << endl;

        vector<bool> visited(vertices, false);
        queue<int> q;

        visited[start] = true;
        q.push(start);

        while (!q.empty()) {
            int current = q.front();
            q.pop();

            cout << current << " ";

            // Enqueue all unvisited neighbors
            for (int neighbor : adjList[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.push(neighbor);
                }
            }
        }

        cout << endl;
    }

    void displayGraph() {
        cout << "\nGraph adjacency list:" << endl;
        for (int i = 0; i < vertices; i++) {
            cout << i << ": ";
            for (int neighbor : adjList[i]) {
                cout << neighbor << " ";
            }
            cout << endl;
        }
    }
};

int main() {
    cout << "=== BFS using Queue ===" << endl;

    Graph g(6);

    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 4);
    g.addEdge(3, 5);
    g.addEdge(4, 5);

    g.displayGraph();

    cout << endl;
    g.BFS(0);

    /*
    Graph visualization:
        0
       / \
      1   2
     /|   |
    3 4---+
     \|
      5
    */

    return 0;
}
```

### 3. Print Queue Management

```cpp name=print_queue.cpp
#include <iostream>
#include <queue>
#include <string>
#include <thread>
#include <chrono>
using namespace std;

struct PrintJob {
    int id;
    string document;
    int pages;

    PrintJob(int i, string doc, int p) : id(i), document(doc), pages(p) {}
};

class Printer {
private:
    queue<PrintJob> jobQueue;
    int jobIdCounter;

public:
    Printer() : jobIdCounter(1) {}

    void addJob(const string& document, int pages) {
        PrintJob job(jobIdCounter++, document, pages);
        jobQueue.push(job);
        cout << "Added to queue: Job #" << job.id << " - \"" << job.document 
             << "\" (" << job.pages << " pages)" << endl;
    }

    void processQueue() {
        if (jobQueue.empty()) {
            cout << "\nNo jobs in queue!" << endl;
            return;
        }

        cout << "\n=== Processing Print Queue ===" << endl;
        cout << "Jobs in queue: " << jobQueue.size() << "\n" << endl;

        while (!jobQueue.empty()) {
            PrintJob job = jobQueue.front();
            jobQueue.pop();

            cout << "Printing Job #" << job.id << ": \"" << job.document << "\"" << endl;

            // Simulate printing time (1 second per page)
            for (int i = 1; i <= job.pages; i++) {
                cout << "  Page " << i << "/" << job.pages << "..." << endl;
                this_thread::sleep_for(chrono::milliseconds(500));
            }

            cout << "  ✓ Job #" << job.id << " completed!\n" << endl;
        }

        cout << "All jobs processed!" << endl;
    }

    int queueSize() const {
        return jobQueue.size();
    }
};

int main() {
    cout << "=== Print Queue Management System ===" << endl;

    Printer printer;

    // Add print jobs
    printer.addJob("Report.pdf", 3);
    printer.addJob("Presentation.pptx", 2);
    printer.addJob("Invoice.docx", 1);
    printer.addJob("Manual.pdf", 4);

    // Process all jobs
    printer.processQueue();

    return 0;
}
```

### 4. Customer Service Queue

```cpp name=customer_service_queue.cpp
#include <iostream>
#include <queue>
#include <string>
using namespace std;

struct Customer {
    int ticketNumber;
    string name;
    string issue;

    Customer(int ticket, string n, string i) 
        : ticketNumber(ticket), name(n), issue(i) {}
};

class ServiceCenter {
private:
    queue<Customer> waitingQueue;
    int nextTicket;

public:
    ServiceCenter() : nextTicket(1) {}

    void addCustomer(const string& name, const string& issue) {
        Customer customer(nextTicket++, name, issue);
        waitingQueue.push(customer);

        cout << "Customer added: " << customer.name 
             << " (Ticket #" << customer.ticketNumber << ")" << endl;
        cout << "Issue: " << customer.issue << endl;
        cout << "Position in queue: " << waitingQueue.size() << "\n" << endl;
    }

    void serveNext() {
        if (waitingQueue.empty()) {
            cout << "No customers waiting!" << endl;
            return;
        }

        Customer customer = waitingQueue.front();
        waitingQueue.pop();

        cout << "\n=== Now Serving ===" << endl;
        cout << "Ticket #" << customer.ticketNumber << endl;
        cout << "Customer: " << customer.name << endl;
        cout << "Issue: " << customer.issue << endl;
        cout << "Customers remaining: " << waitingQueue.size() << "\n" << endl;
    }

    void displayQueue() const {
        if (waitingQueue.empty()) {
            cout << "Queue is empty!" << endl;
            return;
        }

        cout << "\n--- Current Queue ---" << endl;
        queue<Customer> temp = waitingQueue;
        int position = 1;

        while (!temp.empty()) {
            Customer c = temp.front();
            temp.pop();

            cout << position++ << ". Ticket #" << c.ticketNumber 
                 << " - " << c.name << " (" << c.issue << ")" << endl;
        }
        cout << endl;
    }

    int queueLength() const {
        return waitingQueue.size();
    }
};

int main() {
    cout << "=== Customer Service Queue System ===" << endl;

    ServiceCenter center;

    // Customers arrive
    center.addCustomer("Alice", "Account inquiry");
    center.addCustomer("Bob", "Technical support");
    center.addCustomer("Charlie", "Billing question");
    center.addCustomer("Diana", "Product return");

    // Display queue
    center.displayQueue();

    // Serve customers
    center.serveNext();
    center.serveNext();

    // More customers arrive
    center.addCustomer("Eve", "New account");

    center.displayQueue();

    // Serve remaining customers
    center.serveNext();
    center.serveNext();
    center.serveNext();

    return 0;
}
```

### 5. Cache Implementation (LRU Cache)

```cpp name=lru_cache_queue.cpp
#include <iostream>
#include <unordered_map>
#include <list>
using namespace std;

class LRUCache {
private:
    int capacity;
    list<pair<int, int>> cacheList;  // Stores (key, value) pairs
    unordered_map<int, list<pair<int, int>>::iterator> cacheMap;  // Maps key to iterator

public:
    LRUCache(int cap) : capacity(cap) {}

    int get(int key) {
        if (cacheMap.find(key) == cacheMap.end()) {
            cout << "Cache MISS for key " << key << endl;
            return -1;  // Key not found
        }

        // Move accessed item to front (most recently used)
        auto it = cacheMap[key];
        int value = it->second;
        cacheList.erase(it);
        cacheList.push_front({key, value});
        cacheMap[key] = cacheList.begin();

        cout << "Cache HIT for key " << key << " -> " << value << endl;
        return value;
    }

    void put(int key, int value) {
        // If key exists, update it
        if (cacheMap.find(key) != cacheMap.end()) {
            auto it = cacheMap[key];
            cacheList.erase(it);
        } else if (cacheList.size() >= capacity) {
            // Cache is full, remove least recently used
            auto lru = cacheList.back();
            cacheMap.erase(lru.first);
            cacheList.pop_back();
            cout << "Evicted key " << lru.first << " (LRU)" << endl;
        }

        // Add new item to front
        cacheList.push_front({key, value});
        cacheMap[key] = cacheList.begin();

        cout << "Put key " << key << " -> " << value << endl;
    }

    void display() const {
        cout << "\nCache contents (MRU to LRU): ";
        for (auto& p : cacheList) {
            cout << "[" << p.first << ":" << p.second << "] ";
        }
        cout << endl;
    }
};

int main() {
    cout << "=== LRU Cache Implementation ===" << endl;

    LRUCache cache(3);  // Capacity of 3

    cout << "\n--- Adding items ---" << endl;
    cache.put(1, 10);
    cache.put(2, 20);
    cache.put(3, 30);
    cache.display();

    cout << "\n--- Accessing item ---" << endl;
    cache.get(1);
    cache.display();

    cout << "\n--- Adding new item (causes eviction) ---" << endl;
    cache.put(4, 40);
    cache.display();

    cout << "\n--- Accessing items ---" << endl;
    cache.get(2);  // Cache miss
    cache.get(3);  // Cache hit
    cache.display();

    return 0;
}
```

## Time Complexity Analysis

| Operation | Array Implementation | Linked List Implementation | Circular Queue |
|-----------|---------------------|---------------------------|----------------|
| **enqueue()** | O(1) | O(1) | O(1) |
| **dequeue()** | O(1)* | O(1) | O(1) |
| **front()** | O(1) | O(1) | O(1) |
| **isEmpty()** | O(1) | O(1) | O(1) |
| **size()** | O(1) | O(1) | O(1) |

*With circular array; O(n) with simple array due to shifting

## Space Complexity

- **Array-based**: O(n) where n is capacity (may waste space)
- **Linked List-based**: O(n) where n is number of elements
- **Circular Queue**: O(n) where n is capacity (efficient space usage)

## Array vs Linked List Implementation

### Array-Based Queue

✅ **Advantages:**
- Cache-friendly (contiguous memory)
- Fast access to elements
- Simple implementation

❌ **Disadvantages:**
- Fixed size (or resizing overhead)
- Wasted space in simple implementation
- Front pointer movement wastes space

### Linked List-Based Queue

✅ **Advantages:**
- Dynamic size
- No wasted space
- Easy enqueue/dequeue

❌ **Disadvantages:**
- Extra memory for pointers
- Not cache-friendly
- Slower due to pointer dereferencing

### Circular Queue

✅ **Advantages:**
- Efficient space usage
- No shifting required
- Fixed size with no waste

❌ **Disadvantages:**
- Slightly more complex logic
- Fixed capacity

## Common Queue Problems

### 1. Reverse a Queue

```cpp name=reverse_queue.cpp
#include <iostream>
#include <queue>
#include <stack>
using namespace std;

void reverseQueue(queue<int>& q) {
    stack<int> s;

    // Push all queue elements to stack
    while (!q.empty()) {
        s.push(q.front());
        q.pop();
    }

    // Pop from stack and enqueue
    while (!s.empty()) {
        q.push(s.top());
        s.pop();
    }
}

void displayQueue(queue<int> q) {
    cout << "Queue: ";
    while (!q.empty()) {
        cout << q.front() << " ";
        q.pop();
    }
    cout << endl;
}

int main() {
    queue<int> q;
    q.push(10);
    q.push(20);
    q.push(30);
    q.push(40);

    cout << "Original ";
    displayQueue(q);

    reverseQueue(q);

    cout << "Reversed ";
    displayQueue(q);

    return 0;
}
```

### 2. Generate Binary Numbers

```cpp name=generate_binary.cpp
#include <iostream>
#include <queue>
#include <string>
using namespace std;

void generateBinaryNumbers(int n) {
    cout << "First " << n << " binary numbers:" << endl;

    queue<string> q;
    q.push("1");

    for (int i = 0; i < n; i++) {
        string current = q.front();
        q.pop();

        cout << current << endl;

        // Generate next binary numbers
        q.push(current + "0");
        q.push(current + "1");
    }
}

int main() {
    generateBinaryNumbers(10);

    /*
    Algorithm:
    Start with "1"
    For each number, generate two new: append "0" and append "1"
    
    1
    1 + 0 = 10, 1 + 1 = 11
    10 + 0 = 100, 10 + 1 = 101
    11 + 0 = 110, 11 + 1 = 111
    ...
    */

    return 0;
}
```

## Stack vs Queue Comparison

| Feature | Stack | Queue |
|---------|-------|-------|
| **Principle** | LIFO (Last In, First Out) | FIFO (First In, First Out) |
| **Operations** | push(), pop(), top() | enqueue(), dequeue(), front() |
| **Access** | Top only | Front and rear |
| **Use Cases** | Function calls, undo/redo, backtracking | Scheduling, BFS, buffering |
| **Real-world** | Stack of plates | Line of people |

## Key Takeaways

1. **Queue = FIFO**: First In, First Out principle
2. **O(1) Operations**: Enqueue and dequeue are constant time
3. **Two Ends**: Front (removal) and Rear (insertion)
4. **Multiple Types**: Simple, Circular, Priority, Deque
5. **Implementations**: Array (circular) or Linked List
6. **Common Uses**:
   - Task scheduling
   - Breadth-first search
   - Buffering (IO, network)
   - Request handling
   - Resource sharing

7. **When to Use Queue:**
   - Need FIFO behavior
   - Order matters
   - Scheduling tasks
   - Level-order traversal
   - Managing sequential processing


## Binary Search Tree (BST)

A **Binary Search Tree** is a hierarchical data structure where each node has at most two children (left and right), and it maintains a specific ordering property: for every node, all values in its left subtree are smaller, and all values in its right subtree are larger.

## Key Characteristics

- **Ordered Structure**: Left < Parent < Right
- **Time Complexity**: 
  - **Average**: O(log n) for search, insert, delete
  - **Worst**: O(n) when tree becomes skewed
- **No Duplicates**: Typically doesn't allow duplicate values
- **Use Cases**: Searching, sorting, implementing sets/maps

## BST Property

```
For every node N:
- All nodes in left subtree < N
- All nodes in right subtree > N
- Both left and right subtrees are also BSTs
```

## Visual Representation

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

## Basic Node Structure

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

```cpp name=bst_complete.cpp
#include <iostream>
#include <queue>
#include <algorithm>
using namespace std;

class BST {
private:
    struct Node {
        int data;
        Node* left;
        Node* right;

        Node(int value) : data(value), left(nullptr), right(nullptr) {}
    };

    Node* root;

    // Helper: Insert recursively
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

    // Helper: Search recursively
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

    // Helper: Find minimum value node
    Node* findMin(Node* node) const {
        while (node->left != nullptr) {
            node = node->left;
        }
        return node;
    }

    // Helper: Delete recursively
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

    // Helper: Inorder traversal (Left, Root, Right)
    void inorderHelper(Node* node) const {
        if (node != nullptr) {
            inorderHelper(node->left);
            cout << node->data << " ";
            inorderHelper(node->right);
        }
    }

    // Helper: Preorder traversal (Root, Left, Right)
    void preorderHelper(Node* node) const {
        if (node != nullptr) {
            cout << node->data << " ";
            preorderHelper(node->left);
            preorderHelper(node->right);
        }
    }

    // Helper: Postorder traversal (Left, Right, Root)
    void postorderHelper(Node* node) const {
        if (node != nullptr) {
            postorderHelper(node->left);
            postorderHelper(node->right);
            cout << node->data << " ";
        }
    }

    // Helper: Get height
    int heightHelper(Node* node) const {
        if (node == nullptr) {
            return -1;  // Height of empty tree is -1
        }
        return 1 + max(heightHelper(node->left), heightHelper(node->right));
    }

    // Helper: Count nodes
    int countNodesHelper(Node* node) const {
        if (node == nullptr) {
            return 0;
        }
        return 1 + countNodesHelper(node->left) + countNodesHelper(node->right);
    }

    // Helper: Clear tree
    void clearHelper(Node* node) {
        if (node != nullptr) {
            clearHelper(node->left);
            clearHelper(node->right);
            delete node;
        }
    }

public:
    // Constructor
    BST() : root(nullptr) {}

    // Destructor
    ~BST() {
        clear();
    }

    // Insert value
    void insert(int value) {
        root = insertHelper(root, value);
        cout << "Inserted: " << value << endl;
    }

    // Search for value
    bool search(int value) const {
        return searchHelper(root, value);
    }

    // Delete value
    void remove(int value) {
        if (search(value)) {
            root = deleteHelper(root, value);
            cout << "Deleted: " << value << endl;
        } else {
            cout << "Value " << value << " not found!" << endl;
        }
    }

    // Traversals
    void inorder() const {
        cout << "Inorder: ";
        inorderHelper(root);
        cout << endl;
    }

    void preorder() const {
        cout << "Preorder: ";
        preorderHelper(root);
        cout << endl;
    }

    void postorder() const {
        cout << "Postorder: ";
        postorderHelper(root);
        cout << endl;
    }

    // Level order traversal (BFS)
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

    // Get height
    int height() const {
        return heightHelper(root);
    }

    // Count nodes
    int countNodes() const {
        return countNodesHelper(root);
    }

    // Check if empty
    bool isEmpty() const {
        return root == nullptr;
    }

    // Clear tree
    void clear() {
        clearHelper(root);
        root = nullptr;
    }

    // Find minimum value
    int findMinimum() const {
        if (root == nullptr) {
            throw runtime_error("Tree is empty!");
        }
        Node* min = findMin(root);
        return min->data;
    }

    // Find maximum value
    int findMaximum() const {
        if (root == nullptr) {
            throw runtime_error("Tree is empty!");
        }
        Node* current = root;
        while (current->right != nullptr) {
            current = current->right;
        }
        return current->data;
    }
};

int main() {
    cout << "=== Binary Search Tree Implementation ===" << endl;

    BST bst;

    // Insert elements
    cout << "\n--- Inserting elements ---" << endl;
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);

    /*
    Tree structure:
            50
           /  \
          30   70
         / \   / \
        20 40 60 80
    */

    // Traversals
    cout << "\n--- Traversals ---" << endl;
    bst.inorder();      // Sorted order: 20 30 40 50 60 70 80
    bst.preorder();     // Root first: 50 30 20 40 70 60 80
    bst.postorder();    // Root last: 20 40 30 60 80 70 50
    bst.levelOrder();   // Level by level: 50 30 70 20 40 60 80

    // Search
    cout << "\n--- Searching ---" << endl;
    cout << "Search 40: " << (bst.search(40) ? "Found" : "Not Found") << endl;
    cout << "Search 25: " << (bst.search(25) ? "Found" : "Not Found") << endl;

    // Min and Max
    cout << "\n--- Min and Max ---" << endl;
    cout << "Minimum: " << bst.findMinimum() << endl;
    cout << "Maximum: " << bst.findMaximum() << endl;

    // Tree properties
    cout << "\n--- Tree Properties ---" << endl;
    cout << "Height: " << bst.height() << endl;
    cout << "Total nodes: " << bst.countNodes() << endl;

    // Delete elements
    cout << "\n--- Deleting elements ---" << endl;
    bst.remove(20);  // Leaf node
    bst.inorder();

    bst.remove(30);  // Node with two children
    bst.inorder();

    bst.remove(50);  // Root node
    bst.inorder();

    return 0;
}
```

## Iterative Insert and Search

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

## BST Deletion - Detailed Explanation

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

## BST Validation

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

## Common BST Operations

### 1. Find K-th Smallest Element

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

## Time Complexity Analysis

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

## Space Complexity

- **Storage**: O(n) for n nodes
- **Recursion Stack**: 
  - Average: O(log n)
  - Worst: O(n) for skewed tree

## BST vs Other Data Structures

| Structure | Search | Insert | Delete | Sorted Output |
|-----------|--------|--------|--------|---------------|
| **BST** | O(log n)* | O(log n)* | O(log n)* | O(n) |
| **Array (sorted)** | O(log n) | O(n) | O(n) | O(n) |
| **Linked List** | O(n) | O(1) | O(n) | O(n log n) |
| **Hash Table** | O(1) | O(1) | O(1) | O(n log n) |

*Average case; O(n) worst case for unbalanced BST

## Advantages & Disadvantages

### ✅ Advantages
- **Fast search**: O(log n) average case
- **Dynamic**: Easy insertion and deletion
- **Ordered**: Inorder traversal gives sorted data
- **Range queries**: Efficient range searches
- **No extra space**: Unlike hash tables

### ❌ Disadvantages
- **Can become unbalanced**: Worst case O(n)
- **No random access**: Unlike arrays
- **Extra pointers**: Uses more memory than arrays
- **Not cache-friendly**: Random memory access

## When to Use BST

✅ **Use when:**
- Need fast search, insert, and delete
- Need sorted data
- Performing range queries
- Data is dynamic (frequently changing)
- Don't know size in advance

❌ **Don't use when:**
- Need guaranteed O(log n) → Use **AVL Tree** or **Red-Black Tree**
- Need O(1) search → Use **Hash Table**
- Need sequential access → Use **Array** or **Linked List**
- Data is mostly static → Use **Sorted Array**

## BST Traversal Summary

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

## Key Takeaways

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


## Heap
# Heap Data Structure

A **Heap** is a specialized tree-based data structure that satisfies the **heap property**. It's a complete [[Binary tree]] where each node follows a specific ordering relationship with its children. Heaps are commonly used to implement priority queues and for efficient sorting (heap sort).

## Key Characteristics

- **Complete Binary Tree**: All levels filled complety except possibly the last, which fills left to right
- **Heap Property**: Parent-child relationship follows specific order
- **Time Complexity**: 
  - Insert: O(log n)
  - Delete Max/Min: O(log n)
  - Get Max/Min: O(1)
  - Build Heap: O(n)
- **Use Cases**: Priority queues, heap sort, finding k largest/smallest elements

## Types of Heaps

### 1. Max Heap
**Property**: Parent ≥ Children (largest element at root)

```
        100
       /   \
      80    90
     / \   / \
    40 70 60 50
    
Root = Maximum element
```

### 2. Min Heap
**Property**: Parent ≤ Children (smallest element at root)

```
        10
       /   \
      20    30
     / \   / \
    40 50 60 70
    
Root = Minimum element
```

## Visual Representation

### Complete Binary Tree Property

```
Valid (Complete):           Invalid (Not Complete):

        1                       1
       / \                     / \
      2   3                   2   3
     / \  /                  /     \
    4  5 6                  4       6  ← Gap in middle!


Array Representation:
[1, 2, 3, 4, 5, 6]

For index i:
- Left child:  2*i + 1
- Right child: 2*i + 2
- Parent:      (i-1) / 2
```

## Array-Based Heap Implementation

To understand how heaps work, we are now going to implement the Max-Heap:

### Part 1: Class Structure and Member Variables

```cpp
class MaxHeap {
private:
    vector<int> heap;
```

#### Explanation

**`vector<int> heap;`**
- This is the **core data structure** that stores our heap
- Uses a dynamic array (vector) instead of fixed-size array
- **Why vector?** Dynamic resizing, no need to know size in advance
- **Elements stored as:** `[parent, left_child, right_child, ...]`

#### Array-to-Tree Mapping

```
Array:  [90, 60, 80, 40, 50, 70]
Index:   0   1   2   3   4   5

Tree representation:
        90 (index 0)
       /  \
      60   80 (indices 1, 2)
     / \   /
    40 50 70 (indices 3, 4, 5)
```

**Key relationships:**
- Parent of index `i` → `(i-1)/2`
- Left child of `i` → `2*i + 1`
- Right child of `i` → `2*i + 2`

---

### Part 2: Helper Functions for Navigation

#### Function 1: `parent()`

```cpp
int parent(int i) {
    return (i - 1) / 2;
}
```

**Purpose:** Find the parent index of a given node

**Formula:** `parent(i) = (i - 1) / 2`

**Examples:**
```cpp
parent(5) = (5-1)/2 = 4/2 = 2
parent(4) = (4-1)/2 = 3/2 = 1  // Integer division!
parent(3) = (3-1)/2 = 2/2 = 1
parent(1) = (1-1)/2 = 0/2 = 0
parent(0) = (0-1)/2 = -1/2 = 0  // Root has no parent
```

**Visual:**
```
Index:  0   1   2   3   4   5
        90  60  80  40  50  70
        
parent(5) = 2:
        90
       /  \
      60   80 ← parent
     / \   /
    40 50 70 ← index 5
```

---

#### Function 2: `leftChild()`

```cpp
int leftChild(int i) {
    return 2 * i + 1;
}
```

**Purpose:** Find the left child index of a given node

**Formula:** `leftChild(i) = 2*i + 1`

**Examples:**
```cpp
leftChild(0) = 2*0 + 1 = 1
leftChild(1) = 2*1 + 1 = 3
leftChild(2) = 2*2 + 1 = 5
```

**Visual:**
```
Index 0 (90):
        90
       /
      60 ← leftChild(0) = 1
```

---

#### Function 3: `rightChild()`

```cpp
int rightChild(int i) {
    return 2 * i + 2;
}
```

**Purpose:** Find the right child index of a given node

**Formula:** `rightChild(i) = 2*i + 2`

**Examples:**
```cpp
rightChild(0) = 2*0 + 2 = 2
rightChild(1) = 2*1 + 2 = 4
rightChild(2) = 2*2 + 2 = 6
```

**Visual:**
```
Index 0 (90):
        90
         \
         80 ← rightChild(0) = 2
```


### Part 3: HeapifyUp (Bubble Up)

```cpp
void heapifyUp(int index) {
    while (index > 0 && heap[parent(index)] < heap[index]) {
        // Swap with parent
        swap(heap[index], heap[parent(index)]);
        index = parent(index);
    }
}
```

#### Purpose
**Move a newly inserted element UP the tree** until the max heap property is satisfied.

#### Max Heap Property
**Parent ≥ Both children**

#### Line-by-Line Breakdown

**Line 1:** `while (index > 0 && heap[parent(index)] < heap[index])`

```cpp
while (index > 0 && heap[parent(index)] < heap[index])
      └─────┬─────┘  └───────────────┬───────────────┘
            │                        │
     Not at root            Parent < Current child
                            (violates max heap!)
```

- **`index > 0`**: Stop when we reach root (root has no parent)
- **`heap[parent(index)] < heap[index]`**: Parent is smaller than child → violation!

**Line 2:** `swap(heap[index], heap[parent(index)]);`

- Swap the child with its parent
- Child moves up, parent moves down

**Line 3:** `index = parent(index);`

- **Crucial!** Update index to follow the element up
- Continue checking from new position

#### Complete Example: Insert 90

```
Initial heap:
        80
       /  \
      60   70
     / \
    40 50

Step 1: Insert 90 at end
        80
       /  \
      60   70
     / \   /
    40 50 90  ← index = 5

Step 2: HeapifyUp iteration 1
index = 5, parent(5) = 2
heap[5] = 90, heap[2] = 70
90 > 70? YES → SWAP

        80
       /  \
      60   90  ← moved up
     / \   /
    40 50 70
    
index = parent(5) = 2

Step 3: HeapifyUp iteration 2
index = 2, parent(2) = 0
heap[2] = 90, heap[0] = 80
90 > 80? YES → SWAP

        90  ← reached top!
       /  \
      60   80
     / \   /
    40 50 70
    
index = parent(2) = 0

Step 4: Loop check
index = 0 → index > 0? NO → STOP
```

---

### Part 4: HeapifyDown (also called MaxHeapify in CLRS)
In CLRS:
![[Pasted image 20260310014318.png]]

```cpp
void heapifyDown(int index) {
    int size = heap.size();
    int largest = index;
    int left = leftChild(index);
    int right = rightChild(index);

    // Check if left child is larger
    if (left < size && heap[left] > heap[largest]) {
        largest = left;
    }

    // Check if right child is larger
    if (right < size && heap[right] > heap[largest]) {
        largest = right;
    }

    // If largest is not current node, swap and continue
    if (largest != index) {
        swap(heap[index], heap[largest]);
        heapifyDown(largest);
    }
}
```

#### Purpose
**Move an element DOWN the tree** by swapping with the larger child until heap property is satisfied.

#### Line-by-Line Breakdown

**Line 1:** `int size = heap.size();`
- Cache the size to avoid repeated function calls
- Used for bounds checking

**Line 2:** `int largest = index;`
- **Assumption:** Current node is the largest
- Will update if we find a larger child

**Lines 3-4:** Calculate child indices
```cpp
int left = leftChild(index);   // 2*i + 1
int right = rightChild(index); // 2*i + 2
```

**Lines 6-8:** Check left child
```cpp
if (left < size && heap[left] > heap[largest]) {
    largest = left;
}
```
- **`left < size`**: Does left child exist? (bounds check)
- **`heap[left] > heap[largest]`**: Is left child larger?
- If both true → left child is the largest so far

**Lines 10-12:** Check right child
```cpp
if (right < size && heap[right] > heap[largest]) {
    largest = right;
}
```
- Same logic for right child
- **Important:** Compares with current `largest` (might be left child now)

**Lines 14-17:** Swap and recurse
```cpp
if (largest != index) {
    swap(heap[index], heap[largest]);
    heapifyDown(largest);  // Recursive call!
}
```
- **`largest != index`**: Found a larger child?
- Swap current with the larger child
- **Recursively** heapify down from that child's position

#### Complete Example: Extract Max

```
Initial heap:
        90
       /  \
      60   80
     / \   /
    40 50 70

Step 1: Remove root (90), replace with last (70)
        70  ← Now at root, violates heap property!
       /  \
      60   80
     / \
    40 50

Step 2: HeapifyDown from root (index 0)
index = 0
left = 1, right = 2
heap[0] = 70, heap[1] = 60, heap[2] = 80

Find largest:
largest = 0 (initially)
heap[1] = 60 < heap[0] = 70 → no change
heap[2] = 80 > heap[0] = 70 → largest = 2

largest (2) != index (0) → SWAP

        80  ← Swapped
       /  \
      60   70  ← Now at index 2
     / \
    40 50

Step 3: Recursive call heapifyDown(2)
index = 2
left = 5, right = 6
left >= size (5 >= 5) → no left child
right >= size → no right child

No children → STOP (heap property satisfied!)

Final:
        80
       /  \
      60   70
     / \
    40 50
```

---

### Part 5: Constructors

#### Default Constructor

```cpp
MaxHeap() {}
```

**Purpose:** Create an empty heap

**Usage:**
```cpp
MaxHeap heap;  // Empty heap, no elements
```

---

#### Constructor from Array (Build Heap)

```cpp
MaxHeap(const vector<int>& arr) {
    heap = arr;
    // Build heap (heapify)
    for (int i = heap.size() / 2 - 1; i >= 0; i--) {
        heapifyDown(i);
    }
}
```

#### Purpose
Convert an **arbitrary array** into a valid max heap **in O(n) time**!

#### Line-by-Line Breakdown

**Line 1:** `heap = arr;`
- Copy the input array as-is
- Not a heap yet! Just raw data

**Line 3:** `for (int i = heap.size() / 2 - 1; i >= 0; i--)`

```cpp
for (int i = heap.size() / 2 - 1; i >= 0; i--)
        └────────┬────────┘
                 │
         Last non-leaf node
```

**Why `heap.size() / 2 - 1`?**

```
For array of size 7:
Index:  0   1   2   3   4   5   6
        
Tree:       0
           / \
          1   2
         /|   |\
        3 4   5 6
        
Leaf nodes: 3, 4, 5, 6 (indices ≥ 7/2 = 3)
Non-leaf nodes: 0, 1, 2 (indices < 3)

Last non-leaf = 7/2 - 1 = 2
```

**Leaf nodes don't need heapifyDown** (they have no children, automatically satisfy heap property)

**Line 4:** `heapifyDown(i);`
- Process each non-leaf node from bottom to top
- Ensures subtrees are heaps before processing parent

#### Why Bottom-Up is O(n)?

**Top-down (inserting one by one):**
```
Insert n elements → n * log(n) = O(n log n)
```

**Bottom-up (heapify):**
```
Most nodes are near bottom (low height)
Only few nodes at top (high height)
Average work per node is constant!
Total: O(n)
```
Check out [[Time Complexity of Max-Heap Operations#5. Build Heap (`Build-Heap`)]] for clear explanation of why.

#### Example: Build Heap from `[10, 20, 15, 30, 40]`

```
Step 0: Raw array
        10
       /  \
      20   15
     / \
    30 40

Indices to process: 1, 0 (size/2 - 1 = 5/2 - 1 = 1)

Step 1: heapifyDown(1)
Compare 20 with children 30, 40
40 is largest → swap

        10
       /  \
      40   15
     / \
    30 20

Step 2: heapifyDown(0)
Compare 10 with children 40, 15
40 is largest → swap

        40
       /  \
      10   15
     / \
    30 20

Recursively heapifyDown(1):
Compare 10 with children 30, 20
30 is largest → swap

        40
       /  \
      30   15
     / \
    10 20

Done! Valid max heap.
```

---

### Part 6: Insert Operation

```cpp
void insert(int value) {
    heap.push_back(value);
    heapifyUp(heap.size() - 1);
    cout << "Inserted: " << value << endl;
}
```

#### Purpose
Add a new element while maintaining the max heap property.

#### Line-by-Line Breakdown

**Line 1:** `heap.push_back(value);`
- Add element at the **end** of array
- Maintains **complete binary tree** property
- Temporarily violates max heap property

**Line 2:** `heapifyUp(heap.size() - 1);`
- Fix heap property by bubbling up
- `heap.size() - 1` is the index of newly added element

**Line 3:** `cout << "Inserted: " << value << endl;`
- User feedback

#### Time Complexity
**O(log n)** - Element may bubble up entire height of tree

#### Example: Insert 85

```
Before:
        90
       /  \
      60   80
     / \
    40 50
    
Array: [90, 60, 80, 40, 50]

Step 1: push_back(85)
Array: [90, 60, 80, 40, 50, 85]

        90
       /  \
      60   80
     / \   /
    40 50 85  ← Added here (index 5)

Step 2: heapifyUp(5)
parent(5) = 2
85 > 80? YES → Swap

        90
       /  \
      60   85  ← Bubbled up
     / \   /
    40 50 80

parent(2) = 0
85 > 90? NO → Stop

Final:
Array: [90, 60, 85, 40, 50, 80]
```

---

### Part 7: Extract Max Operation

```cpp
int extractMax() {
    if (isEmpty()) {
        throw runtime_error("Heap is empty!");
    }

    int maxValue = heap[0];

    // Move last element to root and heapify down
    heap[0] = heap.back();
    heap.pop_back();

    if (!isEmpty()) {
        heapifyDown(0);
    }

    return maxValue;
}
```

#### Purpose
Remove and return the **maximum element** (root) while maintaining heap property.

#### Line-by-Line Breakdown

**Lines 1-3:** Error handling
```cpp
if (isEmpty()) {
    throw runtime_error("Heap is empty!");
}
```
- Can't extract from empty heap
- Throws exception for safety

**Line 5:** `int maxValue = heap[0];`
- Save the root value to return later
- Root always contains maximum in max heap

**Line 8:** `heap[0] = heap.back();`
- **Key insight:** Move **last element** to root
- Why? Maintains complete binary tree property
- Temporarily violates max heap property

**Line 9:** `heap.pop_back();`
- Remove the last element
- Reduces heap size by 1

**Lines 11-13:** Fix heap property
```cpp
if (!isEmpty()) {
    heapifyDown(0);
}
```
- **Check if not empty:** Edge case when heap had only 1 element
- HeapifyDown from root to restore heap property

**Line 15:** `return maxValue;`
- Return the saved maximum value

#### Time Complexity
**O(log n)** - HeapifyDown may traverse entire height

#### Complete Example

```
Initial:
        90
       /  \
      60   80
     / \   /
    40 50 70
    
Array: [90, 60, 80, 40, 50, 70]

Step 1: maxValue = heap[0] = 90 (saved)

Step 2: heap[0] = heap.back() = 70
Array: [70, 60, 80, 40, 50, 70]

Step 3: heap.pop_back()
Array: [70, 60, 80, 40, 50]

        70  ← Wrong! Violates heap property
       /  \
      60   80
     / \
    40 50

Step 4: heapifyDown(0)
Largest of 70, 60, 80 is 80 → Swap

        80  ← Correct!
       /  \
      60   70
     / \
    40 50

Step 5: return 90
```

---

### Part 8: Get Max (Peek)

```cpp
int getMax() const {
    if (isEmpty()) {
        throw runtime_error("Heap is empty!");
    }
    return heap[0];
}
```

#### Purpose
View the maximum element **without removing** it.

#### Breakdown

- **`const`**: Doesn't modify the heap
- **`heap[0]`**: Root always has maximum
- **O(1) time**: Just array access!

#### Example
```cpp
MaxHeap heap;
heap.insert(50);
heap.insert(70);
heap.insert(60);

cout << heap.getMax();  // Prints: 70 (but doesn't remove it)
cout << heap.getMax();  // Still prints: 70
```

---

### Part 9: Utility Functions

#### isEmpty()

```cpp
bool isEmpty() const {
    return heap.empty();
}
```

**Purpose:** Check if heap has no elements

**Returns:** `true` if empty, `false` otherwise

---

#### size()

```cpp
int size() const {
    return heap.size();
}
```

**Purpose:** Get number of elements in heap

**Returns:** Integer count

---

### Part 10: Display Functions

#### display() - Array View

```cpp
void display() const {
    if (isEmpty()) {
        cout << "Heap is empty" << endl;
        return;
    }

    cout << "Heap array: ";
    for (int val : heap) {
        cout << val << " ";
    }
    cout << endl;
}
```

**Purpose:** Print heap as a simple array

**Output Example:**
```
Heap array: 90 60 80 40 50 70
```

---

#### displayTree() - Tree View

```cpp
void displayTree() const {
    if (isEmpty()) {
        cout << "Heap is empty" << endl;
        return;
    }

    cout << "Heap tree structure:" << endl;
    displayTreeHelper(0, "", false);
}
```

**Purpose:** Print heap as a tree structure (calls helper)

---

#### displayTreeHelper() - Recursive Tree Printer

```cpp
void displayTreeHelper(int index, string prefix, bool isLeft) const {
    if (index >= heap.size()) {
        return;  // Base case: index out of bounds
    }

    cout << prefix;
    cout << (isLeft ? "├──" : "└──");
    cout << heap[index] << endl;

    if (leftChild(index) < heap.size() || rightChild(index) < heap.size()) {
        if (leftChild(index) < heap.size()) {
            displayTreeHelper(leftChild(index), prefix + (isLeft ? "│   " : "    "), true);
        }
        if (rightChild(index) < heap.size()) {
            displayTreeHelper(rightChild(index), prefix + (isLeft ? "│   " : "    "), false);
        }
    }
}
```

##### Purpose
Recursively print tree with nice ASCII art formatting.

##### Parameters

- **`index`**: Current node index to print
- **`prefix`**: String to print before node (for indentation)
- **`isLeft`**: Is this node a left child? (affects formatting)

##### Line-by-Line Breakdown

**Lines 1-3:** Base case
```cpp
if (index >= heap.size()) {
    return;
}
```
- Stop if index is out of bounds (no node exists)

**Lines 5-7:** Print current node
```cpp
cout << prefix;
cout << (isLeft ? "├──" : "└──");
cout << heap[index] << endl;
```
- Print prefix for indentation
- Use `├──` for left child, `└──` for right child
- Print the node value

**Lines 9-16:** Recursive calls for children
```cpp
if (leftChild(index) < heap.size() || rightChild(index) < heap.size()) {
    if (leftChild(index) < heap.size()) {
        displayTreeHelper(leftChild(index), 
                         prefix + (isLeft ? "│   " : "    "), 
                         true);
    }
    if (rightChild(index) < heap.size()) {
        displayTreeHelper(rightChild(index), 
                         prefix + (isLeft ? "│   " : "    "), 
                         false);
    }
}
```

**Prefix logic:**
- If current is left child: add `"│   "` (vertical line continues)
- If current is right child: add `"    "` (no vertical line)

##### Example Output

```
Heap tree structure:
└──90
    ├──60
    │   ├──40
    │   └──50
    └──80
        └──70
```

**Visualization:**
```
        90
       /  \
      60   80
     / \   /
    40 50 70
```

---

### Part 11: Main Function

```cpp
int main() {
    cout << "=== Max Heap Implementation ===" << endl;

    MaxHeap maxHeap;

    // Insert elements
    cout << "\n--- Inserting elements ---" << endl;
    maxHeap.insert(50);
    maxHeap.insert(30);
    maxHeap.insert(70);
    maxHeap.insert(20);
    maxHeap.insert(40);
    maxHeap.insert(60);
    maxHeap.insert(80);

    // Display heap
    cout << "\n";
    maxHeap.display();
    maxHeap.displayTree();

    // Get max
    cout << "\nMaximum element: " << maxHeap.getMax() << endl;

    // Extract max
    cout << "\n--- Extracting maximum elements ---" << endl;
    cout << "Extracted: " << maxHeap.extractMax() << endl;
    cout << "Extracted: " << maxHeap.extractMax() << endl;

    cout << "\n";
    maxHeap.display();
    maxHeap.displayTree();

    return 0;
}
```

#### Step-by-Step Execution

**Create empty heap:**
```cpp
MaxHeap maxHeap;
// heap = []
```

**Insert 50:**
```cpp
maxHeap.insert(50);
// heap = [50]
//   50
```

**Insert 30:**
```cpp
maxHeap.insert(30);
// heap = [50, 30]
//      50
//     /
//    30
```

**Insert 70:**
```cpp
maxHeap.insert(70);
// After heapifyUp: [70, 30, 50]
//      70
//     /  \
//    30   50
```

**After all insertions:**
```
heap = [80, 40, 70, 20, 30, 60, 50]

      80
     /  \
    40   70
   / \   / \
  20 30 60 50
```

**Extract max (80):**
```
Before: [80, 40, 70, 20, 30, 60, 50]
After:  [70, 40, 60, 20, 30, 50]

      70
     /  \
    40   60
   / \   /
  20 30 50
```

**Extract max (70):**
```
Before: [70, 40, 60, 20, 30, 50]
After:  [60, 40, 50, 20, 30]

      60
     /  \
    40   50
   / \
  20 30
```

---

### Summary of Time Complexities

| Operation | Time Complexity | Why |
|-----------|----------------|-----|
| **insert()** | O(log n) | HeapifyUp through height |
| **extractMax()** | O(log n) | HeapifyDown through height |
| **getMax()** | O(1) | Direct array access |
| **Build heap** | O(n) | Bottom-up heapification |
| **heapifyUp()** | O(log n) | Traverse up to root |
| **heapifyDown()** | O(log n) | Traverse down to leaf |

#### Check out the proof for all the [[Time Complexity of Max-Heap Operations]]

## Min Heap Implementation

```cpp name=heap_min_heap.cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

class MinHeap {
private:
    vector<int> heap;

    int parent(int i) { return (i - 1) / 2; }
    int leftChild(int i) { return 2 * i + 1; }
    int rightChild(int i) { return 2 * i + 2; }

    void heapifyUp(int index) {
        while (index > 0 && heap[parent(index)] > heap[index]) {
            swap(heap[index], heap[parent(index)]);
            index = parent(index);
        }
    }

    void heapifyDown(int index) {
        int size = heap.size();
        int smallest = index;
        int left = leftChild(index);
        int right = rightChild(index);

        if (left < size && heap[left] < heap[smallest]) {
            smallest = left;
        }

        if (right < size && heap[right] < heap[smallest]) {
            smallest = right;
        }

        if (smallest != index) {
            swap(heap[index], heap[smallest]);
            heapifyDown(smallest);
        }
    }

public:
    MinHeap() {}

    MinHeap(const vector<int>& arr) {
        heap = arr;
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    void insert(int value) {
        heap.push_back(value);
        heapifyUp(heap.size() - 1);
        cout << "Inserted: " << value << endl;
    }

    int extractMin() {
        if (isEmpty()) {
            throw runtime_error("Heap is empty!");
        }

        int minValue = heap[0];
        heap[0] = heap.back();
        heap.pop_back();

        if (!isEmpty()) {
            heapifyDown(0);
        }

        return minValue;
    }

    int getMin() const {
        if (isEmpty()) {
            throw runtime_error("Heap is empty!");
        }
        return heap[0];
    }

    bool isEmpty() const {
        return heap.empty();
    }

    int size() const {
        return heap.size();
    }

    void display() const {
        cout << "Heap array: ";
        for (int val : heap) {
            cout << val << " ";
        }
        cout << endl;
    }

    void displayTree() const {
        if (isEmpty()) {
            cout << "Heap is empty" << endl;
            return;
        }

        cout << "Heap tree structure:" << endl;
        displayTreeHelper(0, "", false);
    }

private:
    void displayTreeHelper(int index, string prefix, bool isLeft) const {
        if (index >= heap.size()) {
            return;
        }

        cout << prefix;
        cout << (isLeft ? "├──" : "└──");
        cout << heap[index] << endl;

        if (leftChild(index) < heap.size() || rightChild(index) < heap.size()) {
            if (leftChild(index) < heap.size()) {
                displayTreeHelper(leftChild(index), prefix + (isLeft ? "│   " : "    "), true);
            }
            if (rightChild(index) < heap.size()) {
                displayTreeHelper(rightChild(index), prefix + (isLeft ? "│   " : "    "), false);
            }
        }
    }
};

int main() {
    cout << "=== Min Heap Implementation ===" << endl;

    MinHeap minHeap;

    cout << "\n--- Inserting elements ---" << endl;
    minHeap.insert(50);
    minHeap.insert(30);
    minHeap.insert(70);
    minHeap.insert(20);
    minHeap.insert(40);
    minHeap.insert(60);
    minHeap.insert(10);

    cout << "\n";
    minHeap.display();
    minHeap.displayTree();

    cout << "\nMinimum element: " << minHeap.getMin() << endl;

    cout << "\n--- Extracting minimum elements ---" << endl;
    cout << "Extracted: " << minHeap.extractMin() << endl;
    cout << "Extracted: " << minHeap.extractMin() << endl;

    cout << "\n";
    minHeap.display();
    minHeap.displayTree();

    return 0;
}
```

## Heapify Visualization

```cpp name=heapify_visualization.cpp
#include <iostream>
#include <vector>
using namespace std;

class HeapVisualizer {
private:
    vector<int> heap;

    void displayArray(const string& message) {
        cout << message << ": [";
        for (int i = 0; i < heap.size(); i++) {
            cout << heap[i];
            if (i < heap.size() - 1) cout << ", ";
        }
        cout << "]" << endl;
    }

    void heapifyDown(int index, bool verbose = true) {
        int size = heap.size();
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (verbose) {
            cout << "\n  Checking index " << index << " (value: " << heap[index] << ")" << endl;
        }

        if (left < size && heap[left] > heap[largest]) {
            largest = left;
            if (verbose) {
                cout << "    Left child " << heap[left] << " > current" << endl;
            }
        }

        if (right < size && heap[right] > heap[largest]) {
            largest = right;
            if (verbose) {
                cout << "    Right child " << heap[right] << " > current" << endl;
            }
        }

        if (largest != index) {
            if (verbose) {
                cout << "    Swapping " << heap[index] << " with " << heap[largest] << endl;
            }
            swap(heap[index], heap[largest]);
            displayArray("    After swap");
            heapifyDown(largest, verbose);
        } else {
            if (verbose) {
                cout << "    No swap needed, heap property satisfied" << endl;
            }
        }
    }

public:
    void buildHeap(const vector<int>& arr) {
        heap = arr;
        cout << "=== Building Max Heap ===" << endl;
        displayArray("Initial array");

        // Start from last non-leaf node
        int startIdx = heap.size() / 2 - 1;
        cout << "\nStarting from index " << startIdx << " (last non-leaf node)" << endl;

        for (int i = startIdx; i >= 0; i--) {
            cout << "\n--- Heapifying subtree at index " << i << " ---" << endl;
            heapifyDown(i, true);
        }

        cout << "\n=== Heap Built Successfully ===" << endl;
        displayArray("Final heap");
    }

    void displayTree() {
        cout << "\nHeap tree structure:" << endl;
        displayTreeHelper(0, "", false);
    }

private:
    void displayTreeHelper(int index, string prefix, bool isLeft) {
        if (index >= heap.size()) return;

        cout << prefix;
        cout << (isLeft ? "├──" : "└──");
        cout << heap[index] << endl;

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heap.size()) {
            displayTreeHelper(left, prefix + (isLeft ? "│   " : "    "), true);
        }
        if (right < heap.size()) {
            displayTreeHelper(right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
};

int main() {
    vector<int> arr = {10, 20, 15, 30, 40, 25, 35};
    
    HeapVisualizer visualizer;
    visualizer.buildHeap(arr);
    visualizer.displayTree();

    return 0;
}
```

## Heap Sort

### What is Heap Sort?

**Heap Sort** is a comparison-based sorting algorithm that uses a **binary heap** data structure. It combines the best of both worlds:
- **Space efficiency** of insertion sort (in-place, O(1) extra space)
- **Time efficiency** of merge sort (guaranteed O(n log n))

### Core Idea

1. **Build a max heap** from the unsorted array → O(n)
2. **Repeatedly extract the maximum** (root) and place it at the end → O(n log n)

**Result:** Sorted array in ascending order!

---

### Visual Overview

```
Unsorted array: [4, 10, 3, 5, 1]

Step 1: Build Max Heap
[4, 10, 3, 5, 1] → [10, 5, 3, 4, 1]

        10
       /  \
      5    3
     / \
    4   1

Step 2: Extract max repeatedly
Extract 10 → [1, 5, 3, 4 | 10]  ← 10 at end
Heapify    → [5, 4, 3, 1 | 10]

Extract 5  → [1, 4, 3 | 5, 10]
Heapify    → [4, 1, 3 | 5, 10]

Extract 4  → [1, 3 | 4, 5, 10]
Heapify    → [3, 1 | 4, 5, 10]

Extract 3  → [1 | 3, 4, 5, 10]

Final: [1, 3, 4, 5, 10] ✓ Sorted!
```

---

### Pseudocode

#### Complete Heap Sort Algorithm

```text name=heapsort_pseudocode.txt
HEAP-SORT(A, n)
    // A: array to sort
    // n: size of array
    
    // Step 1: Build max heap
    BUILD-MAX-HEAP(A, n)
    
    // Step 2: Extract elements one by one
    heapSize = n
    for i = n-1 down to 1 do
        // Move current root to end
        swap(A[0], A[i])
        
        // Reduce heap size
        heapSize = heapSize - 1
        
        // Heapify the root
        MAX-HEAPIFY(A, 0, heapSize)
    
    return A


BUILD-MAX-HEAP(A, n)
    // Build heap from bottom up
    // Start from last non-leaf node
    
    for i = ⌊n/2⌋ - 1 down to 0 do
        MAX-HEAPIFY(A, i, n)


MAX-HEAPIFY(A, i, heapSize)
    // Maintain max heap property at node i
    // heapSize: current size of heap (might be < array size)
    
    left = 2 × i + 1
    right = 2 × i + 2
    largest = i
    
    // Find largest among node and its children
    if left < heapSize AND A[left] > A[largest] then
        largest = left
    
    if right < heapSize AND A[right] > A[largest] then
        largest = right
    
    // If largest is not current node, swap and recurse
    if largest ≠ i then
        swap(A[i], A[largest])
        MAX-HEAPIFY(A, largest, heapSize)
```

---

### Complete C++ Implementation

```cpp name=heapsort_complete.cpp
#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

class HeapSort {
private:
    // Maintain max heap property at node i
    // heapSize: elements to consider (not full array size during extraction)
    void maxHeapify(vector<int>& arr, int i, int heapSize) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        
        // Find largest among root, left child, right child
        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }
        
        // If largest is not root, swap and continue heapifying
        if (largest != i) {
            swap(arr[i], arr[largest]);
            maxHeapify(arr, largest, heapSize);
        }
    }
    
    // Build max heap from unsorted array
    void buildMaxHeap(vector<int>& arr) {
        int n = arr.size();
        
        // Start from last non-leaf node and heapify all nodes
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(arr, i, n);
        }
    }
    
public:
    // Main heap sort function
    void sort(vector<int>& arr) {
        int n = arr.size();
        
        // Step 1: Build max heap
        buildMaxHeap(arr);
        
        // Step 2: Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root (maximum) to end
            swap(arr[0], arr[i]);
            
            // Heapify the reduced heap
            maxHeapify(arr, 0, i);
        }
    }
    
    // Display array
    void display(const vector<int>& arr, const string& message = "") {
        if (!message.empty()) {
            cout << message << ": ";
        }
        
        cout << "[";
        for (int i = 0; i < arr.size(); i++) {
            cout << arr[i];
            if (i < arr.size() - 1) cout << ", ";
        }
        cout << "]" << endl;
    }
};

int main() {
    cout << "=== Heap Sort Implementation ===" << endl;
    
    HeapSort hs;
    
    // Test case 1
    cout << "\n--- Test 1: Random array ---" << endl;
    vector<int> arr1 = {4, 10, 3, 5, 1};
    hs.display(arr1, "Original");
    hs.sort(arr1);
    hs.display(arr1, "Sorted");
    
    // Test case 2
    cout << "\n--- Test 2: Already sorted ---" << endl;
    vector<int> arr2 = {1, 2, 3, 4, 5};
    hs.display(arr2, "Original");
    hs.sort(arr2);
    hs.display(arr2, "Sorted");
    
    // Test case 3
    cout << "\n--- Test 3: Reverse sorted ---" << endl;
    vector<int> arr3 = {9, 7, 5, 3, 1};
    hs.display(arr3, "Original");
    hs.sort(arr3);
    hs.display(arr3, "Sorted");
    
    // Test case 4
    cout << "\n--- Test 4: Duplicates ---" << endl;
    vector<int> arr4 = {5, 2, 8, 2, 9, 1, 5};
    hs.display(arr4, "Original");
    hs.sort(arr4);
    hs.display(arr4, "Sorted");
    
    return 0;
}
```

**Output:**
```
=== Heap Sort Implementation ===

--- Test 1: Random array ---
Original: [4, 10, 3, 5, 1]
Sorted: [1, 3, 4, 5, 10]

--- Test 2: Already sorted ---
Original: [1, 2, 3, 4, 5]
Sorted: [1, 2, 3, 4, 5]

--- Test 3: Reverse sorted ---
Original: [9, 7, 5, 3, 1]
Sorted: [1, 3, 5, 7, 9]

--- Test 4: Duplicates ---
Original: [5, 2, 8, 2, 9, 1, 5]
Sorted: [1, 2, 2, 5, 5, 8, 9]
```

---

### Detailed Step-by-Step Visualization

Let's trace heap sort on `[4, 10, 3, 5, 1]` with **every single step**.

#### Phase 1: Build Max Heap

**Initial array:**
```
[4, 10, 3, 5, 1]
 0  1  2  3  4

Tree representation:
        4
       / \
      10  3
     / \
    5   1
```

**Step 1.1: Find last non-leaf node**
```
n = 5
Last non-leaf = n/2 - 1 = 5/2 - 1 = 2 - 1 = 1

Process indices: 1 → 0
```

**Step 1.2: Heapify index 1 (value 10)**
```
Current node: 10
Left child (index 3): 5
Right child (index 4): 1

Compare:
10 > 5? YES
10 > 1? YES

largest = 1 (no change needed)

Array: [4, 10, 3, 5, 1]  (unchanged)
```

**Step 1.3: Heapify index 0 (value 4)**
```
Current node: 4
Left child (index 1): 10
Right child (index 2): 3

Compare:
10 > 4? YES → largest = 1
3 > 10? NO

largest = 1 (left child)
Swap: arr[0] ↔ arr[1]

Array: [10, 4, 3, 5, 1]

Tree after swap:
        10
       / \
      4   3
     / \
    5   1

Now recursively heapify index 1:
Current node: 4
Left child (index 3): 5
Right child (index 4): 1

Compare:
5 > 4? YES → largest = 3
1 > 5? NO

largest = 3 (left child)
Swap: arr[1] ↔ arr[3]

Array: [10, 5, 3, 4, 1]

Tree after swap:
        10
       / \
      5   3
     / \
    4   1
```

**Max heap built! ✓**

---

#### Phase 2: Extract Maximum (Sorting)

Now we repeatedly:
1. Swap root with last element
2. Reduce heap size
3. Heapify root

**Iteration 1:**
```
Current heap: [10, 5, 3, 4, 1]
              └─heap size = 5─┘

Step 1: Swap root with last
[10, 5, 3, 4, 1] → [1, 5, 3, 4 | 10]
                             ↑
                    10 is now in final position!

Step 2: Reduce heap size to 4
Active heap: [1, 5, 3, 4]

Step 3: Heapify root (index 0)

Tree:
        1
       / \
      5   3
     /
    4

Current node: 1
Left (1): 5
Right (2): 3

Compare:
5 > 1? YES → largest = 1
3 > 5? NO

Swap: arr[0] ↔ arr[1]
[5, 1, 3, 4 | 10]

Recursively heapify index 1:
Current node: 1
Left (3): 4
Right (4): out of bounds (heap size = 4)

Compare:
4 > 1? YES → largest = 3

Swap: arr[1] ↔ arr[3]
[5, 4, 3, 1 | 10]

Tree:
        5
       / \
      4   3
     /
    1

Heap restored! ✓
```

**Iteration 2:**
```
Current heap: [5, 4, 3, 1 | 10]
              └─size = 4──┘

Swap root with last:
[1, 4, 3 | 5, 10]
          ↑
     5 in final position!

Heapify root (heap size = 3):
Tree:
        1
       / \
      4   3

Compare 1, 4, 3:
largest = 4

Swap: arr[0] ↔ arr[1]
[4, 1, 3 | 5, 10]

Recursively heapify index 1:
Node: 1 (no children in heap size 3)
Done.

Result: [4, 1, 3 | 5, 10]
```

**Iteration 3:**
```
Current heap: [4, 1, 3 | 5, 10]
              └─size = 3─┘

Swap root with last:
[3, 1 | 4, 5, 10]
       ↑
    4 in final position!

Heapify root (heap size = 2):
Tree:
        3
       /
      1

Compare 3, 1:
3 > 1 → largest = 0 (no change)

Result: [3, 1 | 4, 5, 10]
```

**Iteration 4:**
```
Current heap: [3, 1 | 4, 5, 10]
              └size=2┘

Swap root with last:
[1 | 3, 4, 5, 10]
  ↑
3 in final position!

Heap size = 1 (only one element, no heapify needed)

Result: [1 | 3, 4, 5, 10]
```

**Loop ends (i = 0)**

**Final sorted array: [1, 3, 4, 5, 10] ✓**

---

### Detailed Visualization with Tree Structures

```cpp name=heapsort_visualization.cpp
#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

class HeapSortVisualized {
private:
    int stepCount = 0;
    
    void displayArray(const vector<int>& arr, int sortedFrom) {
        cout << "Array: [";
        for (int i = 0; i < arr.size(); i++) {
            if (i == sortedFrom && sortedFrom < arr.size()) {
                cout << "| ";
            }
            cout << arr[i];
            if (i < arr.size() - 1) cout << ", ";
        }
        cout << "]" << endl;
        
        if (sortedFrom < arr.size()) {
            cout << "       ";
            for (int i = 0; i < sortedFrom; i++) {
                cout << "   ";
            }
            cout << "↑ heap  | sorted →" << endl;
        }
    }
    
    void displayTree(const vector<int>& arr, int heapSize, int index = 0, string prefix = "", bool isLeft = true) {
        if (index >= heapSize) return;
        
        cout << prefix;
        cout << (isLeft ? "├──" : "└──");
        cout << arr[index];
        
        if (index >= heapSize) {
            cout << " (outside heap)";
        }
        cout << endl;
        
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        
        if (left < heapSize) {
            displayTree(arr, heapSize, left, prefix + (isLeft ? "│   " : "    "), true);
        }
        if (right < heapSize) {
            displayTree(arr, heapSize, right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
    
    void maxHeapify(vector<int>& arr, int i, int heapSize, bool verbose) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        
        if (verbose) {
            cout << "  Heapifying node " << i << " (value: " << arr[i] << ")" << endl;
        }
        
        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            if (verbose) {
                cout << "    Swapping " << arr[i] << " ↔ " << arr[largest] 
                     << " (indices " << i << " ↔ " << largest << ")" << endl;
            }
            
            swap(arr[i], arr[largest]);
            maxHeapify(arr, largest, heapSize, verbose);
        } else {
            if (verbose) {
                cout << "    No swap needed (heap property satisfied)" << endl;
            }
        }
    }
    
    void buildMaxHeap(vector<int>& arr, bool verbose) {
        int n = arr.size();
        
        if (verbose) {
            cout << "\n╔═══════════════════════════════════════╗" << endl;
            cout << "║  PHASE 1: BUILD MAX HEAP              ║" << endl;
            cout << "╚═══════════════════════════════════════╝" << endl;
            cout << "\nStarting from last non-leaf node: " << (n/2 - 1) << endl;
        }
        
        for (int i = n / 2 - 1; i >= 0; i--) {
            if (verbose) {
                cout << "\n--- Processing index " << i << " ---" << endl;
            }
            maxHeapify(arr, i, n, verbose);
            
            if (verbose) {
                displayArray(arr, n);
            }
        }
        
        if (verbose) {
            cout << "\n✓ Max heap built successfully!" << endl;
            cout << "\nFinal heap structure:" << endl;
            displayTree(arr, n);
        }
    }
    
public:
    void sort(vector<int>& arr, bool verbose = true) {
        int n = arr.size();
        
        if (verbose) {
            cout << "\n════════════════════════════════════════" << endl;
            cout << "        HEAP SORT VISUALIZATION" << endl;
            cout << "═══════════════��════════════════════════" << endl;
            cout << "\nOriginal array: ";
            displayArray(arr, n);
        }
        
        // Phase 1: Build max heap
        buildMaxHeap(arr, verbose);
        
        // Phase 2: Extract elements
        if (verbose) {
            cout << "\n╔═══════════════════════════════════════╗" << endl;
            cout << "║  PHASE 2: EXTRACT AND SORT            ║" << endl;
            cout << "╚═══════════════════════════════════════╝" << endl;
        }
        
        for (int i = n - 1; i > 0; i--) {
            if (verbose) {
                cout << "\n--- Iteration " << (n - i) << " ---" << endl;
                cout << "Current max: " << arr[0] << endl;
                cout << "Swapping arr[0] ↔ arr[" << i << "]: " 
                     << arr[0] << " ↔ " << arr[i] << endl;
            }
            
            swap(arr[0], arr[i]);
            
            if (verbose) {
                displayArray(arr, i);
                cout << "\nHeap structure (size = " << i << "):" << endl;
                displayTree(arr, i);
                cout << "\nRestoring heap property..." << endl;
            }
            
            maxHeapify(arr, 0, i, verbose);
            
            if (verbose) {
                cout << "\nAfter heapify:" << endl;
                displayArray(arr, i);
                displayTree(arr, i);
            }
        }
        
        if (verbose) {
            cout << "\n╔═══════════════════════════════════════╗" << endl;
            cout << "║  SORTING COMPLETE ✓                   ║" << endl;
            cout << "╚═══════════════════════════════════════╝" << endl;
            cout << "\nFinal sorted array: ";
            displayArray(arr, n);
        }
    }
};

int main() {
    HeapSortVisualized hsv;
    
    vector<int> arr = {4, 10, 3, 5, 1};
    hsv.sort(arr, true);
    
    return 0;
}
```

**Output (Partial):**
```
════════════════════════════════════════
        HEAP SORT VISUALIZATION
════════════════════════════════════════

Original array: Array: [4, 10, 3, 5, 1]
       ↑ heap  | sorted →

╔═══════════════════════════════════════╗
║  PHASE 1: BUILD MAX HEAP              ║
╚═══════════════════════════════════════╝

Starting from last non-leaf node: 1

--- Processing index 1 ---
  Heapifying node 1 (value: 10)
    No swap needed (heap property satisfied)
Array: [4, 10, 3, 5, 1]
       ↑ heap  | sorted →

--- Processing index 0 ---
  Heapifying node 0 (value: 4)
    Swapping 4 ↔ 10 (indices 0 ↔ 1)
  Heapifying node 1 (value: 4)
    Swapping 4 ↔ 5 (indices 1 ↔ 3)
  Heapifying node 3 (value: 4)
    No swap needed (heap property satisfied)
Array: [10, 5, 3, 4, 1]
       ↑ heap  | sorted →

✓ Max heap built successfully!

Final heap structure:
└──10
    ├──5
    │   ├──4
    │   └──1
    └──3

╔═══════════════════════════════════════╗
║  PHASE 2: EXTRACT AND SORT            ║
╚═══════════════════════════════════════╝

--- Iteration 1 ---
Current max: 10
Swapping arr[0] ↔ arr[4]: 10 ↔ 1
Array: [1, 5, 3, 4, | 10]
       ↑ heap  | sorted →

Heap structure (size = 4):
└──1
    ├──5
    │   └──4
    └──3

Restoring heap property...
  Heapifying node 0 (value: 1)
    Swapping 1 ↔ 5 (indices 0 ↔ 1)
  Heapifying node 1 (value: 1)
    Swapping 1 ↔ 4 (indices 1 ↔ 3)
  Heapifying node 3 (value: 1)
    No swap needed (heap property satisfied)

After heapify:
Array: [5, 4, 3, 1, | 10]
       ↑ heap  | sorted →
└──5
    ├──4
    │   └──1
    └──3

[... continues for all iterations ...]

╔═══════════════════════════════════════╗
║  SORTING COMPLETE ✓                   ║
╚═══════════════════════════════════════╝

Final sorted array: Array: [1, 3, 4, 5, 10]
```

---

### Time Complexity Analysis

#### Phase 1: Build Max Heap

```
BUILD-MAX-HEAP: O(n)

Proof:
- Process n/2 nodes (non-leaf nodes)
- Height h nodes: at most n/2^(h+1)
- Work per node at height h: O(h)

Total work:
= Σ (n/2^(h+1)) × h  for h = 0 to log(n)
= n × Σ h/2^(h+1)
= n × 2  (geometric series)
= O(n)
```

#### Phase 2: Extract Maximum

```
for i = n-1 down to 1:  ← n-1 iterations
    swap(arr[0], arr[i])  ← O(1)
    maxHeapify(arr, 0, i) ← O(log i)

Total: Σ log(i) for i = 1 to n-1
     = log(1) + log(2) + ... + log(n-1)
     = log(1 × 2 × 3 × ... × (n-1))
     = log((n-1)!)
     ≈ log(n^n)  (Stirling's approximation)
     = n log(n)
     = O(n log n)
```

#### Overall Time Complexity

```
Total = Build Heap + Extract Maximum
      = O(n) + O(n log n)
      = O(n log n)

Best case:    O(n log n)
Average case: O(n log n)
Worst case:   O(n log n)
```

**Heap sort has guaranteed O(n log n) in all cases!**

---

### Space Complexity

#### In-Place Sorting

```
Space used:
- Input array: O(n)
- Recursion stack for heapify: O(log n)
- Few variables: O(1)

Auxiliary space: O(log n)  ← Due to recursion
```

#### Iterative Version (O(1) space)

```cpp name=heapsort_iterative.cpp
void maxHeapifyIterative(vector<int>& arr, int i, int heapSize) {
    while (true) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        
        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest == i) {
            break;  // Heap property satisfied
        }
        
        swap(arr[i], arr[largest]);
        i = largest;  // Continue from child
    }
}
```

**With iterative heapify: O(1) auxiliary space!**

---

### Comparison with Other Sorting Algorithms

| Algorithm | Best | Average | Worst | Space | Stable | In-place |
|-----------|------|---------|-------|-------|--------|----------|
| **Heap Sort** | O(n log n) | O(n log n) | O(n log n) | O(1)* | No | Yes |
| **Quick Sort** | O(n log n) | O(n log n) | O(n²) | O(log n) | No | Yes |
| **Merge Sort** | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | No |
| **Insertion Sort** | O(n) | O(n²) | O(n²) | O(1) | Yes | Yes |
| **Bubble Sort** | O(n) | O(n²) | O(n²) | O(1) | Yes | Yes |

*O(log n) with recursion, O(1) with iteration

---

### Advantages & Disadvantages

#### ✅ Advantages

1. **Guaranteed O(n log n)**: No worst-case degradation (unlike quick sort)
2. **In-place**: O(1) extra space (with iterative heapify)
3. **No recursion needed**: Can be fully iterative
4. **Simple implementation**: Straightforward logic
5. **Good for priority queues**: Natural fit

#### ❌ Disadvantages

1. **Not stable**: Equal elements may be reordered
2. **Not cache-friendly**: Random memory access pattern
3. **Slower than quick sort in practice**: Higher constant factors
4. **Not adaptive**: Doesn't benefit from partially sorted data

---

### Stability Issue Example

```cpp name=stability_example.cpp
#include <iostream>
#include <vector>
using namespace std;

struct Student {
    string name;
    int score;
    
    Student(string n, int s) : name(n), score(s) {}
};

void heapSortStudents(vector<Student>& students) {
    // Build max heap by score
    int n = students.size();
    
    for (int i = n / 2 - 1; i >= 0; i--) {
        // Heapify (simplified, not shown)
    }
    
    // Extract
    for (int i = n - 1; i > 0; i--) {
        swap(students[0], students[i]);
        // Heapify root (not shown)
    }
}

int main() {
    vector<Student> students = {
        Student("Alice", 85),
        Student("Bob", 85),    // Same score as Alice
        Student("Charlie", 90)
    };
    
    cout << "Original order:" << endl;
    for (auto& s : students) {
        cout << s.name << ": " << s.score << endl;
    }
    
    heapSortStudents(students);
    
    cout << "\nAfter heap sort:" << endl;
    for (auto& s : students) {
        cout << s.name << ": " << s.score << endl;
    }
    
    // Alice and Bob might be swapped!
    // Heap sort is NOT stable
    
    return 0;
}
```

**Output (may vary):**
```
Original order:
Alice: 85
Bob: 85
Charlie: 90

After heap sort:
Bob: 85      ← Order changed!
Alice: 85
Charlie: 90
```

---

### Practical Performance Comparison

```cpp name=performance_comparison.cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <chrono>
#include <random>
using namespace std;

void heapSort(vector<int>& arr) {
    // Implementation from earlier
    // ...
}

void measurePerformance() {
    const int SIZE = 100000;
    vector<int> data(SIZE);
    
    // Generate random data
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(1, 1000000);
    
    for (int& x : data) {
        x = dis(gen);
    }
    
    // Test heap sort
    vector<int> arr1 = data;
    auto start1 = chrono::high_resolution_clock::now();
    heapSort(arr1);
    auto end1 = chrono::high_resolution_clock::now();
    auto time1 = chrono::duration_cast<chrono::milliseconds>(end1 - start1).count();
    
    // Test std::sort (intro sort: quick + heap + insertion)
    vector<int> arr2 = data;
    auto start2 = chrono::high_resolution_clock::now();
    sort(arr2.begin(), arr2.end());
    auto end2 = chrono::high_resolution_clock::now();
    auto time2 = chrono::duration_cast<chrono::milliseconds>(end2 - start2).count();
    
    cout << "Sorting " << SIZE << " random integers:" << endl;
    cout << "Heap Sort:  " << time1 << " ms" << endl;
    cout << "std::sort:  " << time2 << " ms" << endl;
    cout << "Ratio:      " << (double)time1 / time2 << "x" << endl;
}

int main() {
    measurePerformance();
    return 0;
}
```

**Typical Output:**
```
Sorting 100000 random integers:
Heap Sort:  45 ms
std::sort:  28 ms
Ratio:      1.6x

(std::sort is typically faster due to better cache locality)
```

---

### When to Use Heap Sort

#### Use Heap Sort When:

1. **Guaranteed O(n log n) required**: No worst-case degradation acceptable
2. **Limited memory**: Need in-place sorting
3. **Embedded systems**: Predictable performance needed
4. **Already have heap structure**: Data naturally in heap
5. **Finding k largest/smallest**: Partial sorting

#### Don't Use When:

1. **Stability required**: Use merge sort instead
2. **Performance critical**: Use quick sort or intro sort
3. **Small arrays**: Use insertion sort
4. **Nearly sorted data**: Use adaptive algorithms

---

### Advanced: Heap Sort Optimizations

#### 1. Bottom-Up Heapify

```cpp
void bottomUpHeapify(vector<int>& arr, int i, int heapSize) {
    int child = 2 * i + 1;
    
    // Find leaf position
    while (child < heapSize) {
        // Choose larger child
        if (child + 1 < heapSize && arr[child + 1] > arr[child]) {
            child++;
        }
        child = 2 * child + 1;
    }
    
    // Bubble up from leaf
    int parent = (child - 1) / 2;
    while (parent >= i && arr[parent] < arr[i]) {
        swap(arr[parent], arr[i]);
        parent = (parent - 1) / 2;
    }
}
```

#### 2. Ternary Heap (3 children per node)

```cpp
// Better cache performance in some cases
int leftChild(int i) { return 3 * i + 1; }
int middleChild(int i) { return 3 * i + 2; }
int rightChild(int i) { return 3 * i + 3; }
```

---

### Complete Working Example with All Features

```cpp name=heapsort_final.cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>
using namespace std;

class HeapSortComplete {
private:
    long long comparisons = 0;
    long long swaps = 0;
    
    void maxHeapify(vector<int>& arr, int i, int heapSize) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        
        if (left < heapSize) {
            comparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }
        
        if (right < heapSize) {
            comparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }
        
        if (largest != i) {
            swaps++;
            swap(arr[i], arr[largest]);
            maxHeapify(arr, largest, heapSize);
        }
    }
    
    void buildMaxHeap(vector<int>& arr) {
        int n = arr.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(arr, i, n);
        }
    }
    
public:
    void sort(vector<int>& arr) {
        comparisons = 0;
        swaps = 0;
        
        int n = arr.size();
        
        buildMaxHeap(arr);
        
        for (int i = n - 1; i > 0; i--) {
            swaps++;
            swap(arr[0], arr[i]);
            maxHeapify(arr, 0, i);
        }
    }
    
    void printStats() {
        cout << "Comparisons: " << comparisons << endl;
        cout << "Swaps: " << swaps << endl;
    }
    
    bool isSorted(const vector<int>& arr) {
        for (int i = 1; i < arr.size(); i++) {
            if (arr[i] < arr[i-1]) return false;
        }
        return true;
    }
};

int main() {
    HeapSortComplete hs;
    
    // Test with different data patterns
    cout << "=== Test 1: Random Data ===" << endl;
    vector<int> arr1 = {64, 34, 25, 12, 22, 11, 90};
    cout << "Before: "; for (int x : arr1) cout << x << " "; cout << endl;
    hs.sort(arr1);
    cout << "After:  "; for (int x : arr1) cout << x << " "; cout << endl;
    hs.printStats();
    cout << "Sorted correctly: " << (hs.isSorted(arr1) ? "YES ✓" : "NO ✗") << endl;
    
    cout << "\n=== Test 2: Already Sorted ===" << endl;
    vector<int> arr2 = {1, 2, 3, 4, 5, 6, 7};
    hs.sort(arr2);
    hs.printStats();
    
    cout << "\n=== Test 3: Reverse Sorted ===" << endl;
    vector<int> arr3 = {7, 6, 5, 4, 3, 2, 1};
    hs.sort(arr3);
    hs.printStats();
    
    return 0;
}
```

---

### Key Takeaways

1. **Two phases**: Build heap O(n), then extract O(n log n)
2. **Guaranteed performance**: O(n log n) in all cases
3. **In-place**: O(1) auxiliary space (iterative version)
4. **Not stable**: Equal elements may be reordered
5. **Simple**: Straightforward implementation
6. **Practical**: Used in systems where predictability matters

## Priority Queue Using Heap

```cpp name=priority_queue_heap.cpp
#include <iostream>
#include <vector>
#include <string>
using namespace std;

struct Task {
    string name;
    int priority;  // Higher number = higher priority

    Task(string n, int p) : name(n), priority(p) {}
};

class PriorityQueue {
private:
    vector<Task> heap;

    int parent(int i) { return (i - 1) / 2; }
    int leftChild(int i) { return 2 * i + 1; }
    int rightChild(int i) { return 2 * i + 2; }

    void heapifyUp(int index) {
        while (index > 0 && heap[parent(index)].priority < heap[index].priority) {
            swap(heap[index], heap[parent(index)]);
            index = parent(index);
        }
    }

    void heapifyDown(int index) {
        int size = heap.size();
        int highest = index;
        int left = leftChild(index);
        int right = rightChild(index);

        if (left < size && heap[left].priority > heap[highest].priority) {
            highest = left;
        }

        if (right < size && heap[right].priority > heap[highest].priority) {
            highest = right;
        }

        if (highest != index) {
            swap(heap[index], heap[highest]);
            heapifyDown(highest);
        }
    }

public:
    void enqueue(const string& name, int priority) {
        Task task(name, priority);
        heap.push_back(task);
        heapifyUp(heap.size() - 1);
        
        cout << "Added: \"" << name << "\" with priority " << priority << endl;
    }

    Task dequeue() {
        if (isEmpty()) {
            throw runtime_error("Priority queue is empty!");
        }

        Task highestPriority = heap[0];
        heap[0] = heap.back();
        heap.pop_back();

        if (!isEmpty()) {
            heapifyDown(0);
        }

        return highestPriority;
    }

    Task peek() const {
        if (isEmpty()) {
            throw runtime_error("Priority queue is empty!");
        }
        return heap[0];
    }

    bool isEmpty() const {
        return heap.empty();
    }

    int size() const {
        return heap.size();
    }

    void display() const {
        if (isEmpty()) {
            cout << "Priority queue is empty" << endl;
            return;
        }

        cout << "\nCurrent tasks (by priority):" << endl;
        for (int i = 0; i < heap.size(); i++) {
            cout << "  " << (i + 1) << ". \"" << heap[i].name 
                 << "\" (priority: " << heap[i].priority << ")" << endl;
        }
    }
};

int main() {
    cout << "=== Priority Queue (Task Scheduler) ===" << endl;

    PriorityQueue pq;

    // Add tasks with different priorities
    cout << "\n--- Adding tasks ---" << endl;
    pq.enqueue("Write documentation", 3);
    pq.enqueue("Fix critical bug", 10);
    pq.enqueue("Code review", 5);
    pq.enqueue("Team meeting", 2);
    pq.enqueue("Deploy to production", 9);
    pq.enqueue("Update dependencies", 4);

    pq.display();

    // Process tasks by priority
    cout << "\n--- Processing tasks by priority ---" << endl;
    while (!pq.isEmpty()) {
        Task task = pq.dequeue();
        cout << "Processing: \"" << task.name 
             << "\" (priority: " << task.priority << ")" << endl;
    }

    return 0;
}
```

## Using STL Priority Queue

```cpp name=stl_priority_queue.cpp
#include <iostream>
#include <queue>
#include <vector>
#include <string>
using namespace std;

int main() {
    cout << "=== STL Priority Queue ===" << endl;

    // Max heap (default)
    cout << "\n--- Max Heap (default) ---" << endl;
    priority_queue<int> maxHeap;

    maxHeap.push(30);
    maxHeap.push(10);
    maxHeap.push(50);
    maxHeap.push(20);

    cout << "Elements (max to min): ";
    while (!maxHeap.empty()) {
        cout << maxHeap.top() << " ";
        maxHeap.pop();
    }
    cout << endl;

    // Min heap
    cout << "\n--- Min Heap ---" << endl;
    priority_queue<int, vector<int>, greater<int>> minHeap;

    minHeap.push(30);
    minHeap.push(10);
    minHeap.push(50);
    minHeap.push(20);

    cout << "Elements (min to max): ";
    while (!minHeap.empty()) {
        cout << minHeap.top() << " ";
        minHeap.pop();
    }
    cout << endl;

    // Custom comparator
    cout << "\n--- Custom Priority Queue ---" << endl;

    struct Task {
        string name;
        int priority;

        Task(string n, int p) : name(n), priority(p) {}

        // For max heap based on priority
        bool operator<(const Task& other) const {
            return priority < other.priority;
        }
    };

    priority_queue<Task> taskQueue;
    taskQueue.push(Task("Low priority", 1));
    taskQueue.push(Task("High priority", 5));
    taskQueue.push(Task("Medium priority", 3));

    cout << "Tasks by priority:" << endl;
    while (!taskQueue.empty()) {
        Task t = taskQueue.top();
        taskQueue.pop();
        cout << "  " << t.name << " (priority: " << t.priority << ")" << endl;
    }

    return 0;
}
```

## Common Heap Problems

### 1. Find K Largest Elements

```cpp name=k_largest_elements.cpp
#include <iostream>
#include <vector>
#include <queue>
using namespace std;

vector<int> findKLargest(const vector<int>& arr, int k) {
    // Use min heap of size k
    priority_queue<int, vector<int>, greater<int>> minHeap;

    for (int num : arr) {
        minHeap.push(num);

        // Keep only k largest elements
        if (minHeap.size() > k) {
            minHeap.pop();
        }
    }

    // Extract elements
    vector<int> result;
    while (!minHeap.empty()) {
        result.push_back(minHeap.top());
        minHeap.pop();
    }

    return result;
}

int main() {
    cout << "=== Find K Largest Elements ===" << endl;

    vector<int> arr = {7, 10, 4, 3, 20, 15};
    int k = 3;

    cout << "Array: ";
    for (int x : arr) cout << x << " ";
    cout << endl;

    vector<int> result = findKLargest(arr, k);

    cout << k << " largest elements: ";
    for (int x : result) cout << x << " ";
    cout << endl;

    return 0;
}
```

### 2. Merge K Sorted Arrays

```cpp name=merge_k_sorted_arrays.cpp
#include <iostream>
#include <vector>
#include <queue>
using namespace std;

struct Element {
    int value;
    int arrayIndex;
    int elementIndex;

    Element(int v, int ai, int ei) 
        : value(v), arrayIndex(ai), elementIndex(ei) {}

    // Min heap comparison
    bool operator>(const Element& other) const {
        return value > other.value;
    }
};

vector<int> mergeKArrays(const vector<vector<int>>& arrays) {
    priority_queue<Element, vector<Element>, greater<Element>> minHeap;
    vector<int> result;

    // Add first element from each array
    for (int i = 0; i < arrays.size(); i++) {
        if (!arrays[i].empty()) {
            minHeap.push(Element(arrays[i][0], i, 0));
        }
    }

    // Extract min and add next element from same array
    while (!minHeap.empty()) {
        Element current = minHeap.top();
        minHeap.pop();

        result.push_back(current.value);

        // Add next element from same array
        int nextIndex = current.elementIndex + 1;
        if (nextIndex < arrays[current.arrayIndex].size()) {
            minHeap.push(Element(
                arrays[current.arrayIndex][nextIndex],
                current.arrayIndex,
                nextIndex
            ));
        }
    }

    return result;
}

int main() {
    cout << "=== Merge K Sorted Arrays ===" << endl;

    vector<vector<int>> arrays = {
        {1, 4, 7},
        {2, 5, 8},
        {3, 6, 9}
    };

    cout << "Input arrays:" << endl;
    for (int i = 0; i < arrays.size(); i++) {
        cout << "  Array " << (i + 1) << ": ";
        for (int x : arrays[i]) cout << x << " ";
        cout << endl;
    }

    vector<int> merged = mergeKArrays(arrays);

    cout << "\nMerged array: ";
    for (int x : merged) cout << x << " ";
    cout << endl;

    return 0;
}
```

### 3. Running Median

```cpp name=running_median.cpp
#include <iostream>
#include <queue>
#include <vector>
#include <iomanip>
using namespace std;

class MedianFinder {
private:
    priority_queue<int> maxHeap;  // Left half (smaller elements)
    priority_queue<int, vector<int>, greater<int>> minHeap;  // Right half (larger elements)

public:
    void addNum(int num) {
        // Add to max heap first
        maxHeap.push(num);

        // Balance: move largest from max heap to min heap
        minHeap.push(maxHeap.top());
        maxHeap.pop();

        // Balance sizes
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.push(minHeap.top());
            minHeap.pop();
        }
    }

    double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.top();
        }
        return (maxHeap.top() + minHeap.top()) / 2.0;
    }
};

int main() {
    cout << "=== Running Median ===" << endl;

    MedianFinder mf;
    vector<int> stream = {5, 15, 1, 3, 8, 7, 9, 2};

    cout << "\nData stream: ";
    for (int num : stream) {
        mf.addNum(num);
        cout << "\nAdded " << num << " → Median: " << fixed << setprecision(1) 
             << mf.findMedian();
    }
    cout << endl;

    return 0;
}
```

### 4. Top K Frequent Elements

```cpp name=top_k_frequent.cpp
#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
using namespace std;

vector<int> topKFrequent(const vector<int>& nums, int k) {
    // Count frequencies
    unordered_map<int, int> freq;
    for (int num : nums) {
        freq[num]++;
    }

    // Min heap of size k
    auto comp = [](pair<int, int> a, pair<int, int> b) {
        return a.second > b.second;  // Compare by frequency
    };
    priority_queue<pair<int, int>, vector<pair<int, int>>, decltype(comp)> minHeap(comp);

    for (auto& p : freq) {
        minHeap.push(p);
        if (minHeap.size() > k) {
            minHeap.pop();
        }
    }

    // Extract results
    vector<int> result;
    while (!minHeap.empty()) {
        result.push_back(minHeap.top().first);
        minHeap.pop();
    }

    return result;
}

int main() {
    cout << "=== Top K Frequent Elements ===" << endl;

    vector<int> nums = {1, 1, 1, 2, 2, 3, 3, 3, 3, 4};
    int k = 2;

    cout << "Array: ";
    for (int x : nums) cout << x << " ";
    cout << endl;

    vector<int> result = topKFrequent(nums, k);

    cout << "Top " << k << " frequent elements: ";
    for (int x : result) cout << x << " ";
    cout << endl;

    return 0;
}
```

## Time Complexity Analysis

| Operation | Time Complexity | Explanation |
|-----------|----------------|-------------|
| **Insert** | O(log n) | Heapify up from leaf to root |
| **Extract Max/Min** | O(log n) | Heapify down from root |
| **Get Max/Min** | O(1) | Just return root |
| **Delete arbitrary** | O(log n) | Find + heapify |
| **Build heap** | O(n) | Bottom-up heapification |
| **Heap sort** | O(n log n) | n extractions |
| **Search** | O(n) | Not a search structure |

## Space Complexity

- **Array-based heap**: O(n) for n elements
- **No extra pointers needed**: Unlike BST
- **Recursion stack**: O(log n) for heapify

## Heap vs Other Data Structures

| Structure | Insert | Delete | Find Min/Max | Search | Sorted Output |
|-----------|--------|--------|--------------|--------|---------------|
| **Heap** | O(log n) | O(log n) | O(1) | O(n) | O(n log n) |
| **BST** | O(log n)* | O(log n)* | O(log n)* | O(log n)* | O(n) |
| **Array (unsorted)** | O(1) | O(n) | O(n) | O(n) | O(n log n) |
| **Array (sorted)** | O(n) | O(n) | O(1) | O(log n) | O(n) |

*Average case for balanced BST

## Heap vs BST

```
Heap:                           BST:
- Complete binary tree          - Not necessarily complete
- Partial ordering              - Total ordering (inorder = sorted)
- Fast min/max access           - Fast search
- Used for priority queues      - Used for searching
- O(1) get min/max              - O(log n) get min/max
- O(n) search                   - O(log n) search

Heap:        90                 BST:         50
            /  \                            /  \
           80  70                          30  70
          / \  / \                        / \  / \
         40 50 60 65                     20 40 60 80
         
Parent ≥ Children               Left < Parent < Right
```

## Advantages & Disadvantages

### ✅ Advantages
- **Fast priority operations**: O(1) get min/max, O(log n) insert/delete
- **Space efficient**: Array-based, no pointers
- **Complete tree**: Always balanced, guaranteed O(log n) height
- **Cache-friendly**: Array representation
- **Build heap in O(n)**: Faster than n insertions

### ❌ Disadvantages
- **No fast search**: O(n) to find arbitrary element
- **No ordering traversal**: Can't traverse in sorted order efficiently
- **Fixed ordering**: Either min or max heap, not both
- **Delete arbitrary slow**: Need to find element first

## When to Use Heap

✅ **Use when:**
- Implementing priority queues
- Need fast access to min/max element
- Finding k largest/smallest elements
- Heap sort
- Median maintenance
- Scheduling tasks by priority
- Merging sorted lists/arrays

❌ **Don't use when:**
- Need fast search for arbitrary elements → Use **Hash Table** or **BST**
- Need sorted traversal → Use **BST**
- Need both min and max → Use two heaps or **BST**
- Need to access arbitrary elements → Use **Array**

## Key Takeaways

1. **Heap Property**: Parent-child relationship (max or min)
2. **Complete Binary Tree**: All levels filled except last
3. **Array Implementation**: Efficient, no pointers needed
4. **O(log n) Operations**: Insert and extract
5. **O(1) Get Min/Max**: Root element
6. **Build Heap O(n)**: Bottom-up heapification
7. **Common Uses**:
   - Priority queues
   - Heap sort
   - Finding k largest/smallest
   - Scheduling algorithms
   - Graph algorithms (Dijkstra, Prim)

8. **Types**:
   - **Binary Heap**: Most common (max/min)
   - **Binomial Heap**: Better for union operations
   - **Fibonacci Heap**: Best amortized complexity
   - **d-ary Heap**: More than 2 children



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

# [[HASHING]]
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

# The Need to Break the `O(n log n)` Barrier

When we use comparison-based sorting algorithms (Merge Sort, Quick Sort), we compare two elements at a time. Mathematically, the lower bound for this is $O(n \log n)$. But, what if we **don't compare elements directly**?

### Example: Sorting Birthdates

You have 1000 people with birthdates in format `(Day, Month, Year)`:

- Person A: 15-03-1995
    
- Person B: 22-03-1995
    

**Traditional approach**: Compare dates directly $\rightarrow O(n \log n)$ **Linear approach**: Exploit the **structure** of the data:

- Days: 1-31 (31 possible values)
    
- Months: 1-12 (12 possible values)
    
- Years: limited range (e.g., 1900-2025)
    

**Key Idea**: When the range of values $k$ is small compared to $n$, we can sort in $O(n + k)$ time!

## Stability in Sorting (Learning to Stand)

To successfully sort complex data (like birthdates) in linear time, we must understand **Stability**.

### What is Stability?

A sorting algorithm is **stable** if two objects with equal keys appear in the same relative order in the sorted output as they appeared in the original input.

**Example:** Imagine sorting a list of items where the "key" is a number, and they have an ID attached: `Input: {2, 'A'}, {1, 'B'}, {2, 'C'}` Both 'A' and 'C' have the key `2`. Notice that 'A' comes _before_ 'C' in the input.

- **Stable Sort Output:** `{1, 'B'}, {2, 'A'}, {2, 'C'}` ('A' is still before 'C').
    
- **Unstable Sort Output:** `{1, 'B'}, {2, 'C'}, {2, 'A'}` (The relative order of equals was scrambled).
    

### Why and Where is it Needed?

Stability is strictly required when you are doing **multi-pass sorting** based on composite keys (like sorting by Day, then Month, then Year). If you sort by Day, and then sort by Month, the Month sort **must be stable**. If it isn't stable, all the hard work you did sorting the Days will be scrambled for people born in the same month!

### How does it connect to Linear Sorting?

**Radix Sort** (which sorts numbers digit-by-digit) relies entirely on a linear sorting subroutine to sort each individual digit. Because Radix sort does this in multiple passes (from least significant digit to most significant digit), **the linear sorting subroutine must be stable**, otherwise, the algorithm falls apart.

# Counting Sort (a stable counting algorithm)

**Counting Sort** is a non-comparison-based sorting algorithm that works by counting the number of occurrences of each distinct element in the input array. It then uses this count information to place elements directly into their correct positions in the sorted output array.

## Key Characteristics

- **Time Complexity**: O(n + k), where n is the number of elements and k is the range of input values
- **Space Complexity**: O(k) for the counting array
- **Stable**: Yes (maintains relative order of equal elements)
- **Not In-place**: Requires additional space
- **Best for**: Small range of integers

## When to Use Counting Sort

✅ **Good for:**
- Sorting integers with a small range (e.g., 0-100)
- When range (k) is not significantly larger than n
- When stability is required

❌ **Not suitable for:**
- Large range of values (wastes memory)
- Floating-point numbers
- Non-numeric data (without modification)

## How It Works

1. **Find the range**: Determine minimum and maximum values
2. **Count occurrences**: Create a count array and store frequency of each element
3. **Cumulative count**: Modify count array to store cumulative counts
4. **Build output**: Place elements in sorted order using count array

## Pseudocode

```text name=counting_sort_pseudocode.txt
COUNTING-SORT(A, n, k)
    // A: input array, n: size, k: range (max value)
    
    // Step 1: Initialize count array
    Create count array C[0...k] initialized to 0
    Create output array B[0...n-1]
    
    // Step 2: Count occurrences of each element
    for i = 0 to n-1 do
        C[A[i]] = C[A[i]] + 1
    
    // Step 3: Calculate cumulative counts
    for i = 1 to k do
        C[i] = C[i] + C[i-1]
    
    // Step 4: Build output array (traverse backwards for stability)
    for i = n-1 down to 0 do
        B[C[A[i]] - 1] = A[i]
        C[A[i]] = C[A[i]] - 1
    
    // Step 5: Copy back to original array
    for i = 0 to n-1 do
        A[i] = B[i]
    
    return A
```

## C++ Implementation

```cpp name=counting_sort.cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

// Basic Counting Sort (for non-negative integers)
void countingSort(vector<int>& arr) {
    int n = arr.size();
    if (n == 0) return;
    
    // Step 1: Find the maximum element to determine range
    int maxElement = *max_element(arr.begin(), arr.end());
    
    // Step 2: Create count array and initialize to 0
    vector<int> count(maxElement + 1, 0);
    
    // Step 3: Store count of each element
    for (int i = 0; i < n; i++) {
        count[arr[i]]++;
    }
    
    // Step 4: Calculate cumulative count
    for (int i = 1; i <= maxElement; i++) {
        count[i] += count[i - 1];
    }
    
    // Step 5: Build the output array (stable sort)
    vector<int> output(n);
    for (int i = n - 1; i >= 0; i--) {
        output[count[arr[i]] - 1] = arr[i];
        count[arr[i]]--;
    }
    
    // Step 6: Copy the output array back to original array
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// Counting Sort for arrays with negative numbers
void countingSortWithNegatives(vector<int>& arr) {
    int n = arr.size();
    if (n == 0) return;
    
    // Find minimum and maximum elements
    int minElement = *min_element(arr.begin(), arr.end());
    int maxElement = *max_element(arr.begin(), arr.end());
    
    int range = maxElement - minElement + 1;
    
    // Create count array
    vector<int> count(range, 0);
    vector<int> output(n);
    
    // Store count of each element (shifted by minElement)
    for (int i = 0; i < n; i++) {
        count[arr[i] - minElement]++;
    }
    
    // Calculate cumulative count
    for (int i = 1; i < range; i++) {
        count[i] += count[i - 1];
    }
    
    // Build output array
    for (int i = n - 1; i >= 0; i--) {
        output[count[arr[i] - minElement] - 1] = arr[i];
        count[arr[i] - minElement]--;
    }
    
    // Copy back to original array
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// Simple counting sort (not stable, but space efficient for display)
void simpleCountingSort(vector<int>& arr) {
    int n = arr.size();
    if (n == 0) return;
    
    int maxElement = *max_element(arr.begin(), arr.end());
    vector<int> count(maxElement + 1, 0);
    
    // Count occurrences
    for (int i = 0; i < n; i++) {
        count[arr[i]]++;
    }
    
    // Reconstruct array
    int index = 0;
    for (int i = 0; i <= maxElement; i++) {
        while (count[i] > 0) {
            arr[index++] = i;
            count[i]--;
        }
    }
}

// Utility function to print array
void printArray(const vector<int>& arr) {
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
}

// Driver code
int main() {
    // Example 1: Basic counting sort
    cout << "Example 1: Basic Counting Sort (non-negative integers)" << endl;
    vector<int> arr1 = {4, 2, 2, 8, 3, 3, 1};
    cout << "Original array: ";
    printArray(arr1);
    
    countingSort(arr1);
    cout << "Sorted array:   ";
    printArray(arr1);
    
    // Example 2: Array with negative numbers
    cout << "\nExample 2: Counting Sort with Negative Numbers" << endl;
    vector<int> arr2 = {-5, -10, 0, -3, 8, 5, -1, 10};
    cout << "Original array: ";
    printArray(arr2);
    
    countingSortWithNegatives(arr2);
    cout << "Sorted array:   ";
    printArray(arr2);
    
    // Example 3: Array with duplicates
    cout << "\nExample 3: Array with Duplicates" << endl;
    vector<int> arr3 = {6, 0, 3, 6, 1, 3, 2, 6, 0};
    cout << "Original array: ";
    printArray(arr3);
    
    simpleCountingSort(arr3);
    cout << "Sorted array:   ";
    printArray(arr3);
    
    return 0;
}
```

## How is it stable?

Let's sort an array of objects where we care about stability. I'll use pairs `(value, original_index)` to track where elements came from:

**Input Array:**

Code

```
[4₀, 2₁, 2₂, 8₃, 3₄, 3₅, 1₆]
```

The subscripts represent the original positions.

### Step-by-Step Execution

#### Step 1: Count Occurrences

Code

```
count[1] = 1  (one 1)
count[2] = 2  (two 2's)
count[3] = 2  (two 3's)
count[4] = 1  (one 4)
count[8] = 1  (one 8)
```

#### Step 2: Cumulative Count

This tells us: "How many elements are ≤ this value?"

Code

```
count[1] = 1  → "1 element ≤ 1"
count[2] = 3  → "3 elements ≤ 2"
count[3] = 5  → "5 elements ≤ 3"
count[4] = 6  → "6 elements ≤ 4"
count[8] = 7  → "7 elements ≤ 8"
```

#### Step 3: Build Output (RIGHT TO LEFT - This is crucial!)

Processing **backwards** from index 6 to 0:

Code

```
i=6: Process 1₆
  count[1] = 1, so place at output[0]
  Decrement count[1] to 0
  Output: [1₆, _, _, _, _, _, _]

i=5: Process 3₅
  count[3] = 5, so place at output[4]
  Decrement count[3] to 4
  Output: [1₆, _, _, _, 3₅, _, _]

i=4: Process 3₄
  count[3] = 4, so place at output[3]
  Decrement count[3] to 3
  Output: [1₆, _, _, 3₄, 3₅, _, _]  ← Notice 3₄ comes before 3₅!

i=3: Process 8₃
  count[8] = 7, so place at output[6]
  Decrement count[8] to 6
  Output: [1₆, _, _, 3₄, 3₅, _, 8₃]

i=2: Process 2₂
  count[2] = 3, so place at output[2]
  Decrement count[2] to 2
  Output: [1₆, _, 2₂, 3₄, 3₅, _, 8₃]

i=1: Process 2₁
  count[2] = 2, so place at output[1]
  Decrement count[2] to 1
  Output: [1₆, 2₁, 2₂, 3₄, 3₅, _, 8₃]  ← Notice 2₁ comes before 2₂!

i=0: Process 4₀
  count[4] = 6, so place at output[5]
  Decrement count[4] to 5
  Output: [1₆, 2₁, 2₂, 3₄, 3₅, 4₀, 8₃]
```

**Final Result:** `[1₆, 2₁, 2₂, 3₄, 3₅, 4₀, 8₃]`

### ✅ Stability Verified!

Look at the equal elements:

- **2₁ appears before 2₂** (same order as input)
- **3₄ appears before 3₅** (same order as input)

## What Happens If We Go Forward (Left to Right)?

Let's see what happens if we process from left to right:

Code

```
i=0: Process 4₀
  count[4] = 6, place at output[5]
  Output: [_, _, _, _, _, 4₀, _]

i=1: Process 2₁
  count[2] = 3, place at output[2]
  Output: [_, _, 2₁, _, _, 4₀, _]

i=2: Process 2₂
  count[2] = 2 (after decrement), place at output[1]
  Output: [_, 2₂, 2₁, _, _, 4₀, _]  ← 2₂ before 2₁? UNSTABLE!
```

**Result would be:** `[1₆, 2₂, 2₁, 3₅, 3₄, 4₀, 8₃]` ❌

The order of equal elements is **reversed** - this is **unstable**!
## Advantages & Disadvantages

### ✅ Advantages
- **Fast**: Linear time complexity O(n + k)
- **Stable**: Maintains relative order
- **Predictable**: Performance doesn't depend on input distribution

### ❌ Disadvantages
- **Space**: Requires O(k) extra space
- **Limited use**: Only works well with small integer ranges
- **Not adaptive**: Doesn't benefit from partially sorted data

## Comparison with Other Sorts

| Algorithm | Time Complexity | Space | Stable |
|-----------|----------------|-------|--------|
| Counting Sort | O(n + k) | O(k) | Yes |
| Quick Sort | O(n log n) | O(log n) | No |
| Merge Sort | O(n log n) | O(n) | Yes |
| Heap Sort | O(n log n) | O(1) | No |

# Radix Sort

# Radix Sort

**Radix Sort** is a non-comparison-based sorting algorithm that sorts numbers by processing individual digits. It sorts numbers digit by digit, starting from the least significant digit (LSD) to the most significant digit (MSD), or vice versa. It uses a stable sorting algorithm (usually counting sort) as a subroutine.

## Key Characteristics

- **Time Complexity**: O(d × (n + k)), where d = number of digits, n = number of elements, k = range of digits (usually 10)
- **Space Complexity**: O(n + k)
- **Stable**: Yes (depends on stable subroutine)
- **Not In-place**: Requires additional space
- **Best for**: Sorting integers or strings with fixed-length keys

## How It Works

Radix sort processes numbers digit by digit using a stable sorting algorithm:

1. **Find the maximum number** to determine the number of digits
2. **Process each digit position** from least significant to most significant
3. **Use stable sort** (counting sort) for each digit position
4. **Repeat** until all digits are processed

### Why Use Counting Sort as Subroutine?

- **Stability is essential**: We need to preserve the order from previous digit sorts
- **Small range**: Digits are 0-9 (or 0-1 for binary), perfect for counting sort
- **Efficiency**: O(n + k) where k=10 is constant

## Visual Example

Let's sort: `[170, 45, 75, 90, 802, 24, 2, 66]`

```
Original: [170, 45, 75, 90, 802, 24, 2, 66]

Pass 1 (Units place - rightmost digit):
Digits:    [0,   5,  5,  0,   2,   4,  2,  6]
Sorted:    [170, 90, 802, 2, 24, 45, 75, 66]

Pass 2 (Tens place):
Digits:    [7,   9,  0,   0,  2,  4,  7,  6]
Sorted:    [802, 2, 24, 45, 66, 170, 75, 90]

Pass 3 (Hundreds place):
Digits:    [8,   0,  0,  0,  0,  1,   0,  0]
Sorted:    [2, 24, 45, 66, 75, 90, 170, 802]

Final:     [2, 24, 45, 66, 75, 90, 170, 802] ✓
```

## Pseudocode

```text name=radix_sort_pseudocode.txt
RADIX-SORT(A, n)
    // A: input array, n: size
    
    // Step 1: Find maximum number to determine number of digits
    max = FIND-MAX(A, n)
    
    // Step 2: Process each digit position (exp = 1, 10, 100, ...)
    exp = 1
    while max / exp > 0 do
        COUNTING-SORT-BY-DIGIT(A, n, exp)
        exp = exp * 10
    
    return A

COUNTING-SORT-BY-DIGIT(A, n, exp)
    // Sort array based on digit at position exp (1, 10, 100, ...)
    
    Create output array B[0...n-1]
    Create count array C[0...9] initialized to 0
    
    // Count occurrences of digits
    for i = 0 to n-1 do
        digit = (A[i] / exp) % 10
        C[digit] = C[digit] + 1
    
    // Calculate cumulative count
    for i = 1 to 9 do
        C[i] = C[i] + C[i-1]
    
    // Build output array (BACKWARD for stability)
    for i = n-1 down to 0 do
        digit = (A[i] / exp) % 10
        B[C[digit] - 1] = A[i]
        C[digit] = C[digit] - 1
    
    // Copy back to original array
    for i = 0 to n-1 do
        A[i] = B[i]
```

## C++ Implementation

```cpp name=radix_sort.cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;

// Counting sort based on specific digit (exp = 1, 10, 100, ...)
void countingSortByDigit(vector<int>& arr, int exp) {
    int n = arr.size();
    vector<int> output(n);
    vector<int> count(10, 0);  // Digits 0-9
    
    // Count occurrences of digits at current position
    for (int i = 0; i < n; i++) {
        int digit = (arr[i] / exp) % 10;
        count[digit]++;
    }
    
    // Calculate cumulative count
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }
    
    // Build output array (backward traversal for stability)
    for (int i = n - 1; i >= 0; i--) {
        int digit = (arr[i] / exp) % 10;
        output[count[digit] - 1] = arr[i];
        count[digit]--;
    }
    
    // Copy output back to original array
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// Radix Sort (LSD - Least Significant Digit first)
void radixSort(vector<int>& arr) {
    if (arr.empty()) return;
    
    // Find maximum number to determine number of digits
    int maxNum = *max_element(arr.begin(), arr.end());
    
    // Process each digit position (1, 10, 100, 1000, ...)
    for (int exp = 1; maxNum / exp > 0; exp *= 10) {
        countingSortByDigit(arr, exp);
    }
}

// Radix Sort for negative numbers
void radixSortWithNegatives(vector<int>& arr) {
    if (arr.empty()) return;
    
    // Separate positive and negative numbers
    vector<int> positive, negative;
    for (int num : arr) {
        if (num >= 0) {
            positive.push_back(num);
        } else {
            negative.push_back(-num);  // Store absolute value
        }
    }
    
    // Sort both arrays
    radixSort(positive);
    radixSort(negative);
    
    // Combine: reversed negatives + positives
    arr.clear();
    for (int i = negative.size() - 1; i >= 0; i--) {
        arr.push_back(-negative[i]);  // Restore negative sign
    }
    for (int num : positive) {
        arr.push_back(num);
    }
}

// Radix Sort using different base (e.g., base 256 for optimization)
void radixSortBase256(vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxNum = *max_element(arr.begin(), arr.end());
    int n = arr.size();
    
    // Process each byte (8 bits at a time)
    for (int exp = 1; maxNum / exp > 0; exp *= 256) {
        vector<int> output(n);
        vector<int> count(256, 0);
        
        // Count occurrences
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 256;
            count[digit]++;
        }
        
        // Cumulative count
        for (int i = 1; i < 256; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 256;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        arr = output;
    }
}

// MSD Radix Sort (Most Significant Digit first) - Recursive
void msdRadixSort(vector<int>& arr, int left, int right, int exp) {
    if (left >= right || exp < 1) return;
    
    vector<vector<int>> buckets(10);
    
    // Distribute elements into buckets based on current digit
    for (int i = left; i <= right; i++) {
        int digit = (arr[i] / exp) % 10;
        buckets[digit].push_back(arr[i]);
    }
    
    // Copy back and recursively sort each bucket
    int index = left;
    for (int digit = 0; digit < 10; digit++) {
        int bucketStart = index;
        for (int num : buckets[digit]) {
            arr[index++] = num;
        }
        int bucketEnd = index - 1;
        
        // Recursively sort this bucket with next digit
        if (bucketStart < bucketEnd) {
            msdRadixSort(arr, bucketStart, bucketEnd, exp / 10);
        }
    }
}

void msdRadixSort(vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxNum = *max_element(arr.begin(), arr.end());
    
    // Find the highest power of 10
    int exp = 1;
    while (maxNum / exp >= 10) {
        exp *= 10;
    }
    
    msdRadixSort(arr, 0, arr.size() - 1, exp);
}

// Utility function to print array
void printArray(const vector<int>& arr, const string& label = "") {
    if (!label.empty()) cout << label << ": ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
}

// Function to demonstrate each pass of radix sort
void radixSortWithVisualization(vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxNum = *max_element(arr.begin(), arr.end());
    int passNum = 1;
    
    cout << "Initial array: ";
    printArray(arr);
    cout << endl;
    
    for (int exp = 1; maxNum / exp > 0; exp *= 10) {
        cout << "Pass " << passNum++ << " (sorting by ";
        if (exp == 1) cout << "ones";
        else if (exp == 10) cout << "tens";
        else if (exp == 100) cout << "hundreds";
        else if (exp == 1000) cout << "thousands";
        else cout << exp << "'s";
        cout << " place):" << endl;
        
        // Show the digit being sorted
        cout << "  Digits: ";
        for (int num : arr) {
            cout << (num / exp) % 10 << " ";
        }
        cout << endl;
        
        countingSortByDigit(arr, exp);
        cout << "  Result: ";
        printArray(arr);
        cout << endl;
    }
}

int main() {
    // Example 1: Basic Radix Sort
    cout << "=== Example 1: Basic Radix Sort ===" << endl;
    vector<int> arr1 = {170, 45, 75, 90, 802, 24, 2, 66};
    cout << "Original: ";
    printArray(arr1);
    radixSort(arr1);
    cout << "Sorted:   ";
    printArray(arr1);
    
    // Example 2: With Visualization
    cout << "\n=== Example 2: Radix Sort with Visualization ===" << endl;
    vector<int> arr2 = {170, 45, 75, 90, 802, 24, 2, 66};
    radixSortWithVisualization(arr2);
    
    // Example 3: Large numbers
    cout << "\n=== Example 3: Large Numbers ===" << endl;
    vector<int> arr3 = {12345, 678, 9012, 3456, 789, 123, 45, 6789};
    cout << "Original: ";
    printArray(arr3);
    radixSort(arr3);
    cout << "Sorted:   ";
    printArray(arr3);
    
    // Example 4: With negative numbers
    cout << "\n=== Example 4: Negative Numbers ===" << endl;
    vector<int> arr4 = {170, -45, 75, -90, 802, 24, -2, 66};
    cout << "Original: ";
    printArray(arr4);
    radixSortWithNegatives(arr4);
    cout << "Sorted:   ";
    printArray(arr4);
    
    // Example 5: MSD Radix Sort
    cout << "\n=== Example 5: MSD Radix Sort ===" << endl;
    vector<int> arr5 = {170, 45, 75, 90, 802, 24, 2, 66};
    cout << "Original: ";
    printArray(arr5);
    msdRadixSort(arr5);
    cout << "Sorted:   ";
    printArray(arr5);
    
    // Example 6: Already sorted
    cout << "\n=== Example 6: Already Sorted ===" << endl;
    vector<int> arr6 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    cout << "Original: ";
    printArray(arr6);
    radixSort(arr6);
    cout << "Sorted:   ";
    printArray(arr6);
    
    // Example 7: All same numbers
    cout << "\n=== Example 7: All Same Numbers ===" << endl;
    vector<int> arr7 = {555, 555, 555, 555};
    cout << "Original: ";
    printArray(arr7);
    radixSort(arr7);
    cout << "Sorted:   ";
    printArray(arr7);
    
    return 0;
}
```

## String Radix Sort

Radix sort can also be used for strings:

```cpp name=radix_sort_strings.cpp
#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

// Counting sort for strings based on character at position 'pos'
void countingSortByChar(vector<string>& arr, int pos, int maxLen) {
    int n = arr.size();
    vector<string> output(n);
    vector<int> count(256, 0);  // ASCII characters
    
    // Count occurrences
    for (int i = 0; i < n; i++) {
        int charIndex = (pos < arr[i].length()) ? arr[i][pos] : 0;
        count[charIndex]++;
    }
    
    // Cumulative count
    for (int i = 1; i < 256; i++) {
        count[i] += count[i - 1];
    }
    
    // Build output (backward for stability)
    for (int i = n - 1; i >= 0; i--) {
        int charIndex = (pos < arr[i].length()) ? arr[i][pos] : 0;
        output[count[charIndex] - 1] = arr[i];
        count[charIndex]--;
    }
    
    arr = output;
}

// Radix sort for strings (LSD)
void radixSortStrings(vector<string>& arr) {
    if (arr.empty()) return;
    
    // Find maximum length
    int maxLen = 0;
    for (const string& str : arr) {
        maxLen = max(maxLen, (int)str.length());
    }
    
    // Sort from rightmost character to leftmost
    for (int pos = maxLen - 1; pos >= 0; pos--) {
        countingSortByChar(arr, pos, maxLen);
    }
}

int main() {
    cout << "=== String Radix Sort ===" << endl;
    vector<string> strings = {"abc", "bcd", "xyz", "aaa", "bbb", "aba"};
    
    cout << "Original: ";
    for (const string& s : strings) cout << s << " ";
    cout << endl;
    
    radixSortStrings(strings);
    
    cout << "Sorted:   ";
    for (const string& s : strings) cout << s << " ";
    cout << endl;
    
    return 0;
}
```

## Detailed Walkthrough

Let's trace radix sort for: `[170, 45, 75, 90, 802, 24, 2, 66]`

**Maximum = 802 → 3 digits needed**

### Pass 1: Sort by ones place (exp = 1)

Extract ones digit: `(num / 1) % 10`
```
170 → 0
45  → 5
75  → 5
90  → 0
802 → 2
24  → 4
2   → 2
66  → 6
```

Count array after counting:
```
count[0] = 2  (170, 90)
count[2] = 2  (802, 2)
count[4] = 1  (24)
count[5] = 2  (45, 75)
count[6] = 1  (66)
```

After cumulative count and stable sort:
```
[170, 90, 802, 2, 24, 45, 75, 66]
```

### Pass 2: Sort by tens place (exp = 10)

Extract tens digit: `(num / 10) % 10`
```
170 → 7
90  → 9
802 → 0
2   → 0
24  → 2
45  → 4
75  → 7
66  → 6
```

After stable sort:
```
[802, 2, 24, 45, 66, 170, 75, 90]
```

### Pass 3: Sort by hundreds place (exp = 100)

Extract hundreds digit: `(num / 100) % 10`
```
802 → 8
2   → 0
24  → 0
45  → 0
66  → 0
170 → 1
75  → 0
90  → 0
```

After stable sort:
```
[2, 24, 45, 66, 75, 90, 170, 802] ✓
```

## Time Complexity Analysis

**Best, Average, Worst Case: O(d × (n + k))**

Where:
- **d** = number of digits (or characters)
- **n** = number of elements
- **k** = range of each digit (usually 10 for decimal, 256 for bytes)

For fixed number of digits (constant d):
- **Effectively O(n)** - Linear time!

**Example:**
- Sorting 1,000,000 numbers (n = 10⁶)
- Each number ≤ 10,000 (d = 5 digits)
- Time: O(5 × (10⁶ + 10)) ≈ O(5 × 10⁶) = 5 million operations

Compare to Quick Sort: O(n log n) = 10⁶ × log₂(10⁶) ≈ 20 million operations

## LSD vs MSD Radix Sort

| Feature            | LSD (Least Significant Digit)          | MSD (Most Significant Digit)           |
| ------------------ | -------------------------------------- | -------------------------------------- |
| **Processing**     | Right to left (ones → tens → hundreds) | Left to right (hundreds → tens → ones) |
| **Implementation** | Iterative, simpler                     | Recursive, more complex                |
| **Stability**      | Must use stable subroutine             | Must use stable subroutine             |
| **Efficiency**     | Processes all elements every pass      | Can skip passes for sorted buckets     |
| **Use Case**       | Fixed-length keys                      | Variable-length keys, strings          |

## Advantages & Disadvantages

### ✅ Advantages
- **Fast**: O(n) for fixed-length keys
- **Stable**: Preserves relative order
- **Predictable**: Performance doesn't depend on input distribution
- **Parallel**: Can be parallelized easily

### ❌ Disadvantages
- **Limited use**: Only works for integers, fixed-length strings
- **Space**: Requires O(n) extra space
- **Not adaptive**: Doesn't benefit from partially sorted data
- **Digit count matters**: Slow for very large numbers

## When to Use Radix Sort

✅ **Use when:**
- Sorting integers with limited range
- Sorting strings of similar length
- You need O(n) time complexity
- Stability is required
- Working with fixed-length keys (SSN, phone numbers, zip codes)

❌ **Don't use when:**
- Floating-point numbers
- Variable-length data
- Very large ranges (many digits)
- Memory is constrained

## Comparison with Other Sorts

| Algorithm      | Time (Average) | Time (Worst) | Space    | Stable |
| -------------- | -------------- | ------------ | -------- | ------ |
| **Radix Sort** | O(d·n)         | O(d·n)       | O(n+k)   | Yes    |
| Quick Sort     | O(n log n)     | O(n²)        | O(log n) | No     |
| Merge Sort     | O(n log n)     | O(n log n)   | O(n)     | Yes    |
| Counting Sort  | O(n+k)         | O(n+k)       | O(k)     | Yes    |

**Note:** When d (number of digits) is constant and small, Radix Sort achieves **linear time O(n)**

# Bucket Sort:
# Bucket Sort

**Bucket Sort** is a distribution-based sorting algorithm that works by distributing elements into several "buckets," sorting each bucket individually, and then concatenating all buckets. It's particularly efficient when input is uniformly distributed over a range.

## Key Characteristics

- **Time Complexity**: 
  - **Best/Average**: O(n + k) where k is number of buckets
  - **Worst**: O(n²) when all elements go into one bucket
- **Space Complexity**: O(n + k)
- **Stable**: Can be (depends on sorting algorithm used for buckets)
- **Not In-place**: Requires additional space
- **Best for**: Uniformly distributed data over a range

## How It Works

1. **Create buckets**: Divide the range into n (or fewer) equally sized intervals
2. **Distribute elements**: Place each element into its appropriate bucket
3. **Sort buckets**: Sort each bucket individually (using insertion sort, quick sort, etc.)
4. **Concatenate**: Merge all sorted buckets in order

## Visual Example

Let's sort: `[0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68]`

Using 5 buckets for range [0, 1):

```
Original: [0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68]

Step 1: Distribute into buckets (range [0.0, 0.2), [0.2, 0.4), ...)

Bucket 0 [0.0-0.2): [0.17, 0.12]
Bucket 1 [0.2-0.4): [0.39, 0.26, 0.21, 0.23]
Bucket 2 [0.4-0.6): []
Bucket 3 [0.6-0.8): [0.78, 0.72, 0.68]
Bucket 4 [0.8-1.0): [0.94]

Step 2: Sort each bucket individually

Bucket 0: [0.12, 0.17]
Bucket 1: [0.21, 0.23, 0.26, 0.39]
Bucket 2: []
Bucket 3: [0.68, 0.72, 0.78]
Bucket 4: [0.94]

Step 3: Concatenate all buckets

Final: [0.12, 0.17, 0.21, 0.23, 0.26, 0.39, 0.68, 0.72, 0.78, 0.94] ✓
```

## Pseudocode

```text name=bucket_sort_pseudocode.txt
BUCKET-SORT(A, n)
    // A: input array, n: size
    
    // Step 1: Create n empty buckets
    Create array of n empty lists B[0...n-1]
    
    // Step 2: Distribute elements into buckets
    for i = 0 to n-1 do
        // Assuming elements are in range [0, 1)
        bucketIndex = floor(n * A[i])
        Insert A[i] into bucket B[bucketIndex]
    
    // Step 3: Sort individual buckets
    for i = 0 to n-1 do
        SORT(B[i])  // Use any sorting algorithm
    
    // Step 4: Concatenate all buckets
    index = 0
    for i = 0 to n-1 do
        for each element x in B[i] do
            A[index] = x
            index = index + 1
    
    return A

// For integer arrays with known range [min, max]
BUCKET-SORT-INTEGER(A, n, min, max)
    bucketCount = n
    bucketRange = (max - min + 1) / bucketCount
    
    Create bucketCount empty buckets
    
    for i = 0 to n-1 do
        bucketIndex = floor((A[i] - min) / bucketRange)
        if bucketIndex >= bucketCount then
            bucketIndex = bucketCount - 1
        Insert A[i] into bucket[bucketIndex]
    
    for each bucket do
        SORT(bucket)
    
    Concatenate all buckets
    
    return A
```

## C++ Implementation

```cpp name=bucket_sort.cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

// Bucket Sort for floating point numbers in range [0, 1)
void bucketSort(vector<float>& arr) {
    int n = arr.size();
    if (n <= 0) return;
    
    // Step 1: Create n empty buckets
    vector<vector<float>> buckets(n);
    
    // Step 2: Put elements into buckets
    for (int i = 0; i < n; i++) {
        int bucketIndex = n * arr[i];  // Index in [0, n-1]
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    // Step 3: Sort individual buckets using insertion sort
    for (int i = 0; i < n; i++) {
        sort(buckets[i].begin(), buckets[i].end());
    }
    
    // Step 4: Concatenate all buckets into arr[]
    int index = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < buckets[i].size(); j++) {
            arr[index++] = buckets[i][j];
        }
    }
}

// Bucket Sort for integers with known range
void bucketSortIntegers(vector<int>& arr) {
    int n = arr.size();
    if (n <= 0) return;
    
    // Find minimum and maximum values
    int minVal = *min_element(arr.begin(), arr.end());
    int maxVal = *max_element(arr.begin(), arr.end());
    
    // Calculate range per bucket
    int bucketCount = n;
    float bucketRange = (float)(maxVal - minVal + 1) / bucketCount;
    
    // Create buckets
    vector<vector<int>> buckets(bucketCount);
    
    // Distribute elements into buckets
    for (int i = 0; i < n; i++) {
        int bucketIndex = (arr[i] - minVal) / bucketRange;
        
        // Handle edge case for maximum value
        if (bucketIndex >= bucketCount) {
            bucketIndex = bucketCount - 1;
        }
        
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    // Sort individual buckets
    for (int i = 0; i < bucketCount; i++) {
        sort(buckets[i].begin(), buckets[i].end());
    }
    
    // Concatenate buckets
    int index = 0;
    for (int i = 0; i < bucketCount; i++) {
        for (int j = 0; j < buckets[i].size(); j++) {
            arr[index++] = buckets[i][j];
        }
    }
}

// Bucket Sort with custom bucket count
void bucketSortCustom(vector<float>& arr, int bucketCount) {
    int n = arr.size();
    if (n <= 0) return;
    
    // Find min and max
    float minVal = *min_element(arr.begin(), arr.end());
    float maxVal = *max_element(arr.begin(), arr.end());
    
    // Calculate range
    float range = maxVal - minVal;
    float bucketRange = range / bucketCount;
    
    // Create buckets
    vector<vector<float>> buckets(bucketCount);
    
    // Distribute elements
    for (int i = 0; i < n; i++) {
        int bucketIndex = (arr[i] - minVal) / bucketRange;
        
        // Handle edge case
        if (bucketIndex >= bucketCount) {
            bucketIndex = bucketCount - 1;
        }
        
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    // Sort buckets
    for (int i = 0; i < bucketCount; i++) {
        sort(buckets[i].begin(), buckets[i].end());
    }
    
    // Concatenate
    int index = 0;
    for (int i = 0; i < bucketCount; i++) {
        for (float val : buckets[i]) {
            arr[index++] = val;
        }
    }
}

// Bucket Sort with visualization
void bucketSortWithVisualization(vector<float>& arr, int bucketCount) {
    int n = arr.size();
    if (n <= 0) return;
    
    cout << "Original array: ";
    for (float val : arr) cout << val << " ";
    cout << "\n\n";
    
    float minVal = *min_element(arr.begin(), arr.end());
    float maxVal = *max_element(arr.begin(), arr.end());
    float range = maxVal - minVal;
    float bucketRange = range / bucketCount;
    
    vector<vector<float>> buckets(bucketCount);
    
    // Distribute
    cout << "Step 1: Distributing into " << bucketCount << " buckets\n";
    for (int i = 0; i < n; i++) {
        int bucketIndex = (arr[i] - minVal) / bucketRange;
        if (bucketIndex >= bucketCount) {
            bucketIndex = bucketCount - 1;
        }
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    // Display buckets before sorting
    for (int i = 0; i < bucketCount; i++) {
        cout << "  Bucket " << i << " [" << minVal + i * bucketRange 
             << " - " << minVal + (i + 1) * bucketRange << "): ";
        if (buckets[i].empty()) {
            cout << "(empty)";
        } else {
            for (float val : buckets[i]) {
                cout << val << " ";
            }
        }
        cout << endl;
    }
    
    // Sort buckets
    cout << "\nStep 2: Sorting individual buckets\n";
    for (int i = 0; i < bucketCount; i++) {
        sort(buckets[i].begin(), buckets[i].end());
        if (!buckets[i].empty()) {
            cout << "  Bucket " << i << " sorted: ";
            for (float val : buckets[i]) {
                cout << val << " ";
            }
            cout << endl;
        }
    }
    
    // Concatenate
    cout << "\nStep 3: Concatenating buckets\n";
    int index = 0;
    for (int i = 0; i < bucketCount; i++) {
        for (float val : buckets[i]) {
            arr[index++] = val;
        }
    }
    
    cout << "Final sorted array: ";
    for (float val : arr) cout << val << " ";
    cout << "\n";
}

// Insertion sort for small buckets (more efficient than std::sort for small arrays)
void insertionSort(vector<float>& bucket) {
    for (int i = 1; i < bucket.size(); i++) {
        float key = bucket[i];
        int j = i - 1;
        while (j >= 0 && bucket[j] > key) {
            bucket[j + 1] = bucket[j];
            j--;
        }
        bucket[j + 1] = key;
    }
}

// Optimized bucket sort using insertion sort for buckets
void bucketSortOptimized(vector<float>& arr) {
    int n = arr.size();
    if (n <= 0) return;
    
    vector<vector<float>> buckets(n);
    
    for (int i = 0; i < n; i++) {
        int bucketIndex = n * arr[i];
        buckets[bucketIndex].push_back(arr[i]);
    }
    
    // Use insertion sort for small buckets (typically more efficient)
    for (int i = 0; i < n; i++) {
        insertionSort(buckets[i]);
    }
    
    int index = 0;
    for (int i = 0; i < n; i++) {
        for (float val : buckets[i]) {
            arr[index++] = val;
        }
    }
}

void printFloatArray(const vector<float>& arr, const string& label = "") {
    if (!label.empty()) cout << label << ": ";
    for (float val : arr) {
        printf("%.2f ", val);
    }
    cout << endl;
}

void printIntArray(const vector<int>& arr, const string& label = "") {
    if (!label.empty()) cout << label << ": ";
    for (int val : arr) {
        cout << val << " ";
    }
    cout << endl;
}

int main() {
    // Example 1: Basic bucket sort for floats [0, 1)
    cout << "=== Example 1: Basic Bucket Sort (floats in [0, 1)) ===" << endl;
    vector<float> arr1 = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
    printFloatArray(arr1, "Original");
    bucketSort(arr1);
    printFloatArray(arr1, "Sorted  ");
    
    // Example 2: Integer bucket sort
    cout << "\n=== Example 2: Bucket Sort for Integers ===" << endl;
    vector<int> arr2 = {42, 32, 33, 52, 37, 47, 51};
    printIntArray(arr2, "Original");
    bucketSortIntegers(arr2);
    printIntArray(arr2, "Sorted  ");
    
    // Example 3: With visualization
    cout << "\n=== Example 3: Bucket Sort with Visualization ===" << endl;
    vector<float> arr3 = {0.78, 0.17, 0.39, 0.26, 0.72};
    bucketSortWithVisualization(arr3, 5);
    
    // Example 4: Different range
    cout << "\n=== Example 4: Custom Range [0, 10) ===" << endl;
    vector<float> arr4 = {8.2, 1.5, 3.9, 2.6, 7.1, 9.4, 2.1};
    printFloatArray(arr4, "Original");
    bucketSortCustom(arr4, 5);
    printFloatArray(arr4, "Sorted  ");
    
    // Example 5: Large dataset
    cout << "\n=== Example 5: Larger Dataset ===" << endl;
    vector<float> arr5 = {0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434, 
                          0.789, 0.234, 0.456, 0.987, 0.123, 0.432};
    printFloatArray(arr5, "Original");
    bucketSort(arr5);
    printFloatArray(arr5, "Sorted  ");
    
    // Example 6: Already sorted
    cout << "\n=== Example 6: Already Sorted ===" << endl;
    vector<float> arr6 = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
    printFloatArray(arr6, "Original");
    bucketSort(arr6);
    printFloatArray(arr6, "Sorted  ");
    
    // Example 7: Reverse sorted
    cout << "\n=== Example 7: Reverse Sorted ===" << endl;
    vector<float> arr7 = {0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
    printFloatArray(arr7, "Original");
    bucketSort(arr7);
    printFloatArray(arr7, "Sorted  ");
    
    // Example 8: All same values
    cout << "\n=== Example 8: All Same Values ===" << endl;
    vector<float> arr8 = {0.5, 0.5, 0.5, 0.5, 0.5};
    printFloatArray(arr8, "Original");
    bucketSort(arr8);
    printFloatArray(arr8, "Sorted  ");
    
    return 0;
}
```

## Advanced Implementation: Generic Bucket Sort

```cpp name=bucket_sort_generic.cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <functional>
using namespace std;

// Generic bucket sort template
template<typename T>
void bucketSortGeneric(vector<T>& arr, int bucketCount, 
                       function<int(T, T, T, int)> getBucketIndex) {
    if (arr.empty()) return;
    
    T minVal = *min_element(arr.begin(), arr.end());
    T maxVal = *max_element(arr.begin(), arr.end());
    
    // Create buckets
    vector<vector<T>> buckets(bucketCount);
    
    // Distribute elements
    for (const T& val : arr) {
        int bucketIndex = getBucketIndex(val, minVal, maxVal, bucketCount);
        buckets[bucketIndex].push_back(val);
    }
    
    // Sort each bucket
    for (auto& bucket : buckets) {
        sort(bucket.begin(), bucket.end());
    }
    
    // Concatenate
    int index = 0;
    for (const auto& bucket : buckets) {
        for (const T& val : bucket) {
            arr[index++] = val;
        }
    }
}

// Bucket sort with statistics
struct BucketStats {
    int totalElements;
    int bucketCount;
    vector<int> elementsPerBucket;
    int maxBucketSize;
    int minBucketSize;
    double avgBucketSize;
};

BucketStats bucketSortWithStats(vector<float>& arr, int bucketCount) {
    BucketStats stats;
    stats.totalElements = arr.size();
    stats.bucketCount = bucketCount;
    stats.elementsPerBucket.resize(bucketCount, 0);
    
    if (arr.empty()) return stats;
    
    float minVal = *min_element(arr.begin(), arr.end());
    float maxVal = *max_element(arr.begin(), arr.end());
    float range = maxVal - minVal;
    float bucketRange = range / bucketCount;
    
    vector<vector<float>> buckets(bucketCount);
    
    // Distribute
    for (float val : arr) {
        int bucketIndex = (val - minVal) / bucketRange;
        if (bucketIndex >= bucketCount) bucketIndex = bucketCount - 1;
        buckets[bucketIndex].push_back(val);
        stats.elementsPerBucket[bucketIndex]++;
    }
    
    // Calculate statistics
    stats.maxBucketSize = *max_element(stats.elementsPerBucket.begin(), 
                                       stats.elementsPerBucket.end());
    stats.minBucketSize = *min_element(stats.elementsPerBucket.begin(), 
                                       stats.elementsPerBucket.end());
    stats.avgBucketSize = (double)stats.totalElements / bucketCount;
    
    // Sort and concatenate
    int index = 0;
    for (auto& bucket : buckets) {
        sort(bucket.begin(), bucket.end());
        for (float val : bucket) {
            arr[index++] = val;
        }
    }
    
    return stats;
}

void printStats(const BucketStats& stats) {
    cout << "\nBucket Sort Statistics:" << endl;
    cout << "  Total elements: " << stats.totalElements << endl;
    cout << "  Number of buckets: " << stats.bucketCount << endl;
    cout << "  Max bucket size: " << stats.maxBucketSize << endl;
    cout << "  Min bucket size: " << stats.minBucketSize << endl;
    cout << "  Avg bucket size: " << stats.avgBucketSize << endl;
    cout << "  Distribution: ";
    for (int size : stats.elementsPerBucket) {
        cout << size << " ";
    }
    cout << endl;
}

int main() {
    // Example: Using generic bucket sort
    cout << "=== Generic Bucket Sort ===" << endl;
    vector<int> arr1 = {42, 32, 33, 52, 37, 47, 51, 29, 38, 44};
    
    auto getBucket = [](int val, int minVal, int maxVal, int bucketCount) {
        int bucketIndex = (int)((double)(val - minVal) / (maxVal - minVal + 1) * bucketCount);
        return min(bucketIndex, bucketCount - 1);
    };
    
    cout << "Original: ";
    for (int val : arr1) cout << val << " ";
    cout << endl;
    
    bucketSortGeneric<int>(arr1, 5, getBucket);
    
    cout << "Sorted:   ";
    for (int val : arr1) cout << val << " ";
    cout << endl;
    
    // Example: With statistics
    cout << "\n=== Bucket Sort with Statistics ===" << endl;
    vector<float> arr2 = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 
                          0.23, 0.68, 0.45, 0.55, 0.82, 0.34, 0.91};
    
    cout << "Original: ";
    for (float val : arr2) printf("%.2f ", val);
    cout << endl;
    
    BucketStats stats = bucketSortWithStats(arr2, 5);
    
    cout << "Sorted:   ";
    for (float val : arr2) printf("%.2f ", val);
    cout << endl;
    
    printStats(stats);
    
    return 0;
}
```

## Detailed Walkthrough

Let's trace bucket sort for: `[0.78, 0.17, 0.39, 0.26, 0.72]` with 5 buckets

**Step 1: Create 5 buckets for range [0, 1)**

```
Bucket 0: [0.0, 0.2)
Bucket 1: [0.2, 0.4)
Bucket 2: [0.4, 0.6)
Bucket 3: [0.6, 0.8)
Bucket 4: [0.8, 1.0)
```

**Step 2: Distribute elements**

Calculate bucket index: `bucketIndex = floor(n * element) = floor(5 * element)`

```
0.78 → floor(5 * 0.78) = floor(3.9) = 3 → Bucket 3
0.17 → floor(5 * 0.17) = floor(0.85) = 0 → Bucket 0
0.39 → floor(5 * 0.39) = floor(1.95) = 1 → Bucket 1
0.26 → floor(5 * 0.26) = floor(1.3) = 1 → Bucket 1
0.72 → floor(5 * 0.72) = floor(3.6) = 3 → Bucket 3
```

**After distribution:**
```
Bucket 0: [0.17]
Bucket 1: [0.39, 0.26]
Bucket 2: []
Bucket 3: [0.78, 0.72]
Bucket 4: []
```

**Step 3: Sort each bucket**

```
Bucket 0: [0.17] (already sorted)
Bucket 1: [0.26, 0.39] (sorted)
Bucket 2: [] (empty)
Bucket 3: [0.72, 0.78] (sorted)
Bucket 4: [] (empty)
```

**Step 4: Concatenate**

```
Result: [0.17] + [0.26, 0.39] + [] + [0.72, 0.78] + []
Final:  [0.17, 0.26, 0.39, 0.72, 0.78] ✓
```

## Time Complexity Analysis

### Best Case: O(n + k)
When elements are **uniformly distributed**, each bucket gets roughly n/k elements.

**Example:**
- n = 100 elements
- k = 10 buckets
- Each bucket ≈ 10 elements
- Sorting each bucket: O(10 log 10) ≈ 33 operations
- Total for all buckets: 10 × 33 = 330 operations
- Distribution + Concatenation: 200 operations
- **Total: O(n + k) ≈ 530 operations**

### Average Case: O(n + n²/k + k)
Simplifies to **O(n)** when k = Θ(n)

### Worst Case: O(n²)
When **all elements go into one bucket**:

```
Input: [0.01, 0.02, 0.03, 0.04, 0.05] (all in bucket 0)

Bucket 0: [0.01, 0.02, 0.03, 0.04, 0.05]
Buckets 1-4: empty

Sorting bucket 0: O(n log n) or O(n²) with insertion sort
```

## Space Complexity

**O(n + k)** where:
- **n**: Space for elements in buckets
- **k**: Space for bucket array

## Choosing Number of Buckets

```cpp name=optimal_bucket_count.cpp
// Rule of thumb: Use sqrt(n) buckets for best average performance
int optimalBucketCount(int n) {
    return max(1, (int)sqrt(n));
}

// Or use n buckets for maximum distribution
int maxBucketCount(int n) {
    return n;
}

// Example usage
int main() {
    vector<float> arr = {/* 100 elements */};
    
    // Good choice: sqrt(100) = 10 buckets
    int buckets = optimalBucketCount(arr.size());
    
    return 0;
}
```

## Advantages & Disadvantages

### Advantages
- **Fast for uniform data**: O(n) average case
- **Simple to understand**: Intuitive divide-and-conquer approach
- **Stable**: Can maintain relative order
- **Parallelizable**: Each bucket can be sorted independently
- **Good for external sorting**: Can handle data that doesn't fit in memory

### Disadvantages
- **Sensitive to distribution**: Poor performance with non-uniform data
- **Space overhead**: Requires O(n + k) extra space
- **Worst case O(n²)**: When all elements in one bucket
- **Requires knowledge of range**: Need to know min/max values
- **Not cache-friendly**: Random access to buckets

## When to Use Bucket Sort

**Use when:**
- Data is **uniformly distributed** over a range
- Sorting floating-point numbers in `[0, 1)`
- You know the range of input values
- You have enough memory for buckets
- External sorting (sorting large files)

**Don't use when:**
- Data has **skewed distribution** (many duplicates, clusters)
- Unknown or very large range
- Memory is constrained
- Data is already nearly sorted (use insertion sort instead)

## Comparison with Other Sorts

| Algorithm | Best | Average | Worst | Space | Stable | Distribution |
|-----------|------|---------|-------|-------|--------|--------------|
| **Bucket Sort** | O(n+k) | O(n) | O(n²) | O(n+k) | Yes* | Uniform |
| **Radix Sort** | O(d·n) | O(d·n) | O(d·n) | O(n+k) | Yes | Any |
| **Counting Sort** | O(n+k) | O(n+k) | O(n+k) | O(k) | Yes | Small range |
| **Quick Sort** | O(n log n) | O(n log n) | O(n²) | O(log n) | No | Any |
| **Merge Sort** | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | Any |

*Stable if the bucket sorting algorithm is stable

## Real-World Applications

1. **Computer Graphics**: Sorting pixels by color intensity
2. **Database Systems**: Sorting records with numeric keys
3. **External Sorting**: Sorting large files that don't fit in memory
4. **Parallel Computing**: Each processor handles different buckets
5. **Geographic Data**: Sorting coordinates within regions

## Optimization Tips

```cpp name=bucket_sort_optimizations.cpp
// 1. Use insertion sort for small buckets (< 10 elements)
void sortBucket(vector<float>& bucket) {
    if (bucket.size() < 10) {
        // Insertion sort is faster for small arrays
        insertionSort(bucket);
    } else {
        sort(bucket.begin(), bucket.end());
    }
}

// 2. Pre-allocate bucket capacity to avoid reallocations
void bucketSortOptimized(vector<float>& arr, int bucketCount) {
    vector<vector<float>> buckets(bucketCount);
    
    // Pre-allocate space
    int avgSize = arr.size() / bucketCount + 1;
    for (auto& bucket : buckets) {
        bucket.reserve(avgSize);
    }
    
    // ... rest of algorithm
}

// 3. Use adaptive bucket count based on data characteristics
int adaptiveBucketCount(const vector<float>& arr) {
    int n = arr.size();
    float minVal = *min_element(arr.begin(), arr.end());
    float maxVal = *max_element(arr.begin(), arr.end());
    float range = maxVal - minVal;
    
    // If range is small, use fewer buckets
    if (range < 1.0) {
        return max(10, n / 10);
    }
    return max(1, (int)sqrt(n));
}
```

Bucket sort is excellent when you have uniformly distributed data and need to achieve linear time performance!

# Algorithms with Randomization Modification:
## 1. [[Randomized QuickSort]]
