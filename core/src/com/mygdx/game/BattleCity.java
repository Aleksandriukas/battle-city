package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.MainScreen;

public class BattleCity extends Game {

	public void create () {
		this.setScreen(new MainScreen(this));
	}

}
