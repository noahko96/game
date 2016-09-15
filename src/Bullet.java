import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Class that keeps track of the information on the bullets
 * It is used by the Level1 and BossLevel classes.
 * It can be used by calling the constructor Bullet().
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
	 * Constructor for Bullet which initializes the shape, activity, and damage
	 */
	public Bullet(){
		myShape = new Circle(USUAL_RADIUS, BULLET_COLOR);
		isActive = true;
		myDamage = INITIAL_DAMAGE;
	}
	
	/**
	 * Getter for the shape of the bullet
	 * 
	 * @return the shape of the bullet
	 */
	public Circle getShape(){
		return myShape;
	}
	
	/**
	 * Getter for the isActive boolean, which determine whether the bullet is still flying or not
	 * 
	 * @return the isActive boolean
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * Makes the isActive boolean false thereby ending the bullets flight
	 */
	public void becomeInactive(){
		isActive = false;
	}
	
	/**
	 * Getter for the damage caused by the bullet
	 * 
	 * @return the damage caused by the bullet
	 */
	public int getDamage(){
		return myDamage;
	}
	
	/**
	 * Setter for the damage caused by the bullet
	 * 
	 * @param damage the damage caused by the bullet
	 */
	public void setDamage(int damage){
		myDamage = damage;
	}

}
