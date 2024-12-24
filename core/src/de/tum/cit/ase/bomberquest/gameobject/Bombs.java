package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import de.tum.cit.ase.bomberquest.map.GameMap;

public class Bombs extends GameObject {
    private static final float DEFAULT_FUSE_TIME = 3.0f;
    private static final int DEFAULT_BLAST_RADIUS = 1;

    private float fuseTime;
    private float elapsedTime;
    private int blastRadius;
    private GameMap gameMap;
    private GameObjectManager gameObjectManager;


public Bombs(float x, float y, GameMap gameMap, GameObjectManager gameObjectManager) {
    // Call the superclass constructor with initial position, walkable, and destructible flags
    super(x, y, false, true); // Bombs are not walkable and are destructible

    // Set the default fuse time for the bomb
    this.fuseTime = DEFAULT_FUSE_TIME;

    // Initialize the elapsed time since the bomb was placed
    this.elapsedTime = 0f;

    // Set the default blast radius for the bomb
    this.blastRadius = DEFAULT_BLAST_RADIUS;

    // Reference to the game map
    this.gameMap = gameMap;

    // Reference to the game object manager
    this.gameObjectManager = gameObjectManager;
}

    @Override
    public void update(float deltaTime) {
        // Increment the elapsed time by the delta time
        elapsedTime += deltaTime;

        // Check if the elapsed time has reached or exceeded the fuse time
        if (elapsedTime >= fuseTime) {
            // Trigger the explosion
            explode();

            // Mark the bomb for removal
            setActive(false);
        }
    }

    private void setActive(boolean b) {
    }

    private void explode() {
        // Create a new blast at the bomb's position
        Blast blast = new Blast(getX(), getY(), blastRadius, 0.5f, 1, gameMap, gameObjectManager);
        gameObjectManager.addGameObject(blast);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY());
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), 1, 1);
    }


    /**
     * Disposes resources used by the bomb.
     * Calls the superclass dispose method to ensure proper cleanup.
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean isActive() {
        return false;
    }
}