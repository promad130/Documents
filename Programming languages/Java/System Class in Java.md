The `System` class is part of `java.lang` package and provides access to system-level resources and utilities. It's **automatically imported** (no need for `import` statement).

---
## Class Overview

```java
public final class System extends Object
```

- **`final`** - Cannot be subclassed
- **All methods are `static`** - Call directly without creating objects
- **Cannot instantiate** - Constructor is private

```java
// ✓ Correct
System.out.println("Hello");

// ✗ Wrong - Cannot create System object
System sys = new System(); // COMPILE ERROR!
```

---
## Standard I/O Streams ([[Input&Output in Java]] is a must)
### **`System.out`** - Standard Output (Print to console)

```java
// Print with newline
System.out.println("Hello World");
System.out.println(42);
System.out.println(3.14);

// Print without newline
System.out.print("Hello ");
System.out.print("World"); // Output: Hello World

// Formatted output (like printf in C)
System.out.printf("Name: %s, Age: %d, Score: %.2f\n", "Alice", 25, 95.5);
// Output: Name: Alice, Age: 25, Score: 95.50

// Format specifiers:
// %s - String
// %d - Integer
// %f - Float/Double
// %.2f - Float with 2 decimal places
// %n - Platform-independent newline
```

**Game Example:**
```java
public class Player {
    private String name;
    private int level;
    private double health;
    
    public void displayStats() {
        System.out.println("=== Player Stats ===");
        System.out.printf("Name: %s\n", name);
        System.out.printf("Level: %d\n", level);
        System.out.printf("Health: %.1f%%\n", health);
        System.out.println("===================");
    }
}
```

---
### **`System.in`** - Standard Input (Read from console)

```java
import java.util.Scanner;

public class InputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        
        System.out.println("Hello " + name + ", age " + age);
        scanner.close();
    }
}
```

**Game Example:**
```java
public class GameMenu {
    private Scanner scanner = new Scanner(System.in);
    
    public void showMenu() {
        System.out.println("1. Start Game");
        System.out.println("2. Load Game");
        System.out.println("3. Exit");
        System.out.print("Choice: ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1: startGame(); break;
            case 2: loadGame(); break;
            case 3: System.exit(0); break;
            default: System.err.println("Invalid choice!");
        }
    }
}
```

---
### **`System.err`** - Standard Error Output (Error messages)

```java
// Normal output
System.out.println("Game started successfully");

// Error output (usually appears in red in IDEs)
System.err.println("ERROR: Failed to load game!");
System.err.println("WARNING: Low memory detected");
```

**When to use:**
- ✓ Error messages
- ✓ Warnings
- ✓ Debug information
- ✗ Regular game output

```java
public class GameLoader {
    public void loadLevel(String filename) {
        try {
            // Load level...
            System.out.println("Level loaded: " + filename);
        } catch (Exception e) {
            System.err.println("ERROR: Cannot load level " + filename);
            System.err.println("Reason: " + e.getMessage());
        }
    }
}
```

---
## Time & Performance
### **`System.currentTimeMillis()`** - Current time in milliseconds

```java
long currentTime = System.currentTimeMillis();
System.out.println(currentTime); // e.g., 1731850234567
```

**Use Cases:**
```java
// 1. Measure execution time
long start = System.currentTimeMillis();
performHeavyCalculation();
long end = System.currentTimeMillis();
System.out.println("Took: " + (end - start) + "ms");

// 2. Cooldown timer
public class Weapon {
    private long lastFireTime = 0;
    private static final long FIRE_RATE = 500; // 500ms
    
    public boolean canFire() {
        long now = System.currentTimeMillis();
        return (now - lastFireTime) >= FIRE_RATE;
    }
    
    public void fire() {
        if (canFire()) {
            // Fire weapon
            lastFireTime = System.currentTimeMillis();
        }
    }
}

// 3. Timestamp for saves
public void saveGame() {
    long timestamp = System.currentTimeMillis();
    String filename = "save_" + timestamp + ".dat";
    System.out.println("Saving to: " + filename);
}
```

