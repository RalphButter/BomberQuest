package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Character {

    /**
     * Constructor to initialize a Character object.
     *
     * @param x         Initial X coordinate
     * @param y         Initial Y coordinate
     * @param health    Initial health of the character
     * @param rectangle
     */
    public Enemy(float x, float y, int health, Rectangle rectangle) {
        super(x, y, health, rectangle);
    }

    //Needs to be fixed
    public Rectangle getBoundingRectangle() {
        return null;
    }

    public void die() {
    }
}
