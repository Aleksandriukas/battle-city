package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class StageOne implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera camera;

    private Viewport viewport;

    private Tank tank;

    private List<Bullet> bullets;

    private List<Enemy> enemies;

    private Integer MAX_ENEMIES = 20;

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
        this.tank = new MainCharacter(16F, 16F,(TiledMapTileLayer) map.getLayers().get(0), bullets, false, 1);
        this.enemies = new ArrayList<>();
        this.enemies.add(new Enemy(16F, 208F,(TiledMapTileLayer) map.getLayers().get(0), bullets));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        this.renderer.setView(this.camera);
        this.renderer.render();

        this.renderer.getBatch().begin();

        this.tank.moveTo(this.keyboardAdapter.getDirection());

        if(this.MAX_ENEMIES !=0) {
            generateEnemy();
        }
        if(this.keyboardAdapter.isFirePressed()){
            this.tank.fire();
        }

        this.tank.render(this.renderer.getBatch());

        for(Bullet bullet : this.bullets){
            bullet.setTextureLayer((TiledMapTileLayer) this.map.getLayers().get(1));
            bullet.render(this.renderer.getBatch());
        }

        for(int i =0 ; i< this.bullets.size();i++){
            if(this.bullets.get(i).isGameOver()){
                this.bullets.remove(i);
            }
        }

        for(Enemy enemy : this.enemies){
            enemy.render(this.renderer.getBatch());
        }

        for(int i =0 ; i< this.enemies.size();i++){
            if(this.enemies.get(i).isGameOver()){
                this.enemies.remove(i);
            }
        }

        interactCharacters();
        this.renderer.getBatch().end();
    }

    public int generateRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    private Time lastEnemyGenerateTime = new Time(0);
    public void generateEnemy(){
        if(this.lastEnemyGenerateTime.getTime() + 10000 < System.currentTimeMillis()){

            MAX_ENEMIES--;
            int number = generateRandomNumber(1,4);
            Vector2 position = new Vector2(16F, 208F);

            if(number == 1){
                position.set(16F, 208F);
            }
            if(number == 2){
                position.set(156F, 208F);
            }
            if(number == 3){
                position.set(208F, 208F);
            }

            this.lastEnemyGenerateTime = new Time(System.currentTimeMillis());
            this.enemies.add(new Enemy(position.x,position.y,(TiledMapTileLayer) map.getLayers().get(0), bullets));
        }
    }

    public void interactCharacters(){
        for(Enemy enemy : this.enemies){
            for(Bullet bullet : this.bullets){
                if(enemy.isIntersected(bullet) && !bullet.isEnemy()){
                    enemy.explore();
                    bullet.explore();
                }
            }
        }

        for(Bullet bullet : this.bullets){
            if(bullet.isIntersected(this.tank) && bullet.isEnemy()){
                this.tank.explore();
                bullet.explore();
            }
        }

        for(int i = 0; i < this.bullets.size();i++){
            for(int j = i+1 ; j< this.bullets.size();j++){
                if(this.bullets.get(i).isIntersected(this.bullets.get(j))){
                    this.bullets.get(i).explore();
                    this.bullets.get(j).explore();
                }
            }
        }



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
