package com.zarokhan.wavegunner.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.Zombies.ZombieManager;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class NewWaveState extends AbstractState {
	
	private int currentWave;
	private int zombiesKilled;

	private float timer;

	public NewWaveState(StateManager states, ResourceManager res) {
		super(states, res);
	}
	
	@Override
	public void update(float delta) {
		timer -= delta;
		
		if(timer <= 0)
			states.setState(State.Game);
	}

	@Override
	public void render(SpriteBatch batch) {
		String msg = "Wave " + currentWave;
		float width = res.fontWhite.getBounds(msg).width;
		float height = res.fontWhite.getBounds(msg).height;
		Vector2 p = new Vector2((MyGame.WIDTH - width)/2, (MyGame.HEIGHT - height)/2 + 50);
		res.fontWhite.draw(batch, msg, p.x, p.y);
		String msg2 = "Zombies killed " + zombiesKilled;
		float x2 = (MyGame.WIDTH - res.smallFont.getBounds(msg2).width)/2;
		res.smallFont.draw(batch, msg2, x2, p.y - 50);
	}
	
	public void setZombiesKilled(int zombiesKilled) {
		this.zombiesKilled = zombiesKilled;
	}
	
	public void setTimer(float timer) {
		this.timer = timer;
	}

	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}
}
