package com.zarokhan.wavegunner.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarokhan.wavegunner.utilities.GameObject;
import com.zarokhan.wavegunner.utilities.ResourceManager;
import com.zarokhan.wavegunner.utilities.SoundManager;

public class Zombie extends GameObject {
	
	public enum Phase{
		TargetPhase, AttackPhase, DeathPhase
	}
	
	private SoundManager sound;
	
	private int id;
	private int health;
	private float damage;
	
	private Vector2 velocity, point;
	private float speed, baseRadius;
	private Phase phase;
	private boolean end;
	
	private float timer;
	
	public Zombie(ResourceManager res, SoundManager sound, int id, Vector2 pos, Vector2 point, float scale, float baseRadius, float speed) {
		super(res.zombie);
		this.sound = sound;
		this.point = point;
		this.phase = Phase.TargetPhase;
		this.baseRadius = baseRadius;
		this.id = id;
		width = 128;
		height = 128;
		source = new Rectangle(0, 0, width, height);
		origin = new Vector2(width/2, height/2);
		this.pos = pos;
		this.speed = speed;
		this.scale = scale;
		this.hitbox = new Rectangle(pos.x, pos.y, 128, 128);
		
		damage = 10;
		health = 15;
		
		direction();
	}
	
	public int getID(){
		return id;
	}
	
	public Phase getPhase(){
		return phase;
	}
	
	public void takeDamage(int damage){
		health -= damage;
		if(health <= 0){
			timer = 0;
			phase = Phase.DeathPhase;
			source.x = 0;
            source.y = 128 * 2;
		}
	}
	
	private void direction(){
		float deltaX = pos.x - point.x;
		float deltaY = pos.y - point.y;
		float angle = (float)MathUtils.atan2(deltaY, deltaX);
		
		this.rotation = angle * MathUtils.radDeg;
		this.velocity = new Vector2(MathUtils.cos(angle), MathUtils.sin(angle));
	}
	
	public void update(float delta, MilitaryBase base){
		// Update animation
		timer += delta;
		
		if(phase == Phase.DeathPhase){
			// Update death animation
			if(timer >= 0.1f && !end){
				timer = 0;
				
				if(!(source.x == 128 * 5))
					source.x += 128;
				else
					end = true;
			}
		}
		
		if(phase == Phase.AttackPhase){
			// Update running animation
			if(timer >= 0.1f){
				timer = 0;
				
				source.x += 128;
				if(source.x == 128 * 6){
					source.x = 0;
					sound.playHit();
					base.takeDamage(damage);
				}
			}
		}
		
		if(phase == Phase.TargetPhase){
			// Check if we have reached destination
			if(Vector2.dst(pos.x, pos.y, point.x, point.y) <= baseRadius){
				phase = Phase.AttackPhase;
				timer = 0;
                source.x = 0;
				source.y = 128;
			}
			
			// Update running animation
			if(timer >= 0.07f){
				timer = 0;
				
				source.x += 128;
				if(source.x == 128 * 8)
					source.x = 0;
			}
			
			// Update position
			this.pos.x -= velocity.x * speed * delta;
			this.pos.y -= velocity.y * speed * delta;
		}
		
		// Update hitbox
		hitbox.x = pos.x;
		hitbox.y = pos.y;
	}
}
