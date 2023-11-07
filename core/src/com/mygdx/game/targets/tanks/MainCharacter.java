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
    private int RESPAWN_TIME = 200;

    public MainCharacter(Float x, Float y, TiledMapTileLayer collisionLayer, List<Bullet> bullets, Boolean isEnemy ) {
        super(x, y, collisionLayer, bullets, isEnemy,  new Vector2(CONSTANTS.BASIC_TANK_TILE[0],CONSTANTS.BASIC_TANK_TILE[1]));
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
                    gameOver = true;
            }
        }, RESPAWN_TIME);
        this.region.changeTexture(new Vector2( CONSTANTS.EXPLORE_TILE[0],CONSTANTS.EXPLORE_TILE[1]), CONSTANTS.TILE_SIZE);
    }

    public void revive(){
        this.isExplored = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                immortal = false;
            }
        }, RESPAWN_TIME);
        this.region.changeTexture(CONSTANTS.BASIC_TANK_TILE[0], CONSTANTS.BASIC_TANK_TILE[1], CONSTANTS.TILE_SIZE);
        teleport(defaultPosition);

    }
}
