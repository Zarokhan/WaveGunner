package com.zarokhan.wavegunner.bg;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.visualobjects.Barrels;
import com.zarokhan.wavegunner.visualobjects.Jeep;

public class Background {

	private ResourceManager res;
	private Barrels barrels;
	private Jeep jeep;
	
	public Background(ResourceManager res) {
		this.res = res;
		barrels = new Barrels(res, new Vector2(MyGame.WIDTH * 0.1f, 800));
		jeep = new Jeep(res, new Vector2(MyGame.WIDTH * 0.8f, 725), -80);

	}

	public void update(float delta) {
		
	}

	public void render(SpriteBatch batch) {
		// Draws grass background
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 4; x++){
				batch.draw(res.grass, x * res.grass.getWidth(), y * res.grass.getHeight());
			}
		}
		batch.draw(res.bg, 0, 550);
		barrels.render(batch);
		jeep.render(batch);
	}
	
}
