package com.run.game.entities.enemies.utils;

import com.badlogic.gdx.physics.box2d.World;
import com.run.game.entities.enemies.EnemyBlackboard;
import com.run.game.entities.enemies.ai.task.EnemyVision;
import com.run.game.entities.enemies.ai.behaviors.EnemyBehavior;
import com.run.game.entities.enemies.ai.behaviors.HumanBehavior;

public class BehaviorFactory {

    public static EnemyBehavior getBehavior(String enemyName, EnemyBlackboard blackboard, World world, ParamFactory.Param param){
        switch (enemyName){
            case "human":
                EnemyVision vision = new EnemyVision(world, param.angleOfView, param.viewDistance);
                return new HumanBehavior(blackboard, vision);
            case "zombie":
                // some logic...
                break;
        }

        throw new IllegalArgumentException("Unknow name enemy: " + enemyName);
    }
}
