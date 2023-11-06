package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.sql.Time;
import java.util.List;

public class Tank extends Target {

    protected List<Bullet> bullets;

    protected Integer health;

    protected Boolean isEnemy;

    private final Time lastFireTime = new Time(0);

    protected final Vector2 texturePosition;

    private Integer timeout = 1000;

    Tank(Float x , Float y, TiledMapTileLayer collisionLayer ,List<Bullet> bullets, Boolean isEnemy, Integer health, Vector2 texturePosition){
        super(x,y,collisionLayer,CONSTANTS.TANK_TILE_SIZE, CONSTANTS.TANK_MODEL_SIZE, new Texture(Gdx.files.internal("tiles.png")));

        this.texturePosition = texturePosition;

        this.bullets = bullets;

        this.isEnemy = isEnemy;
        this.health = health;

    }

    @Override
    public void render(Batch batch){
        if(!isExplored) {
            changeRotation();
        }
        super.render(batch);

    }

    public void changeRotation(){

        if(isExplored){
            return;
        }

        if(this.direction == Bullet.Direction.UP){
            this.region = new TextureRegion(this.texture, (int)(this.texturePosition.x+ 0), (int)(this.texturePosition.y + 0),CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
        }
        if(this.direction == Bullet.Direction.DOWN){
            this.region = new TextureRegion(this.texture, (int)(this.texturePosition.x +64),(int)(this.texturePosition.y+ 0),CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
        }
        if(this.direction == Bullet.Direction.LEFT){
            this.region = new TextureRegion(this.texture,  (int)(this.texturePosition.x+ 32),(int)(this.texturePosition.y+ 0),CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
        }
        if(this.direction == Bullet.Direction.RIGHT){
            this.region = new TextureRegion(this.texture, (int)(this.texturePosition.x+ 96),(int)(this.texturePosition.y + 0) ,CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
        }
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
