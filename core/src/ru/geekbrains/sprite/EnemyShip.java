package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private int damage;
    private TextureRegion skin;

    public EnemyShip(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
