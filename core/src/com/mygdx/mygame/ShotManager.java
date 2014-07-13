package com.mygdx.mygame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ShotManager {

	private static final int SHOT_Y_OFFSET = 90;
	private static final int SHOT_SPEED = 300;
	private static final float MINIMUM_TIME_BETWEEN_SHOTS = .2f;
	private Texture shotTexture;
	private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>();
	private float timeSinceLastShot = 0 ;
	private Sound lazer = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));

	public ShotManager(Texture shotTexture) {
		this.shotTexture = shotTexture;
		
	}

	public void firePlayerShot(int shiptCenterXLocation) {
		if(canFireShot())
		{
			Sprite newShot = new Sprite(shotTexture);
			AnimatedSprite newShotAnimated = new AnimatedSprite(newShot);
			newShotAnimated.setPosition(shiptCenterXLocation, SHOT_Y_OFFSET);
			newShotAnimated.setVelocity(new Vector2(0,SHOT_SPEED));
			shots.add(newShotAnimated);
			timeSinceLastShot = 0f;
			lazer.play();
		}
		
	}

	private boolean canFireShot() {
		
		return timeSinceLastShot > MINIMUM_TIME_BETWEEN_SHOTS;
	}

	public void update() {
		
		Iterator<AnimatedSprite> i = shots.iterator();
		while(i.hasNext())
		{
			AnimatedSprite shot = i.next();
			shot.move();
			if(shot.getY() > MyGdxGame.SCREEN_HEIGHT)
				i.remove();
		}
		timeSinceLastShot += Gdx.graphics.getDeltaTime();
	}

	public  void draw(SpriteBatch batch) {
	 for( AnimatedSprite shot : shots)
	 {
		 shot.draw(batch);
	 }
		
	}



	
}
