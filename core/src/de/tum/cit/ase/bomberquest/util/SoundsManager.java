package de.tum.cit.ase.bomberquest.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

//Todo Implementieren
public class SoundsManager {

    public HashMap<String, Sound> sounds = new HashMap();

    public SoundsManager(){
        sounds.put("menuMusic", Gdx.audio.newSound(Gdx.files.internal("audio/background.mp3")));
        sounds.put("gameMusic",Gdx.audio.newSound(Gdx.files.internal(  "audio/gameMusic.mp3")));

        sounds.put("gameOverMusic",Gdx.audio.newSound(Gdx.files.internal(  "audio/Intense.mp3")));
        sounds.put("winMusic",Gdx.audio.newSound(Gdx.files.internal(  "audio/Test Music.mp3")));

        sounds.put("death",Gdx.audio.newSound(Gdx.files.internal(  "sound/hitHurt.wav")));
        sounds.put("hit",Gdx.audio.newSound(Gdx.files.internal(  "sound/hitHurt.wav")));
        sounds.put("item",Gdx.audio.newSound(Gdx.files.internal(  "sound/Powerup.wav")));

        sounds.put("shoot",Gdx.audio.newSound(Gdx.files.internal(  "sound/laserShoot.wav")));

        sounds.put("clickBomb",Gdx.audio.newSound(Gdx.files.internal(  "sound/clickBomb.wav")));
        sounds.put("boom",Gdx.audio.newSound(Gdx.files.internal(  "sound/explosion.wav")));

    }


    public void play(String sound,float volume){
        sounds.get(sound).play(volume);
    }

    public void playMenuMusic(){
        for(Sound sound : sounds.values()){
            sound.stop();
        }

        sounds.get("menuMusic").stop();
        sounds.get("menuMusic").loop(0.5f);
    }
    public void playGameMusic(){
        for(Sound sound : sounds.values()){
            sound.stop();
        }

        sounds.get("gameMusic").stop();
        sounds.get("gameMusic").loop(0.5f);
    }
    public  void playWinMusic(){
        for(Sound sound : sounds.values()){
            sound.stop();
        }

        sounds.get("winMusic").stop();
        sounds.get("winMusic").loop(0.5f);
    }

    public  void playGameOverMusic(){
        for(Sound sound : sounds.values()){
            sound.stop();
        }

        sounds.get("gameOverMusic").stop();
        sounds.get("gameOverMusic").loop(0.5f);
    }

    public void stopMusic(){
        sounds.get("music").stop();
    }

    public void stop(String sound){
        sounds.get(sound).stop();
    }
}

