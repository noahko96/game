import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * class that keeps track of the statistics of the bullets
 * 
 * @author Noah Over
 *
 */
public class Bullet {
	private static final int USUAL_RADIUS = 3;
	private static final Paint BULLET_COLOR = Color.ORANGERED;
	private static final int INITIAL_DAMAGE = 1;
	
	private Circle myShape;
	private boolean isActive;
	private int myDamage;
	
	/**
	 * constructor for Bullet which initializes the shape, activity, and damage
	 */
	public Bullet(){
		myShape = new Circle(USUAL_RADIUS, BULLET_COLOR);
		isActive = true;
		myDamage = INITIAL_DAMAGE;
	}
	
	/**
	 * getter for the shape of the bullet
	 * 
	 * @return the shape of the bullet
	 */
	public Circle getShape(){
		return myShape;
	}
	
	/**
	 * getter for the isActive boolean, which determine whether the bullet is still flying or not
	 * 
	 * @return the isActive boolean
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * makes the isActive boolean false thereby ending the bullets flight
	 */
	public void becomeInactive(){
		isActive = false;
	}
	
	/**
	 * getter for the damage caused by the bullet
	 * 
	 * @return the damage caused by the bullet
	 */
	public int getDamage(){
		return myDamage;
	}
	
	/**
	 * setter for the damage caused by the bullet
	 * 
	 * @param damage the damage caused by the bullet
	 */
	public void setDamage(int damage){
		myDamage = damage;
	}

}
