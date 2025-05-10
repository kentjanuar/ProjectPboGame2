package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class  HelloWorldGame extends Game {
	public static final int WORLD_WIDTH = 640;
	public static final int WORLD_HEIGHT = 480;

	private float music_volume = 0.5f;
	private Music music;

	AssetManager assetManager;

	BitmapFont loadingfont;

	BitmapFont MenuFont;

	BitmapFont titlefont;

	public BitmapFont getLoadingfont() {
		return loadingfont;
	}

	public BitmapFont getMenuFont() {
		return MenuFont;
	}

	public BitmapFont getTitlefont(){return titlefont;}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load("Blue.png", Texture.class);
		assetManager.finishLoading();

		assetManager.load("backgroundmenu.png", Texture.class);
		assetManager.load("Green.png", Texture.class);
		assetManager.load("Red.png", Texture.class);
		assetManager.load("IdlePlayer.png", Texture.class);
		assetManager.load("sandfix.png",Texture.class);
		assetManager.load("RunPlayer.png", Texture.class);
		assetManager.load("Apple.png", Texture.class);
		assetManager.load("Collected.png", Texture.class);
		assetManager.load("bgm.mp3", Music.class);
		assetManager.load("collect.wav", Sound.class);
		assetManager.load("RunEnemy.png", Texture.class);
		assetManager.load("IdleEnemy.png", Texture.class);
		assetManager.load("background.jpg",Texture.class);


		//loading font tanpa asset Manager
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("VT323-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		parameter.flip = true;
		loadingfont = generator.generateFont(parameter);
		generator.dispose();

		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("LeagueSpartan-Bold.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.size = 24;
		parameter2.flip = true;
		MenuFont = generator2.generateFont(parameter2);
		generator2.dispose();

		FreeTypeFontGenerator generator3 = new FreeTypeFontGenerator(Gdx.files.internal("title.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter3.size = 24;
		parameter3.flip = true;
		titlefont = generator3.generateFont(parameter3);
		generator3.dispose();


		//set loader untuk asset manager
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		// load font dengan asset manager
		// definisi parameter
		FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		mySmallFont.fontFileName = "VT323-Regular.ttf"; // nama file asli
		mySmallFont.fontParameters.size = 32;
		mySmallFont.fontParameters.flip = true;
		//load font dengan asset manager
		assetManager.load("font-small.ttf", BitmapFont.class, mySmallFont); // identifier di asset Manager

		SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("uiskin.atlas");
		assetManager.load("uiskin.json", Skin.class, skinParam);

		this.setScreen(new LoadingScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		assetManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public static TextureRegion[] CreateAnimationFrames(Texture tex, int frameWidth, int frameHeight, int frameCount, boolean flipx, boolean flipy)
	{
		TextureRegion[][] tmp = TextureRegion.split(tex,frameWidth, frameHeight);
		TextureRegion[] frames = new TextureRegion[frameCount];
		int index = 0;
		int row = tex.getHeight() / frameHeight;
		int col = tex.getWidth() / frameWidth;
		for (int i = 0; i < row && index < frameCount; i++) {
			for (int j = 0; j < col && index < frameCount; j++) {
				frames[index] = tmp[i][j];
				frames[index].flip(flipx, flipy);
				index++;
			}
		}
		return frames;
	}

	public void playMusic()
	{
		if(music == null) {
			music = assetManager.get("bgm.mp3", Music.class);
			music.setVolume(music_volume);
		}
		music.setLooping(true);
		if(!music.isPlaying())
			music.play();
	}
	public void setMusicVolume(float volume) {
		music_volume = volume / 100.f;
		music.setVolume(music_volume);
	}
	public void stopMusic() {
		if(music.isPlaying()) music.stop();
	}
	public float getMusicVolume() {
		return music_volume * 100.0f;
	}
}
