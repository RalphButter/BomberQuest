package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class InDestructibleWall extends Wall {
    public InDestructibleWall(float x, float y, boolean walkable, boolean destructible) {
        super(x, y, false, false); // Call to the GameObject constructor; 'false' indicates it's not walkable ; 'false' indicates it's not destructible
        super.setRectangle(new Rectangle((int) (x * 16), (int) (y * 16), 16, 16)); // Define the physical space of the Wall
        setTextureRegion(new TextureRegion(getTexture2(), 0, 80, 16, 16)); // Set the specific texture region for the floor
    }

    @Override
    public void update(float deltaTime) {

    }


}
