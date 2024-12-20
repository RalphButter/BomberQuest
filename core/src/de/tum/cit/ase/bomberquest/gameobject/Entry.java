package de.tum.cit.ase.bomberquest.gameobject;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Entry extends GameObject{

    /**
     * Constructor for creating a new GameObject.
     *
     * @param x            The X-coordinate of the game object.
     * @param y            The Y-coordinate of the game object.
     * @param walkable     Indicates whether the object is walkable.
     * @param destructible Indicates whether the object is destructible or not (destructible and non-destructible walls)
     */
    public Entry(float x, float y, boolean walkable, boolean destructible) {
        super(x, y, false, false);
        this.setTextureRegion(new TextureRegion(getTexture(), 32, 96, 16, 16));
        super.setRectangle(new Rectangle((int) (x * 16), (int) (y * 16), 16, 16));
    }

    @Override
    public void update(float deltaTime) {

    }
}
