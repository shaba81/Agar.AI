package graphics;

import com.badlogic.gdx.Game;

public class AgarAI extends Game{

	StartScreen start;
	MainGameScreen main;
	GameOverScreen gameover;
	
	@Override
	public void create() {
		start = new StartScreen(this);
		main = new MainGameScreen(this);
		gameover = new GameOverScreen(this);
		setScreen(start);
	}
	
	public void changeScreen(String screen) {
		if(screen.equals("main"))
			setScreen(main);
		if(screen.equals("game over"))
			setScreen(gameover);
	}
	
}
