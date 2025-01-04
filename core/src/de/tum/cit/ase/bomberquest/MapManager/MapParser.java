package de.tum.cit.ase.bomberquest.MapManager;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Iterate over each line in the input map data
        for (String line : mapLines) {
            // Split the line into coordinates and object ID based on the '=' delimiter
            String[] parts = line.split("=");
            if (parts.length != 2) {
                // If the line doesn't have exactly two parts, it's malformed; skip it
                System.out.println("Malformed line: " + line);
                continue;
            }

            // Split the coordinates part into x and y based on the ',' delimiter
            String[] coords = parts[0].split(",");
            if (coords.length != 2) {
                // If the coordinates part doesn't have exactly two values, it's malformed; skip it
                System.out.println("Malformed coordinates: " + parts[0]);
                continue;
            }

            try {
                // Parse x and y coordinates as integers
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());

                // Parse the object ID as an integer
                int objectId = Integer.parseInt(parts[1].trim());

                // Create a Point object with the parsed coordinates
                Point point = new Point(x, y);

                // Add the Point and its corresponding object ID to the objects map
                objects.put(point, objectId);
            } catch (NumberFormatException e) {
                // Handle cases where parsing integers fails
                System.out.println("Invalid number format in line: " + line);
                continue;
            }
        }

        // After populating objects, calculate the map's width and height
        calculateDimensions();

        return objects;
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
        final int ENTRY_ID = 1; // Define the object ID that represents the entry point

        // Iterate through each entry in the objects map
        for (Map.Entry<Point, Integer> mapEntry : objects.entrySet()) {
            // Check if the current object's ID matches the ENTRY_ID
            if (mapEntry.getValue().equals(ENTRY_ID)) {
                // If a match is found, set entry to the current Point
                entry = mapEntry.getKey();
                break; // Exit the loop once the entry point is found
            }
        }

        if (entry != null) {
            // If the entry point is found, scale its coordinates by 16
            // This is likely to convert grid coordinates to pixel coordinates
            entry = new Point(entry.x * 16, entry.y * 16);
        } else {
            // If no entry point is found, print an error message
            System.out.println("Entry point not found in the map!");
        }
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
    public static void reset() {
        // Clear all entries from the objects map
        objects.clear();

        // Reset map dimensions to default values
        width = 0;
        height = 0;

        // Reset the entry point to null
        entry = null;

        // Add any additional reset logic here if other static variables are introduced in the future
    }

}
