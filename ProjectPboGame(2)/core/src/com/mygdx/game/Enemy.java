package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Enemy extends MovingObject {
    public Enemy() {
        state = State.RUN;
        Random randomizer = new Random();
        int angka = randomizer.nextInt(100);
        if (angka < 50) {
            DX = -1;
        } else {
            DX = 1;
        }
        this.InitializeAnimation();
    }

    public void InitializeAnimation() {
        HelloWorldGame parentGame = (HelloWorldGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = parentGame.getAssetManager();

        Texture idle = assetManager.get("IdleEnemy.png", Texture.class);
        Texture run = assetManager.get("RunEnemy.png", Texture.class);

        TextureRegion[] frames = HelloWorldGame.CreateAnimationFrames(idle, 32, 32, 11, false, true);
        idleRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        frames = HelloWorldGame.CreateAnimationFrames(idle, 32, 32, 11, true, true);
        idleLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        frames = HelloWorldGame.CreateAnimationFrames(run, 32, 32, 11, false, true);
        runRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        frames = HelloWorldGame.CreateAnimationFrames(run, 32, 32, 11, true, true);
        runLeftAnimation = new Animation<TextureRegion>(0.05f, frames);
        stateTime = 0;
    }

    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        X += DX;
        if (X >= 610) {
            X = 0;
        }
        if (X <= -30) {
            X = 600;
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
}
