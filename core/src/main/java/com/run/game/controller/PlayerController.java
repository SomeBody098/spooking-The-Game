package com.run.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.dto.exte.JoystickDTO;
import com.run.game.entities.player.Player;

public class PlayerController {

    private final Player player;

    public PlayerController(float x, float y, World world) {
        player = new Player(
            x, y,
            Main.PPM,
            Main.PPM,
            world
        );
    }

    public void update(float delta, JoystickDTO joystickDTO, boolean buttonShowIsActive, boolean buttonScareIsAbilityActive){
        player.updateBody(joystickDTO);

        player.updateGraphics(
            delta,
            buttonShowIsActive,
            buttonScareIsAbilityActive
        );
    }

    public void draw(Batch batch){
        player.draw(batch);
    }

    public boolean isAppearance(){
        return player.isAppearance();
    }

    public Vector2 getPosition(){
        return player.getPosition();
    }

    public void dispose(){
        player.dispose();
    }
}
