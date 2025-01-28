package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represents an abstract base class for all playable and non-playable characters in the game.
 * This class provides movement handling, health management, bomb placement properties,
 * and animations for different states.
 *
 * NOTE: Marked for deletion in the comments. If still used, it should serve as a base class
 * for specific character types such as players and enemies.
 */
public abstract class Character {

    // Character's position in the game world
    private float x;
    private float y;

    // Character's previous position (used for movement tracking)
    private float prevX;
    private float prevY;

    // Character attributes
    private float speed; // Movement speed
    private int health;  // Health level
    private boolean alive; // Indicates if the character is alive
    private int bombCount; // Number of bombs the character can place
    private int bombRange; // Range of bomb explosions

    // Movement and animation properties
    private boolean moving; // Indicates if the character is currently moving
    private float stateTime; // Timer for animation playback

    // Texture representation of the character
    private Texture texture;

    // Collision detection rectangle
    private Rectangle rectangle;

    // Character's movement direction
    private Direction currentDirection = Direction.STANDING;

    // Character animations for movement
    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> standingAnimation;

    /**
     * Constructs a new Character at the specified position with a given hitbox.
     *
     * @param x         The initial X coordinate.
     * @param y         The initial Y coordinate.
     * @param rectangle The bounding rectangle for collision detection.
     */
    public Character(float x, float y, Rectangle rectangle) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.speed = 1f;
        this.moving = false;
        this.alive = true;
        this.rectangle = rectangle;
    }

    /**
     * Moves the character to the specified coordinates.
     *
     * @param x New X position.
     * @param y New Y position.
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Increases the character's movement speed. */
    public void increaseSpeed(int speed) {
        this.speed += speed;
    }

    /** Increases the character's health. */
    public void increaseHealth(int health) {
        this.health += health;
    }

    /** Increases the number of bombs the character can place. */
    public void increaseBombCount(int bombCount) {
        this.bombCount += bombCount;
    }

    /** Increases the explosion range of the character's bombs. */
    public void increaseBombRange(int bombRange) {
        this.bombRange += bombRange;
    }

    /**
     * Decreases the character's health. If health reaches 0, the character is marked as dead.
     *
     * @param health The amount of health to decrease.
     */
    public void decreaseHealth(int health) {
        this.health -= health;
        if (this.health <= 0) {
            this.alive = false;
        }
    }

    /** Marks the character as dead. */
    public void die() {
        this.alive = false;
    }

    // Position Getters and Setters
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getPrevX() { return prevX; }
    public void setPrevX(float prevX) { this.prevX = prevX; }
    public float getPrevY() { return prevY; }
    public void setPrevY(float prevY) { this.prevY = prevY; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public int[] getPosition() { return new int[]{(int) getX(), (int) getY()}; }

    // Character Stats Getters and Setters
    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public int getBombCount() { return bombCount; }
    public void setBombCount(int bombCount) { this.bombCount = bombCount; }
    public int getBombRange() { return bombRange; }
    public void setBombRange(int bombRange) { this.bombRange = bombRange; }

    // Animation and Movement State
    public float getStateTime() { return stateTime; }
    public void setStateTime(float stateTime) { this.stateTime = stateTime; }
    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }

    // Texture Handling
    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }

    // Animations Getters and Setters
    public Animation<TextureRegion> getWalkUpAnimation() { return walkUpAnimation; }
    public void setWalkUpAnimation(Animation<TextureRegion> walkUpAnimation) { this.walkUpAnimation = walkUpAnimation; }
    public Animation<TextureRegion> getWalkDownAnimation() { return walkDownAnimation; }
    public void setWalkDownAnimation(Animation<TextureRegion> walkDownAnimation) { this.walkDownAnimation = walkDownAnimation; }
    public Animation<TextureRegion> getWalkLeftAnimation() { return walkLeftAnimation; }
    public void setWalkLeftAnimation(Animation<TextureRegion> walkLeftAnimation) { this.walkLeftAnimation = walkLeftAnimation; }
    public Animation<TextureRegion> getWalkRightAnimation() { return walkRightAnimation; }
    public void setWalkRightAnimation(Animation<TextureRegion> walkRightAnimation) { this.walkRightAnimation = walkRightAnimation; }
    public Animation<TextureRegion> getStandingAnimation() { return standingAnimation; }
    public void setStandingAnimation(Animation<TextureRegion> standingAnimation) { this.standingAnimation = standingAnimation; }

    // Direction Getters and Setters
    public Direction getCurrentDirection() { return currentDirection; }
    public void setCurrentDirection(Direction currentDirection) { this.currentDirection = currentDirection; }

    // Collision Detection
    public Rectangle getRectangle() { return rectangle; }
    public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }
}
