package com.zarokhan.wavegunner.visualobjects;

import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class Jeep extends GameObject {
	
	public Jeep(ResourceManager res, Vector2 pos, float rotation){
		super(res.jeep);
		this.pos = pos;
		this.rotation = rotation;
	}
}
