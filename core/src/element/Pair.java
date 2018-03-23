package element;

import com.badlogic.gdx.math.Vector2;

public class Pair {
	
	String left;
	Vector2 right;
	
	public Pair(String left, Vector2 right) {
		this.left = left;
		this.right = right;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public Vector2 getRight() {
		return right;
	}

	public void setRight(Vector2 right) {
		this.right = right;
	}

}
