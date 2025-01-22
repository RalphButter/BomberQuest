package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import de.tum.cit.ase.bomberquest.gameobject.*;
import de.tum.cit.ase.bomberquest.gameobject.MaxBombPU;
import de.tum.cit.ase.bomberquest.gameobject.MaxRadiusPU;


import java.util.ArrayList;

import java.util.List;

import de.tum.cit.ase.bomberquest.Characters.Enemy;
import de.tum.cit.ase.bomberquest.Characters.Player;
import de.tum.cit.ase.bomberquest.util.FollowCamera;

//I have to import this for the heart image
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.tum.cit.ase.bomberquest.util.Manager;

//ICH WEIß NICHT; WARUM ES NICHT PUSHED
public class GameScreen implements Screen {
    //Core components
    // Reference to the main game object
    private final BomberQuestGame game;
    // Camera used to control the view in the game
    private final OrthographicCamera camera;
    // Font used for rendering text in the game
    private final BitmapFont font;
    // Stage used for managing and rendering UI elements
    private final Stage stage;


    // Viewport used to handle the camera's view and manage the rendering area
    private Viewport viewport;


    // List to store all game entities
    List<Entity> objects;
    // Batch used for drawing 2D graphics
    Batch batch;
    // Map representing the game world
    Map map;
    // Player character in the game
    Player player;
    // Camera that follows the player
    FollowCamera followCamera;


    //Player stats
    int score ;
    float time ;
    int heart;
    int keyCount; // TODO Needs to be set to 1 after all enemies are dead


    // Label to display the player's current score
    Label scoreLabel;
    // Label to display the number of hearts (lives) the player has left
    Label heartLabel;
    // Label to display the remaining time in the game
    Label timeLabel;
    // Label to display the current bomb explosion radius
    Label radiusLabel;
    // Label to display the maximum number of bombs the player can place
    Label maxBombLabel;
    // Label to display the number of enemies remaining in the game
    Label enemyCountLabel;
    // Label to indicate whether the exit is locked or unlocked
    Label exitUnlockLabel;





    //For the Pause Menu
    Table pauseMenu;
    boolean gamePause = false;

    //Players initial values
    int explodeRadius = 1;
    int maxBomb = 1;
    boolean canExit = false;


    public GameScreen(BomberQuestGame game, String mapPath) {
        this.game = game;
        objects = new ArrayList();

        // Create a new OrthographicCamera with the screen's width and height
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // ExtendViewportPermalink
        viewport = new ScreenViewport(camera);
        // This line creates a new `Stage` object, which is used to manage and render UI elements in the game.
        stage = new Stage();

        // Retrieve the font from the game's skin
        font = game.getSkin().getFont("font");

        // ExtendViewportPermalink
        viewport = new ScreenViewport(camera);

        batch = game.getSpriteBatch(); // Get the SpriteBatch from the game instance for rendering
        map = new Map(mapPath, objects); // Create a new Map object with the specified map path and list of game entities
        player = new Player(new Vector2(map.getEntryCell().col * 16, map.getEntryCell().row * 16)); // Create a new Player object at the map's entry cell position
        //Initialize input processing for the game method
        initInput();
        followCamera = new FollowCamera(camera, map); // Create a FollowCamera to follow the player within the map

        spawnPU();

        score  = 0;
        time = 300;
        heart = 3;
        keyCount = 0;
        createUI();

    }

