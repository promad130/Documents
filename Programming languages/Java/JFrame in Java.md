JFrame in Java is a class used to create a window for graphical user interface (GUI) applications. It serves as the main window where you can add buttons, labels, text fields, and other components to build a user interface.

## Key points about JFrame:

- **What is it?**  
    JFrame is a top-level container (a window with a title bar, border, and buttons to close, minimize, or maximize). It forms the base window for GUI applications in Java.
    
- **Where is it used?**  
    It is used in Java Swing applications to create windows for desktop GUI programs such as forms, dialogs, and main application windows.
    
- **Package it belongs to:**  
    JFrame belongs to the **javax.swing** package.
    
- **Basic usage:**  
    You create a JFrame object, set its size and title, add components to it, and make it visible.
    

## Example code (simplified for beginners):

java

`import javax.swing.JFrame; import javax.swing.JButton; public class MyWindow {     public static void main(String[] args) {         JFrame frame = new JFrame("My Window"); // Create JFrame with title         frame.setSize(400, 300);                // Set window size         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app on window close         JButton button = new JButton("Click Me"); // Create a button         frame.add(button);                         // Add button to frame         frame.setVisible(true);                    // Make the window visible     } }`

This creates a simple window with a button inside it.

---
# Basic functions in JFrame
