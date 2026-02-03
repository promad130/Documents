public class Renderer {
    
    public void draw(SpaceShip ship) {
        System.out.println("Drawing SpaceShip: Triangle at (" + 
            ship.getX() + ", " + ship.getY() + ") angle=" + ship.getAngle());
    }
    
    public void draw(Astroid astroid) {
        System.out.println("Drawing Astroid: Circle at (" + 
            astroid.getX() + ", " + astroid.getY() + ") size=" + astroid.getSize());
    }
    
    public void draw(Bullet bullet) {
        System.out.println("Drawing Bullet: Small circle at (" + 
            bullet.getX() + ", " + bullet.getY() + ")");
    }
    
    public void draw(String text, int x, int y) {
        System.out.println("Drawing Text: '" + text + "' at (" + x + ", " + y + ")");
    }
    
    // Polymorphic draw for any GameObject
    public void draw(GameObject obj) {
        if (obj instanceof SpaceShip) {
            draw((SpaceShip) obj);
        } else if (obj instanceof Astroid) {
            draw((Astroid) obj);
        } else if (obj instanceof Bullet) {
            draw((Bullet) obj);
        } else {
            System.out.println("Drawing GameObject at (" + obj.getX() + ", " + obj.getY() + ")");
        }
    }
    
    public void drawAll(GameObjectManager manager) {
        for (GameObject obj : manager.getAllObjects()) {
            draw(obj);
        }
    }
}