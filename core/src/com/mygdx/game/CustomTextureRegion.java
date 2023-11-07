package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CustomTextureRegion  {

    private int nextTileCoordinate;

    private Vector2 defaultPosition;

    private int tileSize;

    private Texture texture;

    private TextureRegion region;

    public CustomTextureRegion(Texture texture,  Vector2 position, int tileSize, int nextTileCoordinate){
        this.tileSize = tileSize;
        this.nextTileCoordinate = nextTileCoordinate;
        this.defaultPosition = position;
        this.texture = texture;
        this.region = new TextureRegion(texture, (int)position.x, (int)position.y, tileSize, tileSize);
    }

    public void changeDirection (Direction direction){
        if(direction == Direction.UP){
           this.region = new TextureRegion(texture,(int)this.defaultPosition.x ,(int) this.defaultPosition.y, tileSize ,tileSize);
        }
        if(direction == Direction.DOWN){
            this.region = new TextureRegion(this.texture,(int)this.defaultPosition.x + nextTileCoordinate*2,(int) this.defaultPosition.y, tileSize ,tileSize);
        }
        if(direction == Direction.LEFT){
            this.region= new TextureRegion(this.texture,(int)this.defaultPosition.x + nextTileCoordinate, (int)this.defaultPosition.y, tileSize, tileSize);
        }
        if(direction == Direction.RIGHT){
            this.region = new TextureRegion(this.texture,(int)this.defaultPosition.x+nextTileCoordinate*3, (int) this.defaultPosition.y, tileSize, tileSize);
        }
    }

    public void changeTexture(Vector2 position, int tileSize){
        this.defaultPosition = position;

        this.region = new TextureRegion(this.texture,(int)position.x, (int)position.y, tileSize, tileSize);
    }

    public void changeTexture(int x, int y, int tileSize){
        this.defaultPosition = new Vector2(x,y);

        this.region = new TextureRegion(this.texture,x, y, tileSize, tileSize);
    }

    public TextureRegion getRegion() {
        return region;
    }
}
