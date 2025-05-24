package com.run.game.service.character.enemy.blackboard;

import com.badlogic.gdx.math.Vector2;
import com.run.game.model.DIRECTION;

public class EnemyBlackboard {

    private DIRECTION direction;
    private final Vector2 targetPosition;

    private final Vector2 currentPosition;

    private boolean hasStopMoving;
    private boolean isViolationOfBorders;
    private boolean isRest;
    private boolean isPatrol;

    public EnemyBlackboard() {
        targetPosition = new Vector2();
        currentPosition = new Vector2();
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(float x, float y) {
        targetPosition.set(x, y);
    }

    public Vector2 getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(float x, float y) {
        currentPosition.set(x, y);
    }

    public boolean isHasStopMoving() {
        return hasStopMoving;
    }

    public void setHasStopMoving(boolean hasStopMoving) {
        this.hasStopMoving = hasStopMoving;
    }

    public boolean isViolationOfBorders() {
        return isViolationOfBorders;
    }

    public void setViolationOfBorders(boolean violationOfBorders) {
        isViolationOfBorders = violationOfBorders;
    }

    public boolean isRest() {
        return isRest;
    }

    public void setRest(boolean rest) {
        isRest = rest;
    }

    public boolean isPatrol() {
        return isPatrol;
    }

    public void setPatrol(boolean patrol) {
        isPatrol = patrol;
    }
}
