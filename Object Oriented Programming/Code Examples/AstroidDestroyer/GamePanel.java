import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

/**
 * Chapter 15-17: Main Game Panel
 * Handles game rendering, input, and game loop
 */
public class GamePanel extends JPanel implements Runnable {
    // Game state
    private boolean running;
    private boolean paused;
    private Thread gameThread;
    private int level;
    
    // Managers
    private GameObjectManager objectManager;
    private CollisionHandler collisionHandler;
    private BulletManager bulletManager;
    private AstroidManager asteroidManager;
    
    // Player
    private SpaceShip player;
    
    // Effects
    private ArrayList<ExplosionEffect> explosions;
    
    // Input tracking
    private boolean upPressed, leftPressed, rightPressed, spacePressed;
    private long lastShotTime;
    private static final long SHOT_COOLDOWN = 200; // milliseconds
    
    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        
        initializeGame();
        setupInputHandlers();
        
        // Start game thread
        running = true;
        paused = false;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    private void initializeGame() {
        level = 1;
        objectManager = new GameObjectManager();
        collisionHandler = new CollisionHandler();
        bulletManager = new BulletManager();
        asteroidManager = new AstroidManager();
        explosions = new ArrayList<>();
        lastShotTime = 0;
        
        // Create player
        player = new SpaceShip(GameConstants.SCREEN_WIDTH / 2.0, 
                              GameConstants.SCREEN_HEIGHT / 2.0);
        objectManager.addObject(player);
        
        // Create initial asteroids
        spawnAsteroids(GameConstants.INITIAL_ASTEROID_COUNT);
    }
    
    private void spawnAsteroids(int count) {
        for (int i = 0; i < count; i++) {
            Astroid ast = AsteroidFactory.createSafeAsteroid(
                player.getX(), 
                player.getY(), 
                150.0 // Minimum distance from player
            );
            objectManager.addObject(ast);
            asteroidManager.addAstroid(ast);
        }
    }
    
