package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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


    Tank(Integer x , Integer y){

        this.direction = Bullet.Direction.UP;

        this.bullets = new ArrayList<>();

        this.position = new Vector2(x,y);

        this.texture = new Texture(Gdx.files.internal("tiles.png"));

        this.region = new TextureRegion(this.texture, 0,0,16,16);
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

    this.position.add(direction);

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
