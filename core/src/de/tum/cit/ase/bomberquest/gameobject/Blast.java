package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the explosion (blast) created by a bomb.
 */


public class Blast extends Entity {
    SpriteSheet sheet;
    float  deathTime = 0;
    public Blast(Vector2 pos) {
        super(pos, null);

        sheet = new SpriteSheet(new Texture("explo1.png"),1,10);
        sheet.setPlay(0,9,0.1f,false);
    }


}
    /*private int blastRadius;
    private float blastDuration; // Total duration the blast remains visible
    private float elapsedTime;   // Time elapsed since the blast was created
    private int blastDamage;
    private List<Rectangle> segments;
    private GameMap gameMap;     // Reference to the game map to interact with walls
    private GameObjectManager gameObjectManager;
    private Texture texture;
    private Animation<TextureRegion> explodeAnimation;
    private boolean isActive;

    public Blast(float x, float y, int blastRadius, float blastDuration, int blastDamage, GameMap gameMap, GameObjectManager gameObjectManager) {
        super(x, y, false, false); // Blasts are not walkable and not destructible
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
        this.blastDamage = blastDamage;
        this.elapsedTime = 0f;
        this.segments = new ArrayList<>();
        this.gameMap = gameMap;
        this.gameObjectManager = gameObjectManager;
        this.isActive = true;

        // Load texture and define animation frames
        this.texture = new Texture(Gdx.files.internal("assets/blast.png")); // Replace with the actual path
        TextureRegion[] explodeFrames = {
                new TextureRegion(texture, 0, 0, 16, 16),
                new TextureRegion(texture, 16, 0, 16, 16),
                new TextureRegion(texture, 32, 0, 16, 16),
                new TextureRegion(texture, 48, 0, 16, 16)
        };
        this.explodeAnimation = new Animation<>(0.1f, explodeFrames);

        createSegments();
    }

    private void createSegments() {
        // Center blast
        segments.add(new Rectangle(getX(), getY(), 1, 1));

        // Add directional segments
        createDirectionalSegments(0, 1);  // Up
        createDirectionalSegments(0, -1); // Down
        createDirectionalSegments(-1, 0); // Left
        createDirectionalSegments(1, 0);  // Right
    }

    private void createDirectionalSegments(int dx, int dy) {
        for (int step = 1; step <= blastRadius; step++) {
            float nextX = getX() + step * dx;
            float nextY = getY() + step * dy;

            if (!gameMap.isWithinBounds(nextX, nextY)) break;
            if (gameMap.isIndestructibleWall(nextX, nextY)) break;

            segments.add(new Rectangle(nextX, nextY, 1, 1));

            if (gameMap.isDestructibleWall(nextX, nextY)) {
                gameMap.destroyWall(nextX, nextY);
                gameObjectManager.spawnPowerUpIfAny(nextX, nextY);
                break;
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        elapsedTime += deltaTime;

        // Automatically deactivate after animation ends
        if (elapsedTime >= explodeAnimation.getAnimationDuration()) {
            isActive = false;
        }

        // Implement collision handling here if needed
        checkCollisions();
    }

    private void checkCollisions() {
        // Logic for checking collisions with player or enemies
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = explodeAnimation.getKeyFrame(elapsedTime, false); // Non-looping animation

        // Render each segment with the animation frame
        for (Rectangle segment : segments) {
            batch.draw(currentFrame, segment.x, segment.y);
        }
    }

    @Override
    public Rectangle getBoundingRectangle() {
        // Returns the total bounding rectangle of all segments
        if (segments.isEmpty()) return new Rectangle(getX(), getY(), 1, 1);

        Rectangle combined = segments.get(0);
        for (Rectangle segment : segments) {
            combined = combined.merge(segment);
        }
        return combined;
    }

    @Override
    public void dispose() {
        texture.dispose(); // Dispose of the texture
        super.dispose();
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }

    public float getBlastDuration() {
        return blastDuration;
    }

    public void setBlastDuration(float blastDuration) {
        this.blastDuration = blastDuration;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getBlastDamage() {
        return blastDamage;
    }

    public void setBlastDamage(int blastDamage) {
        this.blastDamage = blastDamage;
    }

    public List<Rectangle> getSegments() {
        return segments;
    }

    public void setSegments(List<Rectangle> segments) {
        this.segments = segments;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameObjectManager getGameObjectManager() {
        return gameObjectManager;
    }

    public void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Animation<TextureRegion> getExplodeAnimation() {
        return explodeAnimation;
    }

    public void setExplodeAnimation(Animation<TextureRegion> explodeAnimation) {
        this.explodeAnimation = explodeAnimation;
    }

    public void setActive(boolean active) {
        isActive = active;
    }*/

