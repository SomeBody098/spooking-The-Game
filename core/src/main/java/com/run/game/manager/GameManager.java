package com.run.game.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.contact_listeners.GameContactListener;
import com.run.game.controller.EnemiesController;
import com.run.game.controller.MapController;
import com.run.game.controller.PlayerController;
import com.run.game.controller.UiController;
import com.run.game.map.obstacles.impl.Lever;

public class GameManager {
    private final SpriteBatch batch;

    private final World world;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;

    private final FitViewport gameViewport;
    private final ScreenViewport uiViewport;

    private final PlayerController player;
    private final EnemiesController enemies;
    private final MapController map;

    private final UiController uiController;

    private final Box2DDebugRenderer box2DDebugRenderer;

    public GameManager(SpriteBatch batch, World world, OrthographicCamera gameCamera, OrthographicCamera uiCamera, FitViewport gameViewport, ScreenViewport uiViewport) {
        this.batch = batch;
        this.world = world;
        this.gameCamera = gameCamera;
        this.uiCamera = uiCamera;
        this.gameViewport = gameViewport;
        this.uiViewport = uiViewport;

        world.setContactListener(new GameContactListener());

        player = new PlayerController(
            gameCamera.viewportWidth / 2,
            gameCamera.viewportHeight / 2,
            world
        );

        enemies = new EnemiesController("human");

        map = new MapController(batch, gameCamera, world);

        uiController = new UiController(uiViewport, batch, uiCamera);

        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta) {
        gameViewport.apply();
        batch.setProjectionMatrix(gameCamera.combined);

        batch.begin(); {

            map.render(map.getMapLayerByName("ground")); // слой ground

            player.draw(batch);
            enemies.drawAll(batch);

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

        player.update(
            delta,
            uiController.getJoystickDto(),
            uiController.getButtonShowIsActive(),
            uiController.getButtonScareIsAbilityActive()
        );

        enemies.updateAll(
            player.getPosition(),
            player.isAppearance()
        );

        // ui

        uiController.buttonScareCanActive(player.isAppearance());

        updateInteractionOnLever();
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

    public void dispose() {
        enemies.dispose();
        player.dispose();
        map.dispose();
    }
}
