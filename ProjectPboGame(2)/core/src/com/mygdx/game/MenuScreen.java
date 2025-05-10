package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen, InputProcessor {
    HelloWorldGame parentGame;
    SpriteBatch batch;
    OrthographicCamera camera, stageCamera;
    Viewport viewport;
    AssetManager assetManager;

    Stage stage;
    Label optionSoundLabel;
    TextButton playButton, optionButton, optionDoneButton;
    Window optionWindow;

    Texture background;

    InputMultiplexer multiInput;

    public MenuScreen() {
        parentGame = (HelloWorldGame) Gdx.app.getApplicationListener();
        assetManager = parentGame.getAssetManager();

        background = assetManager.get("backgroundmenu.png");

        camera = new OrthographicCamera(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        camera.setToOrtho(false, HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        viewport = new FitViewport(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        batch = new SpriteBatch();

        stageCamera = new OrthographicCamera(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);

        playButton = new TextButton("Play", mySkin);
        playButton.setHeight(64);
        playButton.setWidth(180);
        playButton.setPosition( (HelloWorldGame.WORLD_WIDTH - playButton.getWidth())/ 2, 200);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("test");
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    dispose();
                    parentGame.setScreen(new GameScreen());
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        optionButton = new TextButton("Option", mySkin);
        optionButton.setHeight(48);
        optionButton.setWidth(150);
        optionButton.setPosition((HelloWorldGame.WORLD_WIDTH - optionButton.getWidth())/ 2, 140);
        optionButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);

        optionWindow = new Window("Option", mySkin);
        optionWindow.setHeight(320);
        optionWindow.setWidth(480);
        optionWindow.setPosition((HelloWorldGame.WORLD_WIDTH - optionWindow.getWidth())/ 2, (HelloWorldGame.WORLD_HEIGHT - optionWindow.getHeight())/ 2);
        optionWindow.setMovable(true);
        optionWindow.setModal(true);
        optionWindow.setVisible(false);
        optionWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(optionWindow);

        optionSoundLabel = new Label("Music Volume :", mySkin);
        optionSoundLabel.setAlignment(Align.right);
        optionSoundLabel.setY(250);
        optionSoundLabel.setX(0);
        optionSoundLabel.setPosition(optionWindow.getWidth() / 4, 240);
        optionWindow.addActor(optionSoundLabel);

        final Slider volumeSlider = new Slider(0, 100, 1, false, mySkin);
        volumeSlider.setValue(parentGame.getMusicVolume());
        volumeSlider.setWidth(optionWindow.getWidth() / 2);
        volumeSlider.setPosition(optionWindow.getWidth() / 4, 210);
        optionWindow.addActor(volumeSlider);

        optionDoneButton = new TextButton("Done",mySkin);
        optionDoneButton.setWidth(120);
        optionDoneButton.setHeight(36);
        optionDoneButton.setPosition(optionWindow.getWidth()/2-optionDoneButton.getWidth()/2,72);
        optionDoneButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight())
                {
                    parentGame.setMusicVolume(volumeSlider.getValue());
                    optionWindow.setVisible(false);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        optionWindow.addActor(optionDoneButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
        parentGame.playMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        draw();
        batch.end();
        stage.act();
        stage.draw();
    }

    public void draw() {
        batch.draw(background, 0, 0);
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
