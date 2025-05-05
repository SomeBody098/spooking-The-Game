package com.run.game.model.characters.enemys.exte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.DIRECTION;
import com.run.game.model.characters.enemys.Enemy;

import java.util.Random;

public class Human extends Enemy {

    public static final float SPEED = Main.UNIT_SCALE;

    public static final float VIEW_DISTANCE = 20f;
    public static final float ANGLE_OF_VIEW = 70f;

    private final TextureRegion texture;

    private final Random random;

    private float timePatrol = -1f;
    private float timeRest = 5f;

    private boolean isRest = true;
    private boolean isPatrol = false;

    public Human(float x, float y, float width, float height, World world) { // TODO: 30.03.2025 нарисуй ему текстуру и напиши логику "убегания" от игрока
        super(x, y, width, height, ANGLE_OF_VIEW, VIEW_DISTANCE, world);

        random = new Random();

        texture = new TextureRegion(new Texture("enemy_texture/enemy.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = texture;

        float divW = (float) (currentFrame.getRegionWidth() / 2) * Main.UNIT_SCALE;
        float divH = (float) (currentFrame.getRegionHeight() / 2) * Main.UNIT_SCALE;

        batch.draw(
            currentFrame,
            enemyBody.getBody().getPosition().x - divW,
            enemyBody.getBody().getPosition().y - divH,
            enemyBody.getWidth() / Main.PPM,
            enemyBody.getHeight() / Main.PPM
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
                case 0:
                    enemyBody.setDirection(DIRECTION.RIGHT);
                    break;

                case 1:
                    enemyBody.setDirection(DIRECTION.LEFT);
                    break;

                case 2:
                    enemyBody.setDirection(DIRECTION.UP);
                    break;

                case 3:
                    enemyBody.setDirection(DIRECTION.DOWN);
                    break;
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
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
