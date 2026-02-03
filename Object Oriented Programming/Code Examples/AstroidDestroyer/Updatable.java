/**
 * Chapter 9: Interfaces - Updatable Interface
 * Interface for objects that need to be updated each frame
 */
public interface Updatable {
    /**
     * Update the object's state for the current frame
     * Called once per game loop iteration
     */
    void update();
}
