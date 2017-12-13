package graphics;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
	
	private int numBlobs = 10;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		blob = new Blob(600/2,400/2, 60);
		initBlobs();
	}
	
	public void initBlobs() {
		blobs = new ArrayList<Blob>(numBlobs);
		for (int i = 0; i < numBlobs; i++) {
			Blob b = new Blob((int) Math.random() * 500,(int) Math.random() * 300,(int) Math.random() * 30);
			blobs.add(b);
		}
		System.out.println(blobs.size());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		
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
