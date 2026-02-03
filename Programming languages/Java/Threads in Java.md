## What is a Thread?
### Real-Life Analogy:
Imagine a **restaurant kitchen**:

**Single-threaded (1 chef):**
```
Chef does:
1. Cook pasta ⏱️ 10 min
2. Make sauce ⏱️ 5 min
3. Prepare salad ⏱️ 3 min
Total: 18 minutes
```

**Multi-threaded (3 chefs working simultaneously):**
```
Chef 1: Cook pasta ⏱️ 10 min
Chef 2: Make sauce ⏱️ 5 min  } All at the same time!
Chef 3: Prepare salad ⏱️ 3 min
Total: 10 minutes (just the longest task)
```

### In Programming:
A **thread** is like a separate worker that can execute code independently and simultaneously with other threads.

```
Main Thread:        Thread 1:         Thread 2:
|                   |                 |
| println("Hi")     | download()      | playMusic()
|                   |                 |
| game loop         | process data    | update UI
|                   |                 |
v                   v                 v
```

---
## Why Use Threads?

### Without Threads (Sequential):
```java
public class Game {
    public void start() {
        loadLevel();      // 5 seconds
        loadTextures();   // 3 seconds
        loadSounds();     // 2 seconds
        // Total: 10 seconds loading time!
        startGame();
    }
}
```

### With Threads (Parallel):
```java
public class Game {
    public void start() {
        Thread levelThread = new Thread(() -> loadLevel());
        Thread textureThread = new Thread(() -> loadTextures());
        Thread soundThread = new Thread(() -> loadSounds());
        
        levelThread.start();
        textureThread.start();
        soundThread.start();
        // Total: ~5 seconds (all load simultaneously!)
        
        // Wait for all to finish
        levelThread.join();
        textureThread.join();
        soundThread.join();
        
        startGame();
    }
}
```

---
## Thread Basics

### The Thread Class
```java
public class Thread extends Object implements Runnable
```
A thread in Java represents a **separate path of execution** in your program.

---

## Creating Threads: 3 Ways

### Method 1: Extend Thread Class
```java
// Step 1: Create a class that extends Thread
public class MyThread extends Thread {
    
    @Override
    public void run() {
        // This code runs in a separate thread
        for (int i = 0; i < 5; i++) {
            System.out.println("MyThread: " + i);
            try {
                Thread.sleep(1000); // Sleep 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Step 2: Use it
public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start(); // Starts the thread
        
        // Main thread continues here
        for (int i = 0; i < 5; i++) {
            System.out.println("Main: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

**Output:**
```
Main: 0
MyThread: 0
Main: 1
MyThread: 1
Main: 2
MyThread: 2
...
(Both run simultaneously!)
```

---
### Method 2: Implement Runnable Interface ⭐ (Preferred)
```java
// Step 1: Create a class that implements Runnable
public class MyTask implements Runnable {
    
    @Override
    public void run() {
        // This code runs in a separate thread
        for (int i = 0; i < 5; i++) {
            System.out.println("Task: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Step 2: Create Thread and pass Runnable
public class Main {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task);
        thread.start();
        
        System.out.println("Main thread continues...");
    }
}
```

**Why Runnable is better:**
- ✓ Can extend other classes (Java doesn't support multiple inheritance)
- ✓ Better separation of task and thread
- ✓ Can reuse the same Runnable with multiple threads

---
### Method 3: Anonymous Class / Lambda Expression
```java
public class Main {
    public static void main(String[] args) {
        
        // Anonymous class
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous thread running");
            }
        });
        
        // Lambda expression (shorter!) ⭐
        Thread thread2 = new Thread(() -> {
            System.out.println("Lambda thread running");
        });
        
        thread1.start();
        thread2.start();
    }
}
```

---

## Game Example: Loading Screen
```java
public class LoadingScreen {
    private boolean loading = true;
    