    private void setupInputHandlers() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        });
    }
    
    private void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
            case KeyEvent.VK_R:
                if (player.getCurrentLives() <= 0) {
                    resetGame();
                }
                break;
        }
    }
    
    private void handleKeyRelease(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
        }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / GameConstants.TARGET_FPS;
        double delta = 0;
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if (delta >= 1) {
                if (!paused) {
                    processInput();
                    update();
                }
                repaint();
                delta--;
            }
            
            // Sleep briefly to prevent CPU overuse
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void processInput() {
        if (player.getCurrentLives() <= 0) return;
        
        if (upPressed) {
            player.accelerate(GameConstants.PLAYER_ACCELERATION);
        }
        
        if (leftPressed) {
            player.rotate(-GameConstants.PLAYER_ROTATION_SPEED);
        }
        
        if (rightPressed) {
            player.rotate(GameConstants.PLAYER_ROTATION_SPEED);
        }
        
        if (spacePressed) {
            tryShoot();
        }
    }
    
    private void tryShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= SHOT_COOLDOWN) {
            bulletManager.fireBullet(player.getX(), player.getY(), player.getAngle());
            lastShotTime = currentTime;
        }
    }
    
    private void update() {
        // Update all game objects
        objectManager.updateAll();
        bulletManager.updateAll();
        asteroidManager.updateAll();
        
        // Check collisions
        collisionHandler.checkAllCollisions(objectManager);
        checkBulletAsteroidCollisions();
        
        // Update effects
        updateExplosions();
        
        // Remove inactive objects
        objectManager.removeInactive();
        asteroidManager.removeInactive();
        
        // Check level complete
        if (asteroidManager.getCount() == 0) {
            levelComplete();
        }
    }
    
    private void checkBulletAsteroidCollisions() {
        ArrayList<Bullet> bullets = bulletManager.getBullets();
        ArrayList<Astroid> asteroids = asteroidManager.getAsteroids();
        
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            if (!bullet.isActive()) continue;
            
            for (int j = asteroids.size() - 1; j >= 0; j--) {
                Astroid asteroid = asteroids.get(j);
                if (!asteroid.isActive()) continue;
                
                // Check collision using CollisionDetector
                double distance = PhysicsUtils.calculateDistance(
                    bullet.getX(), bullet.getY(),
                    asteroid.getX(), asteroid.getY()
                );
                
                if (distance < bullet.getRadius() + asteroid.getRadius()) {
                    // Collision detected
                    bullet.setActive(false);
                    asteroid.setActive(false);
                    
                    // Create explosion effect
                    createExplosion(asteroid.getX(), asteroid.getY());
                    
                    // Add score
                    player.addScore(asteroid.getPointValue());
                    
                    // Split asteroid
                    asteroidManager.splitAsteroid(j);
                    
                    break; // Bullet can only hit one asteroid
                }
            }
        }
    }
    
    private void createExplosion(double x, double y) {
        ExplosionEffect explosion = new ExplosionEffect();
        explosion.createExplosion(x, y, 20, Color.ORANGE);
        explosions.add(explosion);
    }
    
    private void updateExplosions() {
        for (int i = explosions.size() - 1; i >= 0; i--) {
            ExplosionEffect exp = explosions.get(i);
            exp.update();
            
            if (exp.isFinished()) {
                explosions.remove(i);
            }
        }
    }
    
    private void levelComplete() {
        level++;
        int asteroidCount = GameConstants.INITIAL_ASTEROID_COUNT + (level - 1);
        spawnAsteroids(Math.min(asteroidCount, GameConstants.MAXIMUM_ASTROIDS));
    }
    
    private void resetGame() {
        objectManager.getAllObjects().clear();
        asteroidManager.getAsteroids().clear();
        bulletManager.clear();
        explosions.clear();
        level = 1;
        
        player = new SpaceShip(GameConstants.SCREEN_WIDTH / 2.0, 
                              GameConstants.SCREEN_HEIGHT / 2.0);
        objectManager.addObject(player);
        
        spawnAsteroids(GameConstants.INITIAL_ASTEROID_COUNT);
        paused = false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable antialiasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw background
        drawStarfield(g2d);
        
        // Draw all game objects
        drawGameObjects(g2d);
        
        // Draw bullets
        for (Bullet bullet : bulletManager.getBullets()) {
            if (bullet.isActive()) {
                bullet.draw(g2d);
            }
        }
        
        // Draw explosions
        for (ExplosionEffect exp : explosions) {
            exp.render(g2d);
        }
        
        // Draw UI
        drawUI(g2d);
        
        // Draw pause screen if paused
        if (paused) {
            drawPauseScreen(g2d);
        }
        
        // Draw game over screen
        if (player.getCurrentLives() <= 0) {
            drawGameOverScreen(g2d);
        }
    }
    
    private void drawStarfield(Graphics2D g2d) {
        // Simple starfield background
        g2d.setColor(new Color(30, 30, 30));
        for (int i = 0; i < 100; i++) {
            int x = (int)(Math.random() * GameConstants.SCREEN_WIDTH);
            int y = (int)(Math.random() * GameConstants.SCREEN_HEIGHT);
            int size = (int)(Math.random() * 2) + 1;
            g2d.fillOval(x, y, size, size);
        }
    }
    
    private void drawGameObjects(Graphics2D g2d) {
        for (GameObject obj : objectManager.getAllObjects()) {
            if (obj instanceof Renderable && obj.isActive()) {
                Renderable renderable = (Renderable) obj;
                if (renderable.isVisible()) {
                    renderable.draw(g2d);
                }
            }
        }
    }
    
    private void drawUI(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        
        // Draw lives
        g2d.drawString("Lives: " + player.getCurrentLives(), 10, 25);
        
        // Draw score
        g2d.drawString("Score: " + player.getCurrentScore(), 10, 50);
        
        // Draw level
        g2d.drawString("Level: " + level, 10, 75);
        
        // Draw controls hint
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2d.drawString("Arrow Keys/WASD: Move | Space: Shoot | ESC: Pause", 10, 
                      GameConstants.SCREEN_HEIGHT - 10);
    }
    
    private void drawPauseScreen(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Pause text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 48));
        String pauseText = "PAUSED";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(pauseText);
        g2d.drawString(pauseText, (getWidth() - textWidth) / 2, getHeight() / 2);
        
        // Resume instruction
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        String resumeText = "Press ESC to resume";
        textWidth = g2d.getFontMetrics().stringWidth(resumeText);
        g2d.drawString(resumeText, (getWidth() - textWidth) / 2, getHeight() / 2 + 40);
    }
    
    private void drawGameOverScreen(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Game over text
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 56));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(gameOverText);
        g2d.drawString(gameOverText, (getWidth() - textWidth) / 2, getHeight() / 2 - 50);
        
        // Final score
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
        String scoreText = "Final Score: " + player.getCurrentScore();
        textWidth = g2d.getFontMetrics().stringWidth(scoreText);
        g2d.drawString(scoreText, (getWidth() - textWidth) / 2, getHeight() / 2 + 10);
        
        // Restart instruction
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        String restartText = "Press R to restart";
        textWidth = g2d.getFontMetrics().stringWidth(restartText);
        g2d.drawString(restartText, (getWidth() - textWidth) / 2, getHeight() / 2 + 50);
    }
    
    public void stopGame() {
        running = false;
    }
}
