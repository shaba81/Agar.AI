package element;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("blob")
public class Blob {
	
	/* EmbASP integration */
	@Param(0)
	protected int id;
	@Param(1)
	protected float x;
	@Param(2)
	protected float y;
	@Param(3)
	protected float radius;
	
	/* Concurrency */
	Lock l = new ReentrantLock();
	
	public Blob() {}
	
	public Blob(int id, float x, float y, float radius) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public int getId() {
		return id;
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

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void addPos(float x, float y) {
		this.x += x * Math.pow(radius*2, -0.5);
		this.y += y * Math.pow(radius*2, -0.5);
	}
	
	public void increment(final float inc) {
		new Thread() {
			public void run() {
				l.lock();
				float initial = radius;
				float target = initial + inc;
				while(radius < target) { 
					radius += 0.2f; 
					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				l.unlock();
			};
		}.start();
	}
	
	public boolean checkCollision(Blob blob) { 
		if(this.x + this.radius > blob.x && this.x - this.radius < blob.x)
			if((this.y + this.radius > blob.y) && (this.y - this.radius < blob.y))
				return true;
		return false;
	}

	
	public float checkDistance(Blob blob) {
		return new Vector2(blob.getX(), blob.getY()).dst(x, y);
	}
	
}
