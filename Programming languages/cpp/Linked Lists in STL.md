
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
    

---

Would you like me to walk you through building an **LRU (Least Recently Used) Cache** using `std::list` and `std::unordered_map`? It is one of the most common and complex interview questions that perfectly demonstrates the true power of the `splice()` method.