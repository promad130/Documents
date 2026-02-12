# Easy 

## Question 1

```text
Algorithm: Quicksort(A, p, r)
Input: Array A, starting index p, ending index r

1.  if p < r:
2.      // Partition the subarray around the pivot, which ends up at index q
3.      q = Partition(A, p, r)
4.      
5.      // Recursively sort the low side
6.      Quicksort(A, p, q - 1)
7.      
8.      // Recursively sort the high side
9.      Quicksort(A, q + 1, r)
```
```text
Algorithm: Partition(A, p, r)
// Lomuto Partition Scheme
// Chooses the last element A[r] as the pivot
Input: Array A, start index p, end index r
Output: The final index of the pivot

1.  pivot = A[r]
2.  i = p - 1    // i is the boundary of the "less than pivot" region
3.  
4.  for j = p to r - 1:
5.      if A[j] <= pivot:
6.          i = i + 1
7.          Swap(A[i], A[j])
8.  
9.  // Place the pivot in its correct sorted position (after the "less than" region)
10. Swap(A[i + 1], A[r])
11. return i + 1
```

## Question 2

So basically we are testing out all the sub arrays using the divide and conqure techniqe:

```text
Algorithm: MaxSubarray(A, low, high)
1.  if low == high:
2.      return A[low]  // Base case: only one element
3.
3.  mid = floor((low + high) / 2)
5.
4.  leftMax = MaxSubarray(A, low, mid)
5.  rightMax = MaxSubarray(A, mid + 1, high)
6.  crossMax = MaxCrossingSum(A, low, mid, high)
9.
7. return max(leftMax, rightMax, crossMax)
```
```text
Algorithm: MaxCrossingSum(A, low, mid, high)
1.  leftSum = -infinity
2.  sum = 0
3.  for i = mid down to low:
4.      sum = sum + A[i]
5.      if sum > leftSum:
6.          leftSum = sum
7.
7.  rightSum = -infinity
8.  sum = 0
9. for j = mid + 1 to high:
10.     sum = sum + A[j]
11.     if sum > rightSum:
12.         rightSum = sum
14.
13. return leftSum + rightSum
```

## Question 3

```text
Algorithm: Solution(nums)
1.  count = 0
2.  candidate = None
3.  for each x in nums:
4.      if count == 0:
5.          candidate = x
6.      
7.      if x == candidate:
8.          count = count + 1
9.      else:
10.         count = count - 1
11. return candidate
```

## Question 4

```text
Algorithm: LongestCommonPrefix(S)
Input: An array of strings S[0...n-1]
Output: The longest common prefix string

1.  if S.length == 0:
2.      return ""
3.
3.  // Let the first string be the reference
4.  ref = S[0]
6.
5.  // Loop through each character index i of the reference string
6.  for i = 0 to ref.length - 1:
7.      char c = ref[i]
8.     
9.     // Compare this character with the i-th character of every other string
10.     for j = 1 to S.length - 1:
11.         // Condition 1: Check if we exceeded the length of S[j]
12.         // Condition 2: Check if characters match
13.         if i == S[j].length OR S[j][i] != c:
14.             return ref.substring(0, i)
17.
15. // If no mismatches found, the whole first string is the prefix
16. return ref
```

### A better logical approach I found online:
**Asymptotically? No.**

**For code brevity? Yes.**

Strictly speaking, your **Vertical Scanning** approach (checking column by column) is the **optimal** algorithm in terms of Time Complexity, running in **$O(S)$** (where $S$ is the total number of characters). You cannot solve this problem faster than reading the input.

However, there is a very popular "clever" approach used in coding interviews that trades a bit of speed for significantly shorter code.

#### The "Sorting" Approach

Instead of checking every string, we can simply **sort** the array of strings.

**The Logic:**

When you sort a list of strings alphabetically, strings with similar prefixes are grouped together. The "most different" strings will end up at the **first** and **last** positions of the sorted array.

Therefore, the Longest Common Prefix of the _entire_ array is simply the common prefix between the **first** and the **last** string.

**Trade-off:**

- **Time Complexity:** $O(N \cdot L \cdot \log N)$ because of sorting (where $N$ is array size, $L$ is max string length). This is **slower** than your approach ($O(N \cdot L)$).
    
- **Space Complexity:** $O(\log N)$ or $O(L)$ depending on the sorting stack.
    
- **Benefit:** The code is incredibly short and less prone to "index out of bounds" errors during implementation.

# Medium 

