package de.tum.cit.ase.bomberquest.Characters;

import de.tum.cit.ase.bomberquest.MapManager.Node;

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
    public List<int[]> findPath(int[] start, int[] goal, String algorithm) {
        switch (algorithm) {
            case "BFS":
                return bfs(start, goal);
            case "DFS":
                return dfs(start, goal);
            case "Dijkstra":
                return dijkstra(start, goal);
            case "A*":
                return aStar(start, goal);
            default:
                throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
        }
    }

    /**
     * Performs a Breadth-First Search (BFS) from start to goal.
     * <p>
     * BFS explores equally in all directions and guarantees the shortest path in an unweighted grid.
     *
     * @param start The starting node [row, col].
     * @param goal  The target node [row, col].
     * @return A list of nodes representing the path, or an empty list if no path exists.
     */

    private List<int[]> bfs(int[] start, int[] goal) {
        Queue<int[]> queue = new LinkedList<>();
        Map<String, int[]> cameFrom = new HashMap<>();

        queue.add(start);
        cameFrom.put(Arrays.toString(start), null); // Start has no predecessor

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            // Goal test
            if (Arrays.equals(current, goal)) {
                return reconstructPath(cameFrom, current);
            }

            for (int[] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.toString(neighbor);
                if (!cameFrom.containsKey(neighborKey)) {
                    cameFrom.put(neighborKey, current);
                    queue.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    /**
     * Performs the A* search algorithm to find an optimal path using a heuristic.
     * <p>
     * A* combines Dijkstra’s Algorithm with heuristics to efficiently find the shortest path.
     *
     * @param start The starting node [row, col].
     * @param goal  The target node [row, col].
     * @return A list of nodes representing the path, or an empty list if no path exists.
     */
    private List<int[]> aStar(int[] start, int[] goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFCost));
        Map<String, Integer> gCosts = new HashMap<>();
        Map<String, int[]> cameFrom = new HashMap<>();

        String startKey = Arrays.toString(start);
        gCosts.put(startKey, 0);
        openSet.add(new Node(start[0], start[1], 0, heuristic(start, goal)));
        cameFrom.put(startKey, null);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            int[] current = currentNode.getPosition();
            // Goal test
            if (Arrays.equals(current, goal)) {
                return reconstructPath(cameFrom, current);
            }

            for (int[] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.toString(neighbor);
                int tentativeGCost = gCosts.get(Arrays.toString(current)) + getMovementCost(neighbor);

                if (!gCosts.containsKey(neighborKey) || tentativeGCost < gCosts.get(neighborKey)) {
                    gCosts.put(neighborKey, tentativeGCost);
                    int fCost = tentativeGCost + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, tentativeGCost, fCost));
                    cameFrom.put(neighborKey, current);
                }
            }
        }

        return new ArrayList<>();
    }



    private List<int[]> dijkstra(int[] start, int[] goal) {
        // Implementation of Dijkstra's algorithm
        return null;
    }

    /**
     * Calculates the Manhattan distance heuristic between two nodes.
     * <p>
     * Suitable for grid-based paths with 4-directional movement.
     *
     * @param node Current node [row, col].
     * @param goal The goal node [row, col].
     * @return The Manhattan distance between node and goal.
     */
    private int heuristic(int[] node, int[] goal) {
        return Math.abs(node[0] - goal[0]) + Math.abs(node[1] - goal[1]);
    }

    /**
     * Retrieves the cost for moving into the given node.
     * In an unweighted grid, this may just be 1, or it can represent
     * different terrain costs if you want more complexity.
     *
     * @param node The node for which to get the movement cost.
     * @return The integer cost of moving into this node.
     */
    private int getMovementCost(int[] node) {
        // If grid[node[0]][node[1]] has some value indicating different terrain,
        // you could return that value. Default is 1 for unweighted grids.
        return 1;
    }




    /**
     * Performs a Depth-First Search (DFS) from start to goal.
     * <p>
     * DFS dives deep into one direction at a time. It does not guarantee the shortest path,
     * but can be useful for certain scenarios.
     *
     * @param start The starting node [row, col].
     * @param goal  The target node [row, col].
     * @return A list of nodes representing the path, or an empty list if no path exists.
     */
    private List<int[]> dfs(int[] start, int[] goal) {
        Stack<int[]> stack = new Stack<>();
        Map<String, int[]> cameFrom = new HashMap<>();

        stack.push(start);
        cameFrom.put(Arrays.toString(start), null);

        while (!stack.isEmpty()) {
            int[] current = stack.pop();

            // Goal test
            if (Arrays.equals(current, goal)) {
                return reconstructPath(cameFrom, current);
            }

            for (int[] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.toString(neighbor);
                if (!cameFrom.containsKey(neighborKey)) {
                    cameFrom.put(neighborKey, current);
                    stack.push(neighbor);
                }
            }
        }

        return new ArrayList<>();
    }



    /**
     * Reconstructs the path from the 'cameFrom' map once the goal is reached.
     * <p>
     * Backtracks from the goal node to the start node to form the path.
     *
     * @param cameFrom A map of node key -> the node from which it was reached.
     * @param current  The current node (usually the goal node).
     * @return The path as a list of [row, col], from start to goal.
     */
    private List<int[]> reconstructPath(Map<String, int[]> cameFrom, int[] current) {
        List<int[]> path = new LinkedList<>();
        String currentKey = Arrays.toString(current);

        while (current != null) {
            path.add(0, current); // Insert at the beginning to reverse the path
            current = cameFrom.get(currentKey);
            if (current != null) {
                currentKey = Arrays.toString(current);
            }
        }

        return path;
    }

    /**
     * Retrieves the valid neighbors for a given node in the grid.
     * <p>
     * Only considers 4-directional movement (up, down, left, right). Modify if diagonal movement is allowed.
     *
     * @param node Current node [row, col].
     * @return A list of valid adjacent [row, col] positions.
     */
    private List<int[]> getNeighbors(int[] node) {
        List<int[]> neighbors = new ArrayList<>();
        int r = node[0];
        int c = node[1];

        // Define movement directions: up, down, left, right
        int[][] directions = {
                {r - 1, c}, // up
                {r + 1, c}, // down
                {r, c - 1}, // left
                {r, c + 1}  // right
        };

        for (int[] dir : directions) {
            int nr = dir[0];
            int nc = dir[1];

            // Check if the new position is within grid boundaries
            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
                // Check if the cell is walkable (assuming 0 = walkable, 1 = obstacle)
                if (grid[nr][nc] == 0) {
                    neighbors.add(new int[]{nr, nc});
                }
            }
        }

        return neighbors;
    }












}
