package gameValues;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import element.Blob;
import element.Pair;
import graphics.AgarAI;

public class GameManager {
	
	/* Game Elements */
	private HashMap<Integer, Blob> blobs; /* Mappa di blob: id=0 mainplayer; id<0 animated; id>0 inanimated */
	
	/* Singleton Instance */
	private static GameManager instance = null;
	
	private AgarAI game;
	private GameManagerDLV dlv;
	
	private GameManager(final AgarAI game) {
		
		this.game = game;
		dlv = new GameManagerDLV();
		
		blobs = new HashMap<Integer, Blob>();
		
		for (int i = 0; i < Constants.inanimatedBlobs; i++) {
			Blob b = new Blob();
			b.setRandomBlob(i+1);
			b.setColor(Constants.colors[i%Constants.colors.length]);
			blobs.put(b.getId(), b);
		}
		for (int i = 0; i < Constants.animatedBlobs; i++) {
			Blob b = new Blob();
			b.setRandomBlob(-i-1);
			b.setColor(Constants.colors[i%Constants.colors.length]);
			blobs.put(b.getId(), b);
		}
		
		Blob player = new Blob(0, 0, 0, 20, Color.RED);
		blobs.put(player.getId(), player);
	}
	
	public static GameManager getInstance(AgarAI game) {
		if(instance == null)
			instance = new GameManager(game);
		return instance;
	}
	
	public void moveWithMouse() {
		Vector2 mouse = new Vector2(Gdx.input.getX() - Constants.SCREEN_WIDTH/2, 
				Constants.SCREEN_HEIGHT/2 - Gdx.input.getY());
		mouse.nor();
		blobs.get(0).addPos(mouse.x*Constants.lerp, mouse.y*Constants.lerp);
	}
	
	public boolean checkCollisions(Blob actor) {
		boolean removed = false;
		Iterator<Blob> it = blobs.values().iterator();
		
		while(it.hasNext() && !removed) {
			Blob tmp = it.next();
			if(actor.getId()!=tmp.getId() && tmp.checkCollision(actor))
				if(actor.getRadius() > tmp.getRadius()) {
					if(tmp.getId() == 0) {
						game.changeScreen("game over");
						return true;
					}
					actor.increment(tmp.getRadius()/2);
					blobs.remove(tmp.getId());
					removed = true;
				}
		} 
		return removed;
	}
	
	public void manageActors() {
		for (Blob b : blobs.values()) {
			if(b.getId() < 0) {
				b.setTarget(dlv.chooseTarget(b, blobs));
				if(checkCollisions(b)) return;
			}
		}
	}
	
	public void managePlayer() {
		blobs.get(0).setTarget(dlv.chooseTarget(getPlayer(), blobs));
		if(checkCollisions(getPlayer())) return;
	}
	
	public Blob getPlayer() {
		return blobs.get(0);
	}
	
	public HashMap<Integer, Blob> getBlobs(){
		return blobs;
	}
}
