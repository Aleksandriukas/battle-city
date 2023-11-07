package com.mygdx.game.targets.tanks;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.targets.Bullet;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainCharacter extends Tank {

    public Integer lives = 1;

    private Vector2 defaultPosition;

    private Boolean immortal = false;

    public MainCharacter(Float x, Float y, TiledMapTileLayer collisionLayer, List<Bullet> bullets, Boolean isEnemy, Integer health) {
        super(x, y, collisionLayer, bullets, isEnemy, health, new Vector2(0,0));
        defaultPosition = new Vector2(x,y);
    }

    @Override
    public void explore(){
        if(immortal){
            return;
        }
        if(lives >0){
            immortal = true;
        }
        lives--;
        this.isExplored = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(immortal){
                    revive();
                    return;
                }
                    System.out.println("Game over");
                    gameOver = true;
            }
        }, 200);
        this.region.setRegion(256,128, CONSTANTS.TANK_TILE_SIZE,CONSTANTS.TANK_TILE_SIZE);
    }

    public void revive(){
        this.isExplored = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                immortal = false;
            }
        }, 200);
        teleport(defaultPosition);

    }
}
