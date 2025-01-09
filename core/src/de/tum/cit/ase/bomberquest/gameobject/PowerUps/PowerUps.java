package de.tum.cit.ase.bomberquest.gameobject.PowerUps;

import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.gameobject.GameObjectManager;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.Characters.Player;

import java.awt.*;

abstract class PowerUp  extends GameObject {
    protected GameMap gameMap;
    protected GameObjectManager gameObjectManager;

    /**
     * Constructor for creating a new GameObject.
     *
     * @param x            The X-coordinate of the game object.
     * @param y            The Y-coordinate of the game object.
     * @param walkable     Indicates whether the object is walkable.
     * @param destructible Indicates whether the object is destructible or not (destructible and non-destructible walls)
     */
    public PowerUp(float x, float y, boolean walkable, boolean destructible, GameMap gameMap, GameObjectManager gameObjectManager) {
        // Call the superclass constructor to initialize position, walkable, and destructible properties
        super(x, y, walkable, destructible);
        // Initialize the game map reference
        this.gameMap = gameMap;
        // Initialize the game object manager reference
        this.gameObjectManager = gameObjectManager;

    }



    /**
     * Applies the power-up effect to the player.
     *
     * @param player The player who collected the power-up.
     */
    public abstract void applyEffect(Player player);


    /**
     * Renders the power-up on the screen.
     *
     * @param g The Graphics object used for rendering.
     */
    public abstract void render(Graphics g);

}