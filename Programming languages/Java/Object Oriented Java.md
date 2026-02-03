**Expected to know:** [[Object Oriented Programming]]
**Topics Covered:**
**Tags:** 

Java is a **pure object-oriented programming language**, meaning everything in Java is encapsulated inside classes and objects. Concepts like encapsulation, abstraction, inheritance, and polymorphism that you studied in the attachment are fully enforced in Java, making it a great language to apply OOP principles.

---
## Classes and Objects in Java
- A **class** in Java is a blueprint or template with [[Attributes(or Data)]] and [[Methods(or Behavior)]] from which objects ([Instances](Instances,%20Constructor%20and%20Object%20Initializer)) are created.
- An **object** is an instance of a class, possessing attributes (fields) and behaviors (methods).

## Defining a Class
```java
public class Dog 
{     
	// Attributes (fields)     
	String breed;     
	int age;     
	String color;     
	
	// Methods (behaviors)     
	void bark() 
	{         
		System.out.println("Woof!");     
	}     
	
	void eat() 
	{         
		System.out.println("Dog is eating");     
	} 
	
}
```

## ![[Constructors in Java]]

## Creating Objects (Instances)
```java
public class Main 
{     
	public static void main(String[] args) 
	{         
		Dog myDog = new Dog();  // Instantiating object         
		myDog.breed = "Golden Retriever";         
		myDog.age = 3;         
		myDog.color = "Golden";         
		myDog.bark();  // Calling a method on object     
	} 
}
```
- Objects are accessed via **reference variables**.

