package com.run.game.service.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.run.game.input.JoystickInputHandler;
import com.run.game.dto.exte.JoystickDTO;
import com.run.game.model.ui.joystick.JoystickBody;
import com.run.game.view.JoystickGraphics;

public class JoystickService extends Actor{

    private final JoystickGraphics graphics;

    private final JoystickBody body;

    private final JoystickInputHandler inputHandler;

    private final JoystickDTO dto;

    public JoystickService(float x, float y, float radius) {
        graphics = new JoystickGraphics();
        body = new JoystickBody(x, y, radius);
        dto = new JoystickDTO("joystick");

        inputHandler = new JoystickInputHandler(
            body.getPositionStick().cpy(),
            body.getPositionCircle().cpy(),
            body.getVectorPool(),
            body.getRadius()
        );
    }

    @Override
    public void draw(Batch uiBatch, float parentAlpha) {
        super.draw(uiBatch, parentAlpha);

        graphics.draw(
            uiBatch, getColor(),
            body.getPositionCircle(),
            body.getPositionStick(),
            body.getRadius(),
            parentAlpha
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        body.setPositionStick(inputHandler.getPosition());
        body.act(inputHandler.isActive());

        updateDto();
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return inputHandler.isActive() ? this : null;
    }

    private void updateDto(){
        dto.setJoystickActive(inputHandler.isActive());
        dto.setJoystickDirection(body.getDirection());
        dto.setNorPositionStickX(body.getNorPositionStickX());
        dto.setNorPositionStickY(body.getNorPositionStickY());
    }

    public JoystickDTO getDto(){
        return dto;
    }

    public JoystickInputHandler getInputHandler() {
        return inputHandler;
    }

    public void dispose(){
        graphics.dispose();
        body.dispose();
    }
}
