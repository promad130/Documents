# Python Data Structures - Complete Reference

## 1. Lists
**Mutable, ordered sequences** - Most versatile data structure

### Creation
```python
empty = []
numbers = [1, 2, 3, 4, 5]
mixed = [1, "hello", 3.14, True]
nested = [[1, 2], [3, 4]]
from_range = list(range(5))  # [0, 1, 2, 3, 4]
```

### Access & Slicing
```python
lst = [10, 20, 30, 40, 50]
lst[0]        # 10 (first element)
lst[-1]       # 50 (last element)
lst[1:4]      # [20, 30, 40] (slice from index 1 to 3)
lst[:3]       # [10, 20, 30] (first 3)
lst[2:]       # [30, 40, 50] (from index 2 onwards)
lst[::2]      # [10, 30, 50] (every 2nd element)
lst[::-1]     # [50, 40, 30, 20, 10] (reverse)
```

### Modification Methods
```python
# Adding elements
lst.append(60)              # Add at end: [10, 20, 30, 40, 50, 60], i.e, modifies the lst
lst2 = lst + lst_           # returns the appended list
lst.insert(2, 25)           # Insert at index 2: [10, 20, 25, 30, 40, 50, 60]
lst.extend([70, 80])        # Extend with another list: [10, 20, 25, 30, 40, 50, 60, 70, 80]

# Removing elements
lst.remove(25)              # Remove first occurrence of 25
popped = lst.pop()          # Remove & return last element
popped = lst.pop(0)         # Remove & return element at index 0
del lst[2]                  # Delete element at index 2
lst.clear()                 # Remove all elements
```

### Searching & Counting
```python
lst = [10, 20, 30, 20, 40]
lst.index(20)               # 1 (first index of 20)
lst.index(20, 2)            # 3 (search from index 2)
lst.count(20)               # 2 (occurrences of 20)
20 in lst                   # True (membership test)
```

### Sorting & Reversing
```python
lst = [3, 1, 4, 1, 5]
lst.sort()                  # [1, 1, 3, 4, 5] (in-place, modifies original)
lst.sort(reverse=True)      # [5, 4, 3, 1, 1] (descending)
sorted(lst)                 # Returns new sorted list, original unchanged
lst.reverse()               # Reverse in-place
```

### Other Operations
```python
len(lst)                    # Length of list
lst.copy()                  # Shallow copy
lst2 = lst[:]               # Another way to copy
min(lst), max(lst)          # Min and max values
sum(lst)                    # Sum of elements (numeric lists)
```

### List Comprehension
```python
squares = [x**2 for x in range(10)]                    # [0, 1, 4, 9, 16, 25, 36, 49, 64, 81]
evens = [x for x in range(20) if x % 2 == 0]         # [0, 2, 4, 6, 8, 10, 12, 14, 16, 18]
pairs = [(x, y) for x in [1, 2] for y in [3, 4]]     # [(1,3), (1,4), (2,3), (2,4)]
```

---

## 2. Tuples
**Immutable, ordered sequences** - Faster than lists, used for fixed data

### Creation
```python
empty = ()
single = (1,)                # Comma required for single element
coords = (10, 20)
mixed = (1, "hello", 3.14)
no_parens = 1, 2, 3          # Also creates tuple
from_list = tuple([1, 2, 3])
```

### Access & Slicing
```python
t = (10, 20, 30, 40, 50)
t[0]          # 10
t[-1]         # 50
t[1:4]        # (20, 30, 40)
t[::-1]       # (50, 40, 30, 20, 10)
```

### Methods (Limited - Immutable!)
```python
t = (1, 2, 3, 2, 4)
t.count(2)                  # 2 (occurrences)
t.index(3)                  # 2 (first index)
len(t)                      # 5
min(t), max(t)              # 1, 4
```

### Unpacking
```python
x, y = (10, 20)             # x=10, y=20
a, b, c = (1, 2, 3)         # a=1, b=2, c=3
first, *rest = (1, 2, 3, 4) # first=1, rest=[2, 3, 4]
```

---

## 3. Sets
**Mutable, unordered collections of unique elements** - Fast membership testing

### Creation
```python
empty = set()               # NOT {} (that's a dict)
numbers = {1, 2, 3, 4, 5}
from_list = set([1, 2, 2, 3])  # {1, 2, 3} (duplicates removed)
```

### Adding & Removing
```python
s = {1, 2, 3}
s.add(4)                    # {1, 2, 3, 4}
s.update([5, 6, 7])         # {1, 2, 3, 4, 5, 6, 7} (add multiple)
s.remove(3)                 # Remove 3 (raises error if not found)
s.discard(10)               # Remove if exists (no error)
popped = s.pop()            # Remove & return arbitrary element
s.clear()                   # Remove all
```

