**Expected to know:** [[Introduction to Programming]],[[Data Structures]], [[C-sharp Programming Language]]
**Topics Covered:**
**Tags:** [[Data Structures]]

We know about the most common data structures offered by each programming language.
Many programming languages offer some new type of data structure depending upon the use-case of the language itself.

Even the common data structures have different syntax sometimes.

---
# Syntax for basic Data Structures
## Arrays
Arrays in java are not reference type as the java does not support pointers, hence they are objects.
Syntax to create is quite similar to that in C#:
```java
int[] arr = new int[<arraySize>];
```

So first:
### **Declaring an Array:**
```java
DataType[] arrayName;    // Preferred, most readable 
DataType arrayName[];    // Legal, sometimes used
```
- This declares a reference to an array, but does _not_ create the array object yet. At this moment, `arrayName` refers to nothing.

And then:
### **Allocating Space for the Array:**
```java
arrayName = new DataType[N]; // N is the number of elements
```
- This uses the `new` keyword to create space for `N` elements.

So java has a root class called [Object](Object%20Class%20in%20Java), and all the arrays are objects of Object class. Their reference is stored on the heap but java does not allow user to access pointers, as it does not support pointers.

### **Initializing with Values:**
```java
int[] arr1 = {2, 35, 2, 6, 1, 3, 513, 56, 2}; // java interprets it as new int[] {}
int[] arr2 = new int[]{2, 32, 321, 5, 3, 7812, 16, 8};
```
- You can specify values at creation using curly braces `{ ... }`.
- The array size is set to the number of values listed.

---
### Accessing Array Elements (Indexing):
- Java arrays are **zero-based**: First element is at index `0`, next at `1`, etc.
- **No negative indexing:** Trying to access `arr[-1]` causes an exception.
- Example:
    ```    java
    arr = 10;      // Sets the first element 
    int x = arr[2];   // Gets the third element
```

---
## Basic Array Functions
### Array Length:
```java
arr.length
```
- This is a public field, NOT a method.
- Example:
    ```    java
    int len = arr.length; // gets size of the array
    ```
- All elements in a newly allocated Java array receive default values: `0` for numbers, `false` for booleans, and `null` for reference types.
### Copying and cloning an array
Even though java does not support working with pointers, reference type's rules still apply here, i.e, for example we have:
```java
int[] arr1 = {1,2,3,4,5,6,7,89}
```

Then we can *"copy"* the array by doing:
```java
int[] arr2 = arr1;
```
By doing this, both of them **refer to the same array** object stored on the heap, but a **true copy is not made**.

But, we can also clone the array by using `clone()` method of the Object class:
```java
int[] arr3 = (int[])arr1.clone();
```
This creates another copy of the array object that `arr1` pointed to on the heap and then assigns it to `arr3`.
`.clone()`  functions always return the array's exact copy with all the elements, but the return type is of `Object[]`, but still, if you assign the clone to the correct primitive type's array, then it is implicitly typecasted.
Hence the "compiler already knows" what is the type of the array cloned when returned by the `clone()` function.

The `clone()` function's return type is Object, but Object is like a broadest return type that java offers.

There are two types of return types in Java:
- The _static_ or _declared_ type (what the variable/return type says)
- The _dynamic_ or _actual_/runtime type (what’s really stored there)

Hence, clone method has a dynamic return value and arrays are objects of the *Object* class, so when it has an *Object* return type, it means any array or any object which is part of Object class. 
So the returned value is actually of the type what you cloned, but is actually labelled as of type Object, which it kind of is(again as arrays are objects of Object class), and will stay Object until stated otherwise.
But whenever used, they will act like the type they cloned, i.e., what they actually are.

So, When you do:
  ``` java
    int[] a = {1, 2, 3}; 
    Object b = a.clone(); // The compiler allows this
```  
`b` is of static type `Object`, but at runtime, Java knows it’s actually `int[]`.

If you do:
```java
int[] c = (int[]) b; // Allowed! b is actually an int[]
```
This is safe—Java will throw an exception only if the **actual object** is not an `int[]`, but here it is.

## Multi-dimensional Arrays


# Hash Maps
## What is a HashMap?
A **HashMap** is a data structure in Java that stores data in **key-value pairs**. Think of it like a dictionary:
- **Key**: The word you're looking up (must be unique)
- **Value**: The definition or information associated with that word

## Basic Syntax

```java
import java.util.HashMap;

// Create a HashMap
HashMap<KeyType, ValueType> mapName = new HashMap<>();
```

## Simple Example

Let's say you want to store student names and their grades:

```java
import java.util.HashMap;

public class HashMapExample {
    public static void main(String[] args) {
        // Create a HashMap to store String keys and Integer values
        HashMap<String, Integer> studentGrades = new HashMap<>();
        
        // Adding data (put method)
        studentGrades.put("Alice", 95);
        studentGrades.put("Bob", 87);
        studentGrades.put("Charlie", 92);
        
        // Getting data (get method)
        int aliceGrade = studentGrades.get("Alice");
        System.out.println("Alice's grade: " + aliceGrade); // Output: 95
        
        // Check if a key exists
        if (studentGrades.containsKey("Bob")) {
            System.out.println("Bob is in the class!");
        }
        
        // Check if a value exists
        if (studentGrades.containsValue(92)) {
            System.out.println("Someone got 92!");
        }
        
        // Remove an entry
        studentGrades.remove("Charlie");
        
        // Get the size
        System.out.println("Number of students: " + studentGrades.size());
    }
}
```

