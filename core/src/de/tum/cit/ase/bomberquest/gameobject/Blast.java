package de.tum.cit.ase.bomberquest.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.Characters.Entity;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the explosion (blast) created by a bomb.
 */


public class Blast extends Entity {
    SpriteSheet sheet;
    float  deathTime = 0;
    public Blast(Vector2 pos) {
        super(pos, null);

        sheet = new SpriteSheet(new Texture("explo1.png"),1,10);
        sheet.setPlay(0,9,0.1f,false);
    }

    @Override
    public void update() {
        super.update();
        sheet.play();

        deathTime += Gdx.graphics.getDeltaTime();
        if(deathTime >= 3){
            destroyFlag = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        TextureRegion t = sheet.getCurrentFrame();
        batch.draw(t, pos.x ,pos.y ,sheet.getWidth()/2,sheet.getHeight()/2,sheet.getWidth(),sheet.getHeight(),1,1,0);
        batch.end();
    }
}

