package com.zarokhan.wavegunner.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.utilities.SoundManager;

public class StateManager {
	
	private OrthographicCamera camera;
	private State state;
	private ResourceManager res;
	private SoundManager sound;
	
	private GameState game;
	private MenuState menu;
	private NewWaveState nextWave;
	
	public StateManager(OrthographicCamera camera){
		this.camera = camera;
		res = new ResourceManager();
		sound = new SoundManager(res);
		
		game = new GameState(this, res, sound, camera);
		menu = new MenuState(this, sound, res);
		nextWave = new NewWaveState(this, res);
		
		sound.playMusic(SoundManager.MusicType.night);
		
		setState(State.Menu);
	}
	
	public void setState(State state){
		this.state = state;
		switch(state){
		case Game:
			break;
		case Menu:
			menu.clearZombies();
			break;
		case NextWaveCutScene:
			nextWave.setTimer(3);
			nextWave.setZombiesKilled(game.getZombiesKilled());
			nextWave.setCurrentWave(game.getCurrentWave());
			break;
		}
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
