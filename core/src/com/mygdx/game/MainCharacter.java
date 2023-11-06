package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainCharacter extends Tank{

    private Integer lifes = 2;

    private Vector2 defaultPosition;

    private Boolean immortal = false;

    MainCharacter(Float x, Float y, TiledMapTileLayer collisionLayer, List<Bullet> bullets, Boolean isEnemy, Integer health) {
        super(x, y, collisionLayer, bullets, isEnemy, health, new Vector2(0,0));
        defaultPosition = new Vector2(x,y);
    }

    @Override
    public void explore(){

        if(lifes>0){
            immortal = true;
        }

        this.isExplored = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setLifes(getLifes() - 1);
                if(immortal){
                    revive();
                    return;
                }
                    System.out.println("Game over");
                    gameOver = true;
            }
        }, 200);
        this.region.setRegion(256,128, 16,16);
    }

    public void revive(){

        this.isExplored = false;
        if(this.direction == Bullet.Direction.UP){this.region = new TextureRegion(this.texture, (int)(this.texturePosition.x+ 0), (int)(this.texturePosition.y + 0),CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                immortal = false;
            }
        }, 200);
        teleport(defaultPosition);

    }

    public void setIsExplored(Boolean isExplored){
        this.isExplored = isExplored;
    }

    public Integer getLifes(){
        return this.lifes;
    }


    public void setLifes(Integer lifes){
        this.lifes = lifes;
    }

}
