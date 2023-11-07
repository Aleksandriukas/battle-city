package com.mygdx.game.targets.tanks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.targets.Bullet;
import com.mygdx.game.targets.Target;

import java.sql.Time;
import java.util.List;

public class Tank extends Target {

    protected List<Bullet> bullets;

    protected Boolean isEnemy;

    private final Time lastFireTime = new Time(0);

    private Integer timeout = 1000;

    Tank(Float x , Float y, TiledMapTileLayer collisionLayer ,List<Bullet> bullets, Boolean isEnemy, Vector2 texturePosition){
        super(x,y,collisionLayer, CONSTANTS.TANK_TILE_SIZE, CONSTANTS.TANK_MODEL_SIZE, texturePosition, 32);

        this.bullets = bullets;

        this.isEnemy = isEnemy;
    }

    @Override
    public void render(Batch batch){
        if(!isExplored) {
            this.region.changeDirection(direction);
        }
        super.render(batch);

    }

    public void fire(){

        if(isExplored){
            return;
        }

        if(this.lastFireTime.getTime() + this.timeout > System.currentTimeMillis()){
            return;
        }

        this.lastFireTime.setTime(System.currentTimeMillis());

        Bullet bullet = new Bullet(this.tilePosition.x + this.tileSize /2  - CONSTANTS.BULLET_TILE_SIZE/2, this.tilePosition.y + this.tileSize/2 - CONSTANTS.BULLET_TILE_SIZE/2,this.collisionLayer ,direction, this.isEnemy);
        this.bullets.add(bullet);
    }

    public void setTimeout(Integer timeout){
        this.timeout = timeout;
    }

}
