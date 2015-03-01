package com.zarokhan.wavegunner.utilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.entity.MilitaryBase;

public class HUD {
	
	private ResourceManager res;
	private MilitaryBase base;
	private Wallet wallet;
	
	private GameObject hud;
	private GameObject dollar;
	
	public HUD(ResourceManager res, MilitaryBase base, Wallet wallet){
		this.res = res;
		this.base = base;
		this.wallet = wallet;
		
		hud = new GameObject(res.HUD);
		hud.setPos(new Vector2((MyGame.WIDTH - hud.getWidth())/2, MyGame.HEIGHT - hud.getHeight()));
		dollar = new GameObject(res.dollar);
		dollar.setPos(new Vector2(hud.pos.x + dollar.getWidth(), hud.pos.y));
		dollar.setScale(0.7f);
	}
	
	public void update(float delta){
		
	}
	
	public void render(SpriteBatch batch){
		// renders top dark bg
		hud.render(batch);
		// renders cash balance
		dollar.render(batch);
		res.dollarFont.draw(batch, "" + wallet.getBalance(), dollar.pos.x + dollar.width, hud.pos.y + (hud.height - res.dollarFont.getBounds("Y").height));
		
		// draws lower hud
		base.render(batch); // draws health
	}
}
