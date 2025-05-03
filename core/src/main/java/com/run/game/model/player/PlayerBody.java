package com.run.game.model.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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

    private boolean hasIntangible = true;

    public PlayerBody(float x, float y, float wight, float height, World world) {
        body = createBody(
            x, y,
            wight * Main.UNIT_SCALE,
            height * Main.UNIT_SCALE,
            world
        );

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
        fixture.setSensor(hasIntangible);
        shape.dispose();

        body.setBullet(true);

        return body;
    }

    public void setIntangible(boolean intangible){
        hasIntangible = intangible;

        body.getFixtureList().get(0).setSensor(intangible);   // FIXME: 02.05.2025 несовсем верная логика, проходит сквозь стены
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
