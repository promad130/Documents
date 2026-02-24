# Easy

## Q1

Aproch:
The Tortoise And Hare Approch:

```Psudocode
FUNCTION findMiddle(head):
    // 1. Initialize two pointers at the start of the list
    SET slow = head
    SET fast = head

    // 2. Loop until the 'fast' pointer hits the end of the list
    // Check both 'fast' and 'fast.next' to avoid stepping into NULL
    WHILE (fast is NOT NULL) AND (fast.next is NOT NULL):
        
        slow = slow.next          // Tortoise moves 1 step
        fast = fast.next.next     // Hare moves 2 steps

    // 3. When the loop finishes, 'slow' is at the middle node
    RETURN slow
END FUNCTION
```

```C++
#include <iostream>
#include <list>
#include <iterator>

/**
 * Using std::list (STL Doubly Linked List)
 * The logic remains the same: One pointer moves twice as fast.
 */
auto findMiddle(std::list<int>& nums) {
    auto slow = nums.begin();
    auto fast = nums.begin();
    
    while(fast != nums.end())
    {
	    auto tempFast = std::next(fast);
	    if(tempFast == nums.end())
		    break;
		fast = std::next(tempFast);
		slow++;
    }
    
    return slow;
}

int main() {
    std::list<int> myList = {1, 2, 3, 4, 5, 6};

    auto middle = findMiddle(myList);

    std::cout << "The middle element is: " << *middle << std::endl;

    return 0;
}
```

## Q2

```pseudocode
FUNCTION reverseList(head):
    SET prev = NULL
    SET curr = head

    WHILE curr is NOT NULL:
        // Use your external variable to save the bridge to the rest of the list
        SET temp = curr.next 
        
        // Reverse the direction of the current node
        curr.next = prev 
        
        // Shift pointers: move prev to where curr is, and curr to the temp
        prev = curr
        curr = temp

    RETURN prev
END FUNCTION
```

```C++
#include <iostream>

struct ListNode {
    int val;
    ListNode* next;
    ListNode(int x) : val(x), next(nullptr) {}
};

class Solution {
public:
    ListNode* reverseList(ListNode* head) {
        ListNode* prev = nullptr;
        ListNode* curr = head;
        ListNode* temp = nullptr; // Your "external variable"

        while (curr != nullptr) {
            temp = curr->next;    // Store the next address
            curr->next = prev;    // Change current's next to the address of first (prev)
            
            // Move forward
            prev = curr;
            curr = temp;
        }

        return prev;
    }
};
```
OR
```C++
#include <iostream>
#include <list>

int main() {
    std::list<int> myList = {1, 2, 3, 4, 5};

    // The STL internal reverse - O(n) time, O(1) space
    myList.reverse();

    for (int val : myList) {
        std::cout << val << " ";
    }
    return 0;
}
```

## Q3

```pseudocode
FUNCTION hasCycle(head):
    SET slow = head
    SET fast = head

    WHILE fast is NOT NULL AND fast.next is NOT NULL:
        slow = slow.next          // 1 step
        fast = fast.next.next     // 2 steps
        
        IF slow == fast:          // They met!
            RETURN true
            
    RETURN false                  // Fast reached the end, so no cycle
END FUNCTION
```

```C++
#include <iostream>

struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    bool hasCycle(ListNode *head) {
        if (head == nullptr) return false;

        ListNode *slow = head;
        ListNode *fast = head;

        while (fast != nullptr && fast->next != nullptr) {
            slow = slow.next;          // Move 1 step
            fast = fast->next->next;   // Move 2 steps

            // If there's a cycle, the fast pointer will eventually catch up to slow
            if (slow == fast) {
                return true;
            }
        }

        return false; // Fast reached the end
    }
};
```


# Medium

## Q1

```pseudocode
FUNCTION addTwoNumbers(l1, l2):
    SET dummyHead = new Node(0) // need the pointer to the start of the new linkedList
    SET curr = dummyHead // this is what we work on
    SET carry = 0

    // Continue as long as there are nodes OR a remaining carry
    WHILE l1 is not NULL OR l2 is not NULL OR carry > 0:
        SET val1 = (l1 is not NULL) ? l1.val : 0
        SET val2 = (l2 is not NULL) ? l2.val : 0
        
        // Sum of digits + carry from previous step
        SET total = val1 + val2 + carry
        carry = total / 10
        SET newNodeVal = total % 10
        
        // Create new node and move forward
        curr.next = new Node(newNodeVal)
        curr = curr.next
        
        IF l1 is not NULL: l1 = l1.next
        IF l2 is not NULL: l2 = l2.next

    RETURN dummyHead.next
END FUNCTION
```

