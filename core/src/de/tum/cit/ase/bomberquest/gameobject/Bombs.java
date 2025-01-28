package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.map.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.util.Manager;
import de.tum.cit.ase.bomberquest.util.Rectangle;

/**
 * Represents a bomb that can be placed in the game.
 * The bomb flashes at an increasing rate as it nears explosion and disappears upon detonation.
 */
public class Bombs extends Entity {

    // Time until the bomb explodes
    private float bombTick = 4f;

    // Countdown timer for bomb explosion
    private float bombTickInterval = bombTick;

    // Interval for flashing effect before explosion
    private float flashInterval = 0.1f;

    // Timer to control flashing effect
    private float flashTimer = flashInterval;

    // Visibility flag used for flashing effect
    private boolean visible = true;

    /**
     * Creates a bomb at the specified position.
     *
     * @param pos The position where the bomb is placed.
     */
    public Bombs(Vector2 pos) {
        super(pos, new Texture("texture/bomb.png"));
    }

    /**
     * Returns the bounding rectangle for collision detection.
     *
     * @return A Rectangle representing the bomb's collision box.
     */
    public Rectangle getRect() {
        return new Rectangle(pos.x, pos.y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Updates the bomb's state, including countdown, flashing effect, and explosion handling.
     * The bomb flashes faster as it nears explosion.
     */
    public void update() {
        // If the bomb is set to explode, mark it for destruction
        if (isExplode()) {
            destroyFlag = true;
        }

        // Decrease countdown timer
        bombTickInterval -= Gdx.graphics.getDeltaTime();

        // Adjust the flash interval dynamically as the explosion nears
        if (bombTickInterval > 0) {
            flashInterval = Math.max(0.1f, bombTickInterval / 10);
        }

        // Toggle visibility for flashing effect
        flashTimer -= Gdx.graphics.getDeltaTime();
        if (flashTimer <= 0) {
            if (bombTickInterval < 3) {
                // Play ticking sound when flashing quickly
                Manager.getInstance().soundsManager.play("clickBomb", 1.0f);
            }
            visible = !visible; // Toggle bomb visibility
            flashTimer = flashInterval; // Reset flash timer
        }
    }

    /**
     * Draws the bomb on the screen. The bomb may flash (disappear and reappear) as it nears explosion.
     *
     * @param batch The batch used for rendering.
     */
    public void draw(Batch batch) {
        // Ensure bomb remains visible until flashing starts
        if (bombTickInterval > 3) {
            visible = true;
        }

        // Only draw the bomb if it's visible (flashing effect)
        if (visible) {
            batch.begin();
            batch.draw(texture, pos.x, pos.y,
                    texture.getWidth(), texture.getHeight(),
                    0, 0, texture.getWidth(), texture.getHeight(),
                    false, false);
            batch.end();
        }
    }

    /**
     * Checks if the bomb has reached its explosion time.
     *
     * @return True if the bomb should explode, false otherwise.
     */
    public boolean isExplode() {
        return bombTickInterval <= 0;
    }

    /**
     * Disposes of the bomb's texture to free up memory.
     */
    public void dispose() {
        texture.dispose();
    }
}
