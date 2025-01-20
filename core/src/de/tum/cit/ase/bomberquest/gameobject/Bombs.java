package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.map.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.util.Manager;
import de.tum.cit.ase.bomberquest.util.Rectangle;
import de.tum.cit.ase.bomberquest.util.SoundsManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.awt.*;

public class Bombs extends Entity {
    private float bombTick = 4f; // Time until the bomb explodes
    private float bombTickInterval = bombTick;
    private float flashInterval = 0.1f; // Initial flash interval
    private float flashTimer = flashInterval; // Timer to control flashing
    private boolean visible = true; // Toggle visibility

    public Bombs(Vector2 pos) {
        super(pos, new Texture("bomb.png"));
    }

    public Rectangle getRect() {
        return new Rectangle(pos.x, pos.y, texture.getWidth(), texture.getHeight());
    }

    public void update() {
        // Decrease bombTickInterval based on delta time
        if(isExplode()){
            destroyFlag = true;
        }

        bombTickInterval -= Gdx.graphics.getDeltaTime();

        // Calculate the new flash interval dynamically
        if (bombTickInterval > 0) {
            flashInterval = Math.max(0.1f, bombTickInterval / 10);
        }

        // Update the flash timer and toggle visibility when the flash interval is reached
        flashTimer -=  Gdx.graphics.getDeltaTime();
        if (flashTimer <= 0) {
            if (bombTickInterval < 3) {
                // Play sound when flashing quickly (assumes a SoundManager class handles sounds)
                Manager.getInstance().soundsManager.play("clickBomb",1.0f);
            }
            visible = !visible; // Toggle visibility
            flashTimer = flashInterval; // Reset the flash timer
        }
    }

    public void draw(Batch batch){
        if (bombTickInterval > 3){
            visible = true;
        }
        if (visible) {
            batch.begin();
            batch.draw(texture, pos.x, pos.y ,texture.getWidth(),texture.getHeight(),0,0,texture.getWidth(),texture.getHeight(),false,false);
            batch.end();
        }

    }

    public boolean isExplode(){
        return bombTickInterval <= 0;
    }


    public void dispose() {
        texture.dispose();
    }
}