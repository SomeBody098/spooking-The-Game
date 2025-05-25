package com.run.game.entities.enemies.utils;

import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.entities.enemies.graphic.EnemyGraphics;
import com.run.game.entities.enemies.graphic.HumanGraphics;

public class EnemyGraphicFactory {
    private static final ObjectMap<String, EnemyGraphics> graphics = new ObjectMap<>();

    public static EnemyGraphics getGraphics(String enemyName){
        if (!graphics.containsKey(enemyName)){
            EnemyGraphics graphic = createGraphics(enemyName);
            graphics.put(enemyName, graphic);
            return graphic;
        }

        return graphics.get(enemyName);
    }

    private static HumanGraphics createGraphics(String enemyName){
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