    /**
     * Creates and sets up the user interface (UI) elements for the game screen.
     * This includes labels for displaying game statistics such as score, time, hearts, bomb radius, max bombs, and enemy count.
     * It also sets up the pause menu with options to load a game or exit to the menu.
     */
    void createUI(){
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        table.center().bottom();


        // Create a table to hold the heart (lives) information
        Table heartTable = new Table();
        table.add(heartTable).minHeight(10); // Add the heart table to the main table with a minimum height
        // Set the background of the heart table to a stone texture
        heartTable.setBackground(new TextureRegionDrawable(new Texture("stone.png")));
        // Create an image for the heart icon
        Image hearthImg = new Image(new Texture("heart.png"));
        // Create a label to display the number of hearts (lives) the player has
        heartLabel = new Label("1", game.getSkin());
        heartLabel.setColor(Color.RED); // Set the color of the heart label to red
        // Add the heart label and heart image to the heart table with padding
        heartTable.add(heartLabel).pad(10);
        heartTable.add(hearthImg);



        // time
        Table timeTable = new Table();
        table.add(timeTable).minHeight(10);
        timeTable.setBackground(new TextureRegionDrawable(new Texture("stone.png")));
        Label label3 = new Label("Time:",game.getSkin());
        timeLabel = new Label("120s",game.getSkin());
        label3.setColor(Color.WHITE);
        label3.setFontScale(0.6f);
        timeLabel.setFontScale(0.6f);
        timeLabel.setColor(Color.WHITE);
        timeTable.add(label3).pad(10);
        timeTable.add(timeLabel);



        // score
        Table scoreTable = new Table();
        table.add(scoreTable).minHeight(10);
        scoreTable.setBackground(new TextureRegionDrawable(new Texture("stone.png")));

        Label label2 = new Label("Score: ", game.getSkin());
        label2.setColor(Color.WHITE); // Set the color of the score text label to white
        label2.setFontScale(0.6f); // Set the font scale of the score text label
        // Create a label to display the player's current score
        scoreLabel = new Label("3400", game.getSkin());
        scoreLabel.setFontScale(0.6f); // Set the font scale of the score label
        scoreLabel.setColor(Color.MAGENTA); // Set the color of the score label to magenta
        // Add the score text label and score label to the score table with padding
        scoreTable.add(label2).pad(10);
        scoreTable.add(scoreLabel);

        // Create a table to hold the key information (bomb radius, max bombs, enemy count, and exit status)
        Table keyTable = new Table();
        table.add(keyTable).minHeight(10); // Add the key table to the main table with a minimum height
        // Set the background of the key table to a stone texture
        keyTable.setBackground(new TextureRegionDrawable(new Texture("stone.png")));

        // Create a label to display the current bomb explosion radius
        radiusLabel = new Label("bomb radius: " + explodeRadius, game.getSkin());
        radiusLabel.setFontScale(0.6f); // Set the font scale of the radius label
        radiusLabel.setColor(Color.WHITE); // Set the color of the radius label to white
        keyTable.add(radiusLabel).pad(10); // Add the radius label to the key table with padding

        // Create a label to display the maximum number of bombs the player can place
        maxBombLabel = new Label("max bomb: " + explodeRadius, game.getSkin());
        maxBombLabel.setFontScale(0.6f); // Set the font scale of the max bomb label
        maxBombLabel.setColor(Color.WHITE); // Set the color of the max bomb label to white
        keyTable.add(maxBombLabel).pad(10); // Add the max bomb label to the key table with padding

        // Create a label to display the number of enemies remaining in the game
        enemyCountLabel = new Label("enemies: " + countEnemy(), game.getSkin());
        enemyCountLabel.setFontScale(0.6f); // Set the font scale of the enemy count label
        enemyCountLabel.setColor(Color.WHITE); // Set the color of the enemy count label to white
        keyTable.add(enemyCountLabel).pad(10); // Add the enemy count label to the key table with padding

        // Create a label to indicate whether the exit is locked or unlocked
        exitUnlockLabel = new Label("exit: locked!", game.getSkin());
        exitUnlockLabel.setFontScale(0.6f); // Set the font scale of the exit unlock label
        exitUnlockLabel.setColor(Color.RED); // Set the color of the exit unlock label to red
        keyTable.add(exitUnlockLabel).pad(10); // Add the exit unlock label to the key table with padding


        //ADDED FUNCTION SO WHEN YOU CLICK ESC THE GAME PAUSES AND YOU CAN SELECT THE MENU OR Another Level
        // Create a table for the pause menu
        pauseMenu = new Table();
        pauseMenu.setFillParent(true); // Make the pause menu table fill the stage

        // Add a label to the pause menu with a message
        pauseMenu.add(new Label("Pause. Press 'ESC' to continue!", game.getSkin())).padBottom(20).row();
        // Create and add a button to load the game
        TextButton loadBtn = new TextButton("Load", game.getSkin());
        pauseMenu.add(loadBtn).width(200).row(); // Add the load button to the pause menu with a specified width
        loadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadGame(); // Load the game when the button is pressed
            }
        });
        // Create and add a button to exit to the menu
        TextButton gotoMenuBtn = new TextButton("Exit", game.getSkin());
        pauseMenu.add(gotoMenuBtn).width(200).row(); // Add the exit button to the pause menu with a specified width
        gotoMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Go to the menu when the button is pressed
            }
        });
        // Initially hide the pause menu
        pauseMenu.setVisible(false);
        // Add the pause menu to the stage
        stage.addActor(pauseMenu);
        // Play the game music
        Manager.getInstance().soundsManager.playGameMusic();

    }



    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Toggle the game pause state
            gamePause = !gamePause; //Sets it to true if it is false and vice versa
            // Show the pause menu based on the game pause state (if true it shows the menu, if false it hides it)
            pauseMenu.setVisible(gamePause);
        }
        // Clear the screen with a black color
        ScreenUtils.clear(0, 0, 0, 1);

        // Update the camera's matrices
        camera.update();
        // Set the batch's projection matrix to the camera's combined matrix
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if(!gamePause) {
            // Create a list to hold newly added entities
            followCamera.follow(player.getPos());

            // update time,score,heart
            time -= v;
            if(time < 0){
                game.goToGameOverScreen(score,time);
                return;
            }

            // Update the time label with the formatted time value
            timeLabel.setText(String.format("%.0f",time) );
            // Update the radius label with the current bomb explosion radius
            radiusLabel.setText("bomb radius: " + explodeRadius);
            // Update the max bomb label with the current maximum number of bombs
            maxBombLabel.setText("max bomb: " + maxBomb);
            //core and heart label
            scoreLabel.setText(score);
            heartLabel.setText(heart);
            // Update the enemy count label with the current number of enemies
            enemyCountLabel.setText("enemies: " + countEnemy());

            // Check if there are no enemies left
            if (countEnemy() == 0) {
                // Set the exit to be unlocked
                canExit = true;
                // Update the exit unlock label to indicate the exit is unlocked
                exitUnlockLabel.setText("exit: unlocked!");
                // Change the color of the exit unlock label to green
                exitUnlockLabel.setColor(Color.GREEN);
            }
            List<Entity> addedObjects = new ArrayList<>();
            // Update the player's state and position based on the map
            player.update(map);
            // Check if the player can place more bombs
            if(countBomb() < maxBomb){
                player.shoot(addedObjects); // Player shoots (places a bomb) if allowed
            } else {
                player.shooting = false; // Disable shooting if the max bomb limit is reached
            }
            // Update the state of all existing entities in the game
            for(Entity obj : objects){
                obj.update();
            }


        // Check for collisions between the player and other objects
        for(Entity obj : objects){
            if(obj instanceof Enemy && obj.getRect().collide(player.getRect())){
                // If the player collides with an enemy, the player dies
                playerDie();
            }
            else if(obj instanceof Exit && obj.getRect().collide(player.getRect())){
                // If the player collides with the exit and can exit, go to the victory screen
                if(canExit){
                    score += 100;
                    game.goToVictoryScreen(score, time);
                }
            }
            else if(obj instanceof MaxRadiusPU && obj.getRect().collide(player.getRect())){
                // If the player collides with a MaxRadiusPU, increase the explosion radius
                obj.destroyFlag = true;
                score += 50;
                explodeRadius += 1;
                if(explodeRadius > 8){
                    explodeRadius = 8;
                }
                Manager.getInstance().soundsManager.play("item", 1.0f);
            }
            else if(obj instanceof MaxBombPU && obj.getRect().collide(player.getRect())){
                // If the player collides with a MaxBombPU, increase the maximum number of bombs
                obj.destroyFlag = true;
                score += 50;
                maxBomb += 1;
                if(maxBomb > 8){
                    maxBomb = 8;
                }
                Manager.getInstance().soundsManager.play("item", 1.0f);
            }
        }

        // Check for collisions between objects
        for(Entity obj : objects){
            for(Entity otherObj: objects){
                if(obj == otherObj){
                    continue; // Skip checking collision with itself
                }
                // Add collision handling logic here if needed
            }
        }

        // Handle bomb explosions
        for(Entity obj : objects){
            if(obj instanceof Bombs && obj.destroyFlag){
                explode((Bombs) obj, addedObjects); // Explode the bomb and add resulting objects
            }
        }

        // Add newly created objects to the main objects list
        objects.addAll(addedObjects);

        // Remove objects that are marked for destruction
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i).destroyFlag){
                objects.remove(i);
            }
        }}

            // Draw the map and player
            map.draw(batch, player);

