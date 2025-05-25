package com.run.game.entities.enemies.ai.task;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.run.game.entities.DIRECTION;
import com.run.game.dto.Dto;
import com.run.game.entities.enemies.EnemyBlackboard;

public class EnemyVision extends LeafTask<EnemyBlackboard> implements RayCastCallback {

    private final float viewDistance;
    private final float angleOfView;

    private final World world;
    private final Vector2 playerPosition;
    private final Vector2 enemyPosition;
    private DIRECTION direction = DIRECTION.NONE;

    private boolean playerIsAppearance;
    private boolean isSeePlayer = false;

    private final Pool<Vector2> vectorPool;

    public EnemyVision(World world, float angleOfView, float viewDistance) {
        this.world = world;
        playerPosition = new Vector2();
        enemyPosition = new Vector2();

        this.angleOfView = angleOfView;
        this.viewDistance = viewDistance;

        vectorPool = new Pool<Vector2>() {
            @Override
            protected Vector2 newObject() {
                return new Vector2();
            }
        };
    }

    @Override
    public Status execute() {
        if (playerIsAppearance) {
            canSee(world, playerPosition, enemyPosition, direction);
        } else {
            isSeePlayer = false;
        }

        updateVision();
        updateBlackboard();

        if (isSeePlayer){
            return Status.SUCCEEDED;
        } else {
            return Status.FAILED;
        }
    }

    @Override
    protected Task<EnemyBlackboard> copyTo(Task<EnemyBlackboard> task) {
        return task.cloneTask();
    }

    private void updateBlackboard(){
        getObject().setSeePlayer(isSeePlayer);
    }

    private void updateVision(){
        EnemyBlackboard blackboard = getObject();

        playerPosition.set(blackboard.getPlayerPosition());
        enemyPosition.set(blackboard.getCurrentPosition());
        direction = blackboard.getDirection();
        playerIsAppearance = blackboard.isPlayerisAppearance();
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
            isSeePlayer = false;
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
            world.rayCast(this, enemyPosition, rayEnd);
        } else {
            isSeePlayer = false;
        }

        // Возвращаем объекты в пул
        vectorPool.free(toPlayer);
        vectorPool.free(rayEnd);
    }
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) { // FIXME: 25.05.2025 из-за того, что rayCast свой луч пускает не только в игрока, но и в стены, то isSeePlayer постоянно то true то false
        // Если луч уже прошёл больше viewDistance - игнорируем                                        FIXME: но доходит в blackboard - false, надо чтобы луч именно в игрока стрелял
        if (fraction * viewDistance >= viewDistance) {
            isSeePlayer = false;
            return -1; // Превышена дистанция
        }

        String nameObject = ((Dto) fixture.getUserData()).getName();

        if (fixture.getUserData() != null && nameObject.equals("player")) {
            isSeePlayer = true;
            return fraction; // Игрок найден
        } else {
            isSeePlayer = false;
            return 0; // прекращаем RayCast
        }
    }

    public void dispose(){
        vectorPool.clear();
    }
}
