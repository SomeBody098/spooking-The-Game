package com.run.game.model.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonStart extends Button {

    private boolean isActive = false;

    public ButtonStart(float x, float y, float width, float height) {
        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = new TextureRegionDrawable(new Texture("ui/buttonStartTexture/startButton1.png"));
        style.down = new TextureRegionDrawable(new Texture("ui/buttonStartTexture/startButton2.png"));

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

    public void setActive(boolean active) {
        isActive = active;
    }
}
