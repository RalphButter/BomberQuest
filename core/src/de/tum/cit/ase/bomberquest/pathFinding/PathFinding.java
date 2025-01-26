package de.tum.cit.ase.bomberquest.pathFinding;

import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import de.tum.cit.ase.bomberquest.gameobject.CellType;
import java.util.ArrayList;
import java.util.List;

public class PathFinding {
    private Map map;
    private List<Node> open_list;
    private List<Node> close_list;
    private Node startNode, endNode;

    public PathFinding(Map map) {
        this.map = map;
    }
    public Map getMap() { return map; }

    private Node isInOpenList(Node node) {
        for (Node n : open_list) {
            if (n.equal(node)) return n;
        }
        return null;
    }
    private Node isInCloseList(int row, int col) {
        for (Node n : close_list) {
            if (n.getRow() == row && n.getCol() == col) return n;
        }
        return null;
    }
    private int findMinFNode() {
        float min = Float.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < open_list.size(); i++) {
            float f = open_list.get(i).getF();
            if (f < min) {
                min = f;
                index = i;
            }
        }
        return index;
    }
    private static float grid_distance(Node a, Node b) {
        int dx = Math.abs(a.getCol() - b.getCol());
        int dy = Math.abs(a.getRow() - b.getRow());
        return dx + dy; // Manhattan
    }
    private void exploreNeighbour(Node node) {
        int[] dir_row = {-1, 1, 0, 0};
        int[] dir_col = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nr = node.getRow() + dir_row[i];
            int nc = node.getCol() + dir_col[i];
            if (nr < 0 || nr >= map.getRows() || nc < 0 || nc >= map.getCols()) continue;
            if (map.getCell(nr, nc).cellType == CellType.INDESTRUCTIBLE_WALL) continue;
            if (map.getCell(nr, nc).cellType == CellType.DESTRUCTIBLE_WALL) continue;
            if (isInCloseList(nr, nc) != null) continue;

            Node newNode = new Node(nr, nc, node);
            Node maybeOpen = isInOpenList(newNode);
            if (maybeOpen != null) {
                newNode = maybeOpen;
            }
            float gCost = node.getG() + 1;
            if (gCost < newNode.getG() || maybeOpen == null) {
                newNode.setG(gCost);
                newNode.setH(grid_distance(newNode, endNode));
                if (maybeOpen == null) {
                    open_list.add(newNode);
                }
            }
        }
    }
    private List<Vector2> showPath(Node node) {
        Node tmp = node;
        List<Node> tracePath = new ArrayList<>();
        while (tmp != null) {
            tracePath.add(tmp);
            tmp = tmp.getParent();
        }
        List<Vector2> path = new ArrayList<>();
        for (int i = tracePath.size() - 1; i >= 0; i--) {
            Node n = tracePath.get(i);
            // n.getCol() = x, n.getRow() = y
            path.add(new Vector2(n.getCol(), n.getRow()));
        }
        return path;
    }

    /**
     * Finds a path from (startRow, startCol) to (targetRow, targetCol).
     * row ~ y, col ~ x
     */
    public List<Vector2> findPath(int startRow, int startCol, int targetRow, int targetCol) {
        startNode = new Node(startRow, startCol, null);
        endNode   = new Node(targetRow,   targetCol, null);

        open_list = new ArrayList<>();
        close_list = new ArrayList<>();
        open_list.add(startNode);

        while (!open_list.isEmpty()) {
            int iMin = findMinFNode();
            Node current = open_list.get(iMin);
            open_list.remove(iMin);
            close_list.add(current);

            if (current.equal(endNode)) {
                List<Vector2> finalPath = showPath(current);
                System.out.println("Path found! Size=" + finalPath.size());
                return finalPath;
            }
            exploreNeighbour(current);
        }
        System.out.println("No path found from ("+startRow+","+startCol+") to ("+targetRow+","+targetCol+")");
        return new ArrayList<>();
    }
}
