package com.run.game.model.enemy.exte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.enemy.Enemy;
import com.run.game.screen.MainScreen;

public class Human extends Enemy {

    public static final float VIEW_DISTANCE = 20;
    public static final float ANGLE_OF_VIEW = 70f;

    private final Texture currentFrame;

    public Human(float x, float y, float width, float height, World world) { // TODO: 30.03.2025 нарисуй ему текстуру и напиши логику "убегания" от игрока
        super(x, y, width, height, ANGLE_OF_VIEW, VIEW_DISTANCE, world);

        currentFrame = new Texture("enemy_texture/enemy.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float divW = getWidth() * MainScreen.UNIT_SCALE;
        float divH = getHeight() * MainScreen.UNIT_SCALE;

        batch.draw(
            currentFrame,
            getPosition().x - divW,
            getPosition().y - divH,
            getWidth() * MainScreen.UNIT_SCALE * 2,
            getHeight() * MainScreen.UNIT_SCALE * 2
        );
    }

    @Override
    public void update(World world, Vector2 playerPosition, float delta, boolean isAppearance) {
        super.update(world, playerPosition, delta, isAppearance);

        if (super.isHasSeesPlayer()){
            getBody().setLinearVelocity(10, 10);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
