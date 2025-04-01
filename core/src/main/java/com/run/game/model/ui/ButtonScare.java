package com.run.game.model.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonScare extends Button { // TODO: 01.04.2025 в будущем, реализовать блокировку кнопки на время
    private boolean isActive = false;

    public ButtonScare(float x, float y, float width, float height) {
        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = new TextureRegionDrawable(new Texture("ui/buttonScareTexture/up.png"));
        style.down = new TextureRegionDrawable(new Texture("ui/buttonScareTexture/down.png"));

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

    public boolean isActive() {
        return isActive;
    }
}
