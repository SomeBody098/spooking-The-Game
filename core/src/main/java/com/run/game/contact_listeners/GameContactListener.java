package com.run.game.contact_listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.run.game.dto.Dto;
import com.run.game.dto.exte.EnemySensorDTO;
import com.run.game.dto.exte.LeverDTO;
import com.run.game.dto.exte.PlayerDTO;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        leverHandler(aDto, bDto);
        enemyHandler(aDto, bDto);
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
        EnemySensorDTO enemySensorDTO = null;
        Dto lastDto = null;

        if (isEnemySensorDto(aDto)) {
            enemySensorDTO = (EnemySensorDTO) aDto;
            lastDto = bDto;

        } else if (isEnemySensorDto(bDto)) {
            enemySensorDTO = (EnemySensorDTO) bDto;
            lastDto = aDto;
        }

        if (enemySensorDTO == null || isEnemyDto(lastDto)) return;

        if (isPlayerDto(lastDto)){  // FIXME: 25.05.2025 враг становится замороженным, так как после поподания в стену переменая isStopMoving постоянно true
            enemySensorDTO.setViolationOfBorders(true);
        } else {
            enemySensorDTO.setStopMoving(true);
        }
    }

    private void enemyReset(Dto aDto, Dto bDto){
        if (isEnemySensorDto(aDto)) {
            EnemySensorDTO enemySensorDTO = ((EnemySensorDTO) aDto);
            enemySensorDTO.setStopMoving(false);
            enemySensorDTO.setViolationOfBorders(false);

        } else if (isEnemySensorDto(bDto)) {
            EnemySensorDTO enemySensorDTO = ((EnemySensorDTO) bDto);
            enemySensorDTO.setStopMoving(false);
            enemySensorDTO.setViolationOfBorders(false);
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

    private boolean isEnemySensorDto(Dto dto){
        return dto.getName().contains("enemy_sensor");
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
