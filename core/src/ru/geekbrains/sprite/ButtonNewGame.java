package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private Game game;

    public ButtonNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getHeight() / 4);
//        setLeft(worldBounds.getLeft() + 0.2f);
    }

    @Override
    protected void action() {
//        game.setScreen(new GameScreen());
//        game.getScreen().show();
    }

}
