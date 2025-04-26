package com.run.game.model.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.DIRECTION;

public abstract class Enemy {

    protected final EnemyBody enemyBody;

    protected final EnemyVision enemyVision;

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

    public void dispose(){
        enemyVision.dispose();
    }

}
