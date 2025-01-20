package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

//TODO GameObject aufteilen in GameObject und Entity

public class GameObject {
    private float x, y;  // Position in the game grid (or world coordinates)
    private boolean walkable;  // Can other objects walk over this?
    private
    Texture texture;
    private
    Texture texture2;// Texture representing the object
    private boolean destructible;  // Deciding whether the object can be destroyed or not
    private TextureRegion textureRegion;
    private Rectangle rectangle;

    public CellType cellType;

    public int row;
    public int col;

    /**
     * Constructor for creating a new GameObject.
     *
     * @param x         The X-coordinate of the game object.
     * @param y         The Y-coordinate of the game object.
     * @param walkable  Indicates whether the object is walkable.
     * @param destructible Indicates whether the object is destructible or not (destructible and non-destructible walls)
     */
    public GameObject(float x, float y, boolean walkable, boolean destructible) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.destructible = destructible;
        this.texture = new Texture(Gdx.files.internal("assets/texture/basictiles.png")); // Load the texture for the object
        this.texture2 = new Texture(Gdx.files.internal("assets/texture/things.png")); // Load the secondary texture for the object in case an object gets destroyed
    }

    public GameObject(int row, int col, CellType cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
    }

    // Standard getters and setters for game object properties
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

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public Texture getTexture2() {
        return texture2;
    }



    // Abstract method for specific updates (to be implemented by subclasses)
    //public abstract void update(float deltaTime);

    //public abstract void render(SpriteBatch batch);

    // Getter for the bounding rectangle of the object
    //public abstract Rectangle getBoundingRectangle();

    // Dispose resources
    public void dispose() {
        texture.dispose();
    }
    //Needs to be fixed?
    //public abstract boolean isActive();




    public Rectangle getRect(){
        return new Rectangle(col * 16,row * 16,16,16);
    }
}