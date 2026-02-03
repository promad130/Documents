/**
 * Game Constants - Chapter 11: Static & Final
 * Contains all game configuration values and utility methods.
 * This class cannot be instantiated (final with private constructor).
 */
public final class GameConstants {
    
    // Private constructor prevents instantiation
    private GameConstants() {
        throw new AssertionError("Cannot instantiate GameConstants");
    }
    
    // === WINDOW CONSTANTS ===
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final String GAME_TITLE = "ASTEROID DESTROYER";
    public static final int TARGET_FPS = 60;
    public static final long FRAME_TIME_MS = 1000 / TARGET_FPS;
    
    // === PLAYER CONSTANTS ===
    public static final double SPACESHIP_WIDTH = 20.0;
    public static final double SPACESHIP_HEIGHT = 20.0;
    public static final double PLAYER_ACCELERATION = 0.5;
    public static final double PLAYER_MAX_SPEED = 10.0;
    public static final double PLAYER_ROTATION_SPEED = 5.0;
    public static final double PLAYER_FRICTION = 0.98;
    public static final int INITIAL_PLAYER_LIVES = 3;
    public static final int PLAYER_INVULNERABILITY_FRAMES = 120; // 2 seconds at 60fps
    
    // === ASTEROID CONSTANTS ===
    public static final int ASTEROID_SIZE_LARGE = 5;
    public static final int ASTEROID_SIZE_MEDIUM = 3;
    public static final int ASTEROID_SIZE_SMALL = 2;
    public static final int ASTEROID_SIZE_TINY = 1;
    public static final int ASTEROID_POINTS_LARGE = 10;
    public static final int ASTEROID_POINTS_MEDIUM = 20;
    public static final int ASTEROID_POINTS_SMALL = 30;
    public static final int ASTEROID_POINTS_TINY = 50;
    public static final double ASTEROID_MIN_SPEED = 1.0;
    public static final double ASTEROID_MAX_SPEED = 3.0;
    public static final int INITIAL_ASTEROID_COUNT = 5;
    public static final int MAXIMUM_ASTROIDS = 20;
    
    // === BULLET CONSTANTS ===
    public static final double BULLET_SPEED = 10.0;
    public static final double BULLET_RADIUS = 3.0;
    public static final int BULLET_FIRE_COOLDOWN = 10; // frames
    
    // === SCORING CONSTANTS ===
    public static final int STARTING_SCORE = 0;
    public static final int EXTRA_LIFE_SCORE = 10000;
    public static final int PLAYER_SPEED = 5;
    
    // === UTILITY METHODS ===
    
    /**
     * Generate a random value within a range
     */
    public static double randomInRange(double min, double max) {
        return min + Math.random() * (max - min);
    }
    
    /**
     * Get point value for destroying an asteroid of given size
     */
    public static int getAsteroidPoints(int size) {
        switch (size) {
            case ASTEROID_SIZE_LARGE: return ASTEROID_POINTS_LARGE;
            case ASTEROID_SIZE_MEDIUM: return ASTEROID_POINTS_MEDIUM;
            case ASTEROID_SIZE_SMALL: return ASTEROID_POINTS_SMALL;
            case ASTEROID_SIZE_TINY: return ASTEROID_POINTS_TINY;
            default: return 0;
        }
    }
    
    /**
     * Check if coordinates are within screen bounds
     */
    public static boolean isInBounds(double x, double y) {
        return x >= 0 && x <= SCREEN_WIDTH && y >= 0 && y <= SCREEN_HEIGHT;
    }
    
    /**
     * Wrap X coordinate to opposite side if out of bounds
     */
    public static double wrapX(double x) {
        if (x < 0) return SCREEN_WIDTH;
        if (x > SCREEN_WIDTH) return 0;
        return x;
    }
    
    /**
     * Wrap Y coordinate to opposite side if out of bounds
     */
    public static double wrapY(double y) {
        if (y < 0) return SCREEN_HEIGHT;
        if (y > SCREEN_HEIGHT) return 0;
        return y;
    }
    
    /**
     * Clamp value between min and max
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
