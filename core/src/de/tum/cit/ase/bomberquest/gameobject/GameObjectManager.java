package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.Characters.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameObjectManager {
    private List<GameObject> gameObjects;

    public GameObjectManager() {
        this.gameObjects = new ArrayList<>();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    //Purpose: Updates the state of each game object.
    public void update(float deltaTime) {
        // Create an iterator to traverse the list of game objects
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            // Get the next game object in the list
            GameObject gameObject = iterator.next();
            // Update the game object with the elapsed time since the last frame
            gameObject.update(deltaTime);
            // If the game object is no longer active, remove it from the list
            if (!gameObject.isActive()) {
                iterator.remove();
            }
        }
    }

    //Purpose: Renders all game objects.
    public void render(SpriteBatch batch) {
        // Iterate through all game objects in the list
        for (GameObject gameObject : gameObjects) {
            // Render each game object using the provided SpriteBatch
            gameObject.render(batch);
        }
    }

    //Purpose: Releases resources used by game objects.
    public void dispose() {
        // Iterate through all game objects in the list
        for (GameObject gameObject : gameObjects) {
            // Call the dispose method on each game object to release resources
            gameObject.dispose();
        }
        // Clear the list of game objects to remove all references
        gameObjects.clear();
    }

//The ability to retrieve the Player object from the list of game objects can be crucial for game logic,
// particularly when updates, interactions, or status checks involving the player are required.
// A method designed for this purpose can streamline these processes effectively.
//    public Player getPlayer() {
//        for (GameObject gameObject : gameObjects) {
//            if (gameObject instanceof Player) {
//                return (Player) gameObject;
//            }
//        }
//        return null;
//    }

    public List<Enemy> getEnemies() {
        return null;
    }

    public void spawnPowerUpIfAny(float nextX, float nextY) {
    }
}