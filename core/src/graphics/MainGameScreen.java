package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import element.Blob;
import gameValues.Constants;
import gameValues.GameManager;

public class MainGameScreen implements Screen {

	/* Logic Elements */
	private AgarAI game;
	private GameManager manager;

	/* Graphic Elements */
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	public MainGameScreen(AgarAI game) {
		this.game = game;
		manager = GameManager.getInstance(game);

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.GREEN);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
	}

	public void updatePlayer(Blob actor) {
		shapeRenderer.circle(actor.getX(), actor.getY(), actor.getRadius());
		camera.position.set(actor.getX(), actor.getY(), 0);
//		System.out.println("player " + camera.position);
		camera.zoom = actor.getRadius() / 35f;
		camera.update();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);
		drawMatrix(shapeRenderer);

		if (Constants.isHumanPlayer) {
			manager.moveWithMouse();
			manager.checkCollisions(manager.getPlayer());
		} else {
			manager.managePlayer();
		}

		manager.manageActors();
		updatePlayer(manager.getPlayer());

		for (Blob b : manager.getBlobs().values())
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());

		shapeRenderer.end();
	}

	public void drawMatrix(ShapeRenderer s) {
		s.setColor(Color.BLACK);
		float begin = Constants.fieldDim/2;
		int numLines = Constants.fieldDim/100;
		for (int i = 0; i < numLines; i++) {
			shapeRenderer.line(-begin, begin - (i * 100), begin, begin - (i * 100));
			shapeRenderer.line(-begin + (i * 100), begin, -begin + (i * 100), -begin);
		}
		s.setColor(Color.GREEN);
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
}
