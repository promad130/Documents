# Java Asteroid Destroyer: A Complete Revision Journey 

## Story Introduction
Welcome, Space Cadet! You've been recruited by the Galactic Defense Force to build the ultimate Asteroid Destroyer system. As you progress through your training (learning Java concepts), you'll piece together components of a fully functional retro-style asteroid game. By the end, you'll have a working game with graphics, collision detection, audio, and smooth gameplay!

---
## Chapter 1: Java Basics & Syntax - Setting Up Your Spaceship

### Theory: The Foundation
**What is Java?**
- Object-oriented, platform-independent programming language
- Runs on JVM (Java Virtual Machine)
- "Write Once, Run Anywhere" philosophy

**Basic Syntax Rules:**
```java
// Class names: PascalCase
public class SpaceShip { }

// Variables: camelCase
int playerScore = 0;

// Constants: UPPER_SNAKE_CASE
final double MAX_SPEED = 10.0;

// Every statement ends with semicolon
int x = 5;
```

**Primitive Data Types:**
- `int` - whole numbers (player score, asteroid count)
- `double` - decimal numbers (coordinates, velocity)
- `boolean` - true/false (isAlive, isColliding)
- `char` - single character
- `byte`, `short`, `long`, `float` - variations

### Practice Questions
**Question 1.1: Create the Game Constants**
Create a class `GameConstants` that stores all game configuration values.

**Requirements:**
- Window width: 800 pixels
- Window height: 600 pixels
- Initial player lives: 3
- Starting score: 0
- Maximum asteroids: 10
- Player speed: 5.0

```java
// YOUR CODE HERE
public class GameConstants {
    // Define all constants using appropriate data types
}
```

**Solution:**
```java
public class GameConstants {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int INITIAL_LIVES = 3;
    public static final int STARTING_SCORE = 0;
    public static final int MAX_ASTEROIDS = 10;
    public static final double PLAYER_SPEED = 5.0;
}
```

---
**Question 1.2: Player Position Variables**
Declare variables to track the spaceship's position and state.

**Requirements:**
- X and Y coordinates (decimals for smooth movement)
- Rotation angle (decimal)
- Alive status (boolean)
- Current score (whole number)

```java
public class SpaceShip {
    // YOUR CODE HERE: Declare appropriate variables
}
```

**Solution:**
```java
public class SpaceShip {
    double x;
    double y;
    double angle;
    boolean isAlive;
    int score;
}
```

---

**Question 1.3: Basic Input/Output - Game Title**
Create a simple program that displays the game title and instructions.

**Requirements:**
- Print game title: "ASTEROID DESTROYER"
- Print controls: "Arrow Keys to Move, Space to Shoot"
- Use escapescape sequences for formatting

```java
public class GameLauncher {
    public static void main(String[] args) {
        // YOUR CODE HERE
    }
}
```

**Solution:**
```java
public class GameLauncher {
    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("    ASTEROID DESTROYER");
        System.out.println("================================");
        System.out.println("\nControls:");
        System.out.println("\tArrow Keys - Move");
        System.out.println("\tSpace - Shoot");
        System.out.println("\nPress any key to start...\n");
    }
}
```

---

## Chapter 2: Loops & Conditionals - Game Logic Flow
### Theory: Control Flow

**Conditional Statements:**
```java
if (condition) {
    // code
} else if (anotherCondition) {
    // code
} else {
    // code
}

// Ternary operator
String status = (isAlive) ? "Active" : "Destroyed";
```

**Loops:**
```java
// For loop - known iterations
for (int i = 0; i < 10; i++) { }

// While loop - condition-based
while (gameRunning) { }

// Do-while - executes at least once
do { } while (condition);

// Enhanced for loop
for (Asteroid ast : asteroids) { }
```

**Switch Statements:**
```java
switch (asteroidSize) {
    case 5: // Large
        break;
    case 3: // Medium
        break;
    case 1: // Small
        break;
    default:
        break;
}
```

### Practice Questions

**Question 2.1: Game Loop Structure**
Create the main game loop that runs 60 times per second (60 FPS).
**Pre-Requisites:**
- Look into `System.currentTimeMillis()` for tracking time
- Look into `Thread.sleep()` for frame rate control

**Requirements:**
- Loop should run while game is active
- Calculate delta time between frames
- Target: 16.67ms per frame (60 FPS)
- Print frame count every 60 frames

```java
public class GameLoop {
    private boolean running = true;
    private int frameCount = 0;
    
    public void start() {
        // YOUR CODE HERE
    }
}
```

**Solution:**
```java
public class GameLoop {
    private boolean running = true;
    private int frameCount = 0;
    private static final double TARGET_TIME = 1000.0 / 60.0; // 16.67ms
    
    public void start() {
        while (running) {
            long startTime = System.currentTimeMillis();
            
            // Game update logic here
            update();
            render();
            
            frameCount++;
            if (frameCount % 60 == 0) {
                System.out.println("Frames: " + frameCount);
            }
            
            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = (long)(TARGET_TIME - elapsedTime);
            
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void update() { /* Game logic */ }
    private void render() { /* Drawing */ }
}
```


---

**Question 2.2: Asteroid Size Management**
Implement logic for asteroid breaking mechanics (5→3→1→0, 2→1→0, 3→1→0).

**Requirements:**
- Use switch statement
- Return number of smaller asteroids created
- Size 5 breaks into 2 size-3 asteroids
- Size 3 breaks into 2 size-1 asteroids
- Size 2 breaks into 2 size-1 asteroids
- Size 1 is destroyed completely

```java
public class AsteroidBreaker {
    public static int breakAsteroid(int size) {
        // YOUR CODE HERE
    }
    
    public static int getNewSize(int currentSize) {
        // YOUR CODE HERE
    }
}
```

**Solution:**
```java
public class AsteroidBreaker {
    public static int breakAsteroid(int size) {
        switch (size) {
            case 5:
            case 3:
            case 2:
                return 2; // Creates 2 smaller asteroids
            case 1:
                return 0; // Destroyed completely
            default:
                return 0;
        }
    }
    
    public static int getNewSize(int currentSize) {
        switch (currentSize) {
            case 5:
                return 3;
            case 3:
            case 2:
                return 1;
            case 1:
                return 0;
            default:
                return 0;
        }
    }
}
```

---

**Question 2.3: Collision Detection Loop**
Check if the player collides with any asteroid in the game.

**Requirements:**
- Use enhanced for loop
- Check distance between player and each asteroid
- If distance < (playerRadius + asteroidRadius), collision detected
- Return true on first collision found

```java
public class CollisionDetector {
    public static boolean checkPlayerCollision(
        double playerX, double playerY, double playerRadius,
        double[][] asteroids // [x, y, radius]
    ) {
        // YOUR CODE HERE
    }
}
```

**Solution:**
```java
public class CollisionDetector {

	public static boolean checkPlayerCollision(
			double playerX, 
			double playerY, 
			double playerRadius,
			double[][] asteroids // [x, y, radius]
		) 
	{
		for (double[] astroid : asteroids) 
		{
			double distanceBetweenPlayerAndAstroid = Math.sqrt( Math.pow((playerX - asteroid[0]), 2) + Math.pow((playerY - asteroid[1]), 2));
			if(distanceBetweenPlayerAndAstroid <= playerRadius + asteroid[2])
				return true;
		}
		return false;
	}
}
```

---
## Chapter 3: Methods & Math Class - Movement & Physics
### Theory: Methods and Math
**Method Syntax:**
```java
accessModifier returnType methodName(parameters) {
    // code
    return value;
}
```

**Math Class (java.lang.Math):**
```java
Math.sqrt(x)           // Square root
Math.pow(base, exp)    // Power
Math.abs(x)            // Absolute value
Math.sin(angle)        // Sine (radians)
Math.cos(angle)        // Cosine (radians)
Math.toRadians(deg)    // Degrees to radians
Math.toDegrees(rad)    // Radians to degrees
Math.random()          // Random [0.0, 1.0)
Math.PI                // Pi constant (3.14159...)
```

### Practice Questions

**Question 3.1: Movement Calculation**
Calculate new position based on angle and speed.

**Requirements:**
- Method: `calculateNewX(currentX, angle, speed)`
- Method: `calculateNewY(currentY, angle, speed)`
- Use Math.cos and Math.sin
- Angle is in degrees, convert to radians

```java
public class MovementCalculator {
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class MovementCalculator {
    public static double calculateNewX(double currentX, double angle, double speed) {
        double radians = Math.toRadians(angle);
        return currentX + Math.cos(radians) * speed;
    }
    
    public static double calculateNewY(double currentY, double angle, double speed) {
        double radians = Math.toRadians(angle);
        return currentY + Math.sin(radians) * speed;
    }
}
```

---
**Question 3.2: Distance Calculation**
Calculate distance between two points (for collision detection).

**Requirements:**
- Method: `calculateDistance(x1, y1, x2, y2)`
- Use Pythagorean theorem
- Return the distance as a double

```java
public class PhysicsUtils {
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class PhysicsUtils {
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
```

---
**Question 3.3: Random Asteroid Generation**
Create methods to generate random asteroid properties.

**Requirements:**
- `randomX()` - random X between 0 and window width
- `randomY()` - random Y between 0 and window height
- `randomAngle()` - random angle between 0 and 360
- `randomSpeed()` - random speed between 1.0 and 3.0
- `randomSize()` - random size from {5, 3, 2}

```java
public class AsteroidGenerator {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class AsteroidGenerator {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public static double randomX() {
        return Math.random() * WINDOW_WIDTH;
    }
    
    public static double randomY() {
        return Math.random() * WINDOW_HEIGHT;
    }
    
    public static double randomAngle() {
        return Math.random() * 360;
    }
    
    public static double randomSpeed() {
        return 1.0 + Math.random() * 2.0; // 1.0 to 3.0
    }
    
    public static int randomSize() {
        int[] sizes = {5, 3, 2};
        int index = (int) (Math.random() * sizes.length);
        return sizes[index];
    }
}
```

---
## Chapter 4: Encapsulation - Building Game Objects

### Theory: Encapsulation
**What is Encapsulation?**
- Bundling data (variables) and methods that operate on data into a single unit (class)
- Hiding internal state using private access modifier
- Providing public getters/setters for controlled access

**Why Encapsulation?**
- Data protection
- Flexibility to change implementation
- Better control over data validation

```java
public class Example {
    private int value; // Private - hidden from outside
    
    // Getter
    public int getValue() {
        return value;
    }
    
    // Setter with validation
    public void setValue(int value) {
        if (value >= 0) {
            this.value = value;
        }
    }
}
```

### Practice Questions

**Question 4.1: Encapsulated Spaceship Class**
Create a fully encapsulated SpaceShip class.

