package com.mygdx.game.targets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.targets.Target;

public class Bullet extends Target {

    private final Boolean isEnemy;
    private final Direction direction;
    private TiledMapTileLayer textureLayer;

    public Bullet(Float x, Float y, TiledMapTileLayer collisionLayer, Direction direction, Boolean isEnemy){
        super(x,y,collisionLayer, CONSTANTS.BULLET_TILE_SIZE, CONSTANTS.BULLET_MODEL_SIZE);

        this.direction= direction;

        this.isEnemy = isEnemy;

        this.setSpeed(2F);

        this.setRegion();
    }
    public void render(Batch batch){
        if(modelPosition.x >= CONSTANTS.MAP_SIZE || modelPosition.x <= 0 || modelPosition.y >= 256 || modelPosition.y >= CONSTANTS.MAP_SIZE){
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
            interact();
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

    public void interact(){
        if(isExplored){
            return;
        }
        explore();

        if(direction == Direction.UP){
            Vector2 cellLeft  = new Vector2(convertCoordinateToTile(get2Point().x), convertCoordinateToTile(get2Point().y +1));
            Vector2 cellRight = new Vector2(convertCoordinateToTile(get3Point().x), convertCoordinateToTile( get3Point().y +1));
            if(this.interactBrick(cellLeft)){ return; }
            if(this.interactBrick(cellRight)){ return; }
        }
        if(direction == Direction.DOWN){
            Vector2 cellLeft  = new Vector2(convertCoordinateToTile( get1Point().x), convertCoordinateToTile(get1Point().y -1));
            Vector2 cellRight = new Vector2(convertCoordinateToTile( get4Point().x), convertCoordinateToTile(get4Point().y -1));
            if(this.interactBrick(cellLeft)){ return; }
            if(this.interactBrick(cellRight)){ return; }
        }
        if(direction == Direction.LEFT){
            Vector2 cellLeft  = new Vector2(convertCoordinateToTile( get1Point().x -1), convertCoordinateToTile( get1Point().y));
            Vector2 cellRight = new Vector2( convertCoordinateToTile( get2Point().x -1), convertCoordinateToTile( get2Point().y));
            if(this.interactBrick(cellLeft)){ return; }
            if(this.interactBrick(cellRight)){ return; }
        }
        if(direction == Direction.RIGHT){
            Vector2 cellLeft  = new Vector2(convertCoordinateToTile( get3Point().x +1), convertCoordinateToTile( get3Point().y));
            Vector2 cellRight = new Vector2(convertCoordinateToTile( get4Point().x +1), convertCoordinateToTile( get4Point().y));
            if(this.interactBrick(cellLeft)){ return; }
            if(this.interactBrick(cellRight)){ return; }
        }
    }

    public boolean interactBrick(Vector2 cell){

        if(collisionLayer.getCell((int) cell.x,(int) cell.y).getTile().getProperties().containsKey("halfBrick")){
            collisionLayer.getCell((int) cell.x,(int) cell.y).setTile(textureLayer.getCell(CONSTANTS.EMPTY_BLOCK[0], CONSTANTS.EMPTY_BLOCK[1]).getTile());
            return true;
        }
        if(collisionLayer.getCell((int) cell.x,(int) cell.y).getTile().getProperties().containsKey("brick")){
            if(this.direction == Direction.UP){
                collisionLayer.getCell((int) cell.x,(int) cell.y).setTile(textureLayer.getCell(CONSTANTS.UP_HALF_BRICK[0],CONSTANTS.UP_HALF_BRICK[1]).getTile());
                return true;
            }
            if(this.direction == Direction.DOWN){
                collisionLayer.getCell((int) cell.x,(int) cell.y).setTile(textureLayer.getCell(CONSTANTS.DOWN_HALF_BRICK[0], CONSTANTS.DOWN_HALF_BRICK[1]).getTile());
                return true;
            }
            if(this.direction == Direction.LEFT){
                collisionLayer.getCell((int) cell.x,(int) cell.y).setTile(textureLayer.getCell(CONSTANTS.LEFT_HALF_BRICK[0], CONSTANTS.LEFT_HALF_BRICK[1]).getTile());
                return true;
            }
            if(this.direction == Direction.RIGHT){
                collisionLayer.getCell((int) cell.x,(int) cell.y).setTile(textureLayer.getCell(CONSTANTS.RIGHT_HALF_BRICK[0], CONSTANTS.RIGHT_HALF_BRICK[1]).getTile());
                return true;
            }
        }
        return false;
    }
}

