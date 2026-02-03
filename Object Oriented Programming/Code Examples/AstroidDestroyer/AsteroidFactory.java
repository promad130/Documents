/**
 * Chapter 11: Static Factory Methods
 * Chapter 13: Nested Classes (AsteroidConfig)
 * Chapter 14: Package organization ready
 * Factory class for creating asteroids with various configurations
 * Implements advanced Java concepts: static methods, nested classes, interfaces
 */
public class AsteroidFactory {
    // Chapter 11: Static field for tracking
    private static int totalAsteroidsCreated = 0;
    
    // Chapter 11: Private constructor - only static methods should be used
    private AsteroidFactory() { }
    
    /**
     * Chapter 13: Static Nested Configuration Class
     * Holds configuration for asteroid creation
     */
    public static class AsteroidConfig {
        private final double speedMultiplier;
        private final boolean randomizeSpeed;
        private final boolean randomizeAngle;
        private final Double fixedAngle;
        
        private AsteroidConfig(Builder builder) {
            this.speedMultiplier = builder.speedMultiplier;
            this.randomizeSpeed = builder.randomizeSpeed;
            this.randomizeAngle = builder.randomizeAngle;
            this.fixedAngle = builder.fixedAngle;
        }
        
        public double getSpeedMultiplier() { return speedMultiplier; }
        public boolean shouldRandomizeSpeed() { return randomizeSpeed; }
        public boolean shouldRandomizeAngle() { return randomizeAngle; }
        public Double getFixedAngle() { return fixedAngle; }
        
        /**
         * Chapter 13: Static Nested Builder Pattern
         * Fluent interface for creating asteroid configurations
         */
        public static class Builder {
            private double speedMultiplier = 1.0;
            private boolean randomizeSpeed = true;
            private boolean randomizeAngle = true;
            private Double fixedAngle = null;
            
            public Builder speedMultiplier(double multiplier) {
                this.speedMultiplier = multiplier;
                return this;
            }
            
            public Builder randomizeSpeed(boolean randomize) {
                this.randomizeSpeed = randomize;
                return this;
            }
            
            public Builder fixedAngle(double angle) {
                this.fixedAngle = angle;
                this.randomizeAngle = false;
                return this;
            }
            
            public Builder randomizeAngle(boolean randomize) {
                this.randomizeAngle = randomize;
                return this;
            }
            
            public AsteroidConfig build() {
                return new AsteroidConfig(this);
            }
        }
    }
    
    /**
     * Chapter 10: Public access modifier for factory method
     * Create a large asteroid at specified position
     */
    public static Astroid createLargeAsteroid(double x, double y) {
        return createLargeAsteroid(x, y, new AsteroidConfig.Builder().build());
    }
    
    /**
     * Chapter 13: Overloaded method with configuration
     * Create a large asteroid with custom configuration
     */
    public static Astroid createLargeAsteroid(double x, double y, AsteroidConfig config) {
        totalAsteroidsCreated++;
        double angle = config.shouldRandomizeAngle() ? 
            Math.random() * 360 : (config.getFixedAngle() != null ? config.getFixedAngle() : 0.0);
        double speed = calculateSpeed(config);
        return new Astroid(x, y, angle, speed, GameConstants.ASTEROID_SIZE_LARGE);
    }
    
    /**
     * Chapter 10: Public access modifier for factory method
     * Create a medium asteroid at specified position
     */
    public static Astroid createMediumAsteroid(double x, double y) {
        return createMediumAsteroid(x, y, new AsteroidConfig.Builder()
            .speedMultiplier(1.2)
            .build());
    }
    
    /**
     * Chapter 13: Overloaded method with configuration
     * Create a medium asteroid with custom configuration
     */
    public static Astroid createMediumAsteroid(double x, double y, AsteroidConfig config) {
        totalAsteroidsCreated++;
        double angle = config.shouldRandomizeAngle() ? 
            Math.random() * 360 : (config.getFixedAngle() != null ? config.getFixedAngle() : 0.0);
        double speed = calculateSpeed(config) * 1.2; // Medium asteroids move slightly faster
        return new Astroid(x, y, angle, speed, GameConstants.ASTEROID_SIZE_MEDIUM);
    }
    
    /**
     * Chapter 10: Public access modifier for factory method
     * Create a small asteroid at specified position
     */
    public static Astroid createSmallAsteroid(double x, double y) {
        return createSmallAsteroid(x, y, new AsteroidConfig.Builder()
            .speedMultiplier(1.5)
            .build());
    }
    
    /**
     * Chapter 13: Overloaded method with configuration
     * Create a small asteroid with custom configuration
     */
    public static Astroid createSmallAsteroid(double x, double y, AsteroidConfig config) {
        totalAsteroidsCreated++;
        double angle = config.shouldRandomizeAngle() ? 
            Math.random() * 360 : (config.getFixedAngle() != null ? config.getFixedAngle() : 0.0);
        double speed = calculateSpeed(config) * 1.5; // Small asteroids move faster
        return new Astroid(x, y, angle, speed, GameConstants.ASTEROID_SIZE_SMALL);
    }
    
