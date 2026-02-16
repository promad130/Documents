# Easy Questions

## Question 1:

```text
SearchInsertPosition(A, n, v):
	low = 0;
	high = n
	
	while(low < high):
		mid = FLOOR{(high + low)/2}
		if(v < A[mid]):
			high = mid - 1;
		else if(v > A[mid]):
			low = mid + 1;
		else:
			return mid;
		
	if(low == high):
		if(A[low] < v):
			return low + 1;
		else:
			return low;
	else:
		return -1;
```

```C++
class Solution {
public:
    int searchInsert(vector<int>& nums, int target) {
        int low = 0;
        int high = nums.size() - 1;

        while(low < high){
            int mid = low + (high - low)/2;
            
            if(target < nums[mid]) {
                high = mid - 1;
            } 
            else if (target > nums[mid]) {
                low = mid + 1;
            }
            else {
                return mid;
            }
        }
		
        if(nums[low] < target) {
            return low + 1;
        } else {
            return low;
        }
    }
};
```


## Question 2

```text
PerfectSquareText(N):
	if (num < 1) return false; 
	if (num == 1) return true;
	long long low = 0;
	long long high = N/2;
	
	while(low <= high):
		long long mid = low + FLOOR(high - low)/2;
		// If mid == sqrt(N), then we got the answer
		if(mid * mid == N):
			return true;
		else if(mid * mid > N):
			high = mid - 1;
		else
			low = mid + 1;
	
	return false;
```


## Question 3

