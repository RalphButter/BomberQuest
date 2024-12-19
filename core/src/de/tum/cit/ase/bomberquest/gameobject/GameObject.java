package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected float x, y;  // Position in the game grid (or world coordinates)
    protected boolean walkable;  // Can other objects walk over this?
    protected Texture texture;  // Texture representing the object
    protected Rectangle boundingBox;  // For collision detection
    protected boolean destructible;  // Deciding whether the object can be destroyed or not
    protected Animation<TextureRegion> animation;  // Optional animation for the object
    protected float stateTime;  // Time for animation state

    /**
     * Constructor for creating a new GameObject.
     *
     * @param x         The X-coordinate of the game object.
     * @param y         The Y-coordinate of the game object.
     * @param walkable  Indicates whether the object is walkable.
     * @param destructible Indicates whether the object is destructible or not (destructible and non-destructible walls)
     * @param texture defines the primary texture of the object based on the provided assets
     */
    public GameObject(float x, float y, boolean walkable, boolean destructible, Texture texture) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.destructible = destructible;
        this.texture = new Texture(Gdx.files.internal("assets/texture/basictiles.png"));
        this.boundingBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.stateTime = 0f;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    // Render method
    public void render(SpriteBatch batch) {
        if (animation != null) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
        } else {
            batch.draw(texture, x, y);
        }
    }

    // Abstract method for specific updates (to be implemented by subclasses)
    public abstract void update(float deltaTime);

    // Dispose resources
    public void dispose() {
        texture.dispose();
    }
}