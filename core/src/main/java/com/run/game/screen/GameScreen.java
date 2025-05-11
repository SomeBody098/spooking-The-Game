package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.model.MainContactListener;
import com.run.game.service.HumanService;
import com.run.game.service.PlayerService;
import com.run.game.model.map.MapFactory;
import com.run.game.model.map.Tile;
import com.run.game.model.ui.UiFactory;
import com.run.game.model.ui.buttons.ButtonScare;
import com.run.game.model.ui.buttons.ButtonShow;
import com.run.game.service.ui.JoystickService;

import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {

    private final Main main;
    private final SpriteBatch batch;

    private final World world;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;

    private final FitViewport gameViewport;
    private final ScreenViewport uiViewport;

    private final PlayerService player;

    private final Stage stage;
    private final JoystickService joystick;
    private final ButtonShow buttonShow;
    private final ButtonScare buttonScare;

    private final HumanService human;

    private final OrthogonalTiledMapRenderer renderer;

    private final Map<String, Array<? extends Tile>> tiledMapLayers;

    private final Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Main main, SpriteBatch batch, OrthographicCamera gameCamera, FitViewport gameViewport, OrthographicCamera uiCamera, ScreenViewport uiViewport, World world) {
        this.main = main;
        this.batch = batch;
        this.gameCamera = gameCamera;
        this.uiCamera = uiCamera;
        this.world = world;
        this.gameViewport = gameViewport;
        this.uiViewport = uiViewport;

        world.setContactListener(new MainContactListener());

        player = new PlayerService(
            gameCamera.viewportWidth / 2,
            gameCamera.viewportHeight / 2,
            Main.PPM,
            Main.PPM,
            world
        );

        human = new HumanService(
            4,
            3,
            Main.PPM,
            Main.PPM,
            world
        );

        tiledMapLayers = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("tileset/graveyard/firstlevel/firstLevel.tmx");

        tiledMapLayers.put("obstacles", MapFactory.createBodyForObstacles(map, world));

        renderer = new OrthogonalTiledMapRenderer(map, Main.UNIT_SCALE);
        renderer.setView(gameCamera);

        stage = UiFactory.createUiInterface(uiViewport, batch, uiCamera);

        joystick = (JoystickService) stage.getActors().get(3);
        buttonShow = (ButtonShow) stage.getActors().get(0);
        buttonScare = (ButtonScare) stage.getActors().get(1);

        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameViewport.apply();
        batch.setProjectionMatrix(gameCamera.combined);

        renderer.render(new int[]{0}); // слой ground

        batch.begin();

        player.draw(batch, 0.02f);
        human.draw(batch);

        batch.end();

        renderer.render(new int[]{1}); // слой obstacles

        box2DDebugRenderer.render(world, gameCamera.combined);

        // рисуем gui
        uiViewport.apply();
        batch.setProjectionMatrix(uiCamera.combined);
        stage.act(delta);
        stage.draw();

        update(delta);
    }

    private void update(float delta){
        world.step(delta, 6, 6);
        gameCamera.update();
        uiCamera.update();

        updatePlayer(delta);
        human.update(delta, world, player.getPosition(), player.isAppearance());

        // ui

        buttonScare.canActive(player.isAppearance());

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            main.update(SCREEN_TYPE.MAIN);
        }
    }

    private void updatePlayer(float delta){
        player.updateBody(joystick.getDto());
        player.updateGraphics(delta, buttonShow.isActive(), buttonScare.isAbilityActive());
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
        human.dispose();
        player.dispose();
        renderer.dispose();
        joystick.dispose();
        stage.dispose();
    }

}
