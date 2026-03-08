# Complete Guide to Queues in C++

I'll teach you queues comprehensively, covering theory, implementations from scratch, and STL usage!

---

## 📚 What is a Queue?

A **Queue** is a linear data structure that follows the **FIFO (First In First Out)** principle. Think of it like a line at a ticket counter - the first person to arrive is the first to be served.

### Key Operations:
- **Enqueue (Push)**: Add an element to the rear/back
- **Dequeue (Pop)**: Remove an element from the front
- **Front**: View the front element without removing it
- **Rear/Back**: View the rear element without removing it
- **isEmpty**: Check if queue is empty
- **Size**: Get number of elements

### Real-world Applications:
- CPU scheduling
- Printer queue management
- Breadth-First Search (BFS) in graphs
- Handling asynchronous data transfer
- Call center phone systems
- IO Buffers

---

## 🔧 Part 1: Queue from Scratch

### Implementation 1: Queue using Array

```cpp name=queue_array.cpp
#include <iostream>
#define MAX 1000
using namespace std;

class Queue {
    int front, rear, size;
    int arr[MAX];

public:
    Queue() {
        front = 0;
        rear = -1;
        size = 0;
    }

    // Add element to queue
    bool enqueue(int x) {
        if (size >= MAX) {
            cout << "Queue Overflow" << endl;
            return false;
        }
        rear = (rear + 1) % MAX;  // Circular increment
        arr[rear] = x;
        size++;
        cout << x << " enqueued to queue" << endl;
        return true;
    }

    // Remove element from queue
    int dequeue() {
        if (isEmpty()) {
            cout << "Queue Underflow" << endl;
            return -1;
        }
        int x = arr[front];
        front = (front + 1) % MAX;  // Circular increment
        size--;
        return x;
    }

    // Get front element
    int getFront() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        return arr[front];
    }

    // Get rear element
    int getRear() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        return arr[rear];
    }

    // Check if queue is empty
    bool isEmpty() {
        return (size == 0);
    }

    // Get size of queue
    int getSize() {
        return size;
    }

    // Display all elements
    void display() {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }
        cout << "Queue elements (front to rear): ";
        for (int i = 0; i < size; i++) {
            int index = (front + i) % MAX;
            cout << arr[index] << " ";
        }
        cout << endl;
    }
};

int main() {
    Queue q;
    
    q.enqueue(10);
    q.enqueue(20);
    q.enqueue(30);
    q.enqueue(40);
    q.enqueue(50);
    
    q.display();
    
    cout << "\nFront element: " << q.getFront() << endl;
    cout << "Rear element: " << q.getRear() << endl;
    cout << "Queue size: " << q.getSize() << endl;
    
    cout << "\n" << q.dequeue() << " dequeued from queue" << endl;
    cout << q.dequeue() << " dequeued from queue" << endl;
    
    q.display();
    
    cout << "\nFront element now: " << q.getFront() << endl;
    cout << "Is queue empty? " << (q.isEmpty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

**Output:**
```
10 enqueued to queue
20 enqueued to queue
30 enqueued to queue
40 enqueued to queue
50 enqueued to queue
Queue elements (front to rear): 10 20 30 40 50 

Front element: 10
Rear element: 50
Queue size: 5

10 dequeued from queue
20 dequeued from queue
Queue elements (front to rear): 30 40 50 

Front element now: 30
Is queue empty? No
```

---

### Implementation 2: Queue using Linked List

```cpp name=queue_linkedlist.cpp
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

class Queue {
    Node* front;
    Node* rear;
    int size;

public:
    Queue() {
        front = nullptr;
        rear = nullptr;
        size = 0;
    }

    // Add element to queue
    void enqueue(int x) {
        Node* newNode = new Node(x);
        
        // If queue is empty
        if (rear == nullptr) {
            front = rear = newNode;
        } else {
            rear->next = newNode;
            rear = newNode;
        }
        size++;
        cout << x << " enqueued to queue" << endl;
    }

