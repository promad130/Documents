
### What is Math class?
The **java.lang.Math** class is a built-in utility class in Java that provides static methods for performing basic mathematical operations. This class extends `Object` and is declared as `public final`, meaning you cannot create instances of it or extend it. All methods in the Math class are static, which means you can call them directly using the class name without creating an object.

- **Static Nature**: All methods are static, so you call them using `Math.methodName()`
    
- **No Object Creation**: You never need to create a Math object to use its methods
    
- **Final Class**: Cannot be extended or subclassed
    
- **Built-in Package**: Part of `java.lang` package, so no import statement is needed

### Math constants
The Math class provides two important mathematical constants:
```java
public class MathConstants {
    public static void main(String[] args) {
        System.out.println("Pi = " + Math.PI);  // 3.141592653589793
        System.out.println("E = " + Math.E);    // 2.718281828459045
    }
}

```

### Key methods:
#### Basic methods
**Finding Maximum and Minimum Values:**
```java
public class BasicMath {
    public static void main(String[] args) {
        int a = 15, b = 25;
        double x = 3.7, y = 2.1;
        
        // Finding maximum values
        System.out.println("Max of " + a + " and " + b + ": " + Math.max(a, b)); // 25
        System.out.println("Max of " + x + " and " + y + ": " + Math.max(x, y)); // 3.7
        
        // Finding minimum values
        System.out.println("Min of " + a + " and " + b + ": " + Math.min(a, b)); // 15
        System.out.println("Min of " + x + " and " + y + ": " + Math.min(x, y)); // 2.1
    }
}

```

**Absolute Values:**
The `abs()` method returns the absolute (positive) value of a number
```java
public class AbsoluteValue {
    public static void main(String[] args) {
        int negativeInt = -42;
        double negativeDouble = -15.7;
        
        System.out.println("Absolute value of " + negativeInt + ": " + Math.abs(negativeInt)); // 42
        System.out.println("Absolute value of " + negativeDouble + ": " + Math.abs(negativeDouble)); // 15.7
    }
}
```

**Power and Square Root:**
```java
public class PowerAndRoot {
    public static void main(String[] args) {
        double base = 2.0;
        double exponent = 8.0;
        double number = 64.0;
        
        // Power calculation
        System.out.println(base + " raised to " + exponent + ": " + Math.pow(base, exponent)); // 256.0
        
        // Square root
        System.out.println("Square root of " + number + ": " + Math.sqrt(number)); // 8.0
        
        // Cube root
        System.out.println("Cube root of 27: " + Math.cbrt(27)); // 3.0
    }
}
```

#### Rounding Methods
Understanding different rounding behaviors is crucial:
```java
public class RoundingMethods {
    public static void main(String[] args) {
        double value = 7.8;
        double negativeValue = -7.8;
        
        System.out.println("Original value: " + value);
        
        // Round to nearest integer
        System.out.println("Math.round(): " + Math.round(value)); // 8
        
        // Ceiling - smallest integer >= value
        System.out.println("Math.ceil(): " + Math.ceil(value)); // 8.0
        
        // Floor - largest integer <= value
        System.out.println("Math.floor(): " + Math.floor(value)); // 7.0
        
        System.out.println("\nWith negative value: " + negativeValue);
        System.out.println("Math.round(): " + Math.round(negativeValue)); // -8
        System.out.println("Math.ceil(): " + Math.ceil(negativeValue)); // -7.0
        System.out.println("Math.floor(): " + Math.floor(negativeValue)); // -8.0
    }
}

```

#### Random Number generation
The `Math.random()` method generates a random double between 0.0 (inclusive) and 1.0 (exclusive):
```java
public class RandomNumbers {
    public static void main(String[] args) {
        // Basic random number (0.0 to 1.0)
        System.out.println("Random decimal: " + Math.random());
        
        // Random integer from 1 to 100
        int randomInt = (int)(Math.random() * 100) + 1;
        System.out.println("Random integer (1-100): " + randomInt);
        
        // Random integer within a specific range (min to max)
        int min = 50, max = 75;
        int rangeRandom = (int)(Math.random() * (max - min + 1)) + min;
        System.out.println("Random integer (" + min + "-" + max + "): " + rangeRandom);
    }
}

```
**Main Takeaway:** The `Math.random()` method in Java does _not_ accept a range parameter—it always returns a _double_ between 0.0 (inclusive) and 1.0 (exclusive).

