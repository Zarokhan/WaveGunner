package com.zarokhan.wavegunner.utilities;

public class SoundManager {
	
	public enum MusicType{
		day, night
	}
	
	private ResourceManager res;
	private boolean muted;
	
	public SoundManager(ResourceManager res){
		this.res = res;
		
	}
	
	private void stopMusic(){
		res.music.stop();
		res.music2.stop();
	}
	
	public void playMusic(MusicType type){
		if(!muted){
			switch(type){
			case day:
                res.music.stop();
				res.music2.play(0.2f);
				break;
			case night:
                res.music2.stop();
				res.music.play(0.5f);
				break;
			}
		}
	}
	
	public void playShot(){
		if(!muted){
			res.shot.play();
			res.basshot.play(0.25f);
		}
	}
	
	public void playSelect(){
		if(!muted)
			res.select.play(0.7f);
	}
	
	public void playUpgrade(){
		if(!muted)
			res.upgrade.play(0.7f);
	}
	
	public void playHit(){
		if(!muted)
			res.hit.play();
	}
}