## Common HashMap Methods

| Method | What it does | Example |
|--------|-------------|---------|
| `put(key, value)` | Adds or updates a key-value pair | `map.put("Apple", 5)` |
| `get(key)` | Gets the value for a key | `map.get("Apple")` returns `5` |
| `remove(key)` | Removes a key-value pair | `map.remove("Apple")` |
| `containsKey(key)` | Checks if key exists | `map.containsKey("Apple")` |
| `containsValue(value)` | Checks if value exists | `map.containsValue(5)` |
| `size()` | Returns number of entries | `map.size()` |
| `isEmpty()` | Checks if map is empty | `map.isEmpty()` |
| `clear()` | Removes all entries | `map.clear()` |

## Looping Through a HashMap

```java
HashMap<String, Integer> fruits = new HashMap<>();
fruits.put("Apple", 5);
fruits.put("Banana", 3);
fruits.put("Orange", 7);

// Method 1: Loop through keys
for (String fruit : fruits.keySet()) {
    System.out.println(fruit + ": " + fruits.get(fruit));
}

// Method 2: Loop through key-value pairs (better!)
for (HashMap.Entry<String, Integer> entry : fruits.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// Method 3: Loop through values only
for (Integer count : fruits.values()) {
    System.out.println("Count: " + count);
}
```

## Important Things to Remember
1. **Keys must be unique**: If you add a key that already exists, it will update the value
   ```java
   map.put("Apple", 5);
   map.put("Apple", 10); // This updates the value to 10
   ```

2. **No order guarantee**: HashMap doesn't maintain any order (use `LinkedHashMap` if you need insertion order)

3. **Null values allowed**: You can have one `null` key and multiple `null` values

4. **Not synchronized**: Not thread-safe by default (use `ConcurrentHashMap` for multi-threading)

## Real-World Example
Here's a practical example - counting word occurrences:

```java
import java.util.HashMap;

public class WordCounter {
    public static void main(String[] args) {
        String sentence = "hello world hello java world";
        String[] words = sentence.split(" ");
        
        HashMap<String, Integer> wordCount = new HashMap<>();
        
        for (String word : words) {
            // If word exists, increment count; otherwise, set to 1
            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
            // Shorter way using getOrDefault:
            // wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        
        System.out.println(wordCount);
        // Output: {java=1, world=2, hello=2}
    }
}
```

## Practice Exercise for You

Try creating a HashMap that stores:
- Book titles as keys
- Authors as values

Then try:
1. Adding 3-4 books
2. Printing all books
3. Searching for a specific book
4. Removing a book

# ArrayList
`ArrayList` is a resizable array implementation in Java that's part of the Java Collections Framework. Unlike regular arrays, ArrayLists can grow and shrink dynamically. 

## Key Features
- **Dynamic sizing**:  Automatically grows when elements are added
- **Indexed access**: Fast access to elements using index
- **Allows duplicates**: Can store duplicate values
- **Ordered**:  Maintains insertion order
- **Not synchronized**: Not thread-safe by default

## Basic Usage
````java name=ArrayListBasics. java
import java.util.ArrayList;

public class ArrayListBasics {
    public static void main(String[] args) {
        // 1. Creating an ArrayList
        ArrayList<String> fruits = new ArrayList<>();
        
        // 2. Adding elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Mango");
        
        System.out.println("Fruits: " + fruits);
        
        // 3. Adding at specific index
        fruits.add(1, "Grapes");
        System.out.println("After adding Grapes at index 1: " + fruits);
        
        // 4. Getting elements
        String firstFruit = fruits.get(0);
        System.out. println("First fruit: " + firstFruit);
        
        // 5. Size of ArrayList
        System.out.println("Number of fruits: " + fruits.size());
        
        // 6. Checking if ArrayList contains an element
        boolean hasBanana = fruits.contains("Banana");
        System.out. println("Contains Banana?  " + hasBanana);
        
        // 7. Finding index of element
        int indexOfOrange = fruits.indexOf("Orange");
        System.out.println("Index of Orange: " + indexOfOrange);
    }
}
````

## Common Operations
````java name=ArrayListOperations. java
import java.util.ArrayList;
import java.util.Collections;

public class ArrayListOperations {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        
        // Adding multiple elements
        numbers.add(50);
        numbers.add(20);
        numbers.add(80);
        numbers.add(10);
        numbers.add(60);
        
        System.out.println("Original:  " + numbers);
        
        // 1. Updating/Modifying elements
        numbers. set(2, 90);  // Replace element at index 2
        System.out.println("After updating index 2: " + numbers);
        
        // 2. Removing elements
        numbers.remove(0);  // Remove by index
        System.out.println("After removing index 0: " + numbers);
        
        numbers.remove(Integer.valueOf(10));  // Remove by value
        System.out.println("After removing value 10: " + numbers);
        
        // 3. Sorting
        Collections.sort(numbers);
        System.out.println("After sorting: " + numbers);
        
        // 4. Clearing all elements
        // numbers.clear();
        
