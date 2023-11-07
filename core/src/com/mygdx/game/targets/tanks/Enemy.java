package com.mygdx.game.targets.tanks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.Utils;
import com.mygdx.game.targets.Bullet;
import com.mygdx.game.targets.tanks.Tank;

import java.sql.Time;
import java.util.List;

public class Enemy extends Tank {

    public enum TankType {
        BASIC, FAST
    }

    private TankType type;

    private int score;

    private Time lastActionTime = new Time(0);
    private final float[] SPEEDS= new float[]{0.5F, 1F};

    private final int[] SCORES = new int[]{100, 200};

    private final int[] TIMEOUTS = new int[]{1500, 1000};

    private Vector2 generatedWay = new Vector2();


    public Enemy(Float x, Float y, TiledMapTileLayer collisionLayer, List<Bullet> bullets, TankType enemyType) {
        super(x,y,collisionLayer,bullets,true, new Vector2(CONSTANTS.BASIC_ENEMY_TILE[0],CONSTANTS.BASIC_ENEMY_TILE[1]));
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
           setSpeed(SPEEDS[0]);
           setTimeout(TIMEOUTS[0]);
           this.score = SCORES[0];
           this.region.changeTexture(new Vector2(CONSTANTS.BASIC_ENEMY_TILE[0], CONSTANTS.BASIC_ENEMY_TILE[1]), CONSTANTS.TANK_TILE_SIZE);
       }
       if(enemyType == TankType.FAST){
           setSpeed(SPEEDS[1]);
           setTimeout(TIMEOUTS[1]);
           this.score = SCORES[1];
           this.region.changeTexture(new Vector2(CONSTANTS.FAST_ENEMY_TILE[0], CONSTANTS.FAST_ENEMY_TILE[1]), CONSTANTS.TANK_TILE_SIZE);
       }
   }

    public void emulateMove(){
        this.generateWay();
        this.moveTo(this.generatedWay);
    }

   public void generateWay(){
       int ACTION_TIMEOUT = 2000;

       if(this.lastActionTime.getTime() + ACTION_TIMEOUT < System.currentTimeMillis()){
            this.lastActionTime = new Time(System.currentTimeMillis());

            int number = Utils.random(1,5);
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
