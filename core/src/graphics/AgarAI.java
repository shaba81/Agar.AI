package graphics;

import com.badlogic.gdx.Game;

public class AgarAI extends Game{

	StartScreen start;
	MainGameScreen main;
	OptionScreen options;
	GameOverScreen gameover;
	
	@Override
	public void create() {
		start = new StartScreen(this);
		main = new MainGameScreen(this);
		options = new OptionScreen(this);
		gameover = new GameOverScreen(this);
		setScreen(start);
	}
	
	public void changeScreen(String screen) {
		if(screen.equals("start"))
			setScreen(start);
		if(screen.equals("main"))
			setScreen(main);
		if(screen.equals("game over"))
			setScreen(gameover);
		if(screen.equals("options"))
			setScreen(options);
	}
	
}
