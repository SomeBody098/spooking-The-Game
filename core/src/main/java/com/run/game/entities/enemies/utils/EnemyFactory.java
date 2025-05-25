package com.run.game.entities.enemies.utils;

import com.badlogic.gdx.physics.box2d.World;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.dto.exte.EnemySensorDTO;
import com.run.game.entities.enemies.Enemy;
import com.run.game.entities.enemies.EnemyBlackboard;
import com.run.game.entities.enemies.EnemyBody;
import com.run.game.entities.enemies.ai.behaviors.EnemyBehavior;
import com.run.game.entities.enemies.graphic.EnemyGraphics;

public class EnemyFactory {

    private static World world;

    public static void init(World world) { // Вызывается один раз при запуске игры
        EnemyFactory.world = world;
    }

    public static Enemy createEnemy(String enemyName){  // FIXME: 25.05.2025 пересмотри логику создания врага, возможны проблемы (к примеру постоянное создание Param, а это обращение к json!).
                                                        // FIXME: 25.05.2025 Скорее всего нужно будет сделать метод, типа isExist в кое каких классах. Но пока: "Работоет - не торогай!"
        ParamFactory.Param param = ParamFactory.getParamForEnemy(enemyName);
        EnemyDTO bodyDto = new EnemyDTO("enemy_" + enemyName);
        EnemySensorDTO sensorDto = new EnemySensorDTO("enemy_sensor_" + enemyName);
        EnemyBlackboard blackboard = new EnemyBlackboard();

        EnemyBehavior behavior = BehaviorFactory.getBehavior(enemyName, blackboard, world, param);
        EnemyBody body = EnemyBodyFactory.getBody(enemyName, bodyDto, sensorDto, world, param);
        EnemyGraphics graphics = EnemyGraphicFactory.getGraphics(enemyName);

        return new Enemy(blackboard, bodyDto, sensorDto, behavior, body, graphics);
    }
}
