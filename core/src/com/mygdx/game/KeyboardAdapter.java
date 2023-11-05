package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class KeyboardAdapter extends InputAdapter {


    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;
    private boolean firePressed;
    private boolean enterPressed;
    private final Vector2 direction = new Vector2();


    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if(keycode == Input.Keys.A ){
            this.leftPressed = true;
        }
        if(keycode == Input.Keys.D){
            this.rightPressed = true;
        }
        if(keycode == Input.Keys.W){
            this.upPressed = true;
        }
        if(keycode == Input.Keys.S){
            this.downPressed = true;
        }

        if(keycode == Input.Keys.ENTER){
            this.enterPressed = true;
        }
        if(keycode == Input.Keys.SPACE){
            this.firePressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A){
            this.leftPressed = false;
        }
        if(keycode == Input.Keys.D){
            this.rightPressed = false;
        }
        if(keycode == Input.Keys.W){
            this.upPressed = false;
        }
        if(keycode == Input.Keys.S){
            this.downPressed = false;
        }
        if(keycode == Input.Keys.ENTER){
            this.enterPressed = false;
        }
        if(keycode == Input.Keys.SPACE){
            this.firePressed = false;
        }

        return false;
    }

    public Vector2 getDirection(){
        direction.set(0,0);
        if(this.leftPressed){
            this.direction.add(-1,0);
            return this.direction;
        }
        if(this.rightPressed){
            this.direction.add(1,0);
            return this.direction;
        }
        if(this.upPressed){
            this.direction.add(0,1);
            return this.direction;
        }
        if(this.downPressed){
            this.direction.add(0,-1);
            return this.direction;
        }

        return this.direction;
    }

    public boolean isFirePressed() {
        return firePressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

}
