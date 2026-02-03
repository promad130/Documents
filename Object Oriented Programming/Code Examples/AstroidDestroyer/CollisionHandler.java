import java.util.ArrayList;

/**
 * Chapter 7 & 9: Polymorphic collision detection and handling
 * Enhanced to work with Collidable interface
 */
public class CollisionHandler
{
    /**
     * Check if two GameObjects collide using circular collision detection
     * Works best when objects implement Collidable interface
     */
    public boolean checkCollision(GameObject obj1, GameObject obj2) {
        if (!obj1.isActive() || !obj2.isActive()) {
            return false;
        }
        
        // Use Collidable radius if available, otherwise use width/2
        double radius1, radius2;
        
        if (obj1 instanceof Collidable) {
            radius1 = ((Collidable) obj1).getCollisionRadius();
        } else {
            radius1 = obj1.getWidth() / 2;
        }
        
        if (obj2 instanceof Collidable) {
            radius2 = ((Collidable) obj2).getCollisionRadius();
        } else {
            radius2 = obj2.getWidth() / 2;
        }
        
        double dx = obj1.getX() - obj2.getX();
        double dy = obj1.getY() - obj2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        return distance < radius1 + radius2;
    }
    
    /**
     * Handle collision between two objects using polymorphic dispatch
     * Calls onCollision() for Collidable objects
     */
    public void handleCollision(GameObject obj1, GameObject obj2) {
        // Call onCollision for Collidable objects
        if (obj1 instanceof Collidable) {
            ((Collidable) obj1).onCollision(obj2);
        }
        if (obj2 instanceof Collidable) {
            ((Collidable) obj2).onCollision(obj1);
        }
        
        // Additional specific collision handling
        // SpaceShip collides with Astroid
        if (obj1 instanceof SpaceShip && obj2 instanceof Astroid) {
            SpaceShip ship = (SpaceShip) obj1;
            Astroid asteroid = (Astroid) obj2;
            if (!ship.isInvulnerable()) {
                obj2.setActive(false); // Destroy asteroid on impact
            }
        }
        else if (obj2 instanceof SpaceShip && obj1 instanceof Astroid) {
            SpaceShip ship = (SpaceShip) obj2;
            Astroid asteroid = (Astroid) obj1;
            if (!ship.isInvulnerable()) {
                obj1.setActive(false); // Destroy asteroid on impact
            }
        }
        // Bullet collides with Astroid
        else if (obj1 instanceof Bullet && obj2 instanceof Astroid) {
            obj1.setActive(false);
            obj2.setActive(false);
            // Astroid splitting handled elsewhere
        }
        else if (obj2 instanceof Bullet && obj1 instanceof Astroid) {
            obj1.setActive(false);
            obj2.setActive(false);
        }
        // Astroid collides with Astroid (bounce)
        else if (obj1 instanceof Astroid && obj2 instanceof Astroid) {
            bounceAstroids((Astroid) obj1, (Astroid) obj2);
        }
    }
    
    private void bounceAstroids(Astroid ast1, Astroid ast2) {
        // Simple velocity swap
        MovingObject mo1 = (MovingObject) ast1;
        MovingObject mo2 = (MovingObject) ast2;
        
        double tempVX = mo1.velocityX;
        double tempVY = mo1.velocityY;
        
        mo1.setVelocity(mo2.velocityX, mo2.velocityY);
        mo2.setVelocity(tempVX, tempVY);
    }
    
    public void checkAllCollisions(GameObjectManager manager) {
        ArrayList<GameObject> objects = manager.getAllObjects();
        
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject obj1 = objects.get(i);
                GameObject obj2 = objects.get(j);
                
                if (checkCollision(obj1, obj2)) {
                    handleCollision(obj1, obj2);
                }
            }
        }
    }
}