**Requirements:**
- Private fields: x, y, angle, lives, score
- Constructor to initialize position at center
- Getters for all fields
- Setters with validation (lives can't be negative, coordinates within bounds)
- Method: `takeDamage()` - decreases lives by 1
- Method: `addScore(int points)` - increases score

```java
public class SpaceShip {
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class SpaceShip {

	private double x;
	private double y;
	private double angle;
	private int lives;
	private int score;
	
	//Player properties Getters
	public double getX() { return x; }
	public double getY() { return y; }
	public double getAngle() { return angle; }
	public int getCurrentLives() { return lives; }
	public int getCurrentScore() { return score; }
	
	//Player properties Setters
	public void setX(double x) {
		if (x >= 0 && x <= GameConstants.WINDOW_WIDTH) { this.x = x; }
	}
	
	public void setY(double y) {
		if (y >= 0 && y <= GameConstants.WINDOW_HEIGHT) { this.y = y; }
	}
	
	public void setAngle(double angle) { this.angle = angle % 360; }
	
	// Game methods
	public void takeDamage() {
		if (lives > 0) {
			lives--;
		}
	}
	
	public void addScore(int points) {
		if (points > 0) {
			score += points;
		}
	}
	
	public boolean isAlive() {
		return lives > 0;
	}
}
```

---
**Question 4.2: Encapsulated Asteroid Class**
Create an encapsulated Asteroid class with all properties.

**Requirements:**
- Private fields: x, y, angle, speed, size, radius
- Constructor with parameters
- All getters
- Method: `update()` - moves asteroid based on angle and speed
- Method: `getPointValue()` - returns score value (size 5=10pts, 3=20pts, 2=30pts, 1=50pts)
- Screen wrapping: if x or y goes off screen, wrap to opposite side

```java
public class Asteroid {
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class Asteroid {
    private double x;
    private double y;
    private double angle;
    private double speed;
    private int size;
    private double radius;
    
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public Asteroid(double x, double y, double angle, double speed, int size) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = speed;
        this.size = size;
        this.radius = size * 5; // Radius based on size
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAngle() { return angle; }
    public double getSpeed() { return speed; }
    public int getSize() { return size; }
    public double getRadius() { return radius; }
    
    public void update() {
        // Move asteroid
        double radians = Math.toRadians(angle);
        x += Math.cos(radians) * speed;
        y += Math.sin(radians) * speed;
        
        // Screen wrapping
        if (x < 0) x = WINDOW_WIDTH;
        if (x > WINDOW_WIDTH) x = 0;
        if (y < 0) y = WINDOW_HEIGHT;
        if (y > WINDOW_HEIGHT) y = 0;
    }
    
    public int getPointValue() {
        switch (size) {
            case 5: return 10;
            case 3: return 20;
            case 2: return 30;
            case 1: return 50;
            default: return 0;
        }
    }
}
```

---
**Question 4.3: Encapsulated Bullet Class**
Create a Bullet class for player shooting.

**Requirements:**
- Private fields: x, y, angle, speed (constant 10.0)
- Constructor taking starting position and angle
- Method: `update()` - moves bullet
- Method: `isOffScreen()` - checks if bullet left the screen
- Bullets don't wrap, they disappear

```java
public class Bullet {
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class Bullet {
    private double x;
    private double y;
    private double angle;
    private static final double SPEED = 10.0;
    private static final double RADIUS = 3.0;
    
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadius() { return RADIUS; }
    
    public void update() {
        double radians = Math.toRadians(angle);
        x += Math.cos(radians) * SPEED;
        y += Math.sin(radians) * SPEED;
    }
    
    public boolean isOffScreen() {
        return x < 0 || x > WINDOW_WIDTH || y < 0 || y > WINDOW_HEIGHT;
    }
}
```


---
## Chapter 5: Arrays & Data Structures - Managing Game Objects

### Theory: Arrays and Collections
**Arrays:**
```java
// Declaration and initialization
int[] scores = new int[10];
String[] names = {"Player1", "Player2"};

// Length property
int length = scores.length;

// Access elements
scores[0] = 100;
```

**ArrayList (java.util.ArrayList):**
```java
import java.util.ArrayList;

ArrayList<Asteroid> asteroids = new ArrayList<>();
asteroids.add(new Asteroid(...));        // Add
asteroids.remove(0);                     // Remove by index
asteroids.get(0);                        // Get element
asteroids.size();                        // Size
asteroids.clear();                       // Clear all
```

**HashMap (java.util.HashMap):**
```java
import java.util.HashMap;

HashMap<String, Integer> scores = new HashMap<>();
scores.put("Player1", 100);              // Add/Update
scores.get("Player1");                   // Get value
scores.containsKey("Player1");           // Check key exists
scores.remove("Player1");                // Remove
```

### Practice Questions

**Question 5.1: High Score System**
Create a high score tracking system using HashMap.

**Requirements:**
- Store player names and their scores
- Method: `addScore(name, score)` - adds/updates score only if higher than existing
- Method: `getTopScores(n)` - returns array of top n scores
- Method: `getPlayerRank(name)` - returns player's rank

```java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreManager {
    // YOUR CODE HERE
}
```

**Solution:**
```java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreManager {
    private HashMap<String, Integer> scores;
    
    public HighScoreManager() {
        scores = new HashMap<>();
    }
    
    public void addScore(String name, int score) {
        if (!scores.containsKey(name) || scores.get(name) < score) {
            scores.put(name, score);
        }
    }
    
    public int[] getTopScores(int n) {
        ArrayList<Integer> scoreList = new ArrayList<>(scores.values());
        Collections.sort(scoreList, Collections.reverseOrder());
        
        int size = Math.min(n, scoreList.size());
        int[] topScores = new int[size];
        
        for (int i = 0; i < size; i++) {
            topScores[i] = scoreList.get(i);
        }
        
        return topScores;
    }
    
    public int getPlayerRank(String name) {
        if (!scores.containsKey(name)) {
            return -1;
        }
        
        int playerScore = scores.get(name);
        int rank = 1;
        
        for (int score : scores.values()) {
            if (score > playerScore) {
                rank++;
            }
        }
        
        return rank;
    }
    
    public int getScore(String name) {
        return scores.getOrDefault(name, 0);
    }
}
```

---
**Question 5.2: Asteroid Manager with ArrayList**
Create a manager class to handle multiple asteroids.

**Requirements:**
- Use `ArrayList<Asteroid>`
- Method: `addAsteroid(asteroid)` - adds to list
- Method: `updateAll()` - updates all asteroids
- Method: `removeAsteroid(index)` - removes asteroid
- Method: `splitAsteroid(index)` - splits asteroid into smaller ones
- Method: `checkCollisions(x, y, radius)` - returns list of colliding asteroids

```java
import java.util.ArrayList;

public class AsteroidManager {
    // YOUR CODE HERE
}
```

**Solution:**
```java
import java.util.ArrayList;

public class AsteroidManager {
    private ArrayList<Asteroid> asteroids;
    
    public AsteroidManager() {
        asteroids = new ArrayList<>();
    }
    
    public void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }
    
    public void updateAll() {
        for (Asteroid asteroid : asteroids) {
            asteroid.update();
        }
    }
    
    public void removeAsteroid(int index) {
        if (index >= 0 && index < asteroids.size()) {
            asteroids.remove(index);
        }
    }
    
    public void splitAsteroid(int index) {
        if (index < 0 || index >= asteroids.size()) {
            return;
        }
        
        Asteroid original = asteroids.get(index);
        int newSize = AsteroidBreaker.getNewSize(original.getSize());
        int count = AsteroidBreaker.breakAsteroid(original.getSize());
        
        asteroids.remove(index);
        
        for (int i = 0; i < count; i++) {
            double newAngle = Math.random() * 360;
            Asteroid newAsteroid = new Asteroid(
                original.getX(),
                original.getY(),
                newAngle,
                original.getSpeed() * 1.2,
                newSize
            );
            asteroids.add(newAsteroid);
        }
    }
    
    public ArrayList<Integer> checkCollisions(double x, double y, double radius) {
        ArrayList<Integer> collisions = new ArrayList<>();
        
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid ast = asteroids.get(i);
            double distance = PhysicsUtils.calculateDistance(x, y, ast.getX(), ast.getY());
            
            if (distance < radius + ast.getRadius()) {
                collisions.add(i);
            }
        }
        
        return collisions;
    }
    
    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }
    
    public int getCount() {
        return asteroids.size();
    }
}
```


---
**Question 5.3: Bullet Manager**
Create a manager for player bullets.

**Requirements:**
- `ArrayList<Bullet>` for active bullets
- Method: `fireBullet(x, y, angle)` - creates and adds new bullet
- Method: `updateAll()` - updates all bullets and removes off-screen ones
- Method: `checkAsteroidHits(asteroidManager)` - checks all bullets against asteroids, returns hits
- Method: `clear()` - removes all bullets

```java
import java.util.ArrayList;

public class BulletManager {
    // YOUR CODE HERE
}
```

Solution:
```java
import java.util.ArrayList;

public class BulletManager {
    private ArrayList<Bullet> bullets;
    
    public BulletManager() {
        bullets = new ArrayList<>();
    }
    
    public void fireBullet(double x, double y, double angle) {
        bullets.add(new Bullet(x, y, angle));
    }
    
    public void updateAll() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            
            if (bullet.isOffScreen()) {
                bullets.remove(i);
            }
        }
    }
    
    public void checkAsteroidHits(AsteroidManager asteroidManager) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            ArrayList<Integer> hits = asteroidManager.checkCollisions(
                bullet.getX(), bullet.getY(), bullet.getRadius()
            );
            
            if (!hits.isEmpty()) {
                // Remove bullet
                bullets.remove(i);
                
                // Split first hit asteroid
                asteroidManager.splitAsteroid(hits.get(0));
            }
        }
    }
    
    public void clear() {
        bullets.clear();
    }
    
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
```
</details>

---

## Chapter 6: Inheritance - Creating a Game Object Hierarchy

### 📚 Theory: Inheritance

**What is Inheritance?**
- Mechanism where one class acquires properties and methods of another class
- Promotes code reusability
- Creates "is-a" relationship

**Syntax:**
```java
public class Child extends Parent {
    // Child inherits all public/protected members of Parent
}
```

**Super Keyword:**
```java
super.methodName();  // Call parent method
super(args);         // Call parent constructor
```

**Method Overriding:**
```java
@Override
public void method() {
    // New implementation
}
```

### 💻 Practice Questions

**Question 6.1: GameObject Base Class**
Create a base GameObject class that all game entities inherit from.

**Requirements:**
- Protected fields: x, y, width, height, active
- Constructor taking x, y, width, height
- Method: `update()` (to be overridden)
- Method: `getBounds()` - returns array [x, y, width, height]
- Method: `isActive()` / `setActive(boolean)`

```java
public class GameObject {
    // YOUR CODE HERE
}
```

```java
public class GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected boolean active;
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
    }
    
    public void update() {
        // To be overridden by subclasses
    }
    
    public double[] getBounds() {
        return new double[]{x, y, width, height};
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
```

---
**Question 6.2: Refactor SpaceShip to Extend GameObject**
Refactor the SpaceShip class to inherit from GameObject.

	**Requirements:**
- Extend GameObject
- Add specific fields: angle, velocity, lives, score
- Call super constructor
- Override `update()` method to handle movement and rotation
- Add method: `rotate(double degrees)`
- Add method: `accelerate(double amount)`

```java
public class SpaceShip extends GameObject {
    // YOUR CODE HERE
}
```

```java
public class SpaceShip extends GameObject {
    private double angle;
    private double velocityX;
    private double velocityY;
    private int lives;
    private int score;
    private static final double FRICTION = 0.98;
    private static final double MAX_SPEED = 8.0;
    
    public SpaceShip(double x, double y) {
        super(x, y, 20, 20); // width and height for spaceship
        this.angle = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.lives = 3;
        this.score = 0;
    }
    
    @Override
    public void update() {
        // Apply velocity
        x += velocityX;
        y += velocityY;
        
        // Apply friction
        velocityX *= FRICTION;
        velocityY *= FRICTION;
        
        // Screen wrapping
        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }
    
    public void rotate(double degrees) {
        angle += degrees;
        angle %= 360;
    }
    
    public void accelerate(double amount) {
        double radians = Math.toRadians(angle);
        velocityX += Math.cos(radians) * amount;
        velocityY += Math.sin(radians) * amount;
        
        // Limit speed
        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (speed > MAX_SPEED) {
            velocityX = (velocityX / speed) * MAX_SPEED;
            velocityY = (velocityY / speed) * MAX_SPEED;
        }
    }
    
    public double getAngle() { return angle; }
    public int getLives() { return lives; }
    public int getScore() { return score; }
    
    public void takeDamage() {
        lives--;
        if (lives < 0) lives = 0;
    }
    
    public void addScore(int points) {
        score += points;
    }
}
```

---
**Question 6.3: MovingObject Base Class**
Create a MovingObject class that extends GameObject and add movement properties.

**Requirements:**
- Extend GameObject
- Add fields: velocityX, velocityY, angle, speed
- Constructor taking x, y, width, height, angle, speed
- Override `update()` to move object based on angle and speed
- Method: `setVelocity(vx, vy)`
- Refactor Asteroid and Bullet to extend MovingObject

```java
public class MovingObject extends GameObject {
    // YOUR CODE HERE
}

// Refactored Asteroid
public class Asteroid extends MovingObject {
    // YOUR CODE HERE
}

// Refactored Bullet
public class Bullet extends MovingObject {
    // YOUR CODE HERE
}
```

Solution:
```java
public class MovingObject extends GameObject {
    protected double velocityX;
    protected double velocityY;
    protected double angle;
    protected double speed;
    
    public MovingObject(double x, double y, double width, double height, double angle, double speed) {
        super(x, y, width, height);
        this.angle = angle;
        this.speed = speed;
        
        // Calculate velocity from angle and speed
        double radians = Math.toRadians(angle);
        this.velocityX = Math.cos(radians) * speed;
        this.velocityY = Math.sin(radians) * speed;
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
    }
    
    public void setVelocity(double vx, double vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }
    
    public double getAngle() { return angle; }
    public double getSpeed() { return speed; }
}

// Refactored Asteroid
public class Asteroid extends MovingObject {
    private int size;
    private double radius;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public Asteroid(double x, double y, double angle, double speed, int size) {
        super(x, y, size * 10, size * 10, angle, speed);
        this.size = size;
        this.radius = size * 5;
    }
    
    @Override
    public void update() {
        super.update();
        
        // Screen wrapping
        if (x < 0) x = WINDOW_WIDTH;
        if (x > WINDOW_WIDTH) x = 0;
        if (y < 0) y = WINDOW_HEIGHT;
        if (y > WINDOW_HEIGHT) y = 0;
    }
    
    public int getSize() { return size; }
    public double getRadius() { return radius; }
    
    public int getPointValue() {
        switch (size) {
            case 5: return 10;
            case 3: return 20;
            case 2: return 30;
            case 1: return 50;
            default: return 0;
        }
    }
}

// Refactored Bullet
public class Bullet extends MovingObject {
    private static final double BULLET_SPEED = 10.0;
    private static final double BULLET_RADIUS = 3.0;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public Bullet(double x, double y, double angle) {
        super(x, y, 6, 6, angle, BULLET_SPEED);
    }
    
    public boolean isOffScreen() {
        return x < 0 || x > WINDOW_WIDTH || y < 0 || y > WINDOW_HEIGHT;
    }
    
    public double getRadius() {
        return BULLET_RADIUS;
    }
}
```

---
## Chapter 7: Polymorphism - Flexible Game Object Handling

### Theory: Polymorphism
**What is Polymorphism?**
- "Many forms" - ability of objects to take multiple forms
- Same method name, different implementations
- Achieved through method overriding and interfaces

**Types:**
1. **Compile-time (Method Overloading):**
```java
void draw(int x, int y) { }
void draw(int x, int y, int size) { }
```

2. **Runtime (Method Overriding):**
```java
GameObject obj = new Asteroid(...);
obj.update(); // Calls Asteroid's update()
```

### Practice Questions
**Question 7.1: Polymorphic GameObject Collection**
Create a unified manager that handles all game objects polymorphically.

**Requirements:**
- `ArrayList<GameObject>` to store all objects
- Method: `addObject(GameObject obj)`
- Method: `updateAll()` - updates all objects
- Method: `removeInactive()` - removes inactive objects
- Method: `getObjectsByType(Class<?> type)` - returns objects of specific type

```java
import java.util.ArrayList;

public class GameObjectManager {
    // YOUR CODE HERE
}
```

**Solution:**
```java
import java.util.ArrayList;

public class GameObjectManager {
    private ArrayList<GameObject> objects;
    
    public GameObjectManager() {
        objects = new ArrayList<>();
    }
    
    public void addObject(GameObject obj) {
        objects.add(obj);
    }
    
    public void updateAll() {
        for (GameObject obj : objects) {
            if (obj.isActive()) {
                obj.update();
            }
        }
    }
    
    public void removeInactive() {
        for (int i = objects.size() - 1; i >= 0; i--) {
            if (!objects.get(i).isActive()) {
                objects.remove(i);
            }
        }
    }
    
    public ArrayList<GameObject> getObjectsByType(Class<?> type) {
        ArrayList<GameObject> result = new ArrayList<>();
        for (GameObject obj : objects) {
            if (type.isInstance(obj)) {
                result.add(obj);
            }
        }
        return result;
    }
    
    public ArrayList<GameObject> getAllObjects() {
        return objects;
    }
    
    public int getCount() {
        return objects.size();
    }
}
```

---
**Question 7.2: Collision Handler with Polymorphism**
Create a collision system that handles different object types polymorphically.

**Requirements:**
- Method: `checkCollision(GameObject obj1, GameObject obj2)` - returns boolean
- Method: `handleCollision(GameObject obj1, GameObject obj2)` - handles specific collision logic
- Use instanceof to determine specific actions
- SpaceShip + Asteroid = damage
- Bullet + Asteroid = destroy both
- Asteroid + Asteroid = bounce

```java
public class CollisionHandler {
    // YOUR CODE HERE
}
```

**Solution:**
```java
public class CollisionHandler {
    
    public boolean checkCollision(GameObject obj1, GameObject obj2) {
        if (!obj1.isActive() || !obj2.isActive()) {
            return false;
        }
        
        double dx = obj1.getX() - obj2.getX();
        double dy = obj1.getY() - obj2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Simple radius-based collision
        double radius1 = obj1.getWidth() / 2;
        double radius2 = obj2.getWidth() / 2;
        
        return distance < radius1 + radius2;
    }
    
    public void handleCollision(GameObject obj1, GameObject obj2) {
        // SpaceShip collides with Asteroid
        if (obj1 instanceof SpaceShip && obj2 instanceof Asteroid) {
            SpaceShip ship = (SpaceShip) obj1;
            ship.takeDamage();
            obj2.setActive(false);
        }
        else if (obj2 instanceof SpaceShip && obj1 instanceof Asteroid) {
            SpaceShip ship = (SpaceShip) obj2;
            ship.takeDamage();
            obj1.setActive(false);
        }
        // Bullet collides with Asteroid
        else if (obj1 instanceof Bullet && obj2 instanceof Asteroid) {
            obj1.setActive(false);
            obj2.setActive(false);
            // Asteroid splitting handled elsewhere
        }
        else if (obj2 instanceof Bullet && obj1 instanceof Asteroid) {
            obj1.setActive(false);
            obj2.setActive(false);
        }
        // Asteroid collides with Asteroid (bounce)
        else if (obj1 instanceof Asteroid && obj2 instanceof Asteroid) {
            bounceAsteroids((Asteroid) obj1, (Asteroid) obj2);
        }
    }
    
    private void bounceAsteroids(Asteroid ast1, Asteroid ast2) {
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
```


---

**Question 7.3: Method Overloading for Drawing**
Create a Renderer class with overloaded draw methods.

**Requirements:**
- `draw(SpaceShip ship)` - draws triangle
- `draw(Asteroid asteroid)` - draws circle with size
- `draw(Bullet bullet)` - draws small circle
- `draw(String text, int x, int y)` - draws text
- All methods print what they're drawing (actual graphics later)

```java
public class Renderer {
    // YOUR CODE HERE
}
```

**Solution**

```java
public class Renderer {
    
    public void draw(SpaceShip ship) {
        System.out.println("Drawing SpaceShip: Triangle at (" + 
            ship.getX() + ", " + ship.getY() + ") angle=" + ship.getAngle());
    }
    
    public void draw(Asteroid asteroid) {
        System.out.println("Drawing Asteroid: Circle at (" + 
            asteroid.getX() + ", " + asteroid.getY() + ") size=" + asteroid.getSize());
    }
    
    public void draw(Bullet bullet) {
        System.out.println("Drawing Bullet: Small circle at (" + 
            bullet.getX() + ", " + bullet.getY() + ")");
    }
    
    public void draw(String text, int x, int y) {
        System.out.println("Drawing Text: '" + text + "' at (" + x + ", " + y + ")");
    }
    
    // Polymorphic draw for any GameObject
    public void draw(GameObject obj) {
        if (obj instanceof SpaceShip) {
            draw((SpaceShip) obj);
        } else if (obj instanceof Asteroid) {
            draw((Asteroid) obj);
        } else if (obj instanceof Bullet) {
            draw((Bullet) obj);
        } else {
            System.out.println("Drawing GameObject at (" + obj.getX() + ", " + obj.getY() + ")");
        }
    }
    
    public void drawAll(GameObjectManager manager) {
        for (GameObject obj : manager.getAllObjects()) {
            draw(obj);
        }
    }
}
```


---

## Chapter 8: Abstract Classes - Defining Game Object Contracts

### 📚 Theory: Abstract Classes

**What are Abstract Classes?**
- Classes that cannot be instantiated directly
- Can have abstract methods (no implementation) and concrete methods
- Used to define common behavior while forcing subclasses to implement specific methods

**Syntax:**
```java
public abstract class AbstractClass {
    // Abstract method - no body
    public abstract void mustImplement();
    
    // Concrete method - has body
    public void canUse() {
        // implementation
    }
}
```

**Rules:**
- Can't create objects of abstract class
- Subclass must implement all abstract methods or be abstract itself
- Can have constructors, fields, concrete methods

### 💻 Practice Questions

**Question 8.1: Abstract Renderable Class**
Create an abstract Renderable class that all drawable objects must extend.

**Requirements:**
- Abstract methods: `draw(Graphics g)`, `getDrawPriority()`
- Concrete method: `isVisible()` (checks if on screen)
- Field: `visible` (boolean)
- Draw priority determines render order (lower = drawn first)

```java
import java.awt.Graphics;

public abstract class Renderable {
    // YOUR CODE HERE
}

// Example: Make Asteroid extend Renderable
public class Asteroid extends MovingObject {
    // YOUR CODE HERE - implement abstract methods
}
```

**Solution:**

```java
import java.awt.Graphics;

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
        return visible && active && x >= -width && x <= 800 + width 
            && y >= -height && y <= 600 + height;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

// Asteroid now extends Renderable
public class Asteroid extends Renderable {
    private int size;
    private double radius;
    private double velocityX, velocityY;
    private double angle, speed;
    
    public Asteroid(double x, double y, double angle, double speed, int size) {
        super(x, y, size * 10, size * 10);
        this.size = size;
        this.radius = size * 5;
        this.angle = angle;
        this.speed = speed;
        
        double radians = Math.toRadians(angle);
        this.velocityX = Math.cos(radians) * speed;
        this.velocityY = Math.sin(radians) * speed;
    }
    
    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            // Draw circle for asteroid
            g.drawOval((int)(x - radius), (int)(y - radius), 
                       (int)(radius * 2), (int)(radius * 2));
        }
    }
    
    @Override
    public int getDrawPriority() {
        return 10; // Asteroids drawn after background but before UI
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        
        // Screen wrapping
        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }
    
    public int getSize() { return size; }
    public double getRadius() { return radius; }
}
```

---
**Question 8.2: Abstract PowerUp Class**
Create an abstract PowerUp class for game power-ups.

**Requirements:**
- Abstract methods: `apply(SpaceShip ship)`, `getType()`
- Fields: x, y, duration, active
- Concrete method: `update()` - moves down slowly
- Concrete method: `checkPickup(SpaceShip ship)` - checks collision
- Create 3 concrete powerups: ShieldPowerUp, RapidFirePowerUp, ExtraLifePowerUp

```java
public abstract class PowerUp {
    // YOUR CODE HERE
}

// Implement these three concrete power-ups
public class ShieldPowerUp extends PowerUp {
    // YOUR CODE HERE
}

public class RapidFirePowerUp extends PowerUp {
    // YOUR CODE HERE
}

public class ExtraLifePowerUp extends PowerUp {
    // YOUR CODE HERE
}
```

💡 Solution

```java
public abstract class PowerUp {
    protected double x;
    protected double y;
    protected int duration; // in seconds
    protected boolean active;
    protected static final double FALL_SPEED = 2.0;
    protected static final double RADIUS = 15.0;
    
    public PowerUp(double x, double y, int duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.active = true;
    }
    
    // Abstract methods
    public abstract void apply(SpaceShip ship);
    public abstract String getType();
    
    // Concrete methods
    public void update() {
        y += FALL_SPEED;
        
        // Deactivate if off screen
        if (y > 600) {
            active = false;
        }
    }
    
    public boolean checkPickup(SpaceShip ship) {
        if (!active) return false;
        
        double dx = x - ship.getX();
        double dy = y - ship.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance < RADIUS + ship.getWidth() / 2) {
            apply(ship);
            active = false;
            return true;
        }
        return false;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }
}

public class ShieldPowerUp extends PowerUp {
    
    public ShieldPowerUp(double x, double y) {
        super(x, y, 10); // 10 second shield
    }
    
    @Override
    public void apply(SpaceShip ship) {
        // Enable shield on ship
        System.out.println("Shield activated for " + duration + " seconds!");
        // In actual implementation, ship would have setShield(duration) method
    }
    
    @Override
    public String getType() {
        return "SHIELD";
    }
}

public class RapidFirePowerUp extends PowerUp {
    
    public RapidFirePowerUp(double x, double y) {
        super(x, y, 8); // 8 second rapid fire
    }
    
    @Override
    public void apply(SpaceShip ship) {
        System.out.println("Rapid Fire activated for " + duration + " seconds!");
        // ship.setRapidFire(duration);
    }
    
    @Override
    public String getType() {
        return "RAPID_FIRE";
    }
}

public class ExtraLifePowerUp extends PowerUp {
    
    public ExtraLifePowerUp(double x, double y) {
        super(x, y, 0); // Instant effect
    }
    
    @Override
    public void apply(SpaceShip ship) {
        System.out.println("Extra life gained!");
        // Assuming SpaceShip has addLife() method
    }
    
    @Override
    public String getType() {
        return "EXTRA_LIFE";
    }
}
```

</details>

---

**Question 8.3: Abstract GameState**
Create an abstract GameState class for different game screens.

**Requirements:**
- Abstract methods: `update()`, `render(Graphics g)`, `handleInput(KeyEvent e)`
- Concrete method: `setState(GameState newState)` - transitions between states
- Create concrete states: MenuState, PlayingState, GameOverState, PausedState

```java
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class GameState {
    // YOUR CODE HERE
}

// Implement at least PlayingState and GameOverState
```

<details>
<summary>💡 Solution</summary>

```java
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class GameState {
    protected GameStateManager manager;
    
    public GameState(GameStateManager manager) {
        this.manager = manager;
    }
    
    // Abstract methods
    public abstract void update();
    public abstract void render(Graphics g);
    public abstract void handleInput(KeyEvent e);
    public abstract void enter(); // Called when state becomes active
    public abstract void exit();  // Called when leaving state
}

public class GameStateManager {
    private GameState currentState;
    
    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        if (currentState != null) {
            currentState.enter();
        }
    }
    
    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }
    
    public void render(Graphics g) {
        if (currentState != null) {
            currentState.render(g);
        }
    }
    
    public void handleInput(KeyEvent e) {
        if (currentState != null) {
            currentState.handleInput(e);
        }
    }
}

public class PlayingState extends GameState {
    private SpaceShip player;
    private GameObjectManager objectManager;
    private CollisionHandler collisionHandler;
    
    public PlayingState(GameStateManager manager) {
        super(manager);
        this.objectManager = new GameObjectManager();
        this.collisionHandler = new CollisionHandler();
    }
    
    @Override
    public void enter() {
        // Initialize game
        player = new SpaceShip(400, 300);
        objectManager.addObject(player);
        
        // Spawn initial asteroids
        for (int i = 0; i < 5; i++) {
            Asteroid ast = new Asteroid(
                Math.random() * 800,
                Math.random() * 600,
                Math.random() * 360,
                1 + Math.random() * 2,
                5
            );
            objectManager.addObject(ast);
        }
    }
    
    @Override
    public void update() {
        objectManager.updateAll();
        collisionHandler.checkAllCollisions(objectManager);
        objectManager.removeInactive();
        
        // Check game over
        if (player.getLives() <= 0) {
            manager.setState(new GameOverState(manager, player.getScore()));
        }
    }
    
    @Override
    public void render(Graphics g) {
        // Render all objects
        for (GameObject obj : objectManager.getAllObjects()) {
            if (obj instanceof Renderable) {
                ((Renderable) obj).draw(g);
            }
        }
        
        // Render UI
        g.drawString("Lives: " + player.getLives(), 10, 20);
        g.drawString("Score: " + player.getScore(), 10, 40);
    }
    
    @Override
    public void handleInput(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            player.accelerate(0.5);
        } else if (key == KeyEvent.VK_LEFT) {
            player.rotate(-5);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.rotate(5);
        } else if (key == KeyEvent.VK_SPACE) {
            // Fire bullet
        } else if (key == KeyEvent.VK_ESCAPE) {
            manager.setState(new PausedState(manager, this));
        }
    }
    
    @Override
    public void exit() {
        // Cleanup if needed
    }
}

public class GameOverState extends GameState {
    private int finalScore;
    
    public GameOverState(GameStateManager manager, int score) {
        super(manager);
        this.finalScore = score;
    }
    
    @Override
    public void enter() {
        System.out.println("Game Over! Final Score: " + finalScore);
    }
    
    @Override
    public void update() {
        // No updates needed in game over screen
    }
    
    @Override
    public void render(Graphics g) {
        g.drawString("GAME OVER", 350, 280);
        g.drawString("Final Score: " + finalScore, 340, 310);
        g.drawString("Press ENTER to restart", 320, 340);
    }
    
    @Override
    public void handleInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            manager.setState(new PlayingState(manager));
        }
    }
    
    @Override
    public void exit() {
        // Cleanup
    }
}

public class PausedState extends GameState {
    private PlayingState previousState;
    
    public PausedState(GameStateManager manager, PlayingState previous) {
        super(manager);
        this.previousState = previous;
    }
    
    @Override
    public void enter() {
        System.out.println("Game Paused");
    }
    
    @Override
    public void update() {
        // No updates while paused
    }
    
    @Override
    public void render(Graphics g) {
        // Render previous state (frozen)
        previousState.render(g);
        
        // Draw pause overlay
        g.drawString("PAUSED", 370, 300);
        g.drawString("Press ESC to resume", 330, 330);
    }
    
    @Override
    public void handleInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            manager.setState(previousState);
        }
    }
    
    @Override
    public void exit() {
        // Resume game
    }
}
```
</details>

---

## Chapter 9: Interfaces - Defining Behaviors

### 📚 Theory: Interfaces

**What are Interfaces?**
- Contract that classes can implement
- Contains only abstract methods (Java 8+ allows default methods)
- A class can implement multiple interfaces
- Achieves abstraction and multiple inheritance

**Syntax:**
```java
public interface Drawable {
    void draw(); // implicitly public and abstract
}

public class MyClass implements Drawable {
    @Override
    public void draw() {
        // implementation
    }
}
```

**Interface vs Abstract Class:**
- Interface: "can do" relationship
- Abstract Class: "is a" *relationship*

### 💻 Practice Questions

**Question 9.1: Collidable Interface**
Create a Collidable interface for objects that can collide.

**Requirements:**
- Method: `getCollisionBounds()` - returns bounds for collision
- Method: `onCollision(GameObject other)` - handles collision event
- Method: `getCollisionRadius()` - for circular collision
- Implement in SpaceShip, Asteroid, Bullet

```java
public interface Collidable {
    // YOUR CODE HERE
}

// Update SpaceShip to implement Collidable
public class SpaceShip extends GameObject implements Collidable {
    // YOUR CODE HERE
}
```

Solution 

```java
import java.awt.Rectangle;

public interface Collidable {
    Rectangle getCollisionBounds();
    void onCollision(GameObject other);
    double getCollisionRadius();
}

// SpaceShip implementing Collidable
public class SpaceShip extends GameObject implements Collidable {
    private double angle;
    private double velocityX, velocityY;
    private int lives;
    private int score;
    private boolean invulnerable;
    private int invulnerableTimer;
    
    public SpaceShip(double x, double y) {
        super(x, y, 20, 20);
        this.angle = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.lives = 3;
        this.score = 0;
        this.invulnerable = false;
    }
    
    @Override
    public Rectangle getColl



# Java Asteroid Destroyer: Part 2 🚀
## Continuation from Chapter 9

---

## Chapter 9: Interfaces - Defining Behaviors (Continued)

**Question 9.1: Collidable Interface (Complete Solution)**

<details>
<summary>💡 Complete Solution</summary>

```java
import java.awt.Rectangle;

public interface Collidable {
    Rectangle getCollisionBounds();
    void onCollision(GameObject other);
    double getCollisionRadius();
}

// SpaceShip implementing Collidable
public class SpaceShip extends GameObject implements Collidable {
    private double angle;
    private double velocityX, velocityY;
    private int lives;
    private int score;
    private boolean invulnerable;
    private int invulnerableTimer;
    
    public SpaceShip(double x, double y) {
        super(x, y, 20, 20);
        this.angle = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.lives = 3;
        this.score = 0;
        this.invulnerable = false;
    }
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - width/2), (int)(y - height/2), 
                           (int)width, (int)height);
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (!invulnerable && other instanceof Asteroid) {
            takeDamage();
            invulnerable = true;
            invulnerableTimer = 120; // 2 seconds at 60fps
        }
    }
    
    @Override
    public double getCollisionRadius() {
        return width / 2.0;
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        velocityX *= 0.98; // friction
        velocityY *= 0.98;
        
        // Handle invulnerability
        if (invulnerable) {
            invulnerableTimer--;
            if (invulnerableTimer <= 0) {
                invulnerable = false;
            }
        }
        
        // Screen wrap
        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }
    
    public void rotate(double deg) { angle += deg; }
    public void accelerate(double amt) {
        double rad = Math.toRadians(angle);
        velocityX += Math.cos(rad) * amt;
        velocityY += Math.sin(rad) * amt;
    }
    
    public void takeDamage() {
        if (lives > 0) lives--;
    }
    
    public void addScore(int pts) { score += pts; }
    public int getLives() { return lives; }
    public int getScore() { return score; }
    public double getAngle() { return angle; }
    public boolean isInvulnerable() { return invulnerable; }
}

