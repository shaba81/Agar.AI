package gameValues;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import element.Blob;

public class GameManager {
	
	/* Game Elements */
	private HashMap<Integer, Blob> blobs; /* Mappa di blob: id=0 mainplayer; id<0 animated; id>0 inanimated */
	
	/* Singleton Instance */
	private static GameManager instance = null;
	
	private GameManagerDLV dlv;
	
	private GameManager() {
		
		dlv = new GameManagerDLV();
		
		blobs = new HashMap<Integer, Blob>();
		for (int i = 0; i < Constants.inanimatedBlobs; i++) {
			Blob b = new Blob(i+1,(float) ((Math.random() * Constants.SCREEN_WIDTH * 2)), 
					(float) ((Math.random() * Constants.SCREEN_HEIGHT * 2)), (float) (Math.random()*40)); 
			blobs.put(b.getId(), b);
		}
		for (int i = 0; i < Constants.animatedBlobs; i++) {
			Blob b = new Blob(-i-1,(float) ((Math.random() * Constants.SCREEN_WIDTH * 2)), 
					(float) ((Math.random() * Constants.SCREEN_HEIGHT * 2)), (float) (Math.random()*40)); 
			blobs.put(b.getId(), b);
		}
		Blob p = new Blob(0, Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2, 40);
		blobs.put(p.getId(), p);
	}
	
	public static GameManager getInstance() {
		if(instance == null)
			instance = new GameManager();
		return instance;
	}
	
	public void moveBlobMouse() {
		Vector2 mouse = new Vector2(Gdx.input.getX() - Constants.SCREEN_WIDTH/2, 
				Constants.SCREEN_HEIGHT/2 - Gdx.input.getY());
		mouse.nor();
		blobs.get(0).addPos(mouse.x*Constants.lerp, mouse.y*Constants.lerp);
	}
	
	public void moveBlobToTarget(Blob actor, Vector2 target) {
		float MoveToX = target.x;
        float MoveToY = target.y;
        float diffX = MoveToX - actor.getX();
        float diffY = MoveToY - actor.getY();
        float angle = (float)Math.atan2(diffY, diffX);
		actor.addPos((float)Math.cos(angle)*Constants.lerp, 
				(float)Math.sin(angle)*Constants.lerp);
	}
	
	public boolean checkCollisions(Blob actor) {
		boolean removed = false;
		Iterator<Blob> it = blobs.values().iterator();
		while(it.hasNext() && !removed) {
			Blob tmp = it.next();
			if(actor.getId()!=tmp.getId() && tmp.checkCollision(actor)) {
				if(actor.getRadius() > tmp.getRadius()) {
					actor.increment(tmp.getRadius()/2);
					blobs.remove(tmp.getId());
					removed = true;
				}
			}
		} 
		return removed;
	}
	
	public void manageActors() {
		for (Blob b : blobs.values()) {
			if(b.getId() <= 0) {
				moveBlobToTarget(b, dlv.chooseTarget(b, blobs));
				if(checkCollisions(b)) return;
			}
		}
	}
	
	public void managePlayer() {
		moveBlobToTarget(blobs.get(0), dlv.chooseTarget(blobs.get(0), blobs));
		if(checkCollisions(blobs.get(0))) return;
	}
	
	public Blob getPlayer() {
		return blobs.get(0);
	}
	
	public HashMap<Integer, Blob> getBlobs(){
		return blobs;
	}
}
