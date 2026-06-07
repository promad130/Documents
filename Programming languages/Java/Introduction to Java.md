***(Expected to follow along in [[Object Oriented Programming]] and [[C-sharp Programming Language]])***
## What is Java?
Java is a high-level, general-purpose, object-oriented programming language. It was developed by James Gosling at Sun Microsystems and first released in 1995. 
Today, Java is owned by Oracle. Its most famous feature is “Write Once, Run Anywhere” (WORA): you write Java code once, and it runs on any device or operating system with a Java Virtual Machine (JVM) installed—without changing the source code.

## Key Features of Java
- **Object-Oriented**: Java adopts object-oriented principles, allowing for modular, flexible, and reusable code.
- **Platform-Independent**: Java code compiles to intermediate bytecode, which can be executed on any JVM—whether Windows, Mac, Linux, or other platforms.
- **Simple Syntax**: Its syntax is straightforward and removes complex features like pointers, making it easier for beginners.
- **Secure**: Java was designed with security in mind. It eliminates certain vulnerabilities found in other languages, making it reliable for building secure applications.
- **Multithreaded**: Java supports multithreading, meaning programs can do many tasks at once.
- **Scalable & Robust**: It’s used both for small programs and large enterprise systems, with error handling and memory management features.

## What is Java Used For?
Java powers applications in diverse sectors:
- **Mobile Apps**: Android applications are primarily written in Java.
- **Web Applications**: Numerous web apps and services use Java for their backend.
- **Enterprise Systems**: Large organizations rely on Java for robust, scalable software (e.g., banking systems, ERP portals).
- **Games and Cloud Computing**: Java is used to build games as well as cloud-based software.
- **Big Data & AI**: Java helps process large data sets and forms the backbone of some machine learning frameworks.

# More About Java
## 1. Object-Oriented Programming in Java
- **Java is fully Object-Oriented**: Every Java program is designed using Object-Oriented principles. You _cannot avoid_ OOP in Java.
- **Two Programming Paradigms**:
    - **Process-Oriented:** (like C) — Focuses on code acting on data, linear steps.
    - **Object-Oriented:** (like Java) — Organizes programs around objects and their interactions, making code easier to manage and scale.

## Why OOP?
- Easier to manage complex systems.
- Reflects real-world relationships and behaviors.

---
## 2. Key OOP Concepts
**Java’s OOP principles are:**
- **Encapsulation**
    - Binds data and code (methods) together.
    - Data is hidden inside a class; exposed only through well-defined interfaces (public methods).
    - Private members: Only accessible within the class.
    - Public members: Accessible to other classes.
    - _Real-life analogy:_ A car’s transmission can only be controlled via the gear-shift, not by the headlights—this interface ensures safety and clarity.
    
- **Inheritance**
    - One class (child) inherits attributes and behaviors from another (parent).
    - Supports _hierarchical classification_ (e.g., Animal → Mammal → Dog → Labrador).
    - Promotes code reuse: Child classes only define what’s different; they inherit everything else.
    
- **Polymorphism**
    - "Many forms": The same interface can be used for different underlying forms (methods).
    - Example: Different types of stacks (int, float, char) can share the same operations with different data.
    - Encourages flexible, clean designs.
    
**Combined, these principles let you build robust, maintainable, and scalable programs.**

---
## 3. Structure of a Simple Java Program
- **Every program is inside a class.**
- The entry point is the `main` method:
```    java
class Example 
{     
	public static void main(String[] args) 
	{        
		System.out.println("This is a simple Java program.");    
	} 
	
}   
```
- **Naming conventions:**    
    - The file name should match the class name (`Example.java` for class `Example`).
For now, don't worry about the code written, just assume that its a main function, a main entry point, but for Java.
The things will get clear when we cover [[Object Oriented Java]].

## Compiling and Running
1. **Compile:** Use `javac`
    ```text
    javac Example.java
    ```
    - Produces `Example.class` (bytecode).
2. **Run:** Use `java`
    ```text
    java Example
	```
    
    - Executes your program, producing output:
```text
This is a simple Java program.
```  

---
## 4. Essential Lexical Elements
**Java source code contains:**
- **Whitespace:** Spaces, tabs, newlines. Used to separate tokens but not strictly enforced by Java.
- **Identifiers:** Names for classes, variables, and methods. Must start with a letter, underscore `_`, or `$`; cannot begin with a digit and are case-sensitive.
- **Literals:** Constant values (like numbers, characters, strings).
- **Comments:** Three types:
    - Single-line (`//`)
    - Multi-line (`/* ... */`)
    - Documentation (`/** ... */`)
    
- **Separators:** Characters with special meaning (e.g., `;` ends statements, `{}` group code blocks, `[]` for arrays).
    
- **Keywords:** Reserved words (like `class`, `public`, `static`, `void`). _You cannot use these for variable or class names._

---
## 5. Working With Data and Control
- **Variables and Expressions:** Used to store and manipulate values; must be declared before use (strongly-typed).
- **Operators:** Arithmetic (`+`, `-`, `*`, `/`), assignment (`=`), comparison (`==`, `<`, `>`), logical (`&&`, `||`).
- **Control Statements:** Structures for flow control like `if`, `else`, `switch`, `for`, `while`, etc.
- **Blocks:** Group statements within `{ }` to create logical structure.

