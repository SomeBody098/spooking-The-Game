package com.run.game.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.Main;
import com.run.game.model.map.obstacles.Obstacles;
import com.run.game.model.map.obstacles.impl.Barrel;
import com.run.game.model.map.obstacles.impl.Lever;
import com.run.game.model.map.obstacles.impl.Wall;

public class MapFactory {       // TODO: 07.05.2025 убрать все эти переменные, оставив получения информации через json файл

    public static final float WIGHT_FOR_WALLTOP_OR_WALLBOTTOM = 32;
    public static final float HEIGHT_FOR_WALLTOP_OR_WALLBOTTOM = 4;

    public static final float WIGHT_FOR_WALLRIGHT_OR_WALLLEFT = 4;
    public static final float HEIGHT_FOR_WALLRIGHT_OR_WALLLEFT = 32;

    public static final float WIGHT_FOR_BARREL = 26;
    public static final float HEIGHT_FOR_BARREL = 32;

    public static ObjectMap<String, Obstacles> createBodyForObstacles(TiledMap map, World world){
        ObjectMap<String, Obstacles> obstacles = new ObjectMap<>();
        TiledMapTileLayer obstaclesLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");

        float toCenterX = (float) obstaclesLayer.getTileWidth() / 2 * Main.UNIT_SCALE;
        float toCenterY = (float) obstaclesLayer.getTileHeight() / 2 * Main.UNIT_SCALE;

        for (int y = 0; y < obstaclesLayer.getHeight(); y++) {
            for (int x = 0; x < obstaclesLayer.getWidth(); x++) {

                TiledMapTileLayer.Cell cell = obstaclesLayer.getCell(x, y);

                if (cell != null && cell.getTile() != null) {
                    String name = cell.getTile().getProperties().get("type", String.class);     /// для вытаскивания имени из клетки - смени свойство class на type в tsx файле таил сета!

                    if (name != null) {
                        switch (name) {
                            case "walltop":
                                obstacles.put("walltop",
                                    createWallTopOrBottom(
                                        x + toCenterX,
                                        y + toCenterY,
                                        world));
                                break;
                            case "wallbottom":
                                obstacles.put("wallbottom",
                                    createWallTopOrBottom(
                                        x + toCenterX,
                                        y + toCenterY,
                                        world));
                                break;

                            case "wallright":
                                obstacles.put("wallright",
                                    createWallRight(x,
                                        y + toCenterY,
                                        world));
                                break;

                            case "wallleft":
                                obstacles.put("wallleft",
                                    createWallLeft(
                                        x + toCenterX * 2,
                                        y + toCenterY,
                                        world));
                                break;

                            case "barrel":
                                obstacles.put("barrel",
                                    createBarrel(
                                        x + toCenterX,
                                        y + toCenterY,
                                        world));
                                break;

                            case "leveroff":
                                obstacles.put("leveroff",
                                    createLever(
                                        x + toCenterX,
                                        y + toCenterY,
                                        world));
                                break;
                        }
                    }
                }
            }
        }

        return obstacles;
    }

    private static Wall createWallTopOrBottom(float x, float y, World world){
        return new Wall(
            x, y,
            WIGHT_FOR_WALLTOP_OR_WALLBOTTOM,
            HEIGHT_FOR_WALLTOP_OR_WALLBOTTOM,
            world
        );
    }

    private static Wall createWallRight(float x, float y, World world){
        return new Wall(
            x, y,
            WIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
            HEIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
            world
        );
    }

    private static Wall createWallLeft(float x, float y, World world){
        return new Wall(
            x, y,
            WIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
            HEIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
            world
        );
    }

    private static Barrel createBarrel(float x, float y, World world){
        return new Barrel(
            x, y,
            WIGHT_FOR_BARREL,
            HEIGHT_FOR_BARREL,
            world
        );
    }

    private static Lever createLever(float x, float y, World world){
        return new Lever(
            x, y,
            WIGHT_FOR_BARREL,
            HEIGHT_FOR_BARREL,
            world
        );
    }

}
