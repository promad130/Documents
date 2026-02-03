public class MovementCalculator {
    public static double calculaterNewX(double currentX, double angle,double speed)
    {
        return currentX + Math.cos(Math.toRadians(angle)) * speed;
    }

    public static double calculaterNewY(double currentY, double angle,double speed)
    {
        return currentY + Math.sin(Math.toRadians(angle)) * speed;
    }
}