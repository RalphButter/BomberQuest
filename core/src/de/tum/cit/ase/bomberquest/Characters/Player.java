package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player character in the game with combined functionality.
 */
public class Player extends Character implements Drawable {

    private final World world;
    private final Body hitbox;

    private int blastRadius = 1; // Initial blast radius
    private int maxBlastRadius = 8;
    private int concurrentBombs;
    private Rectangle bounds;
    private float elapsedTime;

    public Player(World world, float x, float y) {
        super(x, y, new Rectangle(x, y, 16, 32));
        this.world = world;
        this.hitbox = createHitbox(world, x, y);
        this.setTexture(new Texture(Gdx.files.internal("assets/character.png")));

        // Setting animations
        this.setWalkUpAnimation(createAnimation(64, 16, 32));
        this.setWalkDownAnimation(createAnimation(0, 16, 32));
        this.setWalkLeftAnimation(createAnimation(96, 16, 32));
        this.setWalkRightAnimation(createAnimation(32, 16, 32));
    }

    /**
     * Creates a Box2D body for the player.
     * This is what the physics engine uses to move the player around and detect collisions with other bodies.
     *
     * @param world  The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @return The created body.
     */
    private Body createHitbox(World world, float startX, float startY) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Dynamic bodies are affected by forces and collisions.
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set the initial position of the body.
        bodyDef.position.set(startX, startY);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a circle shape for the player.
        CircleShape circle = new CircleShape();
        // Give the circle a radius of 0.3 tiles (the player is 0.6 tiles wide).
        circle.setRadius(0.3f);
        // Attach the shape to the body as a fixture.
        // Bodies can have multiple fixtures, but we only need one for the player.
        body.createFixture(circle, 1.0f);
        // We're done with the shape, so we should dispose of it to free up memory.
        circle.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later.
        body.setUserData(this);
        return body;
    }

    private Animation<TextureRegion> createAnimation(int yOffset, int frameWidth, int frameHeight) {
        List<TextureRegion> frames = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * frameWidth, yOffset, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, frames.toArray(new TextureRegion[0]));
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        hitbox.setTransform(x, y, 0);
    }

    /**
     * Move the player around in a circle by updating the linear velocity of its hitbox every frame.
     * This doesn't actually move the player, but it tells the physics engine how the player should move next frame.
     *
     * @param frameTime the time since the last frame.
     */
    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        // Make the player move in a circle with radius 2 tiles
        // You can change this to make the player move differently, e.g. in response to user input.
        // See Gdx.input.isKeyPressed() for keyboard input
        float xVelocity = (float) Math.sin(this.elapsedTime) * 2;
        float yVelocity = (float) Math.cos(this.elapsedTime) * 2;
        this.hitbox.setLinearVelocity(xVelocity, yVelocity);
    }

    public void update(float deltaTime) {
        setStateTime(getStateTime() + deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(Direction.UP, 0, getSpeed() * deltaTime);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(Direction.DOWN, 0, -getSpeed() * deltaTime);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move(Direction.LEFT, -getSpeed() * deltaTime, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(Direction.RIGHT, getSpeed() * deltaTime, 0);
        } else {
            setMoving(false);
            setCurrentDirection(Direction.STANDING);
        }
    }

    private void move(Direction direction, float dx, float dy) {
        setX(getX() + dx);
        setY(getY() + dy);
        setMoving(true);
        setCurrentDirection(direction);
        hitbox.setTransform(getX(), getY(), 0);
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;
        switch (getCurrentDirection()) {
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
                currentFrame = getWalkDownAnimation().getKeyFrame(0, true);
                break;
        }
        batch.draw(currentFrame, getX(), getY());
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        if (blastRadius <= maxBlastRadius) {
            this.blastRadius = blastRadius;
            // Update HUD or any other related components if necessary
        }
    }

    public int getMaxBlastRadius() {
        return maxBlastRadius;
    }

    public void setMaxBlastRadius(int maxBlastRadius) {
        this.maxBlastRadius = maxBlastRadius;
    }
    public int[] getPosition() {
        return new int[]{(int) getX(), (int) getY()};
    }

    public void moveUp() {
        prevY = this.getY();  // player.setPrevY(player.getyPlayer()); is set to previous yPlayer
        this.setY(this.getY()+getSpeed()); // Move up
        setCurrentDirection(Direction.UP);
        setIsMoving(true);
    }

    public void moveDown() {
        prevY = this.getY();  // player.setPrevY(player.getyPlayer()); is set to previous yPlayer
        this.setY(this.getY()-getSpeed()); // Move down
        setCurrentDirection(Direction.DOWN);
        setIsMoving(true);
    }

    public void moveLeft() {
        prevX = this.getX();  // player.setPrevX(player.getxPlayer()); is set to previous xPlayer
        this.setX(this.getX()-getSpeed()); // Move left
        setCurrentDirection(Direction.LEFT);
        setIsMoving(true);
    }

    public void moveRight() {
        prevX = this.getX();  // player.setPrevX(player.getxPlayer()); is set to previous xPlayer
        this.setX(this.getX()+getSpeed()); // Move right
        setCurrentDirection(Direction.RIGHT);
        setIsMoving(true);
    }

    public void setIsMoving(boolean moving) {
        setMoving(moving);
        if(!isMoving()) {
            setCurrentDirection(Direction.STANDING);
        }
    }


    public void dispose() {
        getTexture().dispose();
    }


    public TextureRegion getCurrentAppearance() {
        // Get the frame of the walk down animation that corresponds to the current time.
        return Animations.CHARACTER_WALK_DOWN.getKeyFrame(this.elapsedTime, true);
    }

    @Override
    public float getX() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().x;
    }

    @Override
    public float getY() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().y;
    }

    public int getConcurrentBombs() {
        return concurrentBombs;
    }

    public void setConcurrentBombs(int concurrentBombs) {
        this.concurrentBombs = concurrentBombs;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void die() {

    }


}
