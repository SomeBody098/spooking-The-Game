package com.run.game.entities.enemies.utils;

import com.run.game.entities.enemies.graphic.EnemyGraphics;
import com.run.game.entities.enemies.graphic.HumanGraphics;

public class EnemyGraphicFactory {

    public static EnemyGraphics getGraphics(String enemyName){
        switch (enemyName){
            case "human":
                return new HumanGraphics();
            case "zombie":
                // some logic...
                break;
        }

        throw new IllegalArgumentException("Unknow name enemy: " + enemyName);
    }
}
