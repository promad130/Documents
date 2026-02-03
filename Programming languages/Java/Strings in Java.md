**Topics Covered:**
**Tags:** [[Data Types and Constants]],  [[Data types in Java]]

The `String` class in Java is a fundamental and widely used class in the Java programming language. It represents a sequence of characters and is used to store and manipulate text.
It exists as an object instead of a derived or primitive data type in java, and is not a [[Wrapper classes]].

## Initialization
String class has various methods to create a string.
As java does not give pointer access, we cannot use strings here like in other languages. The strings here gets stored in either of two places:
- Heap
- String pool constant (String pool constant is a virtual space in **Java Virtual Machine(JVM) Memory**)

Ways to initialize:
```java
String s = "some string";
```
```java
String s = new String(<var>)
String s = new String("Some String");
```

The main difference between the two is the place where each string object is stored.
Instantiation using `" "` stores it in String Pool Constant, while with the new constructor, it is stored in heap

**Strings in java are immutable**, i.e., they cannot be changed. 
Any change in the string leads to the creation of a new one. 
Strings stored in String Pool Constant act as reference, i.e., no two strings in it are ever same, so for example I have two strings like:
```java
String a = "Hello";
String b = "Hello";
```

Then both of them will point to the same string on the memory, i.e, string pool constant.

## Some special methods:
### .intern()
- When you call `intern()` on a `String` object, it checks if an **equal string** already exists in the **String Constant Pool** (the pool of unique string literals maintained by the JVM).
- If it does, then it returns the reference to that pooled string
- If it doesn't, then it creates that string in the sting pool constant.
Hence it ensures that all the strings that are same share the same reference in the memory.
```java
String s1 = new String("Hello");
String s2 = new String("Hello");

System.out.println(s1 == s2);          // false (different references)

String s3 = s1.intern();
String s4 = s2.intern();

System.out.println(s3 == s4);          // true (both refer to pooled string "Hello")
```

Here is an explanation of the some common Java `String` class methods:
### 1. `lastIndexOf()`
- **Purpose:** Finds the _last_ occurrence of a specified character or substring within the string.
- **Signatures:**
    - `int lastIndexOf(int ch)`
    - `int lastIndexOf(int ch, int fromIndex)`
    - `int lastIndexOf(String str)`
    - `int lastIndexOf(String str, int fromIndex)`
- **Returns:** The index of the last occurrence of the specified character or substring. Returns `-1` if not found.
- **Example:**
    ```java
    String s = "banana"; 
    s.lastIndexOf('a');      // Returns 5 (last 'a' at index 5) 
    s.lastIndexOf("na");     // Returns 4 (last "na" sequence starts at 4)
    ```

---
### 2. `compareTo()`
- **Purpose:** Compares the calling string lexicographically to another string.
- **Signature:**
    `int compareTo(String anotherString)`
- **Returns:**
    - 0 if both strings are equal
    - Negative integer if calling string comes _before_ the argument string lexicographically
    - Positive integer if calling string comes _after_ the argument string lexicographically
    
- **Case Sensitivity:** Case-sensitive
- **Example:**
    ```java
    "apple".compareTo("banana");  
    // Negative number, "apple" < "banana" 
    "apple".compareTo("Apple");   
    // Positive number due to case difference 
    "apple".compareTo("apple");   
    // 0
    ```

---
### 3. `toLowerCase()`
- **Purpose:** Returns a new string with all uppercase characters converted to lowercase.
- **Signature:**  
    `String toLowerCase()`
- **Note:** Non-alphabetic characters are unaffected.
- **Example:**
```java
"Java Programming".toLowerCase();  // "java programming"
```

---
### 4. `toUpperCase()`
- **Purpose:** Returns a new string with all lowercase characters converted to uppercase.
- **Signature:**  
    `String toUpperCase()` 
- **Note:** Non-alphabetic characters are unaffected.
- **Example:**
```java
"Java Programming".toUpperCase();  // "JAVA PROGRAMMING"
```

---

## 5. `trim()`

- **Purpose:** Returns a new string by removing leading and trailing whitespace characters (spaces, tabs, etc.).
    
- **Signature:**  
    `String trim()`
    
- **Example:**
```java
String s = "   Hello World   "; s.trim();  // "Hello World"`
```

---

## 6. `replace()`

- **Purpose:** Returns a new string resulting from replacing all occurrences of a specified old character or CharSequence with a new character or CharSequence.
    
- **Signatures:**
    
    - `String replace(char oldChar, char newChar)`
        
    - `String replace(CharSequence target, CharSequence replacement)`
        
- **Example:**
    
    java
    
    `"hello".replace('l', 'p');  // "heppo" "Java".replace("Ja", "Ba"); // "Bava"`
    

