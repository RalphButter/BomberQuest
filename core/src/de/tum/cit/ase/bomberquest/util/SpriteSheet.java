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
//Wir sollten nicht das Enum benutzen. Ich habe auf das Enum aufgebaut

public class SpriteSheet {
    Texture texture; // The texture representing the sprite sheet

    int rows; // Number of rows in the sprite sheet
    int cols; // Number of columns in the sprite sheet

    TextureRegion[] frames; // Array of texture regions representing individual frames
    float countTime = 0; // Time counter for animation
    int from; // Starting frame index for animation
    int to; // Ending frame index for animation
    int current; // Current frame index
    float time; // Duration for each frame
    boolean loop; // Flag to indicate if the animation should loop

    int width; // Width of each frame
    int height; // Height of each frame

    /**
     * Constructs a SpriteSheet object by splitting the provided texture into individual frames.
     *
     * @param texture The texture representing the sprite sheet.
     * @param rows The number of rows in the sprite sheet.
     * @param cols The number of columns in the sprite sheet.
     */
    public SpriteSheet(Texture texture, int rows, int cols) {
        this.texture = texture; // Assign the provided texture to the class variable
        this.rows = rows; // Assign the number of rows to the class variable
        this.cols = cols; // Assign the number of columns to the class variable

        frames = new TextureRegion[rows * cols]; // Initialize the frames array to hold each frame of the sprite sheet
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows); // Split the texture into individual frames

        width = texture.getWidth() / cols; // Calculate the width of each frame
        height = texture.getHeight() / rows; // Calculate the height of each frame

        int ide = 0; // Initialize the frame index
        for (int i = 0; i < rows; i++) { // Loop through each row
            for (int j = 0; j < cols; j++) { // Loop through each column
                frames[ide] = tmp[i][j]; // Assign the frame to the frames array
                ide++; // Increment the frame index
            }
        }
    }


    //TODO fix GetTexture, Do we need a getCurrentFrame function? (Check and if yes implemtn)
    public TextureRegion getTexture(int frame) {
        return frames[frame];
    }

    public TextureRegion getCurrentFrame() {
        return frames[current];
    }

    /**
     * Flips all frames in the sprite sheet horizontally if flipX is true.
     * This method iterates through each frame in the sprite sheet and applies
     * a horizontal flip based on the flipX parameter. This can be useful for
     * changing the direction of animations or character sprites without needing
     * separate sprite sheets for each direction.
     *
     * @param flipX A flag indicating whether to flip the frames horizontally.
     */
    void flip(boolean flipX) {
        // Flips all frames in the sprite sheet horizontally if flipX is true
        int ide = 0;
        for (int i = 0; i < rows; i++) { // Loop through each row
            for (int j = 0; j < cols; j++) { // Loop through each column
                frames[ide].flip(flipX, false); // Flip the frame horizontally
                ide++; // Increment the frame index
            }
        }
    }


    /**
     * Sets the animation playback parameters for the sprite sheet.
     * This method configures the range of frames to be played, the duration for each frame,
     * and whether the animation should loop. It also initializes the current frame to the starting frame.
     *
     * @param from The starting frame index for the animation.
     * @param to The ending frame index for the animation.
     * @param time The duration for each frame in the animation.
     * @param loop A flag indicating whether the animation should loop.
     */
    public void setPlay(int from, int to, float time, boolean loop) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.loop = loop;
        current = from;
    }


    /**
     * Plays the animation by updating the current frame based on the elapsed time.
     * This method increments the countTime variable by the time elapsed since the last frame.
     * If the countTime exceeds the specified time for each frame, the current frame is updated.
     * If the animation reaches the end and loop is true, it restarts from the beginning.
     * If loop is false, the animation stops at the last frame.
     */
    public void play() {
        countTime += Gdx.graphics.getDeltaTime();
        if(countTime >= time) {
            countTime = 0;
            current++;
            if(current > to && loop) {
                current = from;
            }
            else if(current > to && !loop){
                current = to;
            }
        }

    }
    public void draw(Batch batch,Vector2 pos,float scaleX,float scaleY){
        batch.draw(frames[current], pos.x, pos.y,width/2,height/2,width,height,scaleX,scaleY,0);
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

    void dispose()
    {
        texture.dispose();
    }
}
