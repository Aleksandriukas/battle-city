package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.KeyboardAdapter;

public class ScreenBase implements Screen {
    public Viewport viewport;
    public Game parent;
    public KeyboardAdapter keyboardAdapter;
    public OrthographicCamera camera;
    ScreenBase(Game parent){
        this.parent =parent;
        this.camera = new OrthographicCamera();
        viewport = new FitViewport(CONSTANTS.SCREEN_WIDTH,CONSTANTS.SCREEN_HEIGHT, camera);
    }
    @Override
    public void show() {
        this.keyboardAdapter = new KeyboardAdapter();
        Gdx.input.setInputProcessor(this.keyboardAdapter);
    }
    @Override
    public void render(float delta) {

    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    @Override
    public void hide(){
        this.dispose();
    }

    @Override
    public void dispose() {
    }
}
