package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public abstract class GameObject {
    protected float x, y;  // Position in the game grid (or world coordinates)
    protected boolean walkable;  // Can other objects walk over this?
    protected Texture texture;  // Texture representing the object
    protected TextureRegion textureRegion;  // Subregion for rendering specific parts of the texture
    protected Rectangle boundingBox;  // For collision detection
    protected boolean destructible;  // Can this object be destroyed?

    public GameObject(float x, float y, boolean walkable, boolean destructible, Texture texture) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.destructible = destructible;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);  // Default region
        this.boundingBox = new Rectangle(x,y, texture.getWidth(), texture.getHeight());
    }

    // Getters and setters
    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public boolean isWalkable() { return walkable; }
    public void setWalkable(boolean walkable) { this.walkable = walkable; }
    public boolean isDestructible() { return destructible; }
    public void setDestructible(boolean destructible) { this.destructible = destructible; }

    // Render method
    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, x, y);
    }

    // For collision detection
    public boolean intersects(Rectangle other) {
        return boundingBox.overlaps(other);
    }

    // Abstract method for specific updates (to be implemented by subclasses)
    public abstract void update(float deltaTime);

    // Dispose resources
    public void dispose() {
        texture.dispose();
    }
}

