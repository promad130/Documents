# Medium 

## Question 1 : 3Sum
Let the input be taken in an array

```Text
Sort(arr)
vector vector <int> triplets;
for i = 0 to n - 2:  
    if i > 0 and arr[i] == arr[i-1]: continue // Skip duplicates for i
    
    j = i + 1
    k = n - 1  // Fixed bounds
    target = -arr[i]
    
    while (j < k): 
        sum = arr[j] + arr[k]
        
        if(sum > target):
            k--;
        else if(sum < target):
            j++;
        else:
            triplets.push_back({arr[i], arr[j], arr[k]})
            j++; 
            k--; 
            
```

CODE:
```C++

```

## Question 2 : 

The Logic:
Notice that for even numbers, the number is greatest from both left and right
For odd numbers, the number is smallest in left and right

hence, for all even positions:
- if next number > the even position, then swap
For all odd positions:
- if next number is smaller than the odd position, then swap

```Text
Swap(a,b):
	temp = a
	a = b
	b = temp

for i = 0 to n - 1:
	if(i%2 != 0):
		if (arr[i+1] > arr[i]):
			Swap(arr[i+1], arr[i])
	else
		if (arr[i] > arr[i+1]):
			Swap(arr[i+1], arr[i])
```

Gives Time Complexity O(n)


## Question 3 :
Leave the smallest N-2K untouched, and work with the largest 2K values:

```text
Function MinimizeScore(n, k, arr):
    Sort(arr)
    
    score = 0
    
    // 1. Add remaining elements that are not part of k operations
    for i from 0 to (n - 2k - 1):
        score = score + arr[i]
        
    // 2. Perform k operations on the largest 2k elements
    // We pair elements at distance k to maximize the denominator
    for i from (n - 2k) to (n - k - 1):
        score = score + floor(arr[i] / arr[i + k])
        
    return score
```

## Question 4
```text
Algorithm: ApartmentMatching(n, m, k, A, B)
Input: 
    n, m (number of applicants/apartments)
    k (max difference)
    A[1...n] (desired sizes)
    B[1...m] (actual sizes)

1. Sort(A) // O(n log n)
2. Sort(B) // O(m log m)
3. i = 0, j = 0, count = 0
4. while i < n and j < m:
5.     if |A[i] - B[j]| <= k:
6.         count = count + 1
7.         i = i + 1
8.         j = j + 1
9.     else if A[i] < B[j]:
           // Current applicant wants something smaller than current apartment
10.        i = i + 1
11.    else:
           // Current apartment is too small for current applicant
12.        j = j + 1
13. return count
```

# Hard

## Question 1
Logic is to use Divide and Conqure approch


```text
Algorithm: ReversePairs(A, p, r)
Input: Array A, starting index p, ending index r
Output: Number of reverse pairs in A[p...r]

1.  if p >= r:
2.      return 0
3.  
4.  mid = floor((p + r) / 2)
5.  count = ReversePairs(A, p, mid) + ReversePairs(A, mid + 1, r)
6.  
7.  // Step 1: Count Cross-Pairs (The "Two-Pointer" Logic)
8.  j = mid + 1
9.  for i = p to mid:
10.     while j <= r and A[i] > 2 * A[j]:
11.         j = j + 1
12.     count = count + (j - (mid + 1))
13. 
14. // Step 2: Standard Merge (To maintain sorted property for higher recursions)
15. Merge(A, p, mid, r)
16. 
17. return count
```

## Question 2

We use simultaneous merge sort:

```text
Algorithm: SimultaneousSort(a, b, left, right)
Input: Arrays a and b, range [left, right]
Output: A sequence of operations to sort the arrays

1.  // Base Case: Single Element
2.  if left == right:
3.      if a[left] > b[left]:
4.          Swap(a[left], b[left])
5.          Print "3 left" // Operation Type 3
6.      return

7.  // Divide Step
8.  mid = floor((left + right) / 2)
9.  SimultaneousSort(a, b, left, mid)
10. SimultaneousSort(a, b, mid + 1, right)

11. // Conquer Step: Merge the two sorted halves
12. // We use an in-place merge logic (similar to Insertion Sort) to 
13. // respect the "adjacent swap" constraint.
14. Merge(a, b, left, mid, right)
```

```Text
Function: Merge(a, b, left, mid, right)
1.  i = left        // Pointer for the left sorted half
2.  j = mid + 1     // Pointer for the right sorted half

3.  while i <= mid and j <= right:
4.      // Compare the "front" of both halves.
5.      // We primarily sort based on a[i] vs a[j].
6.      if a[i] <= a[j]:
7.          i = i + 1
8.      else:
9.          // The element at 'j' is smaller than 'i'. 
10.         // We need to move the pair (a[j], b[j]) to position 'i'.
11.         // This requires shifting elements from i to j-1 to the right.
            
12.         index = j
13.         while index > i:
14.             // Swap pair at (index) with (index-1)
15.             Swap(a[index], a[index-1])
16.             Print "1 (index-1)"
17.             Swap(b[index], b[index-1])
18.             Print "2 (index-1)"
19.             index = index - 1
            
20.         // After shifting, the element originally at 'j' is now at 'i'.
21.         // We must update our pointers.
22.         i = i + 1
23.         mid = mid + 1   // The left partition effectively grows
24.         j = j + 1       // The next element to consider moves right
```


## Question 3

```text
Algorithm: MinimizePartitions(n, k, A)
Input: n (size), k (partitions), A (sorted array)
Output: Minimum total cost

1.  TotalSpan = A[n-1] - A[0]
2.  
3.  // Create a list of adjacent differences
4.  Differences = []
5.  for i = 0 to n-2:
6.      gap = A[i+1] - A[i]
7.      Differences.append(gap)
8.      
9.  // Sort gaps to find the largest ones (Greedy step)
10. Sort Differences in Descending Order
11. 
12. // We need k subarrays, so we make k-1 cuts
13. RemovedCost = 0
14. for i = 0 to k-2:
15.     RemovedCost = RemovedCost + Differences[i]
16.     
17. return TotalSpan - RemovedCost
```