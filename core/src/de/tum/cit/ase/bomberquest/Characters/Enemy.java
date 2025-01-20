package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.gameobject.Wall;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.util.CollisionDetection;
import de.tum.cit.ase.bomberquest.MapManager.Node;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.util.Rectangle;
import de.tum.cit.ase.bomberquest.Characters.PathFinding;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;

import java.util.List;

//Todo Enemy angleichen an Cell und Map Logik
public class Enemy extends Entity {


//    Pathfinding needs to be implemented
    SpriteSheet sheet;
    PathFinding pathFinding;
    List<Vector2> path;
    Vector2 target;
    Vector2 vel;
    float speed;
    int currentTargetIndex;
    int scaleX = 1;

    //TODO KONSTRUTUR ANGLEICHEN
    public Enemy(Vector2 pos, Texture texture) {
        super(pos, texture);
    }

    /*public Enemy(Vector2 pos, Map map) {
        super(pos, null);
        sheet = new SpriteSheet(new Texture("enemy.png"),1,3);
        sheet.setPlay(0, 2, 0.1f, true);
        pathFinding = new PathFinding(map);

        GameObject randomGameObject = pathFinding.getMap().getRandomEmptyCell();
        path = pathFinding.findPath((int)pos.x/16,(int)pos.y/16, randomGameObject.col, randomGameObject.row );
        for(Vector2 v: path){
        }
        currentTargetIndex = 0;
        speed = 0.25F;
        target = new Vector2(pos.x ,pos.y);
        vel = new Vector2(0,0);
    }*/

}