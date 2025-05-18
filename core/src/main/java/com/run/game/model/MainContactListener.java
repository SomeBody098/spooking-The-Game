package com.run.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.run.game.dto.Dto;
import com.run.game.dto.exte.LeverDTO;
import com.run.game.dto.exte.PlayerDTO;

public class MainContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        LeverDTO leverDTO = null;
        if (isLeverDto(aDto)){
            leverDTO = (LeverDTO) aDto;
        } else if (isLeverDto(bDto)){
            leverDTO = (LeverDTO) bDto;
        }

        if (leverDTO == null) return;

        if (isPlayerDto(aDto) && !leverDTO.isActivate()) {
            ((LeverDTO) bDto).setTouched(true);

        } else if (isPlayerDto(bDto) && !leverDTO.isActivate()) {
            ((LeverDTO) aDto).setTouched(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        if (isLeverDto(aDto)) ((LeverDTO) aDto).setTouched(false);
        else if (isLeverDto(bDto)) ((LeverDTO) bDto).setTouched(false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Dto aDto = getDtoFromFixture(contact.getFixtureA());
        Dto bDto = getDtoFromFixture(contact.getFixtureB());

        if (aDto == null || bDto == null) return;

        if (isPlayerDto(aDto)) {
            PlayerDTO playerDTO = (PlayerDTO) aDto;
            playerHandler(contact, playerDTO, bDto);

        } else if (isPlayerDto(bDto)) {
            PlayerDTO playerDTO = (PlayerDTO) bDto;
            playerHandler(contact, playerDTO, aDto);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private Dto getDtoFromFixture(Fixture fixture){
        if (fixture.getUserData() == null) return null;

        return (Dto) fixture.getUserData();
    }

    private void playerHandler(Contact contact, PlayerDTO playerDTO, Dto dto){
        if (playerDTO == null || !playerDTO.isIntangibleActive()) return;

        if (!isWallDto(dto)) contact.setEnabled(false);
    }

    private void leverHandler(LeverDTO leverDto){
        leverDto.setTouched(true);
    }

    private boolean isPlayerDto(Dto dto){
        return dto.getName().equals("player");
    }

    private boolean isWallDto(Dto dto){
        return dto.getName().equals("wall");
    }

    private boolean isLeverDto(Dto dto){
        return dto.getName().equals("lever");
    }
}
