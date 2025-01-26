package de.tum.cit.ase.bomberquest.pathFinding;

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

    /**
     * Returns the 2D grid representing the game map or terrain.
     *
     * @return The 2D array representing the game map or terrain.
     */
    public Map getMap(){
        return map;
    }

    /**
     * Returns the node in the open list that is equal to the given node.
     *
     * @param node The node to search for in the open list.
     * @return The node in the open list that is equal to the given node, or {@code null} if not found.
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
     * Returns the node in the close list that is at the given row and column.
     *
     * @param row The row of the node to search for in the close list.
     * @param col The column of the node to search for in the close list.
     * @return The node in the close list that is at the given row and column, or {@code null} if not found.
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
     * Returns the index of the node in the open list that has the minimum F value.
     *
     * @return The index of the node in the open list that has the minimum F value.
     */
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



    /**
     * Explores the neighbors of the given node.
     *
     * @param node The node whose neighbors to explore.
     */

//    Ralph bitte die Funktionen noch in der Map Klasse implementieren


    void exploreNeighbour(Node node) {
        int dir_row[] = { -1, 1, 0, 0 };
        int dir_col[] = { 0, 0, -1, 1 };
        //int dir_row[4] = { -1, 1, 0, 0 };
        //int dir_col[4] = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int new_row = dir_row[i] + node.getRow();
            int new_col = dir_col[i] + node.getCol();

            if (new_row > map.getRows() - 1 || new_row < 0 || new_col > map.getCols() - 1 || new_col < 0) {
                continue;
            }
            if (map.getCell(new_row,new_col).cellType == CellType.INDESTRUCTIBLE_WALL) {
                continue;
            }
            if (map.getCell(new_row,new_col).cellType == CellType.DESTRUCTIBLE_WALL) {
                continue;
            }
            if (map.getCell(new_row,new_col).cellType == CellType.BLAST_RADIUS_PU) {
                continue;
            }
            if (map.getCell(new_row,new_col).cellType == CellType.CONCURRENT_PU) {
                continue;
            }
            if (isInCloseList(new_row, new_col) != null) {
                continue;
            }

            Node new_node = new Node(new_row, new_col, node);
            if (isInOpenList(new_node) != null) {
                new_node = isInOpenList(new_node);
            }

            float path_to_this_neightbour = node.getG() + 1;

            if (path_to_this_neightbour < new_node.getG() || isInOpenList(new_node) == null) {
                // is mud

                new_node.setG(path_to_this_neightbour);
                new_node.setH(grid_distance(new_node, endNode));

                //graphData->changeType(new_node->getRow(), new_node->getCol(), 1);
                if (isInOpenList(new_node) == null) {
                    open_list.add(new_node);
                }
            }
        }

    }

    /**
     * Returns the  distance between two nodes.
     *
     * @param a The first node.
     * @param b The second node.
     * @return The distance between the two nodes.
     */

    static float grid_distance(Node a, Node b)
    {
        int dst_x = Math.abs(a.getCol() - b.getCol());
        int dst_y = Math.abs(a.getRow() - b.getRow());

        if (dst_x > dst_y)
        {
            return 5 * dst_y + 3 * (dst_x - dst_y);
        }
        return 5 * dst_x + 3 * (dst_y - dst_x);

    }

    /**
     * Returns the path from the start node to the given node.
     *
     * @param node The node to find the path to.
     * @return The path from the start node to the given node.
     */

    List<Vector2> showPath(Node node) {
        Node tmp = node;
        List<Node> tracepath = new ArrayList<>();
        List<Vector2> path = new ArrayList<>();
        while (tmp != null)
        {
            tracepath.add(tmp);
            tmp = tmp.getParent();
        }
        for (int i = tracepath.size() - 1; i >= 0; i--) {
            Node n = tracepath.get(i);
            path.add(new Vector2(n.getCol(), n.getRow()));
        }

        if(path.size() > 0){
            path.remove(0);
        }

        return path;
    }



    /**
     * Finds a path from the given start node to the given end node.
     *
     * @param col The column of the start node.
     * @param row The row of the start node.
     * @param toCol The column of the end node.
     * @param toRow The row of the end node.
     * @return The path from the start node to the end node.
     */
    public List<Vector2> findPath(int col,int row, int toCol,int toRow) {
        startNode = new Node(row,col,null);
        endNode = new Node(toRow,toCol,null);

        open_list = new ArrayList<>();
        close_list = new ArrayList<>();

        open_list.add(startNode);
        while (open_list.size() != 0) {

            //Sleep(1);
            int i_min = findMinFNode();
            Node currentNode = open_list.get(i_min);


            close_list.add(currentNode);
            if (currentNode.equal(endNode)) {
                return showPath(currentNode);
            }

            open_list.remove(i_min);
            exploreNeighbour(currentNode);
        }
        return new ArrayList<>();
    }



}
