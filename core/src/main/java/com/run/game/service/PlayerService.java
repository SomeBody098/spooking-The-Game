package com.run.game.service;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.controller.PlayerHandleInput;
import com.run.game.dto.exte.JoystickDTO;
import com.run.game.model.characters.player.PlayerBody;
import com.run.game.view.PlayerGraphics;
import com.run.game.dto.exte.PlayerDTO;

public class PlayerService {

    private final PlayerBody body;

    private final PlayerGraphics graphics;

    private final PlayerHandleInput handleInput;

    private final PlayerDTO dto;

    public PlayerService(float x, float y, float wight, float height, World world) {
        graphics = new PlayerGraphics();
        handleInput = new PlayerHandleInput();

        dto = new PlayerDTO("player");
        dto.setIntangibleActive(!graphics.isAppearance());

        body = new PlayerBody(x, y, wight, height, world, dto);
        body.updateDTO(dto);
    }

    public void updateBody(JoystickDTO joystickDTO){
        if (!graphics.isHasScares()) {
            Vector2 newPosition = handleInput.handleInput(
                joystickDTO,
                body.getPosition(),
                PlayerBody.SPEED
            );

            body.updatePosition(newPosition);
            body.updateDirection(handleInput.getDirection());
        }

        boolean isPlayerHasStopMoving = !joystickDTO.isJoystickActive();
        boolean isIntangibleActive = !graphics.isAppearance();

        body.updateState(isPlayerHasStopMoving, isIntangibleActive);
        body.updateDTO(dto);
    }

    public void updateGraphics(float delta, boolean buttonShowIsActive, boolean buttonScareIsActive){
        graphics.setDirection(body.getDirection());
        graphics.update(delta, buttonShowIsActive, buttonScareIsActive, body.isPlayerHasStopMoving());
    }

    public void draw(Batch batch, float parentAlpha) {
        graphics.draw(batch, parentAlpha, body.getPosition(), body.getWidth(), body.getHeight());
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    public boolean isAppearance() {
        return graphics.isAppearance();
    }

    public void dispose() {
        graphics.dispose();
    }
}
