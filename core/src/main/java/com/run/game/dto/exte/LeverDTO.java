package com.run.game.dto.exte;

import com.run.game.dto.Dto;

public class LeverDTO extends Dto {

    private boolean isTouched = false;
    private boolean isActivate = false;

    public LeverDTO(String name) {
        super(name);
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public boolean isActivate() {
        return isActivate;
    }

    public void setActivate(boolean activate) {
        isActivate = activate;
    }
}
