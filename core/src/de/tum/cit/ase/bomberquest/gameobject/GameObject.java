package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.util.Rectangle;

// TODO: Split GameObject into GameObject and Entity

/**
 * Represents a basic game object in the game world.
 * This class stores position and type information but lacks advanced behavior.
 * Future versions may separate static objects (GameObject) from dynamic entities (Entity).
 */
public class GameObject {

    // Grid position of the object
    public int row;
    public int col;

    // Type of the cell (e.g., wall, floor, power-up, etc.)
    public CellType cellType;

    /**
     * Constructs a game object at the specified grid location with a given type.
     *
     * @param row      The row position in the grid.
     * @param col      The column position in the grid.
     * @param cellType The type of the object (e.g., destructible wall, power-up, etc.).
     */
    public GameObject(int row, int col, CellType cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
    }

    /**
     * Returns the bounding rectangle for collision detection.
     * The rectangle is positioned in world coordinates based on the tile size.
     *
     * @return A Rectangle object representing the collision box of this game object.
     */
    public Rectangle getRect() {
        return new Rectangle(col * 16, row * 16, 16, 16);
    }
}
