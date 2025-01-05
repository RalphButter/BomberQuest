package de.tum.cit.ase.bomberquest.MapManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

    /**
     * Reads a map file from the internal path and returns its contents as a list of strings.
     *
     * @param internalPath The relative path to the file in the internal storage of the game.
     * @return A List of strings where each entry represents a single line from the file.
     */

//The readMapFile method is a utility function for reading map files stored in the game's internal assets,
// processing their contents line by line, and returning the lines as a List<String>.
// It is robust and ensures proper resource management while gracefully handling I/O errors.
// This method is essential in a game for loading map layouts or configurations during initialization or level transitions.
    public static List<String> readMapFile(String internalPath) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;

        try {
            FileHandle fileHandle = Gdx.files.internal(internalPath);
            reader = new BufferedReader(new InputStreamReader(fileHandle.read()));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading map file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing reader: " + e.getMessage());
                }
            }
        }

        return lines;
    }
}