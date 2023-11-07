package com.mygdx.game.targets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.Direction;
import com.mygdx.game.targets.Target;

public class Bullet extends Target {

    private final Boolean isEnemy;
    private TiledMapTileLayer textureLayer;

    public Bullet(Float x, Float y, TiledMapTileLayer collisionLayer, Direction direction, Boolean isEnemy){
        super(x,y,collisionLayer, CONSTANTS.BULLET_TILE_SIZE, CONSTANTS.BULLET_MODEL_SIZE, new Vector2(CONSTANTS.BULLET_TILE[0], CONSTANTS.BULLET_TILE[1]), CONSTANTS.BULLET_TILE_SIZE);

        this.direction= direction;

        this.region.changeDirection(direction);

        this.isEnemy = isEnemy;

        float BULLET_SPEED = 2;
        this.setSpeed(BULLET_SPEED);
    }
    public void render(Batch batch){
        if(!this.isExplored) {
            if (this.direction == Direction.UP) {
                moveTo(new Vector2(0, 1));
            }
            if (this.direction == Direction.DOWN) {
                moveTo(new Vector2(0, -1));
            }
            if (this.direction == Direction.LEFT) {
                moveTo(new Vector2(-1, 0));
            }
            if (this.direction == Direction.RIGHT) {
                moveTo(new Vector2(1, 0));
            }
        }
        if(!canMove()){
            interact();
        }
        super.render(batch);
    }

    public void setTextureLayer(TiledMapTileLayer textureLayer){
        this.textureLayer = textureLayer;
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

