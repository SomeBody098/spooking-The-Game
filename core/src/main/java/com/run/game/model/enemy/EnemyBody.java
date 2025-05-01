package com.run.game.model.enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.screen.GameScreen;

public class EnemyBody {

    private final Body body;

    private final float width, height;

    private DIRECTION direction;

    public EnemyBody(float x, float y, float width, float height, World world) {
        body = createBody(
            x,
            y,
            width * Main.UNIT_SCALE,
            height * Main.UNIT_SCALE,
            world
        );

        this.width = width;
        this.height = height;

        direction = DIRECTION.NONE;
    }

    private Body createBody(float x, float y, float wight, float height, World world) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wight, height);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData("enemy");
        shape.dispose();

        body.setTransform(x, y, 0);

        body.setBullet(true);

        return body;
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
