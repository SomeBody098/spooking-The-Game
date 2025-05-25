package com.run.game.dto.exte;

import com.run.game.dto.Dto;

public class EnemySensorDTO extends Dto {

    private boolean isStopMoving;

    private boolean isViolationOfBorders;

    public EnemySensorDTO(String name) {
        super(name);
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
}
