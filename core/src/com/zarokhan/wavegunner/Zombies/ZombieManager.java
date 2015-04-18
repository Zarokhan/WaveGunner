package com.zarokhan.wavegunner.Zombies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.MilitaryBase.MilitaryBase;
import com.zarokhan.wavegunner.States.State;
import com.zarokhan.wavegunner.States.StateManager;
import com.zarokhan.wavegunner.Zombies.Zombie.Phase;
import com.zarokhan.wavegunner.utilities.Button;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.utilities.SoundManager;

public class ZombieManager {
	
	private ResourceManager res;
	private SoundManager sound;
	private StateManager states;
	private Random rnd;
	private List<Zombie> zombie;
	
	private MilitaryBase base;
	
	// Wave Properties
	private int currentWave;
	private int zombiesEachWave;
	private float expansionRate;
	
	// Current Wave Properties
	private float timer;
	private float spawnTime;
	private int totalZombiesSpawned;
	private int zombiesSpawned;
	private boolean waveActive;
	
	// Zombie additonal properties
	private float speed;
	
	// Buttons
	private Button btnNextWave;
	
	public ZombieManager(StateManager states, ResourceManager res, SoundManager sound, MilitaryBase base, int startZombies, float expansionRate) {
		this.states = states;
		this.res = res;
		this.sound = sound;
		this.base = base;
		rnd = new Random();
		zombie = new ArrayList<Zombie>();
		speed = 60;
		this.zombiesEachWave = startZombies;
		this.expansionRate = expansionRate;
		spawnTime = 0.7f;
		btnNextWave = new Button(res.nextwave, res.nextwave);
		btnNextWave.setPosition(new Vector2((MyGame.WIDTH - btnNextWave.getWidth())/2, btnNextWave.getHeight() * 1.5f));
	}
	
	public void zombieHit(int index, int damage){
		zombie.get(index).takeDamage(damage);
	}
	
	public List<Zombie> getZombies(){
		return zombie;
	}
	
	public void clearAllZombies(){
		zombie = new ArrayList<Zombie>();
	}
	
	private void addZombie(){
		// Randomize zombie spawn
		Vector2 p = Vector2.Zero;
		switch(rnd.nextInt(5)){
			// Left side
			case 0:
				p.x = -128;
				p.y = rnd.nextInt(MyGame.HEIGHT/2 - 300) - 100;
				break;
			// Right side
			case 1:
				p.x = MyGame.WIDTH;
				p.y = rnd.nextInt(MyGame.HEIGHT/2 - 300) - 100;
				break;
			// lower left side
			case 2:
				p.x = rnd.nextInt((int)(MyGame.WIDTH/3));
				p.y = -100;
				break;
			// lower middle side
			case 3:
				p.x = (int)(MyGame.WIDTH/3) + rnd.nextInt((int)(MyGame.WIDTH/3));
				p.y = -100;
				break;
			// lower right side
			case 4:
				p.x = (int)(MyGame.WIDTH/3) * 2 + rnd.nextInt((int)(MyGame.WIDTH/3));
				p.y = -100;
				break;
		}
		
		// Adds zombie
		
		zombie.add(new Zombie(res, sound, totalZombiesSpawned, new Vector2(p.x, p.y), base.getPoint(), 1f + rnd.nextFloat() * 0.3f, base.getRadius(), speed));
	}
	
	public void menuMode(){
		speed = 100;
		spawnTime = 3f;
	}
	
	public void startNextWave(){
		clearAllZombies();
		waveActive = true;
		currentWave++;
	}
	
	public void update(float delta) {
		if(!waveActive && btnNextWave.update(delta)){
			sound.playSelect();
			sound.playMusic(SoundManager.MusicType.night);
			startNextWave();
			states.setState(State.NextWaveCutScene);
		}
		
		if(waveActive){
			timer += delta;
			if(timer >= spawnTime && zombiesSpawned < zombiesEachWave){
				timer = 0;
				addZombie();
				totalZombiesSpawned++;
				zombiesSpawned++;
			}	
		}
		
		// Update Zombies
		for(int i = 0; i < zombie.size(); i++){
			zombie.get(i).update(delta, base);
		}
		
		// Check if we should end round
		if(waveActive && zombiesSpawned >= zombiesEachWave){
			boolean allDead = false;
			for(int i = 0; i < zombie.size(); i++){
				if(zombie.get(i).getPhase() != Phase.DeathPhase)
					allDead = true;
			}
			if(!allDead){
				sound.playMusic(SoundManager.MusicType.day);
				waveActive = false;
				zombiesSpawned = 0;
				zombiesEachWave = (int)(zombiesEachWave * expansionRate);
				speed += 5;
			}
		}
	}

	public void render(SpriteBatch batch) {
		for(Zombie z : zombie){
			z.render(batch);
		}
		if(!waveActive)
			btnNextWave.render(batch);
	}
	
	public int getTotalZombiesSpawned() {
		return totalZombiesSpawned;
	}
	
	public int getCurrentWave(){
		return currentWave;
	}
	
	public boolean isWaveActive(){
		return waveActive;
	}
}
