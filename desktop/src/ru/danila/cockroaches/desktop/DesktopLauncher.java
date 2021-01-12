package ru.danila.cockroaches.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.danila.cockroaches.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MyGdxGame.SCR_WIDTH;
		config.height = MyGdxGame.SCR_HEIGHT;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
