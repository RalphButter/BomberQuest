package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.MapManager.MapReader;
import de.tum.cit.ase.bomberquest.gameobject.*;
import de.tum.cit.ase.bomberquest.gameobject.PowerUps.BlastRadiusPowerUp;
import de.tum.cit.ase.bomberquest.gameobject.PowerUps.ConcurrentBombPowerUp;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.screen.Hud;


import java.awt.*;
import java.util.ArrayList;
import de.tum.cit.ase.bomberquest.MapManager.MapParser;
import java.util.List;
import de.tum.cit.ase.bomberquest.MapManager.Node;
import de.tum.cit.ase.bomberquest.map.Flowers;
import de.tum.cit.ase.bomberquest.map.Chest;
import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.Characters.Player;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

    public static final int TILE_SIZE_PX = 16;
    public static final int SCALE = 4;

    private final BomberQuestGame game;
    private final SpriteBatch spriteBatch;
    private final GameMap map;
    private final Player player;
    private final Hud hud;
    private final OrthographicCamera mapCamera;
    private final Viewport viewport;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Music stageMusic;
    private final Music actionMusic;
    private final Sound bombSound;
    private final Sound powerUpSound;
    private final Sound enemySound;
    private final Sound bombPlaceSound;

    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();
    private final List<Entry> entries = new ArrayList<>();
    private final List<Exit> exits = new ArrayList<>();
    private final List<Flowers> flowers = new ArrayList<>();
    private final List<Floor> floors = new ArrayList<>();
    private final List<Chest> chests = new ArrayList<>();
    private Node[][] grid;
    private final List<Bombs> bombs = new ArrayList<>();
    private final List <Blast> blasts = new ArrayList<>();
    private boolean paused;

    private final GameObjectManager gameObjectManager;
    private final GameMap gameMap;
    private float radius;

    private int remainingEnemies = 3;

    private final World world;


    public GameScreen(BomberQuestGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"));

        hud.stage.draw(); // Render the HUD

        this.world = new World(new Vector2(0, 0), true);
        MapParser.resetMap();
        List<String> mapLines = MapReader.readMapFile("path/to/map.properties");
        MapParser.initializeMap(mapLines); // Centralized parsing and validation
        this.map = new GameMap(game);

        this.player = new Player(this.world, MapParser.getEntry().x, MapParser.getEntry().y);

        this.mapCamera = new OrthographicCamera();
        this.viewport = new ExtendViewport(800, 600, mapCamera);
        this.mapRenderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("maps/map-1.tmx"), SCALE);

        this.stageMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/stageMusic.mp3"));
        stageMusic.setVolume(0.2f);
        stageMusic.setLooping(true);
        this.stageMusic.play();

        this.actionMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/actionMusic.mp3"));
        actionMusic.setVolume(0.2f);
        actionMusic.setLooping(true);
        this.actionMusic.play();

        this.bombSound = Gdx.audio.newSound(Gdx.files.internal("audio/bombSound.mp3"));
        this.powerUpSound = Gdx.audio.newSound(Gdx.files.internal("audio/powerUpSound.mp3"));
        this.enemySound = Gdx.audio.newSound(Gdx.files.internal("audio/enemySound.mp3"));
        bombPlaceSound = Gdx.audio.newSound(Gdx.files.internal("audio/bombPlace.mp3"));

        // Initialize missing fields
        this.radius = 1.0f; // Example value, adjust as needed
        this.gameObjectManager = new GameObjectManager();
        this.gameMap = new GameMap(game);

        float coolDownRemaining = Math.max(0, player.getCoolDownTime() - player.getTimeSinceLastBomb());
        hud.updateCoolDown(coolDownRemaining);



    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        Node playerPosition = new Node((int) (map.getPlayer().getX() / TILE_SIZE_PX), (int) (map.getPlayer().getY() / TILE_SIZE_PX), true);

        if (!paused) {

            checkPlayerDeath();

            // Player-Wall collision detection
            for (Wall wall : walls) {
                if (wall.getWallRectangle().overlaps(player.getRectangle())) {
                    if (wall.isDestructible()) {
                        handleDestructibleWall(wall);
                    } else {
                        player.setPosition(player.getPrevX(), player.getPrevY());
                    }
                }
            }

            for (Blast blast : blasts) {
                for (Wall wall : walls) {
                    if (wall instanceof DestructibleWall && blast.getBoundingRectangle().overlaps(wall.getWallRectangle())) {
                        handleDestructibleWall(wall);
                    }
                }
            }


            // Enemy Update Method
            // Update enemies
            for (Enemy enemy : enemies) {
                Node playerNode = grid[(int) (player.getX() / GameScreen.TILE_SIZE_PX)][(int) (player.getY() / GameScreen.TILE_SIZE_PX)];
                enemy.updateState(delta, playerNode, grid); // Handle AI and pathfinding
                enemy.update(delta, walls); // Handle movement and animation
            }

            for (Enemy enemy : enemies) {
                for (Blast blast : blasts) {
                    if (blast.getBoundingRectangle().overlaps(enemy.getBoundingRectangle())) {
                        enemy.die();
                        enemies.remove(enemy); // Remove dead enemy from the list
                        break; // Stop further checks for this enemy
                    }
                }
            }


            // Enemy-Wall collision detection
            for (Wall wall : walls) {
                for (Enemy enemy : enemies) {
                    if (enemy.getRectangle().overlaps(wall.getWallRectangle())) {
                        enemy.handleCollision();
                    }
                }
            }

            // Differentiating between destructible and indestructible walls

            // Enemy-Entry collision detection
            for (Entry entry : entries) {
                for (Enemy enemy : enemies) {
                    if (enemy.getRectangle().overlaps(entry.getRectangle())) {
                        enemy.handleCollision();
                    }
                }
            }

            // Enemy-Exit collision detection
            for (Exit exit : exits) {
                for (Enemy enemy : enemies) {
                    if (enemy.getRectangle().overlaps(exit.getRectangle())) {
                        enemy.handleCollision();
                    }
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                // Call the placeBomb method on the player
                player.placeBomb(map, gameObjectManager, bombPlaceSound, delta);
            }


            // Check for escape key press to go back to the menu
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                pause();
            } else if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)
                    && map.getPlayer().getY() < (MapParser.getHeight() - 1) * TILE_SIZE_PX) { // limits the borders of the game
                player.moveUp(walls);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)
                    && map.getPlayer().getY() > 0) {
                player.moveDown(walls);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    && map.getPlayer().getX() > 0) {
                player.moveLeft(walls);
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    && map.getPlayer().getX() < (MapParser.getWidth() - 1) * TILE_SIZE_PX) {
                player.moveRight(walls);
            }

            // Update player rectangle after movement for collision detection
            Rectangle updatedRectangle = new Rectangle((int) map.getPlayer().getX() + 2, (int) map.getPlayer().getY() + 2, 12, 12);
            map.getPlayer().setRectangle(updatedRectangle);

            // Update the camera, centered in the middle
            updateCamera();
            mapCamera.update();
            spriteBatch.setProjectionMatrix(mapCamera.combined);

            // Render the tile map
            mapRenderer.setView(mapCamera);
            mapRenderer.render();

            player.update(delta, walls);

            // Supports the player animation and movement
            if ((!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) &&
                    (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) &&
                    (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) &&
                    (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
                player.setIsMoving(false);
            }

            // Call draw methods
            spriteBatch.begin();
            draw(spriteBatch, map.getPlayer());
            for (Enemy enemy : enemies) {
                draw(spriteBatch, enemy);
            }
            spriteBatch.end();

            // HUD: Set batch to now draw what the Hud camera sees.
            spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.update(delta, player, remainingEnemies, blasts.isEmpty() ? null : blasts.get(0));
            hud.stage.draw();
        }
    }

    private void updateCamera() {
        mapCamera.position.set(map.getPlayer().getX() * TILE_SIZE_PX * SCALE, map.getPlayer().getY() * TILE_SIZE_PX * SCALE, 0);
        mapCamera.update();
    }

    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hud.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void show() {
        paused = false;
    }

    @Override
    public void hide() {
        paused = true;
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        stageMusic.dispose();
        actionMusic.dispose();
        bombSound.dispose();
        powerUpSound.dispose();
        enemySound.dispose();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // Add these methods to the GameScreen class

    private void handleDestructibleWall(Wall wall) {
        if (wall instanceof DestructibleWall) {
            // Calculate the wall's position in the map grid
            Point wallPosition = new Point((int) wall.getX() / TILE_SIZE_PX, (int) wall.getY() / TILE_SIZE_PX);

            // Get the object ID beneath the wall
            Integer objectId = MapParser.getObjects().get(wallPosition);

            // Handle objects revealed under the wall
            if (objectId != null) {
                switch (objectId) {
                    case 4: // Exit
                        gameObjectManager.addGameObject(new Exit(wall.getX(), wall.getY(), true, false));
                        System.out.println("Exit revealed at: " + wallPosition);
                        break;
                    case 5: // Concurrent Bomb Power-Up
                        gameObjectManager.addGameObject(new ConcurrentBombPowerUp(wall.getX(), wall.getY(), map, gameObjectManager));
                        System.out.println("Concurrent Bomb Power-Up revealed at: " + wallPosition);
                        break;
                    case 6: // Blast Radius Power-Up
                        gameObjectManager.addGameObject(new BlastRadiusPowerUp(wall.getX(), wall.getY(), 1, 0.5f, 1, map, gameObjectManager));
                        System.out.println("Blast Radius Power-Up revealed at: " + wallPosition);
                        break;
                    default:
                        System.out.println("Unknown object beneath the wall at: " + wallPosition);
                        break;
                }
            }

            // Remove the destructible wall from the game
            walls.remove(wall);
            MapParser.getObjects().remove(wallPosition); // Remove it from the map state as well

            System.out.println("Destructible wall removed at: " + wallPosition);
        } else {
            System.err.println("Error: Non-destructible wall passed to handleDestructibleWall.");
        }
    }


    private void handleIndestructibleWall(Wall wall) {
        // Logic for handling indestructible walls
        // For example, you can simply ignore them or add some specific behavior


    }

    private boolean isBombExplodedNear(Wall wall) {
        for (Blast blast : blasts) {
            if (blast.getBoundingRectangle().overlaps(wall.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    private void destroyWall(Wall wall) {
        // Logic to destroy the wall
        // For example, remove the wall from the list of walls and update the game state
        walls.remove(wall);
        // Additional logic to update the game state
    }

    private void explode(float x, float y) {
        Blast blast = new Blast(x, y, (int) radius, 0.5f, 1, gameMap, gameObjectManager);
        gameObjectManager.addGameObject(blast);
        blasts.add(blast);
    }

    private void gameOver() {
        // Logic to handle game over, e.g., show game over screen, stop music, etc.
        stageMusic.stop();
        actionMusic.stop();
        // Transition to game over screen or main menu
        game.setScreen(new GameOverScreen(game));
    }

    public List<Wall> getWalls() {
        return walls;
    }

    private void checkPlayerDeath() {
        // Check for player collision with enemies
        for (Enemy enemy : enemies) {
            if (enemy.getBoundingRectangle().overlaps(player.getRectangle())) {
                player.setAlive(false);
                System.out.println("Player killed by enemy!");
                gameOver();
                return; // Stop further updates
            }
        }

        // Check for player collision with blasts
        for (Blast blast : blasts) {
            if (blast.getBoundingRectangle().overlaps(player.getRectangle())) {
                player.setAlive(false);
                System.out.println("Player hit by explosion!");
                gameOver();
                return; // Stop further updates
            }
        }
    }

}