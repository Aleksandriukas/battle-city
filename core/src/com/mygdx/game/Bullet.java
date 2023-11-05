package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    private final Vector2 position;

    private final Texture texture;

    private final TextureRegion region;

    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private final Direction direction;

    Bullet(Float x , Float y, Direction direction){
        this.position = new Vector2(x + (float) 16 /2 -2,y);

        this.texture = new Texture(Gdx.files.internal("tiles.png"));

        this.region = new TextureRegion(this.texture, 322,102,4,4);

        this.direction= direction;
    }
    public void render(Batch batch){
        if(position.x >= 256 || position.x <= 0 || position.y >= 256 || position.y <= 0){
            dispose();
            return;
        }

        if(this.direction == Direction.UP){
            this.position.add(0,2);
        }
        if(this.direction == Direction.DOWN){
            this.position.add(0,-2);
        }
        if(this.direction == Direction.LEFT){
            this.position.add(-2,0);
        }
        if(this.direction == Direction.RIGHT){
            this.position.add(2,0);
        }

        batch.draw(this.region, this.position.x, this.position.y);
    }

    public void dispose(){
        this.texture.dispose();
    }
}
