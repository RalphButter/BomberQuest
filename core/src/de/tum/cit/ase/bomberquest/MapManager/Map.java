package de.tum.cit.ase.bomberquest.MapManager;

//Basierend auf unserem Meeting, Samstag 18.01.2025
//Entry ist auch so redundant
//BITTE NICHT DIE REDUNDANTEN KLASSEN LÖSCHEN!!! (ICH BRAUCHE DIE GGF. NOCH)
//TODO Evtl. GameObject Klasse anpassen
//TODO DAVOR!!! SPRITE SHEET ERSTELLEN
//TODO ES müssen evtl. die anderen Klassen, die mit Gamobject zu tun haben auch geändert werden.

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.Characters.Player;
import de.tum.cit.ase.bomberquest.gameobject.Cell;
import de.tum.cit.ase.bomberquest.gameobject.CellType;
import de.tum.cit.ase.bomberquest.gameobject.Exit;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.gameobject.PowerUps.BlastRadiusPowerUp;
import de.tum.cit.ase.bomberquest.gameobject.PowerUps.ConcurrentBombPowerUp;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * The Map class serves as the primary structure for managing the game's spatial environment.
 * It provides functionality to store, access, and manipulate game objects within a
 * two-dimensional grid-based representation of the game world. This class acts as a
 * centralized component for handling game elements, ensuring efficient organization
 * and interaction with various objects.
 *
 * Key Responsibilities:
 * - Storing game objects, which are instances of the GameObject class, within a grid structure.
 * - Providing methods to add, remove, and retrieve objects at specific coordinates.
 * - Managing spatial relationships between objects and handling collision or movement logic.
 * - Interfacing with external classes to load map data from files and initialize the environment.
 * - Supporting dynamic updates to the game world during runtime.
 *
 * Potential Redundancies:
 * The comprehensive functionality provided by the Map class can potentially make
 * the following classes redundant by centralizing their responsibilities:
 *
 * - GameObjectManager: If the Map class directly handles the lifecycle of objects,
 *   such as creation, deletion, and tracking, a separate manager might not be required.
 *
 * - Floor + Wall: If terrain or tile-related logic is fully integrated within the Map class,
 *   a dedicated Floor class may become unnecessary.
 *
 * - MapReader and MapParser: If the Map class incorporates efficient parsing and
 *   loading methods, these classes might be reduced to helper utilities or eliminated entirely.
 *
 * By consolidating these responsibilities, the Map class aims to simplify game development
 * by offering a single point of control for spatial management and object interactions.
 */


public class Map {
    int rows; // Number of rows in the map grid, essential for defining the map's dimensions
    int cols; // Number of columns in the map grid, essential for defining the map's dimensions
    GameObject testGameObject; // A test game object for development and debugging purposes
    SpriteSheet mapSheet; // The sprite sheet used for rendering the map tiles
    int cellSize = 16; // The size of each cell in the grid, used for positioning and scaling

    Vector2 pos; // The position of the map in the game world

// Variables for managing the map's cells, randomization, and player position
    GameObject entryGameObject;
    GameObject exitGameObject;
    GameObject[][] grid;
    List<GameObject> emptyGameObjects;
    Random random;
    int keyCount = 0;
    Vector2 playerPos;



    //Todo Wir brauchen noch eine draw + drawWall function
    //Todo Konstrutur (mache ich (Ralph))
    //TODO Wir müssen davor Enemy, etc. machen. Diese Klasse kann erst zum Schluss implementiert werden

