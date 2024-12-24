package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.map.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the explosion (blast) created by a bomb.
 */
public class Blast extends GameObject {
    private int blastRadius;
    private float blastDuration; // Total duration the blast remains visible
    private float elapsedTime;   // Time elapsed since the blast was created
    private int blastDamage;
    private List<Rectangle> segments;
    private GameMap gameMap;     // Reference to the game map to interact with walls
    private GameObjectManager gameObjectManager; // To manage game objects like enemies and player

    /**
     * Constructor for creating a new Blast.
     *
     * @param x                 The X-coordinate of the bomb.
     * @param y                 The Y-coordinate of the bomb.
     * @param blastRadius       The radius of the blast.
     * @param blastDuration     How long the blast remains active.
     * @param blastDamage       Damage dealt by the blast.
     * @param gameMap           Reference to the game map.
     * @param gameObjectManager Reference to the game object manager.
     */
    public Blast(float x, float y, int blastRadius, float blastDuration, int blastDamage, GameMap gameMap, GameObjectManager gameObjectManager) {
        super(x, y, false, false); // Blasts are not walkable and not destructible
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
        this.blastDamage = blastDamage;
        this.elapsedTime = 0f;
        this.segments = new ArrayList<>();
        this.gameMap = gameMap;
        this.gameObjectManager = gameObjectManager;

        createSegments();
    }

    /**
     * Creates blast segments in all four cardinal directions.
     */
    //Ggf. mit 16 mulitplizieren
    private void createSegments() {
        // Create center blast segment
        segments.add(new Rectangle(getX(), getY(), 1, 1));

        // Up
        createDirectionalSegments(0, 1);
        // Down
        createDirectionalSegments(0, -1);
        // Left
        createDirectionalSegments(-1, 0);
        // Right
        createDirectionalSegments(1, 0);
    }

    /**
     * Creates blast segments in a specific direction until an obstacle is hit or blast radius is reached.
     *
     * @param dx Change in X-direction.
     * @param dy Change in Y-direction.
     */
    private void createDirectionalSegments(int dx, int dy) {
        for (int step = 1; step <= blastRadius; step++) {
            float nextX = getX() + step * dx;
            float nextY = getY() + step * dy;

            // Boundary check
            if (!gameMap.isWithinBounds(nextX, nextY)) {
                break;
            }

            // Check for indestructible walls
            if (gameMap.isIndestructibleWall(nextX, nextY)) {
                break; // Stop blast in this direction
            }

            // Add blast segment
            segments.add(new Rectangle(nextX, nextY, 1, 1));

            // Check for destructible walls
            if (gameMap.isDestructibleWall(nextX, nextY)) {
                gameMap.destroyWall(nextX, nextY); // Destroy the wall
                gameObjectManager.spawnPowerUpIfAny(nextX, nextY); // Optionally spawn power-up
                break; // Stop blast after destroying the wall
            }
        }
    }

    /**
     * Updates the blast's state, handles timing, and checks for collisions.
     *
     * @param deltaTime Time elapsed since last update.
     */
    @Override
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= blastDuration) {
            setActive(false); // Mark for removal
        }

        // Check collisions with player and enemies
        checkCollisions();
    }

    private void setActive(boolean b) {
    }

    /**
     * Renders the blast segments.
     *
     * @param batch SpriteBatch for rendering.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Iterate through each segment of the blast
        for (Rectangle segment : segments) {
            // Draw the texture at the segment's coordinates
            batch.draw(getTexture(), segment.x, segment.y);
        }
    }

    /**
     * Checks for collisions between the blast and other entities (player, enemies).
     */
    private void checkCollisions() {

    }

    /**
     * Returns the bounding rectangle encompassing all blast segments.
     *
     * @return Bounding Rectangle.
     */
    @Override
    public Rectangle getBoundingRectangle() {
        // Simple implementation: returns the center blast's rectangle
        // For more accurate collision, consider all segments
        if (!segments.isEmpty()) {
            return segments.get(0);
        }
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

    //Needs to be fixed
    @Override
    public boolean isActive() {
        return false;
    }
}