package de.tum.cit.ase.bomberquest.gameobject.PowerUps;

import de.tum.cit.ase.bomberquest.gameobject.Blast;
import de.tum.cit.ase.bomberquest.gameobject.GameObjectManager;
import de.tum.cit.ase.bomberquest.map.GameMap;

public class BlastRadiusPowerUp extends Blast {
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
    }
}
