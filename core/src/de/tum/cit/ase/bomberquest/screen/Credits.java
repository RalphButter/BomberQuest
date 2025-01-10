package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

public class Credits implements Screen {
    private BomberQuestGame game;
    private SpriteBatch spriteBatch; // Local SpriteBatch
    private BitmapFont font;
    private Viewport viewport;
    private float scrollPosition; // Tracks the vertical scrolling position
    private String creditsText; // The text to be displayed on the credits screen
    private GlyphLayout layout; // Cached layout for text rendering
    private static final float SCROLL_SPEED = 10f; // Increased scrolling speed

    public Credits(BomberQuestGame game) {
        this.game = game;

        // Initialize local SpriteBatch
        spriteBatch = new SpriteBatch();

        // Initialize font
        font = new BitmapFont(); // Consider using a custom font for better visuals
        font.getData().setScale(1.0f); // Start with default size
        font.setColor(1, 1, 1, 1); // Ensure full opacity (RGBA)

        // Initialize viewport
        viewport = new StretchViewport(800, 500); // Desired resolution

        // Define credits text
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
                + "Special thanks to Prof. Dr. Stephan Krusche,\n"
                + "and our dedicated players across the universe.";

        // Initialize GlyphLayout with wrapping and alignment
        layout = new GlyphLayout();
        layout.setText(font, creditsText, Color.WHITE, viewport.getWorldWidth(), Align.center, true);

        // Start scrollPosition just below the screen (adjusted to ensure initial visibility)
        scrollPosition = -layout.height; // Start slightly below to have some text visible
    }

    @Override
    public void show() {
        Gdx.app.log("DEBUG", "Credits screen shown");
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Apply the viewport
        viewport.apply();

        // Debug logs for scrollPosition and layout height
        Gdx.app.log("DEBUG", "scrollPosition: " + scrollPosition + ", layout height: " + layout.height);

        // Begin the local SpriteBatch to draw elements
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // **Fixed Positioning:** Draw text starting at x=0 to allow GlyphLayout's alignment to center the text
        font.draw(spriteBatch, layout, 0, scrollPosition);

        // End the SpriteBatch
        spriteBatch.end();

        // Update the scroll position to create a scrolling effect (scrolling upwards)
        scrollPosition += delta * SCROLL_SPEED;

        // Reset scroll position when the text has fully scrolled off-screen
        if (scrollPosition > viewport.getWorldHeight() + layout.height) {
            scrollPosition = -layout.height; // Reset to start below the screen
        }

        // Handle input to return to the main menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Update GlyphLayout with the new viewport dimensions
        layout.setText(font, creditsText, Color.WHITE, viewport.getWorldWidth(), Align.center, true);

        // Reset scroll position to ensure text remains visible after resize
        scrollPosition = layout.height + 20;
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.app.log("DEBUG", "Credits screen hidden");
    }

    @Override
    public void dispose() {
        spriteBatch.dispose(); // Dispose the local SpriteBatch
        font.dispose(); // Dispose the font to free resources
    }
}
