package de.tum.cit.ase.bomberquest.gameobject.PowerUps;

import de.tum.cit.ase.bomberquest.gameobject.Blast;
import de.tum.cit.ase.bomberquest.gameobject.GameObjectManager;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.map.Player;

import java.awt.*;

public class BlastRadiusPowerUp extends Blast {
    private static final int BLAST_RADIUS_INCREMENT = 1;
    private static final int MAX_BLAST_RADIUS = 8;
    private Rectangle bounds; // For collision detection

    /**
     * Constructor for creating a new Blast.
     *
     * @param x                 The X-coordinate of the bomb.
     * @param y                 The Y-coordinate of the bomb.
     * @param blastRadius       The radius of the blast.
     * @param blastDuration     How long the blast remains active.
     * @param blastDamage       Damage dealt by the blast.
     * @param gameMap           Reference to the game map.
     * @param gameObjectManager Reference to the game object manager.
     */
    public BlastRadiusPowerUp(float x, float y, int blastRadius, float blastDuration, int blastDamage, GameMap gameMap, GameObjectManager gameObjectManager) {
        // Call the superclass constructor with the provided parameters
        super(x, y, blastRadius, blastDuration, blastDamage, gameMap, gameObjectManager);

        // Initialize the bounds for collision detection
        this.bounds = new Rectangle((int)x, (int)y, 32, 32);
    }


    /*
     * Applies the blast radius increase effect to the player.
     *
     * @param player The player who collected the power-up.
     */
    /*@Override
    public void applyEffect(Player player) {
        if (player.getBlastRadius() < MAX_BLAST_RADIUS) {
            player.setBlastRadius(player.getBlastRadius() + BLAST_RADIUS_INCREMENT);
            // Optionally, you can play a sound or trigger an animation here
        }
        // Remove the power-up from the game map after collection
        gameObjectManager.removeGameObject(this);
    }*/

    /**
     * Renders the blast radius power-up on the screen.
     *
     * @param g The Graphics object used for rendering.
     */
    //BITTE ANPASSEN!!!
    public void render(Graphics g) {
        // Set the color to red for the outer circle of the power-up
        g.setColor(Color.RED); // This sets the color to red

        // Draw a filled oval (circle) at the power-up's coordinates with a width and height of 32 pixels
        g.fillOval((int)getX(), (int)getY(), 32, 32);

        // Set the color to white for the plus sign inside the circle
        g.setColor(Color.WHITE); // This sets the color to white

        // Draw a vertical line for the plus sign
        g.drawLine((int)getX() + 16, (int)getY() + 8, (int)getX() + 16, (int)getY() + 24);

        // Draw a horizontal line for the plus sign
        g.drawLine((int)getX() + 8, (int)getY() + 16, (int)getX() + 24, (int)getY() + 16);
    }
    /**
     * Checks for collision with the player and applies the effect if collided.
     *
     * @param player The player object.
     */

    //BITTE ANPASSEN!!!

    /*@Override
    public void update(Player player) {
        if (bounds.overlaps(player.getBounds())) {
            applyEffect(player);
        }
    }*/



}
