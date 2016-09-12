import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the statistics of the enforcers
 * 
 * @author Noah Over
 *
 */
public class Enforcer {
	private static final String APPEARANCE = "Mafia Mayhem enforcer.png";
	private static final int INITIAL_HEALTH = 8;
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private int myHealth;
	private boolean isAlive;
	
	/**
	 * constructor for Enforcer which initializes the image, health, and life
	 */
	public Enforcer(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		myHealth = INITIAL_HEALTH;
		isAlive = true;
	}
	
	/**
	 * getter for the image of the enforcer
	 * 
	 * @return the image of the enforcer
	 */
	public ImageView getImage(){
		return  myAppearance;
	}
	
	/**
	 * getter for the health of the enforcer
	 * 
	 * @return the health of the enforcer
	 */
	public int getHealth(){
		return myHealth;
	}
	
	/**
	 * subtracts however much health was lost from the enforcer's health;
	 * 
	 * @param healthLost however much health was lost
	 */
	public void loseHealth(int healthLost){
		myHealth -= healthLost;
	}
	
	/**
	 * returns the boolean that states whether the enforcer is living or dead
	 * 
	 * @return the isAlive boolean
	 */
	public boolean getIsAlive(){
		return isAlive;
	}
	
	/**
	 * kills the enforcer
	 */
	public void die(){
		isAlive = false;
	}
}
