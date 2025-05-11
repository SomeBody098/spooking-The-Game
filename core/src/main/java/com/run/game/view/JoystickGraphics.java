package com.run.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class JoystickGraphics {

    private final Texture circleTexture;
    private final Texture stickTexture;

    public JoystickGraphics() {
        circleTexture = new Texture("ui/virtualJoystickTexture/circle.png");
        stickTexture = new Texture("ui/virtualJoystickTexture/stick.png");
    }

    public void draw(Batch uiBatch, Color color, Vector2 positionCircle, Vector2 positionStick, float radius, float parentAlpha) {
        uiBatch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        uiBatch.draw(circleTexture,
            positionCircle.x - radius,
            positionCircle.y - radius,
            radius * 2,
            radius * 2
        );

        uiBatch.draw(stickTexture,
            positionStick.x - (float) stickTexture.getWidth() / 2,
            positionStick.y - (float) stickTexture.getHeight() / 2
        );

        uiBatch.setColor(Color.WHITE);
    }

    public void dispose(){
        circleTexture.dispose();
        stickTexture.dispose();
    }
}