Now in java, as discussed before:
![[Introduction to Java#3. Structure of a Simple Java Program]]
![[Syntax of Java#2. Entry Point]]

we have:
```java
public class filename
{
	public static void main(String[] args)
	{
	
	}
}
```
as a starting point.
The main function in java doesn't return any values, hence the void return type, it is a [[Static]] and [[Public]].
Every java file can have only one main function, and that main function has to be either in package protected (i.e., default) or public access modifier.
```java
class A 
{
	
}
```
(This is called a Default Access Modifier, also called Package Protected Modifier)

It takes in the arguments that are given with the run command in the terminal in the array called `args`.

Access modifiers in Java control the **visibility and accessibility** of classes, methods, and variables. They help **encapsulate** data and restrict how other parts of the program interact with those elements. As a beginner, understanding access modifiers is essential for writing safe, maintainable code.

# Access Modifiers
![[Access Modifier]]

## Access Modifiers on Classes in Java
### 1. Public Class
- Declared with the keyword `public`.
- A **public class** is visible to **all other classes everywhere**, regardless of the package they belong to.
- It can be accessed from any other class in any package.
- **Rule:** If a class is declared `public`, **the source file name must exactly match the class name plus `.java` extension**.

Example:
```java
public class MyClass 
{     
	// class code here... 
}
```
If this class is `public`, the file must be named `MyClass.java`.

## 2. Default (Package-Private) Class
- If **no access modifier** is given, the class has **default** or **package-private** access.
- Such a class is visible **only within the same package**.
- It **cannot be accessed from classes in other packages**.
- This is useful for hiding helper or internal classes that should not be part of the public API.

Example:
```java
class Helper 
{     
	// only accessible within the same package 
}
```
If you put this in a file, the file name does **not** have to match the class name.

---
## Rules for Access Modifiers on Classes

| Modifier  | Where Class is Accessible    | Filename Requirement                   |
| --------- | ---------------------------- | -------------------------------------- |
| `public`  | Anywhere (all packages)      | Must match class name (ClassName.java) |
| _default_ | Only within the same package | No restriction on filename             |

---

## Access Modifiers on Nested Classes
In Java, you can have **inner/nested classes** inside another class with additional modifiers:
- `public` — accessible anywhere the outer class is accessible.
- `private` — accessible **only inside the outer class**.
- `protected` — accessible within the same package and to subclasses.
- default (no modifier) — accessible within the same package only.

For example:
```java
public class Outer 
{     
	private class InnerPrivate 
	{         
		// only accessible inside Outer class     
	}          
	
	public class InnerPublic 
	{         
		// accessible anywhere Outer is accessible     
	} 
}
```

---
## Why Are These Rules Important?
- They **enforce encapsulation** — a fundamental OOP principle. You hide the inner workings of a class by restricting access.
- When you make something `public`, you open it to the whole program.
- Default/package-private access is a way to protect your code inside your package but still allow sharing internally.
- The requirement that the file name matches the public class name makes it easy to locate classes and helps the compiler and JVM keep things organized.


It is required to have a class in a java file whose name is same as that of the file. This is because:
1. **Compiler and JVM Identification**
	- When the Java compiler compiles a source file, it creates bytecode files (`.class` files) named after the class defined in that file.
	- The Java Virtual Machine (JVM) expects to find a `.class` file that matches the class name given on the command line.
	- Keeping the file name and the public class name the same ensures that the compiler and JVM can directly locate and load the correct `.class` file easily without ambiguity.
	
2. **Consistency and Organization**
	- This convention enforces a **one-to-one relationship** between public classes and their files, improving organization and maintainability.
	- Especially in large projects, it is easier to find the source code for a particular public class by simply looking for a file with the same name.
	- It avoids confusion that could arise if multiple public classes were allowed per file or files could have unrelated names.    
	
3. **Language Specification and Standard**
	- The Java Language Specification (JLS) mandates this rule as part of the language design and compilation model.
	- This formalizes the structure of Java programs and maintains consistency across tools, IDEs, and the compiler.


# Encapsulation in java 

**Encapsulation** is the mechanism of wrapping data (variables) and code (methods) together as a single unit. It restricts direct access to some of an object's components, which is a means of preventing accidental interference and misuse of the data. 

## Key Concepts
1. **Data Hiding**: Private fields cannot be accessed directly from outside the class
2. **Access Control**: Use access modifiers (private, protected, public)
3. **Getters and Setters**: Public methods to access and modify private fields
4. **Validation**: Control how data is accessed and modified

## Example 1: Basic Encapsulation

```java name=BankAccount.java
public class BankAccount {
    // Private fields - cannot be accessed directly from outside
    private String accountNumber;
    private String accountHolder;
    private double balance;
    
    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }
    
    // Getter methods - provide read access
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public double getBalance() {
        return balance;
    }
    
    // Setter with validation - controlled write access
    public void setAccountHolder(String accountHolder) {
        if (accountHolder != null && ! accountHolder.trim().isEmpty()) {
            this.accountHolder = accountHolder;
        } else {
            System.out.println("Invalid account holder name!");
        }
    }
    
    // Business methods with validation
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System. out.println("Invalid deposit amount!");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System. out.println("Invalid withdrawal amount or insufficient funds!");
        }
    }
    
    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
    }
}
```

```java name=BankAccountDemo.java
public class BankAccountDemo {
    public static void main(String[] args) {
        // Create a bank account
        BankAccount account = new BankAccount("123456", "John Doe", 1000.0);
        
        // Cannot access private fields directly - This would cause error: 
        // account.balance = 5000; // ERROR: balance has private access
        
        // Must use public methods
        account.displayAccountInfo();
        
        System.out.println("\n--- Performing transactions ---");
        account.deposit(500);
        account.withdraw(200);
        
        System.out.println("\n--- Updated Account Info ---");
        account.displayAccountInfo();
        
        // Try invalid operations
        System.out.println("\n--- Invalid operations ---");
        account.deposit(-100);  // Invalid
        account.withdraw(2000); // Insufficient funds
    }
}
```

## Example 2: Person with Validation

```java name=Person.java
public class Person {
    private String name;
    private int age;
    private String email;
    
    // Constructor
    public Person(String name, int age, String email) {
        setName(name);
        setAge(age);
        setEmail(email);
    }
    
    // Getter for name
    public String getName() {
        return name;
    }
    
    // Setter with validation for name
    public void setName(String name) {
        if (name != null && name.length() >= 2) {
            this.name = name;
        } else {
            System.out.println("Name must be at least 2 characters long!");
        }
    }
    
    // Getter for age
    public int getAge() {
        return age;
    }
    
    // Setter with validation for age
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System. out.println("Age must be between 0 and 150!");
        }
    }
    
    // Getter for email
    public String getEmail() {
        return email;
    }
    
    // Setter with validation for email
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("Invalid email format!");
        }
    }
    
    // Display person details
    public void displayInfo() {
        System.out. println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
    }
}
```

```java name=PersonDemo.java
public class PersonDemo {
    public static void main(String[] args) {
        Person person = new Person("Alice", 25, "alice@example.com");
        
        System.out.println("--- Initial Info ---");
        person.displayInfo();
        
        System.out.println("\n--- Updating Info ---");
        person.setAge(30);
        person.setEmail("alice. new@example.com");
        person.displayInfo();
        
        System.out.println("\n--- Invalid Updates ---");
        person.setAge(200);      // Invalid age
        person.setEmail("invalid"); // Invalid email
        person.setName("A");     // Name too short
    }
}
```

## Example 3: Read-Only and Write-Only Properties

```java name=Employee.java
public class Employee {
    private final String employeeId; // Read-only after construction
    private String name;
    private double salary;
    private String password; // Write-only (no getter)
    
    public Employee(String employeeId, String name, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
    }
    
    // Read-only property - only getter, no setter
    public String getEmployeeId() {
        return employeeId;
    }
    
    // Regular property with getter and setter
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Protected property - getter available but returns masked value
    public String getSalaryInfo() {
        return "Salary Range: " + (salary < 50000 ? "Junior" : "Senior");
    }
    
    // Controlled access to salary
    public void updateSalary(double newSalary, String adminPassword) {
        if (adminPassword.equals(this.password)) {
            this. salary = newSalary;
            System.out.println("Salary updated successfully!");
        } else {
            System.out.println("Unauthorized access!");
        }
    }
    
    // Write-only property - only setter, no getter
    public void setPassword(String password) {
        if (password != null && password.length() >= 8) {
            this.password = password;
            System.out.println("Password set successfully!");
        } else {
            System.out.println("Password must be at least 8 characters!");
        }
    }
}
```

## Benefits of Encapsulation

1. **Data Protection**: Prevents unauthorized access and modification
2. **Flexibility**: Internal implementation can change without affecting external code
3. **Validation**: Control over how data is set and retrieved
4. **Maintainability**:  Easier to maintain and modify code
5. **Reusability**: Well-encapsulated classes are easier to reuse
6. **Testing**: Easier to test individual components

## Best Practices

1. Make fields `private` by default
2. Provide `public` getters and setters only when necessary
3. Validate data in setters
4. Use meaningful method names (getX, setX, isX for boolean)
5. Make fields `final` if they shouldn't change after initialization
6. Don't expose internal collections directly (return copies if needed)

## Common Mistakes to Avoid
```java
// BAD - Public fields
public class BadExample {
    public String name; // Anyone can modify directly
    public int age;     // No validation possible
}

// GOOD - Private fields with controlled access
public class GoodExample {
    private String name;
    private int age;
    
    public String getName() { return name; }
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }
    
    public int getAge() { return age; }
    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }
}
```

# Abstraction in java
**Abstraction** is the process of hiding implementation details and showing only the essential features of an object. It focuses on **what** an object does rather than **how** it does it.

## Key Concepts

1. **Abstract Classes**: Classes that cannot be instantiated and may contain abstract methods
2. **Abstract Methods**: Methods declared without implementation
3. **Interfaces**: Pure abstraction with only method signatures (before Java 8)
4. **Hiding Complexity**: Show only necessary details to the user

## Types of Abstraction in Java
1. **Abstract Classes** (0-100% abstraction)
2. **Interfaces** (100% abstraction)

---
## Part 1: Abstract Classes
### Basic Abstract Class
```java name=Shape.java
// Abstract class - cannot be instantiated
public abstract class Shape {
    protected String color;
    
    // Constructor
    public Shape(String color) {
        this.color = color;
    }
    
    // Abstract method - no implementation
    public abstract double calculateArea();
    
    // Abstract method
    public abstract double calculatePerimeter();
    
    // Concrete method - has implementation
    public void displayColor() {
        System.out. println("Color: " + color);
    }
    
    // Concrete method
    public String getColor() {
        return color;
    }
}
```

```java name=Circle.java
public class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    // Must implement abstract methods
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    public void displayInfo() {
        System.out. println("Circle:");
        displayColor();
        System.out. println("Radius: " + radius);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
}
```

```java name=Rectangle.java
public class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
    
    public void displayInfo() {
        System.out.println("Rectangle:");
        displayColor();
        System.out. println("Length: " + length + ", Width: " + width);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
}
```

```java name=Triangle.java
public class Triangle extends Shape {
    private double side1, side2, side3;
    
    public Triangle(String color, double side1, double side2, double side3) {
        super(color);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    
    @Override
    public double calculateArea() {
        // Using Heron's formula
        double s = calculatePerimeter() / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
    
    @Override
    public double calculatePerimeter() {
        return side1 + side2 + side3;
    }
    
    public void displayInfo() {
        System.out.println("Triangle:");
        displayColor();
        System.out.println("Sides: " + side1 + ", " + side2 + ", " + side3);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
}
```

```java name=ShapeDemo.java
public class ShapeDemo {
    public static void main(String[] args) {
        /*
        Cannot instantiate abstract class
        Shape shape = new Shape("Red"); // ERROR!
        But what we can do is:
        Shape myShape = new Circle("Red, 5.0);
		myShape = new Rectangle("Blue", 4.0, 6.0);
		The myShape reference variable is reference to abstract class, 
		Hence, can be used like a normal class but cannot refer to itself, as abstract classes cannot be instantiated.
		*/
        
        // Create concrete objects
        Shape circle = new Circle("Red", 5.0);
        Rectangle rectangle = new Rectangle("Blue", 4.0, 6.0);
        Triangle triangle = new Triangle("Green", 3.0, 4.0, 5.0);
        
        circle.displayInfo();
        System.out.println();
        
        rectangle.displayInfo();
        System.out.println();
        
        triangle.displayInfo();
        System.out.println();
        
        // Polymorphism with abstract class
        System.out.println("--- Using Polymorphism ---");
        Shape[] shapes = {circle, rectangle, triangle};
        
        for (Shape shape : shapes) {
            System.out.println("Area: " + shape.calculateArea());
            System.out.println("Perimeter: " + shape. calculatePerimeter());
            shape.displayColor();
            System.out.println();
        }
    }
}
```

## Part 2: Interfaces
### Example: Interface for Payment System
```java name=Payment.java
// Interface - 100% abstraction (before Java 8)
public interface Payment {
    // Abstract methods (public and abstract by default)
    void processPayment(double amount);
    boolean validatePayment();
    String getPaymentStatus();
}
```

```java name=CreditCardPayment.java
public class CreditCardPayment implements Payment {
    private String cardNumber;
    private String cardHolder;
    private double balance;
    
    public CreditCardPayment(String cardNumber, String cardHolder, double balance) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.balance = balance;
    }
    
    @Override
    public void processPayment(double amount) {
        if (validatePayment() && balance >= amount) {
            balance -= amount;
            System. out.println("Credit Card Payment of $" + amount + " processed successfully");
            System.out.println("Remaining balance: $" + balance);
        } else {
            System.out.println("Payment failed:  Insufficient balance");
        }
    }
    
    @Override
    public boolean validatePayment() {
        return cardNumber != null && cardNumber.length() == 16;
    }
    
    @Override
    public String getPaymentStatus() {
        return "Credit Card - " + cardHolder;
    }
}
```

```java name=PayPalPayment.java
public class PayPalPayment implements Payment {
    private String email;
    private String password;
    private double accountBalance;
    
    public PayPalPayment(String email, String password, double accountBalance) {
        this.email = email;
        this.password = password;
        this.accountBalance = accountBalance;
    }
    
    @Override
    public void processPayment(double amount) {
        if (validatePayment() && accountBalance >= amount) {
            accountBalance -= amount;
            System.out. println("PayPal Payment of $" + amount + " processed successfully");
            System.out.println("Remaining balance: $" + accountBalance);
        } else {
            System.out.println("Payment failed: Invalid credentials or insufficient balance");
        }
    }
    
    @Override
    public boolean validatePayment() {
        return email != null && email.contains("@") && password != null;
    }
    
    @Override
    public String getPaymentStatus() {
        return "PayPal - " + email;
    }
}
```

```java name=CryptoPayment.java
public class CryptoPayment implements Payment {
    private String walletAddress;
    private double cryptoBalance;
    
    public CryptoPayment(String walletAddress, double cryptoBalance) {
        this.walletAddress = walletAddress;
        this.cryptoBalance = cryptoBalance;
    }
    
    @Override
    public void processPayment(double amount) {
        if (validatePayment() && cryptoBalance >= amount) {
            cryptoBalance -= amount;
            System.out.println("Cryptocurrency Payment of $" + amount + " processed successfully");
            System.out.println("Remaining balance: " + cryptoBalance + " BTC");
        } else {
            System.out.println("Payment failed: Invalid wallet or insufficient crypto");
        }
    }
    
    @Override
    public boolean validatePayment() {
        return walletAddress != null && walletAddress.length() > 20;
    }
    
    @Override
    public String getPaymentStatus() {
        return "Cryptocurrency - Wallet: " + walletAddress. substring(0, 10) + "...";
    }
}
```

```java name=PaymentDemo.java
public class PaymentDemo {
    public static void main(String[] args) {
        // Different payment implementations
        Payment creditCard = new CreditCardPayment("1234567890123456", "John Doe", 5000);
        Payment paypal = new PayPalPayment("john@example.com", "password123", 3000);
        Payment crypto = new CryptoPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 2. 5);
        
        // Process payments
        System.out.println("=== Credit Card Payment ===");
        System.out.println(creditCard. getPaymentStatus());
        creditCard.processPayment(1000);
        System.out.println();
        
        System.out.println("=== PayPal Payment ===");
        System.out.println(paypal.getPaymentStatus());
        paypal.processPayment(500);
        System.out.println();
        
        System.out.println("=== Cryptocurrency Payment ===");
        System.out.println(crypto.getPaymentStatus());
        crypto.processPayment(0.5);
        System.out.println();
        
        // Polymorphism with interface
        System.out.println("=== Processing Multiple Payments ===");
        Payment[] payments = {creditCard, paypal, crypto};
        
        for (Payment payment : payments) {
            System.out.println(payment.getPaymentStatus());
            if (payment. validatePayment()) {
                System.out.println("✓ Payment method validated");
            }
            System.out.println();
        }
    }
}
```

### Example 4: Multiple Interface Implementation

```java name=Flyable.java
public interface Flyable {
    void fly();
    void land();
}
```

```java name=Swimmable.java
public interface Swimmable {
    void swim();
}
```

```java name=Animal.java
public abstract class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public abstract void makeSound();
    
    public void display() {
        System.out.println("Animal:  " + name);
    }
}
```

```java name=Duck.java
// Duck can both fly and swim
public class Duck extends Animal implements Flyable, Swimmable {
    
    public Duck(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Quack quack!");
    }
    
    @Override
    public void fly() {
        System.out. println(name + " is flying in the sky");
    }
    
    @Override
    public void land() {
        System.out.println(name + " is landing on water");
    }
    
    @Override
    public void swim() {
        System.out.println(name + " is swimming in the pond");
    }
}
```

```java name=Fish.java
// Fish can only swim
public class Fish extends Animal implements Swimmable {
    
    public Fish(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " makes bubbles");
    }
    
    @Override
    public void swim() {
        System.out. println(name + " is swimming underwater");
    }
}
```

```java name=Airplane.java
// Airplane can fly but is not an animal
public class Airplane implements Flyable {
    private String model;
    
    public Airplane(String model) {
        this.model = model;
    }
    
    @Override
    public void fly() {
        System.out.println(model + " airplane is flying at 30,000 feet");
    }
    
    @Override
    public void land() {
        System.out.println(model + " airplane is landing on runway");
    }
}
```

```java name=AnimalDemo.java
public class AnimalDemo {
    public static void main(String[] args) {
        Duck duck = new Duck("Donald");
        Fish fish = new Fish("Nemo");
        Airplane airplane = new Airplane("Boeing 747");
        
        System.out.println("=== Duck ===");
        duck.display();
        duck.makeSound();
        duck.fly();
        duck.swim();
        duck.land();
        System.out.println();
        
        System.out.println("=== Fish ===");
        fish.display();
        fish.makeSound();
        fish.swim();
        System.out.println();
        
        System.out.println("=== Airplane ===");
        airplane.fly();
        airplane.land();
        System.out.println();
        
        // Polymorphism with interfaces
        System.out. println("=== All Flying Objects ===");
        Flyable[] flyingObjects = {duck, airplane};
        for (Flyable obj : flyingObjects) {
            obj.fly();
        }
        System.out.println();
        
        System.out.println("=== All Swimming Objects ===");
        Swimmable[] swimmingObjects = {duck, fish};
        for (Swimmable obj :  swimmingObjects) {
            obj.swim();
        }
    }
}
```

### Example 5: Interface with Default and Static Methods (Java 8+)

```java name=Vehicle.java
public interface Vehicle {
    // Abstract methods
    void start();
    void stop();
    
    // Default method - provides default implementation
    default void honk() {
        System.out. println("Beep beep!");
    }
    
    // Static method
    static void displayVehicleInfo() {
        System.out.println("This is a vehicle interface");
    }
    
    // Default method
    default void displayType() {
        System.out. println("Type: Generic Vehicle");
    }
}
```

```java name=Car.java
public class Car implements Vehicle {
    private String brand;
    
    public Car(String brand) {
        this.brand = brand;
    }
    
    @Override
    public void start() {
        System.out.println(brand + " car is starting with key");
    }
    
    @Override
    public void stop() {
        System.out.println(brand + " car is stopping");
    }
    
    // Can override default method
    @Override
    public void honk() {
        System.out.println(brand + " car:  HONK HONK!");
    }
    
    @Override
    public void displayType() {
        System.out.println("Type: Car - " + brand);
    }
}
```

```java name=Motorcycle.java
public class Motorcycle implements Vehicle {
    private String brand;
    
    public Motorcycle(String brand) {
        this.brand = brand;
    }
    
    @Override
    public void start() {
        System.out.println(brand + " motorcycle is starting with kick");
    }
    
    @Override
    public void stop() {
        System.out.println(brand + " motorcycle is stopping");
    }
    
    // Uses default honk() method from interface
    // Override displayType only
    @Override
    public void displayType() {
        System.out.println("Type: Motorcycle - " + brand);
    }
}
```

```java name=VehicleDemo.java
public class VehicleDemo {
    public static void main(String[] args) {
        Car car = new Car("Toyota");
        Motorcycle motorcycle = new Motorcycle("Harley Davidson");
        
        // Static method call
        Vehicle.displayVehicleInfo();
        System.out.println();
        
        System.out.println("=== Car ===");
        car.displayType();
        car.start();
        car.honk(); // Overridden method
        car.stop();
        System.out.println();
        
        System.out. println("=== Motorcycle ===");
        motorcycle.displayType();
        motorcycle.start();
        motorcycle.honk(); // Default method from interface
        motorcycle.stop();
    }
}
```

---

## Abstract Class vs Interface

| Feature              | Abstract Class                              | Interface                                |
| -------------------- | ------------------------------------------- | ---------------------------------------- |
| **Methods**          | Can have both abstract and concrete methods | All methods are abstract (before Java 8) |
| **Variables**        | Can have any type of variables              | Only public static final variables       |
| **Inheritance**      | Single inheritance (extends)                | Multiple inheritance (implements)        |
| **Constructor**      | Can have constructors                       | Cannot have constructors                 |
| **Access Modifiers** | Can have any access modifier                | Methods are public by default            |
| **When to Use**      | When classes share common implementation    | When defining a contract/capability      |

## Benefits of Abstraction
1. **Reduces Complexity**: Hides implementation details
2. **Code Reusability**: Common code in base classes/interfaces
3. **Flexibility**:  Easy to add new implementations
4. **Maintainability**: Changes in implementation don't affect users
5. **Polymorphism**: Treat different objects uniformly
6. **Security**: Hide sensitive implementation details


# Inheritance in Java
***(Pre-requisite: [[Inheritance]])***
**Inheritance** is a mechanism where a new class (child/subclass) derives properties and behaviors from an existing class (parent/superclass). It enables code reusability and establishes a relationship between classes.

## Key Concepts
1. **Superclass/Parent Class**:  The class being inherited from
2. **Subclass/Child Class**: The class that inherits
3. **`extends` keyword**: Used to inherit from a class
4. **`super` keyword**: Used to refer to parent class
5. **Method Overriding**: Redefining parent class methods
6. **IS-A Relationship**: Child IS-A type of Parent

---
## Part 1: Single Inheritance
### Example 1: Basic Inheritance
```java name=Animal.java
// Parent/Super class
public class Animal {
    protected String name;
    protected int age;
    
    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Animal constructor called");
    }
    
    // Methods
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}
```

```java name=Dog.java
// Child/Sub class
public class Dog extends Animal {
    private String breed;
    
    // Constructor
    public Dog(String name, int age, String breed) {
        super(name, age); // Call parent constructor
        this. breed = breed;
        System. out.println("Dog constructor called");
    }
    
    // Dog's own method
    public void bark() {
        System.out.println(name + " is barking:  Woof!  Woof!");
    }
    
    // Overriding parent method
    @Override
    public void eat() {
        System.out. println(name + " the dog is eating dog food");
    }
    
    // Extending parent method
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent method
        System.out.println("Breed: " + breed);
    }
}
```

```java name=Cat.java
public class Cat extends Animal {
    private String furColor;
    
    public Cat(String name, int age, String furColor) {
        super(name, age);
        this.furColor = furColor;
        System.out.println("Cat constructor called");
    }
    
    // Cat's own method
    public void meow() {
        System.out.println(name + " is meowing: Meow! Meow!");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " the cat is eating fish");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Fur Color: " + furColor);
    }
}
```

```java name=InheritanceDemo.java
public class InheritanceDemo {
    public static void main(String[] args) {
        System.out.println("=== Creating Dog ===");
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        dog.displayInfo();
        dog.eat();
        dog.sleep();
        dog.bark();
        System.out.println();
        
        System.out.println("=== Creating Cat ===");
        Cat cat = new Cat("Whiskers", 2, "Orange");
        cat.displayInfo();
        cat.eat();
        cat.sleep();
        cat.meow();
        System.out.println();
        
        // Polymorphism - parent reference to child object
        System.out. println("=== Using Polymorphism ===");
        Animal animal1 = new Dog("Max", 5, "Labrador");
        Animal animal2 = new Cat("Fluffy", 1, "White");
        
        animal1.eat(); // Calls Dog's eat()
        animal2.eat(); // Calls Cat's eat()
        
        // animal1.bark(); // ERROR:  Animal doesn't have bark()
        // Need to cast to access Dog-specific methods
        if (animal1 instanceof Dog) {
            ((Dog) animal1).bark();
        }
    }
}
```

---
## Part 2: Multilevel Inheritance
### Example 2: Vehicle Hierarchy
```java name=Vehicle.java
// Level 1 - Base class
public class Vehicle {
    protected String brand;
    protected int year;
    protected double price;
    
    public Vehicle(String brand, int year, double price) {
        this.brand = brand;
        this.year = year;
        this.price = price;
    }
    
    public void start() {
        System.out.println("Vehicle is starting");
    }
    
    public void stop() {
        System.out.println("Vehicle is stopping");
    }
    
    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
    }
}
```

```java name=Car.java
// Level 2 - Inherits from Vehicle
public class Car extends Vehicle {
    protected int numberOfDoors;
    protected String fuelType;
    
    public Car(String brand, int year, double price, int numberOfDoors, String fuelType) {
        super(brand, year, price);
        this.numberOfDoors = numberOfDoors;
        this. fuelType = fuelType;
    }
    
    @Override
    public void start() {
        System.out.println("Car is starting with ignition key");
    }
    
    public void openTrunk() {
        System.out.println("Car trunk is opening");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out. println("Number of Doors: " + numberOfDoors);
        System.out.println("Fuel Type: " + fuelType);
    }
}
```

```java name=ElectricCar.java
// Level 3 - Inherits from Car
public class ElectricCar extends Car {
    private int batteryCapacity;
    private int range;
    
    public ElectricCar(String brand, int year, double price, int numberOfDoors, 
                       int batteryCapacity, int range) {
        super(brand, year, price, numberOfDoors, "Electric");
        this.batteryCapacity = batteryCapacity;
        this.range = range;
    }
    
    @Override
    public void start() {
        System.out. println("Electric car is starting silently with push button");
    }
    
    public void chargeBattery() {
        System.out.println("Charging battery to " + batteryCapacity + " kWh");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Battery Capacity: " + batteryCapacity + " kWh");
        System.out.println("Range: " + range + " km");
    }
}
```

```java name=MultilevelDemo.java
public class MultilevelDemo {
    public static void main(String[] args) {
        System.out.println("=== Vehicle ===");
        Vehicle vehicle = new Vehicle("Generic", 2020, 15000);
        vehicle.displayInfo();
        vehicle.start();
        System.out.println();
        
        System.out.println("=== Car ===");
        Car car = new Car("Toyota", 2022, 25000, 4, "Petrol");
        car.displayInfo();
        car.start();
        car.openTrunk();
        System.out.println();
        
        System.out.println("=== Electric Car ===");
        ElectricCar tesla = new ElectricCar("Tesla", 2023, 50000, 4, 100, 500);
        tesla.displayInfo();
        tesla.start();
        tesla.openTrunk(); // Inherited from Car
        tesla.chargeBattery(); // ElectricCar's own method
        System.out.println();
        
        // All levels of inheritance
        System.out.println("=== Inheritance Chain ===");
        System.out.println("ElectricCar IS-A Car:  " + (tesla instanceof Car));
        System.out.println("ElectricCar IS-A Vehicle: " + (tesla instanceof Vehicle));
        System.out.println("Car IS-A Vehicle: " + (car instanceof Vehicle));
    }
}
```

---
## Part 3: Hierarchical Inheritance
### Example 3: Employee Management System
```java name=Employee.java
// Parent class
public class Employee {
    protected int employeeId;
    protected String name;
    protected double baseSalary;
    
    public Employee(int employeeId, String name, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.baseSalary = baseSalary;
    }
    
    public double calculateSalary() {
        return baseSalary;
    }
    
    public void displayInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out. println("Name: " + name);
        System.out.println("Base Salary: $" + baseSalary);
    }
    
    public void work() {
        System.out. println(name + " is working");
    }
}
```

```java name=Manager.java
// Child class 1
public class Manager extends Employee {
    private int teamSize;
    private double bonus;
    
    public Manager(int employeeId, String name, double baseSalary, int teamSize, double bonus) {
        super(employeeId, name, baseSalary);
        this.teamSize = teamSize;
        this.bonus = bonus;
    }
    
    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: Manager");
        System.out.println("Team Size: " + teamSize);
        System.out.println("Bonus: $" + bonus);
        System.out.println("Total Salary: $" + calculateSalary());
    }
    
    @Override
    public void work() {
        System.out.println(name + " is managing a team of " + teamSize);
    }
    
    public void conductMeeting() {
        System.out.println(name + " is conducting a team meeting");
    }
}
```

```java name=Developer.java
// Child class 2
public class Developer extends Employee {
    private String programmingLanguage;
    private int projectsCompleted;
    
    public Developer(int employeeId, String name, double baseSalary, 
                     String programmingLanguage, int projectsCompleted) {
        super(employeeId, name, baseSalary);
        this.programmingLanguage = programmingLanguage;
        this.projectsCompleted = projectsCompleted;
    }
    
    @Override
    public double calculateSalary() {
        // Bonus based on projects completed
        double projectBonus = projectsCompleted * 500;
        return baseSalary + projectBonus;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: Developer");
        System.out.println("Programming Language: " + programmingLanguage);
        System.out.println("Projects Completed: " + projectsCompleted);
        System.out.println("Total Salary: $" + calculateSalary());
    }
    
    @Override
    public void work() {
        System.out.println(name + " is coding in " + programmingLanguage);
    }
    
    public void writeCode() {
        System.out.println(name + " is writing " + programmingLanguage + " code");
    }
}
```

```java name=Designer.java
// Child class 3
public class Designer extends Employee {
    private String designTool;
    private int designsCompleted;
    
    public Designer(int employeeId, String name, double baseSalary, 
                    String designTool, int designsCompleted) {
        super(employeeId, name, baseSalary);
        this.designTool = designTool;
        this.designsCompleted = designsCompleted;
    }
    
    @Override
    public double calculateSalary() {
        double designBonus = designsCompleted * 300;
        return baseSalary + designBonus;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: Designer");
        System.out.println("Design Tool: " + designTool);
        System.out. println("Designs Completed: " + designsCompleted);
        System.out.println("Total Salary: $" + calculateSalary());
    }
    
    @Override
    public void work() {
        System.out. println(name + " is creating designs using " + designTool);
    }
    
    public void createDesign() {
        System.out.println(name + " is creating a new design in " + designTool);
    }
}
```

```java name=HierarchicalDemo.java
public class HierarchicalDemo {
    public static void main(String[] args) {
        Manager manager = new Manager(101, "Alice Smith", 80000, 10, 15000);
        Developer developer = new Developer(102, "Bob Johnson", 70000, "Java", 8);
        Designer designer = new Designer(103, "Carol White", 65000, "Figma", 12);
        
        System.out.println("=== Manager ===");
        manager.displayInfo();
        manager.work();
        manager.conductMeeting();
        System.out.println();
        
        System.out.println("=== Developer ===");
        developer.displayInfo();
        developer.work();
        developer.writeCode();
        System.out. println();
        
        System. out.println("=== Designer ===");
        designer.displayInfo();
        designer.work();
        designer.createDesign();
        System.out.println();
        
        // Using polymorphism
        System.out.println("=== Employee Array ===");
        Employee[] employees = {manager, developer, designer};
        
        double totalSalary = 0;
        for (Employee emp : employees) {
            System.out.println(emp.name + " - Salary: $" + emp.calculateSalary());
            totalSalary += emp.calculateSalary();
            emp.work();
            System.out.println();
        }
        
        System.out. println("Total Company Salary Expense: $" + totalSalary);
    }
}
```

---
## Part 4: The `super` Keyword

### Example 4: Understanding `super`

```java name=Parent.java
public class Parent {
    protected String name = "Parent";
    
    public Parent() {
        System.out. println("Parent no-arg constructor");
    }
    
    public Parent(String name) {
        this.name = name;
        System.out. println("Parent parameterized constructor:  " + name);
    }
    
    public void display() {
        System.out. println("Parent display method");
    }
    
    public void show() {
        System.out.println("Parent show:  " + name);
    }
}
```

```java name=Child.java
public class Child extends Parent {
    private String name = "Child";
    
    public Child() {
        super(); // Call parent no-arg constructor (optional if no parameters)
        System.out.println("Child no-arg constructor");
    }
    
    public Child(String parentName, String childName) {
        super(parentName); // Call parent parameterized constructor
        this.name = childName;
        System.out. println("Child parameterized constructor:  " + childName);
    }
    
    @Override
    public void display() {
        super.display(); // Call parent's display method
        System.out. println("Child display method");
    }
    
    public void showNames() {
        System.out. println("Child name: " + this.name); // Child's name
        System.out.println("Parent name: " + super.name); // Parent's name
    }
    
    @Override
    public void show() {
        super.show(); // Call parent's show
        System.out.println("Child show: " + this.name);
    }
}
```

```java name=SuperDemo.java
public class SuperDemo {
    public static void main(String[] args) {
        System.out.println("=== Creating Child with no-arg constructor ===");
        Child child1 = new Child();
        child1.display();
        child1.showNames();
        System.out. println();
        
        System. out.println("=== Creating Child with parameterized constructor ===");
        Child child2 = new Child("Parent Name", "Child Name");
        child2.show();
        child2.showNames();
    }
}
```

---

## Part 5: Method Overriding

### Example 5: Method Overriding Rules

```java name=BankAccount.java
public class BankAccount {
    protected double balance;
    
    public BankAccount(double balance) {
        this.balance = balance;
    }
    
    // Method to be overridden
    public double calculateInterest() {
        return balance * 0.03; // 3% interest
    }
    
    // Final method - cannot be overridden
    public final void displayBalance() {
        System.out.println("Balance: $" + balance);
    }
    
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System. out.println("Withdrawn: $" + amount);
        }
    }
}
```

```java name=SavingsAccount.java
public class SavingsAccount extends BankAccount {
    
    public SavingsAccount(double balance) {
        super(balance);
    }
    
    // Overriding with different implementation
    @Override
    public double calculateInterest() {
        return balance * 0.05; // 5% interest for savings
    }
    
    // Overriding with additional functionality
    @Override
    public void withdraw(double amount) {
        if (balance - amount >= 500) { // Minimum balance requirement
            super.withdraw(amount);
        } else {
            System.out. println("Cannot withdraw:  Minimum balance $500 required");
        }
    }
    
    // Cannot override final method
    // public void displayBalance() { } // ERROR! 
}
```

```java name=OverridingDemo.java
public class OverridingDemo {
    public static void main(String[] args) {
        BankAccount regular = new BankAccount(10000);
        SavingsAccount savings = new SavingsAccount(10000);
        
        System. out.println("=== Regular Account ===");
        regular.displayBalance();
        System.out.println("Interest: $" + regular.calculateInterest());
        regular.withdraw(9600);
        regular.displayBalance();
        System.out.println();
        
        System.out.println("=== Savings Account ===");
        savings.displayBalance();
        System.out.println("Interest: $" + savings.calculateInterest());
        savings.withdraw(9600); // Will fail due to minimum balance
        savings.withdraw(5000); // Will succeed
        savings.displayBalance();
    }
}
```

---

## Part 6: Protected Access Modifier

### Example 6: Access Modifiers in Inheritance

```java name=Person.java
public class Person {
    private String ssn; // Private - not accessible in child
    protected String name; // Protected - accessible in child
    public int age; // Public - accessible everywhere
    String address; // Default - accessible in same package
    
    public Person(String ssn, String name, int age, String address) {
        this.ssn = ssn;
        this.name = name;
        this.age = age;
        this.address = address;
    }
    
    private void displaySSN() {
        System.out.println("SSN: " + ssn);
    }
    
    protected void displayName() {
        System.out.println("Name: " + name);
    }
    
    public void displayAge() {
        System.out.println("Age: " + age);
    }
    
    public void displayAllInfo() {
        displaySSN(); // Can access private method within same class
        displayName();
        displayAge();
        System.out.println("Address: " + address);
    }
}
```

```java name=Student.java
public class Student extends Person {
    private int studentId;
    
    public Student(String ssn, String name, int age, String address, int studentId) {
        super(ssn, name, age, address);
        this.studentId = studentId;
    }
    
    public void displayStudentInfo() {
        System.out.println("Student ID:  " + studentId);
        
        // Can access protected and public members
        System.out.println("Name: " + name); // Protected - OK
        System.out.println("Age: " + age); // Public - OK
        System.out.println("Address: " + address); // Default - OK (same package)
        
        // Cannot access private members
        // System. out.println("SSN: " + ssn); // ERROR!
        
        displayName(); // Protected method - OK
        displayAge(); // Public method - OK
        // displaySSN(); // Private method - ERROR!
    }
}
```

```java name=AccessDemo.java
public class AccessDemo {
    public static void main(String[] args) {
        Student student = new Student("123-45-6789", "John Doe", 20, "123 Main St", 1001);
        
        System.out.println("=== From main method ===");
        // student.ssn; // ERROR:  private
        // student.name; // ERROR: protected (different class, not subclass)
        System.out.println("Age: " + student.age); // OK:  public
        
        System.out.println("\n=== Student displayStudentInfo ===");
        student.displayStudentInfo();
        
        System.out.println("\n=== Person displayAllInfo ===");
        student.displayAllInfo();
    }
}
```

---

## Part 7: Constructor Chaining

### Example 7: Constructor Chaining in Inheritance

```java name=Grandparent.java
public class Grandparent {
    protected String family;
    
    public Grandparent() {
        this("Unknown Family");
        System.out.println("Grandparent no-arg constructor");
    }
    
    public Grandparent(String family) {
        this.family = family;
        System.out.println("Grandparent constructor: " + family);
    }
}
```

```java name=Parent2.java
public class Parent2 extends Grandparent {
    protected String lastName;
    
    public Parent2() {
        this("Unknown");
        System.out.println("Parent no-arg constructor");
    }
    
    public Parent2(String lastName) {
        super(lastName + " Family");
        this.lastName = lastName;
        System.out.println("Parent constructor: " + lastName);
    }
}
```

```java name=Child2.java
public class Child2 extends Parent2 {
    private String firstName;
    
    public Child2() {
        this("Unknown", "Unknown");
        System.out.println("Child no-arg constructor");
    }
    
    public Child2(String firstName, String lastName) {
        super(lastName);
        this.firstName = firstName;
        System.out.println("Child constructor: " + firstName + " " + lastName);
    }
    
    public void displayFullInfo() {
        System.out. println("\nFull Name: " + firstName + " " + lastName);
        System.out.println("Family: " + family);
    }
}
```

```java name=ConstructorChainDemo.java
public class ConstructorChainDemo {
    public static void main(String[] args) {
        System.out.println("=== Creating Child with parameters ===");
        Child2 child1 = new Child2("John", "Smith");
        child1.displayFullInfo();
        
        System.out.println("\n=== Creating Child with no args ===");
        Child2 child2 = new Child2();
        child2.displayFullInfo();
    }
}
```

---

## Part 8: Real-World Example - Shape Calculator

```java name=Shape2D.java
public class Shape2D {
    protected String color;
    protected boolean filled;
    
    public Shape2D() {
        this("white", false);
    }
    
    public Shape2D(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public boolean isFilled() {
        return filled;
    }
    
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    public double getArea() {
        return 0. 0;
    }
    
    public double getPerimeter() {
        return 0.0;
    }
    
    @Override
    public String toString() {
        return "Shape[color=" + color + ", filled=" + filled + "]";
    }
}
```

```java name=Circle2. java
public class Circle2 extends Shape2D {
    private double radius;
    
    public Circle2() {
        this(1.0);
    }
    
    public Circle2(double radius) {
        this(radius, "white", false);
    }
    
    public Circle2(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double getArea() {
        return Math. PI * radius * radius;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public String toString() {
        return "Circle[" + super.toString() + ", radius=" + radius + "]";
    }
}
```

```java name=Rectangle2.java
public class Rectangle2 extends Shape2D {
    private double length;
    private double width;
    
    public Rectangle2() {
        this(1.0, 1.0);
    }
    
    public Rectangle2(double length, double width) {
        this(length, width, "white", false);
    }
    
    public Rectangle2(double length, double width, String color, boolean filled) {
        super(color, filled);
        this.length = length;
        this. width = width;
    }
    
    public double getLength() {
        return length;
    }
    
    public void setLength(double length) {
        this.length = length;
    }
    
    public double getWidth() {
        return width;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    @Override
    public double getArea() {
        return length * width;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * (length + width);
    }
    
    @Override
    public String toString() {
        return "Rectangle[" + super. toString() + ", length=" + length + ", width=" + width + "]";
    }
}
```

```java name=Square. java
public class Square extends Rectangle2 {
    
    public Square() {
        super();
    }
    
    public Square(double side) {
        super(side, side);
    }
    
    public Square(double side, String color, boolean filled) {
        super(side, side, color, filled);
    }
    
    public double getSide() {
        return getLength();
    }
    
    public void setSide(double side) {
        setLength(side);
        setWidth(side);
    }
    
    @Override
    public void setLength(double length) {
        super.setLength(length);
        super.setWidth(length);
    }
    
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        super.setLength(width);
    }
    
    @Override
    public String toString() {
        return "Square[" + super.toString().replace("Rectangle", "Shape") + "]";
    }
}
```

```java name=ShapeDemo.java
public class ShapeDemo {
    public static void main(String[] args) {
        // Create shapes
        Circle2 circle = new Circle2(5.0, "red", true);
        Rectangle2 rectangle = new Rectangle2(4.0, 6.0, "blue", false);
        Square square = new Square(5.0, "green", true);
        
        // Display information
        System.out.println("=== Circle ===");
        System.out. println(circle);
        System.out.println("Area: " + circle.getArea());
        System.out.println("Perimeter: " + circle.getPerimeter());
        System.out.println();
        
        System.out. println("=== Rectangle ===");
        System.out.println(rectangle);
        System.out. println("Area: " + rectangle. getArea());
        System.out.println("Perimeter: " + rectangle.getPerimeter());
        System.out.println();
        
        System.out.println("=== Square ===");
        System.out.println(square);
        System.out.println("Area: " + square.getArea());
        System.out. println("Perimeter: " + square.getPerimeter());
        System.out.println();
        
        // Polymorphism
        System.out.println("=== Shape Array (Polymorphism) ===");
        Shape2D[] shapes = {circle, rectangle, square};
        
        double totalArea = 0;
        for (Shape2D shape : shapes) {
            System.out.println(shape. getClass().getSimpleName() + 
                             " - Area: " + shape.getArea() + 
                             ", Perimeter: " + shape. getPerimeter());
            totalArea += shape.getArea();
        }
        System.out.println("\nTotal Area: " + totalArea);
        
        // Demonstrate Square maintains square property
        System.out.println("\n=== Testing Square Property ===");
        square.setLength(10);
        System.out.println("After setLength(10): " + square);
        System.out.println("Width is also:  " + square.getWidth());
    }
}
```

---

## Key Rules of Inheritance

### DO's

1. **Use inheritance for IS-A relationships**
2. **Override methods with @Override annotation**
3. **Call super() in constructor if needed**
4. **Use protected for members that subclasses should access**
5. **Make methods final if they shouldn't be overridden**
6. **Use polymorphism with parent references**

### DON'Ts

1. **Don't use inheritance just for code reuse** (use composition instead)
2. **Don't override and change the behavior drastically**
3. **Don't make everything protected** (breaks encapsulation)
4. **Don't forget to call super() for parameterized constructors**
5. **Don't use inheritance for HAS-A relationships**

---

## Inheritance vs Composition

```java name=InheritanceVsComposition.java
// ❌ BAD - Inheritance used incorrectly
class Employee extends ArrayList<String> {
    // Employee IS NOT a list of strings! 
}

// ✅ GOOD - Composition
class Employee {
    private List<String> skills; // Employee HAS skills
    
    public Employee() {
        skills = new ArrayList<>();
    }
}

// ✅ GOOD - Proper inheritance
class Manager extends Employee {
    // Manager IS AN Employee
}
```

---
## Important Notes
1. **Java doesn't support multiple inheritance** (a class can't extend multiple classes)
2. **A class can only extend ONE class** but implement MULTIPLE interfaces
3. **All classes inherit from Object class** (implicit)
4. **Private members are NOT inherited** (not accessible in subclass)
5. **Constructors are NOT inherited** (but can be called with super())
6. **Static methods are NOT overridden** (they are hidden)

# Upcasting and Downcasting

## What is Casting?

**Casting** is the process of converting a reference of one type to another type in an inheritance hierarchy. 

## Upcasting
**Upcasting** is casting a subclass object to a superclass reference. It's implicit and always safe.

### Key Points:
- Automatic (implicit)
- Always safe
- No ClassCastException
- Can only access parent class members

## Downcasting
**Downcasting** is casting a superclass reference back to a subclass reference. It's explicit and can be risky.

### Key Points:
- Must be explicit
- Can throw ClassCastException
- Use `instanceof` to check before casting
- Gives access to subclass-specific members

---
## Example 1: Basic Upcasting and Downcasting

```java name=Animal.java
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out. println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}
```

```java name=Dog.java
public class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }
    
    public void bark() {
        System.out. println(name + " is barking:  Woof!  Woof!");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " the dog is eating dog food");
    }
    
    public String getBreed() {
        return breed;
    }
}
```

```java name=Cat.java
public class Cat extends Animal {
    private boolean indoor;
    
    public Cat(String name, boolean indoor) {
        super(name);
        this.indoor = indoor;
    }
    
    public void meow() {
        System.out. println(name + " is meowing: Meow! Meow!");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " the cat is eating fish");
    }
    
    public boolean isIndoor() {
        return indoor;
    }
}
```

```java name=CastingDemo.java
public class CastingDemo {
    public static void main(String[] args) {
        // ============ UPCASTING ============
        System.out.println("=== UPCASTING (Implicit) ===");
        
        Dog dog = new Dog("Buddy", "Golden Retriever");
        
        // Upcasting - Child to Parent (Implicit)
        Animal animal1 = dog; // Automatic upcasting
        Animal animal2 = new Cat("Whiskers", true); // Direct upcasting
        
        // Can call parent class methods
        animal1.eat(); // Calls Dog's overridden eat()
        animal1.sleep();
        
        // Cannot call child-specific methods
        // animal1.bark(); // ERROR! Animal doesn't have bark()
        // animal1.getBreed(); // ERROR!
        
        System.out. println();
        
        // ============ DOWNCASTING ============
        System.out.println("=== DOWNCASTING (Explicit) ===");
        
        // We know animal1 is actually a Dog
        if (animal1 instanceof Dog) {
            Dog retrievedDog = (Dog) animal1; // Explicit downcasting
            retrievedDog.bark(); // Now we can call Dog methods
            System.out.println("Breed: " + retrievedDog.getBreed());
        }
        
        System.out.println();
        
        // ============ UNSAFE DOWNCASTING ============
        System.out.println("=== UNSAFE DOWNCASTING ===");
        
        Animal animal3 = new Cat("Fluffy", false);
        
        // This will compile but throw ClassCastException at runtime
        // Dog wrongCast = (Dog) animal3; // ClassCastException!
        
        // SAFE way - always check with instanceof
        if (animal3 instanceof Dog) {
            Dog safeCast = (Dog) animal3;
            safeCast.bark();
        } else {
            System.out. println("animal3 is NOT a Dog, it's a " + 
                             animal3.getClass().getSimpleName());
        }
        
        if (animal3 instanceof Cat) {
            Cat catCast = (Cat) animal3;
            catCast.meow();
            System.out.println("Indoor cat: " + catCast.isIndoor());
        }
    }
}
```

---

## Example 2: Practical Upcasting/Downcasting

```java name=Shape. java
public abstract class Shape {
    protected String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    public abstract double getArea();
    public abstract double getPerimeter();
    
    public String getColor() {
        return color;
    }
}
```

```java name=Circle.java
public class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public double getArea() {
        return Math. PI * radius * radius;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public double getDiameter() {
        return 2 * radius;
    }
}
```

```java name=Rectangle.java
public class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double getArea() {
        return length * width;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * (length + width);
    }
    
    public double getLength() {
        return length;
    }
    
    public double getWidth() {
        return width;
    }
}
```

```java name=ShapeCastingDemo.java
public class ShapeCastingDemo {
    public static void main(String[] args) {
        // Upcasting - storing different shapes in Shape array
        Shape[] shapes = new Shape[3];
        shapes[0] = new Circle("Red", 5.0);           // Upcasting
        shapes[1] = new Rectangle("Blue", 4.0, 6.0);  // Upcasting
        shapes[2] = new Circle("Green", 3.0);         // Upcasting
        
        System.out.println("=== Processing Shapes (Upcasting) ===");
        for (Shape shape : shapes) {
            System.out.println("Color: " + shape.getColor());
            System.out.println("Area: " + shape.getArea());
            System.out.println("Perimeter: " + shape.getPerimeter());
            
            // Downcasting to access specific methods
            if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                System.out.println("Type: Circle");
                System.out.println("Radius: " + circle.getRadius());
                System.out.println("Diameter: " + circle.getDiameter());
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) shape;
                System.out.println("Type: Rectangle");
                System.out.println("Length: " + rectangle.getLength());
                System.out. println("Width: " + rectangle. getWidth());
            }
            
            System.out.println("---");
        }
        
        // Method that accepts parent type
        processShape(new Circle("Yellow", 7.0));
        processShape(new Rectangle("Orange", 5.0, 3.0));
    }
    
    // Method using upcasting - accepts any Shape
    public static void processShape(Shape shape) {
        System.out.println("\nProcessing shape: " + shape.getClass().getSimpleName());
        System.out.println("Area: " + shape.getArea());
    }
}
```

---

## Example 3: instanceof and Pattern Matching (Java 16+)

```java name=ModernCastingDemo.java
public class ModernCastingDemo {
    public static void main(String[] args) {
        Animal[] animals = {
            new Dog("Max", "Labrador"),
            new Cat("Luna", true),
            new Dog("Rocky", "German Shepherd"),
            new Cat("Shadow", false)
        };
        
        System.out.println("=== Traditional instanceof with Casting ===");
        for (Animal animal : animals) {
            // Traditional way
            if (animal instanceof Dog) {
                Dog dog = (Dog) animal; // Explicit cast
                System.out.println("Dog: " + dog.name + ", Breed: " + dog.getBreed());
                dog.bark();
            } else if (animal instanceof Cat) {
                Cat cat = (Cat) animal; // Explicit cast
                System.out.println("Cat: " + cat.name + ", Indoor: " + cat.isIndoor());
                cat.meow();
            }
        }
        
        System.out.println("\n=== Pattern Matching (Java 16+) ===");
        for (Animal animal : animals) {
            // Modern way - Pattern matching (Java 16+)
            if (animal instanceof Dog dog) { // Combined check and cast
                System.out.println("Dog: " + dog.name + ", Breed: " + dog.getBreed());
                dog.bark();
            } else if (animal instanceof Cat cat) { // Combined check and cast
                System.out.println("Cat: " + cat.name + ", Indoor: " + cat.isIndoor());
                cat.meow();
            }
        }
    }
}
```

# Polymorphism

## What is Polymorphism?

**Polymorphism** is the ability of an object to take many forms.  In Java, it means a parent class reference can refer to different child class objects.
In Java, **all non-final, non-static, non-private methods are overridable by default**. No `virtual` keyword needed!

## Types of Polymorphism
1. **Compile-time Polymorphism** (Static) - Method Overloading
2. **Runtime Polymorphism** (Dynamic) - Method Overriding

---
## Compile-time Polymorphism (Method Overloading)

### Example 4: Method Overloading

```java name=Calculator.java
public class Calculator {
    
    // Method 1: Add two integers
    public int add(int a, int b) {
        System.out.println("Adding two integers");
        return a + b;
    }
    
    // Method 2: Add three integers
    public int add(int a, int b, int c) {
        System.out.println("Adding three integers");
        return a + b + c;
    }
    
    // Method 3: Add two doubles
    public double add(double a, double b) {
        System.out. println("Adding two doubles");
        return a + b;
    }
    
    // Method 4: Add two strings (concatenation)
    public String add(String a, String b) {
        System.out.println("Adding two strings");
        return a + b;
    }
    
    // Method 5: Different parameter order
    public String add(String a, int b) {
        System.out.println("Adding string and int");
        return a + b;
    }
    
    public String add(int a, String b) {
        System.out.println("Adding int and string");
        return a + b;
    }
}
```

```java name=OverloadingDemo.java
public class OverloadingDemo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println("=== Method Overloading ===");
        
        // Same method name, different parameters
        System.out.println("Result: " + calc.add(5, 10));
        System.out.println("Result: " + calc.add(5, 10, 15));
        System.out.println("Result: " + calc.add(5.5, 10.5));
        System.out.println("Result: " + calc.add("Hello ", "World"));
        System.out.println("Result: " + calc.add("Number:  ", 42));
        System.out.println("Result: " + calc.add(42, " is the answer"));
    }
}
```

## Runtime Polymorphism (Method Overriding)

### Example 5: Method Overriding and Dynamic Binding
```java name=Employee.java
public class Employee {
    protected String name;
    protected int id;
    protected double baseSalary;
    
    public Employee(String name, int id, double baseSalary) {
        this.name = name;
        this.id = id;
        this.baseSalary = baseSalary;
    }
    
    // Method to be overridden
    public double calculateSalary() {
        return baseSalary;
    }
    
    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Salary: $" + calculateSalary());
    }
    
    public void work() {
        System.out. println(name + " is working");
    }
}
```

```java name=Manager.java
public class Manager extends Employee {
    private double bonus;
    private int teamSize;
    
    public Manager(String name, int id, double baseSalary, double bonus, int teamSize) {
        super(name, id, baseSalary);
        this.bonus = bonus;
        this.teamSize = teamSize;
    }
    
    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
    
    @Override
    public void work() {
        System.out. println(name + " is managing a team of " + teamSize);
    }
    
    public void conductMeeting() {
        System.out.println(name + " is conducting a meeting");
    }
}
```

```java name=Developer.java
public class Developer extends Employee {
    private String language;
    private int projectsCompleted;
    
    public Developer(String name, int id, double baseSalary, 
                     String language, int projectsCompleted) {
        super(name, id, baseSalary);
        this.language = language;
        this.projectsCompleted = projectsCompleted;
    }
    
    @Override
    public double calculateSalary() {
        double projectBonus = projectsCompleted * 500;
        return baseSalary + projectBonus;
    }
    
    @Override
    public void work() {
        System.out.println(name + " is coding in " + language);
    }
    
    public void debugCode() {
        System.out.println(name + " is debugging code");
    }
}
```

```java name=Intern.java
public class Intern extends Employee {
    private int monthsLeft;
    
    public Intern(String name, int id, double baseSalary, int monthsLeft) {
        super(name, id, baseSalary);
        this.monthsLeft = monthsLeft;
    }
    
    @Override
    public double calculateSalary() {
        return baseSalary * 0.5; // Interns get 50% of base salary
    }
    
    @Override
    public void work() {
        System.out. println(name + " is learning and assisting (" + 
                         monthsLeft + " months left)");
    }
}
```

```java name=PolymorphismDemo.java
public class PolymorphismDemo {
    public static void main(String[] args) {
        System.out.println("=== Runtime Polymorphism ===\n");
        
        // Parent reference, different child objects
        Employee emp1 = new Manager("Alice", 101, 80000, 15000, 10);
        Employee emp2 = new Developer("Bob", 102, 70000, "Java", 8);
        Employee emp3 = new Intern("Charlie", 103, 30000, 6);
        Employee emp4 = new Employee("Diana", 104, 50000);
        
        // Array of Employee references (Polymorphism)
        Employee[] employees = {emp1, emp2, emp3, emp4};
        
        System.out.println("=== Processing All Employees ===");
        for (Employee emp : employees) {
            System.out.println("\n--- Employee: " + emp.name + " ---");
            emp.displayInfo();
            emp.work();
            System.out.println("Type: " + emp.getClass().getSimpleName());
            
            // The correct overridden method is called at runtime
            // based on the actual object type, not reference type
        }
        
        // Calculate total salary
        System.out.println("\n=== Salary Summary ===");
        double totalSalary = 0;
        for (Employee emp : employees) {
            double salary = emp.calculateSalary(); // Polymorphic call
            System.out.println(emp.name + ": $" + salary);
            totalSalary += salary;
        }
        System.out.println("Total Company Expense: $" + totalSalary);
        
        // Demonstrating method binding
        System.out.println("\n=== Dynamic Method Binding ===");
        processEmployee(new Manager("Eve", 105, 90000, 20000, 15));
        processEmployee(new Developer("Frank", 106, 75000, "Python", 10));
        processEmployee(new Intern("Grace", 107, 25000, 3));
    }
    
    // Method accepts Employee but works with all subclasses
    public static void processEmployee(Employee emp) {
        System.out. println("\nProcessing:  " + emp.name);
        emp.work(); // Calls appropriate overridden method
        System.out.println("Salary: $" + emp.calculateSalary());
    }
}
```

## Example 6: Polymorphism with Interfaces

```java name=Payment.java
public interface Payment {
    boolean processPayment(double amount);
    String getPaymentMethod();
    double getTransactionFee(double amount);
}
```

```java name=CreditCard.java
public class CreditCard implements Payment {
    private String cardNumber;
    private double balance;
    
    public CreditCard(String cardNumber, double balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
    
    @Override
    public boolean processPayment(double amount) {
        double total = amount + getTransactionFee(amount);
        if (balance >= total) {
            balance -= total;
            System.out.println("Credit Card payment of $" + amount + 
                             " processed (Fee: $" + getTransactionFee(amount) + ")");
            return true;
        }
        System.out.println("Insufficient balance on credit card");
        return false;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card ending in " + cardNumber.substring(cardNumber.length() - 4);
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.03; // 3% fee
    }
    
    public double getBalance() {
        return balance;
    }
}
```

```java name=PayPal.java
public class PayPal implements Payment {
    private String email;
    private double balance;
    
    public PayPal(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }
    
    @Override
    public boolean processPayment(double amount) {
        double total = amount + getTransactionFee(amount);
        if (balance >= total) {
            balance -= total;
            System.out.println("PayPal payment of $" + amount + 
                             " processed (Fee: $" + getTransactionFee(amount) + ")");
            return true;
        }
        System.out.println("Insufficient balance in PayPal account");
        return false;
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal account:  " + email;
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.029 + 0.30; // 2.9% + $0.30
    }
    
    public double getBalance() {
        return balance;
    }
}
```

```java name=Bitcoin.java
public class Bitcoin implements Payment {
    private String walletAddress;
    private double bitcoinBalance;
    
    public Bitcoin(String walletAddress, double bitcoinBalance) {
        this.walletAddress = walletAddress;
        this.bitcoinBalance = bitcoinBalance;
    }
    
    @Override
    public boolean processPayment(double amount) {
        double bitcoinAmount = amount / 40000; // Assume 1 BTC = $40,000
        double total = bitcoinAmount + (bitcoinAmount * 0.01); // 1% fee
        
        if (bitcoinBalance >= total) {
            bitcoinBalance -= total;
            System.out.println("Bitcoin payment of $" + amount + 
                             " (" + bitcoinAmount + " BTC) processed");
            return true;
        }
        System. out.println("Insufficient Bitcoin balance");
        return false;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Bitcoin wallet:  " + walletAddress. substring(0, 10) + "...";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.01; // 1% fee
    }
    
    public double getBitcoinBalance() {
        return bitcoinBalance;
    }
}
```

```java name=PaymentProcessor.java
public class PaymentProcessor {
    
    // Polymorphic method - accepts any Payment implementation
    public void executePayment(Payment payment, double amount) {
        System.out.println("\n--- Processing Payment ---");
        System.out.println("Payment Method: " + payment.getPaymentMethod());
        System.out.println("Amount: $" + amount);
        System.out.println("Transaction Fee: $" + payment.getTransactionFee(amount));
        
        boolean success = payment.processPayment(amount);
        
        if (success) {
            System.out.println("✓ Payment successful!");
        } else {
            System.out.println("✗ Payment failed!");
        }
    }
    
    // Process multiple payments
    public void processMultiplePayments(Payment[] payments, double[] amounts) {
        System.out.println("=== Processing Multiple Payments ===");
        
        for (int i = 0; i < payments.length && i < amounts.length; i++) {
            executePayment(payments[i], amounts[i]);
        }
    }
    
    // Calculate total fees
    public double calculateTotalFees(Payment[] payments, double amount) {
        double totalFees = 0;
        for (Payment payment : payments) {
            totalFees += payment.getTransactionFee(amount);
        }
        return totalFees;
    }
}
```

```java name=PaymentDemo.java
public class PaymentDemo {
    public static void main(String[] args) {
        // Create different payment methods
        Payment creditCard = new CreditCard("1234-5678-9012-3456", 5000);
        Payment paypal = new PayPal("user@example.com", 3000);
        Payment bitcoin = new Bitcoin("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 0.5);
        
        PaymentProcessor processor = new PaymentProcessor();
        
        // Polymorphism - same method works with different payment types
        processor.executePayment(creditCard, 100);
        processor.executePayment(paypal, 250);
        processor.executePayment(bitcoin, 1000);
        
        // Array of different payment types (Polymorphism)
        System.out.println("\n=== Payment Method Comparison ===");
        Payment[] payments = {creditCard, paypal, bitcoin};
        double testAmount = 100;
        
        for (Payment payment : payments) {
            System.out.println("\n" + payment.getPaymentMethod());
            System.out.println("Fee for $" + testAmount + ": $" + 
                             payment.getTransactionFee(testAmount));
        }
        
        // Calculate total fees
        double totalFees = processor.calculateTotalFees(payments, testAmount);
        System.out.println("\nTotal fees for $" + testAmount + 
                         " across all methods: $" + totalFees);
        
        // Process multiple payments
        double[] amounts = {50, 75, 200};
        processor.processMultiplePayments(payments, amounts);
    }
}
```

---

## Example 7: Real-World Polymorphism - Notification System

```java name=Notification.java
public abstract class Notification {
    protected String recipient;
    protected String message;
    
    public Notification(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void send();
    
    // Common method
    public void displayInfo() {
        System.out. println("To: " + recipient);
        System.out.println("Message: " + message);
    }
}
```

```java name=EmailNotification.java
public class EmailNotification extends Notification {
    private String subject;
    
    public EmailNotification(String recipient, String message, String subject) {
        super(recipient, message);
        this.subject = subject;
    }
    
    @Override
    public void send() {
        System.out.println("\n📧 Sending Email Notification");
        System.out.println("To: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("✓ Email sent successfully!");
    }
}
```

```java name=SMSNotification.java
public class SMSNotification extends Notification {
    private String phoneNumber;
    
    public SMSNotification(String phoneNumber, String message) {
        super(phoneNumber, message);
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public void send() {
        System.out.println("\n📱 Sending SMS Notification");
        System.out.println("To: " + phoneNumber);
        System.out. println("Message: " + message);
        System.out.println("✓ SMS sent successfully!");
    }
}
```

```java name=PushNotification.java
public class PushNotification extends Notification {
    private String deviceId;
    private String appName;
    
    public PushNotification(String deviceId, String message, String appName) {
        super(deviceId, message);
        this.deviceId = deviceId;
        this.appName = appName;
    }
    
    @Override
    public void send() {
        System.out.println("\n🔔 Sending Push Notification");
        System.out.println("App: " + appName);
        System.out.println("Device ID: " + deviceId);
        System.out.println("Message: " + message);
        System.out.println("✓ Push notification sent successfully!");
    }
}
```

```java name=SlackNotification.java
public class SlackNotification extends Notification {
    private String channel;
    
    public SlackNotification(String channel, String message) {
        super(channel, message);
        this.channel = channel;
    }
    
    @Override
    public void send() {
        System.out.println("\n💬 Sending Slack Notification");
        System.out.println("Channel: " + channel);
        System.out.println("Message: " + message);
        System.out.println("✓ Slack message sent successfully!");
    }
}
```

```java name=NotificationService.java
public class NotificationService {
    
    // Send single notification (Polymorphism)
    public void sendNotification(Notification notification) {
        notification.send();
    }
    
    // Send multiple notifications (Polymorphism)
    public void sendBulkNotifications(Notification[] notifications) {
        System.out.println("=== Sending Bulk Notifications ===");
        for (Notification notification : notifications) {
            notification.send();
        }
    }
    
    // Send notification to multiple channels
    public void broadcastMessage(String message) {
        System.out.println("=== Broadcasting Message ===");
        
        Notification[] channels = {
            new EmailNotification("admin@example.com", message, "Important Alert"),
            new SMSNotification("+1-234-567-8900", message),
            new PushNotification("device-12345", message, "MyApp"),
            new SlackNotification("#general", message)
        };
        
        for (Notification notification : channels) {
            notification. send();
        }
    }
}
```

```java name=NotificationDemo.java
public class NotificationDemo {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();
        
        // Create different types of notifications
        Notification email = new EmailNotification(
            "john@example.com",
            "Your order has been shipped! ",
            "Order Update"
        );
        
        Notification sms = new SMSNotification(
            "+1-555-123-4567",
            "Your verification code is:  123456"
        );
        
        Notification push = new PushNotification(
            "user-device-789",
            "You have a new message! ",
            "ChatApp"
        );
        
        Notification slack = new SlackNotification(
            "#developers",
            "Deployment completed successfully!"
        );
        
        // Polymorphism - same method works with all notification types
        System.out.println("=== Sending Individual Notifications ===");
        service.sendNotification(email);
        service.sendNotification(sms);
        service.sendNotification(push);
        service.sendNotification(slack);
        
        // Array of different notification types
        System. out.println("\n=== Bulk Notifications ===");
        Notification[] notifications = {
            new EmailNotification("user1@example.com", "Welcome!", "Greeting"),
            new SMSNotification("+1-555-111-2222", "Your appointment is tomorrow"),
            new PushNotification("device-001", "App updated!", "MyApp")
        };
        
        service.sendBulkNotifications(notifications);
        
        // Broadcasting to all channels
        System.out.println("\n=== Broadcasting ===");
        service.broadcastMessage("System maintenance in 1 hour!");
    }
}
```

## Example 8: Polymorphism with Collections

```java name=ShapeCollection.java
import java.util.*;

public class ShapeCollection {
    public static void main(String[] args) {
        // List of different shapes (Polymorphism)
        List<Shape> shapes = new ArrayList<>();
        
        shapes.add(new Circle("Red", 5.0));
        shapes.add(new Rectangle("Blue", 4.0, 6.0));
        shapes.add(new Circle("Green", 3.0));
        shapes.add(new Rectangle("Yellow", 5.0, 5.0));
        
        System.out.println("=== All Shapes ===");
        for (Shape shape : shapes) {
            System.out.println("\nShape: " + shape.getClass().getSimpleName());
            System.out.println("Color: " + shape.getColor());
            System.out.println("Area: " + shape.getArea());
            System.out.println("Perimeter: " + shape.getPerimeter());
        }
        
        // Calculate statistics
        double totalArea = 0;
        double maxArea = 0;
        Shape largestShape = null;
        
        for (Shape shape : shapes) {
            double area = shape.getArea();
            totalArea += area;
            
            if (area > maxArea) {
                maxArea = area;
                largestShape = shape;
            }
        }
        
        System.out.println("\n=== Statistics ===");
        System.out.println("Total shapes: " + shapes.size());
        System.out.println("Total area: " + totalArea);
        System.out.println("Average area: " + (totalArea / shapes.size()));
        System.out.println("Largest shape: " + largestShape.getClass().getSimpleName() +
                         " with area: " + maxArea);
        
        // Filter circles
        System.out.println("\n=== Circles Only ===");
        for (Shape shape : shapes) {
            if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                System.out.println("Circle - Radius: " + circle.getRadius() +
                                 ", Area:  " + circle.getArea());
            }
        }
    }
}
```

## Summary
### Upcasting
```java
Dog dog = new Dog("Buddy", "Labrador");
Animal animal = dog; // Upcasting (implicit)
animal.eat(); // Can call parent methods
// animal.bark(); // ERROR!  Cannot call Dog-specific methods
```
### Downcasting
```java
Animal animal = new Dog("Max", "Husky");
if (animal instanceof Dog) {
    Dog dog = (Dog) animal; // Downcasting (explicit)
    dog.bark(); // Now can call Dog-specific methods
}
```
### Polymorphism
```java
// One interface, many implementations
Payment payment1 = new CreditCard(... );
Payment payment2 = new PayPal(...);
Payment payment3 = new Bitcoin(...);

// Same method call, different behavior
payment1.processPayment(100); // CreditCard processing
payment2.processPayment(100); // PayPal processing
payment3.processPayment(100); // Bitcoin processing
```


# Nested Classes
## What is a Nested Class?
A **nested class** is a class defined **inside another class**. Java allows you to organize related classes together.

```java
class OuterClass {
    // This is the outer class
    
    class InnerClass {
        // This is a nested class (inside OuterClass)
    }
}
```

## Types of Nested Classes
Java has **4 types** of nested classes:
1. **Static Nested Class**
2. **Non-static Nested Class (Inner Class)**
3. **Local Inner Class**
4. **Anonymous Inner Class**

---
## 1. Static Nested Class

A static nested class is declared with the `static` keyword. It **cannot** access non-static members of the outer class directly.

```java
public class OuterClass {
    private static String outerStaticField = "Static field";
    private String outerInstanceField = "Instance field";
    
    // STATIC NESTED CLASS
    static class StaticNestedClass {
        public void display() {
            // Can access static members of outer class
            System.out.println("Accessing: " + outerStaticField);
            
            // CANNOT access instance members directly
            // System.out.println(outerInstanceField); // ERROR!
        }
    }
    
    public static void main(String[] args) {
        // Create instance of static nested class
        OuterClass.StaticNestedClass nested = new OuterClass.StaticNestedClass();
        nested.display();
    }
}
```

**Key Points:**
- Can be created **without** creating an instance of the outer class
- Can only access **static** members of the outer class
- Used when the nested class doesn't need access to outer class instance variables

---
## 2. Non-Static Nested Class (Inner Class)

An inner class is **not static** and has access to **all members** (including private) of the outer class.

```java
public class OuterClass {
    private String outerField = "Outer field";
    private static String staticField = "Static field";
    
    // INNER CLASS (Non-static)
    class InnerClass {
        private String innerField = "Inner field";
        
        public void display() {
            // Can access ALL members of outer class
            System.out.println("Outer field: " + outerField);
            System.out.println("Static field: " + staticField);
            System.out.println("Inner field: " + innerField);
        }
    }
    
    public static void main(String[] args) {
        // Must create outer class instance first
        OuterClass outer = new OuterClass();
        
        // Then create inner class instance
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();
    }
}
```

**Key Points:**
- Must create an **outer class instance** first
- Can access **all members** (static and non-static) of outer class
- Each inner class instance is **associated** with an outer class instance

---
## 3. Local Inner Class
A class defined **inside a method or block**. It's only accessible within that method/block.

```java
public class OuterClass {
    private String outerField = "Outer field";
    
    public void myMethod() {
        // LOCAL INNER CLASS (inside a method)
        class LocalInnerClass {
            public void display() {
                System.out.println("Accessing: " + outerField);
            }
        }
        
        // Create and use the local inner class
        LocalInnerClass local = new LocalInnerClass();
        local.display();
    }
    
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.myMethod();
        
        // Cannot access LocalInnerClass here - it's local to myMethod()
    }
}
```

**Key Points:**
- Defined inside a method or block
- Only accessible within that method/block
- Can access outer class members and **final/effectively final** local variables

---
## 4. Anonymous Inner Class

A class **without a name**, usually used for one-time use (often with interfaces or abstract classes).

```java
// Interface example
interface Greeting {
    void sayHello();
}

public class AnonymousDemo {
    public static void main(String[] args) {
        // ANONYMOUS INNER CLASS
        Greeting greeting = new Greeting() {
            @Override
            public void sayHello() {
                System.out.println("Hello from anonymous class!");
            }
        };
        
        greeting.sayHello();
    }
}
```

**Another example with abstract class:**

```java
abstract class Animal {
    abstract void makeSound();
}

public class Test {
    public static void main(String[] args) {
        // Anonymous inner class extending Animal
        Animal dog = new Animal() {
            @Override
            void makeSound() {
                System.out.println("Woof!");
            }
        };
        
        dog.makeSound();
        
        // Another anonymous inner class
        Animal cat = new Animal() {
            @Override
            void makeSound() {
                System.out.println("Meow!");
            }
        };
        
        cat.makeSound();
    }
}
```

**Key Points:**
- No class name
- Used for quick, one-time implementations
- Commonly used with interfaces, abstract classes, or event handlers

---
## Real-World Example: Combining All Concepts

```java
public class University {
    private String universityName;
    private static int totalStudents = 0;
    
    public University(String name) {
        this.universityName = name;
    }
    
    // 1. STATIC NESTED CLASS
    static class Department {
        private String deptName;
        
        public Department(String name) {
            this.deptName = name;
        }
        
        public void showInfo() {
            System.out.println("Department: " + deptName);
            System.out.println("Total students: " + totalStudents);
            // Cannot access universityName here (it's non-static)
        }
    }
    
    // 2. INNER CLASS (Non-static)
    class Student {
        private String studentName;
        
        public Student(String name) {
            this.studentName = name;
            totalStudents++;
        }
        
        public void showInfo() {
            // Can access outer class members
            System.out.println("Student: " + studentName);
            System.out.println("University: " + universityName);
        }
    }
    
    // 3. LOCAL INNER CLASS (inside method)
    public void admitStudent(String name, int age) {
        // Local inner class
        class AdmissionRecord {
            public void printRecord() {
                System.out.println("Admitting " + name + ", age " + age);
                System.out.println("To: " + universityName);
            }
        }
        
        AdmissionRecord record = new AdmissionRecord();
        record.printRecord();
    }
    
    public static void main(String[] args) {
        // Static nested class - no outer instance needed
        University.Department dept = new University.Department("Computer Science");
        dept.showInfo();
        
        System.out.println("---");
        
        // Inner class - needs outer instance
        University uni = new University("MIT");
        University.Student student1 = uni.new Student("Alice");
        student1.showInfo();
        
        System.out.println("---");
        
        // Local inner class - called through method
        uni.admitStudent("Bob", 20);
        
        System.out.println("---");
        
        // 4. ANONYMOUS INNER CLASS
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Task running in anonymous class!");
            }
        };
        task.run();
    }
}
```

## Comparison

| Type              | Static? | Access to Outer Members       | Creation Syntax                                                         | Use Case                      |
| ----------------- | ------- | ----------------------------- | ----------------------------------------------------------------------- | ----------------------------- |
| **Static Nested** | Yes     | Only static members           | `Outer.Nested nested = new Outer.Nested()`                              | Utility classes, grouping     |
| **Inner Class**   | No      | All members                   | `Outer outer = new Outer();`<br>`Outer.Inner inner = outer.new Inner()` | Closely related functionality |
| **Local Inner**   | No      | All members + local variables | Inside method only                                                      | Helper classes in methods     |
| **Anonymous**     | No      | All members                   | `new Interface() { ... }`                                               | One-time use, event handlers  |
## Why Use Nested Classes?
1. **Logical Grouping**: Group classes that are only used in one place
2. **Encapsulation**: Hide implementation details
3. **More Readable Code**: Keep related code together
4. **Access to Private Members**: Inner classes can access private members of outer class

## Practice Exercise
Try to identify the type of nested class and what will be printed:
```java
public class Outer {
    private String message = "Hello";
    
    static class Nested {
        public void print() {
            System.out.println("Nested class");
        }
    }
    
    class Inner {
        public void print() {
            System.out.println(message + " from Inner");
        }
    }
    
    public void test() {
        Runnable r = new Runnable() {
            public void run() {
                System.out.println(message + " from Anonymous");
            }
        };
        r.run();
    }
}
```

**Questions:**
1. What type is `Nested`?
2. What type is `Inner`?
3. What type is the `Runnable` in `test()` method?

**Answers:**
1. Static Nested Class
2. Inner Class (Non-static)
3. Anonymous Inner Class

# Why Superclass Can Point to Subclass (But Not Vice Versa)

## The Core Principle: "IS-A" Relationship
The key is the **IS-A relationship**:
- A `Dog` **IS-A** `Animal` ✅
- An `Animal` **IS-NOT necessarily a** `Dog` ❌

---
## Example 1: Understanding the Concept
Animal.java
```java
public class Animal {
    public void eat() {
        System.out. println("Animal is eating");
    }
    
    public void sleep() {
        System.out.println("Animal is sleeping");
    }
}
```

Dog.java
```java
public class Dog extends Animal {
    public void bark() {
        System.out. println("Dog is barking");
    }
    
    // Dog has:   eat(), sleep() (inherited), AND bark()
}
```

Cat.java
```java
public class Cat extends Animal {
    public void meow() {
        System.out.println("Cat is meowing");
    }
    
    // Cat has:  eat(), sleep() (inherited), AND meow()
}
```

WhyItWorksDemo.java
```java
public class WhyItWorksDemo {
    public static void main(String[] args) {
        
        // ✅ VALID:  Superclass reference to subclass object
        Animal animal1 = new Dog();
        Animal animal2 = new Cat();
        
        /* WHY THIS WORKS:
         * - Dog IS-A Animal (Dog has all Animal methods)
         * - animal1 reference only "sees" Animal methods
         * - Dog object HAS eat() and sleep(), so it's safe! 
         */
        
        animal1.eat();    // ✅ Works - Dog has eat() (inherited)
        animal1.sleep();  // ✅ Works - Dog has sleep() (inherited)
        // animal1.bark(); // ❌ Compile Error!  Animal reference doesn't know about bark()
        
        System.out.println();
        
        // ❌ INVALID: Subclass reference to superclass object
        // Dog dog = new Animal(); // ❌ COMPILE ERROR! 
        
        /* WHY THIS DOESN'T WORK:
         * - dog reference expects to call bark()
         * - But Animal object doesn't HAVE bark() method!
         * - This would cause problems at runtime
         */
        
        // If Java allowed this (it doesn't):
        // dog.bark(); // ❌ Would crash!  Animal doesn't have bark()
        
        
        // Let's demonstrate the problem with a hypothetical scenario
        demonstrateTheProblem();
    }
    
    public static void demonstrateTheProblem() {
        System.out.println("=== Why Subclass Can't Point to Superclass ===\n");
        
        // Imagine Java allowed this (it doesn't):
        // Dog dog = new Animal(); // Pretend this compiles
        
        /* The problem:
         * Dog reference promises these methods are available: 
         * - eat()   ✅ Animal has this
         * - sleep() ✅ Animal has this  
         * - bark()  ❌ Animal does NOT have this!
         * 
         * When you call dog.bark(), what happens?
         * The Animal object doesn't know how to bark!
         * This would crash the program. 
         */
        
        System.out.println("If subclass could point to superclass:");
        System.out.println("Dog dog = new Animal(); // Imagine this works");
        System.out.println("dog.eat();   // OK - Animal has eat()");
        System.out.println("dog. sleep(); // OK - Animal has sleep()");
        System.out. println("dog.bark();  // CRASH!  Animal doesn't have bark()!");
        System.out.println("\nJava prevents this at compile-time to keep you safe!");
    }
}
```

## Visual Representation
```
┌─────────────────────┐
│      Animal         │
│  ┌──────────────┐  │
│  │ eat()        │  │
│  │ sleep()      │  │
│  └──────────────┘  │
└─────────────────────┘
          △
          │ (inherits)
          │
┌─────────────────────┐
│        Dog          │
│  ┌──────────────┐  │
│  │ eat()        │  │ ← Inherited
│  │ sleep()      │  │ ← Inherited
│  │ bark()       │  │ ← Dog-specific
│  └──────────────┘  │
└─────────────────────┘
```

### Superclass → Subclass (Works)

```java
Animal animal = new Dog();
// Animal reference sees: eat(), sleep()
// Dog object has: eat(), sleep(), bark()
// ✅ Safe!  Dog has everything Animal needs
```

### Subclass → Superclass (Doesn't Work)
``` java
Dog dog = new Animal(); // ❌ Compile Error!
// Dog reference expects: eat(), sleep(), bark()
// Animal object has: eat(), sleep()
// ❌ Unsafe! Animal is missing bark()
```