        // 5. Checking if empty
        System.out.println("Is empty?  " + numbers.isEmpty());
    }
}
````

## Iterating Through ArrayList
````java name=ArrayListIteration.java
import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListIteration {
    public static void main(String[] args) {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        
        // Method 1: For loop
        System.out.println("Using for loop:");
        for (int i = 0; i < colors.size(); i++) {
            System.out.println(colors.get(i));
        }
        
        // Method 2: Enhanced for loop (for-each)
        System.out.println("\nUsing enhanced for loop:");
        for (String color : colors) {
            System. out.println(color);
        }
        
        // Method 3: Iterator
        System.out.println("\nUsing Iterator:");
        Iterator<String> iterator = colors.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator. next());
        }
        
        // Method 4: forEach with Lambda (Java 8+)
        System.out.println("\nUsing forEach with Lambda:");
        colors.forEach(color -> System.out.println(color));
    }
}
````

## ArrayList vs Array

| Feature | Array | ArrayList |
|---------|-------|-----------|
| Size | Fixed | Dynamic |
| Type | Can store primitives & objects | Only objects (uses wrappers for primitives) |
| Performance | Slightly faster | Slightly slower due to overhead |
| Methods | Limited | Many utility methods |
| Syntax | `int[] arr = new int[5]` | `ArrayList<Integer> list = new ArrayList<>()` |

## Working with Different Data Types

````java name=ArrayListDataTypes.java
import java.util.ArrayList;

public class ArrayListDataTypes {
    public static void main(String[] args) {
        // String ArrayList
        ArrayList<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        
        // Integer ArrayList (uses wrapper class)
        ArrayList<Integer> ages = new ArrayList<>();
        ages.add(25);  // Auto-boxing:  int -> Integer
        ages.add(30);
        
        // Double ArrayList
        ArrayList<Double> prices = new ArrayList<>();
        prices.add(19.99);
        prices.add(29.99);
        
        // Boolean ArrayList
        ArrayList<Boolean> flags = new ArrayList<>();
        flags.add(true);
        flags.add(false);
        
        System.out.println("Names: " + names);
        System.out.println("Ages: " + ages);
        System.out.println("Prices: " + prices);
        System.out. println("Flags: " + flags);
    }
}
````

## Common Methods Summary

- **add(element)**: Add element at end
- **add(index, element)**: Insert element at specific index
- **get(index)**: Retrieve element at index
- **set(index, element)**: Update element at index
- **remove(index)**: Remove element at index
- **remove(object)**: Remove first occurrence of object
- **size()**: Get number of elements
- **isEmpty()**: Check if ArrayList is empty
- **contains(object)**: Check if element exists
- **indexOf(object)**: Get index of first occurrence
- **clear()**: Remove all elements
- **sort()** (via Collections): Sort the ArrayList

## Practice Exercise

Try creating a program that:
1. Creates an ArrayList of student names
2. Adds at least 5 students
3. Removes a student
4. Updates a student's name
5. Displays all students
6. Sorts the list alphabetically

# Linked Lists
A **Linked List** is a linear data structure where elements are stored in nodes.  Each node contains:
- **Data**: The actual value
- **Reference (pointer)**: Link to the next node (and previous node in doubly linked lists)

Unlike arrays, linked list elements are not stored in contiguous memory locations. 

## Types of Linked Lists
1. **Singly Linked List**: Each node points to the next node only
2. **Doubly Linked List**: Each node points to both next and previous nodes
3. **Circular Linked List**: Last node points back to the first node

## Java's Built-in LinkedList
Java provides a `LinkedList` class in the Collections Framework:

````java name=LinkedListBasics. java
import java.util.LinkedList;

public class LinkedListBasics {
    public static void main(String[] args) {
        // 1. Creating a LinkedList
        LinkedList<String> animals = new LinkedList<>();
        
        // 2. Adding elements
        animals.add("Dog");
        animals.add("Cat");
        animals.add("Horse");
        animals.add("Cow");
        
        System.out.println("Animals: " + animals);
        
        // 3. Adding at specific positions
        animals.addFirst("Lion");  // Add at beginning
        animals.addLast("Tiger");  // Add at end
        animals.add(2, "Elephant"); // Add at index
        
        System.out. println("After additions:  " + animals);
        
        // 4. Getting elements
        String first = animals.getFirst();
        String last = animals.getLast();
        String atIndex = animals.get(3);
        
        System.out.println("First: " + first);
        System.out.println("Last: " + last);
        System.out.println("At index 3: " + atIndex);
        
        // 5. Size
        System.out.println("Size: " + animals.size());
    }
}
````

## Common LinkedList Operations
````java name=LinkedListOperations.java
import java.util.LinkedList;

