package de.tum.cit.ase.bomberquest.gameobject;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        super(x, y, false, false); // Call to the GameObject constructor; 'false' indicates it's not walkable ; 'false' indicates it's not destructible
        this.setTextureRegion(new TextureRegion(getTexture(), 32, 96, 16, 16)); // Set the specific texture region for the entry
        super.setRectangle(new Rectangle((int) (x * 16), (int) (y * 16), 16, 16)); // Define the physical space of the entry
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY());
    }

    @Override
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), 16, 16);
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
