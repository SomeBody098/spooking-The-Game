package com.run.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.screen.SCREEN_TYPE;
import com.run.game.screen.ScreenFactory;

import java.util.HashMap;
import java.util.Map;

public class Main extends Game {

    public static final float PPM = 32; // 32 пикселя = 1 метр в Box2D
    public static final float UNIT_SCALE = 1f / PPM; // Масштаб для TiledMap
    public static final int MAP_WIDTH_TILES = 10; // Ширина карты в тайлах
    public static final int MAP_HEIGHT_TILES = 10; // Высота карты в тайлах
    public static final float MAP_WIDTH_PIXELS = MAP_WIDTH_TILES * 32; // 320 пикселей
    public static final float MAP_HEIGHT_PIXELS = MAP_HEIGHT_TILES * 32; // 320 пикселей
    public static final float MAP_WIDTH_METERS = MAP_WIDTH_PIXELS / PPM; // 10 метров
    public static final float MAP_HEIGHT_METERS = MAP_HEIGHT_PIXELS / PPM; // 10 метров

    private SpriteBatch batch;

    private OrthographicCamera gameCamera;
    private OrthographicCamera uiCamera;

    private FitViewport gameViewport;
    private ScreenViewport uiViewport;

    private World world;

    private Map<SCREEN_TYPE, Screen> screens;

    @Override
    public void create() {
        batch = new SpriteBatch();

        gameCamera = new OrthographicCamera(10, 10);
        gameCamera.position.set(
            MAP_WIDTH_METERS / 2,
            MAP_HEIGHT_METERS / 2,
            0
        );
        gameCamera.update();

        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(
            (float) Gdx.graphics.getWidth() / 2,
            (float) Gdx.graphics.getHeight() / 2,
            0
        );
        uiCamera.update();

        gameViewport = new FitViewport(Main.MAP_WIDTH_METERS, Main.MAP_HEIGHT_METERS, gameCamera);
        uiViewport = new ScreenViewport(uiCamera);

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

    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public World getWorld() {
        return world;
    }

    public FitViewport getGameViewport() {
        return gameViewport;
    }

    public ScreenViewport getUiViewport() {
        return uiViewport;
    }

    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
        world.dispose();
    }

}
