package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class MovingObject extends Object {
    enum State {
        IDLE,
        RUN,
    }
    enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    State state = State.IDLE;
    Direction animationDirection = Direction.RIGHT;
    Direction direction = Direction.RIGHT;
    float DX, DY;
    Animation<TextureRegion> idleLeftAnimation, runLeftAnimation, idleRightAnimation, runRightAnimation;

    @Override
    public void draw(SpriteBatch batch)
    {
        TextureRegion currentFrame = null;
        if(state == State.RUN && animationDirection == Direction.LEFT)
            currentFrame = runLeftAnimation.getKeyFrame(stateTime, true);
        else if(state == State.RUN && animationDirection == Direction.RIGHT)
            currentFrame = runRightAnimation.getKeyFrame(stateTime, true);
        else if(state == State.IDLE && animationDirection == Direction.LEFT)
            currentFrame = idleLeftAnimation.getKeyFrame(stateTime, true);
        else if(state == State.IDLE && animationDirection == Direction.RIGHT)
            currentFrame = idleRightAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, X-16, Y-16);
    }
}
