package com.run.game.model.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonInteraction extends Button {

    private boolean isActive = false;

    private boolean isAppearance = false;

    public ButtonInteraction(float x, float y, float width, float height) {
        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = new TextureRegionDrawable(new Texture("ui/buttonInteractionTexture/ButtonInteraction1.png"));
        style.down = new TextureRegionDrawable(new Texture("ui/buttonInteractionTexture/ButtonInteraction2.png"));

        super.setStyle(style);

        super.setPosition(x, y);
        super.setWidth(width);
        super.setHeight(height);

        super.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isActive = !isActive;
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        super.setVisible(isAppearance);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAppearance() {
        return isAppearance;
    }

    public void setAppearance(boolean appearance) {
        isAppearance = appearance;
    }
}
