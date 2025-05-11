package com.run.game.model.ui.joystick;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.run.game.model.DIRECTION;

public class JoystickBody {

    private final Vector2 positionCircle;
    private final Vector2 positionStick;

    private final Pool<Vector2> vectorPool;

    private final float radius;

    private DIRECTION direction = DIRECTION.NONE;

    public JoystickBody(float x, float y, float radius) {
        this.radius = radius;
        positionCircle = new Vector2(x, y);
        positionStick = new Vector2(x, y);
        vectorPool = new Pool<Vector2>() {
            @Override
            protected Vector2 newObject() {
                return new Vector2();
            }
        };
    }

    public void act(boolean isActive) {
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
        }
    }

    public void updatePositon(Vector2 positionStick){
        this.positionStick.set(positionStick);
    }

    public float getNorPositionStickX(){
        Vector2 tempPosition = getNorPositionStick();
        float x = tempPosition.x;
        vectorPool.free(tempPosition);

        return x;
    }

    public float getNorPositionStickY(){
        Vector2 tempPosition = getNorPositionStick();
        float y = tempPosition.y;
        vectorPool.free(tempPosition);

        return y;
    }

    private Vector2 getNorPositionStick() {
        Vector2 tempPosition = vectorPool.obtain();
        tempPosition.set(
            positionStick.x - positionCircle.x,
            positionStick.y - positionCircle.y
        );

        return tempPosition.nor();
    }

    public Vector2 getPositionCircle() {
        return positionCircle;
    }

    public Vector2 getPositionStick() {
        return positionStick;
    }

    public Pool<Vector2> getVectorPool() {
        return vectorPool;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public float getRadius() {
        return radius;
    }

    public void dispose(){
        vectorPool.clear();
    }
}
