package de.tum.cit.ase.bomberquest.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * The {@code SoundsManager} class handles all sound effects and background music in the game.
 *
 * <p>It loads various sound files into a {@code HashMap} for easy access and provides methods
 * to play, loop, and stop sounds as needed. This class ensures efficient sound management by
 * avoiding redundant sound file loading.
 *
 * <p>Sounds managed include:
 * - Background music (Menu, Game, Win, Game Over)
 * - Sound effects (Shooting, Explosions, Power-ups, Player Hit)
 *
 * <p>This class is accessed via the {@code Manager} singleton.
 */
public class SoundsManager {

    /**
     * A {@code HashMap} storing sound files with their corresponding names.
     * This allows for quick retrieval and playback of sounds using a string key.
     */
    public HashMap<String, Sound> sounds = new HashMap<>();

    /**
     * Constructor that initializes the sound library by loading all necessary sound files.
     * Each sound file is associated with a unique key in the {@code sounds} HashMap.
     */
    public SoundsManager() {
        // Background music
        sounds.put("menuMusic", Gdx.audio.newSound(Gdx.files.internal("audio/background.mp3")));
        sounds.put("gameMusic", Gdx.audio.newSound(Gdx.files.internal("audio/gameMusic.mp3")));
        sounds.put("gameOverMusic", Gdx.audio.newSound(Gdx.files.internal("audio/Intense.mp3")));
        sounds.put("winMusic", Gdx.audio.newSound(Gdx.files.internal("audio/Test Music.mp3")));

        // Sound effects
        sounds.put("death", Gdx.audio.newSound(Gdx.files.internal("sound/hitHurt.wav")));
        sounds.put("hit", Gdx.audio.newSound(Gdx.files.internal("sound/hitHurt.wav")));
        sounds.put("item", Gdx.audio.newSound(Gdx.files.internal("sound/Powerup.wav")));
        sounds.put("shoot", Gdx.audio.newSound(Gdx.files.internal("sound/laserShoot.wav")));
        sounds.put("clickBomb", Gdx.audio.newSound(Gdx.files.internal("sound/clickBomb.wav")));
        sounds.put("boom", Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav")));
    }

    /**
     * Plays a specified sound effect at a given volume.
     *
     * @param sound The key of the sound to play.
     * @param volume The volume level (0.0 to 1.0).
     */
    public void play(String sound, float volume) {
        sounds.get(sound).play(volume);
    }

    /**
     * Plays the menu background music in a loop.
     * Stops all other currently playing sounds before starting the menu music.
     */
    public void playMenuMusic() {
        for (Sound sound : sounds.values()) {
            sound.stop();
        }
        sounds.get("menuMusic").stop();
        sounds.get("menuMusic").loop(0.5f);
    }

    /**
     * Plays the main game background music in a loop.
     * Stops all other currently playing sounds before starting the game music.
     */
    public void playGameMusic() {
        for (Sound sound : sounds.values()) {
            sound.stop();
        }
        sounds.get("gameMusic").stop();
        sounds.get("gameMusic").loop(0.5f);
    }

    /**
     * Plays the victory background music in a loop.
     * Stops all other currently playing sounds before starting the win music.
     */
    public void playWinMusic() {
        for (Sound sound : sounds.values()) {
            sound.stop();
        }
        sounds.get("winMusic").stop();
        sounds.get("winMusic").loop(0.5f);
    }

    /**
     * Plays the game-over background music in a loop.
     * Stops all other currently playing sounds before starting the game-over music.
     */
    public void playGameOverMusic() {
        for (Sound sound : sounds.values()) {
            sound.stop();
        }
        sounds.get("gameOverMusic").stop();
        sounds.get("gameOverMusic").loop(0.5f);
    }

    /**
     * Stops the currently playing background music.
     */
    public void stopMusic() {
        sounds.get("music").stop();
    }

    /**
     * Stops a specific sound effect or music track.
     *
     * @param sound The key of the sound to stop.
     */
    public void stop(String sound) {
        sounds.get(sound).stop();
    }
}