    public void startGame() {
        // Thread 1: Show loading animation
        Thread animationThread = new Thread(() -> {
            String[] frames = {"|", "/", "-", "\\"};
            int i = 0;
            while (loading) {
                System.out.print("\rLoading " + frames[i % 4]);
                i++;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("\nDone!");
        });
        
        // Thread 2: Load game data
        Thread loadThread = new Thread(() -> {
            System.out.println("Loading textures...");
            sleep(2000);
            System.out.println("Loading sounds...");
            sleep(2000);
            System.out.println("Loading level...");
            sleep(2000);
            loading = false;
        });
        
        animationThread.start();
        loadThread.start();
        
        try {
            loadThread.join(); // Wait for loading to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Game ready!");
    }
    
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new LoadingScreen().startGame();
    }
}
```

**Output:**
```
Loading textures...
Loading |
Loading /
Loading -
Loading \
Loading sounds...
Loading |
Loading /
...
Loading level...
Done!
Game ready!
```

---

## Thread Methods

### **`start()`** - Start the thread
```java
Thread thread = new Thread(() -> {
    System.out.println("Thread running");
});

thread.start(); // Starts a new thread and calls run()
```

**Important:**
```java
thread.start();  // ✓ Correct - runs in new thread
thread.run();    // ✗ Wrong - runs in current thread (no parallelism!)
```

---

### 2️⃣ **`run()`** - Contains the code to execute

```java
class MyThread extends Thread {
    @Override
    public void run() {
        // This code runs in the thread
        System.out.println("Running in thread: " + Thread.currentThread().getName());
    }
}
```

**Don't call directly!** Use `start()` instead.

---

### 3️⃣ **`sleep(milliseconds)`** - Pause thread execution

```java
public static void sleep(long milliseconds) throws InterruptedException
```

**Example:**
```java
System.out.println("Starting...");
Thread.sleep(3000); // Sleep for 3 seconds
System.out.println("Done sleeping!");
```

**Game Example - Blinking Effect:**
```java
public class BlinkingText extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("PRESS START");
            try {
                Thread.sleep(500); // Show for 0.5 sec
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.print("\r             \r"); // Clear
            try {
                Thread.sleep(500); // Hide for 0.5 sec
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

### 4️⃣ **`join()`** - Wait for thread to finish

```java
public void join() throws InterruptedException
public void join(long milliseconds) throws InterruptedException
```

**Example:**
```java
Thread worker = new Thread(() -> {
    System.out.println("Working...");
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Work done!");
});

worker.start();
System.out.println("Waiting for worker...");

worker.join(); // Main thread waits here until worker finishes

System.out.println("Continuing main thread");
```

**Output:**
```
Working...
Waiting for worker...
Work done!
Continuing main thread
```

**With timeout:**
```java
worker.join(1000); // Wait max 1 second
if (worker.isAlive()) {
    System.out.println("Worker still running!");
}
```

**Game Example - Level Loading:**
```java
public class LevelLoader {
    public void loadLevel() {
        Thread loadThread = new Thread(() -> {
            System.out.println("Loading level data...");
            // Simulate loading
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        loadThread.start();
        
        try {
            loadThread.join(); // Wait for loading to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Level ready! Starting game...");
    }
}
```

---

### 5️⃣ **`isAlive()`** - Check if thread is running

```java
Thread thread = new Thread(() -> {
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});

System.out.println("Before start: " + thread.isAlive()); // false

thread.start();
System.out.println("After start: " + thread.isAlive());  // true

Thread.sleep(3000);
System.out.println("After finish: " + thread.isAlive()); // false
```

---

### 6️⃣ **`getName()` / `setName()`** - Thread name

```java
Thread thread = new Thread(() -> {
    System.out.println("Running in: " + Thread.currentThread().getName());
});

thread.setName("MyWorkerThread");
thread.start();

System.out.println("Thread name: " + thread.getName());
```

**Output:**
```
Thread name: MyWorkerThread
Running in: MyWorkerThread
```

**Useful for debugging:**
```java
public class GameThreads {
    public void start() {
        Thread renderThread = new Thread(() -> render());
        Thread physicsThread = new Thread(() -> physics());
        Thread aiThread = new Thread(() -> ai());
        
        renderThread.setName("RenderThread");
        physicsThread.setName("PhysicsThread");
        aiThread.setName("AIThread");
        
        renderThread.start();
        physicsThread.start();
        aiThread.start();
    }
    
    private void render() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " rendering...");
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }
    
    // Similar for physics() and ai()
}
```

---

### 7️⃣ **`currentThread()`** - Get current thread

```java
public static Thread currentThread()
```

**Example:**
```java
public class ThreadInfo {
    public static void main(String[] args) {
        Thread current = Thread.currentThread();
        
        System.out.println("Name: " + current.getName());
        System.out.println("ID: " + current.getId());
        System.out.println("Priority: " + current.getPriority());
        System.out.println("State: " + current.getState());
        System.out.println("Is alive: " + current.isAlive());
    }
}
```

**Output:**
```
Name: main
ID: 1
Priority: 5
State: RUNNABLE
Is alive: true
```

---

### 8️⃣ **`setPriority()` / `getPriority()`** - Thread priority

```java
public static final int MIN_PRIORITY = 1;
public static final int NORM_PRIORITY = 5;
public static final int MAX_PRIORITY = 10;
```

**Example:**
```java
Thread highPriority = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("High priority: " + i);
    }
});

Thread lowPriority = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Low priority: " + i);
    }
});

highPriority.setPriority(Thread.MAX_PRIORITY); // 10
lowPriority.setPriority(Thread.MIN_PRIORITY);  // 1

highPriority.start();
lowPriority.start();
```

**⚠️ Note:** Priority is just a **hint** to the OS scheduler - not guaranteed!

**Game Example:**
```java
public class GameEngine {
    public void start() {
        Thread renderThread = new Thread(() -> render());
        Thread backgroundThread = new Thread(() -> downloadUpdates());
        
        renderThread.setPriority(Thread.MAX_PRIORITY);      // Critical!
        backgroundThread.setPriority(Thread.MIN_PRIORITY);  // Can wait
        
        renderThread.start();
        backgroundThread.start();
    }
}
```

---

### 9️⃣ **`interrupt()`** - Interrupt a thread

```java
Thread worker = new Thread(() -> {
    try {
        while (true) {
            System.out.println("Working...");
            Thread.sleep(1000);
        }
    } catch (InterruptedException e) {
        System.out.println("Interrupted! Stopping work.");
    }
});

worker.start();

Thread.sleep(3000);
worker.interrupt(); // Stop the worker
```

**Check if interrupted:**
```java
Thread worker = new Thread(() -> {
    while (!Thread.interrupted()) {
        System.out.println("Working...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            break; // Exit loop
        }
    }
    System.out.println("Cleanup and exit");
});
```

---

### 🔟 **`yield()`** - Give other threads a chance

```java
public static void yield()
```

Suggests to the scheduler to give CPU time to other threads.

```java
Thread thread1 = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Thread 1: " + i);
        Thread.yield(); // Let other threads run
    }
});

Thread thread2 = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Thread 2: " + i);
        Thread.yield();
    }
});

thread1.start();
thread2.start();
```

---

### 1️⃣1️⃣ **Daemon Threads** - Background threads

```java
thread.setDaemon(true); // Must be called before start()
```

**Daemon threads** are background threads that automatically terminate when all user threads finish.

```java
Thread backgroundThread = new Thread(() -> {
    while (true) {
        System.out.println("Background task running...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            break;
        }
    }
});

backgroundThread.setDaemon(true); // Daemon thread
backgroundThread.start();

Thread.sleep(3000);
System.out.println("Main thread exiting...");
// Program exits, daemon thread automatically stops
```

**Game Example - Auto-save:**
```java
public class AutoSave {
    public void startAutoSave() {
        Thread autoSaveThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Every 1 minute
                    System.out.println("Auto-saving game...");
                    saveGame();
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        
        autoSaveThread.setDaemon(true); // Stops when game exits
        autoSaveThread.start();
    }
    
    private void saveGame() {
        // Save game data
    }
}
```

---

## 🎮 Complete Game Example: Multi-threaded Asteroid Game

```java
public class AsteroidGameThreaded {
    private volatile boolean running = true;
    private SpaceShip player;
    private List<Asteroid> asteroids;
    
    // Thread-safe collections
    private CopyOnWriteArrayList<Bullet> bullets;
    private CopyOnWriteArrayList<Explosion> explosions;
    
    public void start() {
        player = new SpaceShip(400, 300);
        asteroids = new CopyOnWriteArrayList<>();
        bullets = new CopyOnWriteArrayList<>();
        explosions = new CopyOnWriteArrayList<>();
        
        // Thread 1: Game Logic (60 FPS)
        Thread gameThread = new Thread(() -> gameLoop(), "GameThread");
        gameThread.setPriority(Thread.MAX_PRIORITY);
        
        // Thread 2: Rendering (60 FPS)
        Thread renderThread = new Thread(() -> renderLoop(), "RenderThread");
        renderThread.setPriority(Thread.MAX_PRIORITY);
        
        // Thread 3: Physics (30 FPS - less frequent)
        Thread physicsThread = new Thread(() -> physicsLoop(), "PhysicsThread");
        physicsThread.setPriority(Thread.NORM_PRIORITY);
        
        // Thread 4: AI (10 FPS - even less frequent)
        Thread aiThread = new Thread(() -> aiLoop(), "AIThread");
        aiThread.setPriority(Thread.NORM_PRIORITY);
        
        // Thread 5: Auto-save (daemon)
        Thread autoSaveThread = new Thread(() -> autoSaveLoop(), "AutoSave");
        autoSaveThread.setDaemon(true);
        
        // Start all threads
        gameThread.start();
        renderThread.start();
        physicsThread.start();
        aiThread.start();
        autoSaveThread.start();
        
        System.out.println("All threads started!");
    }
    
    private void gameLoop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / 60.0;
        double delta = 0;
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if (delta >= 1) {
                updateGame();
                delta--;
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    private void renderLoop() {
        while (running) {
            render();
            
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    private void physicsLoop() {
        while (running) {
            checkCollisions();
            
            try {
                Thread.sleep(33); // ~30 FPS
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    private void aiLoop() {
        while (running) {
            updateAI();
            
            try {
                Thread.sleep(100); // ~10 FPS
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    private void autoSaveLoop() {
        while (true) {
            try {
                Thread.sleep(60000); // Every minute
                System.out.println("[" + Thread.currentThread().getName() + "] Auto-saving...");
                saveGame();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    private void updateGame() {
        player.update();
        for (Asteroid ast : asteroids) {
            ast.update();
        }
        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }
    
    private void render() {
        // Draw everything
        System.out.print(".");
    }
    
    private void checkCollisions() {
        // Collision detection
    }
    
    private void updateAI() {
        // AI logic
    }
    
    private void saveGame() {
        // Save game state
    }
    
    public void stop() {
        running = false;
        System.out.println("Stopping all threads...");
    }
    
    public static void main(String[] args) throws InterruptedException {
        AsteroidGameThreaded game = new AsteroidGameThreaded();
        game.start();
        
        Thread.sleep(10000); // Run for 10 seconds
        game.stop();
    }
}
```

---

## ⚠️ Thread Safety Issues

### Problem: Race Condition

```java
public class Counter {
    private int count = 0;
    
    public void increment() {
        count++; // NOT thread-safe!
    }
    
    public int getCount() {
        return count;
    }
}

// Two threads incrementing
Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment();
    }
});

Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment();
    }
});

t1.start();
t2.start();
t1.join();
t2.join();

System.out.println("Count: " + counter.getCount());
// Expected: 2000
// Actual: 1843 (or some other wrong number!)
```

---

### Solution 1: `synchronized` Keyword

```java
public class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++; // Now thread-safe!
    }
    
    public synchronized int getCount() {
        return count;
    }
}
```

**How it works:** Only **one thread at a time** can execute synchronized methods.

```java
Thread 1: [increment] waiting... waiting... [increment]
Thread 2: waiting... [increment] [increment] waiting...
```

---

### Solution 2: `synchronized` Block

```java
public class GameScore {
    private int score = 0;
    private final Object lock = new Object();
    
    public void addScore(int points) {
        synchronized (lock) {
            score += points; // Only this part is synchronized
        }
    }
    
    public int getScore() {
        synchronized (lock) {
            return score;
        }
    }
}
```

---

### Solution 3: `volatile` Keyword

```java
public class GameState {
    private volatile boolean gameOver = false;
    
    public void setGameOver(boolean value) {
        gameOver = value; // Visible to all threads immediately
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
}
```

**`volatile`** ensures changes are immediately visible to all threads.

---

### Solution 4: Atomic Classes

```java
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeCounter {
    private AtomicInteger count = new AtomicInteger(0);
    
    public void increment() {
        count.incrementAndGet(); // Thread-safe!
    }
    
    public int getCount() {
        return count.get();
    }
}
```

**Atomic classes:**
- `AtomicInteger`
- `AtomicLong`
- `AtomicBoolean`
- `AtomicReference`

---

## 🎮 Game Example: Thread-Safe Score System

```java
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreSystem {
    private AtomicInteger score = new AtomicInteger(0);
    private AtomicInteger highScore = new AtomicInteger(0);
    
    public void addScore(int points) {
        int newScore = score.addAndGet(points);
        
        // Update high score if needed
        highScore.updateAndGet(current -> Math.max(current, newScore));
        
        System.out.println("Score: " + newScore + " (High: " + highScore.get() + ")");
    }
    
    public void resetScore() {
        score.set(0);
    }
    
    public int getScore() {
        return score.get();
    }
    
    public int getHighScore() {
        return highScore.get();
    }
}

// Usage with multiple threads
public class Game {
    private ScoreSystem scoreSystem = new ScoreSystem();
    
    public void start() {
        // Multiple threads can safely add scores
        Thread player1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                scoreSystem.addScore(10);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        
        Thread player2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                scoreSystem.addScore(15);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        
        player1.start();
        player2.start();
    }
}
```

---

## 📊 Thread States

```java
public enum State {
    NEW,          // Created but not started
    RUNNABLE,     // Running or ready to run
    BLOCKED,      // Blocked waiting for a lock
    WAITING,      // Waiting indefinitely
    TIMED_WAITING, // Waiting for a specific time
    TERMINATED    // Finished execution
}
```

**Diagram:**
```
NEW
 |
 | thread.start()
 v
RUNNABLE ←→ RUNNING
 |           ↓
 |      sleep/wait
 |           ↓
 |     TIMED_WAITING
 |           ↓
 |      time expires
 |           ↓
 └─────→ RUNNABLE
             ↓
         TERMINATED
```

**Check thread state:**
```java
Thread thread = new Thread(() -> {
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {}
});

System.out.println(thread.getState()); // NEW
thread.start();
System.out.println(thread.getState()); // RUNNABLE or TIMED_WAITING
Thread.sleep(3000);
System.out.println(thread.getState()); // TERMINATED
```

---

## 🎯 Best Practices

### ✅ DO:
1. **Use Runnable over Thread** - more flexible
2. **Name your threads** - easier debugging
3. **Handle InterruptedException** - always catch
4. **Use thread-safe collections** - `ConcurrentHashMap`, `CopyOnWriteArrayList`
5. **Minimize synchronized blocks** - only protect critical sections
6. **Use high-level concurrency utilities** - `ExecutorService`, `CountDownLatch`

### ❌ DON'T:
1. **Don't call `run()` directly** - use `start()`
2. **Don't ignore InterruptedException** - handle properly
3. **Don't use `stop()`, `suspend()`, `resume()`** - deprecated and dangerous
4. **Don't share mutable objects** - without synchronization
5. **Don't create too many threads** - use thread pools

---

## 📚 Summary Table

| Method | Purpose | Example |
|--------|---------|---------|
| `start()` | Start thread | `thread.start()` |
| `run()` | Code to execute | `@Override public void run()` |
| `sleep(ms)` | Pause thread | `Thread.sleep(1000)` |
| `join()` | Wait for thread | `thread.join()` |
| `isAlive()` | Check if running | `thread.isAlive()` |
| `getName()` | Get thread name | `thread.getName()` |
| `setName()` | Set thread name | `thread.setName("Worker")` |
| `currentThread()` | Get current thread | `Thread.currentThread()` |
| `setPriority()` | Set priority | `thread.setPriority(10)` |
| `interrupt()` | Interrupt thread | `thread.interrupt()` |
| `yield()` | Give up CPU | `Thread.yield()` |
| `setDaemon()` | Make daemon | `thread.setDaemon(true)` |

---

## 🎮 Final Complete Example

```java
public class SimpleThreadedGame {
    public static void main(String[] args) {
        System.out.println("=== Multi-threaded Game ===\n");
        
        // Game loop thread
        Thread gameThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("[GAME] Frame " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "GameThread");
        
        // Music thread (daemon)
        Thread musicThread = new Thread(() -> {
            int note = 1;
            while (true) {
                System.out.println("[MUSIC] ♪ Note " + note++);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "MusicThread");
        musicThread.setDaemon(true);
        
        // Start threads
        gameThread.start();
        musicThread.start();
        
        // Wait for game to finish
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nGame finished!");
    }
}
```

**Output:**
```
=== Multi-threaded Game ===

[GAME] Frame 1
[MUSIC] ♪ Note 1
[MUSIC] ♪ Note 2
[GAME] Frame 2
[MUSIC] ♪ Note 3
[MUSIC] ♪ Note 4
[GAME] Frame 3
...
Game finished!
```

That's threading in Java! Start simple, practice with examples, and gradually add complexity! 🚀✨