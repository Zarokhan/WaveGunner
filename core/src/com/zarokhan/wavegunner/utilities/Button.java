package com.zarokhan.wavegunner.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.MyGame;

public class Button extends GameObject {
	
	private Texture activeTex;
	private boolean hover, clicked;
	
	private float x, y;
	
	public Button(Texture tex, Texture activeTex) {
		super(tex);
		this.activeTex = activeTex;
		hitbox = new Rectangle();
	}
	
	public void setPosition(Vector2 pos){
		this.pos = pos;
		hitbox = new Rectangle(pos.x, pos.y, width, height);
	}
	
	public boolean update(float delta){
		x = Gdx.input.getX() / (float)(Gdx.graphics.getWidth() / 1920f);
		y = MyGame.HEIGHT - Gdx.input.getY() / (float)(Gdx.graphics.getHeight() / 1080f);
		
		// Hover effect
		if(this.hitbox.contains(x,  y))
			hover = true;
		else
			hover = false;
		
		// Is clicked?
		if(Gdx.input.isTouched()){
			if(hover && !clicked){
				clicked = true;
				return true;
			}
		}else{
			if(clicked)
				clicked = false;
		}
		
		
		
		return false;
	}
	
	@Override
	public void render(SpriteBatch batch){
		if(!hover)
			super.render(batch);
		else
			batch.draw(activeTex, pos.x, pos.y, origin.x, origin.y, activeTex.getWidth(), activeTex.getHeight(), scale, scale, rotation, 0, 0, activeTex.getWidth(), activeTex.getHeight(), false, false);
	}
}
