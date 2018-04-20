package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gameValues.Constants;

public class StartScreen implements Screen {
	
	AgarAI game;
	Stage stage;
	SpriteBatch batch;
	
	public Texture background = new Texture("img/bg.jpg");
	
	public StartScreen(final AgarAI game) {
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();
		
		Button button = new TextButton("Start", Constants.skin);
		button.setPosition(350, 350);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen("main");
			}
		});
		stage.addActor(button);
		
		Button options = new TextButton("Options", Constants.skin);
		options.setPosition(350, 150);
		options.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen("options");
			}
		});
		stage.addActor(options);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(background,0,0);
		batch.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}

}
