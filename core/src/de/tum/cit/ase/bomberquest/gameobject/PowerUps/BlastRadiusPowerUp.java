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
        super(x, y, blastRadius, blastDuration, blastDamage, gameMap, gameObjectManager);
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
        // Draw a distinct visual for the blast radius power-up
        // For example, a red circle with a plus sign
        g.setColor(Color.RED); //Not sure if this is correct
        g.fillOval((int)getX(), (int)getY(), 32, 32);
        g.setColor(Color.WHITE);
        g.drawLine((int)getX() + 16, (int)getY() + 8, (int)getX() + 16, (int)getY() + 24);
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
        if (bounds.intersects(player.getBounds())) {
            applyEffect(player);
        }
    }*/



}