#### Trigonometric methods 
All trigonometric methods in Java work with **radians**, not degrees:
```java
public class TrigonometricMethods {
    public static void main(String[] args) {
        double degrees = 45.0;
        double radians = Math.toRadians(degrees); // Convert degrees to radians
        
        System.out.println("Angle: " + degrees + " degrees = " + radians + " radians");
        
        // Basic trigonometric functions
        System.out.println("sin(" + degrees + "°): " + Math.sin(radians)); // ≈ 0.707
        System.out.println("cos(" + degrees + "°): " + Math.cos(radians)); // ≈ 0.707
        System.out.println("tan(" + degrees + "°): " + Math.tan(radians)); // ≈ 1.0
        
        // Inverse trigonometric functions (return values in radians)
        double value = 0.5;
        System.out.println("arcsin(" + value + "): " + Math.toDegrees(Math.asin(value)) + "°"); // 30°
        System.out.println("arccos(" + value + "): " + Math.toDegrees(Math.acos(value)) + "°"); // 60°
        System.out.println("arctan(" + value + "): " + Math.toDegrees(Math.atan(value)) + "°"); // ≈ 26.57°
        
        // Hyperbolic functions
        System.out.println("sinh(" + value + "): " + Math.sinh(value));
        System.out.println("cosh(" + value + "): " + Math.cosh(value));
        System.out.println("tanh(" + value + "): " + Math.tanh(value));
    }
}
```

#### Logarithmic and exponential methods
```java
public class LogarithmicMethods {
    public static void main(String[] args) {
        double value = 10.0;
        
        // Natural logarithm (base e)
        System.out.println("ln(" + value + "): " + Math.log(value)); // ≈ 2.303
        
        // Base-10 logarithm
        System.out.println("log₁₀(" + value + "): " + Math.log10(value)); // 1.0
        
        // e raised to the power of value
        System.out.println("e^" + value + ": " + Math.exp(value)); // ≈ 22026.47
        
        // Natural log of (value + 1) - more precise for small values
        double smallValue = 0.001;
        System.out.println("ln(" + smallValue + " + 1): " + Math.log1p(smallValue));
        
        // e^value - 1 - more precise for small values
        System.out.println("e^" + smallValue + " - 1: " + Math.expm1(smallValue));
    }
}
```

#### Advanced Arithmetic methods:
Java provides "exact" methods that throw exceptions on overflow:
```java
public class ExactMethods {
    public static void main(String[] args) {
        int a = 15, b = 25;
        long x = 100L, y = 200L;
        
        try {
            // Exact arithmetic operations
            System.out.println("addExact: " + Math.addExact(a, b)); // 40
            System.out.println("subtractExact: " + Math.subtractExact(b, a)); // 10
            System.out.println("multiplyExact: " + Math.multiplyExact(a, b)); // 375
            
            // Increment and decrement
            System.out.println("incrementExact: " + Math.incrementExact(a)); // 16
            System.out.println("decrementExact: " + Math.decrementExact(a)); // 14
            
            // Negation
            System.out.println("negateExact: " + Math.negateExact(a)); // -15
            
            // Convert long to int (throws exception if overflow)
            System.out.println("toIntExact: " + Math.toIntExact(x)); // 100
            
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic overflow occurred!");
        }
    }
}

```

#### Utility Methods
```java
public class UtilityMethods {
    public static void main(String[] args) {
        double x = 3.0, y = 4.0;
        
        // Hypotenuse calculation: √(x² + y²)
        System.out.println("Hypotenuse of " + x + " and " + y + ": " + Math.hypot(x, y)); // 5.0
        
        // Sign function
        System.out.println("Sign of 5.5: " + Math.signum(5.5)); // 1.0
        System.out.println("Sign of -3.2: " + Math.signum(-3.2)); // -1.0
        System.out.println("Sign of 0.0: " + Math.signum(0.0)); // 0.0
        
        // Copy sign from one number to another
        System.out.println("copySign(3.5, -2.1): " + Math.copySign(3.5, -2.1)); // -3.5
        
        // IEEE remainder
        System.out.println("IEEEremainder(5.0, 3.0): " + Math.IEEEremainder(5.0, 3.0)); // -1.0
    }
}

```

### Example
```java
public class AdvancedCalculator {
    
    // Calculate area of a circle
    public static double circleArea(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }
    
    // Calculate distance between two points
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }
    
    // Generate random number within range
    public static int randomInRange(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    
    // Calculate compound interest
    public static double compoundInterest(double principal, double rate, int time) {
        return principal * Math.pow(1 + rate, time);
    }
    
    // Round to specific decimal places
    public static double roundToDecimals(double value, int decimals) {
        double multiplier = Math.pow(10, decimals);
        return Math.round(value * multiplier) / multiplier;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Calculator Demo ===");
        
        // Circle area
        double radius = 5.0;
        System.out.println("Area of circle with radius " + radius + ": " + 
                         roundToDecimals(circleArea(radius), 2));
        
        // Distance calculation
        System.out.println("Distance between (0,0) and (3,4): " + distance(0, 0, 3, 4));
        
        // Random numbers
        System.out.println("Random number between 10-20: " + randomInRange(10, 20));
        
        // Compound interest
        double principal = 1000.0;
        double rate = 0.05; // 5%
        int years = 10;
        System.out.println("$" + principal + " at " + (rate*100) + "% for " + years + 
                         " years: $" + roundToDecimals(compoundInterest(principal, rate, years), 2));
    }
}

```