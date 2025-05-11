package com.run.game.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.run.game.view.PlayerGraphics;
import com.run.game.model.ui.buttons.ButtonInteraction;
import com.run.game.model.ui.buttons.ButtonScare;
import com.run.game.model.ui.buttons.ButtonShow;
import com.run.game.service.ui.JoystickService;

public class UiFactory {

    private static final float VISIBLE =  0.7f;

    private static float buttonSize;
    private static float buttonMargin;

    public static Stage createUiInterface(Viewport uiViewport, Batch batch, Camera uiCamera){
        buttonSize = uiCamera.viewportHeight * 0.1f; // 10% от высоты
        buttonMargin = buttonSize * 0.5f;

        Stage stage = new Stage(uiViewport, batch);
        stage.getRoot().getColor().a = VISIBLE;

        JoystickService joystick = createJoystick(uiCamera);

        stage.addActor(createButtonShow(uiCamera));         // 0
        stage.addActor(createButtonScare(uiCamera));        // 1
        stage.addActor(createButtonInteraction(uiCamera));  // 2
        stage.addActor(joystick);                           // 3

        stage.addListener(joystick.getInputHandler());

        Gdx.input.setInputProcessor(stage);

        return stage;
    }

    private static JoystickService createJoystick(Camera uiCamera){
        float joystickRadius = Math.min(uiCamera.viewportWidth, uiCamera.viewportHeight) * 0.15f; // 15% от меньшей стороны
        float joystickX = joystickRadius * 1.5f;
        float joystickY = joystickRadius * 1.5f;

        return new JoystickService(
            joystickX,
            joystickY,
            joystickRadius
        );
    }

    private static ButtonShow createButtonShow(Camera uiCamera){
        return new ButtonShow(
            uiCamera.viewportWidth - buttonSize - buttonMargin,
            buttonMargin,
            buttonSize,
            buttonSize
        );
    }

    private static ButtonScare createButtonScare(Camera uiCamera){
        return new ButtonScare(
            uiCamera.viewportWidth - buttonSize - buttonMargin,
            buttonMargin + buttonSize,
            buttonSize,
            buttonSize,
            PlayerGraphics.TIME_FOR_SCARE + PlayerGraphics.TIME_FOR_LAUGH
        );
    }

    private static ButtonInteraction createButtonInteraction(Camera uiCamera){
        return new ButtonInteraction(
            uiCamera.viewportWidth - buttonSize - buttonMargin,
            buttonMargin + buttonSize * 3,
            buttonSize,
            buttonSize
        );
    }
}
