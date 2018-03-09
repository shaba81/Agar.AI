package element;

public class Blob {
	
	private float x;
	private float y;
	private int radius;
	
	
	public Blob(float x, float y, int radius) {
		this.x = x;
		this.y = y;
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
	
	public void addPos(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public boolean checkCollision(Blob blob) { 
//		System.out.println("this.x " + this.x + " this.radius " + this.radius + " blob.x " + blob.x);
		if(this.x + this.radius > blob.x && this.x - this.radius < blob.x)
			if((this.y + this.radius > blob.y) && (this.y - this.radius < blob.y))
				return true;
		return false;
	}

}
