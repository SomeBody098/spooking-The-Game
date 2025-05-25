package com.run.game.entities.enemies.ai.task;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.run.game.entities.enemies.EnemyBlackboard;
import com.run.game.entities.DIRECTION;

import java.util.Random;

public class PatrolTask extends LeafTask<EnemyBlackboard> {

    private final Random random;

    private DIRECTION direction = DIRECTION.NONE;

    private final Vector2 targetPosition;
    private float timePatrol = -1f;
    private float timeRest = 5f;

    private boolean isRest = true;
    private boolean isPatrol = false;

    public PatrolTask() {
        random = new Random();
        targetPosition = new Vector2();
    }

    @Override
    public Status execute() {
        boolean changeTargetPosition = changeTargetPosition();
        boolean changeDirection = changeDirection();
        boolean needRest = needRest();
        boolean onRest = onRest();
        boolean onPatrol = onPatrol();

        if (changeTargetPosition || changeDirection || needRest || onRest) updateBlackboard();

        EnemyBlackboard blackboard = getObject();

        if (blackboard.isStopMoving() || blackboard.isViolationOfBorders()) {
            blackboard.setTargetPosition(blackboard.getCurrentPosition().x, blackboard.getCurrentPosition().y);
            return Status.FAILED;
        }

        if (onPatrol) return Status.RUNNING;
        if (onRest) return Status.SUCCEEDED;

        return null;
    }

    private boolean changeTargetPosition(){
        if (timePatrol <= 0 || targetPosition.isZero()){
            targetPosition.set(random.nextInt(11), random.nextInt(11));

            timePatrol = random.nextInt(11);
            isRest = true;

            return true;
        }

        return false;
    }

    private boolean needRest(){
        if (timeRest <= 0){
            timeRest = 5f;
            isRest = false;
            isPatrol = true;

            return true;
        }

        return false;
    }

    private boolean onRest(){
        if (isRest){
            timeRest -= Gdx.graphics.getDeltaTime();
            isPatrol = false;

            return true;
        }

        return false;
    }

    private boolean onPatrol(){
        if (isPatrol){
            timePatrol -= Gdx.graphics.getDeltaTime();

            return true;
        }

        return false;
    }

    private boolean changeDirection(){
        if (Math.abs(targetPosition.x) > Math.abs(targetPosition.y)) {
            if (targetPosition.x > 0.3f) {
                direction = DIRECTION.RIGHT;
                return true;

            } else if (targetPosition.x < -0.3f) {
                direction = DIRECTION.LEFT;
                return true;

            } else {
                return false;
            }

        } else {
            if (targetPosition.y > 0.3f) {
                direction = DIRECTION.UP;
                return true;

            } else if (targetPosition.y < -0.3f) {
                direction = DIRECTION.DOWN;
                return true;

            } else {
                return false;
            }
        }
    }

    private void updateBlackboard(){
        EnemyBlackboard blackboard = getObject();

        blackboard.setTargetPosition(targetPosition.x, targetPosition.y);
        blackboard.setDirection(direction);
        blackboard.setPatrol(isPatrol);
        blackboard.setRest(isRest);
    }

    @Override
    protected Task<EnemyBlackboard> copyTo(Task<EnemyBlackboard> task) {
        return task.cloneTask();
    }

}
