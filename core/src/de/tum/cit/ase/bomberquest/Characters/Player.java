package de.tum.cit.ase.bomberquest.Characters;

public class Player {

    private int bombCount;
    private int bombRange;

    public void increaseBombCount(int bombCount) {
        this.bombCount += bombCount;
    }

    public void increaseBombRange(int bombRange) {
        this.bombRange += bombRange;
    }

    public void increaseBombCount() {
        this.bombCount++;
    }

    public void increaseBombRange() {
        this.bombRange++;
    }
}
