package com.run.game.entities.enemies.ai.behaviors;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.run.game.entities.enemies.ai.task.EnemyVision;
import com.run.game.entities.enemies.EnemyBlackboard;

public class HumanBehavior extends EnemyBehavior {   // TODO: 22.05.2025 реализовать еще несколько Task (к примеру с логикой когда игрок пугает врага)

    private final BehaviorTree<EnemyBlackboard> behaviors;

    public HumanBehavior(EnemyBlackboard blackboard, EnemyVision vision) {
        super(blackboard);

        behaviors = new BehaviorTree<>(
            new Sequence<>(
//                new PatrolTask(),
                vision
            ),
            blackboard
        );
    }

    @Override
    public void update(){
        behaviors.step();
    }
}
