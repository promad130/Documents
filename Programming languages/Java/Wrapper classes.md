Wrapper classes convert primitive data types into objects, enabling primitives to be used in object-oriented contexts where only objects are allowed. Java provides eight wrapper classes corresponding to its eight primitive types: `Byte`, `Short`, `Integer`, `Long`, `Float`, `Double`, `Character`, and `Boolean`.

## Purpose and Benefits
Wrapper classes serve several critical purposes in Java programming:

- **Object-based APIs**: Many Java collections (like `ArrayList`, `HashMap`) and generics require objects rather than primitives, making wrapper classes essential for storing primitive values in these structures
- **Additional functionality**: Wrapper classes provide utility methods for type conversion, parsing strings into numeric values, and comparing values
- **Null values**: Unlike primitives, wrapper objects can hold `null`, which is useful for representing missing or undefined values

## Autoboxing and Unboxing

Autoboxing is the automatic conversion of primitive types to their corresponding wrapper objects, while unboxing is the reverse process. This happens automatically in Java:[^4]

```java
// Autoboxing: primitive to object
ArrayList<Integer> list = new ArrayList<>();
list.add(5);  // int is automatically converted to Integer

// Unboxing: object to primitive
int value = list.get(0);  // Integer is automatically converted to int
```


## Manual Conversion
You can also manually convert between primitives and wrapper objects:

```java
// Primitive to wrapper object
Integer aObj = Integer.valueOf("23");
Double bObj = Double.valueOf(5.55);

// Wrapper object to primitive
int a = aObj.intValue();
double b = bObj.doubleValue();
```

## Important Wrapper Class Methods
### String to Primitive Conversion
The `parseXxx()` methods convert strings to primitive types and are static methods available in numeric wrapper classes:

- **`parseInt(String s)`**: Converts string to `int` primitive
- **`parseLong(String s)`**: Converts string to `long` primitive
- **`parseFloat(String s)`**: Converts string to `float` primitive
- **`parseDouble(String s)`**: Converts string to `double` primitive

```java
String s = "123";
int num = Integer.parseInt(s);        // Returns primitive int
double d = Double.parseDouble("45.6"); // Returns primitive double
```

### String to Wrapper Object Conversion
The `valueOf()` method converts strings (or primitives) to wrapper objects:

```java
String s = "456";
Integer obj = Integer.valueOf(s);     // Returns Integer object
Double dObj = Double.valueOf(12.3); // Returns Double object
Boolean bObj = Boolean.valueOf("true"); // Returns Boolean object
```

### Wrapper Object to Primitive
The `xxxValue()` methods extract primitive values from wrapper objects:
- **`intValue()`**: Returns `int` from Integer
- **`doubleValue()`**: Returns `double` from Double
- **`booleanValue()`**: Returns `boolean` from Boolean
- **`charValue()`**: Returns `char` from Character

```java
Integer obj = 50;
int num = obj.intValue();           // Converts to primitive
Double dObj = 99.9;
double value = dObj.doubleValue();  // Converts to primitive
```

### Wrapper to String Conversion
The `toString()` method converts wrapper objects (or primitives) to string representation:
```java
Integer num = 100;
String str = num.toString();              // Non-static: "100"
String str2 = Integer.toString(250);      // Static version: "250"
```

### Comparison Methods
The `compareTo()` method compares two wrapper objects numerically:[^5][^6]

```java
Integer a = 100;
Integer b = 200;
Integer c = 100;

a.compareTo(b);  // Returns -1 (a < b)
a.compareTo(c);  // Returns 0 (a == c)
b.compareTo(a);  // Returns 1 (b > a)
```
