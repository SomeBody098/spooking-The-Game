package com.run.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.run.game.screen.MainScreen;

public abstract class Enemy {

    public static final float VIEW_DISTANCE = MainScreen.PPM * MainScreen.UNIT_SCALE * 20;
    public static final float ANGLE_OF_VIEW = 70f;

    protected final Color color = new Color(1, 1, 1, 1);

    protected final Body body;

    private final DIRECTION direction;

    private final RayCastCallback callback;

    protected final float width, height;

    private boolean hasSeesPlayer = false;

    private final Pool<Vector2> vectorPool = new Pool<Vector2>() {
        @Override
        protected Vector2 newObject() {
            return new Vector2();
        }
    };

//    private final ShapeRenderer shapeRenderer;

    public Enemy(float x, float y, float width, float height, World world) {
        body = createBody(
            x * MainScreen.UNIT_SCALE,
            y * MainScreen.UNIT_SCALE,
            width * MainScreen.UNIT_SCALE,
            height * MainScreen.UNIT_SCALE,
            world
        );

        callback = new RayCastCallback();

        this.width = width;
        this.height = height;

        direction = DIRECTION.NONE;

//        shapeRenderer = new ShapeRenderer();
    }

    public abstract void draw(Batch batch, float parentAlpha);

    public void update(World world, Vector2 playerPosition, float delta, boolean isAppearance){
        if (!isAppearance) setHasSeesPlayer(false);
        else canSee(world, playerPosition);
    }

    private void canSee(World world, Vector2 playerPosition) {
        Vector2 enemyPosition = body.getPosition();

        // Получаем объекты из пула
        Vector2 toPlayer = vectorPool.obtain();
        Vector2 rayEnd = vectorPool.obtain();

        // Вычисляем направление от врага к игроку
        toPlayer.set(playerPosition).sub(enemyPosition).nor();

        // Проверяем дистанцию до игрока
        float distance = enemyPosition.dst(playerPosition);
        if (distance > VIEW_DISTANCE) {
            setHasSeesPlayer(false);
            vectorPool.free(toPlayer);
            vectorPool.free(rayEnd);
            return; // Игрок слишком далеко
        }

        // Вычисляем конечную точку луча
        rayEnd.set(enemyPosition).add(toPlayer.scl(VIEW_DISTANCE));

        // Вычисляем угол между направлением врага и направлением на игрока
        Vector2 enemyDirection = direction.getVector();
        float angle = enemyDirection.angleRad(toPlayer);

        // Угол обзора
        float viewAngle = ANGLE_OF_VIEW * MathUtils.degreesToRadians;

        // Проверяем, находится ли игрок в пределах угла обзора
        if (Math.abs(angle) <= viewAngle) {
            callback.canSeePlayer = false; // Сбрасываем перед новым RayCast
            world.rayCast(callback, enemyPosition, rayEnd);
            setHasSeesPlayer(callback.canSeePlayer);
        } else {
            setHasSeesPlayer(false);
        }

        // Возвращаем объекты в пул
        vectorPool.free(toPlayer);
        vectorPool.free(rayEnd);

//        // Отладочная отрисовка (опционально)
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.line(enemyPosition, enemyPosition.cpy().add(enemyDirection.scl(5)));
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.line(enemyPosition, playerPosition);
//        shapeRenderer.end();
    }

    private void setHasSeesPlayer(boolean hasSeesPlayer) {
        this.hasSeesPlayer = hasSeesPlayer;
    }

    public boolean isHasSeesPlayer() {
        return hasSeesPlayer;
    }

    private Body createBody(float x, float y, float wight, float height, World world) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wight, height);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData("enemy");
        shape.dispose();

        body.setTransform(x, y, 0);

        body.setBullet(true);

        return body;
    }

    public void dispose(){
        vectorPool.clear();
    }

    protected static class RayCastCallback implements com.badlogic.gdx.physics.box2d.RayCastCallback {
        public boolean canSeePlayer = false;

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            // Если луч уже прошёл больше VIEW_DISTANCE - игнорируем
            if (fraction * VIEW_DISTANCE >= VIEW_DISTANCE) {
                canSeePlayer = false;
                return -1; // Превышена дистанция
            }

            if (fixture.getUserData() != null && fixture.getUserData().equals("brick")) {
                canSeePlayer = false;
                return 0; // Стена - прекращаем RayCast
            }

            if (fixture.getUserData() != null && fixture.getUserData().equals("player")) {
                canSeePlayer = true;
                return fraction; // Игрок найден
            }

            return -1; // Игнорировать другие объекты
        }
    }
}
