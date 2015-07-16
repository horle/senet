package senet.ui.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {

	private OrthographicCamera camera;

	public GameScreen (Senet gam){
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		//camera.setToOrtho(false, 480, 320);
	}
	

//	
//	atlas = new TextureAtlas(Gdx.files.internal("textures/senet.pack"));
//	
//	tokensA = new Array<Sprite>();
//	tokensB = new Array<Sprite>();
//	
//	for (int i = 0; i < Game.tokenCount; i++){
//		
//		Sprite sA = new Sprite(atlas.findRegion("tokenA"));
//		findFieldPosition(0, i, sA);
//		tokensA.add(sA);
//		
//		Sprite sB = new Sprite(atlas.findRegion("tokenB"));
//		findFieldPosition(1, i, sB);
//		tokensB.add(sB);
//	}
//	
//	board = new Sprite(atlas.findRegion("board"));
//	board.setScale(1f);
//	board.setOrigin(0, 0);
//	board.setPosition(0, 0);
//	
//	endBtn = new Sprite(atlas.findRegion("endBtn"));
//	endBtn.setScale(1f);
//	endBtn.setOrigin(20, 20);
//	endBtn.setPosition(20, 20);
	
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1, 0.8f, 0.4f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		

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
