package element;

import com.badlogic.gdx.math.Vector2;

public class Blob {
	
	private Vector2 position;
	private int radius;
	
	public Blob(Vector2 position, int radius) {
		
		this.position = position;
		this.radius = radius;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	

}
