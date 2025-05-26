package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.manager.GameManager;

public class GameScreen implements Screen {

    private final Main main;

    private final GameManager manager;

    private final FitViewport gameViewport;
    private final ScreenViewport uiViewport;

    public GameScreen(Main main, SpriteBatch batch, OrthographicCamera gameCamera, FitViewport gameViewport, OrthographicCamera uiCamera, ScreenViewport uiViewport, World world) {
        this.main = main;
        this.gameViewport = gameViewport;
        this.uiViewport = uiViewport;

        manager = new GameManager(batch, world, gameCamera, uiCamera, gameViewport, uiViewport);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            main.update(SCREEN_TYPE.MAIN);
        }

        manager.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