    /**
     * Chapter 10: Private access modifier for helper method
     * Calculate speed based on configuration
     */
    private static double calculateSpeed(AsteroidConfig config) {
        double baseSpeed;
        if (config.shouldRandomizeSpeed()) {
            baseSpeed = GameConstants.randomInRange(
                GameConstants.ASTEROID_MIN_SPEED,
                GameConstants.ASTEROID_MAX_SPEED
            );
        } else {
            baseSpeed = (GameConstants.ASTEROID_MIN_SPEED + GameConstants.ASTEROID_MAX_SPEED) / 2;
        }
        return baseSpeed * config.getSpeedMultiplier();
    }
    
    /**
     * Chapter 11: Static method for random asteroid creation
     * Create a random asteroid anywhere on screen
     */
    public static Astroid createRandomAsteroid() {
        double x = Math.random() * GameConstants.SCREEN_WIDTH;
        double y = Math.random() * GameConstants.SCREEN_HEIGHT;
        
        int[] sizes = {
            GameConstants.ASTEROID_SIZE_LARGE,
            GameConstants.ASTEROID_SIZE_MEDIUM,
            GameConstants.ASTEROID_SIZE_SMALL
        };
        int size = sizes[(int)(Math.random() * sizes.length)];
        
        return createAsteroidBySize(x, y, size);
    }
    
    /**
     * Chapter 11: Static method with safety logic
     * Create an asteroid at a safe distance from the player
     * Ensures asteroid doesn't spawn on top of player
     */
    public static Astroid createSafeAsteroid(double playerX, double playerY, double minDistance) {
        double x, y, distance;
        
        // Chapter 12: Using wrapper class Integer for max attempts
        Integer maxAttempts = 100;
        Integer attempts = 0;
        
        // Keep trying until we find a safe spawn point
        do {
            x = Math.random() * GameConstants.SCREEN_WIDTH;
            y = Math.random() * GameConstants.SCREEN_HEIGHT;
            distance = calculateDistance(x, y, playerX, playerY);
            attempts++;
            
            // Prevent infinite loop
            if (attempts >= maxAttempts) {
                break;
            }
        } while (distance < minDistance);
        
        return createLargeAsteroid(x, y);
    }
    
    /**
     * Chapter 10: Private helper method
     * Chapter 12: Using wrapper Double for calculations
     */
    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        Double dx = x2 - x1;
        Double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Chapter 10: Private access modifier for internal logic
     * Create an asteroid of specific size at position
     */
    private static Astroid createAsteroidBySize(double x, double y, int size) {
        switch (size) {
            case GameConstants.ASTEROID_SIZE_LARGE:
                return createLargeAsteroid(x, y);
            case GameConstants.ASTEROID_SIZE_MEDIUM:
                return createMediumAsteroid(x, y);
            case GameConstants.ASTEROID_SIZE_SMALL:
                return createSmallAsteroid(x, y);
            default:
                return createLargeAsteroid(x, y);
        }
    }
    
    /**
     * Chapter 13: Create asteroid wave using anonymous class for configuration
     * Returns an array of asteroids spawned in a wave pattern
     */
    public static Astroid[] createAsteroidWave(int count, double centerX, double centerY) {
        Astroid[] wave = new Astroid[count];
        double angleStep = 360.0 / count;
        
        for (int i = 0; i < count; i++) {
            final double angle = i * angleStep;
            
            // Chapter 13: Using nested configuration
            AsteroidConfig config = new AsteroidConfig.Builder()
                .fixedAngle(angle)
                .speedMultiplier(1.0)
                .build();
            
            wave[i] = createLargeAsteroid(centerX, centerY, config);
        }
        
        return wave;
    }
    
    /**
     * Chapter 12: Using wrapper classes for statistics
     * Get statistics about asteroid creation
     */
    public static class AsteroidStats {
        private final Integer totalCreated;
        private final Double averageSpeed;
        private final Long timestamp;
        
        private AsteroidStats(Integer total, Double avgSpeed) {
            this.totalCreated = total;
            this.averageSpeed = avgSpeed;
            this.timestamp = System.currentTimeMillis();
        }
        
        public Integer getTotalCreated() { return totalCreated; }
        public Double getAverageSpeed() { return averageSpeed; }
        public Long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("Total: %d, Avg Speed: %.2f, Time: %d", 
                totalCreated, averageSpeed, timestamp);
        }
    }
    
    /**
     * Chapter 12: Method returning wrapper-based statistics
     */
    public static AsteroidStats getStatistics() {
        Double avgSpeed = (GameConstants.ASTEROID_MIN_SPEED + GameConstants.ASTEROID_MAX_SPEED) / 2.0;
        return new AsteroidStats(totalAsteroidsCreated, avgSpeed);
    }
    
    /**
     * Chapter 11: Static getter method
     * Get total number of asteroids created (statistics)
     */
    public static int getTotalAsteroidsCreated() {
        return totalAsteroidsCreated;
    }
    
    /**
     * Chapter 11: Static method to reset state
     * Reset the counter (for new game)
     */
    public static void resetCounter() {
        totalAsteroidsCreated = 0;
    }
    
    /**
     * Chapter 12: Using Integer wrapper for validation
     * Increment counter with validation
     */
    public static void incrementCounter(Integer amount) {
        if (amount != null && amount > 0) {
            totalAsteroidsCreated += amount;
        }
    }
    
    /**
     * Chapter 14: Documentation for package usage
     * This factory is designed to be used across packages.
     * Import with: import com.asteroidgame.factories.AsteroidFactory;
     * 
     * Chapter 17: Integration notes
     * This factory integrates with:
     * - AsteroidManager for game object management
     * - GameConstants for configuration values
     * - Astroid class for object creation
     */
}
