package de.tum.cit.ase.bomberquest.pathFinding;

import de.tum.cit.ase.bomberquest.pathFinding.Node;

import java.util.*;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.gameobject.CellType;
import de.tum.cit.ase.bomberquest.MapManager.Map;



//!!! CHECK IF COLLISION AGAINST WALLS WORK: ENEMY SHOULDN'T RUN AGAINST A WALL PERMANENTLY
/**
 * The {@code Pathfinder} class implements multiple pathfinding algorithms:
 * BFS, DFS, Dijkstra, and A*.
 * <p>
 * All algorithms operate on an integer grid where each cell can be represented
 * by its coordinates (row, column).
 * <p>
 * Enemies can use this class to find paths towards the player's position using a chosen algorithm.
 */

public class PathFinding {

    // The grid, where grid[r][c] represents the cell at row r, column c.
    // Typically, 0 = walkable, 1 = obstacle/wall
    private int[][] grid;

    List<Node> open_list;
    List<Node> close_list;
    Map map;

    Node startNode;
    Node endNode;
    int distanceType = 0;

    /**
     * Constructs a new Pathfinding object with the given 2D grid.
     *
     * @param map The 2D array representing the game map or terrain.
     *             Each element in the array represents a cell in the grid,
     *             where typically 0 indicates a walkable cell and 1 indicates an obstacle.
     */
    public PathFinding(Map map) {
        this.map = map;
    }

    public Map getMap(){
        return map;
    }

    Node isInOpenList(Node node) {
        for (Node n : open_list) {
            if (n.equal(node)) {
                return n;
            }
        }
        return null;
    }
    Node isInCloseList(int row, int col) {
        for (Node n : close_list) {
            if (n.getRow() == row && n.getCol() == col) {
                return n;
            }
        }
        return null;
    }

    int findMinFNode() {
        float min = 999999999;
        // Node* minF_node = nullptr;
        int index = 0;
        for (int i = 0; i < open_list.size(); i++) {
            if (open_list.get(i).getF() < min) {
                min = open_list.get(i).getF();
                // minF_node = open_list[i];
                index = i;
            }
        }
        return index;
    }










}
