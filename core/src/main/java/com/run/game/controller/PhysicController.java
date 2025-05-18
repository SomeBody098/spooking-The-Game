package com.run.game.controller;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.service.character.enemy.exte.HumanService;
import com.run.game.service.character.player.PlayerService;

public class PhysicController { // TODO: 14.05.2025 сделать контроллер для физики

    private final World world;

    private final PlayerService player;

    private final HumanService enemy;


    private final Box2DDebugRenderer box2DDebugRenderer;

    public PhysicController(PlayerService player, HumanService enemy, World world) {
        this.player = player;
        this.enemy = enemy;
        this.world = world;

        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public void updateWorld(float delta){
        world.step(delta, 6, 6);
    }

    public void updateDebugRenderer(Matrix4 combined){
        box2DDebugRenderer.render(world, combined);
    }

}
