package graphics;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import element.Blob;

public class Render extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	ArrayList<Blob> blobs;
	Blob blob;
	OrthographicCamera camera;
	
	
	private int numBlobs = 10;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 400); //i valori mi dicono lo "zoom della camera".
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		blob = new Blob(600/2,400/2, 60);
		initBlobs();
	}
	
	public void initBlobs() {
		blobs = new ArrayList<Blob>(numBlobs);
		for (int i = 0; i < numBlobs; i++) {
			Blob b = new Blob((int) (Math.random() * 500),(int) (Math.random() * 300),(int) (Math.random() * 20));
			System.out.println(b.getX());
			blobs.add(b);
		}
	}
	
	public void moveBlob() {
		blob.setX(Gdx.input.getX());
		blob.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
		camera.position.set(blob.getX(), blob.getY(), 0);
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
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
