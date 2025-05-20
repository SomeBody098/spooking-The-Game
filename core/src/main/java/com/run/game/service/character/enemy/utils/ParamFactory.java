package com.run.game.service.character.enemy.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class ParamFactory {

    private static final String CONFIG_PATH = "data/enemy_parameters.json"; // Путь к файлу
    private static final JsonValue root; // Корневой объект JSON

    static {
        // Загружаем JSON при первом обращении к фабрике
        FileHandle file = Gdx.files.internal(CONFIG_PATH);
        root = new JsonReader().parse(file);
    }

    public static Param getParamForEnemy(String enemyName) {
        // Проверяем, есть ли такой враг в конфиге
        if (!root.has(enemyName)) {
            throw new IllegalArgumentException("Unknown enemy: " + enemyName);
        }

        // Парсим параметры для указанного врага
        JsonValue enemyJson = root.get(enemyName);
        Param param = new Param();
        param.speed = enemyJson.getFloat("speed");
        param.viewDistance = enemyJson.getFloat("viewDistance");
        param.angleOfView = enemyJson.getFloat("angleOfView");

        return param;
    }

    public static class Param {
        public float speed;
        public float viewDistance;
        public float angleOfView;
    }
}
