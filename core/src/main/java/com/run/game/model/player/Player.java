package com.run.game.model.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.ui.Joystick;

public class Player {

    private final PlayerBody playerBody;

    private final PlayerGraphics playerGraphics;

    public Player(float x, float y, float wight, float height, World world) {
        playerGraphics = new PlayerGraphics();
        playerBody = new PlayerBody(x, y, wight, height, world);
    }

    public void update(float deltaTime, Joystick joystick, boolean buttonShowIsActive, boolean buttonScareIsActive) {
        if (!playerGraphics.isHasScares()) playerBody.handleInput(joystick);
        playerBody.setIntangible(!playerGraphics.isAppearance());       // TODO: 02.05.2025 сделай так, чтобы когда призрак невидим, то не сталкивался с врагами, но стеы чтоб проходить не мог! 

        playerGraphics.setDirection(playerBody.getDirection());
        playerGraphics.update(deltaTime, buttonShowIsActive, buttonScareIsActive, playerBody.isPlayerHasStopMoving());
    }

    public void draw(Batch batch, float parentAlpha) {
        playerGraphics.draw(batch, parentAlpha, playerBody.getPosition(), playerBody.getWidth(), playerBody.getHeight());
    }

    public Vector2 getPosition(){
        return playerBody.getPosition();
    }

    public boolean isAppearance() {
        return playerGraphics.isAppearance();
    }

    public void dispose() {
        playerGraphics.dispose();
    }
}
