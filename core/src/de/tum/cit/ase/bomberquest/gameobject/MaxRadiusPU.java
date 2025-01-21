package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.Texture;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a power-up that increases the maximum radius of the player's bombs.
 * This class extends the Entity class and initializes the power-up with a specific texture.
 */
public class MaxRadiusPU extends Entity {

    /**
     * Constructor to create a MaxRadiusPU object at a specified position.
     *
     * @param pos The position where the power-up will be placed.
     */
    public MaxRadiusPU(Vector2 pos) {
        super(pos, new Texture("maxRadiusPU.png"));
    }
}