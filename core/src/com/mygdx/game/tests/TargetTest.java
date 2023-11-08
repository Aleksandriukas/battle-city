package com.mygdx.game.tests;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.targets.Target;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TargetTest{

    private Target target;

    private TiledMap map;

    private static Application application;

    @Before
    public void init() {
        application = new HeadlessApplication(new ApplicationAdapter() {
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;

        TmxMapLoader loader = new TmxMapLoader();
        // FIXME fix assets location
        map = loader.load(Gdx.files.internal("stage1.tmx").file().getAbsolutePath());
        target = new Target(16F,16F, (TiledMapTileLayer) map.getLayers().get(0), 12, 12, new Vector2(0,0), 0); // BOTTOM LEFT CORNER
    }

    @Test
    public void moveUpTest () {

        boolean canMoveUp = target.canMoveUp();

        Assert.assertTrue(canMoveUp);

    }
    @Test
    public void moveRightTest () {

        boolean canMoveRight = target.canMoveRight();

        Assert.assertTrue(canMoveRight);
    }

    @Test
    public void moveDownTest () {

        boolean canMoveDown = target.canMoveDown();

        Assert.assertFalse(canMoveDown);
    }

    @Test
    public void moveLeftTest () {

        boolean canMoveLeft = target.canMoveLeft();

        Assert.assertFalse(canMoveLeft);
    }

    @Test
    public void firstTargetTest(){

        Target newTarget = new Target(20F,20F, (TiledMapTileLayer) map.getLayers().get(0), 12, 12, new Vector2(0,0), 0);

        boolean intersected = target.isIntersected(newTarget);

        Assert.assertTrue(intersected);
    }

    @Test
    public void secondTargetTest(){

        Target newTarget  = new Target(200F,200F, (TiledMapTileLayer) map.getLayers().get(0), 12, 12, new Vector2(0,0), 0);

        boolean intersected = target.isIntersected(newTarget);

        Assert.assertFalse(intersected);
    }

    @After
    public void cleanUp() {
        application.exit();
        application = null;
    }
}
