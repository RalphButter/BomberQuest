package de.tum.cit.ase.bomberquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import de.tum.cit.ase.bomberquest.audio.MusicTrack;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.screen.*;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FilenameFilter;

/**
 * The BomberQuestGame class represents the core of the Bomber Quest game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class BomberQuestGame extends Game {

    // Screens for different game states
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private WinScreen winScreen;
    private GameOverScreen gameOverScreen;

    /**
     * Sprite Batch for rendering game elements.
     * This eats a lot of memory, so we only want one of these.
     */
    private SpriteBatch spriteBatch;

    /** The game's UI skin. This is used to style the game's UI elements. */
    private Skin skin;

    // Character animation downwards
    private Animation<TextureRegion> characterDownAnimation;
    NativeFileChooser fileChooser;
    BomberQuestGame that;

    /**
     * The file chooser for loading map files from the user's computer.
     * This will give you access to a {@link com.badlogic.gdx.files.FileHandle} object,
     * which you can use to read the contents of the map file as a String, and then parse it into a {@link GameMap}.
     */
    private String filePath;


    /**
     * The map. This is where all the game objects are stored.
     * This is owned by {@link BomberQuestGame} and not by {@link GameScreen}
     * because the map should not be destroyed if we temporarily switch to another screen.
     */
    private GameMap map;

    /**
     * Constructor for BomberQuestGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public BomberQuestGame(NativeFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     * During the class constructor, libGDX is not fully initialized yet.
     * Therefore this method serves as a second constructor for the game,
     * and we can use libGDX resources here.
     */
    @Override
    public void create() {
        this.spriteBatch = new SpriteBatch(); // Create SpriteBatch for rendering
        skin = new Skin(Gdx.files.internal("skin/craftacular/craftacular-ui.json")); // Load UI skin
        this.loadCharacterAnimation(); // Load character animation
        //MusicTrack.BACKGROUND.play(); // Play some background music
        //goToMenu(); // Navigate to the menu screen
    }

    /**
     * Loads the character animation from the character.png file.
     */
    private void loadCharacterAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4; //TODO I am not sure here check if it is correct

        // libGDX internal Array instead of ArrayList because of performance
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            walkFrames.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));
        }

        characterDownAnimation = new Animation<>(0.1f, walkFrames);
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        // No need to dispose here; it's handled in the overridden setScreen method
    }

    /** Returns the skin for UI elements. */
    public Skin getSkin() {
        return skin;
    }

    /** Returns the main SpriteBatch for rendering. */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /** Returns the current map, if there is one. */
    public GameMap getMap() {
        return map;
    }

    /**
     * Returns the currently stored file path from the file chooser.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Returns the current MenuScreen (if any).
     */
    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    /**
     * Returns the current GameScreen (if any).
     */
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Switches to the credits screen.
     */
    public void goToCreditsScreen() {
        setScreen(new Credits(this)); // Pass 'this' to Credits constructor
        // No need to dispose here; it's handled in the overridden setScreen method
    }



    /**
     * Opens a file chooser dialog to select a file from the "maps" directory.
     * Only .properties files are accepted. The chosen file path is saved.
     */
    public void openFileChooser() {
        // Create a configuration for the file chooser
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        conf.directory = Gdx.files.internal("maps"); // Set initial directory if desired

        // Open the file chooser with the specified configuration
        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            // Method checks if the selected file has a .properties extension.
            @Override
            public void onFileChosen(com.badlogic.gdx.files.FileHandle file) {
                // If the file is a .properties file, it saves the file path to the filePath variable.
                if (file.extension().equals("properties")) {
                    filePath = file.path(); // Save the chosen file path
                } else {
                    // If the file is not a .properties file, it logs a message indicating that the selected file is not a .properties file.
                    Gdx.app.log("FileChooser", "Selected file is not a .properties file");
                }
            }

            @Override
            public void onCancellation() {
                // Log a message indicating that the file chooser was cancelled by the user
                Gdx.app.log("FileChooser", "Cancelled");
            }

            @Override
            public void onError(Exception exception) {
                // Log an error message with the details of the exception that occurred
                Gdx.app.error("FileChooser", "Error: " + exception.getMessage());
            }
        });
    }

    /**
     * Called when the player wins. Switches to the Victory screen.
     */
    public void victory() {
//        this.setScreen(new WinScreen(this)); // Set the current screen to VictoryScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Switches to the given screen and disposes of the previous screen.
     * @param screen the new screen
     */
    @Override
    public void setScreen(Screen screen) {
        Screen previousScreen = getScreen(); // Use getter for clarity
        super.setScreen(screen);
        if (previousScreen != null) {
            previousScreen.dispose();
        }
    }

    /** Cleans up resources when the game is disposed. */
    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose(); // Dispose the current screen
        }
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }


    /**
     * Switches to the GameOver screen.
     *
     * @param score The player's score at the time of game over.
     * @param time The time elapsed in the game.
     */
    public void goToGameOverScreen(int score, float time) {
        // Check if the game over screen is already created
        if (gameOverScreen == null) {
            // Create a new game over screen with the current score and time
            gameOverScreen = new GameOverScreen(this, score, time);
        }
        // Set the current screen to the game over screen
        this.setScreen(gameOverScreen);
    }

    /**
     * Switches to the WinScreen, disposing of the menu screen if it exists.
     */
    public void goToVictoryScreen(int score, float time) {
        // Check if the win screen is already created
        if (winScreen == null) {
            // Create a new win screen with the current score and time
            winScreen = new WinScreen(this, score, time);
        }
        // Set the current screen to the win screen
        this.setScreen(winScreen);
    }

    /**
     * Switches to the game screen, disposing of the menu screen if it exists.
     */
    public void goToGame() {
        if (gameScreen == null) {
            //Yeah, I am gonna set default map 1 we can load the other over the load Menu part TODO CHECK IF LOAD WORKS
            gameScreen = new GameScreen(this,"maps/map-1.properties");
        }
        this.setScreen(gameScreen);
    }

    public void loadGame() {
        // Create a new configuration for the file chooser
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();

        // Set the directory to the local "maps" folder
        conf.directory = Gdx.files.local("/maps");

        // Set a name filter to only accept files ending with ".properties"
        conf.nameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        };

        // Set the title of the file chooser dialog
        conf.title = "Choose map";

        // Open the file chooser with the specified configuration
        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                // Print the path of the chosen file
                System.out.println(file.path());
                // Create a new game screen with the chosen file path and set it as the current screen
                gameScreen = new GameScreen(that, file.path());
                setScreen(gameScreen);
            }

            @Override
            public void onCancellation() {
                // Handle cancellation (optional)
            }

            @Override
            public void onError(Exception exception) {
                // Handle error (optional)
            }
        });
    }



}




//Patrick's Methoden


/**
 * Switches to the game screen.
 */
/*
public void goToGame() {
    this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
}*/

/**
 * Called when the player loses all lives. Switches to the GameOver screen.
 */
//public void gameOver(int score,float time) {
//    //Necessary to be safe
//    if (gameOverScreen == null) {
//        gameOverScreen = new GameOverScreen(this,score,time);
//    }
//    this.setScreen(gameOverScreen); //Necessary to switch to gameOverScreen
//}