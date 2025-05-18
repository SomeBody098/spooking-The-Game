package com.run.game.service.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.Main;
import com.run.game.model.map.Interactable;
import com.run.game.model.map.MapFactory;
import com.run.game.model.map.TileObject;

public class MapService {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private final ObjectMap<String, ObjectMap<String, ? extends TileObject>> mapLayers;

    private final ObjectMap<String, Interactable> interactableObject;

    public MapService(Batch batch, OrthographicCamera camera, World world) {
        map = new TmxMapLoader().load("tileset/graveyard/firstlevel/firstLevel.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, Main.UNIT_SCALE, batch);
        renderer.setView(camera);

        mapLayers = new ObjectMap<>();
        mapLayers.put("obstacles", MapFactory.createBodyForObstacles(map, world));

        interactableObject = new ObjectMap<>();
        updateInteractableObjectInMap();
    }

    public void render(MapLayer mapLayer){
        renderer.renderTileLayer((TiledMapTileLayer) mapLayer);
    }

    public void updateInteractableObjectInMap(){
        for (ObjectMap.Entry<String, ObjectMap<String, ? extends TileObject>> layer: mapLayers.entries()){
            for (ObjectMap.Entry<String, ? extends TileObject> tileObject: layer.value.entries()){
                String key = tileObject.key;
                TileObject value = tileObject.value;

                if (value instanceof Interactable &&
                    !interactableObject.containsKey(key)){

                    interactableObject.put(key, (Interactable) value);
                }
            }
        }
    }

    public void updateInteracted(){
        for (ObjectMap.Entry<String, Interactable> interactableEntry: interactableObject.entries()){
            Interactable value = interactableEntry.value;

            if (value.isTouched()) {
                value.interacted();
                break;
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public void setTile(String nameLayer, String tileName, Vector2 positionCell){
        TiledMapTileSet tileset = getTileSet("graveyardLevelsTilleSet");
        TiledMapTile newTile = getTileMapForType(tileName, tileset);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(nameLayer);
        TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();

        newCell.setTile(newTile);
        layer.setCell(
            (int) positionCell.x,
            (int) positionCell.y,
            newCell
        );
    }

    private TiledMapTile getTileMapForType(String tileName, TiledMapTileSet tileset){
        TiledMapTile newTile = null;

        for (TiledMapTile tile: tileset) {
            if (tileName.equals(tile.getProperties().get("type"))) {
                newTile = tile;
                break;
            }
        }

        if (newTile == null) throw new IllegalArgumentException("Unknow name tile: " + tileName);

        return newTile;
    }

    public TiledMapTileLayer.Cell getCellInLayer(String nameLayer, Vector2 positionCell){
        TiledMapTileLayer curentLayer = (TiledMapTileLayer) getMapLayerByName(nameLayer);
        TiledMapTileLayer.Cell cell = curentLayer.getCell((int) positionCell.x, (int) positionCell.y);

        if (cell == null) throw new IllegalArgumentException("There is no cell for positions:\nx- " + positionCell.x + "\ny- " + positionCell.y);

        return cell;
    }

    public TileObject getTileObject(String nameLayer, String nameTile){
        ObjectMap<String, ? extends TileObject> layer = mapLayers.get(nameLayer);

        return layer.get(nameTile);
    }

    public MapLayer getMapLayerByName(String nameLayer){
        MapLayer mapLayer = map.getLayers().get(nameLayer);
        if (mapLayer == null) throw new IllegalArgumentException("Unknow name layer: " + nameLayer);

        return map.getLayers().get(nameLayer);
    }

    public TiledMapTileSet getTileSet(String nameSet){
        return map.getTileSets().getTileSet(nameSet);
    }

    public ObjectMap<String, Interactable> getInteractableObject() {
        return interactableObject;
    }

    public void dispose(){
        renderer.dispose();
        map.dispose();
    }
}
