package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Character {
    private int x;
    private int y;
    private float speed;
    private int health;
    private boolean alive;
    private int bombCount;
    private int bombRange;

    private boolean moving;
    private float stateTime;

    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;

    public Character(int x, int y, int speed, int health, int bombCount, int bombRange) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.alive = true;
        this.bombCount = bombCount;
        this.bombRange = bombRange;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void increaseSpeed(int speed) {
        this.speed += speed;
    }

    public void increaseHealth(int health) {
        this.health += health;
    }

    public void increaseBombCount(int bombCount) {
        this.bombCount += bombCount;
    }

    public void increaseBombRange(int bombRange) {
        this.bombRange += bombRange;
    }

    public void decreaseHealth(int health) {
        this.health -= health;
        if (this.health <= 0) {
            this.alive = false;
        }
    }

    public void die() {
        this.alive = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
