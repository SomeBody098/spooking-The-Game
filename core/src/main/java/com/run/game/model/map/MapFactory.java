package com.run.game.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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

    public static Array<Obstacles> createBodyForObstacles(TiledMap map, World world){
        Array<Obstacles> obstacles = new Array<>();
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
                            case "wallbottom":
                                obstacles.add(new Wall(
                                    x + toCenterX,
                                    y + toCenterY,
                                    WIGHT_FOR_WALLTOP_OR_WALLBOTTOM,
                                    HEIGHT_FOR_WALLTOP_OR_WALLBOTTOM,
                                    world
                                    ));
                                break;

                            case "wallright":
                                obstacles.add(new Wall(
                                    x,
                                    y + toCenterY,
                                    WIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
                                    HEIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
                                    world
                                ));
                                break;

                            case "wallleft":
                                obstacles.add(new Wall(
                                    x + toCenterX * 2,
                                    y + toCenterY,
                                    WIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
                                    HEIGHT_FOR_WALLRIGHT_OR_WALLLEFT,
                                    world
                                ));
                                break;

                            case "barrel":
                                obstacles.add(new Barrel(
                                    x + toCenterX,
                                    y + toCenterY,
                                    WIGHT_FOR_BARREL,
                                    HEIGHT_FOR_BARREL,
                                    world
                                ));
                                break;

                            case "lever":
                                obstacles.add(new Lever(
                                    x + toCenterX,
                                    y + toCenterY,
                                    WIGHT_FOR_BARREL,
                                    HEIGHT_FOR_BARREL,
                                    world
                                ));
                                break;
                        }
                    }
                }
            }
        }

        return obstacles;
    }


}
