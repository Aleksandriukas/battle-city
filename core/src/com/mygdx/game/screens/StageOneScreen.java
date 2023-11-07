package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.*;
import com.mygdx.game.targets.Base;
import com.mygdx.game.targets.Bullet;
import com.mygdx.game.targets.tanks.Enemy;
import com.mygdx.game.targets.tanks.MainCharacter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class StageOneScreen extends ScreenBase {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MainCharacter tank;
    private List<Bullet> bullets;
    private Stat text;
    private Base base;
    private List<Enemy> enemies;
    private Integer MAX_ENEMIES = 20;
    private Integer score = 0;
    public StageOneScreen(Game parent){
        super(parent);
        this.parent = parent;
    }

    @Override
    public void show() {

        this.keyboardAdapter = new KeyboardAdapter();
        Gdx.input.setInputProcessor(this.keyboardAdapter);
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("stage1.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(CONSTANTS.SCREEN_WIDTH,CONSTANTS.SCREEN_HEIGHT, this.camera);
        this.bullets = new ArrayList<>();
        this.tank = new MainCharacter(16F, 16F,(TiledMapTileLayer) map.getLayers().get(0), bullets, false, 1);
        this.enemies = new ArrayList<>();
        this.base = new Base(112F,16F,(TiledMapTileLayer) map.getLayers().get(0),16,16);
        this.text = new Stat("text",this.renderer.getBatch(),viewport);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        this.renderer.setView(this.camera);
        this.renderer.render();
        this.text.update(this.MAX_ENEMIES + this.enemies.size(), this.score, this.tank.lives);
        this.text.render(this.renderer.getBatch());

        this.renderer.getBatch().begin();
            this.tank.moveTo(this.keyboardAdapter.getDirection());

            if (this.MAX_ENEMIES != 0) {
                generateEnemy();
            }
            if (this.keyboardAdapter.isFirePressed()) {
                this.tank.fire();
            }

            this.tank.render(this.renderer.getBatch());

            for (Bullet bullet : this.bullets) {
                bullet.setTextureLayer((TiledMapTileLayer) this.map.getLayers().get(1));
                bullet.render(this.renderer.getBatch());
            }

            for (int i = 0; i < this.bullets.size(); i++) {
                if (this.bullets.get(i).gameOver) {
                    this.bullets.remove(i);
                }
            }

            for (Enemy enemy : this.enemies) {
                enemy.render(this.renderer.getBatch());
            }

            for (int i = 0; i < this.enemies.size(); i++) {
                if (this.enemies.get(i).gameOver) {
                    this.score += this.enemies.get(i).getScore();
                    this.enemies.remove(i);
                }
            }
            this.base.render(this.renderer.getBatch());
            this.renderer.getBatch().end();
            interactCharacters();
            if(this.tank.gameOver){
                 this.parent.setScreen(new GameOverScreen(this.parent,this.score));
        }

    }

    public int generateRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    private Time lastEnemyGenerateTime = new Time(0);
    public void generateEnemy(){
        if(this.lastEnemyGenerateTime.getTime() + 7000 < System.currentTimeMillis()){

            MAX_ENEMIES--;
            int positionNumber = generateRandomNumber(1,4);
            int typeNumber = generateRandomNumber(1,5);

            Enemy.TankType type;
            if(typeNumber == 4){
                type = Enemy.TankType.FAST;
            }
            else{
                type = Enemy.TankType.BASIC;
            }

            Vector2 position = new Vector2(16F, 208F);

            if(positionNumber == 1){
                position.set(16F, 208F);
            }
            if(positionNumber == 2){
                position.set(156F, 208F);
            }
            if(positionNumber == 3){
                position.set(208F, 208F);
            }

            this.lastEnemyGenerateTime = new Time(System.currentTimeMillis());
            this.enemies.add(new Enemy(position.x,position.y,(TiledMapTileLayer) map.getLayers().get(0), bullets, type));
        }
    }

    public void interactCharacters(){
        for(Enemy enemy : this.enemies){

            if(enemy.isIntersected(this.base)){
                this.base.explore();
                this.tank.gameOver = true;
            }

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
    public void dispose() {
        this.map.dispose();
        this.renderer.dispose();
    }
}
