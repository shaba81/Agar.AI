package graphics;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import element.Blob;
import gameValues.GameConfig;

public class Render extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Blob> blobs;
	private Blob blob;
	private OrthographicCamera camera;
	private float lerp = 2.5f; 
	//per rendere fluido il movimento della camera e del blob. Pi� � alto il valore, pi� si muove velocemente
	private int numBlobs = 140;
	
	@Override
	public void create () {
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
			Blob b = new Blob((int) (new Random().nextInt(GameConfig.SCREEN_WIDTH * 2)),
					(int) (new Random().nextInt(GameConfig.SCREEN_HEIGHT * 2)),
					(int) (Math.random() * 20));
			blobs.add(b);
		}
	}
	
	/*public void moveBlob() {  //contiene Linear Interpolation per la camera
		Vector3 position = new Vector3(camera.position);
		position.x += (Gdx.input.getX() - position.x) * lerp * Gdx.graphics.getDeltaTime();
		position.y += ((GameConfig.SCREEN_HEIGHT - Gdx.input.getY()) - position.y) * lerp * Gdx.graphics.getDeltaTime();
		
		blob.setX((int) position.x);
		blob.setY((int) position.y);

		camera.position.set(position.x, position.y, 0);
		camera.update();
	}*/
	
	public void moveBlob() {
		Vector2 mouse = new Vector2(Gdx.input.getX() - GameConfig.SCREEN_WIDTH/2, 
				GameConfig.SCREEN_HEIGHT/2 - Gdx.input.getY());
		
		mouse.nor();
//		System.out.println(mouse);
		blob.addPos(new Vector2(mouse.x*lerp, mouse.y*lerp));
		
		camera.position.set(blob.getPosition(), 0);
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(Color.BLUE.r,Color.BLUE.g,Color.BLUE.b,Color.BLUE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		moveBlob();
		
		shapeRenderer.circle(blob.getPosition().x, blob.getPosition().y, blob.getRadius());
		
		for (Blob b : blobs) {
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
		}

		shapeRenderer.end();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