---

## 7. `valueOf()`

- **Purpose:** Converts different types (primitive types, objects, arrays) into their string representation.
    
- **Signature:**  
    Static overloaded methods like:
    
    - `static String valueOf(int i)`
        
    - `static String valueOf(Object obj)`
        
    - `static String valueOf(char[] data)`
        
- **Usage:**  
    Gets the string representation of the input. Useful for converting numbers, objects, etc. into strings.
    
- **Example:**
    
    java
    
    `String s1 = String.valueOf(123);       // "123" String s2 = String.valueOf(true);      // "true" String s3 = String.valueOf(new int[] {1,2,3});  // A string representation of the array object's reference (not the elements)`
    


## Some Other Functions:
- **`length()`**  
    Returns the length (number of characters) of the string.  
    Example: `int len = str.length();`
    
- **`charAt(int index)`**  
    Returns the character at the specified index (0-based).  
    Example: `char ch = str.charAt(2);`
    
- **`equals(Object obj)`**  
    Compares two strings for exact equality (case-sensitive). Returns a boolean.
    Example: `str1.equals(str2)`
    
- **`equalsIgnoreCase(String str)`**  
    Compares two strings, ignoring case differences.  
    Example: `str1.equalsIgnoreCase(str2)`
    
- **`compareTo(String str)`**  
    Compares two strings lexicographically. Returns an int less than, equal to, or greater than zero.  
    Example: `int cmp = str1.compareTo(str2);`
    
- **`compareToIgnoreCase(String str)`**  
    Compares two strings lexicographically, ignoring case differences.
    
- **`substring(int beginIndex)` and `substring(int beginIndex, int endIndex)`**  
    Returns a substring starting at `beginIndex` or from `beginIndex` to `endIndex - 1`.  
    Example: `String s2 = str.substring(2, 5);`
    
- **`indexOf(int ch)` / `indexOf(String str)`**  
    Returns the index of the first occurrence of the character or substring, or -1 if not found.  
    Example: `int pos = str.indexOf('a');`
    
- **`lastIndexOf(int ch)` / `lastIndexOf(String str)`**  
    Returns the index of the last occurrence of the character or substring.
    
- **`startsWith(String prefix)` / `endsWith(String suffix)`**  
    Tests whether the string begins or ends with the specified prefix or suffix.
    
- **`toLowerCase()` / `toUpperCase()`**  
    Returns a string with all characters converted to lower or upper case.
    
- **`trim()`**  
    Removes leading and trailing whitespace from the string.
    
- **`replace(char oldChar, char newChar)`**  
    Returns a new string resulting from replacing all occurrences of `oldChar` with `newChar`.
    
- **`replaceAll(String regex, String replacement)`**  
    Replaces each substring matching the given regular expression(regex) with the replacement string.
    
- **`split(String regex)` and `split(String regex, int limit)`**  
    Splits the string around matches of the given regular expression and returns an array of substrings.
    
- **`valueOf(...)` (static methods)**  
    Converts various types (int, double, boolean, char[], Object, etc.) to a `String`.
    
- **`toString()`**  
    Returns the string representation of an object; usually overridden in user-defined classes.


# Buffer string:
The **`StringBuffer`** class in Java is a mutable sequence of characters, designed to efficiently handle modifications to strings.

## Key Points About `StringBuffer`:
- **Mutable Strings:**  
    Unlike the `String` class, which creates immutable string objects (cannot be changed once created), `StringBuffer` allows you to **modify the contents** of the character sequence without creating new objects.
    
- **Efficient Modifications:**
    When you need to perform many modifications (such as appending, inserting, deleting characters) to a string, using `StringBuffer` is much more efficient because it avoids creating multiple intermediate `String` objects like the `String` class does.
    
- **Thread-Safe:**  
    Methods of `StringBuffer` are **synchronized**, which means it is safe to use in multi-threaded environments without additional synchronization.
    
- **Common Methods:**
    - `append(...)` — adds data to the end
    - `insert(...)` — inserts data at a specified position
    - `delete(...)`, `deleteCharAt(...)` — remove characters
    - `replace(...)` — replace characters in a range
    - `reverse()` — reverse the sequence
    - `toString()` — converts the `StringBuffer` back to a `String`

