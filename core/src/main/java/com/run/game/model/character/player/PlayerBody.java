package com.run.game.model.character.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.model.BodyFactory;
import com.run.game.dto.exte.PlayerDTO;
import com.run.game.model.character.Character;

public class PlayerBody implements Character {

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

    public void updateState(boolean isPlayerHasStopMoving, boolean isIntangibleActive){
        this.isPlayerHasStopMoving = isPlayerHasStopMoving;
        this.isIntangibleActive = isIntangibleActive;
    }

    public void updatePosition(Vector2 newPosition){
        body.setTransform(newPosition, 0);
    }

    public void updateDirection(DIRECTION direction){
        this.direction = direction;
    }

    public void updateDTO(PlayerDTO userData) {
        userData.setIntangibleActive(isIntangibleActive);
        userData.setPlayerHasStopMoving(isPlayerHasStopMoving);
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

    @Override
    public String getName() {
        return "player";
    }
}
