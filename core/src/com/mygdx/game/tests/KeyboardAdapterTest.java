package com.mygdx.game.tests;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.KeyboardAdapter;
import org.junit.Assert;
import org.junit.Test;

public class KeyboardAdapterTest {
    @Test
    public void testAPress() {
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter();
        keyboardAdapter.keyDown(Input.Keys.A);

        Vector2 direction = keyboardAdapter.getDirection();

        Assert.assertEquals(new Vector2(-1,0), direction);

}
@Test
    public void testDPress() {
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter();
        keyboardAdapter.keyDown(Input.Keys.D);

        Vector2 direction = keyboardAdapter.getDirection();

        Assert.assertEquals(new Vector2(1,0), direction);

    }
@Test
    public void testWPress() {
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter();
        keyboardAdapter.keyDown(Input.Keys.W);

        Vector2 direction = keyboardAdapter.getDirection();

        Assert.assertEquals(new Vector2(0,1), direction);

    }
@Test
    public void testSPress() {
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter();
        keyboardAdapter.keyDown(Input.Keys.S);

        Vector2 direction = keyboardAdapter.getDirection();

        Assert.assertEquals(new Vector2(0,-1), direction);
    }

@Test
    public void testEnterPress() {
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter();
        keyboardAdapter.keyDown(Input.Keys.ENTER);

        boolean enterPressed = keyboardAdapter.isEnterPressed();

        Assert.assertTrue(enterPressed);
    }
@Test
    public void testSpacePress() {
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter();
        keyboardAdapter.keyDown(Input.Keys.SPACE);

        boolean firePressed = keyboardAdapter.isFirePressed();

        Assert.assertTrue(firePressed);
    }
}
