package com.run.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.run.game.dto.exte.JoystickDTO;
import com.run.game.model.map.Interactable;
import com.run.game.model.ui.UiFactory;
import com.run.game.model.ui.buttons.ButtonInteraction;
import com.run.game.model.ui.buttons.ButtonScare;
import com.run.game.model.ui.buttons.ButtonShow;
import com.run.game.service.ui.JoystickService;

public class UiController {
    private final Stage stage;
    private final JoystickService joystick;
    private final ButtonShow buttonShow;
    private final ButtonScare buttonScare;
    private final ButtonInteraction buttonInteraction;

    public UiController(Viewport uiViewport, Batch batch, Camera uiCamera) {
        stage = UiFactory.createUiInterface(uiViewport, batch, uiCamera);

        Array<Actor> actors = stage.getActors();
        buttonShow = (ButtonShow) actors.get(0);
        buttonScare = (ButtonScare) actors.get(1);
        buttonInteraction = (ButtonInteraction) actors.get(2);
        joystick = (JoystickService) actors.get(3);
    }

    public void render(float delta){
        reboot();
        act(delta);
        draw();
    }

    private void reboot(){
        if (Gdx.input.getInputProcessor() != stage) Gdx.input.setInputProcessor(stage);
    }

    private void act(float delta){
        stage.act(delta);
    }

    private void draw(){
        stage.draw();
    }

    public void buttonScareCanActive(boolean playerIsAppearance){
        buttonScare.canActive(playerIsAppearance);
    }

    public void updateButtonInteraction(ObjectMap<String, Interactable> interactableObjects){
        buttonInteraction.update(interactableObjects);
    }

    public JoystickDTO getJoystickDto() {
        return joystick.getDto();
    }

    public boolean getButtonShowIsActive() {
        return buttonShow.isActive();
    }

    public boolean getButtonInteractionIsActive() {
        return buttonInteraction.isActive();
    }

    public boolean getButtonScareIsAbilityActive() {
        return buttonScare.isAbilityActive();
    }

    public void dispose(){
        stage.dispose();
    }
}
