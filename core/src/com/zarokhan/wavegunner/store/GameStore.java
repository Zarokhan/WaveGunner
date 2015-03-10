package com.zarokhan.wavegunner.store;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.entity.MilitaryBase;
import com.zarokhan.wavegunner.gameobjects.Turret;
import com.zarokhan.wavegunner.utilities.Button;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.utilities.SoundManager;
import com.zarokhan.wavegunner.utilities.Wallet;

public class GameStore {
	
	private SoundManager sound;
	private ResourceManager res;
	private Wallet wallet;
	private Turret pturret;
	private MilitaryBase base;
	
	private GameObject turret, barrels, storeHUD;
	// Upgrade buttons
	private Button damage, rapidfire, healthcap, repair, btnClose;
	
	// Prices
	private int dmgCost, rpmCost, hpCost, repairCost;
	// If the store is active
	private boolean active;
	
	public GameStore(ResourceManager res, SoundManager sound, Wallet wallet, Turret playerTurret, MilitaryBase base){
		this.res = res;
		this.sound = sound;
		this.wallet = wallet;
		this.pturret = playerTurret;
		this.base = base;
		
		storeHUD = new GameObject(res.storeHUD);
		storeHUD.setPos(new Vector2((MyGame.WIDTH - storeHUD.getWidth())/2, (MyGame.HEIGHT - storeHUD.getHeight())/2));
		btnClose = new Button(res.close, res.close);
		btnClose.setPosition(new Vector2(storeHUD.getPos().x + storeHUD.getWidth() -  btnClose.getWidth(), storeHUD.getPos().y + storeHUD.getHeight() - btnClose.getHeight()));
		
		// Turret for store visuals
		turret = new GameObject(res.turret1);
		turret.setScale(0.3f);
		turret.setRotation(-90);
		turret.setPos(new Vector2(storeHUD.getPos().x, storeHUD.getPos().y + 200 - 100));
		// Barrels for visuals
		barrels = new GameObject(res.barrels);
		barrels.setRotation(150);
		barrels.setScale(0.8f);
		barrels.setPos(new Vector2(storeHUD.getPos().x + storeHUD.getWidth()/2 + 100, storeHUD.getPos().y + storeHUD.getHeight() * 0.7f - 100));
		
		// damage button
		damage = new Button(res.damage, res.damage);
		damage.setPosition(new Vector2(storeHUD.getPos().x + 50, storeHUD.getPos().y + storeHUD.getHeight() - 400));
		// rapid fire button
		rapidfire = new Button(res.rapid, res.rapid);
		rapidfire.setPosition(new Vector2(storeHUD.getPos().x + 50, storeHUD.getPos().y + storeHUD.getHeight() - 300 - rapidfire.getHeight() - 130));
		// health cap button
		healthcap = new Button(res.healthcap, res.healthcap);
		healthcap.setPosition(new Vector2(storeHUD.getPos().x + storeHUD.getWidth()/2, storeHUD.getPos().y + storeHUD.getHeight() - 400));
		// repair button
		repair = new Button(res.repair, res.repair);
		repair.setPosition(new Vector2(storeHUD.getPos().x + storeHUD.getWidth()/2, storeHUD.getPos().y + storeHUD.getHeight() - 400 - rapidfire.getHeight()));
		
		// Pricing
		dmgCost = 100;
		rpmCost = 100;
		hpCost = 100;
		repairCost = 100;
	}
	
	public boolean isStoreActive(){
		return active;
	}
	
	public void activateStore(){
		active = true;
	}
	
	public void deactivateStore(){
		active = false;
	}
	
	public void update(float delta){
		// If the store is active
		if(active){
			// btn Close store
			if(btnClose.update(delta)){
				sound.playSelect();
				active = false;
			}
			
			// btn damage
			if(damage.update(delta) && wallet.getBalance() >= dmgCost){
				sound.playUpgrade();
				pturret.setDamage((int)(pturret.getDamage() + 5));
				wallet.purchase(dmgCost);
				dmgCost += 40;
			}
			// btn rate of fire
			if(rapidfire.update(delta) && wallet.getBalance() >= rpmCost){
				sound.playUpgrade();
				pturret.setRateFire(pturret.getRateFire() * 0.90f);
				wallet.purchase(rpmCost);
				rpmCost += 40;
			}
			// btn hp
			if(healthcap.update(delta) && wallet.getBalance() >= hpCost){
				sound.playUpgrade();
				base.setMaxHealth(base.getMaxHealth() * 1.2f);
				wallet.purchase(hpCost);
				hpCost += 30;
			}
			// btn repair
			if(repair.update(delta) && wallet.getBalance() >= repairCost){
				sound.playUpgrade();
				if(base.getHealth() != base.getMaxHealth()){
					if(base.getHealth() + 100 > base.getMaxHealth())
						base.setHealth(base.getMaxHealth());
					else
						base.setHealth(base.getHealth() + 100);
					
					wallet.purchase(repairCost);
				}
			}
		}
	}
	
	public void render(SpriteBatch batch){
		if(active){
			// renders store background/ui
			storeHUD.render(batch);
			// renders store headline
			batch.draw(res.shopLogo, storeHUD.getPos().x + (storeHUD.getWidth() - res.shopLogo.getWidth())/2, storeHUD.getPos().y + storeHUD.getHeight() - res.shopLogo.getHeight() - 20);
			// renders exit icon
			btnClose.render(batch);
			// renders store visuals
			turret.render(batch);
			barrels.render(batch);
			// renders weapon damage btn
			damage.render(batch);
			res.smallFont.draw(batch, "Weapon DMG: " + pturret.getDamage() + " Cost: " + dmgCost, damage.getPos().x + 20, damage.getPos().y);
			// renders rapid fire btn
			rapidfire.render(batch);
			res.smallFont.draw(batch, "RPM: " + (int)(60/pturret.getRateFire()) + " Cost: " + rpmCost, rapidfire.getPos().x + 20, rapidfire.getPos().y + 20);
			//res.smallFont.draw(batch, "Current magazine size: " + , x, y)
			
			// renders base health text
			healthcap.render(batch);
			res.smallFont.draw(batch, "Health cap: " + (int)base.getMaxHealth() + " Cost: " + hpCost, healthcap.getPos().x + 20, healthcap.getPos().y + 10);
			// renders base repair
			repair.render(batch);
			res.smallFont.draw(batch, "+ 100 hp Cost: " + repairCost, repair.getPos().x + 20, repair.getPos().y);
		}
	}
}
