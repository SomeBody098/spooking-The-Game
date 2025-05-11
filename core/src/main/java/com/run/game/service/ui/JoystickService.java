package com.run.game.service.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.run.game.controller.JoystickInputListener;
import com.run.game.dto.exte.JoystickDTO;
import com.run.game.model.ui.joystick.JoystickBody;
import com.run.game.view.JoystickGraphics;

public class JoystickService extends Actor{

    private final JoystickGraphics graphics;

    private final JoystickBody body;

    private final JoystickInputListener inputListener;

    private final JoystickDTO dto;

    public JoystickService(float x, float y, float radius) {
        graphics = new JoystickGraphics();
        body = new JoystickBody(x, y, radius);
        dto = new JoystickDTO("joystick");

        inputListener = new JoystickInputListener(
            body.getPositionStick(),
            body.getPositionCircle(),
            body.getVectorPool(),
            body.getRadius()
        );

        addListener(inputListener);
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

        body.act(inputListener.isActive());
        body.updatePositon(inputListener.getPosition());
        updateDto();
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return inputListener.isActive() ? this : null;
    }

    private void updateDto(){
        dto.setJoystickActive(inputListener.isActive());
        dto.setJoystickDirection(body.getDirection());
        dto.setNorPositionStickX(body.getNorPositionStickX());
        dto.setNorPositionStickY(body.getNorPositionStickY());
    }

    public JoystickDTO getDto(){
        return dto;
    }

    public JoystickInputListener getInputListener() {
        return inputListener;
    }

    public void dispose(){
        graphics.dispose();
        body.dispose();
    }
}
