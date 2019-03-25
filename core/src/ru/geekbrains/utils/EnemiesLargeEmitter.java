package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Enemy;

public class EnemiesLargeEmitter {

    private static final float ENEMY_LARGE_HEIGHT = 0.2f;
    private static final float ENEMY_LARGE_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_LARGE_BULLET_VY = -0.3f;
    private static final int ENEMY_LARGE_DAMAGE = 3;
    private static final float ENEMY_LARGE_RELOAD_INTERVAL = 5f;
    private static final int ENEMY_LARGE_HP = 3;

    private Rect worldBounds;

    private float generateInterval = 9f;
    private float generateTimer;

    private TextureRegion[] enemySmallRegion;
    private Vector2 enemyLargeV = new Vector2(0f, -0.1f);

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    public EnemiesLargeEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy2");
        this.enemySmallRegion = Regions.split(textureRegion0, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    enemySmallRegion,
                    enemyLargeV,
                    bulletRegion,
                    ENEMY_LARGE_BULLET_HEIGHT,
                    ENEMY_LARGE_BULLET_VY,
                    ENEMY_LARGE_DAMAGE,
                    ENEMY_LARGE_RELOAD_INTERVAL,
                    ENEMY_LARGE_HEIGHT,
                    ENEMY_LARGE_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
