package com.mygdx.game.targets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.targets.Target;

public class Base  extends Target {
    public Base(Float x, Float y, TiledMapTileLayer collisionLayer, Integer tileSize, Integer modelSize) {
        super(x, y, collisionLayer, tileSize, modelSize);
        this.region =  new TextureRegion(this.texture, 304, 32, CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
    }
    @Override
    public void explore(){
        this.gameOver = true;
        this.isExplored = true;
        this.region =  new TextureRegion(this.texture, 320, 32,CONSTANTS.TILE_SIZE,CONSTANTS.TILE_SIZE);
    }
}
