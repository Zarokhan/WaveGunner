package com.zarokhan.wavegunner;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zarokhan.wavegunner.States.StateManager;

public class MyGame extends ApplicationAdapter {
	
	public final static int WIDTH = 1920, HEIGHT = 1080;
	
	private SpriteBatch batch;
	private StateManager states;
	private OrthographicCamera camera;
	
	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.position.x = WIDTH/2;
		camera.position.y = HEIGHT/2;
		states = new StateManager(camera);
	}

	@Override
	public void render () {
		/*
		 * Update Logic
		 */
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
			Gdx.app.exit();
		camera.update();
		states.update(Gdx.graphics.getRawDeltaTime());
		
		/*
		 * Render Logic
		 */
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		states.render(batch);
		batch.end();
	}
}
