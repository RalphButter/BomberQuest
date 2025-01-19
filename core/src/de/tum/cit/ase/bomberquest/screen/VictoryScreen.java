package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

/**
 * The VictoryScreen class is responsible for displaying the victory screen
 * after the player successfully completes a level in the game.
 * It extends the LibGDX Screen interface and sets up UI components such as labels, buttons,
 * and background music to provide a satisfying conclusion to the level.
 */

//TODO This class is also wrong
public class VictoryScreen implements Screen {
    private final Stage stage; // Stage manages the 2D scene for UI elements.
    private final Music victoryMusic; // Music that plays when the victory screen is displayed.

    /**
     * Constructor for the VictoryScreen class. It initializes the camera, stage,
     * layout (Table), and UI components, and plays victory music.
     *
     * @param game The main game instance used to access shared resources (e.g., skin, sprite batch).
     */
    public VictoryScreen(BomberQuestGame game) {
        // Create a camera for managing the view of the stage.
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Adjust zoom to control the visible portion of the stage.

        // Create a viewport that uses the camera to adjust the stage's view.
        Viewport viewport = new ScreenViewport(camera);

        // Initialize the stage using the viewport and the game's shared sprite batch.
        stage = new Stage(viewport, game.getSpriteBatch());

        // Create a table for layout management, making it easy to arrange UI elements.
        Table table = new Table();
        table.setFillParent(true); // Ensure the table fills the entire stage.
        stage.addActor(table); // Add the table as an actor to the stage.

        // Add a label to display the victory message.
        table.add(new Label("WELL DONE. LEVEL COMPLETED!", game.getSkin(), "title"))
                .padBottom(80) // Add padding below the label for spacing.
                .row(); // Move to the next row for subsequent UI elements.

        // Create a button for navigating back to the main menu.
        TextButton goToMainMenu = new TextButton("Go To Main Menu", game.getSkin());

        // Add a listener to the button to handle user interaction.
        goToMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Stop the victory music when the button is clicked.
                victoryMusic.stop();
                // Navigate back to the main menu screen.
                game.goToMenu();
            }
        });

        // Add the button to the table, setting its width and moving to the next row.
        table.add(goToMainMenu).width(300).row();

        // Load and set up the victory music.
        victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/8-bit-Victory.mp3"));
        victoryMusic.setLooping(false); // Ensure the music does not loop.
        victoryMusic.play(); // Start playing the music.
        victoryMusic.setVolume(0.2f); // Set the volume to a low level.
    }

    /**
     * Renders the screen and its components. Called continuously by the game loop.
     *
     * @param delta Time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        // Clear the screen with the default background color.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the stage logic, ensuring smooth animations and interactions.
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Limit updates to 30 FPS.

        // Render the stage, including all its actors (UI components).
        stage.draw();
    }

    /**
     * Handles resizing the screen. Adjusts the viewport to match the new dimensions.
     *
     * @param width  New screen width.
     * @param height New screen height.
     */
    @Override
    public void resize(int width, int height) {
        // Update the viewport to ensure the stage scales correctly.
        stage.getViewport().update(width, height, true);
    }

    /**
     * Disposes of resources to prevent memory leaks. Called when the screen is no longer needed.
     */
    @Override
    public void dispose() {
        stage.dispose(); // Dispose of the stage and its resources.
    }

    /**
     * Called when this screen is set as the active screen. Prepares it for interaction.
     */
    @Override
    public void show() {
        // Set the stage as the input processor so it can handle user input events.
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are required by the Screen interface but are not used here.

    /**
     * Called when the game is paused (e.g., when the app loses focus).
     */
    @Override public void pause() { }

    /**
     * Called when the game is resumed after being paused.
     */
    @Override public void resume() { }

    /**
     * Called when this screen is no longer the active screen.
     */
    @Override public void hide() { }
}
