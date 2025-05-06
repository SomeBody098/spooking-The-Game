package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
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
import com.run.game.model.characters.enemys.exte.Human;
import com.run.game.model.map.MapFactory;
import com.run.game.model.map.Tile;
import com.run.game.model.characters.player.PlayerGraphics;
import com.run.game.model.ui.ButtonScare;
import com.run.game.model.ui.ButtonShow;
import com.run.game.model.ui.Joystick;
import com.run.game.model.characters.player.Player;

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

    private final Player player;

    private final Stage stage;
    private final Joystick joystick;
    private final ButtonShow buttonShow;
    private final ButtonScare buttonScare;

    private final Human human;

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

        player = new Player(
            gameCamera.viewportWidth / 2,
            gameCamera.viewportHeight / 2,
            Main.PPM,
            Main.PPM,
            world
        );

        human = new Human(
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

        // ui components

        float joystickRadius = Math.min(uiCamera.viewportWidth, uiCamera.viewportHeight) * 0.15f; // 15% от меньшей стороны
        float joystickX = joystickRadius * 1.5f;
        float joystickY = joystickRadius * 1.5f;

        joystick = new Joystick(
            joystickX,
            joystickY,
            joystickRadius
        );

        float buttonSize = uiCamera.viewportHeight * 0.1f; // 10% от высоты
        float buttonShowMargin = buttonSize * 0.5f;

        buttonShow = new ButtonShow(
            uiCamera.viewportWidth - buttonSize - buttonShowMargin,
            buttonShowMargin,
            buttonSize,
            buttonSize
        );

        float buttonScareMargin = buttonSize * 0.5f;

        buttonScare = new ButtonScare(
            uiCamera.viewportWidth - buttonSize - buttonScareMargin,
            buttonScareMargin + buttonShow.getHeight(),
            buttonSize,
            buttonSize,
            PlayerGraphics.TIME_FOR_SCARE + PlayerGraphics.TIME_FOR_LAUGH
        );

        stage = new Stage(new ScreenViewport(uiCamera), batch);
        stage.addActor(buttonShow);
        stage.addActor(joystick);
        stage.addActor(buttonScare);

        Gdx.input.setInputProcessor(new InputMultiplexer(joystick, stage));

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
        human.draw(batch, 1f);

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
        if (Gdx.input.getInputProcessor() != stage){
            Gdx.input.setInputProcessor(new InputMultiplexer(joystick, stage)); // FIXME: 25.04.2025 обрати внимание на этот кусок в будущем
        }

        world.step(delta, 6, 6);
        gameCamera.update();
        uiCamera.update();
        player.update(delta, joystick, buttonShow.isActive(), buttonScare.isAbilityActive());
        human.update(world, player.getPosition(), delta, player.isAppearance());

        // ui

        buttonScare.canActive(player.isAppearance());

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            main.update(SCREEN_TYPE.MAIN);
        }
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
