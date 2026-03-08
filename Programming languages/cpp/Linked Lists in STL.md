

C++ provides two distinct linked list containers. Here is everything you need to know to use them effectively.

---

## 1. `std::list` (The Doubly Linked List)

Whenever you see `std::list` in C++, it is **always a doubly linked list**. Every element has a pointer to both the next element and the previous element.

To use it, you must `#include <list>`.

**Core Operations Code Example:**

```C++
#include <iostream>
#include <list>

using namespace std;

int main() {
    // 1. Initialization
    list<int> myList = {10, 20, 30};

    // 2. Insertions - O(1) time
    myList.push_back(40);  // Adds to the tail: {10, 20, 30, 40}
    myList.push_front(5);  // Adds to the head: {5, 10, 20, 30, 40}

    // 3. Deletions - O(1) time
    myList.pop_back();     // Removes the tail: {5, 10, 20, 30}
    myList.pop_front();    // Removes the head: {10, 20, 30}

    // 4. Traversing with Iterators
    // STL lists don't use raw pointers. They use "Iterators".
    for (list<int>::iterator it = myList.begin(); it != myList.end(); ++it) {
        cout << *it << " -> "; // Use the dereference operator (*) to get the value
    }
    cout << "NULL" << endl;

    return 0;
}
```

### 2. `std::forward_list` (The Singly Linked List)

Introduced in C++11, `std::forward_list` is a **singly linked list**. It only has forward-facing pointers.

**Why use it?** It uses less memory than `std::list` because it lacks the `prev` pointer. It is the exact equivalent of the custom `Node` we built earlier. To use it, you must `#include <forward_list>`.

**The Quirks:** Because it only goes forward, it does not have `push_back()` or `size()`. You can only easily insert at the front or directly after a specific node.

```C++
#include <iostream>
#include <forward_list>

using namespace std;

int main() {
    forward_list<int> flist = {20, 30};

    // Insert at the head - O(1)
    flist.push_front(10); // {10, 20, 30}

    // Insert in the middle requires finding the position first
    auto it = flist.begin(); // points to 10
    ++it;                    // moves to 20
    
    // Inserts AFTER the iterator - O(1)
    flist.insert_after(it, 25); // {10, 20, 25, 30}

    return 0;
}
```

---

## Powerful Built-in STL Methods

The true power of the STL is that it comes with highly optimized algorithms built directly into the container. These will save you massive amounts of time.

- **`myList.reverse()`:** Reverses the entire list in-place in $O(n)$ time. No manual pointer manipulation needed.
    
- **`myList.sort()`:** Sorts the list in $O(n \log n)$ time.
    
- **`myList.merge(otherList)`:** Merges another sorted list into your current sorted list, emptying `otherList` in the process.
    
- **`myList.unique()`:** Removes all consecutive duplicate elements from the list.
    

### 1. The "Superpower" Methods (Built-in Algorithms)

These are the methods that make using the STL worthwhile. They apply to both `std::list` and `std::forward_list` (though `forward_list` variations usually have an `_after` suffix).

|**Method**|**Description**|**Time Complexity**|
|---|---|---|
|**`splice()`** / **`splice_after()`**|**The most important method.** It transfers nodes from one list to another (or within the same list) by purely rewiring pointers. **No memory allocation or copying occurs.**|$O(1)$|
|**`merge(other_list)`**|Merges two _already sorted_ lists into one. `other_list` becomes empty.|$O(n)$|
|**`remove(val)`**|Scans the list and deletes every node where `data == val`.|$O(n)$|
|**`remove_if(condition)`**|Deletes nodes based on a custom condition (usually a lambda function). Excellent for filtering.|$O(n)$|
|**`unique()`**|Removes all _consecutive_ duplicate elements. (Pro-tip: `sort()` the list first if you want to remove _all_ duplicates).|$O(n)$|
|**`sort()`**|Sorts the list. It uses a highly optimized Merge Sort under the hood since Quick Sort relies on random access.|$O(n \log n)$|
|**`reverse()`**|Reverses the order of the nodes by flipping pointers.|$O(n)$|

**Code Example: The Power of Splice and Lambdas**

```C++
#include <iostream>
#include <list>

using namespace std;

int main() {
    list<int> list1 = {1, 2, 3, 4, 5};
    list<int> list2 = {10, 20, 30};

    // 1. Splice: Move all of list2 to the beginning of list1 in O(1) time
    list1.splice(list1.begin(), list2); 
    // list1 is now: {10, 20, 30, 1, 2, 3, 4, 5}, list2 is empty

    // 2. Remove_if: Remove all even numbers using a lambda function
    list1.remove_if([](int n) { return n % 2 == 0; });
    // list1 is now: {1, 3, 5}

    return 0;
}
```

---

### 2. Standard `std::list` Methods (Doubly Linked)

Because `std::list` has pointers pointing both backward and forward, it supports operations at both the head and the tail.

- **`push_back(val)` / `pop_back()`:** Adds or removes an element at the tail. $O(1)$
    
- **`push_front(val)` / `pop_front()`:** Adds or removes an element at the head. $O(1)$
    
- **`front()` / `back()`:** Returns a reference to the first or last element. $O(1)$
    
- **`size()`:** Returns the number of elements in the list. $O(1)$
    
- **`insert(iterator, val)`:** Inserts a value _before_ the specified iterator. $O(1)$ pointer wiring, but $O(n)$ to find the spot.
    
