package element;

import com.badlogic.gdx.math.Vector2;

public class Blob {
	
	private float x;
	private float y;
	Vector2 position;
	private int radius;
	
	
	public Blob(float x, float y, int radius) {
		this.x = x;
		this.y = y;
		this.position = new Vector2 (x, y);
		this.radius = radius;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void addPos(Vector2 pos) {
		this.position.add(pos);
	}
	

}
