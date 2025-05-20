package com.run.game.model.character.enemy.behaviorEnemy;

import com.run.game.model.DIRECTION;

public interface EnemyBehavior {
    DIRECTION patrol(float delta);
}
