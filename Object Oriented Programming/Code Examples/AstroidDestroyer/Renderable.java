import java.awt.Graphics;

/**
 * Abstract class for all game objects that can be drawn on screen.
 * Extends GameObject to inherit position and size properties.
 * All drawable objects must implement draw() and getDrawPriority().
 */
public abstract class Renderable extends GameObject {
    protected boolean visible;
    
    public Renderable(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.visible = true;
    }
    
    // Abstract methods - must be implemented by subclasses
    public abstract void draw(Graphics g);
    public abstract int getDrawPriority();
    
    // Concrete method
    public boolean isVisible() {
        return visible && isActive && x >= -width && x <= 800 + width 
            && y >= -height && y <= 600 + height;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}