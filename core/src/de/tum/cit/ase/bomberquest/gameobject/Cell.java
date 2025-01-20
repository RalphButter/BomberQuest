package de.tum.cit.ase.bomberquest.gameobject;

import de.tum.cit.ase.bomberquest.gameobject.CellType;
import de.tum.cit.ase.bomberquest.util.Rectangle;


// The CellType represents the type of the cell in the game grid.
// It can have various values indicating different types of cells, such as:
// - INDESTRUCTIBLE_WALL: A wall that cannot be destroyed.
// - DESTRUCTIBLE_WALL: A wall that can be destroyed by the player or other game mechanics.
// - ENTRANCE: The starting point or entrance in the game level.
// - EXIT: The exit point or goal in the game level.
// - ENEMY: A cell that contains an enemy.
// - EMPTY: A cell that is empty and can be traversed by the player.

public class Cell {
    // Row position of the cell in the grid
    public int row;
    // Column position of the cell in the grid
    public int col;

    // Type of the cell (e.g., wall, empty, entrance, etc.)
    public CellType cellType;

    // Constructor to initialize the cell with its row, column, and type
    public Cell(int row, int col, CellType cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
    }

    // Method to get the bounding rectangle of the cell
    public Rectangle getRect(){
        return new Rectangle(col * 16, row * 16, 16, 16);
    }
}