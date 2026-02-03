import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * CORRECTED: Asteroid now extends MovingObject (which extends Renderable)
 * This eliminates code duplication for velocity management
 * Chapter 9: Now implements Collidable interface for collision detection
 */
public class Astroid extends MovingObject implements Collidable {
    private int size;
    private double radius;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public Astroid(double x, double y, double angle, double speed, int size) {
        super(x, y, size * 10, size * 10, angle, speed);
        this.size = size;
        this.radius = size * 5;
    }
    
    @Override
    public void update() {
        super.update();  // Use MovingObject's movement
        
        // Screen wrapping
        if (x < 0) x = WINDOW_WIDTH;
        if (x > WINDOW_WIDTH) x = 0;
        if (y < 0) y = WINDOW_HEIGHT;
        if (y > WINDOW_HEIGHT) y = 0;
    }
    
    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            g.setColor(Color.GRAY);
            // Draw circle for asteroid
            g.drawOval((int)(x - radius), (int)(y - radius), 
                       (int)(radius * 2), (int)(radius * 2));
        }
    }
    
    @Override
    public int getDrawPriority() {
        return 10; // Asteroids drawn after background but before UI
    }
    
    public int getSize() { return size; }
    public double getRadius() { return radius; }
    
    /**
     * Returns the point value for destroying this asteroid (Chapter 4 requirement)
     * Smaller asteroids are worth more points
     */
    public int getPointValue() {
        return GameConstants.getAsteroidPoints(size);
    }
    
    // === Collidable Interface Implementation (Chapter 9) ===
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - radius), (int)(y - radius),
                           (int)(radius * 2), (int)(radius * 2));
    }
    
    @Override
    public void onCollision(GameObject other) {
        // Asteroids don't react to collisions (handled by manager)
        // The collision system will handle splitting/destruction
    }
    
    @Override
    public double getCollisionRadius() {
        return radius;
    }
}