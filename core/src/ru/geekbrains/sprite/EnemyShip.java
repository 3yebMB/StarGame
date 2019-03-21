package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class EnemyShip extends Sprite {

    private TextureAtlas atlas;
    private Rect worldBounds;
    private Vector2 v, vbull;
    private int damage;
    private boolean isDestroyed;
//    private TextureRegion skin;
    private int live;

    public EnemyShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.atlas = atlas;
        setHeightProportion(0.15f);
        this.v = new Vector2();
        this.regions = new TextureRegion[2];
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) destroyd();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
//        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void destroyd() {
        isDestroyed = true;
    }

    public void unDestroy() {
        isDestroyed = false;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void shoot() {}

    private void moveRight() {
//        v.set(v0);
    }

    private void moveLeft() {
//        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }
}
