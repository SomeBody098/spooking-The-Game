package com.run.game.service.character.enemy.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.run.game.view.enemy.EnemyGraphics;

public class EnemyGraphicsService {

    private final Array<EnemyGraphics> graphics;

    public EnemyGraphicsService(Array<EnemyGraphics> graphics) {
        this.graphics = graphics;
    }

    public void drawAll(Batch batch, Vector2 position, float width, float height) {
        for (int i = 0; i < graphics.size; i++) {
            drawById(i, batch, position, width, height);
        }
    }

    public void drawById(int index, Batch batch, Vector2 position, float width, float height){
        EnemyGraphics graphic = graphics.get(index);

        graphic.draw(batch, position, width, height);
    }
}
