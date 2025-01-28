package de.tum.cit.ase.bomberquest.util;

/**
 * The {@code Manager} class is a singleton responsible for managing
 * the game's sound system through the {@code SoundsManager} class.
 *
 * <p> This class ensures that only one instance of the {@code Manager}
 * exists at any given time, preventing multiple instances of the
 * {@code SoundsManager} from being created, which could lead to
 * redundant or conflicting audio playback.
 *
 * <p> The {@code getInstance()} method provides global access to the
 * single {@code Manager} instance, ensuring consistent sound control
 * throughout the game.
 */
public class Manager {

    /**
     * The {@code SoundsManager} instance used to manage game sounds.
     * This handles playing background music and sound effects.
     */
    public SoundsManager soundsManager;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the {@code SoundsManager} instance when the {@code Manager}
     * is first created.
     */
    private Manager() {
        soundsManager = new SoundsManager(); // Initialize the sound manager
    }

    /**
     * A static reference to hold the single instance of the {@code Manager} class.
     * This follows the Singleton design pattern to ensure only one instance is created.
     */
    private static Manager instance = null;

    /**
     * Provides global access to the single {@code Manager} instance.
     * If the instance does not exist, it creates a new one; otherwise, it returns the existing instance.
     *
     * @return The single {@code Manager} instance.
     */
    public static Manager getInstance() {
        if (instance == null) { // Check if an instance already exists
            instance = new Manager(); // Create a new instance if none exists
        }
        return instance; // Return the singleton instance
    }
}
