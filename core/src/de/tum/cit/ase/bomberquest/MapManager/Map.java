package de.tum.cit.ase.bomberquest.MapManager;

//Basierend auf unserem Meeting, Samstag 18.01.2025
//Entry ist auch so redundant
//BITTE NICHT DIE REDUNDANTEN KLASSEN LÖSCHEN!!! (ICH BRAUCHE DIE GGF. NOCH)
//TODO Evtl. GameObject Klasse anpassen
//TODO DAVOR!!! SPRITE SHEET ERSTELLEN
//TODO ES müssen evtl. die anderen Klassen, die mit Gamobject zu tun haben auch geändert werden.

import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.util.List;
import java.util.Random;

/**
 * The Map class serves as the primary structure for managing the game's spatial environment.
 * It provides functionality to store, access, and manipulate game objects within a
 * two-dimensional grid-based representation of the game world. This class acts as a
 * centralized component for handling game elements, ensuring efficient organization
 * and interaction with various objects.
 *
 * Key Responsibilities:
 * - Storing game objects, which are instances of the GameObject class, within a grid structure.
 * - Providing methods to add, remove, and retrieve objects at specific coordinates.
 * - Managing spatial relationships between objects and handling collision or movement logic.
 * - Interfacing with external classes to load map data from files and initialize the environment.
 * - Supporting dynamic updates to the game world during runtime.
 *
 * Potential Redundancies:
 * The comprehensive functionality provided by the Map class can potentially make
 * the following classes redundant by centralizing their responsibilities:
 *
 * - GameObjectManager: If the Map class directly handles the lifecycle of objects,
 *   such as creation, deletion, and tracking, a separate manager might not be required.
 *
 * - Floor + Wall: If terrain or tile-related logic is fully integrated within the Map class,
 *   a dedicated Floor class may become unnecessary.
 *
 * - MapReader and MapParser: If the Map class incorporates efficient parsing and
 *   loading methods, these classes might be reduced to helper utilities or eliminated entirely.
 *
 * By consolidating these responsibilities, the Map class aims to simplify game development
 * by offering a single point of control for spatial management and object interactions.
 */


public class Map {
    int rows; // Number of rows in the map grid, essential for defining the map's dimensions
    int cols; // Number of columns in the map grid, essential for defining the map's dimensions
    GameObject testGameObject; // A test game object for development and debugging purposes
    SpriteSheet mapSheet; // The sprite sheet used for rendering the map tiles
    int cellSize = 16; // The size of each cell in the grid, used for positioning and scaling

    Vector2 pos; // The position of the map in the game world

    GameObject entryGameObject; // The entry point game object, marking the start position in the map
    GameObject exitGameObject; // The exit point game object, marking the end position in the map
    GameObject[][] grid; // A 2D array to store game objects within the map grid
    List<GameObject> emptyGameObjects; // A list of empty game objects for managing available positions
    Random random; // A random number generator for procedural content and random events
    int keyCount = 0; // A counter for tracking the number of keys collected or required
    Vector2 playerPos; // The position of the player within the map


    //Todo Wir brauchen noch eine draw + drawWall function
    //Todo Konstrutur (mache ich (Ralph))
    //TODO Wir müssen davor Enemy, etc. machen. Diese Klasse kann erst zum Schluss implementiert werden

}
