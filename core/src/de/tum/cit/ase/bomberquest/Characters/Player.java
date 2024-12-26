package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    /**
     * Constructor to initialize a Character object.
     *
     * @param x      Initial X coordinate
     * @param y      Initial Y coordinate
     * @param health Initial health of the character
     */
    public Player(float x, float y, int health) {
    super(x, y, health, new Rectangle(x + 2, y + 2, 12, 12));
    this.texture = new Texture(Gdx.files.internal("assets/character.png"));

    java.util.List<TextureRegion> walkUpFrames = new ArrayList<>();
    walkUpFrames.add(new TextureRegion(texture, 0, 64, 16, 32));
    walkUpFrames.add(new TextureRegion(texture, 16, 64, 16, 32));
    walkUpFrames.add(new TextureRegion(texture, 32, 64, 16, 32));
    walkUpFrames.add(new TextureRegion(texture, 48, 64, 16, 32));
    setWalkUpAnimation(new Animation<>(0.1f, walkUpFrames.toArray(new TextureRegion[0])));

    java.util.List<TextureRegion> walkDownFrames = new ArrayList<>();
    walkDownFrames.add(new TextureRegion(texture, 0, 0, 16, 32));
    walkDownFrames.add(new TextureRegion(texture, 16, 0, 16, 32));
    walkDownFrames.add(new TextureRegion(texture, 32, 0, 16, 32));
    walkDownFrames.add(new TextureRegion(texture, 48, 0, 16, 32));
    setWalkDownAnimation(new Animation<>(0.1f, walkDownFrames.toArray(new TextureRegion[0])));

    java.util.List<TextureRegion> walkLeftFrames = new ArrayList<>();
    walkLeftFrames.add(new TextureRegion(texture, 0, 96, 16, 32));
    walkLeftFrames.add(new TextureRegion(texture, 16, 96, 16, 32));
    walkLeftFrames.add(new TextureRegion(texture, 32, 96, 16, 32));
    walkLeftFrames.add(new TextureRegion(texture, 48, 96, 16, 32));
    setWalkLeftAnimation(new Animation<>(0.1f, walkLeftFrames.toArray(new TextureRegion[0])));

    List<TextureRegion> walkRightFrames = new ArrayList<>();
    walkRightFrames.add(new TextureRegion(texture, 0, 32, 16, 32));
    walkRightFrames.add(new TextureRegion(texture, 16, 32, 16, 32));
    walkRightFrames.add(new TextureRegion(texture, 32, 32, 16, 32));
    walkRightFrames.add(new TextureRegion(texture, 48, 32, 16, 32));
    setWalkRightAnimation(new Animation<>(0.1f, walkRightFrames.toArray(new TextureRegion[0])));

    List<TextureRegion> standingFrames = new ArrayList<>();
    standingFrames.add(new TextureRegion(texture, 0, 0, 16, 32)); // Assuming the first frame of the down animation is the standing frame
        // setStadingAnimation(new Animation<>(0.1f, standingFrames.toArray(new TextureRegion[0])));

        this.setSpeed(1f);
    this.setHealth(1);
}

    public void moveUp() {
        setY(getY() + getSpeed() * Gdx.graphics.getDeltaTime());
        setCurrentDirection(Direction.UP);
    }

    public void moveDown() {
        setY(getY() - getSpeed() * Gdx.graphics.getDeltaTime());
        setCurrentDirection(Direction.DOWN);
    }

    public void moveLeft() {
        setX(getX() - getSpeed() * Gdx.graphics.getDeltaTime());
        setCurrentDirection(Direction.LEFT);
    }

    public void moveRight() {
        setX(getX() + getSpeed() * Gdx.graphics.getDeltaTime());
        setCurrentDirection(Direction.RIGHT);
    }

    public void render(SpriteBatch batch) { // Render the player sprite
        TextureRegion currentFrame;
        switch (getCurrentDirection()) { // Determine the current frame based on the direction
            case UP:
                currentFrame = getWalkUpAnimation().getKeyFrame(getStateTime(), true);
                break;
            case DOWN:
                currentFrame = getWalkDownAnimation().getKeyFrame(getStateTime(), true);
                break;
            case LEFT:
                currentFrame = getWalkLeftAnimation().getKeyFrame(getStateTime(), true);
                break;
            case RIGHT:
                currentFrame = getWalkRightAnimation().getKeyFrame(getStateTime(), true);
                break;
            default:
                currentFrame = getWalkDownAnimation().getKeyFrame(0, true); // Default to standing frame
                break;
        }
        batch.draw(currentFrame, getX(), getY());
    }

    public void update(float deltaTime) {
        // Update the state time, e.g., for animations
        setStateTime(getStateTime() + deltaTime);
        // Handle user input for movement
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight();
        }
    }

    public void setIsMoving(boolean moving) {
        setMoving(moving);
        if (!isMoving()) {
            setCurrentDirection(Direction.STANDING);
        }
    }

    public void dispose() {
        texture.dispose();
    }

}