public class LinkedListOperations {
    public static void main(String[] args) {
        LinkedList<Integer> numbers = new LinkedList<>();
        
        // Adding elements
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        numbers.add(50);
        
        System.out.println("Original: " + numbers);
        
        // 1. Removing elements
        numbers.removeFirst();  // Remove first element
        System.out.println("After removeFirst(): " + numbers);
        
        numbers.removeLast();   // Remove last element
        System.out.println("After removeLast(): " + numbers);
        
        numbers.remove(1);      // Remove at index
        System.out.println("After remove(1): " + numbers);
        
        numbers.remove(Integer.valueOf(30));  // Remove by value
        System.out.println("After removing value 30: " + numbers);
        
        // 2. Updating elements
        numbers.add(60);
        numbers.add(70);
        numbers.set(1, 100);    // Update element at index
        System.out. println("After set(1, 100): " + numbers);
        
        // 3. Checking contents
        boolean contains = numbers.contains(100);
        System.out.println("Contains 100? " + contains);
        
        int index = numbers.indexOf(70);
        System.out.println("Index of 70: " + index);
        
        // 4. Peek operations (view without removing)
        System.out. println("Peek first: " + numbers.peekFirst());
        System.out.println("Peek last: " + numbers.peekLast());
        
        // 5. Poll operations (retrieve and remove)
        System.out. println("Poll first: " + numbers.pollFirst());
        System.out.println("After poll:  " + numbers);
    }
}
````

## Iterating Through LinkedList
````java name=LinkedListIteration.java
import java. util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;

public class LinkedListIteration {
    public static void main(String[] args) {
        LinkedList<String> cities = new LinkedList<>();
        cities.add("New York");
        cities.add("London");
        cities.add("Tokyo");
        cities.add("Paris");
        
        // Method 1: For loop
        System.out.println("Using for loop:");
        for (int i = 0; i < cities. size(); i++) {
            System.out.println(cities.get(i));
        }
        
        // Method 2: Enhanced for loop
        System.out.println("\nUsing enhanced for loop:");
        for (String city : cities) {
            System.out.println(city);
        }
        
        // Method 3: Iterator (forward)
        System.out.println("\nUsing Iterator:");
        Iterator<String> iterator = cities.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        // Method 4: Descending Iterator (backward)
        System.out.println("\nUsing Descending Iterator:");
        Iterator<String> descIterator = cities.descendingIterator();
        while (descIterator.hasNext()) {
            System.out.println(descIterator.next());
        }
        
        // Method 5: ListIterator (bidirectional)
        System.out.println("\nUsing ListIterator (backward):");
        ListIterator<String> listIterator = cities. listIterator(cities.size());
        while (listIterator.hasPrevious()) {
            System.out. println(listIterator.previous());
        }
        
        // Method 6: forEach with Lambda
        System.out.println("\nUsing forEach:");
        cities.forEach(city -> System.out.println(city));
    }
}
````

## LinkedList as Queue and Deque
LinkedList implements both `Queue` and `Deque` interfaces:

````java name=LinkedListAsQueue.java
import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;

public class LinkedListAsQueue {
    public static void main(String[] args) {
        // LinkedList as Queue (FIFO - First In First Out)
        System.out.println("=== LinkedList as Queue ===");
        Queue<String> queue = new LinkedList<>();
        
        queue.offer("First");   // Add to queue
        queue.offer("Second");
        queue.offer("Third");
        
        System.out.println("Queue: " + queue);
        System.out.println("Peek: " + queue.peek());     // View front
        System.out.println("Poll: " + queue.poll());     // Remove front
        System.out.println("After poll: " + queue);
        
        // LinkedList as Deque (Double-ended queue)
        System.out.println("\n=== LinkedList as Deque ===");
        Deque<String> deque = new LinkedList<>();
        
        deque.offerFirst("Front");    // Add to front
        deque.offerLast("Back");      // Add to back
        deque.offerFirst("New Front");
        
        System.out.println("Deque: " + deque);
        System.out.println("Poll First: " + deque.pollFirst());
        System.out.println("Poll Last: " + deque.pollLast());
        System.out.println("After polls: " + deque);
    }
}
````

