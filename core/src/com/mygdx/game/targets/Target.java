package com.mygdx.game.targets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Target {
    protected Vector2 modelPosition;
    protected Vector2 tilePosition;
    protected Texture texture;
    protected TextureRegion region;
    protected Integer tileSize;
    protected Integer modelSize;
    public Boolean gameOver = false;
    protected Boolean isExplored = false;
    protected Time explosionStart = new Time(0);
    protected enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    protected Direction direction;
    protected Float speed = 1F;
    public final TiledMapTileLayer collisionLayer;

    protected Target(Float x, Float y, TiledMapTileLayer collisionLayer, Integer tileSize, Integer modelSize){

        this.direction = Bullet.Direction.UP;
        this.modelSize = modelSize;
        this.tileSize = tileSize;
        teleport(new Vector2(x,y));
        this.texture = new Texture(Gdx.files.internal("tiles.png"));
        this.region = new TextureRegion(this.texture, 0,0,tileSize,tileSize);
        this.collisionLayer = collisionLayer;
    }

    public void render(Batch batch){
        if(gameOver){
            this.dispose();
            return;
        }

        batch.draw(this.region, this.tilePosition.x, this.tilePosition.y);
    }

    public void dispose(){
        this.texture.dispose();
    }

    public void setSpeed(Float speed){
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

        return !(this.getUpTilesProperties()[0].containsKey("blocked") || this.getUpTilesProperties()[1].containsKey("blocked"));
    }

    public boolean canMoveDown(){
        if(modelPosition.y == 0){
            return false;
        }

        return !(this.getDownTilesProperties()[0].containsKey("blocked") || this.getDownTilesProperties()[1].containsKey("blocked"));
    }

    public boolean canMoveLeft(){
        if(modelPosition.x == 0){
            return false;
        }

        return !(this.getLeftTilesProperties()[0].containsKey("blocked") || this.getLeftTilesProperties()[1].containsKey("blocked"));
    }

    public boolean canMoveRight(){
        if(modelPosition.x == 240-16){
            return false;
        }

        return !(this.getRightTilesProperties()[0].containsKey("blocked") || this.getRightTilesProperties()[1].containsKey("blocked") );
    }

    public int convertCoordinateToTile(float coor){
        return (int) (coor / CONSTANTS.TILE_SIZE);
    }

    public MapProperties[] getRightTilesProperties(){
        int yDownTile = convertCoordinateToTile(this.get4Point().y);
        int yUpTile = convertCoordinateToTile(this.get3Point().y);
        int xTile = convertCoordinateToTile(this.get4Point().x+1);

        return new MapProperties[]{collisionLayer.getCell(xTile, yDownTile).getTile().getProperties(), collisionLayer.getCell(xTile, yUpTile).getTile().getProperties()};
    }

    public MapProperties[] getLeftTilesProperties(){
        int yDownTile = convertCoordinateToTile(this.get1Point().y);
        int yUpTile = convertCoordinateToTile(this.get2Point().y);
        int xTile = convertCoordinateToTile(this.get1Point().x-1);

        return new MapProperties[]{collisionLayer.getCell(xTile, yDownTile).getTile().getProperties(),collisionLayer.getCell(xTile, yUpTile).getTile().getProperties()};
    }

    public MapProperties[] getDownTilesProperties(){
        int xLeftTile = convertCoordinateToTile(this.get1Point().x);
        int xRightTile = convertCoordinateToTile(this.get4Point().x);
        int yTile = convertCoordinateToTile(this.get1Point().y-1);

        return new MapProperties[]{collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties(),collisionLayer.getCell(xRightTile, yTile).getTile().getProperties()};
    }

    public MapProperties[] getUpTilesProperties(){
        int xLeftTile = convertCoordinateToTile(this.get2Point().x);
        int xRightTile = convertCoordinateToTile(this.get3Point().x);
        int yTile = convertCoordinateToTile(this.get2Point().y+1);

        return new MapProperties[]{collisionLayer.getCell(xLeftTile, yTile).getTile().getProperties(),collisionLayer.getCell(xRightTile, yTile).getTile().getProperties()};
    }

    public boolean canMove (){
        if(this.isExplored){
            return false;
        }
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

    public void teleport(Vector2 position){

        this.tilePosition = new Vector2(position);

        int modelOffset = (tileSize - modelSize)/2;

        this.modelPosition = new Vector2(position.x + modelOffset, position.y + modelOffset);
    }

    public void explore(){

        this.isExplored = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameOver = true;
            }
        }, 200);
        this.region.setRegion(256,128, 16,16);
    }

    public boolean isIntersected(Target target){
        return this.modelPosition.x < target.modelPosition.x + target.modelSize &&
                this.modelPosition.x + this.modelSize > target.modelPosition.x &&
                this.modelPosition.y < target.modelPosition.y + target.modelSize &&
                this.modelPosition.y + this.modelSize > target.modelPosition.y;
    }
}