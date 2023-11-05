package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

import javax.swing.plaf.synth.Region;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Tank {

    private  Vector2 position;

    private Texture texture;

    private TextureRegion region;

    private List<Bullet> bullets;

    private Bullet.Direction direction;

    private final Time lastFireTime = new Time(0);

    private final TiledMapTileLayer collisionLayer;

    Tank(Integer x , Integer y, TiledMapTileLayer collisionLayer){

        this.direction = Bullet.Direction.UP;

        this.bullets = new ArrayList<>();

        this.position = new Vector2(x,y);

        this.texture = new Texture(Gdx.files.internal("tiles.png"));

        this.region = new TextureRegion(this.texture, 0,0,16,16);

        this.collisionLayer = collisionLayer;
    }

    public void render(Batch batch){

        if(this.direction == Bullet.Direction.UP){
            this.region = new TextureRegion(this.texture, 0,0,16,16);
        }
        if(this.direction == Bullet.Direction.DOWN){
            this.region = new TextureRegion(this.texture, 64,0,16,16);
        }
        if(this.direction == Bullet.Direction.LEFT){
            this.region = new TextureRegion(this.texture, 32,0,16,16);
        }
        if(this.direction == Bullet.Direction.RIGHT){
            this.region = new TextureRegion(this.texture, 96,0,16,16);
        }

        batch.draw(this.region, this.position.x, this.position.y);
        for(Bullet bullet : this.bullets){
            bullet.render(batch);
        }
    }

    public void dispose(){
        this.texture.dispose();
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
        this.position.add(direction);
    }
    }

    public Vector2 getCollisionPosition(){
        return new Vector2(this.position.x + 8, this.position.y +8);
    }

    public boolean canMove (){

        System.out.println((int)( this.position.x /16 )+ " " + (int) (this.position.y/16));

        int xCollision;
        int yCollision;

        if(this.direction == Bullet.Direction.UP){
            if(position.y == 208-16){
                return false;
            }
            xCollision = (int) (this.getCollisionPosition().x /16);
            yCollision = Math.min ((int) (this.getCollisionPosition().y /16 +1),12);

            return !collisionLayer.getCell(xCollision, yCollision).getTile().getProperties().containsKey("blocked");
        }

        if(this.direction == Bullet.Direction.DOWN){
            if(position.y == 0){
                return false;
            }

            xCollision = (int) (this.getCollisionPosition().x /16);
            yCollision = Math.max((int) (this.getCollisionPosition().y /16 -1),0);

            return !collisionLayer.getCell(xCollision, yCollision ).getTile().getProperties().containsKey("blocked");
        }

        if(this.direction == Bullet.Direction.LEFT){
            if(position.x == 0){
                return false;
            }


            StaticTiledMapTile newTile =  new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("tiles.png")), 0,0,16,16));

            newTile.getProperties().put("blocked", true);

            collisionLayer.getCell(0,0).setTile(newTile);

            xCollision = Math.max((int) (this.getCollisionPosition().x /16 -1),0);
            yCollision = (int) (this.getCollisionPosition().y /16);

            return !collisionLayer.getCell(xCollision, yCollision ).getTile().getProperties().containsKey("blocked");
        }

        if(this.direction == Bullet.Direction.RIGHT){
            if(position.x == 208-16){
                return false;
            }

            xCollision = Math.min((int) (this.getCollisionPosition().x /16 +1),12);
            yCollision = (int) (this.getCollisionPosition().y /16);

            return !collisionLayer.getCell(xCollision, yCollision ).getTile().getProperties().containsKey("blocked");

        }
        return true;
    }

    public void fire(){

        if(this.lastFireTime.getTime() + 1000 > System.currentTimeMillis()){
            return;
        }

        this.lastFireTime.setTime(System.currentTimeMillis());

        Bullet bullet = new Bullet(this.position.x, this.position.y, direction);
        this.bullets.add(bullet);
    }

}
