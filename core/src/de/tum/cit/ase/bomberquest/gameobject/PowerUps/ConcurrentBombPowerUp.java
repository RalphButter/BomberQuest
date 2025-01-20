package de.tum.cit.ase.bomberquest.gameobject.PowerUps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.gameobject.GameObjectManager;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.Characters.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
//TODO Eigentlich ist ja nur position und Texture relevant, hier müssen wir vereinfachen
//Ich glaube PowerUp brauchen wir evtl. nicht
public class ConcurrentBombPowerUp extends Entity {
    // Call the superclass constructor with the position and texture for the ConcurrentBombPowerUp
    public ConcurrentBombPowerUp(Vector2 pos) {
        super(pos, new Texture("maxBombPU.png"));
    }
}

//    private static final int CONCURRENT_BOMB_INCREMENT = 1;
//    private static final int MAX_CONCURRENT_BOMBS = 8;
//    private Rectangle bounds;
//    /**
//     * Constructor for creating a new ConcurrentBombPowerUp.
//     *
//     * @param x                 The X-coordinate of the power-up.
//     * @param y                 The Y-coordinate of the power-up.
//     * @param gameMap           Reference to the game map.
//     * @param gameObjectManager Reference to the game object manager.
//     */
//    public ConcurrentBombPowerUp(float x, float y, GameMap gameMap, GameObjectManager gameObjectManager) {
//        // Call the superclass constructor with the provided parameters
//        super(x, y, true, false, gameMap, gameObjectManager);
//
//        // Initialize the bounds for collision detection
//        this.bounds = new Rectangle((int)x, (int)y, 32, 32);
//    }
//
//
//    /**
//     * Applies the concurrent bomb increase effect to the player.
//     *
//     * @param player The player who collected the power-up.
//     */
//    @Override
//    public void applyEffect(Player player) {
//        // Check if the player's concurrent bombs are less than the maximum allowed
//        if (player.getConcurrentBombs() < MAX_CONCURRENT_BOMBS) {
//            // Increase the player's concurrent bombs by the increment value
//            player.setConcurrentBombs(player.getConcurrentBombs() + CONCURRENT_BOMB_INCREMENT);
//            // Optionally, you can play a sound or trigger an animation here
//        }
//        // Remove the power-up from the game map after collection
//        gameObjectManager.removeGameObject(this);
//    }
//
//    /*    @Override
//    public void applyEffect(Player player) {
//        if (player.getConcurrentBombs() < MAX_CONCURRENT_BOMBS) {
//            player.setConcurrentBombs(player.getConcurrentBombs() + CONCURRENT_BOMB_INCREMENT);
//        }
//    }*/
//
//    /**
//     * Renders the concurrent bomb power-up on the screen.
//     *
//     * @param g The Graphics object used for rendering.
//     */
//    @Override
//    public void render(Graphics g) {
//        // Draw a distinct visual for the concurrent bomb power-up
//        // For example, a blue circle with a bomb icon or a plus sign
//        g.setColor(Color.BLUE);
//        g.fillOval((int)getX(), (int)getY(), 32, 32);
//        g.setColor(Color.WHITE);
//        // Drawing a simple bomb icon: a rectangle with a fuse
//        g.fillRect((int)getX() + 10, (int)getY() + 12, 12, 8); // Bomb body
//        g.drawLine((int)getX() + 16, (int)getY() + 12, (int)getX()+ 16, (int)getY() + 6); // Fuse
//        g.drawLine((int)getX() + 16, (int)getY() + 6, (int)getX() + 18, (int)getY() + 8); // Fuse tip
//        g.drawLine((int)getX() + 16, (int)getY() + 6, (int)getX() + 14, (int)getY() + 8); // Fuse tip
//    }
//
//    /**
//     * Checks for collision with the player and applies the effect if collided.
//     *
//     * @param player The player object.
//     */
//    public void update(Player player) {
//        // Check if the bounds of this power-up overlap with the player's bounds
//        if (bounds.overlaps(player.getBounds())) {
//            // Apply the effect of the power-up to the player
//            applyEffect(player);
//        }
//    }
//
//
//
//    @Override
//    public void update(float deltaTime) {
//
//    }
//
//    @Override
//    public void render(SpriteBatch batch) {
//
//    }
//
//    @Override
//    public Rectangle getBoundingRectangle() {
//        return null;
//    }
//
//    @Override
//    public boolean isActive() {
//        return false;
//    }

