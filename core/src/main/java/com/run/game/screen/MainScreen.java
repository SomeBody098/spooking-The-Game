package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.model.ui.ButtonStart;

public class MainScreen implements Screen {

    private final Main main;

    private final SpriteBatch uiBatch;

    private final OrthographicCamera uiCamera;

    private final Stage stage;
    private final ButtonStart buttonStart;

    public MainScreen(Main main, SpriteBatch uiBatch, OrthographicCamera uiCamera) {
        this.main = main;
        this.uiBatch = uiBatch;
        this.uiCamera = uiCamera;

        float buttonSize = uiCamera.viewportHeight * 0.2f; // 10% от высоты

        buttonStart = new ButtonStart(
            uiCamera.viewportWidth / 2,
            uiCamera.viewportHeight / 2,
            buttonSize,
            buttonSize
        );

        stage = new Stage(new ScreenViewport(uiCamera), uiBatch);

        stage.addActor(buttonStart);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        uiBatch.setProjectionMatrix(uiCamera.combined);
        stage.act(delta);
        stage.draw();

        update();
    }

    private void update(){
        if (Gdx.input.getInputProcessor() != stage){
            Gdx.input.setInputProcessor(stage);
        }

        if (buttonStart.isActive()){
            main.update(SCREEN_TYPE.GAME);
            buttonStart.setActive(false);
        }
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }

}
