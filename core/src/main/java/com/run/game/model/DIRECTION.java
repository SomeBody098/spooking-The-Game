package com.run.game.model;

import com.badlogic.gdx.math.Vector2;

public enum DIRECTION {
    RIGHT(new Vector2(1, 0)),   // Вправо
    LEFT(new Vector2(-1, 0)),   // Влево
    UP(new Vector2(0, 1)),      // Вверх
    DOWN(new Vector2(0, -1)),   // Вниз
    NONE(new Vector2(0, 0));    // Нет направления

    private final Vector2 vector;

    DIRECTION(Vector2 vector) {
        this.vector = vector;
    }

    public Vector2 getVector() {
        return vector;
    }

}
