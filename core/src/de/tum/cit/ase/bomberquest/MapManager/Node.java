package de.tum.cit.ase.bomberquest.MapManager;


//NODE GGF BEI ALLEN AUF WALKABLE TESTEN
/**
 * Represents a node in the grid, primarily used for pathfinding.
 * Each node corresponds to a specific point in the game world and tracks pathfinding costs.
 */

public class Node {
    private int x, y; // The x and y coordinates of the node in the grid
    private boolean walkable; // Flag indicating whether the node is walkable or blocked
    private int gCost; // Cost from the start node to this node
    private int fCost; // gCost + heuristic (used for A* algorithm)
    private int[] position;

    /**
     * Constructor for creating a new Node with specified coordinates and walkability.
     *
     * @param x        The x-coordinate of the node.
     * @param y        The y-coordinate of the node.
     * @param walkable Indicates whether the node is walkable (true) or not (false).
     */
    public Node(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.gCost = Integer.MAX_VALUE; // Initialize gCost to a high value (infinity)
        this.fCost = Integer.MAX_VALUE; // Initialize fCost to a high value (infinity)
    }

    /**
     * Constructor for creating a new Node with specified coordinates and costs.
     *
     * @param x     The x-coordinate of the node.
     * @param y     The y-coordinate of the node.
     * @param gCost The cost from the start node to this node.
     * @param fCost The total cost (gCost + heuristic) for pathfinding.
     */
    public Node(int x, int y, int gCost, int fCost) {
        this.x = x;
        this.y = y;
        this.gCost = gCost;
        this.fCost = fCost;
    }

    /**
     * Constructor for creating a new Node with specified position and costs.
     *
     * @param position The position of the node as an array [x, y].
     * @param gCost    The cost from the start node to this node.
     * @param fCost    The total cost (gCost + heuristic) for pathfinding.
     */
    public Node(int[] position, int gCost, int fCost) {
        this.x = position[0];
        this.y = position[1];
        this.gCost = gCost;
        this.fCost = fCost;
        this.position = position;
    }


    // Getters and setters for node properties

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getFCost() {
        return fCost;
    }

    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }
}
