![[Java GUI Classes and Their Basic Functions (JFrame)#Overview]]

![[Java GUI Classes and Their Basic Functions (JFrame)#What is JFrame?]]


Starting off, the first frame:
```Java
import javax.swing.*; 

public class MyFirstWindow 
{ 
	public static void main(String[] args) 
	{ 
		// 1. Create the window 
		JFrame frame = new JFrame("My First Window"); 
		
		// 2. Set the size (width x height in pixels) 
		frame.setSize(400, 300); 
		
		// 3. Close app when window is closed 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		// 4. Center on screen 
		frame.setLocationRelativeTo(null); 
		
		// 5. Show it! 
		frame.setVisible(true); 
	} 
}
```

## Adding UI components

A window is boring without content. Let's add labels, buttons, and text fields.
The most common Swing components you'll use:

#### JLabel

Displays text or an image. Read-only. `new JLabel("Hello!")`

#### JButton

A clickable button. `new JButton("Click me")`

#### JTextField

Single-line text input. `new JTextField(20)`

#### JTextArea

Multi-line text input. `new JTextArea(5, 20)`

#### JCheckBox

A checkbox toggle. `new JCheckBox("Option")`

#### JComboBox

A dropdown menu. `new JComboBox(items)`

(*Check out [[Java GUI Classes and Their Basic Functions (JFrame)#Core Class Hierarchy]] for more detail*)

```Java
import javax.swing.*; 

public class ComponentDemo 
{ 
	public static void main(String[] args) 
	{ 
		JFrame frame = new JFrame("Components Demo"); 
		frame.setSize(350, 200); 
		frame.setLayout(new FlowLayout()); 
		
		// auto-arrange 
		JLabel label = new JLabel("Your name:"); 
		JTextField field = new JTextField(15); 
		JButton btn = new JButton("Say Hello"); 
		
		// add to window 
		frame.add(label);
		frame.add(field); 
		frame.add(btn); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
		
	} 
	
}
```


## Layout managers

Layouts control HOW components are positioned inside your window. This is one of the most important JFrame concepts.
Java uses **Layout Managers** to automatically position components. Without one, components stack on top of each other!

### `FlowLayout` — left-to-right, wraps like text
![[Pasted image 20260526161200.png]]

```Java
Javaframe.setLayout(new FlowLayout());
```


### `BorderLayout` — 5 fixed regions (default JFrame layout)
![[Pasted image 20260526161214.png]]

```java
frame.setLayout(new BorderLayout()); 
frame.add(btn, BorderLayout.NORTH); // top 
frame.add(label, BorderLayout.CENTER); // middle
```

### `GridLayout` — equal-size grid of cells
![[Pasted image 20260526162134.png]]

```Java
frame.setLayout(new GridLayout(2, 3)); // 2 rows, 3 cols
```


# Events and listeners 

Right now your button does nothing. Let's make it react when the user clicks it.
To respond to a button click, you attach an **ActionListener** to the button. Here's the pattern:

```Java
import javax.swing.*; 
import java.awt.event.*; 

public class ClickDemo 
{ 
	public static void main(String[] args) 
	{ 
		JFrame frame = new JFrame("Click Demo"); 
		
		JButton btn = new JButton("Click me!"); 
		JLabel label = new JLabel("Waiting..."); 
		
		// Attach a listener — runs when button is clicked 
		btn.addActionListener(new ActionListener() 
			{ 
				public void actionPerformed(ActionEvent e) 
				{ 
					label.setText("You clicked the button!"); 
				} 
			}
		); 
		
		frame.setLayout(new FlowLayout()); 
		frame.add(btn); 
		frame.add(label); 
		
		frame.setSize(300, 120); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		frame.setVisible(true); 
	} 
}
```

### Modern shortcut — Lambda (Java 8+)

Instead of the verbose anonymous class above, you can write:

```Java
Javabtn.addActionListener(e -> label.setText("Clicked!"));
```

Both do exactly the same thing. The lambda version is shorter and preferred in modern Java.

### Other common event types:

| Event         | Listener to use  |
| ------------- | ---------------- |
| Button click  | ActionListener   |
| Key pressed   | KeyListener      |
| Mouse click   | MouseListener    |
| Text changed  | DocumentListener |
| Window closed | WindowListener   |

# ALL DONE!!!
## You've got the basics!

Here's what to explore next to level up your Swing skills.
Core JFrame concepts covered!
You now understand windows, components, layouts, and events.

Now head back to [[Java GUI Classes and Their Basic Functions (JFrame)]] and explore things and just fuck around!