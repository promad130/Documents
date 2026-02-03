import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * CORRECTED: SpaceShip now extends Renderable (not GameObject)
 * Custom physics because player has unique acceleration/friction mechanics
 * Chapter 9: Now implements Collidable interface for collision detection
 */
public class SpaceShip extends Renderable implements Collidable {

    private double angle;
    private double velocityX;
    private double velocityY;
    private int lives;
    private int score;
    private boolean invulnerable;
    private int invulnerableTimer;
    private static final double SPACESHIP_WIDTH = 20;
    private static final double SPACESHIP_HEIGHT = 20;
    private static final double FRICTION = 0.98;
    private static final double MAX_SPEED = 10.0;

    public SpaceShip(double x, double y) {
        super(x, y, SPACESHIP_WIDTH, SPACESHIP_HEIGHT);
        this.angle = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.lives = GameConstants.INITIAL_PLAYER_LIVES;
        this.score = GameConstants.STARTING_SCORE;
        this.invulnerable = false;
        this.invulnerableTimer = 0;
    }

    @Override
    public void update() {
        x += velocityX;
        y += velocityY;

        // Apply friction
        velocityX *= FRICTION;
        velocityY *= FRICTION;
        
        // Handle invulnerability timer (Chapter 9)
        if (invulnerable) {
            invulnerableTimer--;
            if (invulnerableTimer <= 0) {
                invulnerable = false;
            }
        }

        // Screen Wrapping
        if (x < 0) {
            x = GameConstants.SCREEN_WIDTH;
        } else if (x > GameConstants.SCREEN_WIDTH) {
            x = 0;
        }

        if (y < 0) {
            y = GameConstants.SCREEN_HEIGHT;
        } else if (y > GameConstants.SCREEN_HEIGHT) {
            y = 0;
        }
    }
    
    // REQUIRED: Implement draw() from Renderable
    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            g.setColor(Color.WHITE);
            
            // Draw triangle spaceship
            int[] xPoints = {
                (int)(x + Math.cos(Math.toRadians(angle)) * 15),
                (int)(x + Math.cos(Math.toRadians(angle + 140)) * 10),
                (int)(x + Math.cos(Math.toRadians(angle - 140)) * 10)
            };
            int[] yPoints = {
                (int)(y + Math.sin(Math.toRadians(angle)) * 15),
                (int)(y + Math.sin(Math.toRadians(angle + 140)) * 10),
                (int)(y + Math.sin(Math.toRadians(angle - 140)) * 10)
            };
            
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }
    
    // REQUIRED: Implement getDrawPriority() from Renderable
    @Override
    public int getDrawPriority() {
        return 15; // Player drawn after asteroids/bullets but before UI
    }

    //Takes angle in degrees
    public void rotate(double deltaAngle) {
        angle = (angle + deltaAngle) % 360;
    }

    public void acceleration(double acceleration) {
        double radians = Math.toRadians(angle);
        velocityX += Math.cos(radians) * acceleration;
        velocityY += Math.sin(radians) * acceleration;
        
        // Limit speed
        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (speed > MAX_SPEED) {
            velocityX = (velocityX / speed) * MAX_SPEED;
            velocityY = (velocityY / speed) * MAX_SPEED;
        }
    }

    // Player properties Getters
    public double getAngle() {
        return angle;
    }
    public int getCurrentLives() {
        return lives;
    }
    public int getCurrentScore() {
        return score;
    }

    //Player properties Setters
    public void setX(double x) {
        if (x >= 0 && x <= GameConstants.SCREEN_WIDTH) {
            this.x = x;
        }
    }

    public void setY(double y) {
        if (y >= 0 && y <= GameConstants.SCREEN_HEIGHT) {
            this.y = y;
        }
    }

    public void setAngle(double angle) {
        this.angle = angle % 360;
    }

    // Game methods
    public void takeDamage() {
        if (lives > 0) {
            lives--;
        }
        else{
            this.setActive(false);
        }
    }

    public void addScore(int points) {
        if (points > 0) {
            score += points;
        }
    }
    
    public boolean isInvulnerable() {
        return invulnerable;
    }
    
    // === Collidable Interface Implementation (Chapter 9) ===
    
    @Override
    public Rectangle getCollisionBounds() {
        return new Rectangle((int)(x - width/2), (int)(y - height/2), 
                           (int)width, (int)height);
    }
    
    @Override
    public void onCollision(GameObject other) {
        if (!invulnerable && other instanceof Astroid) {
            takeDamage();
            invulnerable = true;
            invulnerableTimer = GameConstants.PLAYER_INVULNERABILITY_FRAMES;
        }
    }
    
    @Override
    public double getCollisionRadius() {
        return width / 2.0;
    }
}
