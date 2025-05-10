package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class SpecialEnemy extends Enemy {
    public SpecialEnemy() {
        super();
    }

    @Override
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
}
