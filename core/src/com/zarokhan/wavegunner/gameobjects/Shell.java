package com.zarokhan.wavegunner.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class Shell extends GameObject{
	
	private float dirRotation;
	private float speed;
	private float timer;
	private float rotationVel;
	
	public Shell(ResourceManager res, Vector2 pos, float dirRotation, float rotationVel, float additionalSpeed) {
		super(res.hylsa);
		this.pos = pos;
		this.dirRotation = dirRotation;
		this.rotation = dirRotation + 90;
		this.scale = 0.5f;
		this.rotationVel = rotationVel;
		speed = 200 + additionalSpeed;
	}
	
	public boolean update(float delta){
		timer += delta;
		if(timer >= 100f){
			return true;
		}
		
		if(speed > 0){
			speed -= 1000 * delta;
			rotation += rotationVel;
		}
		else{
			speed = 0;
		}
		
		pos.x += speed * MathUtils.cosDeg(dirRotation) * delta;
		pos.y += speed * MathUtils.sinDeg(dirRotation) * delta;
		
		return false;
	}
}