---
### **`System.nanoTime()`** - High-precision timing (nanoseconds)
```java
long startNano = System.nanoTime();
// Do something...
long endNano = System.nanoTime();
long elapsedNano = endNano - startNano;
long elapsedMillis = elapsedNano / 1_000_000;

System.out.println("Elapsed: " + elapsedNano + " nanoseconds");
System.out.println("Elapsed: " + elapsedMillis + " milliseconds");
```

**When to use:**
- ✓ Precise performance measurement
- ✓ Frame timing in games
- ✓ Benchmarking code
- ✗ Actual timestamps (use `currentTimeMillis()` instead)

```java
public class PerformanceMonitor {
    public void benchmarkMethod() {
        long start = System.nanoTime();
        
        // Test code
        for (int i = 0; i < 1000000; i++) {
            Math.sqrt(i);
        }
        
        long end = System.nanoTime();
        double milliseconds = (end - start) / 1_000_000.0;
        
        System.out.printf("Execution time: %.3f ms\n", milliseconds);
    }
}
```

---
## System Properties
### **`System.getProperty()`** - Get system information
```java
// Operating System
String os = System.getProperty("os.name");
System.out.println("OS: " + os); // Windows 10, Mac OS X, Linux

String osVersion = System.getProperty("os.version");
System.out.println("OS Version: " + osVersion);

// Java Version
String javaVersion = System.getProperty("java.version");
System.out.println("Java: " + javaVersion); // 17.0.1

// User Information
String userName = System.getProperty("user.name");
System.out.println("User: " + userName);

String userHome = System.getProperty("user.home");
System.out.println("Home: " + userHome); // C:\Users\John

String userDir = System.getProperty("user.dir");
System.out.println("Current Dir: " + userDir);

// File Separator (OS-specific)
String fileSep = System.getProperty("file.separator");
System.out.println("File separator: " + fileSep); // \ on Windows, / on Unix

// Line Separator (OS-specific)
String lineSep = System.getProperty("line.separator");
System.out.println("Line separator: " + lineSep.length() + " chars");
```

**Common Properties:**

| Property | Description | Example |
|----------|-------------|---------|
| `os.name` | Operating system name | "Windows 10" |
| `os.version` | OS version | "10.0" |
| `os.arch` | System architecture | "amd64" |
| `java.version` | Java version | "17.0.1" |
| `java.home` | Java installation directory | "C:\Program Files\Java\jdk-17" |
| `user.name` | User account name | "john" |
| `user.home` | User home directory | "C:\Users\john" |
| `user.dir` | Current working directory | "C:\MyProject" |
| `file.separator` | File separator | "\" or "/" |
| `path.separator` | Path separator | ";" or ":" |
| `line.separator` | Line separator | "\r\n" or "\n" |

**Game Example:**
```java
public class GameSaveManager {
    private String getSaveDirectory() {
        String userHome = System.getProperty("user.home");
        String fileSep = System.getProperty("file.separator");
        String gameDir = userHome + fileSep + "MyGame" + fileSep + "saves";
        
        System.out.println("Save directory: " + gameDir);
        return gameDir;
    }
    
    public void checkSystemRequirements() {
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        
        System.out.println("System Requirements Check:");
        System.out.println("OS: " + os);
        System.out.println("Java: " + javaVersion);
        
        if (javaVersion.startsWith("1.8") || javaVersion.startsWith("11")) {
            System.out.println("✓ Java version compatible");
        } else {
            System.err.println("⚠ Warning: Untested Java version");
        }
    }
}
```

---
### **`System.setProperty()`** - Set system properties

```java
// Set custom property
System.setProperty("game.difficulty", "hard");
System.setProperty("game.volume", "80");

// Read it back
String difficulty = System.getProperty("game.difficulty");
String volume = System.getProperty("game.volume");

System.out.println("Difficulty: " + difficulty);
System.out.println("Volume: " + volume);
```

---
## Program Control
### 🚪 **`System.exit(int status)`** - Terminate JVM
```java
System.exit(0);  // Exit with success status
System.exit(1);  // Exit with error status
```

**Status Codes:**
- `0` - Normal termination (success)
- `Non-zero` - Abnormal termination (error)

```java
public class Game {
    public void quit() {
        System.out.println("Saving game...");
        saveGame();
        System.out.println("Goodbye!");
        System.exit(0); // Exit normally
    }
    
    public void criticalError() {
        System.err.println("CRITICAL ERROR: Cannot continue!");
        System.exit(1); // Exit with error code
    }
}
```

