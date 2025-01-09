package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.Characters.Player;

import java.util.ArrayList;
import java.util.Collection;
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

    public void update(float deltaTime) {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            gameObject.update(deltaTime);
            if (!gameObject.isActive()) {
                iterator.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(batch);
        }
    }

    public void dispose() {
        for (GameObject gameObject : gameObjects) {
            gameObject.dispose();
        }
        gameObjects.clear();
    }

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

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public int getBombs() {
        int bombCount = 0;
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Bombs) {
                bombCount++;
            }
        }
        return bombCount;
    }
}