/**
 * Chapter 9: Interfaces - Damageable Interface
 * Interface for objects that can take damage and be destroyed
 */
public interface Damageable {
    /**
     * Apply damage to this object
     * @param amount The amount of damage to apply
     */
    void takeDamage(int amount);
    
    /**
     * Get current health
     * @return Current health value
     */
    int getHealth();
    
    /**
     * Check if the object is destroyed (health <= 0)
     * @return true if destroyed, false otherwise
     */
    boolean isDestroyed();
    
    /**
     * Get maximum health
     * @return Maximum health value
     */
    int getMaxHealth();
}
