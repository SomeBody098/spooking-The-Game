package com.run.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.run.game.screen.SCREEN_TYPE;
import com.run.game.screen.ScreenFactory;

import java.util.HashMap;
import java.util.Map;

public class Main extends Game {

    public static final float PPM = 32;
    public static final float UNIT_SCALE = 1f / PPM;

    private SpriteBatch batch;
    private SpriteBatch uiBatch;

    private OrthographicCamera gameCamera;
    private OrthographicCamera uiCamera;

    private World world;

    private Map<SCREEN_TYPE, Screen> screens;

    @Override
    public void create() {
        batch = new SpriteBatch();
        uiBatch = new SpriteBatch();

        uiBatch = new SpriteBatch();

        float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        gameCamera = new OrthographicCamera(PPM * aspectRatio, PPM);
        gameCamera.position.set(
            PPM * aspectRatio / 2,
            PPM / 2,
            0
        );

        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(
            (float) Gdx.graphics.getWidth() / 2,
            (float) Gdx.graphics.getHeight() / 2,
            0
        );

        world = new World(new Vector2(), false);

        screens = new HashMap<>();
        screens.put(SCREEN_TYPE.MAIN, ScreenFactory.createScreen(this, SCREEN_TYPE.MAIN));

        setScreen(screens.get(SCREEN_TYPE.MAIN));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        screen.render(Gdx.graphics.getDeltaTime());
    }

    public void update(SCREEN_TYPE type){
        if (screens.containsKey(type)){
            setScreen(screens.get(type));
        } else {
            screens.put(type, ScreenFactory.createScreen(this, type));
            setScreen(screens.get(type));
        }
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public SpriteBatch getUiBatch() {
        return uiBatch;
    }

    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
        uiBatch.dispose();
        world.dispose();
    }

}