**Warning:** `System.exit()` immediately terminates the program - no finally blocks execute!

---
## Array Operations
### **`System.arraycopy()`** - Fast array copying

```java
System.arraycopy(src, srcPos, dest, destPos, length);
```

**Parameters:**
- `src` - Source array
- `srcPos` - Starting position in source
- `dest` - Destination array
- `destPos` - Starting position in destination
- `length` - Number of elements to copy

```java
// Example 1: Copy entire array
int[] source = {1, 2, 3, 4, 5};
int[] destination = new int[5];

System.arraycopy(source, 0, destination, 0, source.length);
// destination = {1, 2, 3, 4, 5}

// Example 2: Copy part of array
int[] scores = {100, 95, 80, 75, 60};
int[] topScores = new int[3];

System.arraycopy(scores, 0, topScores, 0, 3);
// topScores = {100, 95, 80}

// Example 3: Copy to middle of array
int[] original = {1, 2, 3, 4, 5};
int[] expanded = new int[10];

System.arraycopy(original, 0, expanded, 2, original.length);
// expanded = {0, 0, 1, 2, 3, 4, 5, 0, 0, 0}
```

**Game Example:**
```java
public class Inventory {
    private Item[] items = new Item[10];
    private int size = 0;
    
    public void expandInventory() {
        // Double the inventory size
        Item[] newItems = new Item[items.length * 2];
        
        // Copy old items to new array (FAST!)
        System.arraycopy(items, 0, newItems, 0, items.length);
        
        items = newItems;
        System.out.println("Inventory expanded to " + items.length + " slots");
    }
    
    public void removeItem(int index) {
        // Shift elements left to fill gap
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[--size] = null;
    }
}
```

**Why use `arraycopy()` instead of loop?**
- **Much faster** - native implementation
- **Less code** - one line vs multiple
- **Fewer bugs** - no off-by-one errors

```java
// Manual copy (slower)
for (int i = 0; i < length; i++) {
    dest[i] = source[i];
}

// System.arraycopy (faster)
System.arraycopy(source, 0, dest, 0, length);
```

---
## Garbage Collection
### **`System.gc()`** - Suggest garbage collection

```java
System.gc(); // Request garbage collection
```

**Important Notes:**
- Only a **suggestion** to JVM - not guaranteed to run
- JVM decides when to actually collect garbage
- **Rarely needed** - JVM manages memory automatically
- Can cause performance hiccups if called too often

```java
public class ResourceManager {
    public void clearUnusedAssets() {
        // Clear references to unused objects
        unusedTextures = null;
        unusedSounds = null;
        
        // Suggest GC (optional - JVM will do it anyway)
        System.gc();
        
        System.out.println("Memory cleanup requested");
    }
}
```

**When you might use it:**
- After loading a large level (clear old level assets)
- Before loading new resources
- In memory-constrained situations

**Best Practice:** Let JVM handle garbage collection automatically!

---
## Environment Variables
### **`System.getenv()`** - Get environment variables

```java
// Get all environment variables
Map<String, String> env = System.getenv();
for (String key : env.keySet()) {
    System.out.println(key + " = " + env.get(key));
}

// Get specific variable
String path = System.getenv("PATH");
String javaHome = System.getenv("JAVA_HOME");
String username = System.getenv("USERNAME"); // Windows
String user = System.getenv("USER"); // Unix/Linux

System.out.println("PATH: " + path);
System.out.println("JAVA_HOME: " + javaHome);
```

**Game Example:**
```java
public class GameConfig {
    public void loadConfig() {
        // Check for debug mode from environment
        String debugMode = System.getenv("GAME_DEBUG");
        if ("true".equals(debugMode)) {
            System.out.println("Debug mode enabled");
            enableDebugFeatures();
        }
        
        // Custom game directory
        String gameDir = System.getenv("GAME_DIR");
        if (gameDir != null) {
            System.out.println("Using custom directory: " + gameDir);
        } else {
            gameDir = System.getProperty("user.home") + "/MyGame";
            System.out.println("Using default directory: " + gameDir);
        }
    }
}
```

---
## Identity Hash Code
### **`System.identityHashCode()`** - Object's memory address hash