    // Remove element from queue
    int dequeue() {
        if (isEmpty()) {
            cout << "Queue Underflow" << endl;
            return -1;
        }
        
        Node* temp = front;
        int dequeued = front->data;
        front = front->next;
        
        // If front becomes null, set rear to null too
        if (front == nullptr) {
            rear = nullptr;
        }
        
        delete temp;
        size--;
        return dequeued;
    }

    // Get front element
    int getFront() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        return front->data;
    }

    // Get rear element
    int getRear() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        return rear->data;
    }

    // Check if queue is empty
    bool isEmpty() {
        return front == nullptr;
    }

    // Get size of queue
    int getSize() {
        return size;
    }

    // Display all elements
    void display() {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }
        
        Node* temp = front;
        cout << "Queue elements (front to rear): ";
        while (temp != nullptr) {
            cout << temp->data << " ";
            temp = temp->next;
        }
        cout << endl;
    }

    // Destructor to free memory
    ~Queue() {
        while (!isEmpty()) {
            dequeue();
        }
    }
};

int main() {
    Queue q;
    
    q.enqueue(10);
    q.enqueue(20);
    q.enqueue(30);
    q.enqueue(40);
    q.enqueue(50);
    
    q.display();
    
    cout << "\nFront element: " << q.getFront() << endl;
    cout << "Rear element: " << q.getRear() << endl;
    cout << "Queue size: " << q.getSize() << endl;
    
    cout << "\n" << q.dequeue() << " dequeued from queue" << endl;
    cout << q.dequeue() << " dequeued from queue" << endl;
    
    q.display();
    
    cout << "\nFront element now: " << q.getFront() << endl;
    cout << "Is queue empty? " << (q.isEmpty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

---

### Implementation 3: Circular Queue using Array

```cpp name=circular_queue.cpp
#include <iostream>
#define MAX 5
using namespace std;

class CircularQueue {
    int front, rear;
    int arr[MAX];
    int size;

public:
    CircularQueue() {
        front = -1;
        rear = -1;
        size = 0;
    }

    // Check if queue is full
    bool isFull() {
        return size == MAX;
    }

    // Check if queue is empty
    bool isEmpty() {
        return size == 0;
    }

    // Add element to queue
    bool enqueue(int x) {
        if (isFull()) {
            cout << "Queue is Full" << endl;
            return false;
        }
        
        // First element
        if (front == -1) {
            front = 0;
        }
        
        rear = (rear + 1) % MAX;
        arr[rear] = x;
        size++;
        cout << x << " enqueued to queue" << endl;
        return true;
    }

    // Remove element from queue
    int dequeue() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        
        int dequeued = arr[front];
        
        // Queue becomes empty
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % MAX;
        }
        
        size--;
        return dequeued;
    }

    // Get front element
    int getFront() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        return arr[front];
    }

    // Get rear element
    int getRear() {
        if (isEmpty()) {
            cout << "Queue is Empty" << endl;
            return -1;
        }
        return arr[rear];
    }

    // Display all elements
    void display() {
        if (isEmpty()) {
            cout << "Queue is empty" << endl;
            return;
        }
        
        cout << "Queue elements: ";
        int i = front;
        while (true) {
            cout << arr[i] << " ";
            if (i == rear) break;
            i = (i + 1) % MAX;
        }
        cout << endl;
    }
};

int main() {
    CircularQueue q;
    
    q.enqueue(10);
    q.enqueue(20);
    q.enqueue(30);
    q.enqueue(40);
    q.enqueue(50);
    
    q.display();
    
    q.enqueue(60); // This will show queue is full
    
    cout << "\n" << q.dequeue() << " dequeued" << endl;
    cout << q.dequeue() << " dequeued" << endl;
    
    q.display();
    
    // Now we can add more elements (circular behavior)
    q.enqueue(60);
    q.enqueue(70);
    
    q.display();
    
    return 0;
}
```

---

## 🎯 Part 2: Queue STL (Standard Template Library)

The C++ STL provides `std::queue` which is a **container adapter** that uses deque by default.

### Basic Queue STL Usage

```cpp name=queue_stl_basics.cpp
#include <iostream>
#include <queue>
using namespace std;

int main() {
    // Create a queue of integers
    queue<int> q;
    
    // Check if empty
    cout << "Is queue empty? " << (q.empty() ? "Yes" : "No") << endl;
    
    // Push elements (enqueue)
    q.push(10);
    q.push(20);
    q.push(30);
    q.push(40);
    q.push(50);
    
    cout << "\nQueue size: " << q.size() << endl;
    
    // Access front and back elements
    cout << "Front element: " << q.front() << endl;
    cout << "Back element: " << q.back() << endl;
    
    // Pop elements (dequeue)
    cout << "\nDequeueing elements:" << endl;
    while (!q.empty()) {
        cout << q.front() << " ";
        q.pop();
    }
    cout << endl;
    
    cout << "\nIs queue empty now? " << (q.empty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

**Output:**
```
Is queue empty? Yes

Queue size: 5
Front element: 10
Back element: 50

Dequeueing elements:
10 20 30 40 50 

Is queue empty now? Yes
```

---

### Queue STL - All Operations

```cpp name=queue_stl_complete.cpp
#include <iostream>
#include <queue>
#include <list>
#include <deque>
using namespace std;

// Function to display queue (requires copying)
void displayQueue(queue<int> q) {
    if (q.empty()) {
        cout << "Queue is empty" << endl;
        return;
    }
    cout << "Queue elements (front to back): ";
    while (!q.empty()) {
        cout << q.front() << " ";
        q.pop();
    }
    cout << endl;
}

int main() {
    // 1. Creating queues with different underlying containers
    queue<int> q1;                    // Default (uses deque)
    queue<int, list<int>> q2;         // Using list
    queue<int, deque<int>> q3;        // Explicitly using deque
    
    // 2. Push operation (enqueue)
    cout << "=== Push Operation ===" << endl;
    q1.push(10);
    q1.push(20);
    q1.push(30);
    q1.push(40);
    q1.push(50);
    displayQueue(q1);
    
    // Refill q1 for further operations
    q1.push(10); q1.push(20); q1.push(30); q1.push(40); q1.push(50);
    
    // 3. Front and Back operations
    cout << "\n=== Front and Back Operations ===" << endl;
    cout << "Front element: " << q1.front() << endl;
    cout << "Back element: " << q1.back() << endl;
    
    // 4. Modify front and back
    cout << "\n=== Modifying Elements ===" << endl;
    q1.front() = 100;  // Modify front
    q1.back() = 500;   // Modify back
    cout << "After modification:" << endl;
    cout << "Front element: " << q1.front() << endl;
    cout << "Back element: " << q1.back() << endl;
    
    // 5. Pop operation (dequeue)
    cout << "\n=== Pop Operation ===" << endl;
    q1.pop();
    cout << "After popping front: ";
    displayQueue(q1);
    
    // Refill q1
    q1.push(10); q1.push(20); q1.push(30); q1.push(40); q1.push(50);
    
    // 6. Size operation
    cout << "\n=== Size Operation ===" << endl;
    cout << "Queue size: " << q1.size() << endl;
    
    // 7. Empty check
    cout << "\n=== Empty Check ===" << endl;
    cout << "Is queue empty? " << (q1.empty() ? "Yes" : "No") << endl;
    
    // 8. Swap operation
    cout << "\n=== Swap Operation ===" << endl;
    queue<int> q4;
    q4.push(100);
    q4.push(200);
    
    cout << "Before swap:" << endl;
    cout << "q1 front: " << q1.front() << ", size: " << q1.size() << endl;
    cout << "q4 front: " << q4.front() << ", size: " << q4.size() << endl;
    
    q1.swap(q4);
    
    cout << "After swap:" << endl;
    cout << "q1 front: " << q1.front() << ", size: " << q1.size() << endl;
    cout << "q4 front: " << q4.front() << ", size: " << q4.size() << endl;
    
    // 9. Emplace operation (C++11)
    cout << "\n=== Emplace Operation ===" << endl;
    q1.emplace(300); // Constructs element in-place
    cout << "After emplace(300), back element: " << q1.back() << endl;
    
    return 0;
}
```

---

### Queue STL with Different Data Types

```cpp name=queue_stl_datatypes.cpp
#include <iostream>
#include <queue>
#include <string>
using namespace std;

// Custom class
class Task {
public:
    string name;
    int priority;
    
    Task(string n, int p) : name(n), priority(p) {}
    
    void display() {
        cout << "Task: " << name << ", Priority: " << priority << endl;
    }
};

int main() {
    // Queue of strings
    cout << "=== Queue of Strings ===" << endl;
    queue<string> names;
    names.push("Alice");
    names.push("Bob");
    names.push("Charlie");
    names.push("David");
    
    cout << "Serving customers:" << endl;
    while (!names.empty()) {
        cout << names.front() << " ";
        names.pop();
    }
    cout << endl;
    
    // Queue of pairs
    cout << "\n=== Queue of Pairs ===" << endl;
    queue<pair<string, int>> orders;
    orders.push({"Pizza", 101});
    orders.push({"Burger", 102});
    orders.push({"Pasta", 103});
    
    cout << "Processing orders:" << endl;
    while (!orders.empty()) {
        cout << "Order " << orders.front().second << ": " 
             << orders.front().first << endl;
        orders.pop();
    }
    
    // Queue of custom objects
    cout << "\n=== Queue of Custom Objects ===" << endl;
    queue<Task> tasks;
    tasks.push(Task("Write Code", 1));
    tasks.push(Task("Review PR", 2));
    tasks.push(Task("Fix Bug", 3));
    
    cout << "Executing tasks:" << endl;
    while (!tasks.empty()) {
        tasks.front().display();
        tasks.pop();
    }
    
    return 0;
}
```

---

## 🌟 Part 3: Special Types of Queues

### 1. Priority Queue

```cpp name=priority_queue.cpp
#include <iostream>
#include <queue>
#include <vector>
using namespace std;

int main() {
    // Max heap (default) - largest element at top
    cout << "=== Max Priority Queue ===" << endl;
    priority_queue<int> maxPQ;
    
    maxPQ.push(30);
    maxPQ.push(10);
    maxPQ.push(50);
    maxPQ.push(20);
    maxPQ.push(40);
    
    cout << "Elements (highest priority first): ";
    while (!maxPQ.empty()) {
        cout << maxPQ.top() << " ";
        maxPQ.pop();
    }
    cout << endl;
    
    // Min heap - smallest element at top
    cout << "\n=== Min Priority Queue ===" << endl;
    priority_queue<int, vector<int>, greater<int>> minPQ;
    
    minPQ.push(30);
    minPQ.push(10);
    minPQ.push(50);
    minPQ.push(20);
    minPQ.push(40);
    
    cout << "Elements (lowest priority first): ";
    while (!minPQ.empty()) {
        cout << minPQ.top() << " ";
        minPQ.pop();
    }
    cout << endl;
    
    // Custom comparator for priority queue
    cout << "\n=== Custom Priority Queue ===" << endl;
    
    // Priority queue of pairs (priority, value)
    priority_queue<pair<int, string>> taskQueue;
    
    taskQueue.push({3, "Low priority task"});
    taskQueue.push({1, "High priority task"});
    taskQueue.push({2, "Medium priority task"});
    
    cout << "Tasks by priority:" << endl;
    while (!taskQueue.empty()) {
        cout << "Priority " << taskQueue.top().first 
             << ": " << taskQueue.top().second << endl;
        taskQueue.pop();
    }
    
    return 0;
}
```

---

### 2. Deque (Double-Ended Queue)

```cpp name=deque_example.cpp
#include <iostream>
#include <deque>
using namespace std;

int main() {
    deque<int> dq;
    
    // Insert at both ends
    cout << "=== Insert Operations ===" << endl;
    dq.push_back(10);    // Insert at back
    dq.push_back(20);
    dq.push_front(5);    // Insert at front
    dq.push_front(1);
    
    cout << "Deque elements: ";
    for (int x : dq) {
        cout << x << " ";
    }
    cout << endl;
    
    // Access elements
    cout << "\n=== Access Operations ===" << endl;
    cout << "Front: " << dq.front() << endl;
    cout << "Back: " << dq.back() << endl;
    cout << "Element at index 2: " << dq[2] << endl;
    cout << "Element at index 2 (using at): " << dq.at(2) << endl;
    
    // Remove from both ends
    cout << "\n=== Remove Operations ===" << endl;
    dq.pop_front();
    dq.pop_back();
    
    cout << "After removing from both ends: ";
    for (int x : dq) {
        cout << x << " ";
    }
    cout << endl;
    
    // Insert at specific position
    cout << "\n=== Insert at Position ===" << endl;
    dq.insert(dq.begin() + 1, 7); // Insert 7 at index 1
    
    cout << "After inserting 7 at index 1: ";
    for (int x : dq) {
        cout << x << " ";
    }
    cout << endl;
    
    // Erase at specific position
    cout << "\n=== Erase at Position ===" << endl;
    dq.erase(dq.begin() + 1); // Erase element at index 1
    
    cout << "After erasing at index 1: ";
    for (int x : dq) {
        cout << x << " ";
    }
    cout << endl;
    
    // Size and capacity
    cout << "\n=== Size Operations ===" << endl;
    cout << "Size: " << dq.size() << endl;
    cout << "Empty: " << (dq.empty() ? "Yes" : "No") << endl;
    
    // Clear all elements
    dq.clear();
    cout << "After clear, empty: " << (dq.empty() ? "Yes" : "No") << endl;
    
    return 0;
}
```

---

## 🚀 Part 4: Practical Problems with Queue

### Problem 1: Implement Stack using Queue

```cpp name=stack_using_queue.cpp
#include <iostream>
#include <queue>
using namespace std;

class StackUsingQueue {
    queue<int> q1, q2;

public:
    // Push operation - O(n)
    void push(int x) {
        // Push to q2
        q2.push(x);
        
        // Transfer all elements from q1 to q2
        while (!q1.empty()) {
            q2.push(q1.front());
            q1.pop();
        }
        
        // Swap q1 and q2
        swap(q1, q2);
    }
    
    // Pop operation - O(1)
    int pop() {
        if (q1.empty()) {
            cout << "Stack is empty" << endl;
            return -1;
        }
        int top = q1.front();
        q1.pop();
        return top;
    }
    
    // Top operation - O(1)
    int top() {
        if (q1.empty()) {
            cout << "Stack is empty" << endl;
            return -1;
        }
        return q1.front();
    }
    
    // Check if empty
    bool empty() {
        return q1.empty();
    }
};

int main() {
    StackUsingQueue s;
    
    s.push(10);
    s.push(20);
    s.push(30);
    s.push(40);
    
    cout << "Top element: " << s.top() << endl;
    
    cout << "Popping elements: ";
    while (!s.empty()) {
        cout << s.pop() << " ";
    }
    cout << endl;
    
    return 0;
}
```

---

### Problem 2: Implement Queue using Stack

```cpp name=queue_using_stack.cpp
#include <iostream>
#include <stack>
using namespace std;

class QueueUsingStack {
    stack<int> s1, s2;

public:
    // Enqueue operation - O(n)
    void enqueue(int x) {
        // Transfer all elements from s1 to s2
        while (!s1.empty()) {
            s2.push(s1.top());
            s1.pop();
        }
        
        // Push new element to s1
        s1.push(x);
        
        // Transfer back all elements from s2 to s1
        while (!s2.empty()) {
            s1.push(s2.top());
            s2.pop();
        }
    }
    
    // Dequeue operation - O(1)
    int dequeue() {
        if (s1.empty()) {
            cout << "Queue is empty" << endl;
            return -1;
        }
        int front = s1.top();
        s1.pop();
        return front;
    }
    
    // Front operation - O(1)
    int front() {
        if (s1.empty()) {
            cout << "Queue is empty" << endl;
            return -1;
        }
        return s1.top();
    }
    
    // Check if empty
    bool empty() {
        return s1.empty();
    }
};

int main() {
    QueueUsingStack q;
    
    q.enqueue(10);
    q.enqueue(20);
    q.enqueue(30);
    q.enqueue(40);
    
    cout << "Front element: " << q.front() << endl;
    
    cout << "Dequeueing elements: ";
    while (!q.empty()) {
        cout << q.dequeue() << " ";
    }
    cout << endl;
    
    return 0;
}
```

---

### Problem 3: First Non-Repeating Character in Stream

```cpp name=first_non_repeating.cpp
#include <iostream>
#include <queue>
#include <unordered_map>
#include <string>
using namespace std;

string firstNonRepeating(string stream) {
    queue<char> q;
    unordered_map<char, int> freq;
    string result = "";
    
    for (char ch : stream) {
        // Increase frequency
        freq[ch]++;
        
        // Add to queue
        q.push(ch);
        
        // Remove characters with frequency > 1 from front
        while (!q.empty() && freq[q.front()] > 1) {
            q.pop();
        }
        
        // If queue is empty, no non-repeating character
        if (q.empty()) {
            result += '#';
        } else {
            result += q.front();
        }
    }
    
    return result;
}

int main() {
    string stream = "aabc";
    cout << "Stream: " << stream << endl;
    cout << "First non-repeating character at each step: " 
         << firstNonRepeating(stream) << endl;
    
    stream = "aabccxb";
    cout << "\nStream: " << stream << endl;
    cout << "First non-repeating character at each step: " 
         << firstNonRepeating(stream) << endl;
    
    return 0;
}
```

---

### Problem 4: Generate Binary Numbers

```cpp name=generate_binary.cpp
#include <iostream>
#include <queue>
#include <string>
using namespace std;

void generateBinaryNumbers(int n) {
    queue<string> q;
    q.push("1");
    
    for (int i = 1; i <= n; i++) {
        string current = q.front();
        q.pop();
        
        cout << current << " ";
        
        // Generate next binary numbers by appending 0 and 1
        q.push(current + "0");
        q.push(current + "1");
    }
    cout << endl;
}

int main() {
    int n = 10;
    cout << "Binary numbers from 1 to " << n << ":" << endl;
    generateBinaryNumbers(n);
    
    return 0;
}
```

---

### Problem 5: Sliding Window Maximum

```cpp name=sliding_window_max.cpp
#include <iostream>
#include <deque>
#include <vector>
using namespace std;

vector<int> maxSlidingWindow(vector<int>& arr, int k) {
    deque<int> dq; // Store indices
    vector<int> result;
    
    for (int i = 0; i < arr.size(); i++) {
        // Remove elements outside current window
        if (!dq.empty() && dq.front() <= i - k) {
            dq.pop_front();
        }
        
        // Remove elements smaller than current element from back
        while (!dq.empty() && arr[dq.back()] < arr[i]) {
            dq.pop_back();
        }
        
        // Add current element index
        dq.push_back(i);
        
        // Add maximum to result (after first window is formed)
        if (i >= k - 1) {
            result.push_back(arr[dq.front()]);
        }
    }
    
    return result;
}

int main() {
    vector<int> arr = {1, 3, -1, -3, 5, 3, 6, 7};
    int k = 3;
    
    cout << "Array: ";
    for (int x : arr) cout << x << " ";
    cout << endl;
    
    cout << "Window size: " << k << endl;
    
    vector<int> result = maxSlidingWindow(arr, k);
    
    cout << "Maximum in each window: ";
    for (int x : result) cout << x << " ";
    cout << endl;
    
    return 0;
}
```

---

### Problem 6: Reverse First K Elements of Queue

```cpp name=reverse_k_elements.cpp
#include <iostream>
#include <queue>
#include <stack>
using namespace std;

void displayQueue(queue<int> q) {
    while (!q.empty()) {
        cout << q.front() << " ";
        q.pop();
    }
    cout << endl;
}

queue<int> reverseFirstK(queue<int> q, int k) {
    if (q.empty() || k > q.size() || k <= 0) {
        return q;
    }
    
    stack<int> s;
    
    // Push first k elements to stack
    for (int i = 0; i < k; i++) {
        s.push(q.front());
        q.pop();
    }
    
    // Enqueue elements from stack
    while (!s.empty()) {
        q.push(s.top());
        s.pop();
    }
    
    // Move remaining elements to back
    for (int i = 0; i < q.size() - k; i++) {
        q.push(q.front());
        q.pop();
    }
    
    return q;
}

int main() {
    queue<int> q;
    q.push(10);
    q.push(20);
    q.push(30);
    q.push(40);
    q.push(50);
    
    cout << "Original queue: ";
    displayQueue(q);
    
    // Refill queue
    q.push(10); q.push(20); q.push(30); q.push(40); q.push(50);
    
    int k = 3;
    q = reverseFirstK(q, k);
    
    cout << "After reversing first " << k << " elements: ";
    displayQueue(q);
    
    return 0;
}
```

---

### Problem 7: Circular Tour (Petrol Pump Problem)

```cpp name=circular_tour.cpp
#include <iostream>
#include <vector>
using namespace std;

struct PetrolPump {
    int petrol;
    int distance;
};

int circularTour(vector<PetrolPump>& pumps) {
    int n = pumps.size();
    int start = 0;
    int currentPetrol = 0;
    int deficit = 0;
    
    for (int i = 0; i < n; i++) {
        currentPetrol += pumps[i].petrol - pumps[i].distance;
        
        if (currentPetrol < 0) {
            deficit += currentPetrol;
            currentPetrol = 0;
            start = i + 1;
        }
    }
    
    // Check if total petrol is sufficient
    if (currentPetrol + deficit >= 0) {
        return start;
    }
    
    return -1; // No solution
}

int main() {
    vector<PetrolPump> pumps = {
        {4, 6},  // Pump 0: 4 petrol, 6 distance to next
        {6, 5},  // Pump 1
        {7, 3},  // Pump 2
        {4, 5}   // Pump 3
    };
    
    int start = circularTour(pumps);
    
    if (start == -1) {
        cout << "No solution exists" << endl;
    } else {
        cout << "Start at pump: " << start << endl;
    }
    
    return 0;
}
```

---

## 📊 Comparison Table

| Feature | Array Queue | Linked List Queue | Circular Queue | STL Queue | Priority Queue | Deque |
|---------|-------------|-------------------|----------------|-----------|----------------|-------|
| **Fixed Size** | Yes | No | Yes | No | No | No |
| **Memory** | Contiguous | Non-contiguous | Contiguous | Depends | Heap | Contiguous |
| **Enqueue** | O(1) | O(1) | O(1) | O(1) | O(log n) | O(1) |
| **Dequeue** | O(1) | O(1) | O(1) | O(1) | O(log n) | O(1) |
| **Access Both Ends** | No | No | No | No | No | Yes |
| **Priority** | No | No | No | No | Yes | No |
| **Best For** | Fixed size | Dynamic | Fixed circular | General use | Prioritized | Both ends |

---

## 🎓 Practice Questions

### Beginner Level:
1. Implement a queue and reverse it
2. Check if a queue is palindrome
3. Interleave first half with second half of queue
4. Implement circular queue using array

### Intermediate Level:
5. LRU Cache implementation using queue and hash map
6. Find the first circular tour that visits all petrol pumps
7. Rotten oranges problem (BFS using queue)
8. Maximum of all subarrays of size k

### Advanced Level:
9. Implement LFU (Least Frequently Used) cache
10. Design a hit counter
11. Shortest path in binary maze (BFS)
12. Design Snake game using deque

---

## ✅ Key Takeaways

1. **Queue follows FIFO** principle
2. **Main operations**: enqueue, dequeue, front, back, isEmpty, size
3. **Array implementation**: O(1) operations, fixed size
4. **Linked List implementation**: O(1) operations, dynamic size
5. **Circular Queue**: Efficient space utilization
6. **STL queue**: Easy to use, production-ready
7. **Priority Queue**: Elements ordered by priority
8. **Deque**: Insert/delete from both ends
9. **Applications**: BFS, scheduling, buffering, level-order traversal

---

Would you like me to:
1. Provide solutions to the practice questions?
2. Explain BFS (Breadth-First Search) using queues?
3. Cover advanced topics like LRU/LFU cache implementation?
4. Show how to solve graph problems using queues?
5. Teach other data structures like trees or graphs?

Let me know what you'd like to explore next! 🚀