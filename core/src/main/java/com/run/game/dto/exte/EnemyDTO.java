package com.run.game.dto.exte;

import com.badlogic.gdx.math.Vector2;
import com.run.game.dto.Dto;

public class EnemyDTO extends Dto {

    private final Vector2 targetPosition;

    private final Vector2 currentPosition;

    public EnemyDTO(String name) {
        super(name);
        targetPosition = new Vector2();
        currentPosition = new Vector2();
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition.set(targetPosition.x, targetPosition.y);
    }

    public Vector2 getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Vector2 currentPosition) {
        this.currentPosition.set(currentPosition.x, currentPosition.y);
    }
}
