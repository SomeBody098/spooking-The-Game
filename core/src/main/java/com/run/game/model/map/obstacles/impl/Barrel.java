package com.run.game.model.map.obstacles.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.map.obstacles.Obstacles;

public class Barrel implements Obstacles {

    private final Vector2 position;
    private final Body body;

    public Barrel(float x, float y, float wight, float height, World world) {
        position = new Vector2(x, y);
        body = createBody(
            x, y,
            wight * Main.UNIT_SCALE,
            height * Main.UNIT_SCALE,
            world
        );
    }

    private Body createBody(float x, float y, float wight, float height, World world){
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wight / 2, height / 2);

        Fixture fixture = body.createFixture(shape, 0.0f);
        fixture.setUserData("barrel");
        shape.dispose();

        body.setTransform(x, y, 0);

        body.setBullet(true);

        return body;
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
