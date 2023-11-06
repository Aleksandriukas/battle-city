package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class StageOne implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera camera;

    private Viewport viewport;

    private Tank tank;

    private List<Bullet> bullets;

    private KeyboardAdapter keyboardAdapter;
    @Override
    public void show() {

        this.keyboardAdapter = new KeyboardAdapter();
        Gdx.input.setInputProcessor(this.keyboardAdapter);
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("stage1.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(320,240, this.camera);
        this.bullets = new ArrayList<>();
        this.tank = new Tank(16F, 16F,(TiledMapTileLayer) map.getLayers().get(0), bullets, false, 1);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        this.renderer.setView(this.camera);
        this.renderer.render();

        this.renderer.getBatch().begin();



        this.tank.moveTo(this.keyboardAdapter.getDirection());


        if(this.keyboardAdapter.isFirePressed()){
            this.tank.fire();
        }

        this.tank.render(this.renderer.getBatch());

        for(Bullet bullet : this.bullets){
            bullet.render(this.renderer.getBatch());
        }

        this.renderer.getBatch().end();
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
    public void dispose() {
        this.map.dispose();
        this.renderer.dispose();
    }

    @Override
    public void hide(){
        this.dispose();
    }
}
