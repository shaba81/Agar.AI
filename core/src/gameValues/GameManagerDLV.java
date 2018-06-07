package gameValues;

import java.util.HashMap;
import java.util.List;

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
import it.unical.mat.embasp.specializations.dlv2.DLV2AnswerSets;
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
		boolean noBlobsInYourScope = true;
		try {
			facts.addProgram("actor(" + actor.getId() + ",\"" + actor.getX() + "\",\"" + actor.getY() + "\","
					+ toInt(actor.getRadius()) + ").");

			for (Blob b : blobs.values()) {
				if (b.checkDistance(actor) < actor.getRadius() * 15) {
					noBlobsInYourScope = false;
					if (b.getId() != actor.getId()) {
						facts.addProgram("blob(" + b.getId() + ",\"" + b.getX() + "\",\"" + b.getY() + "\","
								+ toInt(b.getRadius()) + ").");
						facts.addProgram("distance(" + b.getId() + "," + toInt(b.checkDistance(actor)) + ").");
					}
				}
			}

			int topBorderDistance = toInt(blobToBorder(actor, Constants.topLeft, Constants.topRight));
			int bottomBorderDistance = toInt(blobToBorder(actor, Constants.bottomLeft, Constants.bottomRight));
			int leftBorderDistance = toInt(blobToBorder(actor, Constants.topLeft, Constants.bottomLeft));
			int rightBorderDistance = toInt(blobToBorder(actor, Constants.topRight, Constants.bottomRight));
			
			if(!noBlobsInYourScope) {
				facts.addProgram("bordo(\"top\").");
				facts.addProgram("bordo(\"bottom\").");
				facts.addProgram("bordo(\"left\").");
				facts.addProgram("bordo(\"right\").");
				facts.addProgram("distanzaDalBordo(\"top\"," + topBorderDistance + ").");
				facts.addProgram("distanzaDalBordo(\"bottom\"," + bottomBorderDistance + ").");
				facts.addProgram("distanzaDalBordo(\"left\"," + leftBorderDistance + ").");
				facts.addProgram("distanzaDalBordo(\"right\"," + rightBorderDistance + ").");
			}

			if (actor.getId() == 0) {
				System.out.println("################ Fatti ################");
				String[] fatti = facts.getPrograms().split(" ");
				for (int i = 0; i < fatti.length; i++)
					System.out.println(fatti[i]);
			}

			handler.addProgram(facts);
			encoding.addFilesPath(encodingFile);
			handler.addProgram(encoding);

			Output o = handler.startSync();

			facts.clearAll();
			encoding.clearAll();
			handler.removeAll();

			DLV2AnswerSets answers = (DLV2AnswerSets) o;
			System.out.println("############ AnswerSet ############");
			List<AnswerSet> answersList = answers.getAnswersets();
			if (answersList.isEmpty()) {
				System.out.println("NO ANSWERS");
				return new Pair("NO ANSWERS", new Vector2());
			}

			AnswerSet a = answersList.get(answersList.size() - 1);
			String as = a.toString();

			if (actor.getId() == 0) {
				String[] tmp1 = as.split(" ");
				for (String string : tmp1)
					System.out.println(string);
				System.out.println();
			}

			boolean insegui = false;
			if (as.contains(Constants.INSEGUI)) {
				insegui = true;
				int beginSequence = as.indexOf(Constants.INSEGUI);
				int endSequence = beginSequence + Constants.INSEGUI.length() + 1;
				String id = as.substring(endSequence, as.indexOf(")", endSequence));
				int target = Integer.parseInt(id);
				if (blobs.containsKey(target))
					return new Pair(Constants.INSEGUI, new Vector2(blobs.get(target).getX(), blobs.get(target).getY()));
				
			} else if (as.contains(Constants.SCAPPA)) {
				int beginSequence = as.indexOf(Constants.SCAPPA);
				int endSequence = beginSequence + Constants.SCAPPA.length() + 1;
				String id = as.substring(endSequence, as.indexOf(")", endSequence));
				int target = Integer.parseInt(id);
				if (blobs.containsKey(target)) {
					return new Pair(Constants.SCAPPA, new Vector2(blobs.get(target).getX(), blobs.get(target).getY()));
				}
				
			} else if (as.contains(Constants.ALLONTANATI) && !insegui) {
				int begin1 = as.indexOf(Constants.ALLONTANATI);
				int end1 = begin1 + Constants.ALLONTANATI.length() + 1;
				String border = as.substring(end1, as.indexOf(")", end1));
				int begin2 = as.indexOf("bordoPiuLontano");
				int end2 = begin2 + "bordoPiuLontano".length() + 1;
				String direzione = as.substring(end2, as.indexOf(")", end2));

				if (border.contains("top")) {
					if (direzione.contains("left")) {
						Vector2 uno = new Vector2(actor.getX(), actor.getY() - topBorderDistance);
						Vector2 due = new Vector2(actor.getX() - topBorderDistance, actor.getY());
						Vector2 result = new Vector2(due.x, uno.y);
						return new Pair(Constants.INSEGUI, result);
					}
					if (direzione.contains("right")) {
						Vector2 uno = new Vector2(actor.getX(), actor.getY() - topBorderDistance);
						Vector2 due = new Vector2(actor.getX() + topBorderDistance, actor.getY());
						Vector2 result = new Vector2(due.x, uno.y);
						return new Pair(Constants.INSEGUI, result);
					}
				}

				if (border.contains("bottom")) {
					if (direzione.contains("left")) {
						Vector2 uno = new Vector2(actor.getX(), actor.getY() + bottomBorderDistance);
						Vector2 due = new Vector2(actor.getX() - bottomBorderDistance, actor.getY());
						Vector2 result = new Vector2(due.x, uno.y);
						return new Pair(Constants.INSEGUI, result);
					}
					if (direzione.contains("right")) {
						Vector2 uno = new Vector2(actor.getX(), actor.getY() + bottomBorderDistance);
						Vector2 due = new Vector2(actor.getX() + bottomBorderDistance, actor.getY());
						Vector2 result = new Vector2(due.x, uno.y);
						return new Pair(Constants.INSEGUI, result);
					}
				}

				if (border.contains("left")) {
					if (direzione.contains("top")) {
						Vector2 uno = new Vector2(actor.getX() + leftBorderDistance, actor.getY());
						Vector2 due = new Vector2(actor.getX(), actor.getY() + leftBorderDistance);
						Vector2 result = new Vector2(uno.x, due.y);
						return new Pair(Constants.INSEGUI, result);
					}
					if (direzione.contains("bottom")) {
						Vector2 uno = new Vector2(actor.getX() + leftBorderDistance, actor.getY());
						Vector2 due = new Vector2(actor.getX(), actor.getY() - leftBorderDistance);
						Vector2 result = new Vector2(uno.x, due.y);
						return new Pair(Constants.INSEGUI, result);
					}
				}

				if (border.contains("right")) {
					if (direzione.contains("top")) {
						Vector2 uno = new Vector2(actor.getX() - rightBorderDistance, actor.getY());
						Vector2 due = new Vector2(actor.getX(), actor.getY() + rightBorderDistance);
						Vector2 result = new Vector2(uno.x, due.y);
						return new Pair(Constants.INSEGUI, result);
					}
					if (direzione.contains("bottom")) {
						Vector2 uno = new Vector2(actor.getX() - rightBorderDistance, actor.getY());
						Vector2 due = new Vector2(actor.getX(), actor.getY() - rightBorderDistance);
						Vector2 result = new Vector2(uno.x, due.y);
						return new Pair(Constants.INSEGUI, result);
					}

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Pair(" . ", new Vector2());
	}

	public int toInt(float x) {
		return Math.round(x * 1000);
	}

	public float blobToBorder(Blob blob, Vector2 v1, Vector2 v2) {
		float x1 = v1.x, x2 = v2.x, y1 = v1.y, y2 = v2.y;
		float x = blob.getX(), y = blob.getY();

		float A = x - x1; // position of point rel one end of line
		float B = y - y1;
		float C = x2 - x1; // vector along line
		float D = y2 - y1;
		float E = -D; // orthogonal vector
		float F = C;

		float dot = A * E + B * F;
		float len_sq = E * E + F * F;

		return (float) (Math.abs(dot) / Math.sqrt(len_sq));

	}

	// public Vector2 addition(Vector2 uno, Vector2 due, Blob actor) {
	// Vector2 a = new Vector2(actor.getX(), actor.getY());
	//
	// a.rotate(uno.angle(due));
	//
	// return a;
	// return new Vector2((uno.x + due.x), (uno.y + due.y));

	// }
}
