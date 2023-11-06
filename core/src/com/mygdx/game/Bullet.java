package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Target {

    private final Boolean isEnemy;
    private final Direction direction;


    Bullet(Float x , Float y, TiledMapTileLayer collisionLayer, Direction direction, Boolean isEnemy){
        super(x,y,collisionLayer,CONSTANTS.BULLET_TILE_SIZE, CONSTANTS.BULLET_MODEL_SIZE, new Texture(Gdx.files.internal("tiles.png")));

        this.texture = new Texture(Gdx.files.internal("tiles.png"));

        this.direction= direction;

        this.isEnemy = isEnemy;

        this.setSpeed(2);

        this.setRegion();
    }
    public void render(Batch batch){
        if(modelPosition.x >= 256 || modelPosition.x <= 0 || modelPosition.y >= 256 || modelPosition.y <= 0){
            dispose();
            return;
        }

        if(this.direction == Direction.UP){
            moveTo(new Vector2(0,1));
        }
        if(this.direction == Direction.DOWN){
            moveTo(new Vector2(0,-1));
        }
        if(this.direction == Direction.LEFT){
            moveTo(new Vector2(-1,0));
        }
        if(this.direction == Direction.RIGHT){
            moveTo(new Vector2(1,0));
        }

        super.render(batch);
    }

    private void setRegion(){
        if(direction == Direction.UP){
            this.region = new TextureRegion(this.texture, 320,100,this.tileSize,this.tileSize);
        }
        if(direction == Direction.LEFT){
            this.region = new TextureRegion(this.texture, 328,100,this.tileSize,this.tileSize);
        }

        if(direction == Direction.DOWN){
            this.region = new TextureRegion(this.texture, 336,100,this.tileSize,this.tileSize);
        }

        if(direction == Direction.RIGHT){
            this.region = new TextureRegion(this.texture, 344,102,this.tileSize,this.tileSize);
        }
    }

    public boolean isEnemy(){
        return this.isEnemy;
    }

}

