package com.run.game.model.map.obstacles.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.BodyFactory;
import com.run.game.model.map.obstacles.Obstacles;

public class Barrel implements Obstacles {

    private final Vector2 position;
    private final Body body;

    public Barrel(float x, float y, float wight, float height, World world) {
        position = new Vector2(x, y);
        body = BodyFactory.createPolygonBody(
            "barrel",
            BodyFactory.BODY_TYPE.STATIC,
            true,
            x, y,
            wight, height,
            world
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
        return "barrel";
    }
}
