package com.run.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.run.game.dto.Dto;
import com.run.game.dto.exte.EnemyDTO;
import com.run.game.dto.exte.LeverDTO;
import com.run.game.dto.exte.PlayerDTO;

public class MainContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        leverHandler(aDto, bDto);
    }

    @Override
    public void endContact(Contact contact) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        leverReset(aDto, bDto);
        enemyReset(aDto, bDto);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        playerHandler(contact, aDto, bDto);
        enemyHandler(aDto, bDto);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private Dto getDtoFromFixture(Fixture fixture){
        if (fixture.getUserData() == null) return null;

        return (Dto) fixture.getUserData();
    }

    private void playerHandler(Contact contact, Dto aDto, Dto bDto){
        PlayerDTO playerDTO = null;
        Dto lastDto = null;

        if (isPlayerDto(aDto)) {
            playerDTO = (PlayerDTO) aDto;
            lastDto = bDto;

        } else if (isPlayerDto(bDto)) {
            playerDTO = (PlayerDTO) bDto;
            lastDto = aDto;
        }

        if (playerDTO == null || !playerDTO.isIntangibleActive()) return;

        if (!isWallDto(lastDto)) contact.setEnabled(false);
    }

    private void enemyHandler(Dto aDto, Dto bDto){
        EnemyDTO enemyDTO = null;
        Dto lastDto = null;

        if (isEnemyDto(aDto)) {
            enemyDTO = (EnemyDTO) aDto;
            lastDto = bDto;

        } else if (isEnemyDto(bDto)) {
            enemyDTO = (EnemyDTO) bDto;
            lastDto = aDto;
        }

        if (enemyDTO == null) return;

        if (isPlayerDto(lastDto)){
            enemyDTO.setViolationOfBorders(true);
        } else {
            enemyDTO.setHasStopMoving(true);
        }
    }

    private void enemyReset(Dto aDto, Dto bDto){
        if (isEnemyDto(aDto)) {
            EnemyDTO enemyDTO = ((EnemyDTO) aDto);
            enemyDTO.setHasStopMoving(false);
            enemyDTO.setViolationOfBorders(false);

        } else if (isEnemyDto(bDto)) {
            EnemyDTO enemyDTO = ((EnemyDTO) bDto);
            enemyDTO.setHasStopMoving(false);
            enemyDTO.setViolationOfBorders(false);
        }
    }

    private void leverHandler(Dto aDto, Dto bDto){
        LeverDTO leverDTO = null;
        Dto lastDto = null;

        if (isLeverDto(aDto)){
            leverDTO = (LeverDTO) aDto;
            lastDto = bDto;

        } else if (isLeverDto(bDto)){
            leverDTO = (LeverDTO) bDto;
            lastDto = aDto;
        }

        if (leverDTO == null) return;

        if (isPlayerDto(lastDto) && !leverDTO.isActivate()) {
            leverDTO.setTouched(true);
        }
    }

    private void leverReset(Dto aDto, Dto bDto){
        if (isLeverDto(aDto)) ((LeverDTO) aDto).setTouched(false);
        else if (isLeverDto(bDto)) ((LeverDTO) bDto).setTouched(false);
    }

    private boolean isPlayerDto(Dto dto){
        return dto.getName().equals("player");
    }

    private boolean isEnemyDto(Dto dto){
        return dto.getName().contains("enemy");
    }

    private boolean isWallDto(Dto dto){
        return dto.getName().equals("wall");
    }

    private boolean isLeverDto(Dto dto){
        return dto.getName().equals("lever");
    }
}
