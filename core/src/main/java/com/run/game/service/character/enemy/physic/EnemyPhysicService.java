package com.run.game.service.character.enemy.physic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.model.DIRECTION;
import com.run.game.model.character.enemy.abstractLogic.EnemyBody;
import com.run.game.model.character.enemy.abstractLogic.EnemyVision;
import com.run.game.service.character.enemy.utils.ParamFactory;

public class EnemyPhysicService {

    private final String nameEnemy;

    private final ParamFactory.Param param;

    private final EnemyBody body;
    private final EnemyVision vision;

    private final EnemyDTO dto;

    public EnemyPhysicService(String nameEnemy, float x, float y, float width, float height, World world) {
        this.nameEnemy = nameEnemy;

        param = ParamFactory.getParamForEnemy(nameEnemy);
        dto = new EnemyDTO("enemy_" + nameEnemy);
        body = new EnemyBody(x, y, width, height, world, dto);
        vision = new EnemyVision(param.angleOfView, param.viewDistance);
    }

    public void updateVision(World world, Vector2 playerPosition, boolean playerIsAppearance){
        vision.updateVision(world, playerPosition, body.getBody().getPosition(), body.getDirection(), playerIsAppearance);

        if (vision.isHasSeesPlayer()){
            Gdx.app.log("vision", "AAAAAAAA");
        }
    }

    public void updateBody(DIRECTION direction){
        Vector2 curentPosition = body.getBody().getPosition();
        Vector2 norPosition = direction.getVector();

        body.getBody().setTransform(
            curentPosition.x + (norPosition.x * param.speed),
            curentPosition.y + (norPosition.y * param.speed),
            0
        );
        body.setDirection(direction);
    }

    public String getName() {
        return nameEnemy;
    }

    public void dispose(){
        vision.dispose();
    }
}