// Asteroid implementing Collidable
public class Asteroid extends MovingObject implements Collidable {
    private int size;
    private double radius;
    
    public Asteroid(double x, double y, double angle, double speed, int size) {
        super(x, y, size * 10, size * 10, angle, speed);
        this.size = size;
        this.radius = size * 5;
    }
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - radius), (int)(y - radius),
                           (int)(radius * 2), (int)(radius * 2));
    }
    
    @Override
    public void onCollision(GameObject other) {
        // Asteroids don't react to collisions (handled by manager)
    }
    
    @Override
    public double getCollisionRadius() {
        return radius;
    }
    
    public int getSize() { return size; }
    public double getRadius() { return radius; }
}

// Bullet implementing Collidable
public class Bullet extends MovingObject implements Collidable {
    private static final double RADIUS = 3.0;
    
    public Bullet(double x, double y, double angle) {
        super(x, y, 6, 6, angle, 10.0);
    }
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - RADIUS), (int)(y - RADIUS),
                           (int)(RADIUS * 2), (int)(RADIUS * 2));
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Asteroid) {
            setActive(false);
        }
    }
    
    @Override
    public double getCollisionRadius() {
        return RADIUS;
    }
    
    public boolean isOffScreen() {
        return x < 0 || x > 800 || y < 0 || y > 600;
    }
}
```

</details>

---

**Question 9.2: Updatable and Renderable Interfaces**
Create separate interfaces for updating and rendering game objects.

**Requirements:**
- Interface `Updatable` with method `update()`
- Interface `Renderable` with methods `render(Graphics g)` and `getLayer()`
- Create a `Particle` class that implements both interfaces
- Particles are used for explosion effects when asteroids are destroyed

```java
import java.awt.Graphics;

