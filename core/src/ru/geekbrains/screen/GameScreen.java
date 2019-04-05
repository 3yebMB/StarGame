package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.ButtonNewGame;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.MessageGameOver;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.TrackingStar;
import ru.geekbrains.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;
    private static final String HIGHSCORE = "HS: ";
    private static final String FRAGS = "S: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "L: ";

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private Background background;
    private Texture backgroundTexture;
    private TextureAtlas atlas;

    private TrackingStar starList[];
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemiesEmitter enemiesEmitter;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private int frags;
    public static int highScore;

    private State state;
    private State stateBuf;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private StringBuilder sbScore;
    private ShapeRenderer lifeLine;

    boolean vibro = Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator);
    boolean accel = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    boolean gyros = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);

    static Preferences pref;

    @Override
    public void show() {
        super.show();
        pref = Gdx.app.getPreferences("My Preferences");
        highScore = pref.getInteger("highscore");
        lifeLine = new ShapeRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        starList = new TrackingStar[STAR_COUNT];
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, bulletSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        enemiesEmitter = new EnemiesEmitter(atlas, worldBounds, enemyPool);
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbScore = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new TrackingStar(atlas, mainShip.getV());
        }
        startNewGame();
    }

    @Override
    public void pause() {
        super.pause();
        stateBuf = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        super.resume();
        state = stateBuf;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        if (state == State.PLAYING) {
            mainShip.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemiesEmitter.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }

            if (enemy.getBottom() <= mainShip.getBottom()+mainShip.getBottom()/10) {
                mainShip.damage(enemy.getHp() / 10.0f);
                if (vibro) Gdx.input.vibrate(100);
            }

            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.damage(enemy.getHp());
                mainShip.damage(mainShip.getHp());
                state = State.GAME_OVER;
                if (vibro) Gdx.input.vibrate(500);
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        mainShip.setHp(enemy.getDamage()/2);
                        frags++;
                        if(frags>highScore) {
                            highScore = frags;
                        }
                    }
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                if (vibro) Gdx.input.vibrate(250);
                bullet.destroy();
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                    if (vibro) Gdx.input.vibrate(500);
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            enemyPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();

        lifeLine.begin(ShapeRenderer.ShapeType.Line);
        if (mainShip.getHp() > 75)
            lifeLine.setColor(Color.GREEN);
        else if (mainShip.getHp() > 50)
            lifeLine.setColor(Color.ORANGE);
        else if (mainShip.getHp() > 33)
            lifeLine.setColor(Color.YELLOW);
        else
            lifeLine.setColor(Color.RED);
        lifeLine.line(0, Gdx.graphics.getHeight()-1, (int)(Gdx.graphics.getWidth()*(mainShip.getHp()/(float)100)), Gdx.graphics.getHeight()-1);
        lifeLine.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        sbScore.setLength(0);

        font.draw(batch, sbScore.append(HIGHSCORE).append(highScore), worldBounds.getLeft(), worldBounds.getTop()-0.003f);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.pos.x-0.1f, worldBounds.getTop()-0.003f, Align.center);
        if (mainShip.getHp()>=0)
            font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x+0.1f, worldBounds.getTop()-0.003f, Align.center);
        else
            font.draw(batch, sbHp.append(HP).append(0), worldBounds.pos.x+0.1f, worldBounds.getTop()-0.03f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop()-0.003f, Align.right);
    }

    @Override
    public void dispose() {
        pref.putInteger("highscore", highScore);
        pref.flush();
        backgroundTexture.dispose();
        atlas.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        explosionPool.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        font.dispose();
        lifeLine.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    public void startNewGame() {
        state = State.PLAYING;
        frags = 0;

        mainShip.startNewGame(worldBounds);

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}
