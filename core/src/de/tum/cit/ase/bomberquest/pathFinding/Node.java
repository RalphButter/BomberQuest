package de.tum.cit.ase.bomberquest.pathFinding;

public class Node {
    float g, h;
    Node parent;
    int row, col;
    float distance;

    Node(int row, int col, Node parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;

        g = 0;
        h = 0;
        distance = Integer.MAX_VALUE;
    }

    public float getF() {
        return g + h;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean equal(Node n){
        return n.row == row && n.col == col;
    }
}
