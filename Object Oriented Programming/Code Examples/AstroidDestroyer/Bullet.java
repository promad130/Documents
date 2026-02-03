import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * CORRECTED: Bullet now implements required Renderable methods
 * Chapter 9: Now implements Collidable interface for collision detection
 */
public class Bullet extends MovingObject implements Collidable {

    private static final int BULLET_SPEED = 10;
    private static final int RADIUS = 3;

    public Bullet(double x, double y, double angle) {
        super(x, y, RADIUS * 2, RADIUS * 2, angle, BULLET_SPEED);
    }
    
    @Override
    public void update() {
        super.update();
        // Deactivate if off screen
        if (isBulletOffScreen()) {
            setActive(false);
        }
    }
    
    // REQUIRED: Implement draw() from Renderable
    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            g.setColor(Color.YELLOW);
            g.fillOval(
                (int)(x - RADIUS), 
                (int)(y - RADIUS), 
                RADIUS * 2, 
                RADIUS * 2
            );
        }
    }
    
    // REQUIRED: Implement getDrawPriority() from Renderable
    @Override
    public int getDrawPriority() {
        return 11; // Bullets drawn after asteroids (10) but before player (15)
    }

    public boolean isBulletOffScreen() {
        //Screen Wrapping
        if (this.x < 0) {
            return true;
        } else if (this.x > GameConstants.SCREEN_WIDTH) {
            return true;
        } else if (this.y < 0) {
            return true;
        } else if (this.y > GameConstants.SCREEN_HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    public double getRadius()
    {
        return RADIUS;   
    }
    
    // === Collidable Interface Implementation (Chapter 9) ===
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - RADIUS), (int)(y - RADIUS),
                           (int)(RADIUS * 2), (int)(RADIUS * 2));
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Astroid) {
            setActive(false); // Bullet is destroyed on impact with asteroid
        }
    }
    
    @Override
    public double getCollisionRadius() {
        return RADIUS;
    }
}