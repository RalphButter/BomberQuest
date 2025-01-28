package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.util.Rectangle;

/**
 * Represents a base entity in the game world.
 * This class provides common properties and methods for all game objects that have a position and texture.
 * Subclasses can extend this class to define specific behaviors for characters, bombs, enemies, etc.
 */
public class Entity {

    // Position of the entity in the game world
    public Vector2 pos;

    // Texture used to render the entity
    public Texture texture;

    // Flag to indicate whether the entity should be removed from the game
    public boolean destroyFlag = false;

    /**
     * Constructs a new entity with a given position and texture.
     *
     * @param pos     The initial position of the entity.
     * @param texture The texture representing the entity's appearance.
     */
    public Entity(Vector2 pos, Texture texture) {
        this.pos = pos;
        this.texture = texture;
    }

    /**
     * Updates the entity's state.
     * This method is intended to be overridden by subclasses to define specific update behavior.
     */
    public void update() {
        // Default implementation does nothing
    }

    /**
     * Gets the entity's current position.
     *
     * @return The position of the entity as a Vector2.
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * Sets a new position for the entity.
     *
     * @param pos The new position as a Vector2.
     */
    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /**
     * Returns the bounding rectangle for collision detection.
     *
     * @return A Rectangle representing the entity's hitbox.
     */
    public Rectangle getRect() {
        return new Rectangle(pos.x, pos.y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Draws the entity on the screen using the provided batch.
     *
     * @param batch The batch used for rendering.
     */
    public void draw(Batch batch) {
        batch.begin();
        batch.draw(texture, pos.x, pos.y,
                texture.getWidth(), texture.getHeight(),
                0, 0, texture.getWidth(), texture.getHeight(),
                false, false);
        batch.end();
    }
}
