package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
import de.tum.cit.ase.bomberquest.util.Manager;

/**
 * The GameOverScreen class is responsible for displaying the game-over screen
 * when the player fails to complete a level in the game.
 * It extends the LibGDX Screen interface and sets up UI components such as labels, buttons,
 * and background music to provide feedback to the player.
 */


public class GameOverScreen implements Screen  {
    // Reference to the main game class to access global resources and methods
    //private final BomberQuestGame game; I don't think we need that

    // Stage manages the viewport and handles the drawing and input of UI elements
    private final Stage stage;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     * @param score The player's score at the time of game over.
     * @param time The time elapsed in the game.
     */
    public GameOverScreen(BomberQuestGame game, int score, float time) {
        // Create a new orthographic camera
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        // Create a viewport with the camera
        Viewport viewport = new ScreenViewport(camera);
        // Create a stage for UI elements with the viewport and sprite batch
        stage = new Stage(viewport, game.getSpriteBatch());

        // Create a table for layout
        Table table = new Table();
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        Label gameOverLabel = new Label("GameOver!!", game.getSkin(), "title");
        Label scoreLabel = new Label("Score: " + score, game.getSkin(), "title");
        Label timeLabel = new Label("Time: " + String.format("%.2fs", time), game.getSkin(), "title");

        // Set font scale for labels
        timeLabel.setFontScale(0.5f);
        scoreLabel.setFontScale(0.5f);

        // Set color for the game over label
        gameOverLabel.setColor(Color.WHITE);

        // Add labels to the table with padding and row separation
        table.add(gameOverLabel).padBottom(20).row();
        table.add(scoreLabel).padBottom(20).row();
        table.add(timeLabel).padBottom(20).row();

        // Create and add a button to go to the game screen
        TextButton goToGameButton = new TextButton("Exit!", game.getSkin());
        table.add(goToGameButton).width(300).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Change to the game screen when button is pressed
            }
        });

// Play game over music
        Manager.getInstance().soundsManager.playGameOverMusic();

    }


    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Renders the GameOverScreen. This method should be called every frame to update and draw the stage.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        // Update the stage logic, such as handling input and actions
        //stage.act(delta); We don't need this one

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage

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
    }
}




