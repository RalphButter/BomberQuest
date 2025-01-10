package de.tum.cit.ase.bomberquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.tum.cit.ase.bomberquest.audio.MusicTrack;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.screen.*;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

/**
 * The BomberQuestGame class represents the core of the Bomber Quest game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class BomberQuestGame extends Game {

    // Screens for different game states
    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    /**
     * Sprite Batch for rendering game elements.
     * This eats a lot of memory, so we only want one of these.
     */
    private SpriteBatch spriteBatch;

    /** The game's UI skin. This is used to style the game's UI elements. */
    private Skin skin;

    /**
     * The file chooser for loading map files from the user's computer.
     * This will give you access to a {@link com.badlogic.gdx.files.FileHandle} object,
     * which you can use to read the contents of the map file as a String, and then parse it into a {@link GameMap}.
     */
    private final NativeFileChooser fileChooser;
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
        this.skin = new Skin(Gdx.files.internal("skin/craftacular/craftacular-ui.json")); // Load UI skin
        this.map = new GameMap(this); // Create a new game map (you should change this to load the map from a file instead)
        MusicTrack.BACKGROUND.play(); // Play some background music
        goToMenu(); // Navigate to the menu screen
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
     * Called when the player loses all lives. Switches to the GameOver screen.
     */
    public void gameOver() {
        this.setScreen(new GameOverScreen(this)); // Set the current screen to GameOverScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
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
        this.setScreen(new VictoryScreen(this)); // Set the current screen to VictoryScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Switches to the game screen, disposing of the menu screen if it exists.
     */
    public void goToGame() {
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
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
}




//Patrick's Methoden


/**
 * Switches to the game screen.
 */
/*
public void goToGame() {
    this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
}*/