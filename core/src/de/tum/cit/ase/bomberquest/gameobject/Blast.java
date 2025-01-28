package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an explosion (blast) effect created when a bomb detonates.
 * The blast is animated and exists for a short duration before being removed.
 */
public class Blast extends Entity {

    // SpriteSheet used for the explosion animation
    private SpriteSheet sheet;

    // Timer to track the duration of the explosion before disappearing
    private float deathTime = 0;

    /**
     * Creates a new blast at the specified position.
     * The explosion effect is initialized with an animation.
     *
     * @param pos The position where the blast occurs.
     */
    public Blast(Vector2 pos) {
        super(pos, null);

        // Load explosion animation from sprite sheet
        sheet = new SpriteSheet(new Texture("texture/explo1.png"), 1, 10);

        // Set up the animation to play frames 0 to 9 at 0.1 seconds per frame, non-looping
        sheet.setPlay(0, 9, 0.1f, false);
    }

    /**
     * Updates the blast animation and tracks its lifespan.
     * The blast is marked for removal (`destroyFlag = true`) after 3 seconds.
     */
    @Override
    public void update() {
        super.update();

        // Advance the explosion animation
        sheet.play();

        // Track elapsed time since the explosion started
        deathTime += Gdx.graphics.getDeltaTime();

        // Mark the blast for destruction after 3 seconds
        if (deathTime >= 3) {
            destroyFlag = true;
        }
    }

    /**
     * Draws the blast animation on the screen.
     *
     * @param batch The batch used for rendering.
     */
    @Override
    public void draw(Batch batch) {
        batch.begin();

        // Get the current frame of the explosion animation
        TextureRegion t = sheet.getCurrentFrame();

        // Draw the explosion at its position with proper scaling and centering
        batch.draw(t, pos.x, pos.y,
                sheet.getWidth() / 2, sheet.getHeight() / 2,
                sheet.getWidth(), sheet.getHeight(),
                1, 1, 0);

        batch.end();
    }
}
