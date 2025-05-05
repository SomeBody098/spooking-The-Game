package com.run.game.model.characters.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.model.BodyFactory;
import com.run.game.model.ui.Joystick;

public class PlayerBody {

    public static final float SPEED = Main.UNIT_SCALE;

    private final Body body;

    private final float width, height;

    private DIRECTION direction = DIRECTION.NONE;

    private boolean isPlayerHasStopMoving = false;

    private boolean isIntangibleActive = true;

    public PlayerBody(float x, float y, float wight, float height, World world) {
        body = BodyFactory.createPolygonBody(
            "player",
            BodyFactory.BODY_TYPE.DYNAMIC,
            true,
            x, y,
            wight / 2,  // FIXME: 05.05.2025 возможны проблемы изза произвльного деления на 4 (и в фабричном классе и здесь есть деление на 2, в сумме 4)
            height / 2,   // FIXME: 05.05.2025 возможны проблемы изза произвльного деления на 4 (и в фабричном классе и здесь есть деление на 2, в сумме 4)
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
