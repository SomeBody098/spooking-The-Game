package com.run.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.screen.MainScreen;

public class Brick {

    private final Texture texture;

    private final Body body;

    private final float width, height;

    public Brick(float x, float y, float wight, float height, World world) {
        texture = new Texture("obstacles/brick.png");

        body = createBody(
            x * MainScreen.UNIT_SCALE,
            y * MainScreen.UNIT_SCALE,
            wight * MainScreen.UNIT_SCALE,
            height * MainScreen.UNIT_SCALE,
            world
        );

        this.width = wight;
        this.height = height;
    }

    public void draw(Batch batch) {
        float divW = width * MainScreen.UNIT_SCALE;
        float divH = height * MainScreen.UNIT_SCALE;

        batch.draw(
            texture,
            body.getPosition().x - divW,
            body.getPosition().y - divH,
            width * MainScreen.UNIT_SCALE * 2,
            height * MainScreen.UNIT_SCALE * 2
        );
    }

    private Body createBody(float x, float y, float wight, float height, World world){
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wight, height);

        Fixture fixture = body.createFixture(shape, 0.0f);
        fixture.setUserData("brick");
        shape.dispose();

        body.setTransform(x, y, 0);

        body.setBullet(true);

        return body;
    }

    public void dispose(){
        texture.dispose();
    }

}
