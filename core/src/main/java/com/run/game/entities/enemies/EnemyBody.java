package com.run.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.dto.exte.EnemySensorDTO;
import com.run.game.entities.BodyFactory;
import com.run.game.entities.DIRECTION;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.entities.enemies.utils.ParamFactory;

public class EnemyBody {

    private final Body body;

    private final Body sensor;

    private final float width, height;

    private DIRECTION direction = DIRECTION.NONE;

    private final ParamFactory.Param param;

    public EnemyBody(float x, float y, float width, float height, World world, EnemyDTO bodyDTO, EnemySensorDTO sensorDto, ParamFactory.Param param) {
        this.param = param;

        body = BodyFactory.createPolygonBody(
            BodyFactory.BODY_TYPE.DYNAMIC,
            true,
            false,
            x, y,
            width, height,
            world,
            bodyDTO
        );

        sensor = BodyFactory.createPolygonBody(
            BodyFactory.BODY_TYPE.DYNAMIC,
            true,
            true,
            x, y,
            width + (width / 2),
            height + (height / 2),
            world,
            sensorDto
        );

        this.width = width;
        this.height = height;
    }

    public void update(DIRECTION direction, Vector2 targetPosition){
        Vector2 curentPosition = body.getPosition();
        Vector2 move = targetPosition.nor().scl(param.speed);

        body.setTransform(
            curentPosition.x + move.x,
            curentPosition.y + move.y,
            0
        );

        sensor.setTransform(body.getPosition().x, body.getPosition().y, 0);

        setDirection(direction);
    }

    public Body getBody() {
        return body;
    }

    public Body getSensor() {
        return sensor;
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
