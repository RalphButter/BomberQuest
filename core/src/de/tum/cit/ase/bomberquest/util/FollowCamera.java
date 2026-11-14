package de.tum.cit.ase.bomberquest.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import java.util.Vector;

/**
 * The {@code FollowCamera} class is responsible for ensuring that the camera follows the player
 * while keeping the player within the visible bounds of the game world.
 * It dynamically adjusts the camera position based on the player's location.
 */
public class FollowCamera {
    private final OrthographicCamera camera; // The camera used to render the game world.
    private final Map map; // Reference to the game map to enforce camera boundaries.

    /**
     * Constructs a FollowCamera that follows the player and ensures the camera does not go out of bounds.
     *
     * @param camera The {@code OrthographicCamera} used for rendering the game world.
     * @param map The {@code Map} object representing the game world.
     */
    public FollowCamera(OrthographicCamera camera, Map map) {
        this.camera = camera;
        this.map = map;
        camera.zoom = 0.2f; // Set the zoom level to provide an appropriate view of the map.
    }

    /**
     * Updates the camera's position to follow the target while keeping it within the bounds of the map.
     *
     * @param target The {@code Vector2} position of the target (e.g., the player's location).
     */
    public void follow(Vector2 target) {
        camera.position.x = target.x;
        camera.position.y = target.y;

        float centerX = camera.viewportWidth * camera.zoom * 0.5f;
        float centerY = camera.viewportHeight * camera.zoom * 0.5f;

        // Ensure the camera does not move beyond the top and bottom boundaries of the map
        if (camera.position.y < centerY) {
            camera.position.y = centerY;
        } else if (camera.position.y > map.getRows() * 16 - centerY) {
            camera.position.y = map.getRows() * 16 - centerY;
        }

        // Ensure the camera does not move beyond the left and right boundaries of the map
        if (camera.position.x < centerX) {
            camera.position.x = centerX;
        } else if (camera.position.x > map.getCols() * 16 - centerX) {
            camera.position.x = map.getCols() * 16 - centerX;
        }
    }
}
