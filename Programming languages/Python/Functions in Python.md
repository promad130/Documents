
## Functions

**Define a function** using `def` keyword:

```python
def function_name(parameters):
    # code here
    return value
```

**Basic function**:

```python
def greet():
    print("Hello")

greet()  # Call it
```

**Function with parameters**:

```python
def add(a, b):
    return a + b

result = add(3, 5)  # result is 8
```

**Default parameters**:

```python
def greet(name="Guest"):
    print(f"Hello, {name}")

greet()           # Hello, Guest
greet("Alex")     # Hello, Alex
```

**Variable arguments**:

```python
def sum_all(*args):
    return sum(args)

sum_all(1, 2, 3, 4)  # Returns 10
```


## Object-Oriented Programming

**Class** = Blueprint for creating objects

**Define a class**:


```python
class Dog:
    def __init__(self, name, age):
        self.name = name
        self.age = age
    
    def bark(self):
        print(f"{self.name} says woof!")
```

**`__init__`** = Constructor that runs when you create an object

**`self`** = Refers to the current instance of the class

**Create objects**:

```python
dog1 = Dog("Buddy", 3)
dog2 = Dog("Max", 5)

dog1.bark()  # Buddy says woof!
print(dog2.age)  # 5
```
