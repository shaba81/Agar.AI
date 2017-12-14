package graphics;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import element.Blob;

public class Render extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	ArrayList<Blob> blobs;
	Blob blob;
	OrthographicCamera camera;
	float lerp = 1.5f; //per rendere fluido il movimento della camera e del blob. Più è alto il valore, più si muove velocemente
	
	
	private int numBlobs = 40;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //i valori mi dicono lo "zoom della camera".
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		blob = new Blob(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 60);
		initBlobs();
	}
	
	public void initBlobs() {
		blobs = new ArrayList<Blob>(numBlobs);
		for (int i = 0; i < numBlobs; i++) {
			Blob b = new Blob((int) (Math.random() * Gdx.graphics.getWidth()),(int) (Math.random() * Gdx.graphics.getHeight()),(int) (Math.random() * 20));
			System.out.println(b.getX());
			blobs.add(b);
		}
	}
	
	public void moveBlob() {  //contiene Linear Interpolation per la camera
		Vector3 position = new Vector3(camera.position);
		position.x += (Gdx.input.getX() - position.x) * lerp * Gdx.graphics.getDeltaTime();
		position.y += ((Gdx.graphics.getHeight() - Gdx.input.getY()) - position.y) * lerp * Gdx.graphics.getDeltaTime();
		
		blob.setX((int) position.x);
		blob.setY((int) position.y);

		camera.position.set(position.x, position.y, 0);
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		moveBlob();
		
		shapeRenderer.circle(blob.getX(), blob.getY(), blob.getRadius());
		
		for (Blob b : blobs) {
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
		}

		shapeRenderer.end();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
