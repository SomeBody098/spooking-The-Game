package com.run.game.model.character.enemy.behaviorEnemy.human;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.math.Vector2;
import com.run.game.model.DIRECTION;
import com.run.game.model.character.enemy.behaviorEnemy.EnemyBehavior;
import com.run.game.model.character.enemy.behaviorEnemy.human.tasks.HumanPatrolTask;
import com.run.game.service.character.enemy.blackboard.EnemyBlackboard;


public class HumanBehavior implements EnemyBehavior {   // TODO: 22.05.2025 реализовать еще несколько Task (к примеру с логикой когда игрок пугает врага)

    private final BehaviorTree<EnemyBlackboard> behaviors;

    private final EnemyBlackboard blackboard;

    public HumanBehavior() {
        blackboard = new EnemyBlackboard();
        behaviors = new BehaviorTree<>(
            new Selector<>(
                new HumanPatrolTask()
            ),
            blackboard
        );
    }

    @Override
    public void update(){
        behaviors.step();
    }

    public DIRECTION getDirection(){
        return blackboard.getDirection();
    }

    public void setDirection(DIRECTION direction) {
        blackboard.setDirection(direction);
    }

    public Vector2 getTargetPosition(){
        return blackboard.getTargetPosition();
    }

    public void setTargetPosition(Vector2 targetPosition) {
        setTargetPosition(targetPosition.x, targetPosition.y);
    }

    public void setTargetPosition(float x, float y) {
        blackboard.setTargetPosition(x, y);
    }

    public Vector2 getCurrentPosition() {
        return blackboard.getCurrentPosition();
    }

    public void setCurrentPosition(Vector2 currentPosition) {
        setCurrentPosition(currentPosition.x, currentPosition.y);
    }

    public void setCurrentPosition(float x, float y) {
        blackboard.setCurrentPosition(x, y);
    }

    public boolean isHasStopMoving() {
        return blackboard.isHasStopMoving();
    }

    public void setHasStopMoving(boolean hasStopMoving) {
        blackboard.setHasStopMoving(hasStopMoving);
    }

    public boolean isViolationOfBorders() {
        return blackboard.isViolationOfBorders();
    }

    public void setViolationOfBorders(boolean violationOfBorders) {
        blackboard.setViolationOfBorders(violationOfBorders);
    }

    public boolean isRest() {
        return blackboard.isRest();
    }

    public void setRest(boolean rest) {
        blackboard.setRest(rest);
    }

    public boolean isPatrol() {
        return blackboard.isPatrol();
    }

    public void setPatrol(boolean patrol) {
        blackboard.setPatrol(patrol);
    }
}