(**`.poll()`** is a method that **retrieves AND removes** the first element from a LinkedList (or any Queue). It's commonly used when treating a LinkedList as a Queue, it is in ADT of Queue)

## ArrayList vs LinkedList

| Feature | ArrayList | LinkedList |
|---------|-----------|------------|
| Internal Structure | Dynamic array | Doubly linked list |
| Access Time | O(1) - Fast | O(n) - Slow (must traverse) |
| Insertion/Deletion (middle) | O(n) - Slow (shifting) | O(1) - Fast (once position found) |
| Insertion/Deletion (ends) | O(1) amortized | O(1) - Very fast |
| Memory | Less overhead | More (stores references) |
| Best for | Random access, iteration | Frequent insertions/deletions |

## Custom Singly Linked List Implementation
Here's how to create your own linked list from scratch:

````java name=CustomLinkedList.java
class Node {
    int data;
    Node next;
    
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class CustomLinkedList {
    Node head;
    
    // Add node at the end
    public void add(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
            return;
        }
        
        Node current = head;
        while (current.next != null) {
            current = current. next;
        }
        current.next = newNode;
    }
    
    // Add node at the beginning
    public void addFirst(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }
    
    // Add node at specific position
    public void addAt(int index, int data) {
        if (index == 0) {
            addFirst(data);
            return;
        }
        
        Node newNode = new Node(data);
        Node current = head;
        
        for (int i = 0; i < index - 1 && current != null; i++) {
            current = current.next;
        }
        
        if (current == null) {
            System.out.println("Index out of bounds");
            return;
        }
        
        newNode.next = current.next;
        current.next = newNode;
    }
    
    // Delete first node
    public void deleteFirst() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        head = head.next;
    }
    
    // Delete last node
    public void deleteLast() {
        if (head == null) {
            System.out. println("List is empty");
            return;
        }
        
        if (head.next == null) {
            head = null;
            return;
        }
        
        Node current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        current.next = null;
    }
    
    // Delete node at specific position
    public void deleteAt(int index) {
        if (head == null) {
            System. out.println("List is empty");
            return;
        }
        
        if (index == 0) {
            deleteFirst();
            return;
        }
        
        Node current = head;
        for (int i = 0; i < index - 1 && current. next != null; i++) {
            current = current.next;
        }
        
        if (current.next == null) {
            System.out.println("Index out of bounds");
            return;
        }
        
        current.next = current.next.next;
    }
    
    // Search for a value
    public boolean search(int data) {
        Node current = head;
        while (current != null) {
            if (current. data == data) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    // Get size of list
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
    
    // Display the list
    public void display() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current. next;
        }
        System.out.println("null");
    }
    
    // Reverse the linked list
    public void reverse() {
        Node prev = null;
        Node current = head;
        Node next = null;
        
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }
    
    public static void main(String[] args) {
        CustomLinkedList list = new CustomLinkedList();
        
        // Adding elements
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        
        System.out.println("Original list:");
        list.display();
        
        // Add at beginning
        list.addFirst(5);
        System.out.println("\nAfter addFirst(5):");
        list.display();
        
        // Add at position
        list.addAt(3, 25);
        System.out.println("\nAfter addAt(3, 25):");
        list.display();
        
        // Search
        System.out.println("\nSearch for 25:  " + list.search(25));
        System.out.println("Search for 100: " + list.search(100));
        
        // Size
        System.out.println("\nSize: " + list.size());
        
        // Delete operations
        list.deleteFirst();
        System.out.println("\nAfter deleteFirst():");
        list.display();
        
        list.deleteLast();
        System.out.println("\nAfter deleteLast():");
        list.display();
        
        list.deleteAt(2);
        System.out. println("\nAfter deleteAt(2):");
        list.display();
        
        // Reverse
        list.reverse();
        System.out.println("\nAfter reverse():");
        list.display();
    }
}
````

## Custom Doubly Linked List Implementation
````java name=DoublyLinkedList.java
class DNode {
    int data;
    DNode next;
    DNode prev;
    
    DNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

class DoublyLinkedList {
    DNode head;
    
    // Add at end
    public void add(int data) {
        DNode newNode = new DNode(data);
        
        if (head == null) {
            head = newNode;
            return;
        }
        
        DNode current = head;
        while (current. next != null) {
            current = current.next;
        }
        
        current.next = newNode;
        newNode.prev = current;
    }
    
    // Add at beginning
    public void addFirst(int data) {
        DNode newNode = new DNode(data);
        
        if (head == null) {
            head = newNode;
            return;
        }
        
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }
    
    // Display forward
    public void displayForward() {
        if (head == null) {
            System.out. println("List is empty");
            return;
        }
        
        DNode current = head;
        System.out.print("Forward: null <- ");
        while (current != null) {
            System.out. print(current.data + " <-> ");
            current = current. next;
        }
        System.out.println("null");
    }
    
    // Display backward
    public void displayBackward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        DNode current = head;
        while (current. next != null) {
            current = current.next;
        }
        
        System.out. print("Backward: null <- ");
        while (current != null) {
            System.out. print(current.data + " <-> ");
            current = current. prev;
        }
        System.out.println("null");
    }
    
    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();
        
        list.add(10);
        list.add(20);
        list.add(30);
        list.addFirst(5);
        
        list.displayForward();
        list.displayBackward();
    }
}
````

## Common LinkedList Methods Summary

**Adding:**
- `add(element)` - Add at end
- `addFirst(element)` - Add at beginning
- `addLast(element)` - Add at end
- `add(index, element)` - Add at position

**Removing:**
- `remove()` / `removeFirst()` - Remove first
- `removeLast()` - Remove last
- `remove(index)` - Remove at position
- `remove(object)` - Remove by value

**Accessing:**
- `get(index)` - Get element at index
- `getFirst()` - Get first element
- `getLast()` - Get last element
- `peek()` / `peekFirst()` - View first without removing
- `peekLast()` - View last without removing

**Other:**
- `size()` - Get size
- `contains(object)` - Check if exists
- `clear()` - Remove all elements
- `set(index, element)` - Update element

## Practice Exercises

Try creating programs that:
1. Implement a music playlist using LinkedList
2. Create a browser history (back/forward) using LinkedList
3. Implement a stack using LinkedList
4. Find the middle element of a linked list
5. Detect if a linked list has a cycle

# Vector
I'd be happy to teach you about **Vector** in Java! Here's a comprehensive guide:

## What is Vector? 

**Vector** is a legacy class from Java 1.0 that implements a dynamic array, similar to ArrayList. It's part of the Java Collections Framework and is **synchronized** (thread-safe).

## Key Features

- **Dynamic sizing**: Grows automatically when needed
- **Synchronized**: Thread-safe (all methods are synchronized)
- **Legacy class**: Part of original Java (before Collections Framework)
- **Indexed access**: Fast random access using index
- **Allows duplicates**: Can store duplicate values
- **Ordered**:  Maintains insertion order

