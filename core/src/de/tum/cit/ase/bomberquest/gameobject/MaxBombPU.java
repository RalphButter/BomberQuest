package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Entity;

/**
 * Represents a power-up that increases the maximum number of bombs a player can place.
 * This class extends the Entity class and initializes the power-up with a specific texture.
 */
public class MaxBombPU extends Entity {

    /**
     * Constructor to create a MaxBombPU object at a specified position.
     *
     * @param pos The position where the power-up will be placed.
     */
    public MaxBombPU(Vector2 pos) {
        super(pos, new Texture("texture/maxBombPU.png"));
    }
}