## Example:
```java
StringBuffer sb = new StringBuffer("Hello"); 
sb.append(" World"); 
System.out.println(sb);  // Prints: Hello World 
sb.insert(5, ","); 
System.out.println(sb);  // Prints: Hello, World 
sb.replace(6, 11, "Java"); 
System.out.println(sb);  // Prints: Hello, Java 
sb.reverse(); 
System.out.println(sb);  // Prints: avaJ ,olleH
```
## When to Use `StringBuffer`?
- When you need to **modify strings frequently** in a **multi-threaded** environment.
- For single-threaded scenarios where you want mutable strings but **do not need synchronization**, `StringBuilder` (a similar but faster, non-synchronized class) is preferred.


how does string buffer works? WHere is the object stored, and does it has to be converted in a string? If it has a storage, then why does it needs to be converted to string class?

how does string buffer works? WHere is the object stored, and does it has to be converted in a string? If it has a storage, then why does it needs to be converted to string class?

## How does `StringBuffer` work?

- `StringBuffer` represents a **mutable sequence of characters** stored internally in a **resizable character array**.
    
- When you create a `StringBuffer` object, it allocates a portion of memory (usually on the **heap**) to hold the character array and metadata (like length and capacity).
    
- As you **append, insert, or delete** characters, the buffer dynamically modifies the internal character array without creating new objects each time, making it efficient for frequent modifications.
    
- If the internal capacity is exceeded, the `StringBuffer` automatically **resizes** (usually by allocating a larger array and copying existing content).
    

---

## Where is the `StringBuffer` object stored?

- Like most Java objects, a `StringBuffer` instance is stored on the **heap memory**.
    
- The reference variable pointing to the `StringBuffer` object itself is typically stored on the **stack**, if it’s a local variable, or in the heap if it is an instance or static field.
    

---

## Why does `StringBuffer` need to be converted to `String`?

- Although `StringBuffer` stores and manages characters efficiently, it is **not a `String`**, but a different type designed for **mutable operations**.
    
- Many APIs, libraries, and parts of the Java language itself expect **immutable `String` objects**.
    
- To provide a stable, immutable view suitable for these contexts, `StringBuffer` provides the `toString()` method, which creates and returns a **new immutable `String` object** representing the current character sequence.
    
- This conversion is necessary because:
    
    - `String` and `StringBuffer` serve **different purposes**: `String` is immutable, `StringBuffer` is mutable.
        
    - You cannot directly use a `StringBuffer` object wherever a `String` is required without converting it.
        
    - `toString()` ensures **thread-safe, unmodifiable usage** of the final string value.
        

---

## Understanding `substring(int start)` and `substring(int start, int end)` in Java
In the Java `String` class, the **`substring`** methods are used to extract a part of a string—called a substring—based on specified indices.

---

## 1. `String substring(int start)`

- **Purpose:** Returns a new string that is a substring of the original string, starting from the specified index `start` up to the end of the string.
    
- **Parameters:**
    
    - `start`: The index (0-based) where the substring begins, inclusive.
        
- **Returns:** A new string containing the characters from `start` to the end of the original string.
    
- **Example:**
    
    java
    
    `String s = "Programming"; String sub = s.substring(3);  // "gramming" (from index 3 to end)`
    
- **Behavior notes:**
    
    - Indexing starts at 0.
        
    - If `start` is equal to the string length, an empty string `""` is returned.
        
    - If `start` is out of bounds (`< 0` or `> length`), a `StringIndexOutOfBoundsException` is thrown.
        

---

## 2. `String substring(int start, int end)`

- **Purpose:** Returns a new string that is a substring of the original string, starting from index `start` up to (but not including) index `end`.
    
- **Parameters:**
    
    - `start`: The starting index (inclusive) of the substring.
        
    - `end`: The ending index (exclusive) of the substring.
        
- **Returns:** A new string containing characters from `start` to `end - 1`.
    
- **Example:**
    
    java
    
    `String s = "Programming"; String sub = s.substring(3, 6);  // "gra" (characters at index 3,4,5)`
    
- **Behavior notes:**
    
    - `start` must be >= 0, `end` must be <= string length.
        
    - `start` cannot be greater than `end`.
        
    - Violating above throws `StringIndexOutOfBoundsException`.
        
    - Creating an empty string is possible if `start == end`.
        

---
## How substring works internally
- `substring` creates a _new_ `String` object containing the selected characters.
- In Java versions prior to 7u6, `substring` internally shared the same character array as the original string with just different offsets (start and length). This sometimes caused memory leaks if the original string was large.
- Since Java 7u6 and later, `substring` **creates a new independent character array** with copied characters for the substring, avoiding those memory leaks.