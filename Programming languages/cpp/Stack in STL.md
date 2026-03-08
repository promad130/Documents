# Complete Guide to Stacks in C++

I'll teach you stacks from the ground up, covering both the concept and practical implementations!

---

## 📚 What is a Stack?

A **Stack** is a linear data structure that follows the **LIFO (Last In First Out)** principle. Think of it like a stack of plates - you can only add or remove plates from the top.

### Key Operations:
- **Push**: Add an element to the top
- **Pop**: Remove the top element
- **Top/Peek**: View the top element without removing it
- **isEmpty**: Check if stack is empty
- **Size**: Get number of elements

### Real-world Applications:
- Function call management (recursion)
- Undo/Redo operations
- Expression evaluation (infix to postfix)
- Browser back button
- Balanced parentheses checking

---

## 🔧 Part 1: Stack from Scratch

### Implementation 1: Stack using Array

```cpp name=stack_array.cpp
#include <iostream>
#define MAX 1000
using namespace std;

class Stack {
    int top;
    int arr[MAX]; // Maximum size of Stack

public:
    Stack() { top = -1; } // Constructor

    // Push element onto stack
    bool push(int x) {
        if (top >= (MAX - 1)) {
            cout << "Stack Overflow" << endl;
            return false;
        }
        arr[++top] = x;
        cout << x << " pushed into stack" << endl;
        return true;
    }

    // Pop element from stack
    int pop() {
        if (top < 0) {
            cout << "Stack Underflow" << endl;
            return -1;
        }
        int x = arr[top--];
        return x;
    }

    // Return top element
    int peek() {
        if (top < 0) {
            cout << "Stack is Empty" << endl;
            return -1;
        }
        return arr[top];
    }

    // Check if stack is empty
    bool isEmpty() {
        return (top < 0);
    }

    // Get size of stack
    int size() {
        return top + 1;
    }

    // Display all elements
    void display() {
        if (top < 0) {
            cout << "Stack is empty" << endl;
            return;
        }
        cout << "Stack elements: ";
        for (int i = top; i >= 0; i--) {
            cout << arr[i] << " ";
        }
        cout << endl;
    }
};

int main() {
    Stack s;
    
    s.push(10);
    s.push(20);
    s.push(30);
    s.push(40);
    
    s.display();
    
    cout << "Top element: " << s.peek() << endl;
    cout << "Stack size: " << s.size() << endl;
    
    cout << s.pop() << " popped from stack" << endl;
    cout << s.pop() << " popped from stack" << endl;
    
    s.display();
    
    cout << "Is stack empty? " << (s.isEmpty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

**Output:**
```
10 pushed into stack
20 pushed into stack
30 pushed into stack
40 pushed into stack
Stack elements: 40 30 20 10 
Top element: 40
Stack size: 4
40 popped from stack
30 popped from stack
Stack elements: 20 10 
Is stack empty? No
```

---

### Implementation 2: Stack using Linked List

```cpp name=stack_linkedlist.cpp
#include <iostream>
using namespace std;

// Node structure
class Node {
public:
    int data;
    Node* next;
    
    Node(int value) {
        data = value;
        next = nullptr;
    }
};

class Stack {
    Node* top;
    int count;

public:
    Stack() {
        top = nullptr;
        count = 0;
    }

    // Push element onto stack
    void push(int x) {
        Node* newNode = new Node(x);
        newNode->next = top;
        top = newNode;
        count++;
        cout << x << " pushed into stack" << endl;
    }

    // Pop element from stack
    int pop() {
        if (isEmpty()) {
            cout << "Stack Underflow" << endl;
            return -1;
        }
        Node* temp = top;
        int popped = top->data;
        top = top->next;
        delete temp;
        count--;
        return popped;
    }

    // Return top element
    int peek() {
        if (isEmpty()) {
            cout << "Stack is Empty" << endl;
            return -1;
        }
        return top->data;
    }

