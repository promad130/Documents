public class CollisionDetector {
    public static boolean checkPlayerCollision(
        double playerX, double playerY, double playerRadius,
        double[][] asteroids // [x, y, radius]
    ) {
        for (double[] astroid : asteroids) {
            // double distanceBetweenPlayerAndAstroid = 
            //     Math.sqrt
            //     (
            //         Math.pow((playerX - astroid[0]), 2) + 
            //         Math.pow((playerY - astroid[1]), 2)
            //     );
            double distanceBetweenPlayerAndAstroid = PhysicsUtils.calculateDistance(playerX, playerY, astroid[0], astroid[1]);
            
            if(distanceBetweenPlayerAndAstroid <= playerRadius + astroid[2])
                return true;
        }
        return false;
    }
}