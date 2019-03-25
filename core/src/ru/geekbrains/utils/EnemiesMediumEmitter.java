package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Enemy;

public class EnemiesMediumEmitter {

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.3f;
    private static final int ENEMY_MEDIUM_DAMAGE = 2;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 2;

    private Rect worldBounds;

    private float generateInterval = 6f;
    private float generateTimer;

    private TextureRegion[] enemySmallRegion;
    private Vector2 enemyMediumV = new Vector2(0f, -0.15f);

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    public EnemiesMediumEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy1");
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
                    enemyMediumV,
                    bulletRegion,
                    ENEMY_MEDIUM_BULLET_HEIGHT,
                    ENEMY_MEDIUM_BULLET_VY,
                    ENEMY_MEDIUM_DAMAGE,
                    ENEMY_MEDIUM_RELOAD_INTERVAL,
                    ENEMY_MEDIUM_HEIGHT,
                    ENEMY_MEDIUM_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
