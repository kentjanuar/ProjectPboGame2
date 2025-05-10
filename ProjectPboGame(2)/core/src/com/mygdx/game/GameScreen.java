package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen, InputProcessor {
    HelloWorldGame parentGame;
    AssetManager assetManager;
    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    Texture background_green;
    Texture background_red;
    Player player;
    ArrayList<Apple> appleList;
    ArrayList<Enemy> enemyList;

    ArrayList<Log> logList;

    Random randomizer;
    BitmapFontCache text;

    boolean isGameFinished = false;


    BitmapFont font = new BitmapFont();

    int score;

    public GameScreen() {
        parentGame = (HelloWorldGame) Gdx.app.getApplicationListener();
        assetManager = parentGame.getAssetManager();

        camera = new OrthographicCamera(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        camera.setToOrtho(true, HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT);
        viewport = new FitViewport(HelloWorldGame.WORLD_WIDTH, HelloWorldGame.WORLD_HEIGHT, camera);

        background_green = assetManager.get("Green.png", Texture.class);
        background_red = assetManager.get("Red.png", Texture.class);

        player = new Player();
        player.setX(320);
        player.setY(440);

        randomizer = new Random();

        appleList = new ArrayList<Apple>();
        for (int i = 0; i < 10; i++) {
            Apple a = new Apple();
            Apple b = new Apple();
            a.setX(20 + (float) randomizer.nextInt(600));
            a.setY(250 + (float) randomizer.nextInt(100));

            b.setX(20 + (float) randomizer.nextInt(600));
            b.setY(100 + (float) randomizer.nextInt(150));

            appleList.add(a);
            appleList.add(b);
        }

        enemyList = new ArrayList<Enemy>();
        for (int i = 0; i < 5; i++) {
            Enemy a = new Enemy();
            a.setX(75 + (float) randomizer.nextInt(250));
            a.setY(250 + (float) randomizer.nextInt(100));
            enemyList.add(a);
        }
        for (int i = 0; i < 5; i++) {
            Enemy a = new SpecialEnemy();
            a.setX(75 + (float) randomizer.nextInt(250));
            a.setY(250 + (float) randomizer.nextInt(100));
            enemyList.add(a);
        }

        logList = new ArrayList<Log>();


        Log log1 = new Log();
        Log log2 = new Log();
        Log log3 = new Log();
        Log log4 = new Log();
        Log log5 = new Log();
        log1.setX(0);
        log1.setY(65);
        log2.setX(600);
        log2.setY(100);
        log3.setX(0);
        log3.setY(135);
        log4.setX(600);
        log4.setY(170);
        log5.setX(0);
        log5.setY(210);
        logList.add(log1);
        logList.add(log2);
        logList.add(log3);
        logList.add(log4);
        logList.add(log5);


        text = new BitmapFontCache(assetManager.get("font-small.ttf", BitmapFont.class));
        text.setColor(Color.BLACK);
        text.setPosition(0, 0);
        text.setText("SCORE: 0", 20, 20);
        score = 0;

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        boolean playerIsAlive;
        playerIsAlive = player.checkCollision(player, enemyList);
        if (!player.checkCollisionLog(logList)) {
            playerIsAlive = playerIsAlive && player.checkCollisionBackGround();
        }

        if (playerIsAlive) {
            ScreenUtils.clear(0, 0, 0, 1);
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            draw();
            batch.end();
            processInput();
            updates();
        } else {
            isGameFinished = true;
            batch.begin();
            font.setColor(Color.WHITE);
            font.getData().setScale(2);

            String gameOverText = "!!!  Game Over !!!";
            GlyphLayout layout = new GlyphLayout(font, gameOverText);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            float y = (Gdx.graphics.getHeight() + layout.height) / 2;

            String promptText = "PRESS ENTER TO RETRY, SPACE TO EXIT";
            GlyphLayout layout2 = new GlyphLayout(font, promptText);

            batch.setTransformMatrix(new Matrix4().scale(1, -1, 1).translate(0, -Gdx.graphics.getHeight(), 0)); // Flip vertically
            font.draw(batch, gameOverText, x, y);
            font.draw(batch, promptText, (Gdx.graphics.getWidth() - layout2.width) / 2, (Gdx.graphics.getHeight() + layout2.height) / 2 - 30);
            batch.end();
        }

        if (player.Y <= 20 && score == 200) {
            isGameFinished = true;
            batch.begin();
            font.setColor(Color.WHITE);
            font.getData().setScale(2);

            String winText = "!!!  YOU WIN  !!!";
            GlyphLayout layout = new GlyphLayout(font, winText);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            float y = (Gdx.graphics.getHeight() + layout.height) / 2;

            String promptText = "PRESS ENTER TO RETRY, SPACE TO EXIT";
            GlyphLayout layout2 = new GlyphLayout(font, promptText);

            font.draw(batch, winText, x, y);
            font.draw(batch, promptText, (Gdx.graphics.getWidth() - layout2.width) / 2, (Gdx.graphics.getHeight() + layout2.height) / 2 - 30);

            batch.end();
        }
    }


    public void draw() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++)
                if (i >= 1 && i <= 3.5) {
                    batch.draw(background_red, j * 64, i * 64);
                } else if (i > 3.5) {
                    batch.draw(background_green, j * 64, i * 64);
                } else if (i < 0.5) {
                    batch.draw(background_green, j * 64, i * 64);
                }

        }
        for (Apple a : appleList) {
            a.draw(batch);
        }
        for (Enemy a : enemyList) {
            a.draw(batch);
        }
        for (Log log : logList) {
            log.draw(batch);
        }
        player.draw(batch);
        text.draw(batch);

    }

    public void updates() {
        player.update();
        for (Enemy a : enemyList) {
            a.update();
        }
        for (Log log : logList) {
            log.update();
        }

        Iterator<Apple> iApple = appleList.iterator();
        while (iApple.hasNext()) {
            Apple a = iApple.next();
            a.update();
            if (player.CanCollect(a)) {
                a.collect();
                this.addScore(10);
            }
            if (a.getState() == Apple.State.INACTIVE)
                iApple.remove();
        }
    }

    public void processInput() {

    }

    public void addScore(int add) {
        score += add;
        text.setText("SCORE: " + score, 20, 20);
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
        if (keycode == Input.Keys.LEFT)
            player.SetMove(Player.Direction.LEFT);
        else if (keycode == Input.Keys.RIGHT)
            player.SetMove(Player.Direction.RIGHT);
        else if (keycode == Input.Keys.UP)
            player.SetMove(Player.Direction.UP);
        else if (keycode == Input.Keys.DOWN)
            player.SetMove(Player.Direction.DOWN);

        if(isGameFinished && keycode == Input.Keys.ENTER) {
            parentGame.setScreen(new GameScreen());
        } else if(isGameFinished && keycode == Input.Keys.SPACE) {
            parentGame.setScreen(new MenuScreen());
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT && player.getDirection() == Player.Direction.LEFT)
            player.Stop();
        else if (keycode == Input.Keys.RIGHT && player.getDirection() == Player.Direction.RIGHT)
            player.Stop();
        else if (keycode == Input.Keys.UP && player.getDirection() == Player.Direction.UP)
            player.Stop();
        else if (keycode == Input.Keys.DOWN && player.getDirection() == Player.Direction.DOWN)
            player.Stop();
        return true;
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
