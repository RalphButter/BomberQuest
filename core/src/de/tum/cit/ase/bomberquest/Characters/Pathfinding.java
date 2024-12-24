package de.tum.cit.ase.bomberquest.Characters;

import de.tum.cit.ase.bomberquest.MapManager.Node;

import java.util.*;



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

public class Pathfinding {

    // The grid, where grid[r][c] represents the cell at row r, column c.
    // Typically, 0 = walkable, 1 = obstacle/wall
    private int[][] grid;

    /**
     * Constructs a new Pathfinding object with the given 2D grid.
     *
     * @param grid The 2D array representing the game map or terrain.
     *             Each element in the array represents a cell in the grid,
     *             where typically 0 indicates a walkable cell and 1 indicates an obstacle.
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
                // Use Breadth-First Search algorithm
                return bfs(start, goal);
            case "DFS":
                // Use Depth-First Search algorithm
                return dfs(start, goal);
            case "Dijkstra":
                // Use Dijkstra's algorithm
                return dijkstra(start, goal);
            case "A*":
                // Use A* algorithm
                return aStar(start, goal);
            default:
                // Throw an exception if the algorithm is not recognized
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
        // Queue to manage the nodes to be explored
        Queue<int[]> queue = new LinkedList<>();
        // Map to track the path from start to goal
        Map<String, int[]> cameFrom = new HashMap<>();

        // Initialize the queue with the start node
        queue.add(start);
        // Mark the start node as having no predecessor
        cameFrom.put(Arrays.toString(start), null);

        // Continue exploring until there are no more nodes to explore
        while (!queue.isEmpty()) {
            // Get the current node from the queue
            int[] current = queue.poll();

            // Check if the current node is the goal
            if (Arrays.equals(current, goal)) {
                // Reconstruct and return the path from start to goal
                return reconstructPath(cameFrom, current);
            }

            // Explore the neighbors of the current node
            for (int[] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.toString(neighbor);
                // If the neighbor has not been visited yet
                if (!cameFrom.containsKey(neighborKey)) {
                    // Mark the current node as the predecessor of the neighbor
                    cameFrom.put(neighborKey, current);
                    // Add the neighbor to the queue for further exploration
                    queue.add(neighbor);
                }
            }
        }

        // Return an empty list if no path is found
        return new ArrayList<>();
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
        // Priority queue to manage the open set of nodes to be explored, sorted by fCost
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFCost));
        // Map to track the gCost (cost from start to current node) for each node
        Map<String, Integer> gCosts = new HashMap<>();
        // Map to track the path from start to goal
        Map<String, int[]> cameFrom = new HashMap<>();

        // Initialize the start node
        String startKey = Arrays.toString(start);
        gCosts.put(startKey, 0);
        openSet.add(new Node(start[0], start[1], 0, heuristic(start, goal)));
        cameFrom.put(startKey, null);

        // Continue exploring until there are no more nodes to explore
        while (!openSet.isEmpty()) {
            // Get the node with the lowest fCost from the open set
            Node currentNode = openSet.poll();
            int[] current = currentNode.getPosition();

            // Check if the current node is the goal
            if (Arrays.equals(current, goal)) {
                // Reconstruct and return the path from start to goal
                return reconstructPath(cameFrom, current);
            }

            // Explore the neighbors of the current node
            for (int[] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.toString(neighbor);
                // Calculate the tentative gCost for the neighbor
                int tentativeGCost = gCosts.get(Arrays.toString(current)) + getMovementCost(neighbor);

                // If the neighbor is not in gCosts or the new gCost is lower, update the costs and path
                if (!gCosts.containsKey(neighborKey) || tentativeGCost < gCosts.get(neighborKey)) {
                    gCosts.put(neighborKey, tentativeGCost);
                    int fCost = tentativeGCost + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, tentativeGCost, fCost));
                    cameFrom.put(neighborKey, current);
                }
            }
        }

        // Return an empty list if no path is found
        return new ArrayList<>();
    }



    private List<int[]> dijkstra(int[] start, int[] goal) {
        // Priority queue to manage the nodes to be explored, sorted by fCost
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getFCost));
        // Map to track the cost from start to each node
        Map<String, Integer> costs = new HashMap<>();
        // Map to track the path from start to goal
        Map<String, int[]> cameFrom = new HashMap<>();

        // Initialize the start node
        String startKey = Arrays.toString(start);
        costs.put(startKey, 0);
        pq.add(new Node(start, 0, 0));
        cameFrom.put(startKey, null);

        // Continue exploring until there are no more nodes to explore
        while (!pq.isEmpty()) {
            // Get the node with the lowest fCost from the priority queue
            Node currentNode = pq.poll();
            int[] current = currentNode.getPosition();

            // Check if the current node is the goal
            if (Arrays.equals(current, goal)) {
                // Reconstruct and return the path from start to goal
                return reconstructPath(cameFrom, current);
            }

            // Explore the neighbors of the current node
            for (int[] neighbor : getNeighbors(current)) {
                // Calculate the new cost to reach the neighbor
                int newCost = costs.get(Arrays.toString(current)) + getMovementCost(neighbor);
                String neighborKey = Arrays.toString(neighbor);

                // If the neighbor is not in costs or the new cost is lower, update the costs and path
                if (!costs.containsKey(neighborKey) || newCost < costs.get(neighborKey)) {
                    costs.put(neighborKey, newCost);
                    cameFrom.put(neighborKey, current);
                    pq.add(new Node(neighbor, newCost, newCost));
                }
            }
        }

        // Return an empty list if no path is found
        return new ArrayList<>();
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
        // All Terrain is the same. Adjustments might be needed
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
        // Stack to manage the nodes to be explored
        Stack<int[]> stack = new Stack<>();
        // Map to track the path from start to goal
        Map<String, int[]> cameFrom = new HashMap<>();

        // Initialize the stack with the start node
        stack.push(start);
        // Mark the start node as having no predecessor
        cameFrom.put(Arrays.toString(start), null);

        // Continue exploring until there are no more nodes to explore
        while (!stack.isEmpty()) {
            // Get the current node from the stack
            int[] current = stack.pop();

            // Check if the current node is the goal
            if (Arrays.equals(current, goal)) {
                // Reconstruct and return the path from start to goal
                return reconstructPath(cameFrom, current);
            }

            // Explore the neighbors of the current node
            for (int[] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.toString(neighbor);
                // If the neighbor has not been visited yet
                if (!cameFrom.containsKey(neighborKey)) {
                    // Mark the current node as the predecessor of the neighbor
                    cameFrom.put(neighborKey, current);
                    // Add the neighbor to the stack for further exploration
                    stack.push(neighbor);
                }
            }
        }

        // Return an empty list if no path is found
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
        // List to store the path from start to goal
        List<int[]> path = new LinkedList<>();
        // Convert the current node to a string key
        String currentKey = Arrays.toString(current);

        // Backtrack from the goal to the start node
        while (current != null) {
            // Add the current node to the beginning of the path
            path.add(0, current);
            // Move to the predecessor of the current node
            current = cameFrom.get(currentKey);
            // Update the current key if the current node is not null
            if (current != null) {
                currentKey = Arrays.toString(current);
            }
        }

        // Return the reconstructed path
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
        // List to store the valid neighbors
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

        // Iterate through each direction to find valid neighbors
        for (int[] dir : directions) {
            int nr = dir[0];
            int nc = dir[1];

            // Check if the new position is within grid boundaries
            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
                // Check if the cell is walkable (assuming 0 = walkable, 1 = obstacle)
                if (grid[nr][nc] == 0) {
                    // Add the valid neighbor to the list
                    neighbors.add(new int[]{nr, nc});
                }
            }
        }

        // Return the list of valid neighbors
        return neighbors;
    }












}
