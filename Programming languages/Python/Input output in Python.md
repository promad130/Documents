**Expected to know:** [[Input&Output and placeholders]], [[Variables and typecasting in Python]]
**Topics Covered:** [[#Input and Output in Python]]
**Tags:** [[Input&Output and placeholders]]

# Input and Output in Python

Python provides simple and flexible ways to handle input and output (I/O) operations, making it easy to interact with users and display results.

---
## Input in Python
### **Getting User Input:**  

Use the built-in `input()` function to capture user input from the keyboard.

- By default, `input()` always returns a string.
- You can provide a prompt message as an argument to guide the user.

**Examples:**
```python
name = input("Enter your name: ")
print("Hello,", name)
```

To get numeric input, you must explicitly convert the string to the desired type:
```python
age = int(input("Enter your age: "))
price = float(input("Enter the price: "))
```