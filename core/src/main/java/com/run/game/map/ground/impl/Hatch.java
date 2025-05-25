package com.run.game.map.ground.impl;

import com.run.game.map.Interactable;
import com.run.game.map.ground.Ground;

public class Hatch implements Ground, Interactable {



    @Override
    public void interacted() {

    }

    @Override
    public boolean isTouched() {
        return false;
    }

    @Override
    public boolean isActivate() {
        return false;
    }
}
