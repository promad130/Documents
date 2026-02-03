/**
 * Chapter 6: Base class for all game entities
 * Provides common properties: position, size, active state
 * Chapter 8: Made abstract - cannot be instantiated directly
 */
public abstract class GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected boolean isActive;
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isActive = true;
    }

    /**
     * Update method - to be overridden by subclasses
     * Made abstract to force implementation
     */
    public abstract void update();

    public double[] getBounds() {
        return new double[]{x, y, width, height};
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
}