    // Check if stack is empty
    bool isEmpty() {
        return top == nullptr;
    }

    // Get size of stack
    int size() {
        return count;
    }

    // Display all elements
    void display() {
        if (isEmpty()) {
            cout << "Stack is empty" << endl;
            return;
        }
        Node* temp = top;
        cout << "Stack elements: ";
        while (temp != nullptr) {
            cout << temp->data << " ";
            temp = temp->next;
        }
        cout << endl;
    }

    // Destructor to free memory
    ~Stack() {
        while (!isEmpty()) {
            pop();
        }
    }
};

int main() {
    Stack s;
    
    s.push(10);
    s.push(20);
    s.push(30);
    s.push(40);
    
    s.display();
    
    cout << "Top element: " << s.peek() << endl;
    cout << "Stack size: " << s.size() << endl;
    
    cout << s.pop() << " popped from stack" << endl;
    cout << s.pop() << " popped from stack" << endl;
    
    s.display();
    
    cout << "Is stack empty? " << (s.isEmpty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

---

## 🎯 Part 2: Stack STL (Standard Template Library)

The C++ STL provides `std::stack` which is a **container adapter** that uses other containers (like vector, deque, list) internally.

### Basic Stack STL Usage

```cpp name=stack_stl_basics.cpp
#include <iostream>
#include <stack>
using namespace std;

int main() {
    // Create a stack of integers
    stack<int> s;
    
    // Check if empty
    cout << "Is stack empty? " << (s.empty() ? "Yes" : "No") << endl;
    
    // Push elements
    s.push(10);
    s.push(20);
    s.push(30);
    s.push(40);
    s.push(50);
    
    cout << "Stack size: " << s.size() << endl;
    
    // Access top element
    cout << "Top element: " << s.top() << endl;
    
    // Pop elements
    cout << "\nPopping elements:" << endl;
    while (!s.empty()) {
        cout << s.top() << " ";
        s.pop();
    }
    cout << endl;
    
    cout << "\nIs stack empty now? " << (s.empty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

**Output:**
```
Is stack empty? Yes
Stack size: 5
Top element: 50

Popping elements:
50 40 30 20 10 

Is stack empty now? Yes
```

---

### Stack STL - All Operations

```cpp name=stack_stl_complete.cpp
#include <iostream>
#include <stack>
#include <vector>
using namespace std;

// Function to display stack (requires copying)
void displayStack(stack<int> s) {
    if (s.empty()) {
        cout << "Stack is empty" << endl;
        return;
    }
    cout << "Stack elements (top to bottom): ";
    while (!s.empty()) {
        cout << s.top() << " ";
        s.pop();
    }
    cout << endl;
}

int main() {
    // 1. Creating stacks
    stack<int> s1;                    // Default (uses deque)
    stack<int, vector<int>> s2;       // Using vector
    
    // 2. Push operation
    cout << "=== Push Operation ===" << endl;
    s1.push(10);
    s1.push(20);
    s1.push(30);
    s1.push(40);
    displayStack(s1);
    
    // 3. Top operation
    cout << "\n=== Top Operation ===" << endl;
    cout << "Top element: " << s1.top() << endl;
    
    // 4. Pop operation
    cout << "\n=== Pop Operation ===" << endl;
    s1.pop();
    cout << "After popping: ";
    displayStack(s1);
    
    // 5. Size operation
    cout << "\n=== Size Operation ===" << endl;
    cout << "Stack size: " << s1.size() << endl;
    
    // 6. Empty check
    cout << "\n=== Empty Check ===" << endl;
    cout << "Is stack empty? " << (s1.empty() ? "Yes" : "No") << endl;
    
    // 7. Swap operation
    cout << "\n=== Swap Operation ===" << endl;
    stack<int> s3;
    s3.push(100);
    s3.push(200);
    
    cout << "Before swap:" << endl;
    cout << "s1 top: " << s1.top() << endl;
    cout << "s3 top: " << s3.top() << endl;
    
    s1.swap(s3);
    
    cout << "After swap:" << endl;
    cout << "s1 top: " << s1.top() << endl;
    cout << "s3 top: " << s3.top() << endl;
    
    // 8. Emplace operation (C++11)
    cout << "\n=== Emplace Operation ===" << endl;
    s1.emplace(300); // Constructs element in-place
    cout << "After emplace(300): " << s1.top() << endl;
    
    return 0;
}
```

---

### Stack STL with Different Data Types

```cpp name=stack_stl_datatypes.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

// Custom class
class Person {
public:
    string name;
    int age;
    
    Person(string n, int a) : name(n), age(a) {}
    
    void display() {
        cout << "Name: " << name << ", Age: " << age << endl;
    }
};

int main() {
    // Stack of strings
    cout << "=== Stack of Strings ===" << endl;
    stack<string> names;
    names.push("Alice");
    names.push("Bob");
    names.push("Charlie");
    
    while (!names.empty()) {
        cout << names.top() << endl;
        names.pop();
    }
    
    // Stack of characters
    cout << "\n=== Stack of Characters ===" << endl;
    stack<char> chars;
    string word = "HELLO";
    
    for (char c : word) {
        chars.push(c);
    }
    
    cout << "Reversed: ";
    while (!chars.empty()) {
        cout << chars.top();
        chars.pop();
    }
    cout << endl;
    
    // Stack of custom objects
    cout << "\n=== Stack of Custom Objects ===" << endl;
    stack<Person> people;
    people.push(Person("John", 25));
    people.push(Person("Jane", 30));
    people.push(Person("Jack", 28));
    
    while (!people.empty()) {
        people.top().display();
        people.pop();
    }
    
    return 0;
}
```

---

## 🚀 Part 3: Practical Problems with Stack

### Problem 1: Balanced Parentheses

```cpp name=balanced_parentheses.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

bool isBalanced(string expr) {
    stack<char> s;
    
    for (char ch : expr) {
        // If opening bracket, push to stack
        if (ch == '(' || ch == '{' || ch == '[') {
            s.push(ch);
        }
        // If closing bracket
        else if (ch == ')' || ch == '}' || ch == ']') {
            // Check if stack is empty
            if (s.empty()) {
                return false;
            }
            
            // Check if brackets match
            char top = s.top();
            if ((ch == ')' && top == '(') ||
                (ch == '}' && top == '{') ||
                (ch == ']' && top == '[')) {
                s.pop();
            } else {
                return false;
            }
        }
    }
    
    // Stack should be empty for balanced expression
    return s.empty();
}

int main() {
    string expressions[] = {
        "{[()]}",
        "{[(])}",
        "((()))",
        "((())",
        "{[}]"
    };
    
    for (string expr : expressions) {
        cout << expr << " is " 
             << (isBalanced(expr) ? "Balanced" : "Not Balanced") 
             << endl;
    }
    
    return 0;
}
```

---

### Problem 2: Reverse a String

```cpp name=reverse_string.cpp
#include <iostream>
#include <stack>
#include <string>
using namespace std;

string reverseString(string str) {
    stack<char> s;
    
    // Push all characters to stack
    for (char ch : str) {
        s.push(ch);
    }
    
    // Pop all characters and build reversed string
    string reversed = "";
    while (!s.empty()) {
        reversed += s.top();
        s.pop();
    }
    
    return reversed;
}

int main() {
    string text = "Hello World";
    cout << "Original: " << text << endl;
    cout << "Reversed: " << reverseString(text) << endl;
    
    return 0;
}
```

---

### Problem 3: Next Greater Element

```cpp name=next_greater_element.cpp
#include <iostream>
#include <stack>
#include <vector>
using namespace std;

vector<int> nextGreaterElement(vector<int> arr) {
    int n = arr.size();
    vector<int> result(n, -1);
    stack<int> s;
    
    // Traverse from right to left
    for (int i = n - 1; i >= 0; i--) {
        // Pop elements smaller than current
        while (!s.empty() && s.top() <= arr[i]) {
            s.pop();
        }
        
        // If stack is not empty, top is the next greater element
        if (!s.empty()) {
            result[i] = s.top();
        }
        
        // Push current element
        s.push(arr[i]);
    }
    
    return result;
}

int main() {
    vector<int> arr = {4, 5, 2, 10, 8};
    vector<int> result = nextGreaterElement(arr);
    
    cout << "Array: ";
    for (int num : arr) cout << num << " ";
    cout << endl;
    
    cout << "Next Greater Element: ";
    for (int num : result) cout << num << " ";
    cout << endl;
    
    return 0;
}
```

---

### Problem 4: Evaluate Postfix Expression

```cpp name=evaluate_postfix.cpp
#include <iostream>
#include <stack>
#include <string>
#include <cctype>
using namespace std;

int evaluatePostfix(string expr) {
    stack<int> s;
    
    for (char ch : expr) {
        // If operand, push to stack
        if (isdigit(ch)) {
            s.push(ch - '0'); // Convert char to int
        }
        // If operator, pop two operands and perform operation
        else {
            int val2 = s.top(); s.pop();
            int val1 = s.top(); s.pop();
            
            switch (ch) {
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
    string expr = "231*+9-"; // (2 + (3 * 1)) - 9 = -4
    cout << "Postfix Expression: " << expr << endl;
    cout << "Result: " << evaluatePostfix(expr) << endl;
    
    return 0;
}
```

---

### Problem 5: Stock Span Problem

```cpp name=stock_span.cpp
#include <iostream>
#include <stack>
#include <vector>
using namespace std;

vector<int> calculateSpan(vector<int> prices) {
    int n = prices.size();
    vector<int> span(n);
    stack<int> s; // Stack to store indices
    
    for (int i = 0; i < n; i++) {
        // Pop elements while stack is not empty and top is less than current
        while (!s.empty() && prices[s.top()] <= prices[i]) {
            s.pop();
        }
        
        // If stack is empty, span is i + 1
        // Otherwise, span is difference of indices
        span[i] = s.empty() ? (i + 1) : (i - s.top());
        
        // Push current index
        s.push(i);
    }
    
    return span;
}

int main() {
    vector<int> prices = {100, 80, 60, 70, 60, 75, 85};
    vector<int> span = calculateSpan(prices);
    
    cout << "Prices: ";
    for (int p : prices) cout << p << " ";
    cout << endl;
    
    cout << "Span:   ";
    for (int s : span) cout << s << " ";
    cout << endl;
    
    return 0;
}
```

---

## 📊 Comparison: Array vs Linked List vs STL

| Feature | Array Implementation | Linked List Implementation | STL Stack |
|---------|---------------------|---------------------------|-----------|
| **Size** | Fixed | Dynamic | Dynamic |
| **Memory** | Contiguous | Non-contiguous | Depends on underlying container |
| **Overflow** | Possible | Only with heap memory | Auto-resize (usually) |
| **Implementation Complexity** | Easy | Moderate | Very Easy (built-in) |
| **Performance** | Fast | Slightly slower | Optimized |
| **Use Case** | Known max size | Unknown size | Production code |

---

## 🎓 Practice Questions

### Beginner Level:
1. Implement a stack and reverse a word
2. Check if a given expression has balanced parentheses
3. Convert decimal to binary using stack
4. Implement two stacks in one array

### Intermediate Level:
5. Implement a stack that supports getMin() in O(1)
6. Sort a stack using recursion
7. Reverse a stack using recursion
8. Implement queue using two stacks

### Advanced Level:
9. Largest rectangle in histogram
10. Maximum of minimum for every window size
11. Implement an expression evaluator (infix to postfix)
12. Design a special stack with push, pop, and getMiddle in O(1)
