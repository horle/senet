package senet.ui.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen{

	private final Senet game;
	private OrthographicCamera camera;
	
	public MainMenuScreen (final Senet g){
		
		game = g;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0.8f, 0.4f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.font.setScale(2.5f);
		game.font.draw(game.batch, "Welcome to Senet", 100, 150);
		game.font.draw(game.batch, "Tap here to begin", 100, 100);
		game.batch.end();
		
		if (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			
//			int x = Gdx.input.getX();
//			int y = Gdx.input.getY();
//			
			//if (y > 80 && y < 110 && x > 90){
				
				game.setScreen(new GameScreen(game));
				dispose();
			//}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
