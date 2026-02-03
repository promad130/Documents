
import java.util.ArrayList;

class BulletManager {
    private ArrayList<Bullet> activeBullets;
    
    public BulletManager(){
        activeBullets = new ArrayList<>();
    }

    public void fireBullet(double x, double y, double angle)
    {
        Bullet newBullet = new Bullet(x, y, angle);
        activeBullets.add(newBullet);
    }

    public void updateAll()
    {
        for (Bullet bulleObject : activeBullets) {
            bulleObject.update();
        }
    }

        public void checkAstroidHits(AstroidManager astroidManager) {
        for (int i = activeBullets.size() - 1; i >= 0; i--) {
            Bullet bullet = activeBullets.get(i);
            ArrayList<Integer> hits = astroidManager.checkCollisions(
                bullet.getX(), bullet.getY(), bullet.getRadius()
            );
            
            if (!hits.isEmpty()) {
                // Remove bullet
                activeBullets.remove(i);
                
                // Split first hit astroid
                astroidManager.splitAstroid(hits.get(0));
            }
        }
    }
    
    public void clear() {
        activeBullets.clear();
    }
    
    public ArrayList<Bullet> getBullets() {
        return activeBullets;
    }
}
