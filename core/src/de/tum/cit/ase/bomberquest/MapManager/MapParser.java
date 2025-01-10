package de.tum.cit.ase.bomberquest.MapManager;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapParser {
    private static int width;
    private static int height;
    private static Map<Point, Integer> objects = new HashMap<>();
    private static Point entry;


    /**
     * Parses the map data from a list of string lines into a map of Points to object IDs.
     *
     * Each line in the input list should be in the format "x,y=objectId".
     * For example, "10,20=3" means that at coordinates (10, 20), there is an object with ID 3.
     *
     * @param mapLines The list of strings representing lines from a map file.
     * @return A Map where the key is a Point (x, y coordinates) and the value is an object ID.
     */
    public static Map<Point, Integer> parseMap(List<String> mapLines) {
        for (String line : mapLines) {
            line = line.trim(); // Remove leading and trailing whitespace
            if (line.isEmpty() || line.startsWith("#")) {
                // Skip blank lines and comments
                continue;
            }

            String[] parts = line.split("=");
            if (parts.length != 2) {
                System.out.println("Malformed line: " + line);
                continue;
            }

            String[] coords = parts[0].split(",");
            if (coords.length != 2) {
                System.out.println("Malformed coordinates: " + parts[0]);
                continue;
            }

            try {
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());
                int objectId = Integer.parseInt(parts[1].trim());
                objects.put(new Point(x, y), objectId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in line: " + line);
            }
        }

        calculateDimensions();
        return objects;
    }


    public static void initializeMap(List<String> mapLines) {
        parseMap(mapLines); // Parse the map
        calculateDimensions(); // Calculate map dimensions
        findEntry(); // Find the entry point
        ensureExitExists(); // Ensure there is an exit
        if (!validateOuterBoundaries()) { // Validate outer boundaries
            throw new IllegalStateException("Outer boundaries must consist of indestructible walls!");
        }
    }



    /**
     * Calculates the dimensions of the map based on the points in objects.
     * The width is determined by the maximum x-coordinate plus one.
     * The height is determined by the maximum y-coordinate plus one.
     */
    private static void calculateDimensions() {
        // Calculate the maximum x-coordinate among all points
        width = objects.keySet().stream()
                .mapToInt(p -> p.x)
                .max()
                .orElse(0) + 1; // Add 1 to account for zero-based indexing

        // Calculate the maximum y-coordinate among all points
        height = objects.keySet().stream()
                .mapToInt(p -> p.y)
                .max()
                .orElse(0) + 1; // Add 1 to account for zero-based indexing
    }

    /**
     * Finds the entry point in the map, which is defined by a specific object ID.
     * The default ENTRY_ID is set to 1, but this can be adjusted as needed.
     *
     * If an entry point is found, its coordinates are scaled (multiplied by 16)
     * to position the player appropriately in the game environment.
     *
     * If no entry point is found, a message is printed to indicate the absence.
     */
    public static void findEntry() {
        final int ENTRY_ID = 1;

        // Find the entry point in the map
        for (Map.Entry<Point, Integer> mapEntry : objects.entrySet()) {
            if (mapEntry.getValue().equals(ENTRY_ID)) {
                entry = new Point(mapEntry.getKey().x * 16, mapEntry.getKey().y * 16); // Scale to pixel coordinates
                return;
            }
        }

        // If no entry is found, throw an exception or log an error
        throw new IllegalStateException("Entry point not found in the map! Ensure ENTRY_ID (1) is defined.");
    }


    /**
     * Overrides the default hashCode method.
     *
     * Note: This implementation currently uses the superclass's hashCode method.
     * If MapParser instances are to be used in hash-based collections, consider
     * implementing a proper hashCode based on relevant fields.
     *
     * @return The hash code of the MapParser instance.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Overrides the default equals method.
     *
     * Note: This implementation currently uses the superclass's equals method.
     * If MapParser instances need to be compared based on their fields, implement
     * a proper equals method.
     *
     * @param obj The object to compare with the current instance.
     * @return true if the objects are considered equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Retrieves the width of the map.
     *
     * @return The width of the map as an integer.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Sets the width of the map.
     *
     * @param width The width value to set.
     */
    public static void setWidth(int width) {
        MapParser.width = width;
    }

    /**
     * Retrieves the height of the map.
     *
     * @return The height of the map as an integer.
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Sets the height of the map.
     *
     * @param height The height value to set.
     */
    public static void setHeight(int height) {
        MapParser.height = height;
    }

    /**
     * Retrieves the map objects.
     *
     * @return A Map where the key is a Point (x, y coordinates) and the value is an object ID.
     */
    public static Map<Point, Integer> getObjects() {
        return objects;
    }

    /**
     * Retrieves the entry point of the map.
     *
     * @return The entry Point object, or null if not set.
     */
    public static Point getEntry() {
        return entry;
    }

    /**
     * Resets the map by clearing all map objects and resetting other static variables if necessary.
     *
     * This method is useful for reinitializing the map data, for example, when loading a new map.
     */
    public static void resetMap() {
        // Clear all entries from the objects map
        objects.clear();

        // Reset map dimensions to default values
        width = 0;
        height = 0;

        // Reset the entry point to null
        entry = null;

        // Add any additional reset logic here if other static variables are introduced in the future
    }

    public static void setObjects(Map<Point, Integer> objects) {
        MapParser.objects = objects;
    }

    public static void setEntry(Point entry) {
        MapParser.entry = entry;
    }

    public static void ensureExitExists() {
        final int EXIT_ID = 4; // Exit ID
        if (objects.values().contains(EXIT_ID)) {
            return; // Exit already exists
        }

        // Filter destructible walls (ID 1) and pick a random one
        List<Point> destructibleWalls = objects.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .toList();

        if (!destructibleWalls.isEmpty()) {
            Point randomWall = destructibleWalls.get(new Random().nextInt(destructibleWalls.size()));
            objects.put(randomWall, EXIT_ID); // Replace the wall with an exit
            System.out.println("Added exit under destructible wall at: " + randomWall);
        } else {
            System.err.println("No destructible walls available to place an exit!");
        }
    }

    public static boolean validateOuterBoundaries() {
        for (int x = 0; x < width; x++) {
            if (objects.getOrDefault(new Point(x, 0), -1) != 0 ||
                    objects.getOrDefault(new Point(x, height - 1), -1) != 0) {
                return false; // Bottom or top boundary is invalid
            }
        }

        for (int y = 0; y < height; y++) {
            if (objects.getOrDefault(new Point(0, y), -1) != 0 ||
                    objects.getOrDefault(new Point(width - 1, y), -1) != 0) {
                return false; // Left or right boundary is invalid
            }
        }
        return true;
    }


}
