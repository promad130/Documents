## Table of Contents
1. [Introduction to ADT (Abstract Data Type)](#Abstract%20Data%20Type)
2. [Overview of Java Collection Framework](#overview-of-java-collection-framework)
3. [Collection Framework Architecture](#collection-framework-architecture)
4. [Core Interfaces](#core-interfaces)
5. [ADT: Stack](#adt-stack)
6. [ADT: Queue](#adt-queue)
7. [ADT: List and Linear Lists](#adt-list-and-linear-lists)
8. [List Implementations: ArrayList and LinkedList](#list-implementations)
9. [Set Collections](#set-collections)
10. [Map Collections](#map-collections)
11. [Iterators and Iteration](#iterators-and-iteration)
12. [Sorting and Ordering](#sorting-and-ordering)
13. [Comparators and Custom Ordering](#comparators-and-custom-ordering)
14. [Complete Working Examples](#complete-working-examples)

# Abstract Data Type
## What is an Abstract Data Type (ADT)
An **Abstract Data Type (ADT)** is a theoretical concept that defines:
1. **Data** - What data is stored (like, a Data Structure)
2. **Operations** - What operations can be performed on that data (called Data Structure Design)
3. **Implementation hiding** - HOW the data is stored and operations work is hidden from users

Think of it like a TV remote: You know what buttons to press (operations), but you don't need to know the internal circuitry (implementation).

## Key Characteristics of ADTs
- **Encapsulation**: Hide implementation details
- **Data Abstraction**: Focus on WHAT, not HOW
- **Interface**: Defines operations available to users
- **Information Hiding**: Internal representation is hidden

## ADTs vs Concrete Data Types

| Abstract Data Type | Concrete Implementation   |
| ------------------ | ------------------------- |
| List               | ArrayList, LinkedList     |
| Set                | HashSet, TreeSet          |
| Map                | HashMap, TreeMap          |
| Queue              | LinkedList, PriorityQueue |
| Stack              | Stack, ArrayDeque         |

## Implementing ADTs in Java
Java provides several ways to create ADTs:
### 1. Using Interfaces
### 2. Using Abstract Classes
### 3. Using Classes with Encapsulation

# Examples of ADT:
## Stack
A **Stack** is a linear data structure that follows the **LIFO (Last In, First Out)** principle. Think of it like a stack of plates - you add plates on top and remove from the top.

Example, Stack of books, Stack of Plates, Deck of Cards, Browser back button, etc,.

### **Stack Operations**

| Operation      | Description                    | Time Complexity |
| -------------- | ------------------------------ | --------------- |
| **push(item)** | Add item to top                | O(1)            |
| **pop()**      | Remove and return top item     | O(1)            |
| **peek()**     | View top item without removing | O(1)            |
| **isEmpty()**  | Check if stack is empty        | O(1)            |
| **size()**     | Get number of elements         | O(1)            |

Example:
```text
push(10) → push(20) → push(30) → pop() → peek()

  Empty     [10]      [10]       [10]      [10]
                      [20]       [20]      [20]
                      [30]       
            ↑ top     ↑ top      ↑ top     ↑ top
                                (30 removed) (view 20)
```

## Queue
A Queue is a data structure that follows the **FIFO (First In, First Out)** principle. Think of it like a line at a ticket counter - the first person who enters the line is the first one to be served. 
In Java, Queue is an **interface** that's part of the Java Collections Framework.

### Why Do We Need Queues?
Queues are useful when you need to:
- Process elements in the order they arrive
- Handle tasks like printer job scheduling
- Implement breadth-first search in graphs
- Manage requests in web servers

### Key Queue Methods
| Method       | What it does                      | Returns on failure |
| ------------ | --------------------------------- | ------------------ |
| `offer(E e)` | Adds element to queue             | `false`            |
| `add(E e)`   | Adds element to queue, Enqueue    | Throws exception   |
| `poll()`     | Removes and returns head          | `null`             |
| `remove()`   | Removes and returns head, Dequeue | Throws exception   |
| `peek()`     | Views head without removing       | `null`             |
| `element()`  | Views head without removing       | Throws exception   |
Now here, as we can see, there are two types for each operation, the intention was that when working with bounded queues, i.e., queues with max capacity, the return type is can be expected to be false, but when working with unbounded queues, it is not expected for the add operations to fail, hence should return an error.


# Overview of Java Collection Framework
### Purpose
The Java Collections Framework is a unified architecture for representing and manipulating groups of objects. It provides:

1. **Standardized interfaces** for common data structures
2. **Multiple implementations** optimized for different use cases
3. **Algorithms** that work with all collection types
4. **High performance** with efficient implementations
5. **Code reusability** through a common interface

### Benefits
- **Reduces Development Time**: No need to implement your own data structures
- **Increases Code Quality**: Well-tested, optimized implementations
- **Interoperability**: Common interfaces enable easy switching between implementations
- **Type Safety**: Generic types ensure compile-time safety
- **Performance**: Optimized implementations chosen based on your needs

### Core Concept: The Collection Hierarchy
```
Object
  ├── Iterable (interface)
  │   └── Collection (interface)
  │       ├── List (interface)
  │       │   ├── ArrayList (class)
  │       │   ├── LinkedList (class)
  │       │   └── Vector (class) [Legacy]
  │       │
  │       ├── Set (interface)
  │       │   ├── HashSet (class)
  │       │   ├── LinkedHashSet (class)
  │       │   ├── TreeSet (class)
  │       │   └── SortedSet (interface)
  │       │
  │       └── Queue (interface)
  │           ├── PriorityQueue (class)
  │           ├── LinkedList (class)
  │           └── Deque (interface)
  │
  └── Map (interface)
      ├── HashMap (class)
      ├── TreeMap (class)
      ├── LinkedHashMap (class)
      ├── Hashtable (class) [Legacy]
      └── SortedMap (interface)
```

---
# Collection Framework Architecture
### The Interfaces Layer
The framework is built on several core interfaces:

#### 1. **Collection Interface** (Root Interface)
Base interface for all collection types (except Map).

**Methods**:
```java
// Adding elements
boolean add(Object obj)                    // Add single element
boolean addAll(Collection c)               // Add all from another collection

// Removing elements
boolean remove(Object obj)                 // Remove an occurrence
boolean removeAll(Collection c)            // Remove all elements in c
void clear()                               // Remove all elements

// Querying
boolean contains(Object obj)               // Check if element exists
boolean containsAll(Collection c)          // Check if all from c exist
int size()                                 // Number of elements
boolean isEmpty()                          // Check if empty

// Conversion
Object[] toArray()                         // Convert to array
Iterator iterator()                        // Get iterator

// Comparison
boolean equals(Object obj)                 // Check equality
int hashCode()                             // Get hash code
```

#### 2. **List Interface**
Extends Collection. Represents an **ordered collection** where elements are stored with indices (0-based).

**Key Characteristics**:
- **Ordered**: Maintains insertion order
- **Allows duplicates**: Can contain duplicate elements
- **Index-based access**: Elements accessed by position

**Additional Methods Beyond Collection**:
```java
// Index-based access
Object get(int index)                      // Get element at index
Object set(int index, Object obj)          // Replace element at index

// Index-based manipulation
void add(int index, Object obj)            // Insert at specific position
boolean addAll(int index, Collection c)    // Insert all at position
Object remove(int index)                   // Remove by index

// Searching
int indexOf(Object obj)                    // First occurrence index (-1 if not found)
int lastIndexOf(Object obj)                // Last occurrence index

// Sublist
List subList(int start, int end)          // Get portion of list

// Iteration
ListIterator listIterator()                // Get list iterator
ListIterator listIterator(int index)       // Get iterator starting at index
```

#### 3. **Set Interface**
Extends Collection. Represents a **collection with unique elements**.

**Key Characteristics**:
- **No duplicates**: Cannot contain duplicate elements
- **Unordered**: No guaranteed order (except TreeSet and LinkedHashSet)
- **Mathematical set semantics**: Supports set operations

**Key Methods** (inherited from Collection, with modified behavior):
```java
// add() returns false if element already exists
// All other methods work as in Collection
```

**Subtypes**:
- **SortedSet**: Elements maintained in sorted order
- **NavigableSet**: Extended navigation capabilities

#### 4. **SortedSet Interface**
Extends Set. Elements are kept in **ascending sorted order**.

**Methods**:
```java
Comparator comparator()                    // Get comparator used (null = natural order)
Object first()                             // Get first element
Object last()                              // Get last element
SortedSet headSet(Object toElement)        // Get elements < toElement
SortedSet tailSet(Object fromElement)      // Get elements >= fromElement
SortedSet subSet(Object from, Object to)   // Get elements in range
```

#### 5. **Map Interface**
Represents a collection of **key-value pairs**.

**Key Characteristics**:
- **Key-based lookup**: Retrieve values using keys
- **Unique keys**: Each key maps to at most one value
- **Multiple values with same value**: Multiple keys can map to same value

**Methods**:
```java
// Putting and getting
Object put(Object key, Object value)       // Add or replace
/*
Summary
	Returns the previous value, or null if none was there, 
	note that if the previous value was null, it will throw NullPointerException
	note that if the key-value pair is not compactible, 
	then it will throw ClassCastException
*/
Object get(Object key)                     // Get value by key
/*SUMMARY
	returns null if the map contains no mapping for that key
*/
void putAll(Map m)                         // Add all entries from m
Object remove(Object key)                  // Remove entry by key
void clear()                               // Remove all entries

// Querying
boolean containsKey(Object key)            // Check if key exists
boolean containsValue(Object value)        // Check if value exists
int size()                                 // Number of entries
boolean isEmpty()                          // Check if empty

// Views
Set keySet()                               // Get all keys as Set
Collection values()                        // Get all values as Collection
Set entrySet()                             // Get all entries as Set (Map.Entry objects)
```

#### 6. **SortedMap Interface**
Extends Map. Keys maintained in **sorted order**.

**Methods**:
```java
Comparator comparator()                    // Get comparator used
Object firstKey()                          // Get first key
Object lastKey()                           // Get last key
SortedMap headMap(Object endKey)           // Entries with keys < endKey
SortedMap tailMap(Object startKey)         // Entries with keys >= startKey
SortedMap subMap(Object start, Object end) // Entries in key range
```

#### 7. **Queue Interface**
Extends Collection. Represents a **FIFO (First-In-First-Out) collection**.

**Methods**:
```java
// Adding - throws exception if fails
boolean add(Object obj)

// Adding - returns false if fails (preferred)
boolean offer(Object obj)

// Removing - throws exception if queue empty
Object remove()

// Removing - returns null if queue empty (preferred)
Object poll()

// Viewing - throws exception if queue empty
Object element()

// Viewing - returns null if queue empty (preferred)
Object peek()
```

#### 8. **Deque Interface**
Extends Queue. Represents a **double-ended queue** (can add/remove from both ends).

**Methods** (select ones):
```java
// First element
void addFirst(Object obj)
void addLast(Object obj)
Object removeFirst()
Object removeLast()
Object getFirst()
Object getLast()

// Duplicate values allowed
boolean removeFirstOccurrence(Object obj)
boolean removeLastOccurrence(Object obj)
```

---
## ADT: Stack
### What is a Stack?

A Stack is an ADT where elements follow **LIFO (Last-In-First-Out)** principle:
- Last element added is the first one removed
- Like a stack of plates: you add and remove from the top

### Stack ADT Definition
**Data**: Ordered collection of elements

**Operations**:
- `push(item)`: Add element to top
- `pop()`: Remove and return top element
- `peek()`: View top element without removing
- `isEmpty()`: Check if empty
- `size()`: Get number of elements

### Implementation in Java

#### Using the Stack Class (Legacy)
```java
import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        
        // Push elements
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Stack: " + stack);
        
        // Peek - view top without removing
        System.out.println("Top element: " + stack.peek());
        
        // Pop - remove and return top
        System.out.println("Popped: " + stack.pop());
        System.out.println("Stack after pop: " + stack);
        
        // Check if empty
        System.out.println("Is empty: " + stack.isEmpty());
        System.out.println("Size: " + stack.size());
        
        // Pop remaining elements
        while (!stack.isEmpty()) {
            System.out.println("Popped: " + stack.pop());
        }
    }
}
```

**Output**:
```
Stack: [10, 20, 30]
Top element: 30
Popped: 30
Stack after pop: [10, 20]
Is empty: false
Size: 2
Popped: 20
Popped: 10
```

#### Using LinkedList (Preferred Modern Approach)
```java
import java.util.Deque;
import java.util.LinkedList;

public class StackUsingDeque {
    public static void main(String[] args) {
        Deque<String> stack = new LinkedList<>();
        
        // Push
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        
        // Pop
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
```

**Output**:
```
Third
Second
First
```

### Real-World Use Cases

1. **Expression Evaluation**: Checking balanced parentheses
2. **Undo/Redo Functionality**: Browser history
3. **Function Call Stack**: Compiler implementation
4. **Depth-First Search (DFS)**: Graph traversal

### Example: Balanced Parentheses Checker
```java
import java.util.Stack;

public class ParenthesesChecker {
    static boolean isBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : expression.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                
                char top = stack.pop();
                if (!matches(top, c)) return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    static boolean matches(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
               (opening == '[' && closing == ']') ||
               (opening == '{' && closing == '}');
    }
    
    public static void main(String[] args) {
        System.out.println(isBalanced("({[()]}")); // true
        System.out.println(isBalanced("({[}]}")); // false
    }
}
```

---
## ADT: Queue
### What is a Queue?
A Queue is an ADT where elements follow **FIFO (First-In-First-Out)** principle:
- First element added is the first one removed
- Like a real-world queue at a store: serve customers in order they arrive

### Queue ADT Definition
**Data**: Ordered collection of elements

**Operations**:
- `enqueue(item)` / `add(item)`: Add element at rear
- `dequeue()` / `remove()`: Remove element from front
- `front()` / `peek()`: View front element
- `isEmpty()`: Check if empty
- `size()`: Get number of elements

### Implementation in Java

#### Using Queue Interface with LinkedList

```java
import java.util.Queue;
import java.util.LinkedList;

public class QueueDemo {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        
        // Enqueue (add)
        queue.add("First");
        queue.add("Second");
        queue.add("Third");
        System.out.println("Queue: " + queue);
        
        // Peek - view front
        System.out.println("Front: " + queue.peek());
        
        // Dequeue (remove)
        System.out.println("Dequeued: " + queue.remove());
        System.out.println("Queue after removal: " + queue);
        
        // Size and isEmpty
        System.out.println("Size: " + queue.size());
        System.out.println("Is empty: " + queue.isEmpty());
        
        // Dequeue all
        while (!queue.isEmpty()) {
            System.out.println("Dequeued: " + queue.remove());
        }
    }
}
```

**Output**:
```
Queue: [First, Second, Third]
Front: First
Dequeued: First
Queue after removal: [Second, Third]
Size: 2
Is empty: false
Dequeued: Second
Dequeued: Third
```
(Check for custom implementation at [[Data Structures in Java#Custom Singly Linked List Implementation]])

#### Exception vs. Return Value Methods
Queue provides two sets of methods:

| Operation | Throws Exception | Returns Special Value |
|-----------|------------------|----------------------|
| Add | `add(obj)` | `offer(obj)` |
| Remove | `remove()` | `poll()` |
| View | `element()` | `peek()` |

```java
// Preferred approach - uses return values
Queue<Integer> q = new LinkedList<>();
q.offer(1);        // Add - returns false if fails
q.offer(2);
Integer num = q.poll();  // Remove - returns null if empty
Integer first = q.peek(); // View - returns null if empty
```

### Using PriorityQueue
Elements are ordered by **natural ordering** or a **custom Comparator**.
```java
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        Queue<Integer> pq = new PriorityQueue<>();
        
        // Add elements (not in order)
        pq.add(5);
        pq.add(1);
        pq.add(3);
        pq.add(2);
        pq.add(4);
        
        // Remove elements (will be in sorted order)
        while (!pq.isEmpty()) {
            System.out.print(pq.remove() + " ");
        }
        // Output: 1 2 3 4 5
    }
}
```

### Real-World Use Cases
1. **Task Scheduling**: Process tasks in order they arrive
2. **Print Queue**: Send documents to printer in order
3. **CPU Scheduling**: Process management
4. **Breadth-First Search (BFS)**: Graph traversal
5. **Load Balancing**: Distribute work across servers

### Example: Simple Queue Implementation
```java
public class CustomQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }
    
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }
    
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        T data = front.data;
        front = front.next;
        size--;
        if (isEmpty()) rear = null;
        return data;
    }
    
    public T peek() {
        if (isEmpty()) return null;
        return front.data;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
}
```

---
## ADT: List and Linear Lists
### What is a List?
A List is an ADT that represents an **ordered collection** with:
- **Positional access**: Access elements by index (0-based)
- **Flexibility**: Insert/remove at any position
- **Duplicates allowed**: Can contain duplicate elements

### List ADT Definition
**Data**: Ordered sequence of elements, accessible by position

**Operations**:
- `add(element)`: Append at end
- `add(index, element)`: Insert at specific position
- `get(index)`: Get element at position
- `set(index, element)`: Replace element at position
- `remove(index)`: Remove element at position
- `indexOf(element)`: Find position of element
- `size()`: Get number of elements
- `isEmpty()`: Check if empty
- `iterator()`: Get iterator
- `listIterator()`: Get list iterator (bidirectional)

### General Linear Lists
A linear list is a finite sequence of elements where:
- Elements have a linear ordering
- Each element (except first) has a unique predecessor
- Each element (except last) has a unique successor

**Examples**:
- `[10, 20, 30, 40]` - linear list of integers
- `["apple", "banana", "cherry"]` - linear list of strings
- Empty list `[]` - also a linear list

### List Interface in Java
```java
import java.util.List;
import java.util.ArrayList;

public class ListInterfaceDemo {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();
        
        // Add elements
        fruits.add("Apple");      // Add at end
        fruits.add("Banana");
        fruits.add(1, "Mango");   // Insert at index 1
        
        // Access elements
        System.out.println("Get index 0: " + fruits.get(0));
        System.out.println("Get index 1: " + fruits.get(1));
        
        // Modify element
        fruits.set(0, "Orange");
        System.out.println("After set: " + fruits);
        
        // Find position
        System.out.println("Index of Banana: " + fruits.indexOf("Banana"));
        
        // Remove element
        fruits.remove(1);
        System.out.println("After remove: " + fruits);
        
        // Query
        System.out.println("Size: " + fruits.size());
        System.out.println("Is empty: " + fruits.isEmpty());
        
        // Sublist
        System.out.println("Sublist 0 to 2: " + fruits.subList(0, 2));
    }
}
```

**Output**:
```
Get index 0: Apple
Get index 1: Mango
After set: [Orange, Mango, Banana]
Index of Banana: 2
After remove: [Orange, Banana]
Size: 2
Is empty: false
Sublist 0 to 2: [Orange, Banana]
```

---
## List Implementations: ArrayList and LinkedList
### ArrayList
**Internal Structure**: Dynamic array that grows as needed

**Characteristics**:
- **Fast access**: O(1) to get/set by index
- **Slower insertion/deletion**: O(n) in middle
- **Slow expansion**: Copying array when capacity exceeded
- **Memory efficient**: Stores only data
- **Good for**: Frequent reads, few structural changes

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        
        // Adding elements
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        System.out.println("ArrayList: " + numbers);
        
        // Random access is fast
        System.out.println("Element at index 1: " + numbers.get(1));
        
        // Insertion in middle
        numbers.add(1, 15);
        System.out.println("After insert at 1: " + numbers);
        
        // Capacity operations
        ArrayList<Integer> al = new ArrayList<>(5);
        al.ensureCapacity(10);  // Ensure minimum capacity
        al.trimToSize();        // Trim to actual size
        
        // Iteration
        for (Integer num : numbers) {
            System.out.print(num + " ");
        }
    }
}
```

**Output**:
```
ArrayList: [10, 20, 30]
Element at index 1: 20
After insert at 1: [10, 15, 20, 30]
10 15 20 30
```

### LinkedList

**Internal Structure**: Doubly-linked list with nodes

**Characteristics**:
- **Slow access**: O(n) to get/set by index
- **Fast insertion/deletion**: O(1) if position known
- **Constant expansion**: No array copying needed
- **Extra memory**: Stores pointers for next/previous
- **Good for**: Frequent insertions/deletions, particularly at ends

```java
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        
        // Adding elements
        list.add("First");
        list.add("Second");
        list.add("Third");
        System.out.println("LinkedList: " + list);
        
        // Insertion (efficient anywhere)
        list.add(1, "Middle");
        System.out.println("After insert: " + list);
        
        // Access (slower than ArrayList)
        System.out.println("Element at 1: " + list.get(1));
        
        // LinkedList specific operations
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        
        ll.addFirst(0);          // Add to beginning
        ll.addLast(4);           // Add to end
        System.out.println("After addFirst/addLast: " + ll);
        
        System.out.println("First: " + ll.getFirst());
        System.out.println("Last: " + ll.getLast());
        
        System.out.println("Removed first: " + ll.removeFirst());
        System.out.println("Removed last: " + ll.removeLast());
        System.out.println("After removals: " + ll);
    }
}
```

**Output**:
```
LinkedList: [First, Second, Third]
After insert: [First, Middle, Second, Third]
Element at 1: Middle
After addFirst/addLast: [0, 1, 2, 3, 4]
First: 0
Last: 4
Removed first: 0
Removed last: 4
After removals: [1, 2, 3]
```

### ArrayList vs LinkedList Comparison

| Operation | ArrayList | LinkedList |
|-----------|-----------|------------|
| Get by index | O(1) | O(n) |
| Add at end | O(1) amortized | O(1) |
| Add at beginning | O(n) | O(1) |
| Add in middle | O(n) | O(n) |
| Remove at end | O(1) | O(1) |
| Remove at beginning | O(n) | O(1) |
| Remove in middle | O(n) | O(n) |
| Memory overhead | Low | High |

**When to use**:
- **ArrayList**: When you need fast random access (reading mostly)
- **LinkedList**: When you need fast insertions/deletions (especially at ends)

### Vector (Legacy)
Vector is an older, synchronized version of ArrayList. **Don't use in new code**.

```java
import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>();
        
        // Same methods as ArrayList
        v.add(10);
        v.add(20);
        v.add(30);
        
        // Legacy methods
        v.addElement(40);      // Instead of add()
        v.removeElement(10);   // Instead of remove()
        System.out.println("Vector: " + v);
    }
}
```

---
## List Iterator
### ListIterator Interface
ListIterator is a more powerful iterator specifically for Lists.

**Differences from Iterator**:
- **Bidirectional**: Can iterate forward and backward
- **Position awareness**: Know index position
- **Modification**: Can add elements during iteration

**Methods**:
```java
// Forward traversal
boolean hasNext()
Object next()
int nextIndex()

// Backward traversal
boolean hasPrevious()
Object previous()
int previousIndex()

// Modification
void add(Object obj)           // Insert before cursor
void set(Object obj)           // Replace current element
void remove()                  // Remove current element
```

### Using ListIterator
```java
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class ListIteratorDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        
        System.out.println("Original list: " + numbers);
        
        // Forward iteration
        System.out.println("\nForward:");
        ListIterator<Integer> iter = numbers.listIterator();
        while (iter.hasNext()) {
            int num = iter.next();
            System.out.println("Index: " + iter.previousIndex() + ", Value: " + num);
            
            // Modify during iteration
            if (num == 20) {
                iter.set(25);  // Replace 20 with 25
            }
        }
        System.out.println("After set: " + numbers);
        
        // Backward iteration
        System.out.println("\nBackward:");
        while (iter.hasPrevious()) {
            int num = iter.previous();
            System.out.println("Index: " + iter.nextIndex() + ", Value: " + num);
        }
        
        // Insert using ListIterator
        System.out.println("\nInserting elements:");
        iter = numbers.listIterator();
        iter.next();
        iter.next();
        iter.add(22);   // Insert 22 between 20 and 30
        System.out.println("After add: " + numbers);
    }
}
```

**Output**:
```
Original list: [10, 20, 30, 40]

Forward:
Index: 0, Value: 10
Index: 1, Value: 20
Index: 2, Value: 30
Index: 3, Value: 40
After set: [10, 25, 30, 40]

Backward:
Index: 3, Value: 40
Index: 2, Value: 30
Index: 1, Value: 25
Index: 0, Value: 10

Inserting elements:
After add: [10, 25, 22, 30, 40]
```

---
## Set Collections
### HashSet
**Characteristics**:
- **Unordered**: No guaranteed order
- **Fast operations**: O(1) for add/remove/contains
- **Uses hashing**: Hash table internally
- **No duplicates**: Duplicate elements are ignored
- **Null allowed**: Can contain one null element

```java
import java.util.Set;
import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        Set<String> colors = new HashSet<>();
        
        // Add elements
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Red");  // Duplicate - ignored
        
        System.out.println("HashSet: " + colors);
        System.out.println("Size: " + colors.size());  // 3, not 4
        
        // Check membership
        System.out.println("Contains Red: " + colors.contains("Red"));
        
        // Remove
        colors.remove("Green");
        System.out.println("After remove: " + colors);
        
        // Iteration (unordered)
        for (String color : colors) {
            System.out.println(color);
        }
    }
}
```

**Typical Output**:
```
HashSet: [Red, Blue, Green]
Size: 3
Contains Red: true
After remove: [Red, Blue]
Red
Blue
```

### LinkedHashSet
**Characteristics**:
- **Ordered by insertion**: Maintains insertion order
- **Extends HashSet**: Same operations, but ordered
- **Slightly slower**: Extra linked list overhead
- **Predictable iteration order**: Same as insertion order

```java
import java.util.Set;
import java.util.LinkedHashSet;

public class LinkedHashSetDemo {
    public static void main(String[] args) {
        Set<String> colors = new LinkedHashSet<>();
        
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        
        System.out.println("LinkedHashSet: " + colors);
        // Output: [Red, Green, Blue] - insertion order preserved!
        
        // Iteration order is insertion order
        for (String color : colors) {
            System.out.println(color);
        }
    }
}
```

**Output**:
```
LinkedHashSet: [Red, Green, Blue]
Red
Green
Blue
```

### TreeSet
**Characteristics**:
- **Sorted order**: Elements kept in natural order (or custom)
- **NavigableSet**: Supports navigation methods
- **Slower operations**: O(log n) instead of O(1)
- **Uses red-black tree**: Balanced tree structure
- **No null allowed**: Cannot contain null
- Implements SortedSet interface

```java
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        SortedSet<Integer> numbers = new TreeSet<>();
        
        // Add elements (not in order)
        numbers.add(50);
        numbers.add(10);
        numbers.add(30);
        numbers.add(20);
        numbers.add(40);
        
        System.out.println("TreeSet: " + numbers);  // Sorted!
        // Output: [10, 20, 30, 40, 50]
        
        // SortedSet operations
        System.out.println("First: " + numbers.first());  // 10
        System.out.println("Last: " + numbers.last());    // 50
        
        System.out.println("Head (< 30): " + numbers.headSet(30));
        System.out.println("Tail (>= 30): " + numbers.tailSet(30));
        System.out.println("Sub (20 to 40): " + numbers.subSet(20, 40));
    }
}
```

**Output**:
```
TreeSet: [10, 20, 30, 40, 50]
First: 10
Last: 50
Head (< 30): [10, 20]
Tail (>= 30): [30, 40, 50]
Sub (20 to 40): [20, 30]
```

### Set Operations Example
```java
import java.util.HashSet;
import java.util.Set;

public class SetOperations {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        
        Set<Integer> set2 = new HashSet<>();
        set2.add(3);
        set2.add(4);
        set2.add(5);
        set2.add(6);
        
        // Union
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union);  // [1, 2, 3, 4, 5, 6]
        
        // Intersection
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection);  // [3, 4]
        
        // Difference
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference: " + difference);  // [1, 2]
    }
}
```

---
## Map Collections
### Overview of Maps
Maps store **key-value pairs** where:
- **Keys are unique**: Each key maps to at most one value
- **Values can duplicate**: Multiple keys can map to same value
- **Fast lookup**: O(1) average for HashMap, O(log n) for TreeMap

### HashMap
**Characteristics**:
- **Unordered**: No guaranteed order
- **Fast operations**: O(1) average
- **Hash table**: Uses hashing internally
- **Null key/value**: Allows one null key and null values

```java
import java.util.Map;
import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> ages = new HashMap<>();
        
        // Put key-value pairs
        ages.put("Alice", 25);
        ages.put("Bob", 30);
        ages.put("Charlie", 35);
        
        System.out.println("HashMap: " + ages);
        
        // Get value by key
        System.out.println("Alice's age: " + ages.get("Alice"));
        
        // Check key/value
        System.out.println("Contains Bob: " + ages.containsKey("Bob"));
        System.out.println("Contains 30: " + ages.containsValue(30));
        
        // Update value
        ages.put("Alice", 26);
        System.out.println("Updated: " + ages.get("Alice"));
        
        // Remove
        ages.remove("Bob");
        System.out.println("After remove: " + ages);
        
        // Size and empty
        System.out.println("Size: " + ages.size());
        System.out.println("Is empty: " + ages.isEmpty());
        
        // Get all keys, values, entries
        System.out.println("Keys: " + ages.keySet());
        System.out.println("Values: " + ages.values());
        System.out.println("Entries: " + ages.entrySet());
    }
}
```

**Output**:
```
HashMap: {Alice=25, Charlie=35, Bob=30}
Alice's age: 25
Contains Bob: true
Contains 30: true
Updated: 26
After remove: {Alice=26, Charlie=35}
Size: 2
Is empty: false
Keys: [Alice, Charlie]
Values: [26, 35]
Entries: [Alice=26, Charlie=35]
```

### Iterating Over Map
```java
import java.util.Map;
import java.util.HashMap;