### Set Operations
```python
a = {1, 2, 3, 4}
b = {3, 4, 5, 6}

a | b                       # {1, 2, 3, 4, 5, 6} (union)
a.union(b)                  # Same as above

a & b                       # {3, 4} (intersection)
a.intersection(b)           # Same as above

a - b                       # {1, 2} (difference: in a but not b)
a.difference(b)             # Same as above

a ^ b                       # {1, 2, 5, 6} (symmetric difference)
a.symmetric_difference(b)   # Same as above
```

### Set Comparison
```python
a = {1, 2, 3}
b = {1, 2, 3, 4, 5}

a.issubset(b)               # True (a ⊆ b)
a <= b                      # True (same as above)
b.issuperset(a)             # True (b ⊇ a)
b >= a                      # True (same as above)
a.isdisjoint(b)             # False (have common elements)
```

### Other Operations
```python
s = {1, 2, 3}
len(s)                      # 3
2 in s                      # True (fast membership test)
s.copy()                    # Shallow copy
```

### Set Comprehension
```python
squares = {x**2 for x in range(10)}           # {0, 1, 4, 9, 16, 25, 36, 49, 64, 81}
evens = {x for x in range(20) if x % 2 == 0} # {0, 2, 4, 6, 8, 10, 12, 14, 16, 18}
```

---

## 4. Dictionaries
**Mutable, unordered key-value pairs** - Fast lookups by key

### Creation
```python
empty = {}
empty = dict()
person = {"name": "Alice", "age": 25, "city": "NYC"}
from_tuples = dict([("a", 1), ("b", 2)])
from_kwargs = dict(x=1, y=2, z=3)
```

### Access & Modification
```python
d = {"name": "Alice", "age": 25}

# Access
d["name"]                   # "Alice"
d.get("age")                # 25
d.get("salary", 0)          # 0 (default if key not found)

# Modify/Add
d["age"] = 26               # Update value
d["city"] = "NYC"           # Add new key-value pair

# Remove
del d["city"]               # Delete key-value pair
popped = d.pop("age")       # Remove & return value
popped = d.pop("salary", 0) # With default if not found
key, val = d.popitem()      # Remove & return arbitrary (key, value)
d.clear()                   # Remove all
```

### Dictionary Methods
```python
d = {"a": 1, "b": 2, "c": 3}

# Views (dynamic, reflect changes)
d.keys()                    # dict_keys(['a', 'b', 'c'])
d.values()                  # dict_values([1, 2, 3])
d.items()                   # dict_items([('a', 1), ('b', 2), ('c', 3)])

# Iteration
for key in d:
    print(key, d[key])

for key, value in d.items():
    print(key, value)

# Other
"a" in d                    # True (check key existence)
len(d)                      # 3
d.copy()                    # Shallow copy
```

### Update & Merge
```python
d1 = {"a": 1, "b": 2}
d2 = {"b": 3, "c": 4}

d1.update(d2)               # d1 = {"a": 1, "b": 3, "c": 4} (modifies d1)
d3 = {**d1, **d2}           # Merge using unpacking
d3 = d1 | d2                # Python 3.9+ merge operator
```

### setdefault & defaultdict
```python
d = {"a": 1}
d.setdefault("b", 2)        # Returns 2, sets d["b"] = 2 if not exists
d.setdefault("a", 10)       # Returns 1 (doesn't change existing)

from collections import defaultdict
dd = defaultdict(int)       # Default value for missing keys is 0
dd["count"] += 1            # No KeyError, starts at 0
dd = defaultdict(list)      # Default is empty list
dd["items"].append(1)       # No need to initialize
```

### Dictionary Comprehension
```python
squares = {x: x**2 for x in range(5)}           # {0: 0, 1: 1, 2: 4, 3: 9, 4: 16}
inverted = {v: k for k, v in squares.items()}   # {0: 0, 1: 1, 4: 2, 9: 3, 16: 4}
filtered = {k: v for k, v in squares.items() if v > 5}
```

---

## 5. Strings
**Immutable sequences of characters** - Text processing

### Creation
```python
s = "hello"
s = 'hello'
s = str(123)                # "123"
```

### Access & Slicing
```python
s = "Python"
s[0]          # "P"
s[-1]         # "n"
s[1:4]        # "yth"
s[::-1]       # "nohtyP" (reverse)
```

