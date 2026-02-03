public class AstroidGenerator {
    private static final int WINDOW_WIDTH = GameConstants.SCREEN_WIDTH;
    private static final int WINDOW_HEIGHT = GameConstants.SCREEN_HEIGHT;
    
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
        return 1.0 + Math.random() * 2.0;
    }
    
    public static int randomSize() {
        int[] sizes = {5, 3, 2};
        int index = (int) (Math.random() * sizes.length);
        return sizes[index];
    }
}
