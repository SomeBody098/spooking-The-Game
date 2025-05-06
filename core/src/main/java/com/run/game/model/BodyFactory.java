package com.run.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.run.game.Main;
import com.run.game.model.dto.Dto;

public class BodyFactory {

    public enum BODY_TYPE {
        STATIC, KINEMATIC, DYNAMIC
    }

    private static BodyDef.BodyType getBodyType(BODY_TYPE bodyType){
        switch (bodyType){
            case STATIC:
                return BodyDef.BodyType.StaticBody;
            case KINEMATIC:
                return BodyDef.BodyType.KinematicBody;
            case DYNAMIC:
                return BodyDef.BodyType.DynamicBody;
        }

        throw new IllegalArgumentException("Unknow BODY_TYPE: " + bodyType);
    }

//    public static Body createEdgeBody(String name, BODY_TYPE bodyType, boolean fixedRotation, float posX, float posY, float x1, float y1, float x2, float y2, World world) {
//        BodyDef def = new BodyDef();
//
//        def.type = getBodyType(bodyType);
//        def.fixedRotation = fixedRotation;
//
//        Body body = world.createBody(def);
//
//        Shape shape = createEdgeShape(x1, y1, x2, y2);
//
//        Fixture fixture = body.createFixture(shape, 1f);
//        fixture.setUserData(name);
//        shape.dispose();
//
//        body.setTransform(posX, posY, 0);
//
//        body.setBullet(true);
//
//        return body;
//    }

//    private static EdgeShape createEdgeShape(float x1, float y1, float x2, float y2){
//        EdgeShape edgeShape = new EdgeShape();
//        edgeShape.set(x1, y1, x2, y2);
//
//        return edgeShape;
//    }

    public static Body createCircleBody(BODY_TYPE bodyType, boolean fixedRotation, boolean isSensor, float posX, float posY, float radius, World world, Dto dto) {
        BodyDef def = new BodyDef();

        def.type = getBodyType(bodyType);
        def.fixedRotation = fixedRotation;

        Body body = world.createBody(def);

        Shape shape = createCircleShape((radius / 2) * Main.UNIT_SCALE);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(dto);
        fixture.setSensor(isSensor);
        shape.dispose();

        body.setTransform(posX, posY, 0);

        body.setBullet(true);

        return body;
    }

    private static CircleShape createCircleShape(float radius){
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        return circleShape;
    }

//    public static Body createChainBody(String name, BODY_TYPE bodyType, boolean fixedRotation, float posX, float posY, float[] vertices, int offset, int length, World world) {
//        BodyDef def = new BodyDef();
//
//        def.type = getBodyType(bodyType);
//        def.fixedRotation = fixedRotation;
//
//        Body body = world.createBody(def);
//
//        Shape shape = createChainShape(vertices, offset, length);
//
//        Fixture fixture = body.createFixture(shape, 1f);
//        fixture.setUserData(name);
//        shape.dispose();
//
//        body.setTransform(posX, posY, 0);
//
//        body.setBullet(true);
//
//        return body;
//    }

//    private static ChainShape createChainShape(float[] vertices, int offset, int length){
//        ChainShape chainShape = new ChainShape();
//        chainShape.createChain(vertices, offset, length);
//
//        return chainShape;
//    }

    public static Body createPolygonBody(BODY_TYPE bodyType, boolean fixedRotation, boolean isSensor, float posX, float posY, float wight, float height, World world, Dto dto) {
        BodyDef def = new BodyDef();

        def.type = getBodyType(bodyType);
        def.fixedRotation = fixedRotation;

        Body body = world.createBody(def);

        Shape shape = createPolygonShape(
            (wight / 2) * Main.UNIT_SCALE,
            (height / 2) * Main.UNIT_SCALE
        );

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(dto);
        fixture.setSensor(isSensor);
        shape.dispose();

        body.setTransform(posX, posY, 0);

        body.setBullet(true);

        return body;
    }

    private static PolygonShape createPolygonShape(float wight, float height){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(wight, height);

        return polygonShape;
    }
}
