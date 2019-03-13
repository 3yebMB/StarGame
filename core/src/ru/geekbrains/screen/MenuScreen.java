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
    private Vector2 buf;
    private float scale;

    private static float V_LEN = 6f;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("wallpaper.jpg");
        ball = new Texture("earth4.png");
        touch = new Vector2();
        pos = new Vector2();
        v = new Vector2();
        buf = new Vector2();
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

//        if ((0>pos.y) || (Gdx.graphics.getHeight()-ball.getHeight()<pos.y)) v.set(v.x, v.y*(-1));
//        if ((0>pos.x) || (Gdx.graphics.getWidth()-ball.getWidth()<pos.x)) v.set(v.x*(-1), v.y);

        buf.set(touch);
        if (buf.sub(pos).len() <= V_LEN) pos.set(touch);
        else pos.add(v);

//        pos.add(v);

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
        scale = (float) (Math.random()*10);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch x = " + touch.x + " touch.y = " + touch.y);

//        v = touch.cpy().sub(pos);
//        v.nor();
//        v.scl(scale);

        v.set(touch.cpy().sub(pos)).setLength(V_LEN);

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
