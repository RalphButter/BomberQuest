package de.tum.cit.ase.bomberquest.pathFinding;

import java.util.*;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.gameobject.CellType;
import de.tum.cit.ase.bomberquest.MapManager.Map;

public class PathFinding {

    // The map on which pathfinding is performed
    private Map map;

    // Lists for A* algorithm
    List<Node> open_list;  // Nodes to be explored
    List<Node> close_list; // Nodes already explored

    // Start and end nodes for pathfinding
    Node startNode;
    Node endNode;

    /**
     * Constructor for PathFinding.
     *
     * @param map The game map on which pathfinding will be performed.
     */
    public PathFinding(Map map) {
        this.map = map;
    }

    /**
     * Returns the game map.
     *
     * @return The game map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Checks if a node is in the open list.
     *
     * @param node The node to check.
     * @return The node if found in the open list, otherwise null.
     */
    Node isInOpenList(Node node) {
        for (Node n : open_list) {
            if (n.equal(node)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Checks if a node is in the close list.
     *
     * @param row The row of the node to check.
     * @param col The column of the node to check.
     * @return The node if found in the close list, otherwise null.
     */
    Node isInCloseList(int row, int col) {
        for (Node n : close_list) {
            if (n.getRow() == row && n.getCol() == col) {
                return n;
            }
        }
        return null;
    }

    /**
     * Finds the index of the node in the open list with the minimum F value.
     * F = G (cost to reach this node) + H (estimated cost to reach the end node).
     *
     * @return The index of the node with the minimum F value.
     */
    int findMinFNode() {
        float min = Float.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < open_list.size(); i++) {
            if (open_list.get(i).getF() < min) {
                min = open_list.get(i).getF();
                index = i;
            }
        }
        return index;
    }

    /**
     * Explores the neighbors of a given node.
     * Neighbors are the cells to the top, bottom, left, and right of the current node.
     *
     * @param node The node whose neighbors are to be explored.
     */
    void exploreNeighbour(Node node) {
        // Directions for exploring neighbors: up, down, left, right
        int[] dir_row = { -1, 1, 0, 0 };
        int[] dir_col = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int new_row = dir_row[i] + node.getRow();
            int new_col = dir_col[i] + node.getCol();

            // Skip if the neighbor is outside the map boundaries
            if (new_row > map.getRows() - 1 || new_row < 0 || new_col > map.getCols() - 1 || new_col < 0) {
                continue;
            }

            // Skip if the neighbor is an obstacle (e.g., a wall)
            if (map.getCell(new_row, new_col).cellType == CellType.INDESTRUCTIBLE_WALL ||
                    map.getCell(new_row, new_col).cellType == CellType.DESTRUCTIBLE_WALL ||
                    map.getCell(new_row, new_col).cellType == CellType.BLAST_RADIUS_PU ||
                    map.getCell(new_row, new_col).cellType == CellType.CONCURRENT_PU) {
                continue;
            }

            // Skip if the neighbor is already in the close list
            if (isInCloseList(new_row, new_col) != null) {
                continue;
            }

            // Create a new node for the neighbor
            Node new_node = new Node(new_row, new_col, node);

            // Check if the neighbor is already in the open list
            if (isInOpenList(new_node) != null) {
                new_node = isInOpenList(new_node);
            }

            // Calculate the cost to reach this neighbor
            float path_to_this_neighbor = node.getG() + 1;

            // If this path is better than the previous one, update the neighbor's values
            if (path_to_this_neighbor < new_node.getG() || isInOpenList(new_node) == null) {
                new_node.setG(path_to_this_neighbor); // Update the cost to reach this node
                new_node.setH(grid_distance(new_node, endNode)); // Update the heuristic (estimated cost to the end node)

                // Add the neighbor to the open list if it's not already there
                if (isInOpenList(new_node) == null) {
                    open_list.add(new_node);
                }
            }
        }
    }

    /**
     * Calculates the heuristic distance between two nodes.
     * This is an estimate of the cost to reach the end node from the current node.
     *
     * @param a The current node.
     * @param b The end node.
     * @return The heuristic distance between the two nodes.
     */
    static float grid_distance(Node a, Node b) {
        int dst_x = Math.abs(a.getCol() - b.getCol());
        int dst_y = Math.abs(a.getRow() - b.getRow());

        // Diagonal distance approximation
        if (dst_x > dst_y) {
            return 5 * dst_y + 3 * (dst_x - dst_y);
        }
        return 5 * dst_x + 3 * (dst_y - dst_x);
    }

    /**
     * Constructs the path from the end node back to the start node.
     *
     * @param node The end node.
     * @return A list of Vector2 objects representing the path.
     */
    List<Vector2> showPath(Node node) {
        Node tmp = node;
        List<Node> tracepath = new ArrayList<>();
        List<Vector2> path = new ArrayList<>();

        // Trace the path from the end node to the start node
        while (tmp != null) {
            tracepath.add(tmp);
            tmp = tmp.getParent();
        }

        // Reverse the path to get it from start to end
        for (int i = tracepath.size() - 1; i >= 0; i--) {
            Node n = tracepath.get(i);
            path.add(new Vector2(n.getCol(), n.getRow()));
        }

        // Remove the start node from the path (since the enemy is already there)
        if (path.size() > 0) {
            path.remove(0);
        }

        return path;
    }

    /**
     * Finds a path from the start position to the end position using the A* algorithm.
     *
     * @param col   The column of the start position.
     * @param row   The row of the start position.
     * @param toCol The column of the end position.
     * @param toRow The row of the end position.
     * @return A list of Vector2 objects representing the path, or an empty list if no path is found.
     */
    public List<Vector2> findPath(int col, int row, int toCol, int toRow) {
        // Initialize start and end nodes
        startNode = new Node(row, col, null);
        endNode = new Node(toRow, toCol, null);

        // Initialize open and close lists
        open_list = new ArrayList<>();
        close_list = new ArrayList<>();

        // Add the start node to the open list
        open_list.add(startNode);

        // Main A* loop
        while (!open_list.isEmpty()) {
            // Find the node with the minimum F value in the open list
            int i_min = findMinFNode();
            Node currentNode = open_list.get(i_min);

            // Move the current node from the open list to the close list
            close_list.add(currentNode);

            // If the current node is the end node, return the path
            if (currentNode.equal(endNode)) {
                return showPath(currentNode);
            }

            // Remove the current node from the open list
            open_list.remove(i_min);

            // Explore the neighbors of the current node
            exploreNeighbour(currentNode);
        }

        // If no path is found, return an empty list
        return new ArrayList<>();
    }
}