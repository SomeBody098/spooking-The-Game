package com.run.game.model.map.obstacles.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.BodyFactory;
import com.run.game.model.dto.exte.ObstaclesDTO;
import com.run.game.model.map.obstacles.Obstacles;

public class Wall implements Obstacles {

    private final Vector2 position;
    private final Body body;

    public Wall(float x, float y, float wight, float height, World world) {
        position = new Vector2(x, y);
        body = BodyFactory.createPolygonBody(
            BodyFactory.BODY_TYPE.STATIC,
            true,
            false,
            x, y,
            wight, height,
            world,
            new ObstaclesDTO(getNameObstacles())
        );
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public String getNameObstacles() {
        return "wall";
    }
}
