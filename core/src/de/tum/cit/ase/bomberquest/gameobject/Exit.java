package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Entity;

//TODO Eigentlich ist ja nur position und Texture relevant, hier müssen wir vereinfachen
public class Exit extends Entity {

    public Exit(Vector2 pos, Texture texture) {
        super(pos,  new Texture("exit.png"));
    }
}
