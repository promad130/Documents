
import java.util.ArrayList;

class GameObjectManager {

    private ArrayList<GameObject> gameObjects;

    public GameObjectManager() {
        gameObjects = new ArrayList<>();
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    public void updateAll() {
        for (GameObject obj : gameObjects) {
            obj.update();
        }
    }

    public void removeInactiveObjects() {
        for (GameObject object : gameObjects) {
            if (!object.isActive()) {
                gameObjects.remove(object);
            }
        }
    }

    public ArrayList<GameObject> getObjectsByType(Class<?> type) {
        ArrayList<GameObject> result = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (type.isInstance(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    public ArrayList<GameObject> getAllObjects() {
        return gameObjects;
    }
    
    public int getCount() {
        return gameObjects.size();
    }

}