## Creating a Vector

````java name=VectorBasics.java
import java.util.Vector;

public class VectorBasics {
    public static void main(String[] args) {
        // 1. Default constructor (initial capacity 10)
        Vector<String> vector1 = new Vector<>();
        
        // 2. With initial capacity
        Vector<String> vector2 = new Vector<>(20);
        
        // 3. With initial capacity and increment
        Vector<String> vector3 = new Vector<>(10, 5);  // capacity 10, grows by 5
        
        // 4. From another collection
        Vector<String> vector4 = new Vector<>(vector1);
        
        System.out.println("Vector created successfully!");
        System.out.println("Initial capacity of vector2: " + vector2.capacity());
    }
}
````

## Basic Operations

````java name=VectorOperations. java
import java.util.Vector;

public class VectorOperations {
    public static void main(String[] args) {
        Vector<String> fruits = new Vector<>();
        
        // 1. Adding elements
        fruits. add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Mango");
        
        System. out.println("Fruits: " + fruits);
        System.out.println("Size: " + fruits.size());
        System.out.println("Capacity: " + fruits.capacity());
        
        // 2. Adding at specific index
        fruits.add(1, "Grapes");
        System.out. println("\nAfter adding Grapes at index 1: " + fruits);
        
        // 3. Adding at first and last position
        fruits.addElement("Strawberry");  // Legacy method (same as add)
        fruits.insertElementAt("Kiwi", 0);  // Insert at beginning
        System.out.println("After insertions: " + fruits);
        
        // 4. Getting elements
        String firstFruit = fruits.get(0);
        String elementAt = fruits.elementAt(2);  // Legacy method
        String firstElement = fruits.firstElement();
        String lastElement = fruits.lastElement();
        
        System.out.println("\nFirst fruit (get): " + firstFruit);
        System.out.println("Element at 2: " + elementAt);
        System.out.println("First element: " + firstElement);
        System.out.println("Last element: " + lastElement);
        
        // 5. Checking contents
        boolean hasApple = fruits.contains("Apple");
        int indexOfOrange = fruits.indexOf("Orange");
        
        System.out. println("\nContains Apple?  " + hasApple);
        System.out.println("Index of Orange: " + indexOfOrange);
        
        // 6. Size and capacity
        System.out. println("\nSize: " + fruits.size());
        System.out.println("Capacity: " + fruits.capacity());
        System.out.println("Is empty?  " + fruits.isEmpty());
    }
}
````

**Output:**
```
Fruits: [Apple, Banana, Orange, Mango]
Size: 4
Capacity: 10

After adding Grapes at index 1: [Apple, Grapes, Banana, Orange, Mango]
After insertions: [Kiwi, Apple, Grapes, Banana, Orange, Mango, Strawberry]

First fruit (get): Kiwi
Element at 2: Grapes
First element: Kiwi
Last element: Strawberry

Contains Apple? true
Index of Orange: 4

Size: 7
Capacity: 10
Is empty? false
```

## Modifying and Removing Elements

````java name=VectorModification.java
import java.util. Vector;

public class VectorModification {
    public static void main(String[] args) {
        Vector<Integer> numbers = new Vector<>();
        
        // Adding elements
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        numbers.add(50);
        
        System.out.println("Original: " + numbers);
        
        // 1. Updating elements
        numbers.set(2, 35);  // Update element at index 2
        System.out.println("\nAfter set(2, 35): " + numbers);
        
        numbers.setElementAt(15, 0);  // Legacy method
        System.out.println("After setElementAt(15, 0): " + numbers);
        
        // 2. Removing elements
        numbers.remove(1);  // Remove by index
        System.out.println("\nAfter remove(1): " + numbers);
        
        numbers.remove(Integer.valueOf(40));  // Remove by value
        System.out.println("After removing value 40: " + numbers);
        
        numbers.removeElementAt(0);  // Legacy method
        System.out.println("After removeElementAt(0): " + numbers);
        
        // 3. Remove first and last
        numbers.add(100);
        numbers.add(200);
        System.out.println("\nBefore removing first/last: " + numbers);
        
        numbers.removeElement(35);  // Remove first occurrence
        System.out.println("After removeElement(35): " + numbers);
        
        // 4. Remove all elements
        // numbers.clear();
        // numbers.removeAllElements();  // Legacy method (same as clear)
        
        // 5. Remove range (from index 0 to 1, exclusive of 1)
        numbers.add(300);
        System.out.println("\nBefore removeRange:  " + numbers);
        // Note: removeRange is protected, need to subclass to use it
    }
}
````

## Iterating Through Vector

````java name=VectorIteration.java
import java.util.Vector;
import java.util.Iterator;
import java.util. Enumeration;
import java.util.ListIterator;

