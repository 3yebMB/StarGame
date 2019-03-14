package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
        setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/5);
    }

    public void update() {

    }
}
