package com.run.game.service.character.enemy.behavior;

import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.model.character.enemy.behaviorEnemy.EnemyBehavior;
import com.run.game.model.character.enemy.behaviorEnemy.human.HumanBehavior;

public class EnemyBehaviorService {

    private final ObjectMap<String, EnemyBehavior> behaviors;

    public EnemyBehaviorService() {
        behaviors = new ObjectMap<>();

        behaviors.put("human", new HumanBehavior());
    }

    public void update(EnemyDTO dto){
        for (ObjectMap.Entry<String, EnemyBehavior> entry: behaviors.entries()){
            String nameEnemy = entry.key;
            EnemyBehavior behavior = entry.value;

            updateTasks(behavior);
            updateBlackboard(nameEnemy, behavior, dto);
        }
    }

    private void updateTasks(EnemyBehavior behavior){
        behavior.update();
    }

    private void updateBlackboard(String nameEnemy, EnemyBehavior behavior, EnemyDTO dto){
        switch (nameEnemy){ // FIXME: 23.05.2025 это - тест, нужно будет переместить и перепесать в другое место
            case "human":
                HumanBehavior humanBehavior = ((HumanBehavior) behavior);

                humanBehavior.setCurrentPosition(dto.getCurrentPosition());
                humanBehavior.setHasStopMoving(dto.isHasStopMoving());
                humanBehavior.setViolationOfBorders(dto.isViolationOfBorders());

                return;

            case "zombie":

                // some logic coming soon...

                return;
        }
    }

}
