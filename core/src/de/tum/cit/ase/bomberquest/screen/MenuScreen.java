package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

import java.io.IOException;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    // Reference to the main game class to access global resources and methods
    private final BomberQuestGame game;

    // Stage manages the viewport and handles the drawing and input of UI elements
    private final Stage stage;

    // Music instance to play the background music
    private Music menuMusic; // Background music for the menu screen


    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    /**
     * Constructor for MenuScreen. Initializes the camera, viewport, stage,
     * UI elements, and plays the background music.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(BomberQuestGame game) {
        // Assign the passed game instance to the class member for later use
        this.game = game;

        // Initialize a new OrthographicCamera for 2D rendering
        OrthographicCamera camera = new OrthographicCamera();

        // Set the camera zoom to 1.5 to provide a closer view of the stage
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        // Create a ScreenViewport using the camera, which manages the viewport's size and scaling
        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera

        // Initialize the Stage with the created viewport and the game's SpriteBatch for rendering
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        // Set the input processor to the stage to handle UI input events
        Gdx.input.setInputProcessor(stage); // Enable input handling for the stage

        // Initialize and configure background music
        // Ensure the path is relative to the assets folder
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/background.mp3"));
        menuMusic.setLooping(true); // Set the music to loop indefinitely
        menuMusic.setVolume(0.2f); // Set the music volume to 20% of the maximum
        menuMusic.play(); // Start playing the background music

        // Create a new Table, which is a UI widget for organizing other UI elements in a table layout
        Table table = new Table(); // Create a table for layout

        // Make the table fill the entire stage, ensuring that UI elements are positioned relative to the stage's size
        table.setFillParent(true); // Make the table fill the stage

        // Add the table to the stage so that it becomes part of the scene graph and is rendered
        stage.addActor(table); // Add the table to the stage

        // Create and add a label as the title of the menu screen
        Label titleLabel = new Label("MAZE RUNNER", game.getSkin(), "title"); // Create a title label
        table.add(titleLabel).padBottom(80).row(); // Add the label to the table with bottom padding and move to the next row

        // Create and add the "Go To Game" button
        TextButton goToGameButton = new TextButton("Go To Game", game.getSkin()); // Create a button labeled "Go To Game"
        table.add(goToGameButton).width(300).row(); // Add the button to the table with a width of 300 units and move to the next row
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When the button is pressed, open the file chooser to start a new game
                game.openFileChooser(); // Open file chooser to select game configuration or level

                // Attempt to transition to the game screen
                game.goToGame(); // Change to the game screen when button is pressed

                // Pause the background music to prevent overlap with game music
                menuMusic.pause(); // Pause the menu background music
            }
        });

        // Create and add the "Credits" button
        TextButton creditsButton = new TextButton("Credits", game.getSkin()); // Create a button labeled "Credits"
        table.add(creditsButton).width(300).padTop(20).row(); // Add the button to the table with a width of 300 units, top padding, and move to the next row
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When the "Credits" button is pressed, transition to the Credits screen
                game.goToCreditsScreen(game); // Method to change to the Credits screen

                // Dispose of the background music to free resources
                menuMusic.dispose(); // Dispose of the menu background music
            }
        });

        // Create and add the "Exit" button
        TextButton exitButton = new TextButton("Exit", game.getSkin()); // Create a button labeled "Exit"
        table.add(exitButton).width(300).padTop(20); // Add the button to the table with a width of 300 units and top padding

        // Add a listener to handle the "Exit" button press
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When the "Exit" button is pressed, exit the application
                Gdx.app.exit(); // Exit the application
            }
        });
    }

    
    /**
     * The render method is called every frame to render the menu screen.
     * It clears the screen and draws the stage.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.250f); // Cap frame time to 250ms to prevent spiral of death        ScreenUtils.clear(Color.BLACK);
        ScreenUtils.clear(Color.BLACK);
        stage.act(frameTime); // Update the stage
        stage.draw(); // Draw the stage
    }
    
    /**
     * Resize the stage when the screen is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}


/*    public MenuScreen(BomberQuestGame game) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("Hello World from the Menu!", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        TextButton goToGameButton = new TextButton("Go To Game", game.getSkin());
        table.add(goToGameButton).width(300).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToGame(); // Change to the game screen when button is pressed
            }
        });
    }*/