public class VectorIteration {
    public static void main(String[] args) {
        Vector<String> colors = new Vector<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        colors.add("Purple");
        
        // Method 1: For loop
        System.out.println("Using for loop:");
        for (int i = 0; i < colors.size(); i++) {
            System.out.println(colors.get(i));
        }
        
        // Method 2: Enhanced for loop
        System.out.println("\nUsing enhanced for loop:");
        for (String color : colors) {
            System.out.println(color);
        }
        
        // Method 3: Iterator
        System.out.println("\nUsing Iterator:");
        Iterator<String> iterator = colors.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        // Method 4: ListIterator (bidirectional)
        System.out.println("\nUsing ListIterator (backward):");
        ListIterator<String> listIterator = colors.listIterator(colors.size());
        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous());
        }
        
        // Method 5: Enumeration (Legacy - Vector specific)
        System.out.println("\nUsing Enumeration (legacy):");
        Enumeration<String> enumeration = colors. elements();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
        
        // Method 6: forEach with Lambda
        System.out.println("\nUsing forEach:");
        colors.forEach(color -> System.out.println(color));
    }
}
````

## Capacity Management

````java name=VectorCapacity.java
import java.util.Vector;

public class VectorCapacity {
    public static void main(String[] args) {
        // Initial capacity 5, increment by 3
        Vector<Integer> vector = new Vector<>(5, 3);
        
        System.out.println("Initial capacity: " + vector.capacity());
        System.out.println("Initial size: " + vector.size());
        
        // Add elements to see capacity growth
        for (int i = 1; i <= 10; i++) {
            vector. add(i * 10);
            System.out.println("Added " + (i * 10) + 
                             " | Size: " + vector.size() + 
                             " | Capacity: " + vector.capacity());
        }
        
        // Manually increase capacity
        System.out.println("\n--- Manual Capacity Management ---");
        vector.ensureCapacity(20);
        System.out.println("After ensureCapacity(20): " + vector. capacity());
        
        // Trim to current size
        vector.trimToSize();
        System.out.println("After trimToSize(): " + vector.capacity());
        System.out.println("Size:  " + vector.size());
    }
}
````

**Output:**
```
Initial capacity: 5
Initial size: 0
Added 10 | Size: 1 | Capacity: 5
Added 20 | Size:  2 | Capacity: 5
Added 30 | Size:  3 | Capacity: 5
Added 40 | Size: 4 | Capacity:  5
Added 50 | Size: 5 | Capacity: 5
Added 60 | Size: 6 | Capacity: 8
Added 70 | Size:  7 | Capacity: 8
Added 80 | Size: 8 | Capacity:  8
Added 90 | Size: 9 | Capacity: 11
Added 100 | Size:  10 | Capacity: 11

--- Manual Capacity Management ---
After ensureCapacity(20): 20
After trimToSize(): 10
Size: 10
```

## Vector Methods Summary

### Adding Elements
| Method | Description |
|--------|-------------|
| `add(element)` | Add element at end |
| `add(index, element)` | Insert at specific index |
| `addElement(element)` | Legacy - same as add() |
| `insertElementAt(element, index)` | Legacy - insert at index |
| `addAll(collection)` | Add all elements from collection |

### Removing Elements
| Method | Description |
|--------|-------------|
| `remove(index)` | Remove element at index |
| `remove(object)` | Remove first occurrence of object |
| `removeElement(object)` | Legacy - remove by object |
| `removeElementAt(index)` | Legacy - remove by index |
| `clear()` | Remove all elements |
| `removeAllElements()` | Legacy - clear all |
| `removeAll(collection)` | Remove all matching elements |

### Accessing Elements
| Method | Description |
|--------|-------------|
| `get(index)` | Get element at index |
| `elementAt(index)` | Legacy - get at index |
| `firstElement()` | Get first element |
| `lastElement()` | Get last element |
| `elements()` | Returns Enumeration |

### Other Methods
| Method | Description |
|--------|-------------|
| `set(index, element)` | Update element at index |
| `setElementAt(element, index)` | Legacy - update |
| `size()` | Number of elements |
| `capacity()` | Current capacity |
| `isEmpty()` | Check if empty |
| `contains(object)` | Check if contains |
| `indexOf(object)` | Find index |
| `ensureCapacity(minCapacity)` | Increase capacity |
| `trimToSize()` | Trim to current size |

## ArrayList vs Vector

````java name=ArrayListVsVector.java
import java. util.ArrayList;
import java. util.Vector;

public class ArrayListVsVector {
    public static void main(String[] args) {
        // ArrayList - Not synchronized
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Item 1");
        arrayList. add("Item 2");
        
        // Vector - Synchronized
        Vector<String> vector = new Vector<>();
        vector.add("Item 1");
        vector.add("Item 2");
        
        System.out.println("ArrayList: " + arrayList);
        System.out.println("Vector: " + vector);
        
        // Capacity growth
        System.out.println("\n--- Growth Pattern ---");
        
        ArrayList<Integer> al = new ArrayList<>(5);
        Vector<Integer> v = new Vector<>(5);
        
        // ArrayList grows by 50% (5 -> 7 -> 10)
        // Vector doubles by default (5 -> 10 -> 20)
        
        for (int i = 0; i < 6; i++) {
            al.add(i);
            v.add(i);
        }
        
        System.out.println("ArrayList capacity growth: ~1.5x");
        System.out.println("Vector capacity growth: 2x (or custom increment)");
    }
}
````

## Comparison Table:  ArrayList vs Vector

