package com.run.game.model.character.enemy.abstractLogic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.model.character.Character;

public abstract class Enemy implements Character {

    protected final EnemyBody body;
    protected final EnemyVision vision;

    protected final EnemyDTO dto;

    public Enemy(float x, float y, float width, float height, float angleOfView, float viewDistance, World world) {
        dto = new EnemyDTO("enemy");
        body = new EnemyBody(x, y, width, height, world, dto);
        vision = new EnemyVision(angleOfView, viewDistance);
    }

    public void update(World world, Vector2 playerPosition, boolean isAppearance){
        vision.updateVision(world, playerPosition, body.getBody().getPosition(), body.getDirection(), isAppearance);
    }

    public void dispose(){
        vision.dispose();
    }

}
