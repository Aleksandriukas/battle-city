package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainScreen extends ScreenBase {
    private Stage stage;
    private SpriteBatch batch;

    public MainScreen(Game parent){
        super(parent);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label.LabelStyle font = new Label.LabelStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Label gameOverLabel = new Label("BATTLE CITY",font);
        Label playAgainLabel = new Label("Press enter to play",font);
        table.add(gameOverLabel).expandX();
        table.row();
        table.row();
        table.add(playAgainLabel);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        if(this.keyboardAdapter.isEnterPressed()){
            play();
        }
        stage.draw();
    }

    public void play(){
        this.parent.setScreen(new StageOneScreen(this.parent));
    }
}
