package gameValues;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import element.Blob;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GameManager {
	
	/* Constants */
	public static int SCREEN_WIDTH = Gdx.graphics.getWidth();
	public static int SCREEN_HEIGHT = Gdx.graphics.getHeight();
	
	/* Path AI */
	private String encodingFile = "encoding/agar";

	/* Game Elements */
	private HashMap<Integer, Blob> blobs; /* Mappa di blob: id=0 mainplayer; id<0 animated; id>0 inanimated */
	private float lerp = 2.5f; 
	private int inanimatedBlobs = 0;
	private int animatedBlobs = 8;
	
	/* Singleton Instance */
	private static GameManager instance = null;
	
	private GameManager() {
		blobs = new HashMap<Integer, Blob>();
		for (int i = 0; i < inanimatedBlobs; i++) {
			Blob b = new Blob(i+1,(float) ((Math.random() * SCREEN_WIDTH * 2)), 
					(float) ((Math.random() * SCREEN_HEIGHT * 2)), (float) (Math.random()*40)); 
			blobs.put(b.getId(), b);
		}
		for (int i = 0; i < animatedBlobs; i++) {
			Blob b = new Blob(-i-1,(float) ((Math.random() * SCREEN_WIDTH * 2)), 
					(float) ((Math.random() * SCREEN_HEIGHT * 2)), (float) (Math.random()*40)); 
			blobs.put(b.getId(), b);
		}
		Blob p = new Blob(0,SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 40);
		blobs.put(p.getId(), p);
	}
	
	public static GameManager getInstance() {
		if(instance == null)
			instance = new GameManager();
		return instance;
	}
	
	public void moveBlobMouse() {
		Vector2 mouse = new Vector2(Gdx.input.getX() - SCREEN_WIDTH/2, 
				SCREEN_HEIGHT/2 - Gdx.input.getY());
		mouse.nor();
		blobs.get(0).addPos(mouse.x*lerp, mouse.y*lerp);
	}
	
	public void moveBlobToTarget(Blob actor, Vector2 target) {
		float MoveToX = target.x;
        float MoveToY = target.y;
        float diffX = MoveToX - actor.getX();
        float diffY = MoveToY - actor.getY();
        float angle = (float)Math.atan2(diffY, diffX);
		actor.addPos((float)Math.cos(angle)*lerp, 
				(float)Math.sin(angle)*lerp);
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
				moveBlobToTarget(b, chooseTarget(b));
				if(checkCollisions(b)) return;
			}
		}
	}
	
	public void managePlayer() {
		if(blobs.get(0)!=null) {
			moveBlobToTarget(blobs.get(0), chooseTarget(blobs.get(0)));
			if(checkCollisions(blobs.get(0))) return;
		}
	}
	
	public Vector2 chooseTarget(Blob actor) {
		Handler handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		InputProgram facts = new ASPInputProgram();
		try {
			facts.addProgram("actor(" + actor.getId() + ",\"" + 
					actor.getX() + "\",\"" + actor.getY() + "\",\"" + 
						actor.getRadius() + "\").");
			
//			facts.addProgram("actor(" + actor.getId() + "," + 
//					(int)actor.getX() + "," + (int)actor.getY() + "," + 
//						(int)actor.getRadius() + ").");
			
			for (Blob b : blobs.values()) {
				if(b.getId()!=actor.getId())
					facts.addObjectInput(b);
				facts.addProgram("distance(" + b.getId() + ",\"" + 
					b.checkDistance(actor) + "\").");
//				facts.addProgram("distance(" + b.getId() + "," + 
//						(int)b.checkDistance(actor) + ").");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingFile);
		handler.addProgram(encoding);

		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o; 

		String as = answers.getAnswerSetsString();
		
		/* Caso senza blob */
		if(as.contains("INCONSISTENT")) {
			System.out.println("INCONSISTENT");
			return new Vector2();
		}
		
		
		String[] tmp1 = as.split(" ");
		for (String string : tmp1)
			System.out.println(string);
		
		
		String sequence = "insegui";
		int beginSequence = as.indexOf(sequence);
		int endSequence = beginSequence+sequence.length()+1;
		String tmp = as.substring(endSequence, as.indexOf(")", endSequence));

		int target = Integer.parseInt(tmp); 
		System.out.println("@@@@ id_target: "+ target);
		
		if(blobs.containsKey(target))
			return new Vector2(blobs.get(target).getX(), blobs.get(target).getY());
		return new Vector2();
	}
	
	public Blob getPlayer() {
		return blobs.get(0);
	}
	
	public HashMap<Integer, Blob> getBlobs(){
		return blobs;
	}
}
