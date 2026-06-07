**Iterative Binary Search**

```Plaintext

BINARY-SEARCH(A, n, v)
1.  low = 1
2.  high = n
3.  while low <= high
4.      mid = floor((low + high) / 2)
5.      if A[mid] == v
6.          return mid
7.      elseif A[mid] < v
8.          low = mid + 1
9.      else
10.         high = mid - 1
11. return NIL
```

v = target
A = array
n = number of elements

**Recursive binary search**:

```
function binarySearch(array, target, left, right):
    // Base case: element not found
    if left > right:
        return -1
    
    // Calculate middle index
    mid = left + (right - left) / 2
    
    // Base case: element found
    if array[mid] == target:
        return mid
    
    // Recursive case: search left half
    if array[mid] > target:
        return binarySearch(array, target, left, mid - 1)
    
    // Recursive case: search right half
    else:
        return binarySearch(array, target, mid + 1, right)
```
