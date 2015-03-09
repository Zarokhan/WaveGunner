package com.zarokhan.wavegunner.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ResourceManager {
	
	public Texture grass, floor, barrels, jeep, bullet, zombie, dot, darkbg, healthbar, healthbarforeground;
	public Texture healthbarbg, turret1, gunman, turretfoundation, hylsa, lighteffect, play, playPressed;
	public Texture nextwave, activeNextWave, bg, dollar, logo, menu, storeHUD, HUD;
	public Texture damage, rapid, mag;
	
	// Texture buttons
	public Texture btnStore, close, healthcap, repair, ammo;
	
	public Texture[] muzzle;
	public Sound shot, basshot, music, music2;
    public BitmapFont fontWhite, blackFont, dollarFont, smallFont;
	
	public ResourceManager(){
		// Textures
		grass = new Texture("grass.png");
		floor = new Texture("floor.jpg");
		barrels = new Texture("barrels.png");
		jeep = new Texture("jeep.png");
		bullet = new Texture(Gdx.files.internal("images/bullet2.png"));
		zombie = new Texture("zombie2.png");
		darkbg = new Texture(Gdx.files.internal("images/darkbg.png"));
		healthbar = new Texture("healthbar.png");
		healthbarbg = new Texture("healthbarbg.png");
		healthbarforeground = new Texture("foreground.png");
		turret1 = new Texture("images/turrets/turret2.png");
		gunman = new Texture("gunman2.png");
		turretfoundation = new Texture("images/turrets/base1.png");
		hylsa = new Texture(Gdx.files.internal("images/hylsa.png"));
		bg = new Texture("images/bg.png");
		dollar = new Texture("images/dollar.png");
		logo = new Texture(Gdx.files.internal("images/logo.png"));
		menu = new Texture(Gdx.files.internal("images/buttons/menu.png"));
		
		btnStore = new Texture(Gdx.files.internal("images/buttons/store.png"));
		
		storeHUD = new Texture(Gdx.files.internal("images/StoreHud.png"));
		close = new Texture(Gdx.files.internal("images/buttons/close.png"));
		HUD = new Texture(Gdx.files.internal("images/tophud.png"));
		lighteffect = new Texture(Gdx.files.internal("images/muzzleflash/lighteffect.png"));
		
		damage = new Texture(Gdx.files.internal("images/store/damage.png"));
		rapid = new Texture(Gdx.files.internal("images/store/rapid.png"));
		mag = new Texture(Gdx.files.internal("images/store/mag.png"));
		healthcap = new Texture(Gdx.files.internal("images/store/health.png"));
		repair = new Texture(Gdx.files.internal("images/store/repair.png"));
		ammo = new Texture(Gdx.files.internal("images/store/ammo.png"));
		
		muzzle = new Texture[3];
		muzzle[0] = new Texture("images/muzzleflash/1.png");
		muzzle[1] = new Texture("images/muzzleflash/2.png");
		muzzle[2] = new Texture("images/muzzleflash/3.png");
		
		// Buttons
		nextwave = new Texture(Gdx.files.internal("images/buttons/New_Wave.png"));
		activeNextWave = new Texture(Gdx.files.internal("images/buttons/New_Wave_Pressed.png"));
		play = new Texture(Gdx.files.internal("images/buttons/Play.png"));
		playPressed = new Texture(Gdx.files.internal("images/buttons/Play_Pressed.png"));
		
		Pixmap p = new Pixmap(1, 1, Format.RGB565);
		p.setColor(Color.WHITE);
		p.fill();
		dot = new Texture(p);

		shot = Gdx.audio.newSound(Gdx.files.internal("sound/Gun2.wav"));
		basshot = Gdx.audio.newSound(Gdx.files.internal("sound/BasEcho.wav"));
		music = Gdx.audio.newSound(Gdx.files.internal("sound/thriller.mp3"));
		music2 = Gdx.audio.newSound(Gdx.files.internal("sound/music2.mp3"));
		
        fontWhite = new BitmapFont(Gdx.files.internal("font/ArialWhite70.fnt"));
        dollarFont = new BitmapFont(Gdx.files.internal("font/font-dollar.fnt"));
        smallFont = new BitmapFont(Gdx.files.internal("font/ArialWhiteSmall.fnt"));
	}
}
