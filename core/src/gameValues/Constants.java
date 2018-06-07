package gameValues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class Constants {
	
	/* Graphic Constants */
	public static TextureAtlas texture = new TextureAtlas(Gdx.files.internal("skin/glassy-ui.atlas"));
	public static Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"), texture);

	/* Screen Constants */
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 600;
	
	/* Game Constants */
	public static final String INSEGUI = "insegui";
	public static final String SCAPPA = "scappa";
	public static final String ALLONTANATI = "allontanatiDalBordo";
	public static final float lerp = 25f; 
	public static int inanimatedBlobs = 10;
	public static int animatedBlobs =10;
	public static boolean isHumanPlayer = false;
	public static final int fieldDim = 2000;
	
	/* Field Corners */
	public static final Vector2 topRight = new Vector2(fieldDim/2, fieldDim/2);
	public static final Vector2 bottomRight = new Vector2(fieldDim/2, -fieldDim/2);
	public static final Vector2 topLeft = new Vector2(-fieldDim/2, fieldDim/2);
	public static final Vector2 bottomLeft = new Vector2(-fieldDim/2, -fieldDim/2);
	
	public static final float top = fieldDim/2;
	public static final float bottom = -fieldDim/2;
	public static final float right = fieldDim/2;
	public static final float left = -fieldDim/2;
	
	/* Colors */
	public static final Color[] colors = {
			Color.BLUE,	Color.RED, Color.YELLOW,
			Color.GREEN, Color.ORANGE, Color.BROWN,
			Color.LIME,	Color.CYAN,	Color.MAGENTA,
			Color.PINK,	Color.NAVY
	};
}
