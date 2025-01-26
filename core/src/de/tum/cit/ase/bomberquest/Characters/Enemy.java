package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.pathFinding.PathFinding;
import de.tum.cit.ase.bomberquest.util.Rectangle;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;
import com.badlogic.gdx.graphics.g2d.Batch;


import java.util.List;

//Todo Enemy angleichen an Cell und Map Logik
public class Enemy extends Entity {


    //    Pathfinding needs to be implemented
    SpriteSheet sheet;
    PathFinding pathFinding;
    List<Vector2> path;
    Vector2 target;
    Vector2 vel;
    float speed;
    int currentTargetIndex;
    int scaleX = 1;
    float timeSinceLastPathUpdate = 0;

    Player player;

    //TODO KONSTRUTUR ANGLEICHEN
    public Enemy(Vector2 pos, Map map, Player player) {
        super(pos, null);
        sheet = new SpriteSheet(new Texture("enemy.png"), 1, 3);
        sheet.setPlay(0, 2, 0.1f, true);

        pathFinding = new PathFinding(map);

        this.player = player;

        // Instead of random cell, get the player's row and column
        int playerCol = (int)(player.pos.x / 16);
        int playerRow = (int)(player.pos.y / 16);

        // Now create path to the player's location
        path = pathFinding.findPath(
                (int) pos.x / 16, (int) pos.y / 16,
                playerCol, playerRow
        );

        currentTargetIndex = 0;
        speed = 0.25f;
        target = new Vector2(pos.x, pos.y);
        vel = new Vector2(0, 0);
    }



    @Override
    public Rectangle getRect() {
        // Create and return a new Rectangle representing the bounding box of the enemy
        // The rectangle is slightly smaller than the sprite to allow for better collision detection
        return new Rectangle(pos.x + 2, pos.y + 2, sheet.getWidth() - 4, sheet.getHeight() / 2 - 4);
    }


    @Override
    public void update() {
        super.update();

        // increment your timer
        timeSinceLastPathUpdate += com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        // Recalculate path every 1 second, for example
        if (timeSinceLastPathUpdate >= 1.0f) {
            timeSinceLastPathUpdate = 0;

            // Get player's row/col
            int playerCol = (int)(player.pos.x / 16);
            int playerRow = (int)(player.pos.y / 16);

            // Re-do path:
            path = pathFinding.findPath(
                    (int) pos.x / 16, (int) pos.y / 16,
                    playerCol, playerRow
            );
            currentTargetIndex = 0;
        }

        // Then proceed with your existing target-based movement
        if (Vector2.dst(pos.x, pos.y, target.x, target.y) <= 0.5) {
            currentTargetIndex++;
            if (currentTargetIndex >= path.size()) {
                // If you want them to stand still after reaching player,
                // do so. Or recalc path again, etc.
                return;
            }
            target = path.get(currentTargetIndex).scl(16);
            Vector2 dir = new Vector2(target.x - pos.x, target.y - pos.y).nor();
            vel = dir.scl(speed);
        }

        pos.add(vel);
        scaleX = vel.x < 0 ? -1 : 1;
        sheet.play();
    }


    @Override
    public void draw(Batch batch) {
        // Begin the batch for drawing
        batch.begin();
        // Get the current frame of the sprite sheet
        TextureRegion t = sheet.getCurrentFrame();
        // Draw the current frame at the entity's position with scaling and rotation
        batch.draw(t, pos.x, pos.y, sheet.getWidth() / 2, sheet.getHeight() / 2, sheet.getWidth(), sheet.getHeight(), scaleX, 1, 0);
        // End the batch after drawing
        batch.end();
    }

}