package ru.geekbrains.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.geekbrains.Star2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "StarTrack Game";
		config.useGL30 = false;
        float aspect = 3f/4f;
		config.width = 400;
		config.height = (int) (config.width / aspect);
		config.resizable = false;
		new LwjglApplication(new Star2DGame(), config);
	}
}
