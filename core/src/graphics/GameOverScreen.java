package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameOverScreen implements Screen {

	AgarAI game;
	Stage stage;
	SpriteBatch batch;
	
	public static TextureAtlas texture = new TextureAtlas(Gdx.files.internal("skin/glassy-ui.atlas"));
	public static Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"), texture);
	public Texture background = new Texture("img/wallpaper.jpg");
	
	public GameOverScreen(final AgarAI game) {
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();
		
		Button button = new TextButton("Game Over", skin);
		button.setPosition(350, 100);
		stage.addActor(button);
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
