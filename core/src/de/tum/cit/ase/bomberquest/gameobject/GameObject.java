package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

//TODO GameObject aufteilen in GameObject und Entity

public class GameObject {
    public int row;
    public int col;

    public CellType cellType;

    public GameObject(int row, int col, CellType cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
    }

    public Rectangle getRect(){
        return new Rectangle(col * 16,row * 16,16,16);
    }
}