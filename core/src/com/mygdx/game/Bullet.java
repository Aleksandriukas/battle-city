package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Target {

    private final Boolean isEnemy;
    private final Direction direction;

    private TiledMapTileLayer textureLayer;


    Bullet(Float x , Float y, TiledMapTileLayer collisionLayer ,Direction direction, Boolean isEnemy){
        super(x,y,collisionLayer,CONSTANTS.BULLET_TILE_SIZE, CONSTANTS.BULLET_MODEL_SIZE, new Texture(Gdx.files.internal("tiles.png")));

        this.texture = new Texture(Gdx.files.internal("tiles.png"));

        this.direction= direction;

        this.isEnemy = isEnemy;

        this.setSpeed(2F);

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

        if(!canMove()){
            this.explore();
        }


        super.render(batch);
    }


    public void setTextureLayer(TiledMapTileLayer textureLayer){
        this.textureLayer = textureLayer;
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

    public boolean canMoveUp(){
        if(modelPosition.y == 240-16){
            return false;
        }
        int xLeftTile = (int) (this.get2Point().x  /16);
        int xRightTile = (int) (this.get3Point().x /16);

        int yTile = (int) ((this.get3Point().y +1) /16 );

        boolean leftTileBlocked = collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties().containsKey("blocked");

        boolean rightTileBlocked = collisionLayer.getCell(xRightTile, yTile).getTile().getProperties().containsKey("blocked");

        if(leftTileBlocked && collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xLeftTile, yTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(leftTileBlocked && collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xLeftTile, yTile).setTile(textureLayer.getCell(5, 0).getTile());
            return false;
        }
        if(rightTileBlocked && collisionLayer.getCell(xRightTile, yTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xRightTile, yTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(rightTileBlocked && collisionLayer.getCell(xRightTile, yTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xRightTile, yTile).setTile(textureLayer.getCell(5, 0).getTile());
            return false;
        }

        return !(leftTileBlocked || rightTileBlocked);
    }

    public boolean canMoveDown(){
        if(modelPosition.y == 0){
            return false;
        }
        int xLeftTile = (int) (this.get1Point().x  /16);
        int xRightTile = (int) (this.get4Point().x /16);

        int yTile = (int) ((this.get1Point().y -1) /16 );

        boolean leftTileBlocked = collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties().containsKey("blocked");

        boolean rightTileBlocked = collisionLayer.getCell(xRightTile, yTile).getTile().getProperties().containsKey("blocked");

        if(leftTileBlocked && collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xLeftTile, yTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(leftTileBlocked && collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xLeftTile, yTile).setTile(textureLayer.getCell(3, 0).getTile());
            return false;
        }
        if(rightTileBlocked && collisionLayer.getCell(xRightTile, yTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xRightTile, yTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(rightTileBlocked && collisionLayer.getCell(xRightTile, yTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xRightTile, yTile).setTile(textureLayer.getCell(3, 0).getTile());
            return false;
        }
        return !(leftTileBlocked || rightTileBlocked);
    }

    public boolean canMoveLeft(){

        if(modelPosition.x == 0){
            return false;
        }

        int yDownTile = (int) (this.get1Point().y /16);
        int yUpTile = (int) (this.get2Point().y /16);

        int xTile = (int) ((this.get1Point().x -1) /16 );

        boolean downTileBlocked = collisionLayer.getCell(xTile, yDownTile).getTile().getProperties().containsKey("blocked");

        boolean upTileBlocked = collisionLayer.getCell(xTile, yUpTile).getTile().getProperties().containsKey("blocked");

        if(upTileBlocked && collisionLayer.getCell(xTile, yUpTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xTile, yUpTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(upTileBlocked && collisionLayer.getCell(xTile, yUpTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xTile, yUpTile).setTile(textureLayer.getCell(4, 0).getTile());
            return false;
        }
        if(downTileBlocked && collisionLayer.getCell(xTile, yDownTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xTile, yDownTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(downTileBlocked && collisionLayer.getCell(xTile, yDownTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xTile, yDownTile).setTile(textureLayer.getCell(4, 0).getTile());
            return false;
        }

        return !(downTileBlocked || upTileBlocked);
    }

    public boolean canMoveRight(){
        if(modelPosition.x == 240-16){
            return false;
        }

        int yDownTile = (int) (this.get4Point().y  /16);
        int yUpTile = (int) (this.get3Point().y /16);

        int xTile = (int) ((this.get4Point().x +1) /16 );

        boolean downTileBlocked = collisionLayer.getCell(xTile, yDownTile).getTile().getProperties().containsKey("blocked");

        boolean upTileBlocked = collisionLayer.getCell(xTile, yUpTile).getTile().getProperties().containsKey("blocked");

        if(upTileBlocked && collisionLayer.getCell(xTile, yUpTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xTile, yUpTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(upTileBlocked && collisionLayer.getCell(xTile, yUpTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xTile, yUpTile).setTile(textureLayer.getCell(2, 0).getTile());
            return false;
        }

        if(downTileBlocked && collisionLayer.getCell(xTile, yDownTile).getTile().getProperties().containsKey("halfBrick")){
            explore();
            collisionLayer.getCell(xTile, yDownTile).setTile(textureLayer.getCell(0, 0).getTile());
            return false;
        }
        if(downTileBlocked && collisionLayer.getCell(xTile, yDownTile).getTile().getProperties().containsKey("brick")){
            explore();
            collisionLayer.getCell(xTile, yDownTile).setTile(textureLayer.getCell(2, 0).getTile());
            return false;
        }

        return !(downTileBlocked || upTileBlocked);
    }
}

