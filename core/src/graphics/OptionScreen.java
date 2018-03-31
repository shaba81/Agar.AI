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

	public OptionScreen(final AgarAI game) {
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();

		human = new CheckBox("Human Player", skin);
		human.setPosition(350, 550);
		human.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Constants.isHumanPlayer = !Constants.isHumanPlayer;
				System.out.println(Constants.isHumanPlayer);
			}
		});
		stage.addActor(human);
		
		
		
		Button back = new TextButton("Back", skin);
		back.setPosition(650, 50);
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen("start");
			}
		});
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
