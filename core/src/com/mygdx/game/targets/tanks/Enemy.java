package com.mygdx.game.targets.tanks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.targets.Bullet;
import com.mygdx.game.targets.tanks.Tank;

import java.sql.Time;
import java.util.List;

public class Enemy extends Tank {

    public enum TankType {
        BASIC, FAST, ARMOR
    }

    private TankType type;

    private int score;

    private Time lastActionTime = new Time(0);

    private Vector2 generatedWay = new Vector2();

    public Enemy(Float x, Float y, TiledMapTileLayer collisionLayer, List<Bullet> bullets, TankType enemyType) {
        super(x,y,collisionLayer,bullets,true,1 ,new Vector2(128,0));
        configEnemy(enemyType);
    }

   @Override
   public void render(Batch batch){

        if(!this.isExplored) {
            this.emulateMove();
            this.fire();
        }
        super.render(batch);
   }

   public void configEnemy(TankType enemyType){
       if(enemyType == TankType.BASIC){
           setSpeed(0.5F);
           setTimeout(1500);
           this.score = 100;
           this.texturePosition.set(128,0);
       }
       if(enemyType == TankType.FAST){
           setSpeed(1F);
           setTimeout(1000);
           this.score = 200;
           this.texturePosition.set(128,80);
       }
       if(enemyType == TankType.ARMOR){
           setSpeed(0.25F);
           setTimeout(2000);
           this.score = 300;
           this.texturePosition.set(128,32);
       }
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

    public int getScore(){
        return this.score;
    }

}