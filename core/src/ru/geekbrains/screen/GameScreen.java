package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemiesLargeEmitter;
import ru.geekbrains.utils.EnemiesMediumEmitter;
import ru.geekbrains.utils.EnemiesSmallEmitter;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;

    private Background background;
    private Texture backgroundTexture;
    private TextureAtlas atlas;

    private Star starList[];
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private EnemiesSmallEmitter enemiesSmallEmitter;
    private EnemiesMediumEmitter enemiesMediumEmitter;
    private EnemiesLargeEmitter enemiesLargeEmitter;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        starList = new Star[STAR_COUNT];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound);
        mainShip = new MainShip(atlas, bulletPool, laserSound);
        enemiesSmallEmitter = new EnemiesSmallEmitter(atlas, worldBounds, enemyPool);
        enemiesMediumEmitter = new EnemiesMediumEmitter(atlas, worldBounds, enemyPool);
        enemiesLargeEmitter = new EnemiesLargeEmitter(atlas, worldBounds, enemyPool);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemiesSmallEmitter.generate(delta);
        enemiesMediumEmitter.generate(delta);
        enemiesLargeEmitter.generate(delta);
        if (bulletPool.getActiveObjects().size() != 0) {
            Rectangle intersection = new Rectangle();
            for (int k = 0; k < bulletPool.getActiveObjects().size(); k++) {
//                enemiesSmallEmitter.getEnemyPool().getActiveObjects().get(k).
                Rectangle r1 = enemiesSmallEmitter.getEnemyPool().getActiveObjects().get(k).getRect();
                Rectangle r2 = bulletPool.getActiveObjects().get(k).getRect();
                if (Intersector.intersectRectangles(r1, r2, intersection)) {
                    if () {
                        // удаляем пулю -
                        // удаляем маленький корабль -
                        break;
                    }
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        atlas.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
}
