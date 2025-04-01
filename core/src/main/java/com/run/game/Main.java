package com.run.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.run.game.screen.MainScreen;

public class Main extends Game {

    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainScreen(batch));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
    }

}
