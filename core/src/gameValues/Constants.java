package gameValues;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Constants {

	/* Screen Constants */
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 600;
	
	/* Game Constants */
	public static final String INSEGUI = "insegui";
	public static final String SCAPPA = "scappa";
	public static final float lerp = 25f; 
	public static int inanimatedBlobs = 0;
	public static int animatedBlobs = 20;
	public static boolean isHumanPlayer = true;
	public static final int fieldDim = 2000;
	
	/* Field Corners */
	public static final Vector2 topRight = new Vector2(fieldDim/2, fieldDim/2);
	public static final Vector2 bottomRight = new Vector2(fieldDim/2, -fieldDim/2);
	public static final Vector2 topLeft = new Vector2(-fieldDim/2, fieldDim/2);
	public static final Vector2 bottomLeft = new Vector2(-fieldDim/2, -fieldDim/2);
	
	/* Colors */
	public static final Color[] colors = {
			Color.BLUE,
			Color.RED,
			Color.YELLOW,
			Color.GREEN,
			Color.ORANGE,
			Color.BROWN,
			Color.LIME,
			Color.CYAN,
			Color.MAGENTA,
			Color.PINK,
			Color.NAVY
	};
}
