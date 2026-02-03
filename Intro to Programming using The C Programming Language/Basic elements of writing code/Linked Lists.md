**Pre-requisites:** [[Pointers and Working with Memory Allocation]], [[Memory Allocation]]
**Topics Covered:**
**Tags:** [[Data Structures]]

A **linked list** is a fundamental linear data structure in C that consists of a sequence of nodes, where each node contains data and a pointer to the next node in the sequence. Unlike arrays, linked lists do not require contiguous memory allocation and can grow or shrink dynamically at runtime.

---
## Structure of a Linked List Node
In C, a node is typically defined using a `struct`:
```c
struct Node 
{     
	int data;             // Data part    
	struct Node* next;    // Pointer to the next node 
};
```
- **data**: stores the value or information.
- **next**: a self-referencing pointer that points to the next node in the list.

---
## Types of Linked Lists
- **Singly Linked List**: Each node points to the next node. The last node points to `NULL`.
- **Doubly Linked List**: Each node has pointers to both the next and previous nodes.
- **Circular Linked List**: The last node points back to the first node, forming a circle.

The most basic and common is the singly linked list.

---
## Key Properties
- **Dynamic Size**: Nodes are created and deleted dynamically, so the list can grow or shrink as needed.
- **Non-contiguous Memory**: Nodes can be scattered throughout memory, linked together by pointers.
- **Efficient Insertions/Deletions**: Adding or removing nodes does not require shifting elements, unlike arrays.

---
## Basic Operations**

## **1. Creating and Initializing Nodes**

c

`struct Node* head = NULL; // Start with an empty list // Creating nodes dynamically struct Node* one = malloc(sizeof(struct Node)); struct Node* two = malloc(sizeof(struct Node)); struct Node* three = malloc(sizeof(struct Node)); // Assign data one->data = 1; two->data = 2; three->data = 3; // Link nodes one->next = two; two->next = three; three->next = NULL; // Set head to first node head = one;`

## **2. Traversing the List**

c

`void printList(struct Node* node) {     while (node != NULL) {        printf("%d ", node->data);        node = node->next;    } }`

## **3. Insertion**

- **At the Beginning**: Create a new node, set its `next` to the current head, then update head.
    
- **At the End**: Traverse to the last node, set its `next` to the new node.
    
- **After a Given Node**: Adjust pointers to insert after a specific node.
    

## **4. Deletion**

- **At the Beginning**: Move head to the next node and free the old head.
    
- **At the End**: Traverse to the second-last node, set its `next` to `NULL`, and free the last node.
    
- **By Value**: Find the node with the given value, adjust pointers to bypass it, and free it[2](https://www.tutorialspoint.com/data_structures_algorithms/linked_list_program_in_c.htm).
    

## **5. Searching**

Traverse the list and compare each node’s data with the target value[2](https://www.tutorialspoint.com/data_structures_algorithms/linked_list_program_in_c.htm).

---

## **Advantages Over Arrays**

|Feature|Array|Linked List|
|---|---|---|
|Memory allocation|Contiguous|Non-contiguous|
|Size|Fixed|Dynamic|
|Insert/Delete (middle)|Slow (O(n))|Fast (O(1) if node known)|
|Random access|O(1)|O(n)|

[4](https://www.w3schools.com/dsa/dsa_theory_linkedlists.php)[5](https://edrawmax.wondershare.com/development-tips/singly-linked-list-program-in-c.html)

---

## **Sample Linked List Implementation in C**

c

`#include <stdio.h> #include <stdlib.h> struct Node {     int data;    struct Node* next; }; void printList(struct Node* n) {     while (n != NULL) {        printf("%d ", n->data);        n = n->next;    } } int main() {     struct Node* head = NULL;    struct Node* second = NULL;    struct Node* third = NULL;     // Allocate nodes in memory    head = malloc(sizeof(struct Node));    second = malloc(sizeof(struct Node));    third = malloc(sizeof(struct Node));     head->data = 1;    head->next = second;     second->data = 2;    second->next = third;     third->data = 3;    third->next = NULL;     printList(head);    return 0; }`

---

## **Applications**

- Implementation of stacks and queues.
    
- Dynamic memory management.
    
- Symbol tables and adjacency lists for graphs.
    

---

## **Summary**

- A linked list is a dynamic, linear data structure made up of nodes.
    
- Each node contains data and a pointer to the next node.
    
- Linked lists are efficient for insertions and deletions but do not support fast random access.
    
- They are widely used in situations where the size of the data structure can change frequently[1](https://www.programiz.com/dsa/linked-list)[3](https://www.hackerearth.com/practice/data-structures/linked-list/singly-linked-list/tutorial/)[4](https://www.w3schools.com/dsa/dsa_theory_linkedlists.php)[5](https://edrawmax.wondershare.com/development-tips/singly-linked-list-program-in-c.html)[8](https://www.techiedelight.com/linked-list-implementation-part-1/).
    

For more advanced usage, such as doubly linked lists or circular linked lists, the principles remain similar but with additional pointers and logic.

1. [https://www.programiz.com/dsa/linked-list](https://www.programiz.com/dsa/linked-list)
2. [https://www.tutorialspoint.com/data_structures_algorithms/linked_list_program_in_c.htm](https://www.tutorialspoint.com/data_structures_algorithms/linked_list_program_in_c.htm)
3. [https://www.hackerearth.com/practice/data-structures/linked-list/singly-linked-list/tutorial/](https://www.hackerearth.com/practice/data-structures/linked-list/singly-linked-list/tutorial/)
4. [https://www.w3schools.com/dsa/dsa_theory_linkedlists.php](https://www.w3schools.com/dsa/dsa_theory_linkedlists.php)
5. [https://edrawmax.wondershare.com/development-tips/singly-linked-list-program-in-c.html](https://edrawmax.wondershare.com/development-tips/singly-linked-list-program-in-c.html)
6. [https://github.com/mpmumau/C-Linked-List](https://github.com/mpmumau/C-Linked-List)
7. [https://www.cs.wm.edu/~smherwig/courses/csci415-common/list/index.html](https://www.cs.wm.edu/~smherwig/courses/csci415-common/list/index.html)
8. [https://www.techiedelight.com/linked-list-implementation-part-1/](https://www.techiedelight.com/linked-list-implementation-part-1/)
9. [https://www.ccbp.in/blog/articles/write-a-program-to-implement-stack-using-linked-list-in-c](https://www.ccbp.in/blog/articles/write-a-program-to-implement-stack-using-linked-list-in-c)
10. [https://www.learn-c.org/en/Linked_lists](https://www.learn-c.org/en/Linked_lists)
11. [https://www.youtube.com/watch?v=6wXZ_m3SbEs](https://www.youtube.com/watch?v=6wXZ_m3SbEs)
12. [https://www.scaler.com/topics/c/linked-list-in-c/](https://www.scaler.com/topics/c/linked-list-in-c/)
13. [https://www.upgrad.com/tutorials/software-engineering/c-tutorial/stack-using-linked-list-in-c/](https://www.upgrad.com/tutorials/software-engineering/c-tutorial/stack-using-linked-list-in-c/)
14. [https://www.youtube.com/watch?v=H0pNFhAogHs](https://www.youtube.com/watch?v=H0pNFhAogHs)
15. [https://www.upgrad.com/tutorials/software-engineering/c-tutorial/linked-list-in-c/](https://www.upgrad.com/tutorials/software-engineering/c-tutorial/linked-list-in-c/)
16. [https://www.youtube.com/watch?v=DZwCekO0NJw](https://www.youtube.com/watch?v=DZwCekO0NJw)