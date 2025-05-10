package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Object {
    float stateTime;
    float X,Y;
    public abstract void draw(SpriteBatch batch);
    public abstract void update();
}
