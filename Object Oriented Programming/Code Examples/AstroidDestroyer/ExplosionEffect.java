import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Chapter 9 & 13: Explosion Effect Manager
 * Creates and manages particle effects for explosions
 */
public class ExplosionEffect {
    private ArrayList<Particle> particles;
    
    public ExplosionEffect() {
        particles = new ArrayList<>();
    }
    
    /**
     * Create an explosion at the specified location
     * @param x X coordinate of explosion
     * @param y Y coordinate of explosion
     * @param particleCount Number of particles to create
     * @param baseColor Base color for particles
     */
    public void createExplosion(double x, double y, int particleCount, Color baseColor) {
        for (int i = 0; i < particleCount; i++) {
            double angle = Math.random() * 360;
            double speed = 1 + Math.random() * 4;
            Color particleColor = varyColor(baseColor);
            
            particles.add(new Particle(x, y, angle, speed, 60, particleColor));
        }
    }
    
    /**
     * Vary the color slightly for more natural looking explosions
     */
    private Color varyColor(Color base) {
        int r = Math.max(0, Math.min(255, base.getRed() + (int)(Math.random() * 50 - 25)));
        int g = Math.max(0, Math.min(255, base.getGreen() + (int)(Math.random() * 50 - 25)));
        int b = Math.max(0, Math.min(255, base.getBlue() + (int)(Math.random() * 50 - 25)));
        return new Color(r, g, b);
    }
    
    /**
     * Update all particles in this effect
     */
    public void update() {
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update();
            
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }
    
    /**
     * Render all particles
     */
    public void render(Graphics2D g2d) {
        for (Particle p : particles) {
            p.render(g2d);
        }
    }
    
    /**
     * Check if the explosion effect is finished
     * @return true if all particles are dead
     */
    public boolean isFinished() {
        return particles.isEmpty();
    }
}
