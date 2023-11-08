package com.mygdx.game.tests;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Direction;
import com.mygdx.game.targets.Bullet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BulletTest {

    private Bullet bullet;

    private TiledMap map;

    private static Application application;

    @Before
    public void init() {
        application = new HeadlessApplication(new ApplicationAdapter() {
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;

        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load(Gdx.files.internal("stage1.tmx").file().getAbsolutePath());
        bullet = new Bullet(16F,16F, (TiledMapTileLayer) map.getLayers().get(0), Direction.UP, false); // BOTTOM LEFT CORNER
        this.bullet.setTextureLayer((TiledMapTileLayer) map.getLayers().get(1));
    }

    @Test
    public void firstInteractBrickTest(){

        Vector2 cell = new Vector2(1,1);//EMPTY BLOCK

        Assert.assertFalse(bullet.interactBrick(cell));
    }
    @Test
    public void secondInteractBrickTest(){

        Vector2 cell = new Vector2(2,2);//BRICK BLOCK

        Assert.assertTrue(bullet.interactBrick(cell));
    }

    @Test
    public void thirdInteractBrickTest(){

        Vector2 cell = new Vector2(0,0);//FORM BLOCK

        Assert.assertFalse(bullet.interactBrick(cell));
    }

    @Test
    public void fourthInteractBrickTest(){
        // Aim is to destroy the brick block

        Vector2 cell = new Vector2(2,3);//BRICK BLOCK

        Assert.assertTrue( bullet.interactBrick(cell));
        Assert.assertTrue( bullet.interactBrick(cell));
        Assert.assertFalse( bullet.interactBrick(cell));
    }

    @After
    public void cleanUp() {
        application.exit();
        application = null;
    }

}
