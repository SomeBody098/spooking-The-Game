package com.run.game.model.enemy.exte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.model.enemy.Enemy;
import com.run.game.screen.GameScreen;

import java.util.Random;

public class Human extends Enemy {

    public static final float SPEED = Main.UNIT_SCALE;

    public static final float VIEW_DISTANCE = 20f;
    public static final float ANGLE_OF_VIEW = 70f;

    private final Texture currentFrame;

    private final Random random;

    private float timePatrol = -1f;
    private float timeRest = 5f;

    private boolean isRest = true;
    private boolean isPatrol = false;

    public Human(float x, float y, float width, float height, World world) { // TODO: 30.03.2025 нарисуй ему текстуру и напиши логику "убегания" от игрока
        super(x, y, width, height, ANGLE_OF_VIEW, VIEW_DISTANCE, world);

        random = new Random();

        currentFrame = new Texture("enemy_texture/enemy.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float divW = enemyBody.getWidth() * Main.UNIT_SCALE;
        float divH = enemyBody.getHeight() * Main.UNIT_SCALE;

        batch.draw(
            currentFrame,
            enemyBody.getBody().getPosition().x - divW,
            enemyBody.getBody().getPosition().y - divH,
            enemyBody.getWidth() * Main.UNIT_SCALE * 2,
            enemyBody.getHeight() * Main.UNIT_SCALE * 2
        );
    }

    @Override
    public void update(World world, Vector2 playerPosition, float delta, boolean isAppearance) {
        super.update(world, playerPosition, delta, isAppearance);

        if (enemyVision.isHasSeesPlayer()){
            Gdx.app.log("vision", "AAAAAAAA");
        }

        patrol(delta);
    }

    private void patrol(float delta){
        if (enemyBody.getDirection() == DIRECTION.NONE){
            switch (random.nextInt(4)){
                case 0: enemyBody.setDirection(DIRECTION.RIGHT);
                case 1: enemyBody.setDirection(DIRECTION.LEFT);
                case 2: enemyBody.setDirection(DIRECTION.UP);
                case 3: enemyBody.setDirection(DIRECTION.DOWN);
            }
        }

        if (timePatrol <= 0){
            switch (enemyBody.getDirection()){
                case RIGHT: {
                    enemyBody.setDirection(DIRECTION.LEFT);
                    break;
                }
                case LEFT: {
                    enemyBody.setDirection(DIRECTION.RIGHT);
                    break;
                }
                case UP: {
                    enemyBody.setDirection(DIRECTION.DOWN);
                    break;
                }
                case DOWN: {
                    enemyBody.setDirection(DIRECTION.UP);
                    break;
                }
            }

            timePatrol = random.nextInt(11);
            isRest = true;
        }

        if (isRest){
            timeRest -= delta;
            isPatrol = false;
        }

        if (timeRest <= 0){
            timeRest = 5f;
            isRest = false;
            isPatrol = true;
        }

        if (isPatrol){
            Vector2 enemyDirection = enemyBody.getDirection().getVector();
            float x = enemyBody.getBody().getPosition().x;
            float y = enemyBody.getBody().getPosition().y;

            enemyBody.getBody().setTransform(
                x + SPEED * enemyDirection.x,
                y + SPEED * enemyDirection.y,
                0
            );

            timePatrol -= delta;
        }

        Gdx.app.log("enemy position", enemyBody.getBody().getPosition() + "");
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
