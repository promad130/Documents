
/**
 * CORRECTED: MovingObject now extends Renderable instead of GameObject
 * This makes all moving objects automatically drawable
 */
public abstract class MovingObject extends Renderable {

    protected double velocityX;
    protected double velocityY;
    protected double angle;
    protected double speed;

    public MovingObject(double x, double y, double width, double height, double angle, double speed) {
        super(x, y, width, height);
        this.angle = angle;
        this.speed = speed;
        this.velocityX = Math.cos(Math.toRadians(angle)) * speed;
        this.velocityY = Math.sin(Math.toRadians(angle)) * speed;
    }

    @Override
    public void update() {
        // Don't recalculate velocity every frame - it's already set in constructor
        // Only recalculate when angle/speed actually changes
        this.x += this.velocityX;
        this.y += this.velocityY;
    }

    public void setVelocity(double vx, double vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    // Recalculate velocity based on current angle and speed
    // Useful if angle or speed changes after initialization
    protected void updateVelocityFromAngle() {
        this.velocityX = Math.cos(Math.toRadians(angle)) * speed;
        this.velocityY = Math.sin(Math.toRadians(angle)) * speed;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        // Optionally auto-update velocity (comment out if not desired)
        updateVelocityFromAngle();
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        // Optionally auto-update velocity (comment out if not desired)
        updateVelocityFromAngle();
    }
}