public interface Updatable {
    // YOUR CODE HERE
}

public interface Renderable {
    // YOUR CODE HERE
}

// Particle class for explosion effects
public class Particle implements Updatable, Renderable {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.awt.Graphics;
import java.awt.Color;

public interface Updatable {
    void update();
}

public interface Renderable {
    void render(Graphics g);
    int getLayer(); // 0 = background, 1 = game objects, 2 = effects, 3 = UI
}

public class Particle implements Updatable, Renderable {
    private double x, y;
    private double velocityX, velocityY;
    private int lifetime;
    private int maxLifetime;
    private Color color;
    private double size;
    
    public Particle(double x, double y, double angle, double speed, int lifetime, Color color) {
        this.x = x;
        this.y = y;
        double rad = Math.toRadians(angle);
        this.velocityX = Math.cos(rad) * speed;
        this.velocityY = Math.sin(rad) * speed;
        this.lifetime = lifetime;
        this.maxLifetime = lifetime;
        this.color = color;
        this.size = 3.0;
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        lifetime--;
        
        // Gravity effect
        velocityY += 0.1;
        
        // Fade out
        size = 3.0 * ((double)lifetime / maxLifetime);
    }
    
    @Override
    public void render(Graphics g) {
        if (lifetime > 0) {
            int alpha = (int)(255 * ((double)lifetime / maxLifetime));
            Color fadeColor = new Color(color.getRed(), color.getGreen(), 
                                      color.getBlue(), alpha);
            g.setColor(fadeColor);
            g.fillOval((int)(x - size/2), (int)(y - size/2), 
                      (int)size, (int)size);
        }
    }
    
    @Override
    public int getLayer() {
        return 2; // Effects layer
    }
    
    public boolean isDead() {
        return lifetime <= 0;
    }
}
```

</details>

---

**Question 9.3: Damageable Interface**
Create a Damageable interface for objects that can take damage.

**Requirements:**
- Method: `takeDamage(int amount)` - reduces health
- Method: `getHealth()` - returns current health
- Method: `isDestroyed()` - checks if health <= 0
- Method: `getMaxHealth()` - returns maximum health
- Create a `Boss` class (large asteroid boss) that implements Damageable

```java
public interface Damageable {
    // YOUR CODE HERE
}

// Boss enemy class
public class Boss extends MovingObject implements Damageable, Collidable {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
public interface Damageable {
    void takeDamage(int amount);
    int getHealth();
    boolean isDestroyed();
    int getMaxHealth();
}

public class Boss extends MovingObject implements Damageable, Collidable {
    private int health;
    private int maxHealth;
    private double radius;
    private long lastShootTime;
    private static final long SHOOT_COOLDOWN = 2000; // 2 seconds
    
    public Boss(double x, double y) {
        super(x, y, 100, 100, 0, 0.5);
        this.maxHealth = 100;
        this.health = maxHealth;
        this.radius = 50;
        this.lastShootTime = System.currentTimeMillis();
    }
    
    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
        if (health == 0) {
            setActive(false);
        }
    }
    
    @Override
    public int getHealth() {
        return health;
    }
    
    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }
    
    @Override
    public int getMaxHealth() {
        return maxHealth;
    }
    
