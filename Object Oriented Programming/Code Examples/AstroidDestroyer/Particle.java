import java.awt.Color;
import java.awt.Graphics;

/**
 * Chapter 9: Particle class implementing Updatable
 * Particles are lightweight and don't extend Renderable to avoid overhead
 * Used for visual effects like explosions
 */
public class Particle implements Updatable {
    private double x, y;
    private double velocityX, velocityY;
    private int lifetime;
    private int maxLifetime;
    private Color color;
    private double size;
    private boolean active;
    
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
        this.active = true;
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
        
        if (lifetime <= 0) {
            active = false;
        }
    }
    
    public void render(Graphics g) {
        if (lifetime > 0 && active) {
            int alpha = (int)(255 * ((double)lifetime / maxLifetime));
            Color fadeColor = new Color(color.getRed(), color.getGreen(), 
                                      color.getBlue(), alpha);
            g.setColor(fadeColor);
            g.fillOval((int)(x - size/2), (int)(y - size/2), 
                      (int)size, (int)size);
        }
    }
    
    public boolean isDead() {
        return lifetime <= 0;
    }
}