---
## 6. The Java Class Libraries
- Java provides **built-in classes** for IO, strings, collections, networking, graphics, etc.
- Most programs use these libraries extensively.

---
## 7. Recap: Writing Your First Java Program
- Start by defining a class matching your file name.
- Write the `main` method (`public static void main(String[] args)`).
- Use statements inside `main` to perform operations.
- Compile and run as demonstrated above.

# Compiling a java file

# Getting started!
First have a look at ![[Syntax of Java]]

---
# Elements of the language:
Now that we know about the syntax and implementation of Java, lets have a look at the basics of our programming language:
- [[Variables in Java]]
- [[Data types in Java]]
- [[TypeCasting in Java]]
- [[Operators in Java]] 
- [[Input&Output in Java]] 
- [[Control Flow Statements in Java]]
- [[Data Structures in Java]]
Now lets have a look at object oriented part of the java programming language:
- [[Object Oriented Java]]
We are ought too follow [[Naming Conventions in Object Oriented Programming]] for every program we write.
Now time for a **break**, [we buy a chocolate and discover that it has a **WRAPPER!!!**](Wrapper%20classes)

The arguments of main function in java are an string array.In Java, the `main` method is the entry point for any standalone application. Its standard signature is:
```java
public static void main(String[] args)
```
The parameter: `String[] args`:
- This means the `main` method **accepts one parameter:** an array of `String` objects.
- The parameter name `args` is a common convention (short for “arguments”), but you can use any valid identifier.
- The array holds the **command-line arguments** passed to the program when it starts.

When you run a Java program from the command line, you can pass additional information (arguments) to it, like this:
```text 
    java MyProgram arg1 arg2 "arg 3"
```

The JVM takes these passed arguments and populates the `args` array with them as strings:
```text
    args[0] = "arg1" args[1] = "arg2" args[2] = "arg 3"
```    
- This lets the program read and use inputs dynamically without modifying code, enabling flexible behavior.

**Example:**
```java
public class HelloWorld 
{     
	public static void main(String[] args) 
	{        
		System.out.println("Number of args: " + args.length);        
		for (String arg : args) 
		{            
			System.out.println(arg);        
		}    
	} 
} 
// Running the program: 
// java HelloWorld Hello Java World 

// Output: 
// Number of args: 3 
// Hello 
// Java 
// World
```

# Generics in Java
## What Are Generics and Why Do We Need Them?
Generics allow you to write code that works with **different types** while maintaining **type safety.** Think of generics as creating a "template" or "placeholder" for a type.

### The Problem Without Generics
Before Java 5 (when generics were introduced), here's what happened:
```java
// Old way (without generics) - PROBLEMS!
HashMap list = new HashMap();
list.put("Hello", 123);
list.put(123, "Hello");        // Oops! Can mix types
list.put(45, 23.534);       // No compile-time checking

// Need to cast - risky!
String str = (String) list.get(123);  // Works
String str2 = (String) list.get(45); // RUNTIME ERROR! ClassCastException

```

**Problems:**
1. No type safety - can add any type
2. Need explicit casting
3. Errors only appear at runtime (crashes!)

The solution would be generics, i.e., `<>`
```java
HashMap<String, int> list = new HashMap();
list.put("Hello", 123);
list.put(123, "Hello"); // Compile-Time Error
```

**Benefits:**
1. **Type Safety:** Compiler catches errors
2. **No Casting:** Cleaner code
3. **Code Reusability:** Same code works with different types
4. **Better Readability:** Clear what type is stored

### Generic Syntax: The `<T>` Notation
The angle brackets `<>` contain **type parameters**:
```java
<T>        // T stands for "Type" (most common)
<E>        // E stands for "Element" (used in collections)
<K, V>     // K = Key, V = Value (used in maps)
<N>        // N = Number
```
**Note:** These are just conventions! You could use any letter, but these make code readable.
In generics, the `extends` keyword is used for **BOTH** classes and interfaces.

### Generic Classes
Lets create a simple "Box" class that can hold any type:
```java
// Generic class definition
class Box<T> {
    private T item;  // T is a placeholder
    
    // Constructor
    public Box(T item) {
        this.item = item;
    }
    
    // Setter
    public void set(T item) {
        this.item = item;
    }
    
    // Getter
    public T get() {
        return item;
    }
}

// Using the generic class
public class GenericsDemo {
    public static void main(String[] args) {
        // Box for Strings
        Box<String> stringBox = new Box<>("Hello");
        /*
	        Well one could also do:
	        Box<String> stringBox = new Box<String>("Hello");
	        This is also valid, but <> is called diamond operator, 
	        introduced after java 7 so to avoid writing the type again and again
        */
        String str = stringBox.get();  // No casting!
        System.out.println(str);       // Output: Hello
        
        // Box for Integers
        Box<Integer> intBox = new Box<>(42);
        int num = intBox.get();        // No casting!
        System.out.println(num);       // Output: 42
        
        // Box for Double
        Box<Double> doubleBox = new Box<>(3.14);
        double value = doubleBox.get();
        System.out.println(value);     // Output: 3.14
    }
}
```

**What's happening?**
- `Box<String>` creates a Box that only holds Strings
- `Box<Integer>` creates a Box that only holds Integers
- Same class, different types - that's the power of generics!
- In generics, the `extends` keyword is used for **BOTH** classes and interfaces.

