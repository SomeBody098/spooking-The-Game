package com.run.game.model.enemy;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.run.game.model.DIRECTION;
import com.run.game.screen.MainScreen;

public class EnemyVision implements RayCastCallback {

    private final float viewDistance;
    private final float angleOfView;

    private boolean hasSeesPlayer = false;

    private final Pool<Vector2> vectorPool;

//    private final ShapeRenderer shapeRenderer;


    public EnemyVision(float angleOfView, float viewDistance) {
        this.angleOfView = angleOfView;
        this.viewDistance = MainScreen.PPM * MainScreen.UNIT_SCALE * viewDistance;

        vectorPool = new Pool<Vector2>() {
            @Override
            protected Vector2 newObject() {
                return new Vector2();
            }
        };

//        shapeRenderer = new ShapeRenderer();
    }

    public void canSee(World world, Vector2 playerPosition, Vector2 enemyPosition, DIRECTION direction) {
        // Получаем объекты из пула
        Vector2 toPlayer = vectorPool.obtain();
        Vector2 rayEnd = vectorPool.obtain();

        // Вычисляем направление от врага к игроку
        toPlayer.set(playerPosition).sub(enemyPosition).nor();

        // Проверяем дистанцию до игрока
        float distance = enemyPosition.dst(playerPosition);
        if (distance > viewDistance) {
            hasSeesPlayer = false;
            vectorPool.free(toPlayer);
            vectorPool.free(rayEnd);
            return; // Игрок слишком далеко
        }

        // Вычисляем конечную точку луча
        rayEnd.set(enemyPosition).add(toPlayer.scl(viewDistance));

        // Вычисляем угол между направлением врага и направлением на игрока
        Vector2 enemyDirection = direction.getVector();
        float angle = enemyDirection.angleRad(toPlayer);

        // Угол обзора
        float viewAngle = angleOfView * MathUtils.degreesToRadians;

        // Проверяем, находится ли игрок в пределах угла обзора
        if (Math.abs(angle) <= viewAngle) {
            hasSeesPlayer = false; // Сбрасываем перед новым RayCast
            world.rayCast(this, enemyPosition, rayEnd);
        } else {
            hasSeesPlayer = false;
        }

        // Возвращаем объекты в пул
        vectorPool.free(toPlayer);
        vectorPool.free(rayEnd);

//        // Отладочная отрисовка (опционально)
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.line(enemyPosition, enemyPosition.cpy().add(enemyDirection.scl(5)));
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.line(enemyPosition, playerPosition);
//        shapeRenderer.end();
    }
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        // Если луч уже прошёл больше viewDistance - игнорируем
        if (fraction * viewDistance >= viewDistance) {
            hasSeesPlayer = false;
            return -1; // Превышена дистанция
        }

        if (fixture.getUserData() != null && fixture.getUserData().equals("brick")) {
            hasSeesPlayer = false;
            return 0; // Стена - прекращаем RayCast
        }

        if (fixture.getUserData() != null && fixture.getUserData().equals("player")) {
            hasSeesPlayer = true;
            return fraction; // Игрок найден
        }

        return -1; // Игнорировать другие объекты
    }

    public float getViewDistance() {
        return viewDistance;
    }

    public float getAngleOfView() {
        return angleOfView;
    }

    public boolean isHasSeesPlayer() {
        return hasSeesPlayer;
    }

    public void setHasSeesPlayer(boolean hasSeesPlayer) {
        this.hasSeesPlayer = hasSeesPlayer;
    }

    public void dispose(){
        vectorPool.clear();
//        shapeRenderer.dispose();
    }

}
