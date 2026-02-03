
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Simple GUI Application demonstrating Java GUI classes
 * This example shows JFrame, JPanel, JButton, JLabel, JTextField, and event handling
 */
public class SimpleGUIApp extends JFrame implements ActionListener {

    // GUI Components
    private JPanel mainPanel;
    private JLabel nameLabel, resultLabel;
    private JTextField nameField;
    private JButton greetButton, clearButton;

    // Constructor - sets up the GUI
    public SimpleGUIApp() {
        // Set up the JFrame (main window)
        setTitle("Simple GUI Application");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Initialize components
        initializeComponents();

        // Layout components
        layoutComponents();

        // Add event listeners
        addEventListeners();

        // Make window visible
        setVisible(true);
    }

    private void initializeComponents() {
        // Create components
        mainPanel = new JPanel();
        nameLabel = new JLabel("Enter your name:");
        nameField = new JTextField(20);
        greetButton = new JButton("Greet");
        clearButton = new JButton("Clear");
        resultLabel = new JLabel("Result will appear here");

        // Set preferred sizes using Dimension
        nameField.setPreferredSize(new Dimension(200, 25));
        resultLabel.setPreferredSize(new Dimension(300, 30));

        // Style components
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(Color.BLUE);
        mainPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void layoutComponents() {
        // Use FlowLayout for simple arrangement
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add components to panel
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(greetButton);
        mainPanel.add(clearButton);
        mainPanel.add(resultLabel);

        // Add panel to frame
        add(mainPanel);
    }

    private void addEventListeners() {
        // Add action listeners to buttons
        greetButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Add key listener to text field (Enter key)
        nameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    greetUser();
                }
            }
        });
    }

    // Event handling method
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == greetButton) {
            greetUser();
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void greetUser() {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            resultLabel.setText("Hello, " + name + "! Welcome to Java GUI!");
            resultLabel.setForeground(Color.GREEN);
        } else {
            resultLabel.setText("Please enter your name first!");
            resultLabel.setForeground(Color.RED);
        }
    }

    private void clearFields() {
        nameField.setText("");
        resultLabel.setText("Result will appear here");
        resultLabel.setForeground(Color.BLUE);
        nameField.requestFocus(); // Set focus back to text field
    }

    // Main method - entry point
    public static void main(String[] args) {
        // Use SwingUtilities for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleGUIApp();
            }
        });
    }
}
