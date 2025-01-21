package de.tum.cit.ase.bomberquest.MapManager;

// Based on our meeting on Saturday, 18.01.2025
// Entry class is also redundant
// DO NOT DELETE REDUNDANT CLASSES!!! (I MAY NEED THEM LATER)
// TODO: Possibly adjust the GameObject class
// TODO: BEFORE THAT!!! CREATE SPRITE SHEET
// TODO: Other classes interacting with GameObject might need changes as well

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.Characters.Player;
import de.tum.cit.ase.bomberquest.gameobject.*;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;
import java.io.FileInputStream;
import java.util.*;

/**
 * The {@code Map} class represents the game world as a grid-based structure.
 * It is responsible for managing the placement, retrieval, and manipulation of game objects
 * within the environment.
 *
 * <p>Key Responsibilities:
 * - Storing and managing game objects in a 2D grid.
 * - Loading map data from external files.
 * - Handling spatial relationships and interactions between objects.
 * - Providing utility methods for retrieving and modifying game objects.
 */
public class Map {
    int rows; // Number of rows in the map grid
    int cols; // Number of columns in the map grid
    GameObject testGameObject; // Test object for debugging
    SpriteSheet mapSheet; // Sprite sheet used for rendering the map tiles
    int cellSize = 16; // Size of each cell in the grid (in pixels)

    Vector2 pos; // Position of the map in the game world

    // GameObject variables for entry, exit, grid cells, and randomization
    GameObject entryGameObject;
    GameObject exitGameObject;
    GameObject[][] grid;
    List<GameObject> emptyGameObjects;
    Random random;
    int keyCount = 0;
    Vector2 playerPos;

    /**
     * Constructs a new Map instance by loading the level data from a file.
     *
     * @param path The file path to the map properties file.
     * @param entities The list of entities (players, enemies) to be initialized.
     */
    public Map(String path, List<Entity> entities) {
        mapSheet = new SpriteSheet(new Texture("basictiles.png"), 15, 8);
        Properties prop = new Properties();
        random = new Random();

        try {
            // Load the properties file containing the map data
            prop.load(new FileInputStream(path));

            // Determine grid dimensions by iterating through the properties
            for (java.util.Map.Entry<Object, Object> entry : prop.entrySet()) {
                String key = (String) entry.getKey();
                try {
                    Integer.parseInt(key.split(",")[0]);
                } catch (Exception e) {
                    continue;
                }
                int col = Integer.parseInt(key.split(",")[0]);
                int row = Integer.parseInt(key.split(",")[1]);

                cols = Math.max(cols, col);
                rows = Math.max(rows, row);
            }

            cols += 1; // Adjust for zero-based indexing
            rows += 1;

            // Initialize grid
            grid = new GameObject[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    grid[row][col] = new GameObject(row, col, null);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        pos = new Vector2(0, 0);
    }

    /**
     * Retrieves a specific cell in the grid.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The {@code GameObject} located at the specified position.
     */
    public GameObject getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Returns a random empty cell from the grid.
     *
     * @return A randomly selected empty {@code GameObject}.
     */
    public GameObject getRandomEmptyCell() {
        return emptyGameObjects.get(random.nextInt(emptyGameObjects.size()));
    }

    /**
     * Returns the number of collected keys in the map.
     *
     * @return The key count.
     */
    public int getKeyCount() {
        return keyCount;
    }

    /**
     * Draws the map and its objects onto the screen.
     *
     * @param batch The {@code Batch} used for rendering.
     * @param player The player entity.
     */
    public void draw(Batch batch, Player player) {
        batch.begin();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                GameObject gameObject = grid[row][col];
                if (Vector2.dst(player.pos.x, player.pos.y, gameObject.col * 16, gameObject.row * 16) > 1500) {
                    continue;
                }

                TextureRegion texture = mapSheet.getTexture(64);
                if (gameObject.cellType != null) {
                    switch (gameObject.cellType) {
                        case INDESTRUCTIBLE_WALL:
                            texture = mapSheet.getTexture(0);
                            break;
                        case DESTRUCTIBLE_WALL:
                            texture = mapSheet.getTexture(15);
                            break;
                        case ENTRANCE:
                            texture = mapSheet.getTexture(11);
                            break;
                        case EXIT:
                            texture = mapSheet.getTexture(35);
                            break;
                    }
                }
                drawCell(batch, gameObject, texture);
            }
        }
        batch.end();
    }

    /**
     * Draws a specific cell on the screen.
     *
     * @param batch The rendering batch.
     * @param gameObject The game object to be drawn.
     * @param textureRegion The texture to be used for rendering.
     */
    private void drawCell(Batch batch, GameObject gameObject, TextureRegion textureRegion) {
        batch.draw(textureRegion, pos.x + gameObject.col * cellSize, pos.y + gameObject.row * cellSize,
                mapSheet.getWidth() / 2, mapSheet.getHeight() / 2,
                mapSheet.getWidth(), mapSheet.getHeight(), 1, 1, 0);
    }

    public GameObject getEntryCell(){
        return entryGameObject;
    }
    public GameObject getExitCell(){
        return exitGameObject;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}

