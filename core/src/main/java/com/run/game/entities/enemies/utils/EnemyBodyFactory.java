package com.run.game.entities.enemies.utils;

import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.dto.exte.EnemySensorDTO;
import com.run.game.entities.enemies.EnemyBody;

public class EnemyBodyFactory {

    public static EnemyBody getBody(String enemyName, EnemyDTO bodyDto, EnemySensorDTO sensorDto, World world, ParamFactory.Param param){
        switch (enemyName){
            case "human":
                return createHuman(bodyDto, sensorDto, world, param);
            case "zombie":
                // some logic...
                break;
        }

        throw new IllegalArgumentException("Unknow name enemy: " + enemyName);
    }

    private static EnemyBody createHuman(EnemyDTO bodyDto, EnemySensorDTO sensorDto, World world, ParamFactory.Param param){
        return new EnemyBody(
            4,
            3,
            Main.PPM,
            Main.PPM,
            world, bodyDto, sensorDto, param
        );
    }
}
