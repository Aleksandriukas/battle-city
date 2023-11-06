package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Target {
    protected Vector2 modelPosition;

    protected Vector2 tilePosition;

    protected Texture texture;
    protected TextureRegion region;
    protected Integer tileSize;
    protected Integer modelSize;

    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    protected Direction direction;
    protected Integer speed = 1;
    public final TiledMapTileLayer collisionLayer;
    Target(Float x , Float y, TiledMapTileLayer collisionLayer, Integer tileSize,Integer modelSize, Texture texture){

        this.direction = Bullet.Direction.UP;

        this.modelSize = modelSize;

        this.tileSize = tileSize;

        this.tilePosition = new Vector2(x,y);

        Integer modelOffset = (tileSize - modelSize)/2;

        this.modelPosition = new Vector2(x+ modelOffset ,y + modelOffset);

        this.texture = texture;

        this.region = new TextureRegion(this.texture, 0,0,tileSize,tileSize);

        this.collisionLayer = collisionLayer;
    }

    public void render(Batch batch){
        batch.draw(this.region, this.tilePosition.x, this.tilePosition.y);
    }

    public void dispose(){
        this.texture.dispose();
    }

    public void setSpeed(Integer speed){
        this.speed = speed;
    }

    public void moveTo (Vector2 direction){

        if(direction.x == 1){
            this.direction = Bullet.Direction.RIGHT;
        }
        if(direction.x == -1){
            this.direction = Bullet.Direction.LEFT;
        }
        if(direction.y == 1){
            this.direction = Bullet.Direction.UP;
        }
        if(direction.y == -1){
            this.direction = Bullet.Direction.DOWN;
        }

        if(canMove()) {
            this.modelPosition.add(new Vector2(direction.x* speed, direction.y * speed));
            this.tilePosition.add(new Vector2(direction.x* speed, direction.y * speed));
        }
    }

    public Vector2 get1Point(){
        return new Vector2(this.modelPosition.x , this.modelPosition.y );
    }

    public Vector2 get2Point(){
        return new Vector2(this.modelPosition.x , this.modelPosition.y + this.modelSize );
    }

    public Vector2 get3Point(){
        return new Vector2(this.modelPosition.x +this.modelSize, this.modelPosition.y + this.modelSize );
    }

    public Vector2 get4Point(){
        return new Vector2(this.modelPosition.x + this.modelSize, this.modelPosition.y );
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

        return !(downTileBlocked || upTileBlocked);
    }

    public boolean canMove (){
        if(this.direction == Bullet.Direction.UP){
            return this.canMoveUp();
        }

        if(this.direction == Bullet.Direction.DOWN){
            return this.canMoveDown();
        }

        if(this.direction == Bullet.Direction.LEFT){
            return this.canMoveLeft();
        }

        if(this.direction == Bullet.Direction.RIGHT){
            return this.canMoveRight();
        }
        return true;
    }
}
