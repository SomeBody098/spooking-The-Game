package com.run.game.model.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.model.map.Interactable;

public class ButtonInteraction extends Button {

    private boolean isActive = false;

    private boolean isShowing = false;

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
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isActive = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isActive = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        super.setVisible(isShowing);
    }

    public void update(ObjectMap<String, Interactable> interactableObjects){
        for (ObjectMap.Entry<String, Interactable> interactableEntry: interactableObjects.entries()){
            Interactable value = interactableEntry.value;

            if (value.isTouched()) {
                isShowing = isNeedToShow(value);
                break;
            }
        }
    }

    private boolean isNeedToShow(Interactable value){
        return !value.isActivate();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }
}
