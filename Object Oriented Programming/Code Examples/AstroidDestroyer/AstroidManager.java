
import java.util.ArrayList;

class AstroidManager {

    private ArrayList<Astroid> asteroids;

    public AstroidManager(){
        asteroids = new ArrayList<Astroid>();
    }

    public boolean addAstroid(Astroid astroid)
    {
        try {
            asteroids.add(astroid);
            return true;        
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateAll()
    {
        try {
            for(Astroid astroid : asteroids)
            {
                astroid.update();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeAstroid(int index)
    {
        try {
            asteroids.remove(index);
            return true;    
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean splitAstroid(int index)
    {
        if(index >= 0 && index <= asteroids.size())
        {
            Astroid original = asteroids.get(index);
            int newSize = AstroidBreaker.getNewSize(original.getSize());
            int count = AstroidBreaker.breakAstroid(original.getSize());

            asteroids.remove(index);
            double newAngle = Math.random()*360;
            for (int i = 0; i < count; i++) {
                // FIXED: Constructor parameters in correct order (x, y, angle, speed, size)
                asteroids.add(new Astroid(
                        original.getX(), 
                        original.getY(), 
                        newAngle, 
                        original.getSpeed() * 1.2,  // speed (fixed type)
                        newSize)                     // size (moved to end)
                    );
            }

            return true;
        }
        else{
            return false;
        }
    }

    /*Returns an ArrayList of indexs of astroids that are colliding with the given object in a given frame */
    public ArrayList<Integer> checkCollisions(double x, double y, double radius) {
        ArrayList<Integer> collisions = new ArrayList<>();
        
        for (int i = 0; i < asteroids.size(); i++) {
            Astroid ast = asteroids.get(i);
            double distance = PhysicsUtils.calculateDistance(x, y, ast.getX(), ast.getY());
            
            if (distance < radius + ast.getRadius()) {
                collisions.add(i);
            }
        }
        
        return collisions;
    }
    
    public ArrayList<Astroid> getAsteroids() {
        return asteroids;
    }
    
    public int getCount() {
        return asteroids.size();
    }
    
    public void removeInactive() {
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            if (!asteroids.get(i).isActive()) {
                asteroids.remove(i);
            }
        }
    }
    
}
