package com.zarokhan.wavegunner.visualobjects;

import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;

public class Barrels extends GameObject {

	public Barrels(ResourceManager res, Vector2 pos) {
		super(res.barrels);
		this.pos = pos;
	}

}
