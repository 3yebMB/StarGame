package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		region = new TextureRegion(img, 20, 20, 100, 100);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(0.1f, 0.5f, 0.5f, 1);
		batch.draw(img, 0, 0);
		batch.setColor(0.4f, 0.1f, 0.5f, 1);
		batch.draw(region, 200, 200);
		batch.setColor(0.1f, 0.8f, 0.3f, 0.3f);
		batch.draw(img, 300, 300);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
