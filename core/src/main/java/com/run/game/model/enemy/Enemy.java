package com.run.game.model.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.DIRECTION;

public abstract class Enemy {

    private final EnemyBody enemyBody;

    private final EnemyVision enemyVision;

    public Enemy(float x, float y, float width, float height, float angleOfView, float viewDistance, World world) {
        enemyBody = new EnemyBody(x, y, width, height, world);
        enemyVision = new EnemyVision(angleOfView, viewDistance);

//        shapeRenderer = new ShapeRenderer();
    }

    public abstract void draw(Batch batch, float parentAlpha);

    public void update(World world, Vector2 playerPosition, float delta, boolean isAppearance){
        if (!isAppearance) {
            enemyVision.setHasSeesPlayer(false);
        } else {
            enemyVision.canSee(world, playerPosition,
                enemyBody.getBody().getPosition(),
                enemyBody.getDirection()
            );
        }
    }

    public boolean isHasSeesPlayer() {
        return enemyVision.isHasSeesPlayer();
    }

    public Body getBody() {
        return enemyBody.getBody();
    }

    public Vector2 getPosition(){
        return enemyBody.getBody().getPosition();
    }

    public float getWidth() {
        return enemyBody.getWidth();
    }

    public float getHeight() {
        return enemyBody.getHeight();
    }

    public DIRECTION getDirection() {
        return enemyBody.getDirection();
    }

    public void setDirection(DIRECTION direction){
        enemyBody.setDirection(direction);
    }

    public void dispose(){
        enemyVision.dispose();
    }

}
