package senet.ui.graphical;

import senet.logic.base.Game;
import senet.logic.base.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Senet extends com.badlogic.gdx.Game{
	
	SpriteBatch batch;
	BitmapFont font;
	
	private Game game;
	
	@Override
	public void create() {
		
		int tokenCount = 3;
		
		game.initPlayers(
				new Player(0, "Felix", tokenCount, Color.BLUE),
				new Player(1, "Cedric", tokenCount, Color.RED));
		
		game.initBoard();
		game.initLanguage("German");
		game.initTokens();
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		game = Game.setGameMode(0);
		
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {		

		super.render();
	}
}
