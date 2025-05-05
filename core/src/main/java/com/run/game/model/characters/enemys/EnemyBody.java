package com.run.game.model.characters.enemys;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.BodyFactory;
import com.run.game.model.DIRECTION;

public class EnemyBody {

    private final Body body;

    private final float width, height;

    private DIRECTION direction;

    public EnemyBody(float x, float y, float width, float height, World world) {
        body = BodyFactory.createPolygonBody(
            "enemy",
            BodyFactory.BODY_TYPE.DYNAMIC,
            true,
            x, y,
            width, height,
            world
        );

        this.width = width;
        this.height = height;

        direction = DIRECTION.NONE;
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
