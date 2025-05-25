package com.run.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.run.game.entities.DIRECTION;

public class EnemyBlackboard {

    private DIRECTION direction = DIRECTION.NONE;
    private final Vector2 targetPosition;

    private final Vector2 currentPosition;
    private final Vector2 playerPosition;

    private boolean playerisAppearance;

    private boolean isSeePlayer;

    private boolean isStopMoving;
    private boolean isViolationOfBorders;
    private boolean isRest;
    private boolean isPatrol;

    public EnemyBlackboard() {
        targetPosition = new Vector2();
        currentPosition = new Vector2();
        playerPosition = new Vector2();
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

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(float x, float y) {
        playerPosition.set(x, y);
    }

    public boolean isPlayerisAppearance() {
        return playerisAppearance;
    }

    public void setPlayerisAppearance(boolean playerisAppearance) {
        this.playerisAppearance = playerisAppearance;
    }

    public boolean isSeePlayer() {
        return isSeePlayer;
    }

    public void setSeePlayer(boolean seePlayer) {
        isSeePlayer = seePlayer;
    }

    public boolean isStopMoving() {
        return isStopMoving;
    }

    public void setStopMoving(boolean stopMoving) {
        this.isStopMoving = stopMoving;
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
