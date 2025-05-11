package com.run.game.model.characters.enemys.abstractLogic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.BodyFactory;
import com.run.game.model.DIRECTION;
import com.run.game.dto.exte.EnemyDTO;

public class EnemyBody {

    private final Body body;

    private final float width, height;

    private DIRECTION direction = DIRECTION.UP;

    public EnemyBody(float x, float y, float width, float height, World world, EnemyDTO enemyDTO) {
        body = BodyFactory.createPolygonBody(
            BodyFactory.BODY_TYPE.DYNAMIC,
            true,
            false,
            x, y,
            width, height,
            world,
            enemyDTO
        );

        this.width = width;
        this.height = height;
    }

    public Body getBody() {
        return body;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction){
        this.direction = direction;
    }
}
