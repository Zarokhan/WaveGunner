package com.zarokhan.wavegunner.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public abstract class AbstractState {
	
	protected ResourceManager res;
	protected StateManager states;
	
	public AbstractState(StateManager states, ResourceManager res){
		this.res = res;
		this.states = states;
	}
	
	public abstract void update(float delta);
	
	public abstract void render(SpriteBatch batch);
}
