package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.math.Rectangle;

public abstract class Wall extends GameObject {
    /**
     * Constructor for creating a new GameObject.
     *
     * @param x            The X-coordinate of the game object.
     * @param y            The Y-coordinate of the game object.
     * @param walkable     Indicates whether the object is walkable.
     * @param destructible Indicates whether the object is destructible or not (destructible and non-destructible walls)
     */
    private boolean destroyed;
    public Wall(float x, float y, boolean walkable, boolean destructible) {
        super(x, y, walkable, destructible);
        super.setRectangle(new Rectangle((int) (x * 16), (int) (y * 16), 16, 16));
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public abstract void update(float deltaTime);
}
