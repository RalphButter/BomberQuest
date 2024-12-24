package de.tum.cit.ase.bomberquest.Characters;

import java.util.*;

/**
 * The {@code Pathfinder} class implements multiple pathfinding algorithms:
 * BFS, DFS, Dijkstra, and A*.
 * <p>
 * All algorithms operate on an integer grid where each cell can be represented
 * by its coordinates (row, column).
 * <p>
 * Enemies can use this class to find paths towards the player's position using a chosen algorithm.
 */

public class Pathfinding {

    // The grid, where grid[r][c] represents the cell at row r, column c.
    // Typically, 0 = walkable, 1 = obstacle/wall
    private int[][] grid;

    /**
     * Constructs a new Pathfinder given a 2D grid.
     *
     * @param grid The 2D array representing the game map or terrain.
     */
    public Pathfinding(int[][] grid) {
        this.grid = grid;
    }

    /**
     * Finds a path from a start position to a goal position using the specified algorithm.
     *
     * @param start     An int[] of size 2 representing the starting [row, col].
     * @param goal      An int[] of size 2 representing the goal [row, col].
     * @param algorithm A string specifying the pathfinding algorithm ("BFS", "DFS", "Dijkstra", or "A*").
     * @return A List of int[] representing the path from start to goal, or an empty list if no path is found.
     */




}
