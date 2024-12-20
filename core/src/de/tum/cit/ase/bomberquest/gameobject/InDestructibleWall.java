package de.tum.cit.ase.bomberquest.gameobject;

public class InDestructibleWall extends Wall {
    public InDestructibleWall(float x, float y, boolean walkable, boolean destructible) {
        super(x, y, false, false);
    }

    @Override
    public void update(float deltaTime) {

    }
}
