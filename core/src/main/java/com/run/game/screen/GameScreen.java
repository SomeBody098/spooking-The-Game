package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.controller.UiController;
import com.run.game.entities.MainContactListener;
import com.run.game.entities.enemies.Enemy;
import com.run.game.entities.enemies.utils.EnemyFactory;
import com.run.game.map.obstacles.impl.Lever;
import com.run.game.entities.player.Player;
import com.run.game.map.MapService;

public class GameScreen implements Screen { // FIXME: 18.05.2025 убрать физику в данном скрине (World, Box2DDebugRenderer, PlayerService, EnemyPhysicService.
                                            // FIXME Причем Player и Human Service'ы разделить на физику и графику)
    private final Main main;
    private final SpriteBatch batch;

    private final World world;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;

    private final FitViewport gameViewport;
    private final ScreenViewport uiViewport;

    private final Player player;
    private final Enemy human;
    private final MapService map;

    private final UiController uiController;

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

        human = EnemyFactory.createEnemy("human");

        map = new MapService(batch, gameCamera, world);

        uiController = new UiController(uiViewport, batch, uiCamera);

        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameViewport.apply();
        batch.setProjectionMatrix(gameCamera.combined);

        batch.begin(); {

            map.render(map.getMapLayerByName("ground")); // слой ground

            player.draw(batch, 0.02f);
            human.draw(batch);

            map.render(map.getMapLayerByName("obstacles")); // слой obstacles

        } batch.end();

        box2DDebugRenderer.render(world, gameCamera.combined);

        // рисуем ui
        uiViewport.apply();
        batch.setProjectionMatrix(uiCamera.combined);

        uiController.render(delta);

        update(delta);
    }

    private void update(float delta){
        world.step(delta, 6, 6);
        gameCamera.update();
        uiCamera.update();

        updatePlayer(delta);
        human.update(player.getPosition(), player.isAppearance());

        // ui

        uiController.buttonScareCanActive(player.isAppearance());

        updateInteractionOnLever();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            main.update(SCREEN_TYPE.MAIN);
        }
    }

    private void updatePlayer(float delta){
        player.updateBody(uiController.getJoystickDto());

        player.updateGraphics(
            delta,
            uiController.getButtonShowIsActive(),
            uiController.getButtonScareIsAbilityActive()
        );
    }

    private void updateInteractionOnLever(){  // FIXME: 14.05.2025 ПЕРЕМЕСТИТЬ В ДРУГОЕ МЕСТО И ПЕРЕДЕЛАТЬ! (он еще и глючит моленько кстати, из-за логики самой кнопки)
        Lever lever = (Lever) map.getTileObject("obstacles", "leveroff");

        lever.update();
        if (!player.isAppearance()) {
            lever.setTouched(false);
        }

        uiController.updateButtonInteraction(map.getInteractableObject());

        if (uiController.getButtonInteractionIsActive()) {
            map.updateInteracted();
            map.setTile("obstacles", "leveron", lever.getPosition());
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
        map.dispose();
    }

}