// Draw each entity within a certain distance from the player (fog of war)
            for (Entity obj : objects) {
                if (Vector2.dst(player.getPos().x, player.getPos().y, obj.getPos().x, obj.getPos().y) > 2000) { // fog of war
                    continue;
                }
                obj.draw(batch);
            }

            // Draw the walls of the map
            map.drawWall(batch, player);

            // Draw the player
            player.draw(batch);

            // Update the stage with a maximum delta time of 1/30th of a second
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

            // Draw the stage
            stage.draw();
            // debug
//        Utils.getInstance().drawRect(player.getRect(),camera);
//        for(int row = 0; row < map.getRows();row++) {
//            for (int col = 0; col < map.getCols(); col++) {
//                Cell cell = map.getCell(row,col);
//                Utils.getInstance().drawRect(cell.getRect(),camera);
//            }
//        }

            viewport.apply(false);




    }


    //TODO TEST NEEDED IF THIS WORKS!!!
    void initInput() {
        InputMultiplexer mixInput = new InputMultiplexer(); // Create an InputMultiplexer to handle multiple input processors
        mixInput.addProcessor(stage); // Add the stage as an input processor to handle UI input
        mixInput.addProcessor(new InputAdapter(){ // Add a new InputAdapter to handle player input
            @Override
            public boolean keyDown(int keycode) {
                player.onKeyDown(keycode); // Call the player's onKeyDown method when a key is pressed
                return super.keyDown(keycode); // Return the result of the superclass method
            }

            @Override
            public boolean keyUp(int keycode) {
                player.onKeyUp(keycode); // Call the player's onKeyUp method when a key is released
                return super.keyUp(keycode); // Return the result of the superclass method
            }
        });
        Gdx.input.setInputProcessor(mixInput); // Set the InputMultiplexer as the input processor for the game

    }

    public void explode(Bombs bomb,List<Entity> addedObjects) {
        // Get the bomb's position and radius
        Vector2 bombPosition = bomb.getPos();
        int explosionRadius = explodeRadius; // Use the current explosion radius

        // Calculate the grid coordinates of the bomb
        int bombRow = (int)(bombPosition.y / 16);
        int bombCol = (int)(bombPosition.x / 16);

        // List to store game objects affected by the explosion
        List<GameObject> explosionGameObjects = new ArrayList<>();

        // Iterate through the grid cells within the explosion radius
        int[][] directions = {
                {0, 1},  // Right
                {0, -1}, // Left
                {1, 0},  // Down
                {-1, 0},  // Up,
                {0,0} // center
        };


        // Check each direction for the explosion
        for (int[] dir : directions) {
            for (int i = 1; i <= explosionRadius; i++) {
                int row = bombRow + dir[0] * i;
                int col = bombCol + dir[1] * i;
                Manager.getInstance().soundsManager.play("boom", 1.0f); // Play explosion sound

                // Check if the cell is within the grid boundaries
                if (row >= 0 && row < map.getRows() && col >= 0 && col < map.getCols()) {
                    GameObject gameObject = map.getCell(row, col);

                    explosionGameObjects.add(gameObject); // Add the game object to the explosion list
                    addedObjects.add(new Blast(new Vector2(gameObject.col * 16 - 4, gameObject.row * 16 - 8))); // Add blast effect

                    // Remove destructible walls and stop explosion if an indestructible wall is hit
                    if (gameObject.cellType == CellType.DESTRUCTIBLE_WALL) {
                        gameObject.cellType = null;
                        score += 10;
                    } else if (gameObject.cellType == CellType.INDESTRUCTIBLE_WALL) {
                        break;
                    }
                }
            }
        }

        // Check if the explosion hits the player
        for (GameObject gameObject : explosionGameObjects) {
            if (gameObject.getRect().collide(player.getRect())) {
                playerDie();
                return;
            }
        }

        // Check if the explosion hits any enemies
        for (Entity obj : objects) {
            for (GameObject gameObject : explosionGameObjects) {
                if (obj instanceof Enemy) {
                    if (gameObject.getRect().collide(obj.getRect())) {
                        obj.destroyFlag = true;
                    }
                }
            }
        }
    }




        int countEnemy(){
        int count = 0;
        for (Entity obj : objects){
            //TODO IN CASE OF ERROR IF ENEMY IS CORRECT
            if(obj instanceof Enemy){
                count++;
            }
        }
        return count;
    }




    void spawnPU(){
        // Get a random empty cell from the map
        GameObject emptyGameObject = map.getRandomEmptyCell();

        // Ensure the empty cell is within 200 units from the player's position
        while (Vector2.dst(player.getPos().x, player.getPos().y, emptyGameObject.col * 16, emptyGameObject.row * 16) > 200) {
            emptyGameObject = map.getRandomEmptyCell();
        }
    }



    // Handle the player's death
    void playerDie(){
        heart--; // Decrease the player's heart count
        // Get the entry cell of the map and set the player's position to the entry point
        GameObject entryGameObject = map.getEntryCell();
        player.setPos(new Vector2(entryGameObject.col * 16, entryGameObject.row * 16));
        // Play the death sound effect
        Manager.getInstance().soundsManager.play("death", 1.0f);
        // If the player has no hearts left, go to the game over screen
        if(heart <= 0){
            game.goToGameOverScreen(score, time);
        }
    }


    int countBomb(){
        int count = 0;
        for (Entity obj : objects){
            if(obj instanceof Bombs){
                count++;
            }
        }
        return count;
    }

        @Override
    public void show() {

    }



    @Override
    public void resize(int i, int i1) {
        camera.setToOrtho(false);
        viewport.update(i,i1,false);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
//OLD CODE; TO REMIND ME OF FUNCTIONALITIES THAT NEED TO BE IMPLEMENTED
//ALSO SEE TODO LIST
//    public static final int TILE_SIZE_PX = 16;
//    public static final int SCALE = 4;
//
//    private final BomberQuestGame game;
//    private final SpriteBatch spriteBatch;
//    private final GameMap map;
//    private final Player player;
//    private final Hud hud;
//    private final OrthographicCamera mapCamera;
//    private final Viewport viewport;
//    private final OrthogonalTiledMapRenderer mapRenderer;
//    private final Music stageMusic;
//    private final Music actionMusic;
//    private final Sound bombSound;
//    private final Sound powerUpSound;
//    private final Sound enemySound;
//    private final Sound bombPlaceSound;
//
//    private final List<Enemy> enemies = new ArrayList<>();
//    private final List<Wall> walls = new ArrayList<>();
//    private final List<Entry> entries = new ArrayList<>();
//    private final List<Exit> exits = new ArrayList<>();
//    private final List<Flowers> flowers = new ArrayList<>();
//    private final List<Floor> floors = new ArrayList<>();
//    private final List<Chest> chests = new ArrayList<>();
//    private Node[][] grid;
//    private final List<Bombs> bombs = new ArrayList<>();
//    private final List <Blast> blasts = new ArrayList<>();
//    private boolean paused;
//
//    private final GameObjectManager gameObjectManager;
//    private final GameMap gameMap;
//    private float radius;
//
//    private int remainingEnemies = 3;
//
//    private final World world;
//    private List<String> mapLines; // Declare mapLines
//
//    //TODO I would implement the HUD here and
//    public GameScreen(BomberQuestGame game) {
//        this.game = game;
//        this.spriteBatch = game.getSpriteBatch();
//        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"));
//
//        hud.stage.draw(); // Render the HUD
//
//        this.world = new World(new Vector2(0, 0), true);
//        MapParser.resetMap();
//
//        // Initialize mapLines to avoid potential null references
//        mapLines = new ArrayList<>();
//
//        // Initialize mapLines to avoid potential null references
//        mapLines = new ArrayList<>();
//
//        // Configure the NativeFileChooser
//        NativeFileChooserConfiguration config = new NativeFileChooserConfiguration();
//        config.title = "Select Map File";
//        config.directory = Gdx.files.local("/");
//        //Löschbar, weil User schon die richtige Datei auswählen wird
//        //config.mimeTypes = new String[] { "properties" }; // Use 'mimeTypes' instead of 'filter'        // Instantiate the correct NativeFileChooser based on the platform
//
//        //Ab hier muss das Lambda in sein
//        //TODO: Implement the following methods in the Map Class?
//
//        //NativeFileChooser fileChooser = getNativeFileChooser();
//
//        //TODO: Implement the following methods in the Map Class?
//        /*fileChooser.chooseFile(config, new NativeFileChooserCallback() {
//            @Override
//            public void onFileChosen(FileHandle fileHandle) {
//                try {
//                    // Use LibGDX's FileHandle to read the file content
//                    String fileContent = fileHandle.readString("UTF-8");
//                    // Split the content into lines and store in mapLines
//                    mapLines = Arrays.asList(fileContent.split("\\r?\\n"));
//                    // Proceed with map initialization using mapLines
//                    initializeMap(mapLines);
//                } catch (Exception e) {
//                    System.err.println("Error reading file: " + e.getMessage());
//                    mapLines = new ArrayList<>();
//                    // Handle the error, possibly by loading a default map
//                    //loadDefaultMap();
//                }
//            }
//
//            @Override
//            public void onCancellation() {
//                System.err.println("File selection was canceled.");
//                mapLines = new ArrayList<>();
//                // Handle the cancellation, possibly by loading a default map
//                //TODO: Implement the following methods in the Map Class?
//
//                //loadDefaultMap();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                System.err.println("File chooser error: " + e.getMessage());
//                mapLines = new ArrayList<>();
//                // Handle the error appropriately
//                //TODO: Implement the following methods in the Map Class?
//                //loadDefaultMap();
//            }
//        });*/
//
//
//
//
//
//        //List<String> mapLines = MapReader.readMapFile("maps/map-1.properties");
//        //List<String> mapLines = MapReader.readMapFile("path/to/map.properties");
//        initializeMap(mapLines); // Centralized parsing and validation
//        this.map = new GameMap(game);
//
////        this.player = new Player(this.world, MapParser.getEntry().x, MapParser.getEntry().y);
//
//        this.mapCamera = new OrthographicCamera();
//        this.viewport = new ExtendViewport(800, 600, mapCamera);
//
//        //map-1.properties nicht tmx
//        //this.mapRenderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("maps/map-1.tmx"), SCALE);
//        TiledMap tiledMap = new TmxMapLoader().load("maps/map-1.tmx");
//        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, SCALE);
//
//
//        this.stageMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/stageMusic.mp3"));
//        stageMusic.setVolume(0.2f);
//        stageMusic.setLooping(true);
//        this.stageMusic.play();
//
//        this.actionMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/actionMusic.mp3"));
//        actionMusic.setVolume(0.2f);
//        actionMusic.setLooping(true);
//        this.actionMusic.play();
//
//        this.bombSound = Gdx.audio.newSound(Gdx.files.internal("audio/bombSound.mp3"));
//        this.powerUpSound = Gdx.audio.newSound(Gdx.files.internal("audio/powerUpSound.mp3"));
//        this.enemySound = Gdx.audio.newSound(Gdx.files.internal("audio/enemySound.mp3"));
//        bombPlaceSound = Gdx.audio.newSound(Gdx.files.internal("audio/bombPlace.mp3"));
//
//        // Initialize missing fields
//        this.radius = 1.0f; // Example value, adjust as needed
//        this.gameObjectManager = new GameObjectManager();
//        this.gameMap = new GameMap(game);
//
//        float coolDownRemaining = Math.max(0, player.getCoolDownTime() - player.getTimeSinceLastBomb());
//        hud.updateCoolDown(coolDownRemaining);
//
//
//
//    }
//    @Override
//    public void render(float delta) {
//        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
//        Node playerPosition = new Node((int) (map.getPlayer().getX() / TILE_SIZE_PX), (int) (map.getPlayer().getY() / TILE_SIZE_PX), true);
//
//        if (!paused) {
//
//            //checkPlayerDeath();
//
//            // Player-Wall collision detection
//            for (Wall wall : walls) {
//                if (wall.getWallRectangle().overlaps(player.getRectangle())) {
//                    if (wall.isDestructible()) {
//                        handleDestructibleWall(wall);
//                    } else {
//                        player.setPosition(player.getPrevX(), player.getPrevY());
//                    }
//                }
//            }
////TODO: Implement the following methods in the Map Class?
//            /*for (Blast blast : blasts) {
//                for (Wall wall : walls) {
//                    if (wall instanceof DestructibleWall && blast.getBoundingRectangle().overlaps(wall.getWallRectangle())) {
//                        handleDestructibleWall(wall);
//                    }
//                }
//            }*/
//
//
//            // Enemy Update Method
//            // Update enemies
//            /*
//            //TODO CHECK IF IMPLEMENTED CORRECTLY
//            for (Enemy enemy : enemies) {
//                Node playerNode = grid[(int) (player.getX() / GameScreen.TILE_SIZE_PX)][(int) (player.getY() / GameScreen.TILE_SIZE_PX)];
//                enemy.updateState(delta, playerNode, grid); // Handle AI and pathfinding
//                enemy.update(delta, walls); // Handle movement and animation
//            }
//
//            //TODO: Implement the following methods in the Map Class?
//         for (Enemy enemy : enemies) {
//                for (Blast blast : blasts) {
//                    if (blast.getBoundingRectangle().overlaps(enemy.getBoundingRectangle())) {
//                        enemy.die();
//                        enemies.remove(enemy); // Remove dead enemy from the list
//                        break; // Stop further checks for this enemy
//                    }
//                }
//            }*/
//
//
//            // Enemy-Wall collision detection
//            //TODO CHECK IF IMPLEMENTED CORRECTLY IN MAP
//            /*for (Wall wall : walls) {
//                for (Enemy enemy : enemies) {
//                    if (enemy.getRectangle().overlaps(wall.getWallRectangle())) {
//                        enemy.handleCollision();
//                    }
//                }
//            }
//
//            // Differentiating between destructible and indestructible walls
//
//            // Enemy-Entry collision detection
//            for (Entry entry : entries) {
//                for (Enemy enemy : enemies) {
//                    if (enemy.getRectangle().overlaps(entry.getRectangle())) {
//                        enemy.handleCollision();
//                    }
//                }
//            }
//
//            // Enemy-Exit collision detection
//            for (Exit exit : exits) {
//                for (Enemy enemy : enemies) {
//                    if (enemy.getRectangle().overlaps(exit.getRectangle())) {
//                        enemy.handleCollision();
//                    }
//                }
//            }*/
//
//            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//                // Call the placeBomb method on the player
//                player.placeBomb(map, gameObjectManager, bombPlaceSound, delta);
//            }
//
//
//            // Check for escape key press to go back to the menu
//            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//                pause();
//            } else if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)
//                    && map.getPlayer().getY() < (MapParser.getHeight() - 1) * TILE_SIZE_PX) { // limits the borders of the game
//                player.moveUp(walls);
//            } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)
//                    && map.getPlayer().getY() > 0) {
//                player.moveDown(walls);
//            } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)
//                    && map.getPlayer().getX() > 0) {
//                player.moveLeft(walls);
//            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)
//                    && map.getPlayer().getX() < (MapParser.getWidth() - 1) * TILE_SIZE_PX) {
//                player.moveRight(walls);
//            }
//
//            // Update player rectangle after movement for collision detection
//            Rectangle updatedRectangle = new Rectangle((int) map.getPlayer().getX() + 2, (int) map.getPlayer().getY() + 2, 12, 12);
//            map.getPlayer().setRectangle(updatedRectangle);
//
//            // Update the camera, centered in the middle
//            updateCamera();
//            mapCamera.update();
//            spriteBatch.setProjectionMatrix(mapCamera.combined);
//
//            // Render the tile map
//            mapRenderer.setView(mapCamera);
//            mapRenderer.render();
//
//            player.update(delta, walls);
//
//            // Supports the player animation and movement
//            if ((!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) &&
//                    (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) &&
//                    (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) &&
//                    (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
//                player.setIsMoving(false);
//            }
//
//            // Call draw methods
//            spriteBatch.begin();
//            draw(spriteBatch, map.getPlayer());
//            //TODO I am not sure what this was
//            /*for (Enemy enemy : enemies) {
//                draw(spriteBatch, enemy);
//            }*/
//            spriteBatch.end();
//
//            // HUD: Set batch to now draw what the Hud camera sees.
//            spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
//            hud.update(delta, player, remainingEnemies, blasts.isEmpty() ? null : blasts.get(0));
//            hud.stage.draw();
//        }
//    }
//
//    private void updateCamera() {
//        mapCamera.position.set(map.getPlayer().getX() * TILE_SIZE_PX * SCALE, map.getPlayer().getY() * TILE_SIZE_PX * SCALE, 0);
//        mapCamera.update();
//    }
//
//    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
//        TextureRegion texture = drawable.getCurrentAppearance();
//        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
//        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
//        float width = texture.getRegionWidth() * SCALE;
//        float height = texture.getRegionHeight() * SCALE;
//        spriteBatch.draw(texture, x, y, width, height);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height);
//        hud.resize(width, height);
//    }
//
//    @Override
//    public void pause() {
//        paused = true;
//    }
//
//    @Override
//    public void resume() {
//        paused = false;
//    }
//
//    @Override
//    public void show() {
//        paused = false;
//    }
//
//    @Override
//    public void hide() {
//        paused = true;
//    }
//
//    @Override
//    public void dispose() {
//        mapRenderer.dispose();
//        stageMusic.dispose();
//        actionMusic.dispose();
//        bombSound.dispose();
//        powerUpSound.dispose();
//        enemySound.dispose();
//    }
//
//    public boolean isPaused() {
//        return paused;
//    }
//
//    public void setPaused(boolean paused) {
//        this.paused = paused;
//    }
//
//    // Add these methods to the GameScreen class
//
//    private void handleDestructibleWall(Wall wall) {
//        if (wall instanceof DestructibleWall) {
//            // Calculate the wall's position in the map grid
//            Point wallPosition = new Point((int) wall.getX() / TILE_SIZE_PX, (int) wall.getY() / TILE_SIZE_PX);
//
//            // Get the object ID beneath the wall
//            Integer objectId = MapParser.getObjects().get(wallPosition);
//
//            // Handle objects revealed under the wall
//            //TODO CHECK IF MAP IS IMPLEMENTED THIS CORRECTLY
//            /*if (objectId != null) {
//                switch (objectId) {
//                    case 4: // Exit
//                        gameObjectManager.addGameObject(new Exit(wall.getX(), wall.getY(), true, false));
//                        System.out.println("Exit revealed at: " + wallPosition);
//                        break;
//                    case 5: // Concurrent Bomb Power-Up
//                        gameObjectManager.addGameObject(new ConcurrentBombPowerUp(wall.getX(), wall.getY(), map, gameObjectManager));
//                        System.out.println("Concurrent Bomb Power-Up revealed at: " + wallPosition);
//                        break;
//                    case 6: // Blast Radius Power-Up
//                        //TODO: Implement the following methods in the Map Class?
//
//                        //gameObjectManager.addGameObject(new BlastRadiusPowerUp(wall.getX(), wall.getY(), 1, 0.5f, 1, map, gameObjectManager));
//                        System.out.println("Blast Radius Power-Up revealed at: " + wallPosition);
//                        break;
//                    default:
//                        System.out.println("Unknown object beneath the wall at: " + wallPosition);
//                        break;
//                }
//            }*/
//
//            // Remove the destructible wall from the game
//            walls.remove(wall);
//            MapParser.getObjects().remove(wallPosition); // Remove it from the map state as well
//
//            System.out.println("Destructible wall removed at: " + wallPosition);
//        } else {
//            System.err.println("Error: Non-destructible wall passed to handleDestructibleWall.");
//        }
//    }
//
//
//    private void handleIndestructibleWall(Wall wall) {
//        // Logic for handling indestructible walls
//        // For example, you can simply ignore them or add some specific behavior
//
//
//    }
////TODO: Implement the following methods in the Map Class?
//    /*private boolean isBombExplodedNear(Wall wall) {
//        for (Blast blast : blasts) {
//            if (blast.getBoundingRectangle().overlaps(wall.getRectangle())) {
//                return true;
//            }
//        }
//        return false;
//    }*/
//
//    private void destroyWall(Wall wall) {
//        // Logic to destroy the wall
//        // For example, remove the wall from the list of walls and update the game state
//        walls.remove(wall);
//        // Additional logic to update the game state
//    }
//
//
////TODO: Implement the following methods in the Map Class?
//    /*private void explode(float x, float y) {
//        Blast blast = new Blast(x, y, (int) radius, 0.5f, 1, gameMap, gameObjectManager);
//        gameObjectManager.addGameObject(blast);
//        blasts.add(blast);
//    }*/
//
//    private void gameOver() {
//        // Logic to handle game over, e.g., show game over screen, stop music, etc.
//        stageMusic.stop();
//        actionMusic.stop();
//        // Transition to game over screen or main menu
//        game.setScreen(new GameOverScreen(game));
//    }
//
//    public List<Wall> getWalls() {
//        return walls;
//    }
//
//    //TODO STILL NEEDS TO BE IMPLEMENTED
//    /*private void checkPlayerDeath() {
//        // Check for player collision with enemies
//        for (Enemy enemy : enemies) {
//            if (enemy.getBoundingRectangle().overlaps(player.getRectangle())) {
//                player.setAlive(false);
//                System.out.println("Player killed by enemy!");
//                gameOver();
//                return; // Stop further updates
//            }
//        }*/
//
//        // Check for player collision with blasts
//        /*for (Blast blast : blasts) {
//            if (blast.getBoundingRectangle().overlaps(player.getRectangle())) {
//                player.setAlive(false);
//                System.out.println("Player hit by explosion!");
//                gameOver();
//                return; // Stop further updates
//            }
//        }*/



