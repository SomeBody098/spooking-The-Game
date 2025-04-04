package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.model.Brick;
import com.run.game.model.enemy.exte.Human;
import com.run.game.model.player.PlayerGraphics;
import com.run.game.model.ui.ButtonScare;
import com.run.game.model.ui.ButtonShow;
import com.run.game.model.ui.Joystick;
import com.run.game.model.player.Player;

public class MainScreen implements Screen {

    public static final float PPM = 32;
    public static final float UNIT_SCALE = 1f / PPM;

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

    private final Array<Brick> bricks;

    private final Box2DDebugRenderer box2DDebugRenderer;

    public MainScreen(SpriteBatch batch) {
        this.gameBatch = batch;
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

        player = new Player(
            gameCamera.position.x * PPM,
            gameCamera.position.y * PPM,
            PPM * 2,
            PPM * 2,
            world
        );

        human = new Human(
            gameCamera.position.x * PPM,
            gameCamera.position.y * PPM,
            PPM * 2,
            PPM * 2,
            world
        );

        bricks = new Array<>();

        for (int i = 0; i <= gameCamera.viewportHeight; i++) {
            for (int j = 0; j <= gameCamera.viewportWidth; j++) {
                if (i == 0 || i == gameCamera.viewportHeight ||
                    j == 0 || j == (int) gameCamera.viewportWidth) {

                    bricks.add(new Brick(
                        gameCamera.position.x + j * PPM,
                        gameCamera.position.y + i * PPM,
                        PPM, PPM,
                        world
                    ));
                }
            }
        }

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
        box2DDebugRenderer.render(world, gameCamera.combined);

        gameBatch.setProjectionMatrix(gameCamera.combined);
        gameBatch.begin();

        player.draw(gameBatch, 0.02f);
        human.draw(gameBatch, 1f);
        for (Brick brick: bricks) brick.draw(gameBatch);

        gameBatch.end();

        // рисуем gui
        uiBatch.setProjectionMatrix(uiCamera.combined);
        stage.act(delta);
        stage.draw();

        update(delta);
    }

    private void update(float delta){
        world.step(delta, 6, 6);
        gameCamera.update();
        uiCamera.update();
        player.update(delta, joystick, buttonShow.isActive(), buttonScare.isAbilityActive());
        human.update(world, player.getPosition(), delta, player.isAppearance());

        // ui

        buttonScare.canActive(player.isAppearance());

//        gameCamera.position.x = player.getPosition().x;
//        gameCamera.position.y = player.getPosition().y;
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
        for (Brick brick: bricks) brick.dispose();
        joystick.dispose();
        stage.dispose();
        world.dispose();
    }

}
