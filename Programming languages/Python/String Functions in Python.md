**Expected to know:** [[Data Types in Python]] [[Strings]]
**Topics Covered:** [[#String Interpolation]], [[#len()]]
**Tags:** [[Strings]] [[Python]]
The most useful and basic operation regarding string is string interpolation!
# String Interpolation
Done using the `f` keyword before the string! Useful when we want to interpolate variables of different data types!
```python
import math 
a = 3 
b = 4 

print(f"The hypotenuse is {math.sqrt(a**2 + b**2)}.") 

# Output: The hypotenuse is 5.0.
```
```python
name = 'Alice'
age = 30
print(f"Hello, {name}! You are {age} years old.")
# Output: Hello, Alice! You are 30 years old.
```

There are other ways to interpolate strings in Python.

# len()
This function Returns the length of the string given in the parameter!
```python
a = "Hi"
b = len(a)
print(a+b)

#output:hi 2
```

# variable.find()
This function is applied using the dot operator, and is used to find the first occurrence of the given parameter in the string stored in the given `variable`.
```python
name = input("Enter the input:")
firstOccurance = name.find("A")

print(firstOccurance)
```
Output:
```text
Enter the input:hiAhsjdjjAHHAAHAHA
2
```

And if we want the last recurrance, we use rfind() instead of `find()`