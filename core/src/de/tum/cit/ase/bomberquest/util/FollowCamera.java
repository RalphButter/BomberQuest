package de.tum.cit.ase.bomberquest.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.MapManager.Map;


//The map is to big. We need a function that follows the player
public class FollowCamera {
    OrthographicCamera camera;
    Map map;

    public FollowCamera(OrthographicCamera camera,  Map map){
        this.camera = camera;
        this.map =map;
        camera.zoom = 0.2f;
    }

    public void follow(Vector2 target){
        camera.position.x = target.x;
        camera.position.y = target.y;
        float centerX = camera.viewportWidth * camera.zoom * 0.5f;
        float centerY = camera.viewportHeight * camera.zoom * 0.5f;
        if(camera.position.y  <  centerY){
            camera.position.y = centerY;
        }
        else if(camera.position.y  >  map.getRows()* 16 - centerY   ){
            camera.position.y =  map.getRows()* 16 - centerY ;
        }
        if(camera.position.x  <  centerX){
            camera.position.x = centerX;
        }
        else if(camera.position.x  >  map.getCols()* 16 - centerX  ){
            camera.position.x =  map.getCols()* 16 - centerX;
        }


    }


}
