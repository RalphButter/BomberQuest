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

    //TODO KONSTRUTUR ANGLEICHEN
    public Enemy(Vector2 pos, Map map) {
        super(pos, null);
        // Initialize the sprite sheet for the enemy with the texture "enemy.png", 1 row, and 3 columns
        sheet = new SpriteSheet(new Texture("texture/enemy.png"), 1, 3);
        // Set the sprite sheet to play the animation from frame 0 to frame 2 with a frame duration of 0.1 seconds, looping
        sheet.setPlay(0, 2, 0.1f, true);

        // Initialize the pathfinding system with the current map
        pathFinding = new PathFinding(map);
        // Get a random empty cell from the map to set as the target for the enemy
        GameObject randomGameObject = pathFinding.getMap().getRandomEmptyCell();
        // Find a path from the enemy's current position to the target cell
        path = pathFinding.findPath((int) pos.x / 16, (int) pos.y / 16, randomGameObject.col, randomGameObject.row);

        // Iterate through the path (currently does nothing, but can be used for debugging or future logic)
        for (Vector2 v : path) {
        }

        // Set the current target index to 0, indicating the start of the path
        currentTargetIndex = 0;
        // Set the enemy's movement speed
        speed = 0.25F;
        // Initialize the target position to the enemy's current position
        target = new Vector2(pos.x, pos.y);
        // Initialize the velocity vector to zero
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
        // Call the update method of the superclass
        super.update();
        // Check if the enemy has reached the current target
        if (Vector2.dst(pos.x, pos.y, target.x, target.y) <= 0.5) {
            // Move to the next target in the path
            currentTargetIndex += 1;
            // If the end of the path is reached, find a new path
            if (currentTargetIndex >= path.size()) {
                GameObject randomGameObject = pathFinding.getMap().getRandomEmptyCell();
                path = pathFinding.findPath((int) pos.x / 16, (int) pos.y / 16, randomGameObject.col, randomGameObject.row);
                currentTargetIndex = 0;
                return;
            }
            // Update the target to the next position in the path
            target = path.get(currentTargetIndex);
            target = new Vector2(target.x * 16, target.y * 16);
            // Calculate the direction and velocity towards the new target
            Vector2 dir = new Vector2(target.x - pos.x, target.y - pos.y).nor();
            vel = new Vector2(dir.x * speed, dir.y * speed);
        }
        // Update the position of the enemy
        pos = pos.add(vel);
        // Update the scale for the sprite based on the direction of movement
        if (vel.x < 0) {
            scaleX = -1;
        } else {
            scaleX = 1;
        }
        // Play the sprite sheet animation
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