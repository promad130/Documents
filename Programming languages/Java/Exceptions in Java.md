## Table of Contents
1. [Throwable Hierarchy](#throwable-hierarchy)
2. [Exception Class Hierarchy](#exception-class-hierarchy)
3. [Error vs Exception](#error-vs-exception)
4. [Checked vs Unchecked Exceptions](#checked-vs-unchecked-exceptions)
5. [Exception Handling: Try-Catch-Finally](#exception-handling-try-catch-finally)
6. [Multiple Catch Blocks](#multiple-catch-blocks)
7. [Try-With-Resources](#try-with-resources)
8. [Throw and Throws](#throw-and-throws)
9. [Creating Custom Exceptions](#creating-custom-exceptions)
10. [Exception Propagation](#exception-propagation)
11. [Best Practices](#best-practices)
12. [Complete Working Examples](#complete-working-examples)

---

## Throwable Hierarchy

### The Complete Hierarchy Tree

```
java.lang.Throwable (abstract class - root of all throwables)
│
├─ java.lang.Error (abstract class)
│  ├── StackOverflowError
│  ├── OutOfMemoryError
│  ├── VirtualMachineError
│  ├── AssertionError
│  ├── LinkageError
│  │   ├── NoClassDefFoundError
│  │   ├── ClassNotFoundException
│  │   └── UnsatisfiedLinkError
│  ├── ThreadDeath
│  ├── InternalError
│  └── UnknownError
│
└─ java.lang.Exception (checked exception)
   ├── IOException (checked)
   │   ├── FileNotFoundException
   │   ├── EOFException
   │   └── SocketException
   │
   ├── SQLException (checked)
   │   └── ... database-specific exceptions
   │
   ├── ReflectiveOperationException (checked)
   │   ├── ClassNotFoundException
   │   ├── IllegalAccessException
   │   └── InvocationTargetException
   │
   ├── InterruptedException (checked)
   │
   ├── RuntimeException (unchecked)
   │   ├── NullPointerException
   │   ├── ArrayIndexOutOfBoundsException
   │   ├── ClassCastException
   │   ├── IllegalArgumentException
   │   │   └── NumberFormatException
   │   ├── IllegalStateException
   │   ├── ArithmeticException
   │   ├── UnsupportedOperationException
   │   ├── ConcurrentModificationException
   │   └── NoSuchElementException
   │
   └── Other Checked Exceptions
       └── ...
```

### Understanding the Root: Throwable

```java
public abstract class Throwable implements Serializable {
    // Constructor
    public Throwable();
    public Throwable(String message);
    public Throwable(String message, Throwable cause);
    public Throwable(Throwable cause);
    
    // Key Methods
    public String getMessage();              // Error message
    public String toString();                // Full error description
    public void printStackTrace();            // Print stack trace
    public StackTraceElement[] getStackTrace(); // Get stack trace
    public Throwable getCause();             // Get underlying cause
    public Throwable initCause(Throwable cause); // Set the cause
    
    // Suppressed exceptions (Java 7+)
    public void addSuppressed(Throwable exception);
    public Throwable[] getSuppressed();
}
```

---
## Exception Class Hierarchy
### Checked Exceptions (extends Exception)

**Checked exceptions** MUST be caught or declared in method signature.

```java
Exception (abstract class - parent of all checked exceptions)
```

**Examples**:
- `IOException` - File or stream I/O operations fail
- `SQLException` - Database operation fails
- `InterruptedException` - Thread is interrupted
- `ReflectiveOperationException` - Reflection operation fails

**Why checked?** Compiler forces you to handle them - prevents crashes.

### Unchecked Exceptions (extends RuntimeException
**Unchecked exceptions** can be thrown without catch or throws declaration.

```java
RuntimeException (extends Exception - parent of all unchecked exceptions)
```

**Examples**:
- `NullPointerException` - Accessing null reference
- `ArrayIndexOutOfBoundsException` - Array index out of range
- `ClassCastException` - Invalid type cast
- `ArithmeticException` - Math error (division by zero)
- `NumberFormatException` - Invalid number parsing
- `IllegalArgumentException` - Invalid argument value
- `IllegalStateException` - Object in wrong state
- `UnsupportedOperationException` - Operation not supported

### Errors (extends Throwable)
**Errors** represent serious problems that applications should NOT catch.

**Examples**:
- `OutOfMemoryError` - JVM runs out of memory
- `StackOverflowError` - Stack exceeds capacity
- `NoClassDefFoundError` - Class definition not found at runtime
- `VirtualMachineError` - JVM error

---

## Error vs Exception
### Detailed Comparison

| Aspect | Error | Exception |
|--------|-------|-----------|
| **Parent Class** | Throwable | Throwable (via Exception) |
| **Meaning** | Serious problem, system level | Recoverable problem |
| **Should Catch** | Never | Yes (checked), optionally (unchecked) |
| **Cause** | System/JVM failure | Programming error or unexpected condition |
| **Examples** | OutOfMemoryError, StackOverflowError | IOException, NullPointerException |
| **Recovery** | Not possible | Can handle and recover |
| **When Occurs** | Runtime, unexpected | Can be tested/prevented |
| **Throws in Method** | Not typically | Yes |

### Visual Example
```java
public class ErrorVsException {
    public static void main(String[] args) {
        // Exception - we can handle
        try {
            int result = 10 / 0;  // ArithmeticException (unchecked)
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        
        // Error - should NOT catch (but shown for education)
        try {
            recursiveMethod();  // StackOverflowError
        } catch (StackOverflowError e) {
            System.out.println("Stack overflow!");
        }
    }
    
    static void recursiveMethod() {
        recursiveMethod();  // Infinite recursion → StackOverflowError
    }
}
```

---
## Checked vs Unchecked Exceptions
### Checked Exceptions
**Definition**: Must be caught or declared in method signature.

**Compile Error if Not Handled**:
```java
import java.io.*;

// WRONG - Compile error!
public void readFile() {
    FileReader reader = new FileReader("file.txt");  // ❌ IOException!
}

// CORRECT - Either catch or declare
public void readFile() throws IOException {
    FileReader reader = new FileReader("file.txt");  // ✅ Declared
}

// OR

public void readFile() {
    try {
        FileReader reader = new FileReader("file.txt");
    } catch (IOException e) {
        System.out.println("File error: " + e.getMessage());
    }
}
```

**Common Checked Exceptions**:
- `IOException` and subclasses
- `SQLException`
- `InterruptedException`
- `ClassNotFoundException`

### Unchecked Exceptions

**Definition**: Don't need to be caught or declared.

**No Compile Error**:
```java
// No error - unchecked exception
public void divide(int a, int b) {
    int result = a / b;  // ✅ No throws needed
}

// But runtime error possible
divide(10, 0);  // ArithmeticException at runtime!
```

**Why not require them?** Would make code too verbose. These should be prevented by logic.

**Common Unchecked Exceptions**:
- `NullPointerException` - String str = null; str.length();
- `ArrayIndexOutOfBoundsException` - int[] arr = {1}; arr[5];
- `ClassCastException` - Integer i = (Integer) "string";
- `ArithmeticException` - int x = 5 / 0;
- `NumberFormatException` - Integer.parseInt("abc");

### Decision Table

| Scenario | Exception Type | What to Do |
|----------|----------------|-----------|
| File operation fails | IOException (checked) | Must catch or declare |
| Null reference access | NullPointerException (unchecked) | Optional to catch, better to check null |
| Database query fails | SQLException (checked) | Must catch or declare |
| Invalid cast | ClassCastException (unchecked) | Optional to catch, better to use instanceof |
| Division by zero | ArithmeticException (unchecked) | Optional to catch, better to check divisor |
| Thread interrupted | InterruptedException (checked) | Must catch or declare |
| Invalid parameter | IllegalArgumentException (unchecked) | Optional, better to validate input |

---

## Exception Handling: Try-Catch-Finally

### Basic Structure

```java
try {
    // Code that might throw exception
    // If exception occurs, jump to catch block
    // If no exception, skip catch and go to finally
} catch (ExceptionType1 e) {
    // Handle ExceptionType1
    // Executed only if ExceptionType1 thrown
} catch (ExceptionType2 e) {
    // Handle ExceptionType2
    // Executed only if ExceptionType2 thrown
} finally {
    // Always executed - for cleanup
    // Runs whether exception thrown or not
    // Even if catch block returns/throws
}
```

### Try Block

```java
try {
    // Code that might throw exception
    
    String text = "Hello";
    int length = text.length();           // Safe - no exception
    
    int value = Integer.parseInt("123");  // Safe if valid format
    int result = 10 / 0;                  // Exception here!
    System.out.println(result);           // Never executes
    
    // Control jumps to catch block if exception occurs
}
```

### Catch Block

```java
catch (ArithmeticException e) {
    // Executes only if ArithmeticException thrown
    
    // Access exception information
    System.out.println(e.getMessage());        // "/ by zero"
    System.out.println(e.toString());          // "java.lang.ArithmeticException: / by zero"
    e.printStackTrace();                        // Prints full stack trace
    
    // Handle the error
    System.out.println("Cannot divide by zero!");
}
```

### Finally Block

```java
finally {
    // Executes regardless of exception
    
    System.out.println("Finally block always runs");
    // Perfect for cleanup:
    // - Close file/stream
    // - Release database connection
    // - Clean up resources
}
```

**When Does Finally Execute?**

| Scenario | Finally Executes? |
|----------|-------------------|
| No exception | ✅ Yes |
| Exception caught | ✅ Yes |
| Exception not caught | ✅ Yes (before propagating) |
| Catch block returns | ✅ Yes (before return) |
| Catch block throws new exception | ✅ Yes (before new exception propagates) |
| System.exit() called | ❌ No |

### Complete Example

```java
import java.io.*;

public class TryCatchFinallyDemo {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            // Risky operation
            reader = new FileReader("file.txt");
            System.out.println("File opened successfully");
            
            // Simulate reading
            int data = reader.read();
            
        } catch (FileNotFoundException e) {
            // Handle file not found
            System.out.println("File not found: " + e.getMessage());
            
        } catch (IOException e) {
            // Handle other I/O errors
            System.out.println("I/O error: " + e.getMessage());
            
        } finally {
            // Always cleanup
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("File closed successfully");
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }
}
```

**Output**:
```
File not found: file.txt (No such file or directory)
File closed successfully
```

---

## Multiple Catch Blocks

### Order Matters: Specific to General

```java
try {
    // Code that might throw exception
} catch (FileNotFoundException e) {
    // More specific - must come first
    System.out.println("File not found");
    
} catch (IOException e) {
    // More general - must come second
    // Catches all other IOException subclasses
    System.out.println("I/O error");
    
} catch (Exception e) {
    // Most general - must come last
    System.out.println("Unknown exception");
}
```

**WRONG ORDER - Compile Error**:
```java
try {
    // ...
} catch (Exception e) {           // ❌ Too general
    // ...
} catch (FileNotFoundException e) { // ❌ Never reached!
    // ...
}
```

### Multi-Catch (Java 7+)

Catch multiple exceptions in one block:

```java
try {
    // Code
    
} catch (IOException | SQLException e) {
    // Handles EITHER IOException OR SQLException
    System.out.println("I/O or Database error: " + e.getMessage());
}
```

**Requirements for Multi-Catch**:
- Exceptions must not be related (not parent/child)
- Variable `e` is of type that covers all caught exceptions
- Simplifies code when handling multiple unrelated exceptions

### Example with Multiple Exception Types

```java
import java.io.*;
import java.sql.*;

public class MultiCatchDemo {
    public static void main(String[] args) {
        try {
            // Multiple operations that can throw different exceptions
            readFile();          // Can throw IOException
            queryDatabase();     // Can throw SQLException
            parseNumber();       // Can throw NumberFormatException
            
        } catch (IOException e) {
            System.out.println("File I/O problem: " + e.getMessage());
            
        } catch (SQLException e) {
            System.out.println("Database problem: " + e.getMessage());
            
        } catch (NumberFormatException e) {
            System.out.println("Number format problem: " + e.getMessage());
            
        } catch (Exception e) {
            System.out.println("Unknown problem: " + e.getMessage());
        }
    }
    
    static void readFile() throws IOException {
        // File operations
    }
    
    static void queryDatabase() throws SQLException {
        // Database operations
    }
    
    static void parseNumber() throws NumberFormatException {
        Integer.parseInt("abc");
    }
}
```

---

## Try-With-Resources
### AutoCloseable Interface
Java 7+ provides automatic resource management:

```java
// Automatically closes resource that implements AutoCloseable
try (ResourceType resource = new ResourceType()) {
    // Use resource
} catch (Exception e) {
    // Handle exception
}
// Resource automatically closed here
```

### Benefits

1. **Automatic closing** - no need for finally
2. **Safe closure** - even if exception occurs
3. **Cleaner code** - no nested try-finally
4. **Exception suppression** - handles close() exceptions

### Example: File Operations

```java
import java.io.*;

public class TryWithResourcesDemo {
    public static void main(String[] args) {
        // OLD WAY - Manual closing
        FileReader reader = null;
        try {
            reader = new FileReader("file.txt");
            int data = reader.read();
            System.out.println("Data: " + data);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();  // Nested try-catch for close
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

```java
// NEW WAY - Try-with-resources
import java.io.*;

public class TryWithResourcesDemo {
    public static void main(String[] args) {
        try (FileReader reader = new FileReader("file.txt")) {
            int data = reader.read();
            System.out.println("Data: " + data);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Reader automatically closed!
    }
}
```

### Multiple Resources

```java
try (FileReader reader = new FileReader("input.txt");
     FileWriter writer = new FileWriter("output.txt")) {
    
    // Use both resources
    int data = reader.read();
    writer.write(data);
    
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
// Both automatically closed!
```

### Custom AutoCloseable Resource

```java
public class Connection implements AutoCloseable {
    private String name;
    
    public Connection(String name) {
        this.name = name;
        System.out.println("Opening connection: " + name);
    }
    
    public void execute(String query) {
        System.out.println("Executing: " + query);
    }
    
    @Override
    public void close() {
        System.out.println("Closing connection: " + name);
    }
}

// Usage
public class AutoCloseableDemo {
    public static void main(String[] args) {
        try (Connection conn = new Connection("Database")) {
            conn.execute("SELECT * FROM users");
        }
        // close() automatically called!
    }
}
```

**Output**:
```
Opening connection: Database
Executing: SELECT * FROM users
Closing connection: Database
```

---

## Throw and Throws
### The `throw` Keyword
**Manually throw an exception**
```java
throw new ExceptionType("Error message");
```

**Common Scenarios**:
```java
public void withdraw(double amount) {
    if (amount > balance) {
        throw new IllegalArgumentException("Insufficient funds!");
    }
    balance -= amount;
}
```

### The `throws` Keyword
**Declare that method might throw exception**:
```java
public void readFile() throws IOException {
    // Method can throw IOException
    FileReader reader = new FileReader("file.txt");
}
```

**Multiple exceptions**:
```java
public void complexOperation() throws IOException, SQLException, InterruptedException {
    // Method might throw any of these
}
```

### Difference Between throw and throws

| Aspect | throw | throws |
|--------|-------|--------|
| **Keyword Use** | Action - throw exception | Declaration - declares possibility |
| **Where Used** | Inside method body | Method signature |
| **Syntax** | `throw new Exception(message);` | `throws ExceptionType1, Type2` |
| **Effect** | Immediately throw exception | Method doesn't handle, caller must |
| **What After** | Code below doesn't execute | Method ends |
| **Example** | `throw new IOException("Error");` | `void read() throws IOException` |

### Complete Example

```java
public class ThrowAndThrowsDemo {
    // Method throws exception to caller
    public void validateAge(int age) throws IllegalArgumentException {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative!");
        }
        System.out.println("Age is valid: " + age);
    }
    
    // Method handles exception
    public void validateAgeHandled(int age) {
        try {
            validateAge(age);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        ThrowAndThrowsDemo demo = new ThrowAndThrowsDemo();
        
        // Caller handles exception
        try {
            demo.validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caller caught: " + e.getMessage());
        }
        
        // Method handles internally
        demo.validateAgeHandled(25);
    }
}
```

**Output**:
```
Caller caught: Age cannot be negative!
Age is valid: 25
```

---
## Creating Custom Exceptions
### Why Create Custom Exceptions?
1. **Specific error handling** - Catch specific problems
2. **Clear intent** - Code is self-documenting
3. **Application domain** - Reflect your business logic
4. **Easier debugging** - Know exactly what went wrong

### Creating Checked Custom Exception
```java
// Extends Exception (checked exception)
public class InsufficientFundsException extends Exception {
    
    // Constructor with message
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    // Constructor with message and cause
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Creating Unchecked Custom Exception

```java
// Extends RuntimeException (unchecked exception)
public class InvalidPlayerException extends RuntimeException {
    
    public InvalidPlayerException(String message) {
        super(message);
    }
    
    public InvalidPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Custom Exception with Additional Information

```java
public class ValidationException extends Exception {
    private String fieldName;
    private String invalidValue;
    
    public ValidationException(String message, String fieldName, String invalidValue) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getInvalidValue() {
        return invalidValue;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nField: " + fieldName + 
               "\nInvalid Value: " + invalidValue;
    }
}
```

### Using Custom Exceptions

```java
public class BankAccount {
    private double balance;
    
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    // Throw custom checked exception
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Cannot withdraw $" + amount + 
                ". Available balance: $" + balance
            );
        }
        balance -= amount;
    }
    
    // Throw custom unchecked exception
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                "Deposit amount must be positive!"
            );
        }
        balance += amount;
    }
    
    public double getBalance() {
        return balance;
    }
}
```

### Handling Custom Exceptions

```java
public class CustomExceptionDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        
        // Handling checked custom exception
        try {
            account.withdraw(1500);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
        
        // Handling unchecked custom exception
        try {
            account.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
        
        // Successful operations
        try {
            account.deposit(500);
            account.withdraw(300);
            System.out.println("Final balance: $" + account.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### Exception with Cause Chain

```java
public class DataAccessException extends Exception {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Usage - showing cause chain
try {
    // Database operation
    connectToDatabase();
} catch (SQLException e) {
    // Wrap in custom exception to preserve original cause
    throw new DataAccessException(
        "Failed to access database",
        e  // Original exception is the cause
    );
}
```

---

## Exception Propagation

### Call Stack and Exception Flow

```
main()
  └─ methodA()
      └─ methodB()
          └─ methodC() → throws exception
              ↑ (exception propagates up if not caught)
          ↑ methodB doesn't catch
      ↑ methodA doesn't catch
  ↑ main catches or prints stack trace
```

### Example: Exception Propagation

```java
public class ExceptionPropagation {
    
    static void methodC() throws ArithmeticException {
        System.out.println("In methodC");
        int result = 10 / 0;  // Throws ArithmeticException
    }
    
    static void methodB() throws ArithmeticException {
        System.out.println("In methodB");
        methodC();  // Doesn't catch, propagates up
    }
    
    static void methodA() throws ArithmeticException {
        System.out.println("In methodA");
        methodB();  // Doesn't catch, propagates up
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("In main");
            methodA();  // Exception caught here
        } catch (ArithmeticException e) {
            System.out.println("Caught in main: " + e.getMessage());
            System.out.println("Stack trace:");
            e.printStackTrace();
        }
    }
}
```

**Output**:
```
In main
In methodA
In methodB
In methodC
Caught in main: / by zero
Stack trace:
java.lang.ArithmeticException: / by zero
    at ExceptionPropagation.methodC(ExceptionPropagation.java:6)
    at ExceptionPropagation.methodB(ExceptionPropagation.java:11)
    at ExceptionPropagation.methodA(ExceptionPropagation.java:16)
    at ExceptionPropagation.main(ExceptionPropagation.java:22)
```

### Catch and Re-throw

```java
try {
    // Risky operation
    riskyMethod();
} catch (IOException e) {
    // Log the error
    System.err.println("Error occurred: " + e.getMessage());
    
    // Re-throw for caller to handle
    throw e;
}
```

### Wrap and Re-throw

```java
try {
    // Database operation
    queryDatabase();
} catch (SQLException e) {
    // Convert to unchecked exception
    throw new RuntimeException("Database query failed", e);
}
```

---

## Best Practices

### 1. Catch Specific Exceptions

**BAD**:
```java
try {
    // Some code
} catch (Exception e) {  // ❌ Too broad!
    System.out.println("Error: " + e.getMessage());
}
```

**GOOD**:
```java
try {
    // File operation
} catch (FileNotFoundException e) {
    System.out.println("File not found");
} catch (IOException e) {
    System.out.println("I/O error");
}
```

### 2. Don't Ignore Exceptions

**BAD**:
```java
try {
    // Risky operation
} catch (Exception e) {
    // ❌ Silent failure - no idea what went wrong!
}
```

**GOOD**:
```java
try {
    // Risky operation
} catch (Exception e) {
    // Log, handle, or re-throw
    logger.error("Operation failed", e);
    // or
    throw new ApplicationException("Failed to process", e);
}
```

### 3. Close Resources Properly

**BAD**:
```java
FileReader reader = new FileReader("file.txt");
int data = reader.read();
reader.close();  // ❌ Not called if exception occurs
```

**GOOD**:
```java
try (FileReader reader = new FileReader("file.txt")) {
    int data = reader.read();
}  // ✅ Automatically closed
```

### 4. Provide Meaningful Messages

**BAD**:
```java
throw new IllegalArgumentException("Error");  // ❌ Not helpful
```

**GOOD**:
```java
throw new IllegalArgumentException(
    "User age must be between 0 and 150, got: " + age
);
```

### 5. Don't Catch and Ignore

**BAD**:
```java
try {
    conn.close();
} catch (Exception e) {
    // ❌ Silently ignore close errors
}
```

**GOOD**:
```java
try {
    conn.close();
} catch (IOException e) {
    logger.warn("Failed to close connection", e);
}
```

### 6. Use Try-With-Resources

**BAD**:
```java
FileReader reader = null;
try {
    reader = new FileReader("file.txt");
    // Use reader
} finally {
    if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**GOOD**:
```java
try (FileReader reader = new FileReader("file.txt")) {
    // Use reader
}  // Automatically cleaned up
```

### 7. Don't Return null in Exception Handlers

**BAD**:
```java
try {
    return loadData();
} catch (Exception e) {
    return null;  // ❌ Confuses callers
}
```

**GOOD**:
```java
try {
    return loadData();
} catch (Exception e) {
    throw new DataLoadException("Failed to load data", e);
}
```

### 8. Log with Full Context

**BAD**:
```java
catch (Exception e) {
    System.out.println(e.getMessage());
}
```

**GOOD**:
```java
catch (Exception e) {
    logger.error("Failed to process user: " + userId, e);
    e.printStackTrace();
}
```

---

## Complete Working Examples

### Example 1: Bank Account with Custom Exceptions

```java
// Custom exception
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}

// Bank Account class
public class BankAccount {
    private double balance;
    private String accountNumber;
    
    public BankAccount(String accountNumber, double initialBalance) 
            throws IllegalArgumentException {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        balance += amount;
        System.out.println("Deposited: $" + amount);
    }
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Cannot withdraw $" + amount + ". Balance: $" + balance
            );
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount);
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}

// Main with comprehensive error handling
public class BankingDemo {
    public static void main(String[] args) {
        BankAccount account = null;
        
        try {
            // Create account
            account = new BankAccount("ACC123", 5000);
            System.out.println("Account created: " + account.getAccountNumber());
            
            // Deposit
            account.deposit(1000);
            System.out.println("Balance: $" + account.getBalance());
            
            // Withdraw valid amount
            account.withdraw(2000);
            System.out.println("Balance: $" + account.getBalance());
            
            // Try to withdraw invalid amount
            account.withdraw(-500);  // Invalid
            
        } catch (InvalidAmountException e) {
            System.err.println("Invalid amount error: " + e.getMessage());
            
        } catch (InsufficientFundsException e) {
            System.err.println("Insufficient funds: " + e.getMessage());
            
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument: " + e.getMessage());
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            if (account != null) {
                System.out.println("Final balance: $" + account.getBalance());
            }
        }
    }
}
```

**Output**:
```
Account created: ACC123
Deposited: $1000
Balance: $6000
Withdrawn: $2000
Balance: $4000
Invalid amount error: Withdrawal amount must be positive
Final balance: $4000
```

### Example 2: File Processing with Exception Handling

```java
import java.io.*;

public class FileProcessor {
    
    public static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        
        try (FileReader reader = new FileReader(filename);
             BufferedReader buffer = new BufferedReader(reader)) {
            
            String line;
            while ((line = buffer.readLine()) != null) {
                content.append(line).append("\n");
            }
            
        }  // Resources automatically closed
        
        return content.toString();
    }
    
    public static void writeFile(String filename, String content) 
            throws IOException {
        
        try (FileWriter writer = new FileWriter(filename);
             BufferedWriter buffer = new BufferedWriter(writer)) {
            
            buffer.write(content);
            buffer.flush();
            System.out.println("File written: " + filename);
            
        }  // Resources automatically closed
    }
    
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";
        
        try {
            // Read file
            System.out.println("Reading file...");
            String content = readFile(inputFile);
            System.out.println("Content length: " + content.length());
            
            // Process content
            String processed = content.toUpperCase();
            
            // Write file
            System.out.println("Writing file...");
            writeFile(outputFile, processed);
            
            System.out.println("Process complete!");
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
```

### Example 3: Exception Hierarchy Demonstration

```java
public class ExceptionHierarchyDemo {
    
    static void demonstrateException(int type) throws Exception {
        switch (type) {
            case 1:
                // Checked exception
                throw new IOException("File operation failed");
                
            case 2:
                // Unchecked exception
                throw new NullPointerException("Null reference");
                
            case 3:
                // Custom exception
                throw new ValidationException(
                    "Invalid input",
                    "username",
                    "admin@123"
                );
                
            case 4:
                // Arithmetic exception
                int result = 10 / 0;
                
            default:
                System.out.println("No exception");
        }
    }
    
    public static void main(String[] args) {
        // Test each exception type
        for (int i = 1; i <= 4; i++) {
            try {
                System.out.println("\nTest " + i + ":");
                demonstrateException(i);
                
            } catch (IOException e) {
                System.out.println("Caught IOException: " + e.getMessage());
                
            } catch (NullPointerException e) {
                System.out.println("Caught NullPointerException: " + e.getMessage());
                
            } catch (ValidationException e) {
                System.out.println("Caught ValidationException: " + e.getMessage());
                
            } catch (ArithmeticException e) {
                System.out.println("Caught ArithmeticException: " + e.getMessage());
                
            } catch (Exception e) {
                System.out.println("Caught Exception: " + e.getMessage());
            }
        }
    }
}
```

### Example 4: Custom Exception with Validation

```java
public class AgeValidationException extends Exception {
    private int invalidAge;
    
    public AgeValidationException(String message, int invalidAge) {
        super(message);
        this.invalidAge = invalidAge;
    }
    
    public int getInvalidAge() {
        return invalidAge;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               " (Invalid age: " + invalidAge + ")";
    }
}

public class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) throws AgeValidationException {
        this.name = name;
        setAge(age);
    }
    
    public void setAge(int age) throws AgeValidationException {
        if (age < 0 || age > 150) {
            throw new AgeValidationException(
                "Age must be between 0 and 150",
                age
            );
        }
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person{" + name + ", age=" + age + "}";
    }
}

public class PersonDemo {
    public static void main(String[] args) {
        String[] names = {"John", "Jane", "Bob"};
        int[] ages = {25, 200, 35};  // 200 is invalid
        
        for (int i = 0; i < names.length; i++) {
            try {
                Person person = new Person(names[i], ages[i]);
                System.out.println("Created: " + person);
                
            } catch (AgeValidationException e) {
                System.out.println("Validation error: " + e);
                System.out.println("Invalid age was: " + e.getInvalidAge());
            }
        }
    }
}
```

**Output**:
```
Created: Person{John, age=25}
Validation error: AgeValidationException: Age must be between 0 and 150 (Invalid age: 200)
Invalid age was: 200
Created: Person{Bob, age=35}
```

---

## Exception Handling Flowchart

```
try {
    code()
} 
    ↓
    Exception thrown?
    ├─ NO → skip catch, go to finally
    │       ↓
    │       finally block
    │       ↓
    │       (method ends/returns)
    │
    └─ YES → jump to catch
            ↓
            Exception matches catch type?
            ├─ NO → check next catch
            │       ├─ Matches? Run catch, then finally
            │       └─ No match? Propagate to caller
            │
            └─ YES → run catch
                    ↓
                    finally block
                    ↓
                    (catch finishes or throws new exception)
```

---

## Summary Table

| Concept | Purpose | Example |
|---------|---------|---------|
| **Throwable** | Root of all exceptions/errors | java.lang.Throwable |
| **Exception** | Recoverable errors | IOException, SQLException |
| **Error** | Serious JVM errors | OutOfMemoryError, StackOverflowError |
| **RuntimeException** | Unchecked exceptions | NullPointerException, ArithmeticException |
| **try** | Code that might fail | try { risky code } |
| **catch** | Handle specific exception | catch (IOException e) { } |
| **finally** | Always execute | finally { cleanup } |
| **throw** | Manually throw exception | throw new Exception("msg") |
| **throws** | Declare exception method might throw | void method() throws IOException |
| **Custom Exception** | Domain-specific exception | class MyException extends Exception |
| **try-with-resources** | Auto-close resources | try (Resource r = new Resource()) { } |

This comprehensive guide covers all aspects of exception handling in Java!
