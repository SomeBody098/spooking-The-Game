package com.run.game.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.run.game.model.DIRECTION;

public class Joystick extends Actor implements InputProcessor{
    private final Texture circleTexture;
    private final Texture stickTexture;

    private final Vector2 positionCircle;
    private final Vector2 positionStick;
    private final Vector2 tempPosition; // рассходник

    private final float radius;

    private DIRECTION direction = DIRECTION.NONE;

    private int pointer = -1;
    private boolean isActive = false;

    public Joystick(float x, float y, float radius) {
        circleTexture = new Texture("virtualJoystickTexture/circle.png");
        stickTexture = new Texture("virtualJoystickTexture/stick.png");

        this.radius = radius;

        positionCircle = new Vector2(x, y);
        positionStick = new Vector2(x, y);
        tempPosition = new Vector2();
    }

    @Override
    public void draw(Batch uiBatch, float parentAlpha) {
        super.draw(uiBatch, parentAlpha);

        Color color = getColor();
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

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isActive) {
            Vector2 dir = getNorPositionStick(); // Получаем нормализованный вектор направления

            // Определяем основное направление (с порогом чувствительности)
            if (Math.abs(dir.x) > Math.abs(dir.y)) {
                direction = (dir.x > 0.3f) ? DIRECTION.RIGHT :
                    (dir.x < -0.3f) ? DIRECTION.LEFT :
                        direction;
            } else {
                direction = (dir.y > 0.3f) ? DIRECTION.UP :
                    (dir.y < -0.3f) ? DIRECTION.DOWN :
                        direction;
            }
        } else {
            direction = DIRECTION.NONE;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY; // Инвертируем Y

        if (isTouchInJoystickArea(screenX, screenY) && Joystick.this.pointer == -1) {
            isActive = true;
            Joystick.this.pointer = pointer;
            positionStick.set(screenX, screenY);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (Joystick.this.pointer == pointer) {
            resetJoystick();
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer == Joystick.this.pointer && isActive) {
            screenY = Gdx.graphics.getHeight() - screenY;

            // Ограничиваем движение стика радиусом джойстика
            tempPosition.set(screenX - positionCircle.x, screenY - positionCircle.y);
            if (tempPosition.len() > radius) {
                tempPosition.nor().scl(radius);
            }

            positionStick.set(positionCircle).add(tempPosition);

            return true;
        }

        return false;
    }

    @Override public boolean keyDown(int keycode) {return false;}
    @Override public boolean keyUp(int keycode) {return false;}
    @Override public boolean keyTyped(char character) {return false;}
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {return false;}
    @Override public boolean mouseMoved(int screenX, int screenY) {return false;}
    @Override public boolean scrolled(float amountX, float amountY) {return false;}

    private boolean isTouchInJoystickArea(float screenX, float screenY) {
        // Проверяем, находится ли касание в зоне джойстика
        tempPosition.set(screenX, screenY);
        return tempPosition.dst(positionCircle) <= radius;
    }

    private void resetJoystick() {
        positionStick.set(positionCircle);
        isActive = false;
        pointer = -1;
    }

    public Vector2 getNorPositionStick() {
        tempPosition.set(
            positionStick.x - positionCircle.x,
            positionStick.y - positionCircle.y
        );

        return tempPosition.nor();
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public boolean isActive() {
        return isActive;
    }

    public void dispose(){
        circleTexture.dispose();
        stickTexture.dispose();
    }

}
