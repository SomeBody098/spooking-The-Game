package com.run.game.screen;

import com.badlogic.gdx.Screen;
import com.run.game.Main;

public class ScreenFactory {

    public static Screen createScreen(Main main, SCREEN_TYPE type){
        switch (type){
            case MAIN:
                return new MainScreen(
                    main,
                    main.getBatch(),
                    main.getUiCamera(),
                    main.getUiViewport()
                );

            case GAME:
                return new GameScreen(
                    main,
                    main.getBatch(),
                    main.getGameCamera(),
                    main.getGameViewport(),
                    main.getUiCamera(),
                    main.getUiViewport(),
                    main.getWorld()
                );

        }

        throw new IllegalArgumentException("Unknown screen type: " + type);
    }

}