```C++
struct listNode {
	int value;
	listNode * next;
	
	listNode() : value(0), next(nullptr) {};
	listNode(int x) : value(x), next(nullptr) {};
	listNode(int x, listNode * nextPtr) : value(x), next(nextPtr) {};
}

class solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode* bufferHead = new ListNode(0); // Dummy node
        ListNode* current = bufferHead;
        int carry = 0;
        
        while(l1 != nullptr || l2 != nullptr || carry != 0) {
            int x = (l1 != nullptr) ? l1->val : 0;
            int y = (l2 != nullptr) ? l2->val : 0;
            
            int sum = carry + x + y; // Do the math once (Like using a register)
            carry = sum / 10;
            
            // Create the node only when you HAVE a value to put in it
            current->next = new ListNode(sum % 10);
            current = current->next;
            
            if (l1 != nullptr) l1 = l1->next;
            if (l2 != nullptr) l2 = l2->next;
        }
        
        // Return .next to skip the dummy zero at the start
        return bufferHead->next;
    }
};
```

## Q3
### The Strategy

1. **Find the $k$-th node from the beginning:** Start a pointer `first` at the head and move it $k-1$ steps.
    
2. **Find the $k$-th node from the end:** * This is the clever part. If you have a pointer `fast` that is $k$ steps ahead of a pointer `slow`, when `fast` hits the end (NULL), `slow` will be exactly at the $k$-th node from the end.
    
    - **Pro-tip:** You can actually start your `second` pointer (the one looking for the end-node) at the head exactly when your `first` pointer reaches the $k$-th position.

```pseudocode
FUNCTION swapKthNode(head, k):
    SET first = head
    
    // 1. Move 'first' to the k-th node
    FOR i from 1 to k-1:
        first = first.next
    
    // 2. Use the "Gap" method to find k-th from end
    SET nodeKFromStart = first
    SET second = head
    SET tempFast = first
    
    WHILE tempFast.next is NOT NULL:
        tempFast = tempFast.next
        second = second.next
        
    // 3. Swap the values (just like a standard swap)
    SET tempVal = nodeKFromStart.val
    nodeKFromStart.val = second.val
    second.val = tempVal
    
    RETURN head
END FUNCTION
```

```C++
class Solution {
public:
    ListNode* swapNodes(ListNode* head, int k) {
        ListNode* first = head;
        ListNode* second = head;
        ListNode* temp = nullptr;

        // Move 'first' to the k-th node
        for (int i = 1; i < k; ++i) {
            first = first->next;
        }

        // Bookmark this node to swap later
        ListNode* nodeKFromStart = first;

        // Move 'temp' to the end, while moving 'second' from the start
        // This keeps 'second' exactly k-nodes from the end
        temp = first;
        while (temp->next != nullptr) {
            temp = temp->next;
            second = second->next;
        }

        // Swap values (Standard 8086 style: MOV EAX, [first]; XCHG EAX, [second]...)
        int valBuffer = nodeKFromStart->val;
        nodeKFromStart->val = second->val;
        second->val = valBuffer;

        return head;
    }
};
```

## Q4
Your plan effectively becomes:

1. **Reverse** the list.
2. **Double** it (traverse and multiply each node by 2, handling the carry).

```C++
class Solution {
public:
    // Reuse your reversal logic
    ListNode* reverseList(ListNode* head) {
        ListNode *prev = nullptr, *curr = head;
        while (curr) {
            ListNode* nextNode = curr->next;
            curr->next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }

    ListNode* doubleIt(ListNode* head) {
        head = reverseList(head);
        
        ListNode* curr = head;
        ListNode* last = nullptr;
        int carry = 0;

        while (curr != nullptr) {
            int val = curr->val * 2 + carry;
            curr->val = val % 10;
            carry = val / 10;
            last = curr; // Keep track of the last node for a potential extra carry
            curr = curr->next;
        }

        // If there's a carry left (e.g., 50 * 2 = 100), add a new node
        if (carry) {
            last->next = new ListNode(carry);
        }

        return reverseList(head);
    }
};
```