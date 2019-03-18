package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.DebugGraphics;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 256;
    private Star starList[];

    private Background background;
    private Texture backgroundTexture;
    private TextureAtlas atlas;
    private Music music;

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/ground_sound.mp3"));

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        starList = new Star[STAR_COUNT];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        for (Star star : starList) {
            star.resize(worldBounds);
        }

        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (!music.isPlaying()) music.play();
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);

        for (Star star : starList) {
            star.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        music.dispose();
        atlas.dispose();
        super.dispose();
    }
}
