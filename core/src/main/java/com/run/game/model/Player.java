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
    public static final float TIME_FOR_SCARE = 4f;
    public static final float TIME_FOR_LAUGH = 4f;

    private final Color color = new Color(1, 1, 1, 1);

    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> runUpAnimation;
    private final Animation<TextureRegion> runDownAnimation;
    private float stateTimeForRunAnimations;

    private final Animation<TextureRegion> onSleepingAnimation;
    private float stateTimeForOnSleepingAnimation;

    private final Animation<TextureRegion> sleepAnimation;
    private float stateTimeForSleepingAnimation;

    private final Animation<TextureRegion> readyToScareLeft;
    private final Animation<TextureRegion> readyToScareDown;


    private final Animation<TextureRegion> funnyScareAnimationLeft;
    private final Animation<TextureRegion> funnyScareAnimationUp;
    private final Animation<TextureRegion> funnyScareAnimationDown;
    private float stateTimeForFunnyScareAnimation;
    private boolean hasScares = false;

    private final Animation<TextureRegion> laughAnimation;
    private float stateTimeForLaughAnimation;

    private float timerBeforeSleeping;

    private final Body body;

    private final float width, height;

    private DIRECTION direction = DIRECTION.NONE;

    private boolean isAppearance = false;

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

        readyToScareLeft = new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/ready_to_scare/Ghost_left1.png")),
            new TextureRegion(new Texture("ghost_texture/ready_to_scare/Ghost_left2.png"))
        );

        readyToScareDown = new Animation<>(0.5f,
            new TextureRegion(new Texture("ghost_texture/ready_to_scare/Ghost_down1.png")),
            new TextureRegion(new Texture("ghost_texture/ready_to_scare/Ghost_down2.png"))
        );

        funnyScareAnimationLeft = new Animation<>(0.1f,
            new TextureRegion(new Texture("ghost_texture/scare/scare_funny_left1.png")),
            new TextureRegion(new Texture("ghost_texture/scare/scare_funny_left2.png"))
        );

        funnyScareAnimationDown = new Animation<>(0.1f,
            new TextureRegion(new Texture("ghost_texture/scare/scare_funny_down1.png")),
            new TextureRegion(new Texture("ghost_texture/scare/scare_funny_down2.png"))
        );

        funnyScareAnimationUp = new Animation<>(0.1f,
            new TextureRegion(new Texture("ghost_texture/scare/scare_funny_up1.png")),
            new TextureRegion(new Texture("ghost_texture/scare/scare_funny_up2.png"))
        );

        laughAnimation = new Animation<>(0.1f,
            new TextureRegion(new Texture("ghost_texture/laugh/Ghost_laugh1.png")),
            new TextureRegion(new Texture("ghost_texture/laugh/Ghost_laugh2.png"))
        );
    }

    public void update(float deltaTime, Joystick joystick, boolean buttonShowIsActive, boolean buttonScareIsActive) {
        if (!hasScares) handleInput(deltaTime, joystick, buttonShowIsActive);

        if (buttonScareIsActive && stateTimeForFunnyScareAnimation < TIME_FOR_SCARE && isAppearance){
            hasScares = true;
            stateTimeForFunnyScareAnimation += deltaTime;
        } else {
            hasScares = false;
            stateTimeForFunnyScareAnimation = 0;
        }

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

        if (isAppearance) {
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

        if (!buttonShowIsActive && isAppearance) isAppearance = false;
        else if (buttonShowIsActive) isAppearance = true;

        body.setTransform(x, y, body.getAngle());
    }

    private TextureRegion getCurrentFrame() {
        // 1. Проверка анимации сна (самый высокий приоритет).
        if (shouldPlaySleepAnimation()) {
            return getSleepAnimationFrame();
        }

        // 2. Проверка анимации засыпания
        if (shouldPlayOnSleepingAnimation()) {
            return getOnSleepingAnimationFrame();
        }

        // 3. Обработка движения/испуга
        return getMovementAnimationFrame();
    }

// --- Вспомогательные методы ---

    private boolean shouldPlaySleepAnimation() {
        return timerBeforeSleeping >= TRANSITION_TO_SLEEP +
            onSleepingAnimation.getFrameDuration();
    }

    private TextureRegion getSleepAnimationFrame() {
        return sleepAnimation.getKeyFrame(stateTimeForSleepingAnimation, true);
    }

    private boolean shouldPlayOnSleepingAnimation() {
        return timerBeforeSleeping >= TRANSITION_TO_SLEEP;
    }

    private TextureRegion getOnSleepingAnimationFrame() {
        return onSleepingAnimation.getKeyFrame(stateTimeForOnSleepingAnimation, true);
    }

    private TextureRegion getMovementAnimationFrame() {
        Animation<TextureRegion> animation = getBaseAnimation();
        TextureRegion frame = animation.getKeyFrame(stateTimeForRunAnimations, true);

        handleDirectionFlipping(frame);
        return frame;
    }

    private Animation<TextureRegion> getBaseAnimation() {
        if (!hasScares) {
            return getRunAnimation();
        }
        return getScareAnimation();
    }

    private Animation<TextureRegion> getScareAnimation() {
        switch (direction) {
            case UP: return funnyScareAnimationUp;
            case DOWN: return funnyScareAnimationDown;
            default: return funnyScareAnimationLeft;
        }
    }

    private void handleDirectionFlipping(TextureRegion frame) {
        if (direction == DIRECTION.RIGHT && !frame.isFlipX()) {
            frame.flip(true, false);
        }
        else if (direction == DIRECTION.LEFT && frame.isFlipX()) {
            frame.flip(true, false);
        }
    }

    private Animation<TextureRegion> getRunAnimation() {
        switch (direction) {
            case UP: return runUpAnimation;
            case DOWN: return runDownAnimation;
            default: return runAnimation;
        }
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
        return isAppearance;
    }

    public boolean isHasScares() {
        return hasScares;
    }

    public void dispose() {
        Arrays.stream(runAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(runUpAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(runDownAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(onSleepingAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(sleepAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(readyToScareLeft.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(readyToScareDown.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(funnyScareAnimationLeft.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(funnyScareAnimationUp.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(funnyScareAnimationDown.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
        Arrays.stream(laughAnimation.getKeyFrames()).map(TextureRegion::getTexture).forEach(Texture::dispose);
    }
}