### Generic Methods
You can also create methods that work with any type:
```java
public class GenericMethodDemo {
    
    // Generic method - <T> before return type
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        // Works with Integer array
        Integer[] intArray = {1, 2, 3, 4, 5};
        printArray(intArray);  // Output: 1 2 3 4 5
        
        // Works with String array
        String[] strArray = {"Hello", "World"};
        printArray(strArray);  // Output: Hello World
        
        // Works with Double array
        Double[] doubleArray = {1.1, 2.2, 3.3};
        printArray(doubleArray);  // Output: 1.1 2.2 3.3
    }
}
```

### Multiple Type Parameters:
```java
class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Usage
Pair<String, Integer> pair = new Pair<>("Age", 25);
System.out.println(pair.getKey() + ": " + pair.getValue());
// Output: Age: 25
```

### Bounded Type Parameter in genarics
This is a powerful feature that gives your code flexibility AND safety
**Bounded Type Parameters** allow you to restrict *what kinds of types* can be used as arguments. Think of it as setting rules: *"You can use any type T, as long as it follows these conditions."*

#### 1. The Syntax
You use the `extends` keyword to set an **Upper Bound**.
```java
<T extends SuperClass> 
```

This means `T` must be either **SuperClass** OR a **subclass** of it.[^2]

#### 2. Why is this useful?
Without bounds, `T` is treated like a plain `Object`. You can only call `Object` methods (like `toString()`, `equals()`).
By adding a bound, you can call methods defined in that parent class!

##### Example: A Number Calculator
If we want a class that only works with Numbers (Integer, Double, Float), we restrict it:
```java
// T must be a Number or its subclass
class Stats<T extends Number> {
    private T[] numbers;

    public Stats(T[] numbers) {
        this.numbers = numbers;
    }

    public double average() {
        double sum = 0.0;
        for (T num : numbers) {
            // Because T extends Number, we can call .doubleValue()!
            // If we didn't use "extends Number", this line would fail.
            sum += num.doubleValue(); 
        }
        return sum / numbers.length;
    }
}
```

**Usage:**
```java
Integer[] ints = {1, 2, 3};
Stats<Integer> iStats = new Stats<>(ints); // Works! Integer extends Number

Double[] doubles = {1.1, 2.2};
Stats<Double> dStats = new Stats<>(doubles); // Works! Double extends Number

String[] strs = {"1", "2"};
// Stats<String> sStats = new Stats<>(strs); // COMPILE ERROR! String is not a Number
```

#### 3. Using Multiple Bounds
What if you need `T` to be a `Number` **AND** also implement `Comparable`?
You can use the `&` operator.
```java
// T must extend Number AND implement Comparable
// In generics, the `extends` keyword is used for **BOTH** classes and interfaces.
class Sorter<T extends Number & Comparable<T>> { 
    // ...
}
```

