package com.zarokhan.wavegunner.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.MilitaryBase.MilitaryBase;
import com.zarokhan.wavegunner.Zombies.ZombieManager;
import com.zarokhan.wavegunner.Zombies.Zombie.Phase;
import com.zarokhan.wavegunner.bg.Background;
import com.zarokhan.wavegunner.gameobjects.Turret;
import com.zarokhan.wavegunner.store.GameStore;
import com.zarokhan.wavegunner.utilities.Button;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.HUD;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.utilities.SoundManager;
import com.zarokhan.wavegunner.utilities.Wallet;

public class GameState extends AbstractState {
	
	private SoundManager sound;
	private OrthographicCamera camera;
	private Background bg;
	private MilitaryBase base;
	private Turret turret;
	private ZombieManager zombies;
	
	private HUD hud;
	private Wallet wallet;
	private GameStore store;
	
	private Button btnMenu, btnStore;
	
	public GameState(StateManager states, ResourceManager res, SoundManager sound, OrthographicCamera camera) {
		super(states, res);
		this.camera = camera;
		this.sound = sound;
		bg = new Background(res);
		base = new MilitaryBase(res);
		turret = new Turret(res, sound, camera);
		base.setPoint(new Vector2(MyGame.WIDTH/2 - 60, turret.getCenterPosition().y));
		zombies = new ZombieManager(states, res, sound, base, 10, 1.2f);
		wallet = new Wallet();
		
		hud = new HUD(res, base, wallet);
		store = new GameStore(res, sound, wallet, turret, base);
		
		btnMenu = new Button(res.menu, res.menu);
		btnMenu.setPosition(new Vector2(0, MyGame.HEIGHT - btnMenu.getHeight()));
		btnStore = new Button(res.shop, res.shop);
		btnStore.setPosition(new Vector2(MyGame.WIDTH - btnStore.getWidth(), -20));
	}

	@Override
	public void update(float delta) {
		// If the wave is not active
		if(!zombies.isWaveActive()){
			// Store btn
			if(btnStore.update(delta)){
				sound.playSelect();
				store.activateStore();
			}
			// Update store
			store.update(delta);
		}
		
		// Buttons
		if(btnMenu.update(delta)){
			sound.playSelect();
			states.setState(State.Menu);
		}
		
		turret.update(delta, zombies.isWaveActive());
		zombies.update(delta);
		
		// Bullet and Zombie loops
		for(int i = 0; i < turret.getBullets().size(); i++){
			for(int j = 0; j < zombies.getZombies().size(); j++){
				if(zombies.getZombies().get(j).getPhase() == Phase.DeathPhase)
					continue;
				
				// If the hitboxes intersects each other
				if(turret.getBullets().get(i).getHitbox().overlaps(zombies.getZombies().get(j).getHitbox())){
					int id = zombies.getZombies().get(j).getID();
					boolean hasBeenHit = false;
					for(int k : turret.getBullets().get(i).getHitTargets()){
						if(k == id)
							hasBeenHit = true;
					}
					if(!hasBeenHit){
						zombies.getZombies().get(j).takeDamage(turret.getDamage());
						turret.getBullets().get(i).addHitTarget(id);
						wallet.AddMoney(5);
					}
				}
			}
		}

		// Returns true if the base is dead.
		if(base.update(delta)){
			
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// Draws the bg
		bg.render(batch);
		// Render Zombies
		zombies.render(batch);
		// Render turret
		turret.render(batch);
		// Draws dark bg
		if(zombies.isWaveActive()){
			if(turret.getMuzzleFlashActive())
				batch.draw(res.lighteffect, turret.getBarrelTip().x - res.lighteffect.getWidth()/2, turret.getBarrelTip().y - res.lighteffect.getHeight()/2);
			else
				batch.draw(res.darkbg, 0, 0);
		}
		
		// HUD
		hud.render(batch);
		// Draws buttons
		btnMenu.render(batch);
		
		// Renders stuff when wave is not active
		if(!zombies.isWaveActive()){
			btnStore.render(batch);
			store.render(batch);
		}
	}
	
	public int getZombiesKilled(){
		return zombies.getTotalZombiesSpawned();
	}
	
	public int getCurrentWave(){
		return zombies.getCurrentWave();
	}
}
