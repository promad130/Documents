**Expected to know:** [[Data Types and Constants]]
**Topics Covered:**
**Tags:** [[Data Types and Constants]]

## Data Types Offered by Python
Python provides a rich set of built-in data types, many of which go beyond the basic types found in C. Here’s a categorized overview:

---
## **Text Type**
- `str`: Represents Unicode strings (text data).

## **Numeric Types**
- `int`: Integer numbers (arbitrary precision, no fixed size limit).
- `float`: Floating-point numbers (double precision).
- `complex`: Complex numbers with real and imaginary parts (e.g., `3 + 4j`).

## **Sequence Types**
- `list`: Ordered, mutable sequences (can contain mixed data types).
- `tuple`: Ordered, immutable sequences.
- `range`: Immutable sequence of numbers, often used for looping.

## **Mapping Type**
- `dict`: Unordered, mutable collection of key-value pairs (similar to hash tables).

## **Set Types**
- `set`: Unordered, mutable collection of unique elements.
- `frozenset`: Unordered, immutable collection of unique elements.

## **Boolean Type**
- `bool`: Represents `True` or `False` values.

## **Binary Types**
- `bytes`: Immutable sequence of bytes (used for binary data).
- `bytearray`: Mutable sequence of bytes.
- `memoryview`: A memory view object that exposes buffer protocol for binary data.

**None Type**
- `NoneType`: Represents the absence of a value, using the singleton `None`.

---
# **Highlights Compared to C**

- **No need for explicit type declaration:** Python variables are dynamically typed; their type is inferred from the assigned value
- **Arbitrary-precision integers:** Python’s `int` can grow as large as needed, unlike fixed-width integers in C
- **Built-in support for complex numbers:** Python natively supports complex numbers, which are not directly available in C
- **Rich collection types:** Lists, tuples, sets, and dictionaries are built-in and flexible, whereas C requires manual implementation or use of libraries
- **Boolean is a distinct type:** Python has a true `bool` type, while C traditionally uses integers for truth values
- **Binary data types:** Python includes specialized types for handling binary data, which is more manual in C

---
