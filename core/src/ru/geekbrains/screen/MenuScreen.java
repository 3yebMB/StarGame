package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {
    private SpriteBatch batch;
    private Texture img;
    private Texture ball;
    private Vector2 touch;
    private Vector2 pos;
    private Vector2 v;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("wallpaper.jpg");
        ball = new Texture("earth4.png");
        touch = new Vector2();
        pos = new Vector2();
        v = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(ball, pos.x, pos.y);
        batch.end();

        pos.add(v);

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        ball.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch x = " + touch.x + " touch.y = " + touch.y);

        v = touch.cpy().sub(pos);
        v.nor();

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