## Question 1

```text
Algorithm: Power(x, n)
1.  N = n (cast to long long)
2.  if N < 0:
3.      x = 1 / x
4.      N = -N
5.  
6.  result = 1
7.  current_product = x
8.  
9.  while N > 0:
10.     if (N % 2) == 1:       // If N is odd
11.         result = result * current_product
12.     
13.     current_product = current_product * current_product
14.     N = N / 2
15. return result
```

## Question 2

```text
Algorithm: Quickselect(A, low, high, target_index)
1.  if low == high:
2.      return A[low]
3.  
4.  // Use the same partition function from Quicksort
5.  pivot_index = Partition(A, low, high)
6.  
7.  if pivot_index == target_index:
8.      return A[pivot_index]
9.  else if pivot_index > target_index:
10.     return Quickselect(A, low, pivot_index - 1, target_index)
11. else:
12.     return Quickselect(A, pivot_index + 1, high, target_index)
```

We use Randomized Algorithms to avoid worst case, don't know how is that implemented, but will look into it!

## Question 3

### Recursion (Divide and Conquer)

1. **Iterate** through the string.
2. Whenever you hit an operator (`+`, `-`, `*`), split the string into `left` and `right` parts.
3. **Recursively** solve for all possible results of the `left` part.
4. **Recursively** solve for all possible results of the `right` part.
5. **Combine** every result from the `left` list with every result from the `right` list using the current operator.

```text
Algorithm: DiffWaysToCompute(input)
1.  results = []
2.  
3.  for i = 0 to input.length - 1:
4.      char c = input[i]
5.      if c is '+', '-', or '*':
6.          // Divide
7.          left_results = DiffWaysToCompute(input[0...i])
8.          right_results = DiffWaysToCompute(input[i+1...end])
9.          
10.         // Combine (Cartesian Product)
11.         for l in left_results:
12.             for r in right_results:
13.                 if c == '+': results.add(l + r)
14.                 if c == '-': results.add(l - r)
15.                 if c == '*': results.add(l * r)
16.
16. // Base Case: If results is empty, the input is just a number (e.g., "5")
17. if results is empty:
18.     results.add(Integer(input))
20.
19. return results
```

# Hard

## Question 1

### The logic:

Each building has two coordinates, (start, height) and (end, height)
**Break down buildings into points:** For every building `[L, R, H]`, create two event points:

- **(L, -H)**: A "Start" event. We use negative height `-H` as a trick for sorting (explained below).    
- **(R, H)**: An "End" event. Positive `H` indicates leaving a building.

### The Sort

#### Case 1: Start vs. Start (Two buildings begin at same x)

Imagine Building A (Height 10) and Building B (Height 20) both start at $x=5$.

- **The Goal:** We want the skyline to jump immediately from 0 to 20.
- **The Risk:** If we process the Short building (10) first, our heap max goes $0 \to 10$ (Output: `[5, 10]`). Then we process Tall (20), heap max goes $10 \to 20$ (Output: `[5, 20]`).
- **The Artifact:** You get a vertical line with two points at the same $x$: `[[5,10], [5,20]]`. This is invalid output.
- **The Fix:** Process **Taller First**. Max goes $0 \to 20$. Then process Short (max stays 20). Result: `[[5, 20]]`.

**How `-H` solves it:**
- Start A (10) $\to$ `-10`
- Start B (20) $\to$ `-20`
- **Ascending Sort Order:** `-20` comes before `-10`.
- **Result:** Taller building processed first. ✅


#### Case 2: Start vs. End (One building starts where another ends)

Imagine Building A (Height 10) ends at $x=7$. Building B (Height 10) starts at $x=7$.

- **The Goal:** The skyline should be a flat line at height 10. No change.
- **The Risk:** If we process **End A** first, we remove 10 from heap. Max drops to 0. (Output: `[7, 0]`). Then we process **Start B**, add 10. Max goes back to 10. (Output: `[7, 10]`).
- **The Artifact:** A "dip" to the ground and back up: `[[..., 10], [7, 0], [7, 10]]`.
- **The Fix:** Process **Start First**. Add B (heap has two 10s). Max is 10. Then remove A (heap has one 10). Max is still 10. No change recorded.

**How `-H` vs `H` solves it:**

- Start B (10) $\to$ `-10`
- End A (10) $\to$ `+10`
- **Ascending Sort Order:** `-10` comes before `10`.
- **Result:** Start processed before End. 


#### Case 3: End vs. End (Two buildings end at same x)

