package com.run.game.input;

import com.badlogic.gdx.math.Vector2;
import com.run.game.dto.exte.JoystickDTO;
import com.run.game.model.DIRECTION;

public class PlayerInputHandler {

    private DIRECTION direction = DIRECTION.NONE;

    public Vector2 handleInput(JoystickDTO dto, Vector2 position, float speed) {
        if (dto.isJoystickActive()) {
            direction = dto.getJoystickDirection();

            position.add(
                dto.getNorPositionStickX() * speed,
                dto.getNorPositionStickY() * speed
            );
        }

        return position;
    }

    public DIRECTION getDirection() {
        return direction;
    }
}
