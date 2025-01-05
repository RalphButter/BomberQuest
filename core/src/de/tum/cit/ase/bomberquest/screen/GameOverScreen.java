package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;


public class GameOverScreen implements Screen  {
    // Reference to the main game class to access global resources and methods
    private final BomberQuestGame game;

    // Stage manages the viewport and handles the drawing and input of UI elements
    private final Stage stage;

    // SpriteBatch is used for drawing 2D graphics efficiently
    private SpriteBatch batch; // SpriteBatch for drawing animations

    // Music instance to play the game-over sound
    private Music gameOverSound; // Music to play on game over

    /**
     * Constructor for GameOverScreen. Initializes the camera, viewport, stage,
     * UI elements, and plays the game-over music.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameOverScreen(BomberQuestGame game) {
        // Assign the passed game instance to the class member for later use
        this.game = game;

        // Initialize a new SpriteBatch for rendering animations or other graphics
        batch = new SpriteBatch(); // Create a SpriteBatch for drawing animations

        // Create an OrthographicCamera which is a 2D camera for rendering the stage
        OrthographicCamera camera = new OrthographicCamera();

        // Set the camera zoom to 1.5 to provide a closer view of the stage
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        // Create a ScreenViewport using the camera, which manages the viewport's size and scaling
        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera

        // Initialize the Stage with the created viewport and the game's SpriteBatch for rendering
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        // Create a new Table, which is a UI widget for organizing other UI elements in a table layout
        Table table = new Table(); // Create a table for layout

        // Make the table fill the entire stage, ensuring that UI elements are positioned relative to the stage's size
        table.setFillParent(true); // Make the table fill the stage

        // Add the table to the stage so that it becomes part of the scene graph and is rendered
        stage.addActor(table); // Add the table to the stage

        // Create a new Label with the text "GAME OVER!", using the game's skin for styling, and a "title" style
        Label gameOverLabel = new Label("GAME OVER!", game.getSkin(), "title");

        // Add the label to the table, pad the bottom with 80 units for spacing, and move to the next row
        table.add(gameOverLabel).padBottom(80).row(); // Add a label as a title for the game over screen

        // Create a new TextButton with the label "Go To Main Menu", using the game's skin for styling
        TextButton goToMainMenuButton = new TextButton("Go To Main Menu", game.getSkin());

        // Add a ChangeListener to the button to handle click events
        goToMainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When the button is pressed, call the game's method to switch to the main menu screen
                game.goToMenu(); // Change to the menu screen when button is pressed

                // Stop the game-over music from playing
                gameOverSound.stop(); // Stop the game over music
            }
        });


        // Add the button to the table, set its width to 300 units, and move to the next row
        table.add(goToMainMenuButton).width(300).row(); // Add a button to return to the main menu screen

        // Initialize the gameOverSound by loading the music file from the internal assets directory
        gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("assets/8-bit-Lost.mp3"));

        // Set the music to not loop; it will play only once
        gameOverSound.setLooping(false);

        // Play the game-over music
        gameOverSound.play();

        // Set the volume of the game-over music to 20% of its maximum volume
        gameOverSound.setVolume(0.2f);
    }


    @Override
    public void show() {

    }

    /**
     * Renders the GameOverScreen. This method should be called every frame to update and draw the stage.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        // Update the stage logic, such as handling input and actions
        stage.act(delta);

        // Clear the screen with a black color (RGBA: 0,0,0,1)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage and all its actors (UI elements)
        stage.draw();
    }

    /**
     * Handles the resizing of the screen. Updates the viewport to the new width and height.
     *
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        // Update the stage's viewport with the new screen size, centering the camera
        stage.getViewport().update(width, height, true);
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

    /**
     * Disposes of the resources used by the GameOverScreen to free up memory.
     * This should be called when the screen is no longer needed.
     */
    public void dispose() {
        // Dispose of the stage and SpriteBatch to free resources
        stage.dispose();
        batch.dispose();

        // Dispose of the game-over music to prevent memory leaks
        gameOverSound.dispose();
    }
}




