package com.run.game.model.character.enemy.behaviorEnemy;

import com.badlogic.gdx.math.Vector2;
import com.run.game.model.DIRECTION;

import java.util.Random;

public class HumanBehavior {

    private final Random random;

    private DIRECTION direction = DIRECTION.NONE;

    private float timePatrol = -1f;
    private float timeRest = 5f;

    private boolean isRest = true;
    private boolean isPatrol = false;

    public HumanBehavior() {
        random = new Random();
    }

    public Vector2 patrol(float delta, float speed, Vector2 position){
        if (timePatrol <= 0){
            switch (random.nextInt(4)) {
                case 0:
                    this.direction = (DIRECTION.RIGHT);
                    break;

                case 1:
                    this.direction = (DIRECTION.LEFT);
                    break;

                case 2:
                    this.direction = (DIRECTION.UP);
                    break;

                case 3:
                    this.direction = (DIRECTION.DOWN);
                    break;
            }

            timePatrol = random.nextInt(11);
            isRest = true;
        }

        if (isRest){
            timeRest -= delta;
            isPatrol = false;
        }

        if (timeRest <= 0){
            timeRest = 5f;
            isRest = false;
            isPatrol = true;
        }

        if (isPatrol){
            Vector2 enemyDirection = direction.getVector();

            position.add(speed * enemyDirection.x, speed * enemyDirection.y);
            timePatrol -= delta;
        }

        return position;
    }

    public DIRECTION getDirection() {
        return direction;
    }
}
