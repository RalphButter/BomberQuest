package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Exit extends GameObject {
    /**
     * Constructor for creating a new GameObject.
     *
     * @param x            The X-coordinate of the game object.
     * @param y            The Y-coordinate of the game object.
     * @param walkable     Indicates whether the object is walkable.
     * @param destructible Indicates whether the object is destructible or not (destructible and non-destructible walls)
     */
    public Exit(float x, float y, boolean walkable, boolean destructible) {
        super(x, y, walkable, destructible);
        this.setTextureRegion(new TextureRegion(getTexture(), 0, 96, 16, 16));
        super.setRectangle(new Rectangle((int) (x * 16), (int) (y * 16), 16, 16));
    }

    @Override
    public void update(float deltaTime) {

    }
}
