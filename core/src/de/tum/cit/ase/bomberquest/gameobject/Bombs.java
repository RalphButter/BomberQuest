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
        super(x, y, false, true); // Bombs are not walkable and are destructible
        this.fuseTime = DEFAULT_FUSE_TIME;
        this.elapsedTime = 0f;
        this.blastRadius = DEFAULT_BLAST_RADIUS;
        this.gameMap = gameMap;
        this.gameObjectManager = gameObjectManager;
    }

    @Override
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= fuseTime) {
            explode();
            setActive(false); // Mark for removal
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

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean isActive() {
        return false;
    }
}