package com.run.game.model.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.run.game.model.DIRECTION;
import com.run.game.screen.MainScreen;

import java.util.Arrays;

public class PlayerGraphics {

    public static final float TRANSITION_TO_SLEEP = 15f;
    public static final float TIME_FOR_SCARE = 2f;
    public static final float TIME_FOR_LAUGH = 2f;

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

    private final Animation<TextureRegion> readyToScareLeft;
    private final Animation<TextureRegion> readyToScareDown;


    private final Animation<TextureRegion> funnyScareAnimationLeft;
    private final Animation<TextureRegion> funnyScareAnimationUp;
    private final Animation<TextureRegion> funnyScareAnimationDown;
    private float stateTimeForFunnyScareAnimation;

    private final Animation<TextureRegion> laughAnimation;
    private float stateTimeForLaughAnimation;

    private DIRECTION direction = DIRECTION.NONE;

    private boolean hasScares = false;

    private boolean isAppearance = false;

    private float transparency = 0.2f;

    public PlayerGraphics() {
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

    public void update(float delta, boolean buttonShowIsActive, boolean buttonScareIsActive, boolean playerHasStopMoving) {
        if (!buttonShowIsActive && isAppearance && !hasScares) isAppearance = false;
        else if (buttonShowIsActive) isAppearance = true;

        if (playerHasStopMoving) {
            timerBeforeSleeping += delta;
        } else {
            timerBeforeSleeping = 0;
        }

        checkOnScare(delta, buttonScareIsActive);

        checkOnSleep(delta);
    }

    private void checkOnScare(float delta, boolean buttonScareIsActive){
        if (buttonScareIsActive && shouldPlayScareAnimation() && isAppearance && !shouldPlayLaughAnimation()){
            hasScares = true;
            stateTimeForFunnyScareAnimation += delta;

        } else if (shouldPlayLaughAnimation()){
            hasScares = false;
            stateTimeForLaughAnimation += delta;

        } else {
            hasScares = false;
            stateTimeForLaughAnimation = 0;
            stateTimeForFunnyScareAnimation = 0;
        }
    }

    private void checkOnSleep(float delta){
        if (shouldPlaySleepAnimation()) {
            stateTimeForSleepingAnimation += delta;

        } else if (shouldPlayOnSleepingAnimation()) {
            stateTimeForOnSleepingAnimation += delta;

        } else {
            stateTimeForRunAnimations += delta;
            stateTimeForSleepingAnimation = 0;
            stateTimeForOnSleepingAnimation = 0;
        }
    }

    public void draw(Batch batch, float parentAlpha, Vector2 position, float width, float height) {
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
            position.x - divW,
            position.y - divH,
            width * MainScreen.UNIT_SCALE * 2,
            height * MainScreen.UNIT_SCALE * 2
        );

        batch.setColor(Color.WHITE);

//        Gdx.app.log("Drawing Player", "Draw at X: " + (x - divW) + ", Y: " + (y - divH)); // логи, отслеживают координаты игрока
    }

    private TextureRegion getCurrentFrame() {
        // 1. Проверка анимации сна (самый высокий приоритет).
        if (shouldPlaySleepAnimation() && !hasScares) {
            return getSleepAnimationFrame();
        }

        // 2. Проверка анимации засыпания
        if (shouldPlayOnSleepingAnimation() && !hasScares) {
            return getOnSleepingAnimationFrame();
        }

        // 3. Проверка анимации смеха после "пугания"
        if (shouldPlayLaughAnimation()){
            return getLaughAnimationFrame();
        }

        // 3. Обработка движения/испуга
        return getMovementAnimationFrame();
    }

// --- Вспомогательные методы ---

    private boolean shouldPlaySleepAnimation() {
        return timerBeforeSleeping >= TRANSITION_TO_SLEEP +
            onSleepingAnimation.getAnimationDuration();
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
        if (hasScares && shouldPlayScareAnimation()) {
            return getScareAnimation();
        }

        return getRunAnimation();
    }

    private boolean shouldPlayScareAnimation() {
        return stateTimeForFunnyScareAnimation < TIME_FOR_SCARE;
    }

    private Animation<TextureRegion> getScareAnimation() {
        switch (direction) {
            case UP: return funnyScareAnimationUp;
            case DOWN: return funnyScareAnimationDown;
            default: return funnyScareAnimationLeft;
        }
    }

    private boolean shouldPlayLaughAnimation() {
        return !shouldPlayScareAnimation() && stateTimeForLaughAnimation < TIME_FOR_LAUGH;
    }

    private TextureRegion getLaughAnimationFrame() {
        return laughAnimation.getKeyFrame(stateTimeForLaughAnimation, true);
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

    public boolean isHasScares() {
        return hasScares;
    }

    public boolean isAppearance() {
        return isAppearance;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
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
