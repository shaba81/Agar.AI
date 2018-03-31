package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import gameValues.Constants;

public class OptionScreen implements Screen {

	AgarAI game;
	Stage stage;
	SpriteBatch batch;

	public static TextureAtlas texture = new TextureAtlas(Gdx.files.internal("skin/glassy-ui.atlas"));
	public static Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"), texture);
	public Texture background = new Texture("img/bg.jpg");
	private CheckBox human;
	private Label animatedBlobs;
	private Label inanimatedBlobs;
	private Slider sliderAn;
	private Slider sliderIn;

	public OptionScreen(final AgarAI game) {
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();
		
		Table table = new Table();
		table.setFillParent(true);
		table.center();

		human = new CheckBox("Human Player", skin);
		human.setPosition(350, 550);
		human.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Constants.isHumanPlayer = !Constants.isHumanPlayer;
				System.out.println(Constants.isHumanPlayer);
			}
		});

		animatedBlobs = new Label("Animated Blobs: " + Constants.animatedBlobs, skin);
		animatedBlobs.setPosition(350, 600);
		
		sliderAn = new Slider(0f, 100f, 1f, false, skin);
		sliderAn.setPosition(350, 650);
		sliderAn.setValue(Constants.animatedBlobs);
		sliderAn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Constants.animatedBlobs = (int) sliderAn.getValue();
				System.out.println(Constants.animatedBlobs);
				animatedBlobs.setText("Animated Blobs: " + Constants.animatedBlobs);

			}
		});


		inanimatedBlobs = new Label("Inanimated Blobs: " + Constants.inanimatedBlobs, skin);
		inanimatedBlobs.setPosition(350, 700);
		
		sliderIn = new Slider(0f, 100f, 1f, false, skin);
		sliderIn.setPosition(350, 750);
		sliderIn.setValue(Constants.inanimatedBlobs);
		sliderIn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Constants.inanimatedBlobs = (int) sliderIn.getValue();
				System.out.println(Constants.inanimatedBlobs);
				inanimatedBlobs.setText("Inanimated Blobs: " + Constants.inanimatedBlobs);

			}
		});

		Button back = new TextButton("Back", skin);
		back.setPosition(650, 50);
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen("start");
			}
		});


		
		
//		stage.addActor(human);
//		stage.addActor(animatedBlobs);
//		stage.addActor(sliderAn);
//		stage.addActor(inanimatedBlobs);
//		stage.addActor(sliderIn);
		
		table.add(human);
		table.row();
		table.add(animatedBlobs);
		table.row();
		table.add(sliderAn);
		table.row();
		table.add(inanimatedBlobs);
		table.row();
		table.add(sliderIn);
		
		stage.addActor(table);
		stage.addActor(back);
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		stage.act();
		stage.draw();

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

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();

	}

}
