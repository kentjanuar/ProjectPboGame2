package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen, InputProcessor {
    HelloWorldGame parentGame;
    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    AssetManager assetManager;
    BitmapFontCache textLoading, textAnyKey;

    Texture background_blue;

    public LoadingScreen() {
        parentGame = (HelloWorldGame) Gdx.app.getApplicationListener();
        assetManager = parentGame.getAssetManager();

        background_blue = assetManager.get("Blue.png");

        camera = new OrthographicCamera(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        camera.setToOrtho(true, HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        viewport = new FitViewport(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT, camera);

        textLoading = new BitmapFontCache(((HelloWorldGame)parentGame).getLoadingfont());
        textLoading.setColor(Color.WHITE);
        textLoading.setPosition(0, 0);
        textLoading.setText("Loading 0%", 280, 220);

        textAnyKey = new BitmapFontCache(((HelloWorldGame)parentGame).getLoadingfont());
        textAnyKey.setColor(Color.WHITE);
        textAnyKey.setPosition(0, 0);
        textAnyKey.setText("Press Any Key To Continue", 190, 260);
        textAnyKey.setAlphas(0);


        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        draw();
        batch.end();
        processInput();
        updates();
    }

    public void draw()
    {
        for(int i=0; i<8; i++) {
            for (int j = 0; j < 10; j++)
                batch.draw(background_blue, j * 64, i * 64);
        }

        textLoading.draw(batch);
        textAnyKey.draw(batch);
    }

    public void updates()
    {
        if(assetManager.update())
        {
            textAnyKey.setAlphas(1);
        }
        String progress = String.format("Loading %.0f%%", assetManager.getProgress() * 100);
        textLoading.setText(progress, 250, 220);
    }

    public void processInput()
    {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(assetManager.isFinished())
            parentGame.setScreen(new MenuScreen());
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

