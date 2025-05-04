package com.run.game.model.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.model.ui.Joystick;
import com.run.game.screen.GameScreen;

public class PlayerBody {

    public static final float SPEED = Main.UNIT_SCALE;

    private final Body body;

    private final float width, height;

    private DIRECTION direction = DIRECTION.NONE;

    private boolean isPlayerHasStopMoving = false;

    private boolean isIntangibleActive = true;

    public PlayerBody(float x, float y, float wight, float height, World world) {
        body = createBody(
            x, y,
            wight * Main.UNIT_SCALE,
            height * Main.UNIT_SCALE,
            world
        );

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                if (!isIntangibleActive) return;

                boolean fixtureAIsPlayer = contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("player");
                boolean fixtureBIsPlayer = contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("player");
                boolean fixtureAIsWall = contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("wall");
                boolean fixtureBIsWall = contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("wall");

                if ((fixtureAIsPlayer || fixtureBIsPlayer) && !(fixtureBIsWall || fixtureAIsWall)) contact.setEnabled(false);


            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        this.width = wight;
        this.height = height;
    }

    public void handleInput(Joystick joystick) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        if (joystick.isActive()){
            float joystickX = joystick.getNorPositionStickX();
            float joystickY = joystick.getNorPositionStickY();
            direction = joystick.getDirection();

            x += joystickX * SPEED;
            y += joystickY * SPEED;

            isPlayerHasStopMoving = false;
        } else {
            isPlayerHasStopMoving = true;
        }

        body.setTransform(x, y, body.getAngle());
    }

    private Body createBody(float x, float y, float wight, float height, World world) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(x, y);

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wight / 4, height / 4); // FIXME: 02.05.2025 возможные проблемы из-за деления на 4, (лучше будет на 2) я сделал так для уменшения коллизии игрока

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData("player");
        shape.dispose();

        body.setBullet(true);

        return body;
    }

    public void setIntangible(boolean intangible){
        isIntangibleActive = intangible;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public boolean isPlayerHasStopMoving() {
        return isPlayerHasStopMoving;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
