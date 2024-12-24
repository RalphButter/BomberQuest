package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.gameobject.Blast;
import de.tum.cit.ase.bomberquest.map.Player;


/**
 * A Heads-Up Display (HUD) that displays information on the screen.
 * It uses a separate camera so that it is always fixed on the screen.
 */
public class Hud {
    
    /** The SpriteBatch used to draw the HUD. This is the same as the one used in the GameScreen. */
    private final SpriteBatch spriteBatch;
    /** The font used to draw text on the screen. */
    private final BitmapFont font;
    /** The camera used to render the HUD. */
    private final OrthographicCamera camera;

    public Stage stage;
    private Viewport viewport;
    private float timeCount;

    // Labels for HUD elements
    private Label labelTime;
    private Label labelBlastRadius;
    private Label labelConcurrentBombs;
    private Label labelRemainingEnemies;
    private Label labelAllEnemiesDefeated;
    private Player player;
    private Blast blast;

    // Icons (optional)
    private Texture textureBlastRadius, textureConcurrentBombs, textureEnemies;
    private Skin skin;

    /**
     * Constructor für das HUD. Fügt UI-Elemente hinzu, um Spielerstatistiken anzuzeigen.
     *
     * @param spriteBatch Der SpriteBatch, der zum Zeichnen des HUD verwendet wird.
     * @param font Die BitmapFont, die für die Textanzeige verwendet wird.
     */

    public Hud(SpriteBatch spriteBatch, BitmapFont font) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();

        //TODO get getters
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelTime = new Label("Time: 000", labelStyle);
        labelBlastRadius = new Label("Blast Radius: 0", labelStyle);
        labelConcurrentBombs = new Label("Concurrent Bombs: 0", labelStyle);
        labelRemainingEnemies = new Label("Enemies: 0", labelStyle);
        labelAllEnemiesDefeated = new Label("All enemies defeated! Exit unlocked.", labelStyle);
        labelAllEnemiesDefeated.setColor(Color.GREEN);
        labelAllEnemiesDefeated.setVisible(false); // Initially invisible

        // Define a table for layout
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.padTop(10);

        // Add labels to the table
        table.add(labelTime).expandX().left().padRight(50);
        table.add(labelBlastRadius).expandX().center().padRight(50);
        table.add(labelConcurrentBombs).expandX().right();
        table.row().padTop(10);
        table.add(labelRemainingEnemies).expandX().left().padRight(50);
        table.add(labelAllEnemiesDefeated).expandX().center();
        // Can add more elements as needed

        // Add the table to the stage
        stage = new Stage(new ScreenViewport());
        stage.addActor(table);
    }
    
    /**
     * Renders the HUD on the screen.
     * This uses a different OrthographicCamera so that the HUD is always fixed on the screen.
     */
    /*    Zeichnet das HUD auf dem Bildschirm
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }*/
    public void render() {
        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);
        // Start drawing
        spriteBatch.begin();
        // Draw the HUD elements
        font.draw(spriteBatch, "Press Esc to Pause!", 10, Gdx.graphics.getHeight() - 10);
        // Finish drawing
        spriteBatch.end();
    }
    
    /**
     * Resizes the HUD when the screen size changes.
     * This is called when the window is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }


    /**
     * Updates the HUD with the current game state.
     *
     * @param dt              The time elapsed since the last frame.
     * @param player          The player object to update.
     * @param remainingEnemies The number of remaining enemies.
     * @param blast           The blast object to get the blast radius.
     */
    public void update(float dt, de.tum.cit.ase.bomberquest.map.Player player, int remainingEnemies, Blast blast) {
        // Increment the time counter by the elapsed time
        timeCount += dt;
        // Convert the time counter to an integer for display
        int displayTime = (int) timeCount;
        // Update the time label with the formatted time
        labelTime.setText(String.format("Time: %03d", displayTime));

        // Update the blast radius label with the current blast radius
        labelBlastRadius.setText("Blast Radius: " + blast.getBlastRadius());
        // Update the concurrent bombs label with the current number of concurrent bombs
        labelConcurrentBombs.setText("Concurrent Bombs: " + player.getConcurrentBombs());
        // Update the remaining enemies label with the current number of remaining enemies
        labelRemainingEnemies.setText("Enemies: " + remainingEnemies);

        // Check if all enemies are defeated
        if (remainingEnemies <= 0) {
            // Show the "all enemies defeated" label if no enemies remain
            labelAllEnemiesDefeated.setVisible(true);
        } else {
            // Hide the "all enemies defeated" label if there are still enemies
            labelAllEnemiesDefeated.setVisible(false);
        }
    }


}
