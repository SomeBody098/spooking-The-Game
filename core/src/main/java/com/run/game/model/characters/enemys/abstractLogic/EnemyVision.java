package com.run.game.model.characters.enemys.abstractLogic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.run.game.model.DIRECTION;
import com.run.game.dto.Dto;

public class EnemyVision implements RayCastCallback {

    private final float viewDistance;
    private final float angleOfView;

    private boolean hasSeesPlayer = false;

    private final Pool<Vector2> vectorPool;

    public EnemyVision(float angleOfView, float viewDistance) {
        this.angleOfView = angleOfView;
        this.viewDistance = viewDistance;

        vectorPool = new Pool<Vector2>() {
            @Override
            protected Vector2 newObject() {
                return new Vector2();
            }
        };
    }

    public void updateVision(World world, Vector2 playerPosition, Vector2 enemyPosition, DIRECTION direction, boolean isAppearance){
        if (!isAppearance) {
            setHasSeesPlayer(false);
        } else {
            canSee(world, playerPosition, enemyPosition, direction);
        }
    }

    private void canSee(World world, Vector2 playerPosition, Vector2 enemyPosition, DIRECTION direction) {
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
    }
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        // Если луч уже прошёл больше viewDistance - игнорируем
        if (fraction * viewDistance >= viewDistance) {
            hasSeesPlayer = false;
            return -1; // Превышена дистанция
        }

        String nameObject = ((Dto) fixture.getUserData()).getName();

        if (fixture.getUserData() != null && nameObject.equals("player")) {
            hasSeesPlayer = true;
            return fraction; // Игрок найден
        } else {
            hasSeesPlayer = false;
            return 0; // прекращаем RayCast
        }
    }

    public boolean isHasSeesPlayer() {
        return hasSeesPlayer;
    }

    public void setHasSeesPlayer(boolean hasSeesPlayer) {
        this.hasSeesPlayer = hasSeesPlayer;
    }

    public void dispose(){
        vectorPool.clear();
    }

}