- **`erase(iterator)`:** Removes the element at the specified iterator. $O(1)$ wiring.
    

---

### 3. Unique `std::forward_list` Methods (Singly Linked)

`std::forward_list` is aggressively optimized for memory. Because nodes only point forward, it completely lacks `push_back()`, `pop_back()`, `back()`, and even `size()` (because keeping track of the size takes extra operations).

It forces you to operate slightly differently:

- **`before_begin()`:** Returns an iterator pointing to the imaginary position _before_ the first element. This is essentially the C++ STL version of the **"Dummy Node"** pattern we discussed earlier.
    
- **`insert_after(iterator, val)`:** You cannot insert _before_ an iterator because there is no previous pointer. You must insert _after_.
    
- **`erase_after(iterator)`:** Removes the element immediately following the iterator.
    

**Code Example: Forward List Mechanics**

C++

```
#include <iostream>
#include <forward_list>

using namespace std;

int main() {
    forward_list<int> flist = {20, 30, 40};

    // To insert at the very beginning, we use before_begin()
    flist.insert_after(flist.before_begin(), 10); 
    // flist is now: {10, 20, 30, 40}

    // To delete '30', we need the iterator pointing to '20'
    auto it = flist.begin(); // Points to 10
    ++it;                    // Points to 20
    flist.erase_after(it);   // Deletes the node AFTER 20 (which is 30)
    // flist is now: {10, 20, 40}

    return 0;
}
```

---

### 4. Essential Shared Methods

These apply to almost all STL containers, including both types of linked lists:

- **`empty()`:** Returns `true` if the list has no elements. Always prefer `if(myList.empty())` over `if(myList.size() == 0)`. It is faster and guaranteed to be $O(1)$.
    
- **`clear()`:** Deletes all elements in the list and frees the memory. $O(n)$.
    
- **`begin()` / `end()`:** Returns iterators to the start and one-past-the-end of the list, used for loops.
    


## Accessing the elements in Linked Lists
In C++, because `std::forward_list` (a singly linked list) and `std::list` (a doubly linked list) do not store their elements in contiguous memory like arrays or vectors, **you cannot use the bracket operator `[]` or the `.at()` method** to access a specific node's value.

Instead, you have to use **methods for the endpoints** or **iterators** to reach specific nodes.

Here are the standard ways to access the values inside these containers:

### 1. Accessing the First and Last Nodes Directly

If you only need the data at the very beginning or the very end of the list, the STL provides direct functions that return a reference to the value.

- **`front()`**: Returns the value of the first node. (Available for both `std::forward_list` and `std::list`).
    
- **`back()`**: Returns the value of the last node. (**Available only for `std::list`**, as `forward_list` doesn't keep track of its tail to save memory).
    

C++

```
std::list<int> myList = {10, 20, 30};
std::cout << myList.front(); // Outputs: 10
std::cout << myList.back();  // Outputs: 30

std::forward_list<int> myFwdList = {10, 20, 30};
std::cout << myFwdList.front(); // Outputs: 10
// myFwdList.back() does not exist!
```

---

### 2. Accessing Nodes via Iterators (Dereferencing)

To access the value of any node in the middle of the list, you must use an iterator pointing to that node and **dereference it using the `*` operator**.

#### Scenario A: Iterating through all nodes

When you loop through a list, the iterator points to the current node. You use `*it` to read or modify the value.

C++

```
std::list<int> myList = {10, 20, 30};

for (auto it = myList.begin(); it != myList.end(); ++it) {
    // Access the value using *it
    std::cout << *it << " "; 
    
    // You can also modify the value if the iterator isn't const
    if (*it == 20) {
        *it = 25; 
    }
}
```

#### Scenario B: Reaching a specific "index" (The `std::advance` or `std::next` way)

If you want to access the $n$-th node (for example, the 3rd node), you have to start at the beginning and walk the iterator forward step-by-step. C++ provides utility functions in the `<iterator>` header to make this easier:

- **`std::next(iterator, n)`**: Returns a _new_ iterator advanced by `n` steps.
    
- **`std::advance(iterator, n)`**: Modifies your _existing_ iterator by moving it forward `n` steps.
    

C++

```
#include <iostream>
#include <list>
#include <forward_list>
#include <iterator> // Required for std::next and std::advance

int main() {
    std::list<int> myList = {10, 20, 30, 40, 50};

    // --- Using std::next ---
    // Let's get the 3rd element (which is at "index" 2)
    auto it = std::next(myList.begin(), 2);
    std::cout << "The 3rd element is: " << *it << "\n"; // Outputs: 30

    // --- Using std::advance ---
    std::forward_list<int> fwdList = {100, 200, 300, 400};
    auto fwdIt = fwdList.begin();
    std::advance(fwdIt, 3); // Moves fwdIt forward by 3 steps
    std::cout << "The 4th element is: " << *fwdIt << "\n"; // Outputs: 400

    return 0;
}
```

> **Performance Warning:** Unlike `vector[3]`, which takes $O(1)$ time, advancing an iterator in a list takes $O(n)$ time because it literally has to hop from node to node behind the scenes. If you find yourself frequently trying to access elements by a specific index, a linked list is likely the wrong data structure for your task, and you should consider a `std::vector` or `std::deque`!

---

Would you like to see how to safely insert or erase nodes at specific positions using these iterators?