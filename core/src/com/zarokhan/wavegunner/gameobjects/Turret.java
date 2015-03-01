package com.zarokhan.wavegunner.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class Turret extends GameObject {
	
	// Standard class stuff
	private Random rnd;
	private OrthographicCamera camera;
	private ResourceManager res;
	
	// Bullet properties
	private List<Bullet> bullet;
	private float timer, holdTimer, rateFire;

	private int damage;
	
	// Muzzle flash properties
	private int currentMuzzle;
	private float muzzleTimer;
	private GameObject[] muzzle;
	
	// Visual game objects for turret
	private GameObject turretfoundation;
	private GameObject gunman;
	
	// Recoil properties
	private static final float MAX_RECOIL = 4;
	private int recoilDir;
	private float recoil;
	
	// Shells properties
	private List<Shell> shells;
	private static final float MAX_SHELL_ROT_VEL = 10;
	private static final float MAX_SHELL_ADD_SPEED = 300;
	private int shellDir;
	
	public Turret(ResourceManager res, OrthographicCamera camera){
		super(res.turret1);
		this.camera = camera;
		this.res = res;
		rnd = new Random();
		// Bullets.
		bullet = new ArrayList<Bullet>();
		// Properties for the turret.
		scale = 0.4f;
		origin = new Vector2(297, height - 558);
		rotation = 180;
		pos = new Vector2(MyGame.WIDTH/3 + 20, MyGame.HEIGHT - 400);
		muzzleTimer = 5;
		
		damage = 5;
		rateFire = 0.4f;
		
		// Gunman
		gunman = new GameObject(res.gunman);
		gunman.setScale(1.5f);
		// turret foundation
		turretfoundation = new GameObject(res.turretfoundation);
		turretfoundation.setRotation(45);
		turretfoundation.setPos(new Vector2(pos.x + 117, pos.y + 40));
		turretfoundation.setScale(0.8f);
		
		// Muzzle flash
		muzzle = new GameObject[3];
		muzzle[0] = new GameObject(res.muzzle[0]);
		muzzle[1] = new GameObject(res.muzzle[1]);
		muzzle[2] = new GameObject(res.muzzle[2]);
		
		// recoil
		recoilDir = 1;
		
		// shells
		shells = new ArrayList<Shell>();
		shellDir = 1;
	}
	
	public void removeBullet(int index){
		bullet.remove(index);
	}
	
	public List<Bullet> getBullets(){
		return bullet;
	}
	
	private void shoot(float delta){
		timer -= delta;
		if(timer <= 0f){
			// Recoil
			recoil = rnd.nextFloat() * MAX_RECOIL * recoilDir;
			recoilDir = -recoilDir;
			this.rotation += recoil;
			
			// Bullet & Muzzle
			res.shot.play();
			res.basshot.play(0.25f);
			timer = rateFire;
			muzzleTimer = 0;
			bullet.add(new Bullet(res, getBarrelTip(), this.rotation));
			float rotationVel = MAX_SHELL_ROT_VEL * rnd.nextFloat() * shellDir;
			float additionalSpeed = MAX_SHELL_ADD_SPEED * rnd.nextFloat();
			shells.add(new Shell(res, new Vector2(pos.x + origin.x + MathUtils.cosDeg(rotation) * 100, pos.y + origin.y + MathUtils.sinDeg(rotation) * 100), this.rotation, rotationVel, additionalSpeed));
			shellDir = -shellDir;
		}
	}
	
	public Vector2 getBarrelTip(){
		return new Vector2(pos.x + origin.x + MathUtils.cosDeg(rotation + 90) * (width * scale * 1.5f), pos.y + origin.y + MathUtils.sinDeg(rotation + 90) * (width * scale * 1.5f));
	}
	
	public void update(float delta, boolean waveActive){
		/*
		 * Update Logic.
		 */
		muzzleTimer += delta;
		for(int i = 0; i < bullet.size(); i++){
			if(bullet.get(i).update(delta)){
				bullet.remove(i);
			}
		}
		// Update the gunman position
		gunman.setRotation(this.rotation);
		gunman.setPos(new Vector2(pos.x + origin.x -gunman.getWidth()/2 - MathUtils.cosDeg(rotation + 90) * (width * scale * 0.65f), pos.y + origin.y - gunman.getHeight()/2 - MathUtils.sinDeg(rotation + 90) * (width * scale * 0.65f)));
		// Shells update
		for(int i = 0; i < shells.size(); i++){
			if(shells.get(i).update(delta)){
				shells.remove(i);
			}
		}
		
		/*
		 * Input Logic.
		 */
		
		if(Gdx.input.isTouched()){
			float x = Gdx.input.getX() / (float)(Gdx.graphics.getWidth() / camera.viewportWidth);
			float y = MyGame.HEIGHT - Gdx.input.getY() / (float)(Gdx.graphics.getHeight() / camera.viewportHeight);
			
			float deltaX = pos.x + origin.x - x;
			float deltaY = pos.y + origin.y - y;
			float angle = (float)MathUtils.atan2(deltaY, deltaX);

			this.rotation = angle * MathUtils.radDeg + 90;
			
			if(waveActive){
	            holdTimer += delta;
	            if(holdTimer >= 0.2f)
	                shoot(delta);
			}
		}else{
            holdTimer = 0;
        }
	}
	
	@Override
	public void render(SpriteBatch batch){
		// Draws the bullets
		for (Bullet b : bullet) {
			b.render(batch);
		}
		// Draws the muzzle flash
		if(muzzleTimer <= 0.1f){
			Vector2 p = new Vector2(pos.x + origin.x - res.muzzle[0].getWidth()/2 + MathUtils.cosDeg(rotation + 90) * (width * scale * 1.5f), pos.y + origin.y - res.muzzle[0].getHeight()/2 + MathUtils.sinDeg(rotation + 90) * (width * scale * 1.5f));
			muzzle[currentMuzzle].setPos(p);
			muzzle[currentMuzzle].setScale(0.4f);
			muzzle[currentMuzzle].setRotation(this.rotation);
			muzzle[currentMuzzle].render(batch);
			currentMuzzle++;
			if(currentMuzzle >= 3)
				currentMuzzle = 0;
		}
		// Draws turret foundation
		turretfoundation.render(batch);
		// Draws the shells
		for (Shell s : shells){
			s.render(batch);
		}
		// Draws turret
		super.render(batch);
		// Draws the gunman
		gunman.render(batch);
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public float getRateFire() {
		return rateFire;
	}

	public void setRateFire(float rateFire) {
		this.rateFire = rateFire;
	}
	
	public boolean getMuzzleFlashActive(){
		if(muzzleTimer <= 0.1f)
			return true;
		return false;
	}
}
