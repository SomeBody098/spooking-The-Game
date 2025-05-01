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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.model.obstacles.Brick;
import com.run.game.model.enemy.exte.Human;
import com.run.game.model.player.PlayerGraphics;
import com.run.game.model.ui.ButtonScare;
import com.run.game.model.ui.ButtonShow;
import com.run.game.model.ui.Joystick;
import com.run.game.model.player.Player;

public class GameScreen implements Screen {

    private final Main main;

    private final SpriteBatch gameBatch;
    private final SpriteBatch uiBatch;
    private final Stage stage;

    private final World world;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;

    private final Player player;

    private final Joystick joystick;
    private final ButtonShow buttonShow;
    private final ButtonScare buttonScare;

    private final Human human;

    private final OrthogonalTiledMapRenderer renderer;

    private final Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Main main, SpriteBatch gameBatch, SpriteBatch uiBatch, OrthographicCamera gameCamera, OrthographicCamera uiCamera, World world) {
        this.main = main;
        this.gameBatch = gameBatch;
        this.uiBatch = uiBatch;
        this.gameCamera = gameCamera;
        this.uiCamera = uiCamera;
        this.world = world;

        player = new Player(
            gameCamera.viewportWidth / 2,
            gameCamera.viewportHeight / 2,
            Main.PPM / 2,
            Main.PPM / 2,
            world
        );

        human = new Human(
            1,
            1,
            Main.PPM / 2,
            Main.PPM / 2,
            world
        );

        TiledMap map = new TmxMapLoader().load("tileset/graveyard/firstlevel/firstLevel.tmx");
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

        stage = new Stage(new ScreenViewport(uiCamera), uiBatch);
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
        gameBatch.setProjectionMatrix(gameCamera.combined);
        gameBatch.begin();

        renderer.render();
        player.draw(gameBatch, 0.02f);
        human.draw(gameBatch, 1f);

        gameBatch.end();

        box2DDebugRenderer.render(world, gameCamera.combined);

        // рисуем gui
        uiBatch.setProjectionMatrix(uiCamera.combined);
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

//        gameCamera.position.x = player.getPosition().x;
//        gameCamera.position.y = player.getPosition().y;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            main.update(SCREEN_TYPE.MAIN);
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
        human.dispose();
        player.dispose();
        renderer.dispose();
        joystick.dispose();
        stage.dispose();
    }

}
