package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

public class Credits implements Screen {
    private BomberQuestGame game;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Viewport viewport;
    private float scrollPosition; // Tracks the vertical scrolling position
    private String creditsText; // The text to be displayed on the credits screen



    public Credits(BomberQuestGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(); // Use your preferred font
        viewport = new StretchViewport(800, 500); // Set to your desired resolution

        //TODO TEXT EINFÜGEN
        // The credits text to be displayed
        creditsText = "CREDITS\n\n"
                + "In the vibrant realm of pixels and code,\n"
                + "where imagination fuels endless adventures,\n"
                + "our game was born.\n\n"
                + "This creation stands as a testament\n"
                + "to our passion, creativity, and relentless dedication.\n\n"
                + "As we present our masterpiece to the world,\n"
                + "we pause to honor those who transformed our vision\n"
                + "into a dynamic and captivating reality.\n\n\n"
                + "BOMBERQUEST\n\n"
                + "Developed by: Ralph & Jonas WS24/25\n"
                + "Developed for: Introduction to Programming (CIT5230000) WS24/25\n\n"
                // TODO: Look up audio credits
                + "Special thanks to Lagun, Prof. Dr. Stephan Krusche,\n"
                + "and our dedicated players across the universe.";

        scrollPosition = 30; // Initial scroll position
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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
