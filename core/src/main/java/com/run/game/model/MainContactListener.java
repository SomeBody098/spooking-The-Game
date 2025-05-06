package com.run.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.run.game.model.dto.Dto;
import com.run.game.model.dto.exte.PlayerDTO;

public class MainContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        boolean fixtureAIsPlayer = contact.getFixtureA().getUserData() != null && ((Dto) contact.getFixtureA().getUserData()).getName().equals("player");
        boolean fixtureBIsPlayer = contact.getFixtureB().getUserData() != null && ((Dto) contact.getFixtureB().getUserData()).getName().equals("player");
        boolean fixtureAIsWall = contact.getFixtureA().getUserData() != null && ((Dto) contact.getFixtureA().getUserData()).getName().equals("wall");
        boolean fixtureBIsWall = contact.getFixtureB().getUserData() != null && ((Dto) contact.getFixtureB().getUserData()).getName().equals("wall");

        PlayerDTO playerDTO;
        if (fixtureAIsPlayer) playerDTO = (PlayerDTO) contact.getFixtureA().getUserData();
        else playerDTO = fixtureBIsPlayer ? (PlayerDTO) contact.getFixtureB().getUserData() : null;

        if (playerDTO != null && !playerDTO.isIntangibleActive()) return;

        if ((fixtureAIsPlayer || fixtureBIsPlayer) && !(fixtureBIsWall || fixtureAIsWall)) contact.setEnabled(false);


    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
