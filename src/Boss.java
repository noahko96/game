import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the Boss's statistics
 * 
 * @author Noah Over
 *
 */
public class Boss {
	private static final String APPEARANCE = "Mafia Mayhem boss.png";
	private static final int NORMAL_HEALTH = 10;
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private int myHealth;
	
	/**
	 * Constructor for Boss that sets the appearance and health
	 */
	public Boss(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		myHealth = NORMAL_HEALTH;
	}
	
	/**
	 * getter for Boss's appearance
	 * 
	 * @return Boss's appearance in form of ImageView
	 */
	public ImageView getImage(){
		return  myAppearance;
	}
	
	/**
	 * getter for Boss's health
	 * 
	 * @return Boss's current health in the form of an int
	 */
	public int getHealth(){
		return myHealth;
	}
	
	/**
	 * subtracts the damage done by a bullet from the Boss's health
	 * 
	 * @param damage the damage caused by the bullet
	 */
	public void loseHealth(int damage){
		myHealth -= damage;
	}

}
