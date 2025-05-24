package com.run.game.dto.exte;

import com.badlogic.gdx.math.Vector2;
import com.run.game.dto.Dto;

public class EnemyDTO extends Dto { // TODO: 22.05.2025 догодайся как все параметры перенести в HumanPatrolBehavior (для начала, а потом чтоб до всех LeafTask<EnemyBlackboard> доходило (естественно только для тех, у которых имя с именем ДТО совпадает))

    private Vector2 targetPosition;

    private Vector2 currentPosition;

    private boolean hasStopMoving;

    private boolean isViolationOfBorders;

    public EnemyDTO(String name) {
        super(name);
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Vector2 getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Vector2 currentPosition) {
        this.currentPosition = currentPosition;
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
}
