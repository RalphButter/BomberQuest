package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.Texture;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import com.badlogic.gdx.math.Vector2;

public class MaxRadiusPU extends Entity {
    public MaxRadiusPU(Vector2 pos) {
        super(pos, new Texture("maxRadiusPU.png"));
    }
}
