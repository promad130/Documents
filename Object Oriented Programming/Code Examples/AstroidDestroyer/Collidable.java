import java.awt.Rectangle;

/**
 * Chapter 9: Interfaces - Collidable Interface
 * Interface for objects that can participate in collision detection
 */
public interface Collidable {
    /**
     * Returns the bounding rectangle for collision detection
     */
    Rectangle getCollisionBounds();
    
    /**
     * Called when this object collides with another GameObject
     */
    void onCollision(GameObject other);
    
    /**
     * Returns the radius used for circular collision detection
     */
    double getCollisionRadius();
}
