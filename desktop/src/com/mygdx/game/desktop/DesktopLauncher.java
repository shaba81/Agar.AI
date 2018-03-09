package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import graphics.Render;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Agar.AI";
		config.width = 1000;
		config.height = 600;
//		config.fullscreen = true;
		new LwjglApplication(new Render(), config);
	}
}


