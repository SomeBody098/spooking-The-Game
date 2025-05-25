package com.run.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.dto.exte.EnemySensorDTO;
import com.run.game.entities.Character;
import com.run.game.entities.enemies.ai.behaviors.EnemyBehavior;
import com.run.game.entities.enemies.graphic.EnemyGraphics;

public class Enemy implements Character {

    private final EnemyBlackboard blackboard;
    private final EnemyDTO bodyDto;
    private final EnemySensorDTO sensorDto;
    private final EnemyBehavior behavior;
    private final EnemyBody body;
    private final EnemyGraphics graphics;

    public Enemy(EnemyBlackboard blackboard, EnemyDTO dto, EnemySensorDTO sensorDto, EnemyBehavior behavior, EnemyBody body, EnemyGraphics graphics) {
        this.blackboard = blackboard;
        this.bodyDto = dto;
        this.sensorDto = sensorDto;
        this.behavior = behavior;
        this.body = body;
        this.graphics = graphics;
    }

    public void update(Vector2 playerPosition, boolean playerIsAppearance){
        updateDto();
        updateBlackboard(playerPosition, playerIsAppearance);
        behavior.update();
        body.update(blackboard.getDirection(), blackboard.getTargetPosition());

        if (blackboard.isSeePlayer()){
            Gdx.app.log("see", "ВИДИТ");
        }
    }

    private void updateDto(){
        bodyDto.setTargetPosition(blackboard.getTargetPosition());
        bodyDto.setCurrentPosition(blackboard.getCurrentPosition());
    }

    private void updateBlackboard(Vector2 playerPosition, boolean playerIsAppearance){
        blackboard.setPlayerisAppearance(playerIsAppearance);
        blackboard.setPlayerPosition(playerPosition.x, playerPosition.y);
        blackboard.setStopMoving(sensorDto.isStopMoving());
        blackboard.setViolationOfBorders(sensorDto.isViolationOfBorders());
    }

    public void draw(Batch batch){
        graphics.draw(batch, body.getBody().getPosition(), body.getWidth(), body.getHeight());
    }

    public void dispose(){
        graphics.dispose();
    }

    @Override
    public String getName() {
        return bodyDto.getName();
    }
}
