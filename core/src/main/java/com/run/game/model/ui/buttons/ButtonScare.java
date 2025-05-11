package com.run.game.model.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonScare extends Button {
    private final float cooldownDuration; // Длительность эффекта
    private float remainingCooldown = 0f;
    private boolean isOnCooldown = false;

    public ButtonScare(float x, float y, float width, float height, float cooldownDuration) {
        super(new ButtonStyle(
            new TextureRegionDrawable(new Texture("ui/buttonScareTexture/up.png")),
            new TextureRegionDrawable(new Texture("ui/buttonScareTexture/down.png")),
            null
        ));

        this.cooldownDuration = cooldownDuration;

        setBounds(x, y, width, height);
        setupListeners();
    }

    private void setupListeners() {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isOnCooldown) {
                    activateAbility();
                }
            }
        });
    }

    private void activateAbility() {
        isOnCooldown = true;
        remainingCooldown = cooldownDuration;
    }

    @Override
    public void act(float delta) {
        if (isOnCooldown) {
            remainingCooldown -= delta;

            if (remainingCooldown <= 0) {
                isOnCooldown = false;
                remainingCooldown = 0;
            }
        }

        super.act(delta);
    }

    public void canActive(boolean playerIsAppearance){
        if (!playerIsAppearance) {
            isOnCooldown = false;
            remainingCooldown = 0;
        }
    }

    public boolean isAbilityActive() {
        return isOnCooldown;
    }
}
