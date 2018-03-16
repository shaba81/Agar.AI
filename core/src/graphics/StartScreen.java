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

public class StartScreen implements Screen {
	
	AgarAI game;
	Stage stage;
	SpriteBatch batch;
	
	public static TextureAtlas texture = new TextureAtlas(Gdx.files.internal("skin/glassy-ui.atlas"));
	public static Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"), texture);
	
	public StartScreen(final AgarAI game) {
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();
		
		Button button = new TextButton("Start", skin);
		button.setPosition(350, 150);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen("main");
			}
		});
		stage.addActor(button);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(new Texture("img/wallpaper.jpg"),0,0);
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
