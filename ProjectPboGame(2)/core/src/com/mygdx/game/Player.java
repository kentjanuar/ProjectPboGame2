package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Player extends MovingObject {
    float speed =200;

    public Player()
    {
        this.InitializeAnimation();
    }

    public void InitializeAnimation()
    {
        HelloWorldGame parentGame = (HelloWorldGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = parentGame.getAssetManager();

        Texture idle = assetManager.get("IdlePlayer.png", Texture.class);
        Texture run = assetManager.get("RunPlayer.png", Texture.class);

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
    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();
        //angle += delta * 180;
        stateTime += delta;
        X += DX * speed * delta;
        if(X > 620)
        {
            X = 620;
            this.Stop();
        }
        else if(X < 20)
        {
            X = 20;
            this.Stop();
        }

        Y += DY * speed * delta;
        if(Y > 460)
        {
            Y = 460;
            this.Stop();
        }
        else if(Y < 20)
        {
            Y = 20;
            this.Stop();
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

    public void SetMove(Direction d)
    {
        direction = d;
        state = State.RUN;
        if(animationDirection == Direction.LEFT && d == Direction.RIGHT)
        {
            animationDirection = Direction.RIGHT;
            stateTime = 0;
        }
        else if(animationDirection == Direction.RIGHT && d == Direction.LEFT)
        {
            animationDirection = Direction.LEFT;
            stateTime = 0;
        }

        if(d == Direction.RIGHT)
        {
            DX = 1;
            DY = 0;
        }
        else if(d == Direction.LEFT)
        {
            DX = -1;
            DY = 0;
        }
        else if(d == Direction.UP)
        {
            DX = 0;
            DY = -1;
        }
        else if(d == Direction.DOWN)
        {
            DX = 0;
            DY = 1;
        }
    }
    public boolean checkCollision(Player player, ArrayList<Enemy> enemyList) {
        float playerX = player.getX();
        float playerY = player.getY();
        float collisionRadiusSquared = 256; // Radius tabrakan (di sini menggunakan radius 16 pixel)

        for (Enemy enemy : enemyList) {
            float enemyX = enemy.getX();
            float enemyY = enemy.getY();

            float distanceSquared = (playerX - enemyX) * (playerX - enemyX) + (playerY - enemyY) * (playerY - enemyY);
            if (distanceSquared <= collisionRadiusSquared) {
                return false; // Terjadi tabrakan dengan salah satu enemy dalam enemyList
            }
        }
        return  true;
    }

    public boolean checkCollisionBackGround() {
        int playerGridY = (int) (getY() / 64);
        if (playerGridY >= 1 && playerGridY <= 3.5){
            return false;
        }else return true;
    }

    public boolean checkCollisionLog(ArrayList<Log> logs1) {
        for(Log log : logs1) {
            float logX = log.getX();
            float logY = log.getY();
            if(X > logX && X < logX + 50 && Y > logY && Y < logY + 50) {
                setX(logX + 25);
                return true;
            }
        }
        return false;
    }

    public Direction getDirection() {
        return direction;
    }

    void Stop()
    {
        if(state != State.IDLE) {
            DX = 0;
            DY = 0;
            state = State.IDLE;
        }
    }

    public boolean CanCollect(Apple a)
    {
        if(a.getState() != Apple.State.IDLE)
            return false;
        float dx = X - a.getX();
        float dy = Y - a.getY();
        float d = dx*dx + dy*dy;
        return (d <= 256);
    }
}