### Common Methods
```python
s = "  Hello World  "

# Case conversion
s.lower()                   # "  hello world  "
s.upper()                   # "  HELLO WORLD  "
s.capitalize()              # "  hello world  " (first char upper)
s.title()                   # "  Hello World  "
s.swapcase()                # "  hELLO wORLD  "

# Whitespace
s.strip()                   # "Hello World" (remove leading/trailing)
s.lstrip()                  # "Hello World  " (left strip)
s.rstrip()                  # "  Hello World" (right strip)

# Search & Replace
s.find("World")             # 8 (index, -1 if not found)
s.index("World")            # 8 (raises error if not found)
s.count("l")                # 3
s.replace("World", "Python")  # "  Hello Python  "
"World" in s                # True

# Split & Join
"a,b,c".split(",")          # ["a", "b", "c"]
"hello world".split()       # ["hello", "world"] (split on whitespace)
"-".join(["a", "b", "c"])   # "a-b-c"

# Checks
s.startswith("  He")        # True
s.endswith("ld  ")          # True
"123".isdigit()             # True
"abc".isalpha()             # True
"abc123".isalnum()          # True
"   ".isspace()             # True
"Hello".isupper()           # False
"hello".islower()           # True

# Formatting
"{} {}".format("Hello", "World")         # "Hello World"
"{1} {0}".format("World", "Hello")       # "Hello World"
"{name} is {age}".format(name="Alice", age=25)  # "Alice is 25"

# f-strings (Python 3.6+)
name, age = "Alice", 25
f"{name} is {age}"          # "Alice is 25"
f"{name.upper()}"           # "ALICE"
f"{age:05d}"                # "00025" (formatted)

# Other
len(s)                      # 15
s.center(20, "*")           # "**  Hello World  ***"
s.zfill(20)                 # "00000  Hello World  "
```

---

## 6. Common Operations Across Data Structures

### Conversion Between Types
```python
list("abc")                 # ["a", "b", "c"]
tuple([1, 2, 3])            # (1, 2, 3)
set([1, 2, 2, 3])           # {1, 2, 3}
list({1, 2, 3})             # [1, 2, 3] (order not guaranteed)
dict([("a", 1), ("b", 2)])  # {"a": 1, "b": 2}
"".join(["a", "b", "c"])    # "abc"
```

### Membership & Length
```python
x in collection             # Check if x exists
x not in collection         # Check if x doesn't exist
len(collection)             # Number of elements
```

### Iteration
```python
for item in collection:
    print(item)

# With index (lists, tuples, strings)
for i, item in enumerate(collection):
    print(i, item)

# Multiple iterables
for a, b in zip(list1, list2):
    print(a, b)
```

### Built-in Functions
```python
min([1, 2, 3])              # 1
max([1, 2, 3])              # 3
sum([1, 2, 3])              # 6
any([False, True, False])   # True (at least one True)
all([True, True, False])    # False (all must be True)
sorted([3, 1, 2])           # [1, 2, 3] (returns new list)
reversed([1, 2, 3])         # Returns iterator
list(reversed([1, 2, 3]))   # [3, 2, 1]
```

---

## 7. Advanced: collections Module

### Counter
```python
from collections import Counter

cnt = Counter([1, 2, 2, 3, 3, 3])  # Counter({3: 3, 2: 2, 1: 1})
cnt.most_common(2)                  # [(3, 3), (2, 2)]
cnt["nonexistent"]                  # 0 (no KeyError)
```

### deque (Double-ended queue)
```python
from collections import deque

dq = deque([1, 2, 3])
dq.append(4)                # Add to right: deque([1, 2, 3, 4])
dq.appendleft(0)            # Add to left: deque([0, 1, 2, 3, 4])
dq.pop()                    # Remove from right: 4
dq.popleft()                # Remove from left: 0
dq.rotate(1)                # Rotate right: deque([3, 1, 2])
```

### namedtuple
```python
from collections import namedtuple

Point = namedtuple("Point", ["x", "y"])
p = Point(10, 20)
p.x, p.y                    # 10, 20
p[0], p[1]                  # 10, 20 (also accessible by index)
```

---

## 8. Performance Cheat Sheet

| Operation | List | Tuple | Set | Dict |
|-----------|------|-------|-----|------|
| Access by index | O(1) | O(1) | N/A | N/A |
| Access by key | N/A | N/A | N/A | O(1) |
| Search | O(n) | O(n) | O(1) | O(1) |
| Insert/Delete at end | O(1) | N/A | O(1) | O(1) |
| Insert/Delete at start | O(n) | N/A | O(1) | O(1) |
| Memory | Medium | Low | Medium | High |

---

## 9. When to Use What

- **List**: Need ordered, mutable sequence; frequent modifications
- **Tuple**: Immutable data; dictionary keys; faster than lists
- **Set**: Unique elements; fast membership testing; set operations
- **Dictionary**: Key-value mapping; fast lookups by key
- **String**: Text data; immutable sequences of characters

---

## 10. Common Patterns

### Remove duplicates while preserving order
```python
seen = set()
result = [x for x in lst if not (x in seen or seen.add(x))]
```

### Flatten nested list
```python
nested = [[1, 2], [3, 4], [5]]
flat = [item for sublist in nested for item in sublist]  # [1, 2, 3, 4, 5]
```

### Swap dictionary keys and values
```python
inverted = {v: k for k, v in original.items()}
```

### Count occurrences
```python
from collections import Counter
counts = Counter(my_list)
```

### Group items
```python
from collections import defaultdict
grouped = defaultdict(list)
for key, value in items:
    grouped[key].append(value)
```

### Sort dictionary by value
```python
sorted_items = sorted(d.items(), key=lambda x: x[1])
sorted_dict = dict(sorted_items)
```