package com.mygdx.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	public static final int SCREEN_HEIGHT = 480;
	public static final int SCREEN_WIDTH = 800;
	SpriteBatch batch;
	Texture background;
	private OrthographicCamera camera;
	private ShotManager shotManager;
	private AnimatedSprite spaceshipAnimated;
	private Music gameMusic;
	private Enemy enemy;
	private CollisionManager collisionManager;
	private boolean isGameOver = false;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("space-background.png"));
		Texture spaceshipTexture = new Texture(
				Gdx.files.internal("space-ship-aminated.png"));
		Sprite spaceshipSprite = new Sprite(spaceshipTexture);
		spaceshipAnimated = new AnimatedSprite(spaceshipSprite);
		spaceshipAnimated.setPosition(SCREEN_WIDTH / 2, 0);

		Texture shotTexture = new Texture(Gdx.files.internal("shot.png"));
		Texture enemyShotTexture = new Texture(
				Gdx.files.internal("enemy-shot.png"));
		shotManager = new ShotManager(shotTexture, enemyShotTexture);
		Texture enemyTexture = new Texture(Gdx.files.internal("enemy.png"));
		enemy = new Enemy(enemyTexture, shotManager);

		collisionManager = new CollisionManager(spaceshipAnimated, enemy,
				shotManager);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		gameMusic.setVolume(.50f);
		gameMusic.setLooping(true);
		gameMusic.play();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		if (isGameOver) {
			BitmapFont font = new BitmapFont();
			font.setScale(3);
			font.draw(batch, "Player hit!", 300, 300);

		}

		spaceshipAnimated.draw(batch);
		enemy.draw(batch);
		shotManager.draw(batch);
		batch.end();
		
		handleInput();
		
		if (!isGameOver) {
		
			spaceshipAnimated.move();
			enemy.update();
			shotManager.update();

			collisionManager.handleCollisions();
		}
		if (spaceshipAnimated.isDead()) {
			isGameOver = true;
		}

	}

	private void handleInput() {
		Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(),
				0);
		camera.unproject(touchPosition);
		if (Gdx.input.isTouched()) {
				if(isGameOver)
				{
					spaceshipAnimated.setDead(false);
					this.isGameOver = false;
				}
			if (touchPosition.x > spaceshipAnimated.getX()) {
				spaceshipAnimated.moveRight();
			} else {
				spaceshipAnimated.moveLeft();
			}

			shotManager.firePlayerShot(spaceshipAnimated.getX());
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			spaceshipAnimated.moveLeft();
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			spaceshipAnimated.moveRight();
		else if (Gdx.input.isKeyPressed(Input.Keys.UP))
			shotManager.firePlayerShot(spaceshipAnimated.getX());
	}
}
