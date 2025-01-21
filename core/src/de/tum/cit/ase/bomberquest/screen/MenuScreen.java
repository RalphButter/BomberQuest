package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import de.tum.cit.ase.bomberquest.util.Manager;

/**
 * The {@code MenuScreen} class represents the main menu of the game.
 * It initializes and manages UI elements, including buttons for starting the game, viewing credits, and exiting.
 * Background music is also handled in this class.
 */
public class MenuScreen implements Screen {

    private final BomberQuestGame game;
    private final Stage stage;
    private Music menuMusic; // Background music for the menu

    /**
     * Constructs the menu screen, setting up the stage, UI elements, and background music.
     *
     * @param game The main game instance, used to transition between screens and access resources.
     */
    public MenuScreen(BomberQuestGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements
        Gdx.input.setInputProcessor(stage); // Enable input handling for the stage

        // Initialize background music
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/background.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.2f);
        menuMusic.play();

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        Label titleLabel = new Label("BomberMan", game.getSkin(), "title");
        table.add(titleLabel).padBottom(80).row();

        // Create and add buttons
        TextButton goToGameButton = new TextButton("Go To Game", game.getSkin());
        TextButton creditsButton = new TextButton("Credits", game.getSkin());
        TextButton exitButton = new TextButton("Exit", game.getSkin());

        table.add(goToGameButton).width(300).row();
        table.add(creditsButton).width(300).padTop(20).row();
        table.add(exitButton).width(300).padTop(20);

        // Button listeners
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openFileChooser();
                game.goToGame();
                menuMusic.pause();
            }
        });
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToCreditsScreen();
                menuMusic.dispose();
            }
        });
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    /**
     * Renders the menu screen every frame by clearing the screen and drawing the stage.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * Updates the viewport when the screen is resized.
     *
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Disposes of resources when the screen is no longer needed.
     */
    @Override
    public void dispose() {
        stage.dispose();
        menuMusic.dispose();
    }

    /**
     * Called when the screen is shown, ensuring the stage receives input events.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
