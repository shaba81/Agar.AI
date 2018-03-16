package graphics;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import element.Blob;
import gameValues.GameConfig;

public class MainGameScreen implements Screen {
	private AgarAI game;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private TextureRegion background;
	private ArrayList<Blob> blobs;
	private Blob blob;
	private OrthographicCamera camera;
	private float lerp = 2.5f; 
	//per rendere fluido il movimento della camera e del blob. Pi� � alto il valore, pi� si muove velocemente
	private int numBlobs = 120;
	
	public MainGameScreen(AgarAI game) {
		this.game = game;

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		camera = new OrthographicCamera();
		//i valori mi dicono lo "zoom della camera".
		camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT); 

		shapeRenderer.setColor(Color.GREEN);
		blob = new Blob(GameConfig.SCREEN_WIDTH/2,GameConfig.SCREEN_HEIGHT/2, 40);
		
		background = new TextureRegion(new Texture("img/field.png"));
		
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
		camera.position.set(blob.getX(), blob.getY(), 0); camera.zoom = blob.getRadius()/35f;
		camera.update();
	}

	@Override
	public void render (float delta) {
		//Gdx.gl.glClearColor(Color.WHITE.r,Color.WHITE.g,Color.WHITE.b,Color.WHITE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		moveBlob();
		batch.draw(background, blob.getX(), blob.getY());
		
		shapeRenderer.circle(blob.getX(), blob.getY(), blob.getRadius());
		
		for (int i=0; i<blobs.size(); i++) {
			if(blobs.get(i).checkCollision(blob)) {
				shapeRenderer.circle(blobs.get(i).getX(), blobs.get(i).getY(), blobs.get(i).getRadius());
				blob.increment(blobs.get(i).getRadius()/2);
				blobs.remove(blobs.get(i));
			} else {
				shapeRenderer.circle(blobs.get(i).getX(), blobs.get(i).getY(), blobs.get(i).getRadius());
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
