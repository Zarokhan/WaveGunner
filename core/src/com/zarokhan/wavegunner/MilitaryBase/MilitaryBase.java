package com.zarokhan.wavegunner.MilitaryBase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class MilitaryBase {
	
	private float health, maxHealth;
	private Vector2 point;
	private float radius;
	private float fullWidth;
	
	private GameObject healthbar;
	private GameObject healthbarbg;
	private GameObject foreground;
	
	public MilitaryBase(ResourceManager res){
		maxHealth = 100f;
		health = maxHealth;
		point = Vector2.Zero;
		radius = 450f;
		
		healthbar = new GameObject(res.healthbar);
		foreground = new GameObject(res.healthbarforeground);
		healthbarbg = new GameObject(res.healthbarbg);
		
		Vector2 p = new Vector2((MyGame.WIDTH - foreground.getWidth())/2, 10);
		foreground.setPos(p);
		healthbar.setPos(new Vector2(p.x, p.y));
		healthbarbg.setPos(p);
		healthbar.setOrigin(new Vector2(0, 0));
		
		fullWidth = healthbar.getWidth();
	}
	
	public boolean update(float delta){
		if(health <= 0)
			return true;
		
		return false;
	}
	
	public void takeDamage(float damage){
		health -= damage;
		healthbar.setSource(new Rectangle(0, 0, fullWidth * health/maxHealth, healthbar.getHeight()));
	}
	
	public void render(SpriteBatch batch){
		healthbarbg.render(batch);
		healthbar.render(batch);
		foreground.render(batch);
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
	public Vector2 getPoint() {
		return point;
	}

	public void setPoint(Vector2 point) {
		float x = point.x;
		float y = point.y;
		this.point = new Vector2(x, y);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