    //TODO UNSER SCHWIERIGSTER TEIL ;)
    public Map(String path,List<Entity> entities) {
        mapSheet = new SpriteSheet(new Texture("basictiles.png"),15,8);
        //TODO Wir Brauchen Zelle
        Properties prop = new Properties();
        cols = 0;
        rows = 0;
        random = new Random();

        try {
            // Load a properties file from the specified path
            prop.load(new FileInputStream(path));

            // Iterate through each entry in the properties file
            for (java.util.Map.Entry<Object, Object> entry : prop.entrySet()) {
                String key = (String) entry.getKey();
                try {
                    // Attempt to parse the first part of the key as an integer to skip any malformed entries
                    Integer.parseInt(key.split(",")[0]);
                } catch (Exception e) {
                    // If parsing fails, skip this entry
                    continue;
                }
                // Extract the column and row values from the key
                int col = Integer.parseInt(key.split(",")[0]);
                int row = Integer.parseInt(key.split(",")[1]);

                // Update the maximum column value if the current column is greater
                if (col > cols) {
                    cols = col;
                }
                // Update the maximum row value if the current row is greater
                if (row > rows) {
                    rows = row;
                }
            }

            cols += 1; // Adjust for zero-based indexing to include all columns
            rows += 1; // Adjust for zero-based indexing to include all rows
            // Initialize the grid with the specified number of rows
            grid = new GameObject[rows][];
            for(int row = 0; row < rows;row++){
                // Initialize each row with the specified number of columns
                grid[row] = new GameObject[cols];
                for (int col = 0; col < cols;col++){
                    // Create a new Cell object for each position in the grid
                    //TODO We need to fix GameObject
                    grid[row][col] = new GameObject(row,col,null);
                }
            }


            boolean hasExit = false;

            boolean c = false;
            List<GameObject> destructibleWalls = new ArrayList<>(); // To store destructible walls
            for (java.util.Map.Entry<Object, Object> entry : prop.entrySet()) {
                String key = (String) entry.getKey();
                int value = Integer.valueOf((String) entry.getValue());
                try {
                    // Skip error format
                    Integer.parseInt(key.split(",")[0]);
                } catch (Exception e) {
                    continue;
                }
                //This code snippet extracts the column and row values from a string key
                int col = Integer.parseInt(key.split(",")[0]);
                int row = Integer.parseInt(key.split(",")[1]);

                GameObject gameObject = grid[row][col];
                gameObject.cellType = CellType.fromId(value);
                switch (gameObject.cellType) {
                    case DESTRUCTIBLE_WALL:
                        //TODO I am sure this is correct. We need to fix GameObject
                        destructibleWalls.add(gameObject); // Add to destructible walls list
                        break;
                    case ENTRANCE:
                        entryGameObject = gameObject; // Set the entry game object
                        break;
                    case EXIT:
                        System.out.println("has exit 1");
                        exitGameObject = gameObject; // Set the exit game object
                        hasExit = true; // Mark that an exit has been found
                        gameObject.cellType = CellType.DESTRUCTIBLE_WALL; // Change cell type to destructible wall
                        grid[gameObject.row][gameObject.col].cellType = CellType.DESTRUCTIBLE_WALL; // Update grid cell type
                        //TODO we need to fix Exit
                        entities.add(new Exit(new Vector2(gameObject.col * 16, gameObject.row * 16))); // Add exit entity
                        break;
                    case ENEMY:
                        // Handle enemy logic if needed
                        break;
                }
            }

            // If no exit is found, randomly assign one from destructible walls
            if (!hasExit && !destructibleWalls.isEmpty()) {
                Random random = new Random();
                // TODO select a GameObject from the list of destructible walls. This is done using the Random class to generate a random index within the bounds of the destructibleWalls list. The selected GameObject is then assigned to the variable randomGameObject.
                GameObject randomGameObject = destructibleWalls.get(random.nextInt(destructibleWalls.size()));
                hasExit = true;
                entities.add(new Exit(new Vector2(randomGameObject.col * 16, randomGameObject.row * 16)));
            }

            // Populate the list of empty game objects
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (grid[row][col].cellType == null || grid[row][col].cellType == CellType.ENEMY) {
                        emptyGameObjects.add(grid[row][col]);
                    }
                }
            }
                
                
                //ADD ITEMS
                // Add items to the grid based on the properties file
                for (java.util.Map.Entry<Object, Object> entry : prop.entrySet()) {
                    String key = (String) entry.getKey();
                    int value = Integer.valueOf((String) entry.getValue());
                    try {
                        // Skip error format
                        Integer.parseInt(key.split(",")[0]);
                    } catch (Exception e) {
                        continue;
                    }
                    int col = Integer.parseInt(key.split(",")[0]);
                    int row = Integer.parseInt(key.split(",")[1]);

                    GameObject gameObject = grid[row][col];
                    gameObject.cellType = CellType.fromId(value);
                    switch (gameObject.cellType) {
                        case ENEMY:
                            // Randomly decide whether to add a  Enemy
                            if (random.nextInt(10) <= 3) {
                                // Uncomment and add Enemy if needed
                                // gameObjects.add(new Enemy(new Vector2(cell.col * 16, cell.row * 16), this));
                            } else {
                                entities.add(new Enemy(new Vector2(gameObject.col * 16, gameObject.row * 16), this));
                            }
                            break;
                        case EXIT:
                            // Change cell type to destructible wall for exit cells
                            grid[gameObject.row][gameObject.col].cellType = CellType.DESTRUCTIBLE_WALL;
                            break;
                        case CONCURRENT_PU:
                            // Add a BlastRadiusPowerUp entity and change cell type to destructible wall
                            //TODO FIX CLASS
                            entities.add(new BlastRadiusPowerUp(new Vector2(gameObject.col * 16, gameObject.row * 16)));
                            grid[gameObject.row][gameObject.col].cellType = CellType.DESTRUCTIBLE_WALL;
                            break;
                        case BLAST_RADIUS_PU:
                            // Add a ConcurrentBombPowerUp entity and change cell type to destructible wall
                            //TODO FIX THAT CLASS
                            entities.add(new ConcurrentBombPowerUp(new Vector2(gameObject.col * 16, gameObject.row * 16)));
                            grid[gameObject.row][gameObject.col].cellType = CellType.DESTRUCTIBLE_WALL;
                            break;
                    }
                }


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        pos = new Vector2( 0,0);

    }

    public void chasePlayer(Vector2 playerPos){

    }

    private void drawCell(Batch batch, GameObject gameObject, TextureRegion textureRegion){
        batch.draw(textureRegion, pos.x + gameObject.col * cellSize,pos.y + gameObject.row * cellSize,mapSheet.getWidth()/2,mapSheet.getHeight()/2,mapSheet.getWidth(),mapSheet.getHeight(),1,1,0);
    }

    public GameObject getCell(int row, int col){
        return grid[row][col];
    }


    public GameObject getRandomEmptyCell(){
        int randNum = random.nextInt(emptyGameObjects.size());
        return emptyGameObjects.get(randNum);
    }

    public int getKeyCount(){
        return keyCount;
    }

    public void draw(Batch batch, Player player){
        batch.begin();

        for(int row = 0; row < rows;row++){
            for(int col = 0; col < cols;col++){
                GameObject gameObject = grid[row][col];
                if(Vector2.dst(player.pos.x,player.pos.y, gameObject.col*16, gameObject.row*16) > 1500){
                    continue;
                }

                if(gameObject.cellType == null){
                    drawCell(batch, gameObject,mapSheet.getTexture(64));
                }
                else {
                    switch (gameObject.cellType){
                        case INDESTRUCTIBLE_WALL:
                            drawCell(batch, gameObject,mapSheet.getTexture(0));
                            break;
                        case DESTRUCTIBLE_WALL:
                            drawCell(batch, gameObject,mapSheet.getTexture(15));
                            break;
                        case ENTRANCE:
                            drawCell(batch, gameObject,mapSheet.getTexture(11));
                            break;
                        case EXIT:
                            drawCell(batch, gameObject,mapSheet.getTexture(35));
                            break;
                        case ENEMY:
                        case KEY:
                            drawCell(batch, gameObject,mapSheet.getTexture(11));
                            break;
                    }
                }

            }
        }

        batch.end();
    }

    public void drawWall(Batch batch,Player player){
        batch.begin();

        for(int row = 0; row < rows;row++){
            for(int col = 0; col < cols;col++){
                GameObject gameObject = grid[row][col];
                if(Vector2.dst(player.pos.x,player.pos.y, gameObject.col*16, gameObject.row*16) > 1500){
                    continue;
                }

                if(gameObject.cellType == null){

                }
                else {
                    switch (gameObject.cellType){
                        case DESTRUCTIBLE_WALL:
                            drawCell(batch, gameObject,mapSheet.getTexture(15));
                            break;
                    }
                }

            }
        }

        batch.end();
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
