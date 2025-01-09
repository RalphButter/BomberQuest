package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DestructibleWall extends Wall{


    public DestructibleWall(float x, float y, boolean walkable, boolean destructible) {
        super(x, y, true, true); // Set the walkable and destructible properties; 'true' indicates it's walkable; 'true' indicates it's destructible
        setTextureRegion(new TextureRegion(getTexture(), 32, 0, 16, 16)); // Set the specific texture region for the wall
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
