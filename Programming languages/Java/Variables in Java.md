**Expected to know:** [[Introduction to Programming]]
**Topics Covered:**
**Tags:** [[Variables and their scope]]

A **variable** in Java is a named location in memory used to store data that a program can manipulate. Variables allow programs to remember information as they run – just like how you use labeled jars to store ingredients in your kitchen.

## 1. Declaring Variables
In Java, _all variables must be declared before they are used_. The basic syntax is:
```java
type identifier;
```
- `type`: the data type (like `int`, `double`, `boolean`)
- `identifier`: the variable name

Examples:
```java
int age; 
double price; 
boolean isActive;
```
You can also assign a value while declaring:
```java
int count = 10; 
char initial = 'A';
```
And, to declare multiple variables of the same type at once:
```java
int x, y, z;
```

## 2. Java Data Types
Variables can store different kinds of data, and Java is _strongly typed_ – every variable must have a data type. Here are the **eight primitive types**:

| Type    | Purpose               | Example                |
| ------- | --------------------- | ---------------------- |
| byte    | Small integers        | byte b = 127;          |
| short   | Larger integers       | short s = 32000;       |
| int     | Most common integer   | int n = 100;           |
| long    | Huge integers         | long big = 123456789L; |
| float   | Decimal numbers       | float f = 10.5f;       |
| double  | More precise decimals | double d = 20.123;     |
| char    | Single character      | char ch = 'A';         |
| boolean | true/false values     | boolean isOk = true;   |
|         |                       |                        |

_Note: For decimals, use `f` after a float value, and `L` after a long value._

## 3. Using Variables
Variables are used in expressions, control statements, and for storing temporary results.

Example:
```java
int num; 
num = 100; 
System.out.println("This is num: " + num); 
// Outputs: This is num: 100 num = num * 2; 
System.out.println("The value of num * 2: " + num); 
// Outputs: The value of num * 2: 200
```
Notice how the value of `num` changes during execution.

## 4. Scope and Lifetime
- _Scope_ is where a variable is accessible.
- Variables declared inside a block `{ }` are only accessible within that block.
- Example:
    ```    java
    public static void main(String[] args) 
    {     
	    int outside = 1;    
	    if (true) 
	    {        
		    int inside = 2;        
		    // 'inside' and 'outside' accessible here    
		}    
	// 'inside' NOT accessible here 
	}
```

## 5. Dynamic Initialization
Java lets you _initialize variables dynamically_ (using values calculated at runtime):
```java
double a = 3.0, b = 4.0; 
double c = Math.sqrt(a*a + b*b); // Uses Pythagoras' theorem
```
## 6. Rules & Tips
- Variable names cannot start with a digit and must not use Java reserved keywords.
- Use meaningful names: `studentAge` is better than `x`.
- Java is _case-sensitive_: `value` and `Value` are different.

## 7. Why Declare Variables?
Declaring variables makes your code:
- Safer: prevents type mistakes.
- Easier to read.
- More efficient, as the compiler knows what to expect.

---
# final Variables
A **final variable** is a variable declared with the `final` keyword that can only be assigned a value **once**. Think of it as creating a constant - once you set the value, it's locked in place and cannot be changed throughout the program's execution.

```Java
final int MAX_NUM = 100;
```

# Types of Variables:
## Instance Variables (Field Variables)
Instance variables are declared **inside a class but outside any method**. They belong to an **object** (instance of a class).

### Characteristics:
- Declared inside the class, outside methods
- Each object gets its own copy
- Have default values (0 for numbers, null for objects, false for boolean)
- Exist as long as the object exists
- Can be accessed by all methods in the class
- Can use access modifiers (private, public, protected)

## Local Variables
Local variables are declared **inside a method, constructor, or block**. They only exist within that specific scope.

### Characteristics:
- Declared inside methods/constructors/blocks
- Only exist while the method is running
- Must be initialized before use (no default values!)
- Cannot use access modifiers
- Only accessible within the method/block where declared

## Visual Example
``` Java
public class Student {
    // INSTANCE VARIABLES (belong to the object)
    private String name;
    private int age;
    private double gpa;
    
    // Constructor
    public Student(String name, int age) {
        // LOCAL VARIABLES (parameter variables)
        // 'name' and 'age' here are local variables
        
        this.name = name;  // 'this.name' is instance variable
        this.age = age;    // 'this.age' is instance variable
        this.gpa = 0.0;    // instance variable initialized to 0.0 by default
    }
    
    // Method
    public void study(int hours) {
        // LOCAL VARIABLE
        int focusLevel = 10;  // Must be initialized!
        
        // LOCAL VARIABLE
        double improvement = hours * 0.1;
        
        // Accessing instance variable
        this.gpa = this.gpa + improvement;
        
        System.out.println(name + " studied for " + hours + " hours");
        // 'name' is instance variable (accessible here)
        // 'hours' is local variable (parameter)
        // 'focusLevel' is local variable
    }
    
    public void printInfo() {
        // We can access instance variables here
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("GPA: " + gpa);
        
        // ERROR! Cannot access 'hours' or 'focusLevel' here
        // They only exist inside the study() method
    }
}
```

## Complete Working Example
```Java
public class Car {
    // ========== INSTANCE VARIABLES ==========
    private String brand;
    private String color;
    private int speed;  // Default value is 0
    
    // Constructor
    public Car(String brand, String color) {
        // 'brand' and 'color' parameters are LOCAL VARIABLES
        this.brand = brand;  // Assign local to instance variable
        this.color = color;
    }
    
    // Method
    public void accelerate() {
        // LOCAL VARIABLE - exists only in this method
        int acceleration = 10;
        
        // Modifying INSTANCE VARIABLE
        speed = speed + acceleration;
        
        System.out.println(brand + " is now going " + speed + " mph");
    }
    
    public void brake() {
        // LOCAL VARIABLE - different from 'acceleration' above
        int deceleration = 5;
        
        speed = speed - deceleration;
        
        System.out.println(brand + " slowed down to " + speed + " mph");
    }
    
    // Main method to test
    public static void main(String[] args) {
        // LOCAL VARIABLE - exists only in main method
        Car myCar = new Car("Toyota", "Red");
        
        myCar.accelerate();  // Speed increases by 10
        myCar.accelerate();  // Speed increases by 10 again
        myCar.brake();       // Speed decreases by 5
        
        // Create another object - it has its own instance variables
        Car yourCar = new Car("Honda", "Blue");
        yourCar.accelerate();
        
        // Each Car object has its own 'speed', 'brand', 'color'
    }
}
```

**Output:**

```
Toyota is now going 10 mph
Toyota is now going 20 mph
Toyota slowed down to 15 mph
Honda is now going 10 mph
```

## Key Differences

| Feature              | Instance Variable             | Local Variable           |
| -------------------- | ----------------------------- | ------------------------ |
| **Location**         | Inside class, outside methods | Inside methods/blocks    |
| **Scope**            | Entire class                  | Only within method/block |
| **Lifetime**         | As long as object exists      | Only while method runs   |
| **Default Value**    | Yes (0, null, false)          | No - must initialize!    |
| **Access Modifiers** | Yes (private, public, etc.)   | No                       |
| **Memory**           | Heap                          | Stack                    |
| **Accessed by**      | All methods in class          | Only where declared      |
