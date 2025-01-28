package de.tum.cit.ase.bomberquest.gameobject;

/**
 * The CellType enum defines various types of cells that can exist in the game.
 * Each cell type represents a different element or object within the game,
 * such as walls, enemies, power-ups, and entrances/exits.
 *
 * The fromId method allows converting an integer ID to the corresponding
 * CellType enum value. This is useful for mapping data (e.g., from a file
 * or network) to the appropriate game elements.
 *
 * The CellType enum defines various types of cells that can exist in the game.
 * Each cell type represents a different element or object within the game,
 * such as walls, enemies, power-ups, and entrances/exits.
 *
 * The fromId method allows converting an integer ID to the corresponding
 * CellType enum value. This is useful for mapping data (e.g., from a file
 * or network) to the appropriate game elements.
 */


//Wie besprochen müssen wir wohl oder übel unser Projekt umstrukturieren, um den Load Fehler zu umgehen.
public enum CellType {
    // Enum constants representing different types of cells in the game
    INDESTRUCTIBLE_WALL, // A wall that cannot be destroyed
    DESTRUCTIBLE_WALL,   // A wall that can be destroyed
    EXIT,                // The exit point of the game
    ENTRANCE,            // The entrance point of the game
    ENEMY,               // An enemy character
    KEY,                 // A key that can be collected
    CONCURRENT_PU,       // A power-up for concurrent actions
    BLAST_RADIUS_PU;     // A power-up that increases blast radius

    /**
     * Converts an integer ID to the corresponding CellType enum value.
     * This is useful for mapping data (e.g., from a file or network) to the appropriate game elements.
     *
     * @param x The integer ID representing a cell type.
     * @return The corresponding CellType enum value, or null if the ID is invalid.
     */
    public static CellType fromId(int x) {
        switch(x) {
            case 0:
                return INDESTRUCTIBLE_WALL;
            case 1:
                return DESTRUCTIBLE_WALL;
            case 2:
                return ENTRANCE;
            case 3:
                return ENEMY;
            case 4:
                return EXIT;
            case 5:
                return CONCURRENT_PU;
            case 6:
                return BLAST_RADIUS_PU;
            default:
                return null; // Return null if the ID does not match any CellType
        }
    }


}
