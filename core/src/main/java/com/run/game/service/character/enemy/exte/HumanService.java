package com.run.game.service.character.enemy.exte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.character.enemy.behaviorEnemy.HumanBehavior;
import com.run.game.service.character.enemy.EnemyPhysicService;
import com.run.game.view.HumanGraphics;

public class HumanService extends EnemyPhysicService {

    public static final float SPEED = Main.UNIT_SCALE;
    public static final float VIEW_DISTANCE = Main.PPM * 5 * Main.UNIT_SCALE;
    public static final float ANGLE_OF_VIEW = 70f;

    private final HumanBehavior behavior;

    private final HumanGraphics graphics;

    public HumanService(float x, float y, float width, float height, World world) { // TODO: 30.03.2025 нарисуй ему текстуру и напиши логику "убегания" от игрока
        super(x, y, width, height, ANGLE_OF_VIEW, VIEW_DISTANCE, world);
        behavior = new HumanBehavior();
        graphics = new HumanGraphics();
    }

    public void draw(Batch batch) {
        graphics.draw(batch, body.getBody().getPosition(), body.getWidth(), body.getHeight());
    }

    public void update(float delta, World world, Vector2 playerPosition, boolean isAppearance) {
        super.update(world, playerPosition, isAppearance);

        if (vision.isHasSeesPlayer()){
            Gdx.app.log("vision", "AAAAAAAA");
        }

        body.getBody().setTransform(behavior.patrol(delta, SPEED, body.getBody().getPosition()), 0);
        body.setDirection(behavior.getDirection());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public String getName() {
        return "human";
    }
}
