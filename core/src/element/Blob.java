package element;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import gameValues.Constants;
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
	
	protected Pair target;
	protected Color color;
	
	/* Concurrency */
	Lock l = new ReentrantLock();
	
	public Blob() {}
	
	public Blob(int id, float x, float y, float radius, Color color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.target = new Pair(Constants.INSEGUI, new Vector2().setToRandomDirection());
		this.color = color;
	}
	
	public void setRandomBlob(int id) {
		this.id = id;
		this.radius = (float) (Math.random() * 20) + 5;
		this.target = new Pair(Constants.INSEGUI, new Vector2());
		setRandomPosition();
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
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setTarget(Pair target) {
		this.target = target;
	}
	
	public void setRandomPosition() {
		float xTmp = (float) ((Math.random() * Constants.fieldDim/2) - Constants.fieldDim/2);
		float yTmp = (float) ((Math.random() * Constants.fieldDim/2) - Constants.fieldDim/2);
		setX(xTmp);
		setY(yTmp);
	}
	
	public void move() {
		if(target.getLeft().equals(Constants.INSEGUI) && id<=0)
			moveToTarget();
		else if(target.getLeft().equals(Constants.SCAPPA) && id<=0)
			goAway();
	}
	
	public void moveToTarget() {
		float MoveToX = target.getRight().x;
        float MoveToY = target.getRight().y;
        float diffX = MoveToX - x;
        float diffY = MoveToY - y;
        float angle = (float)Math.atan2(diffY, diffX);
		addPos((float)Math.cos(angle)*Constants.lerp, 
				(float)Math.sin(angle)*Constants.lerp);
	}
	
	public void goAway() {
		float MoveToX = target.getRight().x;
        float MoveToY = target.getRight().y;
	    float diffX = MoveToX - x;
	    float diffY = MoveToY - y;
	    float angle = (float)Math.atan2(diffY, diffX);
	    addPos(-(float)Math.cos(angle)*Constants.lerp, 
				-(float)Math.sin(angle)*Constants.lerp);
	}
	
	public void addPos(float x, float y) {
		double speed = Math.pow(radius*2, -0.5);
		boolean vertical=true, horizontal=true;
		
		if((this.x + x*speed)>=-(Constants.fieldDim)/2 &&
				(this.x + x*speed)<=(Constants.fieldDim)/2) {
			this.x += x * speed;
			horizontal = false;
		}
		
		if((this.y + y*speed)>=-(Constants.fieldDim)/2 &&
				(this.y + y*speed)<=(Constants.fieldDim)/2) {
			this.y += y * speed;
			vertical = false;
		}
		
		if(horizontal && vertical) {
			if(this.x > 0 && this.y > 0) { 
				//System.out.println("topright");
				if(Math.abs(x) > Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.bottomRight);
				else if(Math.abs(x) <= Math.abs(y)) 
					target = new Pair(Constants.INSEGUI, Constants.topLeft);
			}
			else if(this.x > 0 && this.y < 0) { 
				//System.out.println("bottomright");
				if(Math.abs(x) > Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.bottomLeft);
				else if(Math.abs(x) <= Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.topRight);
			}
			else if(this.x < 0 && this.y > 0) { 
				//System.out.println("topleft");
				if(Math.abs(x) > Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.bottomLeft);
				else if(Math.abs(x) <= Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.topRight);
			}
			else if(this.x < 0 && this.y < 0) {
				//System.out.println("bottomleft");
				if(Math.abs(x) > Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.topLeft);
				else if(Math.abs(x) <= Math.abs(y))
					target = new Pair(Constants.INSEGUI, Constants.bottomRight);
			}
		}
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
		if((this.x + this.radius > blob.x) && (this.x - this.radius < blob.x))
			if((this.y + this.radius > blob.y) && (this.y - this.radius < blob.y))
				return true;
		return false;
	}

	
	public float checkDistance(Blob blob) {
		return new Vector2(blob.getX(), blob.getY()).dst(x, y);
	}
	
}
