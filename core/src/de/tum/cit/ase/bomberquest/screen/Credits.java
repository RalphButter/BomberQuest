package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

public class Credits {
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

        // The credits text to be displayed
        creditsText = "CREDITS\n\n"
                + "TEXT EINFÜGEN";

        scrollPosition = 30; // Initial scroll position
    }

}
