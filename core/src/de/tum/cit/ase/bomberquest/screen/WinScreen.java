package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import de.tum.cit.ase.bomberquest.util.Manager;

/**
 * The {@code WinScreen} class represents the victory screen displayed after a successful level completion.
 * It provides a congratulatory message, displays the player's score and completion time,
 * and allows the player to return to the main menu.
 */
public class WinScreen implements Screen {
    private final Stage stage; // Stage for managing UI elements in the 2D scene.

    /**
     * Constructs the WinScreen with victory messages, score, time, and an exit button.
     *
     * @param game The main game instance, used for navigation and accessing resources.
     * @param score The player's final score for the level.
     * @param time The time taken to complete the level.
     */
    public WinScreen(BomberQuestGame game, int score, float time) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add labels for victory message, score, and time
        Label gameOverLabel = new Label("You win!", game.getSkin(), "title");
        Label scoreLabel = new Label("Score: " + score, game.getSkin(), "title");
        Label timeLabel = new Label("Time: " + String.format("%.2fs", time), game.getSkin(), "title");

        gameOverLabel.setColor(Color.WHITE); // Set title text color
        timeLabel.setFontScale(0.5f); // Reduce font size for better readability
        scoreLabel.setFontScale(0.5f); // Reduce font size for better readability

        table.add(gameOverLabel).padBottom(20).row(); // Add title label with padding
        table.add(scoreLabel).padBottom(20).row(); // Add score label with padding
        table.add(timeLabel).padBottom(20).row(); // Add time label with padding

        // Create and add an exit button to return to the menu
        TextButton goToGameButton = new TextButton("Exit", game.getSkin());
        table.add(goToGameButton).width(300).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Return to the main menu when button is pressed
            }
        });

        // Play victory music when the screen is displayed
        Manager.getInstance().soundsManager.playWinMusic();
    }

    /**
     * Called when the screen is shown. Can be used for setup if needed.
     */
    @Override
    public void show() {
    }

    /**
     * Renders the victory screen each frame.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
    }

    /**
     * Called when the screen is resized. Updates the viewport dimensions.
     *
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Called when the game is paused (e.g., when the application loses focus).
     */
    @Override
    public void pause() {
    }

    /**
     * Called when the game is resumed after a pause.
     */
    @Override
    public void resume() {
    }

    /**
     * Called when the screen is hidden (e.g., when switching to another screen).
     */
    @Override
    public void hide() {
    }

    /**
     * Disposes of resources when the screen is no longer needed.
     */
    @Override
    public void dispose() {
    }
}
