package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Character {
    private int x;  // X-coordinate of the character
    private int y;  // Y-coordinate of the character
    private float speed;    // Speed of the character
    private int health;     // Health of the character
    private boolean alive;  // Flag indicating if the character is alive
    private int bombCount; // Number of bombs the character can place
    private int bombRange;  // Range of the bombs placed by the character

    private boolean moving; // Flag indicating if the character is moving
    private float stateTime;   // Time elapsed since the animation started

    Texture texture;

    private Direction currentDirection = Direction.STANDING; // Current direction of the character
    private Animation<TextureRegion> walkUpAnimation;   // Animation for character movement in the up direction
    private Animation<TextureRegion> walkDownAnimation; // Animation for character movement in the down direction
    private Animation<TextureRegion> walkLeftAnimation; // Animation for character movement in the left direction
    private Animation<TextureRegion> walkRightAnimation;   // Animation for character movement in the right direction

    /**
     * Constructor to initialize a Character object.
     *
     * @param x Initial X coordinate
     * @param y Initial Y coordinate
     * @param health Initial health of the character
     */
    public Character(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.speed = 1f;
        this.moving = false;
        this.health = health;
        this.alive = true;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Animation<TextureRegion> getWalkUpAnimation() {
        return walkUpAnimation;
    }

    public void setWalkUpAnimation(Animation<TextureRegion> walkUpAnimation) {
        this.walkUpAnimation = walkUpAnimation;
    }

    public Animation<TextureRegion> getWalkDownAnimation() {
        return walkDownAnimation;
    }

    public void setWalkDownAnimation(Animation<TextureRegion> walkDownAnimation) {
        this.walkDownAnimation = walkDownAnimation;
    }

    public Animation<TextureRegion> getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public void setWalkLeftAnimation(Animation<TextureRegion> walkLeftAnimation) {
        this.walkLeftAnimation = walkLeftAnimation;
    }

    public Animation<TextureRegion> getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public void setWalkRightAnimation(Animation<TextureRegion> walkRightAnimation) {
        this.walkRightAnimation = walkRightAnimation;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
}
