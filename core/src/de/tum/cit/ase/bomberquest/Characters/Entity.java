package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.util.Rectangle;

public class Entity {
    protected Vector2 pos;
    Texture texture;
    public boolean destroyFlag = false;

    public Entity(Vector2 pos, Texture texture) {
        this.pos = pos;
        this.texture = texture;
    }
    public void update(){

    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Rectangle getRect(){
        return new Rectangle(pos.x,pos.y,texture.getWidth(),texture.getHeight());
    }
    public void draw(Batch batch){
        batch.begin();
        batch.draw(texture, pos.x, pos.y ,texture.getWidth(),texture.getHeight(),0,0,texture.getWidth(),texture.getHeight(),false,false);
        batch.end();
    }
}
