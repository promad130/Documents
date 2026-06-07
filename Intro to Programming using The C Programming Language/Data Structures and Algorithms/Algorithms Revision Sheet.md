# Sorting Algorithms
## ![[BubbleSort Rev]]
## ![[InsertionSort Rev]]
## ![[QuickSort Rev]]
## ![[MergeSort Rev]]
## ![[Binary Search Rev]]
# Array Related Algorithms 

## Largest and Second Largest Elements 
What if we are asked to find largest element, or second largest , etc,.?
So Lets look at the brute Force Method:
- Sort the array
- Select the last element = largest
- Select the second largest = second largest element (assuming elements are not repeated)
is this optimal? nope, 
But is it logical? Yep, shows how the algorithm is thought of

Optimal Algorithm:
- Lets say for Largest elements:
	```Text
	LargestElement(A, n):
	int n = -1;
	for (int i = 0; i < n; i++)
	{
		if(A[i] > n)
			n = A[i];
	}
	return n;
	```
- Lets say we need to find the second largest:
	```Text
	SecondLargestElement(A, n):
	n = -1
	n2 = -1
	
	for i in A:
		if i > n
			n = i
			n2 = n
		else if i < n && i > n2
			n2 = i
	return n
	```

## Shifting the Array to Left by D places

so lets say we have `[1,2,3,4,5,6,7]`, so if we say D = 3, then the final array looks like `[4,5,6,7,1,2,3]`
Three ways to approch:
- (Brute)
	- Create an fucttion to shift array 1 place to left
	- Repeat that fuction for D times
- ( Better Approch ) 
	- Store the first D elements in an array K, then
	- Move the next N-D elements D places to the left, i.e., i = 3 to 0, i = 4 to 1,...
	- Then place those D elements from K to the last D elements of the original Array
- (Optimal Approch)
	- Create a method to reverse the array
	- Reverse from start to D
	- Reverse from D to End
	- Reverse the whole array


