package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import element.Blob;
import gameValues.BlobManager;
import gameValues.Constants;
import gameValues.GameManager;

public class MainGameScreen implements Screen {

	/* Logic Elements */
	private AgarAI game;
	private GameManager gameManager;
	private BlobManager blobManager;
 
	/* Graphic Elements */
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch spritebatch;
	private FitViewport viewport;
	private Stage stageMap;
	private Texture mapTexture;
	private Image mapImage;

	public MainGameScreen(AgarAI game) {
		this.game = game;
		gameManager = GameManager.getInstance(game);
		blobManager = new BlobManager(game);

		shapeRenderer = new ShapeRenderer();
		spritebatch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		viewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, camera);
		viewport.apply();
		
		stageMap = new Stage(viewport, spritebatch);
		mapTexture = new Texture(Gdx.files.internal("img/field.png"));
		mapImage = new Image(mapTexture);
		mapImage.setSize(Constants.fieldDim, Constants.fieldDim);
		mapImage.setPosition(-Constants.fieldDim/2, -Constants.fieldDim/2);
		stageMap.addActor(mapImage);
		
		blobManager.start();
	}

	public void updatePlayer(Blob actor) {
		camera.position.set(actor.getX(), actor.getY(), 0);
		camera.zoom = actor.getRadius() / 15f;
		camera.update();
	}

	@Override
	public void render(float delta) { 
		Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled); 
		shapeRenderer.setProjectionMatrix(camera.combined);
		stageMap.act(delta);
		stageMap.draw();
		
		if (Constants.isHumanPlayer) {
			gameManager.moveWithMouse();
			gameManager.checkCollisions(gameManager.getPlayer());
		} else {
			gameManager.managePlayer();
		}

		updatePlayer(gameManager.getPlayer());

		for (Blob b : gameManager.getBlobs().values()) {
			if(!(b.getId() == 0 && Constants.isHumanPlayer))
				b.move();
			shapeRenderer.setColor(b.getColor()); 
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
		}
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
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
