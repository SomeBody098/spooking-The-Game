package com.run.game.service.character;

import com.badlogic.gdx.physics.box2d.World;
import com.run.game.service.character.impl.HumanService;
import com.run.game.service.character.impl.PlayerService;

public class CharacterFactory {

    public static PlayerService createPlayer(float x, float y, float wight, float height, World world){
        return new PlayerService(
            x, y,
            wight, height,
            world
        );
    }

    public static HumanService createHuman(float x, float y, float wight, float height, World world){
        return new HumanService(
            x, y,
            wight, height,
            world
        );
    }

}
