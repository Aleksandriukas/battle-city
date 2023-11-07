package com.mygdx.game.targets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.CustomTextureRegion;
import com.mygdx.game.targets.Target;

public class Base  extends Target {
    public Base(Float x, Float y, TiledMapTileLayer collisionLayer, Integer tileSize, Integer modelSize) {
        super(x, y, collisionLayer, tileSize, modelSize, new Vector2(CONSTANTS.BASE_TILE[0], CONSTANTS.BASE_TILE[1]), 0);
    }
    @Override
    public void explore(){
        this.gameOver = true;
        this.isExplored = true;
        this.region.changeTexture( new Vector2(CONSTANTS.BASE_LOSE_TILE[0],CONSTANTS.BASE_LOSE_TILE[1]), CONSTANTS.TILE_SIZE);
    }
}
