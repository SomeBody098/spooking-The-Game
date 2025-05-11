package com.run.game.model.map.obstacles.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.model.BodyFactory;
import com.run.game.dto.exte.LeverDTO;
import com.run.game.model.map.obstacles.Obstacles;

public class Lever implements Obstacles {

    private final Vector2 position;

    private final Body body;

    private final LeverDTO leverDTO;

    private boolean isTouched = false;
    private boolean isActivate = false;

    public Lever(float x, float y, float wight, float height, World world) {
        position = new Vector2(x, y);
        leverDTO = new LeverDTO(getNameObstacles());
        body = BodyFactory.createPolygonBody(
            BodyFactory.BODY_TYPE.STATIC,
            true,
            true,
            x, y,
            wight, height,
            world,
            leverDTO
        );
    }

    public void update(){
        isTouched = leverDTO.isTouched();
        isActivate = leverDTO.isActivate();
    }

    @Override
    public String getNameObstacles() {
        return "lever";
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public boolean isActivate() {
        return isActivate;
    }

    public void setActivate(boolean activate) {
        isActivate = activate;
    }
}
