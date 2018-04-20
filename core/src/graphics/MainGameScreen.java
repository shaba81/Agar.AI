package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	private boolean pause;
	private float timer;
 
	/* Graphic Elements */
	private OrthographicCamera camera;
	private OrthographicCamera HUDcamera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch spritebatch;
	private FitViewport viewport;
	private FitViewport HUDviewport;
	private Stage stageMap;
	private Stage num;
	private Texture mapTexture;
	private Image mapImage;
	private Label numBlobs;
	private Label yourDim;

	public MainGameScreen(AgarAI game) {
		this.game = game;
		gameManager = GameManager.getInstance(game);
		blobManager = new BlobManager(game);
		pause = false;
		timer = 0;

		shapeRenderer = new ShapeRenderer();
		spritebatch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		HUDcamera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		viewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, camera);
		HUDviewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, HUDcamera);
		viewport.apply();
		HUDviewport.apply();
		
		stageMap = new Stage(viewport, spritebatch);
		mapTexture = new Texture(Gdx.files.internal("img/field.png"));
		mapImage = new Image(mapTexture);
		mapImage.setSize(Constants.fieldDim, Constants.fieldDim);
		mapImage.setPosition(-Constants.fieldDim/2, -Constants.fieldDim/2);
		stageMap.addActor(mapImage);
		
		numBlobs = new Label("BLOBS: " + gameManager.getBlobs().values().size(), Constants.skin);
		yourDim = new Label("YOU: " + gameManager.getPlayer().getRadius(), Constants.skin);
		num = new Stage(HUDviewport, spritebatch);
		
		blobManager.start();
	}

	public void updatePlayer(Blob actor) {
		camera.position.set(actor.getX(), actor.getY(), 0);
		camera.zoom = actor.getRadius() / 35f;
		camera.update();
	}

	@Override
	public void render(float delta) { 
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			pause = !pause;

		Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		shapeRenderer.begin(ShapeType.Filled); 
		shapeRenderer.setProjectionMatrix(camera.combined);
		stageMap.act(delta);
		stageMap.draw();
		
		refreshHUD();
		
		for (Blob b : gameManager.getBlobs().values()) {
			shapeRenderer.setColor(b.getColor()); 
			shapeRenderer.circle(b.getX(), b.getY(), b.getRadius());
		}

		if(!pause) {
			if(timer > 1 && gameManager.getBlobs().size() < 10) {
				gameManager.addBlob();
				timer = 0;
			}

			updatePlayer(gameManager.getPlayer());

			if (Constants.isHumanPlayer) {
				gameManager.moveWithMouse();
				gameManager.checkCollisions(gameManager.getPlayer());
			} else {
				gameManager.managePlayer();
			}
	
			for (Blob b : gameManager.getBlobs().values())
				if(!(b.getId() == 0 && Constants.isHumanPlayer))
					b.move();

			timer += delta;
		}
		shapeRenderer.end();
	}

	
	public void refreshHUD() {
		numBlobs.setText("BLOBS: " + gameManager.getBlobs().values().size());
		numBlobs.setColor(Color.BLUE);
		numBlobs.setSize(1000,1000);
		numBlobs.setPosition(0, 90);
		yourDim.setText("YOU: " + gameManager.getPlayer().getRadius());
		yourDim.setColor(Color.BLUE);
		yourDim.setSize(1000,1000);
		yourDim.setPosition(0, 70);
		num.clear();
		num.addActor(numBlobs);
		num.addActor(yourDim);
		num.act();
		num.draw();
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		stageMap.dispose();
		num.dispose();
		mapTexture.dispose();
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
