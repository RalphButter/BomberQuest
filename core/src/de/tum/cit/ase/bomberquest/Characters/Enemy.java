package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.gameobject.Wall;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.util.CollisionDetection;
import de.tum.cit.ase.bomberquest.MapManager.Node;

import java.util.List;

public class Enemy extends Character implements Drawable {
    private Pathfinding pathfinding;
    private List<int[]> path;
    private int pathIndex;
    private GameObject gameObject;
    private float prevX, prevY;
    private int[] target;

    public Enemy(float x, float y, int[][] grid) {
        super(x, y, new Rectangle(x + 2, y + 2, 12, 12));
        this.texture = new Texture(Gdx.files.internal("assets/mobs.png"));

        TextureRegion[] walkUpFrames = {
                new TextureRegion(texture, 96, 112, 16, 16),
                new TextureRegion(texture, 112, 112, 16, 16),
                new TextureRegion(texture, 128, 112, 16, 16),
        };
        setWalkUpAnimation(new Animation<>(0.1f, walkUpFrames));

        TextureRegion[] walkDownFrames = {
                new TextureRegion(texture, 64, 96, 16, 16),
                new TextureRegion(texture, 80, 96, 16, 16),
                new TextureRegion(texture, 96, 96, 16, 16),
        };
        setWalkDownAnimation(new Animation<>(0.1f, walkDownFrames));

        TextureRegion[] walkLeftFrames = {
                new TextureRegion(texture, 96, 80, 16, 16),
                new TextureRegion(texture, 112, 80, 16, 16),
                new TextureRegion(texture, 128, 80, 16, 16),
        };
        setWalkLeftAnimation(new Animation<>(0.1f, walkLeftFrames));

        TextureRegion[] walkRightFrames = {
                new TextureRegion(texture, 96, 96, 16, 16),
                new TextureRegion(texture, 112, 96, 16, 16),
                new TextureRegion(texture, 128, 96, 16, 16),
        };
        setWalkRightAnimation(new Animation<>(0.1f, walkRightFrames));

        setSpeed(1.5f);
        setHealth(1);

        this.pathfinding = new Pathfinding(grid);
        this.path = null;
        this.pathIndex = 0;
        this.target = new int[]{0, 0}; // Initialize the target position
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

//    public void move(int[] target) {
//        if (path == null || pathIndex >= path.size()) {
//            path = pathfinding.findPath(new int[]{(int) getX(), (int) getY()}, target, "A*");
//            pathIndex = 0;
//        }
//
//        if (path != null && pathIndex < path.size()) {
//            int[] nextStep = path.get(pathIndex);
//            float nextX = nextStep[0];
//            float nextY = nextStep[1];
//
//            Rectangle nextStepRect = new Rectangle(nextX, nextY, 16, 16);
//            Rectangle gameObjectRect = gameObject.getRectangle();
//
//            if (CollisionDetection.isColliding(nextX, nextY, 16, 16, getX(), getY(), getRectangle().width, getRectangle().height)) {
//                handleCollision();
//                return;
//            }
//
//            if (getX() < nextX) moveRight();
//            else if (getX() > nextX) moveLeft();
//            else if (getY() < nextY) moveUp();
//            else if (getY() > nextY) moveDown();
//
//            if (Math.abs(getX() - nextX) < 0.1f && Math.abs(getY() - nextY) < 0.1f) {
//                pathIndex++;
//            }
//        }
//    }

    public void move(int[] target, List<Wall> walls) {
        if (path == null || pathIndex >= path.size()) {
            path = pathfinding.findPath(new int[]{(int) getX(), (int) getY()}, target, "A*");
            pathIndex = 0;
        }

        if (path != null && pathIndex < path.size()) {
            int[] nextStep = path.get(pathIndex);
            float nextX = nextStep[0];
            float nextY = nextStep[1];

            // Check for collisions
            if (!isCollidingWithObject(nextX, nextY, walls)) {
                // Move towards the next step
                if (getX() < nextX) moveRight();
                else if (getX() > nextX) moveLeft();
                else if (getY() < nextY) moveUp();
                else if (getY() > nextY) moveDown();

                // Check if the enemy is close enough to the next step
                if (Math.abs(getX() - nextX) < 0.1f && Math.abs(getY() - nextY) < 0.1f) {
                    pathIndex++; // Proceed to the next step in the path
                }
            } else {
                handleCollision(); // Handle collision and recalculate path
            }
        }
    }


    public void draw(SpriteBatch batch, float x, float y) {
        TextureRegion currentFrame = switch (getCurrentDirection()) {
            case UP -> getWalkUpAnimation().getKeyFrame(getStateTime(), true);
            case DOWN -> getWalkDownAnimation().getKeyFrame(getStateTime(), true);
            case LEFT -> getWalkLeftAnimation().getKeyFrame(getStateTime(), true);
            case RIGHT -> getWalkRightAnimation().getKeyFrame(getStateTime(), true);
            default -> getWalkDownAnimation().getKeyFrame(0.1f);
        };
        batch.draw(currentFrame, x, y);
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getWalkDownAnimation().getKeyFrame(getStateTime(), true);
        batch.draw(currentFrame, getX(), getY());
    }



    @Override
    public void die() {
        setAlive(false); // Mark the enemy as dead
        System.out.println("Enemy died!");

        // Trigger removal logic (handled by GameObjectManager or GameScreen)
        // Play death animation or sound (optional)
    }


    public Rectangle getBoundingRectangle() {
        return getRectangle();
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return null;
    }

    public void updateState(float delta, Node playerPosition, Node[][] grid) {
        // Update target to the player's position
        if (playerPosition != null) {
            target = new int[]{playerPosition.getX(), playerPosition.getY()};
        }

        // Recalculate the path if needed
        if (path == null || pathIndex >= path.size()) {
            path = pathfinding.findPath(new int[]{(int) getX(), (int) getY()}, target, "A*");
            pathIndex = 0;
        }
    }


    public void update(float deltaTime, List<Wall> walls) {
        setStateTime(getStateTime() + deltaTime);

        // Move along the path calculated in updateState
        if (path != null && pathIndex < path.size()) {
            move(target, walls);
        } else {
            setCurrentDirection(Direction.STANDING); // No movement if no valid path
        }
    }



    public Rectangle getRectangle() {
        return super.getRectangle();
    }

    public void handleCollision() {
        setPosition(prevX, prevY);
        setCurrentDirection(Direction.STANDING);

        // Recalculate path to the target
//        path = pathfinding.findPath(new int[]{(int) getX(), (int) getY()}, target, "A*");
//        pathIndex = 0;
    }

    private boolean isCollidingWithObject(float x, float y, List<Wall> walls) {
        Rectangle nextPosition = new Rectangle(x, y, getRectangle().width, getRectangle().height);

        for (Wall wall : walls) {
            if (CollisionDetection.isColliding(
                    nextPosition.x, nextPosition.y, nextPosition.width, nextPosition.height,
                    wall.getWallRectangle().x, wall.getWallRectangle().y, wall.getWallRectangle().width, wall.getWallRectangle().height)) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }


    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }



}