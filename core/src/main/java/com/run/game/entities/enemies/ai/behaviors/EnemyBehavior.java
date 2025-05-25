package com.run.game.entities.enemies.ai.behaviors;

import com.badlogic.gdx.math.Vector2;
import com.run.game.entities.DIRECTION;
import com.run.game.entities.enemies.EnemyBlackboard;

public abstract class EnemyBehavior {

    protected final EnemyBlackboard blackboard;

    public EnemyBehavior(EnemyBlackboard blackboard) {
        this.blackboard = blackboard;
    }

    public abstract void update();

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
        return blackboard.isStopMoving();
    }

    public void setHasStopMoving(boolean hasStopMoving) {
        blackboard.setStopMoving(hasStopMoving);
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
