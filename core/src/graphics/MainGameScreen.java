package graphics;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import element.Blob;
import gameValues.GameConfig;

public class MainGameScreen implements Screen {
	private AgarAI game;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Blob> blobs;
	private Blob blob;
	private OrthographicCamera camera;
	private float lerp = 2.5f; 
	//per rendere fluido il movimento della camera e del blob. Pi� � alto il valore, pi� si muove velocemente
	private int numBlobs = 120;
	
	public MainGameScreen(AgarAI game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT); 
		//i valori mi dicono lo "zoom della camera".
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.WHITE);
		blob = new Blob(GameConfig.SCREEN_WIDTH/2,GameConfig.SCREEN_HEIGHT/2, 40);
		initBlobs();
	}
	
	public void initBlobs() {
		blobs = new ArrayList<Blob>(numBlobs);
		for (int i = 0; i < numBlobs; i++) {
			Blob b = new Blob((int) ((Math.random() * GameConfig.SCREEN_WIDTH * 2)),
					(int) ((Math.random() * GameConfig.SCREEN_HEIGHT * 2)),
					(int) (Math.random()*20));
			blobs.add(b);
		}
	}
	
	public void moveBlob() {
		Vector2 mouse = new Vector2(Gdx.input.getX() - GameConfig.SCREEN_WIDTH/2, 
				GameConfig.SCREEN_HEIGHT/2 - Gdx.input.getY());
		
		mouse.nor();
		blob.addPos(mouse.x*lerp, mouse.y*lerp);
		camera.position.set(blob.getX(), blob.getY(), 0);
		camera.update();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(Color.BLUE.r,Color.BLUE.g,Color.BLUE.b,Color.BLUE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		moveBlob();
		
		shapeRenderer.circle(blob.getX(), blob.getY(), blob.getRadius());
		
		for (Blob b : blobs) {
			if(b.checkCollision(blob)) {System.out.println("collision");
				shapeRenderer.setColor(Color.RED);
				shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
				shapeRenderer.setColor(Color.WHITE);
			} else {
				shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
			}
		}

		shapeRenderer.end();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
