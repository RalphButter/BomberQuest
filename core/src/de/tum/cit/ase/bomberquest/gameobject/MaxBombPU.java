package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Entity;

public class MaxBombPU extends Entity {
    public MaxBombPU(Vector2 pos) {
        super(pos, new Texture("maxBombPU.png"));
    }
}
