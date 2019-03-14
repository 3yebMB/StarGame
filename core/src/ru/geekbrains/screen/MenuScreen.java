package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.BadLogic;

public class MenuScreen extends Base2DScreen {

    private BadLogic badLogic;
    private Background wallpaper;
    private Texture img1;
    private Texture img2;

    @Override
    public void show() {
        super.show();
        img1 = new Texture("earth4.png");
        img2 = new Texture("wallpaper.jpg");
        badLogic = new BadLogic(new TextureRegion(img1));
        wallpaper = new Background(new TextureRegion(img2));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        wallpaper.draw(batch);
        badLogic.draw(batch);
        batch.end();
        badLogic.update();
        wallpaper.update();
    }

    @Override
    public void dispose() {
        img1.dispose();
        img2.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        badLogic.touchDown(touch, pointer);
        return false;
    }
}
