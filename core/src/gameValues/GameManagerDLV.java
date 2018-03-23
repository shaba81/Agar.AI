package gameValues;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import element.Blob;
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
	private String encodingFile = "/home/angelo/Documenti/GitHub/Agar.AI/desktop/encoding/agar";
	
	public GameManagerDLV() {
		handler = new DesktopHandler(new DLV2DesktopService("/home/angelo/Documenti/GitHub/Agar.AI/desktop/lib/dlv2"));
		encoding = new ASPInputProgram();
		facts = new ASPInputProgram();
	}

	public Vector2 chooseTarget(Blob actor, HashMap<Integer, Blob> blobs) {
		System.out.println("++++++++ actor: " + actor.getId());
		try {
			facts.addProgram("actor(" + actor.getId() + ",\"" + 
					actor.getX() + "\",\"" + actor.getY() + "\"," + 
						toInt(actor.getRadius()) + ").");
			
			for (Blob b : blobs.values()) {
				if(b.getId() != actor.getId()) {
					facts.addProgram("blob(" + b.getId() + ",\"" + 
							b.getX() + "\",\"" + b.getY() + "\"," + 
							toInt(b.getRadius()) + ").");
				}
				facts.addProgram("distance(" + b.getId() + "," + 
						toInt(b.checkDistance(actor)) + ").");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			return new Vector2();
		}
		
		AnswerSet a = answersList.get(answersList.size()-1);
		String as = a.toString();
		
		String[] tmp1 = as.split(" ");
		for (String string : tmp1)
			System.out.println(string);
		System.out.println();
		
		String sequence = "insegui";
		int beginSequence = as.indexOf(sequence);
		int endSequence = beginSequence+sequence.length()+1;
		String tmp = as.substring(endSequence, as.indexOf(")", endSequence));

		int target = Integer.parseInt(tmp); 
		
		if(blobs.containsKey(target))
			return new Vector2(blobs.get(target).getX(), blobs.get(target).getY());
		return new Vector2();
	}
	
	public int toInt(float x) {
		return Math.round(x*1000);
	}
	
}
