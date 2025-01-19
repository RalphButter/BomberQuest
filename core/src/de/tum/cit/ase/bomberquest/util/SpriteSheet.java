package de.tum.cit.ase.bomberquest.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * The SpriteSheet class is essential for managing and manipulating sprite sheets in a game.
 * A sprite sheet is a single image file that contains multiple smaller images (frames) arranged in a grid.
 * These frames can represent different states or animations of a game character or object.
 *
 * The SpriteSheet class is particularly useful for the Map, Enemy, and Player classes, as it allows for:
 * - Efficient rendering of different map tiles.
 * - Managing various enemy animations, such as movement.
 * - Handling player character animations for actions like walking and attacking.
 *
 * By using a sprite sheet, the game can optimize memory usage and rendering performance while
 * simplifying the management of visual assets.
 */

//I don't think our current render function work in Enemy and Player. We might need this class.

public class SpriteSheet {
    Texture texture;

    int rows;
    int cols;

    TextureRegion[] frames;
    float countTime = 0;
    int from;
    int to;
    int current;
    float time;
    boolean loop;

    int width;
    int height;

    public SpriteSheet(Texture texture,int rows,int cols){
        this.texture = texture;
        this.rows = rows;
        this.cols = cols;

        frames = new TextureRegion[rows * cols];
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);  // split the sprite sheet
        width = texture.getWidth() / cols;
        height =  texture.getHeight() / rows;
        int ide = 0;
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[ide] = tmp[i][j];
                ide++;
            }
        }
    }


    //TODO fix GetTexture, Do we need a getCurrentFrame function? (Check and if yes implemtn)
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public TextureRegion[] getFrames() {
        return frames;
    }

    public void setFrames(TextureRegion[] frames) {
        this.frames = frames;
    }

    public float getCountTime() {
        return countTime;
    }

    public void setCountTime(float countTime) {
        this.countTime = countTime;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