public class MapIteration {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 5);
        map.put("Banana", 3);
        map.put("Cherry", 7);
        
        // Method 1: Iterate over entries (most efficient)
        System.out.println("Using entrySet:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // Method 2: Iterate over keys
        System.out.println("\nUsing keySet:");
        for (String key : map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }
        
        // Method 3: Iterate over values
        System.out.println("\nUsing values:");
        for (Integer value : map.values()) {
            System.out.println(value);
        }
        
        // Method 4: Using iterator
        System.out.println("\nUsing iterator:");
        Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### TreeMap
**Characteristics**:
- **Sorted by key**: Keys maintained in natural order
- **NavigableMap**: Navigation methods available
- **Slower operations**: O(log n)
- **Red-black tree**: Balanced tree internally
- **No null key**: Cannot have null key

```java
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        SortedMap<String, Integer> map = new TreeMap<>();
        
        map.put("Zebra", 1);
        map.put("Apple", 2);
        map.put("Mango", 3);
        map.put("Banana", 4);
        
        System.out.println("TreeMap: " + map);  // Sorted by key!
        // {Apple=2, Banana=4, Mango=3, Zebra=1}
        
        // SortedMap operations
        System.out.println("First key: " + map.firstKey());      // Apple
        System.out.println("Last key: " + map.lastKey());        // Zebra
        
        System.out.println("Head (< Mango): " + map.headMap("Mango"));
        System.out.println("Tail (>= Mango): " + map.tailMap("Mango"));
        System.out.println("Sub (Banana to Mango): " + 
                          map.subMap("Banana", "Mango"));
    }
}
```

**Output**:
```
TreeMap: {Apple=2, Banana=4, Mango=3, Zebra=1}
First key: Apple
Last key: Zebra
Head (< Mango): {Apple=2, Banana=4}
Tail (>= Mango): {Mango=3, Zebra=1}
Sub (Banana to Mango): {Banana=4}
```

### LinkedHashMap

**Characteristics**:
- **Insertion order**: Maintains insertion order
- **Predictable iteration**: Same as insertion order
- **Similar to HashMap**: But with ordering

```java
import java.util.Map;
import java.util.LinkedHashMap;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
        
        map.put("Zebra", 1);
        map.put("Apple", 2);
        map.put("Mango", 3);
        
        System.out.println("LinkedHashMap: " + map);
        // {Zebra=1, Apple=2, Mango=3} - insertion order preserved!
        
        for (String key : map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }
    }
}
```

**Output**:
```
LinkedHashMap: {Zebra=1, Apple=2, Mango=3}
Zebra: 1
Apple: 2
Mango: 3
```

---
## Iterators and Iteration
### Iterator Interface
**Purpose**: Provide a unified way to traverse any collection

**Methods**:
```java
boolean hasNext()    // Check if more elements
Object next()        // Get next element
void remove()        // Remove current element
```

### Using Iterator

```java
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDemo {
    public static void main(String[] args) {
        Collection<String> animals = new ArrayList<>();
        animals.add("Dog");
        animals.add("Cat");
        animals.add("Bird");
        animals.add("Fish");
        
        // Get iterator
        Iterator<String> iter = animals.iterator();
        
        // Traverse forward
        System.out.println("Forward traversal:");
        while (iter.hasNext()) {
            String animal = iter.next();
            System.out.println(animal);
            
            // Safe removal during iteration
            if (animal.equals("Cat")) {
                iter.remove();
            }
        }
        
        System.out.println("\nAfter removal: " + animals);
    }
}
```

**Output**:
```
Forward traversal:
Dog
Cat
Bird
Fish

After removal: [Dog, Bird, Fish]
```

### Enhanced For Loop vs Iterator

```java
// Enhanced for loop (safer, cleaner)
for (String item : collection) {
    System.out.println(item);
    // Can't modify collection during iteration
}

// Iterator (allows modification)
Iterator<String> iter = collection.iterator();
while (iter.hasNext()) {
    String item = iter.next();
    System.out.println(item);
    if (item.equals("target")) {
        iter.remove();  // Safe removal
    }
}
```

---
## Sorting and Ordering
### Natural Ordering
Objects must implement `Comparable` interface.

```java
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public int compareTo(Person other) {
        // Compare by age (ascending)
        if (this.age < other.age) return -1;
        if (this.age > other.age) return 1;
        return 0;
        
        // Or simply: return Integer.compare(this.age, other.age);
    }
    
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

public class ComparableDemo {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        
        System.out.println("Before sorting: " + people);
        
        Collections.sort(people);
        System.out.println("After sorting: " + people);
        
        // TreeSet also uses natural ordering
        SortedSet<Person> set = new TreeSet<>(people);
        System.out.println("TreeSet: " + set);
    }
}
```

**Output**:
```
Before sorting: [Alice (30), Bob (25), Charlie (35)]
After sorting: [Bob (25), Alice (30), Charlie (35)]
TreeSet: [Bob (25), Alice (30), Charlie (35)]
```

### Custom Comparators
Use `Comparator` for custom ordering without modifying class.

```java
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
    
    public int getAge() { return age; }
    public String getName() { return name; }
}

public class ComparatorDemo {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        
        System.out.println("Original: " + people);
        
        // Sort by age descending
        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p2.getAge(), p1.getAge());  // Reversed
            }
        });
        System.out.println("Sorted by age descending: " + people);
        
        // Sort by name ascending (using lambda)
        Collections.sort(people, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        System.out.println("Sorted by name: " + people);
        
        // Using TreeSet with custom comparator
        SortedSet<Person> set = new TreeSet<>((p1, p2) -> 
            Integer.compare(p2.getAge(), p1.getAge()));
        set.addAll(people);
        System.out.println("TreeSet by age desc: " + set);
    }
}
```

**Output**:
```
Original: [Alice (30), Bob (25), Charlie (35)]
Sorted by age descending: [Charlie (35), Alice (30), Bob (25)]
Sorted by name: [Alice (30), Bob (25), Charlie (35)]
TreeSet by age desc: [Charlie (35), Alice (30), Bob (25)]
```

### Sorting Collections
```java
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class SortingDemo {
    public static void main(String[] args) {
        // Sort a list
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));
        System.out.println("Before: " + numbers);
        Collections.sort(numbers);
        System.out.println("After: " + numbers);
        
        // Reverse sort
        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("Reverse: " + numbers);
        
        // Other Collection algorithms
        Collections.reverse(numbers);
        System.out.println("After reverse: " + numbers);
        
        Collections.shuffle(numbers);
        System.out.println("After shuffle: " + numbers);
        
        System.out.println("Min: " + Collections.min(numbers));
        System.out.println("Max: " + Collections.max(numbers));
        
        Collections.swap(numbers, 0, 4);
        System.out.println("After swap(0,4): " + numbers);
    }
}
```

---
## Comparators and Custom Ordering
### Creating Custom Comparators
```java
import java.util.Comparator;

// Multiple comparator examples
public class CustomComparators {
    
    // String comparators
    public static class CaseInsensitiveComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }
    
    // Reverse comparator
    public static class ReverseComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return b.compareTo(a);
        }
    }
    
    // Complex object comparator (chained)
    public static class PersonMultiComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            // First compare by age
            int ageCompare = Integer.compare(p1.getAge(), p2.getAge());
            if (ageCompare != 0) return ageCompare;
            
            // If ages equal, compare by name
            return p1.getName().compareTo(p2.getName());
        }
    }
}

// Using comparators in TreeMap and TreeSet
public class CustomOrderingDemo {
    public static void main(String[] args) {
        // TreeMap with custom ordering (reverse alphabetical)
        Map<String, Integer> map = new TreeMap<>(
            (s1, s2) -> s2.compareTo(s1)  // Reverse order
        );
        
        map.put("Apple", 5);
        map.put("Zebra", 1);
        map.put("Mango", 3);
        
        System.out.println("TreeMap (reverse): " + map);
        // Output: {Zebra=1, Mango=3, Apple=5}
        
        // TreeSet with multiple criteria
        Set<Person> people = new TreeSet<>(new PersonMultiComparator());
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 30));
        people.add(new Person("Charlie", 25));
        
        System.out.println("Sorted by age, then name: " + people);
    }
}
```

---

## Complete Working Examples

### Example 1: Student Grade Management System

```java
import java.util.*;

class Student implements Comparable<Student> {
    private String name;
    private int studentId;
    private double gpa;
    
    public Student(String name, int id, double gpa) {
        this.name = name;
        this.studentId = id;
        this.gpa = gpa;
    }
    
    public String getName() { return name; }
    public int getStudentId() { return studentId; }
    public double getGpa() { return gpa; }
    
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.studentId, other.studentId);
    }
    
    @Override
    public String toString() {
        return String.format("%s (ID: %d, GPA: %.2f)", name, studentId, gpa);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return this.studentId == other.studentId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(studentId);
    }
}

public class StudentManagementSystem {
    public static void main(String[] args) {
        // List to maintain insertion order
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Alice", 101, 3.8));
        studentList.add(new Student("Bob", 102, 3.5));
        studentList.add(new Student("Charlie", 103, 3.9));
        studentList.add(new Student("Diana", 104, 3.2));
        
        // Set for unique students
        Set<Student> uniqueStudents = new HashSet<>(studentList);
        System.out.println("Unique students: " + uniqueStudents.size());
        
        // Map for quick lookup
        Map<Integer, Student> studentMap = new HashMap<>();
        for (Student s : studentList) {
            studentMap.put(s.getStudentId(), s);
        }
        
        System.out.println("Student with ID 102: " + 
                          studentMap.get(102));
        
        // TreeSet to sort by ID
        SortedSet<Student> sortedById = new TreeSet<>(studentList);
        System.out.println("\nStudents sorted by ID:");
        for (Student s : sortedById) {
            System.out.println("  " + s);
        }
        
        // Sort by GPA (custom comparator)
        List<Student> sortedByGpa = new ArrayList<>(studentList);
        sortedByGpa.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
        System.out.println("\nStudents sorted by GPA (descending):");
        for (Student s : sortedByGpa) {
            System.out.println("  " + s);
        }
        
        // TreeMap: Student ID -> Student info
        SortedMap<Integer, Student> sortedMap = new TreeMap<>(studentMap);
        System.out.println("\nTreeMap of students:");
        for (Map.Entry<Integer, Student> entry : sortedMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
```

**Output**:
```
Unique students: 4
Student with ID 102: Bob (ID: 102, GPA: 3.50)

Students sorted by ID:
  Alice (ID: 101, GPA: 3.80)
  Bob (ID: 102, GPA: 3.50)
  Charlie (ID: 103, GPA: 3.90)
  Diana (ID: 104, GPA: 3.20)

Students sorted by GPA (descending):
  Charlie (ID: 103, GPA: 3.90)
  Alice (ID: 101, GPA: 3.80)
  Bob (ID: 102, GPA: 3.50)
  Diana (ID: 104, GPA: 3.20)

TreeMap of students:
  101 -> Alice (ID: 101, GPA: 3.80)
  102 -> Bob (ID: 102, GPA: 3.50)
  103 -> Charlie (ID: 103, GPA: 3.90)
  104 -> Diana (ID: 104, GPA: 3.20)
```

### Example 2: Expression Evaluation Using Stack

```java
import java.util.Stack;

public class ExpressionEvaluator {
    static boolean isBalanced(String expr) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : expr.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                char open = stack.pop();
                if (!matches(open, c)) return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    static boolean matches(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
    
    public static void main(String[] args) {
        String[] expressions = {
            "({[()]})"}",
            "({[}])}",
            "((()))",
            "([)]",
            "{[()]}",
            ""
        };
        
        for (String expr : expressions) {
            System.out.println(expr + " -> " + 
                             (isBalanced(expr) ? "Balanced" : "Not balanced"));
        }
    }
}
```

**Output**:
```
({[()]}) -> Balanced
({[}]} -> Not balanced
((())) -> Balanced
([)] -> Not balanced
{[()]} -> Balanced
 -> Balanced
```

---

## Best Practices and Tips

1. **Choose the right collection**:
   - **ArrayList**: Frequent reads, few writes
   - **LinkedList**: Frequent insertions/deletions at ends
   - **HashSet**: Fast lookup, no order needed
   - **TreeSet**: Need sorted order
   - **HashMap**: Key-value lookup
   - **TreeMap**: Need sorted keys

2. **Use generics**: Always specify type parameters
   ```java
   List<String> list = new ArrayList<>();  // Good
   List list = new ArrayList();             // Bad - no type safety
   ```

3. **Use enhanced for loops when possible**:
   ```java
   for (String item : list) { ... }        // Good
   for (int i = 0; i < list.size(); i++) { ... }  // Less clean
   ```

4. **Use iterators for removal during iteration**:
   ```java
   Iterator<String> iter = list.iterator();
   while (iter.hasNext()) {
       if (iter.next().equals("target")) {
           iter.remove();  // Safe removal
       }
   }
   ```

5. **Immutable views for safety**:
   ```java
   Collections.unmodifiableList(list)
   Collections.unmodifiableSe Set(set)
   Collections.unmodifiableMap(map)
   ```

6. **Synchronized collections**:
   ```java
   Collections.synchronizedList(list)
   Collections.synchronizedSet(set)
   Collections.synchronizedMap(map)
   ```

---

## Summary

The Java Collection Framework provides a comprehensive, efficient, and easy-to-use way to work with groups of objects. Understanding:

- **ADTs** (what the data structure should do)
- **Interfaces** (the contracts)
- **Implementations** (how it works internally)
- **Algorithms** (sorting, searching, etc.)

...enables you to choose the right tool for the job and write efficient, maintainable code.

### Quick Reference Table

| Collection | Order | Duplicates | Null | Speed | Use When |
|------------|-------|-----------|------|-------|----------|
| ArrayList | Insertion | Yes | Yes | O(1) access | Need fast random access |
| LinkedList | Insertion | Yes | Yes | O(n) access | Need fast insertion/deletion |
| HashSet | None | No | 1 | O(1) avg | Need unique elements, fast lookup |
| TreeSet | Sorted | No | No | O(log n) | Need sorted unique elements |
| HashMap | None | No values | 1 key | O(1) avg | Need key-value mapping |
| TreeMap | Sorted keys | No values | No key | O(log n) | Need sorted key-value mapping |
| Stack | LIFO | Yes | Yes | O(1) | Need LIFO behavior |
| Queue | FIFO | Yes | Yes | O(1) | Need FIFO behavior |
