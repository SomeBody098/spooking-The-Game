package com.run.game.dto;

public abstract class Dto {
    private final String name;

    public Dto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
