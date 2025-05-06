package com.run.game.model.dto.exte;

import com.run.game.model.dto.Dto;

public class PlayerDTO extends Dto {

    private boolean isPlayerHasStopMoving;
    private boolean isIntangibleActive;

    public PlayerDTO(String name) {
        super(name);
    }

    public boolean isPlayerHasStopMoving() {
        return isPlayerHasStopMoving;
    }

    public void setPlayerHasStopMoving(boolean playerHasStopMoving) {
        isPlayerHasStopMoving = playerHasStopMoving;
    }

    public boolean isIntangibleActive() {
        return isIntangibleActive;
    }

    public void setIntangibleActive(boolean intangibleActive) {
        isIntangibleActive = intangibleActive;
    }
}
