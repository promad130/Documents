import javax.swing.*;

/**
 * Chapter 15: Main Game Window using JFrame
 * Creates the game window and contains the game panel
 */
public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    
    public GameWindow() {
        setTitle(GameConstants.GAME_TITLE);
        setSize(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen
        
        // Create and add game panel
        gamePanel = new GamePanel();
        add(gamePanel);
        
        setVisible(true);
        
        // Request focus for key events
        gamePanel.requestFocusInWindow();
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public static void main(String[] args) {
        // Run on Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            new GameWindow();
        });
    }
}
