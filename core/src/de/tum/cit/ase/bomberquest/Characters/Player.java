package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class Player extends Character {
    /**
     * Constructor to initialize a Character object.
     *
     * @param x      Initial X coordinate
     * @param y      Initial Y coordinate
     * @param health Initial health of the character
     */
    public Player(int x, int y, int health) {
        super(x, y, health);
        this.texture = new Texture(Gdx.files.internal("assets/character.png"));

        TextureRegion[] walkUpFrames = {
                new TextureRegion(texture, 0, 64, 16, 32),
                new TextureRegion(texture, 16, 64, 16, 32),
                new TextureRegion(texture, 32, 64, 16, 32),
                new TextureRegion(texture, 48, 64, 16, 32),
        };
        setWalkUpAnimation(new Animation<>(0.1f, walkUpFrames));

        TextureRegion[] walkDownFrames = {
                new TextureRegion(texture, 0, 0, 16, 32),
                new TextureRegion(texture, 16, 0, 16, 32),
                new TextureRegion(texture, 32, 0, 16, 32),
                new TextureRegion(texture, 48, 0, 16, 32),
        };
        setWalkDownAnimation(new Animation<>(0.1f, walkDownFrames));

        TextureRegion[] walkLeftFrames = {
                new TextureRegion(texture, 0, 96, 16, 32),
                new TextureRegion(texture, 16, 96, 16, 32),
                new TextureRegion(texture, 32, 96, 16, 32),
                new TextureRegion(texture, 48, 96, 16, 32),
        };
        setWalkLeftAnimation(new Animation<>(0.1f, walkLeftFrames));

        TextureRegion[] walkRightFrames = {
                new TextureRegion(texture, 0, 32, 16, 32),
                new TextureRegion(texture, 16, 32, 16, 32),
                new TextureRegion(texture, 32, 32, 16, 32),
                new TextureRegion(texture, 48, 32, 16, 32),
        };
        setWalkRightAnimation(new Animation<>(0.1f, walkRightFrames));

        this.rectangle = new Rectangle((int) X + 2, (int) Y + 2,12,12);

        setSpeed(2f);
    }
}
