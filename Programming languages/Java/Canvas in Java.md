Canvas in Java is a blank rectangular area on the screen where an application can draw or receive user input like mouse events.

## Key points about Canvas:

- **What is it?**  
    Canvas is a component that provides a blank space where drawing can be done using graphics, or input events (like mouse actions) can be captured.
    
- **Where is it used?**  
    It is used in Java GUI programs when you want to create custom drawings, animations, or capture graphical interactions from the user.
    
- **Package it belongs to:**  
    Canvas belongs to the **java.awt** package (Abstract Window Toolkit).
    

## Basic example (simplified):

java

`import java.awt.Canvas; import java.awt.Color; import java.awt.Graphics; import javax.swing.JFrame; public class MyCanvasExample extends Canvas {     public void paint(Graphics g) {         g.setColor(Color.RED);         g.drawString("This is a canvas", 50, 50); // Draw text on canvas     }     public static void main(String[] args) {         JFrame frame = new JFrame("Canvas Example");         Canvas canvas = new MyCanvasExample();         canvas.setSize(300, 200);         frame.add(canvas);         frame.setSize(400, 300);         frame.setVisible(true);         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     } }`

This code creates a window with a canvas area where text is drawn.


---
# Basic functions in Canvas
