package gameValues;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.math.Vector2;

import element.Blob;
import element.Pair;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GameManagerDLV {
	
	/* EmbASP Elements */
	private Handler handler;
	private InputProgram facts;
	private InputProgram encoding;
	private String encodingFile = "encoding/scappa&insegui";
	
	public GameManagerDLV() {
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		encoding = new ASPInputProgram();
		facts = new ASPInputProgram();
	}

	public Pair chooseTarget(Blob actor, HashMap<Integer, Blob> blobs) {
		try {
			facts.addProgram("actor(" + actor.getId() + ",\"" + 
					actor.getX() + "\",\"" + actor.getY() + "\"," + 
						toInt(actor.getRadius()) + ").");
			
			for (Blob b : blobs.values()) {
				if(b.getId() != actor.getId()) {
					facts.addProgram("blob(" + b.getId() + ",\"" + 
							b.getX() + "\",\"" + b.getY() + "\"," + 
							toInt(b.getRadius()) + ").");
					facts.addProgram("distance(" + b.getId() + "," + 
							toInt(b.checkDistance(actor)) + ").");
				}
			}
			handler.addProgram(facts);
			encoding.addFilesPath(encodingFile);
			handler.addProgram(encoding);
			
			Output o = handler.startSync();
			
			facts.clearAll();
			encoding.clearAll();
			handler.removeAll();
			
			AnswerSets answers = (AnswerSets) o; 
			
			List<AnswerSet> answersList = answers.getAnswersets();
			if(answersList.isEmpty()) {
				System.out.println("NO ANSWERS");
				return new Pair("NO ANSWERS", new Vector2());
			}
			
			AnswerSet a = answersList.get(answersList.size()-1);
			String as = a.toString();
			
//		String[] tmp1 = as.split(" ");
//		for (String string : tmp1)
//			System.out.println(string);
//		System.out.println();
			
			if(as.contains(Constants.INSEGUI)) {
				int beginSequence = as.indexOf(Constants.INSEGUI);
				int endSequence = beginSequence + Constants.INSEGUI.length() + 1;
				String id = as.substring(endSequence, as.indexOf(")", endSequence));
				
				int target = Integer.parseInt(id); 
				
				if(blobs.containsKey(target))
					return new Pair(Constants.INSEGUI, 
							new Vector2(blobs.get(target).getX(), blobs.get(target).getY()));
			}
			else if(as.contains(Constants.SCAPPA)) {
				int beginSequence = as.indexOf(Constants.SCAPPA);
				int endSequence = beginSequence + Constants.SCAPPA.length() + 1;
				String id = as.substring(endSequence, as.indexOf(")", endSequence));
				
				int target = Integer.parseInt(id); 
				
				if(blobs.containsKey(target))
					return new Pair(Constants.SCAPPA, 
							new Vector2(blobs.get(target).getX(), blobs.get(target).getY()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Pair(" . ", new Vector2());
	}
	
	public int toInt(float x) {
		return Math.round(x*1000);
	}
	
}
