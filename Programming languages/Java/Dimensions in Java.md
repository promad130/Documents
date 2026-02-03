In Java, **Dimension** is a class that holds the width and height values of a GUI component or an object.

## Key points about Dimension:

- **What is it?**  
    Dimension encapsulates the **width** and **height** of a component (like a window, button, or panel) in one object, usually as integer values.
    
- **Where is it used?**  
    It is commonly used in GUI programming to specify or retrieve the size of components, layouts, and windows.
    
- **Package it belongs to:**  
    Dimension belongs to the **java.awt** package.
    

## Constructors of Dimension:

1. **Dimension()**  
    Creates a Dimension object with width and height set to 0.
    
2. **Dimension(Dimension d)**  
    Creates a new Dimension object copying the width and height from another Dimension object.
    
3. **Dimension(int width, int height)**  
    Creates a Dimension object with specified width and height.
    

## Example usage:

java

`import java.awt.Dimension; public class TestDimension {     public static void main(String[] args) {         Dimension d1 = new Dimension();            // width=0, height=0         Dimension d2 = new Dimension(100, 50);    // width=100, height=50         Dimension d3 = new Dimension(d2);          // copy width and height from d2         System.out.println("d1: " + d1.width + " x " + d1.height);         System.out.println("d2: " + d2.width + " x " + d2.height);         System.out.println("d3: " + d3.width + " x " + d3.height);     } }`

# Basic functions in Dimensions
