package com.run.game.entities.enemies.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public interface EnemyGraphics {
    void draw(Batch batch, Vector2 position, float width, float height);
    void dispose();
}
