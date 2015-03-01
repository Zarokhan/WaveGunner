package com.zarokhan.wavegunner.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class StateManager {
	
	private OrthographicCamera camera;
	private State state;
	private ResourceManager res;
	
	private GameState game;
	private MenuState menu;
	private NewWaveState nextWave;
	
	public StateManager(OrthographicCamera camera){
		this.camera = camera;
		res = new ResourceManager();
		
		game = new GameState(this, res, camera);
		menu = new MenuState(this, res);
		nextWave = new NewWaveState(this, res);
		
		setState(State.Menu);
	}
	
	public void setState(State state){
		this.state = state;
		stopAllMusic();
		switch(state){
		case Game:
			//res.music2.loop(0.1f);
			break;
		case Menu:
			menu.clearZombies();
			//res.music.loop(0.1f);
			break;
		case NextWaveCutScene:
			nextWave.setTimer(5);
			nextWave.setZombiesKilled(game.getZombiesKilled());
			nextWave.setCurrentWave(game.getCurrentWave());
			break;
		}
	}
	
	public void stopAllMusic(){
		res.music.stop();
		res.music2.stop();
	}
	
	public void update(float delta){
		switch(state){
		case Game:
			game.update(delta);
			break;
		case Menu:
			menu.update(delta);
			break;
		case NextWaveCutScene:
			nextWave.update(delta);
			break;
		default:
			break;
		}
	}
	
	public void render(SpriteBatch batch){
		switch(state){
		case Game:
			game.render(batch);
			break;
		case Menu:
			menu.render(batch);
			break;
		case NextWaveCutScene:
			nextWave.render(batch);
			break;
		default:
			break;
		
		}
	}
}
