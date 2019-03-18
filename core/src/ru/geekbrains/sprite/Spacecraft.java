package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import javax.swing.plaf.synth.Region;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Spacecraft extends Sprite {

    private float spacecraftHeight;
//    private Vector2 v;
    private Rect worldBounds;
    private TextureRegion regions[] = new TextureRegion[3];

    public Spacecraft(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"), 0, 0, 200, 300));
//        super(atlas.findRegion("main_ship")); // size : 390, 287

        spacecraftHeight = 0.1f; //Rnd.nextFloat(0.1f, 0.1f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setHeightProportion(spacecraftHeight);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = -0;
        float posY = -0.4f;
        pos.set(posX, posY);
    }
}