Imagine Building A (Height 10) and Building B (Height 20) both end at $x=9$.

- **The Goal:** Drop from 20 directly to 0 (or the next height).
- **The Risk:** If we process the **Tall End (20)** first, max drops from 20 to 10. We might output `[9, 10]`. Then process Short End (10), max drops to 0. Output `[9, 0]`.
- **The Artifact:** Redundant steps. We just want the final state at $x=9$.
- **The Fix:** Process **Lower End First**. Remove 10 (Max is still 20). No output. Remove 20 (Max drops to 0). Output `[9, 0]`.

**How `H` solves it:**

- End A (10) $\to$ `+10`
- End B (20) $\to$ `+20`
- **Ascending Sort Order:** `10` comes before `20`.
- **Result:** Lower building processed first. ✅

### Adding the Coordinates
- Initialize a `Max-Heap` (or Multiset) with `0` (representing the ground).
- Iterate through sorted points:
    
    - If it's a **Start** (negative height): Add `|H|` to the heap.
    - If it's an **End** (positive height): Remove `H` from the heap.
    - **Check Max Height:** After processing the event, look at the maximum value in the heap. If the max height has changed from the previous state, this `x` is a "Key Point". Add `[x, current_max]` to the result.


```text
Algorithm: GetSkyline(buildings)
Input: List of buildings [left, right, height]
Output: List of key points [[x, y], ...]

1.  events = []
2.  for each b in buildings:
3.      // Add start point (x=left, y=-height)
4.      events.add(Point(b.left, -b.height))
5.      // Add end point (x=right, y=height)
6.      events.add(Point(b.right, b.height))

7.  // Sort events
    // Primary key: x (ascending)
    // Secondary key: y (ascending) -> This handles the tie-breaking logic
8.  Sort(events)

9.  result = []
10. active_heights = MaxHeap (or Multiset) containing {0}
11. prev_max_height = 0

12. for each e in events:
13.     if e.height < 0:
14.         // Start of a building: Add height to our active set
15.         active_heights.insert(abs(e.height))
16.     else:
17.         // End of a building: Remove height from our active set
18.         active_heights.remove(e.height)
19.     
20.     current_max_height = active_heights.peekMax()
21.     
22.     // If the highest point changed, it's a key point in the skyline
23.     if current_max_height != prev_max_height:
24.         result.add([e.x, current_max_height])
25.         prev_max_height = current_max_height

26. return result
```


## Question 2



## Question 3

```text
Algorithm: ClosestPair(P)
Input: A list of Points P sorted by x-coordinate
Output: Minimum squared Euclidean distance

Function Solve(Points):
    Px = Sort Points by x-coordinate
    Py = Sort Points by y-coordinate
    return Recurse(Px, Py)

Function Recurse(Px, Py):
    n = length(Px)
    
    // Base Case: Use Brute Force for small sets
    if n <= 3:
        return BruteForceDistance(Px)
    
    // 1. Divide
    mid = n / 2
    midPoint = Px[mid]
    
    // Split Px into Left and Right halves
    Px_Left = Px[0 ... mid]
    Px_Right = Px[mid+1 ... n-1]
    
    // Split Py based on x-coordinates to maintain Y-sorting in halves
    // This step is crucial for O(n log n)
    Py_Left = []
    Py_Right = []
    for p in Py:
        if p.x <= midPoint.x:
            Py_Left.append(p)
        else:
            Py_Right.append(p)
            
    // 2. Conquer
    d_left = Recurse(Px_Left, Py_Left)
    d_right = Recurse(Px_Right, Py_Right)
    min_sq_dist = min(d_left, d_right)
    
    // 3. Combine (The Strip)
    // d is the actual distance (sqrt of squared distance) for bandwidth check
    d = sqrt(min_sq_dist) 
    
    // Collect points within 'd' distance of the middle vertical line
    Strip = []
    for p in Py:
        if abs(p.x - midPoint.x) < d:
            Strip.append(p)
            
    // Check for closer pairs in the strip
    for i = 0 to length(Strip) - 1:
        // We only need to check the next few points (geometric constant ~7)
        for j = i + 1 to length(Strip) - 1:
            // Optimization: Stop if vertical distance exceeds current min dist
            if (Strip[j].y - Strip[i].y) >= d:
                break
            
            dist = DistSq(Strip[i], Strip[j])
            min_sq_dist = min(min_sq_dist, dist)
            
    return min_sq_dist
```