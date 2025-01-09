package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Character {
    private float x;  // X-coordinate of the character
    private float y;  // Y-coordinate of the character
    float prevX;    // Previous X-coordinate of the character
    float prevY;   // Previous Y-coordinate of the character
    private float speed;    // Speed of the character
    private int health;     // Health of the character
    private boolean alive;  // Flag indicating if the character is alive
    private int bombCount; // Number of bombs the character can place
    private int bombRange;  // Range of the bombs placed by the character

    private boolean moving; // Flag indicating if the character is moving
    private float stateTime;   // Time elapsed since the animation started

    Texture texture;   // Texture for the character

    private Rectangle rectangle;   // Represents the character's physical space in the game world (used for collision detection)

    private Direction currentDirection = Direction.STANDING; // Current direction of the character
    private Animation<TextureRegion> walkUpAnimation;   // Animation for character movement in the up direction
    private Animation<TextureRegion> walkDownAnimation; // Animation for character movement in the down direction
    private Animation<TextureRegion> walkLeftAnimation; // Animation for character movement in the left direction
    private Animation<TextureRegion> walkRightAnimation;   // Animation for character movement in the right direction
    private Animation<TextureRegion> stadingAnimation; // Animation for character standing still

    /**
     * Constructor to initialize a Character object.
     *
     * @param x Initial X coordinate
     * @param y Initial Y coordinate
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

    public void move(int x, int y) {  // Move the character to the specified position
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public float getPrevX() {
        return prevX;
    }

    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void setPrevY(float prevY) {
        this.prevY = prevY;
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

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPosition(float x, float y) { this.x = x; this.y = y; }

    public int[] getPosition () { return new int[]{(int) getX(), (int) getY()}; }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
    public Rectangle getRectangle() {
        return rectangle;
    }

    public Animation<TextureRegion> getStadingAnimation() {
        return stadingAnimation;
    }

    public void setStadingAnimation(Animation<TextureRegion> stadingAnimation) {
        this.stadingAnimation = stadingAnimation;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
