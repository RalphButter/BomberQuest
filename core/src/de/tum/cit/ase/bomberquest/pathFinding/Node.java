package de.tum.cit.ase.bomberquest.pathFinding;

/**
 * Represents a single node in a pathfinding grid.
 * Used in pathfinding algorithms (e.g., A* search) to store information
 * about the node's position, cost values, and parent reference for path reconstruction.
 */
public class Node {

    // Cost from the start node to this node
    private float g;

    // Heuristic cost estimate from this node to the goal
    private float h;

    // The parent node used to reconstruct the path
    private Node parent;

    // Grid position of the node
    private int row, col;

    // Distance value used in algorithms like Dijkstra
    private float distance;

    /**
     * Constructs a Node with a given row and column position.
     * Initializes pathfinding-related values.
     *
     * @param row    The row index of this node in the grid.
     * @param col    The column index of this node in the grid.
     * @param parent The parent node used for path reconstruction.
     */
    Node(int row, int col, Node parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;

        g = 0; // Initially, no cost from the start node
        h = 0; // Initially, no heuristic cost assigned
        distance = Integer.MAX_VALUE; // Set distance to a large value for algorithms like Dijkstra
    }

    /**
     * Returns the total estimated cost (F) for A* pathfinding.
     * F = G (cost from start) + H (estimated cost to goal).
     *
     * @return The total cost.
     */
    public float getF() {
        return g + h;
    }

    /**
     * Gets the cost from the start node to this node (G-cost).
     *
     * @return The G-cost value.
     */
    public float getG() {
        return g;
    }

    /**
     * Sets the G-cost (cost from the start node to this node).
     *
     * @param g The new G-cost value.
     */
    public void setG(float g) {
        this.g = g;
    }

    /**
     * Gets the estimated cost from this node to the goal (H-cost).
     *
     * @return The H-cost value.
     */
    public float getH() {
        return h;
    }

    /**
     * Sets the H-cost (estimated cost from this node to the goal).
     *
     * @param h The new H-cost value.
     */
    public void setH(float h) {
        this.h = h;
    }

    /**
     * Gets the parent node used to reconstruct the path.
     *
     * @return The parent node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets the parent node for path reconstruction.
     *
     * @param parent The new parent node.
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Gets the row index of this node in the grid.
     *
     * @return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of this node in the grid.
     *
     * @param row The new row index.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column index of this node in the grid.
     *
     * @return The column index.
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column index of this node in the grid.
     *
     * @param col The new column index.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Gets the distance value used for pathfinding algorithms like Dijkstra.
     *
     * @return The distance value.
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets the distance value for algorithms like Dijkstra.
     *
     * @param distance The new distance value.
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * Checks if this node is equal to another node based on grid position.
     *
     * @param n The node to compare with.
     * @return True if both nodes have the same row and column, false otherwise.
     */
    public boolean equal(Node n) {
        return n.row == row && n.col == col;
    }
}