**Rules for Multiple Bounds:**
1. If you have a Class, it **must** come first.
2. You can have only **one** Class (since Java doesn't support multiple inheritance of classes).
3. You can have **multiple** Interfaces.

#### 4. Bounded Wildcards (The `?` Syntax)
Sometimes you'll see this in method parameters. This is slightly different but related.

##### Upper Bounded Wildcard (`? extends Type`)
Accepts the Type or any of its subclasses. Useful for **reading** data.
```java
// Accepts List<Number>, List<Integer>, List<Double>...
public void printNumbers(List<? extends Number> list) {
    for (Number n : list) {
        System.out.println(n);
    }
}
```

##### Lower Bounded Wildcard (`? super Type`)
Accepts the Type or any of its **superclasses**. Useful for **writing** data.
```java
// Accepts List<Integer>, List<Number>, List<Object>
public void addIntegers(List<? super Integer> list) {
    list.add(10); // Safe because we know the list can hold Integers
}
```

#### Summary Cheat Sheet

| Syntax | Name | Meaning | Use Case |
| :-- | :-- | :-- | :-- |
| `<T>` | Unbounded | Any type | Basic generic container |
| `<T extends Foo>` | Upper Bound | `Foo` or subclass | Need to call `Foo` methods |
| `<T extends Foo & Bar>` | Multiple Bound | Subclass of `Foo` AND implements `Bar` | Need specific capabilities |
| `<?>` | Wildcard | Unknown type | Don't care about the type |
| `<? extends Foo>` | Upper Wildcard | `Foo` or subclass | Reading data (Producer) |
| `<? super Foo>` | Lower Wildcard | `Foo` or superclass | Writing data (Consumer) |

# [[Java Collection Interface]]
The **Collection interface** is the **root interface** (top-level parent) of the Java Collections Framework. Think of it as the "blueprint" that defines what ANY collection of objects should be able to do.
```text
                    Iterable<E>
                        |
                   Collection<E>
                        |
        +---------------+---------------+
        |               |               |
      List<E>         Set<E>         Queue<E>
        |               |               |<int>
        |               +---+           +---+
        |               |   |           |   |
        |          SortedSet HashSet  Deque PriorityQueue
        |               |              |
        |          NavigableSet     ArrayDeque
        |               |
        |           TreeSet
        |
        +---+---+---+
        |   |   |   |
    ArrayList LinkedList Vector CopyOnWriteArrayList
              |
           Stack


                    Map<K,V>
                        |
        +---------------+---------------+
        |               |               |
     HashMap        SortedMap      Hashtable
        |               |          (legacy)
    LinkedHashMap  NavigableMap
                        |
                    TreeMap
```

Each of them has an ADT that tells about what data it can work on, and what all operations can be done while hiding the implementation adding a layer of abstraction.

# Iterating through different Java Collections
An Iterator in Java is an interface from the `java.util` package that provides a standardized way to traverse through collections and access their elements sequentially.
The Iterator interface defines three essential methods:
- **hasNext()**: Returns `true` if the iteration has more elements remaining to traverse
- **next()**: Returns the next element in the iteration and advances the cursor forward; throws `NoSuchElementException` if no more elements exist
- **remove()**: Removes the last element returned by `next()` from the underlying collection; can only be called once per `next()` call

The iterator maintains an internal pointer that starts before the first element. When you call `next()`, it returns the current element and advances the cursor to the next position. The `hasNext()` method checks whether there are more elements beyond the current cursor position, returning `false` when the cursor has advanced past the last element.

```Java
ArrayList<String> cars = new ArrayList<>();
cars.add("Volvo");
cars.add("BMW");
cars.add("Ford");

Iterator<String> it = cars.iterator();

while(it.hasNext()) {
    String car = it.next();
    System.out.println(car);
}
```



# Packages in Java
## What are Packages? 
**Packages** are namespaces that organize classes and interfaces into logical groups. Think of them as folders in a file system. 

### Purpose of Packages: 
1. **Organization**: Group related classes together
2. **Namespace Management**: Avoid naming conflicts
3. **Access Control**: Control visibility with access modifiers
4. **Reusability**: Easy to find and reuse classes
5. **Modularity**:  Separate different parts of applications

---
## Package Syntax
```java
package packagename;
// OR
package com.company.projectname;
```

**Rules:**
- Package statement must be the **first line** (except comments)
- Only **one package statement** per file
- Use lowercase letters by convention
- Use domain name in reverse for uniqueness:  `com.google.project`

---
## Example 1: Creating Basic Packages

### Project Structure: 
```
MyProject/
├── com/
│   └── mycompany/
│       └── myapp/
│           ├── Main.java
│           ├── models/
│           │   ├── User.java
│           │   └── Product.java
│           ├── services/
│           │   ├── UserService.java
│           │   └── ProductService.java
│           └── utils/
│               ├── StringHelper.java
│               └── DateHelper.java
```

### Creating Classes in Packages:

```java name=User.java
package com.mycompany.myapp.models;

public class User {
    private int id;
    private String name;
    private String email;
    
    public User(int id, String name, String email) {
        this.id = id;
        this. name = name;
        this. email = email;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
```

```java name=Product.java
package com.mycompany.myapp.models;

public class Product {
    private int id;
    private String name;
    private double price;
    
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}
```

```java name=UserService.java
package com.mycompany.myapp.services;

import com. mycompany.myapp.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;
    
    public UserService() {
        users = new ArrayList<>();
    }
    
    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user. getName());
    }
    
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    public List<User> getAllUsers() {
        return users;
    }
    
    public void displayAllUsers() {
        System.out.println("=== All Users ===");
        for (User user : users) {
            System.out.println(user);
        }
    }
}
```

```java name=ProductService.java
package com.mycompany.myapp.services;

import com. mycompany.myapp.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private List<Product> products;
    
    public ProductService() {
        products = new ArrayList<>();
    }
    
    public void addProduct(Product product) {
        products.add(product);
        System.out. println("Product added: " + product.getName());
    }
    
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
    
    public List<Product> getAllProducts() {
        return products;
    }
    
    public void displayAllProducts() {
        System.out.println("=== All Products ===");
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
```

```java name=StringHelper.java
package com.mycompany.myapp.utils;

public class StringHelper {
    
    // Capitalize first letter
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    // Reverse string
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }
    
    // Check if palindrome
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        String reversed = reverse(str);
        return str.equalsIgnoreCase(reversed);
    }
}
```

```java name=DateHelper.java
package com.mycompany.myapp.utils;

import java. time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    
    private static final DateTimeFormatter DEFAULT_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String getCurrentDate() {
        return LocalDate.now().format(DEFAULT_FORMATTER);
    }
    
    public static String formatDate(LocalDate date) {
        return date.format(DEFAULT_FORMATTER);
    }
    
    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DEFAULT_FORMATTER);
    }
}
```

```java name=Main.java
package com.mycompany.myapp;

// Importing classes from different packages
import com.mycompany.myapp. models.User;
import com. mycompany.myapp.models.Product;
import com.mycompany.myapp.services. UserService;
import com.mycompany.myapp.services. ProductService;
import com.mycompany.myapp.utils. StringHelper;
import com.mycompany.myapp.utils. DateHelper;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Package Demo ===\n");
        
        // Using classes from models package
        User user1 = new User(1, "John Doe", "john@example.com");
        User user2 = new User(2, "Jane Smith", "jane@example.com");
        
        Product product1 = new Product(101, "Laptop", 999.99);
        Product product2 = new Product(102, "Mouse", 29.99);
        
        // Using classes from services package
        UserService userService = new UserService();
        userService.addUser(user1);
        userService.addUser(user2);
        userService.displayAllUsers();
        
        System.out.println();
        
        ProductService productService = new ProductService();
        productService.addProduct(product1);
        productService. addProduct(product2);
        productService.displayAllProducts();
        
        System.out.println();
        
        // Using classes from utils package
        System.out.println("=== String Helper ===");
        String text = "hello";
        System.out.println("Original: " + text);
        System.out.println("Capitalized: " + StringHelper.capitalize(text));
        System.out.println("Reversed: " + StringHelper.reverse(text));
        System.out.println("Is 'racecar' palindrome?  " + 
                         StringHelper.isPalindrome("racecar"));
        
        System.out.println();
        
        System.out. println("=== Date Helper ===");
        System.out.println("Current Date: " + DateHelper.getCurrentDate());
    }
}
```

---

## Import Statements

### Types of Import:

#### 1. **Single Class Import** (Recommended)
```java
import com.mycompany.myapp.models. User;
import com.mycompany.myapp.models.Product;
```

#### 2. **Wildcard Import** (Import all classes from package)
```java
import com.mycompany.myapp.models.*;
import com.mycompany.myapp.services.*;
```

#### 3. **Static Import** (Import static members)
```java
import static com.mycompany.myapp.utils.StringHelper. capitalize;
import static com.mycompany.myapp.utils.StringHelper.*;
import static java.lang.Math.*;

// Now can use directly without class name
String result = capitalize("hello");
double value = sqrt(16);
```

#### 4. **Fully Qualified Name** (No import needed)
```java
com.mycompany.myapp. models.User user = 
    new com.mycompany.myapp.models.User(1, "John", "john@example.com");
```

---

## Example 2: Import Examples
```java name=ImportExamples.java
package com.mycompany.myapp;

// Single class import
import com.mycompany.myapp.models.User;

// Wildcard import
import com.mycompany.myapp.services.*;

// Static import
import static com.mycompany.myapp.utils.StringHelper. capitalize;
import static com.mycompany. myapp.utils.StringHelper. reverse;

// Java built-in packages
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ImportExamples {
    public static void main(String[] args) {
        
        // Using imported class
        User user = new User(1, "Alice", "alice@example.com");
        System.out.println(user);
        
        // Using wildcard imported class
        UserService userService = new UserService();
        userService.addUser(user);
        
        // Using static imported methods (no class name needed)
        String name = "john";
        System.out.println("Capitalized: " + capitalize(name));
        System.out.println("Reversed: " + reverse(name));
        
        // Using fully qualified name (no import needed)
        com.mycompany.myapp. models.Product product = 
            new com. mycompany.myapp.models.Product(1, "Laptop", 999.99);
        System.out. println(product);
        
        // Java built-in classes
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
    }
}
```

---
## Built-in Java Packages
Java provides many built-in packages: 

### 1. **java. lang** (Automatically imported)
```java name=JavaLangExample.java
package com.mycompany.examples;

// java.lang is imported automatically - no import needed! 

public class JavaLangExample {
    public static void main(String[] args) {
        // String, System, Math, Integer, etc.  are from java.lang
        String text = "Hello"; // java.lang.String
        System. out.println(text); // java.lang.System
        
        int max = Math.max(10, 20); // java.lang.Math
        System.out.println("Max: " + max);
        
        Integer num = Integer.parseInt("123"); // java.lang.Integer
        System.out.println("Number: " + num);
        
        // Exception classes
        try {
            throw new Exception("Test exception"); // java.lang.Exception
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
```

### 2. **java.util** (Collections, Date, Random, etc.)
```java name=JavaUtilExample.java
package com.mycompany.examples;

import java.util.*;

public class JavaUtilExample {
    public static void main(String[] args) {
        // Collections
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        System.out.println("List: " + list);
        
        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        System.out.println("Map: " + map);
        
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(1); // Duplicate ignored
        System.out.println("Set: " + set);
        
        // Date
        Date date = new Date();
        System.out.println("Date: " + date);
        
        // Random
        Random random = new Random();
        int randomNum = random.nextInt(100);
        System.out.println("Random: " + randomNum);
        
        // Scanner
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        // String name = scanner.nextLine();
        // System.out.println("Hello, " + name);
    }
}
```

### 3. **java.io** (Input/Output)
```java name=JavaIOExample.java
package com.mycompany. examples;

import java.io.*;

public class JavaIOExample {
    public static void main(String[] args) {
        // File operations
        try {
            // Writing to file
            FileWriter writer = new FileWriter("test.txt");
            writer.write("Hello, World!\n");
            writer.write("This is a test file.");
            writer.close();
            System.out.println("File written successfully");
            
            // Reading from file
            FileReader reader = new FileReader("test.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            String line;
            System.out.println("\nFile contents:");
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
            
            // File info
            File file = new File("test.txt");
            System.out.println("\nFile exists: " + file. exists());
            System.out. println("File size: " + file.length() + " bytes");
            
        } catch (IOException e) {
            System.out. println("Error: " + e. getMessage());
        }
    }
}
```

### 4. **java.time** (Date and Time API - Java 8+)
```java name=JavaTimeExample.java
package com.mycompany.examples;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class JavaTimeExample {
    public static void main(String[] args) {
        // LocalDate
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        
        LocalDate birthday = LocalDate.of(1990, 5, 15);
        System.out.println("Birthday: " + birthday);
        
        // LocalTime
        LocalTime now = LocalTime.now();
        System.out.println("Current time: " + now);
        
        // LocalDateTime
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("Date and Time: " + dateTime);
        
        // Formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatted = dateTime.format(formatter);
        System.out.println("Formatted: " + formatted);
        
        // Duration
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 30);
        Duration duration = Duration.between(start, end);
        System.out.println("Work hours: " + duration.toHours() + " hours");
        
        // Period
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Period period = Period.between(startDate, endDate);
        System.out.println("Period: " + period.getYears() + " years, " + 
                         period.getMonths() + " months");
    }
}
```

---

## Access Control with Packages
```java name=AccessModifierDemo.java
package com.mycompany.access;

public class AccessModifierDemo {
    
    // Public - accessible from anywhere
    public String publicField = "Public";
    
    // Protected - accessible within package and subclasses
    protected String protectedField = "Protected";
    
    // Default (no modifier) - accessible only within package
    String defaultField = "Default";
    
    // Private - accessible only within this class
    private String privateField = "Private";
    
    
    public void displayAll() {
        System.out. println("Public: " + publicField);
        System.out.println("Protected: " + protectedField);
        System.out.println("Default: " + defaultField);
        System.out.println("Private: " + privateField);
    }
}
```

```java name=SamePackageAccess.java
package com.mycompany.access;

public class SamePackageAccess {
    public static void main(String[] args) {
        AccessModifierDemo demo = new AccessModifierDemo();
        
        System.out.println("=== Same Package Access ===");
        System.out.println(demo.publicField);    // OK
        System.out.println(demo.protectedField); // OK
        System. out.println(demo.defaultField);   // OK
        // System.out.println(demo. privateField); // Error! 
    }
}
```

```java name=DifferentPackageAccess.java
package com.mycompany. other;

import com.mycompany.access. AccessModifierDemo;

public class DifferentPackageAccess {
    public static void main(String[] args) {
        AccessModifierDemo demo = new AccessModifierDemo();
        
        System. out.println("=== Different Package Access ===");
        System.out.println(demo.publicField);      // OK
        // System.out.println(demo. protectedField); // Error!  (unless subclass)
        // System.out.println(demo. defaultField);   // Error! 
        // System.out.println(demo. privateField);   // Error!
    }
}
```

```java name=SubclassAccess.java
package com.mycompany.other;

import com.mycompany.access. AccessModifierDemo;

public class SubclassAccess extends AccessModifierDemo {
    public void testAccess() {
        System.out.println("=== Subclass in Different Package ===");
        System.out.println(publicField);      // OK
        System.out.println(protectedField);   // OK (inherited)
        // System.out.println(defaultField);  // Error! 
        // System.out.println(privateField);  // Error! 
    }
}
```

## Note:
Access Modifier Revision:

| Modifier      | Same Class | Same Package | Subclass (Different Package) | Other Package |
| ------------- | ---------- | ------------ | ---------------------------- | ------------- |
| **public**    | 1          | 1            | 1                            | 1             |
| **protected** | 1          | 1            | 1                            | 0             |
| **default**   | 1          | 1            | 0                            | 0             |
| **private**   | 1          | 0            | 0                            | 0             |

## Compiling and Running with Packages

### From Command Line: 
```bash
# Compile (from root directory)
javac com/mycompany/myapp/Main.java

# Run (from root directory)
java com.mycompany.myapp.Main

# Compile all files in package
javac com/mycompany/myapp/*. java
javac com/mycompany/myapp/models/*.java
javac com/mycompany/myapp/services/*. java

# Or compile everything
javac com/mycompany/myapp/**/*.java
```

### Setting CLASSPATH:
**CLASSPATH** is a parameter that tells the Java Virtual Machine (JVM) and Java compiler **where to look for user-defined classes and packages**.

Think of it like a **GPS for Java** - it tells Java where to find the files it needs to run your program. 

#### Why Do We Need CLASSPATH?
When you write: 
```java
import com.myapp.models.User;
```

Java needs to know:
- **Where is the `User. class` file located on my computer?**
- **Which folders should I search? **

CLASSPATH answers these questions! 

#### Simple Analogy
Imagine you're looking for a book: 

```
Without CLASSPATH: 
"Find me the book 'Java Programming'"
Where do I look?  🤷

With CLASSPATH:
"Find me the book 'Java Programming' 
 Look in:  1. My desk (.)
          2. Library (/home/user/books)
          3. Friend's house (/home/friend/books)"
Now I know where to search!  ✅
```

#### How Java Uses CLASSPATH
##### Example 1: Basic Understanding
``` text
Your code: 
  java com.example.HelloWorld

Java thinks:
  "I need to find: com/example/HelloWorld.class"
  
  "Where should I look?  Check CLASSPATH!"
  
  CLASSPATH = .:~/myapp/bin
  
  Search process:
  1. Look in .  (current directory)
     → Check:  ./com/example/HelloWorld. class ❌ Not found
     
  2. Look in ~/myapp/bin
     → Check: ~/myapp/bin/com/example/HelloWorld.class ✅ FOUND!
     
  Load and run the class! 
```

#### Visual Example
##### Project Structure:
```
MyProject/
├── src/
│   └── com/
│       └── myapp/
│           ├── Main.java
│           └── models/
│               └── User.java
├── bin/                    ← Compiled . class files go here
│   └── com/
│       └── myapp/
│           ├── Main.class
│           └── models/
│               └── User.class
└── lib/                    ← External JAR files
    ├── mysql-connector.jar
    └── commons-lang.jar
```

##### Without CLASSPATH: 

```bash
cd ~/MyProject
java com.myapp.Main

# ❌ Error: Could not find or load main class com. myapp.Main
# Java doesn't know to look in 'bin' folder! 
```

##### With CLASSPATH:
```bash
cd ~/MyProject
java -cp bin com.myapp.Main

# ✅ Success! Java knows to look in 'bin' folder
# Java finds:  bin/com/myapp/Main. class
```

---

#### Complete Working Example

##### Step 1: Create the Files
```java name=Main.java
// File location: ~/JavaProjects/MyApp/src/com/myapp/Main.java
package com.myapp;

import com.myapp.models.User;
import com.myapp.utils.Helper;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Application Started ===");
        
        User user = new User("Alice", 25);
        user.display();
        
        Helper.printMessage("Hello from Helper!");
    }
}
```

```java name=User.java
// File location: ~/JavaProjects/MyApp/src/com/myapp/models/User.java
package com.myapp.models;

public class User {
    private String name;
    private int age;
    
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void display() {
        System.out.println("User: " + name + ", Age: " + age);
    }
}
```

```java name=Helper.java
// File location: ~/JavaProjects/MyApp/src/com/myapp/utils/Helper.java
package com.myapp.utils;

public class Helper {
    public static void printMessage(String message) {
        System.out.println("Helper says: " + message);
    }
}
```

##### Step 2: Understanding Directory Structure
```
Before Compilation:
~/JavaProjects/MyApp/
└── src/
    └── com/
        └── myapp/
            ├── Main.java
            ├── models/
            │   └── User.java
            └── utils/
                └── Helper.java

After Compilation:
~/JavaProjects/MyApp/
├── src/        ← Source code (. java files)
│   └── ... 
└── bin/        ← Compiled code (.class files)
    └── com/
        └── myapp/
            ├── Main.class
            ├── models/
            │   └── User.class
            └── utils/
                └── Helper.class
```

##### Step 3: Compile (Create .class files)

```bash
# Navigate to project
cd ~/JavaProjects/MyApp

# Create bin directory
mkdir -p bin

# Compile:  -d tells where to put . class files
javac -d bin src/com/myapp/Main.java src/com/myapp/models/User.java src/com/myapp/utils/Helper.java

# OR compile all at once
javac -d bin src/com/myapp/*. java src/com/myapp/**/*.java
```

**What happened?**
- Java compiler read `.java` files from `src/`
- Created `.class` files in `bin/`
- Maintained package structure in `bin/`

##### Run WITHOUT Setting CLASSPATH (FAILS)

```bash
cd ~/JavaProjects/MyApp

# Try to run
java com.myapp.Main

# ❌ ERROR: 
# Error: Could not find or load main class com.myapp.Main
# Caused by: java.lang.ClassNotFoundException: com.myapp.Main
```

**Why did it fail?**
- Java looked in current directory:  `~/JavaProjects/MyApp/`
- Expected to find:  `~/JavaProjects/MyApp/com/myapp/Main.class`
- But actual location is: `~/JavaProjects/MyApp/bin/com/myapp/Main.class`
- Java doesn't automatically know to look in `bin/`! 

##### Run WITH CLASSPATH (SUCCESS)

```bash
cd ~/JavaProjects/MyApp

# Method 1: Use -cp option
java -cp bin com.myapp.Main

# Method 2: Set CLASSPATH environment variable
export CLASSPATH=bin
java com.myapp.Main

# Method 3: Use absolute path
java -cp ~/JavaProjects/MyApp/bin com.myapp.Main

# ✅ Output:
# === Application Started ===
# User:  Alice, Age: 25
# Helper says: Hello from Helper! 
```

**Why did it work?**
- CLASSPATH told Java:  "Look in the `bin` directory"
- Java found:  `bin/com/myapp/Main.class` ✅
- When Main.class tried to import User.class, Java found: `bin/com/myapp/models/User.class` ✅
- When Main.class tried to import Helper. class, Java found: `bin/com/myapp/utils/Helper.class` ✅

#### CLASSPATH with Multiple Locations
##### Example 2: Multiple Directories

```
Project Structure:
~/Projects/
├── AppCode/
│   └── com/
│       └── myapp/
│           └── Main.class
└── SharedLibs/
    └── com/
        └── utils/
            └── CommonHelper.class
```

```java name=Main.java
package com.myapp;

import com.utils.CommonHelper;  // From different location! 

public class Main {
    public static void main(String[] args) {
        CommonHelper. doSomething();
    }
}
```

**How to run:**

```bash
# Java needs to search BOTH locations
# Linux/Mac (:  separator)
java -cp ~/Projects/AppCode: ~/Projects/SharedLibs com.myapp.Main

# Windows (; separator)
java -cp C:\Projects\AppCode;C:\Projects\SharedLibs com.myapp.Main
```

**Java's search process:**
```
Looking for com.myapp.Main:
1. Check ~/Projects/AppCode/com/myapp/Main.class ✅ FOUND

Looking for com.utils.CommonHelper:
1. Check ~/Projects/AppCode/com/utils/CommonHelper.class ❌ Not found
2. Check ~/Projects/SharedLibs/com/utils/CommonHelper.class ✅ FOUND
```

#### CLASSPATH with JAR Files
##### Example 3: Using External Libraries

```
Project Structure:
~/MyApp/
├── bin/
│   └── com/
│       └── myapp/
│           └── Main.class
└── lib/
    ├── mysql-connector-java-8.0.28.jar
    └── commons-lang3-3.12.0.jar
```

```java name=Main.java
package com. myapp;

import java.sql.Connection;  // From mysql-connector JAR
import org.apache.commons.lang3.StringUtils;  // From commons-lang JAR

public class Main {
    public static void main(String[] args) {
        // Use classes from JARs
        String text = "  hello  ";
        String trimmed = StringUtils.strip(text);
        System.out.println("Trimmed: '" + trimmed + "'");
    }
}
```

**Compile with JARs:**
```bash
# Linux/Mac
javac -cp ".: lib/*" -d bin src/com/myapp/Main.java

```

**Run with JARs:**
```bash
# Linux/Mac
java -cp "bin:lib/*" com.myapp.Main

# Windows  
java -cp "bin;lib\*" com. myapp.Main
```

**What does `lib/*` mean?**
- Include **ALL** `.jar` files in the `lib` directory
- Equivalent to:  `lib/mysql-connector. jar: lib/commons-lang.jar`
- The `-d` flag in `javac` stands for **destination directory**. It specifies where the compiled `.class` files should be placed

---
#### Different Ways to Set CLASSPATH
##### Method 1: Command-Line Option (RECOMMENDED)
```bash
# Explicit and clear - best practice
java -cp bin: lib/* com.myapp.Main
java -classpath bin:lib/* com.myapp.Main  # Same as -cp
```

**Pros:**
- ✅ Explicit and visible
- ✅ Doesn't affect other programs
- ✅ Easy to debug

##### Method 2: Environment Variable (Temporary)
```bash
# Linux/Mac
export CLASSPATH=bin:lib/*
java com.myapp.Main

# Windows
set CLASSPATH=bin;lib\*
java com.myapp.Main
```

**Pros:**
- Don't need to repeat for each command

**Cons:**
- Only lasts for current terminal session
- Affects all Java programs in that session

##### Method 3: Environment Variable (Permanent)
```bash
# Linux/Mac - Add to ~/.bashrc or ~/.bash_profile
echo 'export CLASSPATH=.: ~/mylibs/*' >> ~/.bashrc
source ~/.bashrc

# Windows - System Environment Variables
# Set permanently through System Properties → Environment Variables
```

**Pros:**
- Set once, works everywhere

**Cons:**
- Can cause conflicts between different projects
- Hard to debug when things go wrong
-**NOT RECOMMENDED** for most cases

---
#### CLASSPATH Components Explained
##### The Dot (`.`)
```bash
CLASSPATH=.
```

**Means:** Look in the **current directory** (wherever you are when you run the command)

```bash
# If you're in ~/MyApp/
java -cp . com.myapp.Main
# Looks for: ~/MyApp/com/myapp/Main.class

# If you're in ~/MyApp/bin/
java -cp .  com.myapp.Main
# Looks for: ~/MyApp/bin/com/myapp/Main.class
```

##### Absolute Paths
```bash
# Linux/Mac
CLASSPATH=/home/user/myapp/bin:/home/user/libs

# Windows
CLASSPATH=C:\Users\user\myapp\bin;C:\Users\user\libs
```

**Means:** Specific, exact locations

##### Relative Paths
```bash
CLASSPATH=.:./bin: ./lib/*: ../shared/classes
```

**Means:** Relative to current directory
- `.` = current directory
- `./bin` = bin folder in current directory  
- `../shared` = go up one level, then into shared

##### Wildcards
```bash
# Include all JARs in lib directory
CLASSPATH=lib/*

# Does NOT work for class files
CLASSPATH=bin/*  # ❌ Wrong - doesn't include subdirectories
CLASSPATH=bin    # ✅ Correct
```

---
## Package Naming Conventions
1. **Use reverse domain name**
   ```
   com.google.projectname
   org.apache.commons
   io.github.username
   ```

2. **All lowercase**
   ```java
   package com.mycompany.myapp;  // Good
   package com.MyCompany.MyApp;  //Bad
   ```

3. **Use meaningful names**
   ```
   com.ecommerce.models
   com.ecommerce.services
   com.ecommerce. utils
   ```

4. **Avoid java and javax.***
   ```java
   package java.mypackage;  // Reserved for Java
   ```

---
# File Input and Output in Java
[[Java File IO]]

# Exceptions in Java
[[Exceptions in Java]]

# Some other things
## [[System Class in Java]]
## [The Math class](The%20Math%20class%20in%20Java)
## [Thread class](Threads%20in%20Java.md)

## [[Java GUI Classes and Their Basic Functions (JFrame)]]

## [JFrame](JFrame%20in%20Java)
## [Canvas](Canvas%20in%20Java)
## [Dimensions](Dimensions%20in%20Java)

cover what pack, add, setDefaultCloseOperation, saetLocation, saetLocationRelativeTo does
what JFrame.EXIT_ON_CLOSE, are


cover the oops
quicklty
