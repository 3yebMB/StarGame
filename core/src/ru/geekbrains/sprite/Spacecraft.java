package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Spacecraft extends Sprite {

    private float spacecraftHeight;
//    private Vector2 v;
    private Rect worldBounds;
//    private TextureRegion regions[] = new TextureRegion[3];

    public Spacecraft(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship")); // size : 390, 287

//        regions[0] = new TextureRegion(atlas.findRegion("main_ship"));
//        int width = regions[0].getRegionWidth() / 2;
//        spacecraftHeight = regions[0].getRegionHeight();
//        regions[1] = new TextureRegion(regions[0], 916, 95, width, spacecraftHeight);
//        regions[2] = new TextureRegion(regions[0], 916, 95, width, spacecraftHeight);

        spacecraftHeight = Rnd.nextFloat(0.1f, 0.1f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setHeightProportion(spacecraftHeight);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = -0;
        float posY = -0.44f;
        pos.set(posX, posY);
    }
}
