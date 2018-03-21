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

import element.Blob;
import gameValues.GameManager;

public class MainGameScreen implements Screen {
	
	private AgarAI game;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
//	private Texture background;
//	private Sprite sprite;
	private OrthographicCamera camera;
	private GameManager manager;
	
	
	public MainGameScreen(AgarAI game) {
		this.game = game;
		manager = GameManager.getInstance();

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.GREEN);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT); 
		
//		background = new Texture("img/field.png");
//		sprite = new Sprite(background);
//		sprite.setSize(GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);
	}
	
	public void updatePlayer(Blob actor) {
		shapeRenderer.circle(actor.getX(), actor.getY(), actor.getRadius());
		camera.position.set(actor.getX(), actor.getY(), 0); 
		camera.zoom = actor.getRadius()/35f;
		camera.update();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(Color.WHITE.r,Color.WHITE.g,Color.WHITE.b,Color.WHITE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		manager.manageActors();
		manager.managePlayer();
		updatePlayer(manager.getPlayer());
		
//		batch.draw(sprite, 0, 0, GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);
		
		for (Blob b : manager.getBlobs().values())
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
		
		shapeRenderer.end();
		batch.end();
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
