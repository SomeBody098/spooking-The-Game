package com.run.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.run.game.entities.enemies.Enemy;
import com.run.game.entities.enemies.utils.EnemyFactory;

public class EnemiesController {
    private final ObjectMap<String, Enemy> enemies;

    public EnemiesController(String... enemyName) {
        enemies = new ObjectMap<>();

        for (String name: enemyName) {
            enemies.put(name, EnemyFactory.createEnemy(name));
        }
    }

    public void updateAll(Vector2 playerPosition, boolean playerIsAppearance){
        for (ObjectMap.Entry<String, Enemy> entry: enemies.entries()) {
            Enemy enemy = entry.value;

            enemy.update(playerPosition, playerIsAppearance);
        }
    }

    public void updateOnNames(Vector2 playerPosition, boolean playerIsAppearance, String... enemyName){
        for (String name: enemyName) {
            Enemy enemy = enemies.get(name);

            enemy.update(playerPosition, playerIsAppearance);
        }
    }

    public void drawAll(Batch batch){
        for (ObjectMap.Entry<String, Enemy> entry: enemies.entries()) {
            Enemy enemy = entry.value;

            enemy.draw(batch);
        }
    }

    public void drawOnNames(Batch batch, String... enemyName){
        for (String name: enemyName) {
            Enemy enemy = enemies.get(name);

            enemy.draw(batch);
        }
    }

    public void dispose(){
        for (ObjectMap.Entry<String, Enemy> entry: enemies.entries()) {
            Enemy enemy = entry.value;

            enemy.dispose();
        }
    }
}
