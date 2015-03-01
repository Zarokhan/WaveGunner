package com.zarokhan.wavegunner.gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class Bullet extends GameObject {
	
	private float speed;
	private float timer, timeDeath;
	private List<Integer> hitTarget;
	
	public Bullet(ResourceManager res, Vector2 pos, float rotation) {
		super(res.bullet);
		pos.x -= width/2;
		pos.y -= height/2;
		this.pos = pos;
		this.rotation = rotation + 90;
		this.scale = 0.5f;
		speed = 1200;
		timeDeath = 3;
		hitbox = new Rectangle(pos.x, pos.y, height, height);
		hitTarget = new ArrayList<Integer>();
	}
	
	public void addHitTarget(int id){
		hitTarget.add(id);
	}
	
	public List<Integer> getHitTargets(){
		return hitTarget;
	}
	
	public boolean update(float delta){
		// Update timer.
		timer += delta;
		// Updates the position of the bullet.
		pos.x += MathUtils.cosDeg(rotation) * speed * delta;
		pos.y += MathUtils.sinDeg(rotation) * speed * delta;
		// Updates the hitbox
		hitbox.x = pos.x + origin.x;
		hitbox.y = pos.y + origin.y;
		// Returns true if the bullet is dead.
		if(timer >= timeDeath)
			return true;
		return false;
	}
}
