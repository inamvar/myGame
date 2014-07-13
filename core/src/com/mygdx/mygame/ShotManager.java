package com.mygdx.mygame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ShotManager {

	private static final int SHOT_Y_OFFSET = 90;
	private static final int SHOT_SPEED = 300;
	private Texture shotTexture;
	private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>();

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
			
		}
		
	}

	private boolean canFireShot() {
		
		return true;
	}

	public void update() {
		for(AnimatedSprite shot : shots)
		{
			shot.move();
		}
		
	}

	public  void draw(SpriteBatch batch) {
	 for( AnimatedSprite shot : shots)
	 {
		 shot.draw(batch);
	 }
		
	}



	
}
