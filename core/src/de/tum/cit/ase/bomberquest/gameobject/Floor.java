package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Floor extends GameObject {

        public Floor(float x, float y, boolean walkable, boolean destructible) {
            super(x, y, walkable, false); // Call to the GameObject constructor; 'false' indicates it's not destructible; 'false' indicates it's not walkable
            this.setTextureRegion(new TextureRegion(getTexture(), 0, 32, 16, 16)); // Set the specific texture region for the floor
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
