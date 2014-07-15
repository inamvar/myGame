package com.mygdx.mygame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	private static final float ENEMY_SPEED = 250;
	private Texture enemyTexture;
	private AnimatedSprite animatedSprite;
	private ShotManager shotManager;
	private float spawnTimeout = 0f;

	public Enemy(Texture enemyTexture, ShotManager shotManager) {
		this.enemyTexture = enemyTexture;
		this.shotManager = shotManager;
		spawn();
	}

	private void spawn() {
		Sprite enemySprite = new Sprite(enemyTexture);
		animatedSprite = new AnimatedSprite(enemySprite);
		int xPosition = createRandomXPosition();
		int yPosition = MyGdxGame.SCREEN_HEIGHT - animatedSprite.getHeight();
		// animatedSprite.setPosition(xPosition, );
		animatedSprite.setPosition(xPosition, yPosition);
		animatedSprite.setVelocity(new Vector2(ENEMY_SPEED, 0));
		animatedSprite.setDead(false);
	}

	private int createRandomXPosition() {
		Random random = new Random();
		int randomNumber = random.nextInt(MyGdxGame.SCREEN_WIDTH
				- animatedSprite.getWidth() + 1);
		return randomNumber + animatedSprite.getWidth() / 2;
	}

/*	private int createRandomYPosition() {
		Random random = new Random();
		int randomNumber = random.nextInt(MyGdxGame.SCREEN_HEIGHT
				- animatedSprite.getHeight() + 1);
		return randomNumber + animatedSprite.getHeight() / 2;
	}*/

	public void draw(SpriteBatch batch) {
		if (!animatedSprite.isDead())
			animatedSprite.draw(batch);

	}

	private boolean shouldChangeDirection() {
		Random random = new Random();
		return random.nextInt(21) == 0;
	}

	public void update() {

		if (animatedSprite.isDead()) {
			spawnTimeout -= Gdx.graphics.getDeltaTime();
			if (spawnTimeout < 0) {
				spawn();
			}
		} else {
			if (shouldChangeDirection())
				animatedSprite.changeDirection();
			if (souldFire())
				shotManager.fireEnemyShot(animatedSprite.getX());
			animatedSprite.move();
		}
	}

	private boolean souldFire() {
		Random random = new Random();
		return random.nextInt(31) == 0;
	}

	public Rectangle GetBoundingBox() {
		return animatedSprite.getBoundingBox();
	}

	public void hit() {
		animatedSprite.setDead(true);
		spawnTimeout = 2f;
	}

}
