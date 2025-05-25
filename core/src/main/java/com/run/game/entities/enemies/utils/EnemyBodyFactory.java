package com.run.game.entities.enemies.utils;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.Main;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.dto.exte.EnemySensorDTO;
import com.run.game.entities.enemies.EnemyBody;

public class EnemyBodyFactory {

    private static final ObjectMap<String, EnemyBody> bodys = new ObjectMap<>();

    public static EnemyBody getBody(String enemyName, EnemyDTO bodyDto, EnemySensorDTO sensorDto, World world, ParamFactory.Param param){
        if (!bodys.containsKey(enemyName)){
            EnemyBody body = createBody(enemyName, bodyDto, sensorDto, world, param);
            bodys.put(enemyName, body);
            return body;
        }

        return bodys.get(enemyName);
    }

    private static EnemyBody createBody(String enemyName, EnemyDTO bodyDto, EnemySensorDTO sensorDto, World world, ParamFactory.Param param){
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
