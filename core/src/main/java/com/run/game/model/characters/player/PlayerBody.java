package com.run.game.model.characters.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.model.BodyFactory;
import com.run.game.model.dto.exte.PlayerDTO;
import com.run.game.model.ui.Joystick;

public class PlayerBody {

    public static final float SPEED = Main.UNIT_SCALE;

    private final Body body;

    private final float width, height;

    private DIRECTION direction = DIRECTION.NONE;

    private boolean isPlayerHasStopMoving = false;

    private boolean isIntangibleActive = true;

    public PlayerBody(float x, float y, float wight, float height, World world, PlayerDTO playerDTO) {
        body = BodyFactory.createPolygonBody(
            BodyFactory.BODY_TYPE.DYNAMIC,
            true,
            false,
            x, y,
            wight / 2,  // FIXME: 05.05.2025 возможны проблемы изза произвльного деления на 4 (и в фабричном классе и здесь есть деление на 2, в сумме 4)
            height / 2,   // FIXME: 05.05.2025 возможны проблемы изза произвльного деления на 4 (и в фабричном классе и здесь есть деление на 2, в сумме 4)
            world,
            playerDTO
        );

        this.width = wight;
        this.height = height;
    }

    public void handleInput(Joystick joystick) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        if (joystick.isActive()){
            float joystickX = joystick.getNorPositionStickX();
            float joystickY = joystick.getNorPositionStickY();
            direction = joystick.getDirection();

            x += joystickX * SPEED;
            y += joystickY * SPEED;

            isPlayerHasStopMoving = false;
        } else {
            isPlayerHasStopMoving = true;
        }

        body.setTransform(x, y, body.getAngle());
    }

    public void updateDTO(PlayerDTO userData){
        userData.setIntangibleActive(isIntangibleActive);
        userData.setPlayerHasStopMoving(isPlayerHasStopMoving);
    }

    public void setIntangible(boolean intangible){
        isIntangibleActive = intangible;
    }

    public boolean isIntangibleActive() {
        return isIntangibleActive;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public boolean isPlayerHasStopMoving() {
        return isPlayerHasStopMoving;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
