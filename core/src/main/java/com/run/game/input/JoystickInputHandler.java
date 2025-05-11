package com.run.game.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Pool;

public class JoystickInputHandler extends InputListener {

    private final Vector2 position;

    private final Vector2 finalPosition;

    private final Pool<Vector2> vectorPool;

    private int pointer = -1;

    private final float radius;
    private boolean isActive = false;

    public JoystickInputHandler(Vector2 position, Vector2 finalPosition, Pool<Vector2> vectorPool, float radius) {
        this.position = position;
        this.finalPosition = finalPosition;
        this.vectorPool = vectorPool;
        this.radius = radius;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (isTouchInJoystickArea(x, y) && JoystickInputHandler.this.pointer == -1) {
            isActive = true;
            JoystickInputHandler.this.pointer = pointer;
            position.set(x, y);
            return true;
        }

        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (JoystickInputHandler.this.pointer == pointer) {
            resetJoystick();
            return;
        }

        super.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == JoystickInputHandler.this.pointer && isActive) {
            Vector2 tempPosition = vectorPool.obtain();

            tempPosition.set(x - finalPosition.x, y - finalPosition.y); // Ограничиваем движение стика радиусом джойстика

            if (tempPosition.len() > radius) {
                tempPosition.nor().scl(radius);
            }

            position.set(finalPosition).add(tempPosition);

            vectorPool.free(tempPosition);

            return;
        }

        super.touchDragged(event, x, y, pointer);
    }

    private boolean isTouchInJoystickArea(float screenX, float screenY) {
        Vector2 tempPosition = vectorPool.obtain();
        tempPosition.set(screenX, screenY);

        // Проверяем, находится ли касание в зоне джойстика
        boolean isTouchInJoystickArea = tempPosition.dst(finalPosition) <= radius;

        vectorPool.free(tempPosition);

        return isTouchInJoystickArea;
    }

    private void resetJoystick() {
        position.set(finalPosition);
        isActive = false;
        pointer = -1;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }
}
