package com.run.game.screen;

import com.badlogic.gdx.Screen;
import com.run.game.Main;

public class ScreenFactory {

    public static Screen createScreen(Main main, SCREEN_TYPE type){
        switch (type){
            case MAIN: return new MainScreen(main, main.getUiBatch(), main.getUiCamera());
            case GAME: return new GameScreen(main, main.getBatch(), main.getUiBatch(), main.getGameCamera(), main.getUiCamera(), main.getWorld());
        }

        throw new IllegalArgumentException("Unknown screen type - " + type);
    }

}
