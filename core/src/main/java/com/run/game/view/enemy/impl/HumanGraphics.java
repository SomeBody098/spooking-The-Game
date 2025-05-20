package com.run.game.view.enemy.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.run.game.Main;
import com.run.game.view.enemy.EnemyGraphics;

public class HumanGraphics implements EnemyGraphics {

    private final TextureRegion texture;

    public HumanGraphics() {
        texture = new TextureRegion(new Texture("enemy_texture/enemy.png"));
    }

    @Override
    public void draw(Batch batch, Vector2 position, float width, float height) {
        TextureRegion currentFrame = texture;

        float divW = (float) (currentFrame.getRegionWidth() / 2) * Main.UNIT_SCALE;
        float divH = (float) (currentFrame.getRegionHeight() / 2) * Main.UNIT_SCALE;

        batch.draw(
            currentFrame,
            position.x - divW,
            position.y - divH,
            width / Main.PPM,
            height / Main.PPM
        );
    }

    public void dispose(){
        texture.getTexture().dispose();
    }
}