    @Override
    public double getCollisionRadius() {
        return radius;
    }
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - radius), (int)(y - radius),
                           (int)(radius * 2), (int)(radius * 2));
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Bullet) {
            takeDamage(10);
        }
    }
    
    @Override
    public void update() {
        super.update();
        
        // Simple AI: move in circular pattern
        angle += 1;
        double rad = Math.toRadians(angle);
        x = 400 + Math.cos(rad) * 200;
        y = 300 + Math.sin(rad) * 200;
    }
    
    public boolean canShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_COOLDOWN) {
            lastShootTime = currentTime;
            return true;
        }
        return false;
    }
    
    public double getRadius() { return radius; }
}
```
</details>

---

## Chapter 10: Access Modifiers - Controlling Visibility

### 📚 Theory: Access Modifiers

**Four Access Levels:**

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| `public` | ✓ | ✓ | ✓ | ✓ |
| `protected` | ✓ | ✓ | ✓ | ✗ |
| default (no modifier) | ✓ | ✓ | ✗ | ✗ |
| `private` | ✓ | ✗ | ✗ | ✗ |

**Usage:**
```java
public class Example {
    public int publicVar;       // Accessible everywhere
    protected int protectedVar; // Accessible in subclasses
    int defaultVar;             // Accessible in same package
    private int privateVar;     // Only in this class
}
```

**Class Access Modifiers:**
- `public class` - accessible everywhere
- `class` (default) - accessible only within same package

### 💻 Practice Questions

**Question 10.1: Proper Encapsulation with Access Modifiers**
Refactor the GameObject class with proper access modifiers.

**Requirements:**
- Position (x, y) should be protected (subclasses need access)
- Dimensions (width, height) should be protected
- Active state should be private with getter/setter
- Movement methods should be public
- Helper calculation methods should be private

```java
public class GameObject {
    // YOUR CODE HERE - Add proper access modifiers
}
```

<details>
<summary>💡 Solution</summary>

```java
public class GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    private boolean active;
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
    }
    
    // Public methods - part of public API
    public void update() {
        // To be overridden
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public double[] getBounds() {
        return new double[]{x, y, width, height};
    }
    
    // Protected method - accessible to subclasses
    protected void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    // Private helper method
    private double calculateDistanceTo(double targetX, double targetY) {
        double dx = targetX - x;
        double dy = targetY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
```

</details>

---

**Question 10.2: Package-Private Configuration Class**
Create a configuration class that's only accessible within the game package.

**Requirements:**
- Class should have default (package-private) access
- Contains game configuration constants
- Has a private constructor (singleton pattern)
- Public static method to get instance

```java
// In package: com.asteroidgame

// YOUR CODE HERE
class GameConfig {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
// Package-private class (no public modifier)
class GameConfig {
    private static GameConfig instance;
    
    // Package-private constants
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 600;
    static final int TARGET_FPS = 60;
    static final double PLAYER_ACCELERATION = 0.5;
    static final double PLAYER_ROTATION_SPEED = 5.0;
    static final double BULLET_SPEED = 10.0;
    static final int INITIAL_ASTEROID_COUNT = 5;
    
    // Private constructor - prevents external instantiation
    private GameConfig() {
        // Load configuration from file if needed
    }
    
    // Public static method to get instance
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }
    
    // Protected method for subclasses in same package
    protected void reloadConfig() {
        // Reload configuration logic
    }
    
    // Package-private method
    void validateConfig() {
        if (WINDOW_WIDTH <= 0 || WINDOW_HEIGHT <= 0) {
            throw new IllegalStateException("Invalid window dimensions");
        }
    }
}
```

</details>

---

**Question 10.3: Protected Helper Methods in Hierarchy**
Create a base EnemyShip class with protected helper methods for AI.

**Requirements:**
- Protected methods for AI calculations (only subclasses should use)
- Private fields for internal state
- Public methods for game interaction
- Create two subclasses: ChaserEnemy and PatrolEnemy

```java
public abstract class EnemyShip extends MovingObject {
    // YOUR CODE HERE
}

public class ChaserEnemy extends EnemyShip {
    // YOUR CODE HERE
}

public class PatrolEnemy extends EnemyShip {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
public abstract class EnemyShip extends MovingObject {
    private int health;
    private int damage;
    protected double targetX;
    protected double targetY;
    
    public EnemyShip(double x, double y, int health, int damage) {
        super(x, y, 30, 30, 0, 2.0);
        this.health = health;
        this.damage = damage;
    }
    
    // Public method - external interface
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            setActive(false);
        }
    }
    
    // Public getters
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    
    // Protected methods - for subclass AI
    protected double calculateAngleToTarget() {
        double dx = targetX - x;
        double dy = targetY - y;
        return Math.toDegrees(Math.atan2(dy, dx));
    }
    
    protected double calculateDistanceToTarget() {
        double dx = targetX - x;
        double dy = targetY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    protected void moveTowardsTarget(double speedMultiplier) {
        double angle = calculateAngleToTarget();
        double rad = Math.toRadians(angle);
        velocityX = Math.cos(rad) * speed * speedMultiplier;
        velocityY = Math.sin(rad) * speed * speedMultiplier;
    }
    
    // Private helper method
    private void wrapScreen() {
        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }
    
    // Abstract method - subclasses must implement
    public abstract void updateAI(SpaceShip player);
    
    @Override
    public void update() {
        super.update();
        wrapScreen();
    }
}

public class ChaserEnemy extends EnemyShip {
    private static final double CHASE_SPEED = 1.0;
    
    public ChaserEnemy(double x, double y) {
        super(x, y, 30, 10);
    }
    
    @Override
    public void updateAI(SpaceShip player) {
        // Set target to player position
        targetX = player.getX();
        targetY = player.getY();
        
        // Chase player if close enough
        double distance = calculateDistanceToTarget();
        if (distance < 300) {
            moveTowardsTarget(CHASE_SPEED);
        }
    }
}

public class PatrolEnemy extends EnemyShip {
    private double patrolAngle;
    private int patrolTimer;
    private static final int PATROL_CHANGE_TIME = 120; // 2 seconds
    
    public PatrolEnemy(double x, double y) {
        super(x, y, 20, 5);
        this.patrolAngle = Math.random() * 360;
        this.patrolTimer = 0;
    }
    
    @Override
    public void updateAI(SpaceShip player) {
        patrolTimer++;
        
        // Change patrol direction periodically
        if (patrolTimer >= PATROL_CHANGE_TIME) {
            patrolAngle = Math.random() * 360;
            patrolTimer = 0;
        }
        
        // Set target based on patrol angle
        double rad = Math.toRadians(patrolAngle);
        targetX = x + Math.cos(rad) * 100;
        targetY = y + Math.sin(rad) * 100;
        
        moveTowardsTarget(0.5);
    }
}
```
</details>

---

## Chapter 11: Static & Final - Constants and Utilities

### 📚 Theory: Static and Final

**Static Keyword:**
- Belongs to class, not instances
- Shared across all objects
- Can be accessed without creating an object

```java
public class Example {
    static int count = 0;              // Static variable
    static void staticMethod() { }     // Static method
    
    public Example() {
        count++; // Shared counter
    }
}

// Usage:
Example.count;
Example.staticMethod();
```

**Final Keyword:**
- **Final variable**: constant, cannot be reassigned
- **Final method**: cannot be overridden
- **Final class**: cannot be extended

```java
final int MAX_SPEED = 10;        // Constant
public final void method() { }   // Can't override
public final class MyClass { }   // Can't extend
```

**Static Final:**
```java
public static final double PI = 3.14159; // Class constant
```

### 💻 Practice Questions

**Question 11.1: Game Constants Class**
Create a comprehensive constants class for the entire game.

**Requirements:**
- All constants should be `public static final`
- Organize into logical groups (window, player, asteroid, bullet)
- Add utility methods as `public static`
- No instances should be created (private constructor)

```java
public final class GameConstants {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
public final class GameConstants {
    
    // Private constructor prevents instantiation
    private GameConstants() {
        throw new AssertionError("Cannot instantiate GameConstants");
    }
    
    // === WINDOW CONSTANTS ===
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String GAME_TITLE = "ASTEROID DESTROYER";
    public static final int TARGET_FPS = 60;
    public static final long FRAME_TIME_MS = 1000 / TARGET_FPS;
    
    // === PLAYER CONSTANTS ===
    public static final double PLAYER_SIZE = 20.0;
    public static final double PLAYER_ACCELERATION = 0.5;
    public static final double PLAYER_MAX_SPEED = 8.0;
    public static final double PLAYER_ROTATION_SPEED = 5.0;
    public static final double PLAYER_FRICTION = 0.98;
    public static final int PLAYER_INITIAL_LIVES = 3;
    public static final int PLAYER_INVULNERABILITY_FRAMES = 120;
    
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
    public static final int MAX_ASTEROIDS = 20;
    
    // === BULLET CONSTANTS ===
    public static final double BULLET_SPEED = 10.0;
    public static final double BULLET_RADIUS = 3.0;
    public static final int BULLET_FIRE_COOLDOWN = 10; // frames
    
    // === SCORING CONSTANTS ===
    public static final int EXTRA_LIFE_SCORE = 10000;
    
    // === UTILITY METHODS ===
    public static double randomInRange(double min, double max) {
        return min + Math.random() * (max - min);
    }
    
    public static int getAsteroidPoints(int size) {
        switch (size) {
            case ASTEROID_SIZE_LARGE: return ASTEROID_POINTS_LARGE;
            case ASTEROID_SIZE_MEDIUM: return ASTEROID_POINTS_MEDIUM;
            case ASTEROID_SIZE_SMALL: return ASTEROID_POINTS_SMALL;
            case ASTEROID_SIZE_TINY: return ASTEROID_POINTS_TINY;
            default: return 0;
        }
    }
    
    public static boolean isInBounds(double x, double y) {
        return x >= 0 && x <= WINDOW_WIDTH && y >= 0 && y <= WINDOW_HEIGHT;
    }
    
    public static double wrapX(double x) {
        if (x < 0) return WINDOW_WIDTH;
        if (x > WINDOW_WIDTH) return 0;
        return x;
    }
    
    public static double wrapY(double y) {
        if (y < 0) return WINDOW_HEIGHT;
        if (y > WINDOW_HEIGHT) return 0;
        return y;
    }
}
```

</details>

---

**Question 11.2: Static Factory Methods**
Create a factory class with static methods to create game objects.

**Requirements:**
- Static methods to create different asteroid types
- Static method to create random asteroids
- Static method to create asteroids at safe distance from player
- Static counter to track total asteroids created

```java
public class AsteroidFactory {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
public class AsteroidFactory {
    private static int totalAsteroidsCreated = 0;
    
    // Private constructor - only static methods
    private AsteroidFactory() { }
    
    // Static factory method - create large asteroid
    public static Asteroid createLargeAsteroid(double x, double y) {
        totalAsteroidsCreated++;
        double angle = Math.random() * 360;
        double speed = GameConstants.randomInRange(
            GameConstants.ASTEROID_MIN_SPEED,
            GameConstants.ASTEROID_MAX_SPEED
        );
        return new Asteroid(x, y, angle, speed, GameConstants.ASTEROID_SIZE_LARGE);
    }
    
    // Static factory method - create medium asteroid
    public static Asteroid createMediumAsteroid(double x, double y) {
        totalAsteroidsCreated++;
        double angle = Math.random() * 360;
        double speed = GameConstants.randomInRange(
            GameConstants.ASTEROID_MIN_SPEED,
            GameConstants.ASTEROID_MAX_SPEED
        ) * 1.2;
        return new Asteroid(x, y, angle, speed, GameConstants.ASTEROID_SIZE_MEDIUM);
    }
    
    // Static factory method - create small asteroid
    public static Asteroid createSmallAsteroid(double x, double y) {
        totalAsteroidsCreated++;
        double angle = Math.random() * 360;
        double speed = GameConstants.randomInRange(
            GameConstants.ASTEROID_MIN_SPEED,
            GameConstants.ASTEROID_MAX_SPEED
        ) * 1.5;
        return new Asteroid(x, y, angle, speed, GameConstants.ASTEROID_SIZE_SMALL);
    }
    
    // Static factory method - random asteroid
    public static Asteroid createRandomAsteroid() {
        double x = Math.random() * GameConstants.WINDOW_WIDTH;
        double y = Math.random() * GameConstants.WINDOW_HEIGHT;
        
        int[] sizes = {
            GameConstants.ASTEROID_SIZE_LARGE,
            GameConstants.ASTEROID_SIZE_MEDIUM,
            GameConstants.ASTEROID_SIZE_SMALL
        };
        int size = sizes[(int)(Math.random() * sizes.length)];
        
        return createAsteroidBySize(x, y, size);
    }
    
    // Static factory method - safe spawn (away from player)
    public static Asteroid createSafeAsteroid(double playerX, double playerY, double minDistance) {
        double x, y, distance;
        
        do {
            x = Math.random() * GameConstants.WINDOW_WIDTH;
            y = Math.random() * GameConstants.WINDOW_HEIGHT;
            double dx = x - playerX;
            double dy = y - playerY;
            distance = Math.sqrt(dx * dx + dy * dy);
        } while (distance < minDistance);
        
        return createLargeAsteroid(x, y);
    }
    
    // Helper method
    private static Asteroid createAsteroidBySize(double x, double y, int size) {
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
    
    // Static getter for statistics
    public static int getTotalAsteroidsCreated() {
        return totalAsteroidsCreated;
    }
    
    // Static method to reset counter
    public static void resetCounter() {
        totalAsteroidsCreated = 0;
    }
}
```

</details>

---

**Question 11.3: Singleton Game Manager**
Create a GameManager using static final instance (singleton pattern).

**Requirements:**
- Private static final instance
- Private constructor
- Public static getInstance() method
- Manages game state, score, level
- Final methods that shouldn't be overridden

```java
public final class GameManager {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
public final class GameManager {
    // Single instance - static final
    private static final GameManager INSTANCE = new GameManager();
    
    // Game state
    private int currentLevel;
    private int totalScore;
    private int highScore;
    private boolean gamePaused;
    private long gameStartTime;
    
    // Private constructor prevents external instantiation
    private GameManager() {
        this.currentLevel = 1;
        this.totalScore = 0;
        this.highScore = loadHighScore();
        this.gamePaused = false;
        this.gameStartTime = System.currentTimeMillis();
    }
    
    // Public static method to get instance
    public static GameManager getInstance() {
        return INSTANCE;
    }
    
    // Final method - cannot be overridden
    public final void addScore(int points) {
        totalScore += points;
        if (totalScore > highScore) {
            highScore = totalScore;
            saveHighScore(highScore);
        }
    }
    
    // Final method - cannot be overridden
    public final void nextLevel() {
        currentLevel++;
        System.out.println("Level " + currentLevel + " started!");
    }
    
    public final void resetGame() {
        currentLevel = 1;
        totalScore = 0;
        gamePaused = false;
        gameStartTime = System.currentTimeMillis();
    }
    
    public final int getAsteroidCountForLevel() {
        return GameConstants.INITIAL_ASTEROID_COUNT + (currentLevel - 1) * 2;
    }
    
    public final double getAsteroidSpeedMultiplier() {
        return 1.0 + (currentLevel - 1) * 0.1;
    }
    
    public final void togglePause() {
        gamePaused = !gamePaused;
    }
    
    // Getters
    public int getCurrentLevel() { return currentLevel; }
    public int getTotalScore() { return totalScore; }
    public int getHighScore() { return highScore; }
    public boolean isGamePaused() { return gamePaused; }
    
    public long getPlayTime() {
        return (System.currentTimeMillis() - gameStartTime) / 1000;
    }
    
    // Private helper methods
    private int loadHighScore() {
        // In real implementation, load from file
        return 0;
    }
    
    private void saveHighScore(int score) {
        // In real implementation, save to file
        System.out.println("New High Score: " + score);
    }
}
```
</details>

---

## Chapter 12: Wrapper Classes - Object Representations

### 📚 Theory: Wrapper Classes

**What are Wrapper Classes?**
- Object representations of primitive types
- Allow primitives to be used where objects are required

**Primitive → Wrapper:**
- `int` → `Integer`
- `double` → `Double`
- `boolean` → `Boolean`
- `char` → `Character`
- `float` → `Float`
- `long` → `Long`

**Autoboxing and Unboxing:**
```java
// Autoboxing - primitive to wrapper
Integer num = 5;  // int → Integer

// Unboxing - wrapper to primitive
int value = num;  // Integer → int

// Useful methods
Integer.parseInt("123");      // String to int
Double.parseDouble("3.14");   // String to double
Integer.toString(42);         // int to String
```

### 💻 Practice Questions

**Question 11.1: Score Entry with Wrappers**
Create a score entry system using wrapper classes.

**Requirements:**
- Use wrapper classes to store nullable scores
- Parse string input to numbers
- Handle number format exceptions
- Compare scores using wrapper methods

```java
import java.util.ArrayList;

public class ScoreEntry {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.util.ArrayList;
import java.util.Collections;

public class ScoreEntry {
    private String playerName;
    private Integer score;  // Wrapper allows null
    private Long timestamp;
    private Boolean validated;
    
    public ScoreEntry(String playerName, String scoreStr) {
        this.playerName = playerName;
        this.timestamp = System.currentTimeMillis();
        this.validated = false;
        
        try {
            // Parse string to Integer wrapper
            this.score = Integer.parseInt(scoreStr);
            this.validated = true;
        } catch (NumberFormatException e) {
            this.score = null;  // Wrapper can be null
            System.out.println("Invalid score format: " + scoreStr);
        }
    }
    
    public ScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = Integer.valueOf(score);  // Autoboxing
        this.timestamp = System.currentTimeMillis();
        this.validated = true;
    }
    
    // Check if score is valid
    public boolean isValid() {
        return validated && score != null && score >= 0;
    }
    
    // Compare scores using wrapper methods
    public int compareTo(ScoreEntry other) {
        if (this.score == null && other.score == null) return 0;
        if (this.score == null) return 1;
        if (other.score == null) return -1;
        
        return this.score.compareTo(other.score);  // Integer.compareTo()
    }
    
    // Get score with default value if null
    public int getScoreOrDefault(int defaultValue) {
        return (score != null) ? score : defaultValue;  // Unboxing
    }
    
    // Format score with commas
    public String getFormattedScore() {
        if (score == null) return "N/A";
        return String.format("%,d", score);  // Uses wrapper
    }
    
    public String getPlayerName() { return playerName; }
    public Integer getScore() { return score; }  // Returns wrapper
    public Long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return playerName + ": " + getFormattedScore();
    }
}

// Score manager using wrappers
class ScoreManager {
    private ArrayList<ScoreEntry> entries;
    
    public ScoreManager() {
        entries = new ArrayList<>();
    }
    
    public void addEntry(String name, String scoreStr) {
        ScoreEntry entry = new ScoreEntry(name, scoreStr);
        if (entry.isValid()) {
            entries.add(entry);
        }
    }
    
    public Integer getHighScore() {
        if (entries.isEmpty()) return null;  // Can return null
        
        Integer max = null;
        for (ScoreEntry entry : entries) {
            if (entry.isValid()) {
                if (max == null || entry.getScore() > max) {
                    max = entry.getScore();
                }
            }
        }
        return max;
    }
    
    public Double getAverageScore() {
        if (entries.isEmpty()) return null;
        
        int sum = 0;
        int count = 0;
        
        for (ScoreEntry entry : entries) {
            if (entry.isValid()) {
                sum += entry.getScore();  // Unboxing
                count++;
            }
        }
        
        return count > 0 ? Double.valueOf((double)sum / count) : null;
    }
    
    public void sortScores() {
        Collections.sort(entries, (e1, e2) -> e2.compareTo(e1));  // Descending
    }
}
```

</details>

---

**Question 12.2: Configuration Parser with Wrappers**
Create a configuration parser that uses wrapper classes for flexible settings.

**Requirements:**
- Parse different types from strings
- Use wrapper classes to allow null/default values
- Handle parsing errors gracefully
- Provide type-safe getters

```java
import java.util.HashMap;

public class ConfigParser {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.util.HashMap;

public class ConfigParser {
    private HashMap<String, String> rawValues;
    
    public ConfigParser() {
        rawValues = new HashMap<>();
    }
    
    public void setValue(String key, String value) {
        rawValues.put(key, value);
    }
    
    // Get Integer with default
    public Integer getInteger(String key, Integer defaultValue) {
        String value = rawValues.get(key);
        if (value == null) return defaultValue;
        
        try {
            return Integer.valueOf(value);  // String to Integer wrapper
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer for " + key + ": " + value);
            return defaultValue;
        }
    }
    
    // Get Double with default
    public Double getDouble(String key, Double defaultValue) {
        String value = rawValues.get(key);
        if (value == null) return defaultValue;
        
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid double for " + key + ": " + value);
            return defaultValue;
        }
    }
    
    // Get Boolean with default
    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = rawValues.get(key);
        if (value == null) return defaultValue;
        
        return Boolean.valueOf(value);  // "true" or "false"
    }
    
    // Get Long with default
    public Long getLong(String key, Long defaultValue) {
        String value = rawValues.get(key);
        if (value == null) return defaultValue;
        
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    // Get Character with default
    public Character getCharacter(String key, Character defaultValue) {
        String value = rawValues.get(key);
        if (value == null || value.isEmpty()) return defaultValue;
        
        return Character.valueOf(value.charAt(0));
    }
    
    // Get range-constrained integer
    public Integer getIntegerInRange(String key, Integer defaultValue, 
                                    Integer min, Integer max) {
        Integer value = getInteger(key, defaultValue);
        if (value == null) return defaultValue;
        
        // Use Integer.compare() method
        if (Integer.compare(value, min) < 0) return min;
        if (Integer.compare(value, max) > 0) return max;
        
        return value;
    }
}

// Usage example for game configuration
class GameConfig {
    private ConfigParser parser;
    
    public GameConfig() {
        parser = new ConfigParser();
        loadDefaults();
    }
    
    private void loadDefaults() {
        parser.setValue("window_width", "800");
        parser.setValue("window_height", "600");
        parser.setValue("player_lives", "3");
        parser.setValue("max_speed", "8.0");
        parser.setValue("sound_enabled", "true");
    }
    
    public int getWindowWidth() {
        return parser.getInteger("window_width", 800);  // Unboxing
    }
    
    public int getWindowHeight() {
        return parser.getInteger("window_height", 600);
    }
    
    public int getPlayerLives() {
        return parser.getIntegerInRange("player_lives", 3, 1, 10);
    }
    
    public double getMaxSpeed() {
        return parser.getDouble("max_speed", 8.0);  // Unboxing
    }
    
    public boolean isSoundEnabled() {
        return parser.getBoolean("sound_enabled", true);  // Unboxing
    }
}
```

</details>

---

**Question 12.3: Statistics Tracker with Wrappers**
Create a game statistics tracker using wrapper classes for calculations.

**Requirements:**
- Track nullable statistics (can be unavailable)
- Calculate averages, min, max using wrapper methods
- Handle division by zero
- Format numbers for display

```java
import java.util.ArrayList;

public class GameStatistics {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.util.ArrayList;

public class GameStatistics {
    private ArrayList<Integer> scores;
    private ArrayList<Long> gameDurations;  // in milliseconds
    private ArrayList<Integer> asteroidsDestroyed;
    private Integer gamesPlayed;
    
    public GameStatistics() {
        scores = new ArrayList<>();
        gameDurations = new ArrayList<>();
        asteroidsDestroyed = new ArrayList<>();
        gamesPlayed = 0;
    }
    
    public void recordGame(Integer score, Long duration, Integer asteroids) {
        if (score != null) scores.add(score);
        if (duration != null) gameDurations.add(duration);
        if (asteroids != null) asteroidsDestroyed.add(asteroids);
        gamesPlayed++;
    }
    
    // Get average score (returns null if no data)
    public Double getAverageScore() {
        if (scores.isEmpty()) return null;
        
        long sum = 0;
        for (Integer score : scores) {
            sum += score;  // Unboxing
        }
        
        return Double.valueOf((double)sum / scores.size());
    }
    
    // Get highest score
    public Integer getHighScore() {
        if (scores.isEmpty()) return null;
        
        Integer max = Integer.MIN_VALUE;
        for (Integer score : scores) {
            max = Integer.max(max, score);  // Integer.max()
        }
        
        return max;
    }
    
    // Get lowest score
    public Integer getLowScore() {
        if (scores.isEmpty()) return null;
        
        Integer min = Integer.MAX_VALUE;
        for (Integer score : scores) {
            min = Integer.min(min, score);  // Integer.min()
        }
        
        return min;
    }
    
    // Get average game duration in seconds
    public Double getAverageDuration() {
        if (gameDurations.isEmpty()) return null;
        
        long sum = 0;
        for (Long duration : gameDurations) {
            sum += duration;  // Unboxing
        }
        
        return Double.valueOf((double)sum / gameDurations.size() / 1000.0);
    }
    
    // Get total asteroids destroyed
    public Integer getTotalAsteroidsDestroyed() {
        int total = 0;
        for (Integer count : asteroidsDestroyed) {
            total += count;  // Unboxing
        }
        return Integer.valueOf(total);  // Boxing
    }
    
    // Get formatted statistics
    public String getFormattedStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GAME STATISTICS ===\n");
        sb.append(String.format("Games Played: %d\n", gamesPlayed));
        
        Double avgScore = getAverageScore();
        if (avgScore != null) {
            sb.append(String.format("Average Score: %.2f\n", avgScore));
        }
        
        Integer highScore = getHighScore();
        if (highScore != null) {
            sb.append(String.format("High Score: %,d\n", highScore));
        }
        
        Double avgDuration = getAverageDuration();
        if (avgDuration != null) {
            sb.append(String.format("Avg Game Duration: %.1f sec\n", avgDuration));
        }
        
        Integer totalAsteroids = getTotalAsteroidsDestroyed();
        sb.append(String.format("Total Asteroids Destroyed: %,d\n", totalAsteroids));
        
        return sb.toString();
    }
    
    // Calculate percentile score
    public Double getPercentile(Integer targetScore, Integer percentile) {
        if (scores.isEmpty() || targetScore == null) return null;
        
        int count = 0;
        for (Integer score : scores) {
            if (score <= targetScore) {
                count++;
            }
        }
        
        return Double.valueOf((double)count / scores.size() * 100.0);
    }
}
```
</details>

---

## Chapter 13: Nested Classes - Organizing Related Code

### 📚 Theory: Nested Classes

**Types of Nested Classes:**

1. **Static Nested Class:**
```java
class Outer {
    static class StaticNested {
        // Can be instantiated without Outer instance
    }
}
// Usage: Outer.StaticNested obj = new Outer.StaticNested();
```

2. **Inner Class (Non-static):**
```java
class Outer {
    class Inner {
        // Requires Outer instance
    }
}
// Usage: Outer.Inner obj = outerObj.new Inner();
```

3. **Local Class (inside method):**
```java
void method() {
    class Local {
        // Only accessible within method
    }
}
```

4. **Anonymous Class:**
```java
Interface obj = new Interface() {
    // Implement interface on-the-fly
};
```

### 💻 Practice Questions

**Question 13.1: Static Nested Builder Class**
Create a SpaceShipBuilder as a static nested class inside SpaceShip.

**Requirements:**
- Builder pattern for constructing complex SpaceShip objects
- Fluent interface (method chaining)
- Build method returns SpaceShip instance

```java
public class SpaceShip extends GameObject {
    // YOUR CODE HERE
    
    public static class Builder {
        // YOUR CODE HERE
    }
}
```

<details>
<summary>💡 Solution</summary>

```java
public class SpaceShip extends GameObject {
    private double angle;
    private double velocityX, velocityY;
    private int lives;
    private int score;
    private double maxSpeed;
    private double acceleration;
    private String shipColor;
    private boolean shielded;
    
    // Private constructor - only Builder can create
    private SpaceShip(Builder builder) {
        super(builder.x, builder.y, builder.width, builder.height);
        this.angle = builder.angle;
        this.lives = builder.lives;
        this.score = builder.score;
        this.maxSpeed = builder.maxSpeed;
        this.acceleration = builder.acceleration;
        this.shipColor = builder.shipColor;
        this.shielded = builder.shielded;
        this.velocityX = 0;
        this.velocityY = 0;
    }
    
    // Static nested Builder class
    public static class Builder {
        // Required parameters
        private double x, y;
        
        // Optional parameters with defaults
        private double width = 20.0;
        private double height = 20.0;
        private double angle = 0.0;
        private int lives = 3;
        private int score = 0;
        private double maxSpeed = 8.0;
        private double acceleration = 0.5;
        private String shipColor = "white";
        private boolean shielded = false;
        
        // Constructor with required parameters
        public Builder(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        // Fluent setters
        public Builder size(double width, double height) {
            this.width = width;
            this.height = height;
            return this;
        }
        
        public Builder angle(double angle) {
            this.angle = angle;
            return this;
        }
        
        public Builder lives(int lives) {
            this.lives = lives;
            return this;
        }
        
        public Builder score(int score) {
            this.score = score;
            return this;
        }
        
        public Builder maxSpeed(double maxSpeed) {
            this.maxSpeed = maxSpeed;
            return this;
        }
        
        public Builder acceleration(double acceleration) {
            this.acceleration = acceleration;
            return this;
        }
        
        public Builder color(String color) {
            this.shipColor = color;
            return this;
        }
        
        public Builder shielded(boolean shielded) {
            this.shielded = shielded;
            return this;
        }
        
        // Build method
        public SpaceShip build() {
            return new SpaceShip(this);
        }
    }
    
    // SpaceShip methods
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        velocityX *= 0.98;
        velocityY *= 0.98;
        
        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }
    
    public void rotate(double deg) { angle += deg; }
    
    public void accelerate() {
        double rad = Math.toRadians(angle);
        velocityX += Math.cos(rad) * acceleration;
        velocityY += Math.sin(rad) * acceleration;
        
        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (speed > maxSpeed) {
            velocityX = (velocityX / speed) * maxSpeed;
            velocityY = (velocityY / speed) * maxSpeed;
        }
    }
    
    public void takeDamage() {
        if (!shielded && lives > 0) lives--;
    }
    
    public int getLives() { return lives; }
    public int getScore() { return score; }
    public void addScore(int pts) { score += pts; }
}

// Usage example:
class Main {
    public static void main(String[] args) {
        SpaceShip player = new SpaceShip.Builder(400, 300)
            .lives(5)
            .maxSpeed(10.0)
            .color("blue")
            .shielded(true)
            .build();
    }
}
```

</details>

---

**Question 13.2: Inner Class for Game Input Handler**
Create an InputHandler as an inner class that has access to Game state.

**Requirements:**
- Inner class needs access to outer class's private members
- Handle keyboard events
- Update player ship based on input
- Track key states

```java
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class Game {
    // YOUR CODE HERE
    
    public class InputHandler implements KeyListener {
        // YOUR CODE HERE
    }
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class Game {
    private SpaceShip player;
    private BulletManager bulletManager;
    private boolean gamePaused;
    private InputHandler inputHandler;
    
    public Game() {
        player = new SpaceShip(400, 300);
        bulletManager = new BulletManager();
        gamePaused = false;
        inputHandler = new InputHandler();
    }
    
    public InputHandler getInputHandler() {
        return inputHandler;
    }
    
    public void update() {
        if (!gamePaused) {
            inputHandler.processHeldKeys();
            player.update();
            bulletManager.updateAll();
        }
    }
    
    // Inner class - has access to outer class members
    public class InputHandler implements KeyListener {
        private HashSet<Integer> keysPressed;
        private long lastShotTime;
        private static final long SHOT_COOLDOWN = 200; // milliseconds
        
        public InputHandler() {
            keysPressed = new HashSet<>();
            lastShotTime = 0;
        }
        
        // Process continuously held keys
        public void processHeldKeys() {
            if (keysPressed.contains(KeyEvent.VK_UP)) {
                player.accelerate(0.5);  // Access outer class's player
            }
            
            if (keysPressed.contains(KeyEvent.VK_LEFT)) {
                player.rotate(-5);
            }
            
            if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
                player.rotate(5);
            }
            
            if (keysPressed.contains(KeyEvent.VK_SPACE)) {
                tryShoot();
            }
        }
        
        private void tryShoot() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= SHOT_COOLDOWN) {
                // Access outer class members directly
                bulletManager.fireBullet(
                    player.getX(),
                    player.getY(),
                    player.getAngle()
                );
                lastShotTime = currentTime;
            }
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            keysPressed.add(key);
            
            // Handle single-press keys
            if (key == KeyEvent.VK_ESCAPE) {
                gamePaused = !gamePaused;  // Access outer class field
            }
            
            if (key == KeyEvent.VK_R && gamePaused) {
                resetGame();  // Call outer class method
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            keysPressed.remove(e.getKeyCode());
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            // Not used
        }
        
        public boolean isKeyPressed(int keyCode) {
            return keysPressed.contains(keyCode);
        }
    }
    
    private void resetGame() {
        player = new SpaceShip(400, 300);
        bulletManager.clear();
        gamePaused = false;
    }
}
```

</details>

---

**Question 13.3: Anonymous Class for Particle Effects**
Use anonymous classes to create different particle effect behaviors.

**Requirements:**
- Create ParticleEffect interface
- Use anonymous classes to define explosion, trail, and sparkle effects
- Each effect has different update behavior
- Demonstrate polymorphism

```java
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public interface ParticleEffect {
    // YOUR CODE HERE
}

public class EffectManager {
    // YOUR CODE HERE - create effects using anonymous classes
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public interface ParticleEffect {
    void update();
    void render(Graphics g);
    boolean isFinished();
}

public class EffectManager {
    private ArrayList<ParticleEffect> effects;
    
    public EffectManager() {
        effects = new ArrayList<>();
    }
    
    // Create explosion effect using anonymous class
    public void createExplosion(double x, double y, Color color, int particleCount) {
        ParticleEffect explosion = new ParticleEffect() {
            private ArrayList<ExplosionParticle> particles = new ArrayList<>();
            
            // Initializer block in anonymous class
            {
                for (int i = 0; i < particleCount; i++) {
                    double angle = Math.random() * 360;
                    double speed = 1 + Math.random() * 3;
                    particles.add(new ExplosionParticle(x, y, angle, speed, color));
                }
            }
            
            @Override
            public void update() {
                for (int i = particles.size() - 1; i >= 0; i--) {
                    ExplosionParticle p = particles.get(i);
                    p.update();
                    if (p.isDead()) {
                        particles.remove(i);
                    }
                }
            }
            
            @Override
            public void render(Graphics g) {
                for (ExplosionParticle p : particles) {
                    p.render(g);
                }
            }
            
            @Override
            public boolean isFinished() {
                return particles.isEmpty();
            }
        };
        
        effects.add(explosion);
    }
    
    // Create trail effect using anonymous class
    public void createTrail(SpaceShip ship) {
        ParticleEffect trail = new ParticleEffect() {
            private ArrayList<TrailParticle> particles = new ArrayList<>();
            private int spawnCounter = 0;
            private static final int MAX_PARTICLES = 30;
            
            @Override
            public void update() {
                spawnCounter++;
                
                // Spawn new particle every 3 frames
                if (spawnCounter % 3 == 0 && particles.size() < MAX_PARTICLES) {
                    double angle = ship.getAngle() + 180; // Behind ship
                    particles.add(new TrailParticle(
                        ship.getX(),
                        ship.getY(),
                        angle,
                        0.5,
                        Color.ORANGE
                    ));
                }
                
                // Update existing particles
                for (int i = particles.size() - 1; i >= 0; i--) {
                    TrailParticle p = particles.get(i);
                    p.update();
                    if (p.isDead()) {
                        particles.remove(i);
                    }
                }
            }
            
            @Override
            public void render(Graphics g) {
                for (TrailParticle p : particles) {
                    p.render(g);
                }
            }
            
            @Override
            public boolean isFinished() {
                return false; // Trail runs continuously
            }
        };
        
        effects.add(trail);
    }
    
    // Create sparkle effect using anonymous class
    public void createSparkle(double x, double y) {
        ParticleEffect sparkle = new ParticleEffect() {
            private int lifetime = 30;
            private double currentX = x;
            private double currentY = y;
            private double size = 5;
            private Color[] colors = {Color.YELLOW, Color.WHITE, Color.CYAN};
            private int colorIndex = 0;
            
            @Override
            public void update() {
                lifetime--;
                size = 5 * ((double)lifetime / 30);
                colorIndex = (colorIndex + 1) % colors.length;
            }
            
            @Override
            public void render(Graphics g) {
                if (lifetime > 0) {
                    g.setColor(colors[colorIndex]);
                    g.fillOval(
                        (int)(currentX - size/2),
                        (int)(currentY - size/2),
                        (int)size,
                        (int)size
                    );
                }
            }
            
            @Override
            public boolean isFinished() {
                return lifetime <= 0;
            }
        };
        
        effects.add(sparkle);
    }
    
    public void updateAll() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            ParticleEffect effect = effects.get(i);
            effect.update();
            if (effect.isFinished()) {
                effects.remove(i);
            }
        }
    }
    
    public void renderAll(Graphics g) {
        for (ParticleEffect effect : effects) {
            effect.render(g);
        }
    }
    
    public void clear() {
        effects.clear();
    }
    
    // Helper classes for particles
    private static class ExplosionParticle {
        double x, y, vx, vy;
        int life = 60;
        Color color;
        
        ExplosionParticle(double x, double y, double angle, double speed, Color color) {
            this.x = x;
            this.y = y;
            double rad = Math.toRadians(angle);
            this.vx = Math.cos(rad) * speed;
            this.vy = Math.sin(rad) * speed;
            this.color = color;
        }
        
        void update() {
            x += vx;
            y += vy;
            vy += 0.1; // Gravity
            life--;



# Java Asteroid Destroyer: Part 3 🚀
## Final Chapters - Packages, Graphics & Audio

---

## Chapter 13: Nested Classes (Continued)

**Question 13.3 Helper Classes (Completion)**

```java
        void render(Graphics g) {
            int alpha = (int)(255 * ((double)life / 60));
            Color c = new Color(color.getRed(), color.getGreen(), 
                              color.getBlue(), alpha);
            g.setColor(c);
            g.fillOval((int)(x-2), (int)(y-2), 4, 4);
        }
        
        boolean isDead() { return life <= 0; }
    }
    
    private static class TrailParticle extends ExplosionParticle {
        TrailParticle(double x, double y, double angle, double speed, Color color) {
            super(x, y, angle, speed, color);
            this.life = 20; // Shorter life for trails
        }
    }
}
```
</details>

---

## Chapter 14: Packages & File Organization

### 📚 Theory: Packages

**What are Packages?**
- Namespaces that organize related classes
- Prevent naming conflicts
- Provide access protection
- Create directory structure

**Syntax:**
```java
package com.asteroidgame.entities;

import com.asteroidgame.util.PhysicsUtils;
import java.util.ArrayList;
```

**Package Naming Conventions:**
- All lowercase
- Reverse domain name: `com.company.project`
- Example: `com.asteroidgame.entities`, `com.asteroidgame.managers`

**File Naming Rules:**
- Public class name must match filename
- One public class per file
- Example: `SpaceShip.java` contains `public class SpaceShip`

### 💻 Practice Questions

**Question 14.1: Organize Game into Packages**
Structure the asteroid game into logical packages.

**Requirements:**
- Package structure:
  - `com.asteroidgame` - main game class
  - `com.asteroidgame.entities` - game objects
  - `com.asteroidgame.managers` - manager classes
  - `com.asteroidgame.util` - utility classes
  - `com.asteroidgame.graphics` - rendering classes
- Proper imports between packages

```java
// File: com/asteroidgame/entities/SpaceShip.java
// YOUR CODE HERE

// File: com/asteroidgame/managers/GameManager.java
// YOUR CODE HERE

// File: com/asteroidgame/AsteroidGame.java (main class)
// YOUR CODE HERE
```

<details>
<summary>💡 Solution</summary>

```java
// File: com/asteroidgame/entities/GameObject.java
package com.asteroidgame.entities;

public abstract class GameObject {
    protected double x, y, width, height;
    protected boolean active;
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
    }
    
    public abstract void update();
    
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

// File: com/asteroidgame/entities/SpaceShip.java
package com.asteroidgame.entities;

import com.asteroidgame.util.GameConstants;

public class SpaceShip extends GameObject {
    private double angle, velocityX, velocityY;
    private int lives, score;
    
    public SpaceShip(double x, double y) {
        super(x, y, 20, 20);
        this.angle = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.lives = GameConstants.PLAYER_INITIAL_LIVES;
        this.score = 0;
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        velocityX *= GameConstants.PLAYER_FRICTION;
        velocityY *= GameConstants.PLAYER_FRICTION;
        
        // Screen wrap
        if (x < 0) x = GameConstants.WINDOW_WIDTH;
        if (x > GameConstants.WINDOW_WIDTH) x = 0;
        if (y < 0) y = GameConstants.WINDOW_HEIGHT;
        if (y > GameConstants.WINDOW_HEIGHT) y = 0;
    }
    
    public void rotate(double deg) { angle += deg; }
    
    public void accelerate(double amount) {
        double rad = Math.toRadians(angle);
        velocityX += Math.cos(rad) * amount;
        velocityY += Math.sin(rad) * amount;
    }
    
    public void takeDamage() { if (lives > 0) lives--; }
    public void addScore(int pts) { score += pts; }
    public int getLives() { return lives; }
    public int getScore() { return score; }
    public double getAngle() { return angle; }
}

// File: com/asteroidgame/entities/Asteroid.java
package com.asteroidgame.entities;

import com.asteroidgame.util.GameConstants;

public class Asteroid extends GameObject {
    private int size;
    private double radius, velocityX, velocityY;
    
    public Asteroid(double x, double y, double angle, double speed, int size) {
        super(x, y, size * 10, size * 10);
        this.size = size;
        this.radius = size * 5;
        
        double rad = Math.toRadians(angle);
        this.velocityX = Math.cos(rad) * speed;
        this.velocityY = Math.sin(rad) * speed;
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        
        if (x < 0) x = GameConstants.WINDOW_WIDTH;
        if (x > GameConstants.WINDOW_WIDTH) x = 0;
        if (y < 0) y = GameConstants.WINDOW_HEIGHT;
        if (y > GameConstants.WINDOW_HEIGHT) y = 0;
    }
    
    public int getSize() { return size; }
    public double getRadius() { return radius; }
}

// File: com/asteroidgame/managers/GameObjectManager.java
package com.asteroidgame.managers;

import com.asteroidgame.entities.GameObject;
import java.util.ArrayList;

public class GameObjectManager {
    private ArrayList<GameObject> objects;
    
    public GameObjectManager() {
        objects = new ArrayList<>();
    }
    
    public void addObject(GameObject obj) { objects.add(obj); }
    
    public void updateAll() {
        for (GameObject obj : objects) {
            if (obj.isActive()) obj.update();
        }
    }
    
    public void removeInactive() {
        objects.removeIf(obj -> !obj.isActive());
    }
    
    public ArrayList<GameObject> getAllObjects() { return objects; }
}

// File: com/asteroidgame/util/GameConstants.java
package com.asteroidgame.util;

public final class GameConstants {
    private GameConstants() {}
    
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int PLAYER_INITIAL_LIVES = 3;
    public static final double PLAYER_FRICTION = 0.98;
    public static final double PLAYER_MAX_SPEED = 8.0;
}

// File: com/asteroidgame/AsteroidGame.java
package com.asteroidgame;

import com.asteroidgame.entities.*;
import com.asteroidgame.managers.*;
import com.asteroidgame.util.*;

public class AsteroidGame {
    private SpaceShip player;
    private GameObjectManager objectManager;
    private boolean running;
    
    public AsteroidGame() {
        player = new SpaceShip(GameConstants.WINDOW_WIDTH / 2,
                              GameConstants.WINDOW_HEIGHT / 2);
        objectManager = new GameObjectManager();
        objectManager.addObject(player);
        running = true;
    }
    
    public void run() {
        while (running) {
            objectManager.updateAll();
            objectManager.removeInactive();
            
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        AsteroidGame game = new AsteroidGame();
        game.run();
    }
}
```

</details>

---

**Question 14.2: Import Management**
Demonstrate proper import usage and wildcards.

**Requirements:**
- Show specific imports vs wildcard imports
- Import from standard library (java.util, java.awt)
- Import from custom packages
- Static imports for constants

```java
package com.asteroidgame.graphics;

// YOUR CODE HERE - Add proper imports
```

<details>
<summary>💡 Solution</summary>

```java
package com.asteroidgame.graphics;

// Specific imports - preferred for clarity
import com.asteroidgame.entities.SpaceShip;
import com.asteroidgame.entities.Asteroid;
import com.asteroidgame.entities.Bullet;
import com.asteroidgame.managers.GameObjectManager;

// Static imports for constants
import static com.asteroidgame.util.GameConstants.*;

// Java standard library imports
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;

// Wildcard import for multiple classes from same package
import java.awt.event.*;

public class GameRenderer {
    private Graphics2D g2d;
    
    public GameRenderer(Graphics g) {
        this.g2d = (Graphics2D) g;
    }
    
    public void render(GameObjectManager manager) {
        // Can use imported constants directly
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Render all objects
        for (var obj : manager.getAllObjects()) {
            if (obj instanceof SpaceShip) {
                drawSpaceShip((SpaceShip) obj);
            } else if (obj instanceof Asteroid) {
                drawAsteroid((Asteroid) obj);
            } else if (obj instanceof Bullet) {
                drawBullet((Bullet) obj);
            }
        }
    }
    
    private void drawSpaceShip(SpaceShip ship) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        
        // Draw triangle for ship
        int[] xPoints = {0, -10, 10};
        int[] yPoints = {-15, 10, 10};
        
        AffineTransform old = g2d.getTransform();
        g2d.translate(ship.getX(), ship.getY());
        g2d.rotate(Math.toRadians(ship.getAngle()));
        g2d.drawPolygon(xPoints, yPoints, 3);
        g2d.setTransform(old);
    }
    
    private void drawAsteroid(Asteroid ast) {
        g2d.setColor(Color.GRAY);
        g2d.drawOval(
            (int)(ast.getX() - ast.getRadius()),
            (int)(ast.getY() - ast.getRadius()),
            (int)(ast.getRadius() * 2),
            (int)(ast.getRadius() * 2)
        );
    }
    
    private void drawBullet(Bullet bullet) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(
            (int)(bullet.getX() - 3),
            (int)(bullet.getY() - 3),
            6, 6
        );
    }
}
```

</details>

---

**Question 14.3: Package Access Control**
Create package-private utility class accessible only within package.

**Requirements:**
- Class with default (package-private) access
- Mix of package-private and public methods
- Demonstrate access from same package and outside package

```java
// File: com/asteroidgame/util/MathHelper.java
package com.asteroidgame.util;

// YOUR CODE HERE
```

<details>
<summary>💡 Solution</summary>

```java
// File: com/asteroidgame/util/MathHelper.java
package com.asteroidgame.util;

// Package-private class (no public modifier)
class MathHelper {
    
    // Package-private method - only accessible within util package
    static double wrapValue(double value, double min, double max) {
        if (value < min) return max;
        if (value > max) return min;
        return value;
    }
    
    // Public method - accessible everywhere
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    // Package-private method
    static double lerp(double start, double end, double t) {
        return start + (end - start) * clamp(t, 0, 1);
    }
}

// File: com/asteroidgame/util/PhysicsUtils.java
package com.asteroidgame.util;

public class PhysicsUtils {
    
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public static double wrapX(double x) {
        // Can access MathHelper because same package
        return MathHelper.wrapValue(x, 0, GameConstants.WINDOW_WIDTH);
    }
    
    public static double wrapY(double y) {
        // Can access MathHelper because same package
        return MathHelper.wrapValue(y, 0, GameConstants.WINDOW_HEIGHT);
    }
    
    // Uses package-private lerp method
    static double smoothTransition(double start, double end, double progress) {
        return MathHelper.lerp(start, end, progress);
    }
}

// File: com/asteroidgame/entities/MovingEntity.java
package com.asteroidgame.entities;

import com.asteroidgame.util.PhysicsUtils;
// Cannot import MathHelper - it's package-private!

public abstract class MovingEntity extends GameObject {
    protected double velocityX, velocityY;
    
    public MovingEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        
        // Can use public methods from PhysicsUtils
        x = PhysicsUtils.wrapX(x);
        y = PhysicsUtils.wrapY(y);
        
        // CANNOT do this - MathHelper is package-private:
        // x = MathHelper.wrapValue(x, 0, 800); // COMPILE ERROR!
    }
}
```
</details>

---

## Chapter 15: Graphics with JFrame & Swing

### 📚 Theory: Java Graphics

**JFrame (javax.swing.JFrame):**
- Top-level container for GUI applications
- Contains window frame, title bar, borders

**JPanel (javax.swing.JPanel):**
- Container for components
- Override `paintComponent(Graphics g)` for custom drawing

**Graphics & Graphics2D:**
- `Graphics` - basic drawing methods
- `Graphics2D` - advanced 2D graphics (antialiasing, transforms)

**Key Methods:**
```java
// Drawing shapes
g.drawLine(x1, y1, x2, y2);
g.drawRect(x, y, width, height);
g.fillRect(x, y, width, height);
g.drawOval(x, y, width, height);
g.fillOval(x, y, width, height);
g.drawPolygon(xPoints, yPoints, nPoints);

// Colors and fonts
g.setColor(Color.RED);
g.setFont(new Font("Arial", Font.BOLD, 20));
g.drawString("Text", x, y);
```

### 💻 Practice Questions

**Question 15.1: Create Game Window with JFrame**
Create the main game window using JFrame.

**Requirements:**
- 800x600 window
- Title: "Asteroid Destroyer"
- Center on screen
- Close on exit
- Create custom JPanel for rendering

```java
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    
    public GameWindow() {
        setTitle("Asteroid Destroyer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen
        
        gamePanel = new GamePanel();
        add(gamePanel);
        
        setVisible(true);
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public static void main(String[] args) {
        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new GameWindow();
        });
    }
}

class GamePanel extends JPanel {
    private SpaceShip player;
    private ArrayList<Asteroid> asteroids;
    
    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Initialize game objects
        player = new SpaceShip(400, 300);
        asteroids = new ArrayList<>();
        
        // Add some asteroids
        for (int i = 0; i < 5; i++) {
            asteroids.add(new Asteroid(
                Math.random() * 800,
                Math.random() * 600,
                Math.random() * 360,
                1 + Math.random() * 2,
                5
            ));
        }
        
        // Add key listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        
        // Start game loop
        startGameLoop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw player
        drawSpaceShip(g2d, player);
        
        // Draw asteroids
        for (Asteroid ast : asteroids) {
            drawAsteroid(g2d, ast);
        }
        
        // Draw UI
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Lives: " + player.getLives(), 10, 20);
        g2d.drawString("Score: " + player.getScore(), 10, 40);
    }
    
    private void drawSpaceShip(Graphics2D g2d, SpaceShip ship) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        
        int[] xPoints = {0, -10, 10};
        int[] yPoints = {-15, 10, 10};
        
        AffineTransform old = g2d.getTransform();
        g2d.translate(ship.getX(), ship.getY());
        g2d.rotate(Math.toRadians(ship.getAngle()));
        g2d.drawPolygon(xPoints, yPoints, 3);
        g2d.setTransform(old);
    }
    
    private void drawAsteroid(Graphics2D g2d, Asteroid ast) {
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(
            (int)(ast.getX() - ast.getRadius()),
            (int)(ast.getY() - ast.getRadius()),
            (int)(ast.getRadius() * 2),
            (int)(ast.getRadius() * 2)
        );
    }
    
    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.accelerate(0.5);
                break;
            case KeyEvent.VK_LEFT:
                player.rotate(-5);
                break;
            case KeyEvent.VK_RIGHT:
                player.rotate(5);
                break;
        }
    }
    
    private void startGameLoop() {
        Timer timer = new Timer(16, e -> {
            // Update game state
            player.update();
            for (Asteroid ast : asteroids) {
                ast.update();
            }
            
            // Repaint
            repaint();
        });
        timer.start();
    }
}
```

</details>

---

**Question 15.2: Image Loading and Rendering**
Load and render images for game sprites using BufferedImage.

**Requirements:**
- Load spaceship image from file
- Load asteroid image
- Load background image
- Draw images rotated based on object angle
- Handle missing image files

```java
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class SpriteManager {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class SpriteManager {
    private BufferedImage shipImage;
    private BufferedImage asteroidImage;
    private BufferedImage backgroundImage;
    private BufferedImage bulletImage;
    
    public SpriteManager() {
        loadImages();
    }
    
    private void loadImages() {
        try {
            // Load from resources folder
            shipImage = ImageIO.read(new File("resources/spaceship.png"));
            asteroidImage = ImageIO.read(new File("resources/asteroid.png"));
            backgroundImage = ImageIO.read(new File("resources/background.png"));
            bulletImage = ImageIO.read(new File("resources/bullet.png"));
            
            System.out.println("All images loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
            createFallbackImages();
        }
    }
    
    // Create simple colored images if files not found
    private void createFallbackImages() {
        shipImage = createColoredImage(20, 20, Color.WHITE);
        asteroidImage = createColoredImage(50, 50, Color.GRAY);
        backgroundImage = createColoredImage(800, 600, Color.BLACK);
        bulletImage = createColoredImage(6, 6, Color.YELLOW);
    }
    
    private BufferedImage createColoredImage(int width, int height, Color color) {
        BufferedImage img = new BufferedImage(width, height, 
                                             BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return img;
    }
    
    public void drawShip(Graphics2D g2d, SpaceShip ship) {
        if (shipImage == null) return;
        
        AffineTransform old = g2d.getTransform();
        
        // Translate to ship position
        g2d.translate(ship.getX(), ship.getY());
        
        // Rotate around center
        g2d.rotate(Math.toRadians(ship.getAngle()));
        
        // Draw image centered
        g2d.drawImage(shipImage, 
            -shipImage.getWidth() / 2, 
            -shipImage.getHeight() / 2, 
            null);
        
        g2d.setTransform(old);
    }
    
    public void drawAsteroid(Graphics2D g2d, Asteroid ast) {
        if (asteroidImage == null) return;
        
        int size = (int)(ast.getRadius() * 2);
        
        g2d.drawImage(asteroidImage,
            (int)(ast.getX() - ast.getRadius()),
            (int)(ast.getY() - ast.getRadius()),
            size, size,
            null);
    }
    
    public void drawBullet(Graphics2D g2d, Bullet bullet) {
        if (bulletImage == null) return;
        
        g2d.drawImage(bulletImage,
            (int)(bullet.getX() - 3),
            (int)(bullet.getY() - 3),
            null);
    }
    
    public void drawBackground(Graphics2D g2d) {
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 800, 600);
        }
    }
}
```

</details>

---

**Question 15.3: Particle Effects with Graphics**
Create visual explosion effects when asteroids are destroyed.

**Requirements:**
- Particle system that creates explosion on collision
- Particles fade out over time
- Multiple colored particles
- Use alpha transparency

```java
import java.awt.*;
import java.util.ArrayList;

public class ExplosionEffect {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import java.awt.*;
import java.util.ArrayList;

public class ExplosionEffect {
    private ArrayList<Particle> particles;
    
    public ExplosionEffect() {
        particles = new ArrayList<>();
    }
    
    public void createExplosion(double x, double y, int particleCount, Color baseColor) {
        for (int i = 0; i < particleCount; i++) {
            double angle = Math.random() * 360;
            double speed = 1 + Math.random() * 4;
            Color particleColor = varyColor(baseColor);
            
            particles.add(new Particle(x, y, angle, speed, particleColor, 60));
        }
    }
    
    private Color varyColor(Color base) {
        int r = Math.max(0, Math.min(255, base.getRed() + (int)(Math.random() * 50 - 25)));
        int g = Math.max(0, Math.min(255, base.getGreen() + (int)(Math.random() * 50 - 25)));
        int b = Math.max(0, Math.min(255, base.getBlue() + (int)(Math.random() * 50 - 25)));
        return new Color(r, g, b);
    }
    
    public void update() {
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update();
            
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }
    
    public void render(Graphics2D g2d) {
        for (Particle p : particles) {
            p.render(g2d);
        }
    }
    
    public boolean isFinished() {
        return particles.isEmpty();
    }
    
    private static class Particle {
        private double x, y;
        private double velocityX, velocityY;
        private Color color;
        private int lifetime;
        private int maxLifetime;
        private double size;
        
        public Particle(double x, double y, double angle, double speed, 
                       Color color, int lifetime) {
            this.x = x;
            this.y = y;
            double rad = Math.toRadians(angle);
            this.velocityX = Math.cos(rad) * speed;
            this.velocityY = Math.sin(rad) * speed;
            this.color = color;
            this.lifetime = lifetime;
            this.maxLifetime = lifetime;
            this.size = 2 + Math.random() * 3;
        }
        
        public void update() {
            x += velocityX;
            y += velocityY;
            
            // Gravity
            velocityY += 0.15;
            
            // Slow down
            velocityX *= 0.98;
            velocityY *= 0.98;
            
            lifetime--;
        }
        
        public void render(Graphics2D g2d) {
            if (lifetime > 0) {
                // Calculate alpha based on remaining lifetime
                float alpha = (float)lifetime / maxLifetime;
                int alphaValue = (int)(alpha * 255);
                
                Color fadeColor = new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    alphaValue
                );
                
                g2d.setColor(fadeColor);
                
                // Size diminishes over time
                double currentSize = size * alpha;
                g2d.fillOval(
                    (int)(x - currentSize/2),
                    (int)(y - currentSize/2),
                    (int)currentSize,
                    (int)currentSize
                );
            }
        }
        
        public boolean isDead() {
            return lifetime <= 0;
        }
    }
}

// Usage in game:
class GamePanel extends JPanel {
    private ArrayList<ExplosionEffect> explosions;
    
    public void onAsteroidDestroyed(Asteroid ast) {
        ExplosionEffect explosion = new ExplosionEffect();
        explosion.createExplosion(
            ast.getX(),
            ast.getY(),
            20,  // 20 particles
            Color.ORANGE
        );
        explosions.add(explosion);
    }
    
    public void updateEffects() {
        for (int i = explosions.size() - 1; i >= 0; i--) {
            ExplosionEffect exp = explosions.get(i);
            exp.update();
            
            if (exp.isFinished()) {
                explosions.remove(i);
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Render explosions
        for (ExplosionEffect exp : explosions) {
            exp.render(g2d);
        }
    }
}
```
</details>

---

## Chapter 16: Audio with Java Sound API

### 📚 Theory: Java Sound API

**Clip (javax.sound.sampled.Clip):**
- Loads entire audio file into memory
- Good for short sound effects
- Can loop continuously

**AudioInputStream:**
- Reads audio data
- Supports WAV, AIFF, AU formats

**Key Methods:**
```java
// Load audio
AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
Clip clip = AudioSystem.getClip();
clip.open(audioIn);

// Play audio
clip.start();
clip.loop(Clip.LOOP_CONTINUOUSLY);
clip.stop();
```

### 💻 Practice Questions

**Question 16.1: Sound Manager**
Create a sound manager to handle all game audio.

**Requirements:**
- Load multiple sound effects (shoot, explosion, collision)
- Play sounds on demand
- Background music that loops
- Volume control
- Handle missing audio files

```java
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {
    // YOUR CODE HERE
}
```

<details>
<summary>💡 Solution</summary>

```java
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {
    private HashMap<String, Clip> sounds;
    private Clip backgroundMusic;
    private float masterVolume = 1.0f;
    private boolean soundEnabled = true;
    
    public SoundManager() {
        sounds = new HashMap<>();
        loadSounds();
    }
    
    private void loadSounds() {
        // Load sound effects
        loadSound("shoot", "resources/sounds/shoot.wav");
        loadSound("explosion", "resources/sounds/explosion.wav");
        loadSound("collision", "resources/sounds/collision.wav");
        loadSound("powerup", "resources/sounds/powerup.wav");
        
        // Load background music
        loadBackgroundMusic("resources/sounds/background_music.wav");
    }
    
    private void loadSound(String name, String filepath) {
        try {
            File soundFile = new File(filepath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            sounds.put(name, clip);
            
            System.out.println("Loaded sound: " + name);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound " + name + ": " + e.getMessage());
        }
    }
    
    private void loadBackgroundMusic(String filepath) {
        try {
            File musicFile = new File(filepath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioIn);
            
            System.out.println("Loaded background music");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
    }
    
    public void playSound(String name) {
        if (!soundEnabled) return;
        
        Clip clip = sounds.get(name);
        if (clip != null) {
            // Restart if already playing
            clip.setFramePosition(0);
            clip.start();
        }
    }
    
    public void playBackgroundMusic() {
        if (backgroundMusic != null && soundEnabled) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            setVolume(backgroundMusic, masterVolume * 0.5f); // Lower volume for music
        }
    }
    
    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    
    public void setMasterVolume(float volume) {
        masterVolume = Math.max(0, Math.min(1, volume));
        
        // Update volume for all clips
        for (Clip clip : sounds.values()) {
            setVolume(clip, masterVolume);
        }
        
        if (backgroundMusic != null) {
            setVolume(backgroundMusic, masterVolume * 0.5f);
        }
    }
    
    private void setVolume(Clip clip, float volume) {
        try {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume) + volumeControl.getMinimum();
            volumeControl.setValue(gain);
        } catch (Exception e) {
            System.err.println("Error setting volume: " + e.getMessage());
        }
    }
    
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            stopAllSounds();
        }
    }
    
    public void stopAllSounds() {
        for (Clip clip : sounds.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
        stopBackgroundMusic();
    }
    
    public void cleanup() {
        for (Clip clip : sounds.values()) {
            clip.close();
        }
        if (backgroundMusic != null) {
            backgroundMusic.close();
        }
    }
}

// Usage in game:
class AsteroidGame {
    private SoundManager soundManager;
    
    public AsteroidGame() {
        soundManager = new SoundManager();
        soundManager.playBackgroundMusic();
    }
    
    public void onPlayerShoot() {
        soundManager.playSound("shoot");
    }
    
    public void onAsteroidDestroyed() {
        soundManager.playSound("explosion");
    }
    
    public void onCollision() {
        soundManager.playSound("collision");
    }
    
    public void onPowerUpCollected() {
        soundManager.playSound("powerup");
    }
}
```
</details>

---

## Chapter 17: Complete Game Assembly

### 💻 Final Question: Complete Asteroid Destroyer Game

**Requirements:**
- Assemble all components into working game
- Proper package structure
- Graphics with JFrame
- Sound effects
- Collision detection
- Score tracking
- Multiple levels
- Game over and restart functionality

<details>
<summary>💡 Complete Game Solution</summary>

```java
// Main Game Class
package com.asteroidgame;

import com.asteroidgame.entities.*;
import com.asteroidgame.managers.*;
import com.asteroidgame.graphics.*;
import com.asteroidgame.audio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AsteroidDestroyerGame extends JPanel implements Runnable {
    // Game state
    private boolean running;
    private boolean paused;
    private Thread gameThread;
    
    // Managers
    private GameObjectManager objectManager;
    private CollisionHandler collisionHandler;
    private SoundManager soundManager;
    private SpriteManager spriteManager;
    
    // Player
    private SpaceShip player;
    private BulletManager bulletManager;
    
    // Asteroids
    private ArrayList<Asteroid> asteroids;
    
    // Effects
    private ArrayList<ExplosionEffect> explosions;
    
    // UI
    private int level = 1;
    
    public AsteroidDestroyerGame() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        initializeGame();
        addKeyListener(new GameKeyListener());
        
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    private void initializeGame() {
        objectManager = new GameObjectManager();
        collisionHandler = new CollisionHandler();
        soundManager = new SoundManager();
        spriteManager = new SpriteManager();
        bulletManager = new BulletManager();
        explosions = new ArrayList<>();
        
        // Create player
        player = new SpaceShip(400, 300);
        objectManager.addObject(player);
        
        // Create initial asteroids
        spawnAsteroids(5);
        
        // Start music
        soundManager.playBackgroundMusic();
    }
    
    private void spawnAsteroids(int count) {
        for (int i = 0; i < count; i++) {
            Asteroid ast = new Asteroid(
                Math.random() * 800,
                Math.random() * 600,
                Math.random() * 360,
                1 + Math.random() * 2,
                5
            );
            objectManager.addObject(ast);
        }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;
        double delta = 0;
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if (delta >= 1) {
                if (!paused) {
                    update();
                }
                repaint();
                delta--;
            }
        }
    }
    
    private void update() {
        // Update all objects
        objectManager.updateAll();
        bulletManager.updateAll();
        
        // Check collisions
        collisionHandler.checkAllCollisions(objectManager);
        bulletManager.checkAsteroidHits(objectManager, this);
        
        // Update effects
        for (int i = explosions.size() - 1; i >= 0; i--) {
            ExplosionEffect exp = explosions.get(i);
            exp.update();
            if (exp.isFinished()) {
                explosions.remove(i);
            }
        }
        
        // Remove inactive objects
        objectManager.removeInactive();
        
        // Check level complete
        if (objectManager.getObjectsByType(Asteroid.class).isEmpty()) {
            levelComplete();
        }
        
        // Check game over
        if (player.getLives() <= 0) {
            gameOver();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw background
        spriteManager.drawBackground(g2d);
        
        // Draw all game objects
        for (GameObject obj : objectManager.getAllObjects()) {
            if (obj instanceof SpaceShip) {
                spriteManager.drawShip(g2d, (SpaceShip) obj);
            } else if (obj instanceof Asteroid) {
                spriteManager.drawAsteroid(g2d, (Asteroid) obj);
            }
        }
        
        // Draw bullets
        for (Bullet bullet : bulletManager.getBullets()) {
            spriteManager.drawBullet(g2d, bullet);
        }
        
        // Draw explosions
        for (ExplosionEffect exp : explosions) {
            exp.render(g2d);
        }
        
        // Draw UI
        drawUI(g2d);
        
        if (paused) {
            drawPauseScreen(g2d);
        }
    }
    
    private void drawUI(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Lives: " + player.getLives(), 10, 25);
        g2d.drawString("Score: " + player.getScore(), 10, 50);
        g2d.drawString("Level: " + level, 10, 75);
    }
    
    private void drawPauseScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("PAUSED", 300, 280);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Press ESC to resume", 290, 320);
    }
    
    public void onAsteroidDestroyed(Asteroid ast) {
        ExplosionEffect exp = new ExplosionEffect();
        exp.createExplosion(ast.getX(), ast.getY(), 20, Color.ORANGE);
        explosions.add(exp);
        soundManager.playSound("explosion");
        player.addScore(ast.getPointValue());
    }
    
    private void levelComplete() {
        level++;
        spawnAsteroids(5 + level);
    }
    
    private void gameOver() {
        paused = true;
        soundManager.stopBackgroundMusic();
        // Show game over screen
    }
    
    private class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (paused && e.getKeyCode() != KeyEvent.VK_ESCAPE) return;
            
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    player.accelerate(0.5);
                    break;
                case KeyEvent.VK_LEFT:
                    player.rotate(-5);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.rotate(5);
                    break;
                case KeyEvent.VK_SPACE:
                    bulletManager.fireBullet(player.getX(), player.getY(), player.getAngle());
                    soundManager.playSound("shoot");
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Asteroid Destroyer");
            AsteroidDestroyerGame game = new AsteroidDestroyerGame();
            
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            
            game.requestFocusInWindow();
        });
    }
}
```
</details>

---

## 🎓 Conclusion & Next Steps

Congratulations, Space Cadet! You've completed your training and built a fully functional Asteroid Destroyer game while mastering Java concepts!

### What You've Learned:
✅ Java syntax and basic programming concepts
✅ Object-Oriented Programming (OOP) principles
✅ Data structures (Arrays, ArrayList, HashMap)
✅ Inheritance and Polymorphism
✅ Interfaces and Abstract Classes
✅ Access modifiers and encapsulation
✅ Packages and file organization
✅ Graphics programming with Swing
✅ Audio implementation
✅ Game loop and real-time rendering

### To Run Your Game:
1. Organize files into package structure
2. Add sound files to `resources/sounds/`
3. Add image files to `resources/`
4. Compile: `javac com/asteroidgame/*.java`
5. Run: `java com.asteroidgame.AsteroidDestroyerGame`

### Enhancement Ideas:
- Add more power-ups (shields, rapid fire, multi-shot)
- Implement wave system with increasing difficulty
- Add boss battles at certain levels
- Create multiplayer support
- Add particle trails for movement
- Implement high score persistence (file I/O)
- Add different enemy types

**🚀 Happy Coding! 🚀**