package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.sql.Time;
import java.util.List;

public class Enemy extends Tank {

    enum TankType {
        BASIC, FAST, ARMOR
    }

    private TankType type;

    private Time lastActionTime = new Time(0);

    private Vector2 generatedWay = new Vector2();

    Enemy(Float x , Float y, TiledMapTileLayer collisionLayer ,List<Bullet> bullets) {
        super(x,y,collisionLayer,bullets,true,1 ,new Vector2(128,0));
        this.setSpeed(0.5F);
        this.setTimeout(1500);
    }

   @Override
   public void render(Batch batch){

        if(!this.isExplored) {
            this.emulateMove();
            this.fire();
        }
        super.render(batch);
   }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void emulateMove(){
        this.generateWay();
        this.moveTo(this.generatedWay);
    }

   public void generateWay(){
        if(this.lastActionTime.getTime() + 2000 < System.currentTimeMillis()){
            this.lastActionTime = new Time(System.currentTimeMillis());

            int number = this.getRandomNumber(1,5);
            System.out.println(number);
            if(number == 1){
                this.generatedWay = new Vector2(0,1);
            }
            if(number == 2){
                this.generatedWay = new Vector2(0,-1);
            }
            if(number == 3){
                this.generatedWay = new Vector2(1,0);
            }
            if(number == 4){
                this.generatedWay = new Vector2(-1,0);
            }
        }
    }
}
