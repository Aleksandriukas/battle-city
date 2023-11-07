package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Stat {

    private Stage stage;

    private Table table;
    private Label.LabelStyle font;

     public Stat(String text, Batch batch, Viewport viewport){
         stage = new Stage(viewport, batch);

         table = new Table();
         table.setPosition(280,200);
         font = new Label.LabelStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
     }
        public void render(Batch batch){
            stage.draw();
        }

        public void update(int left, int score, int health){

            stage.clear();
            table.clear();

            Label leftLabel = new Label("left: " + left,font);
            Label scoreLabel = new Label("score: " + score,font);
            Label healthLabel = new Label("health: " + health,font);
            table.add(leftLabel).align(4);
            table.row();
            table.add(scoreLabel).align(4);
            table.row();
            table.add(healthLabel);
            stage.addActor(table);
        }

}
