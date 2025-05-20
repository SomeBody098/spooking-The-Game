package com.run.game.service.character.enemy.behavior;

import com.badlogic.gdx.utils.Array;
import com.run.game.model.character.enemy.behaviorEnemy.EnemyBehavior;

public class EnemyBehaviorService {

    private final Array<EnemyBehavior> behaviors;

    public EnemyBehaviorService(Array<EnemyBehavior> behaviors) {
        this.behaviors = behaviors;
    }

    public void patrol(float delta){
        for (int i = 0; i < behaviors.size; i++) {
            behaviors.get(i).patrol(delta);
        }
    }

}
