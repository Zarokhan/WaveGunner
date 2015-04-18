package com.zarokhan.wavegunner.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.MilitaryBase.MilitaryBase;
import com.zarokhan.wavegunner.Zombies.ZombieManager;
import com.zarokhan.wavegunner.bg.Background;
import com.zarokhan.wavegunner.utilities.Button;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.utilities.SoundManager;

public class MenuState extends AbstractState {
	
	private SoundManager sound;
	
	private Background bg;
	private Button btnPlay;
	private MilitaryBase base;
	private ZombieManager zombies;
	
	public MenuState(StateManager states, SoundManager sound, ResourceManager res) {
		super(states, res);
		this.sound = sound;
		// Creat bg object
		bg = new Background(res);
		
		base = new MilitaryBase(res);
		base.setPoint(new Vector2(MyGame.WIDTH/2 - 30, MyGame.HEIGHT - 200));
		base.setRadius(450);
		
		zombies = new ZombieManager(states, res, sound, base, 100, 0);
		zombies.menuMode();
		zombies.startNextWave();
		
		// Creates buttons
		btnPlay = new Button(res.play, res.play);
		btnPlay.setPosition(new Vector2((MyGame.WIDTH - btnPlay.getWidth())/2, 300));
	}
	
	public void clearZombies(){
		zombies.clearAllZombies();
	}

	@Override
	public void update(float delta) {
		zombies.update(delta);
		
		if(btnPlay.update(delta)){
			sound.playSelect();
			states.setState(State.Game);
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// Draws bg
		bg.render(batch);
		zombies.render(batch);
		
		batch.draw(res.darkbg, 0, 0);
		
		// Draws logo
		batch.draw(res.logo, (MyGame.WIDTH - res.logo.getWidth())/2, MyGame.HEIGHT - res.logo.getHeight() * 1.3f);
		// Draws buttons
		btnPlay.render(batch);
	}

}