| Feature | ArrayList | Vector |
|---------|-----------|--------|
| **Synchronization** | Not synchronized | Synchronized (thread-safe) |
| **Performance** | Faster | Slower (due to synchronization) |
| **Thread Safety** | Not thread-safe | Thread-safe |
| **Growth Rate** | 50% increase | 100% increase (doubles) |
| **Legacy** | Modern (Java 1.2) | Legacy (Java 1.0) |
| **Iteration** | Iterator, forEach | Iterator, forEach, Enumeration |
| **Null Values** | Allowed | Allowed |
| **Use Case** | Single-threaded apps | Multi-threaded apps |

## Thread Safety Example

````java name=VectorThreadSafety.java
import java.util.Vector;
import java.util.ArrayList;

public class VectorThreadSafety {
    public static void main(String[] args) throws InterruptedException {
        // Vector is thread-safe
        Vector<Integer> vector = new Vector<>();
        
        // ArrayList is NOT thread-safe
        ArrayList<Integer> arrayList = new ArrayList<>();
        
        // Create threads that add elements
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                vector.add(i);
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                vector.add(i);
            }
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println("Vector size: " + vector.size());
        System.out.println("Vector is thread-safe, so size = 2000");
        
        // Note: ArrayList in similar scenario might have inconsistent size
        // due to race conditions
    }
}
````

## Stack Using Vector

````java name=VectorStack.java
import java. util.Vector;

public class VectorStack {
    public static void main(String[] args) {
        Vector<String> stack = new Vector<>();
        
        // Push (add to end)
        stack.add("First");
        stack.add("Second");
        stack.add("Third");
        
        System.out.println("Stack: " + stack);
        
        // Pop (remove from end)
        if (!stack.isEmpty()) {
            String top = stack.remove(stack.size() - 1);
            System.out.println("Popped: " + top);
            System.out.println("Stack after pop: " + stack);
        }
        
        // Peek (view last without removing)
        if (!stack. isEmpty()) {
            String peek = stack.lastElement();
            System.out. println("Peek: " + peek);
            System.out.println("Stack unchanged: " + stack);
        }
    }
}
````

## When to Use Vector? 

### ✅ Use Vector When:
- You need thread-safety and don't want to manually synchronize
- Working with legacy code that uses Vector
- Simple multi-threaded scenarios

### ❌ Avoid Vector When:
- Single-threaded applications (use ArrayList instead - faster)
- High-performance requirements (synchronization overhead)
- Modern multi-threaded apps (use `Collections.synchronizedList()` or `CopyOnWriteArrayList`)

## Modern Alternative to Vector

````java name=ModernAlternative.java
import java. util.ArrayList;
import java. util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModernAlternative {
    public static void main(String[] args) {
        // Instead of Vector, use: 
        
        // 1. Synchronized ArrayList (better than Vector)
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add("Item 1");
        syncList.add("Item 2");
        System.out.println("Synchronized ArrayList: " + syncList);
        
        // 2. CopyOnWriteArrayList (best for read-heavy scenarios)
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
        cowList.add("Item 1");
        cowList.add("Item 2");
        System.out.println("CopyOnWriteArrayList: " + cowList);
        
        // 3. Regular ArrayList for single-threaded
        ArrayList<String> regularList = new ArrayList<>();
        regularList. add("Item 1");
        regularList.add("Item 2");
        System.out.println("Regular ArrayList: " + regularList);
    }
}
````

## Complete Example: Student Management

````java name=StudentVector.java
import java.util. Vector;

class Student {
    String name;
    int rollNo;
    double marks;
    
    Student(String name, int rollNo, double marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', rollNo=" + rollNo + ", marks=" + marks + "}";
    }
}

public class StudentVector {
    public static void main(String[] args) {
        Vector<Student> students = new Vector<>(5, 2);
        
        // Adding students
        students.add(new Student("Alice", 101, 85.5));
        students.add(new Student("Bob", 102, 78.0));
        students.add(new Student("Charlie", 103, 92.5));
        students.add(new Student("Diana", 104, 88.0));
        
        System.out.println("=== All Students ===");
        for (Student student : students) {
            System.out.println(student);
        }
        
        // Find student by roll number
        System.out.println("\n=== Search for Roll No 103 ===");
        for (Student student : students) {
            if (student.rollNo == 103) {
                System. out.println("Found: " + student);
                break;
            }
        }
        
        // Remove student
        students.removeIf(s -> s.rollNo == 102);
        System.out.println("\n=== After removing Roll No 102 ===");
        students.forEach(System.out::println);
        
        // Update student marks
        for (Student student : students) {
            if (student.rollNo == 101) {
                student. marks = 90.0;
                System.out.println("\n=== Updated Alice's marks ===");
                System.out.println(student);
            }
        }
        
        System.out.println("\nTotal students: " + students.size());
        System.out.println("Current capacity: " + students.capacity());
    }
}
````

## Summary

**Key Takeaways:**
1. Vector is a **synchronized** (thread-safe) dynamic array
2. **Slower** than ArrayList due to synchronization overhead
3. **Legacy class** - mostly replaced by ArrayList
4. Supports **Enumeration** (old-style iteration)
5. **Doubles in size** by default when capacity is exceeded
6. Use **ArrayList** for single-threaded apps
7. Use **Collections.synchronizedList()** or **CopyOnWriteArrayList** for modern multi-threaded apps

Would you like me to explain any specific concept in more detail, or compare Vector with Stack class (which extends Vector)?