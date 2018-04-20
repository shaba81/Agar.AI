package gameValues;

import graphics.AgarAI;

public class BlobManager extends Thread {

	GameManager manager;

	public BlobManager(AgarAI game) {
		manager = GameManager.getInstance(game); 
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				manager.manageActors(); 
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
