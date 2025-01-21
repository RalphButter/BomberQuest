package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import de.tum.cit.ase.bomberquest.gameobject.Bombs;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.gameobject.CellType;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.screen.GameScreen;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.util.Manager;
import de.tum.cit.ase.bomberquest.util.Rectangle;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.util.List;

/**
 * Represents the player character in the game. Handles movement, animations,
 * interaction with the game world, and shooting mechanics.
 */
public class Player extends Entity {

    boolean onGround = false; // Indicates if the player is on the ground
    float speed; // Movement speed of the player
    boolean dead = false; // Tracks if the player is dead
    boolean jumping = false; // Indicates if the player is jumping

    // Sprite sheets for different movement directions
    SpriteSheet walkSheet_down;
    SpriteSheet walkSheet_up;
    SpriteSheet walkSheet_left;
    SpriteSheet walkSheet_right;
    SpriteSheet sheet; // Currently active sprite sheet

    Vector2 dir; // Movement direction
    Vector2 facingDir; // Direction the player is facing
    int bombCount = 0; // Number of bombs the player can place
    public boolean shooting = false; // Indicates if the player is shooting
    float shootTime = 0; // Timer for shooting actions

    private GameScreen gameScreen; // Reference to the game screen

    /**
     * Initializes the player with a starting position and animation setup.
     *
     * @param pos Initial position of the player.
     */
    public Player(Vector2 pos) {
        super(pos, null);

        // Load animations for walking in different directions
        walkSheet_down = new SpriteSheet(new Texture("player_walk.png"), 4, 4);
        walkSheet_down.setPlay(0, 3, 0.1f, true);

        walkSheet_right = new SpriteSheet(new Texture("player_walk.png"), 4, 4);
        walkSheet_right.setPlay(4, 7, 0.1f, true);

        walkSheet_up = new SpriteSheet(new Texture("player_walk.png"), 4, 4);
        walkSheet_up.setPlay(8, 11, 0.1f, true);

        walkSheet_left = new SpriteSheet(new Texture("player_walk.png"), 4, 4);
        walkSheet_left.setPlay(12, 15, 0.1f, true);

        sheet = walkSheet_right;
        speed = 1;

        dir = new Vector2(0, 0);
        facingDir = new Vector2(1, 0);
    }

    /**
     * Creates a Box2D body to handle physics-based movement and collisions.
     *
     * @param world  The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @return The created Box2D body.
     */
    private Body createHitbox(World world, float startX, float startY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startX, startY);
        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(0.3f);
        body.createFixture(circle, 1.0f);
        circle.dispose();
        body.setUserData(this);
        return body;
    }

    /**
     * Handles key release events to stop movement.
     *
     * @param keycode The released key.
     */
    public void onKeyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                if (dir.x == -1) dir.x = 0;
                break;
            case Input.Keys.D:
                if (dir.x == 1) dir.x = 0;
                break;
            case Input.Keys.W:
                if (dir.y == 1) dir.y = 0;
                break;
            case Input.Keys.S:
                if (dir.y == -1) dir.y = 0;
                break;
        }
    }

    /**
     * Handles key press events to initiate movement and shooting.
     *
     * @param keycode The pressed key.
     */
    public void onKeyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                dir = new Vector2(-1, 0);
                sheet = walkSheet_left;
                facingDir = new Vector2(dir.x, dir.y);
                break;
            case Input.Keys.D:
                dir = new Vector2(1, 0);
                sheet = walkSheet_right;
                facingDir = new Vector2(dir.x, dir.y);
                break;
            case Input.Keys.W:
                dir = new Vector2(0, 1);
                sheet = walkSheet_up;
                facingDir = new Vector2(dir.x, dir.y);
                break;
            case Input.Keys.S:
                dir = new Vector2(0, -1);
                sheet = walkSheet_down;
                facingDir = new Vector2(dir.x, dir.y);
                break;
            case Input.Keys.SPACE:
                shootTime = 0;
                shooting = true;
                break;
        }
    }

    /**
     * Updates the player's position and handles collision detection.
     *
     * @param map The game map used for collision checking.
     */
    public void update(Map map) {
        Vector2 vel = new Vector2(dir.x * speed, dir.y * speed);
        Vector2 prevPos = new Vector2(pos.x, pos.y);

        pos = pos.add(vel);
        if (vel.x != 0 || vel.y != 0) {
            sheet.play();
        }

        // Check for collisions with walls
        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                GameObject gameObject = map.getCell(row, col);
                if ((gameObject.cellType == CellType.INDESTRUCTIBLE_WALL ||
                        gameObject.cellType == CellType.DESTRUCTIBLE_WALL) &&
                        gameObject.getRect().collide(this.getRect())) {
                    pos = prevPos;
                    return;
                }
            }
        }
    }


    /**
     * Shoots a bomb if the player has one available.
     *
     * @param objects List of active game objects.
     */
    public void shoot(List<Entity> objects) {
        if (shooting) {
            bombCount--;
            Vector2 pos = new Vector2((int) getCenter().x / 16 * 16, (int) getCenter().y / 16 * 16);
            objects.add(new Bombs(pos));
            shooting = false;
            Manager.getInstance().soundsManager.play("shoot", 1.0f);
        }

    }

    public Vector2 getCenter(){
        return new Vector2(getRect().x + getRect().w/2,getRect().y + getRect().h/2);
    }

}