```java
Object obj1 = new Object();
Object obj2 = new Object();

int hash1 = System.identityHashCode(obj1);
int hash2 = System.identityHashCode(obj2);

System.out.println("obj1 hash: " + hash1); // e.g., 366712642
System.out.println("obj2 hash: " + hash2); // e.g., 1829164700
```

**Use Case:** Debugging object identity

```java
public class ObjectPool {
    private List<Enemy> pool = new ArrayList<>();
    
    public void debugPool() {
        System.out.println("=== Object Pool ===");
        for (Enemy enemy : pool) {
            int hash = System.identityHashCode(enemy);
            System.out.printf("Enemy@%x (active: %b)\n", hash, enemy.isActive());
        }
    }
}
```

---
## Line Separator
### **`System.lineSeparator()`** - Platform-specific newline

```java
String newline = System.lineSeparator();

// Cross-platform file writing
String content = "Line 1" + System.lineSeparator() +
                "Line 2" + System.lineSeparator() +
                "Line 3";

// Better than:
// String content = "Line 1\n Line 2\n Line 3"; // Won't work correctly on Windows
```

**Why it matters:**
- Windows: `\r\n` (CRLF)
- Unix/Linux/Mac: `\n` (LF)
- Old Mac: `\r` (CR)

```java
public class Logger {
    public void log(String message) {
        String timestamp = new Date().toString();
        String line = timestamp + ": " + message + System.lineSeparator();
        
        // Write to file (works on all OS)
        writeToFile(line);
    }
}
```

---
## Complete Game Example

```java
public class AsteroidGame {
    private boolean running = true;
    private long lastFrameTime;
    private int fps = 0;
    
    public void start() {
        System.out.println("=== Asteroid Game ===");
        checkSystemRequirements();
        loadGameData();
        
        lastFrameTime = System.nanoTime();
        gameLoop();
    }
    
    private void checkSystemRequirements() {
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        
        System.out.println("OS: " + os);
        System.out.println("Java: " + javaVersion);
        
        if (!javaVersion.startsWith("17") && !javaVersion.startsWith("11")) {
            System.err.println("Warning: Recommended Java 11 or 17");
        }
    }
    
    private void loadGameData() {
        String saveDir = System.getProperty("user.home") + 
                        System.getProperty("file.separator") + 
                        "AsteroidGame";
        
        System.out.println("Save directory: " + saveDir);
    }
    
    private void gameLoop() {
        while (running) {
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            
            // Update game
            update(deltaTime);
            render();
            
            // Calculate FPS
            fps++;
            if (fps % 60 == 0) {
                double ms = deltaTime / 1_000_000.0;
                System.out.printf("Frame time: %.2f ms (%.0f FPS)%n", 
                                ms, 1000.0 / ms);
            }
            
            // Maintain 60 FPS
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void update(long deltaTime) {
        // Game logic
    }
    
    private void render() {
        // Drawing
    }
    
    public void quit() {
        System.out.println("Saving game...");
        System.out.println("Goodbye!");
        running = false;
        System.exit(0);
    }
    
    public static void main(String[] args) {
        AsteroidGame game = new AsteroidGame();
        game.start();
    }
}
```

---
## Summary Table

| Method                       | Purpose          | Common Use              |
| ---------------------------- | ---------------- | ----------------------- |
| `System.out.println()`       | Print to console | Debugging, user output  |
| `System.err.println()`       | Print errors     | Error messages          |
| `System.in`                  | Read input       | User input              |
| `System.currentTimeMillis()` | Get time (ms)    | Timers, cooldowns       |
| `System.nanoTime()`          | Get time (ns)    | Performance measurement |
| `System.getProperty()`       | Get system info  | OS detection, paths     |
| `System.setProperty()`       | Set property     | Configuration           |
| `System.exit()`              | Quit program     | Game exit               |
| `System.arraycopy()`         | Copy arrays      | Fast array operations   |
| `System.gc()`                | Suggest GC       | Memory cleanup          |
| `System.getenv()`            | Environment vars | Configuration           |
| `System.lineSeparator()`     | Newline char     | Cross-platform files    |

<blockquote>That's the System class! The Swiss Army knife of Java!</blockquote>