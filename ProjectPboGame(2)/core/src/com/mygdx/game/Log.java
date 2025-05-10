package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Log extends Object {
    private float X, Y;
    boolean cek=true;

    float initialX = X;

    float movementDistance=600;

    private Texture logTexture;
    float speed = 100;

    public Log() {
        HelloWorldGame parentGame = (HelloWorldGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = parentGame.getAssetManager();

        logTexture = assetManager.get("sandfix.png", Texture.class);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(logTexture, X, Y);
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime()*speed;
        float del = -Gdx.graphics.getDeltaTime()*speed;

        if (cek){
            X+=delta;
        }else {
            X-=delta;
        }
        if (cek&&X>=initialX+movementDistance){
            cek=false;
        } else if (!cek&&X<=initialX) {
           cek=true;
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

