package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Apple extends Object {
    enum State {
        IDLE,
        COLLECTED,
        INACTIVE
    }
    Animation<TextureRegion> idleAnimation, collectedAnimation;

    State state = State.IDLE;
    Sound sound;

    public Apple() {
        Game parentGame = (Game)Gdx.app.getApplicationListener();
        AssetManager assetManager = ((HelloWorldGame)parentGame).getAssetManager();
        sound = assetManager.get("collect.wav", Sound.class);

        this.CreateSpriteSheet();
    }

    public void CreateSpriteSheet() {
        Game parentGame = (Game) Gdx.app.getApplicationListener();
        AssetManager assetManager = ((HelloWorldGame)parentGame).getAssetManager();

        Texture apple = assetManager.get("Apple.png", Texture.class);
        Texture collected = assetManager.get("Collected.png", Texture.class);

        TextureRegion[] frames  = HelloWorldGame.CreateAnimationFrames(apple, 32, 32, 17, false, true);
        idleAnimation = new Animation<TextureRegion>(0.05f, frames);

        frames  = HelloWorldGame.CreateAnimationFrames(collected, 32, 32, 6, false, true);
        collectedAnimation = new Animation<TextureRegion>(0.05f, frames);

        stateTime = 0;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;
        if(state == State.IDLE)
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        else if(state == State.COLLECTED)
            currentFrame = collectedAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, X-16, Y-16);
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        if(state == State.COLLECTED && stateTime > 0.6f) {
            state = State.INACTIVE;
        }
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public State getState() {
        return state;
    }

    public void collect()
    {
        if(state != State.COLLECTED) {
            state = State.COLLECTED;
            stateTime = 0;
            sound.play();
        }
    }
}

