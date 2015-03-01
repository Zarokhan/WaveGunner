package com.zarokhan.wavegunner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zarokhan.wavegunner.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "Wave Gunner";
		config.resizable = false;
		config.fullscreen = false;
		config.useGL30 = false;
		new LwjglApplication(new MyGame(), config);
	}
}
