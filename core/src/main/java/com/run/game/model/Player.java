package com.run.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.ui.Joystick;
import com.run.game.screen.MainScreen;

import java.util.Arrays;

public class Player {

    public static final float SPEED = MainScreen.PPM / 5 * MainScreen.UNIT_SCALE;
    public static final float TRANSITION_TO_SLEEP = 15f;

    private final Color color = new Color(1, 1, 1, 1);

    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> runUpAnimation;
    private final Animation<TextureRegion> runDownAnimation;
    private float stateTimeForRunAnimations;

    private final Animation<TextureRegion> onSleepingAnimation;
    private float stateTimeForOnSleepingAnimation;

    private final Animation<TextureRegion> sleepAnimation;
    private float stateTimeForSleepingAnimation;

    private float timerBeforeSleeping;

    private final Body body;

    private final float width, height;

    private DIRECTION direction = DIRECTION.NONE;

    private boolean appearance = false;

    private float transparency = 0.2f;

    public Player(float x, float y, float wight, float height, World world) {
        body = createBody(
            x * MainScreen.UNIT_SCALE,
            y * MainScreen.UNIT_SCALE,
            wight * MainScreen.UNIT_SCALE,
            height * MainScreen.UNIT_SCALE,
            world
        );

        this.width = wight;
        this.height = height;

        runAnimation = new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/run/Ghost_run1.png")),
            new TextureRegion(new Texture("ghost_texture/run/Ghost_run2.png"))
        );

        runUpAnimation = new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/run/Ghost_run_up1.png")),
            new TextureRegion(new Texture("ghost_texture/run/Ghost_run_up2.png"))
        );

        runDownAnimation = new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/run/Ghost_run_down1.png")),
            new TextureRegion(new Texture("ghost_texture/run/Ghost_run_down2.png"))
        );

        onSleepingAnimation = new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/has_sleeping/ghost_has_sleeping1.png")),
            new TextureRegion(new Texture("ghost_texture/has_sleeping/ghost_has_sleeping2.png")),
            new TextureRegion(new Texture("ghost_texture/has_sleeping/ghost_has_sleeping3.png")),
            new TextureRegion(new Texture("ghost_texture/has_sleeping/ghost_has_sleeping4.png"))
        );

        sleepAnimation =  new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/sleep/ghost_sleep1.png")),
            new TextureRegion(new Texture("ghost_texture/sleep/ghost_sleep2.png")),
            new TextureRegion(new Texture("ghost_texture/sleep/ghost_sleep3.png")),
            new TextureRegion(new Texture("ghost_texture/sleep/ghost_sleep4.png"))
        );
    }

    public void update(float deltaTime, Joystick joystick, boolean buttonShowIsActive) {
        handleInput(deltaTime, joystick, buttonShowIsActive);

        if (timerBeforeSleeping >= TRANSITION_TO_SLEEP + onSleepingAnimation.getFrameDuration() * onSleepingAnimation.getKeyFrames().length
            && direction == DIRECTION.NONE) {
            stateTimeForSleepingAnimation += deltaTime;

        } else if (timerBeforeSleeping >= TRANSITION_TO_SLEEP) {
            stateTimeForOnSleepingAnimation += deltaTime;

        } else {
            stateTimeForRunAnimations += deltaTime;
            stateTimeForSleepingAnimation = 0;
            stateTimeForOnSleepingAnimation = 0;
        }
    }


    public void draw(Batch batch, float parentAlpha) {
        float divW = width * MainScreen.UNIT_SCALE;
        float divH = height * MainScreen.UNIT_SCALE;

        TextureRegion currentFrame = getCurrentFrame();

        if (appearance) {
            if (transparency >= 1) transparency = 1;
            else transparency += parentAlpha;
        } else {
            if (transparency <= 0.2) transparency = 0.2f;
            else transparency -= parentAlpha;
        }

        batch.setColor(color.r, color.g, color.b, color.a * transparency);

        batch.draw(
            currentFrame,
            body.getPosition().x - divW,
            body.getPosition().y - divH,
            width * MainScreen.UNIT_SCALE * 2,
            height * MainScreen.UNIT_SCALE * 2
        );

        batch.setColor(Color.WHITE);

//        Gdx.app.log("Drawing Player", "Draw at X: " + (body.getPosition().x - divW) + ", Y: " + (body.getPosition().y - divH)); // логи, отслеживают координаты игрока
    }

    private void handleInput(float delta, Joystick joystick, boolean buttonShowIsActive) {
        boolean playerHasStopMoving = true;
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        if (joystick.isActive()){
            Vector2 position = joystick.getNorPositionStick();
            direction = joystick.getDirection();

            x += position.x * SPEED;
            y += position.y * SPEED;

            playerHasStopMoving = false;
        }

        if (playerHasStopMoving) {
            direction = DIRECTION.NONE;
            timerBeforeSleeping += delta;
        } else {
            timerBeforeSleeping = 0;
        }

        if (!buttonShowIsActive && appearance) appearance = false;
        else if (buttonShowIsActive) appearance = true;

        body.setTransform(x, y, body.getAngle());
    }

    private TextureRegion getCurrentFrame(){
        TextureRegion currentFrame;

        if (timerBeforeSleeping >= TRANSITION_TO_SLEEP + onSleepingAnimation.getFrameDuration() * onSleepingAnimation.getKeyFrames().length) { // onSleepingAnimation отработал?
            currentFrame = sleepAnimation.getKeyFrame(stateTimeForSleepingAnimation, true);

        } else if (timerBeforeSleeping >= TRANSITION_TO_SLEEP) {    // игрок стоит без движения слишком долго?
            currentFrame = onSleepingAnimation.getKeyFrame(stateTimeForOnSleepingAnimation, true);

        } else {    // иначе

            if (direction == DIRECTION.UP) {    // игрок перемешаятся вверх?
                currentFrame = runUpAnimation.getKeyFrame(stateTimeForRunAnimations, true);

            } else if (direction == DIRECTION.DOWN) { // игрок перемешаятся вниз?
                currentFrame = runDownAnimation.getKeyFrame(stateTimeForRunAnimations, true);

            } else {
                currentFrame = runAnimation.getKeyFrame(stateTimeForRunAnimations, true);

                if (direction == DIRECTION.NONE) {
                    currentFrame.flip(false, false);

                } else if (direction == DIRECTION.RIGHT && !currentFrame.isFlipX()) {   // игрок перемешаятся вправо?

                    currentFrame.flip(true, false);
                } else if (direction == DIRECTION.LEFT && currentFrame.isFlipX()) {     // игрок перемешаятся влево?

                    currentFrame.flip(true, false);
                }
            }
        }

        return currentFrame;
    }

    private Body createBody(float x, float y, float wight, float height, World world) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(x, y);

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wight / 2, height / 2);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData("player");
        shape.dispose();

        body.setBullet(true);

        return body;
    }

    public Body getBody() {
        return body;
    }

    public boolean isAppearance() {
        return appearance;
    }

    public void dispose() {
        Arrays.stream(runAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(runUpAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(runDownAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(onSleepingAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(sleepAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
    }
}
