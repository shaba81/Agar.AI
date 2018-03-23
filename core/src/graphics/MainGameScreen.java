package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import element.Blob;
import gameValues.Constants;
import gameValues.GameManager;

public class MainGameScreen implements Screen {

	/* Logic Elements */
	private AgarAI game;
	private GameManager manager;
	
	/* Graphic Elements */
	private OrthographicCamera camera;
	private OrthographicCamera fixedCamera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Texture background;
	
	
	public MainGameScreen(AgarAI game) {
		this.game = game;
		manager = GameManager.getInstance(game);

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.GREEN);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT); 
		
		fixedCamera = new OrthographicCamera();
		fixedCamera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		
		background = new Texture(Gdx.files.internal("img/field.png"));
	}
	
	public void updatePlayer(Blob actor) {
		shapeRenderer.circle(actor.getX(), actor.getY(), actor.getRadius());
		camera.position.set(actor.getX(), actor.getY(), 0); 
		camera.zoom = actor.getRadius()/35f;
		camera.update();
	}

	@Override
	public void render (float delta) {
//		Gdx.gl.glClearColor(Color.WHITE.r,Color.WHITE.g,Color.WHITE.b,Color.WHITE.a);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		batch.setProjectionMatrix(fixedCamera.combined);
		batch.enableBlending();
		
		batch.draw(background, -manager.getPlayer().getX(), -manager.getPlayer().getY(), Constants.SCREEN_WIDTH * 2, Constants.SCREEN_HEIGHT * 2);
		batch.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		manager.manageActors();
		manager.managePlayer();
		updatePlayer(manager.getPlayer());
		
		for (Blob b : manager.getBlobs().values())
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
		
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void show() {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}
